// examples/src/main/scala/com/capnproto/test.capnp

package com.capnproto.test

import com.foursquare.spindle.{Enum, EnumMeta}
import com.capnproto.{HasUnion, UnionMeta, UnionValue, UntypedFieldDescriptor,
  FieldDescriptor, UntypedStruct, Struct, UntypedMetaStruct, MetaStruct,
  StructBuilder, MetaStructBuilder, MetaInterface, UntypedMetaInterface,
  Interface, UntypedInterface, MethodDescriptor, CapnpStruct, CapnpStructBuilder,
  Pointer, CapnpList, CapnpTag, CapnpArenaBuilder, CapnpArena}
import com.twitter.util.Future
import java.nio.ByteBuffer

object TestEnum extends EnumMeta[TestEnum] {
  case class Unknown(override val id: Int) extends TestEnum(TestEnum, id, null, null)

  val foo = new TestEnum(this, 0, "foo", "foo")
  val bar = new TestEnum(this, 1, "bar", "bar")
  val baz = new TestEnum(this, 2, "baz", "baz")
  val qux = new TestEnum(this, 3, "qux", "qux")
  val quux = new TestEnum(this, 4, "quux", "quux")
  val corge = new TestEnum(this, 5, "corge", "corge")
  val grault = new TestEnum(this, 6, "grault", "grault")
  val garply = new TestEnum(this, 7, "garply", "garply")

  override val values = Vector(
    foo,
    bar,
    baz,
    qux,
    quux,
    corge,
    grault,
    garply
  )

  override def findByIdOrNull(id: Int): TestEnum = values.lift(id).getOrElse(null)
  override def findByNameOrNull(name: String): TestEnum = null
  override def findByStringValueOrNull(v: String): TestEnum = null
}

sealed class TestEnum(
  override val meta: EnumMeta[TestEnum],
  override val id: Int,
  override val name: String,
  override val stringValue: String
) extends Enum[TestEnum]

object TestAllTypes extends MetaStruct[TestAllTypes] {
  override type Self = TestAllTypes.type
  override val structName: String = "TestAllTypes"
  override def create(struct: CapnpStruct): TestAllTypes = new TestAllTypesMutable(struct)
  def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestAllTypes.Builder = {
    val (segment, pointerOffset) = arena.allocate(1)
    val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestAllTypes.Builder.dataSectionSizeWords, com.capnproto.test.TestAllTypes.Builder.pointerSectionSizeWords)
    new com.capnproto.test.TestAllTypes.Builder(struct)
  }

  object Builder extends MetaStructBuilder[com.capnproto.test.TestAllTypes, com.capnproto.test.TestAllTypes.Builder] {
    override type Self = com.capnproto.test.TestAllTypes.Builder.type
    override val structName: String = "TestAllTypes"
    override val dataSectionSizeWords: Short = 6
    override val pointerSectionSizeWords: Short = 20
    override def create(struct: CapnpStructBuilder): com.capnproto.test.TestAllTypes.Builder = new com.capnproto.test.TestAllTypes.Builder(struct)
    override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestAllTypes.fields
  }
  class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestAllTypesMutable(struct) with StructBuilder[com.capnproto.test.TestAllTypes, com.capnproto.test.TestAllTypes.Builder] {
    override type MetaBuilderT = com.capnproto.test.TestAllTypes.Builder.type

    override def meta: TestAllTypes.type = TestAllTypes
    override def metaBuilder: MetaBuilderT = com.capnproto.test.TestAllTypes.Builder
    def setVoidField(value: Unit): Builder = { struct.setNone(); this }
    def setBoolField(value: java.lang.Boolean): Builder = { struct.setBoolean(0, value); this }
    def setInt8Field(value: java.lang.Byte): Builder = { struct.setByte(1, value); this }
    def setInt16Field(value: java.lang.Short): Builder = { struct.setShort(1, value); this }
    def setInt32Field(value: java.lang.Integer): Builder = { struct.setInt(1, value); this }
    def setInt64Field(value: java.lang.Long): Builder = { struct.setLong(1, value); this }
    def setUInt8Field(value: java.lang.Byte): Builder = { struct.setByte(16, value); this }
    def setUInt16Field(value: java.lang.Short): Builder = { struct.setShort(9, value); this }
    def setUInt32Field(value: java.lang.Integer): Builder = { struct.setInt(5, value); this }
    def setUInt64Field(value: java.lang.Long): Builder = { struct.setLong(3, value); this }
    def setFloat32Field(value: java.lang.Float): Builder = { struct.setFloat(8, value); this }
    def setFloat64Field(value: java.lang.Double): Builder = { struct.setDouble(5, value); this }
    def setTextField(value: String): Builder = { struct.setString(0, value); this }
    def setDataField(value: Array[Byte]): Builder = { struct.setData(1, value); this }
    def setStructField(value: com.capnproto.test.TestAllTypes): Builder = { struct.setNone(); this }
    def setEnumField(value: com.capnproto.test.TestEnum): Builder = { struct.setShort(18, value.id.toShort); this }
    def setInterfaceField(value: Unit): Builder = { struct.setNone(); this }
    def initStructList(count: Int): Seq[com.capnproto.test.TestAllTypes.Builder] = {
      val list = struct.initPointerList(17, count, com.capnproto.test.TestAllTypes.Builder)
      Range(0, count).map(i => new com.capnproto.test.TestAllTypes.Builder(list.initStruct(i, com.capnproto.test.TestAllTypes.Builder)))
    }
    def setStructList(buildFn: CapnpArenaBuilder => Seq[com.capnproto.test.TestAllTypes.Builder]): Builder = { struct.setStructList(17, com.capnproto.test.TestAllTypes.Builder, buildFn(struct.arena).map(_.struct)); this }
  }

  val voidField = new FieldDescriptor[Unit, TestAllTypes, TestAllTypes.type](
    name = "voidField",
    meta = TestAllTypes,
    getter = _.voidField,
    manifest = manifest[Unit],
    isUnion = false
  )

  val boolField = new FieldDescriptor[java.lang.Boolean, TestAllTypes, TestAllTypes.type](
    name = "boolField",
    meta = TestAllTypes,
    getter = _.boolField,
    manifest = manifest[java.lang.Boolean],
    isUnion = false
  )

  val int8Field = new FieldDescriptor[java.lang.Byte, TestAllTypes, TestAllTypes.type](
    name = "int8Field",
    meta = TestAllTypes,
    getter = _.int8Field,
    manifest = manifest[java.lang.Byte],
    isUnion = false
  )

  val int16Field = new FieldDescriptor[java.lang.Short, TestAllTypes, TestAllTypes.type](
    name = "int16Field",
    meta = TestAllTypes,
    getter = _.int16Field,
    manifest = manifest[java.lang.Short],
    isUnion = false
  )

  val int32Field = new FieldDescriptor[java.lang.Integer, TestAllTypes, TestAllTypes.type](
    name = "int32Field",
    meta = TestAllTypes,
    getter = _.int32Field,
    manifest = manifest[java.lang.Integer],
    isUnion = false
  )

  val int64Field = new FieldDescriptor[java.lang.Long, TestAllTypes, TestAllTypes.type](
    name = "int64Field",
    meta = TestAllTypes,
    getter = _.int64Field,
    manifest = manifest[java.lang.Long],
    isUnion = false
  )

  val uInt8Field = new FieldDescriptor[java.lang.Byte, TestAllTypes, TestAllTypes.type](
    name = "uInt8Field",
    meta = TestAllTypes,
    getter = _.uInt8Field,
    manifest = manifest[java.lang.Byte],
    isUnion = false
  )

  val uInt16Field = new FieldDescriptor[java.lang.Short, TestAllTypes, TestAllTypes.type](
    name = "uInt16Field",
    meta = TestAllTypes,
    getter = _.uInt16Field,
    manifest = manifest[java.lang.Short],
    isUnion = false
  )

  val uInt32Field = new FieldDescriptor[java.lang.Integer, TestAllTypes, TestAllTypes.type](
    name = "uInt32Field",
    meta = TestAllTypes,
    getter = _.uInt32Field,
    manifest = manifest[java.lang.Integer],
    isUnion = false
  )

  val uInt64Field = new FieldDescriptor[java.lang.Long, TestAllTypes, TestAllTypes.type](
    name = "uInt64Field",
    meta = TestAllTypes,
    getter = _.uInt64Field,
    manifest = manifest[java.lang.Long],
    isUnion = false
  )

  val float32Field = new FieldDescriptor[java.lang.Float, TestAllTypes, TestAllTypes.type](
    name = "float32Field",
    meta = TestAllTypes,
    getter = _.float32Field,
    manifest = manifest[java.lang.Float],
    isUnion = false
  )

  val float64Field = new FieldDescriptor[java.lang.Double, TestAllTypes, TestAllTypes.type](
    name = "float64Field",
    meta = TestAllTypes,
    getter = _.float64Field,
    manifest = manifest[java.lang.Double],
    isUnion = false
  )

  val textField = new FieldDescriptor[String, TestAllTypes, TestAllTypes.type](
    name = "textField",
    meta = TestAllTypes,
    getter = _.textField,
    manifest = manifest[String],
    isUnion = false
  )

  val dataField = new FieldDescriptor[Array[Byte], TestAllTypes, TestAllTypes.type](
    name = "dataField",
    meta = TestAllTypes,
    getter = _.dataField,
    manifest = manifest[Array[Byte]],
    isUnion = false
  )

  val structField = new FieldDescriptor[com.capnproto.test.TestAllTypes, TestAllTypes, TestAllTypes.type](
    name = "structField",
    meta = TestAllTypes,
    getter = _.structField,
    manifest = manifest[com.capnproto.test.TestAllTypes],
    isUnion = false
  )

  val enumField = new FieldDescriptor[com.capnproto.test.TestEnum, TestAllTypes, TestAllTypes.type](
    name = "enumField",
    meta = TestAllTypes,
    getter = _.enumField,
    manifest = manifest[com.capnproto.test.TestEnum],
    isUnion = false
  )

  val interfaceField = new FieldDescriptor[Unit, TestAllTypes, TestAllTypes.type](
    name = "interfaceField",
    meta = TestAllTypes,
    getter = _.interfaceField,
    manifest = manifest[Unit],
    isUnion = false
  )

  val voidList = new FieldDescriptor[Seq[Unit], TestAllTypes, TestAllTypes.type](
    name = "voidList",
    meta = TestAllTypes,
    getter = x => Some(x.voidList),
    manifest = manifest[Seq[Unit]],
    isUnion = false
  )

  val boolList = new FieldDescriptor[Seq[java.lang.Boolean], TestAllTypes, TestAllTypes.type](
    name = "boolList",
    meta = TestAllTypes,
    getter = x => Some(x.boolList),
    manifest = manifest[Seq[java.lang.Boolean]],
    isUnion = false
  )

  val int8List = new FieldDescriptor[Seq[java.lang.Byte], TestAllTypes, TestAllTypes.type](
    name = "int8List",
    meta = TestAllTypes,
    getter = x => Some(x.int8List),
    manifest = manifest[Seq[java.lang.Byte]],
    isUnion = false
  )

  val int16List = new FieldDescriptor[Seq[java.lang.Short], TestAllTypes, TestAllTypes.type](
    name = "int16List",
    meta = TestAllTypes,
    getter = x => Some(x.int16List),
    manifest = manifest[Seq[java.lang.Short]],
    isUnion = false
  )

  val int32List = new FieldDescriptor[Seq[java.lang.Integer], TestAllTypes, TestAllTypes.type](
    name = "int32List",
    meta = TestAllTypes,
    getter = x => Some(x.int32List),
    manifest = manifest[Seq[java.lang.Integer]],
    isUnion = false
  )

  val int64List = new FieldDescriptor[Seq[java.lang.Long], TestAllTypes, TestAllTypes.type](
    name = "int64List",
    meta = TestAllTypes,
    getter = x => Some(x.int64List),
    manifest = manifest[Seq[java.lang.Long]],
    isUnion = false
  )

  val uInt8List = new FieldDescriptor[Seq[java.lang.Byte], TestAllTypes, TestAllTypes.type](
    name = "uInt8List",
    meta = TestAllTypes,
    getter = x => Some(x.uInt8List),
    manifest = manifest[Seq[java.lang.Byte]],
    isUnion = false
  )

  val uInt16List = new FieldDescriptor[Seq[java.lang.Short], TestAllTypes, TestAllTypes.type](
    name = "uInt16List",
    meta = TestAllTypes,
    getter = x => Some(x.uInt16List),
    manifest = manifest[Seq[java.lang.Short]],
    isUnion = false
  )

  val uInt32List = new FieldDescriptor[Seq[java.lang.Integer], TestAllTypes, TestAllTypes.type](
    name = "uInt32List",
    meta = TestAllTypes,
    getter = x => Some(x.uInt32List),
    manifest = manifest[Seq[java.lang.Integer]],
    isUnion = false
  )

  val uInt64List = new FieldDescriptor[Seq[java.lang.Long], TestAllTypes, TestAllTypes.type](
    name = "uInt64List",
    meta = TestAllTypes,
    getter = x => Some(x.uInt64List),
    manifest = manifest[Seq[java.lang.Long]],
    isUnion = false
  )

  val float32List = new FieldDescriptor[Seq[java.lang.Float], TestAllTypes, TestAllTypes.type](
    name = "float32List",
    meta = TestAllTypes,
    getter = x => Some(x.float32List),
    manifest = manifest[Seq[java.lang.Float]],
    isUnion = false
  )

  val float64List = new FieldDescriptor[Seq[java.lang.Double], TestAllTypes, TestAllTypes.type](
    name = "float64List",
    meta = TestAllTypes,
    getter = x => Some(x.float64List),
    manifest = manifest[Seq[java.lang.Double]],
    isUnion = false
  )

  val textList = new FieldDescriptor[Seq[String], TestAllTypes, TestAllTypes.type](
    name = "textList",
    meta = TestAllTypes,
    getter = x => Some(x.textList),
    manifest = manifest[Seq[String]],
    isUnion = false
  )

  val dataList = new FieldDescriptor[Seq[Array[Byte]], TestAllTypes, TestAllTypes.type](
    name = "dataList",
    meta = TestAllTypes,
    getter = x => Some(x.dataList),
    manifest = manifest[Seq[Array[Byte]]],
    isUnion = false
  )

  val structList = new FieldDescriptor[Seq[com.capnproto.test.TestAllTypes], TestAllTypes, TestAllTypes.type](
    name = "structList",
    meta = TestAllTypes,
    getter = x => Some(x.structList),
    manifest = manifest[Seq[com.capnproto.test.TestAllTypes]],
    isUnion = false
  )

  val enumList = new FieldDescriptor[Seq[com.capnproto.test.TestEnum], TestAllTypes, TestAllTypes.type](
    name = "enumList",
    meta = TestAllTypes,
    getter = x => Some(x.enumList),
    manifest = manifest[Seq[com.capnproto.test.TestEnum]],
    isUnion = false
  )

  val interfaceList = new FieldDescriptor[Seq[Unit], TestAllTypes, TestAllTypes.type](
    name = "interfaceList",
    meta = TestAllTypes,
    getter = x => Some(x.interfaceList),
    manifest = manifest[Seq[Unit]],
    isUnion = false
  )
  override val fields: Seq[FieldDescriptor[_, TestAllTypes, TestAllTypes.type]] = Seq(voidField, boolField, int8Field, int16Field, int32Field, int64Field, uInt8Field, uInt16Field, uInt32Field, uInt64Field, float32Field, float64Field, textField, dataField, structField, enumField, interfaceField, voidList, boolList, int8List, int16List, int32List, int64List, uInt8List, uInt16List, uInt32List, uInt64List, float32List, float64List, textList, dataList, structList, enumList, interfaceList)
}

trait TestAllTypes extends Struct[TestAllTypes] {
  override type MetaT = TestAllTypes.type
  override type MetaBuilderT = com.capnproto.test.TestAllTypes.Builder.type

  override def meta: TestAllTypes.type = TestAllTypes
  override def metaBuilder: com.capnproto.test.TestAllTypes.Builder.type = com.capnproto.test.TestAllTypes.Builder

  def struct: CapnpStruct

  def voidField: Option[Unit]
  def boolField: Option[java.lang.Boolean]
  def int8Field: Option[java.lang.Byte]
  def int16Field: Option[java.lang.Short]
  def int32Field: Option[java.lang.Integer]
  def int64Field: Option[java.lang.Long]
  def uInt8Field: Option[java.lang.Byte]
  def uInt16Field: Option[java.lang.Short]
  def uInt32Field: Option[java.lang.Integer]
  def uInt64Field: Option[java.lang.Long]
  def float32Field: Option[java.lang.Float]
  def float64Field: Option[java.lang.Double]
  def textField: Option[String]
  def dataField: Option[Array[Byte]]
  def structField: Option[com.capnproto.test.TestAllTypes]
  def enumField: Option[com.capnproto.test.TestEnum]
  def interfaceField: Option[Unit]
  def voidList: Seq[Unit]
  def boolList: Seq[java.lang.Boolean]
  def int8List: Seq[java.lang.Byte]
  def int16List: Seq[java.lang.Short]
  def int32List: Seq[java.lang.Integer]
  def int64List: Seq[java.lang.Long]
  def uInt8List: Seq[java.lang.Byte]
  def uInt16List: Seq[java.lang.Short]
  def uInt32List: Seq[java.lang.Integer]
  def uInt64List: Seq[java.lang.Long]
  def float32List: Seq[java.lang.Float]
  def float64List: Seq[java.lang.Double]
  def textList: Seq[String]
  def dataList: Seq[Array[Byte]]
  def structList: Seq[com.capnproto.test.TestAllTypes]
  def enumList: Seq[com.capnproto.test.TestEnum]
  def interfaceList: Seq[Unit]
}

class TestAllTypesMutable(override val struct: CapnpStruct) extends TestAllTypes {
  override def voidField: Option[Unit] = struct.getNone()
  override def boolField: Option[java.lang.Boolean] = struct.getBoolean(0)
  override def int8Field: Option[java.lang.Byte] = struct.getByte(1)
  override def int16Field: Option[java.lang.Short] = struct.getShort(1)
  override def int32Field: Option[java.lang.Integer] = struct.getInt(1)
  override def int64Field: Option[java.lang.Long] = struct.getLong(1)
  override def uInt8Field: Option[java.lang.Byte] = struct.getByte(16)
  override def uInt16Field: Option[java.lang.Short] = struct.getShort(9)
  override def uInt32Field: Option[java.lang.Integer] = struct.getInt(5)
  override def uInt64Field: Option[java.lang.Long] = struct.getLong(3)
  override def float32Field: Option[java.lang.Float] = struct.getFloat(8)
  override def float64Field: Option[java.lang.Double] = struct.getDouble(5)
  override def textField: Option[String] = struct.getString(0)
  override def dataField: Option[Array[Byte]] = struct.getData(1)
  override def structField: Option[com.capnproto.test.TestAllTypes] = struct.getStruct(2).map(new com.capnproto.test.TestAllTypesMutable(_))
  override def enumField: Option[com.capnproto.test.TestEnum] = struct.getShort(18).map(id => com.capnproto.test.TestEnum.findById(id.toInt).getOrElse(com.capnproto.test.TestEnum.Unknown(id.toShort)))
  override def interfaceField: Option[Unit] = struct.getNone()
  override def voidList: Seq[Unit] = struct.getPrimitiveList(3, _.getVoid _)
  override def boolList: Seq[java.lang.Boolean] = struct.getPrimitiveList(4, _.getBoolean _)
  override def int8List: Seq[java.lang.Byte] = struct.getPrimitiveList(5, _.getByte _)
  override def int16List: Seq[java.lang.Short] = struct.getPrimitiveList(6, _.getShort _)
  override def int32List: Seq[java.lang.Integer] = struct.getPrimitiveList(7, _.getInt _)
  override def int64List: Seq[java.lang.Long] = struct.getPrimitiveList(8, _.getLong _)
  override def uInt8List: Seq[java.lang.Byte] = struct.getPrimitiveList(9, _.getByte _)
  override def uInt16List: Seq[java.lang.Short] = struct.getPrimitiveList(10, _.getShort _)
  override def uInt32List: Seq[java.lang.Integer] = struct.getPrimitiveList(11, _.getInt _)
  override def uInt64List: Seq[java.lang.Long] = struct.getPrimitiveList(12, _.getLong _)
  override def float32List: Seq[java.lang.Float] = struct.getPrimitiveList(13, _.getFloat _)
  override def float64List: Seq[java.lang.Double] = struct.getPrimitiveList(14, _.getDouble _)
  override def textList: Seq[String] = struct.getPrimitiveList(15, _.getString _)
  override def dataList: Seq[Array[Byte]] = struct.getPrimitiveList(16, _.getData _)
  override def structList: Seq[com.capnproto.test.TestAllTypes] = struct.getStructList(17).map(new com.capnproto.test.TestAllTypesMutable(_))
  override def enumList: Seq[com.capnproto.test.TestEnum] = struct.getPrimitiveList(18, _.getShort _).map(id => com.capnproto.test.TestEnum.findByIdOrNull(id.toInt))
  override def interfaceList: Seq[Unit] = struct.getPrimitiveList(19, _.getVoid _)
}

object TestDefaults extends MetaStruct[TestDefaults] {
  override type Self = TestDefaults.type
  override val structName: String = "TestDefaults"
  override def create(struct: CapnpStruct): TestDefaults = new TestDefaultsMutable(struct)
  def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestDefaults.Builder = {
    val (segment, pointerOffset) = arena.allocate(1)
    val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestDefaults.Builder.dataSectionSizeWords, com.capnproto.test.TestDefaults.Builder.pointerSectionSizeWords)
    new com.capnproto.test.TestDefaults.Builder(struct)
  }

  object Builder extends MetaStructBuilder[com.capnproto.test.TestDefaults, com.capnproto.test.TestDefaults.Builder] {
    override type Self = com.capnproto.test.TestDefaults.Builder.type
    override val structName: String = "TestDefaults"
    override val dataSectionSizeWords: Short = 6
    override val pointerSectionSizeWords: Short = 20
    override def create(struct: CapnpStructBuilder): com.capnproto.test.TestDefaults.Builder = new com.capnproto.test.TestDefaults.Builder(struct)
    override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestDefaults.fields
  }
  class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestDefaultsMutable(struct) with StructBuilder[com.capnproto.test.TestDefaults, com.capnproto.test.TestDefaults.Builder] {
    override type MetaBuilderT = com.capnproto.test.TestDefaults.Builder.type

    override def meta: TestDefaults.type = TestDefaults
    override def metaBuilder: MetaBuilderT = com.capnproto.test.TestDefaults.Builder
    def setVoidField(value: Unit): Builder = { struct.setNone(); this }
    def setBoolField(value: java.lang.Boolean): Builder = { struct.setBoolean(0, value); this }
    def setInt8Field(value: java.lang.Byte): Builder = { struct.setByte(1, value); this }
    def setInt16Field(value: java.lang.Short): Builder = { struct.setShort(1, value); this }
    def setInt32Field(value: java.lang.Integer): Builder = { struct.setInt(1, value); this }
    def setInt64Field(value: java.lang.Long): Builder = { struct.setLong(1, value); this }
    def setUInt8Field(value: java.lang.Byte): Builder = { struct.setByte(16, value); this }
    def setUInt16Field(value: java.lang.Short): Builder = { struct.setShort(9, value); this }
    def setUInt32Field(value: java.lang.Integer): Builder = { struct.setInt(5, value); this }
    def setUInt64Field(value: java.lang.Long): Builder = { struct.setLong(3, value); this }
    def setFloat32Field(value: java.lang.Float): Builder = { struct.setFloat(8, value); this }
    def setFloat64Field(value: java.lang.Double): Builder = { struct.setDouble(5, value); this }
    def setTextField(value: String): Builder = { struct.setString(0, value); this }
    def setDataField(value: Array[Byte]): Builder = { struct.setData(1, value); this }
    def setStructField(value: com.capnproto.test.TestAllTypes): Builder = { struct.setNone(); this }
    def setEnumField(value: com.capnproto.test.TestEnum): Builder = { struct.setShort(18, value.id.toShort); this }
    def setInterfaceField(value: Unit): Builder = { struct.setNone(); this }
    def initStructList(count: Int): Seq[com.capnproto.test.TestAllTypes.Builder] = {
      val list = struct.initPointerList(17, count, com.capnproto.test.TestAllTypes.Builder)
      Range(0, count).map(i => new com.capnproto.test.TestAllTypes.Builder(list.initStruct(i, com.capnproto.test.TestAllTypes.Builder)))
    }
    def setStructList(buildFn: CapnpArenaBuilder => Seq[com.capnproto.test.TestAllTypes.Builder]): Builder = { struct.setStructList(17, com.capnproto.test.TestAllTypes.Builder, buildFn(struct.arena).map(_.struct)); this }
  }

  val voidField = new FieldDescriptor[Unit, TestDefaults, TestDefaults.type](
    name = "voidField",
    meta = TestDefaults,
    getter = _.voidField,
    manifest = manifest[Unit],
    isUnion = false
  )

  val boolField = new FieldDescriptor[java.lang.Boolean, TestDefaults, TestDefaults.type](
    name = "boolField",
    meta = TestDefaults,
    getter = _.boolField,
    manifest = manifest[java.lang.Boolean],
    isUnion = false
  )

  val int8Field = new FieldDescriptor[java.lang.Byte, TestDefaults, TestDefaults.type](
    name = "int8Field",
    meta = TestDefaults,
    getter = _.int8Field,
    manifest = manifest[java.lang.Byte],
    isUnion = false
  )

  val int16Field = new FieldDescriptor[java.lang.Short, TestDefaults, TestDefaults.type](
    name = "int16Field",
    meta = TestDefaults,
    getter = _.int16Field,
    manifest = manifest[java.lang.Short],
    isUnion = false
  )

  val int32Field = new FieldDescriptor[java.lang.Integer, TestDefaults, TestDefaults.type](
    name = "int32Field",
    meta = TestDefaults,
    getter = _.int32Field,
    manifest = manifest[java.lang.Integer],
    isUnion = false
  )

  val int64Field = new FieldDescriptor[java.lang.Long, TestDefaults, TestDefaults.type](
    name = "int64Field",
    meta = TestDefaults,
    getter = _.int64Field,
    manifest = manifest[java.lang.Long],
    isUnion = false
  )

  val uInt8Field = new FieldDescriptor[java.lang.Byte, TestDefaults, TestDefaults.type](
    name = "uInt8Field",
    meta = TestDefaults,
    getter = _.uInt8Field,
    manifest = manifest[java.lang.Byte],
    isUnion = false
  )

  val uInt16Field = new FieldDescriptor[java.lang.Short, TestDefaults, TestDefaults.type](
    name = "uInt16Field",
    meta = TestDefaults,
    getter = _.uInt16Field,
    manifest = manifest[java.lang.Short],
    isUnion = false
  )

  val uInt32Field = new FieldDescriptor[java.lang.Integer, TestDefaults, TestDefaults.type](
    name = "uInt32Field",
    meta = TestDefaults,
    getter = _.uInt32Field,
    manifest = manifest[java.lang.Integer],
    isUnion = false
  )

  val uInt64Field = new FieldDescriptor[java.lang.Long, TestDefaults, TestDefaults.type](
    name = "uInt64Field",
    meta = TestDefaults,
    getter = _.uInt64Field,
    manifest = manifest[java.lang.Long],
    isUnion = false
  )

  val float32Field = new FieldDescriptor[java.lang.Float, TestDefaults, TestDefaults.type](
    name = "float32Field",
    meta = TestDefaults,
    getter = _.float32Field,
    manifest = manifest[java.lang.Float],
    isUnion = false
  )

  val float64Field = new FieldDescriptor[java.lang.Double, TestDefaults, TestDefaults.type](
    name = "float64Field",
    meta = TestDefaults,
    getter = _.float64Field,
    manifest = manifest[java.lang.Double],
    isUnion = false
  )

  val textField = new FieldDescriptor[String, TestDefaults, TestDefaults.type](
    name = "textField",
    meta = TestDefaults,
    getter = _.textField,
    manifest = manifest[String],
    isUnion = false
  )

  val dataField = new FieldDescriptor[Array[Byte], TestDefaults, TestDefaults.type](
    name = "dataField",
    meta = TestDefaults,
    getter = _.dataField,
    manifest = manifest[Array[Byte]],
    isUnion = false
  )

  val structField = new FieldDescriptor[com.capnproto.test.TestAllTypes, TestDefaults, TestDefaults.type](
    name = "structField",
    meta = TestDefaults,
    getter = _.structField,
    manifest = manifest[com.capnproto.test.TestAllTypes],
    isUnion = false
  )

  val enumField = new FieldDescriptor[com.capnproto.test.TestEnum, TestDefaults, TestDefaults.type](
    name = "enumField",
    meta = TestDefaults,
    getter = _.enumField,
    manifest = manifest[com.capnproto.test.TestEnum],
    isUnion = false
  )

  val interfaceField = new FieldDescriptor[Unit, TestDefaults, TestDefaults.type](
    name = "interfaceField",
    meta = TestDefaults,
    getter = _.interfaceField,
    manifest = manifest[Unit],
    isUnion = false
  )

  val voidList = new FieldDescriptor[Seq[Unit], TestDefaults, TestDefaults.type](
    name = "voidList",
    meta = TestDefaults,
    getter = x => Some(x.voidList),
    manifest = manifest[Seq[Unit]],
    isUnion = false
  )

  val boolList = new FieldDescriptor[Seq[java.lang.Boolean], TestDefaults, TestDefaults.type](
    name = "boolList",
    meta = TestDefaults,
    getter = x => Some(x.boolList),
    manifest = manifest[Seq[java.lang.Boolean]],
    isUnion = false
  )

  val int8List = new FieldDescriptor[Seq[java.lang.Byte], TestDefaults, TestDefaults.type](
    name = "int8List",
    meta = TestDefaults,
    getter = x => Some(x.int8List),
    manifest = manifest[Seq[java.lang.Byte]],
    isUnion = false
  )

  val int16List = new FieldDescriptor[Seq[java.lang.Short], TestDefaults, TestDefaults.type](
    name = "int16List",
    meta = TestDefaults,
    getter = x => Some(x.int16List),
    manifest = manifest[Seq[java.lang.Short]],
    isUnion = false
  )

  val int32List = new FieldDescriptor[Seq[java.lang.Integer], TestDefaults, TestDefaults.type](
    name = "int32List",
    meta = TestDefaults,
    getter = x => Some(x.int32List),
    manifest = manifest[Seq[java.lang.Integer]],
    isUnion = false
  )

  val int64List = new FieldDescriptor[Seq[java.lang.Long], TestDefaults, TestDefaults.type](
    name = "int64List",
    meta = TestDefaults,
    getter = x => Some(x.int64List),
    manifest = manifest[Seq[java.lang.Long]],
    isUnion = false
  )

  val uInt8List = new FieldDescriptor[Seq[java.lang.Byte], TestDefaults, TestDefaults.type](
    name = "uInt8List",
    meta = TestDefaults,
    getter = x => Some(x.uInt8List),
    manifest = manifest[Seq[java.lang.Byte]],
    isUnion = false
  )

  val uInt16List = new FieldDescriptor[Seq[java.lang.Short], TestDefaults, TestDefaults.type](
    name = "uInt16List",
    meta = TestDefaults,
    getter = x => Some(x.uInt16List),
    manifest = manifest[Seq[java.lang.Short]],
    isUnion = false
  )

  val uInt32List = new FieldDescriptor[Seq[java.lang.Integer], TestDefaults, TestDefaults.type](
    name = "uInt32List",
    meta = TestDefaults,
    getter = x => Some(x.uInt32List),
    manifest = manifest[Seq[java.lang.Integer]],
    isUnion = false
  )

  val uInt64List = new FieldDescriptor[Seq[java.lang.Long], TestDefaults, TestDefaults.type](
    name = "uInt64List",
    meta = TestDefaults,
    getter = x => Some(x.uInt64List),
    manifest = manifest[Seq[java.lang.Long]],
    isUnion = false
  )

  val float32List = new FieldDescriptor[Seq[java.lang.Float], TestDefaults, TestDefaults.type](
    name = "float32List",
    meta = TestDefaults,
    getter = x => Some(x.float32List),
    manifest = manifest[Seq[java.lang.Float]],
    isUnion = false
  )

  val float64List = new FieldDescriptor[Seq[java.lang.Double], TestDefaults, TestDefaults.type](
    name = "float64List",
    meta = TestDefaults,
    getter = x => Some(x.float64List),
    manifest = manifest[Seq[java.lang.Double]],
    isUnion = false
  )

  val textList = new FieldDescriptor[Seq[String], TestDefaults, TestDefaults.type](
    name = "textList",
    meta = TestDefaults,
    getter = x => Some(x.textList),
    manifest = manifest[Seq[String]],
    isUnion = false
  )

  val dataList = new FieldDescriptor[Seq[Array[Byte]], TestDefaults, TestDefaults.type](
    name = "dataList",
    meta = TestDefaults,
    getter = x => Some(x.dataList),
    manifest = manifest[Seq[Array[Byte]]],
    isUnion = false
  )

  val structList = new FieldDescriptor[Seq[com.capnproto.test.TestAllTypes], TestDefaults, TestDefaults.type](
    name = "structList",
    meta = TestDefaults,
    getter = x => Some(x.structList),
    manifest = manifest[Seq[com.capnproto.test.TestAllTypes]],
    isUnion = false
  )

  val enumList = new FieldDescriptor[Seq[com.capnproto.test.TestEnum], TestDefaults, TestDefaults.type](
    name = "enumList",
    meta = TestDefaults,
    getter = x => Some(x.enumList),
    manifest = manifest[Seq[com.capnproto.test.TestEnum]],
    isUnion = false
  )

  val interfaceList = new FieldDescriptor[Seq[Unit], TestDefaults, TestDefaults.type](
    name = "interfaceList",
    meta = TestDefaults,
    getter = x => Some(x.interfaceList),
    manifest = manifest[Seq[Unit]],
    isUnion = false
  )
  override val fields: Seq[FieldDescriptor[_, TestDefaults, TestDefaults.type]] = Seq(voidField, boolField, int8Field, int16Field, int32Field, int64Field, uInt8Field, uInt16Field, uInt32Field, uInt64Field, float32Field, float64Field, textField, dataField, structField, enumField, interfaceField, voidList, boolList, int8List, int16List, int32List, int64List, uInt8List, uInt16List, uInt32List, uInt64List, float32List, float64List, textList, dataList, structList, enumList, interfaceList)
}

trait TestDefaults extends Struct[TestDefaults] {
  override type MetaT = TestDefaults.type
  override type MetaBuilderT = com.capnproto.test.TestDefaults.Builder.type

  override def meta: TestDefaults.type = TestDefaults
  override def metaBuilder: com.capnproto.test.TestDefaults.Builder.type = com.capnproto.test.TestDefaults.Builder

