# injector — recreated Vape-style loader (clean-room reconstruction)

Reconstructed from `60e47510.exe` (see `src/FUN_*.c` decompiles and
`src/clean/`). Behavior-compatible, written from scratch in portable C11.

## Layout

```text
src/injector/
  README.md        this file
  loader.h         shared protocol: bootstrap block, endpoints, errors
  loader.c         pipeline: auth -> acquire -> validate -> inject -> ack-wait
  os_win32.c       Windows backend (real APIs; build with MSVC/MinGW on Windows)
  os_stub.c        stub backend: runs portable logic anywhere (validates bundle)
  bundle.py        packs payload.bin + vape_client.jar into bundle.dat
  Makefile         builds the stub test binary here
```

Payloads live in the workspace root and are **referenced, not copied**:

- `payload.bin` — the injected x64 DLL (sha256 `856e5c2e…5f80d`)
- `payload_jar/vape_client.jar` — inner Java client (sha256 `1aece754…378318`)
- `bundle.dat` — both packed together (`VBDL`, per-entry sha256)

No login: the bundled path extracts + sha-verifies `payload.bin` from
`bundle.dat` offline (`bundle_extract` in `loader.c`, sha256 in
`sha256.c`). The auth/download stages are documented for fidelity but
not on the run path — `loader_test bundle.dat` goes straight to
validate → inject → ack-wait.

## Bundle format (`bundle.dat`, little-endian)

```text
u32 magic  0x56424C44 ("VBDL")
u32 version (1)
u32 n_entries
repeat: u8 namelen, name, u64 offset, u64 size, u8[32] sha256
blob data (payload.bin, vape_client.jar)
```

```sh
python3 src/injector/bundle.py payload.bin payload_jar/vape_client.jar
make -C src/injector && ./src/injector/loader_test
```

## Pipeline (mirrors FUN_140007c30 dispatcher)

1. **auth** — browser flow (`/api/v1/app-auth/generate` → open
   `…/proceed/<code>` → poll `/status`) or `/loader/login`.
2. **acquire** — network mode: `token=` → `/request-payload` →
   `downloadToken=` → `/payload`. Bundled mode: read from `bundle.dat`.
3. **validate** — `MZ` → `e_lfanew` → `PE\0\0` → `PE32+` (0x20b),
   min size 0x40. (Decompile: `FUN_140023524` head.)
4. **inject** — path A reflective (`FUN_140022e10` entry != 0) or path B
   manual-map + `LoadLibraryA` stub (`FUN_140022f60`, 0x200 bytes).
   Bootstrap block `0x1A8` (`"VB4T"`, see `src/clean/bootstrap_inject.c`).
5. **ack-wait** — `BootstrapAck` event, 30 s, `status == 2`.

## Windows build (on Windows)

```bat
cl /std:clatest src\injector\loader.c src\injector\os_win32.c winhttp.lib
```

`os_win32.c` documents the exact API sequence; D3D11/ImGui UI from
`ui/` + `FUN_1400278b8/1f268/20744/21550` plugs in as the front end.
