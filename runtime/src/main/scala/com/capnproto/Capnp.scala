// Copyright 2013 Daniel Harrison. All Rights Reserved.

package com.capnproto

import com.foursquare.field.{OptionalField => RField}

import java.io.{ByteArrayOutputStream, InputStream, IOException, RandomAccessFile, OutputStream}
import java.nio.{ByteBuffer, ByteOrder}
import java.nio.channels.FileChannel
import scala.collection.mutable.ArrayBuffer
import scala.io.Source

trait UntypedFieldDescriptor {
  def name: String
  // def unsafeGetter: Function1[Any, Option[Any]]
  // def unsafeManifest: Manifest[_]
}
case class FieldDescriptor[F, S <: Struct[S], M <: MetaStruct[S]](
  override val name: String,
  meta: M
) extends RField[F, M] with UntypedFieldDescriptor {
  override final def owner: M = meta
}

trait UntypedStruct {
  def meta: UntypedMetaStruct
}
trait Struct[S <: Struct[S]] extends UntypedStruct { self: S =>
  type MetaT <: MetaStruct[S]
  def meta: MetaT
}

trait UntypedMetaStruct {
  def recordName: String
  def fields: Seq[UntypedFieldDescriptor]
}
trait MetaStruct[S <: Struct[S]] extends UntypedMetaStruct {
  type Self = this.type
  def create(struct: CapnpStruct): S
  def fields: Seq[FieldDescriptor[_, S, Self]]
}

trait StructBuilder[S <: Struct[S], B <: StructBuilder[S, B]] extends UntypedStruct { self: B =>
  type MetaBuilderT <: MetaStructBuilder[S, B]
  def metaBuilder: MetaBuilderT
}

trait MetaStructBuilder[S <: Struct[S], B <: StructBuilder[S, B]] extends UntypedMetaStruct {
  type Self = this.type
  def dataSectionSizeWords: Short
  def pointerSectionSizeWords: Short
  def create(struct: CapnpStructBuilder): B
}

trait HasUnion[S <: UnionValue[S]] {
  def discriminant: Short
  def switch: S
  def union: UnionMeta[S]
}

trait UnionValue[U <: UnionValue[U]]

trait UnionMeta[U <: UnionValue[U]]

case class CapnpSegment(val id: Int, val buf: ByteBuffer, val offsetWords: Int)
class CapnpArena(val segments: Seq[CapnpSegment]) {
  def getRoot[U <: Struct[U]](meta: MetaStruct[U]): Option[U] = Pointer.parseStruct(meta, this)
}
object CapnpArena {
  def fromInputStream(is: InputStream): CapnpArena = {
    val buf = {
      val source = Source.fromInputStream(is)(scala.io.Codec.ISO8859)
      val bytes = source.map(_.toByte).toArray
      source.close
      ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN)
    }
    CapnpArena.fromByteBuffer(buf)
  }

  def fromByteBuffer(buf: ByteBuffer, offsetWords: Int = 0): CapnpArena = {
    val segmentCount: Int = buf.getInt(offsetWords * 8) + 1
    val segmentSizes = Range(0, segmentCount).map(r => buf.getInt(offsetWords * 8 + 4 + r * 4))
    val segmentsOffsetWords = offsetWords + (segmentCount + 2) / 2
    val offsets = segmentSizes.foldLeft(List[(Int,Int)]())({ case (os, s) => {
      val acc = os.headOption.map(_._2).getOrElse(segmentsOffsetWords)
      (acc, acc + s) :: os
    }}).reverse
    val segments = offsets.zipWithIndex.map({ case ((begin, end), id) => {
      val ret = buf.duplicate
      ret.order(ByteOrder.LITTLE_ENDIAN).position(begin * 8).limit(end * 8)
      new CapnpSegment(id, ret, begin)
    }})
    new CapnpArena(segments)
  }
}