  def struct: CapnpStruct

  def voidField: Option[Unit]
  def boolField: Option[java.lang.Boolean]
  def int8Field: Option[java.lang.Byte]
  def int16Field: Option[java.lang.Short]
  def int32Field: Option[java.lang.Integer]
  def int64Field: Option[java.lang.Long]
  def uInt8Field: Option[java.lang.Byte]
  def uInt16Field: Option[java.lang.Short]
  def uInt32Field: Option[java.lang.Integer]
  def uInt64Field: Option[java.lang.Long]
  def float32Field: Option[java.lang.Float]
  def float64Field: Option[java.lang.Double]
  def textField: Option[String]
  def dataField: Option[Array[Byte]]
  def structField: Option[com.capnproto.test.TestAllTypes]
  def enumField: Option[com.capnproto.test.TestEnum]
  def interfaceField: Option[Unit]
  def voidList: Seq[Unit]
  def boolList: Seq[java.lang.Boolean]
  def int8List: Seq[java.lang.Byte]
  def int16List: Seq[java.lang.Short]
  def int32List: Seq[java.lang.Integer]
  def int64List: Seq[java.lang.Long]
  def uInt8List: Seq[java.lang.Byte]
  def uInt16List: Seq[java.lang.Short]
  def uInt32List: Seq[java.lang.Integer]
  def uInt64List: Seq[java.lang.Long]
  def float32List: Seq[java.lang.Float]
  def float64List: Seq[java.lang.Double]
  def textList: Seq[String]
  def dataList: Seq[Array[Byte]]
  def structList: Seq[com.capnproto.test.TestAllTypes]
  def enumList: Seq[com.capnproto.test.TestEnum]
  def interfaceList: Seq[Unit]
}

class TestDefaultsMutable(override val struct: CapnpStruct) extends TestDefaults {
  override def voidField: Option[Unit] = struct.getNone()
  override def boolField: Option[java.lang.Boolean] = struct.getBoolean(0)
  override def int8Field: Option[java.lang.Byte] = struct.getByte(1)
  override def int16Field: Option[java.lang.Short] = struct.getShort(1)
  override def int32Field: Option[java.lang.Integer] = struct.getInt(1)
  override def int64Field: Option[java.lang.Long] = struct.getLong(1)
  override def uInt8Field: Option[java.lang.Byte] = struct.getByte(16)
  override def uInt16Field: Option[java.lang.Short] = struct.getShort(9)
  override def uInt32Field: Option[java.lang.Integer] = struct.getInt(5)
  override def uInt64Field: Option[java.lang.Long] = struct.getLong(3)
  override def float32Field: Option[java.lang.Float] = struct.getFloat(8)
  override def float64Field: Option[java.lang.Double] = struct.getDouble(5)
  override def textField: Option[String] = struct.getString(0)
  override def dataField: Option[Array[Byte]] = struct.getData(1)
  override def structField: Option[com.capnproto.test.TestAllTypes] = struct.getStruct(2).map(new com.capnproto.test.TestAllTypesMutable(_))
  override def enumField: Option[com.capnproto.test.TestEnum] = struct.getShort(18).map(id => com.capnproto.test.TestEnum.findById(id.toInt).getOrElse(com.capnproto.test.TestEnum.Unknown(id.toShort)))
  override def interfaceField: Option[Unit] = struct.getNone()
  override def voidList: Seq[Unit] = struct.getPrimitiveList(3, _.getVoid _)
  override def boolList: Seq[java.lang.Boolean] = struct.getPrimitiveList(4, _.getBoolean _)
  override def int8List: Seq[java.lang.Byte] = struct.getPrimitiveList(5, _.getByte _)
  override def int16List: Seq[java.lang.Short] = struct.getPrimitiveList(6, _.getShort _)
  override def int32List: Seq[java.lang.Integer] = struct.getPrimitiveList(7, _.getInt _)
  override def int64List: Seq[java.lang.Long] = struct.getPrimitiveList(8, _.getLong _)
  override def uInt8List: Seq[java.lang.Byte] = struct.getPrimitiveList(9, _.getByte _)
  override def uInt16List: Seq[java.lang.Short] = struct.getPrimitiveList(10, _.getShort _)
  override def uInt32List: Seq[java.lang.Integer] = struct.getPrimitiveList(11, _.getInt _)
  override def uInt64List: Seq[java.lang.Long] = struct.getPrimitiveList(12, _.getLong _)
  override def float32List: Seq[java.lang.Float] = struct.getPrimitiveList(13, _.getFloat _)
  override def float64List: Seq[java.lang.Double] = struct.getPrimitiveList(14, _.getDouble _)
  override def textList: Seq[String] = struct.getPrimitiveList(15, _.getString _)
  override def dataList: Seq[Array[Byte]] = struct.getPrimitiveList(16, _.getData _)
  override def structList: Seq[com.capnproto.test.TestAllTypes] = struct.getStructList(17).map(new com.capnproto.test.TestAllTypesMutable(_))
  override def enumList: Seq[com.capnproto.test.TestEnum] = struct.getPrimitiveList(18, _.getShort _).map(id => com.capnproto.test.TestEnum.findByIdOrNull(id.toInt))
  override def interfaceList: Seq[Unit] = struct.getPrimitiveList(19, _.getVoid _)
}

object TestObject extends MetaStruct[TestObject] {
  override type Self = TestObject.type
  override val structName: String = "TestObject"
  override def create(struct: CapnpStruct): TestObject = new TestObjectMutable(struct)
  def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestObject.Builder = {
    val (segment, pointerOffset) = arena.allocate(1)
    val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestObject.Builder.dataSectionSizeWords, com.capnproto.test.TestObject.Builder.pointerSectionSizeWords)
    new com.capnproto.test.TestObject.Builder(struct)
  }

  object Builder extends MetaStructBuilder[com.capnproto.test.TestObject, com.capnproto.test.TestObject.Builder] {
    override type Self = com.capnproto.test.TestObject.Builder.type
    override val structName: String = "TestObject"
    override val dataSectionSizeWords: Short = 0
    override val pointerSectionSizeWords: Short = 1
    override def create(struct: CapnpStructBuilder): com.capnproto.test.TestObject.Builder = new com.capnproto.test.TestObject.Builder(struct)
    override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestObject.fields
  }
  class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestObjectMutable(struct) with StructBuilder[com.capnproto.test.TestObject, com.capnproto.test.TestObject.Builder] {
    override type MetaBuilderT = com.capnproto.test.TestObject.Builder.type

    override def meta: TestObject.type = TestObject
    override def metaBuilder: MetaBuilderT = com.capnproto.test.TestObject.Builder
    def setObjectField(value: Pointer[_]): Builder = { struct.setNone(); this }
  }

  val objectField = new FieldDescriptor[Pointer[_], TestObject, TestObject.type](
    name = "objectField",
    meta = TestObject,
    getter = _.objectField,
    manifest = manifest[Pointer[_]],
    isUnion = false
  )
  override val fields: Seq[FieldDescriptor[_, TestObject, TestObject.type]] = Seq(objectField)
}

trait TestObject extends Struct[TestObject] {
  override type MetaT = TestObject.type
  override type MetaBuilderT = com.capnproto.test.TestObject.Builder.type

  override def meta: TestObject.type = TestObject
  override def metaBuilder: com.capnproto.test.TestObject.Builder.type = com.capnproto.test.TestObject.Builder

  def struct: CapnpStruct

  def objectField: Option[Pointer[_]]
}

class TestObjectMutable(override val struct: CapnpStruct) extends TestObject {
  override def objectField: Option[Pointer[_]] = struct.getPointer(0)
}

object TestOutOfOrder extends MetaStruct[TestOutOfOrder] {
  override type Self = TestOutOfOrder.type
  override val structName: String = "TestOutOfOrder"
  override def create(struct: CapnpStruct): TestOutOfOrder = new TestOutOfOrderMutable(struct)
  def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestOutOfOrder.Builder = {
    val (segment, pointerOffset) = arena.allocate(1)
    val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestOutOfOrder.Builder.dataSectionSizeWords, com.capnproto.test.TestOutOfOrder.Builder.pointerSectionSizeWords)
    new com.capnproto.test.TestOutOfOrder.Builder(struct)
  }

  object Builder extends MetaStructBuilder[com.capnproto.test.TestOutOfOrder, com.capnproto.test.TestOutOfOrder.Builder] {
    override type Self = com.capnproto.test.TestOutOfOrder.Builder.type
    override val structName: String = "TestOutOfOrder"
    override val dataSectionSizeWords: Short = 0
    override val pointerSectionSizeWords: Short = 9
    override def create(struct: CapnpStructBuilder): com.capnproto.test.TestOutOfOrder.Builder = new com.capnproto.test.TestOutOfOrder.Builder(struct)
    override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestOutOfOrder.fields
  }
  class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestOutOfOrderMutable(struct) with StructBuilder[com.capnproto.test.TestOutOfOrder, com.capnproto.test.TestOutOfOrder.Builder] {
    override type MetaBuilderT = com.capnproto.test.TestOutOfOrder.Builder.type

    override def meta: TestOutOfOrder.type = TestOutOfOrder
    override def metaBuilder: MetaBuilderT = com.capnproto.test.TestOutOfOrder.Builder
    def setFoo(value: String): Builder = { struct.setString(3, value); this }
    def setBar(value: String): Builder = { struct.setString(2, value); this }
    def setBaz(value: String): Builder = { struct.setString(8, value); this }
    def setQux(value: String): Builder = { struct.setString(0, value); this }
    def setQuux(value: String): Builder = { struct.setString(6, value); this }
    def setCorge(value: String): Builder = { struct.setString(4, value); this }
    def setGrault(value: String): Builder = { struct.setString(1, value); this }
    def setGarply(value: String): Builder = { struct.setString(7, value); this }
    def setWaldo(value: String): Builder = { struct.setString(5, value); this }
  }

  val foo = new FieldDescriptor[String, TestOutOfOrder, TestOutOfOrder.type](
    name = "foo",
    meta = TestOutOfOrder,
    getter = _.foo,
    manifest = manifest[String],
    isUnion = false
  )

  val bar = new FieldDescriptor[String, TestOutOfOrder, TestOutOfOrder.type](
    name = "bar",
    meta = TestOutOfOrder,
    getter = _.bar,
    manifest = manifest[String],
    isUnion = false
  )

  val baz = new FieldDescriptor[String, TestOutOfOrder, TestOutOfOrder.type](
    name = "baz",
    meta = TestOutOfOrder,
    getter = _.baz,
    manifest = manifest[String],
    isUnion = false
  )

  val qux = new FieldDescriptor[String, TestOutOfOrder, TestOutOfOrder.type](
    name = "qux",
    meta = TestOutOfOrder,
    getter = _.qux,
    manifest = manifest[String],
    isUnion = false
  )

  val quux = new FieldDescriptor[String, TestOutOfOrder, TestOutOfOrder.type](
    name = "quux",
    meta = TestOutOfOrder,
    getter = _.quux,
    manifest = manifest[String],
    isUnion = false
  )

  val corge = new FieldDescriptor[String, TestOutOfOrder, TestOutOfOrder.type](
    name = "corge",
    meta = TestOutOfOrder,
    getter = _.corge,
    manifest = manifest[String],
    isUnion = false
  )

  val grault = new FieldDescriptor[String, TestOutOfOrder, TestOutOfOrder.type](
    name = "grault",
    meta = TestOutOfOrder,
    getter = _.grault,
    manifest = manifest[String],
    isUnion = false
  )

  val garply = new FieldDescriptor[String, TestOutOfOrder, TestOutOfOrder.type](
    name = "garply",
    meta = TestOutOfOrder,
    getter = _.garply,
    manifest = manifest[String],
    isUnion = false
  )

  val waldo = new FieldDescriptor[String, TestOutOfOrder, TestOutOfOrder.type](
    name = "waldo",
    meta = TestOutOfOrder,
    getter = _.waldo,
    manifest = manifest[String],
    isUnion = false
  )
  override val fields: Seq[FieldDescriptor[_, TestOutOfOrder, TestOutOfOrder.type]] = Seq(foo, bar, baz, qux, quux, corge, grault, garply, waldo)
}

trait TestOutOfOrder extends Struct[TestOutOfOrder] {
  override type MetaT = TestOutOfOrder.type
  override type MetaBuilderT = com.capnproto.test.TestOutOfOrder.Builder.type

  override def meta: TestOutOfOrder.type = TestOutOfOrder
  override def metaBuilder: com.capnproto.test.TestOutOfOrder.Builder.type = com.capnproto.test.TestOutOfOrder.Builder

  def struct: CapnpStruct

  def foo: Option[String]
  def bar: Option[String]
  def baz: Option[String]
  def qux: Option[String]
  def quux: Option[String]
  def corge: Option[String]
  def grault: Option[String]
  def garply: Option[String]
  def waldo: Option[String]
}

class TestOutOfOrderMutable(override val struct: CapnpStruct) extends TestOutOfOrder {
  override def foo: Option[String] = struct.getString(3)
  override def bar: Option[String] = struct.getString(2)
  override def baz: Option[String] = struct.getString(8)
  override def qux: Option[String] = struct.getString(0)
  override def quux: Option[String] = struct.getString(6)
  override def corge: Option[String] = struct.getString(4)
  override def grault: Option[String] = struct.getString(1)
  override def garply: Option[String] = struct.getString(7)
  override def waldo: Option[String] = struct.getString(5)
}

