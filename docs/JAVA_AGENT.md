# macOS Java agent

`src/injector/java_agent/agent.jar` is a portable Java instrumentation agent
(premain + agentmain) that runs on **macOS** (Intel and Apple Silicon) on any
JDK 17+ with `java.lang.instrument`. It is the Java-side counterpart to the
native loader in `src/injector/`: instead of writing a PE into the Minecraft
process, it loads the reconstructed client into a JVM.

Nothing the agent does touches the network. It mirrors the offline *bundled*
path of `loader.c`.

## What it does

1. Locates `bundle.dat` and the client jar (see [resolution](#path-resolution)).
2. Parses the `VBDL` container and verifies the SHA-256 of every entry
   (`payload.bin`, `vape_client.jar`) against the digest in the index.
3. Loads the client jar in a child `URLClassLoader` and touches
   `gg.vape.Vape`.
4. Logs everything under the `[Agent]` prefix; never throws out of `premain`.

## Requirements

| Need | Why |
|---|---|
| JDK 17+ (`java`, `javac`, `jar`) | build + run the agent |
| A **full JDK** (module `jdk.attach`) | only for dynamic attach (`attach.sh`) |
| macOS 11+ | target platform |

```sh
# point at a full JDK on macOS if needed
export JAVA_HOME="$(/usr/libexec/java_home -v 17+)"
```

## Build

```sh
make agent
# -> src/injector/java_agent/agent.jar   (Premain-Class / Agent-Class: Agent)
```

## Run at startup (premain)

```sh
# convenience wrapper: locates bundle/client for you
./src/injector/java_agent/run-agent.sh

# manual
java -javaagent:src/injector/java_agent/agent.jar=verbose \
     -cp src/injector/java_agent Probe
```

Example output:

```text
[Agent] v4-chat loader agent — premain (Mac OS X/aarch64)
[Agent] bundle=…/bundle.dat | client=…/payload_jar/vape_client_deobf_full.jar
[Agent] bundle VBDL v1, 2 entries, 48911727 bytes
[Agent]   payload.bin: 24487424 bytes, sha256 ok
[Agent]   vape_client.jar: 24424167 bytes, sha256 ok
[Agent] loaded gg.vape.Vape from vape_client_deobf_full.jar
```

## Attach to a running JVM (agentmain)

```sh
./src/injector/java_agent/attach.sh                 # auto-find Minecraft via jps
./src/injector/java_agent/attach.sh 12345           # explicit PID
./src/injector/java_agent/attach.sh 12345 verbose   # agent args
```

Manual equivalent:

```sh
javac --add-modules jdk.attach -d src/injector/java_agent \
      src/injector/java_agent/Attach.java
java  --add-modules jdk.attach -cp src/injector/java_agent \
      Attach <pid> src/injector/java_agent/agent.jar "verbose"
```

On recent JDKs, dynamic attach prints a warning; silence it with
`-XX:+EnableDynamicAgentLoading` if desired.

## Path resolution

For each of `bundle.dat` and the client jar, first match wins:

1. agent arg — `bundle=/path`, `client=/path`
2. environment — `VAPE_BUNDLE`, `VAPE_CLIENT_JAR`
3. walking up to 8 parents from the agent jar's own location
4. walking up from the current working directory
5. macOS convenience dirs:
   - `~/Library/Application Support/v4-chat`
   - `/usr/local/share/v4-chat`
   - `/opt/homebrew/share/v4-chat`

The client jar is searched as `payload_jar/vape_client_deobf_full.jar`, then
`…_deobf.jar`, then `…/vape_client.jar`.

## Agent arguments

`-javaagent:agent.jar=k1=v1,k2=v2` — comma-separated, any order:

| Key | Effect |
|---|---|
| `bundle=PATH` | explicit `bundle.dat` |
| `client=PATH` | explicit client jar |
| `verbose` | extra debug lines |

## Troubleshooting

- **`agent.jar missing`** — run `make agent` first.
- **`client load failed … VerifyError`** — expected: the deobfuscated client has
  synthetic gaps. The agent logs one line and continues.
- **`Attach … com.sun.tools.attach` missing** — you are on a JRE; install a full
  JDK and set `JAVA_HOME`.
- **attach “not permitted”** — attach must run as the **same user** as the target
  process; macOS SIP blocks attaching to system JVMs.
- **Gatekeeper / quarantine** — if the jar was downloaded rather than built
  locally: `xattr -dr com.apple.quarantine src/injector/java_agent/agent.jar`.
- **`no bundle.dat found`** — generate it with `make bundle`, or point the agent
  at one with `VAPE_BUNDLE`.
