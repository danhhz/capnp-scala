// examples/src/main/scala/com/capnproto/interface.capnp

package com.capnproto.examples.interface

import com.capnproto.{HasUnion, UnionMeta, UnionValue, UntypedFieldDescriptor,
  FieldDescriptor, UntypedStruct, Struct, UntypedMetaStruct, MetaStruct,
  StructBuilder, MetaStructBuilder, MetaInterface, UntypedMetaInterface,
  Interface, UntypedInterface, MethodDescriptor, CapnpStruct, CapnpStructBuilder,
  Pointer, CapnpList, CapnpTag, CapnpArenaBuilder, CapnpArena, Enum, EnumMeta}
import com.twitter.util.Future
import java.nio.ByteBuffer

object Foo extends MetaStruct[Foo] {
  override type Self = Foo.type
  override val structName: String = "Foo"
  override def create(struct: CapnpStruct): Foo = new FooMutable(struct)
  def newBuilder(arena: CapnpArenaBuilder): com.capnproto.examples.interface.Foo.Builder = {
    val (segment, pointerOffset) = arena.allocate(1)
    val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.examples.interface.Foo.Builder.dataSectionSizeWords, com.capnproto.examples.interface.Foo.Builder.pointerSectionSizeWords)
    new com.capnproto.examples.interface.Foo.Builder(struct)
  }

  object Builder extends MetaStructBuilder[com.capnproto.examples.interface.Foo, com.capnproto.examples.interface.Foo.Builder] {
    override type Self = com.capnproto.examples.interface.Foo.Builder.type
    override val structName: String = "Foo"
    override val dataSectionSizeWords: Short = 0
    override val pointerSectionSizeWords: Short = 1
    override def create(struct: CapnpStructBuilder): com.capnproto.examples.interface.Foo.Builder = new com.capnproto.examples.interface.Foo.Builder(struct)
    override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.examples.interface.Foo.fields
  }
  class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.examples.interface.FooMutable(struct) with StructBuilder[com.capnproto.examples.interface.Foo, com.capnproto.examples.interface.Foo.Builder] {
    override type MetaBuilderT = com.capnproto.examples.interface.Foo.Builder.type

    override def meta: Foo.type = Foo
    override def metaBuilder: MetaBuilderT = com.capnproto.examples.interface.Foo.Builder
    def setKey(value: String): Builder = { struct.setString(0, value); this }
  }

  val key = new FieldDescriptor[String, Foo, Foo.type](
    name = "key",
    meta = Foo,
    getter = _.key,
    manifest = manifest[String],
    isUnion = false
  )
  override val fields: Seq[FieldDescriptor[_, Foo, Foo.type]] = Seq(key)
}

trait Foo extends Struct[Foo] {
  override type MetaT = Foo.type
  override type MetaBuilderT = com.capnproto.examples.interface.Foo.Builder.type

  override def meta: Foo.type = Foo
  override def metaBuilder: com.capnproto.examples.interface.Foo.Builder.type = com.capnproto.examples.interface.Foo.Builder

  def struct: CapnpStruct

  def key: Option[String]
}

class FooMutable(override val struct: CapnpStruct) extends Foo {
  override def key: Option[String] = struct.getString(0)
}

