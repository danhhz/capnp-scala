// examples/src/main/capnp/schema.capnp

package com.capnproto.schema

import com.capnproto.core.{HasUnion, UnionMeta, UnionValue, UntypedFieldDescriptor,
  FieldDescriptor, UntypedStruct, Struct, UntypedMetaStruct, MetaStruct,
  StructBuilder, MetaStructBuilder, MetaInterface, UntypedMetaInterface,
  Interface, UntypedInterface, MethodDescriptor, CapnpStruct, CapnpStructBuilder,
  Pointer, CapnpList, CapnpTag, CapnpArenaBuilder, CapnpArena, Enum, EnumMeta}
import com.twitter.util.Future
import java.nio.ByteBuffer

object Node extends MetaStruct[Node] {
  override type Self = Node.type
  override val structName: String = "Node"
  override def create(struct: CapnpStruct): Node = new NodeMutable(struct)
  def newBuilder(arena: CapnpArenaBuilder): com.capnproto.schema.Node.Builder = {
    val (segment, pointerOffset) = arena.allocate(1)
    val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.schema.Node.Builder.dataSectionSizeWords, com.capnproto.schema.Node.Builder.pointerSectionSizeWords)
    new com.capnproto.schema.Node.Builder(struct)
  }

  object Builder extends MetaStructBuilder[com.capnproto.schema.Node, com.capnproto.schema.Node.Builder] {
    override type Self = com.capnproto.schema.Node.Builder.type
    override val structName: String = "Node"
    override val dataSectionSizeWords: Short = 5
    override val pointerSectionSizeWords: Short = 5
    override def create(struct: CapnpStructBuilder): com.capnproto.schema.Node.Builder = new com.capnproto.schema.Node.Builder(struct)
    override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.schema.Node.fields
  }
  class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.schema.NodeMutable(struct) with StructBuilder[com.capnproto.schema.Node, com.capnproto.schema.Node.Builder] {
    override type MetaBuilderT = com.capnproto.schema.Node.Builder.type

    override def meta: Node.type = Node
    override def metaBuilder: MetaBuilderT = com.capnproto.schema.Node.Builder
    def setId(value: Long): Builder = { struct.setLong(0, value, com.capnproto.schema.Node.id.default); this }
    def setDisplayName(value: String): Builder = { struct.setString(0, value); this }
    def setDisplayNamePrefixLength(value: Int): Builder = { struct.setInt(2, value, com.capnproto.schema.Node.displayNamePrefixLength.default); this }
    def setScopeId(value: Long): Builder = { struct.setLong(2, value, com.capnproto.schema.Node.scopeId.default); this }
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
    def setFile(value: Unit): Builder = { struct.setNone(); struct.setShort(6, 0); this }
    override def __struct: com.capnproto.schema.Node.__Struct.Builder = new com.capnproto.schema.Node.__Struct.Builder(struct)
    override def __enum: com.capnproto.schema.Node.__Enum.Builder = new com.capnproto.schema.Node.__Enum.Builder(struct)
    override def interface: com.capnproto.schema.Node.Interface.Builder = new com.capnproto.schema.Node.Interface.Builder(struct)
    override def const: com.capnproto.schema.Node.Const.Builder = new com.capnproto.schema.Node.Const.Builder(struct)
    override def annotation: com.capnproto.schema.Node.Annotation.Builder = new com.capnproto.schema.Node.Annotation.Builder(struct)
  }

  object NestedNode extends MetaStruct[NestedNode] {
    override type Self = NestedNode.type
    override val structName: String = "NestedNode"
    override def create(struct: CapnpStruct): NestedNode = new NestedNodeMutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.schema.Node.NestedNode.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.schema.Node.NestedNode.Builder.dataSectionSizeWords, com.capnproto.schema.Node.NestedNode.Builder.pointerSectionSizeWords)
      new com.capnproto.schema.Node.NestedNode.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.schema.Node.NestedNode, com.capnproto.schema.Node.NestedNode.Builder] {
      override type Self = com.capnproto.schema.Node.NestedNode.Builder.type
      override val structName: String = "NestedNode"
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
      def setId(value: Long): Builder = { struct.setLong(0, value, com.capnproto.schema.Node.NestedNode.id.default); this }
    }

    val name = new FieldDescriptor[String, NestedNode, NestedNode.type](
      name = "name",
      meta = NestedNode,
      default = Option(""),
      getter = _.name,
      manifest = manifest[String],
      discriminantValue = None
    )

    val id = new FieldDescriptor[Long, NestedNode, NestedNode.type](
      name = "id",
      meta = NestedNode,
      default = Option(0L),
      getter = _.id,
      manifest = manifest[Long],
      discriminantValue = None
    )
    override val fields: Seq[FieldDescriptor[_, NestedNode, NestedNode.type]] = Seq(name, id)
  }

  trait NestedNode extends Struct[NestedNode] {
    override type MetaT = NestedNode.type
    override type MetaBuilderT = com.capnproto.schema.Node.NestedNode.Builder.type

    override def meta: NestedNode.type = NestedNode
    override def metaBuilder: com.capnproto.schema.Node.NestedNode.Builder.type = com.capnproto.schema.Node.NestedNode.Builder

    def struct: CapnpStruct

    def name: Option[String]
    def id: Option[Long]
  }

  class NestedNodeMutable(override val struct: CapnpStruct) extends NestedNode {
    override def name: Option[String] = struct.getString(0, com.capnproto.schema.Node.NestedNode.name.default)
    override def id: Option[Long] = struct.getLong(0, com.capnproto.schema.Node.NestedNode.id.default)
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
    override val structName: String = "Struct"
    override def create(struct: CapnpStruct): __Struct = new __StructMutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.schema.Node.__Struct.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.schema.Node.__Struct.Builder.dataSectionSizeWords, com.capnproto.schema.Node.__Struct.Builder.pointerSectionSizeWords)
      new com.capnproto.schema.Node.__Struct.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.schema.Node.__Struct, com.capnproto.schema.Node.__Struct.Builder] {
      override type Self = com.capnproto.schema.Node.__Struct.Builder.type
      override val structName: String = "Struct"
      override val dataSectionSizeWords: Short = 5
      override val pointerSectionSizeWords: Short = 5
      override def create(struct: CapnpStructBuilder): com.capnproto.schema.Node.__Struct.Builder = new com.capnproto.schema.Node.__Struct.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.schema.Node.__Struct.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.schema.Node.__StructMutable(struct) with StructBuilder[com.capnproto.schema.Node.__Struct, com.capnproto.schema.Node.__Struct.Builder] {
      override type MetaBuilderT = com.capnproto.schema.Node.__Struct.Builder.type

      override def meta: __Struct.type = __Struct
      override def metaBuilder: MetaBuilderT = com.capnproto.schema.Node.__Struct.Builder
      def setDataWordCount(value: Short): Builder = { struct.setShort(7, value, com.capnproto.schema.Node.__Struct.dataWordCount.default); this }
      def setPointerCount(value: Short): Builder = { struct.setShort(12, value, com.capnproto.schema.Node.__Struct.pointerCount.default); this }
      def setPreferredListEncoding(value: com.capnproto.schema.ElementSize): Builder = { struct.setShort(13, value.id.toShort, com.capnproto.schema.Node.__Struct.preferredListEncoding.default.map(_.id)); this }
      def setIsGroup(value: Boolean): Builder = { struct.setBoolean(224, value, com.capnproto.schema.Node.__Struct.isGroup.default); this }
      def setDiscriminantCount(value: Short): Builder = { struct.setShort(15, value, com.capnproto.schema.Node.__Struct.discriminantCount.default); this }
      def setDiscriminantOffset(value: Int): Builder = { struct.setInt(8, value, com.capnproto.schema.Node.__Struct.discriminantOffset.default); this }
      def init__Fields(count: Int): Seq[com.capnproto.schema.Field.Builder] = {
        val list = struct.initPointerList(3, count, com.capnproto.schema.Field.Builder)
        Range(0, count).map(i => new com.capnproto.schema.Field.Builder(list.initStruct(i, com.capnproto.schema.Field.Builder)))
      }
      def set__Fields(buildFn: CapnpArenaBuilder => Seq[com.capnproto.schema.Field.Builder]): Builder = { struct.setStructList(3, com.capnproto.schema.Field.Builder, buildFn(struct.arena).map(_.struct)); this }
    }

    val dataWordCount = new FieldDescriptor[Short, __Struct, __Struct.type](
      name = "dataWordCount",
      meta = __Struct,
      default = Option(0.toShort),
      getter = _.dataWordCount,
      manifest = manifest[Short],
      discriminantValue = None
    )

    val pointerCount = new FieldDescriptor[Short, __Struct, __Struct.type](
      name = "pointerCount",
      meta = __Struct,
      default = Option(0.toShort),
      getter = _.pointerCount,
      manifest = manifest[Short],
      discriminantValue = None
    )

    val preferredListEncoding = new FieldDescriptor[com.capnproto.schema.ElementSize, __Struct, __Struct.type](
      name = "preferredListEncoding",
      meta = __Struct,
      default = Option(com.capnproto.schema.ElementSize.findByIdOrNull(0.toShort)),
      getter = _.preferredListEncoding,
      manifest = manifest[com.capnproto.schema.ElementSize],
      discriminantValue = None
    )

    val isGroup = new FieldDescriptor[Boolean, __Struct, __Struct.type](
      name = "isGroup",
      meta = __Struct,
      default = Option(false),
      getter = _.isGroup,
      manifest = manifest[Boolean],
      discriminantValue = None
    )

    val discriminantCount = new FieldDescriptor[Short, __Struct, __Struct.type](
      name = "discriminantCount",
      meta = __Struct,
      default = Option(0.toShort),
      getter = _.discriminantCount,
      manifest = manifest[Short],
      discriminantValue = None
    )

    val discriminantOffset = new FieldDescriptor[Int, __Struct, __Struct.type](
      name = "discriminantOffset",
      meta = __Struct,
      default = Option(0),
      getter = _.discriminantOffset,
      manifest = manifest[Int],
      discriminantValue = None
    )

    val __fields = new FieldDescriptor[Seq[com.capnproto.schema.Field], __Struct, __Struct.type](
      name = "fields",
      meta = __Struct,
      default = Option(null),
      getter = x => Some(x.__fields),
      manifest = manifest[Seq[com.capnproto.schema.Field]],
      discriminantValue = None
    )
    override val fields: Seq[FieldDescriptor[_, __Struct, __Struct.type]] = Seq(dataWordCount, pointerCount, preferredListEncoding, isGroup, discriminantCount, discriminantOffset, __fields)
  }

  trait __Struct extends Struct[__Struct] {
    override type MetaT = __Struct.type
    override type MetaBuilderT = com.capnproto.schema.Node.__Struct.Builder.type

    override def meta: __Struct.type = __Struct
    override def metaBuilder: com.capnproto.schema.Node.__Struct.Builder.type = com.capnproto.schema.Node.__Struct.Builder

    def struct: CapnpStruct

    def dataWordCount: Option[Short]
    def pointerCount: Option[Short]
    def preferredListEncoding: Option[com.capnproto.schema.ElementSize]
    def isGroup: Option[Boolean]
    def discriminantCount: Option[Short]
    def discriminantOffset: Option[Int]
    def __fields: Seq[com.capnproto.schema.Field]
  }

  class __StructMutable(override val struct: CapnpStruct) extends __Struct {
    override def dataWordCount: Option[Short] = struct.getShort(7, com.capnproto.schema.Node.__Struct.dataWordCount.default)
    override def pointerCount: Option[Short] = struct.getShort(12, com.capnproto.schema.Node.__Struct.pointerCount.default)
    override def preferredListEncoding: Option[com.capnproto.schema.ElementSize] = struct.getShort(13, com.capnproto.schema.Node.__Struct.preferredListEncoding.default.map(_.id)).map(id => com.capnproto.schema.ElementSize.findById(id).getOrElse(com.capnproto.schema.ElementSize.Unknown(id.toShort)))
    override def isGroup: Option[Boolean] = struct.getBoolean(224, com.capnproto.schema.Node.__Struct.isGroup.default)
    override def discriminantCount: Option[Short] = struct.getShort(15, com.capnproto.schema.Node.__Struct.discriminantCount.default)
    override def discriminantOffset: Option[Int] = struct.getInt(8, com.capnproto.schema.Node.__Struct.discriminantOffset.default)
    override def __fields: Seq[com.capnproto.schema.Field] = struct.getList(3, (l: CapnpList) => (o: Int) => l.getStruct(o, com.capnproto.schema.Field), com.capnproto.schema.Node.__Struct.__fields.default)
  }
  object __Enum extends MetaStruct[__Enum] {
    override type Self = __Enum.type
    override val structName: String = "Enum"
    override def create(struct: CapnpStruct): __Enum = new __EnumMutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.schema.Node.__Enum.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.schema.Node.__Enum.Builder.dataSectionSizeWords, com.capnproto.schema.Node.__Enum.Builder.pointerSectionSizeWords)
      new com.capnproto.schema.Node.__Enum.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.schema.Node.__Enum, com.capnproto.schema.Node.__Enum.Builder] {
      override type Self = com.capnproto.schema.Node.__Enum.Builder.type
      override val structName: String = "Enum"
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
      default = Option(null),
      getter = x => Some(x.enumerants),
      manifest = manifest[Seq[com.capnproto.schema.Enumerant]],
      discriminantValue = None
    )
    override val fields: Seq[FieldDescriptor[_, __Enum, __Enum.type]] = Seq(enumerants)
  }

  trait __Enum extends Struct[__Enum] {
    override type MetaT = __Enum.type
    override type MetaBuilderT = com.capnproto.schema.Node.__Enum.Builder.type

    override def meta: __Enum.type = __Enum
    override def metaBuilder: com.capnproto.schema.Node.__Enum.Builder.type = com.capnproto.schema.Node.__Enum.Builder

    def struct: CapnpStruct

    def enumerants: Seq[com.capnproto.schema.Enumerant]
  }

  class __EnumMutable(override val struct: CapnpStruct) extends __Enum {
    override def enumerants: Seq[com.capnproto.schema.Enumerant] = struct.getList(3, (l: CapnpList) => (o: Int) => l.getStruct(o, com.capnproto.schema.Enumerant), com.capnproto.schema.Node.__Enum.enumerants.default)
  }
  object Interface extends MetaStruct[Interface] {
    override type Self = Interface.type
    override val structName: String = "Interface"
    override def create(struct: CapnpStruct): Interface = new InterfaceMutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.schema.Node.Interface.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.schema.Node.Interface.Builder.dataSectionSizeWords, com.capnproto.schema.Node.Interface.Builder.pointerSectionSizeWords)
      new com.capnproto.schema.Node.Interface.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.schema.Node.Interface, com.capnproto.schema.Node.Interface.Builder] {
      override type Self = com.capnproto.schema.Node.Interface.Builder.type
      override val structName: String = "Interface"
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
      default = Option(null),
      getter = x => Some(x.methods),
      manifest = manifest[Seq[com.capnproto.schema.Method]],
      discriminantValue = None
    )
    override val fields: Seq[FieldDescriptor[_, Interface, Interface.type]] = Seq(methods)
  }

  trait Interface extends Struct[Interface] {
    override type MetaT = Interface.type
    override type MetaBuilderT = com.capnproto.schema.Node.Interface.Builder.type

    override def meta: Interface.type = Interface
    override def metaBuilder: com.capnproto.schema.Node.Interface.Builder.type = com.capnproto.schema.Node.Interface.Builder

    def struct: CapnpStruct

    def methods: Seq[com.capnproto.schema.Method]
  }

  class InterfaceMutable(override val struct: CapnpStruct) extends Interface {
    override def methods: Seq[com.capnproto.schema.Method] = struct.getList(3, (l: CapnpList) => (o: Int) => l.getStruct(o, com.capnproto.schema.Method), com.capnproto.schema.Node.Interface.methods.default)
  }
  object Const extends MetaStruct[Const] {
    override type Self = Const.type
    override val structName: String = "Const"
    override def create(struct: CapnpStruct): Const = new ConstMutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.schema.Node.Const.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.schema.Node.Const.Builder.dataSectionSizeWords, com.capnproto.schema.Node.Const.Builder.pointerSectionSizeWords)
      new com.capnproto.schema.Node.Const.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.schema.Node.Const, com.capnproto.schema.Node.Const.Builder] {
      override type Self = com.capnproto.schema.Node.Const.Builder.type
      override val structName: String = "Const"
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
      default = Option(null),
      getter = _.__type,
      manifest = manifest[com.capnproto.schema.__Type],
      discriminantValue = None
    )

    val value = new FieldDescriptor[com.capnproto.schema.Value, Const, Const.type](
      name = "value",
      meta = Const,
      default = Option(null),
      getter = _.value,
      manifest = manifest[com.capnproto.schema.Value],
      discriminantValue = None
    )
    override val fields: Seq[FieldDescriptor[_, Const, Const.type]] = Seq(__type, value)
  }

  trait Const extends Struct[Const] {
    override type MetaT = Const.type
    override type MetaBuilderT = com.capnproto.schema.Node.Const.Builder.type

    override def meta: Const.type = Const
    override def metaBuilder: com.capnproto.schema.Node.Const.Builder.type = com.capnproto.schema.Node.Const.Builder

    def struct: CapnpStruct

    def __type: Option[com.capnproto.schema.__Type]
    def value: Option[com.capnproto.schema.Value]
  }

  class ConstMutable(override val struct: CapnpStruct) extends Const {
    override def __type: Option[com.capnproto.schema.__Type] = struct.getStruct(3, com.capnproto.schema.__Type, com.capnproto.schema.Node.Const.__type.default)
    override def value: Option[com.capnproto.schema.Value] = struct.getStruct(4, com.capnproto.schema.Value, com.capnproto.schema.Node.Const.value.default)
  }
  object Annotation extends MetaStruct[Annotation] {
    override type Self = Annotation.type
    override val structName: String = "Annotation"
    override def create(struct: CapnpStruct): Annotation = new AnnotationMutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.schema.Node.Annotation.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.schema.Node.Annotation.Builder.dataSectionSizeWords, com.capnproto.schema.Node.Annotation.Builder.pointerSectionSizeWords)
      new com.capnproto.schema.Node.Annotation.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.schema.Node.Annotation, com.capnproto.schema.Node.Annotation.Builder] {
      override type Self = com.capnproto.schema.Node.Annotation.Builder.type
      override val structName: String = "Annotation"
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
      def setTargetsFile(value: Boolean): Builder = { struct.setBoolean(112, value, com.capnproto.schema.Node.Annotation.targetsFile.default); this }
      def setTargetsConst(value: Boolean): Builder = { struct.setBoolean(113, value, com.capnproto.schema.Node.Annotation.targetsConst.default); this }
      def setTargetsEnum(value: Boolean): Builder = { struct.setBoolean(114, value, com.capnproto.schema.Node.Annotation.targetsEnum.default); this }
      def setTargetsEnumerant(value: Boolean): Builder = { struct.setBoolean(115, value, com.capnproto.schema.Node.Annotation.targetsEnumerant.default); this }
      def setTargetsStruct(value: Boolean): Builder = { struct.setBoolean(116, value, com.capnproto.schema.Node.Annotation.targetsStruct.default); this }
      def setTargetsField(value: Boolean): Builder = { struct.setBoolean(117, value, com.capnproto.schema.Node.Annotation.targetsField.default); this }
      def setTargetsUnion(value: Boolean): Builder = { struct.setBoolean(118, value, com.capnproto.schema.Node.Annotation.targetsUnion.default); this }
      def setTargetsGroup(value: Boolean): Builder = { struct.setBoolean(119, value, com.capnproto.schema.Node.Annotation.targetsGroup.default); this }
      def setTargetsInterface(value: Boolean): Builder = { struct.setBoolean(120, value, com.capnproto.schema.Node.Annotation.targetsInterface.default); this }
      def setTargetsMethod(value: Boolean): Builder = { struct.setBoolean(121, value, com.capnproto.schema.Node.Annotation.targetsMethod.default); this }
      def setTargetsParam(value: Boolean): Builder = { struct.setBoolean(122, value, com.capnproto.schema.Node.Annotation.targetsParam.default); this }
      def setTargetsAnnotation(value: Boolean): Builder = { struct.setBoolean(123, value, com.capnproto.schema.Node.Annotation.targetsAnnotation.default); this }
    }

    val __type = new FieldDescriptor[com.capnproto.schema.__Type, Annotation, Annotation.type](
      name = "type",
      meta = Annotation,
      default = Option(null),
      getter = _.__type,
      manifest = manifest[com.capnproto.schema.__Type],
      discriminantValue = None
    )

    val targetsFile = new FieldDescriptor[Boolean, Annotation, Annotation.type](
      name = "targetsFile",
      meta = Annotation,
      default = Option(false),
      getter = _.targetsFile,
      manifest = manifest[Boolean],
      discriminantValue = None
    )

    val targetsConst = new FieldDescriptor[Boolean, Annotation, Annotation.type](
      name = "targetsConst",
      meta = Annotation,
      default = Option(false),
      getter = _.targetsConst,
      manifest = manifest[Boolean],
      discriminantValue = None
    )

    val targetsEnum = new FieldDescriptor[Boolean, Annotation, Annotation.type](
      name = "targetsEnum",
      meta = Annotation,
      default = Option(false),
      getter = _.targetsEnum,
      manifest = manifest[Boolean],
      discriminantValue = None
    )

    val targetsEnumerant = new FieldDescriptor[Boolean, Annotation, Annotation.type](
      name = "targetsEnumerant",
      meta = Annotation,
      default = Option(false),
      getter = _.targetsEnumerant,
      manifest = manifest[Boolean],
      discriminantValue = None
    )

    val targetsStruct = new FieldDescriptor[Boolean, Annotation, Annotation.type](
      name = "targetsStruct",
      meta = Annotation,
      default = Option(false),
      getter = _.targetsStruct,
      manifest = manifest[Boolean],
      discriminantValue = None
    )

    val targetsField = new FieldDescriptor[Boolean, Annotation, Annotation.type](
      name = "targetsField",
      meta = Annotation,
      default = Option(false),
      getter = _.targetsField,
      manifest = manifest[Boolean],
      discriminantValue = None
    )

    val targetsUnion = new FieldDescriptor[Boolean, Annotation, Annotation.type](
      name = "targetsUnion",
      meta = Annotation,
      default = Option(false),
      getter = _.targetsUnion,
      manifest = manifest[Boolean],
      discriminantValue = None
    )

    val targetsGroup = new FieldDescriptor[Boolean, Annotation, Annotation.type](
      name = "targetsGroup",
      meta = Annotation,
      default = Option(false),
      getter = _.targetsGroup,
      manifest = manifest[Boolean],
      discriminantValue = None
    )

    val targetsInterface = new FieldDescriptor[Boolean, Annotation, Annotation.type](
      name = "targetsInterface",
      meta = Annotation,
      default = Option(false),
      getter = _.targetsInterface,
      manifest = manifest[Boolean],
      discriminantValue = None
    )

    val targetsMethod = new FieldDescriptor[Boolean, Annotation, Annotation.type](
      name = "targetsMethod",
      meta = Annotation,
      default = Option(false),
      getter = _.targetsMethod,
      manifest = manifest[Boolean],
      discriminantValue = None
    )

    val targetsParam = new FieldDescriptor[Boolean, Annotation, Annotation.type](
      name = "targetsParam",
      meta = Annotation,
      default = Option(false),
      getter = _.targetsParam,
      manifest = manifest[Boolean],
      discriminantValue = None
    )

    val targetsAnnotation = new FieldDescriptor[Boolean, Annotation, Annotation.type](
      name = "targetsAnnotation",
      meta = Annotation,
      default = Option(false),
      getter = _.targetsAnnotation,
      manifest = manifest[Boolean],
      discriminantValue = None
    )
    override val fields: Seq[FieldDescriptor[_, Annotation, Annotation.type]] = Seq(__type, targetsFile, targetsConst, targetsEnum, targetsEnumerant, targetsStruct, targetsField, targetsUnion, targetsGroup, targetsInterface, targetsMethod, targetsParam, targetsAnnotation)
  }

  trait Annotation extends Struct[Annotation] {
    override type MetaT = Annotation.type
    override type MetaBuilderT = com.capnproto.schema.Node.Annotation.Builder.type

    override def meta: Annotation.type = Annotation
    override def metaBuilder: com.capnproto.schema.Node.Annotation.Builder.type = com.capnproto.schema.Node.Annotation.Builder

    def struct: CapnpStruct

    def __type: Option[com.capnproto.schema.__Type]
    def targetsFile: Option[Boolean]
    def targetsConst: Option[Boolean]
    def targetsEnum: Option[Boolean]
    def targetsEnumerant: Option[Boolean]
    def targetsStruct: Option[Boolean]
    def targetsField: Option[Boolean]
    def targetsUnion: Option[Boolean]
    def targetsGroup: Option[Boolean]
    def targetsInterface: Option[Boolean]
    def targetsMethod: Option[Boolean]
    def targetsParam: Option[Boolean]
    def targetsAnnotation: Option[Boolean]
  }

  class AnnotationMutable(override val struct: CapnpStruct) extends Annotation {
    override def __type: Option[com.capnproto.schema.__Type] = struct.getStruct(3, com.capnproto.schema.__Type, com.capnproto.schema.Node.Annotation.__type.default)
    override def targetsFile: Option[Boolean] = struct.getBoolean(112, com.capnproto.schema.Node.Annotation.targetsFile.default)
    override def targetsConst: Option[Boolean] = struct.getBoolean(113, com.capnproto.schema.Node.Annotation.targetsConst.default)
    override def targetsEnum: Option[Boolean] = struct.getBoolean(114, com.capnproto.schema.Node.Annotation.targetsEnum.default)
    override def targetsEnumerant: Option[Boolean] = struct.getBoolean(115, com.capnproto.schema.Node.Annotation.targetsEnumerant.default)
    override def targetsStruct: Option[Boolean] = struct.getBoolean(116, com.capnproto.schema.Node.Annotation.targetsStruct.default)
    override def targetsField: Option[Boolean] = struct.getBoolean(117, com.capnproto.schema.Node.Annotation.targetsField.default)
    override def targetsUnion: Option[Boolean] = struct.getBoolean(118, com.capnproto.schema.Node.Annotation.targetsUnion.default)
    override def targetsGroup: Option[Boolean] = struct.getBoolean(119, com.capnproto.schema.Node.Annotation.targetsGroup.default)
    override def targetsInterface: Option[Boolean] = struct.getBoolean(120, com.capnproto.schema.Node.Annotation.targetsInterface.default)
    override def targetsMethod: Option[Boolean] = struct.getBoolean(121, com.capnproto.schema.Node.Annotation.targetsMethod.default)
    override def targetsParam: Option[Boolean] = struct.getBoolean(122, com.capnproto.schema.Node.Annotation.targetsParam.default)
    override def targetsAnnotation: Option[Boolean] = struct.getBoolean(123, com.capnproto.schema.Node.Annotation.targetsAnnotation.default)
  }
  val id = new FieldDescriptor[Long, Node, Node.type](
    name = "id",
    meta = Node,
    default = Option(0L),
    getter = _.id,
    manifest = manifest[Long],
    discriminantValue = None
  )

  val displayName = new FieldDescriptor[String, Node, Node.type](
    name = "displayName",
    meta = Node,
    default = Option(""),
    getter = _.displayName,
    manifest = manifest[String],
    discriminantValue = None
  )

  val displayNamePrefixLength = new FieldDescriptor[Int, Node, Node.type](
    name = "displayNamePrefixLength",
    meta = Node,
    default = Option(0),
    getter = _.displayNamePrefixLength,
    manifest = manifest[Int],
    discriminantValue = None
  )

  val scopeId = new FieldDescriptor[Long, Node, Node.type](
    name = "scopeId",
    meta = Node,
    default = Option(0L),
    getter = _.scopeId,
    manifest = manifest[Long],
    discriminantValue = None
  )

  val nestedNodes = new FieldDescriptor[Seq[com.capnproto.schema.Node.NestedNode], Node, Node.type](
    name = "nestedNodes",
    meta = Node,
    default = Option(null),
    getter = x => Some(x.nestedNodes),
    manifest = manifest[Seq[com.capnproto.schema.Node.NestedNode]],
    discriminantValue = None
  )

  val annotations = new FieldDescriptor[Seq[com.capnproto.schema.Annotation], Node, Node.type](
    name = "annotations",
    meta = Node,
    default = Option(null),
    getter = x => Some(x.annotations),
    manifest = manifest[Seq[com.capnproto.schema.Annotation]],
    discriminantValue = None
  )

  val file = new FieldDescriptor[Unit, Node, Node.type](
    name = "file",
    meta = Node,
    default = Option(Unit),
    getter = _.file,
    manifest = manifest[Unit],
    discriminantValue = Some(0)
  )

  val __struct = new FieldDescriptor[com.capnproto.schema.Node.__Struct, Node, Node.type](
    name = "struct",
    meta = Node,
    default = None,
    getter = x => Some(x.__struct),
    manifest = manifest[com.capnproto.schema.Node.__Struct],
    discriminantValue = Some(1)
  )

  val __enum = new FieldDescriptor[com.capnproto.schema.Node.__Enum, Node, Node.type](
    name = "enum",
    meta = Node,
    default = None,
    getter = x => Some(x.__enum),
    manifest = manifest[com.capnproto.schema.Node.__Enum],
    discriminantValue = Some(2)
  )

  val interface = new FieldDescriptor[com.capnproto.schema.Node.Interface, Node, Node.type](
    name = "interface",
    meta = Node,
    default = None,
    getter = x => Some(x.interface),
    manifest = manifest[com.capnproto.schema.Node.Interface],
    discriminantValue = Some(3)
  )

  val const = new FieldDescriptor[com.capnproto.schema.Node.Const, Node, Node.type](
    name = "const",
    meta = Node,
    default = None,
    getter = x => Some(x.const),
    manifest = manifest[com.capnproto.schema.Node.Const],
    discriminantValue = Some(4)
  )

  val annotation = new FieldDescriptor[com.capnproto.schema.Node.Annotation, Node, Node.type](
    name = "annotation",
    meta = Node,
    default = None,
    getter = x => Some(x.annotation),
    manifest = manifest[com.capnproto.schema.Node.Annotation],
    discriminantValue = Some(5)
  )
  override val fields: Seq[FieldDescriptor[_, Node, Node.type]] = Seq(id, displayName, displayNamePrefixLength, scopeId, nestedNodes, annotations, file, __struct, __enum, interface, const, annotation)
}

