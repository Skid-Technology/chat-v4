#!/usr/bin/env bash
# fetch-tools.sh — download the vendored decompilers into tools/.
#
# These jars are not committed (see .gitignore). Only needed if you want to
# regenerate src/payload/java/ — the payload sources are already in the repo.
set -euo pipefail

here="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
tools="$here/tools"
mkdir -p "$tools"

CFR_VER="${CFR_VER:-0.152}"
VINEFLOWER_VER="${VINEFLOWER_VER:-1.11.2}"

cfr_url="https://github.com/leibnitz27/cfr/releases/download/${CFR_VER}/cfr-${CFR_VER}.jar"
vf_url="https://github.com/Vineflower/vineflower/releases/download/${VINEFLOWER_VER}/vineflower-${VINEFLOWER_VER}.jar"

fetch() {
  local url="$1" out="$2"
  if [[ -f "$out" ]]; then
    echo "ok    $out (exists)"
    return
  fi
  echo "get   $out"
  curl -fL --retry 3 -o "$out.tmp" "$url"
  mv "$out.tmp" "$out"
}

fetch "$cfr_url" "$tools/cfr.jar"
fetch "$vf_url" "$tools/vineflower.jar"

echo "done — tools/ ready"
