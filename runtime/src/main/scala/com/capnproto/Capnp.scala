// Copyright 2013 Daniel Harrison. All Rights Reserved.

package com.capnproto

import com.foursquare.field.{OptionalField => RField}

import java.io.{ByteArrayOutputStream, InputStream, IOException, RandomAccessFile}
import java.nio.{ByteBuffer, ByteOrder}
import java.nio.channels.FileChannel
import scala.io.Source

trait UntypedFieldDescriptor {
  // def id: Int
  def name: String
  // def unsafeGetter: Function1[Any, Option[Any]]
  // def unsafeManifest: Manifest[_]
}
case class FieldDescriptor[F, S <: Struct[S], M <: MetaStruct[S]](
  // override val id: Int,
  override val name: String,
  meta: M
) extends RField[F, M] with UntypedFieldDescriptor {
  override final def owner: M = meta
}

trait UntypedStruct {
  def meta: UntypedMetaStruct
}
trait Struct[U <: Struct[U]] extends UntypedStruct { self: U =>
  type MetaT <: MetaStruct[U]
  def meta: MetaT
}

trait UntypedMetaStruct {
  def recordName: String
  // def createRawRecord: UntypedStruct
  def fields: Seq[UntypedFieldDescriptor]
  // def ifInstanceFrom(x: AnyRef): Option[UntypedStruct]
}
trait MetaStruct[U <: Struct[U]] extends UntypedMetaStruct {
  type Self = this.type
  def create(struct: CapnpStruct): U
  def fields: Seq[FieldDescriptor[_, U, Self]]
}

trait HasUnion[U <: UnionValue[U]] {
  def discriminant: Short
  def switch: U
  def union: UnionMeta[U]
}

trait UnionValue[U <: UnionValue[U]]

trait UnionMeta[U <: UnionValue[U]]

case class Segment(val buf: ByteBuffer, val offsetWords: Int)
case class Segments(val segments: Seq[Segment]) {
  def asStruct[U <: Struct[U]](meta: MetaStruct[U]): Option[U] = Pointer.parseStruct(meta, this)
}
object Segments {
  def fromInputStream(is: InputStream): Segments = {
    val buf = {
      val source = Source.fromInputStream(is)(scala.io.Codec.ISO8859)
      val bytes = source.map(_.toByte).toArray
      source.close
      ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN)
    }
    Segments.fromByteBuffer(buf)
  }

  def fromByteBuffer(buf: ByteBuffer, offsetWords: Int = 0): Segments = {
    val segmentCount: Int = buf.getInt(offsetWords * 8) + 1
    val segmentSizes = Range(0, segmentCount).map(r => buf.getInt(offsetWords * 8 + 4 + r * 4))
    val segmentsOffsetWords = offsetWords + (segmentCount + 2) / 2
    val offsets = segmentSizes.foldLeft(List[(Int,Int)]())({ case (os, s) => {
      val acc = os.headOption.map(_._2).getOrElse(segmentsOffsetWords)
      (acc, acc + s) :: os
    }}).reverse
    val segments = offsets.map({ case (begin, end) => {
      val ret = buf.duplicate
      ret.order(ByteOrder.LITTLE_ENDIAN).position(begin * 8).limit(end * 8)
      new Segment(ret, begin)
    }})
    new Segments(segments)
  }
}

trait Pointer {
  def segments: Segments
  def buf: ByteBuffer
  def pointerOffsetWords: Int
}
object Pointer {
  val PointerTypeMask: Byte = 3
  val DataOffsetShift = 2
  val PointerSizeMask: Byte = 7
  val ListElementCountShift = 3
  val LandingPadWordsMask: Int = 4
  val LandingPadWordsShift: Int = 2
  val LandingPadOffsetShift: Int = 3

