#!/usr/bin/env python3
"""bundle.py — pack payloads into bundle.dat (see README for format).

Usage: python3 bundle.py <payload.bin> <vape_client.jar> [out=bundle.dat]
"""
import hashlib
import struct
import sys

MAGIC = 0x56424C44  # "VBDL"


def main(files, out="bundle.dat"):
    blobs = []
    for path, name in files:
        data = open(path, "rb").read()
        blobs.append((name, data))
        print(f"{name}: {len(data)} bytes sha256={hashlib.sha256(data).hexdigest()}")
    # header
    index = struct.pack("<III", MAGIC, 1, len(blobs))
    offset = 12
    # index size first: each = 1 + namelen + 8 + 8 + 32
    for name, data in blobs:
        offset += 1 + len(name.encode()) + 8 + 8 + 32
    for name, data in blobs:
        nb = name.encode()
        index += struct.pack("<B", len(nb)) + nb
        index += struct.pack("<QQ", offset, len(data))
        index += hashlib.sha256(data).digest()
        offset += len(data)
    with open(out, "wb") as f:
        f.write(index)
        for _, data in blobs:
            f.write(data)
    print(f"wrote {out} ({offset} bytes)")


if __name__ == "__main__":
    args = sys.argv[1:]
    if len(args) < 2:
        sys.exit("usage: bundle.py <payload.bin> <vape_client.jar> [out]")
    out = args[2] if len(args) > 2 else "bundle.dat"
    main([(args[0], "payload.bin"), (args[1], "vape_client.jar")], out)