trait Node extends Struct[Node] with HasUnion[com.capnproto.schema.Node.Union] {
  override type MetaT = Node.type
  override type MetaBuilderT = com.capnproto.schema.Node.Builder.type

  override def meta: Node.type = Node
  override def metaBuilder: com.capnproto.schema.Node.Builder.type = com.capnproto.schema.Node.Builder

  def struct: CapnpStruct

  def id: Option[Long]
  def displayName: Option[String]
  def displayNamePrefixLength: Option[Int]
  def scopeId: Option[Long]
  def nestedNodes: Seq[com.capnproto.schema.Node.NestedNode]
  def annotations: Seq[com.capnproto.schema.Annotation]
  def file: Option[Unit]
  def __struct: com.capnproto.schema.Node.__Struct
  def __enum: com.capnproto.schema.Node.__Enum
  def interface: com.capnproto.schema.Node.Interface
  def const: com.capnproto.schema.Node.Const
  def annotation: com.capnproto.schema.Node.Annotation
}

class NodeMutable(override val struct: CapnpStruct) extends Node {
  override def discriminant: Short = (struct.getShort(6).getOrElse(0.toShort): Short)
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

  override def id: Option[Long] = struct.getLong(0, com.capnproto.schema.Node.id.default)
  override def displayName: Option[String] = struct.getString(0, com.capnproto.schema.Node.displayName.default)
  override def displayNamePrefixLength: Option[Int] = struct.getInt(2, com.capnproto.schema.Node.displayNamePrefixLength.default)
  override def scopeId: Option[Long] = struct.getLong(2, com.capnproto.schema.Node.scopeId.default)
  override def nestedNodes: Seq[com.capnproto.schema.Node.NestedNode] = struct.getList(1, (l: CapnpList) => (o: Int) => l.getStruct(o, com.capnproto.schema.Node.NestedNode), com.capnproto.schema.Node.nestedNodes.default)
  override def annotations: Seq[com.capnproto.schema.Annotation] = struct.getList(2, (l: CapnpList) => (o: Int) => l.getStruct(o, com.capnproto.schema.Annotation), com.capnproto.schema.Node.annotations.default)
  override def file: Option[Unit] = struct.getNone()
  override def __struct: com.capnproto.schema.Node.__Struct = new com.capnproto.schema.Node.__StructMutable(struct)

