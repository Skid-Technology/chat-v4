#!/usr/bin/env python3
"""
vape_loader.py — reproduce the Vape V4 loader (60e47510.exe) end to end.

Protocol recovered from the original loader via REA/Ghidra (artifacts:

    password login : POST /loader/login                [application/json]
                     body {"username":"..","password":"..","hwid":".."}
                     resp {"token":".."} | {"error":".."}
    browser login  : POST /api/v1/app-auth/generate    [x-www-form-urlencoded]
                     body edition=v4&hwid=<hwid>
                     resp <code>                        (40-char plain text)
                     open https://www.vape.chat/app-auth/proceed/<code>
                     poll POST /api/v1/app-auth/status  [x-www-form-urlencoded]
                     body token=<code>
                     resp {"status":"success","token":".."} | {"status":"timed out"}
    request payload: POST /api/v1/client/request-payload [x-www-form-urlencoded]
                     body token=<token>
                     resp {"downloadToken":".."}
    download       : POST /api/v1/client/payload         [x-www-form-urlencoded]
                     body downloadToken=<downloadToken>
                     resp raw PE32+ image

Then: carve the embedded ZIP/JAR out of the PE, deobfuscate it with
zkm-flowdeobf, and decompile it with CFR.

Base URL http://online.vape.chat ; WinHTTP user agent "Microsoft-WNS/10.0".

Usage:
    vape_loader.py login --browser
    vape_loader.py login --username me@x --password hunter2
    vape_loader.py fetch   -o payload.bin
    vape_loader.py extract payload.bin -o payload_jar/vape_client.jar
    vape_loader.py deobf   payload_jar/vape_client.jar -o payload_jar/vape_client_deobf_full.jar
    vape_loader.py decompile payload_jar/vape_client_deobf_full.jar -o src/decompiled
    vape_loader.py all --browser
"""
from __future__ import annotations

import argparse
import gzip
import hashlib
import json
import os
import platform
import re
import subprocess
import sys
import time
import urllib.error
import urllib.request
import uuid
import webbrowser
import zipfile
from pathlib import Path

BASE_URL = "http://online.vape.chat"
UA = "Microsoft-WNS/10.0"
PROCEED_URL = "https://www.vape.chat/app-auth/proceed/"
STATE = Path(os.environ.get("VAPE_CLI_STATE", Path.home() / ".config/vape-cli/state.json"))
ROOT = Path(__file__).resolve().parents[2]


# ─────────────────────────── HTTP ───────────────────────────

def _post(url: str, body: bytes, content_type: str, timeout: int = 60, retries: int = 3):
    req = urllib.request.Request(url, data=body, method="POST")
    req.add_header("User-Agent", UA)
    req.add_header("Content-Type", content_type)
    req.add_header("Accept", "*/*")
    last: Exception | None = None
    for attempt in range(retries):
        try:
            with urllib.request.urlopen(req, timeout=timeout) as r:
                data = r.read()
                if r.headers.get("Content-Encoding") == "gzip":
                    data = gzip.decompress(data)
                return r.status, dict(r.headers), data
        except urllib.error.HTTPError as e:
            return e.code, dict(e.headers or {}), e.read()
        except Exception as e:  # timeouts, resets, DNS blips
            last = e
            if attempt + 1 < retries:
                time.sleep(1.5 * (attempt + 1))
    raise SystemExit(f"network error talking to {url}: {last}")


def post_form(path: str, fields: dict, base: str = BASE_URL):
    body = "&".join(f"{k}={v}" for k, v in fields.items()).encode()
    return _post(base + path, body, "application/x-www-form-urlencoded")


def post_json(path: str, obj, base: str = BASE_URL):
    body = json.dumps(obj, separators=(",", ":")).encode()
    return _post(base + path, body, "application/json; charset=utf-8")


def _json(body: bytes):
    try:
        return json.loads(body.decode("utf-8", "replace"))
    except Exception:
        return None


# ─────────────────────────── state / hwid ───────────────────────────

def load_state() -> dict:
    try:
        return json.loads(STATE.read_text())
    except Exception:
        return {}


def save_state(state: dict):
    STATE.parent.mkdir(parents=True, exist_ok=True)
    STATE.write_text(json.dumps(state, indent=2))