object TestUnion extends MetaStruct[TestUnion] {
  override type Self = TestUnion.type
  override val structName: String = "TestUnion"
  override def create(struct: CapnpStruct): TestUnion = new TestUnionMutable(struct)
  def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestUnion.Builder = {
    val (segment, pointerOffset) = arena.allocate(1)
    val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestUnion.Builder.dataSectionSizeWords, com.capnproto.test.TestUnion.Builder.pointerSectionSizeWords)
    new com.capnproto.test.TestUnion.Builder(struct)
  }

  object Builder extends MetaStructBuilder[com.capnproto.test.TestUnion, com.capnproto.test.TestUnion.Builder] {
    override type Self = com.capnproto.test.TestUnion.Builder.type
    override val structName: String = "TestUnion"
    override val dataSectionSizeWords: Short = 8
    override val pointerSectionSizeWords: Short = 2
    override def create(struct: CapnpStructBuilder): com.capnproto.test.TestUnion.Builder = new com.capnproto.test.TestUnion.Builder(struct)
    override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestUnion.fields
  }
  class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestUnionMutable(struct) with StructBuilder[com.capnproto.test.TestUnion, com.capnproto.test.TestUnion.Builder] {
    override type MetaBuilderT = com.capnproto.test.TestUnion.Builder.type

    override def meta: TestUnion.type = TestUnion
    override def metaBuilder: MetaBuilderT = com.capnproto.test.TestUnion.Builder
    override def union0: com.capnproto.test.TestUnion.Union0.Builder = new com.capnproto.test.TestUnion.Union0.Builder(struct)
    def setBit0(value: java.lang.Boolean): Builder = { struct.setBoolean(128, value); this }
    override def union1: com.capnproto.test.TestUnion.Union1.Builder = new com.capnproto.test.TestUnion.Union1.Builder(struct)
    def setBit2(value: java.lang.Boolean): Builder = { struct.setBoolean(130, value); this }
    def setBit3(value: java.lang.Boolean): Builder = { struct.setBoolean(131, value); this }
    def setBit4(value: java.lang.Boolean): Builder = { struct.setBoolean(132, value); this }
    def setBit5(value: java.lang.Boolean): Builder = { struct.setBoolean(133, value); this }
    def setBit6(value: java.lang.Boolean): Builder = { struct.setBoolean(134, value); this }
    def setBit7(value: java.lang.Boolean): Builder = { struct.setBoolean(135, value); this }
    override def union2: com.capnproto.test.TestUnion.Union2.Builder = new com.capnproto.test.TestUnion.Union2.Builder(struct)
    override def union3: com.capnproto.test.TestUnion.Union3.Builder = new com.capnproto.test.TestUnion.Union3.Builder(struct)
    def setByte0(value: java.lang.Byte): Builder = { struct.setByte(35, value); this }
  }

  object Union0 extends MetaStruct[Union0] {
    override type Self = Union0.type
    override val structName: String = "Union0"
    override def create(struct: CapnpStruct): Union0 = new Union0Mutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestUnion.Union0.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestUnion.Union0.Builder.dataSectionSizeWords, com.capnproto.test.TestUnion.Union0.Builder.pointerSectionSizeWords)
      new com.capnproto.test.TestUnion.Union0.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.test.TestUnion.Union0, com.capnproto.test.TestUnion.Union0.Builder] {
      override type Self = com.capnproto.test.TestUnion.Union0.Builder.type
      override val structName: String = "Union0"
      override val dataSectionSizeWords: Short = 8
      override val pointerSectionSizeWords: Short = 2
      override def create(struct: CapnpStructBuilder): com.capnproto.test.TestUnion.Union0.Builder = new com.capnproto.test.TestUnion.Union0.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestUnion.Union0.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestUnion.Union0Mutable(struct) with StructBuilder[com.capnproto.test.TestUnion.Union0, com.capnproto.test.TestUnion.Union0.Builder] {
      override type MetaBuilderT = com.capnproto.test.TestUnion.Union0.Builder.type

      override def meta: Union0.type = Union0
      override def metaBuilder: MetaBuilderT = com.capnproto.test.TestUnion.Union0.Builder
      def setU0f0s0(value: Unit): Builder = { struct.setNone(); struct.setShort(0, -1); this }
      def setU0f0s1(value: java.lang.Boolean): Builder = { struct.setBoolean(64, value); struct.setShort(0, -2); this }
      def setU0f0s8(value: java.lang.Byte): Builder = { struct.setByte(8, value); struct.setShort(0, -3); this }
      def setU0f0s16(value: java.lang.Short): Builder = { struct.setShort(4, value); struct.setShort(0, -4); this }
      def setU0f0s32(value: java.lang.Integer): Builder = { struct.setInt(2, value); struct.setShort(0, -5); this }
      def setU0f0s64(value: java.lang.Long): Builder = { struct.setLong(1, value); struct.setShort(0, -6); this }
      def setU0f0sp(value: String): Builder = { struct.setString(0, value); struct.setShort(0, -7); this }
      def setU0f1s0(value: Unit): Builder = { struct.setNone(); struct.setShort(0, -8); this }
      def setU0f1s1(value: java.lang.Boolean): Builder = { struct.setBoolean(64, value); struct.setShort(0, -9); this }
      def setU0f1s8(value: java.lang.Byte): Builder = { struct.setByte(8, value); struct.setShort(0, -10); this }
      def setU0f1s16(value: java.lang.Short): Builder = { struct.setShort(4, value); struct.setShort(0, -11); this }
      def setU0f1s32(value: java.lang.Integer): Builder = { struct.setInt(2, value); struct.setShort(0, -12); this }
      def setU0f1s64(value: java.lang.Long): Builder = { struct.setLong(1, value); struct.setShort(0, -13); this }
      def setU0f1sp(value: String): Builder = { struct.setString(0, value); struct.setShort(0, -14); this }
    }

    sealed trait Union extends UnionValue[com.capnproto.test.TestUnion.Union0.Union]
    object Union extends UnionMeta[com.capnproto.test.TestUnion.Union0.Union] {
      case class Unknown(discriminant: Short) extends com.capnproto.test.TestUnion.Union0.Union
      case class u0f0s0(value: Option[Unit]) extends com.capnproto.test.TestUnion.Union0.Union
      case class u0f0s1(value: Option[java.lang.Boolean]) extends com.capnproto.test.TestUnion.Union0.Union
      case class u0f0s8(value: Option[java.lang.Byte]) extends com.capnproto.test.TestUnion.Union0.Union
      case class u0f0s16(value: Option[java.lang.Short]) extends com.capnproto.test.TestUnion.Union0.Union
      case class u0f0s32(value: Option[java.lang.Integer]) extends com.capnproto.test.TestUnion.Union0.Union
      case class u0f0s64(value: Option[java.lang.Long]) extends com.capnproto.test.TestUnion.Union0.Union
      case class u0f0sp(value: Option[String]) extends com.capnproto.test.TestUnion.Union0.Union
      case class u0f1s0(value: Option[Unit]) extends com.capnproto.test.TestUnion.Union0.Union
      case class u0f1s1(value: Option[java.lang.Boolean]) extends com.capnproto.test.TestUnion.Union0.Union
      case class u0f1s8(value: Option[java.lang.Byte]) extends com.capnproto.test.TestUnion.Union0.Union
      case class u0f1s16(value: Option[java.lang.Short]) extends com.capnproto.test.TestUnion.Union0.Union
      case class u0f1s32(value: Option[java.lang.Integer]) extends com.capnproto.test.TestUnion.Union0.Union
      case class u0f1s64(value: Option[java.lang.Long]) extends com.capnproto.test.TestUnion.Union0.Union
      case class u0f1sp(value: Option[String]) extends com.capnproto.test.TestUnion.Union0.Union
    }

    val u0f0s0 = new FieldDescriptor[Unit, Union0, Union0.type](
      name = "u0f0s0",
      meta = Union0,
      getter = _.u0f0s0,
      manifest = manifest[Unit],
      isUnion = true
    )

    val u0f0s1 = new FieldDescriptor[java.lang.Boolean, Union0, Union0.type](
      name = "u0f0s1",
      meta = Union0,
      getter = _.u0f0s1,
      manifest = manifest[java.lang.Boolean],
      isUnion = true
    )

    val u0f0s8 = new FieldDescriptor[java.lang.Byte, Union0, Union0.type](
      name = "u0f0s8",
      meta = Union0,
      getter = _.u0f0s8,
      manifest = manifest[java.lang.Byte],
      isUnion = true
    )

    val u0f0s16 = new FieldDescriptor[java.lang.Short, Union0, Union0.type](
      name = "u0f0s16",
      meta = Union0,
      getter = _.u0f0s16,
      manifest = manifest[java.lang.Short],
      isUnion = true
    )

    val u0f0s32 = new FieldDescriptor[java.lang.Integer, Union0, Union0.type](
      name = "u0f0s32",
      meta = Union0,
      getter = _.u0f0s32,
      manifest = manifest[java.lang.Integer],
      isUnion = true
    )

    val u0f0s64 = new FieldDescriptor[java.lang.Long, Union0, Union0.type](
      name = "u0f0s64",
      meta = Union0,
      getter = _.u0f0s64,
      manifest = manifest[java.lang.Long],
      isUnion = true
    )

    val u0f0sp = new FieldDescriptor[String, Union0, Union0.type](
      name = "u0f0sp",
      meta = Union0,
      getter = _.u0f0sp,
      manifest = manifest[String],
      isUnion = true
    )

    val u0f1s0 = new FieldDescriptor[Unit, Union0, Union0.type](
      name = "u0f1s0",
      meta = Union0,
      getter = _.u0f1s0,
      manifest = manifest[Unit],
      isUnion = true
    )

    val u0f1s1 = new FieldDescriptor[java.lang.Boolean, Union0, Union0.type](
      name = "u0f1s1",
      meta = Union0,
      getter = _.u0f1s1,
      manifest = manifest[java.lang.Boolean],
      isUnion = true
    )

    val u0f1s8 = new FieldDescriptor[java.lang.Byte, Union0, Union0.type](
      name = "u0f1s8",
      meta = Union0,
      getter = _.u0f1s8,
      manifest = manifest[java.lang.Byte],
      isUnion = true
    )

    val u0f1s16 = new FieldDescriptor[java.lang.Short, Union0, Union0.type](
      name = "u0f1s16",
      meta = Union0,
      getter = _.u0f1s16,
      manifest = manifest[java.lang.Short],
      isUnion = true
    )

    val u0f1s32 = new FieldDescriptor[java.lang.Integer, Union0, Union0.type](
      name = "u0f1s32",
      meta = Union0,
      getter = _.u0f1s32,
      manifest = manifest[java.lang.Integer],
      isUnion = true
    )

    val u0f1s64 = new FieldDescriptor[java.lang.Long, Union0, Union0.type](
      name = "u0f1s64",
      meta = Union0,
      getter = _.u0f1s64,
      manifest = manifest[java.lang.Long],
      isUnion = true
    )

    val u0f1sp = new FieldDescriptor[String, Union0, Union0.type](
      name = "u0f1sp",
      meta = Union0,
      getter = _.u0f1sp,
      manifest = manifest[String],
      isUnion = true
    )
    override val fields: Seq[FieldDescriptor[_, Union0, Union0.type]] = Seq(u0f0s0, u0f0s1, u0f0s8, u0f0s16, u0f0s32, u0f0s64, u0f0sp, u0f1s0, u0f1s1, u0f1s8, u0f1s16, u0f1s32, u0f1s64, u0f1sp)
  }

  trait Union0 extends Struct[Union0] with HasUnion[com.capnproto.test.TestUnion.Union0.Union] {
    override type MetaT = Union0.type
    override type MetaBuilderT = com.capnproto.test.TestUnion.Union0.Builder.type

    override def meta: Union0.type = Union0
    override def metaBuilder: com.capnproto.test.TestUnion.Union0.Builder.type = com.capnproto.test.TestUnion.Union0.Builder

    def struct: CapnpStruct

    def u0f0s0: Option[Unit]
    def u0f0s1: Option[java.lang.Boolean]
    def u0f0s8: Option[java.lang.Byte]
    def u0f0s16: Option[java.lang.Short]
    def u0f0s32: Option[java.lang.Integer]
    def u0f0s64: Option[java.lang.Long]
    def u0f0sp: Option[String]
    def u0f1s0: Option[Unit]
    def u0f1s1: Option[java.lang.Boolean]
    def u0f1s8: Option[java.lang.Byte]
    def u0f1s16: Option[java.lang.Short]
    def u0f1s32: Option[java.lang.Integer]
    def u0f1s64: Option[java.lang.Long]
    def u0f1sp: Option[String]
  }

  class Union0Mutable(override val struct: CapnpStruct) extends Union0 {
    override def discriminant: Short = (struct.getShort(0).getOrElse(new java.lang.Short(0.toShort)): java.lang.Short)
    override def switch: com.capnproto.test.TestUnion.Union0.Union = discriminant match {
      case 0 => com.capnproto.test.TestUnion.Union0.Union.u0f0s0(u0f0s0)
      case 1 => com.capnproto.test.TestUnion.Union0.Union.u0f0s1(u0f0s1)
      case 2 => com.capnproto.test.TestUnion.Union0.Union.u0f0s8(u0f0s8)
      case 3 => com.capnproto.test.TestUnion.Union0.Union.u0f0s16(u0f0s16)
      case 4 => com.capnproto.test.TestUnion.Union0.Union.u0f0s32(u0f0s32)
      case 5 => com.capnproto.test.TestUnion.Union0.Union.u0f0s64(u0f0s64)
      case 6 => com.capnproto.test.TestUnion.Union0.Union.u0f0sp(u0f0sp)
      case 7 => com.capnproto.test.TestUnion.Union0.Union.u0f1s0(u0f1s0)
      case 8 => com.capnproto.test.TestUnion.Union0.Union.u0f1s1(u0f1s1)
      case 9 => com.capnproto.test.TestUnion.Union0.Union.u0f1s8(u0f1s8)
      case 10 => com.capnproto.test.TestUnion.Union0.Union.u0f1s16(u0f1s16)
      case 11 => com.capnproto.test.TestUnion.Union0.Union.u0f1s32(u0f1s32)
      case 12 => com.capnproto.test.TestUnion.Union0.Union.u0f1s64(u0f1s64)
      case 13 => com.capnproto.test.TestUnion.Union0.Union.u0f1sp(u0f1sp)
      case d => com.capnproto.test.TestUnion.Union0.Union.Unknown(d)
    }
    override def union: UnionMeta[com.capnproto.test.TestUnion.Union0.Union] = com.capnproto.test.TestUnion.Union0.Union

    override def u0f0s0: Option[Unit] = struct.getNone()
    override def u0f0s1: Option[java.lang.Boolean] = struct.getBoolean(64)
    override def u0f0s8: Option[java.lang.Byte] = struct.getByte(8)
    override def u0f0s16: Option[java.lang.Short] = struct.getShort(4)
    override def u0f0s32: Option[java.lang.Integer] = struct.getInt(2)
    override def u0f0s64: Option[java.lang.Long] = struct.getLong(1)
    override def u0f0sp: Option[String] = struct.getString(0)
    override def u0f1s0: Option[Unit] = struct.getNone()
    override def u0f1s1: Option[java.lang.Boolean] = struct.getBoolean(64)
    override def u0f1s8: Option[java.lang.Byte] = struct.getByte(8)
    override def u0f1s16: Option[java.lang.Short] = struct.getShort(4)
    override def u0f1s32: Option[java.lang.Integer] = struct.getInt(2)
    override def u0f1s64: Option[java.lang.Long] = struct.getLong(1)
    override def u0f1sp: Option[String] = struct.getString(0)
  }
  object Union1 extends MetaStruct[Union1] {
    override type Self = Union1.type
    override val structName: String = "Union1"
    override def create(struct: CapnpStruct): Union1 = new Union1Mutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestUnion.Union1.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestUnion.Union1.Builder.dataSectionSizeWords, com.capnproto.test.TestUnion.Union1.Builder.pointerSectionSizeWords)
      new com.capnproto.test.TestUnion.Union1.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.test.TestUnion.Union1, com.capnproto.test.TestUnion.Union1.Builder] {
      override type Self = com.capnproto.test.TestUnion.Union1.Builder.type
      override val structName: String = "Union1"
      override val dataSectionSizeWords: Short = 8
      override val pointerSectionSizeWords: Short = 2
      override def create(struct: CapnpStructBuilder): com.capnproto.test.TestUnion.Union1.Builder = new com.capnproto.test.TestUnion.Union1.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestUnion.Union1.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestUnion.Union1Mutable(struct) with StructBuilder[com.capnproto.test.TestUnion.Union1, com.capnproto.test.TestUnion.Union1.Builder] {
      override type MetaBuilderT = com.capnproto.test.TestUnion.Union1.Builder.type

      override def meta: Union1.type = Union1
      override def metaBuilder: MetaBuilderT = com.capnproto.test.TestUnion.Union1.Builder
      def setU1f0s0(value: Unit): Builder = { struct.setNone(); struct.setShort(1, -1); this }
      def setU1f0s1(value: java.lang.Boolean): Builder = { struct.setBoolean(129, value); struct.setShort(1, -2); this }
      def setU1f1s1(value: java.lang.Boolean): Builder = { struct.setBoolean(129, value); struct.setShort(1, -3); this }
      def setU1f0s8(value: java.lang.Byte): Builder = { struct.setByte(17, value); struct.setShort(1, -4); this }
      def setU1f1s8(value: java.lang.Byte): Builder = { struct.setByte(17, value); struct.setShort(1, -5); this }
      def setU1f0s16(value: java.lang.Short): Builder = { struct.setShort(9, value); struct.setShort(1, -6); this }
      def setU1f1s16(value: java.lang.Short): Builder = { struct.setShort(9, value); struct.setShort(1, -7); this }
      def setU1f0s32(value: java.lang.Integer): Builder = { struct.setInt(5, value); struct.setShort(1, -8); this }
      def setU1f1s32(value: java.lang.Integer): Builder = { struct.setInt(5, value); struct.setShort(1, -9); this }
      def setU1f0s64(value: java.lang.Long): Builder = { struct.setLong(3, value); struct.setShort(1, -10); this }
      def setU1f1s64(value: java.lang.Long): Builder = { struct.setLong(3, value); struct.setShort(1, -11); this }
      def setU1f0sp(value: String): Builder = { struct.setString(1, value); struct.setShort(1, -12); this }
      def setU1f1sp(value: String): Builder = { struct.setString(1, value); struct.setShort(1, -13); this }
      def setU1f2s0(value: Unit): Builder = { struct.setNone(); struct.setShort(1, -14); this }
      def setU1f2s1(value: java.lang.Boolean): Builder = { struct.setBoolean(129, value); struct.setShort(1, -15); this }
      def setU1f2s8(value: java.lang.Byte): Builder = { struct.setByte(17, value); struct.setShort(1, -16); this }
      def setU1f2s16(value: java.lang.Short): Builder = { struct.setShort(9, value); struct.setShort(1, -17); this }
      def setU1f2s32(value: java.lang.Integer): Builder = { struct.setInt(5, value); struct.setShort(1, -18); this }
      def setU1f2s64(value: java.lang.Long): Builder = { struct.setLong(3, value); struct.setShort(1, -19); this }
      def setU1f2sp(value: String): Builder = { struct.setString(1, value); struct.setShort(1, -20); this }
    }

    sealed trait Union extends UnionValue[com.capnproto.test.TestUnion.Union1.Union]
    object Union extends UnionMeta[com.capnproto.test.TestUnion.Union1.Union] {
      case class Unknown(discriminant: Short) extends com.capnproto.test.TestUnion.Union1.Union
      case class u1f0s0(value: Option[Unit]) extends com.capnproto.test.TestUnion.Union1.Union
      case class u1f0s1(value: Option[java.lang.Boolean]) extends com.capnproto.test.TestUnion.Union1.Union
      case class u1f1s1(value: Option[java.lang.Boolean]) extends com.capnproto.test.TestUnion.Union1.Union
      case class u1f0s8(value: Option[java.lang.Byte]) extends com.capnproto.test.TestUnion.Union1.Union
      case class u1f1s8(value: Option[java.lang.Byte]) extends com.capnproto.test.TestUnion.Union1.Union
      case class u1f0s16(value: Option[java.lang.Short]) extends com.capnproto.test.TestUnion.Union1.Union
      case class u1f1s16(value: Option[java.lang.Short]) extends com.capnproto.test.TestUnion.Union1.Union
      case class u1f0s32(value: Option[java.lang.Integer]) extends com.capnproto.test.TestUnion.Union1.Union
      case class u1f1s32(value: Option[java.lang.Integer]) extends com.capnproto.test.TestUnion.Union1.Union
      case class u1f0s64(value: Option[java.lang.Long]) extends com.capnproto.test.TestUnion.Union1.Union
      case class u1f1s64(value: Option[java.lang.Long]) extends com.capnproto.test.TestUnion.Union1.Union
      case class u1f0sp(value: Option[String]) extends com.capnproto.test.TestUnion.Union1.Union
      case class u1f1sp(value: Option[String]) extends com.capnproto.test.TestUnion.Union1.Union
      case class u1f2s0(value: Option[Unit]) extends com.capnproto.test.TestUnion.Union1.Union
      case class u1f2s1(value: Option[java.lang.Boolean]) extends com.capnproto.test.TestUnion.Union1.Union
      case class u1f2s8(value: Option[java.lang.Byte]) extends com.capnproto.test.TestUnion.Union1.Union
      case class u1f2s16(value: Option[java.lang.Short]) extends com.capnproto.test.TestUnion.Union1.Union
      case class u1f2s32(value: Option[java.lang.Integer]) extends com.capnproto.test.TestUnion.Union1.Union
      case class u1f2s64(value: Option[java.lang.Long]) extends com.capnproto.test.TestUnion.Union1.Union
      case class u1f2sp(value: Option[String]) extends com.capnproto.test.TestUnion.Union1.Union
    }

    val u1f0s0 = new FieldDescriptor[Unit, Union1, Union1.type](
      name = "u1f0s0",
      meta = Union1,
      getter = _.u1f0s0,
      manifest = manifest[Unit],
      isUnion = true
    )

    val u1f0s1 = new FieldDescriptor[java.lang.Boolean, Union1, Union1.type](
      name = "u1f0s1",
      meta = Union1,
      getter = _.u1f0s1,
      manifest = manifest[java.lang.Boolean],
      isUnion = true
    )

    val u1f1s1 = new FieldDescriptor[java.lang.Boolean, Union1, Union1.type](
      name = "u1f1s1",
      meta = Union1,
      getter = _.u1f1s1,
      manifest = manifest[java.lang.Boolean],
      isUnion = true
    )

    val u1f0s8 = new FieldDescriptor[java.lang.Byte, Union1, Union1.type](
      name = "u1f0s8",
      meta = Union1,
      getter = _.u1f0s8,
      manifest = manifest[java.lang.Byte],
      isUnion = true
    )

    val u1f1s8 = new FieldDescriptor[java.lang.Byte, Union1, Union1.type](
      name = "u1f1s8",
      meta = Union1,
      getter = _.u1f1s8,
      manifest = manifest[java.lang.Byte],
      isUnion = true
    )

    val u1f0s16 = new FieldDescriptor[java.lang.Short, Union1, Union1.type](
      name = "u1f0s16",
      meta = Union1,
      getter = _.u1f0s16,
      manifest = manifest[java.lang.Short],
      isUnion = true
    )

    val u1f1s16 = new FieldDescriptor[java.lang.Short, Union1, Union1.type](
      name = "u1f1s16",
      meta = Union1,
      getter = _.u1f1s16,
      manifest = manifest[java.lang.Short],
      isUnion = true
    )

    val u1f0s32 = new FieldDescriptor[java.lang.Integer, Union1, Union1.type](
      name = "u1f0s32",
      meta = Union1,
      getter = _.u1f0s32,
      manifest = manifest[java.lang.Integer],
      isUnion = true
    )

    val u1f1s32 = new FieldDescriptor[java.lang.Integer, Union1, Union1.type](
      name = "u1f1s32",
      meta = Union1,
      getter = _.u1f1s32,
      manifest = manifest[java.lang.Integer],
      isUnion = true
    )

    val u1f0s64 = new FieldDescriptor[java.lang.Long, Union1, Union1.type](
      name = "u1f0s64",
      meta = Union1,
      getter = _.u1f0s64,
      manifest = manifest[java.lang.Long],
      isUnion = true
    )

    val u1f1s64 = new FieldDescriptor[java.lang.Long, Union1, Union1.type](
      name = "u1f1s64",
      meta = Union1,
      getter = _.u1f1s64,
      manifest = manifest[java.lang.Long],
      isUnion = true
    )

    val u1f0sp = new FieldDescriptor[String, Union1, Union1.type](
      name = "u1f0sp",
      meta = Union1,
      getter = _.u1f0sp,
      manifest = manifest[String],
      isUnion = true
    )

    val u1f1sp = new FieldDescriptor[String, Union1, Union1.type](
      name = "u1f1sp",
      meta = Union1,
      getter = _.u1f1sp,
      manifest = manifest[String],
      isUnion = true
    )

    val u1f2s0 = new FieldDescriptor[Unit, Union1, Union1.type](
      name = "u1f2s0",
      meta = Union1,
      getter = _.u1f2s0,
      manifest = manifest[Unit],
      isUnion = true
    )

    val u1f2s1 = new FieldDescriptor[java.lang.Boolean, Union1, Union1.type](
      name = "u1f2s1",
      meta = Union1,
      getter = _.u1f2s1,
      manifest = manifest[java.lang.Boolean],
      isUnion = true
    )

    val u1f2s8 = new FieldDescriptor[java.lang.Byte, Union1, Union1.type](
      name = "u1f2s8",
      meta = Union1,
      getter = _.u1f2s8,
      manifest = manifest[java.lang.Byte],
      isUnion = true
    )

    val u1f2s16 = new FieldDescriptor[java.lang.Short, Union1, Union1.type](
      name = "u1f2s16",
      meta = Union1,
      getter = _.u1f2s16,
      manifest = manifest[java.lang.Short],
      isUnion = true
    )

    val u1f2s32 = new FieldDescriptor[java.lang.Integer, Union1, Union1.type](
      name = "u1f2s32",
      meta = Union1,
      getter = _.u1f2s32,
      manifest = manifest[java.lang.Integer],
      isUnion = true
    )

    val u1f2s64 = new FieldDescriptor[java.lang.Long, Union1, Union1.type](
      name = "u1f2s64",
      meta = Union1,
      getter = _.u1f2s64,
      manifest = manifest[java.lang.Long],
      isUnion = true
    )

    val u1f2sp = new FieldDescriptor[String, Union1, Union1.type](
      name = "u1f2sp",
      meta = Union1,
      getter = _.u1f2sp,
      manifest = manifest[String],
      isUnion = true
    )
    override val fields: Seq[FieldDescriptor[_, Union1, Union1.type]] = Seq(u1f0s0, u1f0s1, u1f1s1, u1f0s8, u1f1s8, u1f0s16, u1f1s16, u1f0s32, u1f1s32, u1f0s64, u1f1s64, u1f0sp, u1f1sp, u1f2s0, u1f2s1, u1f2s8, u1f2s16, u1f2s32, u1f2s64, u1f2sp)
  }

  trait Union1 extends Struct[Union1] with HasUnion[com.capnproto.test.TestUnion.Union1.Union] {
    override type MetaT = Union1.type
    override type MetaBuilderT = com.capnproto.test.TestUnion.Union1.Builder.type

    override def meta: Union1.type = Union1
    override def metaBuilder: com.capnproto.test.TestUnion.Union1.Builder.type = com.capnproto.test.TestUnion.Union1.Builder

    def struct: CapnpStruct

    def u1f0s0: Option[Unit]
    def u1f0s1: Option[java.lang.Boolean]
    def u1f1s1: Option[java.lang.Boolean]
    def u1f0s8: Option[java.lang.Byte]
    def u1f1s8: Option[java.lang.Byte]
    def u1f0s16: Option[java.lang.Short]
    def u1f1s16: Option[java.lang.Short]
    def u1f0s32: Option[java.lang.Integer]
    def u1f1s32: Option[java.lang.Integer]
    def u1f0s64: Option[java.lang.Long]
    def u1f1s64: Option[java.lang.Long]
    def u1f0sp: Option[String]
    def u1f1sp: Option[String]
    def u1f2s0: Option[Unit]
    def u1f2s1: Option[java.lang.Boolean]
    def u1f2s8: Option[java.lang.Byte]
    def u1f2s16: Option[java.lang.Short]
    def u1f2s32: Option[java.lang.Integer]
    def u1f2s64: Option[java.lang.Long]
    def u1f2sp: Option[String]
  }

  class Union1Mutable(override val struct: CapnpStruct) extends Union1 {
    override def discriminant: Short = (struct.getShort(1).getOrElse(new java.lang.Short(0.toShort)): java.lang.Short)
    override def switch: com.capnproto.test.TestUnion.Union1.Union = discriminant match {
      case 0 => com.capnproto.test.TestUnion.Union1.Union.u1f0s0(u1f0s0)
      case 1 => com.capnproto.test.TestUnion.Union1.Union.u1f0s1(u1f0s1)
      case 2 => com.capnproto.test.TestUnion.Union1.Union.u1f1s1(u1f1s1)
      case 3 => com.capnproto.test.TestUnion.Union1.Union.u1f0s8(u1f0s8)
      case 4 => com.capnproto.test.TestUnion.Union1.Union.u1f1s8(u1f1s8)
      case 5 => com.capnproto.test.TestUnion.Union1.Union.u1f0s16(u1f0s16)
      case 6 => com.capnproto.test.TestUnion.Union1.Union.u1f1s16(u1f1s16)
      case 7 => com.capnproto.test.TestUnion.Union1.Union.u1f0s32(u1f0s32)
      case 8 => com.capnproto.test.TestUnion.Union1.Union.u1f1s32(u1f1s32)
      case 9 => com.capnproto.test.TestUnion.Union1.Union.u1f0s64(u1f0s64)
      case 10 => com.capnproto.test.TestUnion.Union1.Union.u1f1s64(u1f1s64)
      case 11 => com.capnproto.test.TestUnion.Union1.Union.u1f0sp(u1f0sp)
      case 12 => com.capnproto.test.TestUnion.Union1.Union.u1f1sp(u1f1sp)
      case 13 => com.capnproto.test.TestUnion.Union1.Union.u1f2s0(u1f2s0)
      case 14 => com.capnproto.test.TestUnion.Union1.Union.u1f2s1(u1f2s1)
      case 15 => com.capnproto.test.TestUnion.Union1.Union.u1f2s8(u1f2s8)
      case 16 => com.capnproto.test.TestUnion.Union1.Union.u1f2s16(u1f2s16)
      case 17 => com.capnproto.test.TestUnion.Union1.Union.u1f2s32(u1f2s32)
      case 18 => com.capnproto.test.TestUnion.Union1.Union.u1f2s64(u1f2s64)
      case 19 => com.capnproto.test.TestUnion.Union1.Union.u1f2sp(u1f2sp)
      case d => com.capnproto.test.TestUnion.Union1.Union.Unknown(d)
    }
    override def union: UnionMeta[com.capnproto.test.TestUnion.Union1.Union] = com.capnproto.test.TestUnion.Union1.Union

    override def u1f0s0: Option[Unit] = struct.getNone()
    override def u1f0s1: Option[java.lang.Boolean] = struct.getBoolean(129)
    override def u1f1s1: Option[java.lang.Boolean] = struct.getBoolean(129)
    override def u1f0s8: Option[java.lang.Byte] = struct.getByte(17)
    override def u1f1s8: Option[java.lang.Byte] = struct.getByte(17)
    override def u1f0s16: Option[java.lang.Short] = struct.getShort(9)
    override def u1f1s16: Option[java.lang.Short] = struct.getShort(9)
    override def u1f0s32: Option[java.lang.Integer] = struct.getInt(5)
    override def u1f1s32: Option[java.lang.Integer] = struct.getInt(5)
    override def u1f0s64: Option[java.lang.Long] = struct.getLong(3)
    override def u1f1s64: Option[java.lang.Long] = struct.getLong(3)
    override def u1f0sp: Option[String] = struct.getString(1)
    override def u1f1sp: Option[String] = struct.getString(1)
    override def u1f2s0: Option[Unit] = struct.getNone()
    override def u1f2s1: Option[java.lang.Boolean] = struct.getBoolean(129)
    override def u1f2s8: Option[java.lang.Byte] = struct.getByte(17)
    override def u1f2s16: Option[java.lang.Short] = struct.getShort(9)
    override def u1f2s32: Option[java.lang.Integer] = struct.getInt(5)
    override def u1f2s64: Option[java.lang.Long] = struct.getLong(3)
    override def u1f2sp: Option[String] = struct.getString(1)
  }
  object Union2 extends MetaStruct[Union2] {
    override type Self = Union2.type
    override val structName: String = "Union2"
    override def create(struct: CapnpStruct): Union2 = new Union2Mutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestUnion.Union2.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestUnion.Union2.Builder.dataSectionSizeWords, com.capnproto.test.TestUnion.Union2.Builder.pointerSectionSizeWords)
      new com.capnproto.test.TestUnion.Union2.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.test.TestUnion.Union2, com.capnproto.test.TestUnion.Union2.Builder] {
      override type Self = com.capnproto.test.TestUnion.Union2.Builder.type
      override val structName: String = "Union2"
      override val dataSectionSizeWords: Short = 8
      override val pointerSectionSizeWords: Short = 2
      override def create(struct: CapnpStructBuilder): com.capnproto.test.TestUnion.Union2.Builder = new com.capnproto.test.TestUnion.Union2.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestUnion.Union2.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestUnion.Union2Mutable(struct) with StructBuilder[com.capnproto.test.TestUnion.Union2, com.capnproto.test.TestUnion.Union2.Builder] {
      override type MetaBuilderT = com.capnproto.test.TestUnion.Union2.Builder.type

      override def meta: Union2.type = Union2
      override def metaBuilder: MetaBuilderT = com.capnproto.test.TestUnion.Union2.Builder
      def setU2f0s64(value: java.lang.Long): Builder = { struct.setLong(6, value); struct.setShort(2, -5); this }
      def setU2f0s32(value: java.lang.Integer): Builder = { struct.setInt(10, value); struct.setShort(2, -4); this }
      def setU2f0s16(value: java.lang.Short): Builder = { struct.setShort(18, value); struct.setShort(2, -3); this }
      def setU2f0s8(value: java.lang.Byte): Builder = { struct.setByte(33, value); struct.setShort(2, -2); this }
      def setU2f0s1(value: java.lang.Boolean): Builder = { struct.setBoolean(256, value); struct.setShort(2, -1); this }
    }

    sealed trait Union extends UnionValue[com.capnproto.test.TestUnion.Union2.Union]
    object Union extends UnionMeta[com.capnproto.test.TestUnion.Union2.Union] {
      case class Unknown(discriminant: Short) extends com.capnproto.test.TestUnion.Union2.Union
      case class u2f0s64(value: Option[java.lang.Long]) extends com.capnproto.test.TestUnion.Union2.Union
      case class u2f0s32(value: Option[java.lang.Integer]) extends com.capnproto.test.TestUnion.Union2.Union
      case class u2f0s16(value: Option[java.lang.Short]) extends com.capnproto.test.TestUnion.Union2.Union
      case class u2f0s8(value: Option[java.lang.Byte]) extends com.capnproto.test.TestUnion.Union2.Union
      case class u2f0s1(value: Option[java.lang.Boolean]) extends com.capnproto.test.TestUnion.Union2.Union
    }

    val u2f0s64 = new FieldDescriptor[java.lang.Long, Union2, Union2.type](
      name = "u2f0s64",
      meta = Union2,
      getter = _.u2f0s64,
      manifest = manifest[java.lang.Long],
      isUnion = true
    )

    val u2f0s32 = new FieldDescriptor[java.lang.Integer, Union2, Union2.type](
      name = "u2f0s32",
      meta = Union2,
      getter = _.u2f0s32,
      manifest = manifest[java.lang.Integer],
      isUnion = true
    )

    val u2f0s16 = new FieldDescriptor[java.lang.Short, Union2, Union2.type](
      name = "u2f0s16",
      meta = Union2,
      getter = _.u2f0s16,
      manifest = manifest[java.lang.Short],
      isUnion = true
    )

    val u2f0s8 = new FieldDescriptor[java.lang.Byte, Union2, Union2.type](
      name = "u2f0s8",
      meta = Union2,
      getter = _.u2f0s8,
      manifest = manifest[java.lang.Byte],
      isUnion = true
    )

    val u2f0s1 = new FieldDescriptor[java.lang.Boolean, Union2, Union2.type](
      name = "u2f0s1",
      meta = Union2,
      getter = _.u2f0s1,
      manifest = manifest[java.lang.Boolean],
      isUnion = true
    )
    override val fields: Seq[FieldDescriptor[_, Union2, Union2.type]] = Seq(u2f0s64, u2f0s32, u2f0s16, u2f0s8, u2f0s1)
  }

  trait Union2 extends Struct[Union2] with HasUnion[com.capnproto.test.TestUnion.Union2.Union] {
    override type MetaT = Union2.type
    override type MetaBuilderT = com.capnproto.test.TestUnion.Union2.Builder.type

    override def meta: Union2.type = Union2
    override def metaBuilder: com.capnproto.test.TestUnion.Union2.Builder.type = com.capnproto.test.TestUnion.Union2.Builder

    def struct: CapnpStruct

    def u2f0s64: Option[java.lang.Long]
    def u2f0s32: Option[java.lang.Integer]
    def u2f0s16: Option[java.lang.Short]
    def u2f0s8: Option[java.lang.Byte]
    def u2f0s1: Option[java.lang.Boolean]
  }

  class Union2Mutable(override val struct: CapnpStruct) extends Union2 {
    override def discriminant: Short = (struct.getShort(2).getOrElse(new java.lang.Short(0.toShort)): java.lang.Short)
    override def switch: com.capnproto.test.TestUnion.Union2.Union = discriminant match {
      case 0 => com.capnproto.test.TestUnion.Union2.Union.u2f0s64(u2f0s64)
      case 1 => com.capnproto.test.TestUnion.Union2.Union.u2f0s32(u2f0s32)
      case 2 => com.capnproto.test.TestUnion.Union2.Union.u2f0s16(u2f0s16)
      case 3 => com.capnproto.test.TestUnion.Union2.Union.u2f0s8(u2f0s8)
      case 4 => com.capnproto.test.TestUnion.Union2.Union.u2f0s1(u2f0s1)
      case d => com.capnproto.test.TestUnion.Union2.Union.Unknown(d)
    }
    override def union: UnionMeta[com.capnproto.test.TestUnion.Union2.Union] = com.capnproto.test.TestUnion.Union2.Union

    override def u2f0s64: Option[java.lang.Long] = struct.getLong(6)
    override def u2f0s32: Option[java.lang.Integer] = struct.getInt(10)
    override def u2f0s16: Option[java.lang.Short] = struct.getShort(18)
    override def u2f0s8: Option[java.lang.Byte] = struct.getByte(33)
    override def u2f0s1: Option[java.lang.Boolean] = struct.getBoolean(256)
  }
  object Union3 extends MetaStruct[Union3] {
    override type Self = Union3.type
    override val structName: String = "Union3"
    override def create(struct: CapnpStruct): Union3 = new Union3Mutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestUnion.Union3.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestUnion.Union3.Builder.dataSectionSizeWords, com.capnproto.test.TestUnion.Union3.Builder.pointerSectionSizeWords)
      new com.capnproto.test.TestUnion.Union3.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.test.TestUnion.Union3, com.capnproto.test.TestUnion.Union3.Builder] {
      override type Self = com.capnproto.test.TestUnion.Union3.Builder.type
      override val structName: String = "Union3"
      override val dataSectionSizeWords: Short = 8
      override val pointerSectionSizeWords: Short = 2
      override def create(struct: CapnpStructBuilder): com.capnproto.test.TestUnion.Union3.Builder = new com.capnproto.test.TestUnion.Union3.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestUnion.Union3.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestUnion.Union3Mutable(struct) with StructBuilder[com.capnproto.test.TestUnion.Union3, com.capnproto.test.TestUnion.Union3.Builder] {
      override type MetaBuilderT = com.capnproto.test.TestUnion.Union3.Builder.type

      override def meta: Union3.type = Union3
      override def metaBuilder: MetaBuilderT = com.capnproto.test.TestUnion.Union3.Builder
      def setU3f0s64(value: java.lang.Long): Builder = { struct.setLong(7, value); struct.setShort(3, -5); this }
      def setU3f0s32(value: java.lang.Integer): Builder = { struct.setInt(11, value); struct.setShort(3, -4); this }
      def setU3f0s16(value: java.lang.Short): Builder = { struct.setShort(19, value); struct.setShort(3, -3); this }
      def setU3f0s8(value: java.lang.Byte): Builder = { struct.setByte(34, value); struct.setShort(3, -2); this }
      def setU3f0s1(value: java.lang.Boolean): Builder = { struct.setBoolean(257, value); struct.setShort(3, -1); this }
    }

    sealed trait Union extends UnionValue[com.capnproto.test.TestUnion.Union3.Union]
    object Union extends UnionMeta[com.capnproto.test.TestUnion.Union3.Union] {
      case class Unknown(discriminant: Short) extends com.capnproto.test.TestUnion.Union3.Union
      case class u3f0s64(value: Option[java.lang.Long]) extends com.capnproto.test.TestUnion.Union3.Union
      case class u3f0s32(value: Option[java.lang.Integer]) extends com.capnproto.test.TestUnion.Union3.Union
      case class u3f0s16(value: Option[java.lang.Short]) extends com.capnproto.test.TestUnion.Union3.Union
      case class u3f0s8(value: Option[java.lang.Byte]) extends com.capnproto.test.TestUnion.Union3.Union
      case class u3f0s1(value: Option[java.lang.Boolean]) extends com.capnproto.test.TestUnion.Union3.Union
    }

    val u3f0s64 = new FieldDescriptor[java.lang.Long, Union3, Union3.type](
      name = "u3f0s64",
      meta = Union3,
      getter = _.u3f0s64,
      manifest = manifest[java.lang.Long],
      isUnion = true
    )

    val u3f0s32 = new FieldDescriptor[java.lang.Integer, Union3, Union3.type](
      name = "u3f0s32",
      meta = Union3,
      getter = _.u3f0s32,
      manifest = manifest[java.lang.Integer],
      isUnion = true
    )

    val u3f0s16 = new FieldDescriptor[java.lang.Short, Union3, Union3.type](
      name = "u3f0s16",
      meta = Union3,
      getter = _.u3f0s16,
      manifest = manifest[java.lang.Short],
      isUnion = true
    )

    val u3f0s8 = new FieldDescriptor[java.lang.Byte, Union3, Union3.type](
      name = "u3f0s8",
      meta = Union3,
      getter = _.u3f0s8,
      manifest = manifest[java.lang.Byte],
      isUnion = true
    )

    val u3f0s1 = new FieldDescriptor[java.lang.Boolean, Union3, Union3.type](
      name = "u3f0s1",
      meta = Union3,
      getter = _.u3f0s1,
      manifest = manifest[java.lang.Boolean],
      isUnion = true
    )
    override val fields: Seq[FieldDescriptor[_, Union3, Union3.type]] = Seq(u3f0s64, u3f0s32, u3f0s16, u3f0s8, u3f0s1)
  }

  trait Union3 extends Struct[Union3] with HasUnion[com.capnproto.test.TestUnion.Union3.Union] {
    override type MetaT = Union3.type
    override type MetaBuilderT = com.capnproto.test.TestUnion.Union3.Builder.type

    override def meta: Union3.type = Union3
    override def metaBuilder: com.capnproto.test.TestUnion.Union3.Builder.type = com.capnproto.test.TestUnion.Union3.Builder

    def struct: CapnpStruct

    def u3f0s64: Option[java.lang.Long]
    def u3f0s32: Option[java.lang.Integer]
    def u3f0s16: Option[java.lang.Short]
    def u3f0s8: Option[java.lang.Byte]
    def u3f0s1: Option[java.lang.Boolean]
  }

  class Union3Mutable(override val struct: CapnpStruct) extends Union3 {
    override def discriminant: Short = (struct.getShort(3).getOrElse(new java.lang.Short(0.toShort)): java.lang.Short)
    override def switch: com.capnproto.test.TestUnion.Union3.Union = discriminant match {
      case 0 => com.capnproto.test.TestUnion.Union3.Union.u3f0s64(u3f0s64)
      case 1 => com.capnproto.test.TestUnion.Union3.Union.u3f0s32(u3f0s32)
      case 2 => com.capnproto.test.TestUnion.Union3.Union.u3f0s16(u3f0s16)
      case 3 => com.capnproto.test.TestUnion.Union3.Union.u3f0s8(u3f0s8)
      case 4 => com.capnproto.test.TestUnion.Union3.Union.u3f0s1(u3f0s1)
      case d => com.capnproto.test.TestUnion.Union3.Union.Unknown(d)
    }
    override def union: UnionMeta[com.capnproto.test.TestUnion.Union3.Union] = com.capnproto.test.TestUnion.Union3.Union

    override def u3f0s64: Option[java.lang.Long] = struct.getLong(7)
    override def u3f0s32: Option[java.lang.Integer] = struct.getInt(11)
    override def u3f0s16: Option[java.lang.Short] = struct.getShort(19)
    override def u3f0s8: Option[java.lang.Byte] = struct.getByte(34)
    override def u3f0s1: Option[java.lang.Boolean] = struct.getBoolean(257)
  }
  val union0 = new FieldDescriptor[com.capnproto.test.TestUnion.Union0, TestUnion, TestUnion.type](
    name = "union0",
    meta = TestUnion,
    getter = x => Some(x.union0),
    manifest = manifest[com.capnproto.test.TestUnion.Union0],
    isUnion = false
  )

  val bit0 = new FieldDescriptor[java.lang.Boolean, TestUnion, TestUnion.type](
    name = "bit0",
    meta = TestUnion,
    getter = _.bit0,
    manifest = manifest[java.lang.Boolean],
    isUnion = false
  )

  val union1 = new FieldDescriptor[com.capnproto.test.TestUnion.Union1, TestUnion, TestUnion.type](
    name = "union1",
    meta = TestUnion,
    getter = x => Some(x.union1),
    manifest = manifest[com.capnproto.test.TestUnion.Union1],
    isUnion = false
  )

  val bit2 = new FieldDescriptor[java.lang.Boolean, TestUnion, TestUnion.type](
    name = "bit2",
    meta = TestUnion,
    getter = _.bit2,
    manifest = manifest[java.lang.Boolean],
    isUnion = false
  )

  val bit3 = new FieldDescriptor[java.lang.Boolean, TestUnion, TestUnion.type](
    name = "bit3",
    meta = TestUnion,
    getter = _.bit3,
    manifest = manifest[java.lang.Boolean],
    isUnion = false
  )

  val bit4 = new FieldDescriptor[java.lang.Boolean, TestUnion, TestUnion.type](
    name = "bit4",
    meta = TestUnion,
    getter = _.bit4,
    manifest = manifest[java.lang.Boolean],
    isUnion = false
  )

  val bit5 = new FieldDescriptor[java.lang.Boolean, TestUnion, TestUnion.type](
    name = "bit5",
    meta = TestUnion,
    getter = _.bit5,
    manifest = manifest[java.lang.Boolean],
    isUnion = false
  )

  val bit6 = new FieldDescriptor[java.lang.Boolean, TestUnion, TestUnion.type](
    name = "bit6",
    meta = TestUnion,
    getter = _.bit6,
    manifest = manifest[java.lang.Boolean],
    isUnion = false
  )

  val bit7 = new FieldDescriptor[java.lang.Boolean, TestUnion, TestUnion.type](
    name = "bit7",
    meta = TestUnion,
    getter = _.bit7,
    manifest = manifest[java.lang.Boolean],
    isUnion = false
  )

  val union2 = new FieldDescriptor[com.capnproto.test.TestUnion.Union2, TestUnion, TestUnion.type](
    name = "union2",
    meta = TestUnion,
    getter = x => Some(x.union2),
    manifest = manifest[com.capnproto.test.TestUnion.Union2],
    isUnion = false
  )

  val union3 = new FieldDescriptor[com.capnproto.test.TestUnion.Union3, TestUnion, TestUnion.type](
    name = "union3",
    meta = TestUnion,
    getter = x => Some(x.union3),
    manifest = manifest[com.capnproto.test.TestUnion.Union3],
    isUnion = false
  )

  val byte0 = new FieldDescriptor[java.lang.Byte, TestUnion, TestUnion.type](
    name = "byte0",
    meta = TestUnion,
    getter = _.byte0,
    manifest = manifest[java.lang.Byte],
    isUnion = false
  )
  override val fields: Seq[FieldDescriptor[_, TestUnion, TestUnion.type]] = Seq(union0, bit0, union1, bit2, bit3, bit4, bit5, bit6, bit7, union2, union3, byte0)
}

trait TestUnion extends Struct[TestUnion] {
  override type MetaT = TestUnion.type
  override type MetaBuilderT = com.capnproto.test.TestUnion.Builder.type

  override def meta: TestUnion.type = TestUnion
  override def metaBuilder: com.capnproto.test.TestUnion.Builder.type = com.capnproto.test.TestUnion.Builder

  def struct: CapnpStruct

  def union0: com.capnproto.test.TestUnion.Union0
  def bit0: Option[java.lang.Boolean]
  def union1: com.capnproto.test.TestUnion.Union1
  def bit2: Option[java.lang.Boolean]
  def bit3: Option[java.lang.Boolean]
  def bit4: Option[java.lang.Boolean]
  def bit5: Option[java.lang.Boolean]
  def bit6: Option[java.lang.Boolean]
  def bit7: Option[java.lang.Boolean]
  def union2: com.capnproto.test.TestUnion.Union2
  def union3: com.capnproto.test.TestUnion.Union3
  def byte0: Option[java.lang.Byte]
}

class TestUnionMutable(override val struct: CapnpStruct) extends TestUnion {
  override def union0: com.capnproto.test.TestUnion.Union0 = new com.capnproto.test.TestUnion.Union0Mutable(struct)

  override def bit0: Option[java.lang.Boolean] = struct.getBoolean(128)
  override def union1: com.capnproto.test.TestUnion.Union1 = new com.capnproto.test.TestUnion.Union1Mutable(struct)

  override def bit2: Option[java.lang.Boolean] = struct.getBoolean(130)
  override def bit3: Option[java.lang.Boolean] = struct.getBoolean(131)
  override def bit4: Option[java.lang.Boolean] = struct.getBoolean(132)
  override def bit5: Option[java.lang.Boolean] = struct.getBoolean(133)
  override def bit6: Option[java.lang.Boolean] = struct.getBoolean(134)
  override def bit7: Option[java.lang.Boolean] = struct.getBoolean(135)
  override def union2: com.capnproto.test.TestUnion.Union2 = new com.capnproto.test.TestUnion.Union2Mutable(struct)

  override def union3: com.capnproto.test.TestUnion.Union3 = new com.capnproto.test.TestUnion.Union3Mutable(struct)

  override def byte0: Option[java.lang.Byte] = struct.getByte(35)
}

object TestUnnamedUnion extends MetaStruct[TestUnnamedUnion] {
  override type Self = TestUnnamedUnion.type
  override val structName: String = "TestUnnamedUnion"
  override def create(struct: CapnpStruct): TestUnnamedUnion = new TestUnnamedUnionMutable(struct)
  def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestUnnamedUnion.Builder = {
    val (segment, pointerOffset) = arena.allocate(1)
    val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestUnnamedUnion.Builder.dataSectionSizeWords, com.capnproto.test.TestUnnamedUnion.Builder.pointerSectionSizeWords)
    new com.capnproto.test.TestUnnamedUnion.Builder(struct)
  }

  object Builder extends MetaStructBuilder[com.capnproto.test.TestUnnamedUnion, com.capnproto.test.TestUnnamedUnion.Builder] {
    override type Self = com.capnproto.test.TestUnnamedUnion.Builder.type
    override val structName: String = "TestUnnamedUnion"
    override val dataSectionSizeWords: Short = 2
    override val pointerSectionSizeWords: Short = 2
    override def create(struct: CapnpStructBuilder): com.capnproto.test.TestUnnamedUnion.Builder = new com.capnproto.test.TestUnnamedUnion.Builder(struct)
    override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestUnnamedUnion.fields
  }
  class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestUnnamedUnionMutable(struct) with StructBuilder[com.capnproto.test.TestUnnamedUnion, com.capnproto.test.TestUnnamedUnion.Builder] {
    override type MetaBuilderT = com.capnproto.test.TestUnnamedUnion.Builder.type

    override def meta: TestUnnamedUnion.type = TestUnnamedUnion
    override def metaBuilder: MetaBuilderT = com.capnproto.test.TestUnnamedUnion.Builder
    def setBefore(value: String): Builder = { struct.setString(0, value); this }
    def setFoo(value: java.lang.Short): Builder = { struct.setShort(0, value); struct.setShort(2, -1); this }
    def setBar(value: java.lang.Integer): Builder = { struct.setInt(2, value); struct.setShort(2, -2); this }
    def setMiddle(value: java.lang.Short): Builder = { struct.setShort(1, value); this }
    def setAfter(value: String): Builder = { struct.setString(1, value); this }
  }

  sealed trait Union extends UnionValue[com.capnproto.test.TestUnnamedUnion.Union]
  object Union extends UnionMeta[com.capnproto.test.TestUnnamedUnion.Union] {
    case class Unknown(discriminant: Short) extends com.capnproto.test.TestUnnamedUnion.Union
    case class foo(value: Option[java.lang.Short]) extends com.capnproto.test.TestUnnamedUnion.Union
    case class bar(value: Option[java.lang.Integer]) extends com.capnproto.test.TestUnnamedUnion.Union
  }

  val before = new FieldDescriptor[String, TestUnnamedUnion, TestUnnamedUnion.type](
    name = "before",
    meta = TestUnnamedUnion,
    getter = _.before,
    manifest = manifest[String],
    isUnion = false
  )

  val foo = new FieldDescriptor[java.lang.Short, TestUnnamedUnion, TestUnnamedUnion.type](
    name = "foo",
    meta = TestUnnamedUnion,
    getter = _.foo,
    manifest = manifest[java.lang.Short],
    isUnion = true
  )

  val bar = new FieldDescriptor[java.lang.Integer, TestUnnamedUnion, TestUnnamedUnion.type](
    name = "bar",
    meta = TestUnnamedUnion,
    getter = _.bar,
    manifest = manifest[java.lang.Integer],
    isUnion = true
  )

  val middle = new FieldDescriptor[java.lang.Short, TestUnnamedUnion, TestUnnamedUnion.type](
    name = "middle",
    meta = TestUnnamedUnion,
    getter = _.middle,
    manifest = manifest[java.lang.Short],
    isUnion = false
  )

  val after = new FieldDescriptor[String, TestUnnamedUnion, TestUnnamedUnion.type](
    name = "after",
    meta = TestUnnamedUnion,
    getter = _.after,
    manifest = manifest[String],
    isUnion = false
  )
  override val fields: Seq[FieldDescriptor[_, TestUnnamedUnion, TestUnnamedUnion.type]] = Seq(before, foo, bar, middle, after)
}

trait TestUnnamedUnion extends Struct[TestUnnamedUnion] with HasUnion[com.capnproto.test.TestUnnamedUnion.Union] {
  override type MetaT = TestUnnamedUnion.type
  override type MetaBuilderT = com.capnproto.test.TestUnnamedUnion.Builder.type

  override def meta: TestUnnamedUnion.type = TestUnnamedUnion
  override def metaBuilder: com.capnproto.test.TestUnnamedUnion.Builder.type = com.capnproto.test.TestUnnamedUnion.Builder

  def struct: CapnpStruct

  def before: Option[String]
  def foo: Option[java.lang.Short]
  def bar: Option[java.lang.Integer]
  def middle: Option[java.lang.Short]
  def after: Option[String]
}