object Bar extends MetaStruct[Bar] {
  override type Self = Bar.type
  override val structName: String = "Bar"
  override def create(struct: CapnpStruct): Bar = new BarMutable(struct)
  def newBuilder(arena: CapnpArenaBuilder): com.capnproto.examples.interface.Bar.Builder = {
    val (segment, pointerOffset) = arena.allocate(1)
    val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.examples.interface.Bar.Builder.dataSectionSizeWords, com.capnproto.examples.interface.Bar.Builder.pointerSectionSizeWords)
    new com.capnproto.examples.interface.Bar.Builder(struct)
  }

  object Builder extends MetaStructBuilder[com.capnproto.examples.interface.Bar, com.capnproto.examples.interface.Bar.Builder] {
    override type Self = com.capnproto.examples.interface.Bar.Builder.type
    override val structName: String = "Bar"
    override val dataSectionSizeWords: Short = 0
    override val pointerSectionSizeWords: Short = 2
    override def create(struct: CapnpStructBuilder): com.capnproto.examples.interface.Bar.Builder = new com.capnproto.examples.interface.Bar.Builder(struct)
    override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.examples.interface.Bar.fields
  }
  class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.examples.interface.BarMutable(struct) with StructBuilder[com.capnproto.examples.interface.Bar, com.capnproto.examples.interface.Bar.Builder] {
    override type MetaBuilderT = com.capnproto.examples.interface.Bar.Builder.type

    override def meta: Bar.type = Bar
    override def metaBuilder: MetaBuilderT = com.capnproto.examples.interface.Bar.Builder
    def setKey(value: String): Builder = { struct.setString(0, value); this }
    def setValue(value: String): Builder = { struct.setString(1, value); this }
  }

  val key = new FieldDescriptor[String, Bar, Bar.type](
    name = "key",
    meta = Bar,
    getter = _.key,
    manifest = manifest[String],
    isUnion = false
  )

  val value = new FieldDescriptor[String, Bar, Bar.type](
    name = "value",
    meta = Bar,
    getter = _.value,
    manifest = manifest[String],
    isUnion = false
  )
  override val fields: Seq[FieldDescriptor[_, Bar, Bar.type]] = Seq(key, value)
}

trait Bar extends Struct[Bar] {
  override type MetaT = Bar.type
  override type MetaBuilderT = com.capnproto.examples.interface.Bar.Builder.type

  override def meta: Bar.type = Bar
  override def metaBuilder: com.capnproto.examples.interface.Bar.Builder.type = com.capnproto.examples.interface.Bar.Builder

  def struct: CapnpStruct

  def key: Option[String]
  def value: Option[String]
}

class BarMutable(override val struct: CapnpStruct) extends Bar {
  override def key: Option[String] = struct.getString(0)
  override def value: Option[String] = struct.getString(1)
}

object KeyValueStore extends MetaInterface[KeyValueStore] {
  override type Self = KeyValueStore.type
  override val interfaceName: String = "KeyValueStore"

  object Get {
    object Request extends MetaStruct[Request] {
      override type Self = Request.type
      override val structName: String = "Request"
      override def create(struct: CapnpStruct): Request = new RequestMutable(struct)
      def newBuilder(arena: CapnpArenaBuilder): com.capnproto.examples.interface.KeyValueStore.Get.Request.Builder = {
        val (segment, pointerOffset) = arena.allocate(1)
        val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.examples.interface.KeyValueStore.Get.Request.Builder.dataSectionSizeWords, com.capnproto.examples.interface.KeyValueStore.Get.Request.Builder.pointerSectionSizeWords)
        new com.capnproto.examples.interface.KeyValueStore.Get.Request.Builder(struct)
      }

      object Builder extends MetaStructBuilder[com.capnproto.examples.interface.KeyValueStore.Get.Request, com.capnproto.examples.interface.KeyValueStore.Get.Request.Builder] {
        override type Self = com.capnproto.examples.interface.KeyValueStore.Get.Request.Builder.type
        override val structName: String = "Request"
        override val dataSectionSizeWords: Short = 0
        override val pointerSectionSizeWords: Short = 1
        override def create(struct: CapnpStructBuilder): com.capnproto.examples.interface.KeyValueStore.Get.Request.Builder = new com.capnproto.examples.interface.KeyValueStore.Get.Request.Builder(struct)
        override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.examples.interface.KeyValueStore.Get.Request.fields
      }
      class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.examples.interface.KeyValueStore.Get.RequestMutable(struct) with StructBuilder[com.capnproto.examples.interface.KeyValueStore.Get.Request, com.capnproto.examples.interface.KeyValueStore.Get.Request.Builder] {
        override type MetaBuilderT = com.capnproto.examples.interface.KeyValueStore.Get.Request.Builder.type

