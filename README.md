<p align="center">
  <img src="ssets/ss.png" alt="Vape V4" width="350" />
  <br>
  <font size="6"><b>Vape V4 Recode - Leak [Unreleased]</b></font>
  <br>
  <font size="3">by Skid Solutions</font>
</p>

---

<p align="center">
  <a href="https://discord.gg/AEwatvnvSf">https://discord.gg/AEwatvnvSf</a>
</p>

---

Reverse-engineering workspace and clean-room reconstruction of the **Vape V4**
Minecraft client loader (`60e47510.exe`). It contains the recovered loader
protocol, a portable re-implementation, the client's Java source, the recreated
UI, and a **Java agent** that attaches the client to a JVM on macOS and Linux.

Everything is built from `src/` — nothing is downloaded at build time and no
payload binary is committed. Every commit to `main` publishes a pre-release
whose assets are a single-file injector (with the payload inside it) and the
Java agent.

## Layout

```text
.
├── README.md                 you are here
├── Makefile                  top-level build (injector + agent + bundle)
├── docs/                     analysis + build + agent documentation
│   ├── RE-NOTES.md           binary facts, provenance, function triage
│   ├── PROTOCOL.md           recovered network protocol
│   ├── UI_STRINGS.md         recovered UI string inventory
│   ├── PAYLOAD.md            what gets injected + live endpoint probes
│   ├── DEOBF.md              how the client jar was deobfuscated
│   ├── BUILD.md              build / release instructions
│   └── JAVA_AGENT.md         Java agent usage
├── scripts/                  helper scripts (build, fetch-tools, refresh)
├── src/
│   ├── FUN_*.c               Ghidra decompiles of the original loader
│   ├── imports.txt           verbatim PE import table
│   ├── clean/                portable clean-room reconstructions
│   ├── injector/             the recreated loader (C11) + Java agent
│   ├── cli/                  loader CLI: login → fetch → extract → deobf
│   └── payload/              the Java client, as source — see src/payload/README.md
│       ├── java/             Vape's own code (gg/vape + func), 2,955 files
│       ├── resources/        jar assets: mappings, textures, fonts
│       └── pom.xml           Maven build (ASM/Javassist shaded to gg.vape.shaded.*)
├── ui/                       recreated loader UI (replica.html + assets)
├── tools/                    cfr.jar / vineflower.jar (fetched, not vendored)
├── bundle.dat                the packed payload (generated)
└── zkm-flowdeobf/            static JVM control-flow deobfuscator (vendored)
```

## Quick start

```sh
make            # native loader + Java agent + bundle.dat
make payload    # rebuild the Java client from src/payload (Maven)
make single     # one self-contained file: the loader with the payload inside
make test       # verify bundle.dat (extract + sha256 + validate + inject stub)
make dist       # stage release artifacts into dist/
```

Requirements: a C11 compiler, GNU make, JDK 17+ (`javac`, `jar`), Maven,
Python 3. See **[docs/BUILD.md](docs/BUILD.md)** for details.

## Java agent

On macOS and Linux the client is loaded through a JVM agent, either at startup
or by attaching to a running Minecraft/Java process.

```sh
# build
make agent                       # -> src/injector/java_agent/agent.jar

# start a JVM with the agent (bundle path, no auth/network)
./src/injector/java_agent/run-agent.sh

# attach to an already-running Minecraft JVM
./src/injector/java_agent/attach.sh
```

Full options, path resolution and troubleshooting: **[docs/JAVA_AGENT.md](docs/JAVA_AGENT.md)**.

## Loader CLI

`src/cli/vape_loader.py` reproduces the original loader: it logs in the way
`60e47510.exe` does, downloads the payload, carves the embedded Java client,
deobfuscates it and decompiles it. Standard library only.

```sh
python3 src/cli/vape_loader.py login --browser        # or --username/--password
python3 src/cli/vape_loader.py fetch -o payload.bin   # request + download the PE
python3 src/cli/vape_loader.py extract payload.bin    # carve vape_client.jar
python3 src/cli/vape_loader.py deobf                  # zkm-flowdeobf
python3 src/cli/vape_loader.py decompile              # Vineflower -> .decompiled

# …or the whole chain:
python3 src/cli/vape_loader.py all --browser
```

> `login`, `fetch` and `extract` need a Vape account. The payloads they produce
> (and `payload_jar/`) are gitignored: they are only inputs for refreshing the
> source in `src/payload/`, never what the build or the releases use.

To refresh the payload **sources** from the live service in one shot (log in
once, then it's non-interactive):

```sh
scripts/update-payload.sh --login   # auth → fetch → extract → deobf → decompile → install → rebuild → test
```

Protocol details: **[docs/PROTOCOL.md](docs/PROTOCOL.md)**.
CLI usage: **[src/cli/README.md](src/cli/README.md)**.

## How the payload is built

```
src/payload/java + resources/   ──mvn package──▶  target/vape_client.jar
target/vape_client.jar          ──bundle.py────▶  bundle.dat   (VBDL + sha256 per entry)
loader + bundle.dat             ──embed.py─────▶  injector-<os>  (one file)
```

`injector-<os>` reads its **own image**, finds the appended bundle, verifies the
SHA-256 and validates the payload — no external file, no download. The CLI can
also drive the original network path: see **[docs/PAYLOAD.md](docs/PAYLOAD.md)**.

Bundle format (little-endian `VBDL`):

```text
u32 magic  0x56424C44 ("VBDL")
u32 version (1)
u32 n_entries
repeat: u8 namelen, name, u64 offset, u64 size, u8[32] sha256
blob data
```

## Release

`.github/workflows/release.yml` runs on every push to `main`:

- **payload** — `mvn package` compiles the client from `src/payload`
- **injector** — macOS + Linux: build the loader, bundle the jar, embed it, self-test
- **agent** — build `agent.jar` from source and smoke-test `premain`
- **release** — publish a **pre-release** tagged `build-<short-sha>` with
  `injector-macos`, `injector-linux` and `agent.jar`

## Provenance

All loader behavior is reconstructed from static analysis of
`60e47510.exe` (sha256 `581a7ab6…4470c`). The `src/FUN_*.c` files are Ghidra
12.1.4 output; `src/clean/` and `src/injector/` are portable re-implementations
written from that analysis. The client source under `src/payload/java/` is
Vineflower 1.11.2 output of the deobfuscated jar, adapted so it compiles (see
[docs/BUILD.md](docs/BUILD.md#rebuilding-the-payload-source)).

> **Unreleased / educational.** Provided for research and interoperability only.
> No accounts, credentials, or live payloads are included.
