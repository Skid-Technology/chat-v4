/* os_stub.c — stub backend: runs the portable pipeline anywhere.
 * Simulates a target process with malloc'd memory so validate/build/run
 * exercise real logic. mmap/heap stands in for the remote process. */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "loader.h"

static int s_open(uint32_t pid, void **h)
{
    (void)pid;
    *h = malloc(1 << 20); /* fake remote address space */
    return *h ? 0 : -1;
}
static int s_alloc(void *p, size_t n, void **a)
{
    (void)p;
    *a = malloc(n);
    return *a ? 0 : -1;
}
static int s_write(void *p, void *a, const void *b, size_t n)
{
    (void)p;
    memcpy(a, b, n);
    return 0;
}
static int s_read(void *p, const void *a, void *b, size_t n)
{
    (void)p;
    memcpy(b, a, n);
    return 0;
}
static int s_thread(void *p, void *e, void *q, void **t)
{
    (void)p;
    (void)e;
    (void)q;
    *t = malloc(1);
    return *t ? 0 : -1;
}
static int s_wait(void *t, uint32_t ms, uint32_t *code)
{
    (void)ms;
    free(t);
    *code = 1; /* stub loader "succeeds" */
    return 0;
}
static int s_free(void *p, void *a)
{
    (void)p;
    free(a);
    return 0;
}
static int s_close(void *h)
{
    free(h);
    return 0;
}
static bootstrap_t *s_view = NULL;
static int s_bcreate(uint32_t pid, bootstrap_t **v, void **m, void **e)
{
    (void)pid;
    *v = calloc(1, sizeof(bootstrap_t));
    *m = *v;
    *e = malloc(1);
    s_view = *v;
    return (*v && *e) ? 0 : -1;
}
static int s_bdestroy(bootstrap_t *v, void *m, void *e)
{
    (void)m;
    free(v);
    free(e);
    return 0;
}
static int s_ack(void *e, uint32_t ms)
{
    (void)e;
    (void)ms;
    /* simulate the Product DLL acknowledging the bootstrap */
    if (s_view)
        s_view->status = STATUS_ACKED;
    return 0;
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
    s_open, s_alloc, s_write, s_read, s_thread, s_wait, s_free, s_close,
    s_bcreate, s_bdestroy, s_ack, s_post, s_browser
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

int main(int argc, char **argv)
{
    /* usage: loader_test <payload.bin | bundle.dat> */
    uint8_t *filedata = NULL;
    size_t filelen = 0;
    payload_t p = {0};
    const char *err = NULL;
    int rc;
    if (argc < 2) {
        printf("usage: %s <payload.bin|bundle.dat>\n", argv[0]);
        return 2;
    }
    filedata = load_file(argv[1], &filelen);
    if (!filedata) {
        printf("cannot read %s\n", argv[1]);
        return 2;
    }
    if (!bundle_extract(filedata, filelen, "payload.bin",
                          &p.data, &p.len)) {
        /* bundled path: no login, extracted + sha-verified */
        p.from_bundle = 1;
        printf("bundled payload: %zu bytes, sha ok\n", p.len);
    } else {
        p.data = filedata;
        p.len = filelen;
    }
    rc = run_loader(&OS_STUB, &p, 1234, "C:\\load\\Vape421Native.dll",
                    ZEUS_DEFAULT, 5678, &err);
    printf("validate+run: rc=%d err=%s payload=%zu bytes\n",
           rc, err ? err : "(none)", p.len);
    free(filedata);
    /* ack is auto-acked by stub; force the reject path once for coverage */
    return rc == 0 ? 0 : 1;
}
#endif
