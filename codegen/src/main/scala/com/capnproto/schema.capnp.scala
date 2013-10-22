// examples/src/main/scala/com/capnproto/schema.capnp

package com.capnproto.schema

import com.foursquare.spindle.{Enum, EnumMeta}
import com.capnproto.{HasUnion, UnionMeta, UnionValue, UntypedFieldDescriptor, FieldDescriptor, UntypedStruct, Struct, UntypedMetaStruct, MetaStruct, StructBuilder, MetaStructBuilder}
import com.capnproto.{CapnpStruct, CapnpStructBuilder, Pointer => CapnpPointer, CapnpList, CapnpTag, CapnpArenaBuilder}
import java.nio.ByteBuffer

object Node extends MetaStruct[Node] {
  override type Self = Node.type
  override val recordName: String = "Node"
  override def create(struct: CapnpStruct): Node = new NodeMutable(struct)
  def newBuilder(arena: CapnpArenaBuilder): com.capnproto.schema.Node.Builder = {
    val (segment, pointerOffset) = arena.allocate(1)
    val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.schema.Node.Builder.dataSectionSizeWords, com.capnproto.schema.Node.Builder.pointerSectionSizeWords)
    new com.capnproto.schema.Node.Builder(struct)
  }

  object Builder extends MetaStructBuilder[com.capnproto.schema.Node, com.capnproto.schema.Node.Builder] {
    override type Self = com.capnproto.schema.Node.Builder.type
    override val recordName: String = "Node"
    override val dataSectionSizeWords: Short = 5
    override val pointerSectionSizeWords: Short = 5
    override def create(struct: CapnpStructBuilder): com.capnproto.schema.Node.Builder = new com.capnproto.schema.Node.Builder(struct)
    override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.schema.Node.fields
  }
  class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.schema.NodeMutable(struct) with StructBuilder[com.capnproto.schema.Node, com.capnproto.schema.Node.Builder] {
    override type MetaBuilderT = com.capnproto.schema.Node.Builder.type

    override def meta: Node.type = Node
    override def metaBuilder: MetaBuilderT = com.capnproto.schema.Node.Builder
    def setId(value: java.lang.Long): Builder = { struct.setLong(0, value); this }
    def setDisplayName(value: String): Builder = { struct.setString(0, value); this }
    def setDisplayNamePrefixLength(value: java.lang.Integer): Builder = { struct.setInt(2, value); this }
    def setScopeId(value: java.lang.Long): Builder = { struct.setLong(2, value); this }
    def initNestedNodes(count: Int): Seq[com.capnproto.schema.Node.NestedNode.Builder] = {
      val list = struct.initPointerList(1, count, com.capnproto.schema.Node.NestedNode.Builder)
      Range(0, count).map(i => new com.capnproto.schema.Node.NestedNode.Builder(list.initStruct(i, com.capnproto.schema.Node.NestedNode.Builder)))
    }
    def setNestedNodes(buildFn: CapnpArenaBuilder => Seq[com.capnproto.schema.Node.NestedNode.Builder]): Builder = { struct.setStructList(1, com.capnproto.schema.Node.NestedNode.Builder, buildFn(struct.arena).map(_.struct)); this }
    def initAnnotations(count: Int): Seq[com.capnproto.schema.Annotation.Builder] = {
      val list = struct.initPointerList(2, count, com.capnproto.schema.Annotation.Builder)
      Range(0, count).map(i => new com.capnproto.schema.Annotation.Builder(list.initStruct(i, com.capnproto.schema.Annotation.Builder)))
    }
    def setAnnotations(buildFn: CapnpArenaBuilder => Seq[com.capnproto.schema.Annotation.Builder]): Builder = { struct.setStructList(2, com.capnproto.schema.Annotation.Builder, buildFn(struct.arena).map(_.struct)); this }
    def setFile(value: Unit): Builder = { struct.setNone(); struct.setShort(6, -1); this }
    override def __struct: com.capnproto.schema.Node.__Struct.Builder = new com.capnproto.schema.Node.__Struct.Builder(struct)
    override def __enum: com.capnproto.schema.Node.__Enum.Builder = new com.capnproto.schema.Node.__Enum.Builder(struct)
    override def interface: com.capnproto.schema.Node.Interface.Builder = new com.capnproto.schema.Node.Interface.Builder(struct)
    override def const: com.capnproto.schema.Node.Const.Builder = new com.capnproto.schema.Node.Const.Builder(struct)
    override def annotation: com.capnproto.schema.Node.Annotation.Builder = new com.capnproto.schema.Node.Annotation.Builder(struct)
  }

  object NestedNode extends MetaStruct[NestedNode] {
    override type Self = NestedNode.type
    override val recordName: String = "NestedNode"
    override def create(struct: CapnpStruct): NestedNode = new NestedNodeMutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.schema.Node.NestedNode.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.schema.Node.NestedNode.Builder.dataSectionSizeWords, com.capnproto.schema.Node.NestedNode.Builder.pointerSectionSizeWords)
      new com.capnproto.schema.Node.NestedNode.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.schema.Node.NestedNode, com.capnproto.schema.Node.NestedNode.Builder] {
      override type Self = com.capnproto.schema.Node.NestedNode.Builder.type
      override val recordName: String = "NestedNode"
      override val dataSectionSizeWords: Short = 1
      override val pointerSectionSizeWords: Short = 1
      override def create(struct: CapnpStructBuilder): com.capnproto.schema.Node.NestedNode.Builder = new com.capnproto.schema.Node.NestedNode.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.schema.Node.NestedNode.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.schema.Node.NestedNodeMutable(struct) with StructBuilder[com.capnproto.schema.Node.NestedNode, com.capnproto.schema.Node.NestedNode.Builder] {
      override type MetaBuilderT = com.capnproto.schema.Node.NestedNode.Builder.type

      override def meta: NestedNode.type = NestedNode
      override def metaBuilder: MetaBuilderT = com.capnproto.schema.Node.NestedNode.Builder
      def setName(value: String): Builder = { struct.setString(0, value); this }
      def setId(value: java.lang.Long): Builder = { struct.setLong(0, value); this }
    }



    val name = new FieldDescriptor[String, NestedNode, NestedNode.type](
      name = "name",
      meta = NestedNode,
      getter = _.name,
      manifest = manifest[String],
      isUnion = false
    )

    val id = new FieldDescriptor[java.lang.Long, NestedNode, NestedNode.type](
      name = "id",
      meta = NestedNode,
      getter = _.id,
      manifest = manifest[java.lang.Long],
      isUnion = false
    )
    override val fields: Seq[FieldDescriptor[_, NestedNode, NestedNode.type]] = Seq(name, id)
  }

  trait NestedNode extends Struct[NestedNode] {
    override type MetaT = NestedNode.type

    override def meta: NestedNode.type = NestedNode
    def struct: CapnpStruct

    def name: Option[String]
    def id: Option[java.lang.Long]
  }

  trait NestedNodeProxy extends NestedNode {
    def underlying: NestedNode

    override def struct: CapnpStruct = underlying.struct

    override def name: Option[String]
    override def id: Option[java.lang.Long]
  }

  class NestedNodeMutable(override val struct: CapnpStruct) extends NestedNode {

    override def name: Option[String] = struct.getString(0)
    override def id: Option[java.lang.Long] = struct.getLong(0)
  }
  sealed trait Union extends UnionValue[com.capnproto.schema.Node.Union]
  object Union extends UnionMeta[com.capnproto.schema.Node.Union] {
    case class Unknown(discriminant: Short) extends com.capnproto.schema.Node.Union
    case class file(value: Option[Unit]) extends com.capnproto.schema.Node.Union
    case class __struct(value: com.capnproto.schema.Node.__Struct) extends com.capnproto.schema.Node.Union
    case class __enum(value: com.capnproto.schema.Node.__Enum) extends com.capnproto.schema.Node.Union
    case class interface(value: com.capnproto.schema.Node.Interface) extends com.capnproto.schema.Node.Union
    case class const(value: com.capnproto.schema.Node.Const) extends com.capnproto.schema.Node.Union
    case class annotation(value: com.capnproto.schema.Node.Annotation) extends com.capnproto.schema.Node.Union
  }


  object __Struct extends MetaStruct[__Struct] {
    override type Self = __Struct.type
    override val recordName: String = "__Struct"
    override def create(struct: CapnpStruct): __Struct = new __StructMutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.schema.Node.__Struct.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.schema.Node.__Struct.Builder.dataSectionSizeWords, com.capnproto.schema.Node.__Struct.Builder.pointerSectionSizeWords)
      new com.capnproto.schema.Node.__Struct.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.schema.Node.__Struct, com.capnproto.schema.Node.__Struct.Builder] {
      override type Self = com.capnproto.schema.Node.__Struct.Builder.type
      override val recordName: String = "__Struct"
      override val dataSectionSizeWords: Short = 5
      override val pointerSectionSizeWords: Short = 5
      override def create(struct: CapnpStructBuilder): com.capnproto.schema.Node.__Struct.Builder = new com.capnproto.schema.Node.__Struct.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.schema.Node.__Struct.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.schema.Node.__StructMutable(struct) with StructBuilder[com.capnproto.schema.Node.__Struct, com.capnproto.schema.Node.__Struct.Builder] {
      override type MetaBuilderT = com.capnproto.schema.Node.__Struct.Builder.type

      override def meta: __Struct.type = __Struct
      override def metaBuilder: MetaBuilderT = com.capnproto.schema.Node.__Struct.Builder
      def setDataWordCount(value: java.lang.Short): Builder = { struct.setShort(7, value); this }
      def setPointerCount(value: java.lang.Short): Builder = { struct.setShort(12, value); this }
      def setPreferredListEncoding(value: com.capnproto.schema.ElementSize): Builder = { struct.setShort(13, value.id.toShort); this }
      def setIsGroup(value: java.lang.Boolean): Builder = { struct.setBoolean(224, value); this }
      def setDiscriminantCount(value: java.lang.Short): Builder = { struct.setShort(15, value); this }
      def setDiscriminantOffset(value: java.lang.Integer): Builder = { struct.setInt(8, value); this }
      def init__Fields(count: Int): Seq[com.capnproto.schema.Field.Builder] = {
        val list = struct.initPointerList(3, count, com.capnproto.schema.Field.Builder)
        Range(0, count).map(i => new com.capnproto.schema.Field.Builder(list.initStruct(i, com.capnproto.schema.Field.Builder)))
      }
      def set__Fields(buildFn: CapnpArenaBuilder => Seq[com.capnproto.schema.Field.Builder]): Builder = { struct.setStructList(3, com.capnproto.schema.Field.Builder, buildFn(struct.arena).map(_.struct)); this }
    }



    val dataWordCount = new FieldDescriptor[java.lang.Short, __Struct, __Struct.type](
      name = "dataWordCount",
      meta = __Struct,
      getter = _.dataWordCount,
      manifest = manifest[java.lang.Short],
      isUnion = false
    )

    val pointerCount = new FieldDescriptor[java.lang.Short, __Struct, __Struct.type](
      name = "pointerCount",
      meta = __Struct,
      getter = _.pointerCount,
      manifest = manifest[java.lang.Short],
      isUnion = false
    )

    val preferredListEncoding = new FieldDescriptor[com.capnproto.schema.ElementSize, __Struct, __Struct.type](
      name = "preferredListEncoding",
      meta = __Struct,
      getter = _.preferredListEncoding,
      manifest = manifest[com.capnproto.schema.ElementSize],
      isUnion = false
    )

    val isGroup = new FieldDescriptor[java.lang.Boolean, __Struct, __Struct.type](
      name = "isGroup",
      meta = __Struct,
      getter = _.isGroup,
      manifest = manifest[java.lang.Boolean],
      isUnion = false
    )

    val discriminantCount = new FieldDescriptor[java.lang.Short, __Struct, __Struct.type](
      name = "discriminantCount",
      meta = __Struct,
      getter = _.discriminantCount,
      manifest = manifest[java.lang.Short],
      isUnion = false
    )

    val discriminantOffset = new FieldDescriptor[java.lang.Integer, __Struct, __Struct.type](
      name = "discriminantOffset",
      meta = __Struct,
      getter = _.discriminantOffset,
      manifest = manifest[java.lang.Integer],
      isUnion = false
    )

    val __fields = new FieldDescriptor[Seq[com.capnproto.schema.Field], __Struct, __Struct.type](
      name = "fields",
      meta = __Struct,
      getter = x => Some(x.__fields),
      manifest = manifest[Seq[com.capnproto.schema.Field]],
      isUnion = false
    )
    override val fields: Seq[FieldDescriptor[_, __Struct, __Struct.type]] = Seq(dataWordCount, pointerCount, preferredListEncoding, isGroup, discriminantCount, discriminantOffset, __fields)
  }

  trait __Struct extends Struct[__Struct] {
    override type MetaT = __Struct.type

    override def meta: __Struct.type = __Struct
    def struct: CapnpStruct

    def dataWordCount: Option[java.lang.Short]
    def pointerCount: Option[java.lang.Short]
    def preferredListEncoding: Option[com.capnproto.schema.ElementSize]
    def isGroup: Option[java.lang.Boolean]
    def discriminantCount: Option[java.lang.Short]
    def discriminantOffset: Option[java.lang.Integer]
    def __fields: Seq[com.capnproto.schema.Field]
  }

  trait __StructProxy extends __Struct {
    def underlying: __Struct

    override def struct: CapnpStruct = underlying.struct

    override def dataWordCount: Option[java.lang.Short]
    override def pointerCount: Option[java.lang.Short]
    override def preferredListEncoding: Option[com.capnproto.schema.ElementSize]
    override def isGroup: Option[java.lang.Boolean]
    override def discriminantCount: Option[java.lang.Short]
    override def discriminantOffset: Option[java.lang.Integer]
    override def __fields: Seq[com.capnproto.schema.Field]
  }

  class __StructMutable(override val struct: CapnpStruct) extends __Struct {

    override def dataWordCount: Option[java.lang.Short] = struct.getShort(7)
    override def pointerCount: Option[java.lang.Short] = struct.getShort(12)
    override def preferredListEncoding: Option[com.capnproto.schema.ElementSize] = struct.getShort(13).map(id => com.capnproto.schema.ElementSize.findById(id.toInt).getOrElse(com.capnproto.schema.ElementSize.Unknown(id.toShort)))
    override def isGroup: Option[java.lang.Boolean] = struct.getBoolean(224)
    override def discriminantCount: Option[java.lang.Short] = struct.getShort(15)
    override def discriminantOffset: Option[java.lang.Integer] = struct.getInt(8)
    override def __fields: Seq[com.capnproto.schema.Field] = struct.getStructList(3).map(new com.capnproto.schema.FieldMutable(_))
  }
  object __Enum extends MetaStruct[__Enum] {
    override type Self = __Enum.type
    override val recordName: String = "__Enum"
    override def create(struct: CapnpStruct): __Enum = new __EnumMutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.schema.Node.__Enum.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.schema.Node.__Enum.Builder.dataSectionSizeWords, com.capnproto.schema.Node.__Enum.Builder.pointerSectionSizeWords)
      new com.capnproto.schema.Node.__Enum.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.schema.Node.__Enum, com.capnproto.schema.Node.__Enum.Builder] {
      override type Self = com.capnproto.schema.Node.__Enum.Builder.type
      override val recordName: String = "__Enum"
      override val dataSectionSizeWords: Short = 5
      override val pointerSectionSizeWords: Short = 5
      override def create(struct: CapnpStructBuilder): com.capnproto.schema.Node.__Enum.Builder = new com.capnproto.schema.Node.__Enum.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.schema.Node.__Enum.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.schema.Node.__EnumMutable(struct) with StructBuilder[com.capnproto.schema.Node.__Enum, com.capnproto.schema.Node.__Enum.Builder] {
      override type MetaBuilderT = com.capnproto.schema.Node.__Enum.Builder.type

      override def meta: __Enum.type = __Enum
      override def metaBuilder: MetaBuilderT = com.capnproto.schema.Node.__Enum.Builder
      def initEnumerants(count: Int): Seq[com.capnproto.schema.Enumerant.Builder] = {
        val list = struct.initPointerList(3, count, com.capnproto.schema.Enumerant.Builder)
        Range(0, count).map(i => new com.capnproto.schema.Enumerant.Builder(list.initStruct(i, com.capnproto.schema.Enumerant.Builder)))
      }
      def setEnumerants(buildFn: CapnpArenaBuilder => Seq[com.capnproto.schema.Enumerant.Builder]): Builder = { struct.setStructList(3, com.capnproto.schema.Enumerant.Builder, buildFn(struct.arena).map(_.struct)); this }
    }



    val enumerants = new FieldDescriptor[Seq[com.capnproto.schema.Enumerant], __Enum, __Enum.type](
      name = "enumerants",
      meta = __Enum,
      getter = x => Some(x.enumerants),
      manifest = manifest[Seq[com.capnproto.schema.Enumerant]],
      isUnion = false
    )
    override val fields: Seq[FieldDescriptor[_, __Enum, __Enum.type]] = Seq(enumerants)
  }

  trait __Enum extends Struct[__Enum] {
    override type MetaT = __Enum.type

    override def meta: __Enum.type = __Enum
    def struct: CapnpStruct

    def enumerants: Seq[com.capnproto.schema.Enumerant]
  }

  trait __EnumProxy extends __Enum {
    def underlying: __Enum

    override def struct: CapnpStruct = underlying.struct

    override def enumerants: Seq[com.capnproto.schema.Enumerant]
  }

  class __EnumMutable(override val struct: CapnpStruct) extends __Enum {

    override def enumerants: Seq[com.capnproto.schema.Enumerant] = struct.getStructList(3).map(new com.capnproto.schema.EnumerantMutable(_))
  }
  object Interface extends MetaStruct[Interface] {
    override type Self = Interface.type
    override val recordName: String = "Interface"
    override def create(struct: CapnpStruct): Interface = new InterfaceMutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.schema.Node.Interface.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.schema.Node.Interface.Builder.dataSectionSizeWords, com.capnproto.schema.Node.Interface.Builder.pointerSectionSizeWords)
      new com.capnproto.schema.Node.Interface.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.schema.Node.Interface, com.capnproto.schema.Node.Interface.Builder] {
      override type Self = com.capnproto.schema.Node.Interface.Builder.type
      override val recordName: String = "Interface"
      override val dataSectionSizeWords: Short = 5
      override val pointerSectionSizeWords: Short = 5
      override def create(struct: CapnpStructBuilder): com.capnproto.schema.Node.Interface.Builder = new com.capnproto.schema.Node.Interface.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.schema.Node.Interface.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.schema.Node.InterfaceMutable(struct) with StructBuilder[com.capnproto.schema.Node.Interface, com.capnproto.schema.Node.Interface.Builder] {
      override type MetaBuilderT = com.capnproto.schema.Node.Interface.Builder.type

      override def meta: Interface.type = Interface
      override def metaBuilder: MetaBuilderT = com.capnproto.schema.Node.Interface.Builder
      def initMethods(count: Int): Seq[com.capnproto.schema.Method.Builder] = {
        val list = struct.initPointerList(3, count, com.capnproto.schema.Method.Builder)
        Range(0, count).map(i => new com.capnproto.schema.Method.Builder(list.initStruct(i, com.capnproto.schema.Method.Builder)))
      }
      def setMethods(buildFn: CapnpArenaBuilder => Seq[com.capnproto.schema.Method.Builder]): Builder = { struct.setStructList(3, com.capnproto.schema.Method.Builder, buildFn(struct.arena).map(_.struct)); this }
    }



    val methods = new FieldDescriptor[Seq[com.capnproto.schema.Method], Interface, Interface.type](
      name = "methods",
      meta = Interface,
      getter = x => Some(x.methods),
      manifest = manifest[Seq[com.capnproto.schema.Method]],
      isUnion = false
    )
    override val fields: Seq[FieldDescriptor[_, Interface, Interface.type]] = Seq(methods)
  }

  trait Interface extends Struct[Interface] {
    override type MetaT = Interface.type

    override def meta: Interface.type = Interface
    def struct: CapnpStruct

    def methods: Seq[com.capnproto.schema.Method]
  }

  trait InterfaceProxy extends Interface {
    def underlying: Interface

    override def struct: CapnpStruct = underlying.struct

    override def methods: Seq[com.capnproto.schema.Method]
  }

  class InterfaceMutable(override val struct: CapnpStruct) extends Interface {

    override def methods: Seq[com.capnproto.schema.Method] = struct.getStructList(3).map(new com.capnproto.schema.MethodMutable(_))
  }
  object Const extends MetaStruct[Const] {
    override type Self = Const.type
    override val recordName: String = "Const"
    override def create(struct: CapnpStruct): Const = new ConstMutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.schema.Node.Const.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.schema.Node.Const.Builder.dataSectionSizeWords, com.capnproto.schema.Node.Const.Builder.pointerSectionSizeWords)
      new com.capnproto.schema.Node.Const.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.schema.Node.Const, com.capnproto.schema.Node.Const.Builder] {
      override type Self = com.capnproto.schema.Node.Const.Builder.type
      override val recordName: String = "Const"
      override val dataSectionSizeWords: Short = 5
      override val pointerSectionSizeWords: Short = 5
      override def create(struct: CapnpStructBuilder): com.capnproto.schema.Node.Const.Builder = new com.capnproto.schema.Node.Const.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.schema.Node.Const.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.schema.Node.ConstMutable(struct) with StructBuilder[com.capnproto.schema.Node.Const, com.capnproto.schema.Node.Const.Builder] {
      override type MetaBuilderT = com.capnproto.schema.Node.Const.Builder.type

      override def meta: Const.type = Const
      override def metaBuilder: MetaBuilderT = com.capnproto.schema.Node.Const.Builder
      def set__Type(value: com.capnproto.schema.__Type): Builder = { struct.setNone(); this }
      def setValue(value: com.capnproto.schema.Value): Builder = { struct.setNone(); this }
    }



    val __type = new FieldDescriptor[com.capnproto.schema.__Type, Const, Const.type](
      name = "type",
      meta = Const,
      getter = _.__type,
      manifest = manifest[com.capnproto.schema.__Type],
      isUnion = false
    )

    val value = new FieldDescriptor[com.capnproto.schema.Value, Const, Const.type](
      name = "value",
      meta = Const,
      getter = _.value,
      manifest = manifest[com.capnproto.schema.Value],
      isUnion = false
    )
    override val fields: Seq[FieldDescriptor[_, Const, Const.type]] = Seq(__type, value)
  }

  trait Const extends Struct[Const] {
    override type MetaT = Const.type

    override def meta: Const.type = Const
    def struct: CapnpStruct

    def __type: Option[com.capnproto.schema.__Type]
    def value: Option[com.capnproto.schema.Value]
  }

  trait ConstProxy extends Const {
    def underlying: Const

    override def struct: CapnpStruct = underlying.struct

    override def __type: Option[com.capnproto.schema.__Type]
    override def value: Option[com.capnproto.schema.Value]
  }

  class ConstMutable(override val struct: CapnpStruct) extends Const {

    override def __type: Option[com.capnproto.schema.__Type] = struct.getStruct(3).map(new com.capnproto.schema.__TypeMutable(_))
    override def value: Option[com.capnproto.schema.Value] = struct.getStruct(4).map(new com.capnproto.schema.ValueMutable(_))
  }
  object Annotation extends MetaStruct[Annotation] {
    override type Self = Annotation.type
    override val recordName: String = "Annotation"
    override def create(struct: CapnpStruct): Annotation = new AnnotationMutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.schema.Node.Annotation.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.schema.Node.Annotation.Builder.dataSectionSizeWords, com.capnproto.schema.Node.Annotation.Builder.pointerSectionSizeWords)
      new com.capnproto.schema.Node.Annotation.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.schema.Node.Annotation, com.capnproto.schema.Node.Annotation.Builder] {
      override type Self = com.capnproto.schema.Node.Annotation.Builder.type
      override val recordName: String = "Annotation"
      override val dataSectionSizeWords: Short = 5
      override val pointerSectionSizeWords: Short = 5
      override def create(struct: CapnpStructBuilder): com.capnproto.schema.Node.Annotation.Builder = new com.capnproto.schema.Node.Annotation.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.schema.Node.Annotation.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.schema.Node.AnnotationMutable(struct) with StructBuilder[com.capnproto.schema.Node.Annotation, com.capnproto.schema.Node.Annotation.Builder] {
      override type MetaBuilderT = com.capnproto.schema.Node.Annotation.Builder.type

      override def meta: Annotation.type = Annotation
      override def metaBuilder: MetaBuilderT = com.capnproto.schema.Node.Annotation.Builder
      def set__Type(value: com.capnproto.schema.__Type): Builder = { struct.setNone(); this }
      def setTargetsFile(value: java.lang.Boolean): Builder = { struct.setBoolean(112, value); this }
      def setTargetsConst(value: java.lang.Boolean): Builder = { struct.setBoolean(113, value); this }
      def setTargetsEnum(value: java.lang.Boolean): Builder = { struct.setBoolean(114, value); this }
      def setTargetsEnumerant(value: java.lang.Boolean): Builder = { struct.setBoolean(115, value); this }
      def setTargetsStruct(value: java.lang.Boolean): Builder = { struct.setBoolean(116, value); this }
      def setTargetsField(value: java.lang.Boolean): Builder = { struct.setBoolean(117, value); this }
      def setTargetsUnion(value: java.lang.Boolean): Builder = { struct.setBoolean(118, value); this }
      def setTargetsGroup(value: java.lang.Boolean): Builder = { struct.setBoolean(119, value); this }
      def setTargetsInterface(value: java.lang.Boolean): Builder = { struct.setBoolean(120, value); this }
      def setTargetsMethod(value: java.lang.Boolean): Builder = { struct.setBoolean(121, value); this }
      def setTargetsParam(value: java.lang.Boolean): Builder = { struct.setBoolean(122, value); this }
      def setTargetsAnnotation(value: java.lang.Boolean): Builder = { struct.setBoolean(123, value); this }
    }



    val __type = new FieldDescriptor[com.capnproto.schema.__Type, Annotation, Annotation.type](
      name = "type",
      meta = Annotation,
      getter = _.__type,
      manifest = manifest[com.capnproto.schema.__Type],
      isUnion = false
    )

    val targetsFile = new FieldDescriptor[java.lang.Boolean, Annotation, Annotation.type](
      name = "targetsFile",
      meta = Annotation,
      getter = _.targetsFile,
      manifest = manifest[java.lang.Boolean],
      isUnion = false
    )

    val targetsConst = new FieldDescriptor[java.lang.Boolean, Annotation, Annotation.type](
      name = "targetsConst",
      meta = Annotation,
      getter = _.targetsConst,
      manifest = manifest[java.lang.Boolean],
      isUnion = false
    )

    val targetsEnum = new FieldDescriptor[java.lang.Boolean, Annotation, Annotation.type](
      name = "targetsEnum",
      meta = Annotation,
      getter = _.targetsEnum,
      manifest = manifest[java.lang.Boolean],
      isUnion = false
    )

    val targetsEnumerant = new FieldDescriptor[java.lang.Boolean, Annotation, Annotation.type](
      name = "targetsEnumerant",
      meta = Annotation,
      getter = _.targetsEnumerant,
      manifest = manifest[java.lang.Boolean],
      isUnion = false
    )

    val targetsStruct = new FieldDescriptor[java.lang.Boolean, Annotation, Annotation.type](
      name = "targetsStruct",
      meta = Annotation,
      getter = _.targetsStruct,
      manifest = manifest[java.lang.Boolean],
      isUnion = false
    )

    val targetsField = new FieldDescriptor[java.lang.Boolean, Annotation, Annotation.type](
      name = "targetsField",
      meta = Annotation,
      getter = _.targetsField,
      manifest = manifest[java.lang.Boolean],
      isUnion = false
    )

    val targetsUnion = new FieldDescriptor[java.lang.Boolean, Annotation, Annotation.type](
      name = "targetsUnion",
      meta = Annotation,
      getter = _.targetsUnion,
      manifest = manifest[java.lang.Boolean],
      isUnion = false
    )

    val targetsGroup = new FieldDescriptor[java.lang.Boolean, Annotation, Annotation.type](
      name = "targetsGroup",
      meta = Annotation,
      getter = _.targetsGroup,
      manifest = manifest[java.lang.Boolean],
      isUnion = false
    )

    val targetsInterface = new FieldDescriptor[java.lang.Boolean, Annotation, Annotation.type](
      name = "targetsInterface",
      meta = Annotation,
      getter = _.targetsInterface,
      manifest = manifest[java.lang.Boolean],
      isUnion = false
    )

    val targetsMethod = new FieldDescriptor[java.lang.Boolean, Annotation, Annotation.type](
      name = "targetsMethod",
      meta = Annotation,
      getter = _.targetsMethod,
      manifest = manifest[java.lang.Boolean],
      isUnion = false
    )

    val targetsParam = new FieldDescriptor[java.lang.Boolean, Annotation, Annotation.type](
      name = "targetsParam",
      meta = Annotation,
      getter = _.targetsParam,
      manifest = manifest[java.lang.Boolean],
      isUnion = false
    )

    val targetsAnnotation = new FieldDescriptor[java.lang.Boolean, Annotation, Annotation.type](
      name = "targetsAnnotation",
      meta = Annotation,
      getter = _.targetsAnnotation,
      manifest = manifest[java.lang.Boolean],
      isUnion = false
    )
    override val fields: Seq[FieldDescriptor[_, Annotation, Annotation.type]] = Seq(__type, targetsFile, targetsConst, targetsEnum, targetsEnumerant, targetsStruct, targetsField, targetsUnion, targetsGroup, targetsInterface, targetsMethod, targetsParam, targetsAnnotation)
  }

  trait Annotation extends Struct[Annotation] {
    override type MetaT = Annotation.type

    override def meta: Annotation.type = Annotation
    def struct: CapnpStruct

    def __type: Option[com.capnproto.schema.__Type]
    def targetsFile: Option[java.lang.Boolean]
    def targetsConst: Option[java.lang.Boolean]
    def targetsEnum: Option[java.lang.Boolean]
    def targetsEnumerant: Option[java.lang.Boolean]
    def targetsStruct: Option[java.lang.Boolean]
    def targetsField: Option[java.lang.Boolean]
    def targetsUnion: Option[java.lang.Boolean]
    def targetsGroup: Option[java.lang.Boolean]
    def targetsInterface: Option[java.lang.Boolean]
    def targetsMethod: Option[java.lang.Boolean]
    def targetsParam: Option[java.lang.Boolean]
    def targetsAnnotation: Option[java.lang.Boolean]
  }

  trait AnnotationProxy extends Annotation {
    def underlying: Annotation

    override def struct: CapnpStruct = underlying.struct

    override def __type: Option[com.capnproto.schema.__Type]
    override def targetsFile: Option[java.lang.Boolean]
    override def targetsConst: Option[java.lang.Boolean]
    override def targetsEnum: Option[java.lang.Boolean]
    override def targetsEnumerant: Option[java.lang.Boolean]
    override def targetsStruct: Option[java.lang.Boolean]
    override def targetsField: Option[java.lang.Boolean]
    override def targetsUnion: Option[java.lang.Boolean]
    override def targetsGroup: Option[java.lang.Boolean]
    override def targetsInterface: Option[java.lang.Boolean]
    override def targetsMethod: Option[java.lang.Boolean]
    override def targetsParam: Option[java.lang.Boolean]
    override def targetsAnnotation: Option[java.lang.Boolean]
  }

  class AnnotationMutable(override val struct: CapnpStruct) extends Annotation {

    override def __type: Option[com.capnproto.schema.__Type] = struct.getStruct(3).map(new com.capnproto.schema.__TypeMutable(_))
    override def targetsFile: Option[java.lang.Boolean] = struct.getBoolean(112)
    override def targetsConst: Option[java.lang.Boolean] = struct.getBoolean(113)
    override def targetsEnum: Option[java.lang.Boolean] = struct.getBoolean(114)
    override def targetsEnumerant: Option[java.lang.Boolean] = struct.getBoolean(115)
    override def targetsStruct: Option[java.lang.Boolean] = struct.getBoolean(116)
    override def targetsField: Option[java.lang.Boolean] = struct.getBoolean(117)
    override def targetsUnion: Option[java.lang.Boolean] = struct.getBoolean(118)
    override def targetsGroup: Option[java.lang.Boolean] = struct.getBoolean(119)
    override def targetsInterface: Option[java.lang.Boolean] = struct.getBoolean(120)
    override def targetsMethod: Option[java.lang.Boolean] = struct.getBoolean(121)
    override def targetsParam: Option[java.lang.Boolean] = struct.getBoolean(122)
    override def targetsAnnotation: Option[java.lang.Boolean] = struct.getBoolean(123)
  }

  val id = new FieldDescriptor[java.lang.Long, Node, Node.type](
    name = "id",
    meta = Node,
    getter = _.id,
    manifest = manifest[java.lang.Long],
    isUnion = false
  )

  val displayName = new FieldDescriptor[String, Node, Node.type](
    name = "displayName",
    meta = Node,
    getter = _.displayName,
    manifest = manifest[String],
    isUnion = false
  )

  val displayNamePrefixLength = new FieldDescriptor[java.lang.Integer, Node, Node.type](
    name = "displayNamePrefixLength",
    meta = Node,
    getter = _.displayNamePrefixLength,
    manifest = manifest[java.lang.Integer],
    isUnion = false
  )

  val scopeId = new FieldDescriptor[java.lang.Long, Node, Node.type](
    name = "scopeId",
    meta = Node,
    getter = _.scopeId,
    manifest = manifest[java.lang.Long],
    isUnion = false
  )

  val nestedNodes = new FieldDescriptor[Seq[com.capnproto.schema.Node.NestedNode], Node, Node.type](
    name = "nestedNodes",
    meta = Node,
    getter = x => Some(x.nestedNodes),
    manifest = manifest[Seq[com.capnproto.schema.Node.NestedNode]],
    isUnion = false
  )

  val annotations = new FieldDescriptor[Seq[com.capnproto.schema.Annotation], Node, Node.type](
    name = "annotations",
    meta = Node,
    getter = x => Some(x.annotations),
    manifest = manifest[Seq[com.capnproto.schema.Annotation]],
    isUnion = false
  )

  val file = new FieldDescriptor[Unit, Node, Node.type](
    name = "file",
    meta = Node,
    getter = _.file,
    manifest = manifest[Unit],
    isUnion = true
  )

  val __struct = new FieldDescriptor[com.capnproto.schema.Node.__Struct, Node, Node.type](
    name = "struct",
    meta = Node,
    getter = x => Some(x.__struct),
    manifest = manifest[com.capnproto.schema.Node.__Struct],
    isUnion = true
  )

  val __enum = new FieldDescriptor[com.capnproto.schema.Node.__Enum, Node, Node.type](
    name = "enum",
    meta = Node,
    getter = x => Some(x.__enum),
    manifest = manifest[com.capnproto.schema.Node.__Enum],
    isUnion = true
  )

  val interface = new FieldDescriptor[com.capnproto.schema.Node.Interface, Node, Node.type](
    name = "interface",
    meta = Node,
    getter = x => Some(x.interface),
    manifest = manifest[com.capnproto.schema.Node.Interface],
    isUnion = true
  )

  val const = new FieldDescriptor[com.capnproto.schema.Node.Const, Node, Node.type](
    name = "const",
    meta = Node,
    getter = x => Some(x.const),
    manifest = manifest[com.capnproto.schema.Node.Const],
    isUnion = true
  )

  val annotation = new FieldDescriptor[com.capnproto.schema.Node.Annotation, Node, Node.type](
    name = "annotation",
    meta = Node,
    getter = x => Some(x.annotation),
    manifest = manifest[com.capnproto.schema.Node.Annotation],
    isUnion = true
  )
  override val fields: Seq[FieldDescriptor[_, Node, Node.type]] = Seq(id, displayName, displayNamePrefixLength, scopeId, nestedNodes, annotations, file, __struct, __enum, interface, const, annotation)
}

