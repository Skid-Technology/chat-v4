# Vape V4 loader — network protocol (static strings only)

Binary: `60e47510.exe` (sha256
`581a7ab6c901a9c51bb8fecb35ab91b62e12c03d302b4adc5e1a2be80044470c`).

Scope: everything below comes **only** from raw-string extraction
(`python3`: narrow `[\x20-\x7e]{5,}` runs + UTF-16LE
`(?:[\x20-\x7e]\x00){5,}` runs, verified narrow-vs-wide per string).
Request/response order, HTTP methods per endpoint, and field semantics are
**unverified** — no disassembly or traffic capture was used (REA tooling
down). Anything inferred is marked `(inference, unverified)`.

## 1. Base URLs / addresses — observed-verbatim

- `http://online.vape.chat` [wide] — env-var name `VAPE_ONLINE_BASE_URL`
  [wide] sits adjacent; (inference, unverified: base URL is read from that
  env var, with `VAPE_ONLINE_BASE_URL is too long for the bootstrap` [wide]
  as its length guard).
- `https://www.vape.chat/app-auth/proceed/` [wide] — adjacent to
  `Unable to start browser login` [wide] and a wide `open` verb;
  (inference, unverified: opened via `ShellExecuteW("open", url)` for the
  browser-login flow).
- `zeus-prod.vape.chat:8091` [narrow] — env-var name `VAPE_ZEUS_ADDRESS`
  [narrow] sits adjacent, plus `VAPE_ZEUS_ADDRESS must use host:port
  format` [wide]; (inference, unverified: Zeus host:port comes from that
  env var).

## 2. Endpoints — observed-verbatim

- `/api/v1/client/request-payload` [wide]
- `/api/v1/client/payload` [wide]
- `/api/v1/app-auth/generate` [wide]
- `/api/v1/app-auth/status` [wide]
- `/loader/login` [wide]

Which host each path is joined to is unverified, but adjacency groups them:
`request-payload`/`payload` sit with the `online.vape.chat` block and the
form-urlencoded content type; `app-auth/*` sit with the JSON content type;
`/loader/login` sits with the `{"username":"` JSON fragments and the
`Unable to log in to the local Service` [wide] error (inference,
unverified: `/loader/login` targets a local service/controller rather than
the public web host).

## 3. Content types — observed-verbatim

- `Content-Type: application/x-www-form-urlencoded` [wide] (adjacent to the
  `request-payload`/`payload` block)
- `Content-Type: application/json; charset=utf-8` [wide] (adjacent to the
  `app-auth/*` block)
- `POST` [wide] (single method token observed; per-endpoint mapping
  unverified)
- `Microsoft-WNS/10.0` [wide] (user-agent-style header value; header name
  it attaches to is unverified)

## 4. Bodies / JSON shapes — observed string fragments (order unverified)

Form-urlencoded fragment [narrow]:

- `edition=v4&hwid=`
- `token=`
- `downloadToken=`
- `downloadToken` (bare key, also observed)

JSON fragments [narrow], all within one ~64-byte span (`"}` / `","hwid":"`
/ `","password":"` / `{"username":"`), so they likely compose a single
object; key order and optionality unverified:

- `{"username":"`
- `","password":"`
- `","hwid":"`
- `"}`

Response-key fragments [narrow] (roles unverified — key vs value vs log
token; all four sit in one tight span with `timed out`):

- `status`
- `success`
- `token`
- `timed out`
- `error`

Other auth-adjacent tokens [narrow]:

- `username` (bare; separate location from the JSON fragments)
- `client-error` (hyphenated literal; likely a status/error value)

Note on `UserID=`: the strings `UserID=0x%08X%n` / ` UserID=%08X` exist but
only inside the ImGui table-settings serialization cluster
(`RefScale=`, `Column`, `Weight=`, `Width=`, `Visible=`, `Order=`, `Sort=`).
There is no adjacency to any protocol/JSON block, so it is **not**
documented as a protocol field here despite the superficial name match.

## 5. Transport / debug scaffolding — observed-verbatim

WinHTTP imports [narrow]: `winhttp.dll`, `WinHttpOpen`,
`WinHttpConnect`, `WinHttpOpenRequest`, `WinHttpSendRequest`,
`WinHttpReceiveResponse`, `WinHttpQueryHeaders`,
`WinHttpQueryDataAvailable`, `WinHttpReadData`, `WinHttpSetTimeouts`,
`WinHttpCloseHandle`, `WinHttpCrackUrl`.

Stage-tag fragments [narrow] (likely log prefixes for the WinHTTP phases;
mapping to calls unverified): `CrackUrl:`, `Open:`, `Connect:`,
`OpenRequest:`, `ReceiveResponse:`, `SendRequest:`.

Misc adjacent tokens: `vape_v4` [narrow] sits inside a stb_image decoder
string cluster (`unnamed`, `unknown image type`, `outofmem`,
`unsupported`, …) — not protocol. `%04X` / `%08X` / `C:\` [wide] are
formatting/path fragments in the same blocks, use unverified.
