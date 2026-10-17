#!/usr/bin/env python3
"""embed.py — append a bundle to an executable, making a single-file injector.

    embed.py <loader> <bundle.dat> <out>

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
          f"{__import__('hashlib').sha256(bundle).hexdigest()})")


if __name__ == "__main__":
    if len(sys.argv) != 4:
        sys.exit("usage: embed.py <loader> <bundle.dat> <out>")
    embed(sys.argv[1], sys.argv[2], sys.argv[3])
