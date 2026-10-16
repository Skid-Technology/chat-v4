# PAYLOAD — what the injector injects, and can we download it?

Date: 2026-10-16. All server responses below are observed, via curl.

## What gets injected (from the binary, static)

1. Loader logs in (`/loader/login` with `{"username":"…","password":"…","hwid":"…"}`)
   or browser auth (`/api/v1/app-auth/generate` → user opens
   `https://www.vape.chat/app-auth/proceed/…` → `/api/v1/app-auth/status`).
2. Loader POSTs `token=<token>` (form-urlencoded) to
   `/api/v1/client/request-payload`, then to `/api/v1/client/payload`.
3. Response bytes are validated as a PE before injection:
   `Payload image is empty` / `too small to be a valid PE` /
   `invalid PE headers` / `not a valid 64-bit PE image`.
4. The 64-bit PE is reflectively loaded into the Minecraft process
   (`java.exe`/`javaw.exe`): `Failed to write reflective image into
   target process`, `Failed to start reflective loader thread`,
   `Reflective loader returned failure` — plus an injection-stub path
   (`Failed to write/allocate/execute injection stub…`,
   `Injection stub execution timed out`).
5. Product DLL on disk is `Vape421Native.dll`, staged via `NativeLoader.dll`;
   C2 rendezvous is the `Bootstrap`/`BootstrapAck` shared block + zeus
   host (`zeus-prod.vape.chat:8091`).

So the injected artifact = the server-returned 64-bit PE
(the Vape V4 client for the selected Minecraft version).

## Live server probes (no credentials)

| Request | Result |
|---|---|
| `POST /api/v1/client/request-payload` body `token=` | `401 {"error":"Unauthorized"}` |
| `POST /api/v1/client/payload` body `token=` | `401 {"error":"Unauthorized"}` |
| `GET /api/v1/client/payload` | `405 Method Not Allowed`, `Allow: POST` (Spring Whitelabel page) |
| `POST /loader/login` body `{}` (JSON) | `400 {"successful":false,"error":"Invalid username or password"}` |

Infra: Cloudflare front, Express back end (`X-Powered-By: Express`).

## Conclusion

The payload cannot be downloaded without a valid Vape account token —
every payload endpoint 401s on an empty/invalid token, and brute-forcing
or bypassing that is out of scope. To retrieve it you need to log in
through the loader (or `POST /loader/login` with real credentials, take
the returned token) and replay:
`POST http://online.vape.chat/api/v1/client/request-payload`
with `token=<token>`, then save the response bytes and verify the MZ/PE
headers. The cleaned `request_payload.c` in this directory implements
exactly that request — point its `base_url`/token at real values and
swap `demo_post` for a real transport.
