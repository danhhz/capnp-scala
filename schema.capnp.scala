// examples/schema.capnp

package foo

import com.foursquare.spindle.{Enum, EnumMeta}
import capnp.{HasUnion, UnionMeta, UnionValue, UntypedFieldDescriptor, FieldDescriptor, UntypedStruct, Struct, UntypedMetaStruct, MetaStruct}
import capnp.{CapnpStruct, Pointer => CapnpPointer, CapnpList, CapnpTag}
import java.nio.ByteBuffer

object Node extends MetaStruct[Node] {
  override type Self = Node.type
  override def create(struct: CapnpStruct): Node = new NodeMutable(struct)
  override val recordName: String = "Node"
  override val fields: Seq[FieldDescriptor[_, Node, Node.type]] = Seq(id, displayName, displayNamePrefixLength, scopeId, nestedNodes, annotations, file, __struct, __enum, interface, const, annotation)

  object NestedNode extends MetaStruct[NestedNode] {
    override type Self = NestedNode.type
    override def create(struct: CapnpStruct): NestedNode = new NestedNodeMutable(struct)
    override val recordName: String = "NestedNode"
    override val fields: Seq[FieldDescriptor[_, NestedNode, NestedNode.type]] = Seq(name, id)


    val name = new FieldDescriptor[String, NestedNode, NestedNode.type](
      name = "name",
      meta = NestedNode
    )

    val id = new FieldDescriptor[java.lang.Long, NestedNode, NestedNode.type](
      name = "id",
      meta = NestedNode
    )
  }

  trait NestedNode extends Struct[NestedNode] {
    override type MetaT = NestedNode.type

    def struct: CapnpStruct

    def name: Option[String]
    def id: Option[java.lang.Long]
    override def toString: String = pretty(0)
    def pretty(indent: Int = 0): String = {
      println("    " * indent + "[" + meta.recordName + "]")
      name.foreach(fieldValue => {print("    " * indent + " name: "); println(fieldValue);})
      id.foreach(fieldValue => {print("    " * indent + " id: "); println(fieldValue);})

      ""
    }
  }

  trait NestedNodeProxy extends NestedNode {
    def underlying: NestedNode

    override def struct: CapnpStruct = underlying.struct

    override def name: Option[String]
    override def id: Option[java.lang.Long]
  }

  class NestedNodeMutable(override val struct: CapnpStruct) extends NestedNode {
    override def meta: NestedNode.type = NestedNode

    override def name: Option[String] = struct.getString(0)
    override def id: Option[java.lang.Long] = struct.getLong(0)
  }
  sealed trait Union extends UnionValue[foo.Node.Union] { def pretty(indent: Int = 0): String } 
  object Union extends UnionMeta[foo.Node.Union] {
    case class Unknown(discriminant: Short) extends foo.Node.Union { def pretty(indent: Int): String = toString }
    case class file(value: Option[Unit]) extends foo.Node.Union { def pretty(indent: Int): String = { println(value.toString); "" }}
    case class __struct(value: Option[foo.Node.__Struct]) extends foo.Node.Union { def pretty(indent: Int): String = { value.map(fieldValue => {
print("(\n"); fieldValue.pretty(indent + 1); print("    " * indent + ")\n");}).getOrElse(println("None")); ""}}
    case class __enum(value: Option[foo.Node.__Enum]) extends foo.Node.Union { def pretty(indent: Int): String = { value.map(fieldValue => {
print("(\n"); fieldValue.pretty(indent + 1); print("    " * indent + ")\n");}).getOrElse(println("None")); ""}}
    case class interface(value: Option[foo.Node.Interface]) extends foo.Node.Union { def pretty(indent: Int): String = { value.map(fieldValue => {
print("(\n"); fieldValue.pretty(indent + 1); print("    " * indent + ")\n");}).getOrElse(println("None")); ""}}
    case class const(value: Option[foo.Node.Const]) extends foo.Node.Union { def pretty(indent: Int): String = { value.map(fieldValue => {
print("(\n"); fieldValue.pretty(indent + 1); print("    " * indent + ")\n");}).getOrElse(println("None")); ""}}
    case class annotation(value: Option[foo.Node.Annotation]) extends foo.Node.Union { def pretty(indent: Int): String = { value.map(fieldValue => {
print("(\n"); fieldValue.pretty(indent + 1); print("    " * indent + ")\n");}).getOrElse(println("None")); ""}}
  }

  object __Struct extends MetaStruct[__Struct] {
    override type Self = __Struct.type
    override def create(struct: CapnpStruct): __Struct = new __StructMutable(struct)
    override val recordName: String = "__Struct"
    override val fields: Seq[FieldDescriptor[_, __Struct, __Struct.type]] = Seq(dataWordCount, pointerCount, preferredListEncoding, isGroup, discriminantCount, discriminantOffset, __fields)


    val dataWordCount = new FieldDescriptor[java.lang.Short, __Struct, __Struct.type](
      name = "dataWordCount",
      meta = __Struct
    )

    val pointerCount = new FieldDescriptor[java.lang.Short, __Struct, __Struct.type](
      name = "pointerCount",
      meta = __Struct
    )

    val preferredListEncoding = new FieldDescriptor[foo.ElementSize, __Struct, __Struct.type](
      name = "preferredListEncoding",
      meta = __Struct
    )

    val isGroup = new FieldDescriptor[java.lang.Boolean, __Struct, __Struct.type](
      name = "isGroup",
      meta = __Struct
    )

    val discriminantCount = new FieldDescriptor[java.lang.Short, __Struct, __Struct.type](
      name = "discriminantCount",
      meta = __Struct
    )

    val discriminantOffset = new FieldDescriptor[java.lang.Integer, __Struct, __Struct.type](
      name = "discriminantOffset",
      meta = __Struct
    )

    val __fields = new FieldDescriptor[Seq[foo.Field], __Struct, __Struct.type](
      name = "fields",
      meta = __Struct
    )
  }

  trait __Struct extends Struct[__Struct] {
    override type MetaT = __Struct.type

    def struct: CapnpStruct

    def dataWordCount: Option[java.lang.Short]
    def pointerCount: Option[java.lang.Short]
    def preferredListEncoding: Option[foo.ElementSize]
    def isGroup: Option[java.lang.Boolean]
    def discriminantCount: Option[java.lang.Short]
    def discriminantOffset: Option[java.lang.Integer]
    def __fields: Option[Seq[foo.Field]]
    override def toString: String = pretty(0)
    def pretty(indent: Int = 0): String = {
      println("    " * indent + "[" + meta.recordName + "]")
      dataWordCount.foreach(fieldValue => {print("    " * indent + " dataWordCount: "); println(fieldValue);})
      pointerCount.foreach(fieldValue => {print("    " * indent + " pointerCount: "); println(fieldValue);})
      preferredListEncoding.foreach(fieldValue => {print("    " * indent + " preferredListEncoding: "); println(fieldValue);})
      isGroup.foreach(fieldValue => {print("    " * indent + " isGroup: "); println(fieldValue);})
      discriminantCount.foreach(fieldValue => {print("    " * indent + " discriminantCount: "); println(fieldValue);})
      discriminantOffset.foreach(fieldValue => {print("    " * indent + " discriminantOffset: "); println(fieldValue);})
      __fields.foreach(fieldValue => {print("    " * indent + " __fields: "); print("[\n"); fieldValue.map(_.pretty(indent + 1)); print("    " * indent + " ]\n");})

      ""
    }
  }

  trait __StructProxy extends __Struct {
    def underlying: __Struct

    override def struct: CapnpStruct = underlying.struct

    override def dataWordCount: Option[java.lang.Short]
    override def pointerCount: Option[java.lang.Short]
    override def preferredListEncoding: Option[foo.ElementSize]
    override def isGroup: Option[java.lang.Boolean]
    override def discriminantCount: Option[java.lang.Short]
    override def discriminantOffset: Option[java.lang.Integer]
    override def __fields: Option[Seq[foo.Field]]
  }

  class __StructMutable(override val struct: CapnpStruct) extends __Struct {
    override def meta: __Struct.type = __Struct

    override def dataWordCount: Option[java.lang.Short] = struct.getShort(7)
    override def pointerCount: Option[java.lang.Short] = struct.getShort(12)
    override def preferredListEncoding: Option[foo.ElementSize] = struct.getShort(13).map(id => foo.ElementSize.findById(id.toInt).getOrElse(foo.ElementSize.Unknown(id.toShort)))
    override def isGroup: Option[java.lang.Boolean] = struct.getBoolean(224)
    override def discriminantCount: Option[java.lang.Short] = struct.getShort(15)
    override def discriminantOffset: Option[java.lang.Integer] = struct.getInt(8)
    override def __fields: Option[Seq[foo.Field]] = struct.getStructList(3).map(_.map(new foo.FieldMutable(_)))
  }
  object __Enum extends MetaStruct[__Enum] {
    override type Self = __Enum.type
    override def create(struct: CapnpStruct): __Enum = new __EnumMutable(struct)
    override val recordName: String = "__Enum"
    override val fields: Seq[FieldDescriptor[_, __Enum, __Enum.type]] = Seq(enumerants)


    val enumerants = new FieldDescriptor[Seq[foo.Enumerant], __Enum, __Enum.type](
      name = "enumerants",
      meta = __Enum
    )
  }

  trait __Enum extends Struct[__Enum] {
    override type MetaT = __Enum.type

    def struct: CapnpStruct

