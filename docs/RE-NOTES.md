# v4-chat RE notes — 60e47510.exe

Target: `60e47510.exe` — PE32+ x86-64, Vape V4 Minecraft loader.
Size 1364047 bytes,
sha256 `581a7ab6c901a9c51bb8fecb35ab91b62e12c03d302b4adc5e1a2be80044470c`.
PE facts (verified 2026-10-16 via `objdump -p` + python header parse):
entry RVA `0xc8050` (VA `0x1400c8050`), image base `0x140000000`,
linker 14.51, link date Sep 28 2026 (`TimeDateStamp 0x6aba86c0`;
objdump-local 16:24:48 / UTC 15:24:48), subsystem GUI (2).
6 sections — see `src/imports.txt` header for VAddr/VSize/RawSize/entropy.
Full import table: `src/imports.txt` (verbatim `objdump -p` + header).

Environment note: REA analysis tools were DOWN at the time of writing.
Everything below marked "verified locally" used only
`objdump` / `otool` / `strings` / `python3` stdlib — no installs.
Ghidra-decompiler content comes from the pre-existing files, not re-run here.

## (a) Recovered files — provenance

Both files in `src/` are Ghidra 12.1.4 decompiler output obtained via
REA `analyze_function` **before** the outage. Headers in each file state this;
roles/callers were re-checked against the on-disk source on 2026-10-16
(line refs below are to the current `src/` files).

### `src/FUN_140005af0_request_payload.c`
- Proc `FUN_140005af0` @ `0x140005af0`, 295 bytes (header claim; VA is inside
  `.text`, verified locally).
- Role: builds the `"token=<token>"` POST body (`FUN_14000a024(local_40,"token=",param_3)`)
  and POSTs it to `/api/v1/client/request-payload`
  (`FUN_1400055c0(&local_80,param_2,L"/api/v1/client/request-payload",local_40)`).
- Caller: `FUN_140007c30` (per file header; not re-verified — REA down).
- String note: the endpoint is UTF-16LE in the binary
  (verified locally: wide `"/api/v1/client/request-payload"` @ file off `0xe85c0`,
  wide `"request-payload"` @ `0xe85de`; no ASCII copy).
  ASCII `"token="` @ `0xe85b4` is the narrow format fragment.

### `src/FUN_140023070_bootstrap_inject.c`
- Proc `FUN_140023070` @ `0x140023070`, 1204 bytes (header claim; VA inside
  `.text`, verified locally).
- Role: socket-bootstrap injector into the Minecraft process:
  - builds `"Bootstrap"` / `"BootstrapAck"` names (`FUN_140022788`, lines 88–89),
    `CreateFileMappingW(..., 0x1a8, ...)` + `MapViewOfFile(..., 0x1a8)` (lines 94, 104),
    `CreateEventW(..., L"BootstrapAck"-name)` (line 109);
  - header magic `lpBaseAddress[0]=0x54423456`, size `0x1a80002` (lines 133–134);
  - opens target via `FUN_140021d3c` ("Failed to open Minecraft process", line 157),
    writes Product DLL path (`FUN_140021f28`, "Failed to write the Product DLL path",
    lines 163–167), resolves loader entry (`FUN_14002262c`, line 176),
    starts loading (`FUN_1400224a0`, line 181);
  - two 30 s waits: `WaitForSingleObject(local_b8,30000)` (loader thread, line 187)
    then `WaitForSingleObject(hObject,30000)` (ack event, line 189);
  - ack magic: success iff `lpBaseAddress[0x69]==2` (line 191);
    else `"Product DLL rejected the socket bootstrap"` (line 195) /
    `"Product DLL did not acknowledge the socket bootstrap"` (line 198).
- Caller: `FUN_140007c30` (per file header).
- String note: all of the above literals are UTF-16LE in the binary
  (verified locally, e.g. wide `"Bootstrap"` @ `0xe9f98`,
  `"BootstrapAck"` @ `0xe9fb0`, `"Minecraft process"` @ `0xea0be`,
  `"Product DLL"` @ `0xea118`, `"NativeLoader.dll"` @ `0xe8652`).
  `VAPE_ZEUS_ADDRESS` exists as both ASCII @ `0xe9de8` and wide @ `0xe9f00`;
  `VAPE_ONLINE_BASE_URL` wide @ `0xe86a8`.

## (b) NOT yet recovered

REA was down before these two could be decompiled. Do not treat the
one-line roles below as decompiler output — they are triage hypotheses from
wide-string xrefs only.

### `FUN_140023524` @ `0x140023524` — 2793 bytes — reflective-inject (triage)
- VA inside `.text` (RVA `0x23524`; verified locally). Size 2793 bytes per task brief.
- Signature strings (both UTF-16LE-only, verified locally 2026-10-16):
  - wide `"Failed to write reflective image into target process"` @ file off `0xea3f0`;
  - wide `"Reflective loader returned failure"` @ `0xea4c0`;
  - related: ASCII `"ReflectiveLoader"` @ `0xe9e28`; wide `"reflective"` @ `0xea410`.
  - No ASCII copies of the two long sentences (confirms wide-string build).
