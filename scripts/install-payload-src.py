#!/usr/bin/env python3
"""install-payload-src.py — install a decompile as the vendored payload source.

    install-payload-src.py <decompiled_dir> <client_jar> <out_dir>

Writes:

    <out_dir>/java        Vape's own code only (gg/vape minus the relocated
                          gg/vape/shaded, plus func/) — third-party libraries
                          come from Maven, see src/payload/pom.xml
    <out_dir>/resources   the jar's non-class assets (mappings/, resources/)

Used by scripts/update-payload.sh; the build itself never runs this.
"""
import os
import shutil
import sys
import zipfile

TOPS = ("gg/vape", "func")
SKIP = "gg/vape/shaded"
ASSET_PREFIXES = ("mappings/", "resources/")


def install(dec, jar, out):
    java = os.path.join(out, "java")
    res = os.path.join(out, "resources")
    shutil.rmtree(out, ignore_errors=True)
    os.makedirs(java)
    os.makedirs(res)

    n = 0
    for top in TOPS:
        base = os.path.join(dec, top)
        if not os.path.isdir(base):
            continue
        for root, dirs, files in os.walk(base):
            rel = os.path.relpath(root, dec).replace(os.sep, "/")
            if rel == SKIP or rel.startswith(SKIP + "/"):
                dirs[:] = []
                continue
            dst = os.path.join(java, rel)
            os.makedirs(dst, exist_ok=True)
            for f in files:
                if f.endswith(".java"):
                    shutil.copy2(os.path.join(root, f), os.path.join(dst, f))
                    n += 1

    r = 0
    with zipfile.ZipFile(jar) as z:
        for info in z.infolist():
            name = info.filename
            if info.is_dir() or name.endswith(".class"):
                continue
            if not name.startswith(ASSET_PREFIXES):
                continue
            dst = os.path.join(res, name)
            os.makedirs(os.path.dirname(dst), exist_ok=True)
            with z.open(info) as src, open(dst, "wb") as fd:
                shutil.copyfileobj(src, fd)
            r += 1

    print(f"{out}: {n} .java files, {r} resource files")
    return n


if __name__ == "__main__":
    if len(sys.argv) != 4:
        sys.exit(__doc__)
    install(sys.argv[1], sys.argv[2], sys.argv[3])