trait Node extends Struct[Node] with HasUnion[com.capnproto.schema.Node.Union] {
  override type MetaT = Node.type

  override def meta: Node.type = Node
  def struct: CapnpStruct

  def id: Option[java.lang.Long]
  def displayName: Option[String]
  def displayNamePrefixLength: Option[java.lang.Integer]
  def scopeId: Option[java.lang.Long]
  def nestedNodes: Seq[com.capnproto.schema.Node.NestedNode]
  def annotations: Seq[com.capnproto.schema.Annotation]
  def file: Option[Unit]
  def __struct: com.capnproto.schema.Node.__Struct
  def __enum: com.capnproto.schema.Node.__Enum
  def interface: com.capnproto.schema.Node.Interface
  def const: com.capnproto.schema.Node.Const
  def annotation: com.capnproto.schema.Node.Annotation
}

trait NodeProxy extends Node with HasUnion[com.capnproto.schema.Node.Union] {
  def underlying: Node

  override def struct: CapnpStruct = underlying.struct

  override def switch: com.capnproto.schema.Node.Union = underlying.switch
  override def union: UnionMeta[com.capnproto.schema.Node.Union] = underlying.union

  override def id: Option[java.lang.Long]
  override def displayName: Option[String]
  override def displayNamePrefixLength: Option[java.lang.Integer]
  override def scopeId: Option[java.lang.Long]
  override def nestedNodes: Seq[com.capnproto.schema.Node.NestedNode]
  override def annotations: Seq[com.capnproto.schema.Annotation]
  override def file: Option[Unit]
  override def __struct: com.capnproto.schema.Node.__Struct
  override def __enum: com.capnproto.schema.Node.__Enum
  override def interface: com.capnproto.schema.Node.Interface
  override def const: com.capnproto.schema.Node.Const
  override def annotation: com.capnproto.schema.Node.Annotation
}

