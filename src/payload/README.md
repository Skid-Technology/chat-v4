# src/payload — the client, as source

This is the Java payload. It is built here, from source, every time:

```sh
make payload          # or: mvn -f src/payload/pom.xml package
# -> src/payload/target/vape_client.jar   (gitignored)
```

Nothing is downloaded at build time and no payload binary is committed.

## Layout

```text
java/        Vape's own code — 2,955 .java files
  gg/vape/**   the client itself (the relocated gg/vape/shaded is NOT here)
  func/**      a couple of helper classes
resources/   the jar's non-class assets
  mappings/    Minecraft mappings for the versions the client supports
  resources/   fonts, textures, shaders, sounds, translations
pom.xml      dependencies + shade relocation
```

Third-party libraries are **Maven dependencies**, not vendored source: netty,
guava, gson, LWJGL 2.9.3, jinput, commons-io, protobuf, plus JetBrains and
JSR-305 annotations (compile-only).

Two of them are relocated, exactly as the original jar had them, by
`maven-shade-plugin`:

```text
org.objectweb.asm  ->  gg.vape.shaded.org.objectweb.asm
javassist          ->  gg.vape.shaded.javassist
```

The output is a fat jar with the client, its dependencies, the relocated
ASM/Javassist, the resources from `resources/` and the native libraries (LWJGL,
jinput, OpenAL) at the root — the same shape as the original payload jar.

## Where the source comes from

It is decompiler output, made to compile:

1. `scripts/update-payload.sh` fetches the PE, carves the client jar out of it,
   deobfuscates it (`zkm-flowdeobf`) and decompiles it with **Vineflower**
   1.11.2 — CFR's output has ~114 syntax errors on this jar, Vineflower's 6.
2. `scripts/install-payload-src.py` installs Vape's own packages into
   `java/` (this `pom.xml` is left alone) and the jar's assets into
   `resources/`.
3. The decompiler's remaining artifacts were fixed by hand: duplicated local
   names in reused `switch` scopes, `new X; … <init>(…)` patterns, raw-type
   casts, desugared try-with-resources. Those fixes are described in
   `docs/BUILD.md` and `docs/DEOBF.md`.

`mvn package` must stay green: CI runs exactly that, and nothing else, before
packing the jar into the injector.
