captnproto-scala
================

[PREQUISITE] /bin/capnp-json compiled from [https://github.com/paperstreet/capnproto-json](https://github.com/paperstreet/capnproto-json)

    sbt assembly
    capnp compile -o./capnp-identity.sh addressbook.capnp
    cat addressbook.capnp.identity