  def parseStructFromPath[U <: Struct[U]](meta: MetaStruct[U], path: String): Option[U] = {
    val file = new RandomAccessFile(path, "r")
    val channel = file.getChannel
    val buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size).order(ByteOrder.LITTLE_ENDIAN)
    file.close
    parseStruct(meta, Segments.fromByteBuffer(buf))
  }

  def parseStruct[U <: Struct[U]](meta: MetaStruct[U], segments: Segments): Option[U] = {
    fromFirstSegment(segments).flatMap(_ match {
      case s: CapnpStruct => Some(meta.create(s))
      case _ => None
    })
  }

  def fromFirstSegment(segments: Segments): Option[Pointer] = {
    apply(segments, segments.segments.head.buf, segments.segments.head.offsetWords)
  }

  def apply(segments: Segments, buf: ByteBuffer, pointerOffsetWords: Int): Option[Pointer] = {
    val pointerOffsetBytes = pointerOffsetWords * 8
    // println("@" + pointerOffsetBytes + " => " + Range(0, 8).map(o => buf.get(pointerOffsetBytes + o)).map("%02x".format(_)).mkString(" ")) 
    if (buf.getLong(pointerOffsetBytes) == 0) None else {
      val pointerType: Int = buf.get(pointerOffsetBytes) & PointerTypeMask
      val dataOffsetWords: Int = buf.getInt(pointerOffsetBytes) >>> DataOffsetShift
      pointerType match {
        case 0 => Some({
          val dataSectionSizeWords: Short = buf.getShort(pointerOffsetBytes + 4)
          val pointerSectionSizeWords: Short = buf.getShort(pointerOffsetBytes + 6)
          new CapnpStruct(segments, buf, pointerOffsetWords, dataOffsetWords, dataSectionSizeWords, pointerSectionSizeWords)
        })
        case 1 => Some({
          val pointerSize: Int = buf.get(pointerOffsetBytes + 4) & PointerSizeMask
          val listElementCount: Int = {
            if (pointerSize == 7) {
              val tag = new CapnpTag(segments, buf, pointerOffsetWords + 1 + dataOffsetWords)
              tag.elementCountWords
            } else {
              buf.getInt(pointerOffsetWords * 8 + 4) >>> ListElementCountShift
            }
          }
          new CapnpList(segments, buf, pointerOffsetWords, dataOffsetWords, pointerSize, listElementCount)
        })
        case 2 => {
          val landingPadWords: Int = ((buf.get(pointerOffsetBytes) & LandingPadWordsMask) >>> LandingPadWordsShift) + 1
          val pointerOffsetWords: Int = buf.getInt(pointerOffsetBytes) >>> LandingPadOffsetShift
          val segmentid: Int = buf.getInt(pointerOffsetBytes + 4)
          val segment = segments.segments(segmentid)
          val far = new CapnpFarSegmentPointer(segments, segment.buf, segment.offsetWords + pointerOffsetWords, landingPadWords)
          far.getPointer
        }
        case _ => throw new IllegalArgumentException("Unknown pointer type: " + pointerType)
      }
    }
  }
}

class CapnpFarSegmentPointer(
  override val segments: Segments,
  override val buf: ByteBuffer,
  override val pointerOffsetWords: Int,
  val landingPadWords: Int
) extends Pointer {
  def getPointer: Option[Pointer] = {
    if (landingPadWords == 1) {
      Pointer(segments, buf, pointerOffsetWords)
    } else {
      throw new IllegalArgumentException("Double far pointers are not yet supported")
    }
  }
}

