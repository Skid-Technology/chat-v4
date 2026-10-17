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
protocol, a portable re-implementation, the deobfuscated client sources, the
recreated UI, and a **macOS Java agent** that attaches the client to a JVM.

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
│   ├── DEOBF.md             how the client jar was deobfuscated
│   ├── BUILD.md              build / release instructions
│   └── JAVA_AGENT.md         macOS agent usage
├── scripts/                  helper scripts (build, fetch-tools, release)
├── src/
│   ├── FUN_*.c               Ghidra decompiles of the original loader
│   ├── imports.txt           verbatim PE import table
│   ├── clean/                portable clean-room reconstructions
│   ├── injector/             the recreated loader (C11) + java agent
│   ├── cli/                  loader CLI: login → fetch → extract → deobf
│   └── decompiled/           CFR (symbol-renamed) client sources
├── ui/                       recreated loader UI (replica.html + assets)
├── tools/                    cfr.jar / vineflower.jar (fetched, not vendored)
├── payload_jar/              client jars
├── payload.bin               injected x64 PE
├── bundle.dat                payload.bin + client jar, packed (generated)
└── zkm-flowdeobf/            static JVM control-flow deobfuscator (vendored)
```

## Quick start

```sh
make            # build the native loader + the Java agent + bundle.dat
make test       # verify bundle.dat (extract + sha256 + validate + inject stub)
make dist       # stage release artifacts into dist/
```

Requirements: a C11 compiler, GNU make, JDK 17+ (`javac`, `jar`), Python 3.
See **[docs/BUILD.md](docs/BUILD.md)** for details and Windows build notes.

## macOS Java agent

The agent loads the reconstructed client into a JVM on macOS, either at startup
or by attaching to a running Minecraft/Java process.

```sh
# build
make agent                       # -> src/injector/java_agent/agent.jar

# start a JVM with the agent (bundle path, no auth/network)
./src/injector/java_agent/run-agent.sh

# attach to an already-running Minecraft JVM on macOS
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
python3 src/cli/vape_loader.py extract payload.bin     # carve vape_client.jar
python3 src/cli/vape_loader.py deobf                   # zkm-flowdeobf
python3 src/cli/vape_loader.py decompile               # CFR -> src/decompiled

# …or the whole chain:
python3 src/cli/vape_loader.py all --browser
```

To refresh the payloads and the decompiled sources in one shot (log in once,
then it's non-interactive):

```sh
scripts/update-payload.sh --login     # auth → fetch → extract → deobf → decompile → bundle → test
```

Protocol details: **[docs/PROTOCOL.md](docs/PROTOCOL.md)**.
CLI usage: **[src/cli/README.md](src/cli/README.md)**.

## Bundle format (`bundle.dat`)

Little-endian `VBDL` container holding `payload.bin` and the client jar, each
with a SHA-256 digest that the loader verifies before injection.

```text
u32 magic  0x56424C44 ("VBDL")
u32 version (1)
u32 n_entries
repeat: u8 namelen, name, u64 offset, u64 size, u8[32] sha256
blob data
```

```sh
python3 src/injector/bundle.py payload.bin payload_jar/vape_client.jar
```

## Release

`.github/workflows/release.yml` builds the native loader (macOS + Linux), the
Java agent, and `bundle.dat`, then publishes them as GitHub release assets when
a `v*` tag is pushed (or run manually from the Actions tab).

## Provenance

All loader behavior is reconstructed from static analysis of
`60e47510.exe` (sha256 `581a7ab6…4470c`). The `src/FUN_*.c` files are Ghidra
12.1.4 output; `src/clean/` and `src/injector/` are portable re-implementations
written from that analysis. Client sources under `src/decompiled/` are CFR
0.152 output of the deobfuscated jar.

> **Unreleased / educational.** Provided for research and interoperability only.
> No accounts, credentials, or live payloads are included.