        override def meta: Request.type = Request
        override def metaBuilder: MetaBuilderT = com.capnproto.examples.interface.KeyValueStore.Get.Request.Builder
        def setKey(value: String): Builder = { struct.setString(0, value); this }
      }

      val key = new FieldDescriptor[String, Request, Request.type](
        name = "key",
        meta = Request,
        getter = _.key,
        manifest = manifest[String],
        isUnion = false
      )
      override val fields: Seq[FieldDescriptor[_, Request, Request.type]] = Seq(key)
    }

    trait Request extends Struct[Request] {
      override type MetaT = Request.type
      override type MetaBuilderT = com.capnproto.examples.interface.KeyValueStore.Get.Request.Builder.type

      override def meta: Request.type = Request
      override def metaBuilder: com.capnproto.examples.interface.KeyValueStore.Get.Request.Builder.type = com.capnproto.examples.interface.KeyValueStore.Get.Request.Builder

      def struct: CapnpStruct

      def key: Option[String]
    }

    class RequestMutable(override val struct: CapnpStruct) extends Request {
      override def key: Option[String] = struct.getString(0)
    }
    object Response extends MetaStruct[Response] {
      override type Self = Response.type
      override val structName: String = "Response"
      override def create(struct: CapnpStruct): Response = new ResponseMutable(struct)
      def newBuilder(arena: CapnpArenaBuilder): com.capnproto.examples.interface.KeyValueStore.Get.Response.Builder = {
        val (segment, pointerOffset) = arena.allocate(1)
        val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.examples.interface.KeyValueStore.Get.Response.Builder.dataSectionSizeWords, com.capnproto.examples.interface.KeyValueStore.Get.Response.Builder.pointerSectionSizeWords)
        new com.capnproto.examples.interface.KeyValueStore.Get.Response.Builder(struct)
      }

      object Builder extends MetaStructBuilder[com.capnproto.examples.interface.KeyValueStore.Get.Response, com.capnproto.examples.interface.KeyValueStore.Get.Response.Builder] {
        override type Self = com.capnproto.examples.interface.KeyValueStore.Get.Response.Builder.type
        override val structName: String = "Response"
        override val dataSectionSizeWords: Short = 0
        override val pointerSectionSizeWords: Short = 1
        override def create(struct: CapnpStructBuilder): com.capnproto.examples.interface.KeyValueStore.Get.Response.Builder = new com.capnproto.examples.interface.KeyValueStore.Get.Response.Builder(struct)
        override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.examples.interface.KeyValueStore.Get.Response.fields
      }
      class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.examples.interface.KeyValueStore.Get.ResponseMutable(struct) with StructBuilder[com.capnproto.examples.interface.KeyValueStore.Get.Response, com.capnproto.examples.interface.KeyValueStore.Get.Response.Builder] {
        override type MetaBuilderT = com.capnproto.examples.interface.KeyValueStore.Get.Response.Builder.type

        override def meta: Response.type = Response
        override def metaBuilder: MetaBuilderT = com.capnproto.examples.interface.KeyValueStore.Get.Response.Builder
        def setValue(value: String): Builder = { struct.setString(0, value); this }
      }