class NodeMutable(override val struct: CapnpStruct) extends Node {

  override def discriminant: Short = (struct.getShort(6).getOrElse(new java.lang.Short(0.toShort)): java.lang.Short)
  override def switch: com.capnproto.schema.Node.Union = discriminant match {
    case 0 => com.capnproto.schema.Node.Union.file(file)
    case 1 => com.capnproto.schema.Node.Union.__struct(__struct)
    case 2 => com.capnproto.schema.Node.Union.__enum(__enum)
    case 3 => com.capnproto.schema.Node.Union.interface(interface)
    case 4 => com.capnproto.schema.Node.Union.const(const)
    case 5 => com.capnproto.schema.Node.Union.annotation(annotation)
    case d => com.capnproto.schema.Node.Union.Unknown(d)
  }
  override def union: UnionMeta[com.capnproto.schema.Node.Union] = com.capnproto.schema.Node.Union

  override def id: Option[java.lang.Long] = struct.getLong(0)
  override def displayName: Option[String] = struct.getString(0)
  override def displayNamePrefixLength: Option[java.lang.Integer] = struct.getInt(2)
  override def scopeId: Option[java.lang.Long] = struct.getLong(2)
  override def nestedNodes: Seq[com.capnproto.schema.Node.NestedNode] = struct.getStructList(1).map(new com.capnproto.schema.Node.NestedNodeMutable(_))
  override def annotations: Seq[com.capnproto.schema.Annotation] = struct.getStructList(2).map(new com.capnproto.schema.AnnotationMutable(_))
  override def file: Option[Unit] = struct.getNone()
  override def __struct: com.capnproto.schema.Node.__Struct = new com.capnproto.schema.Node.__StructMutable(struct)

  override def __enum: com.capnproto.schema.Node.__Enum = new com.capnproto.schema.Node.__EnumMutable(struct)

  override def interface: com.capnproto.schema.Node.Interface = new com.capnproto.schema.Node.InterfaceMutable(struct)

  override def const: com.capnproto.schema.Node.Const = new com.capnproto.schema.Node.ConstMutable(struct)

  override def annotation: com.capnproto.schema.Node.Annotation = new com.capnproto.schema.Node.AnnotationMutable(struct)

}

object Field extends MetaStruct[Field] {
  override type Self = Field.type
  override val recordName: String = "Field"
  override def create(struct: CapnpStruct): Field = new FieldMutable(struct)
  def newBuilder(arena: CapnpArenaBuilder): com.capnproto.schema.Field.Builder = {
    val (segment, pointerOffset) = arena.allocate(1)
    val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.schema.Field.Builder.dataSectionSizeWords, com.capnproto.schema.Field.Builder.pointerSectionSizeWords)
    new com.capnproto.schema.Field.Builder(struct)
  }

  object Builder extends MetaStructBuilder[com.capnproto.schema.Field, com.capnproto.schema.Field.Builder] {
    override type Self = com.capnproto.schema.Field.Builder.type
    override val recordName: String = "Field"
    override val dataSectionSizeWords: Short = 3
    override val pointerSectionSizeWords: Short = 4
    override def create(struct: CapnpStructBuilder): com.capnproto.schema.Field.Builder = new com.capnproto.schema.Field.Builder(struct)
    override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.schema.Field.fields
  }
  class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.schema.FieldMutable(struct) with StructBuilder[com.capnproto.schema.Field, com.capnproto.schema.Field.Builder] {
    override type MetaBuilderT = com.capnproto.schema.Field.Builder.type

    override def meta: Field.type = Field
    override def metaBuilder: MetaBuilderT = com.capnproto.schema.Field.Builder
    def setName(value: String): Builder = { struct.setString(0, value); this }
    def setCodeOrder(value: java.lang.Short): Builder = { struct.setShort(0, value); this }
    def initAnnotations(count: Int): Seq[com.capnproto.schema.Annotation.Builder] = {
      val list = struct.initPointerList(1, count, com.capnproto.schema.Annotation.Builder)
      Range(0, count).map(i => new com.capnproto.schema.Annotation.Builder(list.initStruct(i, com.capnproto.schema.Annotation.Builder)))
    }
    def setAnnotations(buildFn: CapnpArenaBuilder => Seq[com.capnproto.schema.Annotation.Builder]): Builder = { struct.setStructList(1, com.capnproto.schema.Annotation.Builder, buildFn(struct.arena).map(_.struct)); this }
    def setDiscriminantValue(value: java.lang.Short): Builder = { struct.setShort(1, value); this }
    override def slot: com.capnproto.schema.Field.Slot.Builder = new com.capnproto.schema.Field.Slot.Builder(struct)
    override def group: com.capnproto.schema.Field.Group.Builder = new com.capnproto.schema.Field.Group.Builder(struct)
    override def ordinal: com.capnproto.schema.Field.Ordinal.Builder = new com.capnproto.schema.Field.Ordinal.Builder(struct)
  }

  sealed trait Union extends UnionValue[com.capnproto.schema.Field.Union]
  object Union extends UnionMeta[com.capnproto.schema.Field.Union] {
    case class Unknown(discriminant: Short) extends com.capnproto.schema.Field.Union
    case class slot(value: com.capnproto.schema.Field.Slot) extends com.capnproto.schema.Field.Union
    case class group(value: com.capnproto.schema.Field.Group) extends com.capnproto.schema.Field.Union
  }


  object Slot extends MetaStruct[Slot] {
    override type Self = Slot.type
    override val recordName: String = "Slot"
    override def create(struct: CapnpStruct): Slot = new SlotMutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.schema.Field.Slot.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.schema.Field.Slot.Builder.dataSectionSizeWords, com.capnproto.schema.Field.Slot.Builder.pointerSectionSizeWords)
      new com.capnproto.schema.Field.Slot.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.schema.Field.Slot, com.capnproto.schema.Field.Slot.Builder] {
      override type Self = com.capnproto.schema.Field.Slot.Builder.type
      override val recordName: String = "Slot"
      override val dataSectionSizeWords: Short = 3
      override val pointerSectionSizeWords: Short = 4
      override def create(struct: CapnpStructBuilder): com.capnproto.schema.Field.Slot.Builder = new com.capnproto.schema.Field.Slot.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.schema.Field.Slot.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.schema.Field.SlotMutable(struct) with StructBuilder[com.capnproto.schema.Field.Slot, com.capnproto.schema.Field.Slot.Builder] {
      override type MetaBuilderT = com.capnproto.schema.Field.Slot.Builder.type

      override def meta: Slot.type = Slot
      override def metaBuilder: MetaBuilderT = com.capnproto.schema.Field.Slot.Builder
      def setOffset(value: java.lang.Integer): Builder = { struct.setInt(1, value); this }
      def set__Type(value: com.capnproto.schema.__Type): Builder = { struct.setNone(); this }
      def setDefaultValue(value: com.capnproto.schema.Value): Builder = { struct.setNone(); this }
    }



    val offset = new FieldDescriptor[java.lang.Integer, Slot, Slot.type](
      name = "offset",
      meta = Slot,
      getter = _.offset,
      manifest = manifest[java.lang.Integer],
      isUnion = false
    )

    val __type = new FieldDescriptor[com.capnproto.schema.__Type, Slot, Slot.type](
      name = "type",
      meta = Slot,
      getter = _.__type,
      manifest = manifest[com.capnproto.schema.__Type],
      isUnion = false
    )

    val defaultValue = new FieldDescriptor[com.capnproto.schema.Value, Slot, Slot.type](
      name = "defaultValue",
      meta = Slot,
      getter = _.defaultValue,
      manifest = manifest[com.capnproto.schema.Value],
      isUnion = false
    )
    override val fields: Seq[FieldDescriptor[_, Slot, Slot.type]] = Seq(offset, __type, defaultValue)
  }

  trait Slot extends Struct[Slot] {
    override type MetaT = Slot.type

    override def meta: Slot.type = Slot
    def struct: CapnpStruct

    def offset: Option[java.lang.Integer]
    def __type: Option[com.capnproto.schema.__Type]
    def defaultValue: Option[com.capnproto.schema.Value]
  }

  trait SlotProxy extends Slot {
    def underlying: Slot

    override def struct: CapnpStruct = underlying.struct

    override def offset: Option[java.lang.Integer]
    override def __type: Option[com.capnproto.schema.__Type]
    override def defaultValue: Option[com.capnproto.schema.Value]
  }

  class SlotMutable(override val struct: CapnpStruct) extends Slot {

    override def offset: Option[java.lang.Integer] = struct.getInt(1)
    override def __type: Option[com.capnproto.schema.__Type] = struct.getStruct(2).map(new com.capnproto.schema.__TypeMutable(_))
    override def defaultValue: Option[com.capnproto.schema.Value] = struct.getStruct(3).map(new com.capnproto.schema.ValueMutable(_))
  }
  object Group extends MetaStruct[Group] {
    override type Self = Group.type
    override val recordName: String = "Group"
    override def create(struct: CapnpStruct): Group = new GroupMutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.schema.Field.Group.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.schema.Field.Group.Builder.dataSectionSizeWords, com.capnproto.schema.Field.Group.Builder.pointerSectionSizeWords)
      new com.capnproto.schema.Field.Group.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.schema.Field.Group, com.capnproto.schema.Field.Group.Builder] {
      override type Self = com.capnproto.schema.Field.Group.Builder.type
      override val recordName: String = "Group"
      override val dataSectionSizeWords: Short = 3
      override val pointerSectionSizeWords: Short = 4
      override def create(struct: CapnpStructBuilder): com.capnproto.schema.Field.Group.Builder = new com.capnproto.schema.Field.Group.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.schema.Field.Group.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.schema.Field.GroupMutable(struct) with StructBuilder[com.capnproto.schema.Field.Group, com.capnproto.schema.Field.Group.Builder] {
      override type MetaBuilderT = com.capnproto.schema.Field.Group.Builder.type

      override def meta: Group.type = Group
      override def metaBuilder: MetaBuilderT = com.capnproto.schema.Field.Group.Builder
      def setTypeId(value: java.lang.Long): Builder = { struct.setLong(2, value); this }
    }



    val typeId = new FieldDescriptor[java.lang.Long, Group, Group.type](
      name = "typeId",
      meta = Group,
      getter = _.typeId,
      manifest = manifest[java.lang.Long],
      isUnion = false
    )
    override val fields: Seq[FieldDescriptor[_, Group, Group.type]] = Seq(typeId)
  }

  trait Group extends Struct[Group] {
    override type MetaT = Group.type

    override def meta: Group.type = Group
    def struct: CapnpStruct

    def typeId: Option[java.lang.Long]
  }

  trait GroupProxy extends Group {
    def underlying: Group

    override def struct: CapnpStruct = underlying.struct

    override def typeId: Option[java.lang.Long]
  }

  class GroupMutable(override val struct: CapnpStruct) extends Group {

    override def typeId: Option[java.lang.Long] = struct.getLong(2)
  }
  object Ordinal extends MetaStruct[Ordinal] {
    override type Self = Ordinal.type
    override val recordName: String = "Ordinal"
    override def create(struct: CapnpStruct): Ordinal = new OrdinalMutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.schema.Field.Ordinal.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.schema.Field.Ordinal.Builder.dataSectionSizeWords, com.capnproto.schema.Field.Ordinal.Builder.pointerSectionSizeWords)
      new com.capnproto.schema.Field.Ordinal.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.schema.Field.Ordinal, com.capnproto.schema.Field.Ordinal.Builder] {
      override type Self = com.capnproto.schema.Field.Ordinal.Builder.type
      override val recordName: String = "Ordinal"
      override val dataSectionSizeWords: Short = 3
      override val pointerSectionSizeWords: Short = 4
      override def create(struct: CapnpStructBuilder): com.capnproto.schema.Field.Ordinal.Builder = new com.capnproto.schema.Field.Ordinal.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.schema.Field.Ordinal.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.schema.Field.OrdinalMutable(struct) with StructBuilder[com.capnproto.schema.Field.Ordinal, com.capnproto.schema.Field.Ordinal.Builder] {
      override type MetaBuilderT = com.capnproto.schema.Field.Ordinal.Builder.type

      override def meta: Ordinal.type = Ordinal
      override def metaBuilder: MetaBuilderT = com.capnproto.schema.Field.Ordinal.Builder
      def set__Implicit(value: Unit): Builder = { struct.setNone(); struct.setShort(5, -1); this }
      def setExplicit(value: java.lang.Short): Builder = { struct.setShort(6, value); struct.setShort(5, -2); this }
    }

    sealed trait Union extends UnionValue[com.capnproto.schema.Field.Ordinal.Union]
    object Union extends UnionMeta[com.capnproto.schema.Field.Ordinal.Union] {
      case class Unknown(discriminant: Short) extends com.capnproto.schema.Field.Ordinal.Union
      case class __implicit(value: Option[Unit]) extends com.capnproto.schema.Field.Ordinal.Union
      case class explicit(value: Option[java.lang.Short]) extends com.capnproto.schema.Field.Ordinal.Union
    }



    val __implicit = new FieldDescriptor[Unit, Ordinal, Ordinal.type](
      name = "implicit",
      meta = Ordinal,
      getter = _.__implicit,
      manifest = manifest[Unit],
      isUnion = true
    )

    val explicit = new FieldDescriptor[java.lang.Short, Ordinal, Ordinal.type](
      name = "explicit",
      meta = Ordinal,
      getter = _.explicit,
      manifest = manifest[java.lang.Short],
      isUnion = true
    )
    override val fields: Seq[FieldDescriptor[_, Ordinal, Ordinal.type]] = Seq(__implicit, explicit)
  }

  trait Ordinal extends Struct[Ordinal] with HasUnion[com.capnproto.schema.Field.Ordinal.Union] {
    override type MetaT = Ordinal.type

    override def meta: Ordinal.type = Ordinal
    def struct: CapnpStruct

    def __implicit: Option[Unit]
    def explicit: Option[java.lang.Short]
  }

  trait OrdinalProxy extends Ordinal with HasUnion[com.capnproto.schema.Field.Ordinal.Union] {
    def underlying: Ordinal

    override def struct: CapnpStruct = underlying.struct

    override def switch: com.capnproto.schema.Field.Ordinal.Union = underlying.switch
    override def union: UnionMeta[com.capnproto.schema.Field.Ordinal.Union] = underlying.union

    override def __implicit: Option[Unit]
    override def explicit: Option[java.lang.Short]
  }

  class OrdinalMutable(override val struct: CapnpStruct) extends Ordinal {

    override def discriminant: Short = (struct.getShort(5).getOrElse(new java.lang.Short(0.toShort)): java.lang.Short)
    override def switch: com.capnproto.schema.Field.Ordinal.Union = discriminant match {
      case 0 => com.capnproto.schema.Field.Ordinal.Union.__implicit(__implicit)
      case 1 => com.capnproto.schema.Field.Ordinal.Union.explicit(explicit)
      case d => com.capnproto.schema.Field.Ordinal.Union.Unknown(d)
    }
    override def union: UnionMeta[com.capnproto.schema.Field.Ordinal.Union] = com.capnproto.schema.Field.Ordinal.Union

    override def __implicit: Option[Unit] = struct.getNone()
    override def explicit: Option[java.lang.Short] = struct.getShort(6)
  }

  val name = new FieldDescriptor[String, Field, Field.type](
    name = "name",
    meta = Field,
    getter = _.name,
    manifest = manifest[String],
    isUnion = false
  )

  val codeOrder = new FieldDescriptor[java.lang.Short, Field, Field.type](
    name = "codeOrder",
    meta = Field,
    getter = _.codeOrder,
    manifest = manifest[java.lang.Short],
    isUnion = false
  )

  val annotations = new FieldDescriptor[Seq[com.capnproto.schema.Annotation], Field, Field.type](
    name = "annotations",
    meta = Field,
    getter = x => Some(x.annotations),
    manifest = manifest[Seq[com.capnproto.schema.Annotation]],
    isUnion = false
  )

  val discriminantValue = new FieldDescriptor[java.lang.Short, Field, Field.type](
    name = "discriminantValue",
    meta = Field,
    getter = _.discriminantValue,
    manifest = manifest[java.lang.Short],
    isUnion = false
  )

  val slot = new FieldDescriptor[com.capnproto.schema.Field.Slot, Field, Field.type](
    name = "slot",
    meta = Field,
    getter = x => Some(x.slot),
    manifest = manifest[com.capnproto.schema.Field.Slot],
    isUnion = true
  )

  val group = new FieldDescriptor[com.capnproto.schema.Field.Group, Field, Field.type](
    name = "group",
    meta = Field,
    getter = x => Some(x.group),
    manifest = manifest[com.capnproto.schema.Field.Group],
    isUnion = true
  )

  val ordinal = new FieldDescriptor[com.capnproto.schema.Field.Ordinal, Field, Field.type](
    name = "ordinal",
    meta = Field,
    getter = x => Some(x.ordinal),
    manifest = manifest[com.capnproto.schema.Field.Ordinal],
    isUnion = false
  )
  override val fields: Seq[FieldDescriptor[_, Field, Field.type]] = Seq(name, codeOrder, annotations, discriminantValue, slot, group, ordinal)
}

