// Copyright 2013 Daniel Harrison. All Rights Reserved.

package com.capnproto.codegen

import com.capnproto.{Pointer, CapnpArena}
import com.capnproto.schema.{Node, CodeGeneratorRequest, __Type, Value, Field}

import java.io.FileWriter
import java.nio.{ByteBuffer, ByteOrder}

object CapnpScala {

  def getSchemas(nodes: Seq[Node]): Map[java.lang.Long, Node] = {
    nodes.map(node => (node.id.get, node)).toMap
  }

  def main(args: Array[String]): Unit = {
    val parsed = CapnpArena.fromInputStream(System.in).getRoot(CodeGeneratorRequest)
      .getOrElse(throw new IllegalArgumentException("Couldn't parse stdin as CodeGeneratorRequest"))
    val schemasById = getSchemas(parsed.nodes)

    def getDependency(scope: Node, id: Long): Node = {
      schemasById.get(id).get
    }

    def genType(ctype: __Type, scope: Node): StringTree = {
      StringTree(ctype.switch match {
        case __Type.Union.void(_) => "Unit"
        case __Type.Union.bool(_) => "java.lang.Boolean"
        case __Type.Union.int8(_) => "java.lang.Byte"
        case __Type.Union.int16(_) => "java.lang.Short"
        case __Type.Union.int32(_) => "java.lang.Integer"
        case __Type.Union.int64(_) => "java.lang.Long"
        case __Type.Union.uint8(_) => "java.lang.Byte"
        case __Type.Union.uint16(_) => "java.lang.Short"
        case __Type.Union.uint32(_) => "java.lang.Integer"
        case __Type.Union.uint64(_) => "java.lang.Long"
        case __Type.Union.float32(_) => "java.lang.Double"
        case __Type.Union.float64(_) => "java.lang.Double"
        case __Type.Union.text(_) => "String"
        case __Type.Union.data(_) => "Array[Byte]"
        case __Type.Union.list(_) => StringTree("Seq[", genType(ctype.list.elementType.get, scope), "]")
        case __Type.Union.__enum(_) => nodeName(getDependency(scope, ctype.__enum.typeId.get))
        case __Type.Union.__struct(_) => nodeName(getDependency(scope, ctype.__struct.typeId.get))
        case __Type.Union.interface(_) => nodeName(getDependency(scope, ctype.interface.typeId.get))
        case __Type.Union.__object(_) => "AnyRef"
        case _ => throw new IllegalArgumentException("Unknown ctype: " + ctype)
      })
    }

    def genAccessor(ctype: __Type, scope: Node, offset: Int): StringTree = {
      ctype.switch match {
        case __Type.Union.void(_) => StringTree("getNone()")
        case __Type.Union.bool(_) => StringTree("getBoolean(", offset, ")")
        case __Type.Union.int8(_) => StringTree("getByte(", offset, ")")
        case __Type.Union.int16(_) => StringTree("getShort(", offset, ")")
        case __Type.Union.int32(_) => StringTree("getInt(", offset, ")")
        case __Type.Union.int64(_) => StringTree("getLong(", offset, ")")
        case __Type.Union.uint8(_) => StringTree("getByte(", offset, ")")
        case __Type.Union.uint16(_) => StringTree("getShort(", offset, ")")
        case __Type.Union.uint32(_) => StringTree("getInt(", offset, ")")
        case __Type.Union.uint64(_) => StringTree("getLong(", offset, ")")
        case __Type.Union.float32(_) => StringTree("getDouble(", offset, ")")
        case __Type.Union.float64(_) => StringTree("getDouble(", offset, ")")
        case __Type.Union.text(_) => StringTree("getString(", offset, ")")
        case __Type.Union.data(_) => StringTree("getData(", offset, ")")
        case __Type.Union.list(_) => StringTree(
          "getStructList(", offset, ").map(new ", genType(ctype.list.elementType.get, scope), "Mutable(_))"
        )
        case __Type.Union.__enum(_) => StringTree(
          "getShort(", offset, ").map(id => ", nodeName(getDependency(scope, ctype.__enum.typeId.get)), ".findById(id.toInt).getOrElse(", nodeName(getDependency(scope, ctype.__enum.typeId.get)), ".Unknown(id.toShort)))"
        )
        case __Type.Union.__struct(_) => StringTree(
          "getStruct(", offset, ").map(new ", nodeName(getDependency(scope, ctype.__struct.typeId.get)), "Mutable(_))"
        )
        case __Type.Union.interface(_) => StringTree("getNone(", offset, ")")
        case __Type.Union.__object(_) => StringTree("getNone(", offset, ")")
        case _ => throw new IllegalArgumentException("Unknown ctype: " + ctype)
      }
    }

    def genSetter(ctype: __Type, scope: Node, offset: Int): StringTree = {
      ctype.switch match {
        case __Type.Union.void(_) => StringTree("setNone()")
        case __Type.Union.bool(_) => StringTree("setBoolean(", offset, ", value)")
        case __Type.Union.int8(_) => StringTree("setByte(", offset, ", value)")
        case __Type.Union.int16(_) => StringTree("setShort(", offset, ", value)")
        case __Type.Union.int32(_) => StringTree("setInt(", offset, ", value)")
        case __Type.Union.int64(_) => StringTree("setLong(", offset, ", value)")
        case __Type.Union.uint8(_) => StringTree("setByte(", offset, ", value)")
        case __Type.Union.uint16(_) => StringTree("setShort(", offset, ", value)")
        case __Type.Union.uint32(_) => StringTree("setInt(", offset, ", value)")
        case __Type.Union.uint64(_) => StringTree("setLong(", offset, ", value)")
        case __Type.Union.float32(_) => StringTree("setDouble(", offset, ", value)")
        case __Type.Union.float64(_) => StringTree("setDouble(", offset, ", value)")
        case __Type.Union.text(_) => StringTree("setString(", offset, ", value)")
        case __Type.Union.data(_) => StringTree("setData(", offset, ", value)")
        case __Type.Union.list(_) => StringTree("setNone()")
        case __Type.Union.__enum(_) => StringTree("setShort(", offset, ", value.id.toShort)")
        case __Type.Union.__struct(_) => StringTree("setNone()")
        case __Type.Union.interface(_) => StringTree("setNone()")
        case __Type.Union.__object(_) => StringTree("setNone()")
        case _ => throw new IllegalArgumentException("Unknown ctype: " + ctype)
      }
    }

    def genValue(ctype: __Type, value: Value, scope: Node): StringTree = {
      ctype.switch match {
        case __Type.Union.void(_) => StringTree("void")
        case __Type.Union.bool(_) => StringTree(value.bool.get)
        case __Type.Union.int8(_) => StringTree(value.int8.get)
        case __Type.Union.int16(_) => StringTree(value.int16.get)
        case __Type.Union.int32(_) => StringTree(value.int32.get)
        case __Type.Union.int64(_) => StringTree(value.int64.get)
        case __Type.Union.uint8(_) => StringTree(value.uint8.get)
        case __Type.Union.uint16(_) => StringTree(value.uint16.get)
        case __Type.Union.uint32(_) => StringTree(value.uint32.get)
        case __Type.Union.uint64(_) => StringTree(value.uint64.get)
        case __Type.Union.float32(_) => StringTree(value.float32.get)
        case __Type.Union.float64(_) => StringTree(value.float64.get)
        case __Type.Union.text(_) => StringTree("\"", value.text.get, "\"")
        case __Type.Union.data(_) => StringTree("Array(", value.data.get.mkString(", "), ")")
        case __Type.Union.list(_) => StringTree("TODO(ctype.list(_))")
        case __Type.Union.__enum(_) => StringTree(ctype.__enum)
        case __Type.Union.__struct(_) => StringTree("TODO(ctype.__struct(_))")
        case __Type.Union.interface(_) => StringTree("")
        case __Type.Union.__object(_) => StringTree("")
        case _ => throw new IllegalArgumentException("Unknown ctype: " + ctype)
      }
    }

    def getUnqualifiedName(schema: Node): String = {
      val parent = schemasById.get(schema.scopeId.get).get
      val nodeNameOpt = parent.nestedNodes.find(_.id == schema.id).map(_.name.get)
      nodeNameOpt.orElse({
        parent.__struct.__fields.find(_.group.typeId == schema.id).map(f => scalaCaseName(f.name.get))
      }).map(scalaEscapeName(_)).getOrElse("?")
    }

    def getScalaPackageName(file: Node): String = {
      file.annotations.find(_.id.get == -2365685817485540979L).map(_.value.get.text.get).getOrElse("")
    }

    def nodeName(target: Node): StringTree = {
      var targetParents = {
        val builder = Vector.newBuilder[Node]
        var parent = target
        while (parent.scopeId.exists(_ != 0)) {
          parent = schemasById.get(parent.scopeId.get).get
          builder += parent
        }
        builder.result
      }

      val fileNode = targetParents.last
      var path = StringTree()
      while (targetParents.nonEmpty) {
        if (targetParents.last.scopeId.isEmpty || targetParents.last.scopeId.get == 0) {
          // No-op.
        } else {
          path = StringTree(path, getUnqualifiedName(targetParents.last), ".")
        }
        targetParents = targetParents.dropRight(1)
      }

      StringTree(getScalaPackageName(fileNode), ".", path, getUnqualifiedName(target))
    }

    def fieldScalaType(field: Field, scope: Node): StringTree = {
      field.switch match {
        case Field.Union.slot(slot) => genType(slot.__type.get, scope)
        case Field.Union.group(group) => {
          var parent = schemasById.get(getDependency(scope, group.typeId.get).scopeId.get).get
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

    def genGroupDecl(schema: Node, name: String, scopeId: Long, indent: StringTreeIndent): StringTree = {
      genDecl(schema, scalaEscapeName(scalaCaseName(name)), scopeId, indent)
    }

    def genDecl(schema: Node, nameRaw: String, scopeId: Long, indent: StringTreeIndent): StringTree = {
      val name = scalaEscapeName(nameRaw)
      schema.switch match {
        case Node.Union.__struct(struct) => {
          val fields = struct.__fields.sortBy(_.codeOrder)
          val unionFields = fields.filter(_.discriminantValue.isDefined)
          val groupFields = fields.filter(_.switch match { case Field.Union.group(_) => true; case _ => false})
          StringTree(
            indent, "object ", name, " extends MetaStruct[", name, "] {\n",
            indent.next, "override type Self = ", name, ".type\n",
            indent.next, "override val recordName: String = \"", nameRaw, "\"\n",
            indent.next, "override def create(struct: CapnpStruct): ", name, " = new ", name, "Mutable(struct)\n",
            indent.next, "override val fields: Seq[FieldDescriptor[_, ", name, ", ", name, ".type]] = Seq(",
            StringTree.join(", ", fields.map(field => StringTree(scalaEscapeName(field.name.get)))),
            ")\n",
            "\n",
            indent.next, "object Builder extends MetaStructBuilder[", nodeName(schema), ", ", nodeName(schema), ".Builder] {\n",
            indent.next.next, "override type Self = ", nodeName(schema), ".Builder.type\n",
            indent.next.next, "override val recordName: String = \"", nameRaw, "\"\n",
            indent.next.next, "override val dataSectionSizeWords: Short = ", struct.dataWordCount.getOrElse(0), "\n",
            indent.next.next, "override val pointerSectionSizeWords: Short = ", struct.pointerCount.getOrElse(0), "\n",
            indent.next.next, "override def create(struct: CapnpStructBuilder): ", nodeName(schema), ".Builder = new ", nodeName(schema), ".Builder(struct)\n",
            indent.next.next, "override def fields: Seq[UntypedFieldDescriptor] = ", nodeName(schema), ".fields\n",
            indent.next, "}\n",
            indent.next, "class Builder(override val struct: CapnpStructBuilder) extends ", nodeName(schema), "Mutable(struct) with StructBuilder[", nodeName(schema), ", ", nodeName(schema), ".Builder] {\n",
            indent.next.next, "override type MetaBuilderT = ", nodeName(schema), ".Builder.type\n\n",
            indent.next.next, "override def meta: ", name, ".type = ", name, "\n",
            indent.next.next, "override def metaBuilder: MetaBuilderT = ", nodeName(schema), ".Builder\n",
            fields.map(field => StringTree(
              field.switch match {
                case Field.Union.slot(slot) => StringTree(
                  indent.next.next, "def set", scalaCaseName(scalaEscapeName(field.name.get)), "(value: ", fieldScalaType(field, schema), "): Builder = { ",
                  "struct.", genSetter(slot.__type.get, schema, slot.offset.map(_.toInt).getOrElse(0)), "; ",
                  field.discriminantValue.map(d => StringTree(
                    "struct.setShort(", struct.discriminantOffset.getOrElse(0), ", ", d, "); "
                  )),
                  "this }\n",
                  slot.__type.get.switch match {
                    case __Type.Union.list(list) => list.elementType.get.switch match {
                      case __Type.Union.__struct(_) => StringTree(
                        indent.next.next, "def init", scalaCaseName(scalaEscapeName(field.name.get)), "(count: Int): Seq[", genType(list.elementType.get, schema), ".Builder] = {\n",
                        indent.next.next.next, "val list = struct.initPointerList(", slot.offset.getOrElse(0), ", count, ", genType(list.elementType.get, schema), ".Builder)\n",
                        indent.next.next.next, "Range(0, count).map(i => new ", genType(list.elementType.get, schema), ".Builder(list.initStruct(i, ", genType(list.elementType.get, schema), ".Builder)))\n",
                        indent.next.next, "}\n"
                      )
                      case _ => StringTree()
                    }
                    case _ => StringTree()
                  }
                )
                case Field.Union.group(group) => StringTree(
                  indent.next.next, "override def ", scalaEscapeName(field.name.get), ": ", fieldScalaType(field, schema), ".Builder = new ", fieldScalaType(field, schema), ".Builder(struct)\n"
                )
                case _ => throw new IllegalArgumentException("Unknown: " + field.switch)
              }
            )),
            indent.next, "}\n\n",
            genNestedDecls(schema, indent.next),
            if (unionFields.isEmpty) StringTree() else StringTree(
              indent.next, "sealed trait Union extends UnionValue[", nodeName(schema), ".Union]\n",
              indent.next, "object Union extends UnionMeta[", nodeName(schema), ".Union] {\n",
              indent.next.next, "case class Unknown(discriminant: Short) extends ", nodeName(schema), ".Union\n",
              unionFields.map(unionField => StringTree(
                indent.next.next, "case class ", scalaEscapeName(unionField.name.get), "(value: ",
                unionField.switch match {
                  case Field.Union.slot(slot) => slot.__type.get.switch match {
                    case __Type.Union.list(_) => StringTree(fieldScalaType(unionField, schema))
                    case _ => StringTree("Option[", fieldScalaType(unionField, schema), "]")
                  }
                  case Field.Union.group(group) => StringTree(fieldScalaType(unionField, schema))
                  case _ => throw new IllegalArgumentException("Unknown: " + unionField.switch)
                },
                ") extends ", nodeName(schema), ".Union\n"
              )),
              indent.next, "}\n\n"
            ), "\n",
            groupFields.map(groupField => {
              val groupNode = getDependency(schema, groupField.group.typeId.get)
              genGroupDecl(groupNode, groupField.name.get, groupNode.scopeId.get, indent.next)
            }), "\n",
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
            indent.next, "override def meta: ", name, ".type = ", name, "\n",
            indent.next, "def struct: CapnpStruct\n\n",
            fields.map(field => StringTree(
              indent.next, "def ", scalaEscapeName(field.name.get), ": ",
              field.switch match {
                case Field.Union.slot(slot) => slot.__type.get.switch match {
                  case __Type.Union.list(_) => StringTree(fieldScalaType(field, schema), "\n")
                  case _ => StringTree("Option[", fieldScalaType(field, schema), "]\n")
                }
                case Field.Union.group(group) => StringTree(fieldScalaType(field, schema), "\n")
                case _ => throw new IllegalArgumentException("Unknown: " + field.switch)
              }
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
              indent.next, "override def ", scalaEscapeName(field.name.get), ": ",
              field.switch match {
                case Field.Union.slot(slot) => slot.__type.get.switch match {
                  case __Type.Union.list(_) => StringTree(fieldScalaType(field, schema), "\n")
                  case _ => StringTree("Option[", fieldScalaType(field, schema), "]\n")
                }
                case Field.Union.group(group) => StringTree(fieldScalaType(field, schema), "\n")
                case _ => throw new IllegalArgumentException("Unknown: " + field.switch)
              }
            )),
            // indent.next, "override def copy(",
            // StringTree.join(", ", fields.map(field => StringTree(scalaEscapeName(field.name.get), ": ", fieldScalaType(field, schema))))
            // , "): ", name, " = {\n",
            // indent.next.next, "underlying.copy(", StringTree.join(", ", fields.map(f => StringTree(scalaEscapeName(f.name.get)))), ")\n",
            // indent.next, "}\n",
            indent, "}\n\n",


            indent, "class ", name, "Mutable(override val struct: CapnpStruct) extends ", name, " {\n",
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
              indent.next, "override def ", scalaEscapeName(field.name.get), ": ",
              field.switch match {
                case Field.Union.slot(slot) => slot.__type.get.switch match {
                  case __Type.Union.list(_) => StringTree(
                    fieldScalaType(field, schema), " = struct.", genAccessor(slot.__type.get, schema, slot.offset.map(_.toInt).getOrElse(0))
                  )
                  case _ => StringTree(
                    "Option[", fieldScalaType(field, schema), "] = struct.", genAccessor(slot.__type.get, schema, slot.offset.map(_.toInt).getOrElse(0))
                  )
                }
                case Field.Union.group(group) => StringTree(
                  fieldScalaType(field, schema), " = new ", nodeName(schema), ".", scalaEscapeName(scalaCaseName(field.name.get)), "Mutable(struct)\n"
                )
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
        case Node.Union.__enum(enum) => {
          StringTree(
            indent, "object ", name, " extends EnumMeta[", name, "] {\n",
            genNestedDecls(schema, indent.next),
            indent.next, "case class Unknown(override val id: Int) extends ", name, "(", name, ", id, null, null)\n\n",
            StringTree.join("\n", enum.enumerants.sortBy(_.codeOrder).zipWithIndex.map({ case (e, i) => StringTree(
              indent.next, "val ", e.name.get, " = new ", name, "(this, ", i.toString, ", \"", e.name.get, "\", \"", e.name.get, "\")"
            )})), "\n\n",
            indent.next, "override val values = Vector(\n",
            StringTree.join(",\n", enum.enumerants.sortBy(_.codeOrder).map(e => StringTree(indent.next.next, e.name.get))), "\n",
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

    def genNestedDecls(schema: Node, indent: StringTreeIndent): StringTree = {
      val id = schema.id.get;
      StringTree.join("\n", schema.nestedNodes.map(nested => {
        genDecl(schemasById(nested.id.get), nested.name.get, id, indent)
      }).toSeq)
    }

    def genFile(file: Node): StringTree = {
      StringTree(
        "// ", file.displayName, "\n\n",
        "package ", getScalaPackageName(file), "\n\n",
        "import com.foursquare.spindle.{Enum, EnumMeta}\n",
        "import com.capnproto.{HasUnion, UnionMeta, UnionValue, UntypedFieldDescriptor, FieldDescriptor, UntypedStruct, Struct, UntypedMetaStruct, MetaStruct, StructBuilder, MetaStructBuilder}\n",
        "import com.capnproto.{CapnpStruct, CapnpStructBuilder, Pointer => CapnpPointer, CapnpList, CapnpTag}\n",
        "import java.nio.ByteBuffer\n",
        "\n",
        genNestedDecls(file, new StringTreeIndent(0, "  "))
      )
    }

    parsed.requestedFiles.map(requestedFile => {
      val outputPath = requestedFile.filename.get + ".scala"
      val output = genFile(schemasById(requestedFile.id.get)).flatten
      val source = new FileWriter(outputPath)
      source.write(output)
      source.close
      println("Wrote " + outputPath)
    })
  }
}