    def enumerants: Option[Seq[foo.Enumerant]]
    override def toString: String = pretty(0)
    def pretty(indent: Int = 0): String = {
      println("    " * indent + "[" + meta.recordName + "]")
      enumerants.foreach(fieldValue => {print("    " * indent + " enumerants: "); print("[\n"); fieldValue.map(_.pretty(indent + 1)); print("    " * indent + " ]\n");})

      ""
    }
  }

  trait __EnumProxy extends __Enum {
    def underlying: __Enum

    override def struct: CapnpStruct = underlying.struct

    override def enumerants: Option[Seq[foo.Enumerant]]
  }

  class __EnumMutable(override val struct: CapnpStruct) extends __Enum {
    override def meta: __Enum.type = __Enum

    override def enumerants: Option[Seq[foo.Enumerant]] = struct.getStructList(3).map(_.map(new foo.EnumerantMutable(_)))
  }
  object Interface extends MetaStruct[Interface] {
    override type Self = Interface.type
    override def create(struct: CapnpStruct): Interface = new InterfaceMutable(struct)
    override val recordName: String = "Interface"
    override val fields: Seq[FieldDescriptor[_, Interface, Interface.type]] = Seq(methods)


    val methods = new FieldDescriptor[Seq[foo.Method], Interface, Interface.type](
      name = "methods",
      meta = Interface
    )
  }

  trait Interface extends Struct[Interface] {
    override type MetaT = Interface.type

    def struct: CapnpStruct

    def methods: Option[Seq[foo.Method]]
    override def toString: String = pretty(0)
    def pretty(indent: Int = 0): String = {
      println("    " * indent + "[" + meta.recordName + "]")
      methods.foreach(fieldValue => {print("    " * indent + " methods: "); print("[\n"); fieldValue.map(_.pretty(indent + 1)); print("    " * indent + " ]\n");})

      ""
    }
  }

  trait InterfaceProxy extends Interface {
    def underlying: Interface

    override def struct: CapnpStruct = underlying.struct

    override def methods: Option[Seq[foo.Method]]
  }

  class InterfaceMutable(override val struct: CapnpStruct) extends Interface {
    override def meta: Interface.type = Interface

    override def methods: Option[Seq[foo.Method]] = struct.getStructList(3).map(_.map(new foo.MethodMutable(_)))
  }
  object Const extends MetaStruct[Const] {
    override type Self = Const.type
    override def create(struct: CapnpStruct): Const = new ConstMutable(struct)
    override val recordName: String = "Const"
    override val fields: Seq[FieldDescriptor[_, Const, Const.type]] = Seq(__type, value)


    val __type = new FieldDescriptor[foo.__Type, Const, Const.type](
      name = "type",
      meta = Const
    )

    val value = new FieldDescriptor[foo.Value, Const, Const.type](
      name = "value",
      meta = Const
    )
  }

  trait Const extends Struct[Const] {
    override type MetaT = Const.type

    def struct: CapnpStruct

    def __type: Option[foo.__Type]
    def value: Option[foo.Value]
    override def toString: String = pretty(0)
    def pretty(indent: Int = 0): String = {
      println("    " * indent + "[" + meta.recordName + "]")
      __type.foreach(fieldValue => {print("    " * indent + " __type: "); print("\n"); fieldValue.pretty(indent + 1);})
      value.foreach(fieldValue => {print("    " * indent + " value: "); print("\n"); fieldValue.pretty(indent + 1);})

      ""
    }
  }

  trait ConstProxy extends Const {
    def underlying: Const

    override def struct: CapnpStruct = underlying.struct

    override def __type: Option[foo.__Type]
    override def value: Option[foo.Value]
  }

  class ConstMutable(override val struct: CapnpStruct) extends Const {
    override def meta: Const.type = Const

    override def __type: Option[foo.__Type] = struct.getStruct(3).map(new foo.__TypeMutable(_))
    override def value: Option[foo.Value] = struct.getStruct(4).map(new foo.ValueMutable(_))
  }
  object Annotation extends MetaStruct[Annotation] {
    override type Self = Annotation.type
    override def create(struct: CapnpStruct): Annotation = new AnnotationMutable(struct)
    override val recordName: String = "Annotation"
    override val fields: Seq[FieldDescriptor[_, Annotation, Annotation.type]] = Seq(__type, targetsFile, targetsConst, targetsEnum, targetsEnumerant, targetsStruct, targetsField, targetsUnion, targetsGroup, targetsInterface, targetsMethod, targetsParam, targetsAnnotation)


    val __type = new FieldDescriptor[foo.__Type, Annotation, Annotation.type](
      name = "type",
      meta = Annotation
    )

    val targetsFile = new FieldDescriptor[java.lang.Boolean, Annotation, Annotation.type](
      name = "targetsFile",
      meta = Annotation
    )

    val targetsConst = new FieldDescriptor[java.lang.Boolean, Annotation, Annotation.type](
      name = "targetsConst",
      meta = Annotation
    )

    val targetsEnum = new FieldDescriptor[java.lang.Boolean, Annotation, Annotation.type](
      name = "targetsEnum",
      meta = Annotation
    )

    val targetsEnumerant = new FieldDescriptor[java.lang.Boolean, Annotation, Annotation.type](
      name = "targetsEnumerant",
      meta = Annotation
    )

    val targetsStruct = new FieldDescriptor[java.lang.Boolean, Annotation, Annotation.type](
      name = "targetsStruct",
      meta = Annotation
    )

    val targetsField = new FieldDescriptor[java.lang.Boolean, Annotation, Annotation.type](
      name = "targetsField",
      meta = Annotation
    )

    val targetsUnion = new FieldDescriptor[java.lang.Boolean, Annotation, Annotation.type](
      name = "targetsUnion",
      meta = Annotation
    )

    val targetsGroup = new FieldDescriptor[java.lang.Boolean, Annotation, Annotation.type](
      name = "targetsGroup",
      meta = Annotation
    )

    val targetsInterface = new FieldDescriptor[java.lang.Boolean, Annotation, Annotation.type](
      name = "targetsInterface",
      meta = Annotation
    )

    val targetsMethod = new FieldDescriptor[java.lang.Boolean, Annotation, Annotation.type](
      name = "targetsMethod",
      meta = Annotation
    )

    val targetsParam = new FieldDescriptor[java.lang.Boolean, Annotation, Annotation.type](
      name = "targetsParam",
      meta = Annotation
    )

    val targetsAnnotation = new FieldDescriptor[java.lang.Boolean, Annotation, Annotation.type](
      name = "targetsAnnotation",
      meta = Annotation
    )
  }

  trait Annotation extends Struct[Annotation] {
    override type MetaT = Annotation.type

    def struct: CapnpStruct

    def __type: Option[foo.__Type]
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
    override def toString: String = pretty(0)
    def pretty(indent: Int = 0): String = {
      println("    " * indent + "[" + meta.recordName + "]")
      __type.foreach(fieldValue => {print("    " * indent + " __type: "); print("\n"); fieldValue.pretty(indent + 1);})
      targetsFile.foreach(fieldValue => {print("    " * indent + " targetsFile: "); println(fieldValue);})
      targetsConst.foreach(fieldValue => {print("    " * indent + " targetsConst: "); println(fieldValue);})
      targetsEnum.foreach(fieldValue => {print("    " * indent + " targetsEnum: "); println(fieldValue);})
      targetsEnumerant.foreach(fieldValue => {print("    " * indent + " targetsEnumerant: "); println(fieldValue);})
      targetsStruct.foreach(fieldValue => {print("    " * indent + " targetsStruct: "); println(fieldValue);})
      targetsField.foreach(fieldValue => {print("    " * indent + " targetsField: "); println(fieldValue);})
      targetsUnion.foreach(fieldValue => {print("    " * indent + " targetsUnion: "); println(fieldValue);})
      targetsGroup.foreach(fieldValue => {print("    " * indent + " targetsGroup: "); println(fieldValue);})
      targetsInterface.foreach(fieldValue => {print("    " * indent + " targetsInterface: "); println(fieldValue);})
      targetsMethod.foreach(fieldValue => {print("    " * indent + " targetsMethod: "); println(fieldValue);})
      targetsParam.foreach(fieldValue => {print("    " * indent + " targetsParam: "); println(fieldValue);})
      targetsAnnotation.foreach(fieldValue => {print("    " * indent + " targetsAnnotation: "); println(fieldValue);})

      ""
    }
  }

  trait AnnotationProxy extends Annotation {
    def underlying: Annotation

    override def struct: CapnpStruct = underlying.struct

    override def __type: Option[foo.__Type]
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
    override def meta: Annotation.type = Annotation

    override def __type: Option[foo.__Type] = struct.getStruct(3).map(new foo.__TypeMutable(_))
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
    meta = Node
  )

  val displayName = new FieldDescriptor[String, Node, Node.type](
    name = "displayName",
    meta = Node
  )

  val displayNamePrefixLength = new FieldDescriptor[java.lang.Integer, Node, Node.type](
    name = "displayNamePrefixLength",
    meta = Node
  )

  val scopeId = new FieldDescriptor[java.lang.Long, Node, Node.type](
    name = "scopeId",
    meta = Node
  )

  val nestedNodes = new FieldDescriptor[Seq[foo.Node.NestedNode], Node, Node.type](
    name = "nestedNodes",
    meta = Node
  )

  val annotations = new FieldDescriptor[Seq[foo.Annotation], Node, Node.type](
    name = "annotations",
    meta = Node
  )

  val file = new FieldDescriptor[Unit, Node, Node.type](
    name = "file",
    meta = Node
  )