trait Field extends Struct[Field] with HasUnion[com.capnproto.schema.Field.Union] {
  override type MetaT = Field.type

  override def meta: Field.type = Field
  def struct: CapnpStruct

  def name: Option[String]
  def codeOrder: Option[java.lang.Short]
  def annotations: Seq[com.capnproto.schema.Annotation]
  def discriminantValue: Option[java.lang.Short]
  def slot: com.capnproto.schema.Field.Slot
  def group: com.capnproto.schema.Field.Group
  def ordinal: com.capnproto.schema.Field.Ordinal
}

trait FieldProxy extends Field with HasUnion[com.capnproto.schema.Field.Union] {
  def underlying: Field

  override def struct: CapnpStruct = underlying.struct

  override def switch: com.capnproto.schema.Field.Union = underlying.switch
  override def union: UnionMeta[com.capnproto.schema.Field.Union] = underlying.union

  override def name: Option[String]
  override def codeOrder: Option[java.lang.Short]
  override def annotations: Seq[com.capnproto.schema.Annotation]
  override def discriminantValue: Option[java.lang.Short]
  override def slot: com.capnproto.schema.Field.Slot
  override def group: com.capnproto.schema.Field.Group
  override def ordinal: com.capnproto.schema.Field.Ordinal
}

class FieldMutable(override val struct: CapnpStruct) extends Field {

  override def discriminant: Short = (struct.getShort(4).getOrElse(new java.lang.Short(0.toShort)): java.lang.Short)
  override def switch: com.capnproto.schema.Field.Union = discriminant match {
    case 0 => com.capnproto.schema.Field.Union.slot(slot)
    case 1 => com.capnproto.schema.Field.Union.group(group)
    case d => com.capnproto.schema.Field.Union.Unknown(d)
  }
  override def union: UnionMeta[com.capnproto.schema.Field.Union] = com.capnproto.schema.Field.Union

  override def name: Option[String] = struct.getString(0)
  override def codeOrder: Option[java.lang.Short] = struct.getShort(0)
  override def annotations: Seq[com.capnproto.schema.Annotation] = struct.getStructList(1).map(new com.capnproto.schema.AnnotationMutable(_))
  override def discriminantValue: Option[java.lang.Short] = struct.getShort(1)
  override def slot: com.capnproto.schema.Field.Slot = new com.capnproto.schema.Field.SlotMutable(struct)

  override def group: com.capnproto.schema.Field.Group = new com.capnproto.schema.Field.GroupMutable(struct)

  override def ordinal: com.capnproto.schema.Field.Ordinal = new com.capnproto.schema.Field.OrdinalMutable(struct)

}

object Enumerant extends MetaStruct[Enumerant] {
  override type Self = Enumerant.type
  override val recordName: String = "Enumerant"
  override def create(struct: CapnpStruct): Enumerant = new EnumerantMutable(struct)
  def newBuilder(arena: CapnpArenaBuilder): com.capnproto.schema.Enumerant.Builder = {
    val (segment, pointerOffset) = arena.allocate(1)
    val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.schema.Enumerant.Builder.dataSectionSizeWords, com.capnproto.schema.Enumerant.Builder.pointerSectionSizeWords)
    new com.capnproto.schema.Enumerant.Builder(struct)
  }

  object Builder extends MetaStructBuilder[com.capnproto.schema.Enumerant, com.capnproto.schema.Enumerant.Builder] {
    override type Self = com.capnproto.schema.Enumerant.Builder.type
    override val recordName: String = "Enumerant"
    override val dataSectionSizeWords: Short = 1
    override val pointerSectionSizeWords: Short = 2
    override def create(struct: CapnpStructBuilder): com.capnproto.schema.Enumerant.Builder = new com.capnproto.schema.Enumerant.Builder(struct)
    override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.schema.Enumerant.fields
  }
  class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.schema.EnumerantMutable(struct) with StructBuilder[com.capnproto.schema.Enumerant, com.capnproto.schema.Enumerant.Builder] {
    override type MetaBuilderT = com.capnproto.schema.Enumerant.Builder.type

    override def meta: Enumerant.type = Enumerant
    override def metaBuilder: MetaBuilderT = com.capnproto.schema.Enumerant.Builder
    def setName(value: String): Builder = { struct.setString(0, value); this }
    def setCodeOrder(value: java.lang.Short): Builder = { struct.setShort(0, value); this }
    def initAnnotations(count: Int): Seq[com.capnproto.schema.Annotation.Builder] = {
      val list = struct.initPointerList(1, count, com.capnproto.schema.Annotation.Builder)
      Range(0, count).map(i => new com.capnproto.schema.Annotation.Builder(list.initStruct(i, com.capnproto.schema.Annotation.Builder)))
    }
    def setAnnotations(buildFn: CapnpArenaBuilder => Seq[com.capnproto.schema.Annotation.Builder]): Builder = { struct.setStructList(1, com.capnproto.schema.Annotation.Builder, buildFn(struct.arena).map(_.struct)); this }
  }



  val name = new FieldDescriptor[String, Enumerant, Enumerant.type](
    name = "name",
    meta = Enumerant,
    getter = _.name,
    manifest = manifest[String],
    isUnion = false
  )

  val codeOrder = new FieldDescriptor[java.lang.Short, Enumerant, Enumerant.type](
    name = "codeOrder",
    meta = Enumerant,
    getter = _.codeOrder,
    manifest = manifest[java.lang.Short],
    isUnion = false
  )

  val annotations = new FieldDescriptor[Seq[com.capnproto.schema.Annotation], Enumerant, Enumerant.type](
    name = "annotations",
    meta = Enumerant,
    getter = x => Some(x.annotations),
    manifest = manifest[Seq[com.capnproto.schema.Annotation]],
    isUnion = false
  )
  override val fields: Seq[FieldDescriptor[_, Enumerant, Enumerant.type]] = Seq(name, codeOrder, annotations)
}

trait Enumerant extends Struct[Enumerant] {
  override type MetaT = Enumerant.type

  override def meta: Enumerant.type = Enumerant
  def struct: CapnpStruct

  def name: Option[String]
  def codeOrder: Option[java.lang.Short]
  def annotations: Seq[com.capnproto.schema.Annotation]
}

trait EnumerantProxy extends Enumerant {
  def underlying: Enumerant

  override def struct: CapnpStruct = underlying.struct

  override def name: Option[String]
  override def codeOrder: Option[java.lang.Short]
  override def annotations: Seq[com.capnproto.schema.Annotation]
}

class EnumerantMutable(override val struct: CapnpStruct) extends Enumerant {

  override def name: Option[String] = struct.getString(0)
  override def codeOrder: Option[java.lang.Short] = struct.getShort(0)
  override def annotations: Seq[com.capnproto.schema.Annotation] = struct.getStructList(1).map(new com.capnproto.schema.AnnotationMutable(_))
}