  override def __enum: com.capnproto.schema.Node.__Enum = new com.capnproto.schema.Node.__EnumMutable(struct)

  override def interface: com.capnproto.schema.Node.Interface = new com.capnproto.schema.Node.InterfaceMutable(struct)

  override def const: com.capnproto.schema.Node.Const = new com.capnproto.schema.Node.ConstMutable(struct)

  override def annotation: com.capnproto.schema.Node.Annotation = new com.capnproto.schema.Node.AnnotationMutable(struct)

}

object Field extends MetaStruct[Field] {
  override type Self = Field.type
  override val structName: String = "Field"
  override def create(struct: CapnpStruct): Field = new FieldMutable(struct)
  def newBuilder(arena: CapnpArenaBuilder): com.capnproto.schema.Field.Builder = {
    val (segment, pointerOffset) = arena.allocate(1)
    val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.schema.Field.Builder.dataSectionSizeWords, com.capnproto.schema.Field.Builder.pointerSectionSizeWords)
    new com.capnproto.schema.Field.Builder(struct)
  }

  object Builder extends MetaStructBuilder[com.capnproto.schema.Field, com.capnproto.schema.Field.Builder] {
    override type Self = com.capnproto.schema.Field.Builder.type
    override val structName: String = "Field"
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
    def setCodeOrder(value: Short): Builder = { struct.setShort(0, value, com.capnproto.schema.Field.codeOrder.default); this }
    def initAnnotations(count: Int): Seq[com.capnproto.schema.Annotation.Builder] = {
      val list = struct.initPointerList(1, count, com.capnproto.schema.Annotation.Builder)
      Range(0, count).map(i => new com.capnproto.schema.Annotation.Builder(list.initStruct(i, com.capnproto.schema.Annotation.Builder)))
    }
    def setAnnotations(buildFn: CapnpArenaBuilder => Seq[com.capnproto.schema.Annotation.Builder]): Builder = { struct.setStructList(1, com.capnproto.schema.Annotation.Builder, buildFn(struct.arena).map(_.struct)); this }
    def setDiscriminantValue(value: Short): Builder = { struct.setShort(1, value, com.capnproto.schema.Field.discriminantValue.default); this }
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
    override val structName: String = "Slot"
    override def create(struct: CapnpStruct): Slot = new SlotMutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.schema.Field.Slot.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.schema.Field.Slot.Builder.dataSectionSizeWords, com.capnproto.schema.Field.Slot.Builder.pointerSectionSizeWords)
      new com.capnproto.schema.Field.Slot.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.schema.Field.Slot, com.capnproto.schema.Field.Slot.Builder] {
      override type Self = com.capnproto.schema.Field.Slot.Builder.type
      override val structName: String = "Slot"
      override val dataSectionSizeWords: Short = 3
      override val pointerSectionSizeWords: Short = 4
      override def create(struct: CapnpStructBuilder): com.capnproto.schema.Field.Slot.Builder = new com.capnproto.schema.Field.Slot.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.schema.Field.Slot.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.schema.Field.SlotMutable(struct) with StructBuilder[com.capnproto.schema.Field.Slot, com.capnproto.schema.Field.Slot.Builder] {
      override type MetaBuilderT = com.capnproto.schema.Field.Slot.Builder.type

      override def meta: Slot.type = Slot
      override def metaBuilder: MetaBuilderT = com.capnproto.schema.Field.Slot.Builder
      def setOffset(value: Int): Builder = { struct.setInt(1, value, com.capnproto.schema.Field.Slot.offset.default); this }
      def set__Type(value: com.capnproto.schema.__Type): Builder = { struct.setNone(); this }
      def setDefaultValue(value: com.capnproto.schema.Value): Builder = { struct.setNone(); this }
      def setHadExplicitDefault(value: Boolean): Builder = { struct.setBoolean(128, value, com.capnproto.schema.Field.Slot.hadExplicitDefault.default); this }
    }

    val offset = new FieldDescriptor[Int, Slot, Slot.type](
      name = "offset",
      meta = Slot,
      default = Option(0),
      getter = _.offset,
      manifest = manifest[Int],
      discriminantValue = None
    )

    val __type = new FieldDescriptor[com.capnproto.schema.__Type, Slot, Slot.type](
      name = "type",
      meta = Slot,
      default = Option(null),
      getter = _.__type,
      manifest = manifest[com.capnproto.schema.__Type],
      discriminantValue = None
    )

    val defaultValue = new FieldDescriptor[com.capnproto.schema.Value, Slot, Slot.type](
      name = "defaultValue",
      meta = Slot,
      default = Option(null),
      getter = _.defaultValue,
      manifest = manifest[com.capnproto.schema.Value],
      discriminantValue = None
    )

    val hadExplicitDefault = new FieldDescriptor[Boolean, Slot, Slot.type](
      name = "hadExplicitDefault",
      meta = Slot,
      default = Option(false),
      getter = _.hadExplicitDefault,
      manifest = manifest[Boolean],
      discriminantValue = None
    )
    override val fields: Seq[FieldDescriptor[_, Slot, Slot.type]] = Seq(offset, __type, defaultValue, hadExplicitDefault)
  }

  trait Slot extends Struct[Slot] {
    override type MetaT = Slot.type
    override type MetaBuilderT = com.capnproto.schema.Field.Slot.Builder.type

    override def meta: Slot.type = Slot
    override def metaBuilder: com.capnproto.schema.Field.Slot.Builder.type = com.capnproto.schema.Field.Slot.Builder

    def struct: CapnpStruct

    def offset: Option[Int]
    def __type: Option[com.capnproto.schema.__Type]
    def defaultValue: Option[com.capnproto.schema.Value]
    def hadExplicitDefault: Option[Boolean]
  }

  class SlotMutable(override val struct: CapnpStruct) extends Slot {
    override def offset: Option[Int] = struct.getInt(1, com.capnproto.schema.Field.Slot.offset.default)
    override def __type: Option[com.capnproto.schema.__Type] = struct.getStruct(2, com.capnproto.schema.__Type, com.capnproto.schema.Field.Slot.__type.default)
    override def defaultValue: Option[com.capnproto.schema.Value] = struct.getStruct(3, com.capnproto.schema.Value, com.capnproto.schema.Field.Slot.defaultValue.default)
    override def hadExplicitDefault: Option[Boolean] = struct.getBoolean(128, com.capnproto.schema.Field.Slot.hadExplicitDefault.default)
  }
  object Group extends MetaStruct[Group] {
    override type Self = Group.type
    override val structName: String = "Group"
    override def create(struct: CapnpStruct): Group = new GroupMutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.schema.Field.Group.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.schema.Field.Group.Builder.dataSectionSizeWords, com.capnproto.schema.Field.Group.Builder.pointerSectionSizeWords)
      new com.capnproto.schema.Field.Group.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.schema.Field.Group, com.capnproto.schema.Field.Group.Builder] {
      override type Self = com.capnproto.schema.Field.Group.Builder.type
      override val structName: String = "Group"
      override val dataSectionSizeWords: Short = 3
      override val pointerSectionSizeWords: Short = 4
      override def create(struct: CapnpStructBuilder): com.capnproto.schema.Field.Group.Builder = new com.capnproto.schema.Field.Group.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.schema.Field.Group.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.schema.Field.GroupMutable(struct) with StructBuilder[com.capnproto.schema.Field.Group, com.capnproto.schema.Field.Group.Builder] {
      override type MetaBuilderT = com.capnproto.schema.Field.Group.Builder.type

      override def meta: Group.type = Group
      override def metaBuilder: MetaBuilderT = com.capnproto.schema.Field.Group.Builder
      def setTypeId(value: Long): Builder = { struct.setLong(2, value, com.capnproto.schema.Field.Group.typeId.default); this }
    }

    val typeId = new FieldDescriptor[Long, Group, Group.type](
      name = "typeId",
      meta = Group,
      default = Option(0L),
      getter = _.typeId,
      manifest = manifest[Long],
      discriminantValue = None
    )
    override val fields: Seq[FieldDescriptor[_, Group, Group.type]] = Seq(typeId)
  }

  trait Group extends Struct[Group] {
    override type MetaT = Group.type
    override type MetaBuilderT = com.capnproto.schema.Field.Group.Builder.type

    override def meta: Group.type = Group
    override def metaBuilder: com.capnproto.schema.Field.Group.Builder.type = com.capnproto.schema.Field.Group.Builder

    def struct: CapnpStruct

    def typeId: Option[Long]
  }

  class GroupMutable(override val struct: CapnpStruct) extends Group {
    override def typeId: Option[Long] = struct.getLong(2, com.capnproto.schema.Field.Group.typeId.default)
  }
  object Ordinal extends MetaStruct[Ordinal] {
    override type Self = Ordinal.type
    override val structName: String = "Ordinal"
    override def create(struct: CapnpStruct): Ordinal = new OrdinalMutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.schema.Field.Ordinal.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.schema.Field.Ordinal.Builder.dataSectionSizeWords, com.capnproto.schema.Field.Ordinal.Builder.pointerSectionSizeWords)
      new com.capnproto.schema.Field.Ordinal.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.schema.Field.Ordinal, com.capnproto.schema.Field.Ordinal.Builder] {
      override type Self = com.capnproto.schema.Field.Ordinal.Builder.type
      override val structName: String = "Ordinal"
      override val dataSectionSizeWords: Short = 3
      override val pointerSectionSizeWords: Short = 4
      override def create(struct: CapnpStructBuilder): com.capnproto.schema.Field.Ordinal.Builder = new com.capnproto.schema.Field.Ordinal.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.schema.Field.Ordinal.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.schema.Field.OrdinalMutable(struct) with StructBuilder[com.capnproto.schema.Field.Ordinal, com.capnproto.schema.Field.Ordinal.Builder] {
      override type MetaBuilderT = com.capnproto.schema.Field.Ordinal.Builder.type

      override def meta: Ordinal.type = Ordinal
      override def metaBuilder: MetaBuilderT = com.capnproto.schema.Field.Ordinal.Builder
      def set__Implicit(value: Unit): Builder = { struct.setNone(); struct.setShort(5, 0); this }
      def setExplicit(value: Short): Builder = { struct.setShort(6, value, com.capnproto.schema.Field.Ordinal.explicit.default); struct.setShort(5, 1); this }
    }

    sealed trait Union extends UnionValue[com.capnproto.schema.Field.Ordinal.Union]
    object Union extends UnionMeta[com.capnproto.schema.Field.Ordinal.Union] {
      case class Unknown(discriminant: Short) extends com.capnproto.schema.Field.Ordinal.Union
      case class __implicit(value: Option[Unit]) extends com.capnproto.schema.Field.Ordinal.Union
      case class explicit(value: Option[Short]) extends com.capnproto.schema.Field.Ordinal.Union
    }

    val __implicit = new FieldDescriptor[Unit, Ordinal, Ordinal.type](
      name = "implicit",
      meta = Ordinal,
      default = Option(Unit),
      getter = _.__implicit,
      manifest = manifest[Unit],
      discriminantValue = Some(0)
    )

    val explicit = new FieldDescriptor[Short, Ordinal, Ordinal.type](
      name = "explicit",
      meta = Ordinal,
      default = Option(0.toShort),
      getter = _.explicit,
      manifest = manifest[Short],
      discriminantValue = Some(1)
    )
    override val fields: Seq[FieldDescriptor[_, Ordinal, Ordinal.type]] = Seq(__implicit, explicit)
  }

  trait Ordinal extends Struct[Ordinal] with HasUnion[com.capnproto.schema.Field.Ordinal.Union] {
    override type MetaT = Ordinal.type
    override type MetaBuilderT = com.capnproto.schema.Field.Ordinal.Builder.type

    override def meta: Ordinal.type = Ordinal
    override def metaBuilder: com.capnproto.schema.Field.Ordinal.Builder.type = com.capnproto.schema.Field.Ordinal.Builder

    def struct: CapnpStruct

    def __implicit: Option[Unit]
    def explicit: Option[Short]
  }

  class OrdinalMutable(override val struct: CapnpStruct) extends Ordinal {
    override def discriminant: Short = (struct.getShort(5).getOrElse(0.toShort): Short)
    override def switch: com.capnproto.schema.Field.Ordinal.Union = discriminant match {
      case 0 => com.capnproto.schema.Field.Ordinal.Union.__implicit(__implicit)
      case 1 => com.capnproto.schema.Field.Ordinal.Union.explicit(explicit)
      case d => com.capnproto.schema.Field.Ordinal.Union.Unknown(d)
    }
    override def union: UnionMeta[com.capnproto.schema.Field.Ordinal.Union] = com.capnproto.schema.Field.Ordinal.Union

    override def __implicit: Option[Unit] = struct.getNone()
    override def explicit: Option[Short] = struct.getShort(6, com.capnproto.schema.Field.Ordinal.explicit.default)
  }
  val name = new FieldDescriptor[String, Field, Field.type](
    name = "name",
    meta = Field,
    default = Option(""),
    getter = _.name,
    manifest = manifest[String],
    discriminantValue = None
  )

  val codeOrder = new FieldDescriptor[Short, Field, Field.type](
    name = "codeOrder",
    meta = Field,
    default = Option(0.toShort),
    getter = _.codeOrder,
    manifest = manifest[Short],
    discriminantValue = None
  )

  val annotations = new FieldDescriptor[Seq[com.capnproto.schema.Annotation], Field, Field.type](
    name = "annotations",
    meta = Field,
    default = Option(null),
    getter = x => Some(x.annotations),
    manifest = manifest[Seq[com.capnproto.schema.Annotation]],
    discriminantValue = None
  )

  val discriminantValue = new FieldDescriptor[Short, Field, Field.type](
    name = "discriminantValue",
    meta = Field,
    default = Option(-1.toShort),
    getter = _.discriminantValue,
    manifest = manifest[Short],
    discriminantValue = None
  )

  val slot = new FieldDescriptor[com.capnproto.schema.Field.Slot, Field, Field.type](
    name = "slot",
    meta = Field,
    default = None,
    getter = x => Some(x.slot),
    manifest = manifest[com.capnproto.schema.Field.Slot],
    discriminantValue = Some(0)
  )

  val group = new FieldDescriptor[com.capnproto.schema.Field.Group, Field, Field.type](
    name = "group",
    meta = Field,
    default = None,
    getter = x => Some(x.group),
    manifest = manifest[com.capnproto.schema.Field.Group],
    discriminantValue = Some(1)
  )

  val ordinal = new FieldDescriptor[com.capnproto.schema.Field.Ordinal, Field, Field.type](
    name = "ordinal",
    meta = Field,
    default = None,
    getter = x => Some(x.ordinal),
    manifest = manifest[com.capnproto.schema.Field.Ordinal],
    discriminantValue = None
  )
  override val fields: Seq[FieldDescriptor[_, Field, Field.type]] = Seq(name, codeOrder, annotations, discriminantValue, slot, group, ordinal)
}

