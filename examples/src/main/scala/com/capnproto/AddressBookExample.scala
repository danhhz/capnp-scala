// Copyright 2013 Daniel Harrison. All Rights Reserved.

package com.capnproto

import com.capnproto.addressbook.{AddressBook, Person}

object AddressBookExample {

  def writeAddressBook: Unit = {
    val arena = new CapnpArenaBuilder()
    val addressbook = arena.getRootBuilder(AddressBook.Builder)
    val peopleBuilders = addressbook.initPeople(2)

    val alice = peopleBuilders(0)
      .setId(123)
      .setName("Alice")
      .setEmail("alice@example.com")
    alice.initPhones(1)(0)
      .setNumber("555-1212")
      .set__Type(Person.PhoneNumber.__Type.mobile)
    alice.employment.setSchool("MIT")

    val bob = peopleBuilders(1)
      .setId(456)
     .setName("Bob")
      .setEmail("bob@example.com")
    val bobPhones = bob.initPhones(2)
    bobPhones(0)
      .setNumber("555-4567")
      .set__Type(Person.PhoneNumber.__Type.home)
    bobPhones(1)
      .setNumber("555-7654")
      .set__Type(Person.PhoneNumber.__Type.work)
    bob.employment.setUnemployed()

    arena.writeToOutputStream(new java.io.FileOutputStream("/tmp/addressbook.bin"))
  }

  def readAddressBook: Unit = {
    val addressbook = CapnpArena.fromInputStream(System.in).getRoot(AddressBook)
      .getOrElse(throw new IllegalArgumentException("Couldn't parse stdin as CodeGeneratorRequest"))
    addressbook.people.foreach(person => {
      println(person.name.getOrElse("") + ": " + person.email.getOrElse(""))
      person.phones.foreach(phone => {
        val phoneType = phone.__type match {
          case Some(Person.PhoneNumber.__Type.mobile) => "mobile"
          case Some(Person.PhoneNumber.__Type.home) => "home"
          case Some(Person.PhoneNumber.__Type.work) => "work"
          case _ => "UNKNOWN"
        }
        println("  "+ phoneType+ " phone: " + phone.number.getOrElse(""))
      })
      person.employment.switch match {
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
      case "write" => writeAddressBook
      case "read" => readAddressBook
      case _ => printUsage
    }
  }
}