  val __struct = new FieldDescriptor[foo.Node.__Struct, Node, Node.type](
    name = "struct",
    meta = Node
  )

  val __enum = new FieldDescriptor[foo.Node.__Enum, Node, Node.type](
    name = "enum",
    meta = Node
  )

  val interface = new FieldDescriptor[foo.Node.Interface, Node, Node.type](
    name = "interface",
    meta = Node
  )

  val const = new FieldDescriptor[foo.Node.Const, Node, Node.type](
    name = "const",
    meta = Node
  )

  val annotation = new FieldDescriptor[foo.Node.Annotation, Node, Node.type](
    name = "annotation",
    meta = Node
  )
}

trait Node extends Struct[Node] with HasUnion[foo.Node.Union] {
  override type MetaT = Node.type

  def struct: CapnpStruct

  def id: Option[java.lang.Long]
  def displayName: Option[String]
  def displayNamePrefixLength: Option[java.lang.Integer]
  def scopeId: Option[java.lang.Long]
  def nestedNodes: Option[Seq[foo.Node.NestedNode]]
  def annotations: Option[Seq[foo.Annotation]]
  def file: Option[Unit]
  def __struct: Option[foo.Node.__Struct]
  def __enum: Option[foo.Node.__Enum]
  def interface: Option[foo.Node.Interface]
  def const: Option[foo.Node.Const]
  def annotation: Option[foo.Node.Annotation]
  override def toString: String = pretty(0)
  def pretty(indent: Int = 0): String = {
    println("    " * indent + "[" + meta.recordName + "]")
    id.foreach(fieldValue => {print("    " * indent + " id: "); println(fieldValue);})
    displayName.foreach(fieldValue => {print("    " * indent + " displayName: "); println(fieldValue);})
    displayNamePrefixLength.foreach(fieldValue => {print("    " * indent + " displayNamePrefixLength: "); println(fieldValue);})
    scopeId.foreach(fieldValue => {print("    " * indent + " scopeId: "); println(fieldValue);})
    nestedNodes.foreach(fieldValue => {print("    " * indent + " nestedNodes: "); print("[\n"); fieldValue.map(_.pretty(indent + 1)); print("    " * indent + " ]\n");})
    annotations.foreach(fieldValue => {print("    " * indent + " annotations: "); print("[\n"); fieldValue.map(_.pretty(indent + 1)); print("    " * indent + " ]\n");})
    print("    " * indent + " union: "); switch.pretty(indent)
    ""
  }
}

trait NodeProxy extends Node with HasUnion[foo.Node.Union] {
  def underlying: Node

  override def struct: CapnpStruct = underlying.struct

  override def switch: foo.Node.Union = underlying.switch
  override def union: UnionMeta[foo.Node.Union] = underlying.union

  override def id: Option[java.lang.Long]
  override def displayName: Option[String]
  override def displayNamePrefixLength: Option[java.lang.Integer]
  override def scopeId: Option[java.lang.Long]
  override def nestedNodes: Option[Seq[foo.Node.NestedNode]]
  override def annotations: Option[Seq[foo.Annotation]]
  override def file: Option[Unit]
  override def __struct: Option[foo.Node.__Struct]
  override def __enum: Option[foo.Node.__Enum]
  override def interface: Option[foo.Node.Interface]
  override def const: Option[foo.Node.Const]
  override def annotation: Option[foo.Node.Annotation]
}

class NodeMutable(override val struct: CapnpStruct) extends Node {
  override def meta: Node.type = Node

  override def discriminant: Short = (struct.getShort(6).getOrElse(new java.lang.Short(0.toShort)): java.lang.Short)
  override def switch: foo.Node.Union = discriminant match {
    case 0 => foo.Node.Union.file(file)
    case 1 => foo.Node.Union.__struct(__struct)
    case 2 => foo.Node.Union.__enum(__enum)
    case 3 => foo.Node.Union.interface(interface)
    case 4 => foo.Node.Union.const(const)
    case 5 => foo.Node.Union.annotation(annotation)
    case d => foo.Node.Union.Unknown(d)
  }
  override def union: UnionMeta[foo.Node.Union] = foo.Node.Union

  override def id: Option[java.lang.Long] = struct.getLong(0)
  override def displayName: Option[String] = struct.getString(0)
  override def displayNamePrefixLength: Option[java.lang.Integer] = struct.getInt(2)
  override def scopeId: Option[java.lang.Long] = struct.getLong(2)
  override def nestedNodes: Option[Seq[foo.Node.NestedNode]] = struct.getStructList(1).map(_.map(new foo.Node.NestedNodeMutable(_)))
  override def annotations: Option[Seq[foo.Annotation]] = struct.getStructList(2).map(_.map(new foo.AnnotationMutable(_)))
  override def file: Option[Unit] = struct.getNone()
  override def __struct: Option[foo.Node.__Struct] = Some(new foo.Node.__StructMutable(struct))

  override def __enum: Option[foo.Node.__Enum] = Some(new foo.Node.__EnumMutable(struct))

  override def interface: Option[foo.Node.Interface] = Some(new foo.Node.InterfaceMutable(struct))

  override def const: Option[foo.Node.Const] = Some(new foo.Node.ConstMutable(struct))

  override def annotation: Option[foo.Node.Annotation] = Some(new foo.Node.AnnotationMutable(struct))

}

object Field extends MetaStruct[Field] {
  override type Self = Field.type
  override def create(struct: CapnpStruct): Field = new FieldMutable(struct)
  override val recordName: String = "Field"
  override val fields: Seq[FieldDescriptor[_, Field, Field.type]] = Seq(name, codeOrder, annotations, discriminantValue, slot, group, ordinal)

  sealed trait Union extends UnionValue[foo.Field.Union] { def pretty(indent: Int = 0): String } 
  object Union extends UnionMeta[foo.Field.Union] {
    case class Unknown(discriminant: Short) extends foo.Field.Union { def pretty(indent: Int): String = toString }
    case class slot(value: Option[foo.Field.Slot]) extends foo.Field.Union { def pretty(indent: Int): String = { value.map(fieldValue => {
print("(\n"); fieldValue.pretty(indent + 1); print("    " * indent + ")\n");}).getOrElse(println("None")); ""}}
    case class group(value: Option[foo.Field.Group]) extends foo.Field.Union { def pretty(indent: Int): String = { value.map(fieldValue => {
print("(\n"); fieldValue.pretty(indent + 1); print("    " * indent + ")\n");}).getOrElse(println("None")); ""}}
  }

  object Slot extends MetaStruct[Slot] {
    override type Self = Slot.type
    override def create(struct: CapnpStruct): Slot = new SlotMutable(struct)
    override val recordName: String = "Slot"
    override val fields: Seq[FieldDescriptor[_, Slot, Slot.type]] = Seq(offset, __type, defaultValue)


    val offset = new FieldDescriptor[java.lang.Integer, Slot, Slot.type](
      name = "offset",
      meta = Slot
    )

    val __type = new FieldDescriptor[foo.__Type, Slot, Slot.type](
      name = "type",
      meta = Slot
    )

    val defaultValue = new FieldDescriptor[foo.Value, Slot, Slot.type](
      name = "defaultValue",
      meta = Slot
    )
  }

  trait Slot extends Struct[Slot] {
    override type MetaT = Slot.type

    def struct: CapnpStruct

    def offset: Option[java.lang.Integer]
    def __type: Option[foo.__Type]
    def defaultValue: Option[foo.Value]
    override def toString: String = pretty(0)
    def pretty(indent: Int = 0): String = {
      println("    " * indent + "[" + meta.recordName + "]")
      offset.foreach(fieldValue => {print("    " * indent + " offset: "); println(fieldValue);})
      __type.foreach(fieldValue => {print("    " * indent + " __type: "); print("\n"); fieldValue.pretty(indent + 1);})
      defaultValue.foreach(fieldValue => {print("    " * indent + " defaultValue: "); print("\n"); fieldValue.pretty(indent + 1);})

      ""
    }
  }

  trait SlotProxy extends Slot {
    def underlying: Slot

    override def struct: CapnpStruct = underlying.struct

    override def offset: Option[java.lang.Integer]
    override def __type: Option[foo.__Type]
    override def defaultValue: Option[foo.Value]
  }

  class SlotMutable(override val struct: CapnpStruct) extends Slot {
    override def meta: Slot.type = Slot

    override def offset: Option[java.lang.Integer] = struct.getInt(1)
    override def __type: Option[foo.__Type] = struct.getStruct(2).map(new foo.__TypeMutable(_))
    override def defaultValue: Option[foo.Value] = struct.getStruct(3).map(new foo.ValueMutable(_))
  }
  object Group extends MetaStruct[Group] {
    override type Self = Group.type
    override def create(struct: CapnpStruct): Group = new GroupMutable(struct)
    override val recordName: String = "Group"
    override val fields: Seq[FieldDescriptor[_, Group, Group.type]] = Seq(typeId)


    val typeId = new FieldDescriptor[java.lang.Long, Group, Group.type](
      name = "typeId",
      meta = Group
    )
  }

  trait Group extends Struct[Group] {
    override type MetaT = Group.type

    def struct: CapnpStruct

    def typeId: Option[java.lang.Long]
    override def toString: String = pretty(0)
    def pretty(indent: Int = 0): String = {
      println("    " * indent + "[" + meta.recordName + "]")
      typeId.foreach(fieldValue => {print("    " * indent + " typeId: "); println(fieldValue);})

      ""
    }
  }

  trait GroupProxy extends Group {
    def underlying: Group

    override def struct: CapnpStruct = underlying.struct