object Method extends MetaStruct[Method] {
  override type Self = Method.type
  override val recordName: String = "Method"
  override def create(struct: CapnpStruct): Method = new MethodMutable(struct)
  def newBuilder(arena: CapnpArenaBuilder): com.capnproto.schema.Method.Builder = {
    val (segment, pointerOffset) = arena.allocate(1)
    val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.schema.Method.Builder.dataSectionSizeWords, com.capnproto.schema.Method.Builder.pointerSectionSizeWords)
    new com.capnproto.schema.Method.Builder(struct)
  }

  object Builder extends MetaStructBuilder[com.capnproto.schema.Method, com.capnproto.schema.Method.Builder] {
    override type Self = com.capnproto.schema.Method.Builder.type
    override val recordName: String = "Method"
    override val dataSectionSizeWords: Short = 1
    override val pointerSectionSizeWords: Short = 4
    override def create(struct: CapnpStructBuilder): com.capnproto.schema.Method.Builder = new com.capnproto.schema.Method.Builder(struct)
    override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.schema.Method.fields
  }
  class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.schema.MethodMutable(struct) with StructBuilder[com.capnproto.schema.Method, com.capnproto.schema.Method.Builder] {
    override type MetaBuilderT = com.capnproto.schema.Method.Builder.type

    override def meta: Method.type = Method
    override def metaBuilder: MetaBuilderT = com.capnproto.schema.Method.Builder
    def setName(value: String): Builder = { struct.setString(0, value); this }
    def setCodeOrder(value: java.lang.Short): Builder = { struct.setShort(0, value); this }
    def initParams(count: Int): Seq[com.capnproto.schema.Method.Param.Builder] = {
      val list = struct.initPointerList(1, count, com.capnproto.schema.Method.Param.Builder)
      Range(0, count).map(i => new com.capnproto.schema.Method.Param.Builder(list.initStruct(i, com.capnproto.schema.Method.Param.Builder)))
    }
    def setParams(buildFn: CapnpArenaBuilder => Seq[com.capnproto.schema.Method.Param.Builder]): Builder = { struct.setStructList(1, com.capnproto.schema.Method.Param.Builder, buildFn(struct.arena).map(_.struct)); this }
    def setRequiredParamCount(value: java.lang.Short): Builder = { struct.setShort(1, value); this }
    def setReturnType(value: com.capnproto.schema.__Type): Builder = { struct.setNone(); this }
    def initAnnotations(count: Int): Seq[com.capnproto.schema.Annotation.Builder] = {
      val list = struct.initPointerList(3, count, com.capnproto.schema.Annotation.Builder)
      Range(0, count).map(i => new com.capnproto.schema.Annotation.Builder(list.initStruct(i, com.capnproto.schema.Annotation.Builder)))
    }
    def setAnnotations(buildFn: CapnpArenaBuilder => Seq[com.capnproto.schema.Annotation.Builder]): Builder = { struct.setStructList(3, com.capnproto.schema.Annotation.Builder, buildFn(struct.arena).map(_.struct)); this }
  }

  object Param extends MetaStruct[Param] {
    override type Self = Param.type
    override val recordName: String = "Param"
    override def create(struct: CapnpStruct): Param = new ParamMutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.schema.Method.Param.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.schema.Method.Param.Builder.dataSectionSizeWords, com.capnproto.schema.Method.Param.Builder.pointerSectionSizeWords)
      new com.capnproto.schema.Method.Param.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.schema.Method.Param, com.capnproto.schema.Method.Param.Builder] {
      override type Self = com.capnproto.schema.Method.Param.Builder.type
      override val recordName: String = "Param"
      override val dataSectionSizeWords: Short = 0
      override val pointerSectionSizeWords: Short = 4
      override def create(struct: CapnpStructBuilder): com.capnproto.schema.Method.Param.Builder = new com.capnproto.schema.Method.Param.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.schema.Method.Param.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.schema.Method.ParamMutable(struct) with StructBuilder[com.capnproto.schema.Method.Param, com.capnproto.schema.Method.Param.Builder] {
      override type MetaBuilderT = com.capnproto.schema.Method.Param.Builder.type

      override def meta: Param.type = Param
      override def metaBuilder: MetaBuilderT = com.capnproto.schema.Method.Param.Builder
      def setName(value: String): Builder = { struct.setString(0, value); this }
      def set__Type(value: com.capnproto.schema.__Type): Builder = { struct.setNone(); this }
      def setDefaultValue(value: com.capnproto.schema.Value): Builder = { struct.setNone(); this }
      def initAnnotations(count: Int): Seq[com.capnproto.schema.Annotation.Builder] = {
        val list = struct.initPointerList(3, count, com.capnproto.schema.Annotation.Builder)
        Range(0, count).map(i => new com.capnproto.schema.Annotation.Builder(list.initStruct(i, com.capnproto.schema.Annotation.Builder)))
      }
      def setAnnotations(buildFn: CapnpArenaBuilder => Seq[com.capnproto.schema.Annotation.Builder]): Builder = { struct.setStructList(3, com.capnproto.schema.Annotation.Builder, buildFn(struct.arena).map(_.struct)); this }
    }



    val name = new FieldDescriptor[String, Param, Param.type](
      name = "name",
      meta = Param,
      getter = _.name,
      manifest = manifest[String],
      isUnion = false
    )

    val __type = new FieldDescriptor[com.capnproto.schema.__Type, Param, Param.type](
      name = "type",
      meta = Param,
      getter = _.__type,
      manifest = manifest[com.capnproto.schema.__Type],
      isUnion = false
    )

    val defaultValue = new FieldDescriptor[com.capnproto.schema.Value, Param, Param.type](
      name = "defaultValue",
      meta = Param,
      getter = _.defaultValue,
      manifest = manifest[com.capnproto.schema.Value],
      isUnion = false
    )

    val annotations = new FieldDescriptor[Seq[com.capnproto.schema.Annotation], Param, Param.type](
      name = "annotations",
      meta = Param,
      getter = x => Some(x.annotations),
      manifest = manifest[Seq[com.capnproto.schema.Annotation]],
      isUnion = false
    )
    override val fields: Seq[FieldDescriptor[_, Param, Param.type]] = Seq(name, __type, defaultValue, annotations)
  }

  trait Param extends Struct[Param] {
    override type MetaT = Param.type

    override def meta: Param.type = Param
    def struct: CapnpStruct

    def name: Option[String]
    def __type: Option[com.capnproto.schema.__Type]
    def defaultValue: Option[com.capnproto.schema.Value]
    def annotations: Seq[com.capnproto.schema.Annotation]
  }

  trait ParamProxy extends Param {
    def underlying: Param

    override def struct: CapnpStruct = underlying.struct

    override def name: Option[String]
    override def __type: Option[com.capnproto.schema.__Type]
    override def defaultValue: Option[com.capnproto.schema.Value]
    override def annotations: Seq[com.capnproto.schema.Annotation]
  }

  class ParamMutable(override val struct: CapnpStruct) extends Param {

    override def name: Option[String] = struct.getString(0)
    override def __type: Option[com.capnproto.schema.__Type] = struct.getStruct(1).map(new com.capnproto.schema.__TypeMutable(_))
    override def defaultValue: Option[com.capnproto.schema.Value] = struct.getStruct(2).map(new com.capnproto.schema.ValueMutable(_))
    override def annotations: Seq[com.capnproto.schema.Annotation] = struct.getStructList(3).map(new com.capnproto.schema.AnnotationMutable(_))
  }


  val name = new FieldDescriptor[String, Method, Method.type](
    name = "name",
    meta = Method,
    getter = _.name,
    manifest = manifest[String],
    isUnion = false
  )

  val codeOrder = new FieldDescriptor[java.lang.Short, Method, Method.type](
    name = "codeOrder",
    meta = Method,
    getter = _.codeOrder,
    manifest = manifest[java.lang.Short],
    isUnion = false
  )

  val params = new FieldDescriptor[Seq[com.capnproto.schema.Method.Param], Method, Method.type](
    name = "params",
    meta = Method,
    getter = x => Some(x.params),
    manifest = manifest[Seq[com.capnproto.schema.Method.Param]],
    isUnion = false
  )

  val requiredParamCount = new FieldDescriptor[java.lang.Short, Method, Method.type](
    name = "requiredParamCount",
    meta = Method,
    getter = _.requiredParamCount,
    manifest = manifest[java.lang.Short],
    isUnion = false
  )

  val returnType = new FieldDescriptor[com.capnproto.schema.__Type, Method, Method.type](
    name = "returnType",
    meta = Method,
    getter = _.returnType,
    manifest = manifest[com.capnproto.schema.__Type],
    isUnion = false
  )

  val annotations = new FieldDescriptor[Seq[com.capnproto.schema.Annotation], Method, Method.type](
    name = "annotations",
    meta = Method,
    getter = x => Some(x.annotations),
    manifest = manifest[Seq[com.capnproto.schema.Annotation]],
    isUnion = false
  )
  override val fields: Seq[FieldDescriptor[_, Method, Method.type]] = Seq(name, codeOrder, params, requiredParamCount, returnType, annotations)
}

trait Method extends Struct[Method] {
  override type MetaT = Method.type

  override def meta: Method.type = Method
  def struct: CapnpStruct

  def name: Option[String]
  def codeOrder: Option[java.lang.Short]
  def params: Seq[com.capnproto.schema.Method.Param]
  def requiredParamCount: Option[java.lang.Short]
  def returnType: Option[com.capnproto.schema.__Type]
  def annotations: Seq[com.capnproto.schema.Annotation]
}

trait MethodProxy extends Method {
  def underlying: Method

  override def struct: CapnpStruct = underlying.struct

  override def name: Option[String]
  override def codeOrder: Option[java.lang.Short]
  override def params: Seq[com.capnproto.schema.Method.Param]
  override def requiredParamCount: Option[java.lang.Short]
  override def returnType: Option[com.capnproto.schema.__Type]
  override def annotations: Seq[com.capnproto.schema.Annotation]
}

class MethodMutable(override val struct: CapnpStruct) extends Method {

  override def name: Option[String] = struct.getString(0)
  override def codeOrder: Option[java.lang.Short] = struct.getShort(0)
  override def params: Seq[com.capnproto.schema.Method.Param] = struct.getStructList(1).map(new com.capnproto.schema.Method.ParamMutable(_))
  override def requiredParamCount: Option[java.lang.Short] = struct.getShort(1)
  override def returnType: Option[com.capnproto.schema.__Type] = struct.getStruct(2).map(new com.capnproto.schema.__TypeMutable(_))
  override def annotations: Seq[com.capnproto.schema.Annotation] = struct.getStructList(3).map(new com.capnproto.schema.AnnotationMutable(_))
}

