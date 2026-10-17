#!/usr/bin/env python3
"""embed.py — append a bundle to an executable, making a single-file injector.

    embed.py <loader> <bundle.dat> <out>
    embed.py --check <file> [<bundle.dat>]     (verify what a file carries)

Layout:

    <loader bytes> <bundle bytes> <trailer>

    trailer = b"VAPEEMB\\0"  (8 bytes)
              u64 offset     (where the bundle starts, LE)
              u64 size       (bundle length, LE)

The loader reads its own image, checks the last 24 bytes and, if the magic
is there, reads the bundle straight out of itself — no external file. See
find_embedded_bundle() in src/injector/loader.c.

On macOS the appended bytes invalidate the linker's ad-hoc signature, so
re-sign afterwards:  codesign --force --sign - <out>
"""
import hashlib
import os
import struct
import sys

MAGIC = b"VAPEEMB\x00"
TRAILER = 24


def embed(loader_path, bundle_path, out):
    loader = open(loader_path, "rb").read()
    bundle = open(bundle_path, "rb").read()
    offset = len(loader)
    with open(out, "wb") as f:
        f.write(loader)
        f.write(bundle)
        f.write(MAGIC + struct.pack("<QQ", offset, len(bundle)))
    os.chmod(out, 0o755)
    print(f"wrote {out}: {len(loader)} B loader + {len(bundle)} B bundle "
          f"(bundle at offset {offset}, sha256 "
          f"{hashlib.sha256(bundle).hexdigest()})")


def check(path, bundle_path=None):
    """Report the bundle a single-file injector carries, and optionally
    confirm it is byte-for-byte the bundle it was built from."""
    data = open(path, "rb").read()
    if len(data) <= TRAILER or data[-TRAILER:-TRAILER + 8] != MAGIC:
        sys.exit(f"{path}: no embedded bundle")
    offset, size = struct.unpack("<QQ", data[-16:])
    if offset + size > len(data) - TRAILER:
        sys.exit(f"{path}: trailer out of range")
    blob = data[offset:offset + size]
    print(f"{path}: {len(data)} B total, bundle {size} B at offset {offset}, "
          f"sha256 {hashlib.sha256(blob).hexdigest()}")
    if bundle_path:
        if open(bundle_path, "rb").read() != blob:
            sys.exit(f"{path}: embedded bundle does not match {bundle_path}")
        print(f"embedded bundle matches {bundle_path}")


if __name__ == "__main__":
    if len(sys.argv) >= 3 and sys.argv[1] == "--check":
        check(sys.argv[2], sys.argv[3] if len(sys.argv) == 4 else None)
    elif len(sys.argv) == 4:
        embed(sys.argv[1], sys.argv[2], sys.argv[3])
    else:
        sys.exit("usage: embed.py <loader> <bundle.dat> <out>\n"
                 "       embed.py --check <file> [<bundle.dat>]")
