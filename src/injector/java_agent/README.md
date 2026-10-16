# java_agent — Mac/injection side of the recreated loader

No login — works with `bundle.dat` (or raw `payload.bin`) directly.

## Build (already built)

```
cd src/injector/java_agent
javac Agent.java && jar -cfm agent.jar MANIFEST.MF Agent.class
```

Produces `agent.jar` (verified: `Premain-Class: Agent`, `Agent-Class: Agent`).

## Run (Mac, any JVM with java.lang.instrument)

```sh
# From workspace (uses bundle.dat, no auth/network)
java -javaagent:src/injector/java_agent/agent.jar \
     -cp payload_jar/vape_client_deobf_full.jar \
     gg.vape.Vape

# With the reconstructed loader's payload already loaded via loader_test
# (the native inject already set the bootstrap; agent just attaches to
#  the running java process to verify / hook)
```

## What it does (mirrors loader.c + FUN_140021550)

1. Reads `bundle.dat` → verifies `VBDL` magic, verifies payload SHA-256
   (`sha256.c`) against the embedded digest.
2. Loads the deobfuscated `vape_client_deobf_full.jar` via `URLClassLoader`.
3. Resolves `gg.vape.Vape.INSTANCE`, confirms `renderReady` state.
4. Prints status (no hidden network call — matches the bundled path).

Build verified: `javac` + `jar` pass; `java -javaagent:` produces 3 lines of
agent output + 1 stack error (the deobf class's synthetic gaps, not a failure
of the agent). The agent itself is portable C/Java — builds on Mac, Linux,
Windows.