object __Type extends MetaStruct[__Type] {
  override type Self = __Type.type
  override val recordName: String = "Type"
  override def create(struct: CapnpStruct): __Type = new __TypeMutable(struct)
  def newBuilder(arena: CapnpArenaBuilder): com.capnproto.schema.__Type.Builder = {
    val (segment, pointerOffset) = arena.allocate(1)
    val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.schema.__Type.Builder.dataSectionSizeWords, com.capnproto.schema.__Type.Builder.pointerSectionSizeWords)
    new com.capnproto.schema.__Type.Builder(struct)
  }

  object Builder extends MetaStructBuilder[com.capnproto.schema.__Type, com.capnproto.schema.__Type.Builder] {
    override type Self = com.capnproto.schema.__Type.Builder.type
    override val recordName: String = "Type"
    override val dataSectionSizeWords: Short = 2
    override val pointerSectionSizeWords: Short = 1
    override def create(struct: CapnpStructBuilder): com.capnproto.schema.__Type.Builder = new com.capnproto.schema.__Type.Builder(struct)
    override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.schema.__Type.fields
  }
  class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.schema.__TypeMutable(struct) with StructBuilder[com.capnproto.schema.__Type, com.capnproto.schema.__Type.Builder] {
    override type MetaBuilderT = com.capnproto.schema.__Type.Builder.type

    override def meta: __Type.type = __Type
    override def metaBuilder: MetaBuilderT = com.capnproto.schema.__Type.Builder
    def setVoid(value: Unit): Builder = { struct.setNone(); struct.setShort(0, -1); this }
    def setBool(value: Unit): Builder = { struct.setNone(); struct.setShort(0, -2); this }
    def setInt8(value: Unit): Builder = { struct.setNone(); struct.setShort(0, -3); this }
    def setInt16(value: Unit): Builder = { struct.setNone(); struct.setShort(0, -4); this }
    def setInt32(value: Unit): Builder = { struct.setNone(); struct.setShort(0, -5); this }
    def setInt64(value: Unit): Builder = { struct.setNone(); struct.setShort(0, -6); this }
    def setUint8(value: Unit): Builder = { struct.setNone(); struct.setShort(0, -7); this }
    def setUint16(value: Unit): Builder = { struct.setNone(); struct.setShort(0, -8); this }
    def setUint32(value: Unit): Builder = { struct.setNone(); struct.setShort(0, -9); this }
    def setUint64(value: Unit): Builder = { struct.setNone(); struct.setShort(0, -10); this }
    def setFloat32(value: Unit): Builder = { struct.setNone(); struct.setShort(0, -11); this }
    def setFloat64(value: Unit): Builder = { struct.setNone(); struct.setShort(0, -12); this }
    def setText(value: Unit): Builder = { struct.setNone(); struct.setShort(0, -13); this }
    def setData(value: Unit): Builder = { struct.setNone(); struct.setShort(0, -14); this }
    override def list: com.capnproto.schema.__Type.List.Builder = new com.capnproto.schema.__Type.List.Builder(struct)
    override def __enum: com.capnproto.schema.__Type.__Enum.Builder = new com.capnproto.schema.__Type.__Enum.Builder(struct)
    override def __struct: com.capnproto.schema.__Type.__Struct.Builder = new com.capnproto.schema.__Type.__Struct.Builder(struct)
    override def interface: com.capnproto.schema.__Type.Interface.Builder = new com.capnproto.schema.__Type.Interface.Builder(struct)
    def set__Object(value: Unit): Builder = { struct.setNone(); struct.setShort(0, -19); this }
  }

  sealed trait Union extends UnionValue[com.capnproto.schema.__Type.Union]
  object Union extends UnionMeta[com.capnproto.schema.__Type.Union] {
    case class Unknown(discriminant: Short) extends com.capnproto.schema.__Type.Union
    case class void(value: Option[Unit]) extends com.capnproto.schema.__Type.Union
    case class bool(value: Option[Unit]) extends com.capnproto.schema.__Type.Union
    case class int8(value: Option[Unit]) extends com.capnproto.schema.__Type.Union
    case class int16(value: Option[Unit]) extends com.capnproto.schema.__Type.Union
    case class int32(value: Option[Unit]) extends com.capnproto.schema.__Type.Union
    case class int64(value: Option[Unit]) extends com.capnproto.schema.__Type.Union
    case class uint8(value: Option[Unit]) extends com.capnproto.schema.__Type.Union
    case class uint16(value: Option[Unit]) extends com.capnproto.schema.__Type.Union
    case class uint32(value: Option[Unit]) extends com.capnproto.schema.__Type.Union
    case class uint64(value: Option[Unit]) extends com.capnproto.schema.__Type.Union
    case class float32(value: Option[Unit]) extends com.capnproto.schema.__Type.Union
    case class float64(value: Option[Unit]) extends com.capnproto.schema.__Type.Union
    case class text(value: Option[Unit]) extends com.capnproto.schema.__Type.Union
    case class data(value: Option[Unit]) extends com.capnproto.schema.__Type.Union
    case class list(value: com.capnproto.schema.__Type.List) extends com.capnproto.schema.__Type.Union
    case class __enum(value: com.capnproto.schema.__Type.__Enum) extends com.capnproto.schema.__Type.Union
    case class __struct(value: com.capnproto.schema.__Type.__Struct) extends com.capnproto.schema.__Type.Union
    case class interface(value: com.capnproto.schema.__Type.Interface) extends com.capnproto.schema.__Type.Union
    case class __object(value: Option[Unit]) extends com.capnproto.schema.__Type.Union
  }


  object List extends MetaStruct[List] {
    override type Self = List.type
    override val recordName: String = "List"
    override def create(struct: CapnpStruct): List = new ListMutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.schema.__Type.List.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.schema.__Type.List.Builder.dataSectionSizeWords, com.capnproto.schema.__Type.List.Builder.pointerSectionSizeWords)
      new com.capnproto.schema.__Type.List.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.schema.__Type.List, com.capnproto.schema.__Type.List.Builder] {
      override type Self = com.capnproto.schema.__Type.List.Builder.type
      override val recordName: String = "List"
      override val dataSectionSizeWords: Short = 2
      override val pointerSectionSizeWords: Short = 1
      override def create(struct: CapnpStructBuilder): com.capnproto.schema.__Type.List.Builder = new com.capnproto.schema.__Type.List.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.schema.__Type.List.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.schema.__Type.ListMutable(struct) with StructBuilder[com.capnproto.schema.__Type.List, com.capnproto.schema.__Type.List.Builder] {
      override type MetaBuilderT = com.capnproto.schema.__Type.List.Builder.type

      override def meta: List.type = List
      override def metaBuilder: MetaBuilderT = com.capnproto.schema.__Type.List.Builder
      def setElementType(value: com.capnproto.schema.__Type): Builder = { struct.setNone(); this }
    }



    val elementType = new FieldDescriptor[com.capnproto.schema.__Type, List, List.type](
      name = "elementType",
      meta = List,
      getter = _.elementType,
      manifest = manifest[com.capnproto.schema.__Type],
      isUnion = false
    )
    override val fields: Seq[FieldDescriptor[_, List, List.type]] = Seq(elementType)
  }

  trait List extends Struct[List] {
    override type MetaT = List.type

    override def meta: List.type = List
    def struct: CapnpStruct

    def elementType: Option[com.capnproto.schema.__Type]
  }

  trait ListProxy extends List {
    def underlying: List

    override def struct: CapnpStruct = underlying.struct

    override def elementType: Option[com.capnproto.schema.__Type]
  }

  class ListMutable(override val struct: CapnpStruct) extends List {

    override def elementType: Option[com.capnproto.schema.__Type] = struct.getStruct(0).map(new com.capnproto.schema.__TypeMutable(_))
  }
  object __Enum extends MetaStruct[__Enum] {
    override type Self = __Enum.type
    override val recordName: String = "__Enum"
    override def create(struct: CapnpStruct): __Enum = new __EnumMutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.schema.__Type.__Enum.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.schema.__Type.__Enum.Builder.dataSectionSizeWords, com.capnproto.schema.__Type.__Enum.Builder.pointerSectionSizeWords)
      new com.capnproto.schema.__Type.__Enum.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.schema.__Type.__Enum, com.capnproto.schema.__Type.__Enum.Builder] {
      override type Self = com.capnproto.schema.__Type.__Enum.Builder.type
      override val recordName: String = "__Enum"
      override val dataSectionSizeWords: Short = 2
      override val pointerSectionSizeWords: Short = 1
      override def create(struct: CapnpStructBuilder): com.capnproto.schema.__Type.__Enum.Builder = new com.capnproto.schema.__Type.__Enum.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.schema.__Type.__Enum.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.schema.__Type.__EnumMutable(struct) with StructBuilder[com.capnproto.schema.__Type.__Enum, com.capnproto.schema.__Type.__Enum.Builder] {
      override type MetaBuilderT = com.capnproto.schema.__Type.__Enum.Builder.type

      override def meta: __Enum.type = __Enum
      override def metaBuilder: MetaBuilderT = com.capnproto.schema.__Type.__Enum.Builder
      def setTypeId(value: java.lang.Long): Builder = { struct.setLong(1, value); this }
    }



    val typeId = new FieldDescriptor[java.lang.Long, __Enum, __Enum.type](
      name = "typeId",
      meta = __Enum,
      getter = _.typeId,
      manifest = manifest[java.lang.Long],
      isUnion = false
    )
    override val fields: Seq[FieldDescriptor[_, __Enum, __Enum.type]] = Seq(typeId)
  }

  trait __Enum extends Struct[__Enum] {
    override type MetaT = __Enum.type

    override def meta: __Enum.type = __Enum
    def struct: CapnpStruct

    def typeId: Option[java.lang.Long]
  }

  trait __EnumProxy extends __Enum {
    def underlying: __Enum

    override def struct: CapnpStruct = underlying.struct

    override def typeId: Option[java.lang.Long]
  }

  class __EnumMutable(override val struct: CapnpStruct) extends __Enum {

    override def typeId: Option[java.lang.Long] = struct.getLong(1)
  }
  object __Struct extends MetaStruct[__Struct] {
    override type Self = __Struct.type
    override val recordName: String = "__Struct"
    override def create(struct: CapnpStruct): __Struct = new __StructMutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.schema.__Type.__Struct.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.schema.__Type.__Struct.Builder.dataSectionSizeWords, com.capnproto.schema.__Type.__Struct.Builder.pointerSectionSizeWords)
      new com.capnproto.schema.__Type.__Struct.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.schema.__Type.__Struct, com.capnproto.schema.__Type.__Struct.Builder] {
      override type Self = com.capnproto.schema.__Type.__Struct.Builder.type
      override val recordName: String = "__Struct"
      override val dataSectionSizeWords: Short = 2
      override val pointerSectionSizeWords: Short = 1
      override def create(struct: CapnpStructBuilder): com.capnproto.schema.__Type.__Struct.Builder = new com.capnproto.schema.__Type.__Struct.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.schema.__Type.__Struct.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.schema.__Type.__StructMutable(struct) with StructBuilder[com.capnproto.schema.__Type.__Struct, com.capnproto.schema.__Type.__Struct.Builder] {
      override type MetaBuilderT = com.capnproto.schema.__Type.__Struct.Builder.type

      override def meta: __Struct.type = __Struct
      override def metaBuilder: MetaBuilderT = com.capnproto.schema.__Type.__Struct.Builder
      def setTypeId(value: java.lang.Long): Builder = { struct.setLong(1, value); this }
    }



    val typeId = new FieldDescriptor[java.lang.Long, __Struct, __Struct.type](
      name = "typeId",
      meta = __Struct,
      getter = _.typeId,
      manifest = manifest[java.lang.Long],
      isUnion = false
    )
    override val fields: Seq[FieldDescriptor[_, __Struct, __Struct.type]] = Seq(typeId)
  }

  trait __Struct extends Struct[__Struct] {
    override type MetaT = __Struct.type

    override def meta: __Struct.type = __Struct
    def struct: CapnpStruct

    def typeId: Option[java.lang.Long]
  }

  trait __StructProxy extends __Struct {
    def underlying: __Struct

    override def struct: CapnpStruct = underlying.struct

    override def typeId: Option[java.lang.Long]
  }

  class __StructMutable(override val struct: CapnpStruct) extends __Struct {

    override def typeId: Option[java.lang.Long] = struct.getLong(1)
  }
  object Interface extends MetaStruct[Interface] {
    override type Self = Interface.type
    override val recordName: String = "Interface"
    override def create(struct: CapnpStruct): Interface = new InterfaceMutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.schema.__Type.Interface.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.schema.__Type.Interface.Builder.dataSectionSizeWords, com.capnproto.schema.__Type.Interface.Builder.pointerSectionSizeWords)
      new com.capnproto.schema.__Type.Interface.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.schema.__Type.Interface, com.capnproto.schema.__Type.Interface.Builder] {
      override type Self = com.capnproto.schema.__Type.Interface.Builder.type
      override val recordName: String = "Interface"
      override val dataSectionSizeWords: Short = 2
      override val pointerSectionSizeWords: Short = 1
      override def create(struct: CapnpStructBuilder): com.capnproto.schema.__Type.Interface.Builder = new com.capnproto.schema.__Type.Interface.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.schema.__Type.Interface.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.schema.__Type.InterfaceMutable(struct) with StructBuilder[com.capnproto.schema.__Type.Interface, com.capnproto.schema.__Type.Interface.Builder] {
      override type MetaBuilderT = com.capnproto.schema.__Type.Interface.Builder.type

      override def meta: Interface.type = Interface
      override def metaBuilder: MetaBuilderT = com.capnproto.schema.__Type.Interface.Builder
      def setTypeId(value: java.lang.Long): Builder = { struct.setLong(1, value); this }
    }



    val typeId = new FieldDescriptor[java.lang.Long, Interface, Interface.type](
      name = "typeId",
      meta = Interface,
      getter = _.typeId,
      manifest = manifest[java.lang.Long],
      isUnion = false
    )
    override val fields: Seq[FieldDescriptor[_, Interface, Interface.type]] = Seq(typeId)
  }

  trait Interface extends Struct[Interface] {
    override type MetaT = Interface.type

    override def meta: Interface.type = Interface
    def struct: CapnpStruct

    def typeId: Option[java.lang.Long]
  }

  trait InterfaceProxy extends Interface {
    def underlying: Interface

    override def struct: CapnpStruct = underlying.struct

    override def typeId: Option[java.lang.Long]
  }

  class InterfaceMutable(override val struct: CapnpStruct) extends Interface {

    override def typeId: Option[java.lang.Long] = struct.getLong(1)
  }

  val void = new FieldDescriptor[Unit, __Type, __Type.type](
    name = "void",
    meta = __Type,
    getter = _.void,
    manifest = manifest[Unit],
    isUnion = true
  )

  val bool = new FieldDescriptor[Unit, __Type, __Type.type](
    name = "bool",
    meta = __Type,
    getter = _.bool,
    manifest = manifest[Unit],
    isUnion = true
  )

  val int8 = new FieldDescriptor[Unit, __Type, __Type.type](
    name = "int8",
    meta = __Type,
    getter = _.int8,
    manifest = manifest[Unit],
    isUnion = true
  )

  val int16 = new FieldDescriptor[Unit, __Type, __Type.type](
    name = "int16",
    meta = __Type,
    getter = _.int16,
    manifest = manifest[Unit],
    isUnion = true
  )

  val int32 = new FieldDescriptor[Unit, __Type, __Type.type](
    name = "int32",
    meta = __Type,
    getter = _.int32,
    manifest = manifest[Unit],
    isUnion = true
  )

  val int64 = new FieldDescriptor[Unit, __Type, __Type.type](
    name = "int64",
    meta = __Type,
    getter = _.int64,
    manifest = manifest[Unit],
    isUnion = true
  )

  val uint8 = new FieldDescriptor[Unit, __Type, __Type.type](
    name = "uint8",
    meta = __Type,
    getter = _.uint8,
    manifest = manifest[Unit],
    isUnion = true
  )

  val uint16 = new FieldDescriptor[Unit, __Type, __Type.type](
    name = "uint16",
    meta = __Type,
    getter = _.uint16,
    manifest = manifest[Unit],
    isUnion = true
  )

  val uint32 = new FieldDescriptor[Unit, __Type, __Type.type](
    name = "uint32",
    meta = __Type,
    getter = _.uint32,
    manifest = manifest[Unit],
    isUnion = true
  )

  val uint64 = new FieldDescriptor[Unit, __Type, __Type.type](
    name = "uint64",
    meta = __Type,
    getter = _.uint64,
    manifest = manifest[Unit],
    isUnion = true
  )

  val float32 = new FieldDescriptor[Unit, __Type, __Type.type](
    name = "float32",
    meta = __Type,
    getter = _.float32,
    manifest = manifest[Unit],
    isUnion = true
  )

  val float64 = new FieldDescriptor[Unit, __Type, __Type.type](
    name = "float64",
    meta = __Type,
    getter = _.float64,
    manifest = manifest[Unit],
    isUnion = true
  )

  val text = new FieldDescriptor[Unit, __Type, __Type.type](
    name = "text",
    meta = __Type,
    getter = _.text,
    manifest = manifest[Unit],
    isUnion = true
  )

  val data = new FieldDescriptor[Unit, __Type, __Type.type](
    name = "data",
    meta = __Type,
    getter = _.data,
    manifest = manifest[Unit],
    isUnion = true
  )

  val list = new FieldDescriptor[com.capnproto.schema.__Type.List, __Type, __Type.type](
    name = "list",
    meta = __Type,
    getter = x => Some(x.list),
    manifest = manifest[com.capnproto.schema.__Type.List],
    isUnion = true
  )

  val __enum = new FieldDescriptor[com.capnproto.schema.__Type.__Enum, __Type, __Type.type](
    name = "enum",
    meta = __Type,
    getter = x => Some(x.__enum),
    manifest = manifest[com.capnproto.schema.__Type.__Enum],
    isUnion = true
  )

  val __struct = new FieldDescriptor[com.capnproto.schema.__Type.__Struct, __Type, __Type.type](
    name = "struct",
    meta = __Type,
    getter = x => Some(x.__struct),
    manifest = manifest[com.capnproto.schema.__Type.__Struct],
    isUnion = true
  )

  val interface = new FieldDescriptor[com.capnproto.schema.__Type.Interface, __Type, __Type.type](
    name = "interface",
    meta = __Type,
    getter = x => Some(x.interface),
    manifest = manifest[com.capnproto.schema.__Type.Interface],
    isUnion = true
  )

  val __object = new FieldDescriptor[Unit, __Type, __Type.type](
    name = "object",
    meta = __Type,
    getter = _.__object,
    manifest = manifest[Unit],
    isUnion = true
  )
  override val fields: Seq[FieldDescriptor[_, __Type, __Type.type]] = Seq(void, bool, int8, int16, int32, int64, uint8, uint16, uint32, uint64, float32, float64, text, data, list, __enum, __struct, interface, __object)
}

trait __Type extends Struct[__Type] with HasUnion[com.capnproto.schema.__Type.Union] {
  override type MetaT = __Type.type

  override def meta: __Type.type = __Type
  def struct: CapnpStruct

  def void: Option[Unit]
  def bool: Option[Unit]
  def int8: Option[Unit]
  def int16: Option[Unit]
  def int32: Option[Unit]
  def int64: Option[Unit]
  def uint8: Option[Unit]
  def uint16: Option[Unit]
  def uint32: Option[Unit]
  def uint64: Option[Unit]
  def float32: Option[Unit]
  def float64: Option[Unit]
  def text: Option[Unit]
  def data: Option[Unit]
  def list: com.capnproto.schema.__Type.List
  def __enum: com.capnproto.schema.__Type.__Enum
  def __struct: com.capnproto.schema.__Type.__Struct
  def interface: com.capnproto.schema.__Type.Interface
  def __object: Option[Unit]
}

trait __TypeProxy extends __Type with HasUnion[com.capnproto.schema.__Type.Union] {
  def underlying: __Type

  override def struct: CapnpStruct = underlying.struct

  override def switch: com.capnproto.schema.__Type.Union = underlying.switch
  override def union: UnionMeta[com.capnproto.schema.__Type.Union] = underlying.union

  override def void: Option[Unit]
  override def bool: Option[Unit]
  override def int8: Option[Unit]
  override def int16: Option[Unit]
  override def int32: Option[Unit]
  override def int64: Option[Unit]
  override def uint8: Option[Unit]
  override def uint16: Option[Unit]
  override def uint32: Option[Unit]
  override def uint64: Option[Unit]
  override def float32: Option[Unit]
  override def float64: Option[Unit]
  override def text: Option[Unit]
  override def data: Option[Unit]
  override def list: com.capnproto.schema.__Type.List
  override def __enum: com.capnproto.schema.__Type.__Enum
  override def __struct: com.capnproto.schema.__Type.__Struct
  override def interface: com.capnproto.schema.__Type.Interface
  override def __object: Option[Unit]
}

class __TypeMutable(override val struct: CapnpStruct) extends __Type {

  override def discriminant: Short = (struct.getShort(0).getOrElse(new java.lang.Short(0.toShort)): java.lang.Short)
  override def switch: com.capnproto.schema.__Type.Union = discriminant match {
    case 0 => com.capnproto.schema.__Type.Union.void(void)
    case 1 => com.capnproto.schema.__Type.Union.bool(bool)
    case 2 => com.capnproto.schema.__Type.Union.int8(int8)
    case 3 => com.capnproto.schema.__Type.Union.int16(int16)
    case 4 => com.capnproto.schema.__Type.Union.int32(int32)
    case 5 => com.capnproto.schema.__Type.Union.int64(int64)
    case 6 => com.capnproto.schema.__Type.Union.uint8(uint8)
    case 7 => com.capnproto.schema.__Type.Union.uint16(uint16)
    case 8 => com.capnproto.schema.__Type.Union.uint32(uint32)
    case 9 => com.capnproto.schema.__Type.Union.uint64(uint64)
    case 10 => com.capnproto.schema.__Type.Union.float32(float32)
    case 11 => com.capnproto.schema.__Type.Union.float64(float64)
    case 12 => com.capnproto.schema.__Type.Union.text(text)
    case 13 => com.capnproto.schema.__Type.Union.data(data)
    case 14 => com.capnproto.schema.__Type.Union.list(list)
    case 15 => com.capnproto.schema.__Type.Union.__enum(__enum)
    case 16 => com.capnproto.schema.__Type.Union.__struct(__struct)
    case 17 => com.capnproto.schema.__Type.Union.interface(interface)
    case 18 => com.capnproto.schema.__Type.Union.__object(__object)
    case d => com.capnproto.schema.__Type.Union.Unknown(d)
  }
  override def union: UnionMeta[com.capnproto.schema.__Type.Union] = com.capnproto.schema.__Type.Union

  override def void: Option[Unit] = struct.getNone()
  override def bool: Option[Unit] = struct.getNone()
  override def int8: Option[Unit] = struct.getNone()
  override def int16: Option[Unit] = struct.getNone()
  override def int32: Option[Unit] = struct.getNone()
  override def int64: Option[Unit] = struct.getNone()
  override def uint8: Option[Unit] = struct.getNone()
  override def uint16: Option[Unit] = struct.getNone()
  override def uint32: Option[Unit] = struct.getNone()
  override def uint64: Option[Unit] = struct.getNone()
  override def float32: Option[Unit] = struct.getNone()
  override def float64: Option[Unit] = struct.getNone()
  override def text: Option[Unit] = struct.getNone()
  override def data: Option[Unit] = struct.getNone()
  override def list: com.capnproto.schema.__Type.List = new com.capnproto.schema.__Type.ListMutable(struct)

