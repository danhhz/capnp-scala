// examples/src/main/scala/com/capnproto/addressbook.capnp

package foo

import com.foursquare.spindle.{Enum, EnumMeta}
import com.capnproto.{HasUnion, UnionMeta, UnionValue, UntypedFieldDescriptor, FieldDescriptor, UntypedStruct, Struct, UntypedMetaStruct, MetaStruct, StructBuilder, MetaStructBuilder}
import com.capnproto.{CapnpStruct, CapnpStructBuilder, Pointer => CapnpPointer, CapnpList, CapnpTag}
import java.nio.ByteBuffer

object Person extends MetaStruct[Person] {
  override type Self = Person.type
  override val recordName: String = "Person"
  override def create(struct: CapnpStruct): Person = new PersonMutable(struct)
  override val fields: Seq[FieldDescriptor[_, Person, Person.type]] = Seq(id, name, email, phones, employment)

  object Builder extends MetaStructBuilder[foo.Person, foo.Person.Builder] {
    override type Self = foo.Person.Builder.type
    override val recordName: String = "Person"
    override val dataSectionSizeWords: Short = 1
    override val pointerSectionSizeWords: Short = 4
    override def create(struct: CapnpStructBuilder): foo.Person.Builder = new foo.Person.Builder(struct)
    override def fields: Seq[UntypedFieldDescriptor] = foo.Person.fields
  }
  class Builder(override val struct: CapnpStructBuilder) extends foo.PersonMutable(struct) with StructBuilder[foo.Person, foo.Person.Builder] {
    override type MetaBuilderT = foo.Person.Builder.type

    override def meta: Person.type = Person
    override def metaBuilder: MetaBuilderT = foo.Person.Builder
    def setId(value: java.lang.Integer): Builder = { struct.setInt(0, value); this }
    def setName(value: String): Builder = { struct.setString(0, value); this }
    def setEmail(value: String): Builder = { struct.setString(1, value); this }
    def setPhones(value: Seq[foo.Person.PhoneNumber]): Builder = { struct.setNone(); this }
    def initPhones(count: Int): Seq[foo.Person.PhoneNumber.Builder] = {
      val list = struct.initPointerList(2, count, foo.Person.PhoneNumber.Builder)
      Range(0, count).map(i => new foo.Person.PhoneNumber.Builder(list.initStruct(i, foo.Person.PhoneNumber.Builder)))
    }
    override def employment: Option[foo.Person.Employment.Builder] = Some(new foo.Person.Employment.Builder(struct))
  }

  object PhoneNumber extends MetaStruct[PhoneNumber] {
    override type Self = PhoneNumber.type
    override val recordName: String = "PhoneNumber"
    override def create(struct: CapnpStruct): PhoneNumber = new PhoneNumberMutable(struct)
    override val fields: Seq[FieldDescriptor[_, PhoneNumber, PhoneNumber.type]] = Seq(number, __type)

    object Builder extends MetaStructBuilder[foo.Person.PhoneNumber, foo.Person.PhoneNumber.Builder] {
      override type Self = foo.Person.PhoneNumber.Builder.type
      override val recordName: String = "PhoneNumber"
      override val dataSectionSizeWords: Short = 1
      override val pointerSectionSizeWords: Short = 1
      override def create(struct: CapnpStructBuilder): foo.Person.PhoneNumber.Builder = new foo.Person.PhoneNumber.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = foo.Person.PhoneNumber.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends foo.Person.PhoneNumberMutable(struct) with StructBuilder[foo.Person.PhoneNumber, foo.Person.PhoneNumber.Builder] {
      override type MetaBuilderT = foo.Person.PhoneNumber.Builder.type

      override def meta: PhoneNumber.type = PhoneNumber
      override def metaBuilder: MetaBuilderT = foo.Person.PhoneNumber.Builder
      def setNumber(value: String): Builder = { struct.setString(0, value); this }
      def set__Type(value: foo.Person.PhoneNumber.__Type): Builder = { struct.setShort(0, value.id.toShort); this }
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
      meta = PhoneNumber
    )

    val __type = new FieldDescriptor[foo.Person.PhoneNumber.__Type, PhoneNumber, PhoneNumber.type](
      name = "type",
      meta = PhoneNumber
    )
  }

  trait PhoneNumber extends Struct[PhoneNumber] {
    override type MetaT = PhoneNumber.type

    override def meta: PhoneNumber.type = PhoneNumber
    def struct: CapnpStruct

    def number: Option[String]
    def __type: Option[foo.Person.PhoneNumber.__Type]
  }

  trait PhoneNumberProxy extends PhoneNumber {
    def underlying: PhoneNumber

