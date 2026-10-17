#!/bin/bash
# update-all.sh — rebuild injector + refresh payload/workspace from current bundle.
# Safe to run from any checkout (repo root is derived from this script); no
# auth/network — the bundled path only.
set -euo pipefail
DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
echo "=== workspace ==="; ls -la "$DIR"/payload.bin "$DIR"/bundle.dat "$DIR"/payload_jar/*.jar 2>/dev/null || true
echo "=== rebuild injector + agent (portable) ==="
make -C "$DIR/src/injector" clean && make -C "$DIR/src/injector" all
echo "=== refresh bundle from current payloads ==="
rm -f "$DIR/bundle.dat"
make -C "$DIR" bundle
echo "=== verify bundle path (no auth/network) ==="
"$DIR/src/injector/loader_test" "$DIR/bundle.dat" | grep -E 'rc=|bundled payload'
echo "=== sha256 of payload + jar ==="
shasum -a 256 "$DIR"/payload.bin "$DIR"/payload_jar/vape_client.jar | awk '{print $1, $2}'
echo "=== deobf artifacts (if present) ==="
ls -la "$DIR"/payload_jar/vape_client_deobf*.jar 2>/dev/null || echo "none yet"
echo "=== decompiled sources ==="
test -d "$DIR/src/decompiled" && echo "src/decompiled: $(find "$DIR/src/decompiled" -type f | wc -l | tr -d ' ') files" || echo "none"
echo "=== mockup source ==="; ls -la "$DIR"/ui/replica.html
echo "=== done ==="
