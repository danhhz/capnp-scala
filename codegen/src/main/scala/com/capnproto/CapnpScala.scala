// Copyright 2013 Daniel Harrison. All Rights Reserved.

package com.capnproto.codegen

import com.capnproto.{Pointer, Segments}

import java.io.FileWriter
import java.nio.{ByteBuffer, ByteOrder}

object CapnpScala {

  def getSchemas(nodes: Seq[foo.Node]): Map[java.lang.Long, foo.Node] = {
    nodes.map(node => (node.id.get, node)).toMap
  }

  def main(args: Array[String]): Unit = {
    val parsed = Segments.fromInputStream(System.in).asStruct(foo.CodeGeneratorRequest)
      .getOrElse(throw new IllegalArgumentException("Couldn't parse stdin as CodeGeneratorRequest"))
    val schemasById = getSchemas(parsed.nodes.get)

    def getDependency(scope: foo.Node, id: Long): foo.Node = {
      schemasById.get(id).get
    }

    def genType(ctype: foo.__Type, scope: foo.Node): StringTree = {
      StringTree(ctype.switch match {
        case foo.__Type.Union.void(_) => "Unit"
        case foo.__Type.Union.bool(_) => "java.lang.Boolean"
        case foo.__Type.Union.int8(_) => "java.lang.Byte"
        case foo.__Type.Union.int16(_) => "java.lang.Short"
        case foo.__Type.Union.int32(_) => "java.lang.Integer"
        case foo.__Type.Union.int64(_) => "java.lang.Long"
        case foo.__Type.Union.uint8(_) => "java.lang.Byte"
        case foo.__Type.Union.uint16(_) => "java.lang.Short"
        case foo.__Type.Union.uint32(_) => "java.lang.Integer"
        case foo.__Type.Union.uint64(_) => "java.lang.Long"
        case foo.__Type.Union.float32(_) => "java.lang.Double"
        case foo.__Type.Union.float64(_) => "java.lang.Double"
        case foo.__Type.Union.text(_) => "String"
        case foo.__Type.Union.data(_) => "ByteBuffer"
        case foo.__Type.Union.list(_) => StringTree("Seq[", genType(ctype.list.get.elementType.get, scope), "]")
        case foo.__Type.Union.__enum(_) => nodeName(getDependency(scope, ctype.__enum.get.typeId.get))
        case foo.__Type.Union.__struct(_) => nodeName(getDependency(scope, ctype.__struct.get.typeId.get))
        case foo.__Type.Union.interface(_) => nodeName(getDependency(scope, ctype.interface.get.typeId.get))
        case foo.__Type.Union.__object(_) => "AnyRef"
        case _ => throw new IllegalArgumentException("Unknown ctype: " + ctype)
      })
    }

    def genAccessor(ctype: foo.__Type, scope: foo.Node, offset: Int): StringTree = {
      ctype.switch match {
        case foo.__Type.Union.void(_) => StringTree("getNone()")
        case foo.__Type.Union.bool(_) => StringTree("getBoolean(", offset, ")")
        case foo.__Type.Union.int8(_) => StringTree("getByte(", offset, ")")
        case foo.__Type.Union.int16(_) => StringTree("getShort(", offset, ")")
        case foo.__Type.Union.int32(_) => StringTree("getInt(", offset, ")")
        case foo.__Type.Union.int64(_) => StringTree("getLong(", offset, ")")
        case foo.__Type.Union.uint8(_) => StringTree("getByte(", offset, ")")
        case foo.__Type.Union.uint16(_) => StringTree("getShort(", offset, ")")
        case foo.__Type.Union.uint32(_) => StringTree("getInt(", offset, ")")
        case foo.__Type.Union.uint64(_) => StringTree("getLong(", offset, ")")
        case foo.__Type.Union.float32(_) => StringTree("getDouble(", offset, ")")
        case foo.__Type.Union.float64(_) => StringTree("getDouble(", offset, ")")
        case foo.__Type.Union.text(_) => StringTree("getString(", offset, ")")
        case foo.__Type.Union.data(_) => StringTree("getNone(", offset, ")")
        case foo.__Type.Union.list(_) => {
          StringTree("getStructList(", offset, ").map(_.map(new ", genType(ctype.list.get.elementType.get, scope), "Mutable(_)))")
        }
        case foo.__Type.Union.__enum(_) => {
          StringTree("getShort(", offset, ").map(id => ", nodeName(getDependency(scope, ctype.__enum.get.typeId.get)), ".findById(id.toInt).getOrElse(", nodeName(getDependency(scope, ctype.__enum.get.typeId.get)), ".Unknown(id.toShort)))")
        }
        case foo.__Type.Union.__struct(_) => {
          StringTree("getStruct(", offset, ").map(new ", nodeName(getDependency(scope, ctype.__struct.get.typeId.get)), "Mutable(_))")
        }
        case foo.__Type.Union.interface(_) => StringTree("getNone(", offset, ")")
        case foo.__Type.Union.__object(_) => StringTree("getNone(", offset, ")")
        case _ => throw new IllegalArgumentException("Unknown ctype: " + ctype)
      }
    }

    def genValue(ctype: foo.__Type, value: foo.Value, scope: foo.Node): StringTree = {
      ctype.switch match {
        case foo.__Type.Union.void(_) => StringTree("void")
        case foo.__Type.Union.bool(_) => StringTree(value.bool.get)
        case foo.__Type.Union.int8(_) => StringTree(value.int8.get)
        case foo.__Type.Union.int16(_) => StringTree(value.int16.get)
        case foo.__Type.Union.int32(_) => StringTree(value.int32.get)
        case foo.__Type.Union.int64(_) => StringTree(value.int64.get)
        case foo.__Type.Union.uint8(_) => StringTree(value.uint8.get)
        case foo.__Type.Union.uint16(_) => StringTree(value.uint16.get)
        case foo.__Type.Union.uint32(_) => StringTree(value.uint32.get)
        case foo.__Type.Union.uint64(_) => StringTree(value.uint64.get)
        case foo.__Type.Union.float32(_) => StringTree(value.float32.get)
        case foo.__Type.Union.float64(_) => StringTree(value.float64.get)
        case foo.__Type.Union.text(_) => StringTree("\"", value.text.get, "\"")
        case foo.__Type.Union.data(_) => StringTree("\"", value.data.get, "\"")
        case foo.__Type.Union.list(_) => StringTree("TODO(ctype.list(_))")
        case foo.__Type.Union.__enum(_) => StringTree(ctype.__enum.get)
        case foo.__Type.Union.__struct(_) => StringTree("TODO(ctype.__struct(_))")
        case foo.__Type.Union.interface(_) => StringTree("")
        case foo.__Type.Union.__object(_) => StringTree("")
        case _ => throw new IllegalArgumentException("Unknown ctype: " + ctype)
      }
    }

    def getUnqualifiedName(schema: foo.Node): String = {
      val parent = schemasById.get(schema.scopeId.get).get
      val nodeNameOpt = parent.nestedNodes.getOrElse(Nil).find(_.id == schema.id).map(_.name.get)
      nodeNameOpt.orElse({
        parent.__struct.flatMap(_.__fields.get.find(_.group.exists(_.typeId == schema.id))).map(f => scalaCaseName(f.name.get))
      }).map(scalaEscapeName(_)).getOrElse("?")
    }

    def getScalaPackageName: String = {
      return "foo"
    }

    def nodeName(target: foo.Node): StringTree = {
      var targetParents = {
        val builder = Vector.newBuilder[foo.Node]
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

    def fieldScalaType(field: foo.Field, scope: foo.Node): StringTree = {
      field.switch match {
        case foo.Field.Union.slot(slot) => genType(slot.get.__type.get, scope)
        case foo.Field.Union.group(group) => {
          var parent = schemasById.get(getDependency(scope, group.get.typeId.get).scopeId.get).get
          var path = nodeName(parent)
          StringTree(path, ".", scalaEscapeName(scalaCaseName(field.name.get)))
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

    def genGroupDecl(schema: foo.Node, name: String, scopeId: Long, indent: StringTreeIndent): StringTree = {
      genDecl(schema, scalaEscapeName(scalaCaseName(name)), scopeId, indent)
    }

    def genDecl(schema: foo.Node, nameRaw: String, scopeId: Long, indent: StringTreeIndent): StringTree = {
      val name = scalaEscapeName(nameRaw)
      schema.switch match {
        case foo.Node.Union.__struct(s) => {
          val struct = s.get
          val fields = struct.__fields.get.sortBy(_.codeOrder)
          val unionFields = fields.filter(_.discriminantValue.isDefined)
          val groupFields = fields.filter(_.switch match { case foo.Field.Union.group(_) => true; case _ => false})
          StringTree(
            indent, "object ", name, " extends MetaStruct[", name, "] {\n",
            indent.next, "override type Self = ", name, ".type\n",
            indent.next, "override def create(struct: CapnpStruct): ", name, " = new ", name, "Mutable(struct)\n",
            indent.next, "override val recordName: String = \"", nameRaw, "\"\n",
            indent.next, "override val fields: Seq[FieldDescriptor[_, ", name, ", ", name, ".type]] = Seq(",
            StringTree.join(", ", fields.map(field => StringTree(scalaEscapeName(field.name.get)))),
            ")\n",
            "\n",
            genNestedDecls(schema, indent.next),
            if (unionFields.isEmpty) StringTree() else StringTree(
              indent.next, "sealed trait Union extends UnionValue[", nodeName(schema), ".Union]\n",
              indent.next, "object Union extends UnionMeta[", nodeName(schema), ".Union] {\n",
              indent.next.next, "case class Unknown(discriminant: Short) extends ", nodeName(schema), ".Union\n",
              unionFields.map(unionField => StringTree(
                indent.next.next, "case class ", scalaEscapeName(unionField.name.get), "(value: Option[", fieldScalaType(unionField, schema), "]) extends ", nodeName(schema), ".Union\n"
              )),
              indent.next, "}\n\n"
            ),
            groupFields.map(groupField => {
              val groupNode = getDependency(schema, groupField.group.get.typeId.get)
              genGroupDecl(groupNode, groupField.name.get, groupNode.scopeId.get, indent.next)
            }),
            // indent.next, "def apply(",
            // StringTree.join(", ", fields.map(field => StringTree(scalaEscapeName(field.name.get), ": ", fieldScalaType(field, schema)))),
            // "): ", name, " = {\n",
            // indent.next.next, "new ", name, "Mutable(", StringTree.join(", ", fields.map(f => StringTree(scalaEscapeName(f.name.get)))), ")\n",
            // indent.next, "}\n",
            "\n",
            StringTree.join("\n", fields.map(field => StringTree(
              indent.next, "val ", scalaEscapeName(field.name.get), " = new FieldDescriptor[", fieldScalaType(field, schema), ", ", name, ", ", name, ".type](\n",
              indent.next.next, "name = \"", field.name.get, "\",\n",
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
              indent.next, "def ", scalaEscapeName(field.name.get), ": Option[", fieldScalaType(field, schema), "]\n"
            )),
            // indent.next, "def copy(",
            // StringTree.join(", ", fields.map(field => StringTree(scalaEscapeName(field.name.get), ": ", fieldScalaType(field, schema), " = this.", scalaEscapeName(field.name.get), ".getOrElse(null)")))
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
              indent.next, "override def ", scalaEscapeName(field.name.get), ": Option[", fieldScalaType(field, schema), "]\n"
            )),
            // indent.next, "override def copy(",
            // StringTree.join(", ", fields.map(field => StringTree(scalaEscapeName(field.name.get), ": ", fieldScalaType(field, schema))))
            // , "): ", name, " = {\n",
            // indent.next.next, "underlying.copy(", StringTree.join(", ", fields.map(f => StringTree(scalaEscapeName(f.name.get)))), ")\n",
            // indent.next, "}\n",
            indent, "}\n\n",


            indent, "class ", name, "Mutable(override val struct: CapnpStruct) extends ", name, " {\n",
            indent.next, "override def meta: ", name, ".type = ", name, "\n",
            "\n",
            if (unionFields.isEmpty) StringTree() else StringTree(
              indent.next, "override def discriminant: Short = (struct.getShort(", struct.discriminantOffset.getOrElse(0).toString, ").getOrElse(new java.lang.Short(0.toShort)): java.lang.Short)\n",
              indent.next, "override def switch: ", nodeName(schema), ".Union = discriminant match {\n",
              unionFields.zipWithIndex.map({ case (unionField, i) => StringTree(
                indent.next.next, "case ", i.toString, " => ", nodeName(schema), ".Union.", scalaEscapeName(unionField.name.get), "(", scalaEscapeName(unionField.name.get), ")\n"
              )}),
              indent.next.next, "case d => ", nodeName(schema), ".Union.Unknown(d)\n",
              indent.next, "}\n",
              indent.next, "override def union: UnionMeta[", nodeName(schema), ".Union] = ", nodeName(schema), ".Union\n",
              "\n"
            ),
            StringTree.join("\n", fields.map(field => StringTree(
              indent.next, "override def ", scalaEscapeName(field.name.get), ": Option[", fieldScalaType(field, schema), "] = ",
              field.switch match {
                case foo.Field.Union.slot(slot) => StringTree("struct.", genAccessor(slot.get.__type.get, schema, slot.get.offset.map(_.toInt).getOrElse(0)))
                case foo.Field.Union.group(group) => {
                  StringTree("Some(new ", nodeName(schema), ".", scalaEscapeName(scalaCaseName(field.name.get)), "Mutable(struct))\n")
                }
                case _ => throw new IllegalArgumentException("Unknown: " + field.switch)
              }
            ))), "\n",
            // indent.next, "override def copy(",
            // StringTree.join(", ", fields.map(field => StringTree(scalaEscapeName(field.name.get), ": ", fieldScalaType(field, schema))))
            // , "): ", name, " = {\n",
            // indent.next.next, "new ", name, "Mutable(", StringTree.join(", ", fields.map(f => StringTree(scalaEscapeName(f.name.get)))), ")\n",
            // indent.next, "}\n",
            indent, "}\n"
          )
        }
        case foo.Node.Union.__enum(e) => {
          val enum = e.get
          StringTree(
            indent, "object ", name, " extends EnumMeta[", name, "] {\n",
            genNestedDecls(schema, indent.next),
            indent.next, "case class Unknown(override val id: Int) extends ", name, "(", name, ", id, null, null)\n\n",
            StringTree.join("\n", enum.enumerants.get.sortBy(_.codeOrder).zipWithIndex.map({ case (e, i) => StringTree(
              indent.next, "val ", e.name.get, " = new ", name, "(this, ", i.toString, ", \"", e.name.get, "\", \"", e.name.get, "\")"
            )})), "\n\n",
            indent.next, "override val values = Vector(\n",
            StringTree.join(",\n", enum.enumerants.get.sortBy(_.codeOrder).map(e => StringTree(indent.next.next, e.name.get))), "\n",
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

    def genNestedDecls(schema: foo.Node, indent: StringTreeIndent): StringTree = {
      val id = schema.id.get;
      StringTree.join("\n", schema.nestedNodes.getOrElse(Nil).map(nested => {
        genDecl(schemasById(nested.id.get), nested.name.get, id, indent)
      }).toSeq)
    }

    def genFile(file: foo.Node): StringTree = {
      StringTree(
        "// ", file.displayName, "\n\n",
        "package ", getScalaPackageName, "\n\n",
        "import com.foursquare.spindle.{Enum, EnumMeta}\n",
        "import com.capnproto.{HasUnion, UnionMeta, UnionValue, UntypedFieldDescriptor, FieldDescriptor, UntypedStruct, Struct, UntypedMetaStruct, MetaStruct}\n",
        "import com.capnproto.{CapnpStruct, Pointer => CapnpPointer, CapnpList, CapnpTag}\n",
        "import java.nio.ByteBuffer\n",
        "\n",
        genNestedDecls(file, new StringTreeIndent(0, "  "))
      )
    }

    parsed.requestedFiles.get.map(requestedFile => {
      val outputPath = requestedFile.filename.get + ".scala"
      val output = genFile(schemasById(requestedFile.id.get)).flatten
      val source = new FileWriter(outputPath)
      source.write(output)
      source.close
      println("Wrote " + outputPath)
    })
  }
}