    override def typeId: Option[java.lang.Long]
  }

  class GroupMutable(override val struct: CapnpStruct) extends Group {
    override def meta: Group.type = Group

    override def typeId: Option[java.lang.Long] = struct.getLong(2)
  }
  object Ordinal extends MetaStruct[Ordinal] {
    override type Self = Ordinal.type
    override def create(struct: CapnpStruct): Ordinal = new OrdinalMutable(struct)
    override val recordName: String = "Ordinal"
    override val fields: Seq[FieldDescriptor[_, Ordinal, Ordinal.type]] = Seq(__implicit, explicit)

    sealed trait Union extends UnionValue[foo.Field.Ordinal.Union] { def pretty(indent: Int = 0): String } 
    object Union extends UnionMeta[foo.Field.Ordinal.Union] {
      case class Unknown(discriminant: Short) extends foo.Field.Ordinal.Union { def pretty(indent: Int): String = toString }
      case class __implicit(value: Option[Unit]) extends foo.Field.Ordinal.Union { def pretty(indent: Int): String = { println(value.toString); "" }}
      case class explicit(value: Option[java.lang.Short]) extends foo.Field.Ordinal.Union { def pretty(indent: Int): String = { println(value.toString); "" }}
    }


    val __implicit = new FieldDescriptor[Unit, Ordinal, Ordinal.type](
      name = "implicit",
      meta = Ordinal
    )

    val explicit = new FieldDescriptor[java.lang.Short, Ordinal, Ordinal.type](
      name = "explicit",
      meta = Ordinal
    )
  }

  trait Ordinal extends Struct[Ordinal] with HasUnion[foo.Field.Ordinal.Union] {
    override type MetaT = Ordinal.type

    def struct: CapnpStruct

    def __implicit: Option[Unit]
    def explicit: Option[java.lang.Short]
    override def toString: String = pretty(0)
    def pretty(indent: Int = 0): String = {
      println("    " * indent + "[" + meta.recordName + "]")
      print("    " * indent + " union: "); switch.pretty(indent)
      ""
    }
  }

  trait OrdinalProxy extends Ordinal with HasUnion[foo.Field.Ordinal.Union] {
    def underlying: Ordinal

    override def struct: CapnpStruct = underlying.struct

    override def switch: foo.Field.Ordinal.Union = underlying.switch
    override def union: UnionMeta[foo.Field.Ordinal.Union] = underlying.union

    override def __implicit: Option[Unit]
    override def explicit: Option[java.lang.Short]
  }

  class OrdinalMutable(override val struct: CapnpStruct) extends Ordinal {
    override def meta: Ordinal.type = Ordinal

    override def discriminant: Short = (struct.getShort(5).getOrElse(new java.lang.Short(0.toShort)): java.lang.Short)
    override def switch: foo.Field.Ordinal.Union = discriminant match {
      case 0 => foo.Field.Ordinal.Union.__implicit(__implicit)
      case 1 => foo.Field.Ordinal.Union.explicit(explicit)
      case d => foo.Field.Ordinal.Union.Unknown(d)
    }
    override def union: UnionMeta[foo.Field.Ordinal.Union] = foo.Field.Ordinal.Union

    override def __implicit: Option[Unit] = struct.getNone()
    override def explicit: Option[java.lang.Short] = struct.getShort(6)
  }

  val name = new FieldDescriptor[String, Field, Field.type](
    name = "name",
    meta = Field
  )

  val codeOrder = new FieldDescriptor[java.lang.Short, Field, Field.type](
    name = "codeOrder",
    meta = Field
  )

  val annotations = new FieldDescriptor[Seq[foo.Annotation], Field, Field.type](
    name = "annotations",
    meta = Field
  )

  val discriminantValue = new FieldDescriptor[java.lang.Short, Field, Field.type](
    name = "discriminantValue",
    meta = Field
  )

  val slot = new FieldDescriptor[foo.Field.Slot, Field, Field.type](
    name = "slot",
    meta = Field
  )

  val group = new FieldDescriptor[foo.Field.Group, Field, Field.type](
    name = "group",
    meta = Field
  )

  val ordinal = new FieldDescriptor[foo.Field.Ordinal, Field, Field.type](
    name = "ordinal",
    meta = Field
  )
}

trait Field extends Struct[Field] with HasUnion[foo.Field.Union] {
  override type MetaT = Field.type

  def struct: CapnpStruct

  def name: Option[String]
  def codeOrder: Option[java.lang.Short]
  def annotations: Option[Seq[foo.Annotation]]
  def discriminantValue: Option[java.lang.Short]
  def slot: Option[foo.Field.Slot]
  def group: Option[foo.Field.Group]
  def ordinal: Option[foo.Field.Ordinal]
  override def toString: String = pretty(0)
  def pretty(indent: Int = 0): String = {
    println("    " * indent + "[" + meta.recordName + "]")
    name.foreach(fieldValue => {print("    " * indent + " name: "); println(fieldValue);})
    codeOrder.foreach(fieldValue => {print("    " * indent + " codeOrder: "); println(fieldValue);})
    annotations.foreach(fieldValue => {print("    " * indent + " annotations: "); print("[\n"); fieldValue.map(_.pretty(indent + 1)); print("    " * indent + " ]\n");})
    discriminantValue.foreach(fieldValue => {print("    " * indent + " discriminantValue: "); println(fieldValue);})
    ordinal.foreach(fieldValue => {print("    " * indent + " ordinal: "); print("\n"); fieldValue.pretty(indent + 1);})
    print("    " * indent + " union: "); switch.pretty(indent)
    ""
  }
}

trait FieldProxy extends Field with HasUnion[foo.Field.Union] {
  def underlying: Field

  override def struct: CapnpStruct = underlying.struct

  override def switch: foo.Field.Union = underlying.switch
  override def union: UnionMeta[foo.Field.Union] = underlying.union

  override def name: Option[String]
  override def codeOrder: Option[java.lang.Short]
  override def annotations: Option[Seq[foo.Annotation]]
  override def discriminantValue: Option[java.lang.Short]
  override def slot: Option[foo.Field.Slot]
  override def group: Option[foo.Field.Group]
  override def ordinal: Option[foo.Field.Ordinal]
}

class FieldMutable(override val struct: CapnpStruct) extends Field {
  override def meta: Field.type = Field

  override def discriminant: Short = (struct.getShort(4).getOrElse(new java.lang.Short(0.toShort)): java.lang.Short)
  override def switch: foo.Field.Union = discriminant match {
    case 0 => foo.Field.Union.slot(slot)
    case 1 => foo.Field.Union.group(group)
    case d => foo.Field.Union.Unknown(d)
  }
  override def union: UnionMeta[foo.Field.Union] = foo.Field.Union

  override def name: Option[String] = struct.getString(0)
  override def codeOrder: Option[java.lang.Short] = struct.getShort(0)
  override def annotations: Option[Seq[foo.Annotation]] = struct.getStructList(1).map(_.map(new foo.AnnotationMutable(_)))
  override def discriminantValue: Option[java.lang.Short] = struct.getShort(1)
  override def slot: Option[foo.Field.Slot] = Some(new foo.Field.SlotMutable(struct))

  override def group: Option[foo.Field.Group] = Some(new foo.Field.GroupMutable(struct))

  override def ordinal: Option[foo.Field.Ordinal] = Some(new foo.Field.OrdinalMutable(struct))

}

object Enumerant extends MetaStruct[Enumerant] {
  override type Self = Enumerant.type
  override def create(struct: CapnpStruct): Enumerant = new EnumerantMutable(struct)
  override val recordName: String = "Enumerant"
  override val fields: Seq[FieldDescriptor[_, Enumerant, Enumerant.type]] = Seq(name, codeOrder, annotations)


  val name = new FieldDescriptor[String, Enumerant, Enumerant.type](
    name = "name",
    meta = Enumerant
  )

  val codeOrder = new FieldDescriptor[java.lang.Short, Enumerant, Enumerant.type](
    name = "codeOrder",
    meta = Enumerant
  )

  val annotations = new FieldDescriptor[Seq[foo.Annotation], Enumerant, Enumerant.type](
    name = "annotations",
    meta = Enumerant
  )
}

trait Enumerant extends Struct[Enumerant] {
  override type MetaT = Enumerant.type

  def struct: CapnpStruct

  def name: Option[String]
  def codeOrder: Option[java.lang.Short]
  def annotations: Option[Seq[foo.Annotation]]
  override def toString: String = pretty(0)
  def pretty(indent: Int = 0): String = {
    println("    " * indent + "[" + meta.recordName + "]")
    name.foreach(fieldValue => {print("    " * indent + " name: "); println(fieldValue);})
    codeOrder.foreach(fieldValue => {print("    " * indent + " codeOrder: "); println(fieldValue);})
    annotations.foreach(fieldValue => {print("    " * indent + " annotations: "); print("[\n"); fieldValue.map(_.pretty(indent + 1)); print("    " * indent + " ]\n");})

    ""
  }
}

trait EnumerantProxy extends Enumerant {
  def underlying: Enumerant

  override def struct: CapnpStruct = underlying.struct

  override def name: Option[String]
  override def codeOrder: Option[java.lang.Short]
  override def annotations: Option[Seq[foo.Annotation]]
}

class EnumerantMutable(override val struct: CapnpStruct) extends Enumerant {
  override def meta: Enumerant.type = Enumerant

  override def name: Option[String] = struct.getString(0)
  override def codeOrder: Option[java.lang.Short] = struct.getShort(0)
  override def annotations: Option[Seq[foo.Annotation]] = struct.getStructList(1).map(_.map(new foo.AnnotationMutable(_)))
}