trait Pointer {
  def arena: CapnpArena
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
    parseStruct(meta, CapnpArena.fromByteBuffer(buf))
  }

  def parseStruct[U <: Struct[U]](meta: MetaStruct[U], arena: CapnpArena): Option[U] = {
    fromFirstSegment(arena).flatMap(_ match {
      case s: CapnpStruct => Some(meta.create(s))
      case _ => None
    })
  }

  def fromFirstSegment(arena: CapnpArena): Option[Pointer] = {
    apply(arena, arena.segments.head.buf, arena.segments.head.offsetWords)
  }

  def apply(arena: CapnpArena, buf: ByteBuffer, pointerOffsetWords: Int): Option[Pointer] = {
    val pointerOffsetBytes = pointerOffsetWords * 8
    // println("@" + pointerOffsetBytes + " => " + Range(0, 8).map(o => buf.get(pointerOffsetBytes + o)).map("%02x".format(_)).mkString(" ")) 
    if (buf.getLong(pointerOffsetBytes) == 0) None else {
      val pointerType: Int = buf.get(pointerOffsetBytes) & PointerTypeMask
      val dataOffsetWords: Int = buf.getInt(pointerOffsetBytes) >>> DataOffsetShift
      pointerType match {
        case 0 => Some({
          val dataSectionSizeWords: Short = buf.getShort(pointerOffsetBytes + 4)
          val pointerSectionSizeWords: Short = buf.getShort(pointerOffsetBytes + 6)
          new CapnpStruct(arena, buf, pointerOffsetWords, dataOffsetWords, dataSectionSizeWords, pointerSectionSizeWords)
        })
        case 1 => Some({
          val pointerSize: Int = buf.get(pointerOffsetBytes + 4) & PointerSizeMask
          val listElementCount: Int = {
            if (pointerSize == 7) {
              val tag = new CapnpTag(arena, buf, pointerOffsetWords + 1 + dataOffsetWords)
              tag.elementCountWords
            } else {
              buf.getInt(pointerOffsetWords * 8 + 4) >>> ListElementCountShift
            }
          }
          new CapnpList(arena, buf, pointerOffsetWords, dataOffsetWords, pointerSize, listElementCount)
        })
        case 2 => {
          val landingPadWords: Int = ((buf.get(pointerOffsetBytes) & LandingPadWordsMask) >>> LandingPadWordsShift) + 1
          val pointerOffsetWords: Int = buf.getInt(pointerOffsetBytes) >>> LandingPadOffsetShift
          val segmentid: Int = buf.getInt(pointerOffsetBytes + 4)
          val segment = arena.segments(segmentid)
          val far = new CapnpFarSegmentPointer(arena, segment.buf, segment.offsetWords + pointerOffsetWords, landingPadWords)
          far.getPointer
        }
        case _ => throw new IllegalArgumentException("Unknown pointer type: " + pointerType)
      }
    }
  }
}

class CapnpFarSegmentPointer(
  override val arena: CapnpArena,
  override val buf: ByteBuffer,
  override val pointerOffsetWords: Int,
  val landingPadWords: Int
) extends Pointer {
  def getPointer: Option[Pointer] = {
    if (landingPadWords == 1) {
      Pointer(arena, buf, pointerOffsetWords)
    } else {
      throw new IllegalArgumentException("Double far pointers are not yet supported")
    }
  }
}

class CapnpList(
  override val arena: CapnpArena,
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
    val tag = new CapnpTag(arena, buf, pointerOffsetWords + 1 + dataOffsetWords)
    val offsetWords = offset * (tag.dataSectionSizeWords + tag.pointerSectionSizeWords)
    new CapnpStruct(arena, buf, pointerOffsetWords + 1 + dataOffsetWords, offsetWords, tag.dataSectionSizeWords, tag.pointerSectionSizeWords)
  }
}