class TestUnnamedUnionMutable(override val struct: CapnpStruct) extends TestUnnamedUnion {
  override def discriminant: Short = (struct.getShort(2).getOrElse(new java.lang.Short(0.toShort)): java.lang.Short)
  override def switch: com.capnproto.test.TestUnnamedUnion.Union = discriminant match {
    case 0 => com.capnproto.test.TestUnnamedUnion.Union.foo(foo)
    case 1 => com.capnproto.test.TestUnnamedUnion.Union.bar(bar)
    case d => com.capnproto.test.TestUnnamedUnion.Union.Unknown(d)
  }
  override def union: UnionMeta[com.capnproto.test.TestUnnamedUnion.Union] = com.capnproto.test.TestUnnamedUnion.Union

  override def before: Option[String] = struct.getString(0)
  override def foo: Option[java.lang.Short] = struct.getShort(0)
  override def bar: Option[java.lang.Integer] = struct.getInt(2)
  override def middle: Option[java.lang.Short] = struct.getShort(1)
  override def after: Option[String] = struct.getString(1)
}

object TestUnionInUnion extends MetaStruct[TestUnionInUnion] {
  override type Self = TestUnionInUnion.type
  override val structName: String = "TestUnionInUnion"
  override def create(struct: CapnpStruct): TestUnionInUnion = new TestUnionInUnionMutable(struct)
  def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestUnionInUnion.Builder = {
    val (segment, pointerOffset) = arena.allocate(1)
    val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestUnionInUnion.Builder.dataSectionSizeWords, com.capnproto.test.TestUnionInUnion.Builder.pointerSectionSizeWords)
    new com.capnproto.test.TestUnionInUnion.Builder(struct)
  }

  object Builder extends MetaStructBuilder[com.capnproto.test.TestUnionInUnion, com.capnproto.test.TestUnionInUnion.Builder] {
    override type Self = com.capnproto.test.TestUnionInUnion.Builder.type
    override val structName: String = "TestUnionInUnion"
    override val dataSectionSizeWords: Short = 2
    override val pointerSectionSizeWords: Short = 0
    override def create(struct: CapnpStructBuilder): com.capnproto.test.TestUnionInUnion.Builder = new com.capnproto.test.TestUnionInUnion.Builder(struct)
    override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestUnionInUnion.fields
  }
  class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestUnionInUnionMutable(struct) with StructBuilder[com.capnproto.test.TestUnionInUnion, com.capnproto.test.TestUnionInUnion.Builder] {
    override type MetaBuilderT = com.capnproto.test.TestUnionInUnion.Builder.type

    override def meta: TestUnionInUnion.type = TestUnionInUnion
    override def metaBuilder: MetaBuilderT = com.capnproto.test.TestUnionInUnion.Builder
    override def outer: com.capnproto.test.TestUnionInUnion.Outer.Builder = new com.capnproto.test.TestUnionInUnion.Outer.Builder(struct)
  }

  object Outer extends MetaStruct[Outer] {
    override type Self = Outer.type
    override val structName: String = "Outer"
    override def create(struct: CapnpStruct): Outer = new OuterMutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestUnionInUnion.Outer.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestUnionInUnion.Outer.Builder.dataSectionSizeWords, com.capnproto.test.TestUnionInUnion.Outer.Builder.pointerSectionSizeWords)
      new com.capnproto.test.TestUnionInUnion.Outer.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.test.TestUnionInUnion.Outer, com.capnproto.test.TestUnionInUnion.Outer.Builder] {
      override type Self = com.capnproto.test.TestUnionInUnion.Outer.Builder.type
      override val structName: String = "Outer"
      override val dataSectionSizeWords: Short = 2
      override val pointerSectionSizeWords: Short = 0
      override def create(struct: CapnpStructBuilder): com.capnproto.test.TestUnionInUnion.Outer.Builder = new com.capnproto.test.TestUnionInUnion.Outer.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestUnionInUnion.Outer.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestUnionInUnion.OuterMutable(struct) with StructBuilder[com.capnproto.test.TestUnionInUnion.Outer, com.capnproto.test.TestUnionInUnion.Outer.Builder] {
      override type MetaBuilderT = com.capnproto.test.TestUnionInUnion.Outer.Builder.type

      override def meta: Outer.type = Outer
      override def metaBuilder: MetaBuilderT = com.capnproto.test.TestUnionInUnion.Outer.Builder
      override def inner: com.capnproto.test.TestUnionInUnion.Outer.Inner.Builder = new com.capnproto.test.TestUnionInUnion.Outer.Inner.Builder(struct)
      def setBaz(value: java.lang.Integer): Builder = { struct.setInt(0, value); struct.setShort(4, -2); this }
    }

    sealed trait Union extends UnionValue[com.capnproto.test.TestUnionInUnion.Outer.Union]
    object Union extends UnionMeta[com.capnproto.test.TestUnionInUnion.Outer.Union] {
      case class Unknown(discriminant: Short) extends com.capnproto.test.TestUnionInUnion.Outer.Union
      case class inner(value: com.capnproto.test.TestUnionInUnion.Outer.Inner) extends com.capnproto.test.TestUnionInUnion.Outer.Union
      case class baz(value: Option[java.lang.Integer]) extends com.capnproto.test.TestUnionInUnion.Outer.Union
    }

    object Inner extends MetaStruct[Inner] {
      override type Self = Inner.type
      override val structName: String = "Inner"
      override def create(struct: CapnpStruct): Inner = new InnerMutable(struct)
      def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestUnionInUnion.Outer.Inner.Builder = {
        val (segment, pointerOffset) = arena.allocate(1)
        val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestUnionInUnion.Outer.Inner.Builder.dataSectionSizeWords, com.capnproto.test.TestUnionInUnion.Outer.Inner.Builder.pointerSectionSizeWords)
        new com.capnproto.test.TestUnionInUnion.Outer.Inner.Builder(struct)
      }

      object Builder extends MetaStructBuilder[com.capnproto.test.TestUnionInUnion.Outer.Inner, com.capnproto.test.TestUnionInUnion.Outer.Inner.Builder] {
        override type Self = com.capnproto.test.TestUnionInUnion.Outer.Inner.Builder.type
        override val structName: String = "Inner"
        override val dataSectionSizeWords: Short = 2
        override val pointerSectionSizeWords: Short = 0
        override def create(struct: CapnpStructBuilder): com.capnproto.test.TestUnionInUnion.Outer.Inner.Builder = new com.capnproto.test.TestUnionInUnion.Outer.Inner.Builder(struct)
        override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestUnionInUnion.Outer.Inner.fields
      }
      class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestUnionInUnion.Outer.InnerMutable(struct) with StructBuilder[com.capnproto.test.TestUnionInUnion.Outer.Inner, com.capnproto.test.TestUnionInUnion.Outer.Inner.Builder] {
        override type MetaBuilderT = com.capnproto.test.TestUnionInUnion.Outer.Inner.Builder.type

        override def meta: Inner.type = Inner
        override def metaBuilder: MetaBuilderT = com.capnproto.test.TestUnionInUnion.Outer.Inner.Builder
        def setFoo(value: java.lang.Integer): Builder = { struct.setInt(0, value); struct.setShort(2, -1); this }
        def setBar(value: java.lang.Integer): Builder = { struct.setInt(0, value); struct.setShort(2, -2); this }
      }

      sealed trait Union extends UnionValue[com.capnproto.test.TestUnionInUnion.Outer.Inner.Union]
      object Union extends UnionMeta[com.capnproto.test.TestUnionInUnion.Outer.Inner.Union] {
        case class Unknown(discriminant: Short) extends com.capnproto.test.TestUnionInUnion.Outer.Inner.Union
        case class foo(value: Option[java.lang.Integer]) extends com.capnproto.test.TestUnionInUnion.Outer.Inner.Union
        case class bar(value: Option[java.lang.Integer]) extends com.capnproto.test.TestUnionInUnion.Outer.Inner.Union
      }

      val foo = new FieldDescriptor[java.lang.Integer, Inner, Inner.type](
        name = "foo",
        meta = Inner,
        getter = _.foo,
        manifest = manifest[java.lang.Integer],
        isUnion = true
      )

      val bar = new FieldDescriptor[java.lang.Integer, Inner, Inner.type](
        name = "bar",
        meta = Inner,
        getter = _.bar,
        manifest = manifest[java.lang.Integer],
        isUnion = true
      )
      override val fields: Seq[FieldDescriptor[_, Inner, Inner.type]] = Seq(foo, bar)
    }

    trait Inner extends Struct[Inner] with HasUnion[com.capnproto.test.TestUnionInUnion.Outer.Inner.Union] {
      override type MetaT = Inner.type
      override type MetaBuilderT = com.capnproto.test.TestUnionInUnion.Outer.Inner.Builder.type

      override def meta: Inner.type = Inner
      override def metaBuilder: com.capnproto.test.TestUnionInUnion.Outer.Inner.Builder.type = com.capnproto.test.TestUnionInUnion.Outer.Inner.Builder

      def struct: CapnpStruct

      def foo: Option[java.lang.Integer]
      def bar: Option[java.lang.Integer]
    }

    class InnerMutable(override val struct: CapnpStruct) extends Inner {
      override def discriminant: Short = (struct.getShort(2).getOrElse(new java.lang.Short(0.toShort)): java.lang.Short)
      override def switch: com.capnproto.test.TestUnionInUnion.Outer.Inner.Union = discriminant match {
        case 0 => com.capnproto.test.TestUnionInUnion.Outer.Inner.Union.foo(foo)
        case 1 => com.capnproto.test.TestUnionInUnion.Outer.Inner.Union.bar(bar)
        case d => com.capnproto.test.TestUnionInUnion.Outer.Inner.Union.Unknown(d)
      }
      override def union: UnionMeta[com.capnproto.test.TestUnionInUnion.Outer.Inner.Union] = com.capnproto.test.TestUnionInUnion.Outer.Inner.Union

      override def foo: Option[java.lang.Integer] = struct.getInt(0)
      override def bar: Option[java.lang.Integer] = struct.getInt(0)
    }
    val inner = new FieldDescriptor[com.capnproto.test.TestUnionInUnion.Outer.Inner, Outer, Outer.type](
      name = "inner",
      meta = Outer,
      getter = x => Some(x.inner),
      manifest = manifest[com.capnproto.test.TestUnionInUnion.Outer.Inner],
      isUnion = true
    )

    val baz = new FieldDescriptor[java.lang.Integer, Outer, Outer.type](
      name = "baz",
      meta = Outer,
      getter = _.baz,
      manifest = manifest[java.lang.Integer],
      isUnion = true
    )
    override val fields: Seq[FieldDescriptor[_, Outer, Outer.type]] = Seq(inner, baz)
  }

  trait Outer extends Struct[Outer] with HasUnion[com.capnproto.test.TestUnionInUnion.Outer.Union] {
    override type MetaT = Outer.type
    override type MetaBuilderT = com.capnproto.test.TestUnionInUnion.Outer.Builder.type

    override def meta: Outer.type = Outer
    override def metaBuilder: com.capnproto.test.TestUnionInUnion.Outer.Builder.type = com.capnproto.test.TestUnionInUnion.Outer.Builder

    def struct: CapnpStruct

    def inner: com.capnproto.test.TestUnionInUnion.Outer.Inner
    def baz: Option[java.lang.Integer]
  }

  class OuterMutable(override val struct: CapnpStruct) extends Outer {
    override def discriminant: Short = (struct.getShort(4).getOrElse(new java.lang.Short(0.toShort)): java.lang.Short)
    override def switch: com.capnproto.test.TestUnionInUnion.Outer.Union = discriminant match {
      case 0 => com.capnproto.test.TestUnionInUnion.Outer.Union.inner(inner)
      case 1 => com.capnproto.test.TestUnionInUnion.Outer.Union.baz(baz)
      case d => com.capnproto.test.TestUnionInUnion.Outer.Union.Unknown(d)
    }
    override def union: UnionMeta[com.capnproto.test.TestUnionInUnion.Outer.Union] = com.capnproto.test.TestUnionInUnion.Outer.Union

    override def inner: com.capnproto.test.TestUnionInUnion.Outer.Inner = new com.capnproto.test.TestUnionInUnion.Outer.InnerMutable(struct)

    override def baz: Option[java.lang.Integer] = struct.getInt(0)
  }
  val outer = new FieldDescriptor[com.capnproto.test.TestUnionInUnion.Outer, TestUnionInUnion, TestUnionInUnion.type](
    name = "outer",
    meta = TestUnionInUnion,
    getter = x => Some(x.outer),
    manifest = manifest[com.capnproto.test.TestUnionInUnion.Outer],
    isUnion = false
  )
  override val fields: Seq[FieldDescriptor[_, TestUnionInUnion, TestUnionInUnion.type]] = Seq(outer)
}

trait TestUnionInUnion extends Struct[TestUnionInUnion] {
  override type MetaT = TestUnionInUnion.type
  override type MetaBuilderT = com.capnproto.test.TestUnionInUnion.Builder.type

  override def meta: TestUnionInUnion.type = TestUnionInUnion
  override def metaBuilder: com.capnproto.test.TestUnionInUnion.Builder.type = com.capnproto.test.TestUnionInUnion.Builder

  def struct: CapnpStruct

  def outer: com.capnproto.test.TestUnionInUnion.Outer
}

class TestUnionInUnionMutable(override val struct: CapnpStruct) extends TestUnionInUnion {
  override def outer: com.capnproto.test.TestUnionInUnion.Outer = new com.capnproto.test.TestUnionInUnion.OuterMutable(struct)

}

object TestGroups extends MetaStruct[TestGroups] {
  override type Self = TestGroups.type
  override val structName: String = "TestGroups"
  override def create(struct: CapnpStruct): TestGroups = new TestGroupsMutable(struct)
  def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestGroups.Builder = {
    val (segment, pointerOffset) = arena.allocate(1)
    val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestGroups.Builder.dataSectionSizeWords, com.capnproto.test.TestGroups.Builder.pointerSectionSizeWords)
    new com.capnproto.test.TestGroups.Builder(struct)
  }

  object Builder extends MetaStructBuilder[com.capnproto.test.TestGroups, com.capnproto.test.TestGroups.Builder] {
    override type Self = com.capnproto.test.TestGroups.Builder.type
    override val structName: String = "TestGroups"
    override val dataSectionSizeWords: Short = 2
    override val pointerSectionSizeWords: Short = 2
    override def create(struct: CapnpStructBuilder): com.capnproto.test.TestGroups.Builder = new com.capnproto.test.TestGroups.Builder(struct)
    override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestGroups.fields
  }
  class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestGroupsMutable(struct) with StructBuilder[com.capnproto.test.TestGroups, com.capnproto.test.TestGroups.Builder] {
    override type MetaBuilderT = com.capnproto.test.TestGroups.Builder.type

    override def meta: TestGroups.type = TestGroups
    override def metaBuilder: MetaBuilderT = com.capnproto.test.TestGroups.Builder
    override def groups: com.capnproto.test.TestGroups.Groups.Builder = new com.capnproto.test.TestGroups.Groups.Builder(struct)
  }

  object Groups extends MetaStruct[Groups] {
    override type Self = Groups.type
    override val structName: String = "Groups"
    override def create(struct: CapnpStruct): Groups = new GroupsMutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestGroups.Groups.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestGroups.Groups.Builder.dataSectionSizeWords, com.capnproto.test.TestGroups.Groups.Builder.pointerSectionSizeWords)
      new com.capnproto.test.TestGroups.Groups.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.test.TestGroups.Groups, com.capnproto.test.TestGroups.Groups.Builder] {
      override type Self = com.capnproto.test.TestGroups.Groups.Builder.type
      override val structName: String = "Groups"
      override val dataSectionSizeWords: Short = 2
      override val pointerSectionSizeWords: Short = 2
      override def create(struct: CapnpStructBuilder): com.capnproto.test.TestGroups.Groups.Builder = new com.capnproto.test.TestGroups.Groups.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestGroups.Groups.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestGroups.GroupsMutable(struct) with StructBuilder[com.capnproto.test.TestGroups.Groups, com.capnproto.test.TestGroups.Groups.Builder] {
      override type MetaBuilderT = com.capnproto.test.TestGroups.Groups.Builder.type

      override def meta: Groups.type = Groups
      override def metaBuilder: MetaBuilderT = com.capnproto.test.TestGroups.Groups.Builder
      override def foo: com.capnproto.test.TestGroups.Groups.Foo.Builder = new com.capnproto.test.TestGroups.Groups.Foo.Builder(struct)
      override def bar: com.capnproto.test.TestGroups.Groups.Bar.Builder = new com.capnproto.test.TestGroups.Groups.Bar.Builder(struct)
      override def baz: com.capnproto.test.TestGroups.Groups.Baz.Builder = new com.capnproto.test.TestGroups.Groups.Baz.Builder(struct)
    }

    sealed trait Union extends UnionValue[com.capnproto.test.TestGroups.Groups.Union]
    object Union extends UnionMeta[com.capnproto.test.TestGroups.Groups.Union] {
      case class Unknown(discriminant: Short) extends com.capnproto.test.TestGroups.Groups.Union
      case class foo(value: com.capnproto.test.TestGroups.Groups.Foo) extends com.capnproto.test.TestGroups.Groups.Union
      case class bar(value: com.capnproto.test.TestGroups.Groups.Bar) extends com.capnproto.test.TestGroups.Groups.Union
      case class baz(value: com.capnproto.test.TestGroups.Groups.Baz) extends com.capnproto.test.TestGroups.Groups.Union
    }

    object Foo extends MetaStruct[Foo] {
      override type Self = Foo.type
      override val structName: String = "Foo"
      override def create(struct: CapnpStruct): Foo = new FooMutable(struct)
      def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestGroups.Groups.Foo.Builder = {
        val (segment, pointerOffset) = arena.allocate(1)
        val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestGroups.Groups.Foo.Builder.dataSectionSizeWords, com.capnproto.test.TestGroups.Groups.Foo.Builder.pointerSectionSizeWords)
        new com.capnproto.test.TestGroups.Groups.Foo.Builder(struct)
      }

      object Builder extends MetaStructBuilder[com.capnproto.test.TestGroups.Groups.Foo, com.capnproto.test.TestGroups.Groups.Foo.Builder] {
        override type Self = com.capnproto.test.TestGroups.Groups.Foo.Builder.type
        override val structName: String = "Foo"
        override val dataSectionSizeWords: Short = 2
        override val pointerSectionSizeWords: Short = 2
        override def create(struct: CapnpStructBuilder): com.capnproto.test.TestGroups.Groups.Foo.Builder = new com.capnproto.test.TestGroups.Groups.Foo.Builder(struct)
        override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestGroups.Groups.Foo.fields
      }
      class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestGroups.Groups.FooMutable(struct) with StructBuilder[com.capnproto.test.TestGroups.Groups.Foo, com.capnproto.test.TestGroups.Groups.Foo.Builder] {
        override type MetaBuilderT = com.capnproto.test.TestGroups.Groups.Foo.Builder.type

        override def meta: Foo.type = Foo
        override def metaBuilder: MetaBuilderT = com.capnproto.test.TestGroups.Groups.Foo.Builder
        def setCorge(value: java.lang.Integer): Builder = { struct.setInt(0, value); this }
        def setGrault(value: java.lang.Long): Builder = { struct.setLong(1, value); this }
        def setGarply(value: String): Builder = { struct.setString(0, value); this }
      }

      val corge = new FieldDescriptor[java.lang.Integer, Foo, Foo.type](
        name = "corge",
        meta = Foo,
        getter = _.corge,
        manifest = manifest[java.lang.Integer],
        isUnion = false
      )

      val grault = new FieldDescriptor[java.lang.Long, Foo, Foo.type](
        name = "grault",
        meta = Foo,
        getter = _.grault,
        manifest = manifest[java.lang.Long],
        isUnion = false
      )

      val garply = new FieldDescriptor[String, Foo, Foo.type](
        name = "garply",
        meta = Foo,
        getter = _.garply,
        manifest = manifest[String],
        isUnion = false
      )
      override val fields: Seq[FieldDescriptor[_, Foo, Foo.type]] = Seq(corge, grault, garply)
    }

    trait Foo extends Struct[Foo] {
      override type MetaT = Foo.type
      override type MetaBuilderT = com.capnproto.test.TestGroups.Groups.Foo.Builder.type

      override def meta: Foo.type = Foo
      override def metaBuilder: com.capnproto.test.TestGroups.Groups.Foo.Builder.type = com.capnproto.test.TestGroups.Groups.Foo.Builder

      def struct: CapnpStruct

      def corge: Option[java.lang.Integer]
      def grault: Option[java.lang.Long]
      def garply: Option[String]
    }

    class FooMutable(override val struct: CapnpStruct) extends Foo {
      override def corge: Option[java.lang.Integer] = struct.getInt(0)
      override def grault: Option[java.lang.Long] = struct.getLong(1)
      override def garply: Option[String] = struct.getString(0)
    }
    object Bar extends MetaStruct[Bar] {
      override type Self = Bar.type
      override val structName: String = "Bar"
      override def create(struct: CapnpStruct): Bar = new BarMutable(struct)
      def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestGroups.Groups.Bar.Builder = {
        val (segment, pointerOffset) = arena.allocate(1)
        val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestGroups.Groups.Bar.Builder.dataSectionSizeWords, com.capnproto.test.TestGroups.Groups.Bar.Builder.pointerSectionSizeWords)
        new com.capnproto.test.TestGroups.Groups.Bar.Builder(struct)
      }

      object Builder extends MetaStructBuilder[com.capnproto.test.TestGroups.Groups.Bar, com.capnproto.test.TestGroups.Groups.Bar.Builder] {
        override type Self = com.capnproto.test.TestGroups.Groups.Bar.Builder.type
        override val structName: String = "Bar"
        override val dataSectionSizeWords: Short = 2
        override val pointerSectionSizeWords: Short = 2
        override def create(struct: CapnpStructBuilder): com.capnproto.test.TestGroups.Groups.Bar.Builder = new com.capnproto.test.TestGroups.Groups.Bar.Builder(struct)
        override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestGroups.Groups.Bar.fields
      }
      class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestGroups.Groups.BarMutable(struct) with StructBuilder[com.capnproto.test.TestGroups.Groups.Bar, com.capnproto.test.TestGroups.Groups.Bar.Builder] {
        override type MetaBuilderT = com.capnproto.test.TestGroups.Groups.Bar.Builder.type

        override def meta: Bar.type = Bar
        override def metaBuilder: MetaBuilderT = com.capnproto.test.TestGroups.Groups.Bar.Builder
        def setCorge(value: java.lang.Integer): Builder = { struct.setInt(0, value); this }
        def setGrault(value: String): Builder = { struct.setString(0, value); this }
        def setGarply(value: java.lang.Long): Builder = { struct.setLong(1, value); this }
      }

      val corge = new FieldDescriptor[java.lang.Integer, Bar, Bar.type](
        name = "corge",
        meta = Bar,
        getter = _.corge,
        manifest = manifest[java.lang.Integer],
        isUnion = false
      )

      val grault = new FieldDescriptor[String, Bar, Bar.type](
        name = "grault",
        meta = Bar,
        getter = _.grault,
        manifest = manifest[String],
        isUnion = false
      )

      val garply = new FieldDescriptor[java.lang.Long, Bar, Bar.type](
        name = "garply",
        meta = Bar,
        getter = _.garply,
        manifest = manifest[java.lang.Long],
        isUnion = false
      )
      override val fields: Seq[FieldDescriptor[_, Bar, Bar.type]] = Seq(corge, grault, garply)
    }

    trait Bar extends Struct[Bar] {
      override type MetaT = Bar.type
      override type MetaBuilderT = com.capnproto.test.TestGroups.Groups.Bar.Builder.type

      override def meta: Bar.type = Bar
      override def metaBuilder: com.capnproto.test.TestGroups.Groups.Bar.Builder.type = com.capnproto.test.TestGroups.Groups.Bar.Builder

      def struct: CapnpStruct

      def corge: Option[java.lang.Integer]
      def grault: Option[String]
      def garply: Option[java.lang.Long]
    }

    class BarMutable(override val struct: CapnpStruct) extends Bar {
      override def corge: Option[java.lang.Integer] = struct.getInt(0)
      override def grault: Option[String] = struct.getString(0)
      override def garply: Option[java.lang.Long] = struct.getLong(1)
    }
    object Baz extends MetaStruct[Baz] {
      override type Self = Baz.type
      override val structName: String = "Baz"
      override def create(struct: CapnpStruct): Baz = new BazMutable(struct)
      def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestGroups.Groups.Baz.Builder = {
        val (segment, pointerOffset) = arena.allocate(1)
        val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestGroups.Groups.Baz.Builder.dataSectionSizeWords, com.capnproto.test.TestGroups.Groups.Baz.Builder.pointerSectionSizeWords)
        new com.capnproto.test.TestGroups.Groups.Baz.Builder(struct)
      }

      object Builder extends MetaStructBuilder[com.capnproto.test.TestGroups.Groups.Baz, com.capnproto.test.TestGroups.Groups.Baz.Builder] {
        override type Self = com.capnproto.test.TestGroups.Groups.Baz.Builder.type
        override val structName: String = "Baz"
        override val dataSectionSizeWords: Short = 2
        override val pointerSectionSizeWords: Short = 2
        override def create(struct: CapnpStructBuilder): com.capnproto.test.TestGroups.Groups.Baz.Builder = new com.capnproto.test.TestGroups.Groups.Baz.Builder(struct)
        override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestGroups.Groups.Baz.fields
      }
      class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestGroups.Groups.BazMutable(struct) with StructBuilder[com.capnproto.test.TestGroups.Groups.Baz, com.capnproto.test.TestGroups.Groups.Baz.Builder] {
        override type MetaBuilderT = com.capnproto.test.TestGroups.Groups.Baz.Builder.type

        override def meta: Baz.type = Baz
        override def metaBuilder: MetaBuilderT = com.capnproto.test.TestGroups.Groups.Baz.Builder
        def setCorge(value: java.lang.Integer): Builder = { struct.setInt(0, value); this }
        def setGrault(value: String): Builder = { struct.setString(0, value); this }
        def setGarply(value: String): Builder = { struct.setString(1, value); this }
      }

      val corge = new FieldDescriptor[java.lang.Integer, Baz, Baz.type](
        name = "corge",
        meta = Baz,
        getter = _.corge,
        manifest = manifest[java.lang.Integer],
        isUnion = false
      )

      val grault = new FieldDescriptor[String, Baz, Baz.type](
        name = "grault",
        meta = Baz,
        getter = _.grault,
        manifest = manifest[String],
        isUnion = false
      )

      val garply = new FieldDescriptor[String, Baz, Baz.type](
        name = "garply",
        meta = Baz,
        getter = _.garply,
        manifest = manifest[String],
        isUnion = false
      )
      override val fields: Seq[FieldDescriptor[_, Baz, Baz.type]] = Seq(corge, grault, garply)
    }

    trait Baz extends Struct[Baz] {
      override type MetaT = Baz.type
      override type MetaBuilderT = com.capnproto.test.TestGroups.Groups.Baz.Builder.type

      override def meta: Baz.type = Baz
      override def metaBuilder: com.capnproto.test.TestGroups.Groups.Baz.Builder.type = com.capnproto.test.TestGroups.Groups.Baz.Builder

      def struct: CapnpStruct

      def corge: Option[java.lang.Integer]
      def grault: Option[String]
      def garply: Option[String]
    }

    class BazMutable(override val struct: CapnpStruct) extends Baz {
      override def corge: Option[java.lang.Integer] = struct.getInt(0)
      override def grault: Option[String] = struct.getString(0)
      override def garply: Option[String] = struct.getString(1)
    }
    val foo = new FieldDescriptor[com.capnproto.test.TestGroups.Groups.Foo, Groups, Groups.type](
      name = "foo",
      meta = Groups,
      getter = x => Some(x.foo),
      manifest = manifest[com.capnproto.test.TestGroups.Groups.Foo],
      isUnion = true
    )

    val bar = new FieldDescriptor[com.capnproto.test.TestGroups.Groups.Bar, Groups, Groups.type](
      name = "bar",
      meta = Groups,
      getter = x => Some(x.bar),
      manifest = manifest[com.capnproto.test.TestGroups.Groups.Bar],
      isUnion = true
    )

    val baz = new FieldDescriptor[com.capnproto.test.TestGroups.Groups.Baz, Groups, Groups.type](
      name = "baz",
      meta = Groups,
      getter = x => Some(x.baz),
      manifest = manifest[com.capnproto.test.TestGroups.Groups.Baz],
      isUnion = true
    )
    override val fields: Seq[FieldDescriptor[_, Groups, Groups.type]] = Seq(foo, bar, baz)
  }

  trait Groups extends Struct[Groups] with HasUnion[com.capnproto.test.TestGroups.Groups.Union] {
    override type MetaT = Groups.type
    override type MetaBuilderT = com.capnproto.test.TestGroups.Groups.Builder.type

    override def meta: Groups.type = Groups
    override def metaBuilder: com.capnproto.test.TestGroups.Groups.Builder.type = com.capnproto.test.TestGroups.Groups.Builder

    def struct: CapnpStruct

    def foo: com.capnproto.test.TestGroups.Groups.Foo
    def bar: com.capnproto.test.TestGroups.Groups.Bar
    def baz: com.capnproto.test.TestGroups.Groups.Baz
  }

  class GroupsMutable(override val struct: CapnpStruct) extends Groups {
    override def discriminant: Short = (struct.getShort(2).getOrElse(new java.lang.Short(0.toShort)): java.lang.Short)
    override def switch: com.capnproto.test.TestGroups.Groups.Union = discriminant match {
      case 0 => com.capnproto.test.TestGroups.Groups.Union.foo(foo)
      case 1 => com.capnproto.test.TestGroups.Groups.Union.bar(bar)
      case 2 => com.capnproto.test.TestGroups.Groups.Union.baz(baz)
      case d => com.capnproto.test.TestGroups.Groups.Union.Unknown(d)
    }
    override def union: UnionMeta[com.capnproto.test.TestGroups.Groups.Union] = com.capnproto.test.TestGroups.Groups.Union

    override def foo: com.capnproto.test.TestGroups.Groups.Foo = new com.capnproto.test.TestGroups.Groups.FooMutable(struct)

    override def bar: com.capnproto.test.TestGroups.Groups.Bar = new com.capnproto.test.TestGroups.Groups.BarMutable(struct)

    override def baz: com.capnproto.test.TestGroups.Groups.Baz = new com.capnproto.test.TestGroups.Groups.BazMutable(struct)

  }
  val groups = new FieldDescriptor[com.capnproto.test.TestGroups.Groups, TestGroups, TestGroups.type](
    name = "groups",
    meta = TestGroups,
    getter = x => Some(x.groups),
    manifest = manifest[com.capnproto.test.TestGroups.Groups],
    isUnion = false
  )
  override val fields: Seq[FieldDescriptor[_, TestGroups, TestGroups.type]] = Seq(groups)
}

trait TestGroups extends Struct[TestGroups] {
  override type MetaT = TestGroups.type
  override type MetaBuilderT = com.capnproto.test.TestGroups.Builder.type

  override def meta: TestGroups.type = TestGroups
  override def metaBuilder: com.capnproto.test.TestGroups.Builder.type = com.capnproto.test.TestGroups.Builder

  def struct: CapnpStruct

  def groups: com.capnproto.test.TestGroups.Groups
}

class TestGroupsMutable(override val struct: CapnpStruct) extends TestGroups {
  override def groups: com.capnproto.test.TestGroups.Groups = new com.capnproto.test.TestGroups.GroupsMutable(struct)

}