object Method extends MetaStruct[Method] {
  override type Self = Method.type
  override def create(struct: CapnpStruct): Method = new MethodMutable(struct)
  override val recordName: String = "Method"
  override val fields: Seq[FieldDescriptor[_, Method, Method.type]] = Seq(name, codeOrder, params, requiredParamCount, returnType, annotations)

  object Param extends MetaStruct[Param] {
    override type Self = Param.type
    override def create(struct: CapnpStruct): Param = new ParamMutable(struct)
    override val recordName: String = "Param"
    override val fields: Seq[FieldDescriptor[_, Param, Param.type]] = Seq(name, __type, defaultValue, annotations)


    val name = new FieldDescriptor[String, Param, Param.type](
      name = "name",
      meta = Param
    )

    val __type = new FieldDescriptor[foo.__Type, Param, Param.type](
      name = "type",
      meta = Param
    )

    val defaultValue = new FieldDescriptor[foo.Value, Param, Param.type](
      name = "defaultValue",
      meta = Param
    )

    val annotations = new FieldDescriptor[Seq[foo.Annotation], Param, Param.type](
      name = "annotations",
      meta = Param
    )
  }

  trait Param extends Struct[Param] {
    override type MetaT = Param.type

    def struct: CapnpStruct

    def name: Option[String]
    def __type: Option[foo.__Type]
    def defaultValue: Option[foo.Value]
    def annotations: Option[Seq[foo.Annotation]]
    override def toString: String = pretty(0)
    def pretty(indent: Int = 0): String = {
      println("    " * indent + "[" + meta.recordName + "]")
      name.foreach(fieldValue => {print("    " * indent + " name: "); println(fieldValue);})
      __type.foreach(fieldValue => {print("    " * indent + " __type: "); print("\n"); fieldValue.pretty(indent + 1);})
      defaultValue.foreach(fieldValue => {print("    " * indent + " defaultValue: "); print("\n"); fieldValue.pretty(indent + 1);})
      annotations.foreach(fieldValue => {print("    " * indent + " annotations: "); print("[\n"); fieldValue.map(_.pretty(indent + 1)); print("    " * indent + " ]\n");})

      ""
    }
  }

  trait ParamProxy extends Param {
    def underlying: Param

    override def struct: CapnpStruct = underlying.struct

    override def name: Option[String]
    override def __type: Option[foo.__Type]
    override def defaultValue: Option[foo.Value]
    override def annotations: Option[Seq[foo.Annotation]]
  }

  class ParamMutable(override val struct: CapnpStruct) extends Param {
    override def meta: Param.type = Param

    override def name: Option[String] = struct.getString(0)
    override def __type: Option[foo.__Type] = struct.getStruct(1).map(new foo.__TypeMutable(_))
    override def defaultValue: Option[foo.Value] = struct.getStruct(2).map(new foo.ValueMutable(_))
    override def annotations: Option[Seq[foo.Annotation]] = struct.getStructList(3).map(_.map(new foo.AnnotationMutable(_)))
  }

  val name = new FieldDescriptor[String, Method, Method.type](
    name = "name",
    meta = Method
  )

  val codeOrder = new FieldDescriptor[java.lang.Short, Method, Method.type](
    name = "codeOrder",
    meta = Method
  )

  val params = new FieldDescriptor[Seq[foo.Method.Param], Method, Method.type](
    name = "params",
    meta = Method
  )

  val requiredParamCount = new FieldDescriptor[java.lang.Short, Method, Method.type](
    name = "requiredParamCount",
    meta = Method
  )

  val returnType = new FieldDescriptor[foo.__Type, Method, Method.type](
    name = "returnType",
    meta = Method
  )

  val annotations = new FieldDescriptor[Seq[foo.Annotation], Method, Method.type](
    name = "annotations",
    meta = Method
  )
}

trait Method extends Struct[Method] {
  override type MetaT = Method.type

  def struct: CapnpStruct

  def name: Option[String]
  def codeOrder: Option[java.lang.Short]
  def params: Option[Seq[foo.Method.Param]]
  def requiredParamCount: Option[java.lang.Short]
  def returnType: Option[foo.__Type]
  def annotations: Option[Seq[foo.Annotation]]
  override def toString: String = pretty(0)
  def pretty(indent: Int = 0): String = {
    println("    " * indent + "[" + meta.recordName + "]")
    name.foreach(fieldValue => {print("    " * indent + " name: "); println(fieldValue);})
    codeOrder.foreach(fieldValue => {print("    " * indent + " codeOrder: "); println(fieldValue);})
    params.foreach(fieldValue => {print("    " * indent + " params: "); print("[\n"); fieldValue.map(_.pretty(indent + 1)); print("    " * indent + " ]\n");})
    requiredParamCount.foreach(fieldValue => {print("    " * indent + " requiredParamCount: "); println(fieldValue);})
    returnType.foreach(fieldValue => {print("    " * indent + " returnType: "); print("\n"); fieldValue.pretty(indent + 1);})
    annotations.foreach(fieldValue => {print("    " * indent + " annotations: "); print("[\n"); fieldValue.map(_.pretty(indent + 1)); print("    " * indent + " ]\n");})

    ""
  }
}

trait MethodProxy extends Method {
  def underlying: Method

  override def struct: CapnpStruct = underlying.struct

  override def name: Option[String]
  override def codeOrder: Option[java.lang.Short]
  override def params: Option[Seq[foo.Method.Param]]
  override def requiredParamCount: Option[java.lang.Short]
  override def returnType: Option[foo.__Type]
  override def annotations: Option[Seq[foo.Annotation]]
}

class MethodMutable(override val struct: CapnpStruct) extends Method {
  override def meta: Method.type = Method

  override def name: Option[String] = struct.getString(0)
  override def codeOrder: Option[java.lang.Short] = struct.getShort(0)
  override def params: Option[Seq[foo.Method.Param]] = struct.getStructList(1).map(_.map(new foo.Method.ParamMutable(_)))
  override def requiredParamCount: Option[java.lang.Short] = struct.getShort(1)
  override def returnType: Option[foo.__Type] = struct.getStruct(2).map(new foo.__TypeMutable(_))
  override def annotations: Option[Seq[foo.Annotation]] = struct.getStructList(3).map(_.map(new foo.AnnotationMutable(_)))
}

object __Type extends MetaStruct[__Type] {
  override type Self = __Type.type
  override def create(struct: CapnpStruct): __Type = new __TypeMutable(struct)
  override val recordName: String = "Type"
  override val fields: Seq[FieldDescriptor[_, __Type, __Type.type]] = Seq(void, bool, int8, int16, int32, int64, uint8, uint16, uint32, uint64, float32, float64, text, data, list, __enum, __struct, interface, __object)

  sealed trait Union extends UnionValue[foo.__Type.Union] { def pretty(indent: Int = 0): String } 
  object Union extends UnionMeta[foo.__Type.Union] {
    case class Unknown(discriminant: Short) extends foo.__Type.Union { def pretty(indent: Int): String = toString }
    case class void(value: Option[Unit]) extends foo.__Type.Union { def pretty(indent: Int): String = { println(value.toString); "" }}
    case class bool(value: Option[Unit]) extends foo.__Type.Union { def pretty(indent: Int): String = { println(value.toString); "" }}
    case class int8(value: Option[Unit]) extends foo.__Type.Union { def pretty(indent: Int): String = { println(value.toString); "" }}
    case class int16(value: Option[Unit]) extends foo.__Type.Union { def pretty(indent: Int): String = { println(value.toString); "" }}
    case class int32(value: Option[Unit]) extends foo.__Type.Union { def pretty(indent: Int): String = { println(value.toString); "" }}
    case class int64(value: Option[Unit]) extends foo.__Type.Union { def pretty(indent: Int): String = { println(value.toString); "" }}
    case class uint8(value: Option[Unit]) extends foo.__Type.Union { def pretty(indent: Int): String = { println(value.toString); "" }}
    case class uint16(value: Option[Unit]) extends foo.__Type.Union { def pretty(indent: Int): String = { println(value.toString); "" }}
    case class uint32(value: Option[Unit]) extends foo.__Type.Union { def pretty(indent: Int): String = { println(value.toString); "" }}
    case class uint64(value: Option[Unit]) extends foo.__Type.Union { def pretty(indent: Int): String = { println(value.toString); "" }}
    case class float32(value: Option[Unit]) extends foo.__Type.Union { def pretty(indent: Int): String = { println(value.toString); "" }}
    case class float64(value: Option[Unit]) extends foo.__Type.Union { def pretty(indent: Int): String = { println(value.toString); "" }}
    case class text(value: Option[Unit]) extends foo.__Type.Union { def pretty(indent: Int): String = { println(value.toString); "" }}
    case class data(value: Option[Unit]) extends foo.__Type.Union { def pretty(indent: Int): String = { println(value.toString); "" }}
    case class list(value: Option[foo.__Type.List]) extends foo.__Type.Union { def pretty(indent: Int): String = { value.map(fieldValue => {
print("(\n"); fieldValue.pretty(indent + 1); print("    " * indent + ")\n");}).getOrElse(println("None")); ""}}
    case class __enum(value: Option[foo.__Type.__Enum]) extends foo.__Type.Union { def pretty(indent: Int): String = { value.map(fieldValue => {
print("(\n"); fieldValue.pretty(indent + 1); print("    " * indent + ")\n");}).getOrElse(println("None")); ""}}
    case class __struct(value: Option[foo.__Type.__Struct]) extends foo.__Type.Union { def pretty(indent: Int): String = { value.map(fieldValue => {
print("(\n"); fieldValue.pretty(indent + 1); print("    " * indent + ")\n");}).getOrElse(println("None")); ""}}
    case class interface(value: Option[foo.__Type.Interface]) extends foo.__Type.Union { def pretty(indent: Int): String = { value.map(fieldValue => {
print("(\n"); fieldValue.pretty(indent + 1); print("    " * indent + ")\n");}).getOrElse(println("None")); ""}}
    case class __object(value: Option[Unit]) extends foo.__Type.Union { def pretty(indent: Int): String = { println(value.toString); "" }}
  }

