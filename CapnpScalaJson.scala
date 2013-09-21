package raw {
  case class Void(
    ignored: Option[Any]
  )

  case class NestedNode(
    name: String,
    id: Long
  )

  case class CType_List(
    elementType: CType
  )

  case class CType_Enum(
    typeId: Long
  )

  case class CType_Struct(
    typeId: Long
  )

  case class CType_Interface(
    typeId: Long
  )

  case class CType(
    void: Option[Void],
    bool: Option[Void],
    int8: Option[Void],
    int16: Option[Void],
    int32: Option[Void],
    int64: Option[Void],
    uint8: Option[Void],
    uint16: Option[Void],
    uint32: Option[Void],
    uint64: Option[Void],
    float32: Option[Void],
    float64: Option[Void],
    text: Option[Void],
    data: Option[Void],
    list: Option[CType_List],
    enum: Option[CType_Enum],
    struct: Option[CType_Struct],
    interface: Option[CType_Interface],
    cobject: Option[Void]
  )

  case class Value(
    void: Option[Void],
    bool: Option[Boolean],
    int8: Option[Short],
    int16: Option[Short],
    int32: Option[Int],
    int64: Option[Long],
    uint8: Option[Short],
    uint16: Option[Short],
    uint32: Option[Int],
    uint64: Option[Long],
    float32: Option[Double],
    float64: Option[Double],
    text: Option[String],
    data: Option[String],
    list: Option[Object],
    enum: Option[Short],
    struct: Option[Object],
    interface: Option[Void],
    cobject: Option[Object]
  )

  case class FieldUnion_Slot(
    offset: Option[Int],
    ctype: CType,
    defaultValue: Value
  )

  case class FieldUnion_Group(
    typeId: Long
  )

  case class FieldUnion_Ordinal(
    cimplicit: Option[Void],
    explicit: Option[Short]
  )

  case class Field(
    name: String,
    codeOrder: Option[Short],
    annotations: Option[Seq[Annotation]],
    discriminantValue: Option[Short],
    slot: Option[FieldUnion_Slot],
    group: Option[FieldUnion_Group],
    ordinal: Option[FieldUnion_Ordinal]
  )

  case class Enumerant(
    name: String,
    codeOrder: Option[Short],
    annotations: Option[Seq[Annotation]]
  )

  case class Method_Param(
    name: String,
    ctype: CType,
    defaultValue: Value,
    annotations: Option[Seq[Annotation]]
  )

  case class Method(
    name: String,
    codeOrder: Option[Short],
    params: Seq[Method_Param],
    requiredParamCount: Short,
    returnType: CType,
    annotations: Option[Seq[Annotation]]
  )

  case class Annotation(
    id: Long,
    value: Value
  )

  case class NodeUnion_File(
    ignored: Option[Any]
  )

  case class NodeUnion_Struct(
    dataWordCount: Option[Short],
    pointerCount: Short,
    preferredListEncoding: String,
    isGroup: Option[Boolean],
    discriminantCount: Option[Short],
    discriminantOffset: Option[Int],
    fields: Seq[Field]
  )

  case class NodeUnion_Enum(
    enumerants: Seq[Enumerant]
  )

  case class NodeUnion_Interface(
    methods: Seq[Method]
  )

  case class NodeUnion_Const(
    ctype: CType,
    value: Value
  )

  case class NodeUnion_Annotation(
    ctype: CType,
    targetsFile: Option[Boolean],
    targetsConst: Option[Boolean],
    targetsEnum: Option[Boolean],
    targetsEnumerant: Option[Boolean],
    targetsStruct: Option[Boolean],
    targetsField: Option[Boolean],
    targetsUnion: Option[Boolean],
    targetsGroup: Option[Boolean],
    targetsInterface: Option[Boolean],
    targetsMethod: Option[Boolean],
    targetsParam: Option[Boolean],
    targetsAnnotation: Option[Boolean]
  )

  case class Node(
    id: Long,
    displayName: String,
    displayNamePrefixLength: Option[Int],
    scopeId: Option[Long],
    nestedNodes: Option[Seq[NestedNode]],
    annotations: Option[Seq[Annotation]],
    file: Option[NodeUnion_File],
    struct: Option[NodeUnion_Struct],
    enum: Option[NodeUnion_Enum],
    interface: Option[NodeUnion_Interface],
    const: Option[NodeUnion_Const],
    annotation: Option[NodeUnion_Annotation]
  )

  case class Import(
    id: Long,
    name: String
  )

  case class RequestedFile(
    id: Long,
    filename: String,
    imports: Seq[Import]
  )

  case class CodeGeneratorRequest(
    nodes: Seq[Node],
    requestedFiles: Seq[RequestedFile]
  )
}