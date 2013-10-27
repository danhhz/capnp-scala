// Copyright 2013 Daniel Harrison. All Rights Reserved.

package com.capnproto

import com.foursquare.field.{OptionalField => RField}
import com.twitter.util.Future

import java.io.{ByteArrayOutputStream, InputStream, IOException, RandomAccessFile, OutputStream}
import java.nio.{ByteBuffer, ByteOrder}
import java.nio.channels.FileChannel
import scala.collection.mutable.ArrayBuffer
import scala.io.Source

trait UntypedFieldDescriptor {
  def name: String
  def unsafeGetter: Function1[Any, Option[Any]]
  def unsafeManifest: Manifest[_]
  def isUnion: Boolean
}
case class FieldDescriptor[F, S <: Struct[S], M <: MetaStruct[S]](
  override val name: String,
  meta: M,
  getter: S => Option[F],
  manifest: Manifest[F],
  override val isUnion: Boolean
) extends RField[F, M] with UntypedFieldDescriptor {
  override final def owner: M = meta
  override def unsafeGetter: Function1[Any, Option[Any]] = getter.asInstanceOf[Function1[Any, Option[Any]]]
  override def unsafeManifest: Manifest[_] = manifest
}

trait UntypedStruct {
  def meta: UntypedMetaStruct

  override def toString: String = {
    val union = this match {
      case u: HasUnion[_] => " [" + u.switch.toString + "]"
      case _ => ""
    }
    "(" +
    meta.fields.filterNot(_.isUnion).flatMap(field => {
      field.unsafeGetter(this).map(v => field.name + ": " + v)
    }).mkString(", ") +
    union +
    ")"
  }
}
trait Struct[S <: Struct[S]] extends UntypedStruct { self: S =>
  type MetaT <: MetaStruct[S]
  type MetaBuilderT <: MetaStructBuilder[S, _]
  def meta: MetaT
  def metaBuilder: MetaBuilderT
}

trait UntypedMetaStruct {
  def structName: String
  def create(struct: CapnpStruct): Struct[_]
  def fields: Seq[UntypedFieldDescriptor]
}
trait MetaStruct[S <: Struct[S]] extends UntypedMetaStruct {
  type Self = this.type
  override def create(struct: CapnpStruct): S
  def fields: Seq[FieldDescriptor[_, S, Self]]
}

trait StructBuilder[S <: Struct[S], B <: StructBuilder[S, B]] extends UntypedStruct { self: B =>
  type MetaBuilderT <: MetaStructBuilder[S, B]
  def metaBuilder: MetaBuilderT
}

trait MetaStructBuilder[S <: Struct[S], B <: StructBuilder[S, B]] {
  type Self = this.type
  def structName: String
  def dataSectionSizeWords: Short
  def pointerSectionSizeWords: Short
  def create(struct: CapnpStructBuilder): B
  def fields: Seq[UntypedFieldDescriptor]
}

trait HasUnion[S <: UnionValue[S]] {
  def discriminant: Short
  def switch: S
  def union: UnionMeta[S]
}

trait UnionValue[U <: UnionValue[U]]

trait UnionMeta[U <: UnionValue[U]]

trait UntypedMethodDescriptor {
  def name: String
  def request: UntypedMetaStruct
  def response: UntypedMetaStruct
  def unsafeGetter: Function1[UntypedInterface, Function2[Struct[_], CapnpArenaBuilder, Future[Struct[_]]]]
}
case class MethodDescriptor[Req <: Struct[Req], Res <: Struct[Res], I <: Interface[I], M <: MetaInterface[I]](
  override val name: String,
  meta: M,
  override val request: MetaStruct[Req],
  override val response: MetaStruct[Res],
  getter: I => Function2[Req, CapnpArenaBuilder, Future[Res]]
) extends UntypedMethodDescriptor {
  override def unsafeGetter: Function1[UntypedInterface, Function2[Struct[_], CapnpArenaBuilder, Future[Struct[_]]]] = getter.asInstanceOf[Function1[UntypedInterface, Function2[Struct[_], CapnpArenaBuilder, Future[Struct[_]]]]]
}