class CapnpStruct(
  override val arena: CapnpArena,
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
    Pointer(arena, buf, pointerOffsetWords + 1 + dataOffsetWords + dataSectionSizeWords + offset)
  }
  def getData(offset: Int): Option[Array[Byte]] = getPointer(offset).flatMap(_ match {
    case l: CapnpList => {
      Some((0 to l.listElementCount - 1).map(l.getByte(_)).toArray)
    }
    case _ => None
  })
  def getString(offset: Int): Option[String] = getData(offset).map(bytes => new String(bytes.dropRight(1)))

  def getStruct(offset: Int): Option[CapnpStruct] = getPointer(offset).flatMap(_ match {
    case s: CapnpStruct => Some(s)
    case _ => None
  })
  def getStructList(offset: Int): Seq[CapnpStruct] = getPointer(offset) match {
    case Some(l: CapnpList) => {
      (0 to l.listElementCount-1).map(l.getComposite(_)).map(_ match {
        case s: CapnpStruct => s
      })
    }
    case _ => Nil
  }

  def getNone[T](o: Int = 0): Option[T] = None
}

class CapnpTag(
  override val arena: CapnpArena,
  override val buf: ByteBuffer,
  override val pointerOffsetWords: Int
) extends Pointer {
  val pointerType: Int = buf.get(pointerOffsetWords * 8) & Pointer.PointerTypeMask
  val elementCountWords: Int = buf.getInt(pointerOffsetWords * 8) >>> Pointer.DataOffsetShift
  val dataSectionSizeWords: Short = buf.getShort(pointerOffsetWords * 8 + 4)
  val pointerSectionSizeWords: Short = buf.getShort(pointerOffsetWords * 8 + 6)
}

object CapnpArenaBuilder {
  val SegmentAllocationWords = 1024
}
class CapnpArenaBuilder(
  override val segments: ArrayBuffer[CapnpSegmentBuilder] = ArrayBuffer.empty
) extends CapnpArena(segments) {

  def rootSegment: CapnpSegmentBuilder = segments.headOption.getOrElse(allocate(0)._1)

  def getRootBuilder[S <: Struct[S], B <: StructBuilder[S, B]](meta: MetaStructBuilder[S, B]): B = {
    val (segment, pointerOffset) = allocate(1)
    if (pointerOffset != 0) throw new IllegalArgumentException("Arena.getRootBuilder may only be called once")
    val struct = CapnpStructBuilder(this, segment, pointerOffset, meta.dataSectionSizeWords, meta.pointerSectionSizeWords)
    meta.create(struct)
  }

  def allocate(wordCount: Int): (CapnpSegmentBuilder, Int) = {
    val allocationOpt = segments.view.reverseMap(s => s.allocate(wordCount).map((s, _))).flatten.headOption
    allocationOpt.getOrElse({
      val size = math.max(wordCount, CapnpArenaBuilder.SegmentAllocationWords * (2 << segments.size) * 8)
      val buf = ByteBuffer.allocate(size).order(ByteOrder.LITTLE_ENDIAN)
      val segment = new CapnpSegmentBuilder(segments.size, buf, 0, wordCount)
      segments.append(segment)
      (segment, 0)
    })
  }

  def writeToOutputStream(os: OutputStream): Unit = {
    val bufSize = 4 + 4 * segments.size
    val padding = if (bufSize % 8 == 0) 0 else 8 - bufSize % 8
    val buf = ByteBuffer.allocate(bufSize + padding).order(ByteOrder.LITTLE_ENDIAN)
      .putInt(0, segments.size - 1)
    segments.zipWithIndex.foreach({ case (s, i) => buf.putInt(4 + 4 * i, s.positionWords) })
    os.write(buf.array, 0, bufSize + padding)
    segments.foreach(s => os.write(s.buf.array, s.offsetWords * 8, s.positionWords * 8))
  }
}

class CapnpSegmentBuilder(
  id: Int,
  buf: ByteBuffer,
  offsetWords: Int,
  var positionWords: Int = 0
) extends CapnpSegment(id, buf, offsetWords) {
  def allocate(wordCount: Int): Option[Int] = {
    if ((offsetWords + positionWords + wordCount) * 8 > buf.limit) None else Some({
      val ret = positionWords
      positionWords += wordCount
      ret
    })
  }
}