def derive_hwid() -> str:
    """8 uppercase hex chars, matching the loader's %08X of the C: serial.

    On macOS there is no C: volume, so derive a stable value from the host
    UUID; on Windows, use the real volume serial."""
    if platform.system() == "Windows":
        try:
            import ctypes
            serial = ctypes.c_uint(0)
            ctypes.windll.kernel32.GetVolumeInformationW(
                "C:\\", None, 0, ctypes.byref(serial), None, None, None, 0)
            return f"{serial.value & 0xFFFFFFFF:08X}"
        except Exception:
            pass
    seed = uuid.getnode()
    try:
        out = subprocess.run(["ioreg", "-rd1", "-c", "IOPlatformExpertDevice"],
                             capture_output=True, text=True, timeout=5).stdout
        m = re.search(r'"IOPlatformUUID"\s*=\s*"([^"]+)"', out)
        if m:
            seed = int(hashlib.sha256(m.group(1).encode()).hexdigest()[:16], 16)
    except Exception:
        pass
    return f"{seed & 0xFFFFFFFF:08X}"


def resolve_hwid(explicit: str | None) -> str:
    st = load_state()
    if explicit:
        st["hwid"] = explicit
    if "hwid" not in st:
        st["hwid"] = derive_hwid()
    save_state(st)
    return st["hwid"]


# ─────────────────────────── login ───────────────────────────

def login_password(user: str, password: str, hwid: str, base: str) -> str:
    status, _, body = post_json("/loader/login",
                                {"username": user, "password": password, "hwid": hwid}, base)
    j = _json(body) or {}
    if j.get("token"):
        return j["token"]
    msg = str(j.get("error") or body[:200])
    if "hwid" in msg.lower():
        msg += (f"\n  hwid sent: {hwid}\n"
                "  Either reset the HWID in your Vape account settings, or re-run with the\n"
                "  value the account expects:  vape_loader.py --hwid XXXXXXXX login …")
    raise SystemExit(f"login failed (HTTP {status}): {msg}")


def login_browser(hwid: str, base: str, timeout: int = 300,
                  open_browser: bool = True, code: str | None = None) -> str:
    if not code:
        status, _, body = post_form("/api/v1/app-auth/generate",
                                    {"edition": "v4", "hwid": hwid}, base)
        code = body.decode("utf-8", "replace").strip().strip('"')
        if status != 200 or len(code) != 40:
            raise SystemExit(f"app-auth/generate failed (HTTP {status}): {code[:200]!r}")

    st = load_state()
    st["app_auth_code"] = code
    st["hwid"] = hwid
    save_state(st)

    url = PROCEED_URL + code
    print(f"[login] code        {code}")
    print(f"[login] proceed URL {url}")
    print("[login] open it in a browser that is ALREADY signed in to vape.chat and")
    print("        confirm promptly — the code is short-lived, and the site answers")
    print("        'Unable to complete operation. Please check uri and try again.'")
    print("        once it has expired.")
    if open_browser:
        try:
            webbrowser.open(url)
        except Exception:
            pass

    print("[login] waiting for browser confirmation…")
    deadline = time.time() + timeout
    while time.time() < deadline:
        time.sleep(2)
        _, _, sbody = post_form("/api/v1/app-auth/status", {"token": code}, base)
        j = _json(sbody) or {}
        status_field = str(j.get("status", ""))
        if status_field == "success":
            token = j.get("token")
            if not token:
                raise SystemExit("status=success but no token in response")
            return token
        if status_field == "timed out":
            raise SystemExit("browser login timed out (server)")
        sys.stdout.write(".")
        sys.stdout.flush()
    raise SystemExit(
        "\nbrowser login timed out locally. If the browser showed\n"
        "  \"Unable to complete operation. Please check uri and try again.\"\n"
        "the code expired before the login finished. Retry (the URL above stays\n"
        "valid only briefly, so be signed in first), or use password login:\n"
        "  vape_loader.py login --username <email> --password <pw>")


def get_login(args) -> str:
    hwid = resolve_hwid(getattr(args, "hwid", None))
    base = args.base_url
    user = getattr(args, "username", None) or os.environ.get("VAPE_USERNAME")
    password = getattr(args, "password", None) or os.environ.get("VAPE_PASSWORD")
    if user and password:
        token = login_password(user, password, hwid, base)
    elif user or password:
        raise SystemExit("need both a username and a password")
    else:
        token = login_browser(hwid, base,
                              timeout=getattr(args, "timeout", 300),
                              open_browser=not getattr(args, "no_open", False),
                              code=getattr(args, "code", None))
    st = load_state()
    st["token"] = token
    st["hwid"] = hwid
    save_state(st)
    return token


