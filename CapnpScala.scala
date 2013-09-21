import com.codahale.jerkson.Json
import scala.io.Source

package raw {
  case class NestedNode(
    name: String,
    id: Long
  )

  case class CType(
    ignored: Option[Int]
  )

  case class Value(
    ignored: Option[Int]
  )

  case class FieldUnion0(
    ctype: CType,
    defaultValue: Value
  )

  case class FieldUnion1(
    ignored: Option[Int]
  )

  case class FieldUnion2(
    ignored: Option[Int]
  )

  case class Field(
    name: String,
    codeOrder: Option[Short],
    annotations: Option[Seq[Annotation]],
    slot: Option[FieldUnion0],
    group: Option[FieldUnion1],
    ordinal: Option[FieldUnion2]
  )

  case class Annotation(
    id: Long,
    value: Value
  )

  case class NodeUnion0(
    dataWordCount: Option[Short],
    pointerCount: Short,
    preferredListEncoding: String,
    fields: Seq[Field]
  )

  case class NodeUnion1(
    ctype: CType
  )

  case class Node(
    id: Long,
    displayName: String,
    displayNamePrefixLength: Option[Int],
    scopeId: Option[Long],
    nestedNodes: Option[Seq[NestedNode]],
    annotations: Option[Seq[Annotation]],
    struct: Option[NodeUnion0],
    annotation: Option[NodeUnion1]
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

object CapnpScala {

  lazy val rawJson = {
    val path = "/Users/dan/Dropbox/Projects/capnp/json/addressbook.capnp.json"
    val source = scala.io.Source.fromFile(path)
    val lines = source.mkString
    source.close
    lines.replaceAll(""""type":""", """"ctype":""")
  }

  def getSchemas(nodes: Seq[raw.Node]): Map[Long, raw.Node] = {
    nodes.map(node => (node.id, node)).toMap
  }

  def main(args: Array[String]): Unit = {
    val parsed = Json.parse[raw.CodeGeneratorRequest](rawJson)
    val schemasById = getSchemas(parsed.nodes)

    def genValue(ctype: raw.CType, value: raw.Value, scope: raw.Node): String = {
      "TODO(genValue)"
    }

    def nodeName(target: raw.Node, scope: raw.Node): String = {
      "TODO(nodeName)"
    }

    def genAnnotation(annotation: raw.Annotation, scope: raw.Node, prefix: String = " ", suffix: String = ""): String = {
      val decl = schemasById(annotation.id)
      val annDecl = decl.annotation.get
      val value = genValue(annDecl.ctype, annotation.value, decl)
      if (value.startsWith("(")) {
        prefix + "$" + nodeName(decl, scope) + value + suffix
      } else {
        prefix + "$" + nodeName(decl, scope) + "(" + value + ")" + suffix
      }
    }

    def genAnnotations(schema: raw.Node): String = {
      val scope = schemasById(schema.id)
      schema.annotations.flatten.map(genAnnotation(_, scope)).mkString
    }

    def genDecl(schema: raw.Node, name: String, scopeId: Long, indent: Int): String = {
      schema match {
        case _ if schema.struct.isDefined => {
          val struct = schema.struct.get
          val enc = if (struct.preferredListEncoding == "inlineComposite") { "" } else { "TODO(inlineComposite)" }
          indent + "struct " + name +
          "@0x" + schema.id + genAnnotations(schema) + " {  # " +
          struct.dataWordCount.getOrElse(0.toShort) * 8 + " bytes, " +
          struct.pointerCount + " ptrs" +
          enc +
          // genStructFields(struct, indent + 2) +
          genNestedDecls(schema, indent + 2) +
          indent + "}\n"
        }
      }
    }

    def genNestedDecls(schema: raw.Node, indent: Int): String = {
      val id = schema.id;
      schema.nestedNodes.flatten.map(nested => {
        genDecl(schemasById(nested.id), nested.name, id, indent)
      }).mkString("\n")
    }

    def genFile(file: raw.Node): String = {
      "# %s\n@0x%s;\n".format(file.displayName, file.id.toString) +
      file.annotations.flatten.map(genAnnotation(_, file, "", ";\n")).mkString +
      genNestedDecls(file, 0)
    }

    parsed.requestedFiles.map(requestedFile => {
      println(genFile(schemasById(requestedFile.id)))
    })
  }
}
