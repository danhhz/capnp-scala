// Copyright 2013 Daniel Harrison. All Rights Reserved.

package com.capnproto

import com.foursquare.field.{OptionalField => RField}

import java.io.{ByteArrayOutputStream, InputStream, IOException, RandomAccessFile}
import java.nio.{ByteBuffer, ByteOrder}
import java.nio.channels.FileChannel

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

trait UnionValue[U <: UnionValue[U]] {

}

trait UnionMeta[U <: UnionValue[U]]

object Segment {
  def parseSegments(buf: ByteBuffer, offsetWords: Int = 0) = {
    val segmentCount: Int = buf.getInt(offsetWords * 8)
    val segmentSizes = Range(0, segmentCount).map(r => buf.getInt(offsetWords * 8 + 4 + r * 4))
    val padding = if (segmentSizes.size % 2 == 0) 4 else 0
    segmentSizes
  }
}

trait Pointer {
  def buf: ByteBuffer
  def pointerOffsetWords: Int
}
object Pointer {
  val PointerTypeMask: Byte = 3
  val DataOffsetMask: Int = ~(PointerTypeMask.toInt)
  val DataOffsetShift = 2
  val PointerSizeMask: Byte = 7
  val ListElementCountMask: Int = ~(PointerSizeMask.toInt)
  val ListElementCountShift = 3

  def parseStructFromPath[U <: Struct[U]](meta: MetaStruct[U], path: String): Option[U] = {
    val file = new RandomAccessFile(path, "r")
    val channel = file.getChannel
    val buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size).order(ByteOrder.LITTLE_ENDIAN)
    file.close
    parseStruct(meta, buf, 1)
  }

  def parseStruct[U <: Struct[U]](meta: MetaStruct[U], buf: ByteBuffer, pointerOffsetWords: Int = 0): Option[U] = {
    apply(buf, pointerOffsetWords).flatMap(_ match {
      case s: CapnpStruct => Some(meta.create(s))
      case _ => None
    })
  }

  def apply(buf: ByteBuffer, pointerOffsetWords: Int): Option[Pointer] = {
    val pointerOffsetBytes = pointerOffsetWords * 8
    println("@" + pointerOffsetBytes + " => " + Range(0, 8).map(o => buf.get(pointerOffsetBytes + o)).map("%02x".format(_)).mkString(" ")) 
    if (buf.getLong(pointerOffsetBytes) == 0) None else {
      val pointerType: Byte = {
        (buf.get(pointerOffsetBytes) & PointerTypeMask).toByte
      }
      val dataOffsetWords: Int = {
        (buf.getInt(pointerOffsetBytes) & DataOffsetMask) >>> DataOffsetShift
      }
      pointerType match {
        case 0 => Some({
          val dataSectionSizeWords: Short = {
            buf.getShort(pointerOffsetBytes + 4)
          }
          val pointerSectionSizeWords: Short = {
            buf.getShort(pointerOffsetBytes + 6)
          }
          new CapnpStruct(buf, pointerOffsetWords, dataOffsetWords, dataSectionSizeWords, pointerSectionSizeWords)
        })
        case 1 => Some({
          val pointerSize: Int = {
            buf.get(pointerOffsetBytes + 4) & PointerSizeMask
          }
          val listElementCount: Int = {
            if (pointerSize == 7) {
              val tag = new CapnpTag(buf, pointerOffsetWords + 1 + dataOffsetWords)
              tag.elementCountWords
            } else {
              (buf.getInt(pointerOffsetWords * 8 + 4) & ListElementCountMask) >>> ListElementCountShift
            }
          }
          new CapnpList(buf, pointerOffsetWords, dataOffsetWords, pointerSize, listElementCount)
        })
        case 2 => throw new IllegalArgumentException("Inter-Segment Pointers are not yet supported")
        case _ => throw new IllegalArgumentException("Unknown pointer type: " + pointerType)
      }
    }
  }
}

class CapnpList(
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
    val tag = new CapnpTag(buf, pointerOffsetWords + 1 + dataOffsetWords)
    val offsetWords = offset * (tag.dataSectionSizeWords + tag.pointerSectionSizeWords)
    new CapnpStruct(buf, pointerOffsetWords + 1 + dataOffsetWords, offsetWords, tag.dataSectionSizeWords, tag.pointerSectionSizeWords)
  }
}

class CapnpStruct(
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
    Pointer(buf, pointerOffsetWords + 1 + dataOffsetWords + dataSectionSizeWords + offset)
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

class CapnpTag(val buf: ByteBuffer, override val pointerOffsetWords: Int) extends Pointer {
  val pointerType: Byte = {
    (buf.get(pointerOffsetWords * 8) & Pointer.PointerTypeMask).toByte
  }
  val elementCountWords: Int = {
    (buf.getInt(pointerOffsetWords * 8) & Pointer.DataOffsetMask) >>> Pointer.DataOffsetShift
  }
  val dataSectionSizeWords: Short = {
    buf.getShort(pointerOffsetWords * 8 + 4)
  }
  val pointerSectionSizeWords: Short = {
    buf.getShort(pointerOffsetWords * 8 + 6)
  }
}