trait Field extends Struct[Field] with HasUnion[com.capnproto.schema.Field.Union] {
  override type MetaT = Field.type
  override type MetaBuilderT = com.capnproto.schema.Field.Builder.type

  override def meta: Field.type = Field
  override def metaBuilder: com.capnproto.schema.Field.Builder.type = com.capnproto.schema.Field.Builder

  def struct: CapnpStruct

  def name: Option[String]
  def codeOrder: Option[Short]
  def annotations: Seq[com.capnproto.schema.Annotation]
  def discriminantValue: Option[Short]
  def slot: com.capnproto.schema.Field.Slot
  def group: com.capnproto.schema.Field.Group
  def ordinal: com.capnproto.schema.Field.Ordinal
}

class FieldMutable(override val struct: CapnpStruct) extends Field {
  override def discriminant: Short = (struct.getShort(4).getOrElse(0.toShort): Short)
  override def switch: com.capnproto.schema.Field.Union = discriminant match {
    case 0 => com.capnproto.schema.Field.Union.slot(slot)
    case 1 => com.capnproto.schema.Field.Union.group(group)
    case d => com.capnproto.schema.Field.Union.Unknown(d)
  }
  override def union: UnionMeta[com.capnproto.schema.Field.Union] = com.capnproto.schema.Field.Union

  override def name: Option[String] = struct.getString(0, com.capnproto.schema.Field.name.default)
  override def codeOrder: Option[Short] = struct.getShort(0, com.capnproto.schema.Field.codeOrder.default)
  override def annotations: Seq[com.capnproto.schema.Annotation] = struct.getList(1, (l: CapnpList) => (o: Int) => l.getStruct(o, com.capnproto.schema.Annotation), com.capnproto.schema.Field.annotations.default)
  override def discriminantValue: Option[Short] = struct.getShort(1, com.capnproto.schema.Field.discriminantValue.default)
  override def slot: com.capnproto.schema.Field.Slot = new com.capnproto.schema.Field.SlotMutable(struct)

  override def group: com.capnproto.schema.Field.Group = new com.capnproto.schema.Field.GroupMutable(struct)

  override def ordinal: com.capnproto.schema.Field.Ordinal = new com.capnproto.schema.Field.OrdinalMutable(struct)

}

object Enumerant extends MetaStruct[Enumerant] {
  override type Self = Enumerant.type
  override val structName: String = "Enumerant"
  override def create(struct: CapnpStruct): Enumerant = new EnumerantMutable(struct)
  def newBuilder(arena: CapnpArenaBuilder): com.capnproto.schema.Enumerant.Builder = {
    val (segment, pointerOffset) = arena.allocate(1)
    val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.schema.Enumerant.Builder.dataSectionSizeWords, com.capnproto.schema.Enumerant.Builder.pointerSectionSizeWords)
    new com.capnproto.schema.Enumerant.Builder(struct)
  }

  object Builder extends MetaStructBuilder[com.capnproto.schema.Enumerant, com.capnproto.schema.Enumerant.Builder] {
    override type Self = com.capnproto.schema.Enumerant.Builder.type
    override val structName: String = "Enumerant"
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
    def setCodeOrder(value: Short): Builder = { struct.setShort(0, value, com.capnproto.schema.Enumerant.codeOrder.default); this }
    def initAnnotations(count: Int): Seq[com.capnproto.schema.Annotation.Builder] = {
      val list = struct.initPointerList(1, count, com.capnproto.schema.Annotation.Builder)
      Range(0, count).map(i => new com.capnproto.schema.Annotation.Builder(list.initStruct(i, com.capnproto.schema.Annotation.Builder)))
    }
    def setAnnotations(buildFn: CapnpArenaBuilder => Seq[com.capnproto.schema.Annotation.Builder]): Builder = { struct.setStructList(1, com.capnproto.schema.Annotation.Builder, buildFn(struct.arena).map(_.struct)); this }
  }

  val name = new FieldDescriptor[String, Enumerant, Enumerant.type](
    name = "name",
    meta = Enumerant,
    default = Option(""),
    getter = _.name,
    manifest = manifest[String],
    discriminantValue = None
  )

  val codeOrder = new FieldDescriptor[Short, Enumerant, Enumerant.type](
    name = "codeOrder",
    meta = Enumerant,
    default = Option(0.toShort),
    getter = _.codeOrder,
    manifest = manifest[Short],
    discriminantValue = None
  )

  val annotations = new FieldDescriptor[Seq[com.capnproto.schema.Annotation], Enumerant, Enumerant.type](
    name = "annotations",
    meta = Enumerant,
    default = Option(null),
    getter = x => Some(x.annotations),
    manifest = manifest[Seq[com.capnproto.schema.Annotation]],
    discriminantValue = None
  )
  override val fields: Seq[FieldDescriptor[_, Enumerant, Enumerant.type]] = Seq(name, codeOrder, annotations)
}