object TestInterleavedGroups extends MetaStruct[TestInterleavedGroups] {
  override type Self = TestInterleavedGroups.type
  override val structName: String = "TestInterleavedGroups"
  override def create(struct: CapnpStruct): TestInterleavedGroups = new TestInterleavedGroupsMutable(struct)
  def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestInterleavedGroups.Builder = {
    val (segment, pointerOffset) = arena.allocate(1)
    val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestInterleavedGroups.Builder.dataSectionSizeWords, com.capnproto.test.TestInterleavedGroups.Builder.pointerSectionSizeWords)
    new com.capnproto.test.TestInterleavedGroups.Builder(struct)
  }

  object Builder extends MetaStructBuilder[com.capnproto.test.TestInterleavedGroups, com.capnproto.test.TestInterleavedGroups.Builder] {
    override type Self = com.capnproto.test.TestInterleavedGroups.Builder.type
    override val structName: String = "TestInterleavedGroups"
    override val dataSectionSizeWords: Short = 6
    override val pointerSectionSizeWords: Short = 6
    override def create(struct: CapnpStructBuilder): com.capnproto.test.TestInterleavedGroups.Builder = new com.capnproto.test.TestInterleavedGroups.Builder(struct)
    override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestInterleavedGroups.fields
  }
  class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestInterleavedGroupsMutable(struct) with StructBuilder[com.capnproto.test.TestInterleavedGroups, com.capnproto.test.TestInterleavedGroups.Builder] {
    override type MetaBuilderT = com.capnproto.test.TestInterleavedGroups.Builder.type

    override def meta: TestInterleavedGroups.type = TestInterleavedGroups
    override def metaBuilder: MetaBuilderT = com.capnproto.test.TestInterleavedGroups.Builder
    override def group1: com.capnproto.test.TestInterleavedGroups.Group1.Builder = new com.capnproto.test.TestInterleavedGroups.Group1.Builder(struct)
    override def group2: com.capnproto.test.TestInterleavedGroups.Group2.Builder = new com.capnproto.test.TestInterleavedGroups.Group2.Builder(struct)
  }

  object Group1 extends MetaStruct[Group1] {
    override type Self = Group1.type
    override val structName: String = "Group1"
    override def create(struct: CapnpStruct): Group1 = new Group1Mutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestInterleavedGroups.Group1.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestInterleavedGroups.Group1.Builder.dataSectionSizeWords, com.capnproto.test.TestInterleavedGroups.Group1.Builder.pointerSectionSizeWords)
      new com.capnproto.test.TestInterleavedGroups.Group1.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.test.TestInterleavedGroups.Group1, com.capnproto.test.TestInterleavedGroups.Group1.Builder] {
      override type Self = com.capnproto.test.TestInterleavedGroups.Group1.Builder.type
      override val structName: String = "Group1"
      override val dataSectionSizeWords: Short = 6
      override val pointerSectionSizeWords: Short = 6
      override def create(struct: CapnpStructBuilder): com.capnproto.test.TestInterleavedGroups.Group1.Builder = new com.capnproto.test.TestInterleavedGroups.Group1.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestInterleavedGroups.Group1.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestInterleavedGroups.Group1Mutable(struct) with StructBuilder[com.capnproto.test.TestInterleavedGroups.Group1, com.capnproto.test.TestInterleavedGroups.Group1.Builder] {
      override type MetaBuilderT = com.capnproto.test.TestInterleavedGroups.Group1.Builder.type

      override def meta: Group1.type = Group1
      override def metaBuilder: MetaBuilderT = com.capnproto.test.TestInterleavedGroups.Group1.Builder
      def setFoo(value: java.lang.Integer): Builder = { struct.setInt(0, value); this }
      def setBar(value: java.lang.Long): Builder = { struct.setLong(1, value); this }
      def setQux(value: java.lang.Short): Builder = { struct.setShort(12, value); struct.setShort(14, -1); this }
      override def corge: com.capnproto.test.TestInterleavedGroups.Group1.Corge.Builder = new com.capnproto.test.TestInterleavedGroups.Group1.Corge.Builder(struct)
      def setFred(value: String): Builder = { struct.setString(2, value); struct.setShort(14, -3); this }
      def setWaldo(value: String): Builder = { struct.setString(0, value); this }
    }

    sealed trait Union extends UnionValue[com.capnproto.test.TestInterleavedGroups.Group1.Union]
    object Union extends UnionMeta[com.capnproto.test.TestInterleavedGroups.Group1.Union] {
      case class Unknown(discriminant: Short) extends com.capnproto.test.TestInterleavedGroups.Group1.Union
      case class qux(value: Option[java.lang.Short]) extends com.capnproto.test.TestInterleavedGroups.Group1.Union
      case class corge(value: com.capnproto.test.TestInterleavedGroups.Group1.Corge) extends com.capnproto.test.TestInterleavedGroups.Group1.Union
      case class fred(value: Option[String]) extends com.capnproto.test.TestInterleavedGroups.Group1.Union
    }

    object Corge extends MetaStruct[Corge] {
      override type Self = Corge.type
      override val structName: String = "Corge"
      override def create(struct: CapnpStruct): Corge = new CorgeMutable(struct)
      def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestInterleavedGroups.Group1.Corge.Builder = {
        val (segment, pointerOffset) = arena.allocate(1)
        val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestInterleavedGroups.Group1.Corge.Builder.dataSectionSizeWords, com.capnproto.test.TestInterleavedGroups.Group1.Corge.Builder.pointerSectionSizeWords)
        new com.capnproto.test.TestInterleavedGroups.Group1.Corge.Builder(struct)
      }

      object Builder extends MetaStructBuilder[com.capnproto.test.TestInterleavedGroups.Group1.Corge, com.capnproto.test.TestInterleavedGroups.Group1.Corge.Builder] {
        override type Self = com.capnproto.test.TestInterleavedGroups.Group1.Corge.Builder.type
        override val structName: String = "Corge"
        override val dataSectionSizeWords: Short = 6
        override val pointerSectionSizeWords: Short = 6
        override def create(struct: CapnpStructBuilder): com.capnproto.test.TestInterleavedGroups.Group1.Corge.Builder = new com.capnproto.test.TestInterleavedGroups.Group1.Corge.Builder(struct)
        override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestInterleavedGroups.Group1.Corge.fields
      }
      class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestInterleavedGroups.Group1.CorgeMutable(struct) with StructBuilder[com.capnproto.test.TestInterleavedGroups.Group1.Corge, com.capnproto.test.TestInterleavedGroups.Group1.Corge.Builder] {
        override type MetaBuilderT = com.capnproto.test.TestInterleavedGroups.Group1.Corge.Builder.type

        override def meta: Corge.type = Corge
        override def metaBuilder: MetaBuilderT = com.capnproto.test.TestInterleavedGroups.Group1.Corge.Builder
        def setGrault(value: java.lang.Long): Builder = { struct.setLong(4, value); this }
        def setGarply(value: java.lang.Short): Builder = { struct.setShort(12, value); this }
        def setPlugh(value: String): Builder = { struct.setString(2, value); this }
        def setXyzzy(value: String): Builder = { struct.setString(4, value); this }
      }

      val grault = new FieldDescriptor[java.lang.Long, Corge, Corge.type](
        name = "grault",
        meta = Corge,
        getter = _.grault,
        manifest = manifest[java.lang.Long],
        isUnion = false
      )

      val garply = new FieldDescriptor[java.lang.Short, Corge, Corge.type](
        name = "garply",
        meta = Corge,
        getter = _.garply,
        manifest = manifest[java.lang.Short],
        isUnion = false
      )

      val plugh = new FieldDescriptor[String, Corge, Corge.type](
        name = "plugh",
        meta = Corge,
        getter = _.plugh,
        manifest = manifest[String],
        isUnion = false
      )

      val xyzzy = new FieldDescriptor[String, Corge, Corge.type](
        name = "xyzzy",
        meta = Corge,
        getter = _.xyzzy,
        manifest = manifest[String],
        isUnion = false
      )
      override val fields: Seq[FieldDescriptor[_, Corge, Corge.type]] = Seq(grault, garply, plugh, xyzzy)
    }

    trait Corge extends Struct[Corge] {
      override type MetaT = Corge.type
      override type MetaBuilderT = com.capnproto.test.TestInterleavedGroups.Group1.Corge.Builder.type

      override def meta: Corge.type = Corge
      override def metaBuilder: com.capnproto.test.TestInterleavedGroups.Group1.Corge.Builder.type = com.capnproto.test.TestInterleavedGroups.Group1.Corge.Builder

      def struct: CapnpStruct

      def grault: Option[java.lang.Long]
      def garply: Option[java.lang.Short]
      def plugh: Option[String]
      def xyzzy: Option[String]
    }

    class CorgeMutable(override val struct: CapnpStruct) extends Corge {
      override def grault: Option[java.lang.Long] = struct.getLong(4)
      override def garply: Option[java.lang.Short] = struct.getShort(12)
      override def plugh: Option[String] = struct.getString(2)
      override def xyzzy: Option[String] = struct.getString(4)
    }
    val foo = new FieldDescriptor[java.lang.Integer, Group1, Group1.type](
      name = "foo",
      meta = Group1,
      getter = _.foo,
      manifest = manifest[java.lang.Integer],
      isUnion = false
    )

    val bar = new FieldDescriptor[java.lang.Long, Group1, Group1.type](
      name = "bar",
      meta = Group1,
      getter = _.bar,
      manifest = manifest[java.lang.Long],
      isUnion = false
    )

    val qux = new FieldDescriptor[java.lang.Short, Group1, Group1.type](
      name = "qux",
      meta = Group1,
      getter = _.qux,
      manifest = manifest[java.lang.Short],
      isUnion = true
    )

    val corge = new FieldDescriptor[com.capnproto.test.TestInterleavedGroups.Group1.Corge, Group1, Group1.type](
      name = "corge",
      meta = Group1,
      getter = x => Some(x.corge),
      manifest = manifest[com.capnproto.test.TestInterleavedGroups.Group1.Corge],
      isUnion = true
    )

    val fred = new FieldDescriptor[String, Group1, Group1.type](
      name = "fred",
      meta = Group1,
      getter = _.fred,
      manifest = manifest[String],
      isUnion = true
    )

    val waldo = new FieldDescriptor[String, Group1, Group1.type](
      name = "waldo",
      meta = Group1,
      getter = _.waldo,
      manifest = manifest[String],
      isUnion = false
    )
    override val fields: Seq[FieldDescriptor[_, Group1, Group1.type]] = Seq(foo, bar, qux, corge, fred, waldo)
  }

  trait Group1 extends Struct[Group1] with HasUnion[com.capnproto.test.TestInterleavedGroups.Group1.Union] {
    override type MetaT = Group1.type
    override type MetaBuilderT = com.capnproto.test.TestInterleavedGroups.Group1.Builder.type

    override def meta: Group1.type = Group1
    override def metaBuilder: com.capnproto.test.TestInterleavedGroups.Group1.Builder.type = com.capnproto.test.TestInterleavedGroups.Group1.Builder

    def struct: CapnpStruct

    def foo: Option[java.lang.Integer]
    def bar: Option[java.lang.Long]
    def qux: Option[java.lang.Short]
    def corge: com.capnproto.test.TestInterleavedGroups.Group1.Corge
    def fred: Option[String]
    def waldo: Option[String]
  }

  class Group1Mutable(override val struct: CapnpStruct) extends Group1 {
    override def discriminant: Short = (struct.getShort(14).getOrElse(new java.lang.Short(0.toShort)): java.lang.Short)
    override def switch: com.capnproto.test.TestInterleavedGroups.Group1.Union = discriminant match {
      case 0 => com.capnproto.test.TestInterleavedGroups.Group1.Union.qux(qux)
      case 1 => com.capnproto.test.TestInterleavedGroups.Group1.Union.corge(corge)
      case 2 => com.capnproto.test.TestInterleavedGroups.Group1.Union.fred(fred)
      case d => com.capnproto.test.TestInterleavedGroups.Group1.Union.Unknown(d)
    }
    override def union: UnionMeta[com.capnproto.test.TestInterleavedGroups.Group1.Union] = com.capnproto.test.TestInterleavedGroups.Group1.Union

    override def foo: Option[java.lang.Integer] = struct.getInt(0)
    override def bar: Option[java.lang.Long] = struct.getLong(1)
    override def qux: Option[java.lang.Short] = struct.getShort(12)
    override def corge: com.capnproto.test.TestInterleavedGroups.Group1.Corge = new com.capnproto.test.TestInterleavedGroups.Group1.CorgeMutable(struct)

    override def fred: Option[String] = struct.getString(2)
    override def waldo: Option[String] = struct.getString(0)
  }
  object Group2 extends MetaStruct[Group2] {
    override type Self = Group2.type
    override val structName: String = "Group2"
    override def create(struct: CapnpStruct): Group2 = new Group2Mutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestInterleavedGroups.Group2.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestInterleavedGroups.Group2.Builder.dataSectionSizeWords, com.capnproto.test.TestInterleavedGroups.Group2.Builder.pointerSectionSizeWords)
      new com.capnproto.test.TestInterleavedGroups.Group2.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.test.TestInterleavedGroups.Group2, com.capnproto.test.TestInterleavedGroups.Group2.Builder] {
      override type Self = com.capnproto.test.TestInterleavedGroups.Group2.Builder.type
      override val structName: String = "Group2"
      override val dataSectionSizeWords: Short = 6
      override val pointerSectionSizeWords: Short = 6
      override def create(struct: CapnpStructBuilder): com.capnproto.test.TestInterleavedGroups.Group2.Builder = new com.capnproto.test.TestInterleavedGroups.Group2.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestInterleavedGroups.Group2.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestInterleavedGroups.Group2Mutable(struct) with StructBuilder[com.capnproto.test.TestInterleavedGroups.Group2, com.capnproto.test.TestInterleavedGroups.Group2.Builder] {
      override type MetaBuilderT = com.capnproto.test.TestInterleavedGroups.Group2.Builder.type

      override def meta: Group2.type = Group2
      override def metaBuilder: MetaBuilderT = com.capnproto.test.TestInterleavedGroups.Group2.Builder
      def setFoo(value: java.lang.Integer): Builder = { struct.setInt(1, value); this }
      def setBar(value: java.lang.Long): Builder = { struct.setLong(2, value); this }
      def setQux(value: java.lang.Short): Builder = { struct.setShort(13, value); struct.setShort(15, -1); this }
      override def corge: com.capnproto.test.TestInterleavedGroups.Group2.Corge.Builder = new com.capnproto.test.TestInterleavedGroups.Group2.Corge.Builder(struct)
      def setFred(value: String): Builder = { struct.setString(3, value); struct.setShort(15, -3); this }
      def setWaldo(value: String): Builder = { struct.setString(1, value); this }
    }

    sealed trait Union extends UnionValue[com.capnproto.test.TestInterleavedGroups.Group2.Union]
    object Union extends UnionMeta[com.capnproto.test.TestInterleavedGroups.Group2.Union] {
      case class Unknown(discriminant: Short) extends com.capnproto.test.TestInterleavedGroups.Group2.Union
      case class qux(value: Option[java.lang.Short]) extends com.capnproto.test.TestInterleavedGroups.Group2.Union
      case class corge(value: com.capnproto.test.TestInterleavedGroups.Group2.Corge) extends com.capnproto.test.TestInterleavedGroups.Group2.Union
      case class fred(value: Option[String]) extends com.capnproto.test.TestInterleavedGroups.Group2.Union
    }

    object Corge extends MetaStruct[Corge] {
      override type Self = Corge.type
      override val structName: String = "Corge"
      override def create(struct: CapnpStruct): Corge = new CorgeMutable(struct)
      def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestInterleavedGroups.Group2.Corge.Builder = {
        val (segment, pointerOffset) = arena.allocate(1)
        val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestInterleavedGroups.Group2.Corge.Builder.dataSectionSizeWords, com.capnproto.test.TestInterleavedGroups.Group2.Corge.Builder.pointerSectionSizeWords)
        new com.capnproto.test.TestInterleavedGroups.Group2.Corge.Builder(struct)
      }

      object Builder extends MetaStructBuilder[com.capnproto.test.TestInterleavedGroups.Group2.Corge, com.capnproto.test.TestInterleavedGroups.Group2.Corge.Builder] {
        override type Self = com.capnproto.test.TestInterleavedGroups.Group2.Corge.Builder.type
        override val structName: String = "Corge"
        override val dataSectionSizeWords: Short = 6
        override val pointerSectionSizeWords: Short = 6
        override def create(struct: CapnpStructBuilder): com.capnproto.test.TestInterleavedGroups.Group2.Corge.Builder = new com.capnproto.test.TestInterleavedGroups.Group2.Corge.Builder(struct)
        override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestInterleavedGroups.Group2.Corge.fields
      }
      class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestInterleavedGroups.Group2.CorgeMutable(struct) with StructBuilder[com.capnproto.test.TestInterleavedGroups.Group2.Corge, com.capnproto.test.TestInterleavedGroups.Group2.Corge.Builder] {
        override type MetaBuilderT = com.capnproto.test.TestInterleavedGroups.Group2.Corge.Builder.type

        override def meta: Corge.type = Corge
        override def metaBuilder: MetaBuilderT = com.capnproto.test.TestInterleavedGroups.Group2.Corge.Builder
        def setGrault(value: java.lang.Long): Builder = { struct.setLong(5, value); this }
        def setGarply(value: java.lang.Short): Builder = { struct.setShort(13, value); this }
        def setPlugh(value: String): Builder = { struct.setString(3, value); this }
        def setXyzzy(value: String): Builder = { struct.setString(5, value); this }
      }

      val grault = new FieldDescriptor[java.lang.Long, Corge, Corge.type](
        name = "grault",
        meta = Corge,
        getter = _.grault,
        manifest = manifest[java.lang.Long],
        isUnion = false
      )

      val garply = new FieldDescriptor[java.lang.Short, Corge, Corge.type](
        name = "garply",
        meta = Corge,
        getter = _.garply,
        manifest = manifest[java.lang.Short],
        isUnion = false
      )

      val plugh = new FieldDescriptor[String, Corge, Corge.type](
        name = "plugh",
        meta = Corge,
        getter = _.plugh,
        manifest = manifest[String],
        isUnion = false
      )

      val xyzzy = new FieldDescriptor[String, Corge, Corge.type](
        name = "xyzzy",
        meta = Corge,
        getter = _.xyzzy,
        manifest = manifest[String],
        isUnion = false
      )
      override val fields: Seq[FieldDescriptor[_, Corge, Corge.type]] = Seq(grault, garply, plugh, xyzzy)
    }

    trait Corge extends Struct[Corge] {
      override type MetaT = Corge.type
      override type MetaBuilderT = com.capnproto.test.TestInterleavedGroups.Group2.Corge.Builder.type

      override def meta: Corge.type = Corge
      override def metaBuilder: com.capnproto.test.TestInterleavedGroups.Group2.Corge.Builder.type = com.capnproto.test.TestInterleavedGroups.Group2.Corge.Builder

      def struct: CapnpStruct

      def grault: Option[java.lang.Long]
      def garply: Option[java.lang.Short]
      def plugh: Option[String]
      def xyzzy: Option[String]
    }

    class CorgeMutable(override val struct: CapnpStruct) extends Corge {
      override def grault: Option[java.lang.Long] = struct.getLong(5)
      override def garply: Option[java.lang.Short] = struct.getShort(13)
      override def plugh: Option[String] = struct.getString(3)
      override def xyzzy: Option[String] = struct.getString(5)
    }
    val foo = new FieldDescriptor[java.lang.Integer, Group2, Group2.type](
      name = "foo",
      meta = Group2,
      getter = _.foo,
      manifest = manifest[java.lang.Integer],
      isUnion = false
    )

    val bar = new FieldDescriptor[java.lang.Long, Group2, Group2.type](
      name = "bar",
      meta = Group2,
      getter = _.bar,
      manifest = manifest[java.lang.Long],
      isUnion = false
    )

    val qux = new FieldDescriptor[java.lang.Short, Group2, Group2.type](
      name = "qux",
      meta = Group2,
      getter = _.qux,
      manifest = manifest[java.lang.Short],
      isUnion = true
    )

    val corge = new FieldDescriptor[com.capnproto.test.TestInterleavedGroups.Group2.Corge, Group2, Group2.type](
      name = "corge",
      meta = Group2,
      getter = x => Some(x.corge),
      manifest = manifest[com.capnproto.test.TestInterleavedGroups.Group2.Corge],
      isUnion = true
    )

    val fred = new FieldDescriptor[String, Group2, Group2.type](
      name = "fred",
      meta = Group2,
      getter = _.fred,
      manifest = manifest[String],
      isUnion = true
    )

    val waldo = new FieldDescriptor[String, Group2, Group2.type](
      name = "waldo",
      meta = Group2,
      getter = _.waldo,
      manifest = manifest[String],
      isUnion = false
    )
    override val fields: Seq[FieldDescriptor[_, Group2, Group2.type]] = Seq(foo, bar, qux, corge, fred, waldo)
  }

  trait Group2 extends Struct[Group2] with HasUnion[com.capnproto.test.TestInterleavedGroups.Group2.Union] {
    override type MetaT = Group2.type
    override type MetaBuilderT = com.capnproto.test.TestInterleavedGroups.Group2.Builder.type

    override def meta: Group2.type = Group2
    override def metaBuilder: com.capnproto.test.TestInterleavedGroups.Group2.Builder.type = com.capnproto.test.TestInterleavedGroups.Group2.Builder

    def struct: CapnpStruct

    def foo: Option[java.lang.Integer]
    def bar: Option[java.lang.Long]
    def qux: Option[java.lang.Short]
    def corge: com.capnproto.test.TestInterleavedGroups.Group2.Corge
    def fred: Option[String]
    def waldo: Option[String]
  }

  class Group2Mutable(override val struct: CapnpStruct) extends Group2 {
    override def discriminant: Short = (struct.getShort(15).getOrElse(new java.lang.Short(0.toShort)): java.lang.Short)
    override def switch: com.capnproto.test.TestInterleavedGroups.Group2.Union = discriminant match {
      case 0 => com.capnproto.test.TestInterleavedGroups.Group2.Union.qux(qux)
      case 1 => com.capnproto.test.TestInterleavedGroups.Group2.Union.corge(corge)
      case 2 => com.capnproto.test.TestInterleavedGroups.Group2.Union.fred(fred)
      case d => com.capnproto.test.TestInterleavedGroups.Group2.Union.Unknown(d)
    }
    override def union: UnionMeta[com.capnproto.test.TestInterleavedGroups.Group2.Union] = com.capnproto.test.TestInterleavedGroups.Group2.Union

    override def foo: Option[java.lang.Integer] = struct.getInt(1)
    override def bar: Option[java.lang.Long] = struct.getLong(2)
    override def qux: Option[java.lang.Short] = struct.getShort(13)
    override def corge: com.capnproto.test.TestInterleavedGroups.Group2.Corge = new com.capnproto.test.TestInterleavedGroups.Group2.CorgeMutable(struct)

    override def fred: Option[String] = struct.getString(3)
    override def waldo: Option[String] = struct.getString(1)
  }
  val group1 = new FieldDescriptor[com.capnproto.test.TestInterleavedGroups.Group1, TestInterleavedGroups, TestInterleavedGroups.type](
    name = "group1",
    meta = TestInterleavedGroups,
    getter = x => Some(x.group1),
    manifest = manifest[com.capnproto.test.TestInterleavedGroups.Group1],
    isUnion = false
  )

  val group2 = new FieldDescriptor[com.capnproto.test.TestInterleavedGroups.Group2, TestInterleavedGroups, TestInterleavedGroups.type](
    name = "group2",
    meta = TestInterleavedGroups,
    getter = x => Some(x.group2),
    manifest = manifest[com.capnproto.test.TestInterleavedGroups.Group2],
    isUnion = false
  )
  override val fields: Seq[FieldDescriptor[_, TestInterleavedGroups, TestInterleavedGroups.type]] = Seq(group1, group2)
}

trait TestInterleavedGroups extends Struct[TestInterleavedGroups] {
  override type MetaT = TestInterleavedGroups.type
  override type MetaBuilderT = com.capnproto.test.TestInterleavedGroups.Builder.type

  override def meta: TestInterleavedGroups.type = TestInterleavedGroups
  override def metaBuilder: com.capnproto.test.TestInterleavedGroups.Builder.type = com.capnproto.test.TestInterleavedGroups.Builder

  def struct: CapnpStruct

  def group1: com.capnproto.test.TestInterleavedGroups.Group1
  def group2: com.capnproto.test.TestInterleavedGroups.Group2
}

class TestInterleavedGroupsMutable(override val struct: CapnpStruct) extends TestInterleavedGroups {
  override def group1: com.capnproto.test.TestInterleavedGroups.Group1 = new com.capnproto.test.TestInterleavedGroups.Group1Mutable(struct)

  override def group2: com.capnproto.test.TestInterleavedGroups.Group2 = new com.capnproto.test.TestInterleavedGroups.Group2Mutable(struct)

}

object TestUnionDefaults extends MetaStruct[TestUnionDefaults] {
  override type Self = TestUnionDefaults.type
  override val structName: String = "TestUnionDefaults"
  override def create(struct: CapnpStruct): TestUnionDefaults = new TestUnionDefaultsMutable(struct)
  def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestUnionDefaults.Builder = {
    val (segment, pointerOffset) = arena.allocate(1)
    val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestUnionDefaults.Builder.dataSectionSizeWords, com.capnproto.test.TestUnionDefaults.Builder.pointerSectionSizeWords)
    new com.capnproto.test.TestUnionDefaults.Builder(struct)
  }

  object Builder extends MetaStructBuilder[com.capnproto.test.TestUnionDefaults, com.capnproto.test.TestUnionDefaults.Builder] {
    override type Self = com.capnproto.test.TestUnionDefaults.Builder.type
    override val structName: String = "TestUnionDefaults"
    override val dataSectionSizeWords: Short = 0
    override val pointerSectionSizeWords: Short = 4
    override def create(struct: CapnpStructBuilder): com.capnproto.test.TestUnionDefaults.Builder = new com.capnproto.test.TestUnionDefaults.Builder(struct)
    override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestUnionDefaults.fields
  }
  class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestUnionDefaultsMutable(struct) with StructBuilder[com.capnproto.test.TestUnionDefaults, com.capnproto.test.TestUnionDefaults.Builder] {
    override type MetaBuilderT = com.capnproto.test.TestUnionDefaults.Builder.type

    override def meta: TestUnionDefaults.type = TestUnionDefaults
    override def metaBuilder: MetaBuilderT = com.capnproto.test.TestUnionDefaults.Builder
    def setS16s8s64s8Set(value: com.capnproto.test.TestUnion): Builder = { struct.setNone(); this }
    def setS0sps1s32Set(value: com.capnproto.test.TestUnion): Builder = { struct.setNone(); this }
    def setUnnamed1(value: com.capnproto.test.TestUnnamedUnion): Builder = { struct.setNone(); this }
    def setUnnamed2(value: com.capnproto.test.TestUnnamedUnion): Builder = { struct.setNone(); this }
  }

  val s16s8s64s8Set = new FieldDescriptor[com.capnproto.test.TestUnion, TestUnionDefaults, TestUnionDefaults.type](
    name = "s16s8s64s8Set",
    meta = TestUnionDefaults,
    getter = _.s16s8s64s8Set,
    manifest = manifest[com.capnproto.test.TestUnion],
    isUnion = false
  )

  val s0sps1s32Set = new FieldDescriptor[com.capnproto.test.TestUnion, TestUnionDefaults, TestUnionDefaults.type](
    name = "s0sps1s32Set",
    meta = TestUnionDefaults,
    getter = _.s0sps1s32Set,
    manifest = manifest[com.capnproto.test.TestUnion],
    isUnion = false
  )

  val unnamed1 = new FieldDescriptor[com.capnproto.test.TestUnnamedUnion, TestUnionDefaults, TestUnionDefaults.type](
    name = "unnamed1",
    meta = TestUnionDefaults,
    getter = _.unnamed1,
    manifest = manifest[com.capnproto.test.TestUnnamedUnion],
    isUnion = false
  )

  val unnamed2 = new FieldDescriptor[com.capnproto.test.TestUnnamedUnion, TestUnionDefaults, TestUnionDefaults.type](
    name = "unnamed2",
    meta = TestUnionDefaults,
    getter = _.unnamed2,
    manifest = manifest[com.capnproto.test.TestUnnamedUnion],
    isUnion = false
  )
  override val fields: Seq[FieldDescriptor[_, TestUnionDefaults, TestUnionDefaults.type]] = Seq(s16s8s64s8Set, s0sps1s32Set, unnamed1, unnamed2)
}

trait TestUnionDefaults extends Struct[TestUnionDefaults] {
  override type MetaT = TestUnionDefaults.type
  override type MetaBuilderT = com.capnproto.test.TestUnionDefaults.Builder.type

  override def meta: TestUnionDefaults.type = TestUnionDefaults
  override def metaBuilder: com.capnproto.test.TestUnionDefaults.Builder.type = com.capnproto.test.TestUnionDefaults.Builder

  def struct: CapnpStruct

  def s16s8s64s8Set: Option[com.capnproto.test.TestUnion]
  def s0sps1s32Set: Option[com.capnproto.test.TestUnion]
  def unnamed1: Option[com.capnproto.test.TestUnnamedUnion]
  def unnamed2: Option[com.capnproto.test.TestUnnamedUnion]
}

class TestUnionDefaultsMutable(override val struct: CapnpStruct) extends TestUnionDefaults {
  override def s16s8s64s8Set: Option[com.capnproto.test.TestUnion] = struct.getStruct(0).map(new com.capnproto.test.TestUnionMutable(_))
  override def s0sps1s32Set: Option[com.capnproto.test.TestUnion] = struct.getStruct(1).map(new com.capnproto.test.TestUnionMutable(_))
  override def unnamed1: Option[com.capnproto.test.TestUnnamedUnion] = struct.getStruct(2).map(new com.capnproto.test.TestUnnamedUnionMutable(_))
  override def unnamed2: Option[com.capnproto.test.TestUnnamedUnion] = struct.getStruct(3).map(new com.capnproto.test.TestUnnamedUnionMutable(_))
}

object TestNestedTypes extends MetaStruct[TestNestedTypes] {
  override type Self = TestNestedTypes.type
  override val structName: String = "TestNestedTypes"
  override def create(struct: CapnpStruct): TestNestedTypes = new TestNestedTypesMutable(struct)
  def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestNestedTypes.Builder = {
    val (segment, pointerOffset) = arena.allocate(1)
    val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestNestedTypes.Builder.dataSectionSizeWords, com.capnproto.test.TestNestedTypes.Builder.pointerSectionSizeWords)
    new com.capnproto.test.TestNestedTypes.Builder(struct)
  }

  object Builder extends MetaStructBuilder[com.capnproto.test.TestNestedTypes, com.capnproto.test.TestNestedTypes.Builder] {
    override type Self = com.capnproto.test.TestNestedTypes.Builder.type
    override val structName: String = "TestNestedTypes"
    override val dataSectionSizeWords: Short = 1
    override val pointerSectionSizeWords: Short = 1
    override def create(struct: CapnpStructBuilder): com.capnproto.test.TestNestedTypes.Builder = new com.capnproto.test.TestNestedTypes.Builder(struct)
    override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestNestedTypes.fields
  }
  class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestNestedTypesMutable(struct) with StructBuilder[com.capnproto.test.TestNestedTypes, com.capnproto.test.TestNestedTypes.Builder] {
    override type MetaBuilderT = com.capnproto.test.TestNestedTypes.Builder.type

    override def meta: TestNestedTypes.type = TestNestedTypes
    override def metaBuilder: MetaBuilderT = com.capnproto.test.TestNestedTypes.Builder
    def setNestedStruct(value: com.capnproto.test.TestNestedTypes.NestedStruct): Builder = { struct.setNone(); this }
    def setOuterNestedEnum(value: com.capnproto.test.TestNestedTypes.NestedEnum): Builder = { struct.setShort(0, value.id.toShort); this }
    def setInnerNestedEnum(value: com.capnproto.test.TestNestedTypes.NestedStruct.NestedEnum): Builder = { struct.setShort(1, value.id.toShort); this }
  }

  object NestedEnum extends EnumMeta[NestedEnum] {
    case class Unknown(override val id: Int) extends NestedEnum(NestedEnum, id, null, null)

    val foo = new NestedEnum(this, 0, "foo", "foo")
    val bar = new NestedEnum(this, 1, "bar", "bar")

    override val values = Vector(
      foo,
      bar
    )

    override def findByIdOrNull(id: Int): NestedEnum = values.lift(id).getOrElse(null)
    override def findByNameOrNull(name: String): NestedEnum = null
    override def findByStringValueOrNull(v: String): NestedEnum = null
  }

  sealed class NestedEnum(
    override val meta: EnumMeta[NestedEnum],
    override val id: Int,
    override val name: String,
    override val stringValue: String
  ) extends Enum[NestedEnum]

  object NestedStruct extends MetaStruct[NestedStruct] {
    override type Self = NestedStruct.type
    override val structName: String = "NestedStruct"
    override def create(struct: CapnpStruct): NestedStruct = new NestedStructMutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestNestedTypes.NestedStruct.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestNestedTypes.NestedStruct.Builder.dataSectionSizeWords, com.capnproto.test.TestNestedTypes.NestedStruct.Builder.pointerSectionSizeWords)
      new com.capnproto.test.TestNestedTypes.NestedStruct.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.test.TestNestedTypes.NestedStruct, com.capnproto.test.TestNestedTypes.NestedStruct.Builder] {
      override type Self = com.capnproto.test.TestNestedTypes.NestedStruct.Builder.type
      override val structName: String = "NestedStruct"
      override val dataSectionSizeWords: Short = 1
      override val pointerSectionSizeWords: Short = 0
      override def create(struct: CapnpStructBuilder): com.capnproto.test.TestNestedTypes.NestedStruct.Builder = new com.capnproto.test.TestNestedTypes.NestedStruct.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestNestedTypes.NestedStruct.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestNestedTypes.NestedStructMutable(struct) with StructBuilder[com.capnproto.test.TestNestedTypes.NestedStruct, com.capnproto.test.TestNestedTypes.NestedStruct.Builder] {
      override type MetaBuilderT = com.capnproto.test.TestNestedTypes.NestedStruct.Builder.type

      override def meta: NestedStruct.type = NestedStruct
      override def metaBuilder: MetaBuilderT = com.capnproto.test.TestNestedTypes.NestedStruct.Builder
      def setOuterNestedEnum(value: com.capnproto.test.TestNestedTypes.NestedEnum): Builder = { struct.setShort(0, value.id.toShort); this }
      def setInnerNestedEnum(value: com.capnproto.test.TestNestedTypes.NestedStruct.NestedEnum): Builder = { struct.setShort(1, value.id.toShort); this }
    }

    object NestedEnum extends EnumMeta[NestedEnum] {
      case class Unknown(override val id: Int) extends NestedEnum(NestedEnum, id, null, null)

      val baz = new NestedEnum(this, 0, "baz", "baz")
      val qux = new NestedEnum(this, 1, "qux", "qux")
      val quux = new NestedEnum(this, 2, "quux", "quux")

      override val values = Vector(
        baz,
        qux,
        quux
      )

      override def findByIdOrNull(id: Int): NestedEnum = values.lift(id).getOrElse(null)
      override def findByNameOrNull(name: String): NestedEnum = null
      override def findByStringValueOrNull(v: String): NestedEnum = null
    }

    sealed class NestedEnum(
      override val meta: EnumMeta[NestedEnum],
      override val id: Int,
      override val name: String,
      override val stringValue: String
    ) extends Enum[NestedEnum]
    val outerNestedEnum = new FieldDescriptor[com.capnproto.test.TestNestedTypes.NestedEnum, NestedStruct, NestedStruct.type](
      name = "outerNestedEnum",
      meta = NestedStruct,
      getter = _.outerNestedEnum,
      manifest = manifest[com.capnproto.test.TestNestedTypes.NestedEnum],
      isUnion = false
    )

    val innerNestedEnum = new FieldDescriptor[com.capnproto.test.TestNestedTypes.NestedStruct.NestedEnum, NestedStruct, NestedStruct.type](
      name = "innerNestedEnum",
      meta = NestedStruct,
      getter = _.innerNestedEnum,
      manifest = manifest[com.capnproto.test.TestNestedTypes.NestedStruct.NestedEnum],
      isUnion = false
    )
    override val fields: Seq[FieldDescriptor[_, NestedStruct, NestedStruct.type]] = Seq(outerNestedEnum, innerNestedEnum)
  }

  trait NestedStruct extends Struct[NestedStruct] {
    override type MetaT = NestedStruct.type
    override type MetaBuilderT = com.capnproto.test.TestNestedTypes.NestedStruct.Builder.type

    override def meta: NestedStruct.type = NestedStruct
    override def metaBuilder: com.capnproto.test.TestNestedTypes.NestedStruct.Builder.type = com.capnproto.test.TestNestedTypes.NestedStruct.Builder

    def struct: CapnpStruct

    def outerNestedEnum: Option[com.capnproto.test.TestNestedTypes.NestedEnum]
    def innerNestedEnum: Option[com.capnproto.test.TestNestedTypes.NestedStruct.NestedEnum]
  }

  class NestedStructMutable(override val struct: CapnpStruct) extends NestedStruct {
    override def outerNestedEnum: Option[com.capnproto.test.TestNestedTypes.NestedEnum] = struct.getShort(0).map(id => com.capnproto.test.TestNestedTypes.NestedEnum.findById(id.toInt).getOrElse(com.capnproto.test.TestNestedTypes.NestedEnum.Unknown(id.toShort)))
    override def innerNestedEnum: Option[com.capnproto.test.TestNestedTypes.NestedStruct.NestedEnum] = struct.getShort(1).map(id => com.capnproto.test.TestNestedTypes.NestedStruct.NestedEnum.findById(id.toInt).getOrElse(com.capnproto.test.TestNestedTypes.NestedStruct.NestedEnum.Unknown(id.toShort)))
  }
  val nestedStruct = new FieldDescriptor[com.capnproto.test.TestNestedTypes.NestedStruct, TestNestedTypes, TestNestedTypes.type](
    name = "nestedStruct",
    meta = TestNestedTypes,
    getter = _.nestedStruct,
    manifest = manifest[com.capnproto.test.TestNestedTypes.NestedStruct],
    isUnion = false
  )

  val outerNestedEnum = new FieldDescriptor[com.capnproto.test.TestNestedTypes.NestedEnum, TestNestedTypes, TestNestedTypes.type](
    name = "outerNestedEnum",
    meta = TestNestedTypes,
    getter = _.outerNestedEnum,
    manifest = manifest[com.capnproto.test.TestNestedTypes.NestedEnum],
    isUnion = false
  )

  val innerNestedEnum = new FieldDescriptor[com.capnproto.test.TestNestedTypes.NestedStruct.NestedEnum, TestNestedTypes, TestNestedTypes.type](
    name = "innerNestedEnum",
    meta = TestNestedTypes,
    getter = _.innerNestedEnum,
    manifest = manifest[com.capnproto.test.TestNestedTypes.NestedStruct.NestedEnum],
    isUnion = false
  )
  override val fields: Seq[FieldDescriptor[_, TestNestedTypes, TestNestedTypes.type]] = Seq(nestedStruct, outerNestedEnum, innerNestedEnum)
}