class CapnpList(
  override val segments: Segments,
  override val buf: ByteBuffer,
  override val pointerOffsetWords: Int,
  val dataOffsetWords: Int,
  val pointerSize: Int,
  val listElementCount: Int
) extends Pointer {

  def getByte(offset: Int): Byte = {
    if (pointerSize != 2) throw new IllegalArgumentException("This list is not Bytes.")
    buf.get((pointerOffsetWords + 1 + dataOffsetWords) * 8 + offset * 1)
  }

  def getComposite(offset: Int): Pointer = {
    if (pointerSize != 7) throw new IllegalArgumentException("This list is not Composites.")
    val tag = new CapnpTag(segments, buf, pointerOffsetWords + 1 + dataOffsetWords)
    val offsetWords = offset * (tag.dataSectionSizeWords + tag.pointerSectionSizeWords)
    new CapnpStruct(segments, buf, pointerOffsetWords + 1 + dataOffsetWords, offsetWords, tag.dataSectionSizeWords, tag.pointerSectionSizeWords)
  }
}

class CapnpStruct(
  override val segments: Segments,
  override val buf: ByteBuffer,
  override val pointerOffsetWords: Int,
  val dataOffsetWords: Int,
  val dataSectionSizeWords: Short,
  val pointerSectionSizeWords: Short
) extends Pointer {

  def getBoolean(offset: Int): Option[java.lang.Boolean] = None
  def getByte(offset: Int): Option[java.lang.Byte] = {
    val ret = buf.get((pointerOffsetWords + 1 + dataOffsetWords) * 8 + offset * 1)
    val default: java.lang.Byte = 0.toByte
    if (ret != default) Some(ret) else None    
  }
  def getShort(offset: Int): Option[java.lang.Short] = {
    val ret = buf.getShort((pointerOffsetWords + 1 + dataOffsetWords) * 8 + offset * 2)
    val default: java.lang.Short = 0.toShort
    if (ret != default) Some(ret) else None    
  }
  def getInt(offset: Int): Option[java.lang.Integer] = {
    val ret = buf.getInt((pointerOffsetWords + 1 + dataOffsetWords) * 8 + offset * 4)
    val default: java.lang.Integer = 0
    if (ret != default) Some(ret) else None    
  }
  def getLong(offset: Int): Option[java.lang.Long] = {
    val ret = buf.getLong((pointerOffsetWords + 1 + dataOffsetWords) * 8 + offset * 8)
    val default: java.lang.Long = 0
    if (ret != default) Some(ret) else None    
  }
  def getDouble(offset: Int): Option[java.lang.Double] = {
    val ret = buf.getDouble((pointerOffsetWords + 1 + dataOffsetWords) * 8 + offset * 8)
    val default: java.lang.Double = 0
    if (ret != default) Some(ret) else None    
  }

  def getPointer(offset: Int): Option[Pointer] = {
    Pointer(segments, buf, pointerOffsetWords + 1 + dataOffsetWords + dataSectionSizeWords + offset)
  }
  def getString(offset: Int): Option[String] = getPointer(offset).flatMap(_ match {
    case l: CapnpList => {
      Some(new String((0 to l.listElementCount - 1).map(l.getByte(_)).toArray.dropRight(1)))
    }
    case _ => None
  })
  def getStruct(offset: Int): Option[CapnpStruct] = getPointer(offset).flatMap(_ match {
    case s: CapnpStruct => Some(s)
    case _ => None
  })
  def getStructList(offset: Int): Option[Seq[CapnpStruct]] = getPointer(offset).map(_ match {
    case l: CapnpList => {
      (0 to l.listElementCount-1).map(l.getComposite(_)).map(_ match {
        case s: CapnpStruct => s
      })
    }
    case _ => Nil
  })

  def getNone[T](o: Int = 0): Option[T] = None
}

class CapnpTag(
  override val segments: Segments,
  override val buf: ByteBuffer,
  override val pointerOffsetWords: Int
) extends Pointer {
  val pointerType: Int = buf.get(pointerOffsetWords * 8) & Pointer.PointerTypeMask
  val elementCountWords: Int = buf.getInt(pointerOffsetWords * 8) >>> Pointer.DataOffsetShift
  val dataSectionSizeWords: Short = buf.getShort(pointerOffsetWords * 8 + 4)
  val pointerSectionSizeWords: Short = buf.getShort(pointerOffsetWords * 8 + 6)
}