object CapnpListBuilder {
  def simpleList(
    arena: CapnpArenaBuilder,
    segment: CapnpSegmentBuilder,
    pointerOffsetWords: Int,
    pointerSize: Int,
    listElementCount: Int
  ): CapnpListBuilder = {
    if (pointerSize == 7) ??? else {
      val bits = pointerSize match {
        case 2 => 8
      }
      val bitsSize = bits * listElementCount
      val padding = if (bitsSize % 64 == 0) 0 else 64 - bitsSize % 64
      val sizeWords = (bitsSize + padding) / 64
      val allocatedWords = segment.allocate(sizeWords).getOrElse(???)
      val dataOffsetWords = allocatedWords - (pointerOffsetWords + 1)

      val pointerIntOne = (dataOffsetWords << 2) + 1
      segment.buf.putInt((segment.offsetWords + pointerOffsetWords) * 8, pointerIntOne)
      val pointerIntTwo = (listElementCount << 3) + pointerSize
      segment.buf.putInt((segment.offsetWords + pointerOffsetWords) * 8 + 4, pointerIntTwo)

      new CapnpListBuilder(arena, segment, pointerOffsetWords, dataOffsetWords, pointerSize, listElementCount)
    }
  }

  def taggedList(
    arena: CapnpArenaBuilder,
    segment: CapnpSegmentBuilder,
    pointerOffsetWords: Int,
    listElementCount: Int,
    dataSectionSizeWords: Short,
    pointerSectionSizeWords: Short
  ): CapnpListBuilder = {
    val pointerSize = 7

    val dataSizeWords = (dataSectionSizeWords + pointerSectionSizeWords) * listElementCount
    val sizeWords = 1 + dataSizeWords
    val allocatedWords = segment.allocate(sizeWords).getOrElse(???)
    val dataOffsetWords = allocatedWords - (pointerOffsetWords + 1)

    val pointerIntOne = (dataOffsetWords << 2) + 1
    segment.buf.putInt((segment.offsetWords + pointerOffsetWords) * 8, pointerIntOne)
    val pointerIntTwo = (dataSizeWords << 3) + pointerSize
    segment.buf.putInt((segment.offsetWords + pointerOffsetWords) * 8 + 4, pointerIntTwo)

    // TODO(dan): Put this in a CapnpTagBuilder.
    val tagIntOne = (listElementCount << 2) + 0
    segment.buf.putInt((segment.offsetWords + pointerOffsetWords + 1 + dataOffsetWords) * 8, tagIntOne)
    segment.buf.putShort((segment.offsetWords + pointerOffsetWords + 1 + dataOffsetWords) * 8 + 4, dataSectionSizeWords)
    segment.buf.putShort((segment.offsetWords + pointerOffsetWords + 1 + dataOffsetWords) * 8 + 6, pointerSectionSizeWords)

    new CapnpListBuilder(arena, segment, pointerOffsetWords, dataOffsetWords + 1, pointerSize, listElementCount)
  }
}
class CapnpListBuilder(
  override val arena: CapnpArenaBuilder,
  val segment: CapnpSegmentBuilder,
  override val pointerOffsetWords: Int,
  override val dataOffsetWords: Int,
  override val pointerSize: Int,
  override val listElementCount: Int
) extends CapnpList(arena, segment.buf, segment.offsetWords + pointerOffsetWords, dataOffsetWords, pointerSize, listElementCount) {
  def putBytes(bytes: Array[Byte]): Unit = {
    val offsetBytes = (segment.offsetWords + pointerOffsetWords + 1 + dataOffsetWords) * 8
    Range(0, bytes.size).foreach(o => segment.buf.put(offsetBytes + o, bytes(o)))
  }

  def initStruct(index: Int, meta: MetaStructBuilder[_, _]): CapnpStructBuilder = {
    val elementSizeWords = meta.dataSectionSizeWords + meta.pointerSectionSizeWords
    val structDataOffsetWords = dataOffsetWords + elementSizeWords * index
    new CapnpStructBuilder(arena, segment, pointerOffsetWords, structDataOffsetWords, meta.dataSectionSizeWords, meta.pointerSectionSizeWords)
  }
}

