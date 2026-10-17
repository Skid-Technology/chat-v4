#!/usr/bin/env bash
# build.sh — one-shot build without needing to remember the make targets.
#
#   ./scripts/build.sh          everything (loader + agent + bundle)
#   ./scripts/build.sh test     build + verify bundle.dat
#   ./scripts/build.sh dist     build + stage dist/ artifacts
set -euo pipefail

here="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$here"

target="${1:-all}"
case "$target" in
  all|injector|agent|bundle|test|dist|clean) ;;
  *) echo "usage: $0 [all|injector|agent|bundle|test|dist|clean]" >&2; exit 2 ;;
esac

echo "== v4-chat: make $target =="
exec make "$target"
