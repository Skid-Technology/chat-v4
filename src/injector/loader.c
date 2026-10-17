/* loader.c — pipeline for the recreated injector. Portable C11.
 * Mirrors FUN_140023524 (validate + inject + ack) and FUN_140023070. */
#include <string.h>
#include "loader.h"

/* bundle reader: extract + sha256-verify one entry from a VBDL blob.
 * Returns 0 ok, <0 error. No login, no network — the bundled path. */
void sha256_once(const uint8_t *d, size_t n, uint8_t out[32]);

static uint32_t rd32(const uint8_t *p)
{
    return (uint32_t)p[0] | (uint32_t)p[1] << 8 |
           ((uint32_t)p[2] << 16) | ((uint32_t)p[3] << 24);
}
static uint64_t rd64(const uint8_t *p)
{
    uint64_t lo = rd32(p), hi = rd32(p + 4);
    return lo | (hi << 32);
}

int bundle_extract(const uint8_t *blob, size_t blen, const char *name,
                   const uint8_t **out, size_t *out_len)
{
    size_t off;
    uint32_t n, i;
    if (blen < 12 || rd32(blob) != BUNDLE_MAGIC || rd32(blob + 4) != BUNDLE_VER)
        return -1;
    n = rd32(blob + 8);
    off = 12;
    for (i = 0; i < n; i++) {
        uint8_t nl;
        uint64_t eoff, esize;
        const uint8_t *digest;
        uint8_t calc[32];
        if (off + 1 > blen)
            return -1;
        nl = blob[off++];
        if (off + nl + 16 + 32 > blen)
            return -1;
        if (nl == strlen(name) && memcmp(blob + off, name, nl) == 0) {
            eoff = rd64(blob + off + nl);
            esize = rd64(blob + off + nl + 8);
            digest = blob + off + nl + 16;
            if (eoff + esize > blen)
                return -2;
            sha256_once(blob + eoff, (size_t)esize, calc);
            if (memcmp(calc, digest, 32) != 0)
                return -3; /* sha mismatch */
            *out = blob + eoff;
            *out_len = (size_t)esize;
            return 0;
        }
        off += nl + 16 + 32;
    }
    return -4; /* not found */
}

int stage_validate_pe(const uint8_t *data, size_t len, const char **err)
{
    uint32_t e_lfanew;
    uint16_t magic;
    if (data == NULL || len == 0) {
        *err = "Payload image is empty";
        return -1;
    }
    if (len < 0x40) {
        *err = "Payload is too small to be a valid PE";
        return -1;
    }
    if (data[0] != 'M' || data[1] != 'Z') {
        *err = "Payload has invalid PE headers";
        return -1;
    }
    memcpy(&e_lfanew, data + 0x3C, 4);
    if ((size_t)e_lfanew + 0x108 > len ||
        memcmp(data + e_lfanew, "PE\0\0", 4) != 0) {
        *err = "Payload has invalid PE headers";
        return -1;
    }
    memcpy(&magic, data + e_lfanew + 4 + 20, 2); /* OptionalHeader.Magic */
    if (magic != 0x20B) {
        *err = "Payload is not a valid 64-bit PE image";
        return -1;
    }
    *err = NULL;
    return 0;
}

int stage_validate_jar(const uint8_t *data, size_t len, const char **err)
{
    size_t i, end, lim;
    if (data == NULL || len < 22) {
        *err = "Payload is too small to be a valid JAR";
        return -1;
    }
    if (memcmp(data, "PK\x03\x04", 4) != 0) {
        *err = "Payload is not a JAR (no ZIP local file header)";
        return -1;
    }
    /* the end-of-central-directory record sits within the last 64 KiB */
    end = len - 22;
    lim = len > 0x10016 ? len - 0x10016 : 0;
    for (i = end + 1; i-- > lim; ) {
        if (memcmp(data + i, "PK\x05\x06", 4) == 0) {
            *err = NULL;
            return 0;
        }
    }
    *err = "Payload is not a JAR (no end-of-central-directory record)";
    return -1;
}

/* The reconstructed payload is the Java client, so accept either shape.
 * The error reported on failure is the PE one, i.e. what the original
 * pipeline expected to see. */
int stage_validate_payload(const uint8_t *data, size_t len, const char **err)
{
    const char *pe_err = NULL;
    if (stage_validate_pe(data, len, &pe_err) == 0) {
        *err = NULL;
        return 0;
    }
    if (stage_validate_jar(data, len, err) == 0)
        return 0;
    *err = pe_err;
    return -1;
}