    override def struct: CapnpStruct = underlying.struct

    override def number: Option[String]
    override def __type: Option[foo.Person.PhoneNumber.__Type]
  }

  class PhoneNumberMutable(override val struct: CapnpStruct) extends PhoneNumber {

    override def number: Option[String] = struct.getString(0)
    override def __type: Option[foo.Person.PhoneNumber.__Type] = struct.getShort(0).map(id => foo.Person.PhoneNumber.__Type.findById(id.toInt).getOrElse(foo.Person.PhoneNumber.__Type.Unknown(id.toShort)))
  }

  object Employment extends MetaStruct[Employment] {
    override type Self = Employment.type
    override val recordName: String = "Employment"
    override def create(struct: CapnpStruct): Employment = new EmploymentMutable(struct)
    override val fields: Seq[FieldDescriptor[_, Employment, Employment.type]] = Seq(unemployed, employer, school, selfEmployed)

    object Builder extends MetaStructBuilder[foo.Person.Employment, foo.Person.Employment.Builder] {
      override type Self = foo.Person.Employment.Builder.type
      override val recordName: String = "Employment"
      override val dataSectionSizeWords: Short = 1
      override val pointerSectionSizeWords: Short = 4
      override def create(struct: CapnpStructBuilder): foo.Person.Employment.Builder = new foo.Person.Employment.Builder(struct)
      override def fields: Seq[UntypedFieldDescriptor] = foo.Person.Employment.fields
    }
    class Builder(override val struct: CapnpStructBuilder) extends foo.Person.EmploymentMutable(struct) with StructBuilder[foo.Person.Employment, foo.Person.Employment.Builder] {
      override type MetaBuilderT = foo.Person.Employment.Builder.type

      override def meta: Employment.type = Employment
      override def metaBuilder: MetaBuilderT = foo.Person.Employment.Builder
      def setUnemployed(value: Unit): Builder = { struct.setNone(); struct.setShort(2, -1); this }
      def setEmployer(value: String): Builder = { struct.setString(3, value); struct.setShort(2, -2); this }
      def setSchool(value: String): Builder = { struct.setString(3, value); struct.setShort(2, -3); this }
      def setSelfEmployed(value: Unit): Builder = { struct.setNone(); struct.setShort(2, -4); this }
    }

    sealed trait Union extends UnionValue[foo.Person.Employment.Union]
    object Union extends UnionMeta[foo.Person.Employment.Union] {
      case class Unknown(discriminant: Short) extends foo.Person.Employment.Union
      case class unemployed(value: Option[Unit]) extends foo.Person.Employment.Union
      case class employer(value: Option[String]) extends foo.Person.Employment.Union
      case class school(value: Option[String]) extends foo.Person.Employment.Union
      case class selfEmployed(value: Option[Unit]) extends foo.Person.Employment.Union
    }



    val unemployed = new FieldDescriptor[Unit, Employment, Employment.type](
      name = "unemployed",
      meta = Employment
    )

    val employer = new FieldDescriptor[String, Employment, Employment.type](
      name = "employer",
      meta = Employment
    )

    val school = new FieldDescriptor[String, Employment, Employment.type](
      name = "school",
      meta = Employment
    )

    val selfEmployed = new FieldDescriptor[Unit, Employment, Employment.type](
      name = "selfEmployed",
      meta = Employment
    )
  }

  trait Employment extends Struct[Employment] with HasUnion[foo.Person.Employment.Union] {
    override type MetaT = Employment.type

    override def meta: Employment.type = Employment
    def struct: CapnpStruct

    def unemployed: Option[Unit]
    def employer: Option[String]
    def school: Option[String]
    def selfEmployed: Option[Unit]
  }

  trait EmploymentProxy extends Employment with HasUnion[foo.Person.Employment.Union] {
    def underlying: Employment

    override def struct: CapnpStruct = underlying.struct

    override def switch: foo.Person.Employment.Union = underlying.switch
    override def union: UnionMeta[foo.Person.Employment.Union] = underlying.union

    override def unemployed: Option[Unit]
    override def employer: Option[String]
    override def school: Option[String]
    override def selfEmployed: Option[Unit]
  }

  class EmploymentMutable(override val struct: CapnpStruct) extends Employment {

    override def discriminant: Short = (struct.getShort(2).getOrElse(new java.lang.Short(0.toShort)): java.lang.Short)
    override def switch: foo.Person.Employment.Union = discriminant match {
      case 0 => foo.Person.Employment.Union.unemployed(unemployed)
      case 1 => foo.Person.Employment.Union.employer(employer)
      case 2 => foo.Person.Employment.Union.school(school)
      case 3 => foo.Person.Employment.Union.selfEmployed(selfEmployed)
      case d => foo.Person.Employment.Union.Unknown(d)
    }
    override def union: UnionMeta[foo.Person.Employment.Union] = foo.Person.Employment.Union

