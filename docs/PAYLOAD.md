# PAYLOAD — what the injector injects, and how to fetch it

Date: 2026-10-17. Server responses below are observed via curl; the
retrieval section at the end records the payload actually fetched through
`src/cli/vape_loader.py`.

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

## Retrieval — done, and scripted

> **The payloads below are not committed.** The payload that ships is rebuilt
> from source in `src/payload` by CI (`mvn package` → `vape_client.jar` →
> `bundle.dat` → single-file injector). The artifacts here are only the input
> used to refresh that source. See `docs/BUILD.md`.

The payload needs a valid account token; with one, the whole chain is
`scripts/update-payload.sh` (or `make update-payload`):

```sh
scripts/update-payload.sh --login     # authenticates, then does everything below
# ≡ auth → fetch → extract → deobfuscate → decompile → install source → rebuild → test
```

Latest capture (upstream had moved on since the previous one):

| Artifact | Bytes | sha256 |
|---|---:|---|
| `payload.bin` (PE32+ x64) | 25,160,704 | `2d2d4eca01db6d81a4566762b3864b223f87fa3ff82f61e7a6f7fc6fc8fc4528` |
| `payload_jar/vape_client.jar` (carved) | 25,096,879 | `75411df446b7af9ab963ddb864345c1a8124830fe102812986dcad6198d9b722` |
| `payload_jar/vape_client_deobf_full.jar` | 25,253,236 | `a6756f968093eb63c62f1482ba508b204d871b2aff1e2b49ea5293b129965ecd` |
| `src/payload` → rebuilt `vape_client.jar` | 30,166,774 | `2b6c9e9ee501ac8e96b4e65a0fa040958458e9b3c1642f33d87992fdb0854c54` |

The last row is **our** artifact, not Vape's: the client recompiled from
`src/payload/java` with Maven, ASM/Javassist relocated into
`gg.vape.shaded.*` and the dependencies shaded in. It is larger than the
original because it carries full `netty-all`, not the subset Vape had picked.

The JAR manifest identifies this build as **Vape 4.22**
(`Implementation-Title: Vape 4.22 recovered Java payload`). The previous
capture (24,487,424 B, `856e5c2e…d5f80d`) is superseded.

Two gotchas worth knowing:

- `/loader/login` is hardware-locked. A mismatch answers
  `400 {"error":"HWID locked. Please reset your HWID in your account settings."}`
  — reset the HWID in the account settings, then retry (the CLI prints the
  `--hwid` it sent).
- app-auth/browser codes expire after ~285 s (see `PROTOCOL.md`); password
  login avoids the browser round-trip entirely.

The cleaned `request_payload.c` in this directory implements the same
request for reference.