  object List extends MetaStruct[List] {
    override type Self = List.type
    override def create(struct: CapnpStruct): List = new ListMutable(struct)
    override val recordName: String = "List"
    override val fields: Seq[FieldDescriptor[_, List, List.type]] = Seq(elementType)


    val elementType = new FieldDescriptor[foo.__Type, List, List.type](
      name = "elementType",
      meta = List
    )
  }

  trait List extends Struct[List] {
    override type MetaT = List.type

    def struct: CapnpStruct

    def elementType: Option[foo.__Type]
    override def toString: String = pretty(0)
    def pretty(indent: Int = 0): String = {
      println("    " * indent + "[" + meta.recordName + "]")
      elementType.foreach(fieldValue => {print("    " * indent + " elementType: "); print("\n"); fieldValue.pretty(indent + 1);})

      ""
    }
  }

  trait ListProxy extends List {
    def underlying: List

    override def struct: CapnpStruct = underlying.struct

    override def elementType: Option[foo.__Type]
  }

  class ListMutable(override val struct: CapnpStruct) extends List {
    override def meta: List.type = List

    override def elementType: Option[foo.__Type] = struct.getStruct(0).map(new foo.__TypeMutable(_))
  }
  object __Enum extends MetaStruct[__Enum] {
    override type Self = __Enum.type
    override def create(struct: CapnpStruct): __Enum = new __EnumMutable(struct)
    override val recordName: String = "__Enum"
    override val fields: Seq[FieldDescriptor[_, __Enum, __Enum.type]] = Seq(typeId)


    val typeId = new FieldDescriptor[java.lang.Long, __Enum, __Enum.type](
      name = "typeId",
      meta = __Enum
    )
  }

  trait __Enum extends Struct[__Enum] {
    override type MetaT = __Enum.type

    def struct: CapnpStruct

    def typeId: Option[java.lang.Long]
    override def toString: String = pretty(0)
    def pretty(indent: Int = 0): String = {
      println("    " * indent + "[" + meta.recordName + "]")
      typeId.foreach(fieldValue => {print("    " * indent + " typeId: "); println(fieldValue);})

      ""
    }
  }

  trait __EnumProxy extends __Enum {
    def underlying: __Enum

    override def struct: CapnpStruct = underlying.struct

    override def typeId: Option[java.lang.Long]
  }

  class __EnumMutable(override val struct: CapnpStruct) extends __Enum {
    override def meta: __Enum.type = __Enum

    override def typeId: Option[java.lang.Long] = struct.getLong(1)
  }
  object __Struct extends MetaStruct[__Struct] {
    override type Self = __Struct.type
    override def create(struct: CapnpStruct): __Struct = new __StructMutable(struct)
    override val recordName: String = "__Struct"
    override val fields: Seq[FieldDescriptor[_, __Struct, __Struct.type]] = Seq(typeId)


    val typeId = new FieldDescriptor[java.lang.Long, __Struct, __Struct.type](
      name = "typeId",
      meta = __Struct
    )
  }

  trait __Struct extends Struct[__Struct] {
    override type MetaT = __Struct.type

    def struct: CapnpStruct

    def typeId: Option[java.lang.Long]
    override def toString: String = pretty(0)
    def pretty(indent: Int = 0): String = {
      println("    " * indent + "[" + meta.recordName + "]")
      typeId.foreach(fieldValue => {print("    " * indent + " typeId: "); println(fieldValue);})

      ""
    }
  }

  trait __StructProxy extends __Struct {
    def underlying: __Struct

    override def struct: CapnpStruct = underlying.struct

    override def typeId: Option[java.lang.Long]
  }

  class __StructMutable(override val struct: CapnpStruct) extends __Struct {
    override def meta: __Struct.type = __Struct

    override def typeId: Option[java.lang.Long] = struct.getLong(1)
  }
  object Interface extends MetaStruct[Interface] {
    override type Self = Interface.type
    override def create(struct: CapnpStruct): Interface = new InterfaceMutable(struct)
    override val recordName: String = "Interface"
    override val fields: Seq[FieldDescriptor[_, Interface, Interface.type]] = Seq(typeId)


    val typeId = new FieldDescriptor[java.lang.Long, Interface, Interface.type](
      name = "typeId",
      meta = Interface
    )
  }

  trait Interface extends Struct[Interface] {
    override type MetaT = Interface.type

    def struct: CapnpStruct

    def typeId: Option[java.lang.Long]
    override def toString: String = pretty(0)
    def pretty(indent: Int = 0): String = {
      println("    " * indent + "[" + meta.recordName + "]")
      typeId.foreach(fieldValue => {print("    " * indent + " typeId: "); println(fieldValue);})

      ""
    }
  }

  trait InterfaceProxy extends Interface {
    def underlying: Interface

    override def struct: CapnpStruct = underlying.struct

    override def typeId: Option[java.lang.Long]
  }

  class InterfaceMutable(override val struct: CapnpStruct) extends Interface {
    override def meta: Interface.type = Interface

    override def typeId: Option[java.lang.Long] = struct.getLong(1)
  }

  val void = new FieldDescriptor[Unit, __Type, __Type.type](
    name = "void",
    meta = __Type
  )

  val bool = new FieldDescriptor[Unit, __Type, __Type.type](
    name = "bool",
    meta = __Type
  )

  val int8 = new FieldDescriptor[Unit, __Type, __Type.type](
    name = "int8",
    meta = __Type
  )

  val int16 = new FieldDescriptor[Unit, __Type, __Type.type](
    name = "int16",
    meta = __Type
  )

  val int32 = new FieldDescriptor[Unit, __Type, __Type.type](
    name = "int32",
    meta = __Type
  )

  val int64 = new FieldDescriptor[Unit, __Type, __Type.type](
    name = "int64",
    meta = __Type
  )

  val uint8 = new FieldDescriptor[Unit, __Type, __Type.type](
    name = "uint8",
    meta = __Type
  )

  val uint16 = new FieldDescriptor[Unit, __Type, __Type.type](
    name = "uint16",
    meta = __Type
  )

  val uint32 = new FieldDescriptor[Unit, __Type, __Type.type](
    name = "uint32",
    meta = __Type
  )

  val uint64 = new FieldDescriptor[Unit, __Type, __Type.type](
    name = "uint64",
    meta = __Type
  )

  val float32 = new FieldDescriptor[Unit, __Type, __Type.type](
    name = "float32",
    meta = __Type
  )

  val float64 = new FieldDescriptor[Unit, __Type, __Type.type](
    name = "float64",
    meta = __Type
  )

  val text = new FieldDescriptor[Unit, __Type, __Type.type](
    name = "text",
    meta = __Type
  )

  val data = new FieldDescriptor[Unit, __Type, __Type.type](
    name = "data",
    meta = __Type
  )

  val list = new FieldDescriptor[foo.__Type.List, __Type, __Type.type](
    name = "list",
    meta = __Type
  )

  val __enum = new FieldDescriptor[foo.__Type.__Enum, __Type, __Type.type](
    name = "enum",
    meta = __Type
  )

  val __struct = new FieldDescriptor[foo.__Type.__Struct, __Type, __Type.type](
    name = "struct",
    meta = __Type
  )

  val interface = new FieldDescriptor[foo.__Type.Interface, __Type, __Type.type](
    name = "interface",
    meta = __Type
  )

  val __object = new FieldDescriptor[Unit, __Type, __Type.type](
    name = "object",
    meta = __Type
  )
}

trait __Type extends Struct[__Type] with HasUnion[foo.__Type.Union] {
  override type MetaT = __Type.type

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
  def list: Option[foo.__Type.List]
  def __enum: Option[foo.__Type.__Enum]
  def __struct: Option[foo.__Type.__Struct]
  def interface: Option[foo.__Type.Interface]
  def __object: Option[Unit]
  override def toString: String = pretty(0)
  def pretty(indent: Int = 0): String = {
    println("    " * indent + "[" + meta.recordName + "]")
    print("    " * indent + " union: "); switch.pretty(indent)
    ""
  }
}

trait __TypeProxy extends __Type with HasUnion[foo.__Type.Union] {
  def underlying: __Type

  override def struct: CapnpStruct = underlying.struct

  override def switch: foo.__Type.Union = underlying.switch
  override def union: UnionMeta[foo.__Type.Union] = underlying.union

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
  override def list: Option[foo.__Type.List]
  override def __enum: Option[foo.__Type.__Enum]
  override def __struct: Option[foo.__Type.__Struct]
  override def interface: Option[foo.__Type.Interface]
  override def __object: Option[Unit]
}

class __TypeMutable(override val struct: CapnpStruct) extends __Type {
  override def meta: __Type.type = __Type

