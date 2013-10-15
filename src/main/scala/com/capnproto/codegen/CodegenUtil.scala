// Copyright 2013 Daniel Harrison. All Rights Reserved.

package com.capnproto.codegen

object CodegenUtil {
  // List of Scala reserved words from Scala Language Specification (SLS) Section 1.1
  val ScalaReservedWords = Set(
    "abstract", "case", "class", "def", "do", "else", "extends", "false", "final", "finally", "for", "forSome",
    "if", "implicit", "import", "lazy", "match", "new", "null", "object", "override", "package", "private",
    "protected", "return", "sealed", "super", "this", "throw", "trait", "try", "true", "type", "val", "var", "while",
    "with", "yield"
  )

  val RecordReservedWords = Set(
    "enum", "copy", "meta", "union", "switch", "struct", "fields", "recordname"
  )

  val ReservedWords = ScalaReservedWords ++ RecordReservedWords
}