trait UntypedInterface {
  def meta: UntypedMetaInterface
}
trait Interface[I <: Interface[I]] extends UntypedInterface { self: I =>
  type MetaT <: MetaInterface[I]
  def meta: MetaT
}

trait UntypedMetaInterface {
  def interfaceName: String
  def methods: Seq[UntypedMethodDescriptor]
}
trait MetaInterface[I <: Interface[I]] extends UntypedMetaInterface {
  type Self = this.type
  def methods: Seq[MethodDescriptor[_, _, I, Self]]
}

case class CapnpSegment(val id: Int, val buf: ByteBuffer, val offsetWords: Int)
class CapnpArena(val segments: Seq[CapnpSegment]) {
  def getRoot[U <: Struct[U]](meta: MetaStruct[U]): Option[U] = Pointer.parseStruct(meta, this)
}
object CapnpArena {
  def fromInputStream(is: InputStream): Option[CapnpArena] = {
    val source = Source.fromInputStream(is)(scala.io.Codec.ISO8859)
    val bytes = source.map(_.toByte).toArray
    source.close
    fromBytes(bytes)
  }

  def fromBytes(bytes: Array[Byte], offsetWords: Int = 0): Option[CapnpArena] = {
    fromByteBuffer(ByteBuffer.wrap(bytes), offsetWords)
  }

  def fromByteBuffer(buf: ByteBuffer, offsetWords: Int = 0): Option[CapnpArena] = {
    try {
      buf.order(ByteOrder.LITTLE_ENDIAN)
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
      Some(new CapnpArena(segments))
    } catch {
      case ioob: IndexOutOfBoundsException => None
    }
  }
}

