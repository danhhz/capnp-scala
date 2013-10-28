// Copyright 2013 Daniel Harrison. All Rights Reserved.

package com.capnproto

abstract class Enum[T <: Enum[T]] extends Ordered[T] { self: T =>
  def meta: EnumMeta[T]

  def id: java.lang.Short
  def name: String

  override def toString: String = name

  // Implementation of Ordered[T].
  override def compare(other: T) = this.id.toShort.compare(other.id)
}

abstract class EnumMeta[T <: Enum[T]] {
  def values: Vector[T]

  def findByIdOrNull(id: java.lang.Short): T
  def findByNameOrNull(name: String): T

  def findById(id: java.lang.Short): Option[T] = Option(findByIdOrNull(id))
  def findByName(name: String): Option[T] = Option(findByNameOrNull(name))

  def apply(id: java.lang.Short): Option[T] = findById(id)

  def unapply(name: String): Option[T] = findByName(name)
}