  override def discriminant: Short = (struct.getShort(0).getOrElse(new java.lang.Short(0.toShort)): java.lang.Short)
  override def switch: foo.__Type.Union = discriminant match {
    case 0 => foo.__Type.Union.void(void)
    case 1 => foo.__Type.Union.bool(bool)
    case 2 => foo.__Type.Union.int8(int8)
    case 3 => foo.__Type.Union.int16(int16)
    case 4 => foo.__Type.Union.int32(int32)
    case 5 => foo.__Type.Union.int64(int64)
    case 6 => foo.__Type.Union.uint8(uint8)
    case 7 => foo.__Type.Union.uint16(uint16)
    case 8 => foo.__Type.Union.uint32(uint32)
    case 9 => foo.__Type.Union.uint64(uint64)
    case 10 => foo.__Type.Union.float32(float32)
    case 11 => foo.__Type.Union.float64(float64)
    case 12 => foo.__Type.Union.text(text)
    case 13 => foo.__Type.Union.data(data)
    case 14 => foo.__Type.Union.list(list)
    case 15 => foo.__Type.Union.__enum(__enum)
    case 16 => foo.__Type.Union.__struct(__struct)
    case 17 => foo.__Type.Union.interface(interface)
    case 18 => foo.__Type.Union.__object(__object)
    case d => foo.__Type.Union.Unknown(d)
  }
  override def union: UnionMeta[foo.__Type.Union] = foo.__Type.Union

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
  override def list: Option[foo.__Type.List] = Some(new foo.__Type.ListMutable(struct))

  override def __enum: Option[foo.__Type.__Enum] = Some(new foo.__Type.__EnumMutable(struct))

  override def __struct: Option[foo.__Type.__Struct] = Some(new foo.__Type.__StructMutable(struct))

  override def interface: Option[foo.__Type.Interface] = Some(new foo.__Type.InterfaceMutable(struct))

  override def __object: Option[Unit] = struct.getNone()
}

object Value extends MetaStruct[Value] {
  override type Self = Value.type
  override def create(struct: CapnpStruct): Value = new ValueMutable(struct)
  override val recordName: String = "Value"
  override val fields: Seq[FieldDescriptor[_, Value, Value.type]] = Seq(void, bool, int8, int16, int32, int64, uint8, uint16, uint32, uint64, float32, float64, text, data, list, __enum, __struct, interface, __object)

  sealed trait Union extends UnionValue[foo.Value.Union] { def pretty(indent: Int = 0): String } 
  object Union extends UnionMeta[foo.Value.Union] {
    case class Unknown(discriminant: Short) extends foo.Value.Union { def pretty(indent: Int): String = toString }
    case class void(value: Option[Unit]) extends foo.Value.Union { def pretty(indent: Int): String = { println(value.toString); "" }}
    case class bool(value: Option[java.lang.Boolean]) extends foo.Value.Union { def pretty(indent: Int): String = { println(value.toString); "" }}
    case class int8(value: Option[java.lang.Byte]) extends foo.Value.Union { def pretty(indent: Int): String = { println(value.toString); "" }}
    case class int16(value: Option[java.lang.Short]) extends foo.Value.Union { def pretty(indent: Int): String = { println(value.toString); "" }}
    case class int32(value: Option[java.lang.Integer]) extends foo.Value.Union { def pretty(indent: Int): String = { println(value.toString); "" }}
    case class int64(value: Option[java.lang.Long]) extends foo.Value.Union { def pretty(indent: Int): String = { println(value.toString); "" }}
    case class uint8(value: Option[java.lang.Byte]) extends foo.Value.Union { def pretty(indent: Int): String = { println(value.toString); "" }}
    case class uint16(value: Option[java.lang.Short]) extends foo.Value.Union { def pretty(indent: Int): String = { println(value.toString); "" }}
    case class uint32(value: Option[java.lang.Integer]) extends foo.Value.Union { def pretty(indent: Int): String = { println(value.toString); "" }}
    case class uint64(value: Option[java.lang.Long]) extends foo.Value.Union { def pretty(indent: Int): String = { println(value.toString); "" }}
    case class float32(value: Option[java.lang.Double]) extends foo.Value.Union { def pretty(indent: Int): String = { println(value.toString); "" }}
    case class float64(value: Option[java.lang.Double]) extends foo.Value.Union { def pretty(indent: Int): String = { println(value.toString); "" }}
    case class text(value: Option[String]) extends foo.Value.Union { def pretty(indent: Int): String = { println(value.toString); "" }}
    case class data(value: Option[ByteBuffer]) extends foo.Value.Union { def pretty(indent: Int): String = { println(value.toString); "" }}
    case class list(value: Option[AnyRef]) extends foo.Value.Union { def pretty(indent: Int): String = { println(value.toString); "" }}
    case class __enum(value: Option[java.lang.Short]) extends foo.Value.Union { def pretty(indent: Int): String = { println(value.toString); "" }}
    case class __struct(value: Option[AnyRef]) extends foo.Value.Union { def pretty(indent: Int): String = { println(value.toString); "" }}
    case class interface(value: Option[Unit]) extends foo.Value.Union { def pretty(indent: Int): String = { println(value.toString); "" }}
    case class __object(value: Option[AnyRef]) extends foo.Value.Union { def pretty(indent: Int): String = { println(value.toString); "" }}
  }


  val void = new FieldDescriptor[Unit, Value, Value.type](
    name = "void",
    meta = Value
  )

  val bool = new FieldDescriptor[java.lang.Boolean, Value, Value.type](
    name = "bool",
    meta = Value
  )

  val int8 = new FieldDescriptor[java.lang.Byte, Value, Value.type](
    name = "int8",
    meta = Value
  )

  val int16 = new FieldDescriptor[java.lang.Short, Value, Value.type](
    name = "int16",
    meta = Value
  )

  val int32 = new FieldDescriptor[java.lang.Integer, Value, Value.type](
    name = "int32",
    meta = Value
  )

  val int64 = new FieldDescriptor[java.lang.Long, Value, Value.type](
    name = "int64",
    meta = Value
  )

  val uint8 = new FieldDescriptor[java.lang.Byte, Value, Value.type](
    name = "uint8",
    meta = Value
  )

  val uint16 = new FieldDescriptor[java.lang.Short, Value, Value.type](
    name = "uint16",
    meta = Value
  )

  val uint32 = new FieldDescriptor[java.lang.Integer, Value, Value.type](
    name = "uint32",
    meta = Value
  )

  val uint64 = new FieldDescriptor[java.lang.Long, Value, Value.type](
    name = "uint64",
    meta = Value
  )

  val float32 = new FieldDescriptor[java.lang.Double, Value, Value.type](
    name = "float32",
    meta = Value
  )

  val float64 = new FieldDescriptor[java.lang.Double, Value, Value.type](
    name = "float64",
    meta = Value
  )

  val text = new FieldDescriptor[String, Value, Value.type](
    name = "text",
    meta = Value
  )

  val data = new FieldDescriptor[ByteBuffer, Value, Value.type](
    name = "data",
    meta = Value
  )

  val list = new FieldDescriptor[AnyRef, Value, Value.type](
    name = "list",
    meta = Value
  )

  val __enum = new FieldDescriptor[java.lang.Short, Value, Value.type](
    name = "enum",
    meta = Value
  )

  val __struct = new FieldDescriptor[AnyRef, Value, Value.type](
    name = "struct",
    meta = Value
  )

  val interface = new FieldDescriptor[Unit, Value, Value.type](
    name = "interface",
    meta = Value
  )

  val __object = new FieldDescriptor[AnyRef, Value, Value.type](
    name = "object",
    meta = Value
  )
}

trait Value extends Struct[Value] with HasUnion[foo.Value.Union] {
  override type MetaT = Value.type

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
  def float32: Option[java.lang.Double]
  def float64: Option[java.lang.Double]
  def text: Option[String]
  def data: Option[ByteBuffer]
  def list: Option[AnyRef]
  def __enum: Option[java.lang.Short]
  def __struct: Option[AnyRef]
  def interface: Option[Unit]
  def __object: Option[AnyRef]
  override def toString: String = pretty(0)
  def pretty(indent: Int = 0): String = {
    println("    " * indent + "[" + meta.recordName + "]")
    print("    " * indent + " union: "); switch.pretty(indent)
    ""
  }
}

trait ValueProxy extends Value with HasUnion[foo.Value.Union] {
  def underlying: Value

  override def struct: CapnpStruct = underlying.struct

  override def switch: foo.Value.Union = underlying.switch
  override def union: UnionMeta[foo.Value.Union] = underlying.union

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
  override def float32: Option[java.lang.Double]
  override def float64: Option[java.lang.Double]
  override def text: Option[String]
  override def data: Option[ByteBuffer]
  override def list: Option[AnyRef]
  override def __enum: Option[java.lang.Short]
  override def __struct: Option[AnyRef]
  override def interface: Option[Unit]
  override def __object: Option[AnyRef]
}

class ValueMutable(override val struct: CapnpStruct) extends Value {
  override def meta: Value.type = Value