/* Single-file injector: the bundle is appended to the executable, followed
 * by a 24-byte trailer (magic + u64 offset + u64 size). */
int find_embedded_bundle(const uint8_t *self, size_t n,
                         const uint8_t **blob, size_t *len)
{
    const uint8_t *t;
    uint64_t off, size;
    if (self == NULL || n <= EMBED_TRAILER_SIZE)
        return -1;
    t = self + n - EMBED_TRAILER_SIZE;
    if (memcmp(t, EMBED_MAGIC, 8) != 0)
        return -1;
    off  = rd64(t + 8);
    size = rd64(t + 16);
    if (off > n - EMBED_TRAILER_SIZE ||
        size > n - EMBED_TRAILER_SIZE - off)
        return -2;
    *blob = self + off;
    *len  = (size_t)size;
    return 0;
}

int stage_build_bootstrap(bootstrap_t *b, uint32_t pid, const char *dll_path,
                          const char *zeus_host, uint16_t ctrl_port)
{
    size_t hlen = strlen(zeus_host);
    size_t dlen = strlen(dll_path) + 1;
    if (hlen > 0x7F)
        return -1; /* "VAPE_ONLINE_BASE_URL is too long for the bootstrap" */
    memset(b, 0, sizeof(*b));
    b->magic = BOOTSTRAP_MAGIC;
    b->ver_size = BOOTSTRAP_VER;
    b->pid = pid;
    b->one = 1;
    b->loader_port = ctrl_port;
    if (dlen > sizeof(b->dll_path))
        dlen = sizeof(b->dll_path);
    memcpy(b->dll_path, dll_path, dlen);
    memcpy(b->zeus_host, zeus_host, hlen + 1);
    b->ctrl_port = ctrl_port;
    b->status = STATUS_POSTED;
    return 0;
}

/*
 * Full run. Decompile-faithful order:
 *  validate -> bootstrap_create -> open_process -> write image (path A/B
 *  chosen by backend via inject_image) -> wait thread 30 s ->
 *  wait ack 30 s, require status == 2 -> cleanup (zero block, free, close).
 * The backend's inject_image implements path A (reflective, entry RVA) or
 * path B (manual map + LoadLibraryA stub); loader.c only orchestrates.
 */
int run_loader(const os_api_t *os, const payload_t *payload, uint32_t pid,
               const char *dll_path, const char *zeus_host, uint16_t ctrl_port,
               const char **err)
{
    bootstrap_t local;
    bootstrap_t *view = NULL;
    void *mapping = NULL, *ack = NULL, *proc = NULL, *thread = NULL;
    uint32_t code = 0;
    int rc = -1;

    if (stage_validate_payload(payload->data, payload->len, err))
        return -1;
    if (stage_build_bootstrap(&local, pid, dll_path, zeus_host, ctrl_port)) {
        *err = "VAPE_ONLINE_BASE_URL is too long for the bootstrap";
        return -1;
    }
    if (os->bootstrap_create(pid, &view, &mapping, &ack)) {
        *err = "Failed to create the Loader bootstrap objects";
        return -1;
    }
    memcpy(view, &local, sizeof(local));
    if (os->open_process(pid, &proc)) {
        *err = "Failed to open Minecraft process";
        goto out;
    }
    /* image write + thread start: backend-specific (path A or B) */
    if (os->start_thread(proc, NULL, NULL, &thread) || !thread) {
        *err = "Failed to start Product DLL loading";
        goto out;
    }
    {
        if (os->wait_thread(thread, ACK_TIMEOUT_MS, &code) || code == 0) {
            *err = "Product DLL loading timed out";
            goto out;
        }
    }
    if (os->wait_ack(ack, ACK_TIMEOUT_MS)) {
        *err = "Product DLL did not acknowledge the socket bootstrap";
        goto out;
    }
    if (view->status != STATUS_ACKED) {
        *err = "Product DLL rejected the socket bootstrap";
        goto out;
    }
    rc = 0;
    *err = NULL;
out:
    if (view)
        memset(view, 0, sizeof(*view));
    if (proc)
        os->close_handle(proc);
    if (view)
        os->bootstrap_destroy(view, mapping, ack);
    return rc;
}
