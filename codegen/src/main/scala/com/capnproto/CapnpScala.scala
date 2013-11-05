// Copyright 2013 Daniel Harrison. All Rights Reserved.

package com.capnproto.codegen

import com.capnproto.core.{Pointer, CapnpArena, CapnpList, CapnpStruct, CapnpArenaBuilder}
import com.capnproto.schema.{Node, CodeGeneratorRequest, __Type, Value, Field}

import java.io.{File, FileWriter}
import java.nio.{ByteBuffer, ByteOrder}

object CapnpScala {

  def getSchemas(nodes: Seq[Node]): Map[Long, Node] = {
    nodes.map(node => (node.id.get, node)).toMap
  }

  def main(args: Array[String]): Unit = {
    val parsed = (for {
      arena <- CapnpArena.fromInputStream(System.in)
      message <- arena.getRoot(CodeGeneratorRequest)
    } yield message).getOrElse(
      throw new IllegalArgumentException("Couldn't parse stdin as CodeGeneratorRequest")
    )
    val schemasById = getSchemas(parsed.nodes)

    def getDependency(scope: Node, id: Long): Node = {
      schemasById.get(id).get
    }

    def genType(ctype: __Type, scope: Node): StringTree = {
      StringTree(ctype.switch match {
        case __Type.Union.void(_) => "Unit"
        case __Type.Union.bool(_) => "Boolean"
        case __Type.Union.int8(_) => "Byte"
        case __Type.Union.int16(_) => "Short"
        case __Type.Union.int32(_) => "Int"
        case __Type.Union.int64(_) => "Long"
        case __Type.Union.uint8(_) => "Byte"
        case __Type.Union.uint16(_) => "Short"
        case __Type.Union.uint32(_) => "Int"
        case __Type.Union.uint64(_) => "Long"
        case __Type.Union.float32(_) => "Float"
        case __Type.Union.float64(_) => "Double"
        case __Type.Union.text(_) => "String"
        case __Type.Union.data(_) => "Array[Byte]"
        case __Type.Union.list(_) => StringTree("Seq[", genType(ctype.list.elementType.get, scope), "]")
        case __Type.Union.__enum(_) => nodeName(getDependency(scope, ctype.__enum.typeId.get))
        case __Type.Union.__struct(_) => nodeName(getDependency(scope, ctype.__struct.typeId.get))
        case __Type.Union.interface(_) => nodeName(getDependency(scope, ctype.interface.typeId.get))
        case __Type.Union.__object(_) => "Pointer[_]"
        case _ => throw new IllegalArgumentException("Unknown ctype: " + ctype)
      })
    }

    def genListAccessorFn(listType: __Type.List, scope: Node): StringTree = {
      listType.elementType.get.switch match {
        case __Type.Union.void(_) => StringTree("_.getVoid _")
        case __Type.Union.bool(_) => StringTree("_.getBoolean _")
        case __Type.Union.int8(_) => StringTree("_.getByte _")
        case __Type.Union.int16(_) => StringTree("_.getShort _")
        case __Type.Union.int32(_) => StringTree("_.getInt _")
        case __Type.Union.int64(_) => StringTree("_.getLong _")
        case __Type.Union.uint8(_) => StringTree("_.getByte _")
        case __Type.Union.uint16(_) => StringTree("_.getShort _")
        case __Type.Union.uint32(_) => StringTree("_.getInt _")
        case __Type.Union.uint64(_) => StringTree("_.getLong _")
        case __Type.Union.float32(_) => StringTree("_.getFloat _")
        case __Type.Union.float64(_) => StringTree("_.getDouble _")
        case __Type.Union.text(_) => StringTree("_.getString _")
        case __Type.Union.data(_) => StringTree("_.getData _")
        case __Type.Union.list(sublistType) => StringTree(
          "(l: CapnpList) => (o: Int) => l.getList(o).toSeq(", genListAccessorFn(sublistType, scope), ")"
        )
        case __Type.Union.__enum(_) => StringTree("(l: CapnpList) => (o: Int) => ", genType(listType.elementType.get, scope), ".findById(l.getShort(o)).getOrElse(", genType(listType.elementType.get, scope), ".Unknown(l.getShort(o)))")
        case __Type.Union.__struct(_) => StringTree("(l: CapnpList) => (o: Int) => l.getStruct(o, ", genType(listType.elementType.get, scope), ")")
        case __Type.Union.interface(_) => StringTree("_.getVoid _")
        case __Type.Union.__object(_) => StringTree("_.getPointer _")
        case _ => throw new IllegalArgumentException("Unknown listType: " + listType)
      }
    }

    def genAccessor(ctype: __Type, path: StringTree, field: Field, scope: Node, offset: Int): StringTree = {
      ctype.switch match {
        case __Type.Union.void(_) => StringTree("getNone()")
        case __Type.Union.bool(_) => StringTree("getBoolean(", offset, ", ", path, ".", scalaEscapeName(field.name.get), ".default)")
        case __Type.Union.int8(_) => StringTree("getByte(", offset, ", ", path, ".", scalaEscapeName(field.name.get), ".default)")
        case __Type.Union.int16(_) => StringTree("getShort(", offset, ", ", path, ".", scalaEscapeName(field.name.get), ".default)")
        case __Type.Union.int32(_) => StringTree("getInt(", offset, ", ", path, ".", scalaEscapeName(field.name.get), ".default)")
        case __Type.Union.int64(_) => StringTree("getLong(", offset, ", ", path, ".", scalaEscapeName(field.name.get), ".default)")
        case __Type.Union.uint8(_) => StringTree("getByte(", offset, ", ", path, ".", scalaEscapeName(field.name.get), ".default)")
        case __Type.Union.uint16(_) => StringTree("getShort(", offset, ", ", path, ".", scalaEscapeName(field.name.get), ".default)")
        case __Type.Union.uint32(_) => StringTree("getInt(", offset, ", ", path, ".", scalaEscapeName(field.name.get), ".default)")
        case __Type.Union.uint64(_) => StringTree("getLong(", offset, ", ", path, ".", scalaEscapeName(field.name.get), ".default)")
        case __Type.Union.float32(_) => StringTree("getFloat(", offset, ", ", path, ".", scalaEscapeName(field.name.get), ".default)")
        case __Type.Union.float64(_) => StringTree("getDouble(", offset, ", ", path, ".", scalaEscapeName(field.name.get), ".default)")
        case __Type.Union.text(_) => StringTree("getString(", offset, ", ", path, ".", scalaEscapeName(field.name.get), ".default)")
        case __Type.Union.data(_) => StringTree("getData(", offset, ", ", path, ".", scalaEscapeName(field.name.get), ".default)")
        case __Type.Union.list(listType) => StringTree(
          "getList(", offset, ", ", genListAccessorFn(listType, scope), ", ", path, ".", scalaEscapeName(field.name.get), ".default)"
        )
        case __Type.Union.__enum(_) => StringTree(
          "getShort(", offset, ", ", path, ".", scalaEscapeName(field.name.get), ".default.map(_.id)).map(id => ", nodeName(getDependency(scope, ctype.__enum.typeId.get)), ".findById(id).getOrElse(", nodeName(getDependency(scope, ctype.__enum.typeId.get)), ".Unknown(id.toShort)))"
        )
        case __Type.Union.__struct(_) => StringTree(
          "getStruct(", offset, ", ", nodeName(getDependency(scope, ctype.__struct.typeId.get)), ", ", path, ".", scalaEscapeName(field.name.get), ".default)"
        )
        case __Type.Union.interface(_) => StringTree("getNone(", offset, ")")
        case __Type.Union.__object(_) => StringTree("getPointer(", offset, ")")
        case _ => throw new IllegalArgumentException("Unknown ctype: " + ctype)
      }
    }

    def genSetter(ctype: __Type, path: StringTree, field: Field, scope: Node, offset: Int): StringTree = {
      ctype.switch match {
        case __Type.Union.void(_) => StringTree("setNone()")
        case __Type.Union.bool(_) => StringTree("setBoolean(", offset, ", value, ", path, ".", scalaEscapeName(field.name.get), ".default)")
        case __Type.Union.int8(_) => StringTree("setByte(", offset, ", value, ", path, ".", scalaEscapeName(field.name.get), ".default)")
        case __Type.Union.int16(_) => StringTree("setShort(", offset, ", value, ", path, ".", scalaEscapeName(field.name.get), ".default)")
        case __Type.Union.int32(_) => StringTree("setInt(", offset, ", value, ", path, ".", scalaEscapeName(field.name.get), ".default)")
        case __Type.Union.int64(_) => StringTree("setLong(", offset, ", value, ", path, ".", scalaEscapeName(field.name.get), ".default)")
        case __Type.Union.uint8(_) => StringTree("setByte(", offset, ", value, ", path, ".", scalaEscapeName(field.name.get), ".default)")
        case __Type.Union.uint16(_) => StringTree("setShort(", offset, ", value, ", path, ".", scalaEscapeName(field.name.get), ".default)")
        case __Type.Union.uint32(_) => StringTree("setInt(", offset, ", value, ", path, ".", scalaEscapeName(field.name.get), ".default)")
        case __Type.Union.uint64(_) => StringTree("setLong(", offset, ", value, ", path, ".", scalaEscapeName(field.name.get), ".default)")
        case __Type.Union.float32(_) => StringTree("setFloat(", offset, ", value, ", path, ".", scalaEscapeName(field.name.get), ".default)")
        case __Type.Union.float64(_) => StringTree("setDouble(", offset, ", value, ", path, ".", scalaEscapeName(field.name.get), ".default)")
        case __Type.Union.text(_) => StringTree("setString(", offset, ", value)")
        case __Type.Union.data(_) => StringTree("setData(", offset, ", value)")
        case __Type.Union.list(_) => StringTree("setNone()")
        case __Type.Union.__enum(_) => StringTree("setShort(", offset, ", value.id.toShort, ", path, ".", scalaEscapeName(field.name.get), ".default.map(_.id))")
        case __Type.Union.__struct(_) => StringTree("setNone()")
        case __Type.Union.interface(_) => StringTree("setNone()")
        case __Type.Union.__object(_) => StringTree("setNone()")
        case _ => throw new IllegalArgumentException("Unknown ctype: " + ctype)
      }
    }

    def printDouble(value: Double): String = {
      value match {
        case Double.PositiveInfinity => "Double.PositiveInfinity"
        case Double.NegativeInfinity => "Double.NegativeInfinity"
        case _ if value.isNaN => "Double.NaN"
        case _ => value.toString
      }
    }

    def genListValue(listType: __Type.List, list: CapnpList, scope: Node): StringTree = {
      StringTree(
        "Seq[", genType(listType.elementType.get, scope), "](",
        listType.elementType.get.switch match {
          case __Type.Union.void(_) => StringTree("Unit")
          case __Type.Union.bool(_) => list.toSeq(_.getBoolean).map(_.toString).mkString(", ")
          case __Type.Union.int8(_) => list.toSeq(_.getByte).map(_.toString + ".toByte").mkString(", ")
          case __Type.Union.int16(_) => list.toSeq(_.getShort).map(_.toString + ".toShort").mkString(", ")
          case __Type.Union.int32(_) => list.toSeq(_.getInt).map(_.toString).mkString(", ")
          case __Type.Union.int64(_) => list.toSeq(_.getLong).map(_.toString + "L").mkString(", ")
          case __Type.Union.uint8(_) => list.toSeq(_.getByte).map(_.toString + ".toByte").mkString(", ")
          case __Type.Union.uint16(_) => list.toSeq(_.getShort).map(_.toString + ".toShort").mkString(", ")
          case __Type.Union.uint32(_) => list.toSeq(_.getInt).map(_.toString).mkString(", ")
          case __Type.Union.uint64(_) => list.toSeq(_.getLong).map(_.toString + "L").mkString(", ")
          case __Type.Union.float32(_) => list.toSeq(_.getFloat).map(f => printDouble(f.toDouble) + ".toFloat").mkString(", ")
          case __Type.Union.float64(_) => list.toSeq(_.getDouble).map(printDouble(_)).mkString(", ")
          case __Type.Union.text(_) => StringTree.join(", ", list.toSeq(_.getString).map(value => {
            StringTree("\"", value, "\"")
          }))
          case __Type.Union.data(_) => StringTree.join(", ", list.toSeq(_.getData).map(value => {
            StringTree("Array[Byte](", value.mkString(", "), ")")
          }))
          case __Type.Union.list(sublistType) => list.toPointerSeq.map(_ match {
            case sublist: CapnpList => genListValue(sublistType, sublist, scope)
            case p => throw new IllegalArgumentException("Expected Pointer of type CapnpList but got: " + p)
          }).mkString(", ")
          case __Type.Union.__enum(enumType) => {
            val node = getDependency(scope, enumType.typeId.get)
            node.switch match {
              case Node.Union.__enum(enumDef) => StringTree.join(", ", list.toSeq(_.getShort).map(value => {
                StringTree(nodeName(node), ".", enumDef.enumerants(value.toInt).name.get)
              }))
              case n => throw new IllegalArgumentException("Expected enum but got: " + n)
            }
          }
          case __Type.Union.__struct(structType) => StringTree.join(", ", list.toPointerSeq.map(value => {
            value match {
              case struct: CapnpStruct => {
                val arena = new CapnpArenaBuilder()
                val (segment, offset) = arena.getRoot
                struct.copyToSegment(segment, offset)
                val bytes = arena.getBytes
                StringTree(
                  "CapnpArena.fromBytes(Array[Byte](",
                    bytes.map(_.toString).mkString(", "),
                  ")).get.getRoot(", nodeName(getDependency(scope, structType.typeId.get)), ").get"
                )
              }
              case p => throw new IllegalArgumentException("Expected Pointer of type CapnpStruct but got: " + p)
            }
          }))
          case __Type.Union.interface(_) => StringTree("TODO")
          case __Type.Union.__object(_) => StringTree("TODO")
          case _ => throw new IllegalArgumentException("")
        },
        ")"
      )
    }

    def genValue(ctype: __Type, value: Value, scope: Node): StringTree = {
      ctype.switch match {
        case __Type.Union.void(_) => StringTree("Unit")
        case __Type.Union.bool(_) => StringTree(if (value.bool.exists(x => x)) "true" else "false")
        case __Type.Union.int8(_) => StringTree(value.int8.getOrElse(0.toByte) + ".toByte")
        case __Type.Union.int16(_) => StringTree(value.int16.getOrElse(0.toShort) + ".toShort")
        case __Type.Union.int32(_) => StringTree(value.int32.getOrElse(0))
        case __Type.Union.int64(_) => StringTree(value.int64.getOrElse(0L) + "L")
        case __Type.Union.uint8(_) => StringTree(value.uint8.getOrElse(0.toByte) + ".toByte")
        case __Type.Union.uint16(_) => StringTree(value.uint16.getOrElse(0.toShort) + ".toShort")
        case __Type.Union.uint32(_) => StringTree(value.uint32.getOrElse(0))
        case __Type.Union.uint64(_) => StringTree(value.uint64.getOrElse(0L) + "L")
        case __Type.Union.float32(_) => StringTree(printDouble(value.float32.getOrElse(0.0f).toDouble) + ".toFloat")
        case __Type.Union.float64(_) => StringTree(printDouble(value.float64.getOrElse(0.0).toDouble))
        case __Type.Union.text(_) => StringTree("\"", value.text.getOrElse(""), "\"")
        case __Type.Union.data(_) => StringTree("Array[Byte](", value.data.getOrElse(Array()).mkString(", "), ")")
        case __Type.Union.list(listType) => value.__object.map(_ match {
          case list: CapnpList => genListValue(listType, list, scope)
          case p => throw new IllegalArgumentException("Expected Pointer of type CapnpList but got: " + p)
        }).getOrElse(StringTree("null"))
        case __Type.Union.__enum(_) => StringTree(genType(ctype, scope), ".findByIdOrNull(", value.uint16.getOrElse(0.toShort), ".toShort)")
        case __Type.Union.__struct(_) => value.__object.map(_ match {
          case struct: CapnpStruct => {
            val arena = new CapnpArenaBuilder()
            val (segment, offset) = arena.getRoot
            struct.copyToSegment(segment, offset)
            val bytes = arena.getBytes
            StringTree(
              "CapnpArena.fromBytes(Array[Byte](",
                bytes.map(_.toString).mkString(", "),
              ")).get.getRoot(", nodeName(getDependency(scope, ctype.__struct.typeId.get)), ").get"
            )
          }
          case p => throw new IllegalArgumentException("Expected Pointer of type CapnpStruct but got: " + p)
        }).getOrElse(StringTree("null"))
        case __Type.Union.interface(_) => StringTree("null")
        case __Type.Union.__object(_) => StringTree("null")
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
      file.annotations.find(_.id.get == -2365685817485540979L).map(_.value.get.text.get + ".").getOrElse("")
    }

    def nodePath(target: Node): StringTree = {
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
      StringTree(getScalaPackageName(fileNode), path)
    }
    def nodeName(target: Node): StringTree = {
      StringTree(nodePath(target), getUnqualifiedName(target))
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

    def genGroupDecl(schema: Node, name: String, path: StringTree, scopeId: Long, indent: StringTreeIndent): StringTree = {
      genDecl(schema, scalaCaseName(name), path, scopeId, indent)
    }

    def genDecl(schema: Node, nameRaw: String, path: StringTree, scopeId: Long, indent: StringTreeIndent): StringTree = {
      val name = scalaEscapeName(nameRaw)
      val fullPath = StringTree(path, name)
      schema.switch match {
        case Node.Union.__struct(struct) => {
          val unionPath = StringTree(fullPath, ".Union")
          val mutablePath = StringTree(fullPath, "Mutable")
          val builderPath = StringTree(fullPath, ".Builder")
          val fields = struct.__fields.sortBy(_.codeOrder)
          val unionFields = fields.filter(_.discriminantValue.isDefined)
          val groupFields = fields.filter(_.switch match { case Field.Union.group(_) => true; case _ => false})
          StringTree(
            indent, "object ", name, " extends MetaStruct[", name, "] {\n",
            indent.next, "override type Self = ", name, ".type\n",
            indent.next, "override val structName: String = \"", nameRaw, "\"\n",
            indent.next, "override def create(struct: CapnpStruct): ", name, " = new ", name, "Mutable(struct)\n",
            indent.next, "def newBuilder(arena: CapnpArenaBuilder): ", builderPath, " = {\n",
            indent.next.next, "val (segment, pointerOffset) = arena.allocate(1)\n",
            indent.next.next, "val struct = CapnpStructBuilder(arena, segment, pointerOffset, ", builderPath, ".dataSectionSizeWords, ", builderPath, ".pointerSectionSizeWords)\n",
            indent.next.next, "new ", builderPath, "(struct)\n",
            indent.next, "}\n",
            "\n",
            indent.next, "object Builder extends MetaStructBuilder[", fullPath, ", ", builderPath, "] {\n",
            indent.next.next, "override type Self = ", builderPath, ".type\n",
            indent.next.next, "override val structName: String = \"", nameRaw, "\"\n",
            indent.next.next, "override val dataSectionSizeWords: Short = ", struct.dataWordCount.getOrElse(0), "\n",
            indent.next.next, "override val pointerSectionSizeWords: Short = ", struct.pointerCount.getOrElse(0), "\n",
            indent.next.next, "override def create(struct: CapnpStructBuilder): ", builderPath, " = new ", builderPath, "(struct)\n",
            indent.next.next, "override def fields: Seq[UntypedFieldDescriptor] = ", fullPath, ".fields\n",
            indent.next, "}\n",
            indent.next, "class Builder(override val struct: CapnpStructBuilder) extends ", mutablePath, "(struct) with StructBuilder[", fullPath, ", ", builderPath, "] {\n",
            indent.next.next, "override type MetaBuilderT = ", builderPath, ".type\n\n",
            indent.next.next, "override def meta: ", name, ".type = ", name, "\n",
            indent.next.next, "override def metaBuilder: MetaBuilderT = ", builderPath, "\n",
            fields.map(field => StringTree(
              field.switch match {
                case Field.Union.slot(slot) => slot.__type.get.switch match {
                  case __Type.Union.list(list) => list.elementType.get.switch match {
                    case __Type.Union.__struct(_) => StringTree(
                      indent.next.next, "def init", scalaCaseName(scalaEscapeName(field.name.get)), "(count: Int): Seq[", genType(list.elementType.get, schema), ".Builder] = {\n",
                      indent.next.next.next, "val list = struct.initPointerList(", slot.offset.getOrElse(0), ", count, ", genType(list.elementType.get, schema), ".Builder)\n",
                      indent.next.next.next, "Range(0, count).map(i => new ", genType(list.elementType.get, schema), ".Builder(list.initStruct(i, ", genType(list.elementType.get, schema), ".Builder)))\n",
                      indent.next.next, "}\n",
                      indent.next.next, "def set", scalaCaseName(scalaEscapeName(field.name.get)), "(buildFn: CapnpArenaBuilder => Seq[", genType(list.elementType.get, schema), ".Builder]): Builder = { ",
                      "struct.setStructList(", slot.offset.getOrElse(0), ", ", genType(list.elementType.get, schema), ".Builder, buildFn(struct.arena).map(_.struct)); ",
                      "this }\n"
                    )
                    case _ => StringTree()
                  }
                  case _ => StringTree(
                    indent.next.next, "def set", scalaCaseName(scalaEscapeName(field.name.get)), "(value: ", fieldScalaType(field, schema), "): Builder = { ",
                    "struct.", genSetter(slot.__type.get, fullPath, field, schema, slot.offset.map(_.toInt).getOrElse(0)), "; ",
                    field.discriminantValue.map(d => StringTree(
                      "struct.setShort(", struct.discriminantOffset.getOrElse(0), ", ", d, "); "
                    )),
                    "this }\n"
                  )
                }
                case Field.Union.group(group) => StringTree(
                  indent.next.next, "override def ", scalaEscapeName(field.name.get), ": ", fieldScalaType(field, schema), ".Builder = new ", fieldScalaType(field, schema), ".Builder(struct)\n"
                )
                case _ => throw new IllegalArgumentException("Unknown: " + field.switch)
              }
            )),
            indent.next, "}\n\n",
            genNestedDecls(schema, indent.next),
            if (unionFields.isEmpty) StringTree() else StringTree(
              indent.next, "sealed trait Union extends UnionValue[", unionPath, "]\n",
              indent.next, "object Union extends UnionMeta[", unionPath, "] {\n",
              indent.next.next, "case class Unknown(discriminant: Short) extends ", unionPath, "\n",
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
                ") extends ", unionPath, "\n"
              )),
              indent.next, "}\n\n"
            ),
            groupFields.map(groupField => {
              val groupNode = getDependency(schema, groupField.group.typeId.get)
              genGroupDecl(groupNode, groupField.name.get, StringTree(fullPath, "."), groupNode.scopeId.get, indent.next)
            }),
            StringTree.join("\n", fields.map(field => StringTree(
              indent.next, "val ", scalaEscapeName(field.name.get), " = new FieldDescriptor[", fieldScalaType(field, schema), ", ", name, ", ", name, ".type](\n",
              indent.next.next, "name = \"", field.name.get, "\",\n",
              indent.next.next, "meta = ", name, ",\n",
              indent.next.next, "default = ",
              field.switch match {
                case Field.Union.slot(slot) => {
                  // TODO(dan): Figure out why hadExplicitDefault doesn't seem
                  // to be set and use it.
                  if (true /*slot.hadExplicitDefault.exists(x => x)*/) {
                    StringTree("Option(", genValue(slot.__type.get, slot.defaultValue.get, schema), ")")
                  } else StringTree("None")
                }
                case Field.Union.group(group) => {
                  StringTree("None")
                }
                case _ => throw new IllegalArgumentException("Unknown: " + field.switch)
              },
              ",\n",
              indent.next.next, "getter = ",
              field.switch match {
                case Field.Union.slot(slot) => slot.__type.get.switch match {
                  case __Type.Union.list(_) => StringTree("x => Some(x.", scalaEscapeName(field.name.get), ")")
                  case _ => StringTree("_.", scalaEscapeName(field.name.get))
                }
                case Field.Union.group(group) => StringTree("x => Some(x.", scalaEscapeName(field.name.get), ")")
                case _ => throw new IllegalArgumentException("Unknown: " + field.switch)
              },
              ",\n",
              indent.next.next, "manifest = manifest[", fieldScalaType(field, schema), "],\n",
              indent.next.next, "discriminantValue = ", field.discriminantValue.toString, "\n",
              indent.next, ")\n"
            ))),
            indent.next, "override val fields: Seq[FieldDescriptor[_, ", name, ", ", name, ".type]] = Seq(",
            StringTree.join(", ", fields.map(field => StringTree(scalaEscapeName(field.name.get)))),
            ")\n",
            indent, "}\n\n",


            indent, "trait ", name, " extends Struct[", name, "]",
            if (unionFields.isEmpty) StringTree() else StringTree(" with HasUnion[", unionPath, "]"),
            " {\n",
            indent.next, "override type MetaT = ", name, ".type\n",
            indent.next, "override type MetaBuilderT = ", builderPath, ".type\n",
            "\n",
            indent.next, "override def meta: ", name, ".type = ", name, "\n",
            indent.next, "override def metaBuilder: ", builderPath, ".type = ", builderPath, "\n",
            "\n",
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
            indent, "}\n\n",


            indent, "class ", name, "Mutable(override val struct: CapnpStruct) extends ", name, " {\n",
            if (unionFields.isEmpty) StringTree() else StringTree(
              indent.next, "override def discriminant: Short = (struct.getShort(", struct.discriminantOffset.getOrElse(0).toString, ").getOrElse(0.toShort): Short)\n",
              indent.next, "override def switch: ", unionPath, " = discriminant match {\n",
              unionFields.zipWithIndex.map({ case (unionField, i) => StringTree(
                indent.next.next, "case ", i.toString, " => ", unionPath, ".", scalaEscapeName(unionField.name.get), "(", scalaEscapeName(unionField.name.get), ")\n"
              )}),
              indent.next.next, "case d => ", unionPath, ".Unknown(d)\n",
              indent.next, "}\n",
              indent.next, "override def union: UnionMeta[", unionPath, "] = ", unionPath, "\n",
              "\n"
            ),
            StringTree.join("\n", fields.map(field => StringTree(
              indent.next, "override def ", scalaEscapeName(field.name.get), ": ",
              field.switch match {
                case Field.Union.slot(slot) => slot.__type.get.switch match {
                  case __Type.Union.list(_) => StringTree(
                    fieldScalaType(field, schema), " = struct.", genAccessor(slot.__type.get, fullPath, field, schema, slot.offset.map(_.toInt).getOrElse(0))
                  )
                  case _ => StringTree(
                    "Option[", fieldScalaType(field, schema), "] = struct.", genAccessor(slot.__type.get, fullPath, field, schema, slot.offset.map(_.toInt).getOrElse(0))
                  )
                }
                case Field.Union.group(group) => StringTree(
                  fieldScalaType(field, schema), " = new ", fullPath, ".", scalaEscapeName(scalaCaseName(field.name.get)), "Mutable(struct)\n"
                )
                case _ => throw new IllegalArgumentException("Unknown: " + field.switch)
              }
            ))), "\n",
            indent, "}\n"
          )
        }
        case Node.Union.__enum(enum) => {
          val exhaustiveMatch = schema.annotations.exists(_.id.exists(_ == -4568588524881625937L))
          StringTree(
            indent, "object ", name, " extends EnumMeta[", name, "] {\n",
            genNestedDecls(schema, indent.next),
            if (exhaustiveMatch) StringTree() else StringTree(
              indent.next, "case class Unknown(override val id: Short) extends ", name, "(", name, ", id, null)\n\n"
            ),
            StringTree.join("\n", enum.enumerants.sortBy(_.codeOrder).zipWithIndex.map({ case (e, i) => StringTree(
              indent.next, "val ", e.name.get, " = new ", name, "(this, ", i.toString, ".toShort, \"", e.name.get, "\")"
            )})), "\n\n",
            indent.next, "override val values = Vector(\n",
            StringTree.join(",\n", enum.enumerants.sortBy(_.codeOrder).map(e => StringTree(indent.next.next, e.name.get))), "\n",
            indent.next, ")\n\n",
            indent.next, "override def findByIdOrNull(id: Short): ", name, " = values.lift(id.toInt).getOrElse(null)\n",
            indent.next, "override def findByNameOrNull(name: String): ", name, " = null\n",
            indent, "}\n\n",
            indent, "sealed class ", name, "(\n",
            indent.next, "override val meta: EnumMeta[", name, "],\n",
            indent.next, "override val id: Short,\n",
            indent.next, "override val name: String\n",
            indent, ") extends Enum[", name, "]\n"
          )
        }
        case Node.Union.const(const) => StringTree(
          indent, "val ", scalaCaseName(name), ": ", genType(const.__type.get, schema), " = ", genValue(const.__type.get, const.value.get, schema), "\n"
        )
        case Node.Union.interface(interface) => {
          StringTree(
            indent, "object ", name, " extends MetaInterface[", name, "] {\n",
            indent.next, "override type Self = ", name, ".type\n",
            indent.next, "override val interfaceName: String = \"", nameRaw, "\"\n",
            "\n",
            StringTree.join("\n", interface.methods.map(method => {
              val methodName = scalaCaseName(scalaEscapeName(method.name.get))
              val methodPath = StringTree(fullPath, ".", methodName)
              val paramNode = getDependency(schema, method.paramStructType.get)
              val resultNode = getDependency(schema, method.resultStructType.get)
              StringTree(
                indent.next, "object ", methodName, " {\n",
                genDecl(paramNode, "Request", StringTree(methodPath, "."), schema.id.get, indent.next.next),
                genDecl(resultNode, "Response", StringTree(methodPath, "."), schema.id.get, indent.next.next),
                indent.next, "}\n"
              )
            })),
            "\n",
            StringTree.join("\n", interface.methods.map(method => StringTree(
              indent.next, "val ", scalaEscapeName(method.name.get), " = new MethodDescriptor[", fullPath, ".", scalaCaseName(scalaEscapeName(method.name.get)), ".Request", ", ", fullPath, ".", scalaCaseName(scalaEscapeName(method.name.get)), ".Response", ", ", name, ", ", name, ".type](\n",
              indent.next.next, "name = \"", method.name.get, "\",\n",
              indent.next.next, "meta = ", name, ",\n",
              indent.next.next, "request = ", fullPath, ".", scalaCaseName(scalaEscapeName(method.name.get)), ".Request", ",\n",
              indent.next.next, "response = ", fullPath, ".", scalaCaseName(scalaEscapeName(method.name.get)), ".Response", ",\n",
              indent.next.next, "getter = _.", scalaEscapeName(method.name.get), " _\n",
              indent.next, ")\n"
            ))),
            indent.next, "override val methods: Seq[MethodDescriptor[_, _, ", name, ", ", name, ".type]] = Seq(",
            StringTree.join(", ", interface.methods.map(method => StringTree(scalaEscapeName(method.name.get)))),
            ")\n",
            indent, "}\n",


            indent, "trait ", name, " extends Interface[", name, "] {\n",
            indent.next, "override type MetaT = ", name, ".type\n",
            indent.next, "override def meta: ", name, ".type = ", name, "\n",
            "\n",
            interface.methods.map(method => StringTree(
              indent.next, "def ", scalaEscapeName(method.name.get), "(request: ", name, ".", scalaCaseName(scalaEscapeName(method.name.get)), ".Request, responseArena: CapnpArenaBuilder): Future[", name, ".", scalaCaseName(scalaEscapeName(method.name.get)), ".Response]\n"
            )),
            indent, "}\n"
          )
        }
        case Node.Union.annotation(_) => StringTree()
        case s => throw new IllegalArgumentException("Unknown schema: " + s)
      }
    }

    def genNestedDecls(schema: Node, indent: StringTreeIndent): StringTree = {
      val id = schema.id.get;
      StringTree.join("\n", schema.nestedNodes.map(nested => {
        val node = schemasById(nested.id.get)
        genDecl(node, nested.name.get, nodePath(node), id, indent)
      }).toSeq)
    }

    def genFile(file: Node): StringTree = {
      val filePackage = getScalaPackageName(file).dropRight(1)
      StringTree(
        "// ", file.displayName, "\n\n",
        if (filePackage.isEmpty) StringTree() else StringTree("package ", filePackage, "\n\n"),
        "import com.capnproto.core.{HasUnion, UnionMeta, UnionValue, UntypedFieldDescriptor,\n",
        "  FieldDescriptor, UntypedStruct, Struct, UntypedMetaStruct, MetaStruct,\n",
        "  StructBuilder, MetaStructBuilder, MetaInterface, UntypedMetaInterface,\n",
        "  Interface, UntypedInterface, MethodDescriptor, CapnpStruct, CapnpStructBuilder,\n",
        "  Pointer, CapnpList, CapnpTag, CapnpArenaBuilder, CapnpArena, Enum, EnumMeta}\n",
        "import com.twitter.util.Future\n",
        "import java.nio.ByteBuffer\n",
        "\n",
        genNestedDecls(file, new StringTreeIndent(0, "  "))
      )
    }

    parsed.requestedFiles.map(requestedFile => {
      val outputFile = new File(requestedFile.filename.get + ".scala")
      outputFile.getParentFile.mkdirs
      val output = genFile(schemasById(requestedFile.id.get)).flatten
      val source = new FileWriter(outputFile)
      source.write(output)
      source.close
      println("Wrote " + outputFile.getAbsolutePath)
    })
  }
}