object CapnpStructBuilder {
  def apply(
    arena: CapnpArenaBuilder,
    segment: CapnpSegmentBuilder,
    pointerOffsetWords: Int,
    dataSectionSizeWords: Short,
    pointerSectionSizeWords: Short
  ): CapnpStructBuilder = {
    val sizeWords = dataSectionSizeWords + pointerSectionSizeWords
    val allocatedWords = segment.allocate(sizeWords).getOrElse(???)
    val dataOffsetWords = allocatedWords - (pointerOffsetWords + 1)

    val structIntOne: Int = (dataOffsetWords << 2) + 0
    segment.buf.putInt((segment.offsetWords + pointerOffsetWords) * 8, structIntOne)
    segment.buf.putShort((segment.offsetWords + pointerOffsetWords) * 8 + 4, dataSectionSizeWords)
    segment.buf.putShort((segment.offsetWords + pointerOffsetWords) * 8 + 6, pointerSectionSizeWords)

    new CapnpStructBuilder(arena, segment, pointerOffsetWords, dataOffsetWords, dataSectionSizeWords, pointerSectionSizeWords)
  }
}
class CapnpStructBuilder(
  override val arena: CapnpArenaBuilder,
  val segment: CapnpSegmentBuilder,
  override val pointerOffsetWords: Int,
  override val dataOffsetWords: Int,
  override val dataSectionSizeWords: Short,
  override val pointerSectionSizeWords: Short
) extends CapnpStruct(arena, segment.buf, segment.offsetWords + pointerOffsetWords, dataOffsetWords, dataSectionSizeWords, pointerSectionSizeWords) {

  def setBoolean(offset: Int, value: Boolean): Unit = ???
  def setByte(offset: Int, value: Byte): Unit = {
    segment.buf.put((segment.offsetWords + pointerOffsetWords + 1 + dataOffsetWords) * 8 + offset * 1, value)
  }
  def setShort(offset: Int, value: Short): Unit = {
    segment.buf.putShort((segment.offsetWords + pointerOffsetWords + 1 + dataOffsetWords) * 8 + offset * 2, value)
  }
  def setInt(offset: Int, value: Int): Unit = {
    segment.buf.putInt((segment.offsetWords + pointerOffsetWords + 1 + dataOffsetWords) * 8 + offset * 4, value)
  }
  def setLong(offset: Int, value: Long): Unit = {
    segment.buf.putLong((segment.offsetWords + pointerOffsetWords + 1 + dataOffsetWords) * 8 + offset * 8, value)
  }
  def setDouble(offset: Int, value: Double): Unit = {
    segment.buf.putDouble((segment.offsetWords + pointerOffsetWords + 1 + dataOffsetWords) * 8 + offset * 8, value)
  }

  def setData(offset: Int, bytes: Array[Byte]): Unit = {
    CapnpListBuilder.simpleList(arena, segment, pointerOffsetWords + 1 + dataOffsetWords + dataSectionSizeWords + offset, 2, bytes.size)
      .putBytes(bytes)
  }
  def setString(offset: Int, value: String): Unit = {
    val bytes = value.getBytes("UTF-8") :+ 0.toByte
    setData(offset, bytes)
  }

  def initPointerList(offset: Int, listElementCount: Int, meta: MetaStructBuilder[_, _]): CapnpListBuilder = {
    CapnpListBuilder.taggedList(arena, segment, pointerOffsetWords + 1 + dataOffsetWords + dataSectionSizeWords + offset, listElementCount, meta.dataSectionSizeWords, meta.pointerSectionSizeWords)
  }

  def setNone(): Unit = {}
}