  override def discriminant: Short = (struct.getShort(0).getOrElse(new java.lang.Short(0.toShort)): java.lang.Short)
  override def switch: foo.Value.Union = discriminant match {
    case 0 => foo.Value.Union.void(void)
    case 1 => foo.Value.Union.bool(bool)
    case 2 => foo.Value.Union.int8(int8)
    case 3 => foo.Value.Union.int16(int16)
    case 4 => foo.Value.Union.int32(int32)
    case 5 => foo.Value.Union.int64(int64)
    case 6 => foo.Value.Union.uint8(uint8)
    case 7 => foo.Value.Union.uint16(uint16)
    case 8 => foo.Value.Union.uint32(uint32)
    case 9 => foo.Value.Union.uint64(uint64)
    case 10 => foo.Value.Union.float32(float32)
    case 11 => foo.Value.Union.float64(float64)
    case 12 => foo.Value.Union.text(text)
    case 13 => foo.Value.Union.data(data)
    case 14 => foo.Value.Union.list(list)
    case 15 => foo.Value.Union.__enum(__enum)
    case 16 => foo.Value.Union.__struct(__struct)
    case 17 => foo.Value.Union.interface(interface)
    case 18 => foo.Value.Union.__object(__object)
    case d => foo.Value.Union.Unknown(d)
  }
  override def union: UnionMeta[foo.Value.Union] = foo.Value.Union

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
  override def float32: Option[java.lang.Double] = struct.getDouble(1)
  override def float64: Option[java.lang.Double] = struct.getDouble(1)
  override def text: Option[String] = struct.getString(0)
  override def data: Option[ByteBuffer] = struct.getNone(0)
  override def list: Option[AnyRef] = struct.getNone(0)
  override def __enum: Option[java.lang.Short] = struct.getShort(1)
  override def __struct: Option[AnyRef] = struct.getNone(0)
  override def interface: Option[Unit] = struct.getNone()
  override def __object: Option[AnyRef] = struct.getNone(0)
}

object Annotation extends MetaStruct[Annotation] {
  override type Self = Annotation.type
  override def create(struct: CapnpStruct): Annotation = new AnnotationMutable(struct)
  override val recordName: String = "Annotation"
  override val fields: Seq[FieldDescriptor[_, Annotation, Annotation.type]] = Seq(id, value)


  val id = new FieldDescriptor[java.lang.Long, Annotation, Annotation.type](
    name = "id",
    meta = Annotation
  )

  val value = new FieldDescriptor[foo.Value, Annotation, Annotation.type](
    name = "value",
    meta = Annotation
  )
}

trait Annotation extends Struct[Annotation] {
  override type MetaT = Annotation.type

  def struct: CapnpStruct

  def id: Option[java.lang.Long]
  def value: Option[foo.Value]
  override def toString: String = pretty(0)
  def pretty(indent: Int = 0): String = {
    println("    " * indent + "[" + meta.recordName + "]")
    id.foreach(fieldValue => {print("    " * indent + " id: "); println(fieldValue);})
    value.foreach(fieldValue => {print("    " * indent + " value: "); print("\n"); fieldValue.pretty(indent + 1);})

    ""
  }
}

trait AnnotationProxy extends Annotation {
  def underlying: Annotation

  override def struct: CapnpStruct = underlying.struct

  override def id: Option[java.lang.Long]
  override def value: Option[foo.Value]
}

class AnnotationMutable(override val struct: CapnpStruct) extends Annotation {
  override def meta: Annotation.type = Annotation

  override def id: Option[java.lang.Long] = struct.getLong(0)
  override def value: Option[foo.Value] = struct.getStruct(0).map(new foo.ValueMutable(_))
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
  override def create(struct: CapnpStruct): CodeGeneratorRequest = new CodeGeneratorRequestMutable(struct)
  override val recordName: String = "CodeGeneratorRequest"
  override val fields: Seq[FieldDescriptor[_, CodeGeneratorRequest, CodeGeneratorRequest.type]] = Seq(nodes, requestedFiles)

  object RequestedFile extends MetaStruct[RequestedFile] {
    override type Self = RequestedFile.type
    override def create(struct: CapnpStruct): RequestedFile = new RequestedFileMutable(struct)
    override val recordName: String = "RequestedFile"
    override val fields: Seq[FieldDescriptor[_, RequestedFile, RequestedFile.type]] = Seq(id, filename, imports)

    object __Import extends MetaStruct[__Import] {
      override type Self = __Import.type
      override def create(struct: CapnpStruct): __Import = new __ImportMutable(struct)
      override val recordName: String = "Import"
      override val fields: Seq[FieldDescriptor[_, __Import, __Import.type]] = Seq(id, name)


      val id = new FieldDescriptor[java.lang.Long, __Import, __Import.type](
        name = "id",
        meta = __Import
      )

      val name = new FieldDescriptor[String, __Import, __Import.type](
        name = "name",
        meta = __Import
      )
    }

    trait __Import extends Struct[__Import] {
      override type MetaT = __Import.type

      def struct: CapnpStruct

      def id: Option[java.lang.Long]
      def name: Option[String]
      override def toString: String = pretty(0)
      def pretty(indent: Int = 0): String = {
        println("    " * indent + "[" + meta.recordName + "]")
        id.foreach(fieldValue => {print("    " * indent + " id: "); println(fieldValue);})
        name.foreach(fieldValue => {print("    " * indent + " name: "); println(fieldValue);})

        ""
      }
    }

    trait __ImportProxy extends __Import {
      def underlying: __Import

      override def struct: CapnpStruct = underlying.struct

      override def id: Option[java.lang.Long]
      override def name: Option[String]
    }

    class __ImportMutable(override val struct: CapnpStruct) extends __Import {
      override def meta: __Import.type = __Import

      override def id: Option[java.lang.Long] = struct.getLong(0)
      override def name: Option[String] = struct.getString(0)
    }

    val id = new FieldDescriptor[java.lang.Long, RequestedFile, RequestedFile.type](
      name = "id",
      meta = RequestedFile
    )

    val filename = new FieldDescriptor[String, RequestedFile, RequestedFile.type](
      name = "filename",
      meta = RequestedFile
    )

    val imports = new FieldDescriptor[Seq[foo.CodeGeneratorRequest.RequestedFile.__Import], RequestedFile, RequestedFile.type](
      name = "imports",
      meta = RequestedFile
    )
  }

  trait RequestedFile extends Struct[RequestedFile] {
    override type MetaT = RequestedFile.type

    def struct: CapnpStruct

    def id: Option[java.lang.Long]
    def filename: Option[String]
    def imports: Option[Seq[foo.CodeGeneratorRequest.RequestedFile.__Import]]
    override def toString: String = pretty(0)
    def pretty(indent: Int = 0): String = {
      println("    " * indent + "[" + meta.recordName + "]")
      id.foreach(fieldValue => {print("    " * indent + " id: "); println(fieldValue);})
      filename.foreach(fieldValue => {print("    " * indent + " filename: "); println(fieldValue);})
      imports.foreach(fieldValue => {print("    " * indent + " imports: "); print("[\n"); fieldValue.map(_.pretty(indent + 1)); print("    " * indent + " ]\n");})

      ""
    }
  }

  trait RequestedFileProxy extends RequestedFile {
    def underlying: RequestedFile

    override def struct: CapnpStruct = underlying.struct

    override def id: Option[java.lang.Long]
    override def filename: Option[String]
    override def imports: Option[Seq[foo.CodeGeneratorRequest.RequestedFile.__Import]]
  }

  class RequestedFileMutable(override val struct: CapnpStruct) extends RequestedFile {
    override def meta: RequestedFile.type = RequestedFile

    override def id: Option[java.lang.Long] = struct.getLong(0)
    override def filename: Option[String] = struct.getString(0)
    override def imports: Option[Seq[foo.CodeGeneratorRequest.RequestedFile.__Import]] = struct.getStructList(1).map(_.map(new foo.CodeGeneratorRequest.RequestedFile.__ImportMutable(_)))
  }

  val nodes = new FieldDescriptor[Seq[foo.Node], CodeGeneratorRequest, CodeGeneratorRequest.type](
    name = "nodes",
    meta = CodeGeneratorRequest
  )

  val requestedFiles = new FieldDescriptor[Seq[foo.CodeGeneratorRequest.RequestedFile], CodeGeneratorRequest, CodeGeneratorRequest.type](
    name = "requestedFiles",
    meta = CodeGeneratorRequest
  )
}

trait CodeGeneratorRequest extends Struct[CodeGeneratorRequest] {
  override type MetaT = CodeGeneratorRequest.type

  def struct: CapnpStruct

  def nodes: Option[Seq[foo.Node]]
  def requestedFiles: Option[Seq[foo.CodeGeneratorRequest.RequestedFile]]
  override def toString: String = pretty(0)
  def pretty(indent: Int = 0): String = {
    println("    " * indent + "[" + meta.recordName + "]")
    nodes.foreach(fieldValue => {print("    " * indent + " nodes: "); print("[\n"); fieldValue.map(_.pretty(indent + 1)); print("    " * indent + " ]\n");})
    requestedFiles.foreach(fieldValue => {print("    " * indent + " requestedFiles: "); print("[\n"); fieldValue.map(_.pretty(indent + 1)); print("    " * indent + " ]\n");})

    ""
  }
}

trait CodeGeneratorRequestProxy extends CodeGeneratorRequest {
  def underlying: CodeGeneratorRequest

  override def struct: CapnpStruct = underlying.struct

  override def nodes: Option[Seq[foo.Node]]
  override def requestedFiles: Option[Seq[foo.CodeGeneratorRequest.RequestedFile]]
}

class CodeGeneratorRequestMutable(override val struct: CapnpStruct) extends CodeGeneratorRequest {
  override def meta: CodeGeneratorRequest.type = CodeGeneratorRequest

  override def nodes: Option[Seq[foo.Node]] = struct.getStructList(0).map(_.map(new foo.NodeMutable(_)))
  override def requestedFiles: Option[Seq[foo.CodeGeneratorRequest.RequestedFile]] = struct.getStructList(1).map(_.map(new foo.CodeGeneratorRequest.RequestedFileMutable(_)))
}
