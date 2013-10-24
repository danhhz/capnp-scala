// examples/src/main/scala/com/capnproto/addressbook.capnp

package com.capnproto.addressbook

import com.foursquare.spindle.{Enum, EnumMeta}
import com.capnproto.{HasUnion, UnionMeta, UnionValue, UntypedFieldDescriptor,
  FieldDescriptor, UntypedStruct, Struct, UntypedMetaStruct, MetaStruct,
  StructBuilder, MetaStructBuilder, MetaInterface, UntypedMetaInterface,
  Interface, UntypedInterface, MethodDescriptor, CapnpStruct, CapnpStructBuilder,
  Pointer, CapnpList, CapnpTag, CapnpArenaBuilder}
import com.twitter.util.Future
import java.nio.ByteBuffer

object Person extends MetaStruct[Person] {
  override type Self = Person.type
  override val structName: String = "Person"
  override def create(struct: CapnpStruct): Person = new PersonMutable(struct)
  def newBuilder(arena: CapnpArenaBuilder): com.capnproto.addressbook.Person.Builder = {
    val (segment, pointerOffset) = arena.allocate(1)
    val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.addressbook.Person.Builder.dataSectionSizeWords, com.capnproto.addressbook.Person.Builder.pointerSectionSizeWords)
    new com.capnproto.addressbook.Person.Builder(struct)
  }

  object Builder extends MetaStructBuilder[com.capnproto.addressbook.Person, com.capnproto.addressbook.Person.Builder] {
    override type Self = com.capnproto.addressbook.Person.Builder.type
    override val structName: String = "Person"
    override val dataSectionSizeWords: Short = 1
    override val pointerSectionSizeWords: Short = 4
    override def create(struct: CapnpStructBuilder): com.capnproto.addressbook.Person.Builder = new com.capnproto.addressbook.Person.Builder(struct)
    override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.addressbook.Person.fields
  }
  class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.addressbook.PersonMutable(struct) with StructBuilder[com.capnproto.addressbook.Person, com.capnproto.addressbook.Person.Builder] {
    override type MetaBuilderT = com.capnproto.addressbook.Person.Builder.type

    override def meta: Person.type = Person
    override def metaBuilder: MetaBuilderT = com.capnproto.addressbook.Person.Builder
    def setId(value: java.lang.Integer): Builder = { struct.setInt(0, value); this }
    def setName(value: String): Builder = { struct.setString(0, value); this }
    def setEmail(value: String): Builder = { struct.setString(1, value); this }
    def initPhones(count: Int): Seq[com.capnproto.addressbook.Person.PhoneNumber.Builder] = {
      val list = struct.initPointerList(2, count, com.capnproto.addressbook.Person.PhoneNumber.Builder)
      Range(0, count).map(i => new com.capnproto.addressbook.Person.PhoneNumber.Builder(list.initStruct(i, com.capnproto.addressbook.Person.PhoneNumber.Builder)))
    }
    def setPhones(buildFn: CapnpArenaBuilder => Seq[com.capnproto.addressbook.Person.PhoneNumber.Builder]): Builder = { struct.setStructList(2, com.capnproto.addressbook.Person.PhoneNumber.Builder, buildFn(struct.arena).map(_.struct)); this }
    override def employment: com.capnproto.addressbook.Person.Employment.Builder = new com.capnproto.addressbook.Person.Employment.Builder(struct)
  }

  object PhoneNumber extends MetaStruct[PhoneNumber] {
    override type Self = PhoneNumber.type
    override val structName: String = "PhoneNumber"
    override def create(struct: CapnpStruct): PhoneNumber = new PhoneNumberMutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.addressbook.Person.PhoneNumber.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.addressbook.Person.PhoneNumber.Builder.dataSectionSizeWords, com.capnproto.addressbook.Person.PhoneNumber.Builder.pointerSectionSizeWords)
      new com.capnproto.addressbook.Person.PhoneNumber.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.addressbook.Person.PhoneNumber, com.capnproto.addressbook.Person.PhoneNumber.Builder] {
      override type Self = com.capnproto.addressbook.Person.PhoneNumber.Builder.type
      override val structName: String = "PhoneNumber"
      override val dataSectionSizeWords: Short = 1
      override val pointerSectionSizeWords: Short = 1
      override def create(struct: CapnpStructBuilder): com.capnproto.addressbook.Person.PhoneNumber.Builder = new com.capnproto.addressbook.Person.PhoneNumber.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.addressbook.Person.PhoneNumber.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.addressbook.Person.PhoneNumberMutable(struct) with StructBuilder[com.capnproto.addressbook.Person.PhoneNumber, com.capnproto.addressbook.Person.PhoneNumber.Builder] {
      override type MetaBuilderT = com.capnproto.addressbook.Person.PhoneNumber.Builder.type

      override def meta: PhoneNumber.type = PhoneNumber
      override def metaBuilder: MetaBuilderT = com.capnproto.addressbook.Person.PhoneNumber.Builder
      def setNumber(value: String): Builder = { struct.setString(0, value); this }
      def set__Type(value: com.capnproto.addressbook.Person.PhoneNumber.__Type): Builder = { struct.setShort(0, value.id.toShort); this }
    }

    object __Type extends EnumMeta[__Type] {
      case class Unknown(override val id: Int) extends __Type(__Type, id, null, null)

      val mobile = new __Type(this, 0, "mobile", "mobile")
      val home = new __Type(this, 1, "home", "home")
      val work = new __Type(this, 2, "work", "work")

      override val values = Vector(
        mobile,
        home,
        work
      )

      override def findByIdOrNull(id: Int): __Type = values.lift(id).getOrElse(null)
      override def findByNameOrNull(name: String): __Type = null
      override def findByStringValueOrNull(v: String): __Type = null
    }

    sealed class __Type(
      override val meta: EnumMeta[__Type],
      override val id: Int,
      override val name: String,
      override val stringValue: String
    ) extends Enum[__Type]
    val number = new FieldDescriptor[String, PhoneNumber, PhoneNumber.type](
      name = "number",
      meta = PhoneNumber,
      getter = _.number,
      manifest = manifest[String],
      isUnion = false
    )

    val __type = new FieldDescriptor[com.capnproto.addressbook.Person.PhoneNumber.__Type, PhoneNumber, PhoneNumber.type](
      name = "type",
      meta = PhoneNumber,
      getter = _.__type,
      manifest = manifest[com.capnproto.addressbook.Person.PhoneNumber.__Type],
      isUnion = false
    )
    override val fields: Seq[FieldDescriptor[_, PhoneNumber, PhoneNumber.type]] = Seq(number, __type)
  }

  trait PhoneNumber extends Struct[PhoneNumber] {
    override type MetaT = PhoneNumber.type
    override type MetaBuilderT = com.capnproto.addressbook.Person.PhoneNumber.Builder.type

    override def meta: PhoneNumber.type = PhoneNumber
    override def metaBuilder: com.capnproto.addressbook.Person.PhoneNumber.Builder.type = com.capnproto.addressbook.Person.PhoneNumber.Builder

    def struct: CapnpStruct

    def number: Option[String]
    def __type: Option[com.capnproto.addressbook.Person.PhoneNumber.__Type]
  }

  class PhoneNumberMutable(override val struct: CapnpStruct) extends PhoneNumber {
    override def number: Option[String] = struct.getString(0)
    override def __type: Option[com.capnproto.addressbook.Person.PhoneNumber.__Type] = struct.getShort(0).map(id => com.capnproto.addressbook.Person.PhoneNumber.__Type.findById(id.toInt).getOrElse(com.capnproto.addressbook.Person.PhoneNumber.__Type.Unknown(id.toShort)))
  }
  object Employment extends MetaStruct[Employment] {
    override type Self = Employment.type
    override val structName: String = "Employment"
    override def create(struct: CapnpStruct): Employment = new EmploymentMutable(struct)
    def newBuilder(arena: CapnpArenaBuilder): com.capnproto.addressbook.Person.Employment.Builder = {
      val (segment, pointerOffset) = arena.allocate(1)
      val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.addressbook.Person.Employment.Builder.dataSectionSizeWords, com.capnproto.addressbook.Person.Employment.Builder.pointerSectionSizeWords)
      new com.capnproto.addressbook.Person.Employment.Builder(struct)
    }

    object Builder extends MetaStructBuilder[com.capnproto.addressbook.Person.Employment, com.capnproto.addressbook.Person.Employment.Builder] {
      override type Self = com.capnproto.addressbook.Person.Employment.Builder.type
      override val structName: String = "Employment"
      override val dataSectionSizeWords: Short = 1
      override val pointerSectionSizeWords: Short = 4
      override def create(struct: CapnpStructBuilder): com.capnproto.addressbook.Person.Employment.Builder = new com.capnproto.addressbook.Person.Employment.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.addressbook.Person.Employment.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.addressbook.Person.EmploymentMutable(struct) with StructBuilder[com.capnproto.addressbook.Person.Employment, com.capnproto.addressbook.Person.Employment.Builder] {
      override type MetaBuilderT = com.capnproto.addressbook.Person.Employment.Builder.type

      override def meta: Employment.type = Employment
      override def metaBuilder: MetaBuilderT = com.capnproto.addressbook.Person.Employment.Builder
      def setUnemployed(value: Unit): Builder = { struct.setNone(); struct.setShort(2, -1); this }
      def setEmployer(value: String): Builder = { struct.setString(3, value); struct.setShort(2, -2); this }
      def setSchool(value: String): Builder = { struct.setString(3, value); struct.setShort(2, -3); this }
      def setSelfEmployed(value: Unit): Builder = { struct.setNone(); struct.setShort(2, -4); this }
    }

    sealed trait Union extends UnionValue[com.capnproto.addressbook.Person.Employment.Union]
    object Union extends UnionMeta[com.capnproto.addressbook.Person.Employment.Union] {
      case class Unknown(discriminant: Short) extends com.capnproto.addressbook.Person.Employment.Union
      case class unemployed(value: Option[Unit]) extends com.capnproto.addressbook.Person.Employment.Union
      case class employer(value: Option[String]) extends com.capnproto.addressbook.Person.Employment.Union
      case class school(value: Option[String]) extends com.capnproto.addressbook.Person.Employment.Union
      case class selfEmployed(value: Option[Unit]) extends com.capnproto.addressbook.Person.Employment.Union
    }

    val unemployed = new FieldDescriptor[Unit, Employment, Employment.type](
      name = "unemployed",
      meta = Employment,
      getter = _.unemployed,
      manifest = manifest[Unit],
      isUnion = true
    )

    val employer = new FieldDescriptor[String, Employment, Employment.type](
      name = "employer",
      meta = Employment,
      getter = _.employer,
      manifest = manifest[String],
      isUnion = true
    )

    val school = new FieldDescriptor[String, Employment, Employment.type](
      name = "school",
      meta = Employment,
      getter = _.school,
      manifest = manifest[String],
      isUnion = true
    )

    val selfEmployed = new FieldDescriptor[Unit, Employment, Employment.type](
      name = "selfEmployed",
      meta = Employment,
      getter = _.selfEmployed,
      manifest = manifest[Unit],
      isUnion = true
    )
    override val fields: Seq[FieldDescriptor[_, Employment, Employment.type]] = Seq(unemployed, employer, school, selfEmployed)
  }

  trait Employment extends Struct[Employment] with HasUnion[com.capnproto.addressbook.Person.Employment.Union] {
    override type MetaT = Employment.type
    override type MetaBuilderT = com.capnproto.addressbook.Person.Employment.Builder.type

    override def meta: Employment.type = Employment
    override def metaBuilder: com.capnproto.addressbook.Person.Employment.Builder.type = com.capnproto.addressbook.Person.Employment.Builder

    def struct: CapnpStruct

    def unemployed: Option[Unit]
    def employer: Option[String]
    def school: Option[String]
    def selfEmployed: Option[Unit]
  }

  class EmploymentMutable(override val struct: CapnpStruct) extends Employment {
    override def discriminant: Short = (struct.getShort(2).getOrElse(new java.lang.Short(0.toShort)): java.lang.Short)
    override def switch: com.capnproto.addressbook.Person.Employment.Union = discriminant match {
      case 0 => com.capnproto.addressbook.Person.Employment.Union.unemployed(unemployed)
      case 1 => com.capnproto.addressbook.Person.Employment.Union.employer(employer)
      case 2 => com.capnproto.addressbook.Person.Employment.Union.school(school)
      case 3 => com.capnproto.addressbook.Person.Employment.Union.selfEmployed(selfEmployed)
      case d => com.capnproto.addressbook.Person.Employment.Union.Unknown(d)
    }
    override def union: UnionMeta[com.capnproto.addressbook.Person.Employment.Union] = com.capnproto.addressbook.Person.Employment.Union

    override def unemployed: Option[Unit] = struct.getNone()
    override def employer: Option[String] = struct.getString(3)
    override def school: Option[String] = struct.getString(3)
    override def selfEmployed: Option[Unit] = struct.getNone()
  }
  val id = new FieldDescriptor[java.lang.Integer, Person, Person.type](
    name = "id",
    meta = Person,
    getter = _.id,
    manifest = manifest[java.lang.Integer],
    isUnion = false
  )

  val name = new FieldDescriptor[String, Person, Person.type](
    name = "name",
    meta = Person,
    getter = _.name,
    manifest = manifest[String],
    isUnion = false
  )

  val email = new FieldDescriptor[String, Person, Person.type](
    name = "email",
    meta = Person,
    getter = _.email,
    manifest = manifest[String],
    isUnion = false
  )

  val phones = new FieldDescriptor[Seq[com.capnproto.addressbook.Person.PhoneNumber], Person, Person.type](
    name = "phones",
    meta = Person,
    getter = x => Some(x.phones),
    manifest = manifest[Seq[com.capnproto.addressbook.Person.PhoneNumber]],
    isUnion = false
  )

  val employment = new FieldDescriptor[com.capnproto.addressbook.Person.Employment, Person, Person.type](
    name = "employment",
    meta = Person,
    getter = x => Some(x.employment),
    manifest = manifest[com.capnproto.addressbook.Person.Employment],
    isUnion = false
  )
  override val fields: Seq[FieldDescriptor[_, Person, Person.type]] = Seq(id, name, email, phones, employment)
}

