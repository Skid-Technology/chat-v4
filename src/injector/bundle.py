#!/usr/bin/env python3
"""bundle.py — pack one or more blobs into a VBDL bundle.

    bundle.py <file>[:<name>] ... [-o out]

Entry names default to the file's base name. The loader extracts entries
by name and sha256-verifies each one, so the bundle is self-checking.

Format (little-endian):
    u32 magic "VBDL" | u32 version | u32 count
    then per entry: u8 name_len | name | u64 offset | u64 size | 32B sha256
    then the payload bytes, in the same order.
"""
import argparse
import hashlib
import os
import struct

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
    ap = argparse.ArgumentParser(description=__doc__)
    ap.add_argument("files", nargs="+", metavar="file[:name]",
                    help="blob to include, optionally with an explicit entry name")
    ap.add_argument("-o", "--out", default="bundle.dat", help="output path")
    a = ap.parse_args()
    entries = []
    for spec in a.files:
        path, _, name = spec.partition(":")
        entries.append((path, name or os.path.basename(path)))
    main(entries, a.out)