- Adjacency hint: starts 0x4B4 bytes after `FUN_140023070` (1204-byte) start —
  consistent with being the next inject-stage helper, but bounds need Ghidra.

### `FUN_14000e2ec` @ `0x14000e2ec` — 1905 bytes — app-auth (triage)
- VA inside `.text` (RVA `0xe2ec`; verified locally). Size 1905 bytes per task brief.
- Signature strings (both UTF-16LE-only, verified locally):
  - wide `"/api/v1/app-auth/generate"` @ `0xe8a28` (also wide `"app-auth"` @ `0xe8a38`);
  - wide `"https://www.vape.chat/app-auth/proceed/"` @ `0xe8ab0`.
  - No ASCII copies.
- Expected behaviour (unconfirmed): request an app-auth challenge/session from
  `/api/v1/app-auth/generate`, then direct the user to the `vape.chat/.../proceed/`
  URL — i.e. the browser-mediated auth step before the request-payload call above.

### How to re-run once REA is repaired
1. Repair/check REA backend (Ghidra 12.1.4 must be the decompiler).
2. Re-run the same entry point used for the two existing files:
   `analyze_function` on `60e47510.exe` (sha256 `581a7ab6…`) at
   `0x140023524` and at `0x14000e2ec`.
3. Save outputs with the existing naming/header convention:
   - `src/FUN_140023524_reflective_inject.c` with header
     `Proc: FUN_140023524 @ 0x140023524, 2793 bytes. Role: …`
   - `src/FUN_14000e2ec_app_auth.c` with header
     `Proc: FUN_14000e2ec @ 0x14000e2ec, 1905 bytes. Role: …`
   keeping the `Ghidra 12.1.4 decompiler output via REA analyze_function.` line.
4. Confirm: caller xref (suspect `FUN_140007c30` by analogy — verify, don't assume),
   wide-string xrefs to the signature literals at the file offsets above,
   and byte-size match (2793 / 1905).
5. If sizes disagree, prefer the decompiler's function bounds and update this file.

## (c) Anti-debug / anti-VM verdict: none found (local check)

Method: raw-byte search (ASCII + UTF-16LE) over the whole file, `strings` greps,
full `objdump -p` import-table grep, `.text` opcode scan, python PE/TLS parse —
all 2026-10-16, REA down. Case-insensitive for VM-vendor strings.

Absent (ASCII **and** wide **and** import table):
`IsDebuggerPresent`, `CheckRemoteDebuggerPresent`, `NtQueryInformationProcess`,
`OutputDebugString`, `GetTickCount`, `RDTSC` (as text), and VM-vendor strings
`VMWARE` / `VirtualBox` / `VBOX` / `QEMU` / `VMXh` / `VBoxGuest` / `vmci`
(all case-insensitive). `strings | grep -i` for the debugger/VM set returns nothing;
import-table grep for the debugger set returns 0 hits.

`RDTSC` opcode (`0F 31`): exactly **one** `0F 31` byte-pair in `.text`
(`text+0x22f9`, file off `0x26f9`, VA `0x1400032f9`), but context bytes are
`e8 0f 31 02 00` — i.e. a `CALL rel32` displacement, not an `RDTSC` instruction.
No real `RDTSC` found.

Present but dual-use (loader-legitimate, not by themselves anti-debug):
`NtAllocateVirtualMemory` (@`0xe9d20`), `NtFreeVirtualMemory` (@`0xe9d40`),
`NtProtectVirtualMemory` (@`0xe9d60`), `NtWriteVirtualMemory` (@`0xe9d82`),
`NtOpenProcess` (@`0xe9d10`), `NtCreateThreadEx` (@`0xe9da0`) — ASCII-library
strings; `ReadProcessMemory`, `CreateToolhelp32Snapshot`, `Process32FirstW`,
`Process32NextW`, `QueryPerformanceCounter`, `SetUnhandledExceptionFilter`
(all in the import table / string table; see `src/imports.txt`).
`CreateToolhelp32Snapshot`/`Process32First`/`Next` + `Module32First`/`Next` are
also consistent with ordinary process/module enumeration (and the loader's
"Select a Minecraft" flow); `QueryPerformanceCounter` with no `GetTickCount`/
`RDTSC` alongside is weak timing evidence at most.

TLS: directory present (`objdump -p` entry 9, RVA `0xf2680` size `0x28`:
`StartAddressOfRawData 0x1400f31c0`, `AddressOfCallBacks 0x1400ca968`).
Callback array is empty: RVA `0xca968` → file off `0xc9768`,
`cb[0]=0x0`, `cb[1]=0x0` (python `struct <Q` read). Verdict: CRT stub, no TLS-callback
anti-debug.
