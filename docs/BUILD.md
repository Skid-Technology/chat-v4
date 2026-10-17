# Build & release

## Requirements

| Tool | Version | Used for |
|---|---|---|
| C compiler | C11 (`cc`, `clang`, `gcc`) | native loader |
| GNU make | 3.81+ | build orchestration |
| JDK | 17+ (`javac`, `jar`) | Java agent |
| Python | 3.8+ | `bundle.py` |
| Gradle | 8.x / 9.x | optional — rebuild the deobfuscator |

macOS: install the JDK via `brew install openjdk` or a Temurin build, then
`export JAVA_HOME="$(/usr/libexec/java_home -v 17+)"`.

## Targets

```sh
make            # native loader + Java agent + bundle.dat
make injector   # native loader (src/injector/loader_test)
make agent      # Java agent (src/injector/java_agent/agent.jar)
make bundle     # pack bundle.dat from payload.bin + payload_jar/vape_client.jar
make test       # build, then verify bundle.dat end to end
make dist       # stage release artifacts into dist/
make cli        # print loader-CLI help
make fetch      # loader CLI: login -> fetch -> extract -> deobf -> decompile
make update     # rebuild everything + refresh bundle.dat (update.sh)
make update-payload   # refresh payloads + decompiled sources from the live service
make clean      # remove all build output
```

`make test` runs the portable loader against `bundle.dat`: it extracts the
payload, checks the SHA-256, validates the PE, builds the bootstrap block and
runs the inject/ack pipeline against the stub backend.

## Payloads

`bundle.dat` is generated from two inputs:

- `payload.bin` — the injected x64 PE (sha256 `856e5c2e…5f80d`)
- `payload_jar/vape_client.jar` — the inner Java client (sha256 `1aece754…378318`)

They are committed so CI can build a complete release. To repack after changing
either:

```sh
make bundle
```

## Windows loader

`src/injector/os_win32.c` documents the exact Win32/NT API sequence but is not
compiled on other platforms. On Windows (MSVC):

```bat
cl /std:clatest src\injector\loader.c src\injector\os_win32.c winhttp.lib
```

## Rebuilding the decompiled sources (optional)

`src/decompiled/` is CFR 0.152 output of `payload_jar/vape_client_deobf_full.jar`.

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

# 3. decompile with CFR (see docs/cfr-summary.txt for known gaps)
java -jar ../tools/cfr.jar ../payload_jar/vape_client_deobf_full.jar \
     --outputdir ../src/decompiled
```

See `docs/DEOBF.md` for the full record and stats.

## Release

`make dist` produces:

```text
dist/
  loader_test   native loader (portable stub backend)
  agent.jar     macOS Java agent
  bundle.dat    packed payload
```

The GitHub release workflow builds the same artifacts on macOS and Linux and
attaches them to a release when a `v*` tag is pushed. To cut one locally:

```sh
git tag v1.0.0 && git push origin v1.0.0
```

## CI

`.github/workflows/release.yml`:

- **native** — `macos-latest` + `ubuntu-latest`: build the loader, run `make test`.
- **agent** — `ubuntu-latest` with Temurin 17: build `agent.jar`, smoke-test `premain`.
- **release** — on `v*` tags (or manual dispatch): collect `dist/` and publish a
  GitHub release with `loader_test`, `agent.jar` and `bundle.dat` attached.