trait TestNestedTypes extends Struct[TestNestedTypes] {
  override type MetaT = TestNestedTypes.type
  override type MetaBuilderT = com.capnproto.test.TestNestedTypes.Builder.type

  override def meta: TestNestedTypes.type = TestNestedTypes
  override def metaBuilder: com.capnproto.test.TestNestedTypes.Builder.type = com.capnproto.test.TestNestedTypes.Builder

  def struct: CapnpStruct

  def nestedStruct: Option[com.capnproto.test.TestNestedTypes.NestedStruct]
  def outerNestedEnum: Option[com.capnproto.test.TestNestedTypes.NestedEnum]
  def innerNestedEnum: Option[com.capnproto.test.TestNestedTypes.NestedStruct.NestedEnum]
}

class TestNestedTypesMutable(override val struct: CapnpStruct) extends TestNestedTypes {
  override def nestedStruct: Option[com.capnproto.test.TestNestedTypes.NestedStruct] = struct.getStruct(0).map(new com.capnproto.test.TestNestedTypes.NestedStructMutable(_))
  override def outerNestedEnum: Option[com.capnproto.test.TestNestedTypes.NestedEnum] = struct.getShort(0).map(id => com.capnproto.test.TestNestedTypes.NestedEnum.findById(id.toInt).getOrElse(com.capnproto.test.TestNestedTypes.NestedEnum.Unknown(id.toShort)))
  override def innerNestedEnum: Option[com.capnproto.test.TestNestedTypes.NestedStruct.NestedEnum] = struct.getShort(1).map(id => com.capnproto.test.TestNestedTypes.NestedStruct.NestedEnum.findById(id.toInt).getOrElse(com.capnproto.test.TestNestedTypes.NestedStruct.NestedEnum.Unknown(id.toShort)))
}

object TestUsing extends MetaStruct[TestUsing] {
  override type Self = TestUsing.type
  override val structName: String = "TestUsing"
  override def create(struct: CapnpStruct): TestUsing = new TestUsingMutable(struct)
  def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestUsing.Builder = {
    val (segment, pointerOffset) = arena.allocate(1)
    val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestUsing.Builder.dataSectionSizeWords, com.capnproto.test.TestUsing.Builder.pointerSectionSizeWords)
    new com.capnproto.test.TestUsing.Builder(struct)
  }

  object Builder extends MetaStructBuilder[com.capnproto.test.TestUsing, com.capnproto.test.TestUsing.Builder] {
    override type Self = com.capnproto.test.TestUsing.Builder.type
    override val structName: String = "TestUsing"
    override val dataSectionSizeWords: Short = 1
    override val pointerSectionSizeWords: Short = 0
    override def create(struct: CapnpStructBuilder): com.capnproto.test.TestUsing.Builder = new com.capnproto.test.TestUsing.Builder(struct)
    override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestUsing.fields
  }
  class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestUsingMutable(struct) with StructBuilder[com.capnproto.test.TestUsing, com.capnproto.test.TestUsing.Builder] {
    override type MetaBuilderT = com.capnproto.test.TestUsing.Builder.type

    override def meta: TestUsing.type = TestUsing
    override def metaBuilder: MetaBuilderT = com.capnproto.test.TestUsing.Builder
    def setOuterNestedEnum(value: com.capnproto.test.TestNestedTypes.NestedEnum): Builder = { struct.setShort(1, value.id.toShort); this }
    def setInnerNestedEnum(value: com.capnproto.test.TestNestedTypes.NestedStruct.NestedEnum): Builder = { struct.setShort(0, value.id.toShort); this }
  }

  val outerNestedEnum = new FieldDescriptor[com.capnproto.test.TestNestedTypes.NestedEnum, TestUsing, TestUsing.type](
    name = "outerNestedEnum",
    meta = TestUsing,
    getter = _.outerNestedEnum,
    manifest = manifest[com.capnproto.test.TestNestedTypes.NestedEnum],
    isUnion = false
  )

  val innerNestedEnum = new FieldDescriptor[com.capnproto.test.TestNestedTypes.NestedStruct.NestedEnum, TestUsing, TestUsing.type](
    name = "innerNestedEnum",
    meta = TestUsing,
    getter = _.innerNestedEnum,
    manifest = manifest[com.capnproto.test.TestNestedTypes.NestedStruct.NestedEnum],
    isUnion = false
  )
  override val fields: Seq[FieldDescriptor[_, TestUsing, TestUsing.type]] = Seq(outerNestedEnum, innerNestedEnum)
}

trait TestUsing extends Struct[TestUsing] {
  override type MetaT = TestUsing.type
  override type MetaBuilderT = com.capnproto.test.TestUsing.Builder.type

  override def meta: TestUsing.type = TestUsing
  override def metaBuilder: com.capnproto.test.TestUsing.Builder.type = com.capnproto.test.TestUsing.Builder

  def struct: CapnpStruct

  def outerNestedEnum: Option[com.capnproto.test.TestNestedTypes.NestedEnum]
  def innerNestedEnum: Option[com.capnproto.test.TestNestedTypes.NestedStruct.NestedEnum]
}

class TestUsingMutable(override val struct: CapnpStruct) extends TestUsing {
  override def outerNestedEnum: Option[com.capnproto.test.TestNestedTypes.NestedEnum] = struct.getShort(1).map(id => com.capnproto.test.TestNestedTypes.NestedEnum.findById(id.toInt).getOrElse(com.capnproto.test.TestNestedTypes.NestedEnum.Unknown(id.toShort)))
  override def innerNestedEnum: Option[com.capnproto.test.TestNestedTypes.NestedStruct.NestedEnum] = struct.getShort(0).map(id => com.capnproto.test.TestNestedTypes.NestedStruct.NestedEnum.findById(id.toInt).getOrElse(com.capnproto.test.TestNestedTypes.NestedStruct.NestedEnum.Unknown(id.toShort)))
}

object TestLists extends MetaStruct[TestLists] {
  override type Self = TestLists.type
  override val structName: String = "TestLists"
  override def create(struct: CapnpStruct): TestLists = new TestListsMutable(struct)
  def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestLists.Builder = {
    val (segment, pointerOffset) = arena.allocate(1)
    val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestLists.Builder.dataSectionSizeWords, com.capnproto.test.TestLists.Builder.pointerSectionSizeWords)
    new com.capnproto.test.TestLists.Builder(struct)
  }

  object Builder extends MetaStructBuilder[com.capnproto.test.TestLists, com.capnproto.test.TestLists.Builder] {
    override type Self = com.capnproto.test.TestLists.Builder.type
    override val structName: String = "TestLists"
    override val dataSectionSizeWords: Short = 0
    override val pointerSectionSizeWords: Short = 10
    override def create(struct: CapnpStructBuilder): com.capnproto.test.TestLists.Builder = new com.capnproto.test.TestLists.Builder(struct)
    override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestLists.fields
  }
  class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestListsMutable(struct) with StructBuilder[com.capnproto.test.TestLists, com.capnproto.test.TestLists.Builder] {
    override type MetaBuilderT = com.capnproto.test.TestLists.Builder.type

    override def meta: TestLists.type = TestLists
    override def metaBuilder: MetaBuilderT = com.capnproto.test.TestLists.Builder
    def initList0(count: Int): Seq[com.capnproto.test.TestLists.Struct0.Builder] = {
      val list = struct.initPointerList(0, count, com.capnproto.test.TestLists.Struct0.Builder)
      Range(0, count).map(i => new com.capnproto.test.TestLists.Struct0.Builder(list.initStruct(i, com.capnproto.test.TestLists.Struct0.Builder)))
    }
    def setList0(buildFn: CapnpArenaBuilder => Seq[com.capnproto.test.TestLists.Struct0.Builder]): Builder = { struct.setStructList(0, com.capnproto.test.TestLists.Struct0.Builder, buildFn(struct.arena).map(_.struct)); this }
    def initList1(count: Int): Seq[com.capnproto.test.TestLists.Struct1.Builder] = {
      val list = struct.initPointerList(1, count, com.capnproto.test.TestLists.Struct1.Builder)
      Range(0, count).map(i => new com.capnproto.test.TestLists.Struct1.Builder(list.initStruct(i, com.capnproto.test.TestLists.Struct1.Builder)))
    }
    def setList1(buildFn: CapnpArenaBuilder => Seq[com.capnproto.test.TestLists.Struct1.Builder]): Builder = { struct.setStructList(1, com.capnproto.test.TestLists.Struct1.Builder, buildFn(struct.arena).map(_.struct)); this }
    def initList8(count: Int): Seq[com.capnproto.test.TestLists.Struct8.Builder] = {
      val list = struct.initPointerList(2, count, com.capnproto.test.TestLists.Struct8.Builder)
      Range(0, count).map(i => new com.capnproto.test.TestLists.Struct8.Builder(list.initStruct(i, com.capnproto.test.TestLists.Struct8.Builder)))
    }
    def setList8(buildFn: CapnpArenaBuilder => Seq[com.capnproto.test.TestLists.Struct8.Builder]): Builder = { struct.setStructList(2, com.capnproto.test.TestLists.Struct8.Builder, buildFn(struct.arena).map(_.struct)); this }
    def initList16(count: Int): Seq[com.capnproto.test.TestLists.Struct16.Builder] = {
      val list = struct.initPointerList(3, count, com.capnproto.test.TestLists.Struct16.Builder)
      Range(0, count).map(i => new com.capnproto.test.TestLists.Struct16.Builder(list.initStruct(i, com.capnproto.test.TestLists.Struct16.Builder)))
    }
    def setList16(buildFn: CapnpArenaBuilder => Seq[com.capnproto.test.TestLists.Struct16.Builder]): Builder = { struct.setStructList(3, com.capnproto.test.TestLists.Struct16.Builder, buildFn(struct.arena).map(_.struct)); this }
    def initList32(count: Int): Seq[com.capnproto.test.TestLists.Struct32.Builder] = {
      val list = struct.initPointerList(4, count, com.capnproto.test.TestLists.Struct32.Builder)
      Range(0, count).map(i => new com.capnproto.test.TestLists.Struct32.Builder(list.initStruct(i, com.capnproto.test.TestLists.Struct32.Builder)))
    }
    def setList32(buildFn: CapnpArenaBuilder => Seq[com.capnproto.test.TestLists.Struct32.Builder]): Builder = { struct.setStructList(4, com.capnproto.test.TestLists.Struct32.Builder, buildFn(struct.arena).map(_.struct)); this }
    def initList64(count: Int): Seq[com.capnproto.test.TestLists.Struct64.Builder] = {
      val list = struct.initPointerList(5, count, com.capnproto.test.TestLists.Struct64.Builder)
      Range(0, count).map(i => new com.capnproto.test.TestLists.Struct64.Builder(list.initStruct(i, com.capnproto.test.TestLists.Struct64.Builder)))
    }
    def setList64(buildFn: CapnpArenaBuilder => Seq[com.capnproto.test.TestLists.Struct64.Builder]): Builder = { struct.setStructList(5, com.capnproto.test.TestLists.Struct64.Builder, buildFn(struct.arena).map(_.struct)); this }
    def initListP(count: Int): Seq[com.capnproto.test.TestLists.StructP.Builder] = {
      val list = struct.initPointerList(6, count, com.capnproto.test.TestLists.StructP.Builder)
      Range(0, count).map(i => new com.capnproto.test.TestLists.StructP.Builder(list.initStruct(i, com.capnproto.test.TestLists.StructP.Builder)))
    }
    def setListP(buildFn: CapnpArenaBuilder => Seq[com.capnproto.test.TestLists.StructP.Builder]): Builder = { struct.setStructList(6, com.capnproto.test.TestLists.StructP.Builder, buildFn(struct.arena).map(_.struct)); this }
  }

  object Struct0 extends MetaStruct[Struct0] {
    override type Self = Struct0.type
    override val structName: String = "Struct0"
    override def create(struct: CapnpStruct): Struct0 = new Struct0Mutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestLists.Struct0.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestLists.Struct0.Builder.dataSectionSizeWords, com.capnproto.test.TestLists.Struct0.Builder.pointerSectionSizeWords)
      new com.capnproto.test.TestLists.Struct0.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.test.TestLists.Struct0, com.capnproto.test.TestLists.Struct0.Builder] {
      override type Self = com.capnproto.test.TestLists.Struct0.Builder.type
      override val structName: String = "Struct0"
      override val dataSectionSizeWords: Short = 0
      override val pointerSectionSizeWords: Short = 0
      override def create(struct: CapnpStructBuilder): com.capnproto.test.TestLists.Struct0.Builder = new com.capnproto.test.TestLists.Struct0.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestLists.Struct0.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestLists.Struct0Mutable(struct) with StructBuilder[com.capnproto.test.TestLists.Struct0, com.capnproto.test.TestLists.Struct0.Builder] {
      override type MetaBuilderT = com.capnproto.test.TestLists.Struct0.Builder.type

      override def meta: Struct0.type = Struct0
      override def metaBuilder: MetaBuilderT = com.capnproto.test.TestLists.Struct0.Builder
      def setF(value: Unit): Builder = { struct.setNone(); this }
    }

    val f = new FieldDescriptor[Unit, Struct0, Struct0.type](
      name = "f",
      meta = Struct0,
      getter = _.f,
      manifest = manifest[Unit],
      isUnion = false
    )
    override val fields: Seq[FieldDescriptor[_, Struct0, Struct0.type]] = Seq(f)
  }

  trait Struct0 extends Struct[Struct0] {
    override type MetaT = Struct0.type
    override type MetaBuilderT = com.capnproto.test.TestLists.Struct0.Builder.type

    override def meta: Struct0.type = Struct0
    override def metaBuilder: com.capnproto.test.TestLists.Struct0.Builder.type = com.capnproto.test.TestLists.Struct0.Builder

    def struct: CapnpStruct

    def f: Option[Unit]
  }

  class Struct0Mutable(override val struct: CapnpStruct) extends Struct0 {
    override def f: Option[Unit] = struct.getNone()
  }

  object Struct1 extends MetaStruct[Struct1] {
    override type Self = Struct1.type
    override val structName: String = "Struct1"
    override def create(struct: CapnpStruct): Struct1 = new Struct1Mutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestLists.Struct1.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestLists.Struct1.Builder.dataSectionSizeWords, com.capnproto.test.TestLists.Struct1.Builder.pointerSectionSizeWords)
      new com.capnproto.test.TestLists.Struct1.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.test.TestLists.Struct1, com.capnproto.test.TestLists.Struct1.Builder] {
      override type Self = com.capnproto.test.TestLists.Struct1.Builder.type
      override val structName: String = "Struct1"
      override val dataSectionSizeWords: Short = 1
      override val pointerSectionSizeWords: Short = 0
      override def create(struct: CapnpStructBuilder): com.capnproto.test.TestLists.Struct1.Builder = new com.capnproto.test.TestLists.Struct1.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestLists.Struct1.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestLists.Struct1Mutable(struct) with StructBuilder[com.capnproto.test.TestLists.Struct1, com.capnproto.test.TestLists.Struct1.Builder] {
      override type MetaBuilderT = com.capnproto.test.TestLists.Struct1.Builder.type

      override def meta: Struct1.type = Struct1
      override def metaBuilder: MetaBuilderT = com.capnproto.test.TestLists.Struct1.Builder
      def setF(value: java.lang.Boolean): Builder = { struct.setBoolean(0, value); this }
    }

    val f = new FieldDescriptor[java.lang.Boolean, Struct1, Struct1.type](
      name = "f",
      meta = Struct1,
      getter = _.f,
      manifest = manifest[java.lang.Boolean],
      isUnion = false
    )
    override val fields: Seq[FieldDescriptor[_, Struct1, Struct1.type]] = Seq(f)
  }

  trait Struct1 extends Struct[Struct1] {
    override type MetaT = Struct1.type
    override type MetaBuilderT = com.capnproto.test.TestLists.Struct1.Builder.type

    override def meta: Struct1.type = Struct1
    override def metaBuilder: com.capnproto.test.TestLists.Struct1.Builder.type = com.capnproto.test.TestLists.Struct1.Builder

    def struct: CapnpStruct

    def f: Option[java.lang.Boolean]
  }

  class Struct1Mutable(override val struct: CapnpStruct) extends Struct1 {
    override def f: Option[java.lang.Boolean] = struct.getBoolean(0)
  }

  object Struct8 extends MetaStruct[Struct8] {
    override type Self = Struct8.type
    override val structName: String = "Struct8"
    override def create(struct: CapnpStruct): Struct8 = new Struct8Mutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestLists.Struct8.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestLists.Struct8.Builder.dataSectionSizeWords, com.capnproto.test.TestLists.Struct8.Builder.pointerSectionSizeWords)
      new com.capnproto.test.TestLists.Struct8.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.test.TestLists.Struct8, com.capnproto.test.TestLists.Struct8.Builder] {
      override type Self = com.capnproto.test.TestLists.Struct8.Builder.type
      override val structName: String = "Struct8"
      override val dataSectionSizeWords: Short = 1
      override val pointerSectionSizeWords: Short = 0
      override def create(struct: CapnpStructBuilder): com.capnproto.test.TestLists.Struct8.Builder = new com.capnproto.test.TestLists.Struct8.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestLists.Struct8.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestLists.Struct8Mutable(struct) with StructBuilder[com.capnproto.test.TestLists.Struct8, com.capnproto.test.TestLists.Struct8.Builder] {
      override type MetaBuilderT = com.capnproto.test.TestLists.Struct8.Builder.type

      override def meta: Struct8.type = Struct8
      override def metaBuilder: MetaBuilderT = com.capnproto.test.TestLists.Struct8.Builder
      def setF(value: java.lang.Byte): Builder = { struct.setByte(0, value); this }
    }

    val f = new FieldDescriptor[java.lang.Byte, Struct8, Struct8.type](
      name = "f",
      meta = Struct8,
      getter = _.f,
      manifest = manifest[java.lang.Byte],
      isUnion = false
    )
    override val fields: Seq[FieldDescriptor[_, Struct8, Struct8.type]] = Seq(f)
  }

  trait Struct8 extends Struct[Struct8] {
    override type MetaT = Struct8.type
    override type MetaBuilderT = com.capnproto.test.TestLists.Struct8.Builder.type

    override def meta: Struct8.type = Struct8
    override def metaBuilder: com.capnproto.test.TestLists.Struct8.Builder.type = com.capnproto.test.TestLists.Struct8.Builder

    def struct: CapnpStruct

    def f: Option[java.lang.Byte]
  }

  class Struct8Mutable(override val struct: CapnpStruct) extends Struct8 {
    override def f: Option[java.lang.Byte] = struct.getByte(0)
  }

  object Struct16 extends MetaStruct[Struct16] {
    override type Self = Struct16.type
    override val structName: String = "Struct16"
    override def create(struct: CapnpStruct): Struct16 = new Struct16Mutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestLists.Struct16.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestLists.Struct16.Builder.dataSectionSizeWords, com.capnproto.test.TestLists.Struct16.Builder.pointerSectionSizeWords)
      new com.capnproto.test.TestLists.Struct16.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.test.TestLists.Struct16, com.capnproto.test.TestLists.Struct16.Builder] {
      override type Self = com.capnproto.test.TestLists.Struct16.Builder.type
      override val structName: String = "Struct16"
      override val dataSectionSizeWords: Short = 1
      override val pointerSectionSizeWords: Short = 0
      override def create(struct: CapnpStructBuilder): com.capnproto.test.TestLists.Struct16.Builder = new com.capnproto.test.TestLists.Struct16.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestLists.Struct16.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestLists.Struct16Mutable(struct) with StructBuilder[com.capnproto.test.TestLists.Struct16, com.capnproto.test.TestLists.Struct16.Builder] {
      override type MetaBuilderT = com.capnproto.test.TestLists.Struct16.Builder.type

      override def meta: Struct16.type = Struct16
      override def metaBuilder: MetaBuilderT = com.capnproto.test.TestLists.Struct16.Builder
      def setF(value: java.lang.Short): Builder = { struct.setShort(0, value); this }
    }

    val f = new FieldDescriptor[java.lang.Short, Struct16, Struct16.type](
      name = "f",
      meta = Struct16,
      getter = _.f,
      manifest = manifest[java.lang.Short],
      isUnion = false
    )
    override val fields: Seq[FieldDescriptor[_, Struct16, Struct16.type]] = Seq(f)
  }

  trait Struct16 extends Struct[Struct16] {
    override type MetaT = Struct16.type
    override type MetaBuilderT = com.capnproto.test.TestLists.Struct16.Builder.type

    override def meta: Struct16.type = Struct16
    override def metaBuilder: com.capnproto.test.TestLists.Struct16.Builder.type = com.capnproto.test.TestLists.Struct16.Builder

    def struct: CapnpStruct

    def f: Option[java.lang.Short]
  }

  class Struct16Mutable(override val struct: CapnpStruct) extends Struct16 {
    override def f: Option[java.lang.Short] = struct.getShort(0)
  }

  object Struct32 extends MetaStruct[Struct32] {
    override type Self = Struct32.type
    override val structName: String = "Struct32"
    override def create(struct: CapnpStruct): Struct32 = new Struct32Mutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestLists.Struct32.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestLists.Struct32.Builder.dataSectionSizeWords, com.capnproto.test.TestLists.Struct32.Builder.pointerSectionSizeWords)
      new com.capnproto.test.TestLists.Struct32.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.test.TestLists.Struct32, com.capnproto.test.TestLists.Struct32.Builder] {
      override type Self = com.capnproto.test.TestLists.Struct32.Builder.type
      override val structName: String = "Struct32"
      override val dataSectionSizeWords: Short = 1
      override val pointerSectionSizeWords: Short = 0
      override def create(struct: CapnpStructBuilder): com.capnproto.test.TestLists.Struct32.Builder = new com.capnproto.test.TestLists.Struct32.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestLists.Struct32.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestLists.Struct32Mutable(struct) with StructBuilder[com.capnproto.test.TestLists.Struct32, com.capnproto.test.TestLists.Struct32.Builder] {
      override type MetaBuilderT = com.capnproto.test.TestLists.Struct32.Builder.type

      override def meta: Struct32.type = Struct32
      override def metaBuilder: MetaBuilderT = com.capnproto.test.TestLists.Struct32.Builder
      def setF(value: java.lang.Integer): Builder = { struct.setInt(0, value); this }
    }

    val f = new FieldDescriptor[java.lang.Integer, Struct32, Struct32.type](
      name = "f",
      meta = Struct32,
      getter = _.f,
      manifest = manifest[java.lang.Integer],
      isUnion = false
    )
    override val fields: Seq[FieldDescriptor[_, Struct32, Struct32.type]] = Seq(f)
  }

  trait Struct32 extends Struct[Struct32] {
    override type MetaT = Struct32.type
    override type MetaBuilderT = com.capnproto.test.TestLists.Struct32.Builder.type

    override def meta: Struct32.type = Struct32
    override def metaBuilder: com.capnproto.test.TestLists.Struct32.Builder.type = com.capnproto.test.TestLists.Struct32.Builder

    def struct: CapnpStruct

    def f: Option[java.lang.Integer]
  }

  class Struct32Mutable(override val struct: CapnpStruct) extends Struct32 {
    override def f: Option[java.lang.Integer] = struct.getInt(0)
  }

  object Struct64 extends MetaStruct[Struct64] {
    override type Self = Struct64.type
    override val structName: String = "Struct64"
    override def create(struct: CapnpStruct): Struct64 = new Struct64Mutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestLists.Struct64.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestLists.Struct64.Builder.dataSectionSizeWords, com.capnproto.test.TestLists.Struct64.Builder.pointerSectionSizeWords)
      new com.capnproto.test.TestLists.Struct64.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.test.TestLists.Struct64, com.capnproto.test.TestLists.Struct64.Builder] {
      override type Self = com.capnproto.test.TestLists.Struct64.Builder.type
      override val structName: String = "Struct64"
      override val dataSectionSizeWords: Short = 1
      override val pointerSectionSizeWords: Short = 0
      override def create(struct: CapnpStructBuilder): com.capnproto.test.TestLists.Struct64.Builder = new com.capnproto.test.TestLists.Struct64.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestLists.Struct64.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestLists.Struct64Mutable(struct) with StructBuilder[com.capnproto.test.TestLists.Struct64, com.capnproto.test.TestLists.Struct64.Builder] {
      override type MetaBuilderT = com.capnproto.test.TestLists.Struct64.Builder.type

      override def meta: Struct64.type = Struct64
      override def metaBuilder: MetaBuilderT = com.capnproto.test.TestLists.Struct64.Builder
      def setF(value: java.lang.Long): Builder = { struct.setLong(0, value); this }
    }

    val f = new FieldDescriptor[java.lang.Long, Struct64, Struct64.type](
      name = "f",
      meta = Struct64,
      getter = _.f,
      manifest = manifest[java.lang.Long],
      isUnion = false
    )
    override val fields: Seq[FieldDescriptor[_, Struct64, Struct64.type]] = Seq(f)
  }

  trait Struct64 extends Struct[Struct64] {
    override type MetaT = Struct64.type
    override type MetaBuilderT = com.capnproto.test.TestLists.Struct64.Builder.type

    override def meta: Struct64.type = Struct64
    override def metaBuilder: com.capnproto.test.TestLists.Struct64.Builder.type = com.capnproto.test.TestLists.Struct64.Builder

    def struct: CapnpStruct

    def f: Option[java.lang.Long]
  }

  class Struct64Mutable(override val struct: CapnpStruct) extends Struct64 {
    override def f: Option[java.lang.Long] = struct.getLong(0)
  }

  object StructP extends MetaStruct[StructP] {
    override type Self = StructP.type
    override val structName: String = "StructP"
    override def create(struct: CapnpStruct): StructP = new StructPMutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestLists.StructP.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestLists.StructP.Builder.dataSectionSizeWords, com.capnproto.test.TestLists.StructP.Builder.pointerSectionSizeWords)
      new com.capnproto.test.TestLists.StructP.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.test.TestLists.StructP, com.capnproto.test.TestLists.StructP.Builder] {
      override type Self = com.capnproto.test.TestLists.StructP.Builder.type
      override val structName: String = "StructP"
      override val dataSectionSizeWords: Short = 0
      override val pointerSectionSizeWords: Short = 1
      override def create(struct: CapnpStructBuilder): com.capnproto.test.TestLists.StructP.Builder = new com.capnproto.test.TestLists.StructP.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestLists.StructP.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestLists.StructPMutable(struct) with StructBuilder[com.capnproto.test.TestLists.StructP, com.capnproto.test.TestLists.StructP.Builder] {
      override type MetaBuilderT = com.capnproto.test.TestLists.StructP.Builder.type

      override def meta: StructP.type = StructP
      override def metaBuilder: MetaBuilderT = com.capnproto.test.TestLists.StructP.Builder
      def setF(value: String): Builder = { struct.setString(0, value); this }
    }

    val f = new FieldDescriptor[String, StructP, StructP.type](
      name = "f",
      meta = StructP,
      getter = _.f,
      manifest = manifest[String],
      isUnion = false
    )
    override val fields: Seq[FieldDescriptor[_, StructP, StructP.type]] = Seq(f)
  }

  trait StructP extends Struct[StructP] {
    override type MetaT = StructP.type
    override type MetaBuilderT = com.capnproto.test.TestLists.StructP.Builder.type

    override def meta: StructP.type = StructP
    override def metaBuilder: com.capnproto.test.TestLists.StructP.Builder.type = com.capnproto.test.TestLists.StructP.Builder

    def struct: CapnpStruct

    def f: Option[String]
  }

  class StructPMutable(override val struct: CapnpStruct) extends StructP {
    override def f: Option[String] = struct.getString(0)
  }

  object Struct0c extends MetaStruct[Struct0c] {
    override type Self = Struct0c.type
    override val structName: String = "Struct0c"
    override def create(struct: CapnpStruct): Struct0c = new Struct0cMutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestLists.Struct0c.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestLists.Struct0c.Builder.dataSectionSizeWords, com.capnproto.test.TestLists.Struct0c.Builder.pointerSectionSizeWords)
      new com.capnproto.test.TestLists.Struct0c.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.test.TestLists.Struct0c, com.capnproto.test.TestLists.Struct0c.Builder] {
      override type Self = com.capnproto.test.TestLists.Struct0c.Builder.type
      override val structName: String = "Struct0c"
      override val dataSectionSizeWords: Short = 0
      override val pointerSectionSizeWords: Short = 1
      override def create(struct: CapnpStructBuilder): com.capnproto.test.TestLists.Struct0c.Builder = new com.capnproto.test.TestLists.Struct0c.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestLists.Struct0c.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestLists.Struct0cMutable(struct) with StructBuilder[com.capnproto.test.TestLists.Struct0c, com.capnproto.test.TestLists.Struct0c.Builder] {
      override type MetaBuilderT = com.capnproto.test.TestLists.Struct0c.Builder.type

      override def meta: Struct0c.type = Struct0c
      override def metaBuilder: MetaBuilderT = com.capnproto.test.TestLists.Struct0c.Builder
      def setF(value: Unit): Builder = { struct.setNone(); this }
      def setPad(value: String): Builder = { struct.setString(0, value); this }
    }

    val f = new FieldDescriptor[Unit, Struct0c, Struct0c.type](
      name = "f",
      meta = Struct0c,
      getter = _.f,
      manifest = manifest[Unit],
      isUnion = false
    )

    val pad = new FieldDescriptor[String, Struct0c, Struct0c.type](
      name = "pad",
      meta = Struct0c,
      getter = _.pad,
      manifest = manifest[String],
      isUnion = false
    )
    override val fields: Seq[FieldDescriptor[_, Struct0c, Struct0c.type]] = Seq(f, pad)
  }

  trait Struct0c extends Struct[Struct0c] {
    override type MetaT = Struct0c.type
    override type MetaBuilderT = com.capnproto.test.TestLists.Struct0c.Builder.type

    override def meta: Struct0c.type = Struct0c
    override def metaBuilder: com.capnproto.test.TestLists.Struct0c.Builder.type = com.capnproto.test.TestLists.Struct0c.Builder

    def struct: CapnpStruct

    def f: Option[Unit]
    def pad: Option[String]
  }

  class Struct0cMutable(override val struct: CapnpStruct) extends Struct0c {
    override def f: Option[Unit] = struct.getNone()
    override def pad: Option[String] = struct.getString(0)
  }

  object Struct1c extends MetaStruct[Struct1c] {
    override type Self = Struct1c.type
    override val structName: String = "Struct1c"
    override def create(struct: CapnpStruct): Struct1c = new Struct1cMutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestLists.Struct1c.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestLists.Struct1c.Builder.dataSectionSizeWords, com.capnproto.test.TestLists.Struct1c.Builder.pointerSectionSizeWords)
      new com.capnproto.test.TestLists.Struct1c.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.test.TestLists.Struct1c, com.capnproto.test.TestLists.Struct1c.Builder] {
      override type Self = com.capnproto.test.TestLists.Struct1c.Builder.type
      override val structName: String = "Struct1c"
      override val dataSectionSizeWords: Short = 1
      override val pointerSectionSizeWords: Short = 1
      override def create(struct: CapnpStructBuilder): com.capnproto.test.TestLists.Struct1c.Builder = new com.capnproto.test.TestLists.Struct1c.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestLists.Struct1c.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestLists.Struct1cMutable(struct) with StructBuilder[com.capnproto.test.TestLists.Struct1c, com.capnproto.test.TestLists.Struct1c.Builder] {
      override type MetaBuilderT = com.capnproto.test.TestLists.Struct1c.Builder.type

      override def meta: Struct1c.type = Struct1c
      override def metaBuilder: MetaBuilderT = com.capnproto.test.TestLists.Struct1c.Builder
      def setF(value: java.lang.Boolean): Builder = { struct.setBoolean(0, value); this }
      def setPad(value: String): Builder = { struct.setString(0, value); this }
    }

    val f = new FieldDescriptor[java.lang.Boolean, Struct1c, Struct1c.type](
      name = "f",
      meta = Struct1c,
      getter = _.f,
      manifest = manifest[java.lang.Boolean],
      isUnion = false
    )

    val pad = new FieldDescriptor[String, Struct1c, Struct1c.type](
      name = "pad",
      meta = Struct1c,
      getter = _.pad,
      manifest = manifest[String],
      isUnion = false
    )
    override val fields: Seq[FieldDescriptor[_, Struct1c, Struct1c.type]] = Seq(f, pad)
  }

  trait Struct1c extends Struct[Struct1c] {
    override type MetaT = Struct1c.type
    override type MetaBuilderT = com.capnproto.test.TestLists.Struct1c.Builder.type

    override def meta: Struct1c.type = Struct1c
    override def metaBuilder: com.capnproto.test.TestLists.Struct1c.Builder.type = com.capnproto.test.TestLists.Struct1c.Builder

    def struct: CapnpStruct

    def f: Option[java.lang.Boolean]
    def pad: Option[String]
  }

  class Struct1cMutable(override val struct: CapnpStruct) extends Struct1c {
    override def f: Option[java.lang.Boolean] = struct.getBoolean(0)
    override def pad: Option[String] = struct.getString(0)
  }

  object Struct8c extends MetaStruct[Struct8c] {
    override type Self = Struct8c.type
    override val structName: String = "Struct8c"
    override def create(struct: CapnpStruct): Struct8c = new Struct8cMutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestLists.Struct8c.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestLists.Struct8c.Builder.dataSectionSizeWords, com.capnproto.test.TestLists.Struct8c.Builder.pointerSectionSizeWords)
      new com.capnproto.test.TestLists.Struct8c.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.test.TestLists.Struct8c, com.capnproto.test.TestLists.Struct8c.Builder] {
      override type Self = com.capnproto.test.TestLists.Struct8c.Builder.type
      override val structName: String = "Struct8c"
      override val dataSectionSizeWords: Short = 1
      override val pointerSectionSizeWords: Short = 1
      override def create(struct: CapnpStructBuilder): com.capnproto.test.TestLists.Struct8c.Builder = new com.capnproto.test.TestLists.Struct8c.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestLists.Struct8c.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestLists.Struct8cMutable(struct) with StructBuilder[com.capnproto.test.TestLists.Struct8c, com.capnproto.test.TestLists.Struct8c.Builder] {
      override type MetaBuilderT = com.capnproto.test.TestLists.Struct8c.Builder.type

      override def meta: Struct8c.type = Struct8c
      override def metaBuilder: MetaBuilderT = com.capnproto.test.TestLists.Struct8c.Builder
      def setF(value: java.lang.Byte): Builder = { struct.setByte(0, value); this }
      def setPad(value: String): Builder = { struct.setString(0, value); this }
    }

    val f = new FieldDescriptor[java.lang.Byte, Struct8c, Struct8c.type](
      name = "f",
      meta = Struct8c,
      getter = _.f,
      manifest = manifest[java.lang.Byte],
      isUnion = false
    )

    val pad = new FieldDescriptor[String, Struct8c, Struct8c.type](
      name = "pad",
      meta = Struct8c,
      getter = _.pad,
      manifest = manifest[String],
      isUnion = false
    )
    override val fields: Seq[FieldDescriptor[_, Struct8c, Struct8c.type]] = Seq(f, pad)
  }

  trait Struct8c extends Struct[Struct8c] {
    override type MetaT = Struct8c.type
    override type MetaBuilderT = com.capnproto.test.TestLists.Struct8c.Builder.type

    override def meta: Struct8c.type = Struct8c
    override def metaBuilder: com.capnproto.test.TestLists.Struct8c.Builder.type = com.capnproto.test.TestLists.Struct8c.Builder

    def struct: CapnpStruct

    def f: Option[java.lang.Byte]
    def pad: Option[String]
  }

  class Struct8cMutable(override val struct: CapnpStruct) extends Struct8c {
    override def f: Option[java.lang.Byte] = struct.getByte(0)
    override def pad: Option[String] = struct.getString(0)
  }

  object Struct16c extends MetaStruct[Struct16c] {
    override type Self = Struct16c.type
    override val structName: String = "Struct16c"
    override def create(struct: CapnpStruct): Struct16c = new Struct16cMutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestLists.Struct16c.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestLists.Struct16c.Builder.dataSectionSizeWords, com.capnproto.test.TestLists.Struct16c.Builder.pointerSectionSizeWords)
      new com.capnproto.test.TestLists.Struct16c.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.test.TestLists.Struct16c, com.capnproto.test.TestLists.Struct16c.Builder] {
      override type Self = com.capnproto.test.TestLists.Struct16c.Builder.type
      override val structName: String = "Struct16c"
      override val dataSectionSizeWords: Short = 1
      override val pointerSectionSizeWords: Short = 1
      override def create(struct: CapnpStructBuilder): com.capnproto.test.TestLists.Struct16c.Builder = new com.capnproto.test.TestLists.Struct16c.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestLists.Struct16c.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestLists.Struct16cMutable(struct) with StructBuilder[com.capnproto.test.TestLists.Struct16c, com.capnproto.test.TestLists.Struct16c.Builder] {
      override type MetaBuilderT = com.capnproto.test.TestLists.Struct16c.Builder.type

      override def meta: Struct16c.type = Struct16c
      override def metaBuilder: MetaBuilderT = com.capnproto.test.TestLists.Struct16c.Builder
      def setF(value: java.lang.Short): Builder = { struct.setShort(0, value); this }
      def setPad(value: String): Builder = { struct.setString(0, value); this }
    }

    val f = new FieldDescriptor[java.lang.Short, Struct16c, Struct16c.type](
      name = "f",
      meta = Struct16c,
      getter = _.f,
      manifest = manifest[java.lang.Short],
      isUnion = false
    )

    val pad = new FieldDescriptor[String, Struct16c, Struct16c.type](
      name = "pad",
      meta = Struct16c,
      getter = _.pad,
      manifest = manifest[String],
      isUnion = false
    )
    override val fields: Seq[FieldDescriptor[_, Struct16c, Struct16c.type]] = Seq(f, pad)
  }

  trait Struct16c extends Struct[Struct16c] {
    override type MetaT = Struct16c.type
    override type MetaBuilderT = com.capnproto.test.TestLists.Struct16c.Builder.type

    override def meta: Struct16c.type = Struct16c
    override def metaBuilder: com.capnproto.test.TestLists.Struct16c.Builder.type = com.capnproto.test.TestLists.Struct16c.Builder

    def struct: CapnpStruct

    def f: Option[java.lang.Short]
    def pad: Option[String]
  }

  class Struct16cMutable(override val struct: CapnpStruct) extends Struct16c {
    override def f: Option[java.lang.Short] = struct.getShort(0)
    override def pad: Option[String] = struct.getString(0)
  }

  object Struct32c extends MetaStruct[Struct32c] {
    override type Self = Struct32c.type
    override val structName: String = "Struct32c"
    override def create(struct: CapnpStruct): Struct32c = new Struct32cMutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestLists.Struct32c.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestLists.Struct32c.Builder.dataSectionSizeWords, com.capnproto.test.TestLists.Struct32c.Builder.pointerSectionSizeWords)
      new com.capnproto.test.TestLists.Struct32c.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.test.TestLists.Struct32c, com.capnproto.test.TestLists.Struct32c.Builder] {
      override type Self = com.capnproto.test.TestLists.Struct32c.Builder.type
      override val structName: String = "Struct32c"
      override val dataSectionSizeWords: Short = 1
      override val pointerSectionSizeWords: Short = 1
      override def create(struct: CapnpStructBuilder): com.capnproto.test.TestLists.Struct32c.Builder = new com.capnproto.test.TestLists.Struct32c.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestLists.Struct32c.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestLists.Struct32cMutable(struct) with StructBuilder[com.capnproto.test.TestLists.Struct32c, com.capnproto.test.TestLists.Struct32c.Builder] {
      override type MetaBuilderT = com.capnproto.test.TestLists.Struct32c.Builder.type

      override def meta: Struct32c.type = Struct32c
      override def metaBuilder: MetaBuilderT = com.capnproto.test.TestLists.Struct32c.Builder
      def setF(value: java.lang.Integer): Builder = { struct.setInt(0, value); this }
      def setPad(value: String): Builder = { struct.setString(0, value); this }
    }

    val f = new FieldDescriptor[java.lang.Integer, Struct32c, Struct32c.type](
      name = "f",
      meta = Struct32c,
      getter = _.f,
      manifest = manifest[java.lang.Integer],
      isUnion = false
    )

    val pad = new FieldDescriptor[String, Struct32c, Struct32c.type](
      name = "pad",
      meta = Struct32c,
      getter = _.pad,
      manifest = manifest[String],
      isUnion = false
    )
    override val fields: Seq[FieldDescriptor[_, Struct32c, Struct32c.type]] = Seq(f, pad)
  }

  trait Struct32c extends Struct[Struct32c] {
    override type MetaT = Struct32c.type
    override type MetaBuilderT = com.capnproto.test.TestLists.Struct32c.Builder.type

    override def meta: Struct32c.type = Struct32c
    override def metaBuilder: com.capnproto.test.TestLists.Struct32c.Builder.type = com.capnproto.test.TestLists.Struct32c.Builder

    def struct: CapnpStruct

    def f: Option[java.lang.Integer]
    def pad: Option[String]
  }

  class Struct32cMutable(override val struct: CapnpStruct) extends Struct32c {
    override def f: Option[java.lang.Integer] = struct.getInt(0)
    override def pad: Option[String] = struct.getString(0)
  }

  object Struct64c extends MetaStruct[Struct64c] {
    override type Self = Struct64c.type
    override val structName: String = "Struct64c"
    override def create(struct: CapnpStruct): Struct64c = new Struct64cMutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestLists.Struct64c.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestLists.Struct64c.Builder.dataSectionSizeWords, com.capnproto.test.TestLists.Struct64c.Builder.pointerSectionSizeWords)
      new com.capnproto.test.TestLists.Struct64c.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.test.TestLists.Struct64c, com.capnproto.test.TestLists.Struct64c.Builder] {
      override type Self = com.capnproto.test.TestLists.Struct64c.Builder.type
      override val structName: String = "Struct64c"
      override val dataSectionSizeWords: Short = 1
      override val pointerSectionSizeWords: Short = 1
      override def create(struct: CapnpStructBuilder): com.capnproto.test.TestLists.Struct64c.Builder = new com.capnproto.test.TestLists.Struct64c.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestLists.Struct64c.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestLists.Struct64cMutable(struct) with StructBuilder[com.capnproto.test.TestLists.Struct64c, com.capnproto.test.TestLists.Struct64c.Builder] {
      override type MetaBuilderT = com.capnproto.test.TestLists.Struct64c.Builder.type

      override def meta: Struct64c.type = Struct64c
      override def metaBuilder: MetaBuilderT = com.capnproto.test.TestLists.Struct64c.Builder
      def setF(value: java.lang.Long): Builder = { struct.setLong(0, value); this }
      def setPad(value: String): Builder = { struct.setString(0, value); this }
    }

    val f = new FieldDescriptor[java.lang.Long, Struct64c, Struct64c.type](
      name = "f",
      meta = Struct64c,
      getter = _.f,
      manifest = manifest[java.lang.Long],
      isUnion = false
    )

    val pad = new FieldDescriptor[String, Struct64c, Struct64c.type](
      name = "pad",
      meta = Struct64c,
      getter = _.pad,
      manifest = manifest[String],
      isUnion = false
    )
    override val fields: Seq[FieldDescriptor[_, Struct64c, Struct64c.type]] = Seq(f, pad)
  }

  trait Struct64c extends Struct[Struct64c] {
    override type MetaT = Struct64c.type
    override type MetaBuilderT = com.capnproto.test.TestLists.Struct64c.Builder.type

    override def meta: Struct64c.type = Struct64c
    override def metaBuilder: com.capnproto.test.TestLists.Struct64c.Builder.type = com.capnproto.test.TestLists.Struct64c.Builder

    def struct: CapnpStruct

    def f: Option[java.lang.Long]
    def pad: Option[String]
  }

  class Struct64cMutable(override val struct: CapnpStruct) extends Struct64c {
    override def f: Option[java.lang.Long] = struct.getLong(0)
    override def pad: Option[String] = struct.getString(0)
  }

  object StructPc extends MetaStruct[StructPc] {
    override type Self = StructPc.type
    override val structName: String = "StructPc"
    override def create(struct: CapnpStruct): StructPc = new StructPcMutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestLists.StructPc.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestLists.StructPc.Builder.dataSectionSizeWords, com.capnproto.test.TestLists.StructPc.Builder.pointerSectionSizeWords)
      new com.capnproto.test.TestLists.StructPc.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.test.TestLists.StructPc, com.capnproto.test.TestLists.StructPc.Builder] {
      override type Self = com.capnproto.test.TestLists.StructPc.Builder.type
      override val structName: String = "StructPc"
      override val dataSectionSizeWords: Short = 1
      override val pointerSectionSizeWords: Short = 1
      override def create(struct: CapnpStructBuilder): com.capnproto.test.TestLists.StructPc.Builder = new com.capnproto.test.TestLists.StructPc.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestLists.StructPc.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestLists.StructPcMutable(struct) with StructBuilder[com.capnproto.test.TestLists.StructPc, com.capnproto.test.TestLists.StructPc.Builder] {
      override type MetaBuilderT = com.capnproto.test.TestLists.StructPc.Builder.type

      override def meta: StructPc.type = StructPc
      override def metaBuilder: MetaBuilderT = com.capnproto.test.TestLists.StructPc.Builder
      def setF(value: String): Builder = { struct.setString(0, value); this }
      def setPad(value: java.lang.Long): Builder = { struct.setLong(0, value); this }
    }

    val f = new FieldDescriptor[String, StructPc, StructPc.type](
      name = "f",
      meta = StructPc,
      getter = _.f,
      manifest = manifest[String],
      isUnion = false
    )

    val pad = new FieldDescriptor[java.lang.Long, StructPc, StructPc.type](
      name = "pad",
      meta = StructPc,
      getter = _.pad,
      manifest = manifest[java.lang.Long],
      isUnion = false
    )
    override val fields: Seq[FieldDescriptor[_, StructPc, StructPc.type]] = Seq(f, pad)
  }

  trait StructPc extends Struct[StructPc] {
    override type MetaT = StructPc.type
    override type MetaBuilderT = com.capnproto.test.TestLists.StructPc.Builder.type

    override def meta: StructPc.type = StructPc
    override def metaBuilder: com.capnproto.test.TestLists.StructPc.Builder.type = com.capnproto.test.TestLists.StructPc.Builder

    def struct: CapnpStruct

    def f: Option[String]
    def pad: Option[java.lang.Long]
  }

  class StructPcMutable(override val struct: CapnpStruct) extends StructPc {
    override def f: Option[String] = struct.getString(0)
    override def pad: Option[java.lang.Long] = struct.getLong(0)
  }
  val list0 = new FieldDescriptor[Seq[com.capnproto.test.TestLists.Struct0], TestLists, TestLists.type](
    name = "list0",
    meta = TestLists,
    getter = x => Some(x.list0),
    manifest = manifest[Seq[com.capnproto.test.TestLists.Struct0]],
    isUnion = false
  )

  val list1 = new FieldDescriptor[Seq[com.capnproto.test.TestLists.Struct1], TestLists, TestLists.type](
    name = "list1",
    meta = TestLists,
    getter = x => Some(x.list1),
    manifest = manifest[Seq[com.capnproto.test.TestLists.Struct1]],
    isUnion = false
  )

  val list8 = new FieldDescriptor[Seq[com.capnproto.test.TestLists.Struct8], TestLists, TestLists.type](
    name = "list8",
    meta = TestLists,
    getter = x => Some(x.list8),
    manifest = manifest[Seq[com.capnproto.test.TestLists.Struct8]],
    isUnion = false
  )

  val list16 = new FieldDescriptor[Seq[com.capnproto.test.TestLists.Struct16], TestLists, TestLists.type](
    name = "list16",
    meta = TestLists,
    getter = x => Some(x.list16),
    manifest = manifest[Seq[com.capnproto.test.TestLists.Struct16]],
    isUnion = false
  )

  val list32 = new FieldDescriptor[Seq[com.capnproto.test.TestLists.Struct32], TestLists, TestLists.type](
    name = "list32",
    meta = TestLists,
    getter = x => Some(x.list32),
    manifest = manifest[Seq[com.capnproto.test.TestLists.Struct32]],
    isUnion = false
  )

  val list64 = new FieldDescriptor[Seq[com.capnproto.test.TestLists.Struct64], TestLists, TestLists.type](
    name = "list64",
    meta = TestLists,
    getter = x => Some(x.list64),
    manifest = manifest[Seq[com.capnproto.test.TestLists.Struct64]],
    isUnion = false
  )

  val listP = new FieldDescriptor[Seq[com.capnproto.test.TestLists.StructP], TestLists, TestLists.type](
    name = "listP",
    meta = TestLists,
    getter = x => Some(x.listP),
    manifest = manifest[Seq[com.capnproto.test.TestLists.StructP]],
    isUnion = false
  )

  val int32ListList = new FieldDescriptor[Seq[Seq[java.lang.Integer]], TestLists, TestLists.type](
    name = "int32ListList",
    meta = TestLists,
    getter = x => Some(x.int32ListList),
    manifest = manifest[Seq[Seq[java.lang.Integer]]],
    isUnion = false
  )

  val textListList = new FieldDescriptor[Seq[Seq[String]], TestLists, TestLists.type](
    name = "textListList",
    meta = TestLists,
    getter = x => Some(x.textListList),
    manifest = manifest[Seq[Seq[String]]],
    isUnion = false
  )

  val structListList = new FieldDescriptor[Seq[Seq[com.capnproto.test.TestAllTypes]], TestLists, TestLists.type](
    name = "structListList",
    meta = TestLists,
    getter = x => Some(x.structListList),
    manifest = manifest[Seq[Seq[com.capnproto.test.TestAllTypes]]],
    isUnion = false
  )
  override val fields: Seq[FieldDescriptor[_, TestLists, TestLists.type]] = Seq(list0, list1, list8, list16, list32, list64, listP, int32ListList, textListList, structListList)
}

