// Copyright 2013 Daniel Harrison. All Rights Reserved.

package com.capnp.rpc

import com.capnp.core.{Interface, UntypedMethodDescriptor, CapnpArena, Pointer, CapnpArenaBuilder}
import com.twitter.finatra.{Controller, FinatraServer, Request}
import com.twitter.server.Admin
import com.twitter.util.Future

trait ControllerHelpers {
  def requestRawBytes(request: Request): Array[Byte] = {
    val buffer = request.getContent.toByteBuffer
    val bytes = new Array[Byte](buffer.remaining)
    buffer.get(bytes, buffer.arrayOffset, buffer.remaining)
    bytes
  }
}

class RpcController(val impl: Interface[_]) extends Controller with ControllerHelpers {
  val meta = impl.meta
  val methods: Seq[UntypedMethodDescriptor] = meta.methods

  val prefix = "/rpc/" + meta.interfaceName

  get("/")(request => redirect(prefix).toFuture)
  get("/rpc")(request => redirect(prefix).toFuture)

  get(prefix)(request => render.plain(
    "interface: " + meta.interfaceName + "\n" +
    methods.map(_.name).mkString("\n") + "\n"
  ).toFuture)

  methods.foreach(method => {
    get(prefix + "/" + method.name)(request => render.plain(
        "method: " + method.name + "\n" +
        "request: (" + method.request.fields.map(f => f.name + ": " + f.unsafeManifest.toString).mkString(", ") + ")\n" +
        "response: (" + method.response.fields.map(f => f.name + ": " + f.unsafeManifest.toString).mkString(", ") + ")\n"
    ).toFuture)

    post(prefix + "/" + method.name)(httpRequest => {
      val bytes = requestRawBytes(httpRequest)
      // println(bytes.grouped(8).map(_.map("%02x".format(_)).mkString("")).mkString(" "))
      val responseOpt = for {
        requestArena <- CapnpArena.fromBytes(bytes)
        rpcRequest <- Pointer.parseStructRaw(method.request, requestArena)
      } yield {
        val responseArena = new CapnpArenaBuilder()
        val rpcResponseF = method.unsafeGetter(impl)(rpcRequest, responseArena)
        rpcResponseF.map(_ => {
          render.body(responseArena.getBytes)
        })
      }
      responseOpt.getOrElse(render
        .status(400)
        .body("Could not parse input (" + bytes.length + " bytes) as " + method.request.structName)
        .toFuture
      )
    })
  })
}

class RpcServer(rpc: Interface[_]) extends FinatraServer with Admin {
  register(new RpcController(rpc))
}
