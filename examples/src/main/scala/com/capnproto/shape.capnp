# shape.capnp
@0xeec85b7137407fa2;
struct Shape @0x83dec065fb0d8baa {  # 32 bytes, 0 ptrs
  area @0 :Float64;  # bits[0, 64)
  union {  # tag bits [128, 144)
    circle :group {  # union tag = 0
      radius @1 :Float64;  # bits[64, 128)
    }
    rectangle :group {  # union tag = 1
      width @2 :Float64;  # bits[64, 128)
      height @3 :Float64;  # bits[192, 256)
    }
  }
}
