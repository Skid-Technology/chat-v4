# Build & release

## Requirements

| Tool | Version | Used for |
|---|---|---|
| C compiler | C11 (`cc`, `clang`, `gcc`) | native loader |
| GNU make | 3.81+ | build orchestration |
| JDK | 17+ (`javac`, `jar`, Maven needs 8+) | Java agent |
| Maven | 3.8+ | building the client payload from `src/payload` |
| Python | 3.8+ | `bundle.py`, `embed.py`, install helper |
| Gradle | 8.x / 9.x | optional — rebuild the deobfuscator |

macOS: install the JDK via `brew install openjdk` or a Temurin build, then
`export JAVA_HOME="$(/usr/libexec/java_home -v 17+)"`.

## Targets

```sh
make                  # native loader + Java agent + bundle.dat
make payload          # rebuild the Java client from src/payload (Maven + shade)
make injector         # native loader (src/injector/loader_test)
make agent            # Java agent (src/injector/java_agent/agent.jar)
make bundle           # pack bundle.dat from the built payload
make single           # single-file injector for this OS (loader + payload)
make test             # build, then verify bundle.dat end to end
make dist             # stage release artifacts into dist/
make cli              # print loader-CLI help
make fetch            # loader CLI: login -> fetch -> extract -> deobf -> decompile
make update           # rebuild everything + refresh bundle.dat (update.sh)
make update-payload   # refresh the payload SOURCE from the live service
make clean            # remove all build output
```

`make test` runs the portable loader against `bundle.dat`: it extracts the
payload, checks the SHA-256, validates it, builds the bootstrap block and runs
the inject/ack pipeline against the stub backend.

## The payload: source in, jar out

The payload lives in `src/` as **source** — it is never downloaded and no
payload binary is committed.

```text
src/payload/
  java/        Vape's own code: gg/vape/** (minus the relocated gg/vape/shaded)
               plus func/, 2,955 .java files                     → compiled
  resources/   the jar's assets: mappings/, resources/ (fonts, textures) → copied
  pom.xml      dependencies + shade relocation
```

```sh
make payload    # -> src/payload/target/vape_client.jar
```

Third-party libraries are **dependencies**, not vendored source — netty, guava,
gson, LWJGL 2.9.3, jinput, commons-io, protobuf, plus the JetBrains and JSR-305
annotations. Only two are relocated, exactly as the original jar had them, by
`maven-shade-plugin`:

```text
org.objectweb.asm  ->  gg.vape.shaded.org.objectweb.asm
javassist          ->  gg.vape.shaded.javassist
```

The build produces a fat jar containing the client, its dependencies, the
relocated ASM/Javassist, the resources and the native libraries (LWJGL, jinput,
OpenAL) at the root — the same shape as the original payload jar.

`src/payload/target/` is gitignored.

## Rebuilding the payload source

`src/payload/java/` is decompiler output that has been made to compile. The
refresh chain (needs an account) is:

```sh
# 1. fetch the vendored decompilers
./scripts/fetch-tools.sh

# 2. deobfuscate the original client (needs Gradle + network)
cd zkm-flowdeobf
gradle run --args="../../payload_jar/vape_client.jar \
  ../../payload_jar/vape_client_deobf_full.jar ../../payload_jar/deobf_report_full.tsv \
  --remove-identity-rethrows --normalize-exception-ranges --normalize-zkm-shared-tails \
  --materialize-residual-stacks --specialize-binary-sentinel-tails \
  --clean-constant-residual-islands"

# 3. decompile + install the source, then rebuild
../../scripts/update-payload.sh        # or:  python3 ../src/cli/vape_loader.py decompile …
```

`update-payload.sh` decompiles with **Vineflower** (not CFR — CFR's output has
~114 syntax errors on this jar, Vineflower's has 6) and installs only Vape's own
packages into `src/payload/java`, keeping `pom.xml` intact.

See `docs/DEOBF.md` for the full record and stats.

## Windows loader

`src/injector/os_win32.c` documents the exact Win32/NT API sequence but is not
compiled on other platforms. On Windows (MSVC):

```bat
cl /std:clatest src\injector\loader.c src\injector\os_win32.c winhttp.lib
```

## Release

`make dist` produces:

```text
dist/
  injector-darwin / injector-linux   one file: loader + embedded payload
  agent.jar                          Java agent (macOS / Linux)
  bundle.dat                         the packed payload on its own
```

The GitHub workflow builds the same artifacts and publishes them on **every
commit to `main`**, not only on tags.

## CI

`.github/workflows/release.yml`:

- **payload** — `ubuntu-latest`, Temurin 21: `mvn -f src/payload/pom.xml package`.
- **injector** — `macos-latest` + `ubuntu-latest`: build the loader, bundle the
  payload, embed it into one file, then **run that file with no arguments** as a
  self-test.
- **agent** — `ubuntu-latest`, Temurin 17: build `agent.jar`, smoke-test `premain`.
- **release** — download the artifacts, assemble `injector-macos`,
  `injector-linux` and `agent.jar`, and publish a **pre-release** tagged
  `build-<short-sha>` (re-runs upload with `--clobber`).

No payload binary is published on its own: the client jar only ever ships
*inside* the injector.