trait Person extends Struct[Person] {
  override type MetaT = Person.type
  override type MetaBuilderT = com.capnproto.addressbook.Person.Builder.type

  override def meta: Person.type = Person
  override def metaBuilder: com.capnproto.addressbook.Person.Builder.type = com.capnproto.addressbook.Person.Builder

  def struct: CapnpStruct

  def id: Option[java.lang.Integer]
  def name: Option[String]
  def email: Option[String]
  def phones: Seq[com.capnproto.addressbook.Person.PhoneNumber]
  def employment: com.capnproto.addressbook.Person.Employment
}

class PersonMutable(override val struct: CapnpStruct) extends Person {
  override def id: Option[java.lang.Integer] = struct.getInt(0)
  override def name: Option[String] = struct.getString(0)
  override def email: Option[String] = struct.getString(1)
  override def phones: Seq[com.capnproto.addressbook.Person.PhoneNumber] = struct.getStructList(2).map(new com.capnproto.addressbook.Person.PhoneNumberMutable(_))
  override def employment: com.capnproto.addressbook.Person.Employment = new com.capnproto.addressbook.Person.EmploymentMutable(struct)

}

object AddressBook extends MetaStruct[AddressBook] {
  override type Self = AddressBook.type
  override val structName: String = "AddressBook"
  override def create(struct: CapnpStruct): AddressBook = new AddressBookMutable(struct)
  def newBuilder(arena: CapnpArenaBuilder): com.capnproto.addressbook.AddressBook.Builder = {
    val (segment, pointerOffset) = arena.allocate(1)
    val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.addressbook.AddressBook.Builder.dataSectionSizeWords, com.capnproto.addressbook.AddressBook.Builder.pointerSectionSizeWords)
    new com.capnproto.addressbook.AddressBook.Builder(struct)
  }

  object Builder extends MetaStructBuilder[com.capnproto.addressbook.AddressBook, com.capnproto.addressbook.AddressBook.Builder] {
    override type Self = com.capnproto.addressbook.AddressBook.Builder.type
    override val structName: String = "AddressBook"
    override val dataSectionSizeWords: Short = 0
    override val pointerSectionSizeWords: Short = 1
    override def create(struct: CapnpStructBuilder): com.capnproto.addressbook.AddressBook.Builder = new com.capnproto.addressbook.AddressBook.Builder(struct)
    override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.addressbook.AddressBook.fields
  }
  class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.addressbook.AddressBookMutable(struct) with StructBuilder[com.capnproto.addressbook.AddressBook, com.capnproto.addressbook.AddressBook.Builder] {
    override type MetaBuilderT = com.capnproto.addressbook.AddressBook.Builder.type

    override def meta: AddressBook.type = AddressBook
    override def metaBuilder: MetaBuilderT = com.capnproto.addressbook.AddressBook.Builder
    def initPeople(count: Int): Seq[com.capnproto.addressbook.Person.Builder] = {
      val list = struct.initPointerList(0, count, com.capnproto.addressbook.Person.Builder)
      Range(0, count).map(i => new com.capnproto.addressbook.Person.Builder(list.initStruct(i, com.capnproto.addressbook.Person.Builder)))
    }
    def setPeople(buildFn: CapnpArenaBuilder => Seq[com.capnproto.addressbook.Person.Builder]): Builder = { struct.setStructList(0, com.capnproto.addressbook.Person.Builder, buildFn(struct.arena).map(_.struct)); this }
  }

  val people = new FieldDescriptor[Seq[com.capnproto.addressbook.Person], AddressBook, AddressBook.type](
    name = "people",
    meta = AddressBook,
    getter = x => Some(x.people),
    manifest = manifest[Seq[com.capnproto.addressbook.Person]],
    isUnion = false
  )
  override val fields: Seq[FieldDescriptor[_, AddressBook, AddressBook.type]] = Seq(people)
}

trait AddressBook extends Struct[AddressBook] {
  override type MetaT = AddressBook.type
  override type MetaBuilderT = com.capnproto.addressbook.AddressBook.Builder.type

  override def meta: AddressBook.type = AddressBook
  override def metaBuilder: com.capnproto.addressbook.AddressBook.Builder.type = com.capnproto.addressbook.AddressBook.Builder

  def struct: CapnpStruct

  def people: Seq[com.capnproto.addressbook.Person]
}

class AddressBookMutable(override val struct: CapnpStruct) extends AddressBook {
  override def people: Seq[com.capnproto.addressbook.Person] = struct.getStructList(0).map(new com.capnproto.addressbook.PersonMutable(_))
}
