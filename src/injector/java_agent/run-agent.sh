#!/usr/bin/env bash
# run-agent.sh — launch a JVM with the v4-chat agent (premain), macOS-friendly.
#
#   ./run-agent.sh [agentArgs]
#
# Resolution of bundle.dat / client jar is handled inside the agent; the
# environment overrides below take priority:
#   VAPE_BUNDLE      path to bundle.dat
#   VAPE_CLIENT_JAR  path to the client jar
#   VAPE_MAIN        main class (default: Probe)
set -euo pipefail

here="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
root="$(cd "$here/../../.." && pwd)"

agent="$here/agent.jar"
if [[ ! -f "$agent" ]]; then
  echo "agent.jar missing — run: make agent" >&2
  exit 1
fi

args="${1:-}"
[[ -n "${VAPE_BUNDLE:-}" ]]     && args="${args:+$args,}bundle=$VAPE_BUNDLE"
[[ -n "${VAPE_CLIENT_JAR:-}" ]] && args="${args:+$args,}client=$VAPE_CLIENT_JAR"

main="${VAPE_MAIN:-Probe}"
echo "[run-agent] root=$root agent=$agent main=$main args=${args:-<none>}"
exec java -javaagent:"$agent${args:+=$args}" -cp "$here" "$main"