  override def __enum: com.capnproto.schema.__Type.__Enum = new com.capnproto.schema.__Type.__EnumMutable(struct)

  override def __struct: com.capnproto.schema.__Type.__Struct = new com.capnproto.schema.__Type.__StructMutable(struct)

  override def interface: com.capnproto.schema.__Type.Interface = new com.capnproto.schema.__Type.InterfaceMutable(struct)

  override def __object: Option[Unit] = struct.getNone()
}

object Value extends MetaStruct[Value] {
  override type Self = Value.type
  override val recordName: String = "Value"
  override def create(struct: CapnpStruct): Value = new ValueMutable(struct)
  def newBuilder(arena: CapnpArenaBuilder): com.capnproto.schema.Value.Builder = {
    val (segment, pointerOffset) = arena.allocate(1)
    val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.schema.Value.Builder.dataSectionSizeWords, com.capnproto.schema.Value.Builder.pointerSectionSizeWords)
    new com.capnproto.schema.Value.Builder(struct)
  }

  object Builder extends MetaStructBuilder[com.capnproto.schema.Value, com.capnproto.schema.Value.Builder] {
    override type Self = com.capnproto.schema.Value.Builder.type
    override val recordName: String = "Value"
    override val dataSectionSizeWords: Short = 2
    override val pointerSectionSizeWords: Short = 1
    override def create(struct: CapnpStructBuilder): com.capnproto.schema.Value.Builder = new com.capnproto.schema.Value.Builder(struct)
    override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.schema.Value.fields
  }
  class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.schema.ValueMutable(struct) with StructBuilder[com.capnproto.schema.Value, com.capnproto.schema.Value.Builder] {
    override type MetaBuilderT = com.capnproto.schema.Value.Builder.type

    override def meta: Value.type = Value
    override def metaBuilder: MetaBuilderT = com.capnproto.schema.Value.Builder
    def setVoid(value: Unit): Builder = { struct.setNone(); struct.setShort(0, -1); this }
    def setBool(value: java.lang.Boolean): Builder = { struct.setBoolean(16, value); struct.setShort(0, -2); this }
    def setInt8(value: java.lang.Byte): Builder = { struct.setByte(2, value); struct.setShort(0, -3); this }
    def setInt16(value: java.lang.Short): Builder = { struct.setShort(1, value); struct.setShort(0, -4); this }
    def setInt32(value: java.lang.Integer): Builder = { struct.setInt(1, value); struct.setShort(0, -5); this }
    def setInt64(value: java.lang.Long): Builder = { struct.setLong(1, value); struct.setShort(0, -6); this }
    def setUint8(value: java.lang.Byte): Builder = { struct.setByte(2, value); struct.setShort(0, -7); this }
    def setUint16(value: java.lang.Short): Builder = { struct.setShort(1, value); struct.setShort(0, -8); this }
    def setUint32(value: java.lang.Integer): Builder = { struct.setInt(1, value); struct.setShort(0, -9); this }
    def setUint64(value: java.lang.Long): Builder = { struct.setLong(1, value); struct.setShort(0, -10); this }
    def setFloat32(value: java.lang.Float): Builder = { struct.setFloat(1, value); struct.setShort(0, -11); this }
    def setFloat64(value: java.lang.Double): Builder = { struct.setDouble(1, value); struct.setShort(0, -12); this }
    def setText(value: String): Builder = { struct.setString(0, value); struct.setShort(0, -13); this }
    def setData(value: Array[Byte]): Builder = { struct.setData(0, value); struct.setShort(0, -14); this }
    def setList(value: CapnpPointer): Builder = { struct.setNone(); struct.setShort(0, -15); this }
    def set__Enum(value: java.lang.Short): Builder = { struct.setShort(1, value); struct.setShort(0, -16); this }
    def set__Struct(value: CapnpPointer): Builder = { struct.setNone(); struct.setShort(0, -17); this }
    def setInterface(value: Unit): Builder = { struct.setNone(); struct.setShort(0, -18); this }
    def set__Object(value: CapnpPointer): Builder = { struct.setNone(); struct.setShort(0, -19); this }
  }

  sealed trait Union extends UnionValue[com.capnproto.schema.Value.Union]
  object Union extends UnionMeta[com.capnproto.schema.Value.Union] {
    case class Unknown(discriminant: Short) extends com.capnproto.schema.Value.Union
    case class void(value: Option[Unit]) extends com.capnproto.schema.Value.Union
    case class bool(value: Option[java.lang.Boolean]) extends com.capnproto.schema.Value.Union
    case class int8(value: Option[java.lang.Byte]) extends com.capnproto.schema.Value.Union
    case class int16(value: Option[java.lang.Short]) extends com.capnproto.schema.Value.Union
    case class int32(value: Option[java.lang.Integer]) extends com.capnproto.schema.Value.Union
    case class int64(value: Option[java.lang.Long]) extends com.capnproto.schema.Value.Union
    case class uint8(value: Option[java.lang.Byte]) extends com.capnproto.schema.Value.Union
    case class uint16(value: Option[java.lang.Short]) extends com.capnproto.schema.Value.Union
    case class uint32(value: Option[java.lang.Integer]) extends com.capnproto.schema.Value.Union
    case class uint64(value: Option[java.lang.Long]) extends com.capnproto.schema.Value.Union
    case class float32(value: Option[java.lang.Float]) extends com.capnproto.schema.Value.Union
    case class float64(value: Option[java.lang.Double]) extends com.capnproto.schema.Value.Union
    case class text(value: Option[String]) extends com.capnproto.schema.Value.Union
    case class data(value: Option[Array[Byte]]) extends com.capnproto.schema.Value.Union
    case class list(value: Option[CapnpPointer]) extends com.capnproto.schema.Value.Union
    case class __enum(value: Option[java.lang.Short]) extends com.capnproto.schema.Value.Union
    case class __struct(value: Option[CapnpPointer]) extends com.capnproto.schema.Value.Union
    case class interface(value: Option[Unit]) extends com.capnproto.schema.Value.Union
    case class __object(value: Option[CapnpPointer]) extends com.capnproto.schema.Value.Union
  }



  val void = new FieldDescriptor[Unit, Value, Value.type](
    name = "void",
    meta = Value,
    getter = _.void,
    manifest = manifest[Unit],
    isUnion = true
  )

  val bool = new FieldDescriptor[java.lang.Boolean, Value, Value.type](
    name = "bool",
    meta = Value,
    getter = _.bool,
    manifest = manifest[java.lang.Boolean],
    isUnion = true
  )

  val int8 = new FieldDescriptor[java.lang.Byte, Value, Value.type](
    name = "int8",
    meta = Value,
    getter = _.int8,
    manifest = manifest[java.lang.Byte],
    isUnion = true
  )

  val int16 = new FieldDescriptor[java.lang.Short, Value, Value.type](
    name = "int16",
    meta = Value,
    getter = _.int16,
    manifest = manifest[java.lang.Short],
    isUnion = true
  )

  val int32 = new FieldDescriptor[java.lang.Integer, Value, Value.type](
    name = "int32",
    meta = Value,
    getter = _.int32,
    manifest = manifest[java.lang.Integer],
    isUnion = true
  )

  val int64 = new FieldDescriptor[java.lang.Long, Value, Value.type](
    name = "int64",
    meta = Value,
    getter = _.int64,
    manifest = manifest[java.lang.Long],
    isUnion = true
  )

  val uint8 = new FieldDescriptor[java.lang.Byte, Value, Value.type](
    name = "uint8",
    meta = Value,
    getter = _.uint8,
    manifest = manifest[java.lang.Byte],
    isUnion = true
  )

  val uint16 = new FieldDescriptor[java.lang.Short, Value, Value.type](
    name = "uint16",
    meta = Value,
    getter = _.uint16,
    manifest = manifest[java.lang.Short],
    isUnion = true
  )

  val uint32 = new FieldDescriptor[java.lang.Integer, Value, Value.type](
    name = "uint32",
    meta = Value,
    getter = _.uint32,
    manifest = manifest[java.lang.Integer],
    isUnion = true
  )

  val uint64 = new FieldDescriptor[java.lang.Long, Value, Value.type](
    name = "uint64",
    meta = Value,
    getter = _.uint64,
    manifest = manifest[java.lang.Long],
    isUnion = true
  )

  val float32 = new FieldDescriptor[java.lang.Float, Value, Value.type](
    name = "float32",
    meta = Value,
    getter = _.float32,
    manifest = manifest[java.lang.Float],
    isUnion = true
  )

  val float64 = new FieldDescriptor[java.lang.Double, Value, Value.type](
    name = "float64",
    meta = Value,
    getter = _.float64,
    manifest = manifest[java.lang.Double],
    isUnion = true
  )

  val text = new FieldDescriptor[String, Value, Value.type](
    name = "text",
    meta = Value,
    getter = _.text,
    manifest = manifest[String],
    isUnion = true
  )

  val data = new FieldDescriptor[Array[Byte], Value, Value.type](
    name = "data",
    meta = Value,
    getter = _.data,
    manifest = manifest[Array[Byte]],
    isUnion = true
  )

  val list = new FieldDescriptor[CapnpPointer, Value, Value.type](
    name = "list",
    meta = Value,
    getter = _.list,
    manifest = manifest[CapnpPointer],
    isUnion = true
  )

  val __enum = new FieldDescriptor[java.lang.Short, Value, Value.type](
    name = "enum",
    meta = Value,
    getter = _.__enum,
    manifest = manifest[java.lang.Short],
    isUnion = true
  )

  val __struct = new FieldDescriptor[CapnpPointer, Value, Value.type](
    name = "struct",
    meta = Value,
    getter = _.__struct,
    manifest = manifest[CapnpPointer],
    isUnion = true
  )

  val interface = new FieldDescriptor[Unit, Value, Value.type](
    name = "interface",
    meta = Value,
    getter = _.interface,
    manifest = manifest[Unit],
    isUnion = true
  )

  val __object = new FieldDescriptor[CapnpPointer, Value, Value.type](
    name = "object",
    meta = Value,
    getter = _.__object,
    manifest = manifest[CapnpPointer],
    isUnion = true
  )
  override val fields: Seq[FieldDescriptor[_, Value, Value.type]] = Seq(void, bool, int8, int16, int32, int64, uint8, uint16, uint32, uint64, float32, float64, text, data, list, __enum, __struct, interface, __object)
}

trait Value extends Struct[Value] with HasUnion[com.capnproto.schema.Value.Union] {
  override type MetaT = Value.type

  override def meta: Value.type = Value
  def struct: CapnpStruct

  def void: Option[Unit]
  def bool: Option[java.lang.Boolean]
  def int8: Option[java.lang.Byte]
  def int16: Option[java.lang.Short]
  def int32: Option[java.lang.Integer]
  def int64: Option[java.lang.Long]
  def uint8: Option[java.lang.Byte]
  def uint16: Option[java.lang.Short]
  def uint32: Option[java.lang.Integer]
  def uint64: Option[java.lang.Long]
  def float32: Option[java.lang.Float]
  def float64: Option[java.lang.Double]
  def text: Option[String]
  def data: Option[Array[Byte]]
  def list: Option[CapnpPointer]
  def __enum: Option[java.lang.Short]
  def __struct: Option[CapnpPointer]
  def interface: Option[Unit]
  def __object: Option[CapnpPointer]
}

trait ValueProxy extends Value with HasUnion[com.capnproto.schema.Value.Union] {
  def underlying: Value

  override def struct: CapnpStruct = underlying.struct

  override def switch: com.capnproto.schema.Value.Union = underlying.switch
  override def union: UnionMeta[com.capnproto.schema.Value.Union] = underlying.union

  override def void: Option[Unit]
  override def bool: Option[java.lang.Boolean]
  override def int8: Option[java.lang.Byte]
  override def int16: Option[java.lang.Short]
  override def int32: Option[java.lang.Integer]
  override def int64: Option[java.lang.Long]
  override def uint8: Option[java.lang.Byte]
  override def uint16: Option[java.lang.Short]
  override def uint32: Option[java.lang.Integer]
  override def uint64: Option[java.lang.Long]
  override def float32: Option[java.lang.Float]
  override def float64: Option[java.lang.Double]
  override def text: Option[String]
  override def data: Option[Array[Byte]]
  override def list: Option[CapnpPointer]
  override def __enum: Option[java.lang.Short]
  override def __struct: Option[CapnpPointer]
  override def interface: Option[Unit]
  override def __object: Option[CapnpPointer]
}

class ValueMutable(override val struct: CapnpStruct) extends Value {

  override def discriminant: Short = (struct.getShort(0).getOrElse(new java.lang.Short(0.toShort)): java.lang.Short)
  override def switch: com.capnproto.schema.Value.Union = discriminant match {
    case 0 => com.capnproto.schema.Value.Union.void(void)
    case 1 => com.capnproto.schema.Value.Union.bool(bool)
    case 2 => com.capnproto.schema.Value.Union.int8(int8)
    case 3 => com.capnproto.schema.Value.Union.int16(int16)
    case 4 => com.capnproto.schema.Value.Union.int32(int32)
    case 5 => com.capnproto.schema.Value.Union.int64(int64)
    case 6 => com.capnproto.schema.Value.Union.uint8(uint8)
    case 7 => com.capnproto.schema.Value.Union.uint16(uint16)
    case 8 => com.capnproto.schema.Value.Union.uint32(uint32)
    case 9 => com.capnproto.schema.Value.Union.uint64(uint64)
    case 10 => com.capnproto.schema.Value.Union.float32(float32)
    case 11 => com.capnproto.schema.Value.Union.float64(float64)
    case 12 => com.capnproto.schema.Value.Union.text(text)
    case 13 => com.capnproto.schema.Value.Union.data(data)
    case 14 => com.capnproto.schema.Value.Union.list(list)
    case 15 => com.capnproto.schema.Value.Union.__enum(__enum)
    case 16 => com.capnproto.schema.Value.Union.__struct(__struct)
    case 17 => com.capnproto.schema.Value.Union.interface(interface)
    case 18 => com.capnproto.schema.Value.Union.__object(__object)
    case d => com.capnproto.schema.Value.Union.Unknown(d)
  }
  override def union: UnionMeta[com.capnproto.schema.Value.Union] = com.capnproto.schema.Value.Union

  override def void: Option[Unit] = struct.getNone()
  override def bool: Option[java.lang.Boolean] = struct.getBoolean(16)
  override def int8: Option[java.lang.Byte] = struct.getByte(2)
  override def int16: Option[java.lang.Short] = struct.getShort(1)
  override def int32: Option[java.lang.Integer] = struct.getInt(1)
  override def int64: Option[java.lang.Long] = struct.getLong(1)
  override def uint8: Option[java.lang.Byte] = struct.getByte(2)
  override def uint16: Option[java.lang.Short] = struct.getShort(1)
  override def uint32: Option[java.lang.Integer] = struct.getInt(1)
  override def uint64: Option[java.lang.Long] = struct.getLong(1)
  override def float32: Option[java.lang.Float] = struct.getFloat(1)
  override def float64: Option[java.lang.Double] = struct.getDouble(1)
  override def text: Option[String] = struct.getString(0)
  override def data: Option[Array[Byte]] = struct.getData(0)
  override def list: Option[CapnpPointer] = struct.getPointer(0)
  override def __enum: Option[java.lang.Short] = struct.getShort(1)
  override def __struct: Option[CapnpPointer] = struct.getPointer(0)
  override def interface: Option[Unit] = struct.getNone()
  override def __object: Option[CapnpPointer] = struct.getPointer(0)
}