trait Enumerant extends Struct[Enumerant] {
  override type MetaT = Enumerant.type
  override type MetaBuilderT = com.capnproto.schema.Enumerant.Builder.type

  override def meta: Enumerant.type = Enumerant
  override def metaBuilder: com.capnproto.schema.Enumerant.Builder.type = com.capnproto.schema.Enumerant.Builder

  def struct: CapnpStruct

  def name: Option[String]
  def codeOrder: Option[Short]
  def annotations: Seq[com.capnproto.schema.Annotation]
}

class EnumerantMutable(override val struct: CapnpStruct) extends Enumerant {
  override def name: Option[String] = struct.getString(0, com.capnproto.schema.Enumerant.name.default)
  override def codeOrder: Option[Short] = struct.getShort(0, com.capnproto.schema.Enumerant.codeOrder.default)
  override def annotations: Seq[com.capnproto.schema.Annotation] = struct.getList(1, (l: CapnpList) => (o: Int) => l.getStruct(o, com.capnproto.schema.Annotation), com.capnproto.schema.Enumerant.annotations.default)
}

object Method extends MetaStruct[Method] {
  override type Self = Method.type
  override val structName: String = "Method"
  override def create(struct: CapnpStruct): Method = new MethodMutable(struct)
  def newBuilder(arena: CapnpArenaBuilder): com.capnproto.schema.Method.Builder = {
    val (segment, pointerOffset) = arena.allocate(1)
    val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.schema.Method.Builder.dataSectionSizeWords, com.capnproto.schema.Method.Builder.pointerSectionSizeWords)
    new com.capnproto.schema.Method.Builder(struct)
  }

  object Builder extends MetaStructBuilder[com.capnproto.schema.Method, com.capnproto.schema.Method.Builder] {
    override type Self = com.capnproto.schema.Method.Builder.type
    override val structName: String = "Method"
    override val dataSectionSizeWords: Short = 3
    override val pointerSectionSizeWords: Short = 2
    override def create(struct: CapnpStructBuilder): com.capnproto.schema.Method.Builder = new com.capnproto.schema.Method.Builder(struct)
    override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.schema.Method.fields
  }
  class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.schema.MethodMutable(struct) with StructBuilder[com.capnproto.schema.Method, com.capnproto.schema.Method.Builder] {
    override type MetaBuilderT = com.capnproto.schema.Method.Builder.type

    override def meta: Method.type = Method
    override def metaBuilder: MetaBuilderT = com.capnproto.schema.Method.Builder
    def setName(value: String): Builder = { struct.setString(0, value); this }
    def setCodeOrder(value: Short): Builder = { struct.setShort(0, value, com.capnproto.schema.Method.codeOrder.default); this }
    def setParamStructType(value: Long): Builder = { struct.setLong(1, value, com.capnproto.schema.Method.paramStructType.default); this }
    def setResultStructType(value: Long): Builder = { struct.setLong(2, value, com.capnproto.schema.Method.resultStructType.default); this }
    def initAnnotations(count: Int): Seq[com.capnproto.schema.Annotation.Builder] = {
      val list = struct.initPointerList(1, count, com.capnproto.schema.Annotation.Builder)
      Range(0, count).map(i => new com.capnproto.schema.Annotation.Builder(list.initStruct(i, com.capnproto.schema.Annotation.Builder)))
    }
    def setAnnotations(buildFn: CapnpArenaBuilder => Seq[com.capnproto.schema.Annotation.Builder]): Builder = { struct.setStructList(1, com.capnproto.schema.Annotation.Builder, buildFn(struct.arena).map(_.struct)); this }
  }

  val name = new FieldDescriptor[String, Method, Method.type](
    name = "name",
    meta = Method,
    default = Option(""),
    getter = _.name,
    manifest = manifest[String],
    discriminantValue = None
  )

  val codeOrder = new FieldDescriptor[Short, Method, Method.type](
    name = "codeOrder",
    meta = Method,
    default = Option(0.toShort),
    getter = _.codeOrder,
    manifest = manifest[Short],
    discriminantValue = None
  )

  val paramStructType = new FieldDescriptor[Long, Method, Method.type](
    name = "paramStructType",
    meta = Method,
    default = Option(0L),
    getter = _.paramStructType,
    manifest = manifest[Long],
    discriminantValue = None
  )

  val resultStructType = new FieldDescriptor[Long, Method, Method.type](
    name = "resultStructType",
    meta = Method,
    default = Option(0L),
    getter = _.resultStructType,
    manifest = manifest[Long],
    discriminantValue = None
  )

  val annotations = new FieldDescriptor[Seq[com.capnproto.schema.Annotation], Method, Method.type](
    name = "annotations",
    meta = Method,
    default = Option(null),
    getter = x => Some(x.annotations),
    manifest = manifest[Seq[com.capnproto.schema.Annotation]],
    discriminantValue = None
  )
  override val fields: Seq[FieldDescriptor[_, Method, Method.type]] = Seq(name, codeOrder, paramStructType, resultStructType, annotations)
}

trait Method extends Struct[Method] {
  override type MetaT = Method.type
  override type MetaBuilderT = com.capnproto.schema.Method.Builder.type

  override def meta: Method.type = Method
  override def metaBuilder: com.capnproto.schema.Method.Builder.type = com.capnproto.schema.Method.Builder

  def struct: CapnpStruct

  def name: Option[String]
  def codeOrder: Option[Short]
  def paramStructType: Option[Long]
  def resultStructType: Option[Long]
  def annotations: Seq[com.capnproto.schema.Annotation]
}

class MethodMutable(override val struct: CapnpStruct) extends Method {
  override def name: Option[String] = struct.getString(0, com.capnproto.schema.Method.name.default)
  override def codeOrder: Option[Short] = struct.getShort(0, com.capnproto.schema.Method.codeOrder.default)
  override def paramStructType: Option[Long] = struct.getLong(1, com.capnproto.schema.Method.paramStructType.default)
  override def resultStructType: Option[Long] = struct.getLong(2, com.capnproto.schema.Method.resultStructType.default)
  override def annotations: Seq[com.capnproto.schema.Annotation] = struct.getList(1, (l: CapnpList) => (o: Int) => l.getStruct(o, com.capnproto.schema.Annotation), com.capnproto.schema.Method.annotations.default)
}

