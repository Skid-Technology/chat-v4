# Vape V4 loader — network protocol

Binary: `60e47510.exe` (sha256
`581a7ab6c901a9c51bb8fecb35ab91b62e12c03d302b4adc5e1a2be80044470c`).

Recovered by decompiling the loader with REA/Ghidra (functions cited below).
Earlier passes were string-extraction only; those inferences are now resolved
and the confirming functions are named.

## 0. Transport

Every request goes through a WinHTTP helper (`FUN_140008a98` for JSON,
`FUN_1400055c0` for form-urlencoded):

```text
WinHttpOpen(L"Microsoft-WNS/10.0", WINHTTP_ACCESS_TYPE_DEFAULT_PROXY, 0)
WinHttpSetTimeouts(...)
WinHttpCrackUrl(base_url) -> host / port / path
WinHttpConnect(...)
WinHttpOpenRequest(..., L"POST")
WinHttpSendRequest(WinHttpAddRequestHeaders(Content-Type: ...))
WinHttpReceiveResponse / WinHttpQueryHeaders / WinHttpQueryDataAvailable / WinHttpReadData
```

- Base URL: `http://online.vape.chat` (env override `VAPE_ONLINE_BASE_URL`).
- User agent: `Microsoft-WNS/10.0`.
- `Content-Type: application/json; charset=utf-8` for `/loader/login`.
- `Content-Type: application/x-www-form-urlencoded` for everything else.

## 1. Password login — `FUN_140009a3c`

```text
POST http://online.vape.chat/loader/login
Content-Type: application/json; charset=utf-8

{"username":"<user>","password":"<pass>","hwid":"<hwid>"}
```

Response JSON: `{"token":"<token>"}` on success, `{"error":"<msg>"}` on failure.
The loader maps a missing token to the UI error `Unable to log in to the local
Service`.

## 2. Browser (app-auth) login — `FUN_14000e2ec` (worker thread from `FUN_1400091bc`)

```text
POST /api/v1/app-auth/generate        [x-www-form-urlencoded]
     edition=v4&hwid=<hwid>
  -> 200, body = <code>               (40-character plain-text code)

open https://www.vape.chat/app-auth/proceed/<code>   (ShellExecuteW "open")

loop every ~2 s:
  POST /api/v1/app-auth/status        [x-www-form-urlencoded]
       token=<code>
    -> {"status":"success","token":"<token>"}   (done)
    -> {"status":"timed out"}                   (error: "Browser login timed out")
    -> anything else                            (keep polling)
```

Measured behaviour: the code stays valid for **~285 s** (4¾ min). After that
`GET /app-auth/proceed/<code>` returns `200` instead of the `302`-to-login, the
status endpoint reports `{"status":"timed out"}`, and the site renders
`/app-auth/error` — the page reading *"Unable to complete operation. Please
check uri and try again."*. Sign in to vape.chat **before** opening the proceed
URL so the round-trip fits inside that window.

Password login is the simpler, browser-free path and is confirmed live:
`POST /loader/login` answers `400 {"successful":false,"error":"Invalid username
or password"}` for bad credentials and `{"token":"…"}` on success.

## 3. Payload request — `FUN_140005af0`

```text
POST /api/v1/client/request-payload   [x-www-form-urlencoded]
     token=<token>
  -> {"downloadToken":"<downloadToken>"}
```

`FUN_140005c18` extracts the `downloadToken` field by scanning for
`"downloadToken"` and reading the quoted value.

## 4. Payload download — `FUN_140007c30`

```text
POST /api/v1/client/payload           [x-www-form-urlencoded]
     downloadToken=<downloadToken>
  -> raw bytes: a PE32+ (x64) image
```

The response is validated as a PE before use (see `docs/PAYLOAD.md`): `MZ` →
`e_lfanew` → `PE\0\0` → optional-header magic `0x20B`. On failure the loader
reports `Failed to download the payload from the server`.

## 5. HWID — `FUN_140008284`

`hwid` is the 8-uppercase-hex serial number of `C:\`
(`GetVolumeInformationW(L"C:\\", ...)` formatted with `%08X`). It is sent on
both login paths and is the only machine identifier in the protocol.

## 6. What is inside the payload

The downloaded PE embeds the Java client as a ZIP/JAR appended to the image.
The first `PK\x03\x04` local-file header marks the archive start and the
`PK\x05\x06` end-of-central-directory record its end; carving that range
reproduces the client JAR byte for byte (verified: 24,424,167 bytes, sha256
`1aece754…378318`). See `src/cli/vape_loader.py` (`extract`).

## 7. Reproducing the flow

`src/cli/vape_loader.py` implements all of the above:

```sh
python3 src/cli/vape_loader.py login --browser
python3 src/cli/vape_loader.py fetch -o payload.bin
python3 src/cli/vape_loader.py extract payload.bin -o payload_jar/vape_client.jar
python3 src/cli/vape_loader.py deobf  payload_jar/vape_client.jar
python3 src/cli/vape_loader.py decompile payload_jar/vape_client_deobf_full.jar
```

Live checks (no account needed): `/api/v1/app-auth/generate` returns a
40-character code, and `/api/v1/client/request-payload` with an unknown token
returns `403 {"error":"Forbidden: account not found"}`.