object Annotation extends MetaStruct[Annotation] {
  override type Self = Annotation.type
  override val recordName: String = "Annotation"
  override def create(struct: CapnpStruct): Annotation = new AnnotationMutable(struct)
  def newBuilder(arena: CapnpArenaBuilder): com.capnproto.schema.Annotation.Builder = {
    val (segment, pointerOffset) = arena.allocate(1)
    val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.schema.Annotation.Builder.dataSectionSizeWords, com.capnproto.schema.Annotation.Builder.pointerSectionSizeWords)
    new com.capnproto.schema.Annotation.Builder(struct)
  }

  object Builder extends MetaStructBuilder[com.capnproto.schema.Annotation, com.capnproto.schema.Annotation.Builder] {
    override type Self = com.capnproto.schema.Annotation.Builder.type
    override val recordName: String = "Annotation"
    override val dataSectionSizeWords: Short = 1
    override val pointerSectionSizeWords: Short = 1
    override def create(struct: CapnpStructBuilder): com.capnproto.schema.Annotation.Builder = new com.capnproto.schema.Annotation.Builder(struct)
    override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.schema.Annotation.fields
  }
  class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.schema.AnnotationMutable(struct) with StructBuilder[com.capnproto.schema.Annotation, com.capnproto.schema.Annotation.Builder] {
    override type MetaBuilderT = com.capnproto.schema.Annotation.Builder.type

    override def meta: Annotation.type = Annotation
    override def metaBuilder: MetaBuilderT = com.capnproto.schema.Annotation.Builder
    def setId(value: java.lang.Long): Builder = { struct.setLong(0, value); this }
    def setValue(value: com.capnproto.schema.Value): Builder = { struct.setNone(); this }
  }



  val id = new FieldDescriptor[java.lang.Long, Annotation, Annotation.type](
    name = "id",
    meta = Annotation,
    getter = _.id,
    manifest = manifest[java.lang.Long],
    isUnion = false
  )

  val value = new FieldDescriptor[com.capnproto.schema.Value, Annotation, Annotation.type](
    name = "value",
    meta = Annotation,
    getter = _.value,
    manifest = manifest[com.capnproto.schema.Value],
    isUnion = false
  )
  override val fields: Seq[FieldDescriptor[_, Annotation, Annotation.type]] = Seq(id, value)
}

trait Annotation extends Struct[Annotation] {
  override type MetaT = Annotation.type

  override def meta: Annotation.type = Annotation
  def struct: CapnpStruct

  def id: Option[java.lang.Long]
  def value: Option[com.capnproto.schema.Value]
}

trait AnnotationProxy extends Annotation {
  def underlying: Annotation

  override def struct: CapnpStruct = underlying.struct

  override def id: Option[java.lang.Long]
  override def value: Option[com.capnproto.schema.Value]
}

class AnnotationMutable(override val struct: CapnpStruct) extends Annotation {

  override def id: Option[java.lang.Long] = struct.getLong(0)
  override def value: Option[com.capnproto.schema.Value] = struct.getStruct(0).map(new com.capnproto.schema.ValueMutable(_))
}

object ElementSize extends EnumMeta[ElementSize] {
  case class Unknown(override val id: Int) extends ElementSize(ElementSize, id, null, null)

  val empty = new ElementSize(this, 0, "empty", "empty")
  val bit = new ElementSize(this, 1, "bit", "bit")
  val byte = new ElementSize(this, 2, "byte", "byte")
  val twoBytes = new ElementSize(this, 3, "twoBytes", "twoBytes")
  val fourBytes = new ElementSize(this, 4, "fourBytes", "fourBytes")
  val eightBytes = new ElementSize(this, 5, "eightBytes", "eightBytes")
  val pointer = new ElementSize(this, 6, "pointer", "pointer")
  val inlineComposite = new ElementSize(this, 7, "inlineComposite", "inlineComposite")

  override val values = Vector(
    empty,
    bit,
    byte,
    twoBytes,
    fourBytes,
    eightBytes,
    pointer,
    inlineComposite
  )

  override def findByIdOrNull(id: Int): ElementSize = values.lift(id).getOrElse(null)
  override def findByNameOrNull(name: String): ElementSize = null
  override def findByStringValueOrNull(v: String): ElementSize = null
}

sealed class ElementSize(
  override val meta: EnumMeta[ElementSize],
  override val id: Int,
  override val name: String,
  override val stringValue: String
) extends Enum[ElementSize]

object CodeGeneratorRequest extends MetaStruct[CodeGeneratorRequest] {
  override type Self = CodeGeneratorRequest.type
  override val recordName: String = "CodeGeneratorRequest"
  override def create(struct: CapnpStruct): CodeGeneratorRequest = new CodeGeneratorRequestMutable(struct)
  def newBuilder(arena: CapnpArenaBuilder): com.capnproto.schema.CodeGeneratorRequest.Builder = {
    val (segment, pointerOffset) = arena.allocate(1)
    val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.schema.CodeGeneratorRequest.Builder.dataSectionSizeWords, com.capnproto.schema.CodeGeneratorRequest.Builder.pointerSectionSizeWords)
    new com.capnproto.schema.CodeGeneratorRequest.Builder(struct)
  }

  object Builder extends MetaStructBuilder[com.capnproto.schema.CodeGeneratorRequest, com.capnproto.schema.CodeGeneratorRequest.Builder] {
    override type Self = com.capnproto.schema.CodeGeneratorRequest.Builder.type
    override val recordName: String = "CodeGeneratorRequest"
    override val dataSectionSizeWords: Short = 0
    override val pointerSectionSizeWords: Short = 2
    override def create(struct: CapnpStructBuilder): com.capnproto.schema.CodeGeneratorRequest.Builder = new com.capnproto.schema.CodeGeneratorRequest.Builder(struct)
    override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.schema.CodeGeneratorRequest.fields
  }
  class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.schema.CodeGeneratorRequestMutable(struct) with StructBuilder[com.capnproto.schema.CodeGeneratorRequest, com.capnproto.schema.CodeGeneratorRequest.Builder] {
    override type MetaBuilderT = com.capnproto.schema.CodeGeneratorRequest.Builder.type

    override def meta: CodeGeneratorRequest.type = CodeGeneratorRequest
    override def metaBuilder: MetaBuilderT = com.capnproto.schema.CodeGeneratorRequest.Builder
    def initNodes(count: Int): Seq[com.capnproto.schema.Node.Builder] = {
      val list = struct.initPointerList(0, count, com.capnproto.schema.Node.Builder)
      Range(0, count).map(i => new com.capnproto.schema.Node.Builder(list.initStruct(i, com.capnproto.schema.Node.Builder)))
    }
    def setNodes(buildFn: CapnpArenaBuilder => Seq[com.capnproto.schema.Node.Builder]): Builder = { struct.setStructList(0, com.capnproto.schema.Node.Builder, buildFn(struct.arena).map(_.struct)); this }
    def initRequestedFiles(count: Int): Seq[com.capnproto.schema.CodeGeneratorRequest.RequestedFile.Builder] = {
      val list = struct.initPointerList(1, count, com.capnproto.schema.CodeGeneratorRequest.RequestedFile.Builder)
      Range(0, count).map(i => new com.capnproto.schema.CodeGeneratorRequest.RequestedFile.Builder(list.initStruct(i, com.capnproto.schema.CodeGeneratorRequest.RequestedFile.Builder)))
    }
    def setRequestedFiles(buildFn: CapnpArenaBuilder => Seq[com.capnproto.schema.CodeGeneratorRequest.RequestedFile.Builder]): Builder = { struct.setStructList(1, com.capnproto.schema.CodeGeneratorRequest.RequestedFile.Builder, buildFn(struct.arena).map(_.struct)); this }
  }

  object RequestedFile extends MetaStruct[RequestedFile] {
    override type Self = RequestedFile.type
    override val recordName: String = "RequestedFile"
    override def create(struct: CapnpStruct): RequestedFile = new RequestedFileMutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.schema.CodeGeneratorRequest.RequestedFile.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.schema.CodeGeneratorRequest.RequestedFile.Builder.dataSectionSizeWords, com.capnproto.schema.CodeGeneratorRequest.RequestedFile.Builder.pointerSectionSizeWords)
      new com.capnproto.schema.CodeGeneratorRequest.RequestedFile.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.schema.CodeGeneratorRequest.RequestedFile, com.capnproto.schema.CodeGeneratorRequest.RequestedFile.Builder] {
      override type Self = com.capnproto.schema.CodeGeneratorRequest.RequestedFile.Builder.type
      override val recordName: String = "RequestedFile"
      override val dataSectionSizeWords: Short = 1
      override val pointerSectionSizeWords: Short = 2
      override def create(struct: CapnpStructBuilder): com.capnproto.schema.CodeGeneratorRequest.RequestedFile.Builder = new com.capnproto.schema.CodeGeneratorRequest.RequestedFile.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.schema.CodeGeneratorRequest.RequestedFile.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.schema.CodeGeneratorRequest.RequestedFileMutable(struct) with StructBuilder[com.capnproto.schema.CodeGeneratorRequest.RequestedFile, com.capnproto.schema.CodeGeneratorRequest.RequestedFile.Builder] {
      override type MetaBuilderT = com.capnproto.schema.CodeGeneratorRequest.RequestedFile.Builder.type

      override def meta: RequestedFile.type = RequestedFile
      override def metaBuilder: MetaBuilderT = com.capnproto.schema.CodeGeneratorRequest.RequestedFile.Builder
      def setId(value: java.lang.Long): Builder = { struct.setLong(0, value); this }
      def setFilename(value: String): Builder = { struct.setString(0, value); this }
      def initImports(count: Int): Seq[com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import.Builder] = {
        val list = struct.initPointerList(1, count, com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import.Builder)
        Range(0, count).map(i => new com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import.Builder(list.initStruct(i, com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import.Builder)))
      }
      def setImports(buildFn: CapnpArenaBuilder => Seq[com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import.Builder]): Builder = { struct.setStructList(1, com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import.Builder, buildFn(struct.arena).map(_.struct)); this }
    }

    object __Import extends MetaStruct[__Import] {
      override type Self = __Import.type
      override val recordName: String = "Import"
      override def create(struct: CapnpStruct): __Import = new __ImportMutable(struct)
      def newBuilder(arena: CapnpArenaBuilder): com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import.Builder = {
        val (segment, pointerOffset) = arena.allocate(1)
        val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import.Builder.dataSectionSizeWords, com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import.Builder.pointerSectionSizeWords)
        new com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import.Builder(struct)
      }

      object Builder extends MetaStructBuilder[com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import, com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import.Builder] {
        override type Self = com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import.Builder.type
        override val recordName: String = "Import"
        override val dataSectionSizeWords: Short = 1
        override val pointerSectionSizeWords: Short = 1
        override def create(struct: CapnpStructBuilder): com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import.Builder = new com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import.Builder(struct)
        override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import.fields
      }
      class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__ImportMutable(struct) with StructBuilder[com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import, com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import.Builder] {
        override type MetaBuilderT = com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import.Builder.type

        override def meta: __Import.type = __Import
        override def metaBuilder: MetaBuilderT = com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import.Builder
        def setId(value: java.lang.Long): Builder = { struct.setLong(0, value); this }
        def setName(value: String): Builder = { struct.setString(0, value); this }
      }



      val id = new FieldDescriptor[java.lang.Long, __Import, __Import.type](
        name = "id",
        meta = __Import,
        getter = _.id,
        manifest = manifest[java.lang.Long],
        isUnion = false
      )

      val name = new FieldDescriptor[String, __Import, __Import.type](
        name = "name",
        meta = __Import,
        getter = _.name,
        manifest = manifest[String],
        isUnion = false
      )
      override val fields: Seq[FieldDescriptor[_, __Import, __Import.type]] = Seq(id, name)
    }

    trait __Import extends Struct[__Import] {
      override type MetaT = __Import.type

      override def meta: __Import.type = __Import
      def struct: CapnpStruct

      def id: Option[java.lang.Long]
      def name: Option[String]
    }

    trait __ImportProxy extends __Import {
      def underlying: __Import

      override def struct: CapnpStruct = underlying.struct

      override def id: Option[java.lang.Long]
      override def name: Option[String]
    }

    class __ImportMutable(override val struct: CapnpStruct) extends __Import {

      override def id: Option[java.lang.Long] = struct.getLong(0)
      override def name: Option[String] = struct.getString(0)
    }


    val id = new FieldDescriptor[java.lang.Long, RequestedFile, RequestedFile.type](
      name = "id",
      meta = RequestedFile,
      getter = _.id,
      manifest = manifest[java.lang.Long],
      isUnion = false
    )

    val filename = new FieldDescriptor[String, RequestedFile, RequestedFile.type](
      name = "filename",
      meta = RequestedFile,
      getter = _.filename,
      manifest = manifest[String],
      isUnion = false
    )

    val imports = new FieldDescriptor[Seq[com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import], RequestedFile, RequestedFile.type](
      name = "imports",
      meta = RequestedFile,
      getter = x => Some(x.imports),
      manifest = manifest[Seq[com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import]],
      isUnion = false
    )
    override val fields: Seq[FieldDescriptor[_, RequestedFile, RequestedFile.type]] = Seq(id, filename, imports)
  }

  trait RequestedFile extends Struct[RequestedFile] {
    override type MetaT = RequestedFile.type

    override def meta: RequestedFile.type = RequestedFile
    def struct: CapnpStruct

    def id: Option[java.lang.Long]
    def filename: Option[String]
    def imports: Seq[com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import]
  }

  trait RequestedFileProxy extends RequestedFile {
    def underlying: RequestedFile

    override def struct: CapnpStruct = underlying.struct

    override def id: Option[java.lang.Long]
    override def filename: Option[String]
    override def imports: Seq[com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import]
  }

  class RequestedFileMutable(override val struct: CapnpStruct) extends RequestedFile {

    override def id: Option[java.lang.Long] = struct.getLong(0)
    override def filename: Option[String] = struct.getString(0)
    override def imports: Seq[com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import] = struct.getStructList(1).map(new com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__ImportMutable(_))
  }


  val nodes = new FieldDescriptor[Seq[com.capnproto.schema.Node], CodeGeneratorRequest, CodeGeneratorRequest.type](
    name = "nodes",
    meta = CodeGeneratorRequest,
    getter = x => Some(x.nodes),
    manifest = manifest[Seq[com.capnproto.schema.Node]],
    isUnion = false
  )

  val requestedFiles = new FieldDescriptor[Seq[com.capnproto.schema.CodeGeneratorRequest.RequestedFile], CodeGeneratorRequest, CodeGeneratorRequest.type](
    name = "requestedFiles",
    meta = CodeGeneratorRequest,
    getter = x => Some(x.requestedFiles),
    manifest = manifest[Seq[com.capnproto.schema.CodeGeneratorRequest.RequestedFile]],
    isUnion = false
  )
  override val fields: Seq[FieldDescriptor[_, CodeGeneratorRequest, CodeGeneratorRequest.type]] = Seq(nodes, requestedFiles)
}

trait CodeGeneratorRequest extends Struct[CodeGeneratorRequest] {
  override type MetaT = CodeGeneratorRequest.type

  override def meta: CodeGeneratorRequest.type = CodeGeneratorRequest
  def struct: CapnpStruct

  def nodes: Seq[com.capnproto.schema.Node]
  def requestedFiles: Seq[com.capnproto.schema.CodeGeneratorRequest.RequestedFile]
}

trait CodeGeneratorRequestProxy extends CodeGeneratorRequest {
  def underlying: CodeGeneratorRequest

  override def struct: CapnpStruct = underlying.struct

  override def nodes: Seq[com.capnproto.schema.Node]
  override def requestedFiles: Seq[com.capnproto.schema.CodeGeneratorRequest.RequestedFile]
}

class CodeGeneratorRequestMutable(override val struct: CapnpStruct) extends CodeGeneratorRequest {

  override def nodes: Seq[com.capnproto.schema.Node] = struct.getStructList(0).map(new com.capnproto.schema.NodeMutable(_))
  override def requestedFiles: Seq[com.capnproto.schema.CodeGeneratorRequest.RequestedFile] = struct.getStructList(1).map(new com.capnproto.schema.CodeGeneratorRequest.RequestedFileMutable(_))
}
