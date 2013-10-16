// Copyright 2013 Daniel Harrison. All Rights Reserved.

package com.capnproto

import foo.{AddressBook, Person}

object AddressBookExample {

  def readAddressBook: Unit = {
    val addressbook = Segments.fromInputStream(System.in).asStruct(AddressBook)
      .getOrElse(throw new IllegalArgumentException("Couldn't parse stdin as CodeGeneratorRequest"))
    addressbook.people.getOrElse(Nil).foreach(person => {
      println(person.name.getOrElse("") + ": " + person.email.getOrElse(""))
      person.phones.getOrElse(Nil).foreach(phone => {
        val phoneType = phone.__type match {
          case Some(Person.PhoneNumber.__Type.mobile) => "mobile"
          case Some(Person.PhoneNumber.__Type.home) => "home"
          case Some(Person.PhoneNumber.__Type.work) => "work"
          case _ => "UNKNOWN"
        }
        println("  "+ phoneType+ " phone: " + phone.number.getOrElse(""))
      })
      person.employment.get.switch match {
        case Person.Employment.Union.unemployed(_) => println("  unemployed")
        case Person.Employment.Union.employer(employer) => println("  employer: " + employer.getOrElse(""))
        case Person.Employment.Union.school(school) => println("  student at: " + school.getOrElse(""))
        case Person.Employment.Union.selfEmployed(_) => println("  self-employed")
        case Person.Employment.Union.Unknown(_) => println("  UNKNOWN")
      }
    })
  }

  def printUsage: Unit = {
    println("Usage AddressBookExample read|write")
    System.exit(1)
  }

	def main(args: Array[String]): Unit = {
    args(0) match {
      case "read" => readAddressBook
      case _ => printUsage
    }
  }
}