trait Pointer[P <: Pointer[P]] {
  def arena: CapnpArena
  def buf: ByteBuffer
  def pointerOffsetWords: Int
  def sizeWords: Int
  def copyTo(offsetWords: Int): P
  def copyToSegment(segment: CapnpSegmentBuilder, offsetWords: Int): P
  def copyDataToSegment(segment: CapnpSegmentBuilder, offsetWords: Int): Unit
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
    CapnpArena.fromByteBuffer(buf).flatMap(arena => parseStruct(meta, arena))
  }

  def parseStruct[U <: Struct[U]](meta: MetaStruct[U], arena: CapnpArena): Option[U] = {
    fromFirstSegment(arena).flatMap(_ match {
      case s: CapnpStruct => Some(meta.create(s))
      case _ => None
    })
  }

  def parseStructRaw(meta: UntypedMetaStruct, arena: CapnpArena): Option[Struct[_]] = {
    fromFirstSegment(arena).flatMap(_ match {
      case s: CapnpStruct => Some(meta.create(s))
      case _ => None
    })
  }

  def fromFirstSegment(arena: CapnpArena): Option[Pointer[_]] = {
    apply(arena, arena.segments.head.buf, arena.segments.head.offsetWords)
  }

  def apply(arena: CapnpArena, buf: ByteBuffer, pointerOffsetWords: Int): Option[Pointer[_]] = {
    val pointerOffsetBytes = pointerOffsetWords * 8
    // print("@" + pointerOffsetBytes + " => " + Range(0, 8).map(o => buf.get(pointerOffsetBytes + o)).map("%02x".format(_)).mkString(" ") + " ")
    if (buf.getLong(pointerOffsetBytes) == 0) {
      // println("[null]")
      None
    } else {
      val pointerType: Int = buf.get(pointerOffsetBytes) & PointerTypeMask
      val dataOffsetWords: Int = buf.getInt(pointerOffsetBytes) >> DataOffsetShift
      pointerType match {
        case 0 => Some({
          val dataSectionSizeWords: Short = buf.getShort(pointerOffsetBytes + 4)
          val pointerSectionSizeWords: Short = buf.getShort(pointerOffsetBytes + 6)
          // println(pointerType, dataOffsetWords, dataSectionSizeWords, pointerSectionSizeWords)
          new CapnpStruct(arena, buf, pointerOffsetWords, dataOffsetWords, dataSectionSizeWords, pointerSectionSizeWords)
        })
        case 1 => Some({
          val pointerSize: Int = buf.get(pointerOffsetBytes + 4) & PointerSizeMask
          val listElementCount: Int = if (pointerSize == 7) {
            val tag = new CapnpTag(arena, buf, pointerOffsetWords + 1 + dataOffsetWords)
            tag.elementCountWords
          } else {
            buf.getInt(pointerOffsetWords * 8 + 4) >>> ListElementCountShift
          }
          // println(pointerType, dataOffsetWords, pointerSize, listElementCount)
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
  val arena: CapnpArena,
  val buf: ByteBuffer,
  val pointerOffsetWords: Int,
  val landingPadWords: Int
) {
  def getPointer: Option[Pointer[_]] = {
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
) extends Pointer[CapnpList] {
  lazy val tag = new CapnpTag(arena, buf, pointerOffsetWords + 1 + dataOffsetWords)

  private def divideRoundUp(a: Int, b: Int): Int = (a + b - 1) / b

  def dataSizeWords: Int = pointerSize match {
    case 0 => 0
    case 1 => divideRoundUp(1 * listElementCount, 64)
    case 2 => divideRoundUp(8 * listElementCount, 64)
    case 3 => divideRoundUp(16 * listElementCount, 64)
    case 4 => divideRoundUp(32 * listElementCount, 64)
    case 5 => divideRoundUp(64 * listElementCount, 64)
    case 6 => listElementCount
    case 7 => 1 + (tag.dataSectionSizeWords + tag.pointerSectionSizeWords) * listElementCount
  }
  override def sizeWords: Int = {
    val recursiveDataSizeWords = pointerSize match {
      case 0 => 0
      case 1 => divideRoundUp(1 * listElementCount, 64)
      case 2 => divideRoundUp(8 * listElementCount, 64)
      case 3 => divideRoundUp(16 * listElementCount, 64)
      case 4 => divideRoundUp(32 * listElementCount, 64)
      case 5 => divideRoundUp(64 * listElementCount, 64)
      case 6 => Range(0, listElementCount).map(getPointer(_).sizeWords).sum
      case 7 => 1 + Range(0, listElementCount).map(getComposite(_).sizeWords).sum - listElementCount
    }
    1 + recursiveDataSizeWords
  }

  def getVoid(offset: Int): Unit = {
    if (pointerSize != 0) throw new IllegalArgumentException("This list is not Voids.")
  }

  def getBoolean(offset: Int): java.lang.Boolean = {
    if (pointerSize != 1) throw new IllegalArgumentException("This list is not Booleans.")
    val byte = buf.get((pointerOffsetWords + 1 + dataOffsetWords) * 8 + (offset / 8))
    val mask = 1 << (offset % 8)
    if ((byte & mask) > 0) java.lang.Boolean.TRUE else java.lang.Boolean.FALSE
  }

  def getByte(offset: Int): java.lang.Byte = {
    if (pointerSize != 2) throw new IllegalArgumentException("This list is not Bytes.")
    buf.get((pointerOffsetWords + 1 + dataOffsetWords) * 8 + offset * 1)
  }

  def getShort(offset: Int): java.lang.Short = {
    if (pointerSize != 3) throw new IllegalArgumentException("This list is not Shorts.")
    buf.getShort((pointerOffsetWords + 1 + dataOffsetWords) * 8 + offset * 2)
  }

  def getInt(offset: Int): java.lang.Integer = {
    if (pointerSize != 4) throw new IllegalArgumentException("This list is not Ints.")
    buf.getInt((pointerOffsetWords + 1 + dataOffsetWords) * 8 + offset * 4)
  }

  def getLong(offset: Int): java.lang.Long = {
    if (pointerSize != 5) throw new IllegalArgumentException("This list is not Longs.")
    buf.getLong((pointerOffsetWords + 1 + dataOffsetWords) * 8 + offset * 8)
  }

  def getFloat(offset: Int): java.lang.Float = {
    if (pointerSize != 4) throw new IllegalArgumentException("This list is not Floats.")
    buf.getFloat((pointerOffsetWords + 1 + dataOffsetWords) * 8 + offset * 4)
  }

  def getDouble(offset: Int): java.lang.Double = {
    if (pointerSize != 5) throw new IllegalArgumentException("This list is not Doubles.")
    buf.getDouble((pointerOffsetWords + 1 + dataOffsetWords) * 8 + offset * 8)
  }

  def getPointer(offset: Int): Pointer[_] = {
    if (pointerSize != 6) throw new IllegalArgumentException("This list is not Pointers.")
    Pointer(arena, buf, pointerOffsetWords + 1 + dataOffsetWords + offset)
      .getOrElse(throw new IllegalArgumentException("This list is not Pointers."))
  }

  def getData(offset: Int): Array[Byte] = getPointer(offset) match {
    case l: CapnpList => {
      (0 to l.listElementCount - 1).map(l.getByte(_).toByte).toArray
    }
    case _ => Array()
  }
  def getString(offset: Int): String = new String(getData(offset).dropRight(1))

  def getComposite(offset: Int): Pointer[_] = {
    if (pointerSize != 7) throw new IllegalArgumentException("This list is not Composites.")
    val offsetWords = offset * (tag.dataSectionSizeWords + tag.pointerSectionSizeWords)
    new CapnpStruct(arena, buf, pointerOffsetWords + 1 + dataOffsetWords, offsetWords, tag.dataSectionSizeWords, tag.pointerSectionSizeWords)
  }

  def toSeq[A](f: Int => A): Seq[A] = {
    Range(0, listElementCount).map(f)
  }

  def toPointerSeq: Seq[Pointer[_]] = {
    pointerSize match {
      case 6 => Range(0, listElementCount).map(getPointer)
      case 7 => Range(0, listElementCount).map(getComposite)
      case _ => throw new IllegalArgumentException("This list is not Pointers: " + pointerSize)
    }
  }
  def toStructSeq: Seq[CapnpStruct] = toPointerSeq.map(_ match {
    case s: CapnpStruct => s
    case p => throw new IllegalArgumentException("Expected pointer of type CapnpStruct, but got: " + p)
  })

  def copyTo(newPointerOffsetWords: Int): CapnpList = {
    val newDataOffsetWords = (pointerOffsetWords + 1 + dataOffsetWords) - (newPointerOffsetWords + 1)
    val pointerIntOne = (newDataOffsetWords << 2) + 1
    buf.putInt((newPointerOffsetWords) * 8, pointerIntOne)
    val pointerIntTwo = if (pointerSize == 7) {
      (((tag.dataSectionSizeWords + tag.pointerSectionSizeWords) * listElementCount) << 3) + pointerSize
    } else {
      (listElementCount << 3) + pointerSize
    }
    buf.putInt((newPointerOffsetWords) * 8 + 4, pointerIntTwo)
    buf.putLong(pointerOffsetWords * 8, 0)

    new CapnpList(arena, buf, newPointerOffsetWords, newDataOffsetWords, pointerSize, listElementCount)
  }

  def copyToSegment(newSegment: CapnpSegmentBuilder, newPointerOffsetWords: Int): CapnpList = {
    val allocatedWords = newSegment.allocate(dataSizeWords)
      .getOrElse(throw new IllegalArgumentException("Not enough space in segment."))
    val newDataOffsetWords = allocatedWords - (newPointerOffsetWords + 1)
    copyDataToSegment(newSegment, newPointerOffsetWords + 1 + newDataOffsetWords)

    val pointerIntOne = (newDataOffsetWords << 2) + 1
    newSegment.buf.putInt((newSegment.offsetWords + newPointerOffsetWords) * 8, pointerIntOne)
    val pointerIntTwo = if (pointerSize == 7) {
      val tag = new CapnpTag(arena, buf, pointerOffsetWords + 1 + dataOffsetWords)
      (((tag.dataSectionSizeWords + tag.pointerSectionSizeWords) * listElementCount) << 3) + pointerSize
    } else {
      (listElementCount << 3) + pointerSize
    }
    newSegment.buf.putInt((newSegment.offsetWords + newPointerOffsetWords) * 8 + 4, pointerIntTwo)

    new CapnpList(arena, newSegment.buf, newPointerOffsetWords, newDataOffsetWords, pointerSize, listElementCount)
  }

  def copyDataToSegment(newSegment: CapnpSegmentBuilder, offsetWords: Int): Unit = {
    pointerSize match {
      case 0 | 1 | 2 | 3 | 4 | 5 => Range(0, dataSizeWords * 8).foreach(copyOffset => {
        val byte = buf.get((pointerOffsetWords + 1 + dataOffsetWords) * 8 + copyOffset)
        newSegment.buf.put((newSegment.offsetWords + offsetWords) * 8 + copyOffset, byte)
      })
      case 6 => Range(0, listElementCount).foreach(copyOffset => {
        getPointer(copyOffset).copyToSegment(newSegment, offsetWords + copyOffset)
      })
      case 7 => {
        val tagIntOne = (listElementCount << 2) + 0
        newSegment.buf.putInt((newSegment.offsetWords + offsetWords) * 8, tagIntOne)
        newSegment.buf.putShort((newSegment.offsetWords + offsetWords) * 8 + 4, tag.dataSectionSizeWords)
        newSegment.buf.putShort((newSegment.offsetWords + offsetWords) * 8 + 6, tag.pointerSectionSizeWords)

        val listElementSizeWords = tag.dataSectionSizeWords + tag.pointerSectionSizeWords
        Range(0, listElementCount).foreach(copyOffset => {
          getComposite(copyOffset).copyDataToSegment(newSegment, offsetWords + 1 + copyOffset * listElementSizeWords)
        })
      }
    }
  }
}

class CapnpStruct(
  override val arena: CapnpArena,
  override val buf: ByteBuffer,
  override val pointerOffsetWords: Int,
  val dataOffsetWords: Int,
  val dataSectionSizeWords: Short,
  val pointerSectionSizeWords: Short
) extends Pointer[CapnpStruct] {

  override def sizeWords: Int = {
    val recursivePointerSectionSizeWords = Range(0, pointerSectionSizeWords).map(p => {
      getPointer(p).map(_.sizeWords).getOrElse(1)
    }).sum
    1 + dataSectionSizeWords + recursivePointerSectionSizeWords
  }

  def getBoolean(offset: Int): Option[java.lang.Boolean] = {
    val byte = buf.get((pointerOffsetWords + 1 + dataOffsetWords) * 8 + (offset / 8))
    val mask = 1 << (offset % 8)
    Some(if ((byte & mask) > 0) java.lang.Boolean.TRUE else java.lang.Boolean.FALSE)
  }
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
  def getFloat(offset: Int): Option[java.lang.Float] = {
    val ret = buf.getFloat((pointerOffsetWords + 1 + dataOffsetWords) * 8 + offset * 4)
    val default: java.lang.Double = 0
    if (ret != default) Some(ret) else None
  }
  def getDouble(offset: Int): Option[java.lang.Double] = {
    val ret = buf.getDouble((pointerOffsetWords + 1 + dataOffsetWords) * 8 + offset * 8)
    val default: java.lang.Double = 0
    if (ret != default) Some(ret) else None
  }

  def getPointer(offset: Int): Option[Pointer[_]] = {
    Pointer(arena, buf, pointerOffsetWords + 1 + dataOffsetWords + dataSectionSizeWords + offset)
  }
  def getData(offset: Int): Option[Array[Byte]] = getPointer(offset).flatMap(_ match {
    case l: CapnpList => {
      Some((0 to l.listElementCount - 1).map(l.getByte(_).toByte).toArray)
    }
    case _ => None
  })
  def getString(offset: Int): Option[String] = getData(offset).map(bytes => new String(bytes.dropRight(1)))

  def getStruct(offset: Int): Option[CapnpStruct] = getPointer(offset).flatMap(_ match {
    case s: CapnpStruct => Some(s)
    case _ => None
  })

  def getPointerList(offset: Int): Seq[Pointer[_]] = getPointer(offset) match {
    case Some(l: CapnpList) => (0 to l.listElementCount-1).map(l.getPointer(_))
    case None => Nil
    case p => throw new IllegalArgumentException("This field is not a list: " + p)
  }

  def getStructList(offset: Int): Seq[CapnpStruct] = getPointer(offset) match {
    case Some(l: CapnpList) => {
      (0 to l.listElementCount-1).map(l.getComposite(_)).map(_ match {
        case s: CapnpStruct => s
      })
    }
    case None => Nil
    case p => throw new IllegalArgumentException("This field is not a list: " + p)
  }

  def getListList(offset: Int): Seq[CapnpList] = getPointer(offset) match {
    case Some(l: CapnpList) => {
      (0 to l.listElementCount-1).map(l.getComposite(_)).map(_ match {
        case l: CapnpList => l
      })
    }
    case None => Nil
    case p => throw new IllegalArgumentException("This field is not a list: " + p)
  }

  def getPrimitiveList[T](offset: Int, fn: CapnpList => Int => T): Seq[T] = getPointer(offset) match {
    case Some(l: CapnpList) => (0 to l.listElementCount-1).map(offset => fn(l)(offset))
    case None => Nil
    case p => throw new IllegalArgumentException("This field is not a list: " + p)
  }

  def getNone[T](o: Int = 0): Option[T] = None

  def copyTo(newPointerOffsetWords: Int): CapnpStruct = {
    val newDataOffsetWords = (pointerOffsetWords + 1 + dataOffsetWords) - (newPointerOffsetWords + 1)
    val structIntOne: Int = (newDataOffsetWords << 2) + 0
    buf.putInt((newPointerOffsetWords) * 8, structIntOne)
    buf.putShort((newPointerOffsetWords) * 8 + 4, dataSectionSizeWords)
    buf.putShort((newPointerOffsetWords) * 8 + 6, pointerSectionSizeWords)
    buf.putLong(pointerOffsetWords * 8, 0)

    new CapnpStruct(arena, buf, newPointerOffsetWords, newDataOffsetWords, dataSectionSizeWords, pointerSectionSizeWords)
  }

  def copyToSegment(newSegment: CapnpSegmentBuilder, newPointerOffsetWords: Int): CapnpStruct = {
    val allocatedWords = newSegment.allocate(dataSectionSizeWords + pointerSectionSizeWords)
      .getOrElse(throw new IllegalArgumentException("Not enough space in segment."))
    val newDataOffsetWords = allocatedWords - (newPointerOffsetWords + 1)
    copyDataToSegment(newSegment, newPointerOffsetWords + 1 + newDataOffsetWords)

    val structIntOne: Int = (newDataOffsetWords << 2) + 0
    newSegment.buf.putInt((newSegment.offsetWords + newPointerOffsetWords) * 8, structIntOne)
    newSegment.buf.putShort((newSegment.offsetWords + newPointerOffsetWords) * 8 + 4, dataSectionSizeWords)
    newSegment.buf.putShort((newSegment.offsetWords + newPointerOffsetWords) * 8 + 6, pointerSectionSizeWords)

    new CapnpStruct(arena, newSegment.buf, newPointerOffsetWords, newDataOffsetWords, dataSectionSizeWords, pointerSectionSizeWords)
  }

  def copyDataToSegment(newSegment: CapnpSegmentBuilder, offsetWords: Int): Unit = {
    Range(0, dataSectionSizeWords * 8).foreach(copyOffset => {
      val byte = buf.get((pointerOffsetWords + 1 + dataOffsetWords) * 8 + copyOffset)
      newSegment.buf.put((offsetWords) * 8 + copyOffset, byte)
    })
    Range(0, pointerSectionSizeWords).foreach(copyOffset => {
      getPointer(copyOffset).foreach(pointer => {
        pointer.copyToSegment(newSegment, offsetWords + dataSectionSizeWords + copyOffset)
      })
    })
  }
}

class CapnpTag(
  val arena: CapnpArena,
  val buf: ByteBuffer,
  val pointerOffsetWords: Int
) {
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

  val getRoot: (CapnpSegmentBuilder, Int) = allocate(1)
  def getRootBuilder[S <: Struct[S], B <: StructBuilder[S, B]](meta: MetaStructBuilder[S, B]): B = {
    val (segment, pointerOffset) = getRoot
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

  def getBytes: Array[Byte] = {
    val os = new ByteArrayOutputStream()
    writeToOutputStream(os)
    os.toByteArray
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
  def copyFrom(index: Int, meta: MetaStructBuilder[_, _], value: CapnpStructBuilder): Unit = {
    val elementSizeWords = meta.dataSectionSizeWords + meta.pointerSectionSizeWords

    val valueDataOffsetWords = value.segment.offsetWords + value.pointerOffsetWords + 1 + value.dataOffsetWords
    val indexDataOffsetWords = segment.offsetWords + pointerOffsetWords + 1 + dataOffsetWords + elementSizeWords * index
    Range(0, meta.dataSectionSizeWords * 8).foreach(copyOffset => {
      val byte = value.segment.buf.get(valueDataOffsetWords * 8 + copyOffset)
      segment.buf.put(indexDataOffsetWords * 8 + copyOffset, byte)
    })

    Range(0, meta.pointerSectionSizeWords).foreach(pointerIndex => {
      val indexPointerOffsetWords = indexDataOffsetWords + meta.dataSectionSizeWords + pointerIndex
      value.getPointer(pointerIndex).foreach(pointer => {
        pointer.copyTo(indexPointerOffsetWords)
      })
    })
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
  def setFloat(offset: Int, value: Float): Unit = {
    segment.buf.putFloat((segment.offsetWords + pointerOffsetWords + 1 + dataOffsetWords) * 8 + offset * 4, value)
  }
  def setDouble(offset: Int, value: Double): Unit = {
    segment.buf.putDouble((segment.offsetWords + pointerOffsetWords + 1 + dataOffsetWords) * 8 + offset * 8, value)
  }

  def setData(offset: Int, value: Array[Byte]): Unit = {
    CapnpListBuilder.simpleList(arena, segment, pointerOffsetWords + 1 + dataOffsetWords + dataSectionSizeWords + offset, 2, value.size)
      .putBytes(value)
  }
  def setString(offset: Int, value: String): Unit = {
    val bytes = value.getBytes("UTF-8") :+ 0.toByte
    setData(offset, bytes)
  }

  def initPointerList(offset: Int, listElementCount: Int, meta: MetaStructBuilder[_, _]): CapnpListBuilder = {
    CapnpListBuilder.taggedList(arena, segment, pointerOffsetWords + 1 + dataOffsetWords + dataSectionSizeWords + offset, listElementCount, meta.dataSectionSizeWords, meta.pointerSectionSizeWords)
  }

  def setStructList(offset: Int, meta: MetaStructBuilder[_, _], values: Seq[CapnpStructBuilder]): Unit = {
    // TODO(dan): Check that values are allocated in the same arena.
    val list = initPointerList(offset, values.size, meta)
    values.zipWithIndex.foreach({ case (value, index) => list.copyFrom(index, meta, value) })
  }

  def setNone(): Unit = {}


  def moveTo(newPointerOffsetWords: Int): CapnpStructBuilder = {
    val newDataOffsetWords = (pointerOffsetWords + 1 + dataOffsetWords) - (newPointerOffsetWords + 1)
    val structIntOne: Int = (newDataOffsetWords << 2) + 0
    segment.buf.putInt((segment.offsetWords + newPointerOffsetWords) * 8, structIntOne)
    segment.buf.putShort((segment.offsetWords + newPointerOffsetWords) * 8 + 4, dataSectionSizeWords)
    segment.buf.putShort((segment.offsetWords + newPointerOffsetWords) * 8 + 6, pointerSectionSizeWords)

    new CapnpStructBuilder(arena, segment, newPointerOffsetWords, newDataOffsetWords, dataSectionSizeWords, pointerSectionSizeWords)
  }
}
