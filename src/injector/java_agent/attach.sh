#!/usr/bin/env bash
# attach.sh — dynamically attach the v4-chat agent to a running JVM on macOS.
#
#   ./attach.sh [pid] [agentArgs]
#
# With no PID, finds the first running Minecraft/Java process via `jps`.
# Requires a full JDK (module jdk.attach) and the same user as the target.
set -euo pipefail

here="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
agent="$here/agent.jar"
[[ -f "$agent" ]] || { echo "agent.jar missing — run: make agent" >&2; exit 1; }

pid="${1:-}"
args="${2:-}"

if [[ -z "$pid" ]]; then
  echo "[attach] no PID given — searching with jps…"
  pid="$(jps -l | grep -iE 'minecraft|net\.minecraft|java' | grep -v Jps | awk '{print $1}' | head -n1 || true)"
fi
if [[ -z "$pid" ]]; then
  echo "[attach] could not find a target JVM. Pass a PID explicitly." >&2
  exit 1
fi

# Compile the attach helper on demand (needs jdk.attach).
if [[ ! -f "$here/Attach.class" ]]; then
  echo "[attach] building Attach helper…"
  javac --add-modules jdk.attach -d "$here" "$here/Attach.java"
fi

echo "[attach] pid=$pid agent=$agent args=${args:-<none>}"
exec java --add-modules jdk.attach -cp "$here" Attach "$pid" "$agent" "$args"
