# vape_loader.py — loader CLI

Reproduces the original Vape V4 loader (`60e47510.exe`) end to end on any
platform with Python 3.8+: it logs in the way the loader does, downloads the
payload, carves the embedded Java client out of it, deobfuscates it, and
decompiles it.

Python standard library only — no pip installs.

## Protocol (recovered from the binary)

Full details in [`docs/PROTOCOL.md`](../../docs/PROTOCOL.md). Summary:

| Step | Request | Response |
|---|---|---|
| password login | `POST /loader/login` (JSON `{username,password,hwid}`) | `{"token":…}` |
| browser login | `POST /api/v1/app-auth/generate` (`edition=v4&hwid=`) | 40-char code |
| …poll | `POST /api/v1/app-auth/status` (`token=<code>`) | `{"status":"success","token":…}` |
| request payload | `POST /api/v1/client/request-payload` (`token=…`) | `{"downloadToken":…}` |
| download | `POST /api/v1/client/payload` (`downloadToken=…`) | raw PE32+ |

Base URL `http://online.vape.chat`, user agent `Microsoft-WNS/10.0`.

## Commands

```sh
# 1. log in (browser is the default; opens the proceed URL and polls)
python3 src/cli/vape_loader.py login --browser
#    or with credentials
python3 src/cli/vape_loader.py login --username me@example.com --password 'hunter2'

# 2. request + download the payload PE
python3 src/cli/vape_loader.py fetch -o payload.bin

# 3. carve the client JAR out of the PE
python3 src/cli/vape_loader.py extract payload.bin -o payload_jar/vape_client.jar

# 4. deobfuscate it (needs Gradle; drives ../zkm-flowdeobf)
python3 src/cli/vape_loader.py deobf payload_jar/vape_client.jar

# 5. decompile it (needs tools/vineflower.jar; run scripts/fetch-tools.sh)
python3 src/cli/vape_loader.py decompile payload_jar/vape_client_deobf_full.jar -o .decompiled
#    → install only Vape's packages as the payload source: scripts/install-payload-src.py

# …or all of it in one go
python3 src/cli/vape_loader.py all --browser
```

Progress and hashes are printed at each stage. The token and a stable `hwid`
are cached in `~/.config/vape-cli/state.json` (override with `VAPE_CLI_STATE`).

## Whole-repo refresh

`scripts/update-payload.sh` chains every step — auth → fetch → extract →
deobfuscate → decompile (staged and swapped only if sane) → repack
`bundle.dat` → `make test`:

```sh
scripts/update-payload.sh --login      # re-authenticate, then do everything
scripts/update-payload.sh              # later runs reuse the saved token
scripts/update-payload.sh --dry-run    # show the chain, run nothing
scripts/update-payload.sh --stage      # also `git add` payloads + sources
```

Or `make update-payload ARGS=--login`.

## Notes

- `--hwid` overrides the hardware id. The loader uses the `C:\` volume serial;
  on macOS the CLI derives a stable 8-hex value from the platform UUID.
- `--base-url` overrides the API host (e.g. a proxy or mirror).
- The `extract` step is verified: carving the committed `payload.bin` yields
  `vape_client.jar` (24,424,167 bytes, sha256 `1aece754…378318`).
- `login --browser` requires you to complete the sign-in in the opened browser
  tab; the CLI polls `/api/v1/app-auth/status` until it reports `success`.
