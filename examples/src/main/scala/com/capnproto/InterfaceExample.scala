// Copyright 2013 Daniel Harrison. All Rights Reserved.

package com.capnp.examples.interface

import com.capnp.core.CapnpArenaBuilder
import com.capnp.rpc.RpcServer
import com.twitter.finatra.{Controller, FinatraServer, Request}
import com.twitter.server.Admin
import com.twitter.util.Future

class KeyValueStoreImpl extends KeyValueStore {
  val store = scala.collection.mutable.HashMap[String, String]()
  store.put("hi", "hi dan")
  override def get(request: KeyValueStore.Get.Request, responseArena: CapnpArenaBuilder): Future[KeyValueStore.Get.Response] = {
    val response = responseArena.getRootBuilder(KeyValueStore.Get.Response.Builder)
    request.key.flatMap(store.get(_)).foreach(response.setValue(_))
    Future(response)
  }
  override def put(request: KeyValueStore.Put.Request, responseArena: CapnpArenaBuilder): Future[KeyValueStore.Put.Response] = {
    for {
      key <- request.key
      value <- request.value
    } store.put(key, value)
    val response = responseArena.getRootBuilder(KeyValueStore.Put.Response.Builder)
    Future(response)
  }
}

object KeyValueStoreServer extends RpcServer(new KeyValueStoreImpl)
