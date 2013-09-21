import com.codahale.jerkson.Json
import scala.io.Source

object CapnpScala {

  lazy val rawJson = {
    val path = "addressbook.capnp.json"
    val source = scala.io.Source.fromFile(path)
    val lines = source.mkString
    source.close
    lines
      .replaceAll(""""type":""", """"ctype":""")
      .replaceAll(""""object":""", """"cobject":""")
      .replaceAll(""""implicit":""", """"cimplicit":""")
      .replaceAll(""": null""", """: {}""")
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
        case _ if ctype.void.isDefined => "Void"
        case _ if ctype.bool.isDefined => "Bool"
        case _ if ctype.int8.isDefined => "Int8"
        case _ if ctype.int16.isDefined => "Int16"
        case _ if ctype.int32.isDefined => "Int32"
        case _ if ctype.int64.isDefined => "Int64"
        case _ if ctype.uint8.isDefined => "UInt8"
        case _ if ctype.uint16.isDefined => "UInt16"
        case _ if ctype.uint32.isDefined => "UInt32"
        case _ if ctype.uint64.isDefined => "UInt64"
        case _ if ctype.float32.isDefined => "Float32"
        case _ if ctype.float64.isDefined => "Float64"
        case _ if ctype.text.isDefined => "Text"
        case _ if ctype.data.isDefined => "Data"
        case _ if ctype.list.isDefined => StringTree("List(", genType(ctype.list.get.elementType, scope), ")")
        case _ if ctype.enum.isDefined => nodeName(getDependency(scope, ctype.enum.get.typeId), scope)
        case _ if ctype.struct.isDefined => nodeName(getDependency(scope, ctype.struct.get.typeId), scope)
        case _ if ctype.interface.isDefined => nodeName(getDependency(scope, ctype.interface.get.typeId), scope)
        case _ if ctype.cobject.isDefined => "Object"
        case _ => throw new IllegalArgumentException("Unknown ctype: " + ctype)
      })
    }

    def typeSizeBits(ctype: raw.CType): Int = {
      ctype match {
        case _ if ctype.void.isDefined => 0
        case _ if ctype.bool.isDefined => 1
        case _ if ctype.int8.isDefined => 8
        case _ if ctype.int16.isDefined => 16
        case _ if ctype.int32.isDefined => 32
        case _ if ctype.int64.isDefined => 64
        case _ if ctype.uint8.isDefined => 8
        case _ if ctype.uint16.isDefined => 16
        case _ if ctype.uint32.isDefined => 32
        case _ if ctype.uint64.isDefined => 64
        case _ if ctype.float32.isDefined => 32
        case _ if ctype.float64.isDefined => 64
        case _ if ctype.text.isDefined => -1
        case _ if ctype.data.isDefined => -1
        case _ if ctype.list.isDefined => -1
        case _ if ctype.enum.isDefined => 16
        case _ if ctype.struct.isDefined => -1
        case _ if ctype.interface.isDefined => -1
        case _ if ctype.cobject.isDefined => -1
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
      parent.nestedNodes.flatten.find(_.id == schema.id).map(_.name).getOrElse("(?)")
    }

    // TODO(dan): This is super gross. Fix it with folds.
    def nodeName(target: raw.Node, scope: raw.Node): StringTree = {
      var targetParents = {
        val builder = Vector.newBuilder[raw.Node]
        var parent = target
        while (parent.scopeId.exists(_ != 0)) {
          parent = schemasById.get(parent.scopeId.get).get
          builder += parent
        }
        builder.result
      }

      var scopeParts = {
        val builder = Vector.newBuilder[raw.Node]
        var parent = scope
        builder += parent
        while (parent.scopeId.exists(_ != 0)) {
          parent = schemasById.get(parent.scopeId.get).get
          builder += parent
        }
        builder.result
      }

      while (targetParents.nonEmpty && scopeParts.nonEmpty
          && targetParents.last == scopeParts.last) {
        targetParents = targetParents.dropRight(1)
        scopeParts = scopeParts.dropRight(1)
      }


      var path = StringTree()
      while (targetParents.nonEmpty) {
        if (targetParents.last.scopeId.isEmpty || targetParents.last.scopeId.get == 0) {
          path = StringTree(path, "import \"/", targetParents.last.displayName, "\".")
        } else {
          path = StringTree(path, getUnqualifiedName(targetParents.last), ".")
        }
        targetParents = targetParents.dropRight(1)
      }

      StringTree(path, getUnqualifiedName(target))
    }

    def genAnnotation(annotation: raw.Annotation, scope: raw.Node, prefix: String = " ", suffix: String = ""): StringTree = {
      val decl = schemasById(annotation.id)
      val annDecl = decl.annotation.get
      val value = genValue(annDecl.ctype, annotation.value, decl)
      if (value.flatten.startsWith("(")) {
        StringTree(prefix, "$", nodeName(decl, scope), value, suffix)
      } else {
        StringTree(prefix, "$", nodeName(decl, scope), "(", value, ")", suffix)
      }
    }

    def genAnnotations(annotations: Option[Seq[raw.Annotation]], scope: raw.Node): StringTree = {
      StringTree(annotations.flatten.map(genAnnotation(_, scope)))
    }

    def genAnnotationsSchema(schema: raw.Node): StringTree = {
      val scope = schemasById(schema.id)
      genAnnotations(schema.annotations, scope)
    }

    def elementSizeName(elementSize: String): String = {
      elementSize match {
        case "empty" => "void"
        case "bit" => "1-bit"
        case "byte" => "8-bit"
        case "twoBytes" => "16-bit"
        case "fourBytes" => "32-bit"
        case "eightBytes" => "64-bit"
        case "pointer" => "pointer"
        case "inlineComposite" => "void"
        case _ => ""
      }
    }

    def genStructFields(schema: raw.NodeUnion_Struct, scope: raw.Node, indent: StringTreeIndent): Seq[StringTree] = {
      var seenUnion: Boolean = false
      schema.fields.sortBy(_.codeOrder).map(field => {
        if (field.discriminantValue.isDefined) {
          if (seenUnion) {
            StringTree()
          } else {
            seenUnion = true
            val offset = scope.struct.get.discriminantOffset.get
            val unionFields = schema.fields.filter(_.discriminantValue.isDefined)
            StringTree(
              indent, "union {  # tag bits [", offset * 16, ", ", offset * 16 + 16, ")\n",
              unionFields.sortBy(_.codeOrder).map(u => genStructField(u, scope, indent.next)),
              indent, "}\n"
            )
          }
        } else {
          genStructField(field, scope, indent)
        }
      })
    }

    def genStructField(field: raw.Field, scope: raw.Node, indent: StringTreeIndent): StringTree = {
      field match {
        case _ if field.slot.isDefined => {
          val slot = field.slot.get
          val offset = slot.offset.getOrElse(0)
          val size = typeSizeBits(slot.ctype)
          StringTree(
            indent, field.name, " @", field.ordinal.get.explicit,
            " :", genType(slot.ctype, scope),
            // if (isEmptyValue(slot.defaultValue)) {
            //   StringTree()
            // } else {
            //   StringTree(" = ", genValue(slot.type, slot.defaultValue))
            // },
            genAnnotations(field.annotations, scope),
            ";  # ",
            if (size == -1) {
              StringTree("ptr[", offset, "]")
            } else {
              StringTree("bits[", offset * size, ", ", (offset + 1) * size, ")")
            },
            field.discriminantValue.map(dv => StringTree(", union tag = ", dv)),
            "\n"
          )
        }
        case _ if field.group.isDefined => {
          val newScope = getDependency(scope, field.group.get.typeId)
          val group = newScope.struct.get
          StringTree(
            indent, field.name,
            " :group ", genAnnotations(field.annotations, newScope), "{",
            field.discriminantValue.map(dv => StringTree(", union tag = ", dv)),
            "\n",
            genStructFields(group, newScope, indent.next),
            indent, "}\n"
          )
        }
        case _ => throw new IllegalArgumentException("Unknown field: " + field)
      }
    }

    def genDecl(schema: raw.Node, name: String, scopeId: Long, indent: StringTreeIndent): StringTree = {
      schema match {
        case _ if schema.struct.isDefined => {
          val struct = schema.struct.get
          val enc = if (struct.preferredListEncoding == "inlineComposite") {
            StringTree()
          } else {
            StringTree(", packed as ", elementSizeName(struct.preferredListEncoding))
          }
          StringTree(
            indent, "struct ", name,
            " @0x", schema.id.toHexString, genAnnotationsSchema(schema), " {  # ",
            struct.dataWordCount.getOrElse(0.toShort) * 8, " bytes, ",
            struct.pointerCount, " ptrs",
            enc,
            "\n",
            genStructFields(struct, schema, indent.next),
            genNestedDecls(schema, indent.next),
            indent, "}\n"
          )
        }
        case _ if schema.enum.isDefined => {
          val enum = schema.enum.get
          StringTree(
            indent, "enum ", name,
            " @0x", schema.id.toHexString, genAnnotationsSchema(schema), " {\n",
            enum.enumerants.sortBy(_.codeOrder).zipWithIndex.map({ case (e, i) => StringTree(
              indent.next, e.name, " @", i, genAnnotations(e.annotations, schema), ";\n"
            )}),
            genNestedDecls(schema, indent.next),
            indent, "}\n"
          )
        }
        case _ => throw new IllegalArgumentException("Unknown schema: " + schema)
      }
    }

    def genNestedDecls(schema: raw.Node, indent: StringTreeIndent): StringTree = {
      val id = schema.id;
      StringTree(schema.nestedNodes.flatten.map(nested => {
        genDecl(schemasById(nested.id), nested.name, id, indent)
      }))
    }

    def genFile(file: raw.Node): StringTree = {
      StringTree(
        "# ", file.displayName, "\n",
        "@0x", file.id.toHexString, ";\n",
        file.annotations.flatten.map(genAnnotation(_, file, "", ";\n")),
        genNestedDecls(file, new StringTreeIndent(0, "  "))
      )
    }

    parsed.requestedFiles.map(requestedFile => {
      println(genFile(schemasById(requestedFile.id)))
    })
  }
}
