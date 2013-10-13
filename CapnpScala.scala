import com.codahale.jerkson.Json

import java.io.FileWriter
import scala.io.Source

object CapnpScala {

  def rewriteJson(raw: String): String = {
    raw
      .replaceAll(""""type"\s*:""", """"ctype":""")
      .replaceAll(""""object"\s*:""", """"cobject":""")
      .replaceAll(""""implicit"\s*:""", """"cimplicit":""")
      .replaceAll(""":\s*null""", """: {}""")
  }

  val AddressJsonPath = "schema.capnp.json"
  val AddressJsonOutputPath = AddressJsonPath.replace(".json", ".scala.bak")
  lazy val rawJson = {
    val source = Source.fromFile(AddressJsonPath)
    val raw = source.mkString
    source.close
    rewriteJson(raw)
  }

  def getSchemas(nodes: Seq[raw.Node]): Map[Long, raw.Node] = {
    nodes.map(node => (node.id, node)).toMap
  }

  def main(args: Array[String]): Unit = {
    val parsed = Json.parse[raw.CodeGeneratorRequest](rawJson)
    val schemasById = getSchemas(parsed.nodes)

    def getDependency(scope: raw.Node, id: Long): raw.Node = {
      schemasById.get(id).get
    }

    def genType(ctype: raw.CType, scope: raw.Node): StringTree = {
      StringTree(ctype match {
        case _ if ctype.void.isDefined => "Unit"
        case _ if ctype.bool.isDefined => "java.lang.Boolean"
        case _ if ctype.int8.isDefined => "java.lang.Byte"
        case _ if ctype.int16.isDefined => "java.lang.Short"
        case _ if ctype.int32.isDefined => "java.lang.Integer"
        case _ if ctype.int64.isDefined => "java.lang.Long"
        case _ if ctype.uint8.isDefined => "java.lang.Byte"
        case _ if ctype.uint16.isDefined => "java.lang.Short"
        case _ if ctype.uint32.isDefined => "java.lang.Integer"
        case _ if ctype.uint64.isDefined => "java.lang.Long"
        case _ if ctype.float32.isDefined => "java.lang.Double"
        case _ if ctype.float64.isDefined => "java.lang.Double"
        case _ if ctype.text.isDefined => "String"
        case _ if ctype.data.isDefined => "ByteBuffer"
        case _ if ctype.list.isDefined => StringTree("Seq[", genType(ctype.list.get.elementType, scope), "]")
        case _ if ctype.enum.isDefined => nodeName(getDependency(scope, ctype.enum.get.typeId))
        case _ if ctype.struct.isDefined => nodeName(getDependency(scope, ctype.struct.get.typeId))
        case _ if ctype.interface.isDefined => nodeName(getDependency(scope, ctype.interface.get.typeId))
        case _ if ctype.cobject.isDefined => "AnyRef"
        case _ => throw new IllegalArgumentException("Unknown ctype: " + ctype)
      })
    }

    def genAccessor(ctype: raw.CType, scope: raw.Node, offset: Int): StringTree = {
      ctype match {
        case _ if ctype.void.isDefined => StringTree("getNone()")
        case _ if ctype.bool.isDefined => StringTree("getBoolean(", offset, ")")
        case _ if ctype.int8.isDefined => StringTree("getByte(", offset, ")")
        case _ if ctype.int16.isDefined => StringTree("getShort(", offset, ")")
        case _ if ctype.int32.isDefined => StringTree("getInt(", offset, ")")
        case _ if ctype.int64.isDefined => StringTree("getLong(", offset, ")")
        case _ if ctype.uint8.isDefined => StringTree("getByte(", offset, ")")
        case _ if ctype.uint16.isDefined => StringTree("getShort(", offset, ")")
        case _ if ctype.uint32.isDefined => StringTree("getInt(", offset, ")")
        case _ if ctype.uint64.isDefined => StringTree("getLong(", offset, ")")
        case _ if ctype.float32.isDefined => StringTree("getDouble(", offset, ")")
        case _ if ctype.float64.isDefined => StringTree("getDouble(", offset, ")")
        case _ if ctype.text.isDefined => StringTree("getString(", offset, ")")
        case _ if ctype.data.isDefined => StringTree("getNone(", offset, ")")
        case _ if ctype.list.isDefined => {
          StringTree("getStructList(", offset, ").map(_.map(new ", genType(ctype.list.get.elementType, scope), "Mutable(_)))")
        }
        case _ if ctype.enum.isDefined => {
          StringTree("getShort(", offset, ").map(id => ", nodeName(getDependency(scope, ctype.enum.get.typeId)), ".findById(id.toInt).getOrElse(", nodeName(getDependency(scope, ctype.enum.get.typeId)), ".Unknown(id.toShort)))")
        }
        case _ if ctype.struct.isDefined => StringTree("getNone(", offset, ")")
        case _ if ctype.interface.isDefined => StringTree("getNone(", offset, ")")
        case _ if ctype.cobject.isDefined => StringTree("getNone(", offset, ")")
        case _ => throw new IllegalArgumentException("Unknown ctype: " + ctype)
      }
    }

    def genValue(ctype: raw.CType, value: raw.Value, scope: raw.Node): StringTree = {
      ctype match {
        case _ if ctype.void.isDefined => StringTree("void")
        case _ if ctype.bool.isDefined => StringTree(value.bool.get)
        case _ if ctype.int8.isDefined => StringTree(value.int8.get)
        case _ if ctype.int16.isDefined => StringTree(value.int16.get)
        case _ if ctype.int32.isDefined => StringTree(value.int32.get)
        case _ if ctype.int64.isDefined => StringTree(value.int64.get)
        case _ if ctype.uint8.isDefined => StringTree(value.uint8.get)
        case _ if ctype.uint16.isDefined => StringTree(value.uint16.get)
        case _ if ctype.uint32.isDefined => StringTree(value.uint32.get)
        case _ if ctype.uint64.isDefined => StringTree(value.uint64.get)
        case _ if ctype.float32.isDefined => StringTree(value.float32.get)
        case _ if ctype.float64.isDefined => StringTree(value.float64.get)
        case _ if ctype.text.isDefined => StringTree("\"", value.text.get, "\"")
        case _ if ctype.data.isDefined => StringTree("\"", value.data.get, "\"")
        case _ if ctype.list.isDefined => StringTree("TODO(ctype.list.isDefined)")
        case _ if ctype.enum.isDefined => StringTree(ctype.enum.get)
        case _ if ctype.struct.isDefined => StringTree("TODO(ctype.struct.isDefined)")
        case _ if ctype.interface.isDefined => StringTree("")
        case _ if ctype.cobject.isDefined => StringTree("")
        case _ => throw new IllegalArgumentException("Unknown ctype: " + ctype)
      }
    }

    def getUnqualifiedName(schema: raw.Node): String = {
      val parent = schemasById.get(schema.scopeId.get).get
      val nodeNameOpt = parent.nestedNodes.flatten.find(_.id == schema.id).map(_.name)
      nodeNameOpt.orElse({
        parent.struct.flatMap(_.fields.find(_.group.exists(_.typeId == schema.id))).map(f => scalaCaseName(f.name))
      }).map(scalaEscapeName(_)).getOrElse("?")
    }

    def getScalaPackageName: String = {
      return "foo"
    }

    def nodeName(target: raw.Node): StringTree = {
      var targetParents = {
        val builder = Vector.newBuilder[raw.Node]
        var parent = target
        while (parent.scopeId.exists(_ != 0)) {
          parent = schemasById.get(parent.scopeId.get).get
          builder += parent
        }
        builder.result
      }

      var path = StringTree()
      while (targetParents.nonEmpty) {
        if (targetParents.last.scopeId.isEmpty || targetParents.last.scopeId.get == 0) {
          // No-op.
        } else {
          path = StringTree(path, getUnqualifiedName(targetParents.last), ".")
        }
        targetParents = targetParents.dropRight(1)
      }

      StringTree(getScalaPackageName, ".", path, getUnqualifiedName(target))
    }

    def fieldScalaType(field: raw.Field, scope: raw.Node): StringTree = {
      field match {
        case _ if field.slot.isDefined => genType(field.slot.get.ctype, scope)
        case _ if field.group.isDefined => {
          var parent = schemasById.get(getDependency(scope, field.group.get.typeId).scopeId.get).get
          var path = nodeName(parent)
          StringTree(path, ".", scalaEscapeName(scalaCaseName(field.name)))
        }
        case _ => throw new IllegalArgumentException("Unknown field: " + field)
      }
    }

    def scalaCaseName(name: String): String = {
      if (name.startsWith("__")) {
        "__" + scalaCaseName(name.drop(2))
      } else {
        name.take(1).toUpperCase ++ name.drop(1)
      }
    }

    def scalaEscapeName(name: String): String = {
      if (CodegenUtil.ReservedWords.contains(name.toLowerCase)) "__" + name else name
    }

    def genGroupDecl(schema: raw.Node, name: String, scopeId: Long, indent: StringTreeIndent): StringTree = {
      genDecl(schema, scalaEscapeName(scalaCaseName(name)), scopeId, indent)
    }

    def genDecl(schema: raw.Node, nameRaw: String, scopeId: Long, indent: StringTreeIndent): StringTree = {
      val name = scalaEscapeName(nameRaw)
      schema match {
        case _ if schema.struct.isDefined => {
          val struct = schema.struct.get
          val fields = struct.fields.sortBy(_.codeOrder)
          val unionFields = fields.filter(_.discriminantValue.isDefined)
          val groupFields = fields.filter(_.group.isDefined)
          StringTree(
            indent, "object ", name, " extends MetaStruct[", name, "] {\n",
            indent.next, "override type Self = ", name, ".type\n",
            indent.next, "override val recordName: String = \"", nameRaw, "\"\n",
            indent.next, "override val fields: Seq[FieldDescriptor[_, ", name, ", ", name, ".type]] = Seq(",
            StringTree.join(", ", fields.map(field => StringTree(scalaEscapeName(field.name)))),
            ")\n",
            "\n",
            genNestedDecls(schema, indent.next),
            if (unionFields.isEmpty) StringTree() else StringTree(
              indent.next, "sealed trait Union extends UnionValue[", nodeName(schema), ".Union]\n",
              indent.next, "object Union extends UnionMeta[", nodeName(schema), ".Union] {\n",
              indent.next.next, "case class Unknown(discriminant: Short) extends ", nodeName(schema), ".Union\n",
              unionFields.map(unionField => StringTree(
                indent.next.next, "case class ", scalaEscapeName(unionField.name), "(value: Option[", fieldScalaType(unionField, schema), "]) extends ", nodeName(schema), ".Union\n"
              )),
              indent.next, "}\n\n"
            ),
            groupFields.map(groupField => {
              val groupNode = getDependency(schema, groupField.group.get.typeId)
              genGroupDecl(groupNode, groupField.name, groupNode.scopeId.get, indent.next)
            }),
            // indent.next, "def apply(",
            // StringTree.join(", ", fields.map(field => StringTree(scalaEscapeName(field.name), ": ", fieldScalaType(field, schema)))),
            // "): ", name, " = {\n",
            // indent.next.next, "new ", name, "Mutable(", StringTree.join(", ", fields.map(f => StringTree(scalaEscapeName(f.name)))), ")\n",
            // indent.next, "}\n",
            "\n",
            StringTree.join("\n", fields.map(field => StringTree(
              indent.next, "val ", scalaEscapeName(field.name), " = new FieldDescriptor[", fieldScalaType(field, schema), ", ", name, ", ", name, ".type](\n",
              indent.next.next, "name = \"", field.name, "\",\n",
              indent.next.next, "meta = ", name, "\n",
              indent.next, ")\n"
            ))),
            indent, "}\n\n",


            indent, "trait ", name, " extends Struct[", name, "]",
            if (unionFields.isEmpty) StringTree() else StringTree(" with HasUnion[", nodeName(schema), ".Union]"),
            " {\n",
            indent.next, "override type MetaT = ", name, ".type\n\n",
            indent.next, "def struct: CapnpStruct\n\n",
            fields.map(field => StringTree(
              indent.next, "def ", scalaEscapeName(field.name), ": Option[", fieldScalaType(field, schema), "]\n"
            )),
            // indent.next, "def copy(",
            // StringTree.join(", ", fields.map(field => StringTree(scalaEscapeName(field.name), ": ", fieldScalaType(field, schema), " = this.", scalaEscapeName(field.name), ".getOrElse(null)")))
            // , "): ", name, "\n",
            indent, "}\n\n",


            indent, "trait ", name, "Proxy extends ", name,
            if (unionFields.isEmpty) StringTree() else StringTree(" with HasUnion[", nodeName(schema), ".Union]"),
            " {\n",
            indent.next, "def underlying: ", name, "\n\n",
            indent.next, "override def struct: CapnpStruct = underlying.struct\n\n",
            if (unionFields.isEmpty) StringTree() else StringTree(
              indent.next, "override def switch: ", nodeName(schema), ".Union = underlying.switch\n",
              indent.next, "override def union: UnionMeta[", nodeName(schema), ".Union] = underlying.union\n",
              "\n"
            ),
            fields.map(field => StringTree(
              indent.next, "override def ", scalaEscapeName(field.name), ": Option[", fieldScalaType(field, schema), "]\n"
            )),
            // indent.next, "override def copy(",
            // StringTree.join(", ", fields.map(field => StringTree(scalaEscapeName(field.name), ": ", fieldScalaType(field, schema))))
            // , "): ", name, " = {\n",
            // indent.next.next, "underlying.copy(", StringTree.join(", ", fields.map(f => StringTree(scalaEscapeName(f.name)))), ")\n",
            // indent.next, "}\n",
            indent, "}\n\n",


            indent, "class ", name, "Mutable(override val struct: CapnpStruct) extends ", name, " {\n",
            indent.next, "override def meta: ", name, ".type = ", name, "\n",
            "\n",
            if (unionFields.isEmpty) StringTree() else StringTree(
              indent.next, "override def discriminant: Short = (struct.getShort(", struct.discriminantOffset.getOrElse(0).toString, ").getOrElse(new java.lang.Short(0.toShort)): java.lang.Short)\n",
              indent.next, "override def switch: ", nodeName(schema), ".Union = discriminant match {\n",
              unionFields.zipWithIndex.map({ case (unionField, i) => StringTree(
                indent.next.next, "case ", i.toString, " => ", nodeName(schema), ".Union.", scalaEscapeName(unionField.name), "(", scalaEscapeName(unionField.name), ")\n"
              )}),
              indent.next.next, "case d => ", nodeName(schema), ".Union.Unknown(d)\n",
              indent.next, "}\n",
              indent.next, "override def union: UnionMeta[", nodeName(schema), ".Union] = ", nodeName(schema), ".Union\n",
              "\n"
            ),
            StringTree.join("\n", fields.map(field => StringTree(
              indent.next, "override def ", scalaEscapeName(field.name), ": Option[", fieldScalaType(field, schema), "] = ",
              field match {
                case _ if field.slot.isDefined => StringTree("struct.", genAccessor(field.slot.get.ctype, schema, field.slot.get.offset.getOrElse(0)))
                case _ if field.group.isDefined => {
                  StringTree("Some(new ", nodeName(schema), ".", scalaEscapeName(scalaCaseName(field.name)), "Mutable(struct))\n")
                }
              }
            ))), "\n",
            // indent.next, "override def copy(",
            // StringTree.join(", ", fields.map(field => StringTree(scalaEscapeName(field.name), ": ", fieldScalaType(field, schema))))
            // , "): ", name, " = {\n",
            // indent.next.next, "new ", name, "Mutable(", StringTree.join(", ", fields.map(f => StringTree(scalaEscapeName(f.name)))), ")\n",
            // indent.next, "}\n",
            indent, "}\n"
          )
        }
        case _ if schema.enum.isDefined => {
          val enum = schema.enum.get
          StringTree(
            indent, "object ", name, " extends EnumMeta[", name, "] {\n",
            genNestedDecls(schema, indent.next),
            indent.next, "case class Unknown(override val id: Int) extends ", name, "(", name, ", id, null, null)\n\n",
            StringTree.join("\n", enum.enumerants.sortBy(_.codeOrder).zipWithIndex.map({ case (e, i) => StringTree(
              indent.next, "val ", e.name, " = new ", name, "(this, ", i.toString, ", \"", e.name, "\", \"", e.name, "\")"
            )})), "\n\n",
            indent.next, "override val values = Vector(\n",
            StringTree.join(",\n", enum.enumerants.sortBy(_.codeOrder).map(e => StringTree(indent.next.next, e.name))), "\n",
            indent.next, ")\n\n",
            indent.next, "override def findByIdOrNull(id: Int): ", name, " = values.lift(id).getOrElse(null)\n",
            indent.next, "override def findByNameOrNull(name: String): ", name, " = null\n",
            indent.next, "override def findByStringValueOrNull(v: String): ", name, " = null\n",
            indent, "}\n\n",
            indent, "sealed class ", name, "(\n",
            indent.next, "override val meta: EnumMeta[", name, "],\n",
            indent.next, "override val id: Int,\n",
            indent.next, "override val name: String,\n",
            indent.next, "override val stringValue: String\n",
            indent, ") extends Enum[", name, "]\n"
          )
        }
        case _ => throw new IllegalArgumentException("Unknown schema: " + schema)
      }
    }

    def genNestedDecls(schema: raw.Node, indent: StringTreeIndent): StringTree = {
      val id = schema.id;
      StringTree.join("\n", schema.nestedNodes.flatten.map(nested => {
        genDecl(schemasById(nested.id), nested.name, id, indent)
      }).toSeq)
    }

    def genFile(file: raw.Node): StringTree = {
      StringTree(
        "// ", file.displayName, "\n\n",
        "package ", getScalaPackageName, "\n\n",
        "import com.foursquare.spindle.{Enum, EnumMeta}\n",
        "import capnp.{HasUnion, UnionMeta, UnionValue, UntypedFieldDescriptor, FieldDescriptor, UntypedStruct, Struct, UntypedMetaStruct, MetaStruct}\n",
        "import capnp.{CapnpStruct, Pointer => CapnpPointer, CapnpList, CapnpTag}\n",
        "import java.nio.ByteBuffer\n",
        "\n",
        genNestedDecls(file, new StringTreeIndent(0, "  "))
      )
    }

    parsed.requestedFiles.map(requestedFile => {
      val output = genFile(schemasById(requestedFile.id)).flatten
      val source = new FileWriter(AddressJsonOutputPath)
      source.write(output)
      source.close
    })
  }
}
