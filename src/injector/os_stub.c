/* os_stub.c — stub backend: runs the portable pipeline anywhere.
 * Simulates a target process with malloc'd memory so validate/build/run
 * exercise real logic. Heap stands in for the remote process, and the ack is
 * produced locally, so the full order in run_loader() is walked without a
 * Windows target. The real backend is os_win32.c. */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "loader.h"

#if defined(_WIN32)
#include <windows.h>
#elif defined(__APPLE__)
#include <mach-o/dyld.h>
#else
#include <unistd.h>
#endif

static bootstrap_t *s_view = NULL;
static void *s_remote = NULL;

static int s_bcreate(uint32_t pid, bootstrap_t **v, void **m, void **e,
                     const char **err)
{
    (void)pid;
    *v = calloc(1, sizeof(bootstrap_t));
    *m = *v;
    *e = malloc(1);
    s_view = *v;
    *err = NULL;
    return (*v && *e) ? 0 : -1;
}
static void s_bdestroy(bootstrap_t *v, void *m, void *e)
{
    (void)m;
    free(v);
    free(e);
    s_view = NULL;
}
static int s_ack(void *e, uint32_t ms)
{
    (void)e;
    (void)ms;
    /* simulate the product acknowledging the bootstrap */
    if (s_view)
        s_view->status = STATUS_ACKED;
    return 0;
}
static int s_inject(const payload_t *p, uint32_t pid, const char *dll_path,
                    const char **err)
{
    (void)pid;
    if (!p || !p->data || !p->len) {
        *err = "Payload image is empty";
        return -1;
    }
    /* stand in for NtAllocateVirtualMemory + NtWriteVirtualMemory */
    s_remote = malloc(p->len ? p->len : 1);
    if (!s_remote) {
        *err = "Failed to allocate memory in target process for payload";
        return -1;
    }
    memcpy(s_remote, p->data, p->len);
    printf("[stub] placed %zu bytes%s%s\n", p->len,
           dll_path ? ", path " : "", dll_path ? dll_path : "");
    return 0;
}
static int s_wait(uint32_t ms, uint32_t *code)
{
    (void)ms;
    *code = 1; /* stub loader "succeeds" */
    return 0;
}
static void s_detach(void)
{
    free(s_remote);
    s_remote = NULL;
}
static int s_post(const char *u, const char *b, uint8_t **o, size_t *n)
{
    (void)u;
    (void)b;
    *o = NULL;
    *n = 0;
    return 1;
}
static int s_browser(const char *u)
{
    printf("[stub] would open: %s\n", u);
    return 0;
}

const os_api_t OS_STUB = {
    s_bcreate, s_bdestroy, s_ack, s_inject, s_wait, s_detach, s_post, s_browser
};

#ifdef LOADER_TEST_MAIN
static uint8_t *load_file(const char *path, size_t *n)
{
    FILE *f = fopen(path, "rb");
    uint8_t *b;
    if (!f)
        return NULL;
    fseek(f, 0, SEEK_END);
    *n = (size_t)ftell(f);
    fseek(f, 0, SEEK_SET);
    b = malloc(*n);
    if (b && fread(b, 1, *n, f) != *n) {
        free(b);
        b = NULL;
    }
    fclose(f);
    return b;
}

/* Path of the running image, so the loader can read its own embedded bundle. */
static int self_path(char *out, size_t cap)
{
#if defined(_WIN32)
    return GetModuleFileNameA(NULL, out, (DWORD)cap) ? 0 : -1;
#elif defined(__APPLE__)
    uint32_t sz = (uint32_t)cap;
    return _NSGetExecutablePath(out, &sz) == 0 ? 0 : -1;
#else
    ssize_t r = readlink("/proc/self/exe", out, cap - 1);
    if (r <= 0)
        return -1;
    out[r] = '\0';
    return 0;
#endif
}

static uint8_t *load_self(size_t *n)
{
    char path[4096];
    if (self_path(path, sizeof path))
        return NULL;
    return load_file(path, n);
}

int main(int argc, char **argv)
{
    uint8_t *filedata = NULL;
    size_t filelen = 0;
    const uint8_t *blob = NULL;
    size_t bloblen = 0;
    payload_t p = {0};
    const uint8_t *host = NULL;
    size_t hostlen = 0;
    const char *err = NULL;
    int rc;

    if (argc >= 2) {
        /* usage: injector <bundle.dat> */
        filedata = load_file(argv[1], &filelen);
        if (!filedata) {
            printf("cannot read %s\n", argv[1]);
            return 2;
        }
        blob = filedata;
        bloblen = filelen;
    } else {
        /* Single-file injector: read our own image and pick up the bundle
         * the release pipeline appended (src/injector/embed.py). */
        filedata = load_self(&filelen);
        if (!filedata) {
            printf("usage: %s <bundle.dat>   (this image carries no payload)\n",
                   argv[0]);
            return 2;
        }
        if (find_embedded_bundle(filedata, filelen, &blob, &bloblen)) {
            printf("%s: no embedded payload found\n", argv[0]);
            free(filedata);
            return 2;
        }
        printf("embedded bundle: %zu bytes\n", bloblen);
    }

    if (bundle_extract(blob, bloblen, HOST_DLL_ENTRY, &host, &hostlen) == 0)
        printf("host    : %zu bytes\n", hostlen);
    if (bundle_extract(blob, bloblen, PAYLOAD_ENTRY, &p.data, &p.len)) {
        /* no VBDL index (raw blob): treat it as the payload itself */
        p.data = blob;
        p.len = bloblen;
    }
    p.from_bundle = 1;
    printf("payload : %zu bytes\n", p.len);

    rc = run_loader(&OS_STUB, &p, 1234, host ? "Vape421Native.dll" : NULL,
                    LOADER_BASE_URL, ZEUS_DEFAULT, 8091, &err);
    printf("validate+run: rc=%d err=%s\n", rc, err ? err : "(none)");
    free(filedata);
    return rc == 0 ? 0 : 1;
}
#endif