trait TestLists extends Struct[TestLists] {
  override type MetaT = TestLists.type
  override type MetaBuilderT = com.capnproto.test.TestLists.Builder.type

  override def meta: TestLists.type = TestLists
  override def metaBuilder: com.capnproto.test.TestLists.Builder.type = com.capnproto.test.TestLists.Builder

  def struct: CapnpStruct

  def list0: Seq[com.capnproto.test.TestLists.Struct0]
  def list1: Seq[com.capnproto.test.TestLists.Struct1]
  def list8: Seq[com.capnproto.test.TestLists.Struct8]
  def list16: Seq[com.capnproto.test.TestLists.Struct16]
  def list32: Seq[com.capnproto.test.TestLists.Struct32]
  def list64: Seq[com.capnproto.test.TestLists.Struct64]
  def listP: Seq[com.capnproto.test.TestLists.StructP]
  def int32ListList: Seq[Seq[java.lang.Integer]]
  def textListList: Seq[Seq[String]]
  def structListList: Seq[Seq[com.capnproto.test.TestAllTypes]]
}

class TestListsMutable(override val struct: CapnpStruct) extends TestLists {
  override def list0: Seq[com.capnproto.test.TestLists.Struct0] = struct.getStructList(0).map(new com.capnproto.test.TestLists.Struct0Mutable(_))
  override def list1: Seq[com.capnproto.test.TestLists.Struct1] = struct.getStructList(1).map(new com.capnproto.test.TestLists.Struct1Mutable(_))
  override def list8: Seq[com.capnproto.test.TestLists.Struct8] = struct.getStructList(2).map(new com.capnproto.test.TestLists.Struct8Mutable(_))
  override def list16: Seq[com.capnproto.test.TestLists.Struct16] = struct.getStructList(3).map(new com.capnproto.test.TestLists.Struct16Mutable(_))
  override def list32: Seq[com.capnproto.test.TestLists.Struct32] = struct.getStructList(4).map(new com.capnproto.test.TestLists.Struct32Mutable(_))
  override def list64: Seq[com.capnproto.test.TestLists.Struct64] = struct.getStructList(5).map(new com.capnproto.test.TestLists.Struct64Mutable(_))
  override def listP: Seq[com.capnproto.test.TestLists.StructP] = struct.getStructList(6).map(new com.capnproto.test.TestLists.StructPMutable(_))
  override def int32ListList: Seq[Seq[java.lang.Integer]] = struct.getListList(7).map(list => list.toSeq(list.getInt))
  override def textListList: Seq[Seq[String]] = struct.getListList(8).map(list => list.toSeq(list.getString))
  override def structListList: Seq[Seq[com.capnproto.test.TestAllTypes]] = struct.getListList(9).map(list => list.toStructSeq.map(new com.capnproto.test.TestAllTypesMutable(_)))
}

object TestFieldZeroIsBit extends MetaStruct[TestFieldZeroIsBit] {
  override type Self = TestFieldZeroIsBit.type
  override val structName: String = "TestFieldZeroIsBit"
  override def create(struct: CapnpStruct): TestFieldZeroIsBit = new TestFieldZeroIsBitMutable(struct)
  def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestFieldZeroIsBit.Builder = {
    val (segment, pointerOffset) = arena.allocate(1)
    val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestFieldZeroIsBit.Builder.dataSectionSizeWords, com.capnproto.test.TestFieldZeroIsBit.Builder.pointerSectionSizeWords)
    new com.capnproto.test.TestFieldZeroIsBit.Builder(struct)
  }

  object Builder extends MetaStructBuilder[com.capnproto.test.TestFieldZeroIsBit, com.capnproto.test.TestFieldZeroIsBit.Builder] {
    override type Self = com.capnproto.test.TestFieldZeroIsBit.Builder.type
    override val structName: String = "TestFieldZeroIsBit"
    override val dataSectionSizeWords: Short = 1
    override val pointerSectionSizeWords: Short = 0
    override def create(struct: CapnpStructBuilder): com.capnproto.test.TestFieldZeroIsBit.Builder = new com.capnproto.test.TestFieldZeroIsBit.Builder(struct)
    override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestFieldZeroIsBit.fields
  }
  class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestFieldZeroIsBitMutable(struct) with StructBuilder[com.capnproto.test.TestFieldZeroIsBit, com.capnproto.test.TestFieldZeroIsBit.Builder] {
    override type MetaBuilderT = com.capnproto.test.TestFieldZeroIsBit.Builder.type

    override def meta: TestFieldZeroIsBit.type = TestFieldZeroIsBit
    override def metaBuilder: MetaBuilderT = com.capnproto.test.TestFieldZeroIsBit.Builder
    def setBit(value: java.lang.Boolean): Builder = { struct.setBoolean(0, value); this }
    def setSecondBit(value: java.lang.Boolean): Builder = { struct.setBoolean(1, value); this }
    def setThirdField(value: java.lang.Byte): Builder = { struct.setByte(1, value); this }
  }

  val bit = new FieldDescriptor[java.lang.Boolean, TestFieldZeroIsBit, TestFieldZeroIsBit.type](
    name = "bit",
    meta = TestFieldZeroIsBit,
    getter = _.bit,
    manifest = manifest[java.lang.Boolean],
    isUnion = false
  )

  val secondBit = new FieldDescriptor[java.lang.Boolean, TestFieldZeroIsBit, TestFieldZeroIsBit.type](
    name = "secondBit",
    meta = TestFieldZeroIsBit,
    getter = _.secondBit,
    manifest = manifest[java.lang.Boolean],
    isUnion = false
  )

  val thirdField = new FieldDescriptor[java.lang.Byte, TestFieldZeroIsBit, TestFieldZeroIsBit.type](
    name = "thirdField",
    meta = TestFieldZeroIsBit,
    getter = _.thirdField,
    manifest = manifest[java.lang.Byte],
    isUnion = false
  )
  override val fields: Seq[FieldDescriptor[_, TestFieldZeroIsBit, TestFieldZeroIsBit.type]] = Seq(bit, secondBit, thirdField)
}

trait TestFieldZeroIsBit extends Struct[TestFieldZeroIsBit] {
  override type MetaT = TestFieldZeroIsBit.type
  override type MetaBuilderT = com.capnproto.test.TestFieldZeroIsBit.Builder.type

  override def meta: TestFieldZeroIsBit.type = TestFieldZeroIsBit
  override def metaBuilder: com.capnproto.test.TestFieldZeroIsBit.Builder.type = com.capnproto.test.TestFieldZeroIsBit.Builder

  def struct: CapnpStruct

  def bit: Option[java.lang.Boolean]
  def secondBit: Option[java.lang.Boolean]
  def thirdField: Option[java.lang.Byte]
}

class TestFieldZeroIsBitMutable(override val struct: CapnpStruct) extends TestFieldZeroIsBit {
  override def bit: Option[java.lang.Boolean] = struct.getBoolean(0)
  override def secondBit: Option[java.lang.Boolean] = struct.getBoolean(1)
  override def thirdField: Option[java.lang.Byte] = struct.getByte(1)
}

object TestListDefaults extends MetaStruct[TestListDefaults] {
  override type Self = TestListDefaults.type
  override val structName: String = "TestListDefaults"
  override def create(struct: CapnpStruct): TestListDefaults = new TestListDefaultsMutable(struct)
  def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestListDefaults.Builder = {
    val (segment, pointerOffset) = arena.allocate(1)
    val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestListDefaults.Builder.dataSectionSizeWords, com.capnproto.test.TestListDefaults.Builder.pointerSectionSizeWords)
    new com.capnproto.test.TestListDefaults.Builder(struct)
  }

  object Builder extends MetaStructBuilder[com.capnproto.test.TestListDefaults, com.capnproto.test.TestListDefaults.Builder] {
    override type Self = com.capnproto.test.TestListDefaults.Builder.type
    override val structName: String = "TestListDefaults"
    override val dataSectionSizeWords: Short = 0
    override val pointerSectionSizeWords: Short = 1
    override def create(struct: CapnpStructBuilder): com.capnproto.test.TestListDefaults.Builder = new com.capnproto.test.TestListDefaults.Builder(struct)
    override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestListDefaults.fields
  }
  class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestListDefaultsMutable(struct) with StructBuilder[com.capnproto.test.TestListDefaults, com.capnproto.test.TestListDefaults.Builder] {
    override type MetaBuilderT = com.capnproto.test.TestListDefaults.Builder.type

    override def meta: TestListDefaults.type = TestListDefaults
    override def metaBuilder: MetaBuilderT = com.capnproto.test.TestListDefaults.Builder
    def setLists(value: com.capnproto.test.TestLists): Builder = { struct.setNone(); this }
  }

  val lists = new FieldDescriptor[com.capnproto.test.TestLists, TestListDefaults, TestListDefaults.type](
    name = "lists",
    meta = TestListDefaults,
    getter = _.lists,
    manifest = manifest[com.capnproto.test.TestLists],
    isUnion = false
  )
  override val fields: Seq[FieldDescriptor[_, TestListDefaults, TestListDefaults.type]] = Seq(lists)
}

trait TestListDefaults extends Struct[TestListDefaults] {
  override type MetaT = TestListDefaults.type
  override type MetaBuilderT = com.capnproto.test.TestListDefaults.Builder.type

  override def meta: TestListDefaults.type = TestListDefaults
  override def metaBuilder: com.capnproto.test.TestListDefaults.Builder.type = com.capnproto.test.TestListDefaults.Builder

  def struct: CapnpStruct

  def lists: Option[com.capnproto.test.TestLists]
}

class TestListDefaultsMutable(override val struct: CapnpStruct) extends TestListDefaults {
  override def lists: Option[com.capnproto.test.TestLists] = struct.getStruct(0).map(new com.capnproto.test.TestListsMutable(_))
}

object TestLateUnion extends MetaStruct[TestLateUnion] {
  override type Self = TestLateUnion.type
  override val structName: String = "TestLateUnion"
  override def create(struct: CapnpStruct): TestLateUnion = new TestLateUnionMutable(struct)
  def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestLateUnion.Builder = {
    val (segment, pointerOffset) = arena.allocate(1)
    val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestLateUnion.Builder.dataSectionSizeWords, com.capnproto.test.TestLateUnion.Builder.pointerSectionSizeWords)
    new com.capnproto.test.TestLateUnion.Builder(struct)
  }

  object Builder extends MetaStructBuilder[com.capnproto.test.TestLateUnion, com.capnproto.test.TestLateUnion.Builder] {
    override type Self = com.capnproto.test.TestLateUnion.Builder.type
    override val structName: String = "TestLateUnion"
    override val dataSectionSizeWords: Short = 3
    override val pointerSectionSizeWords: Short = 3
    override def create(struct: CapnpStructBuilder): com.capnproto.test.TestLateUnion.Builder = new com.capnproto.test.TestLateUnion.Builder(struct)
    override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestLateUnion.fields
  }
  class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestLateUnionMutable(struct) with StructBuilder[com.capnproto.test.TestLateUnion, com.capnproto.test.TestLateUnion.Builder] {
    override type MetaBuilderT = com.capnproto.test.TestLateUnion.Builder.type

    override def meta: TestLateUnion.type = TestLateUnion
    override def metaBuilder: MetaBuilderT = com.capnproto.test.TestLateUnion.Builder
    def setFoo(value: java.lang.Integer): Builder = { struct.setInt(0, value); this }
    def setBar(value: String): Builder = { struct.setString(0, value); this }
    def setBaz(value: java.lang.Short): Builder = { struct.setShort(2, value); this }
    override def theUnion: com.capnproto.test.TestLateUnion.TheUnion.Builder = new com.capnproto.test.TestLateUnion.TheUnion.Builder(struct)
    override def anotherUnion: com.capnproto.test.TestLateUnion.AnotherUnion.Builder = new com.capnproto.test.TestLateUnion.AnotherUnion.Builder(struct)
  }

  object TheUnion extends MetaStruct[TheUnion] {
    override type Self = TheUnion.type
    override val structName: String = "TheUnion"
    override def create(struct: CapnpStruct): TheUnion = new TheUnionMutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestLateUnion.TheUnion.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestLateUnion.TheUnion.Builder.dataSectionSizeWords, com.capnproto.test.TestLateUnion.TheUnion.Builder.pointerSectionSizeWords)
      new com.capnproto.test.TestLateUnion.TheUnion.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.test.TestLateUnion.TheUnion, com.capnproto.test.TestLateUnion.TheUnion.Builder] {
      override type Self = com.capnproto.test.TestLateUnion.TheUnion.Builder.type
      override val structName: String = "TheUnion"
      override val dataSectionSizeWords: Short = 3
      override val pointerSectionSizeWords: Short = 3
      override def create(struct: CapnpStructBuilder): com.capnproto.test.TestLateUnion.TheUnion.Builder = new com.capnproto.test.TestLateUnion.TheUnion.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestLateUnion.TheUnion.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestLateUnion.TheUnionMutable(struct) with StructBuilder[com.capnproto.test.TestLateUnion.TheUnion, com.capnproto.test.TestLateUnion.TheUnion.Builder] {
      override type MetaBuilderT = com.capnproto.test.TestLateUnion.TheUnion.Builder.type

      override def meta: TheUnion.type = TheUnion
      override def metaBuilder: MetaBuilderT = com.capnproto.test.TestLateUnion.TheUnion.Builder
      def setQux(value: String): Builder = { struct.setString(1, value); struct.setShort(3, -1); this }
      def setGrault(value: java.lang.Float): Builder = { struct.setFloat(2, value); struct.setShort(3, -3); this }
    }

    sealed trait Union extends UnionValue[com.capnproto.test.TestLateUnion.TheUnion.Union]
    object Union extends UnionMeta[com.capnproto.test.TestLateUnion.TheUnion.Union] {
      case class Unknown(discriminant: Short) extends com.capnproto.test.TestLateUnion.TheUnion.Union
      case class qux(value: Option[String]) extends com.capnproto.test.TestLateUnion.TheUnion.Union
      case class corge(value: Seq[java.lang.Integer]) extends com.capnproto.test.TestLateUnion.TheUnion.Union
      case class grault(value: Option[java.lang.Float]) extends com.capnproto.test.TestLateUnion.TheUnion.Union
    }

    val qux = new FieldDescriptor[String, TheUnion, TheUnion.type](
      name = "qux",
      meta = TheUnion,
      getter = _.qux,
      manifest = manifest[String],
      isUnion = true
    )

    val corge = new FieldDescriptor[Seq[java.lang.Integer], TheUnion, TheUnion.type](
      name = "corge",
      meta = TheUnion,
      getter = x => Some(x.corge),
      manifest = manifest[Seq[java.lang.Integer]],
      isUnion = true
    )

    val grault = new FieldDescriptor[java.lang.Float, TheUnion, TheUnion.type](
      name = "grault",
      meta = TheUnion,
      getter = _.grault,
      manifest = manifest[java.lang.Float],
      isUnion = true
    )
    override val fields: Seq[FieldDescriptor[_, TheUnion, TheUnion.type]] = Seq(qux, corge, grault)
  }

  trait TheUnion extends Struct[TheUnion] with HasUnion[com.capnproto.test.TestLateUnion.TheUnion.Union] {
    override type MetaT = TheUnion.type
    override type MetaBuilderT = com.capnproto.test.TestLateUnion.TheUnion.Builder.type

    override def meta: TheUnion.type = TheUnion
    override def metaBuilder: com.capnproto.test.TestLateUnion.TheUnion.Builder.type = com.capnproto.test.TestLateUnion.TheUnion.Builder

    def struct: CapnpStruct

    def qux: Option[String]
    def corge: Seq[java.lang.Integer]
    def grault: Option[java.lang.Float]
  }

  class TheUnionMutable(override val struct: CapnpStruct) extends TheUnion {
    override def discriminant: Short = (struct.getShort(3).getOrElse(new java.lang.Short(0.toShort)): java.lang.Short)
    override def switch: com.capnproto.test.TestLateUnion.TheUnion.Union = discriminant match {
      case 0 => com.capnproto.test.TestLateUnion.TheUnion.Union.qux(qux)
      case 1 => com.capnproto.test.TestLateUnion.TheUnion.Union.corge(corge)
      case 2 => com.capnproto.test.TestLateUnion.TheUnion.Union.grault(grault)
      case d => com.capnproto.test.TestLateUnion.TheUnion.Union.Unknown(d)
    }
    override def union: UnionMeta[com.capnproto.test.TestLateUnion.TheUnion.Union] = com.capnproto.test.TestLateUnion.TheUnion.Union

    override def qux: Option[String] = struct.getString(1)
    override def corge: Seq[java.lang.Integer] = struct.getPrimitiveList(1, _.getInt _)
    override def grault: Option[java.lang.Float] = struct.getFloat(2)
  }
  object AnotherUnion extends MetaStruct[AnotherUnion] {
    override type Self = AnotherUnion.type
    override val structName: String = "AnotherUnion"
    override def create(struct: CapnpStruct): AnotherUnion = new AnotherUnionMutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestLateUnion.AnotherUnion.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestLateUnion.AnotherUnion.Builder.dataSectionSizeWords, com.capnproto.test.TestLateUnion.AnotherUnion.Builder.pointerSectionSizeWords)
      new com.capnproto.test.TestLateUnion.AnotherUnion.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.test.TestLateUnion.AnotherUnion, com.capnproto.test.TestLateUnion.AnotherUnion.Builder] {
      override type Self = com.capnproto.test.TestLateUnion.AnotherUnion.Builder.type
      override val structName: String = "AnotherUnion"
      override val dataSectionSizeWords: Short = 3
      override val pointerSectionSizeWords: Short = 3
      override def create(struct: CapnpStructBuilder): com.capnproto.test.TestLateUnion.AnotherUnion.Builder = new com.capnproto.test.TestLateUnion.AnotherUnion.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestLateUnion.AnotherUnion.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestLateUnion.AnotherUnionMutable(struct) with StructBuilder[com.capnproto.test.TestLateUnion.AnotherUnion, com.capnproto.test.TestLateUnion.AnotherUnion.Builder] {
      override type MetaBuilderT = com.capnproto.test.TestLateUnion.AnotherUnion.Builder.type

      override def meta: AnotherUnion.type = AnotherUnion
      override def metaBuilder: MetaBuilderT = com.capnproto.test.TestLateUnion.AnotherUnion.Builder
      def setQux(value: String): Builder = { struct.setString(2, value); struct.setShort(6, -1); this }
      def setGrault(value: java.lang.Float): Builder = { struct.setFloat(4, value); struct.setShort(6, -3); this }
    }

    sealed trait Union extends UnionValue[com.capnproto.test.TestLateUnion.AnotherUnion.Union]
    object Union extends UnionMeta[com.capnproto.test.TestLateUnion.AnotherUnion.Union] {
      case class Unknown(discriminant: Short) extends com.capnproto.test.TestLateUnion.AnotherUnion.Union
      case class qux(value: Option[String]) extends com.capnproto.test.TestLateUnion.AnotherUnion.Union
      case class corge(value: Seq[java.lang.Integer]) extends com.capnproto.test.TestLateUnion.AnotherUnion.Union
      case class grault(value: Option[java.lang.Float]) extends com.capnproto.test.TestLateUnion.AnotherUnion.Union
    }

    val qux = new FieldDescriptor[String, AnotherUnion, AnotherUnion.type](
      name = "qux",
      meta = AnotherUnion,
      getter = _.qux,
      manifest = manifest[String],
      isUnion = true
    )

    val corge = new FieldDescriptor[Seq[java.lang.Integer], AnotherUnion, AnotherUnion.type](
      name = "corge",
      meta = AnotherUnion,
      getter = x => Some(x.corge),
      manifest = manifest[Seq[java.lang.Integer]],
      isUnion = true
    )

    val grault = new FieldDescriptor[java.lang.Float, AnotherUnion, AnotherUnion.type](
      name = "grault",
      meta = AnotherUnion,
      getter = _.grault,
      manifest = manifest[java.lang.Float],
      isUnion = true
    )
    override val fields: Seq[FieldDescriptor[_, AnotherUnion, AnotherUnion.type]] = Seq(qux, corge, grault)
  }

  trait AnotherUnion extends Struct[AnotherUnion] with HasUnion[com.capnproto.test.TestLateUnion.AnotherUnion.Union] {
    override type MetaT = AnotherUnion.type
    override type MetaBuilderT = com.capnproto.test.TestLateUnion.AnotherUnion.Builder.type

    override def meta: AnotherUnion.type = AnotherUnion
    override def metaBuilder: com.capnproto.test.TestLateUnion.AnotherUnion.Builder.type = com.capnproto.test.TestLateUnion.AnotherUnion.Builder

    def struct: CapnpStruct

    def qux: Option[String]
    def corge: Seq[java.lang.Integer]
    def grault: Option[java.lang.Float]
  }

  class AnotherUnionMutable(override val struct: CapnpStruct) extends AnotherUnion {
    override def discriminant: Short = (struct.getShort(6).getOrElse(new java.lang.Short(0.toShort)): java.lang.Short)
    override def switch: com.capnproto.test.TestLateUnion.AnotherUnion.Union = discriminant match {
      case 0 => com.capnproto.test.TestLateUnion.AnotherUnion.Union.qux(qux)
      case 1 => com.capnproto.test.TestLateUnion.AnotherUnion.Union.corge(corge)
      case 2 => com.capnproto.test.TestLateUnion.AnotherUnion.Union.grault(grault)
      case d => com.capnproto.test.TestLateUnion.AnotherUnion.Union.Unknown(d)
    }
    override def union: UnionMeta[com.capnproto.test.TestLateUnion.AnotherUnion.Union] = com.capnproto.test.TestLateUnion.AnotherUnion.Union

    override def qux: Option[String] = struct.getString(2)
    override def corge: Seq[java.lang.Integer] = struct.getPrimitiveList(2, _.getInt _)
    override def grault: Option[java.lang.Float] = struct.getFloat(4)
  }
  val foo = new FieldDescriptor[java.lang.Integer, TestLateUnion, TestLateUnion.type](
    name = "foo",
    meta = TestLateUnion,
    getter = _.foo,
    manifest = manifest[java.lang.Integer],
    isUnion = false
  )

  val bar = new FieldDescriptor[String, TestLateUnion, TestLateUnion.type](
    name = "bar",
    meta = TestLateUnion,
    getter = _.bar,
    manifest = manifest[String],
    isUnion = false
  )

  val baz = new FieldDescriptor[java.lang.Short, TestLateUnion, TestLateUnion.type](
    name = "baz",
    meta = TestLateUnion,
    getter = _.baz,
    manifest = manifest[java.lang.Short],
    isUnion = false
  )

  val theUnion = new FieldDescriptor[com.capnproto.test.TestLateUnion.TheUnion, TestLateUnion, TestLateUnion.type](
    name = "theUnion",
    meta = TestLateUnion,
    getter = x => Some(x.theUnion),
    manifest = manifest[com.capnproto.test.TestLateUnion.TheUnion],
    isUnion = false
  )

  val anotherUnion = new FieldDescriptor[com.capnproto.test.TestLateUnion.AnotherUnion, TestLateUnion, TestLateUnion.type](
    name = "anotherUnion",
    meta = TestLateUnion,
    getter = x => Some(x.anotherUnion),
    manifest = manifest[com.capnproto.test.TestLateUnion.AnotherUnion],
    isUnion = false
  )
  override val fields: Seq[FieldDescriptor[_, TestLateUnion, TestLateUnion.type]] = Seq(foo, bar, baz, theUnion, anotherUnion)
}

