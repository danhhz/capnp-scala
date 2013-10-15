// Copyright 2013 Daniel Harrison. All Rights Reserved.

package com.capnproto.codegen

trait StringTree {
  def flatten: String
  override def toString: String = flatten
}

class StringTreeLeaf(override val flatten: String) extends StringTree

class StringTreeNode(children: Seq[StringTree], delim: String) extends StringTree {
  lazy val flatten: String = children.map(_.flatten).mkString(delim)
}

class StringTreeIndent(level: Int, indent: String = "  ") extends StringTree {
  lazy val flatten: String = indent * level
  def next: StringTreeIndent = new StringTreeIndent(level + 1, indent)
}

object StringTree {
  def apply(as: Any*): StringTree = {
    val children: Seq[StringTree] = as.toSeq.map(_ match {
      case st: StringTree => st
      case sts: Seq[_] => StringTree.apply(sts: _*)
      case stOpt: Option[_] => stOpt.map(StringTree(_)).getOrElse(StringTree())
      case s: String => new StringTreeLeaf(s)
      case s => new StringTreeLeaf(s.toString)
    })
    new StringTreeNode(children, "")
  }

  def join(sep: String, xs: Seq[StringTree]): StringTree = {
    StringTree(xs.flatMap(x => Seq(sep, x)).drop(1))
  }
}
