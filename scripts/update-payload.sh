#!/usr/bin/env bash
# ─────────────────────────────────────────────────────────────
# update-payload.sh — refresh the payloads and the decompiled
# sources from the live Vape service, end to end:
#
#   auth → fetch → extract → deobfuscate → decompile
#        → swap sources → repack bundle.dat → verify
#
# Quick start
#   scripts/update-payload.sh              # reuse the saved token
#   scripts/update-payload.sh --login      # (re)authenticate first
#   VAPE_USERNAME=you@example.com VAPE_PASSWORD=… scripts/update-payload.sh --login
#
# The token and hwid live in ~/.config/vape-cli/state.json
# (override with $VAPE_CLI_STATE). Log in once and every later run is
# non-interactive.
# ─────────────────────────────────────────────────────────────
set -euo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$ROOT"

PY="${PYTHON:-python3}"
CLI="$ROOT/src/cli/vape_loader.py"
STATE="${VAPE_CLI_STATE:-$HOME/.config/vape-cli/state.json}"

PAYLOAD="$ROOT/payload.bin"
JAR="$ROOT/payload_jar/vape_client.jar"
DEOBF_JAR="$ROOT/payload_jar/vape_client_deobf_full.jar"
REPORT="$ROOT/payload_jar/deobf_report_full.tsv"
PAYLOAD_DIR="$ROOT/src/payload"          # the vendored payload source (committed)
DEC_TMP="$ROOT/.decompiled"              # staging tree for the decompile
NEW="$ROOT/.payload.new"                 # staging tree for the install

DO_LOGIN=0      # 0 = only if no token, 1 = force, -1 = never
DO_DEOBF=1
DO_TEST=1
DO_STAGE=0
DRY=0

usage() {
  cat <<'EOF'
usage: scripts/update-payload.sh [options]

  --login        re-authenticate before fetching (browser, or
                 VAPE_USERNAME/VAPE_PASSWORD if both are exported)
  --no-login     never authenticate; fail if the saved token is gone
  --no-deobf     skip zkm-flowdeobf (recompile from the current jar)
  --no-test      skip `make test` after repacking
  --stage        `git add` the refreshed payload + sources at the end
  --dry-run      print the chain without executing anything
  -h, --help     this message
EOF
}

for a in "$@"; do
  case "$a" in
    --login)    DO_LOGIN=1 ;;
    --no-login) DO_LOGIN=-1 ;;
    --no-deobf) DO_DEOBF=0 ;;
    --no-test)  DO_TEST=0 ;;
    --stage)    DO_STAGE=1 ;;
    --dry-run)  DRY=1 ;;
    -h|--help)  usage; exit 0 ;;
    *) echo "unknown option: $a" >&2; echo; usage >&2; exit 2 ;;
  esac
done

step() { printf '\n\033[1m== %s\033[0m\n' "$*"; }
run()  { printf '   $ %s\n' "$*"; [ "$DRY" = 1 ] || "$@"; }
note() { printf '   · %s\n' "$*"; }
sha()  {
  if command -v sha256sum >/dev/null 2>&1; then sha256sum "$1" | cut -d' ' -f1
  else shasum -a 256 "$1" | cut -d' ' -f1; fi
}

have_token() {
  "$PY" - "$STATE" <<'PY' 2>/dev/null
import json, sys
try:
    state = json.load(open(sys.argv[1]))
except Exception:
    sys.exit(1)
sys.exit(0 if state.get("token") else 1)
PY
}

# ── 1. auth ──────────────────────────────────────────────────
if [ "$DO_LOGIN" = -1 ]; then
  step "1/6 auth — skipped (--no-login)"
  if [ "$DRY" = 0 ] && ! have_token; then
    echo "   no token in $STATE — run: $CLI login" >&2
    exit 1
  fi
elif [ "$DO_LOGIN" = 1 ] || ! have_token; then
  if [ -n "${VAPE_USERNAME:-}" ] && [ -n "${VAPE_PASSWORD:-}" ]; then
    step "1/6 auth — password login"
    run "$PY" "$CLI" login
  else
    step "1/6 auth — browser login"
    note "open the printed URL, confirm, and leave it open until it succeeds"
    run "$PY" "$CLI" login --browser
  fi
  if [ "$DRY" = 0 ] && ! have_token; then
    echo "   login did not produce a token" >&2
    exit 1
  fi
else
  step "1/6 auth — reusing the saved token"
fi

# ── 2. fetch ─────────────────────────────────────────────────
step "2/6 fetch — request + download the payload PE"
run "$PY" "$CLI" fetch -o "$PAYLOAD"

# ── 3. extract ───────────────────────────────────────────────
step "3/6 extract — carve the embedded client JAR out of the PE"
run "$PY" "$CLI" extract "$PAYLOAD" -o "$JAR"

# ── 4. deobfuscate ───────────────────────────────────────────
if [ "$DO_DEOBF" = 1 ]; then
  step "4/6 deobf — zkm-flowdeobf"
  run "$PY" "$CLI" deobf "$JAR" -o "$DEOBF_JAR" --report "$REPORT"
else
  step "4/6 deobf — skipped (--no-deobf)"
fi

# ── 5. decompile + install the source ────────────────────────
step "5/6 decompile — Vineflower, then install src/payload (Vape's code + resources)"
run rm -rf "$DEC_TMP" "$NEW"
run "$PY" "$CLI" decompile "$DEOBF_JAR" -o "$DEC_TMP"
run "$PY" "$ROOT/scripts/install-payload-src.py" "$DEC_TMP" "$JAR" "$NEW"
if [ "$DRY" = 0 ]; then
  n=$(find "$NEW/java" -name '*.java' 2>/dev/null | wc -l | tr -d ' ')
  r=$(find "$NEW/resources" -type f 2>/dev/null | wc -l | tr -d ' ')
  if [ "$n" -lt 2000 ] || [ "$r" -lt 50 ]; then
    echo "   install looks wrong ($n .java, $r resources) — leaving $PAYLOAD_DIR alone" >&2
    exit 1
  fi
  note "$n .java files, $r resource files (pom.xml kept)"
  run rm -rf "$PAYLOAD_DIR/java" "$PAYLOAD_DIR/resources"
  run mv "$NEW/java" "$PAYLOAD_DIR/java"
  run mv "$NEW/resources" "$PAYLOAD_DIR/resources"
  run rm -rf "$NEW" "$DEC_TMP"
fi

# ── 6. rebuild from source + verify ──────────────────────────
step "6/6 verify — rebuild the payload from src/payload, repack bundle.dat, self-test"
run make -C "$ROOT" bundle
[ "$DO_TEST" = 1 ] && run make -C "$ROOT" test

if [ "$DO_STAGE" = 1 ] && [ "$DRY" = 0 ]; then
  step "stage — git add the refreshed artifacts"
  run git -C "$ROOT" add -A -- src/payload
fi

# ── summary ──────────────────────────────────────────────────
step "summary"
if [ "$DRY" = 1 ]; then
  note "dry run — nothing was executed or written"
else
  for f in "$PAYLOAD" "$JAR" "$DEOBF_JAR" "$ROOT/src/payload/target/vape_client.jar" "$ROOT/bundle.dat"; do
    [ -f "$f" ] || continue
    printf '   %-38s %11s B' "${f#$ROOT/}" "$(wc -c < "$f" | tr -d ' ')"
    case "$f" in
      *bundle.dat) printf '\n' ;;
      *)           printf '  sha256 %s\n' "$(sha "$f")" ;;
    esac
  done
  echo
  printf '   next: review `git status`, then commit the refreshed payload + sources\n'
fi