trait TestLateUnion extends Struct[TestLateUnion] {
  override type MetaT = TestLateUnion.type
  override type MetaBuilderT = com.capnproto.test.TestLateUnion.Builder.type

  override def meta: TestLateUnion.type = TestLateUnion
  override def metaBuilder: com.capnproto.test.TestLateUnion.Builder.type = com.capnproto.test.TestLateUnion.Builder

  def struct: CapnpStruct

  def foo: Option[java.lang.Integer]
  def bar: Option[String]
  def baz: Option[java.lang.Short]
  def theUnion: com.capnproto.test.TestLateUnion.TheUnion
  def anotherUnion: com.capnproto.test.TestLateUnion.AnotherUnion
}

class TestLateUnionMutable(override val struct: CapnpStruct) extends TestLateUnion {
  override def foo: Option[java.lang.Integer] = struct.getInt(0)
  override def bar: Option[String] = struct.getString(0)
  override def baz: Option[java.lang.Short] = struct.getShort(2)
  override def theUnion: com.capnproto.test.TestLateUnion.TheUnion = new com.capnproto.test.TestLateUnion.TheUnionMutable(struct)

  override def anotherUnion: com.capnproto.test.TestLateUnion.AnotherUnion = new com.capnproto.test.TestLateUnion.AnotherUnionMutable(struct)

}

object TestOldVersion extends MetaStruct[TestOldVersion] {
  override type Self = TestOldVersion.type
  override val structName: String = "TestOldVersion"
  override def create(struct: CapnpStruct): TestOldVersion = new TestOldVersionMutable(struct)
  def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestOldVersion.Builder = {
    val (segment, pointerOffset) = arena.allocate(1)
    val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestOldVersion.Builder.dataSectionSizeWords, com.capnproto.test.TestOldVersion.Builder.pointerSectionSizeWords)
    new com.capnproto.test.TestOldVersion.Builder(struct)
  }

  object Builder extends MetaStructBuilder[com.capnproto.test.TestOldVersion, com.capnproto.test.TestOldVersion.Builder] {
    override type Self = com.capnproto.test.TestOldVersion.Builder.type
    override val structName: String = "TestOldVersion"
    override val dataSectionSizeWords: Short = 1
    override val pointerSectionSizeWords: Short = 2
    override def create(struct: CapnpStructBuilder): com.capnproto.test.TestOldVersion.Builder = new com.capnproto.test.TestOldVersion.Builder(struct)
    override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestOldVersion.fields
  }
  class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestOldVersionMutable(struct) with StructBuilder[com.capnproto.test.TestOldVersion, com.capnproto.test.TestOldVersion.Builder] {
    override type MetaBuilderT = com.capnproto.test.TestOldVersion.Builder.type

    override def meta: TestOldVersion.type = TestOldVersion
    override def metaBuilder: MetaBuilderT = com.capnproto.test.TestOldVersion.Builder
    def setOld1(value: java.lang.Long): Builder = { struct.setLong(0, value); this }
    def setOld2(value: String): Builder = { struct.setString(0, value); this }
    def setOld3(value: com.capnproto.test.TestOldVersion): Builder = { struct.setNone(); this }
  }

  val old1 = new FieldDescriptor[java.lang.Long, TestOldVersion, TestOldVersion.type](
    name = "old1",
    meta = TestOldVersion,
    getter = _.old1,
    manifest = manifest[java.lang.Long],
    isUnion = false
  )

  val old2 = new FieldDescriptor[String, TestOldVersion, TestOldVersion.type](
    name = "old2",
    meta = TestOldVersion,
    getter = _.old2,
    manifest = manifest[String],
    isUnion = false
  )

  val old3 = new FieldDescriptor[com.capnproto.test.TestOldVersion, TestOldVersion, TestOldVersion.type](
    name = "old3",
    meta = TestOldVersion,
    getter = _.old3,
    manifest = manifest[com.capnproto.test.TestOldVersion],
    isUnion = false
  )
  override val fields: Seq[FieldDescriptor[_, TestOldVersion, TestOldVersion.type]] = Seq(old1, old2, old3)
}

trait TestOldVersion extends Struct[TestOldVersion] {
  override type MetaT = TestOldVersion.type
  override type MetaBuilderT = com.capnproto.test.TestOldVersion.Builder.type

  override def meta: TestOldVersion.type = TestOldVersion
  override def metaBuilder: com.capnproto.test.TestOldVersion.Builder.type = com.capnproto.test.TestOldVersion.Builder

  def struct: CapnpStruct

  def old1: Option[java.lang.Long]
  def old2: Option[String]
  def old3: Option[com.capnproto.test.TestOldVersion]
}

class TestOldVersionMutable(override val struct: CapnpStruct) extends TestOldVersion {
  override def old1: Option[java.lang.Long] = struct.getLong(0)
  override def old2: Option[String] = struct.getString(0)
  override def old3: Option[com.capnproto.test.TestOldVersion] = struct.getStruct(1).map(new com.capnproto.test.TestOldVersionMutable(_))
}

object TestNewVersion extends MetaStruct[TestNewVersion] {
  override type Self = TestNewVersion.type
  override val structName: String = "TestNewVersion"
  override def create(struct: CapnpStruct): TestNewVersion = new TestNewVersionMutable(struct)
  def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestNewVersion.Builder = {
    val (segment, pointerOffset) = arena.allocate(1)
    val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestNewVersion.Builder.dataSectionSizeWords, com.capnproto.test.TestNewVersion.Builder.pointerSectionSizeWords)
    new com.capnproto.test.TestNewVersion.Builder(struct)
  }

  object Builder extends MetaStructBuilder[com.capnproto.test.TestNewVersion, com.capnproto.test.TestNewVersion.Builder] {
    override type Self = com.capnproto.test.TestNewVersion.Builder.type
    override val structName: String = "TestNewVersion"
    override val dataSectionSizeWords: Short = 2
    override val pointerSectionSizeWords: Short = 3
    override def create(struct: CapnpStructBuilder): com.capnproto.test.TestNewVersion.Builder = new com.capnproto.test.TestNewVersion.Builder(struct)
    override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestNewVersion.fields
  }
  class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestNewVersionMutable(struct) with StructBuilder[com.capnproto.test.TestNewVersion, com.capnproto.test.TestNewVersion.Builder] {
    override type MetaBuilderT = com.capnproto.test.TestNewVersion.Builder.type

    override def meta: TestNewVersion.type = TestNewVersion
    override def metaBuilder: MetaBuilderT = com.capnproto.test.TestNewVersion.Builder
    def setOld1(value: java.lang.Long): Builder = { struct.setLong(0, value); this }
    def setOld2(value: String): Builder = { struct.setString(0, value); this }
    def setOld3(value: com.capnproto.test.TestNewVersion): Builder = { struct.setNone(); this }
    def setNew1(value: java.lang.Long): Builder = { struct.setLong(1, value); this }
    def setNew2(value: String): Builder = { struct.setString(2, value); this }
  }

  val old1 = new FieldDescriptor[java.lang.Long, TestNewVersion, TestNewVersion.type](
    name = "old1",
    meta = TestNewVersion,
    getter = _.old1,
    manifest = manifest[java.lang.Long],
    isUnion = false
  )

  val old2 = new FieldDescriptor[String, TestNewVersion, TestNewVersion.type](
    name = "old2",
    meta = TestNewVersion,
    getter = _.old2,
    manifest = manifest[String],
    isUnion = false
  )

  val old3 = new FieldDescriptor[com.capnproto.test.TestNewVersion, TestNewVersion, TestNewVersion.type](
    name = "old3",
    meta = TestNewVersion,
    getter = _.old3,
    manifest = manifest[com.capnproto.test.TestNewVersion],
    isUnion = false
  )

  val new1 = new FieldDescriptor[java.lang.Long, TestNewVersion, TestNewVersion.type](
    name = "new1",
    meta = TestNewVersion,
    getter = _.new1,
    manifest = manifest[java.lang.Long],
    isUnion = false
  )

  val new2 = new FieldDescriptor[String, TestNewVersion, TestNewVersion.type](
    name = "new2",
    meta = TestNewVersion,
    getter = _.new2,
    manifest = manifest[String],
    isUnion = false
  )
  override val fields: Seq[FieldDescriptor[_, TestNewVersion, TestNewVersion.type]] = Seq(old1, old2, old3, new1, new2)
}

trait TestNewVersion extends Struct[TestNewVersion] {
  override type MetaT = TestNewVersion.type
  override type MetaBuilderT = com.capnproto.test.TestNewVersion.Builder.type

  override def meta: TestNewVersion.type = TestNewVersion
  override def metaBuilder: com.capnproto.test.TestNewVersion.Builder.type = com.capnproto.test.TestNewVersion.Builder

  def struct: CapnpStruct

  def old1: Option[java.lang.Long]
  def old2: Option[String]
  def old3: Option[com.capnproto.test.TestNewVersion]
  def new1: Option[java.lang.Long]
  def new2: Option[String]
}

class TestNewVersionMutable(override val struct: CapnpStruct) extends TestNewVersion {
  override def old1: Option[java.lang.Long] = struct.getLong(0)
  override def old2: Option[String] = struct.getString(0)
  override def old3: Option[com.capnproto.test.TestNewVersion] = struct.getStruct(1).map(new com.capnproto.test.TestNewVersionMutable(_))
  override def new1: Option[java.lang.Long] = struct.getLong(1)
  override def new2: Option[String] = struct.getString(2)
}

object TestStructUnion extends MetaStruct[TestStructUnion] {
  override type Self = TestStructUnion.type
  override val structName: String = "TestStructUnion"
  override def create(struct: CapnpStruct): TestStructUnion = new TestStructUnionMutable(struct)
  def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestStructUnion.Builder = {
    val (segment, pointerOffset) = arena.allocate(1)
    val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestStructUnion.Builder.dataSectionSizeWords, com.capnproto.test.TestStructUnion.Builder.pointerSectionSizeWords)
    new com.capnproto.test.TestStructUnion.Builder(struct)
  }

  object Builder extends MetaStructBuilder[com.capnproto.test.TestStructUnion, com.capnproto.test.TestStructUnion.Builder] {
    override type Self = com.capnproto.test.TestStructUnion.Builder.type
    override val structName: String = "TestStructUnion"
    override val dataSectionSizeWords: Short = 1
    override val pointerSectionSizeWords: Short = 1
    override def create(struct: CapnpStructBuilder): com.capnproto.test.TestStructUnion.Builder = new com.capnproto.test.TestStructUnion.Builder(struct)
    override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestStructUnion.fields
  }
  class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestStructUnionMutable(struct) with StructBuilder[com.capnproto.test.TestStructUnion, com.capnproto.test.TestStructUnion.Builder] {
    override type MetaBuilderT = com.capnproto.test.TestStructUnion.Builder.type

    override def meta: TestStructUnion.type = TestStructUnion
    override def metaBuilder: MetaBuilderT = com.capnproto.test.TestStructUnion.Builder
    override def un: com.capnproto.test.TestStructUnion.Un.Builder = new com.capnproto.test.TestStructUnion.Un.Builder(struct)
  }

  object Un extends MetaStruct[Un] {
    override type Self = Un.type
    override val structName: String = "Un"
    override def create(struct: CapnpStruct): Un = new UnMutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestStructUnion.Un.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestStructUnion.Un.Builder.dataSectionSizeWords, com.capnproto.test.TestStructUnion.Un.Builder.pointerSectionSizeWords)
      new com.capnproto.test.TestStructUnion.Un.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.test.TestStructUnion.Un, com.capnproto.test.TestStructUnion.Un.Builder] {
      override type Self = com.capnproto.test.TestStructUnion.Un.Builder.type
      override val structName: String = "Un"
      override val dataSectionSizeWords: Short = 1
      override val pointerSectionSizeWords: Short = 1
      override def create(struct: CapnpStructBuilder): com.capnproto.test.TestStructUnion.Un.Builder = new com.capnproto.test.TestStructUnion.Un.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestStructUnion.Un.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestStructUnion.UnMutable(struct) with StructBuilder[com.capnproto.test.TestStructUnion.Un, com.capnproto.test.TestStructUnion.Un.Builder] {
      override type MetaBuilderT = com.capnproto.test.TestStructUnion.Un.Builder.type

      override def meta: Un.type = Un
      override def metaBuilder: MetaBuilderT = com.capnproto.test.TestStructUnion.Un.Builder
      def setAllTypes(value: com.capnproto.test.TestAllTypes): Builder = { struct.setNone(); struct.setShort(0, -1); this }
      def set__Object(value: com.capnproto.test.TestObject): Builder = { struct.setNone(); struct.setShort(0, -2); this }
    }

    sealed trait Union extends UnionValue[com.capnproto.test.TestStructUnion.Un.Union]
    object Union extends UnionMeta[com.capnproto.test.TestStructUnion.Un.Union] {
      case class Unknown(discriminant: Short) extends com.capnproto.test.TestStructUnion.Un.Union
      case class allTypes(value: Option[com.capnproto.test.TestAllTypes]) extends com.capnproto.test.TestStructUnion.Un.Union
      case class __object(value: Option[com.capnproto.test.TestObject]) extends com.capnproto.test.TestStructUnion.Un.Union
    }

    val allTypes = new FieldDescriptor[com.capnproto.test.TestAllTypes, Un, Un.type](
      name = "allTypes",
      meta = Un,
      getter = _.allTypes,
      manifest = manifest[com.capnproto.test.TestAllTypes],
      isUnion = true
    )

    val __object = new FieldDescriptor[com.capnproto.test.TestObject, Un, Un.type](
      name = "object",
      meta = Un,
      getter = _.__object,
      manifest = manifest[com.capnproto.test.TestObject],
      isUnion = true
    )
    override val fields: Seq[FieldDescriptor[_, Un, Un.type]] = Seq(allTypes, __object)
  }

  trait Un extends Struct[Un] with HasUnion[com.capnproto.test.TestStructUnion.Un.Union] {
    override type MetaT = Un.type
    override type MetaBuilderT = com.capnproto.test.TestStructUnion.Un.Builder.type

    override def meta: Un.type = Un
    override def metaBuilder: com.capnproto.test.TestStructUnion.Un.Builder.type = com.capnproto.test.TestStructUnion.Un.Builder

    def struct: CapnpStruct

    def allTypes: Option[com.capnproto.test.TestAllTypes]
    def __object: Option[com.capnproto.test.TestObject]
  }

  class UnMutable(override val struct: CapnpStruct) extends Un {
    override def discriminant: Short = (struct.getShort(0).getOrElse(new java.lang.Short(0.toShort)): java.lang.Short)
    override def switch: com.capnproto.test.TestStructUnion.Un.Union = discriminant match {
      case 0 => com.capnproto.test.TestStructUnion.Un.Union.allTypes(allTypes)
      case 1 => com.capnproto.test.TestStructUnion.Un.Union.__object(__object)
      case d => com.capnproto.test.TestStructUnion.Un.Union.Unknown(d)
    }
    override def union: UnionMeta[com.capnproto.test.TestStructUnion.Un.Union] = com.capnproto.test.TestStructUnion.Un.Union

    override def allTypes: Option[com.capnproto.test.TestAllTypes] = struct.getStruct(0).map(new com.capnproto.test.TestAllTypesMutable(_))
    override def __object: Option[com.capnproto.test.TestObject] = struct.getStruct(0).map(new com.capnproto.test.TestObjectMutable(_))
  }
  val un = new FieldDescriptor[com.capnproto.test.TestStructUnion.Un, TestStructUnion, TestStructUnion.type](
    name = "un",
    meta = TestStructUnion,
    getter = x => Some(x.un),
    manifest = manifest[com.capnproto.test.TestStructUnion.Un],
    isUnion = false
  )
  override val fields: Seq[FieldDescriptor[_, TestStructUnion, TestStructUnion.type]] = Seq(un)
}

trait TestStructUnion extends Struct[TestStructUnion] {
  override type MetaT = TestStructUnion.type
  override type MetaBuilderT = com.capnproto.test.TestStructUnion.Builder.type

  override def meta: TestStructUnion.type = TestStructUnion
  override def metaBuilder: com.capnproto.test.TestStructUnion.Builder.type = com.capnproto.test.TestStructUnion.Builder

  def struct: CapnpStruct

  def un: com.capnproto.test.TestStructUnion.Un
}

class TestStructUnionMutable(override val struct: CapnpStruct) extends TestStructUnion {
  override def un: com.capnproto.test.TestStructUnion.Un = new com.capnproto.test.TestStructUnion.UnMutable(struct)

}

object TestEmptyStruct extends MetaStruct[TestEmptyStruct] {
  override type Self = TestEmptyStruct.type
  override val structName: String = "TestEmptyStruct"
  override def create(struct: CapnpStruct): TestEmptyStruct = new TestEmptyStructMutable(struct)
  def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestEmptyStruct.Builder = {
    val (segment, pointerOffset) = arena.allocate(1)
    val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestEmptyStruct.Builder.dataSectionSizeWords, com.capnproto.test.TestEmptyStruct.Builder.pointerSectionSizeWords)
    new com.capnproto.test.TestEmptyStruct.Builder(struct)
  }

  object Builder extends MetaStructBuilder[com.capnproto.test.TestEmptyStruct, com.capnproto.test.TestEmptyStruct.Builder] {
    override type Self = com.capnproto.test.TestEmptyStruct.Builder.type
    override val structName: String = "TestEmptyStruct"
    override val dataSectionSizeWords: Short = 0
    override val pointerSectionSizeWords: Short = 0
    override def create(struct: CapnpStructBuilder): com.capnproto.test.TestEmptyStruct.Builder = new com.capnproto.test.TestEmptyStruct.Builder(struct)
    override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestEmptyStruct.fields
  }
  class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestEmptyStructMutable(struct) with StructBuilder[com.capnproto.test.TestEmptyStruct, com.capnproto.test.TestEmptyStruct.Builder] {
    override type MetaBuilderT = com.capnproto.test.TestEmptyStruct.Builder.type

    override def meta: TestEmptyStruct.type = TestEmptyStruct
    override def metaBuilder: MetaBuilderT = com.capnproto.test.TestEmptyStruct.Builder
  }

  override val fields: Seq[FieldDescriptor[_, TestEmptyStruct, TestEmptyStruct.type]] = Seq()
}

trait TestEmptyStruct extends Struct[TestEmptyStruct] {
  override type MetaT = TestEmptyStruct.type
  override type MetaBuilderT = com.capnproto.test.TestEmptyStruct.Builder.type

  override def meta: TestEmptyStruct.type = TestEmptyStruct
  override def metaBuilder: com.capnproto.test.TestEmptyStruct.Builder.type = com.capnproto.test.TestEmptyStruct.Builder

  def struct: CapnpStruct

}

class TestEmptyStructMutable(override val struct: CapnpStruct) extends TestEmptyStruct {

}

object TestConstants extends MetaStruct[TestConstants] {
  override type Self = TestConstants.type
  override val structName: String = "TestConstants"
  override def create(struct: CapnpStruct): TestConstants = new TestConstantsMutable(struct)
  def newBuilder(arena: CapnpArenaBuilder): com.capnproto.test.TestConstants.Builder = {
    val (segment, pointerOffset) = arena.allocate(1)
    val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.test.TestConstants.Builder.dataSectionSizeWords, com.capnproto.test.TestConstants.Builder.pointerSectionSizeWords)
    new com.capnproto.test.TestConstants.Builder(struct)
  }

  object Builder extends MetaStructBuilder[com.capnproto.test.TestConstants, com.capnproto.test.TestConstants.Builder] {
    override type Self = com.capnproto.test.TestConstants.Builder.type
    override val structName: String = "TestConstants"
    override val dataSectionSizeWords: Short = 0
    override val pointerSectionSizeWords: Short = 0
    override def create(struct: CapnpStructBuilder): com.capnproto.test.TestConstants.Builder = new com.capnproto.test.TestConstants.Builder(struct)
    override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.test.TestConstants.fields
  }
  class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.test.TestConstantsMutable(struct) with StructBuilder[com.capnproto.test.TestConstants, com.capnproto.test.TestConstants.Builder] {
    override type MetaBuilderT = com.capnproto.test.TestConstants.Builder.type

    override def meta: TestConstants.type = TestConstants
    override def metaBuilder: MetaBuilderT = com.capnproto.test.TestConstants.Builder
  }

  val VoidConst: Unit = Unit

  val BoolConst: java.lang.Boolean = true

  val Int8Const: java.lang.Byte = -123.toByte

  val Int16Const: java.lang.Short = -12345.toShort

  val Int32Const: java.lang.Integer = -12345678

  val Int64Const: java.lang.Long = -123456789012345L

  val Uint8Const: java.lang.Byte = -22.toByte

  val Uint16Const: java.lang.Short = -19858.toShort

  val Uint32Const: java.lang.Integer = -838178284

  val Uint64Const: java.lang.Long = -6101065172474983726L

  val Float32Const: java.lang.Float = 1234.5.toFloat

  val Float64Const: java.lang.Double = -1.23E47

  val TextConst: String = "foo"

  val DataConst: Array[Byte] = Array[Byte](98, 97, 114)

  val StructConst: com.capnproto.test.TestAllTypes = CapnpArena.fromBytes(Array[Byte](0, 0, 0, 0, -48, 0, 0, 0, 0, 0, 0, 0, 6, 0, 20, 0, 1, -12, -128, 13, 14, 16, 76, -5, 78, 115, -24, 56, -90, 51, 0, 0, 90, 0, -46, 4, 20, -120, 98, 3, -46, 10, 111, 18, 33, 25, -52, 4, 95, 112, 9, -81, 2, 0, 0, 0, 0, 0, 0, 0, 0, -112, 117, 64, 77, 0, 0, 0, 34, 0, 0, 0, 77, 0, 0, 0, 26, 0, 0, 0, 76, 0, 0, 0, 6, 0, 20, 0, 37, 1, 0, 0, 24, 0, 0, 0, 33, 1, 0, 0, 41, 0, 0, 0, 33, 1, 0, 0, 34, 0, 0, 0, 33, 1, 0, 0, 35, 0, 0, 0, 33, 1, 0, 0, 36, 0, 0, 0, 37, 1, 0, 0, 37, 0, 0, 0, 49, 1, 0, 0, 34, 0, 0, 0, 49, 1, 0, 0, 35, 0, 0, 0, 49, 1, 0, 0, 36, 0, 0, 0, 53, 1, 0, 0, 37, 0, 0, 0, 65, 1, 0, 0, 52, 0, 0, 0, 73, 1, 0, 0, 53, 0, 0, 0, 93, 1, 0, 0, 30, 0, 0, 0, 113, 1, 0, 0, 30, 0, 0, 0, -123, 1, 0, 0, 119, 2, 0, 0, -43, 2, 0, 0, 27, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 98, 97, 122, 0, 0, 0, 0, 0, 113, 117, 120, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 77, 0, 0, 0, 58, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 72, 0, 0, 0, 6, 0, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 110, 101, 115, 116, 101, 100, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 77, 0, 0, 0, 114, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 114, 101, 97, 108, 108, 121, 32, 110, 101, 115, 116, 101, 100, 0, 0, 0, 26, 0, 0, 0, 0, 0, 0, 0, 12, -34, -128, 127, 0, 0, 0, 0, -46, 4, -46, -23, 0, -128, -1, 127, 78, 97, -68, 0, 64, -45, -96, -6, 0, 0, 0, -128, -1, -1, -1, 127, 121, -33, 13, -122, 72, 112, 0, 0, 46, 117, 19, -3, -118, -106, -3, -1, 0, 0, 0, 0, 0, 0, 0, -128, -1, -1, -1, -1, -1, -1, -1, 127, 12, 34, 0, -1, 0, 0, 0, 0, -46, 4, 46, 22, 0, 0, -1, -1, 78, 97, -68, 0, -64, 44, 95, 5, 0, 0, 0, 0, -1, -1, -1, -1, 121, -33, 13, -122, 72, 112, 0, 0, -46, -118, -20, 2, 117, 105, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0, 56, -76, -106, 73, -62, -67, -16, 124, -62, -67, -16, -4, -22, 28, 8, 2, -22, 28, 8, -126, 0, 0, 0, 0, 0, 0, 0, 0, 64, -34, 119, -125, 33, 18, -36, 66, 41, -112, 35, -54, -27, -56, 118, 127, 41, -112, 35, -54, -27, -56, 118, -1, -111, -9, 80, 55, -98, 120, 102, 0, -111, -9, 80, 55, -98, 120, 102, -128, 9, 0, 0, 0, 42, 0, 0, 0, 9, 0, 0, 0, 50, 0, 0, 0, 9, 0, 0, 0, 58, 0, 0, 0, 113, 117, 117, 120, 0, 0, 0, 0, 99, 111, 114, 103, 101, 0, 0, 0, 103, 114, 97, 117, 108, 116, 0, 0, 9, 0, 0, 0, 50, 0, 0, 0, 9, 0, 0, 0, 42, 0, 0, 0, 9, 0, 0, 0, 34, 0, 0, 0, 103, 97, 114, 112, 108, 121, 0, 0, 119, 97, 108, 100, 111, 0, 0, 0, 102, 114, 101, 100, 0, 0, 0, 0, 12, 0, 0, 0, 6, 0, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 29, 1, 0, 0, 122, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -67, 0, 0, 0, 122, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 93, 0, 0, 0, 122, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 120, 32, 115, 116, 114, 117, 99, 116, 108, 105, 115, 116, 32, 49, 0, 0, 120, 32, 115, 116, 114, 117, 99, 116, 108, 105, 115, 116, 32, 50, 0, 0, 120, 32, 115, 116, 114, 117, 99, 116, 108, 105, 115, 116, 32, 51, 0, 0, 3, 0, 1, 0, 6, 0, 0, 0)).get.getRoot(com.capnproto.test.TestAllTypes).get

  val EnumConst: com.capnproto.test.TestEnum = com.capnproto.test.TestEnum.findByIdOrNull(5)

  val VoidListConst: Seq[Unit] = Seq[Unit](Unit)

  val BoolListConst: Seq[java.lang.Boolean] = Seq[java.lang.Boolean](true, false, false, true)

  val Int8ListConst: Seq[java.lang.Byte] = Seq[java.lang.Byte](111.toByte, -111.toByte)

  val Int16ListConst: Seq[java.lang.Short] = Seq[java.lang.Short](11111.toShort, -11111.toShort)

  val Int32ListConst: Seq[java.lang.Integer] = Seq[java.lang.Integer](111111111, -111111111)

  val Int64ListConst: Seq[java.lang.Long] = Seq[java.lang.Long](1111111111111111111L, -1111111111111111111L)

  val Uint8ListConst: Seq[java.lang.Byte] = Seq[java.lang.Byte](111.toByte, -34.toByte)

  val Uint16ListConst: Seq[java.lang.Short] = Seq[java.lang.Short](-32203.toShort, -21092.toShort)

  val Uint32ListConst: Seq[java.lang.Integer] = Seq[java.lang.Integer](-961633963)

  val Uint64ListConst: Seq[java.lang.Long] = Seq[java.lang.Long](-7335632962598440505L)

  val Float32ListConst: Seq[java.lang.Float] = Seq[java.lang.Float](5555.5.toFloat, Double.PositiveInfinity.toFloat, Double.NegativeInfinity.toFloat, Double.NaN.toFloat)

  val Float64ListConst: Seq[java.lang.Double] = Seq[java.lang.Double](7777.75, Double.PositiveInfinity, Double.NegativeInfinity, Double.NaN)

  val TextListConst: Seq[String] = Seq[String]("plugh", "xyzzy", "thud")

  val DataListConst: Seq[Array[Byte]] = Seq[Array[Byte]](Array[Byte](111, 111, 112, 115), Array[Byte](101, 120, 104, 97, 117, 115, 116, 101, 100), Array[Byte](114, 102, 99, 51, 48, 57, 50))

  val StructListConst: Seq[com.capnproto.test.TestAllTypes] = Seq[com.capnproto.test.TestAllTypes](CapnpArena.fromBytes(Array[Byte](0, 0, 0, 0, 29, 0, 0, 0, 0, 0, 0, 0, 6, 0, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 77, 0, 0, 0, 106, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 115, 116, 114, 117, 99, 116, 108, 105, 115, 116, 32, 49, 0, 0, 0, 0)).get.getRoot(com.capnproto.test.TestAllTypes).get, CapnpArena.fromBytes(Array[Byte](0, 0, 0, 0, 29, 0, 0, 0, 0, 0, 0, 0, 6, 0, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 77, 0, 0, 0, 106, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 115, 116, 114, 117, 99, 116, 108, 105, 115, 116, 32, 50, 0, 0, 0, 0)).get.getRoot(com.capnproto.test.TestAllTypes).get, CapnpArena.fromBytes(Array[Byte](0, 0, 0, 0, 29, 0, 0, 0, 0, 0, 0, 0, 6, 0, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 77, 0, 0, 0, 106, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 115, 116, 114, 117, 99, 116, 108, 105, 115, 116, 32, 51, 0, 0, 0, 0)).get.getRoot(com.capnproto.test.TestAllTypes).get)

  val EnumListConst: Seq[com.capnproto.test.TestEnum] = Seq[com.capnproto.test.TestEnum](com.capnproto.test.TestEnum.foo, com.capnproto.test.TestEnum.garply)
  override val fields: Seq[FieldDescriptor[_, TestConstants, TestConstants.type]] = Seq()
}

trait TestConstants extends Struct[TestConstants] {
  override type MetaT = TestConstants.type
  override type MetaBuilderT = com.capnproto.test.TestConstants.Builder.type

  override def meta: TestConstants.type = TestConstants
  override def metaBuilder: com.capnproto.test.TestConstants.Builder.type = com.capnproto.test.TestConstants.Builder

  def struct: CapnpStruct

}

class TestConstantsMutable(override val struct: CapnpStruct) extends TestConstants {

}
