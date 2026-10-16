#!/bin/bash
# update-all.sh — rebuild injector + refresh payload/workspace from current bundle.
# Works from /Volumes/SSD/scratch/skids/v4-chat or any worktree with these files.
set -euo pipefail
DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
DIR=/Volumes/SSD/scratch/skids/v4-chat
echo "=== workspace ==="; ls -la "$DIR"/payload.bin "$DIR"/bundle.dat "$DIR"/payload_jar/*.jar 2>/dev/null || true
echo "=== rebuild injector (portable) ==="
make -C "$DIR/src/injector" clean && make -C "$DIR/src/injector" loader_test
echo "=== verify bundle path (no auth/network) ==="
"$DIR/src/injector/loader_test" "$DIR/bundle.dat" | grep -E 'rc=|bundled payload'
echo "=== sha256 of payload + jar ==="
shasum -a 256 "$DIR"/payload.bin "$DIR"/payload_jar/vape_client.jar | awk '{print $1, $2}'
echo "=== deobf artifacts (if present) ==="
ls -la "$DIR"/payload_jar/vape_client_deobf*.jar 2>/dev/null || echo "none yet"
echo "=== rename workers ==="; ls "$DIR"/src/java/renames/*.tsv 2>/dev/null || echo "none (rate-limited — rebuild w/ lower concurrency)"
echo "=== mockup source ==="; ls -la "$DIR"/ui/replica.html
echo "=== done ==="