object __Type extends MetaStruct[__Type] {
  override type Self = __Type.type
  override val structName: String = "Type"
  override def create(struct: CapnpStruct): __Type = new __TypeMutable(struct)
  def newBuilder(arena: CapnpArenaBuilder): com.capnproto.schema.__Type.Builder = {
    val (segment, pointerOffset) = arena.allocate(1)
    val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.schema.__Type.Builder.dataSectionSizeWords, com.capnproto.schema.__Type.Builder.pointerSectionSizeWords)
    new com.capnproto.schema.__Type.Builder(struct)
  }

  object Builder extends MetaStructBuilder[com.capnproto.schema.__Type, com.capnproto.schema.__Type.Builder] {
    override type Self = com.capnproto.schema.__Type.Builder.type
    override val structName: String = "Type"
    override val dataSectionSizeWords: Short = 2
    override val pointerSectionSizeWords: Short = 1
    override def create(struct: CapnpStructBuilder): com.capnproto.schema.__Type.Builder = new com.capnproto.schema.__Type.Builder(struct)
    override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.schema.__Type.fields
  }
  class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.schema.__TypeMutable(struct) with StructBuilder[com.capnproto.schema.__Type, com.capnproto.schema.__Type.Builder] {
    override type MetaBuilderT = com.capnproto.schema.__Type.Builder.type

    override def meta: __Type.type = __Type
    override def metaBuilder: MetaBuilderT = com.capnproto.schema.__Type.Builder
    def setVoid(value: Unit): Builder = { struct.setNone(); struct.setShort(0, 0); this }
    def setBool(value: Unit): Builder = { struct.setNone(); struct.setShort(0, 1); this }
    def setInt8(value: Unit): Builder = { struct.setNone(); struct.setShort(0, 2); this }
    def setInt16(value: Unit): Builder = { struct.setNone(); struct.setShort(0, 3); this }
    def setInt32(value: Unit): Builder = { struct.setNone(); struct.setShort(0, 4); this }
    def setInt64(value: Unit): Builder = { struct.setNone(); struct.setShort(0, 5); this }
    def setUint8(value: Unit): Builder = { struct.setNone(); struct.setShort(0, 6); this }
    def setUint16(value: Unit): Builder = { struct.setNone(); struct.setShort(0, 7); this }
    def setUint32(value: Unit): Builder = { struct.setNone(); struct.setShort(0, 8); this }
    def setUint64(value: Unit): Builder = { struct.setNone(); struct.setShort(0, 9); this }
    def setFloat32(value: Unit): Builder = { struct.setNone(); struct.setShort(0, 10); this }
    def setFloat64(value: Unit): Builder = { struct.setNone(); struct.setShort(0, 11); this }
    def setText(value: Unit): Builder = { struct.setNone(); struct.setShort(0, 12); this }
    def setData(value: Unit): Builder = { struct.setNone(); struct.setShort(0, 13); this }
    override def list: com.capnproto.schema.__Type.List.Builder = new com.capnproto.schema.__Type.List.Builder(struct)
    override def __enum: com.capnproto.schema.__Type.__Enum.Builder = new com.capnproto.schema.__Type.__Enum.Builder(struct)
    override def __struct: com.capnproto.schema.__Type.__Struct.Builder = new com.capnproto.schema.__Type.__Struct.Builder(struct)
    override def interface: com.capnproto.schema.__Type.Interface.Builder = new com.capnproto.schema.__Type.Interface.Builder(struct)
    def set__Object(value: Unit): Builder = { struct.setNone(); struct.setShort(0, 18); this }
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
    override val structName: String = "List"
    override def create(struct: CapnpStruct): List = new ListMutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.schema.__Type.List.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.schema.__Type.List.Builder.dataSectionSizeWords, com.capnproto.schema.__Type.List.Builder.pointerSectionSizeWords)
      new com.capnproto.schema.__Type.List.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.schema.__Type.List, com.capnproto.schema.__Type.List.Builder] {
      override type Self = com.capnproto.schema.__Type.List.Builder.type
      override val structName: String = "List"
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
      default = Option(null),
      getter = _.elementType,
      manifest = manifest[com.capnproto.schema.__Type],
      discriminantValue = None
    )
    override val fields: Seq[FieldDescriptor[_, List, List.type]] = Seq(elementType)
  }

  trait List extends Struct[List] {
    override type MetaT = List.type
    override type MetaBuilderT = com.capnproto.schema.__Type.List.Builder.type

    override def meta: List.type = List
    override def metaBuilder: com.capnproto.schema.__Type.List.Builder.type = com.capnproto.schema.__Type.List.Builder

    def struct: CapnpStruct

    def elementType: Option[com.capnproto.schema.__Type]
  }

  class ListMutable(override val struct: CapnpStruct) extends List {
    override def elementType: Option[com.capnproto.schema.__Type] = struct.getStruct(0, com.capnproto.schema.__Type, com.capnproto.schema.__Type.List.elementType.default)
  }
  object __Enum extends MetaStruct[__Enum] {
    override type Self = __Enum.type
    override val structName: String = "Enum"
    override def create(struct: CapnpStruct): __Enum = new __EnumMutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.schema.__Type.__Enum.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.schema.__Type.__Enum.Builder.dataSectionSizeWords, com.capnproto.schema.__Type.__Enum.Builder.pointerSectionSizeWords)
      new com.capnproto.schema.__Type.__Enum.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.schema.__Type.__Enum, com.capnproto.schema.__Type.__Enum.Builder] {
      override type Self = com.capnproto.schema.__Type.__Enum.Builder.type
      override val structName: String = "Enum"
      override val dataSectionSizeWords: Short = 2
      override val pointerSectionSizeWords: Short = 1
      override def create(struct: CapnpStructBuilder): com.capnproto.schema.__Type.__Enum.Builder = new com.capnproto.schema.__Type.__Enum.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.schema.__Type.__Enum.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.schema.__Type.__EnumMutable(struct) with StructBuilder[com.capnproto.schema.__Type.__Enum, com.capnproto.schema.__Type.__Enum.Builder] {
      override type MetaBuilderT = com.capnproto.schema.__Type.__Enum.Builder.type

      override def meta: __Enum.type = __Enum
      override def metaBuilder: MetaBuilderT = com.capnproto.schema.__Type.__Enum.Builder
      def setTypeId(value: Long): Builder = { struct.setLong(1, value, com.capnproto.schema.__Type.__Enum.typeId.default); this }
    }

    val typeId = new FieldDescriptor[Long, __Enum, __Enum.type](
      name = "typeId",
      meta = __Enum,
      default = Option(0L),
      getter = _.typeId,
      manifest = manifest[Long],
      discriminantValue = None
    )
    override val fields: Seq[FieldDescriptor[_, __Enum, __Enum.type]] = Seq(typeId)
  }

  trait __Enum extends Struct[__Enum] {
    override type MetaT = __Enum.type
    override type MetaBuilderT = com.capnproto.schema.__Type.__Enum.Builder.type

    override def meta: __Enum.type = __Enum
    override def metaBuilder: com.capnproto.schema.__Type.__Enum.Builder.type = com.capnproto.schema.__Type.__Enum.Builder

    def struct: CapnpStruct

    def typeId: Option[Long]
  }

  class __EnumMutable(override val struct: CapnpStruct) extends __Enum {
    override def typeId: Option[Long] = struct.getLong(1, com.capnproto.schema.__Type.__Enum.typeId.default)
  }
  object __Struct extends MetaStruct[__Struct] {
    override type Self = __Struct.type
    override val structName: String = "Struct"
    override def create(struct: CapnpStruct): __Struct = new __StructMutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.schema.__Type.__Struct.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.schema.__Type.__Struct.Builder.dataSectionSizeWords, com.capnproto.schema.__Type.__Struct.Builder.pointerSectionSizeWords)
      new com.capnproto.schema.__Type.__Struct.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.schema.__Type.__Struct, com.capnproto.schema.__Type.__Struct.Builder] {
      override type Self = com.capnproto.schema.__Type.__Struct.Builder.type
      override val structName: String = "Struct"
      override val dataSectionSizeWords: Short = 2
      override val pointerSectionSizeWords: Short = 1
      override def create(struct: CapnpStructBuilder): com.capnproto.schema.__Type.__Struct.Builder = new com.capnproto.schema.__Type.__Struct.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.schema.__Type.__Struct.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.schema.__Type.__StructMutable(struct) with StructBuilder[com.capnproto.schema.__Type.__Struct, com.capnproto.schema.__Type.__Struct.Builder] {
      override type MetaBuilderT = com.capnproto.schema.__Type.__Struct.Builder.type

      override def meta: __Struct.type = __Struct
      override def metaBuilder: MetaBuilderT = com.capnproto.schema.__Type.__Struct.Builder
      def setTypeId(value: Long): Builder = { struct.setLong(1, value, com.capnproto.schema.__Type.__Struct.typeId.default); this }
    }

    val typeId = new FieldDescriptor[Long, __Struct, __Struct.type](
      name = "typeId",
      meta = __Struct,
      default = Option(0L),
      getter = _.typeId,
      manifest = manifest[Long],
      discriminantValue = None
    )
    override val fields: Seq[FieldDescriptor[_, __Struct, __Struct.type]] = Seq(typeId)
  }

  trait __Struct extends Struct[__Struct] {
    override type MetaT = __Struct.type
    override type MetaBuilderT = com.capnproto.schema.__Type.__Struct.Builder.type

    override def meta: __Struct.type = __Struct
    override def metaBuilder: com.capnproto.schema.__Type.__Struct.Builder.type = com.capnproto.schema.__Type.__Struct.Builder

    def struct: CapnpStruct

    def typeId: Option[Long]
  }

  class __StructMutable(override val struct: CapnpStruct) extends __Struct {
    override def typeId: Option[Long] = struct.getLong(1, com.capnproto.schema.__Type.__Struct.typeId.default)
  }
  object Interface extends MetaStruct[Interface] {
    override type Self = Interface.type
    override val structName: String = "Interface"
    override def create(struct: CapnpStruct): Interface = new InterfaceMutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.schema.__Type.Interface.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.schema.__Type.Interface.Builder.dataSectionSizeWords, com.capnproto.schema.__Type.Interface.Builder.pointerSectionSizeWords)
      new com.capnproto.schema.__Type.Interface.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.schema.__Type.Interface, com.capnproto.schema.__Type.Interface.Builder] {
      override type Self = com.capnproto.schema.__Type.Interface.Builder.type
      override val structName: String = "Interface"
      override val dataSectionSizeWords: Short = 2
      override val pointerSectionSizeWords: Short = 1
      override def create(struct: CapnpStructBuilder): com.capnproto.schema.__Type.Interface.Builder = new com.capnproto.schema.__Type.Interface.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.schema.__Type.Interface.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.schema.__Type.InterfaceMutable(struct) with StructBuilder[com.capnproto.schema.__Type.Interface, com.capnproto.schema.__Type.Interface.Builder] {
      override type MetaBuilderT = com.capnproto.schema.__Type.Interface.Builder.type

      override def meta: Interface.type = Interface
      override def metaBuilder: MetaBuilderT = com.capnproto.schema.__Type.Interface.Builder
      def setTypeId(value: Long): Builder = { struct.setLong(1, value, com.capnproto.schema.__Type.Interface.typeId.default); this }
    }

    val typeId = new FieldDescriptor[Long, Interface, Interface.type](
      name = "typeId",
      meta = Interface,
      default = Option(0L),
      getter = _.typeId,
      manifest = manifest[Long],
      discriminantValue = None
    )
    override val fields: Seq[FieldDescriptor[_, Interface, Interface.type]] = Seq(typeId)
  }

  trait Interface extends Struct[Interface] {
    override type MetaT = Interface.type
    override type MetaBuilderT = com.capnproto.schema.__Type.Interface.Builder.type

    override def meta: Interface.type = Interface
    override def metaBuilder: com.capnproto.schema.__Type.Interface.Builder.type = com.capnproto.schema.__Type.Interface.Builder

    def struct: CapnpStruct

    def typeId: Option[Long]
  }

  class InterfaceMutable(override val struct: CapnpStruct) extends Interface {
    override def typeId: Option[Long] = struct.getLong(1, com.capnproto.schema.__Type.Interface.typeId.default)
  }
  val void = new FieldDescriptor[Unit, __Type, __Type.type](
    name = "void",
    meta = __Type,
    default = Option(Unit),
    getter = _.void,
    manifest = manifest[Unit],
    discriminantValue = Some(0)
  )

  val bool = new FieldDescriptor[Unit, __Type, __Type.type](
    name = "bool",
    meta = __Type,
    default = Option(Unit),
    getter = _.bool,
    manifest = manifest[Unit],
    discriminantValue = Some(1)
  )

  val int8 = new FieldDescriptor[Unit, __Type, __Type.type](
    name = "int8",
    meta = __Type,
    default = Option(Unit),
    getter = _.int8,
    manifest = manifest[Unit],
    discriminantValue = Some(2)
  )

  val int16 = new FieldDescriptor[Unit, __Type, __Type.type](
    name = "int16",
    meta = __Type,
    default = Option(Unit),
    getter = _.int16,
    manifest = manifest[Unit],
    discriminantValue = Some(3)
  )

  val int32 = new FieldDescriptor[Unit, __Type, __Type.type](
    name = "int32",
    meta = __Type,
    default = Option(Unit),
    getter = _.int32,
    manifest = manifest[Unit],
    discriminantValue = Some(4)
  )

  val int64 = new FieldDescriptor[Unit, __Type, __Type.type](
    name = "int64",
    meta = __Type,
    default = Option(Unit),
    getter = _.int64,
    manifest = manifest[Unit],
    discriminantValue = Some(5)
  )

  val uint8 = new FieldDescriptor[Unit, __Type, __Type.type](
    name = "uint8",
    meta = __Type,
    default = Option(Unit),
    getter = _.uint8,
    manifest = manifest[Unit],
    discriminantValue = Some(6)
  )

  val uint16 = new FieldDescriptor[Unit, __Type, __Type.type](
    name = "uint16",
    meta = __Type,
    default = Option(Unit),
    getter = _.uint16,
    manifest = manifest[Unit],
    discriminantValue = Some(7)
  )

  val uint32 = new FieldDescriptor[Unit, __Type, __Type.type](
    name = "uint32",
    meta = __Type,
    default = Option(Unit),
    getter = _.uint32,
    manifest = manifest[Unit],
    discriminantValue = Some(8)
  )

  val uint64 = new FieldDescriptor[Unit, __Type, __Type.type](
    name = "uint64",
    meta = __Type,
    default = Option(Unit),
    getter = _.uint64,
    manifest = manifest[Unit],
    discriminantValue = Some(9)
  )

  val float32 = new FieldDescriptor[Unit, __Type, __Type.type](
    name = "float32",
    meta = __Type,
    default = Option(Unit),
    getter = _.float32,
    manifest = manifest[Unit],
    discriminantValue = Some(10)
  )

  val float64 = new FieldDescriptor[Unit, __Type, __Type.type](
    name = "float64",
    meta = __Type,
    default = Option(Unit),
    getter = _.float64,
    manifest = manifest[Unit],
    discriminantValue = Some(11)
  )

  val text = new FieldDescriptor[Unit, __Type, __Type.type](
    name = "text",
    meta = __Type,
    default = Option(Unit),
    getter = _.text,
    manifest = manifest[Unit],
    discriminantValue = Some(12)
  )

  val data = new FieldDescriptor[Unit, __Type, __Type.type](
    name = "data",
    meta = __Type,
    default = Option(Unit),
    getter = _.data,
    manifest = manifest[Unit],
    discriminantValue = Some(13)
  )

  val list = new FieldDescriptor[com.capnproto.schema.__Type.List, __Type, __Type.type](
    name = "list",
    meta = __Type,
    default = None,
    getter = x => Some(x.list),
    manifest = manifest[com.capnproto.schema.__Type.List],
    discriminantValue = Some(14)
  )

  val __enum = new FieldDescriptor[com.capnproto.schema.__Type.__Enum, __Type, __Type.type](
    name = "enum",
    meta = __Type,
    default = None,
    getter = x => Some(x.__enum),
    manifest = manifest[com.capnproto.schema.__Type.__Enum],
    discriminantValue = Some(15)
  )

  val __struct = new FieldDescriptor[com.capnproto.schema.__Type.__Struct, __Type, __Type.type](
    name = "struct",
    meta = __Type,
    default = None,
    getter = x => Some(x.__struct),
    manifest = manifest[com.capnproto.schema.__Type.__Struct],
    discriminantValue = Some(16)
  )

  val interface = new FieldDescriptor[com.capnproto.schema.__Type.Interface, __Type, __Type.type](
    name = "interface",
    meta = __Type,
    default = None,
    getter = x => Some(x.interface),
    manifest = manifest[com.capnproto.schema.__Type.Interface],
    discriminantValue = Some(17)
  )

  val __object = new FieldDescriptor[Unit, __Type, __Type.type](
    name = "object",
    meta = __Type,
    default = Option(Unit),
    getter = _.__object,
    manifest = manifest[Unit],
    discriminantValue = Some(18)
  )
  override val fields: Seq[FieldDescriptor[_, __Type, __Type.type]] = Seq(void, bool, int8, int16, int32, int64, uint8, uint16, uint32, uint64, float32, float64, text, data, list, __enum, __struct, interface, __object)
}

trait __Type extends Struct[__Type] with HasUnion[com.capnproto.schema.__Type.Union] {
  override type MetaT = __Type.type
  override type MetaBuilderT = com.capnproto.schema.__Type.Builder.type

  override def meta: __Type.type = __Type
  override def metaBuilder: com.capnproto.schema.__Type.Builder.type = com.capnproto.schema.__Type.Builder

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

class __TypeMutable(override val struct: CapnpStruct) extends __Type {
  override def discriminant: Short = (struct.getShort(0).getOrElse(0.toShort): Short)
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
  override val structName: String = "Value"
  override def create(struct: CapnpStruct): Value = new ValueMutable(struct)
  def newBuilder(arena: CapnpArenaBuilder): com.capnproto.schema.Value.Builder = {
    val (segment, pointerOffset) = arena.allocate(1)
    val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.schema.Value.Builder.dataSectionSizeWords, com.capnproto.schema.Value.Builder.pointerSectionSizeWords)
    new com.capnproto.schema.Value.Builder(struct)
  }

  object Builder extends MetaStructBuilder[com.capnproto.schema.Value, com.capnproto.schema.Value.Builder] {
    override type Self = com.capnproto.schema.Value.Builder.type
    override val structName: String = "Value"
    override val dataSectionSizeWords: Short = 2
    override val pointerSectionSizeWords: Short = 1
    override def create(struct: CapnpStructBuilder): com.capnproto.schema.Value.Builder = new com.capnproto.schema.Value.Builder(struct)
    override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.schema.Value.fields
  }
  class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.schema.ValueMutable(struct) with StructBuilder[com.capnproto.schema.Value, com.capnproto.schema.Value.Builder] {
    override type MetaBuilderT = com.capnproto.schema.Value.Builder.type