    override def unemployed: Option[Unit] = struct.getNone()
    override def employer: Option[String] = struct.getString(3)
    override def school: Option[String] = struct.getString(3)
    override def selfEmployed: Option[Unit] = struct.getNone()
  }

  val id = new FieldDescriptor[java.lang.Integer, Person, Person.type](
    name = "id",
    meta = Person
  )

  val name = new FieldDescriptor[String, Person, Person.type](
    name = "name",
    meta = Person
  )

  val email = new FieldDescriptor[String, Person, Person.type](
    name = "email",
    meta = Person
  )

  val phones = new FieldDescriptor[Seq[foo.Person.PhoneNumber], Person, Person.type](
    name = "phones",
    meta = Person
  )

  val employment = new FieldDescriptor[foo.Person.Employment, Person, Person.type](
    name = "employment",
    meta = Person
  )
}

trait Person extends Struct[Person] {
  override type MetaT = Person.type

  override def meta: Person.type = Person
  def struct: CapnpStruct

  def id: Option[java.lang.Integer]
  def name: Option[String]
  def email: Option[String]
  def phones: Option[Seq[foo.Person.PhoneNumber]]
  def employment: Option[foo.Person.Employment]
}

trait PersonProxy extends Person {
  def underlying: Person

  override def struct: CapnpStruct = underlying.struct

  override def id: Option[java.lang.Integer]
  override def name: Option[String]
  override def email: Option[String]
  override def phones: Option[Seq[foo.Person.PhoneNumber]]
  override def employment: Option[foo.Person.Employment]
}

class PersonMutable(override val struct: CapnpStruct) extends Person {

  override def id: Option[java.lang.Integer] = struct.getInt(0)
  override def name: Option[String] = struct.getString(0)
  override def email: Option[String] = struct.getString(1)
  override def phones: Option[Seq[foo.Person.PhoneNumber]] = struct.getStructList(2).map(_.map(new foo.Person.PhoneNumberMutable(_)))
  override def employment: Option[foo.Person.Employment] = Some(new foo.Person.EmploymentMutable(struct))

}

object AddressBook extends MetaStruct[AddressBook] {
  override type Self = AddressBook.type
  override val recordName: String = "AddressBook"
  override def create(struct: CapnpStruct): AddressBook = new AddressBookMutable(struct)
  override val fields: Seq[FieldDescriptor[_, AddressBook, AddressBook.type]] = Seq(people)

  object Builder extends MetaStructBuilder[foo.AddressBook, foo.AddressBook.Builder] {
    override type Self = foo.AddressBook.Builder.type
    override val recordName: String = "AddressBook"
    override val dataSectionSizeWords: Short = 0
    override val pointerSectionSizeWords: Short = 1
    override def create(struct: CapnpStructBuilder): foo.AddressBook.Builder = new foo.AddressBook.Builder(struct)
    override def fields: Seq[UntypedFieldDescriptor] = foo.AddressBook.fields
  }
  class Builder(override val struct: CapnpStructBuilder) extends foo.AddressBookMutable(struct) with StructBuilder[foo.AddressBook, foo.AddressBook.Builder] {
    override type MetaBuilderT = foo.AddressBook.Builder.type

    override def meta: AddressBook.type = AddressBook
    override def metaBuilder: MetaBuilderT = foo.AddressBook.Builder
    def setPeople(value: Seq[foo.Person]): Builder = { struct.setNone(); this }
    def initPeople(count: Int): Seq[foo.Person.Builder] = {
      val list = struct.initPointerList(0, count, foo.Person.Builder)
      Range(0, count).map(i => new foo.Person.Builder(list.initStruct(i, foo.Person.Builder)))
    }
  }



  val people = new FieldDescriptor[Seq[foo.Person], AddressBook, AddressBook.type](
    name = "people",
    meta = AddressBook
  )
}

trait AddressBook extends Struct[AddressBook] {
  override type MetaT = AddressBook.type

  override def meta: AddressBook.type = AddressBook
  def struct: CapnpStruct

  def people: Option[Seq[foo.Person]]
}

trait AddressBookProxy extends AddressBook {
  def underlying: AddressBook

  override def struct: CapnpStruct = underlying.struct

  override def people: Option[Seq[foo.Person]]
}

class AddressBookMutable(override val struct: CapnpStruct) extends AddressBook {

  override def people: Option[Seq[foo.Person]] = struct.getStructList(0).map(_.map(new foo.PersonMutable(_)))
}