# ─────────────────────────── payload ───────────────────────────

def request_payload(token: str, base: str) -> str:
    status, _, body = post_form("/api/v1/client/request-payload", {"token": token}, base)
    j = _json(body) or {}
    if j.get("downloadToken"):
        return j["downloadToken"]
    raise SystemExit(f"request-payload failed (HTTP {status}): {body[:200]!r}")


def download_payload(download_token: str, base: str) -> bytes:
    status, _, body = post_form("/api/v1/client/payload",
                                {"downloadToken": download_token}, base)
    if status != 200 or not body:
        raise SystemExit(f"payload download failed (HTTP {status})")
    return body


def validate_pe(data: bytes):
    if len(data) < 0x40 or data[:2] != b"MZ":
        raise SystemExit("payload is not a valid PE image (bad MZ)")
    e_lfanew = int.from_bytes(data[0x3C:0x40], "little")
    if data[e_lfanew:e_lfanew + 4] != b"PE\x00\x00":
        raise SystemExit("payload is not a valid PE image (bad PE header)")
    magic = int.from_bytes(data[e_lfanew + 24:e_lfanew + 26], "little")
    if magic != 0x20B:
        raise SystemExit(f"payload is not a 64-bit PE (magic 0x{magic:x})")


def cmd_fetch(args) -> Path:
    base = args.base_url
    token = args.token or load_state().get("token")
    if not token:
        raise SystemExit("no token — run `login` first or pass --token")
    dt = request_payload(token, base)
    print(f"[fetch] downloadToken={dt[:8]}…")
    data = download_payload(dt, base)
    validate_pe(data)
    out = Path(args.out)
    out.write_bytes(data)
    print(f"[fetch] wrote {out} ({len(data)} bytes) sha256={hashlib.sha256(data).hexdigest()}")
    return out


# ─────────────────────────── extract ───────────────────────────

def carve_jar(pe: bytes) -> bytes:
    start = pe.find(b"PK\x03\x04")
    if start < 0:
        raise SystemExit("no embedded ZIP found in the payload")
    eocd = pe.rfind(b"PK\x05\x06")
    if eocd < 0:
        raise SystemExit("embedded ZIP has no end-of-central-directory record")
    end = eocd + 22
    jar = pe[start:end]
    with zipfile.ZipFile(__import__("io").BytesIO(jar)) as _:
        pass  # integrity check
    return jar


def cmd_extract(args) -> Path:
    pe = Path(args.payload).read_bytes()
    jar = carve_jar(pe)
    out = Path(args.out)
    out.write_bytes(jar)
    print(f"[extract] carved {len(jar)} bytes -> {out} sha256={hashlib.sha256(jar).hexdigest()}")
    return out


# ─────────────────────────── deobfuscate ───────────────────────────

DEOBF_FLAGS = [
    "--remove-identity-rethrows", "--normalize-exception-ranges",
    "--normalize-zkm-shared-tails", "--materialize-residual-stacks",
    "--specialize-binary-sentinel-tails", "--clean-constant-residual-islands",
]


def cmd_deobf(args) -> Path:
    tool = ROOT / "zkm-flowdeobf"
    if not (tool / "build.gradle").is_file():
        raise SystemExit(f"zkm-flowdeobf not found under {ROOT}")
    src = Path(args.jar).resolve()
    out = Path(args.out).resolve()
    report = Path(args.report).resolve()
    gradle = os.environ.get("GRADLE", "gradle")
    cmd_args = f"{src} {out} {report} " + " ".join(DEOBF_FLAGS)
    print(f"[deobf] gradle run --args=\"{cmd_args}\"")
    rc = subprocess.run([gradle, "run", "--console=plain", "-q",
                         f"--args={cmd_args}"], cwd=tool).returncode
    if rc != 0 or not out.is_file():
        raise SystemExit("deobfuscation failed")
    print(f"[deobf] wrote {out} sha256={hashlib.sha256(out.read_bytes()).hexdigest()}")
    return out


# ─────────────────────────── decompile ───────────────────────────