    override def meta: Value.type = Value
    override def metaBuilder: MetaBuilderT = com.capnproto.schema.Value.Builder
    def setVoid(value: Unit): Builder = { struct.setNone(); struct.setShort(0, 0); this }
    def setBool(value: Boolean): Builder = { struct.setBoolean(16, value, com.capnproto.schema.Value.bool.default); struct.setShort(0, 1); this }
    def setInt8(value: Byte): Builder = { struct.setByte(2, value, com.capnproto.schema.Value.int8.default); struct.setShort(0, 2); this }
    def setInt16(value: Short): Builder = { struct.setShort(1, value, com.capnproto.schema.Value.int16.default); struct.setShort(0, 3); this }
    def setInt32(value: Int): Builder = { struct.setInt(1, value, com.capnproto.schema.Value.int32.default); struct.setShort(0, 4); this }
    def setInt64(value: Long): Builder = { struct.setLong(1, value, com.capnproto.schema.Value.int64.default); struct.setShort(0, 5); this }
    def setUint8(value: Byte): Builder = { struct.setByte(2, value, com.capnproto.schema.Value.uint8.default); struct.setShort(0, 6); this }
    def setUint16(value: Short): Builder = { struct.setShort(1, value, com.capnproto.schema.Value.uint16.default); struct.setShort(0, 7); this }
    def setUint32(value: Int): Builder = { struct.setInt(1, value, com.capnproto.schema.Value.uint32.default); struct.setShort(0, 8); this }
    def setUint64(value: Long): Builder = { struct.setLong(1, value, com.capnproto.schema.Value.uint64.default); struct.setShort(0, 9); this }
    def setFloat32(value: Float): Builder = { struct.setFloat(1, value, com.capnproto.schema.Value.float32.default); struct.setShort(0, 10); this }
    def setFloat64(value: Double): Builder = { struct.setDouble(1, value, com.capnproto.schema.Value.float64.default); struct.setShort(0, 11); this }
    def setText(value: String): Builder = { struct.setString(0, value); struct.setShort(0, 12); this }
    def setData(value: Array[Byte]): Builder = { struct.setData(0, value); struct.setShort(0, 13); this }
    def setList(value: Pointer[_]): Builder = { struct.setNone(); struct.setShort(0, 14); this }
    def set__Enum(value: Short): Builder = { struct.setShort(1, value, com.capnproto.schema.Value.__enum.default); struct.setShort(0, 15); this }
    def set__Struct(value: Pointer[_]): Builder = { struct.setNone(); struct.setShort(0, 16); this }
    def setInterface(value: Unit): Builder = { struct.setNone(); struct.setShort(0, 17); this }
    def set__Object(value: Pointer[_]): Builder = { struct.setNone(); struct.setShort(0, 18); this }
  }

  sealed trait Union extends UnionValue[com.capnproto.schema.Value.Union]
  object Union extends UnionMeta[com.capnproto.schema.Value.Union] {
    case class Unknown(discriminant: Short) extends com.capnproto.schema.Value.Union
    case class void(value: Option[Unit]) extends com.capnproto.schema.Value.Union
    case class bool(value: Option[Boolean]) extends com.capnproto.schema.Value.Union
    case class int8(value: Option[Byte]) extends com.capnproto.schema.Value.Union
    case class int16(value: Option[Short]) extends com.capnproto.schema.Value.Union
    case class int32(value: Option[Int]) extends com.capnproto.schema.Value.Union
    case class int64(value: Option[Long]) extends com.capnproto.schema.Value.Union
    case class uint8(value: Option[Byte]) extends com.capnproto.schema.Value.Union
    case class uint16(value: Option[Short]) extends com.capnproto.schema.Value.Union
    case class uint32(value: Option[Int]) extends com.capnproto.schema.Value.Union
    case class uint64(value: Option[Long]) extends com.capnproto.schema.Value.Union
    case class float32(value: Option[Float]) extends com.capnproto.schema.Value.Union
    case class float64(value: Option[Double]) extends com.capnproto.schema.Value.Union
    case class text(value: Option[String]) extends com.capnproto.schema.Value.Union
    case class data(value: Option[Array[Byte]]) extends com.capnproto.schema.Value.Union
    case class list(value: Option[Pointer[_]]) extends com.capnproto.schema.Value.Union
    case class __enum(value: Option[Short]) extends com.capnproto.schema.Value.Union
    case class __struct(value: Option[Pointer[_]]) extends com.capnproto.schema.Value.Union
    case class interface(value: Option[Unit]) extends com.capnproto.schema.Value.Union
    case class __object(value: Option[Pointer[_]]) extends com.capnproto.schema.Value.Union
  }

  val void = new FieldDescriptor[Unit, Value, Value.type](
    name = "void",
    meta = Value,
    default = Option(Unit),
    getter = _.void,
    manifest = manifest[Unit],
    discriminantValue = Some(0)
  )

  val bool = new FieldDescriptor[Boolean, Value, Value.type](
    name = "bool",
    meta = Value,
    default = Option(false),
    getter = _.bool,
    manifest = manifest[Boolean],
    discriminantValue = Some(1)
  )

  val int8 = new FieldDescriptor[Byte, Value, Value.type](
    name = "int8",
    meta = Value,
    default = Option(0.toByte),
    getter = _.int8,
    manifest = manifest[Byte],
    discriminantValue = Some(2)
  )

  val int16 = new FieldDescriptor[Short, Value, Value.type](
    name = "int16",
    meta = Value,
    default = Option(0.toShort),
    getter = _.int16,
    manifest = manifest[Short],
    discriminantValue = Some(3)
  )

  val int32 = new FieldDescriptor[Int, Value, Value.type](
    name = "int32",
    meta = Value,
    default = Option(0),
    getter = _.int32,
    manifest = manifest[Int],
    discriminantValue = Some(4)
  )

  val int64 = new FieldDescriptor[Long, Value, Value.type](
    name = "int64",
    meta = Value,
    default = Option(0L),
    getter = _.int64,
    manifest = manifest[Long],
    discriminantValue = Some(5)
  )

  val uint8 = new FieldDescriptor[Byte, Value, Value.type](
    name = "uint8",
    meta = Value,
    default = Option(0.toByte),
    getter = _.uint8,
    manifest = manifest[Byte],
    discriminantValue = Some(6)
  )

  val uint16 = new FieldDescriptor[Short, Value, Value.type](
    name = "uint16",
    meta = Value,
    default = Option(0.toShort),
    getter = _.uint16,
    manifest = manifest[Short],
    discriminantValue = Some(7)
  )

  val uint32 = new FieldDescriptor[Int, Value, Value.type](
    name = "uint32",
    meta = Value,
    default = Option(0),
    getter = _.uint32,
    manifest = manifest[Int],
    discriminantValue = Some(8)
  )

  val uint64 = new FieldDescriptor[Long, Value, Value.type](
    name = "uint64",
    meta = Value,
    default = Option(0L),
    getter = _.uint64,
    manifest = manifest[Long],
    discriminantValue = Some(9)
  )

  val float32 = new FieldDescriptor[Float, Value, Value.type](
    name = "float32",
    meta = Value,
    default = Option(0.0.toFloat),
    getter = _.float32,
    manifest = manifest[Float],
    discriminantValue = Some(10)
  )

  val float64 = new FieldDescriptor[Double, Value, Value.type](
    name = "float64",
    meta = Value,
    default = Option(0.0),
    getter = _.float64,
    manifest = manifest[Double],
    discriminantValue = Some(11)
  )

  val text = new FieldDescriptor[String, Value, Value.type](
    name = "text",
    meta = Value,
    default = Option(""),
    getter = _.text,
    manifest = manifest[String],
    discriminantValue = Some(12)
  )

  val data = new FieldDescriptor[Array[Byte], Value, Value.type](
    name = "data",
    meta = Value,
    default = Option(Array[Byte]()),
    getter = _.data,
    manifest = manifest[Array[Byte]],
    discriminantValue = Some(13)
  )

  val list = new FieldDescriptor[Pointer[_], Value, Value.type](
    name = "list",
    meta = Value,
    default = Option(null),
    getter = _.list,
    manifest = manifest[Pointer[_]],
    discriminantValue = Some(14)
  )

  val __enum = new FieldDescriptor[Short, Value, Value.type](
    name = "enum",
    meta = Value,
    default = Option(0.toShort),
    getter = _.__enum,
    manifest = manifest[Short],
    discriminantValue = Some(15)
  )

  val __struct = new FieldDescriptor[Pointer[_], Value, Value.type](
    name = "struct",
    meta = Value,
    default = Option(null),
    getter = _.__struct,
    manifest = manifest[Pointer[_]],
    discriminantValue = Some(16)
  )

  val interface = new FieldDescriptor[Unit, Value, Value.type](
    name = "interface",
    meta = Value,
    default = Option(Unit),
    getter = _.interface,
    manifest = manifest[Unit],
    discriminantValue = Some(17)
  )

  val __object = new FieldDescriptor[Pointer[_], Value, Value.type](
    name = "object",
    meta = Value,
    default = Option(null),
    getter = _.__object,
    manifest = manifest[Pointer[_]],
    discriminantValue = Some(18)
  )
  override val fields: Seq[FieldDescriptor[_, Value, Value.type]] = Seq(void, bool, int8, int16, int32, int64, uint8, uint16, uint32, uint64, float32, float64, text, data, list, __enum, __struct, interface, __object)
}

trait Value extends Struct[Value] with HasUnion[com.capnproto.schema.Value.Union] {
  override type MetaT = Value.type
  override type MetaBuilderT = com.capnproto.schema.Value.Builder.type

  override def meta: Value.type = Value
  override def metaBuilder: com.capnproto.schema.Value.Builder.type = com.capnproto.schema.Value.Builder

  def struct: CapnpStruct

  def void: Option[Unit]
  def bool: Option[Boolean]
  def int8: Option[Byte]
  def int16: Option[Short]
  def int32: Option[Int]
  def int64: Option[Long]
  def uint8: Option[Byte]
  def uint16: Option[Short]
  def uint32: Option[Int]
  def uint64: Option[Long]
  def float32: Option[Float]
  def float64: Option[Double]
  def text: Option[String]
  def data: Option[Array[Byte]]
  def list: Option[Pointer[_]]
  def __enum: Option[Short]
  def __struct: Option[Pointer[_]]
  def interface: Option[Unit]
  def __object: Option[Pointer[_]]
}

class ValueMutable(override val struct: CapnpStruct) extends Value {
  override def discriminant: Short = (struct.getShort(0).getOrElse(0.toShort): Short)
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
  override def bool: Option[Boolean] = struct.getBoolean(16, com.capnproto.schema.Value.bool.default)
  override def int8: Option[Byte] = struct.getByte(2, com.capnproto.schema.Value.int8.default)
  override def int16: Option[Short] = struct.getShort(1, com.capnproto.schema.Value.int16.default)
  override def int32: Option[Int] = struct.getInt(1, com.capnproto.schema.Value.int32.default)
  override def int64: Option[Long] = struct.getLong(1, com.capnproto.schema.Value.int64.default)
  override def uint8: Option[Byte] = struct.getByte(2, com.capnproto.schema.Value.uint8.default)
  override def uint16: Option[Short] = struct.getShort(1, com.capnproto.schema.Value.uint16.default)
  override def uint32: Option[Int] = struct.getInt(1, com.capnproto.schema.Value.uint32.default)
  override def uint64: Option[Long] = struct.getLong(1, com.capnproto.schema.Value.uint64.default)
  override def float32: Option[Float] = struct.getFloat(1, com.capnproto.schema.Value.float32.default)
  override def float64: Option[Double] = struct.getDouble(1, com.capnproto.schema.Value.float64.default)
  override def text: Option[String] = struct.getString(0, com.capnproto.schema.Value.text.default)
  override def data: Option[Array[Byte]] = struct.getData(0, com.capnproto.schema.Value.data.default)
  override def list: Option[Pointer[_]] = struct.getPointer(0)
  override def __enum: Option[Short] = struct.getShort(1, com.capnproto.schema.Value.__enum.default)
  override def __struct: Option[Pointer[_]] = struct.getPointer(0)
  override def interface: Option[Unit] = struct.getNone()
  override def __object: Option[Pointer[_]] = struct.getPointer(0)
}

object Annotation extends MetaStruct[Annotation] {
  override type Self = Annotation.type
  override val structName: String = "Annotation"
  override def create(struct: CapnpStruct): Annotation = new AnnotationMutable(struct)
  def newBuilder(arena: CapnpArenaBuilder): com.capnproto.schema.Annotation.Builder = {
    val (segment, pointerOffset) = arena.allocate(1)
    val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.schema.Annotation.Builder.dataSectionSizeWords, com.capnproto.schema.Annotation.Builder.pointerSectionSizeWords)
    new com.capnproto.schema.Annotation.Builder(struct)
  }

  object Builder extends MetaStructBuilder[com.capnproto.schema.Annotation, com.capnproto.schema.Annotation.Builder] {
    override type Self = com.capnproto.schema.Annotation.Builder.type
    override val structName: String = "Annotation"
    override val dataSectionSizeWords: Short = 1
    override val pointerSectionSizeWords: Short = 1
    override def create(struct: CapnpStructBuilder): com.capnproto.schema.Annotation.Builder = new com.capnproto.schema.Annotation.Builder(struct)
    override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.schema.Annotation.fields
  }
  class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.schema.AnnotationMutable(struct) with StructBuilder[com.capnproto.schema.Annotation, com.capnproto.schema.Annotation.Builder] {
    override type MetaBuilderT = com.capnproto.schema.Annotation.Builder.type

    override def meta: Annotation.type = Annotation
    override def metaBuilder: MetaBuilderT = com.capnproto.schema.Annotation.Builder
    def setId(value: Long): Builder = { struct.setLong(0, value, com.capnproto.schema.Annotation.id.default); this }
    def setValue(value: com.capnproto.schema.Value): Builder = { struct.setNone(); this }
  }

  val id = new FieldDescriptor[Long, Annotation, Annotation.type](
    name = "id",
    meta = Annotation,
    default = Option(0L),
    getter = _.id,
    manifest = manifest[Long],
    discriminantValue = None
  )

  val value = new FieldDescriptor[com.capnproto.schema.Value, Annotation, Annotation.type](
    name = "value",
    meta = Annotation,
    default = Option(null),
    getter = _.value,
    manifest = manifest[com.capnproto.schema.Value],
    discriminantValue = None
  )
  override val fields: Seq[FieldDescriptor[_, Annotation, Annotation.type]] = Seq(id, value)
}

