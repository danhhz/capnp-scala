# Copyright 2013 Daniel Harrison. All Rights Reserved.

@0xd56250b943e601c8;

using Scala = import "scala.capnp";
$Scala.namespace("com.capnp.examples.interface");

# TODO(dan): Write a commandline rpc tool so these aren't needed. Or bug Kenton.
struct Foo {
  key @0 :Text;
}
struct Bar {
  key @0 :Text;
  value @1 :Text;
}

interface KeyValueStore {
  get @0 (key: Text) -> (value: Text);
  put @1 (key: Text, value: Text) -> ();
}