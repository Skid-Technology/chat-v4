# java_agent — macOS / Linux side of the loader

`make agent` at the repo root builds `agent.jar` (premain + agentmain) and the
Skid Solutions splash that goes with it.

```sh
java -Dskid.splash=false \
     -javaagent:src/injector/java_agent/agent.jar=verbose \
     -cp <client>.jar gg.vape.Vape
```

Args are `key=value[,key=value…]`: `bundle=`, `client=`, `verbose`. Without a
`bundle=` argument the agent looks for `bundle.dat` next to itself and in the
working directory.

The splash is on by default; it is skipped on a headless JVM and when
`-Dskid.splash=false` is set. `attach.sh` is the dynamic-attach path for a
JVM that is already running.