trait Annotation extends Struct[Annotation] {
  override type MetaT = Annotation.type
  override type MetaBuilderT = com.capnproto.schema.Annotation.Builder.type

  override def meta: Annotation.type = Annotation
  override def metaBuilder: com.capnproto.schema.Annotation.Builder.type = com.capnproto.schema.Annotation.Builder

  def struct: CapnpStruct

  def id: Option[Long]
  def value: Option[com.capnproto.schema.Value]
}

class AnnotationMutable(override val struct: CapnpStruct) extends Annotation {
  override def id: Option[Long] = struct.getLong(0, com.capnproto.schema.Annotation.id.default)
  override def value: Option[com.capnproto.schema.Value] = struct.getStruct(0, com.capnproto.schema.Value, com.capnproto.schema.Annotation.value.default)
}

object ElementSize extends EnumMeta[ElementSize] {
  case class Unknown(override val id: Short) extends ElementSize(ElementSize, id, null)

  val empty = new ElementSize(this, 0.toShort, "empty")
  val bit = new ElementSize(this, 1.toShort, "bit")
  val byte = new ElementSize(this, 2.toShort, "byte")
  val twoBytes = new ElementSize(this, 3.toShort, "twoBytes")
  val fourBytes = new ElementSize(this, 4.toShort, "fourBytes")
  val eightBytes = new ElementSize(this, 5.toShort, "eightBytes")
  val pointer = new ElementSize(this, 6.toShort, "pointer")
  val inlineComposite = new ElementSize(this, 7.toShort, "inlineComposite")

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

  override def findByIdOrNull(id: Short): ElementSize = values.lift(id.toInt).getOrElse(null)
  override def findByNameOrNull(name: String): ElementSize = null
}

sealed class ElementSize(
  override val meta: EnumMeta[ElementSize],
  override val id: Short,
  override val name: String
) extends Enum[ElementSize]

object CodeGeneratorRequest extends MetaStruct[CodeGeneratorRequest] {
  override type Self = CodeGeneratorRequest.type
  override val structName: String = "CodeGeneratorRequest"
  override def create(struct: CapnpStruct): CodeGeneratorRequest = new CodeGeneratorRequestMutable(struct)
  def newBuilder(arena: CapnpArenaBuilder): com.capnproto.schema.CodeGeneratorRequest.Builder = {
    val (segment, pointerOffset) = arena.allocate(1)
    val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.schema.CodeGeneratorRequest.Builder.dataSectionSizeWords, com.capnproto.schema.CodeGeneratorRequest.Builder.pointerSectionSizeWords)
    new com.capnproto.schema.CodeGeneratorRequest.Builder(struct)
  }

  object Builder extends MetaStructBuilder[com.capnproto.schema.CodeGeneratorRequest, com.capnproto.schema.CodeGeneratorRequest.Builder] {
    override type Self = com.capnproto.schema.CodeGeneratorRequest.Builder.type
    override val structName: String = "CodeGeneratorRequest"
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
    override val structName: String = "RequestedFile"
    override def create(struct: CapnpStruct): RequestedFile = new RequestedFileMutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.schema.CodeGeneratorRequest.RequestedFile.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.schema.CodeGeneratorRequest.RequestedFile.Builder.dataSectionSizeWords, com.capnproto.schema.CodeGeneratorRequest.RequestedFile.Builder.pointerSectionSizeWords)
      new com.capnproto.schema.CodeGeneratorRequest.RequestedFile.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.schema.CodeGeneratorRequest.RequestedFile, com.capnproto.schema.CodeGeneratorRequest.RequestedFile.Builder] {
      override type Self = com.capnproto.schema.CodeGeneratorRequest.RequestedFile.Builder.type
      override val structName: String = "RequestedFile"
      override val dataSectionSizeWords: Short = 1
      override val pointerSectionSizeWords: Short = 2
      override def create(struct: CapnpStructBuilder): com.capnproto.schema.CodeGeneratorRequest.RequestedFile.Builder = new com.capnproto.schema.CodeGeneratorRequest.RequestedFile.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.schema.CodeGeneratorRequest.RequestedFile.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.schema.CodeGeneratorRequest.RequestedFileMutable(struct) with StructBuilder[com.capnproto.schema.CodeGeneratorRequest.RequestedFile, com.capnproto.schema.CodeGeneratorRequest.RequestedFile.Builder] {
      override type MetaBuilderT = com.capnproto.schema.CodeGeneratorRequest.RequestedFile.Builder.type

      override def meta: RequestedFile.type = RequestedFile
      override def metaBuilder: MetaBuilderT = com.capnproto.schema.CodeGeneratorRequest.RequestedFile.Builder
      def setId(value: Long): Builder = { struct.setLong(0, value, com.capnproto.schema.CodeGeneratorRequest.RequestedFile.id.default); this }
      def setFilename(value: String): Builder = { struct.setString(0, value); this }
      def initImports(count: Int): Seq[com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import.Builder] = {
        val list = struct.initPointerList(1, count, com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import.Builder)
        Range(0, count).map(i => new com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import.Builder(list.initStruct(i, com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import.Builder)))
      }
      def setImports(buildFn: CapnpArenaBuilder => Seq[com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import.Builder]): Builder = { struct.setStructList(1, com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import.Builder, buildFn(struct.arena).map(_.struct)); this }
    }

    object __Import extends MetaStruct[__Import] {
      override type Self = __Import.type
      override val structName: String = "Import"
      override def create(struct: CapnpStruct): __Import = new __ImportMutable(struct)
      def newBuilder(arena: CapnpArenaBuilder): com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import.Builder = {
        val (segment, pointerOffset) = arena.allocate(1)
        val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import.Builder.dataSectionSizeWords, com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import.Builder.pointerSectionSizeWords)
        new com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import.Builder(struct)
      }

      object Builder extends MetaStructBuilder[com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import, com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import.Builder] {
        override type Self = com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import.Builder.type
        override val structName: String = "Import"
        override val dataSectionSizeWords: Short = 1
        override val pointerSectionSizeWords: Short = 1
        override def create(struct: CapnpStructBuilder): com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import.Builder = new com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import.Builder(struct)
        override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import.fields
      }
      class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__ImportMutable(struct) with StructBuilder[com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import, com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import.Builder] {
        override type MetaBuilderT = com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import.Builder.type

        override def meta: __Import.type = __Import
        override def metaBuilder: MetaBuilderT = com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import.Builder
        def setId(value: Long): Builder = { struct.setLong(0, value, com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import.id.default); this }
        def setName(value: String): Builder = { struct.setString(0, value); this }
      }

      val id = new FieldDescriptor[Long, __Import, __Import.type](
        name = "id",
        meta = __Import,
        default = Option(0L),
        getter = _.id,
        manifest = manifest[Long],
        discriminantValue = None
      )

      val name = new FieldDescriptor[String, __Import, __Import.type](
        name = "name",
        meta = __Import,
        default = Option(""),
        getter = _.name,
        manifest = manifest[String],
        discriminantValue = None
      )
      override val fields: Seq[FieldDescriptor[_, __Import, __Import.type]] = Seq(id, name)
    }

    trait __Import extends Struct[__Import] {
      override type MetaT = __Import.type
      override type MetaBuilderT = com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import.Builder.type

      override def meta: __Import.type = __Import
      override def metaBuilder: com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import.Builder.type = com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import.Builder

      def struct: CapnpStruct

      def id: Option[Long]
      def name: Option[String]
    }

    class __ImportMutable(override val struct: CapnpStruct) extends __Import {
      override def id: Option[Long] = struct.getLong(0, com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import.id.default)
      override def name: Option[String] = struct.getString(0, com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import.name.default)
    }
    val id = new FieldDescriptor[Long, RequestedFile, RequestedFile.type](
      name = "id",
      meta = RequestedFile,
      default = Option(0L),
      getter = _.id,
      manifest = manifest[Long],
      discriminantValue = None
    )

    val filename = new FieldDescriptor[String, RequestedFile, RequestedFile.type](
      name = "filename",
      meta = RequestedFile,
      default = Option(""),
      getter = _.filename,
      manifest = manifest[String],
      discriminantValue = None
    )

    val imports = new FieldDescriptor[Seq[com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import], RequestedFile, RequestedFile.type](
      name = "imports",
      meta = RequestedFile,
      default = Option(null),
      getter = x => Some(x.imports),
      manifest = manifest[Seq[com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import]],
      discriminantValue = None
    )
    override val fields: Seq[FieldDescriptor[_, RequestedFile, RequestedFile.type]] = Seq(id, filename, imports)
  }

  trait RequestedFile extends Struct[RequestedFile] {
    override type MetaT = RequestedFile.type
    override type MetaBuilderT = com.capnproto.schema.CodeGeneratorRequest.RequestedFile.Builder.type

    override def meta: RequestedFile.type = RequestedFile
    override def metaBuilder: com.capnproto.schema.CodeGeneratorRequest.RequestedFile.Builder.type = com.capnproto.schema.CodeGeneratorRequest.RequestedFile.Builder

    def struct: CapnpStruct

    def id: Option[Long]
    def filename: Option[String]
    def imports: Seq[com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import]
  }

  class RequestedFileMutable(override val struct: CapnpStruct) extends RequestedFile {
    override def id: Option[Long] = struct.getLong(0, com.capnproto.schema.CodeGeneratorRequest.RequestedFile.id.default)
    override def filename: Option[String] = struct.getString(0, com.capnproto.schema.CodeGeneratorRequest.RequestedFile.filename.default)
    override def imports: Seq[com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import] = struct.getList(1, (l: CapnpList) => (o: Int) => l.getStruct(o, com.capnproto.schema.CodeGeneratorRequest.RequestedFile.__Import), com.capnproto.schema.CodeGeneratorRequest.RequestedFile.imports.default)
  }
  val nodes = new FieldDescriptor[Seq[com.capnproto.schema.Node], CodeGeneratorRequest, CodeGeneratorRequest.type](
    name = "nodes",
    meta = CodeGeneratorRequest,
    default = Option(null),
    getter = x => Some(x.nodes),
    manifest = manifest[Seq[com.capnproto.schema.Node]],
    discriminantValue = None
  )

  val requestedFiles = new FieldDescriptor[Seq[com.capnproto.schema.CodeGeneratorRequest.RequestedFile], CodeGeneratorRequest, CodeGeneratorRequest.type](
    name = "requestedFiles",
    meta = CodeGeneratorRequest,
    default = Option(null),
    getter = x => Some(x.requestedFiles),
    manifest = manifest[Seq[com.capnproto.schema.CodeGeneratorRequest.RequestedFile]],
    discriminantValue = None
  )
  override val fields: Seq[FieldDescriptor[_, CodeGeneratorRequest, CodeGeneratorRequest.type]] = Seq(nodes, requestedFiles)
}

trait CodeGeneratorRequest extends Struct[CodeGeneratorRequest] {
  override type MetaT = CodeGeneratorRequest.type
  override type MetaBuilderT = com.capnproto.schema.CodeGeneratorRequest.Builder.type

  override def meta: CodeGeneratorRequest.type = CodeGeneratorRequest
  override def metaBuilder: com.capnproto.schema.CodeGeneratorRequest.Builder.type = com.capnproto.schema.CodeGeneratorRequest.Builder

  def struct: CapnpStruct

  def nodes: Seq[com.capnproto.schema.Node]
  def requestedFiles: Seq[com.capnproto.schema.CodeGeneratorRequest.RequestedFile]
}

class CodeGeneratorRequestMutable(override val struct: CapnpStruct) extends CodeGeneratorRequest {
  override def nodes: Seq[com.capnproto.schema.Node] = struct.getList(0, (l: CapnpList) => (o: Int) => l.getStruct(o, com.capnproto.schema.Node), com.capnproto.schema.CodeGeneratorRequest.nodes.default)
  override def requestedFiles: Seq[com.capnproto.schema.CodeGeneratorRequest.RequestedFile] = struct.getList(1, (l: CapnpList) => (o: Int) => l.getStruct(o, com.capnproto.schema.CodeGeneratorRequest.RequestedFile), com.capnproto.schema.CodeGeneratorRequest.requestedFiles.default)
}