def cmd_decompile(args) -> Path:
    cfr = ROOT / "tools/cfr.jar"
    if not cfr.is_file():
        raise SystemExit("tools/cfr.jar missing — run scripts/fetch-tools.sh")
    src = Path(args.jar).resolve()
    out = Path(args.out).resolve()
    out.mkdir(parents=True, exist_ok=True)
    print(f"[decompile] cfr {src.name} -> {out}")
    rc = subprocess.run(["java", "-jar", str(cfr), str(src), "--outputdir", str(out)]).returncode
    if rc != 0:
        raise SystemExit("cfr decompilation failed")
    n = sum(1 for _ in out.rglob("*.java"))
    print(f"[decompile] wrote {n} .java files to {out}")
    return out


# ─────────────────────────── pipeline ───────────────────────────

def cmd_login(args):
    token = get_login(args)
    print(f"[login] token={token[:12]}… (saved to {STATE})")
    return token


def cmd_all(args):
    token = get_login(args)
    print(f"[login] token={token[:12]}…")
    args.token = token
    args.out = args.payload_out
    pe = cmd_fetch(args)
    args.payload = str(pe)
    args.out = args.jar_out
    jar = cmd_extract(args)
    args.jar = str(jar)
    args.out = args.deobf_out
    args.report = str(Path(args.deobf_out).with_suffix(".report.tsv"))
    deobf = cmd_deobf(args)
    args.jar = str(deobf)
    args.out = args.src_out
    cmd_decompile(args)


# ─────────────────────────── cli ───────────────────────────

def build_parser() -> argparse.ArgumentParser:
    p = argparse.ArgumentParser(prog="vape_loader.py",
                                description="Vape V4 loader — login, fetch, extract, deobfuscate.")
    p.add_argument("--base-url", default=BASE_URL)
    p.add_argument("--hwid", help="override the hardware id (8 hex chars)")
    sub = p.add_subparsers(dest="cmd", required=True)

    l = sub.add_parser("login", help="authenticate and store a token")
    l.add_argument("--browser", action="store_true", help="browser flow (default)")
    l.add_argument("--username")
    l.add_argument("--password")
    l.add_argument("--code", help="reuse an existing app-auth code instead of generating one")
    l.add_argument("--timeout", type=int, default=300)
    l.add_argument("--no-open", action="store_true", help="don't launch a browser")
    l.set_defaults(func=cmd_login)

    f = sub.add_parser("fetch", help="request + download the payload PE")
    f.add_argument("--token")
    f.add_argument("-o", "--out", default="payload.bin")
    f.set_defaults(func=cmd_fetch)

    e = sub.add_parser("extract", help="carve the client JAR out of the PE")
    e.add_argument("payload", nargs="?", default="payload.bin")
    e.add_argument("-o", "--out", default="payload_jar/vape_client.jar")
    e.set_defaults(func=cmd_extract)

    d = sub.add_parser("deobf", help="deobfuscate the client JAR (zkm-flowdeobf)")
    d.add_argument("jar", nargs="?", default="payload_jar/vape_client.jar")
    d.add_argument("-o", "--out", default="payload_jar/vape_client_deobf_full.jar")
    d.add_argument("--report", default="payload_jar/deobf_report_full.tsv")
    d.set_defaults(func=cmd_deobf)

    c = sub.add_parser("decompile", help="decompile the JAR with CFR")
    c.add_argument("jar", nargs="?", default="payload_jar/vape_client_deobf_full.jar")
    c.add_argument("-o", "--out", default="src/decompiled")
    c.set_defaults(func=cmd_decompile)

    a = sub.add_parser("all", help="login -> fetch -> extract -> deobf -> decompile")
    a.add_argument("--browser", action="store_true")
    a.add_argument("--username")
    a.add_argument("--password")
    a.add_argument("--code", help="reuse an existing app-auth code instead of generating one")
    a.add_argument("--timeout", type=int, default=300)
    a.add_argument("--no-open", action="store_true")
    a.add_argument("--payload-out", default="payload.bin")
    a.add_argument("--jar-out", default="payload_jar/vape_client.jar")
    a.add_argument("--deobf-out", default="payload_jar/vape_client_deobf_full.jar")
    a.add_argument("--src-out", default="src/decompiled")
    a.set_defaults(func=cmd_all)

    return p


def main(argv=None):
    args = build_parser().parse_args(argv)
    args.func(args)
    # persist hwid continuity
    if args.hwid:
        st = load_state()
        st["hwid"] = args.hwid
        save_state(st)


if __name__ == "__main__":
    main()