      val value = new FieldDescriptor[String, Response, Response.type](
        name = "value",
        meta = Response,
        getter = _.value,
        manifest = manifest[String],
        isUnion = false
      )
      override val fields: Seq[FieldDescriptor[_, Response, Response.type]] = Seq(value)
    }

    trait Response extends Struct[Response] {
      override type MetaT = Response.type
      override type MetaBuilderT = com.capnproto.examples.interface.KeyValueStore.Get.Response.Builder.type

      override def meta: Response.type = Response
      override def metaBuilder: com.capnproto.examples.interface.KeyValueStore.Get.Response.Builder.type = com.capnproto.examples.interface.KeyValueStore.Get.Response.Builder

      def struct: CapnpStruct

      def value: Option[String]
    }

    class ResponseMutable(override val struct: CapnpStruct) extends Response {
      override def value: Option[String] = struct.getString(0)
    }
  }

  object Put {
    object Request extends MetaStruct[Request] {
      override type Self = Request.type
      override val structName: String = "Request"
      override def create(struct: CapnpStruct): Request = new RequestMutable(struct)
      def newBuilder(arena: CapnpArenaBuilder): com.capnproto.examples.interface.KeyValueStore.Put.Request.Builder = {
        val (segment, pointerOffset) = arena.allocate(1)
        val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.examples.interface.KeyValueStore.Put.Request.Builder.dataSectionSizeWords, com.capnproto.examples.interface.KeyValueStore.Put.Request.Builder.pointerSectionSizeWords)
        new com.capnproto.examples.interface.KeyValueStore.Put.Request.Builder(struct)
      }

      object Builder extends MetaStructBuilder[com.capnproto.examples.interface.KeyValueStore.Put.Request, com.capnproto.examples.interface.KeyValueStore.Put.Request.Builder] {
        override type Self = com.capnproto.examples.interface.KeyValueStore.Put.Request.Builder.type
        override val structName: String = "Request"
        override val dataSectionSizeWords: Short = 0
        override val pointerSectionSizeWords: Short = 2
        override def create(struct: CapnpStructBuilder): com.capnproto.examples.interface.KeyValueStore.Put.Request.Builder = new com.capnproto.examples.interface.KeyValueStore.Put.Request.Builder(struct)
        override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.examples.interface.KeyValueStore.Put.Request.fields
      }
      class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.examples.interface.KeyValueStore.Put.RequestMutable(struct) with StructBuilder[com.capnproto.examples.interface.KeyValueStore.Put.Request, com.capnproto.examples.interface.KeyValueStore.Put.Request.Builder] {
        override type MetaBuilderT = com.capnproto.examples.interface.KeyValueStore.Put.Request.Builder.type

        override def meta: Request.type = Request
        override def metaBuilder: MetaBuilderT = com.capnproto.examples.interface.KeyValueStore.Put.Request.Builder
        def setKey(value: String): Builder = { struct.setString(0, value); this }
        def setValue(value: String): Builder = { struct.setString(1, value); this }
      }

      val key = new FieldDescriptor[String, Request, Request.type](
        name = "key",
        meta = Request,
        getter = _.key,
        manifest = manifest[String],
        isUnion = false
      )

      val value = new FieldDescriptor[String, Request, Request.type](
        name = "value",
        meta = Request,
        getter = _.value,
        manifest = manifest[String],
        isUnion = false
      )
      override val fields: Seq[FieldDescriptor[_, Request, Request.type]] = Seq(key, value)
    }

    trait Request extends Struct[Request] {
      override type MetaT = Request.type
      override type MetaBuilderT = com.capnproto.examples.interface.KeyValueStore.Put.Request.Builder.type

      override def meta: Request.type = Request
      override def metaBuilder: com.capnproto.examples.interface.KeyValueStore.Put.Request.Builder.type = com.capnproto.examples.interface.KeyValueStore.Put.Request.Builder

      def struct: CapnpStruct

      def key: Option[String]
      def value: Option[String]
    }

    class RequestMutable(override val struct: CapnpStruct) extends Request {
      override def key: Option[String] = struct.getString(0)
      override def value: Option[String] = struct.getString(1)
    }
    object Response extends MetaStruct[Response] {
      override type Self = Response.type
      override val structName: String = "Response"
      override def create(struct: CapnpStruct): Response = new ResponseMutable(struct)
      def newBuilder(arena: CapnpArenaBuilder): com.capnproto.examples.interface.KeyValueStore.Put.Response.Builder = {
        val (segment, pointerOffset) = arena.allocate(1)
        val struct = CapnpStructBuilder(arena, segment, pointerOffset, com.capnproto.examples.interface.KeyValueStore.Put.Response.Builder.dataSectionSizeWords, com.capnproto.examples.interface.KeyValueStore.Put.Response.Builder.pointerSectionSizeWords)
        new com.capnproto.examples.interface.KeyValueStore.Put.Response.Builder(struct)
      }

      object Builder extends MetaStructBuilder[com.capnproto.examples.interface.KeyValueStore.Put.Response, com.capnproto.examples.interface.KeyValueStore.Put.Response.Builder] {
        override type Self = com.capnproto.examples.interface.KeyValueStore.Put.Response.Builder.type
        override val structName: String = "Response"
        override val dataSectionSizeWords: Short = 0
        override val pointerSectionSizeWords: Short = 0
        override def create(struct: CapnpStructBuilder): com.capnproto.examples.interface.KeyValueStore.Put.Response.Builder = new com.capnproto.examples.interface.KeyValueStore.Put.Response.Builder(struct)
        override def fields: Seq[UntypedFieldDescriptor] = com.capnproto.examples.interface.KeyValueStore.Put.Response.fields
      }
      class Builder(override val struct: CapnpStructBuilder) extends com.capnproto.examples.interface.KeyValueStore.Put.ResponseMutable(struct) with StructBuilder[com.capnproto.examples.interface.KeyValueStore.Put.Response, com.capnproto.examples.interface.KeyValueStore.Put.Response.Builder] {
        override type MetaBuilderT = com.capnproto.examples.interface.KeyValueStore.Put.Response.Builder.type

        override def meta: Response.type = Response
        override def metaBuilder: MetaBuilderT = com.capnproto.examples.interface.KeyValueStore.Put.Response.Builder
      }

      override val fields: Seq[FieldDescriptor[_, Response, Response.type]] = Seq()
    }

    trait Response extends Struct[Response] {
      override type MetaT = Response.type
      override type MetaBuilderT = com.capnproto.examples.interface.KeyValueStore.Put.Response.Builder.type

      override def meta: Response.type = Response
      override def metaBuilder: com.capnproto.examples.interface.KeyValueStore.Put.Response.Builder.type = com.capnproto.examples.interface.KeyValueStore.Put.Response.Builder

      def struct: CapnpStruct

    }

    class ResponseMutable(override val struct: CapnpStruct) extends Response {

    }
  }

  val get = new MethodDescriptor[com.capnproto.examples.interface.KeyValueStore.Get.Request, com.capnproto.examples.interface.KeyValueStore.Get.Response, KeyValueStore, KeyValueStore.type](
    name = "get",
    meta = KeyValueStore,
    request = com.capnproto.examples.interface.KeyValueStore.Get.Request,
    response = com.capnproto.examples.interface.KeyValueStore.Get.Response,
    getter = _.get _
  )

  val put = new MethodDescriptor[com.capnproto.examples.interface.KeyValueStore.Put.Request, com.capnproto.examples.interface.KeyValueStore.Put.Response, KeyValueStore, KeyValueStore.type](
    name = "put",
    meta = KeyValueStore,
    request = com.capnproto.examples.interface.KeyValueStore.Put.Request,
    response = com.capnproto.examples.interface.KeyValueStore.Put.Response,
    getter = _.put _
  )
  override val methods: Seq[MethodDescriptor[_, _, KeyValueStore, KeyValueStore.type]] = Seq(get, put)
}
trait KeyValueStore extends Interface[KeyValueStore] {
  override type MetaT = KeyValueStore.type
  override def meta: KeyValueStore.type = KeyValueStore

  def get(request: KeyValueStore.Get.Request, responseArena: CapnpArenaBuilder): Future[KeyValueStore.Get.Response]
  def put(request: KeyValueStore.Put.Request, responseArena: CapnpArenaBuilder): Future[KeyValueStore.Put.Response]
}
