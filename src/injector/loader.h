/* loader.h — shared protocol constants for the recreated injector.
 * Portable C11.
 *
 * Sources: strings, decompiles and the reconstruction notes for the Loader
 * (2ef23a8d.exe, sha256 9780102…c900add) — bootstrap block layout, object
 * names, error strings and the injection order all come from there. */
#ifndef LOADER_H
#define LOADER_H

#include <stdint.h>
#include <stddef.h>

#ifdef __cplusplus
extern "C" {
#endif

/* --- endpoints (verbatim from binary) --- */
#define LOADER_BASE_URL      "http://online.vape.chat"
#define PATH_AUTH_GENERATE   "/api/v1/app-auth/generate"
#define PATH_AUTH_STATUS     "/api/v1/app-auth/status"
#define AUTH_PROCEED_URL     "https://www.vape.chat/app-auth/proceed/"
#define PATH_REQ_PAYLOAD     "/api/v1/client/request-payload"
#define PATH_PAYLOAD         "/api/v1/client/payload"
#define PATH_LOADER_LOGIN    "/loader/login"
#define ZEUS_DEFAULT         "zeus-prod.vape.chat:8091"

/* --- bootstrap block (shared-memory handshake) ---------------------------
 * The Loader creates the mapping, writes this block and waits for the loaded
 * product to set status to 2. Field offsets are the observed ones. */
#define BOOTSTRAP_SIZE   0x1A8u
#define BOOTSTRAP_MAGIC  0x54423456u /* "VB4T" */
#define BOOTSTRAP_VER    0x1A80002u  /* low u16 = version 2, high u16 = 0x1A8 */
#define STATUS_POSTED    1u          /* set by the loader */
#define STATUS_ACKED     2u          /* set by the product = accept */
#define ACK_TIMEOUT_MS   30000u
#define INJECT_TIMEOUT_MS 30000u

/* Object names are built as L"Local\\Vape421." + <base> + <decimal pid>. */
#define BOOTSTRAP_NAME_PREFIX "Local\\Vape421."

#pragma pack(push, 1)
typedef struct {
    uint32_t magic;
    uint32_t ver_size;
    uint32_t pid;
    uint32_t one;
    uint16_t loader_port;      /* loader-controller port */
    uint16_t pad10;
    char     url[0x100];       /* VAPE_ONLINE_BASE_URL, <= 255 + NUL */
    char     zeus_host[0x80];  /* VAPE_ZEUS_ADDRESS "host:port", <= 127 + NUL */
    uint16_t zeus_port;        /* numeric port parsed from zeus_host */
    uint8_t  pad196[14];
    uint32_t status;
} bootstrap_t;
#pragma pack(pop)

_Static_assert(sizeof(bootstrap_t) == BOOTSTRAP_SIZE, "bootstrap block is 0x1A8");

/* --- bundle format --- */
#define BUNDLE_MAGIC 0x56424C44u /* "VBDL" */
#define BUNDLE_VER   1u

/* --- single-file injector -------------------------------------------------
 * The released injector is one file: the loader image, the bundle appended,
 * and a 24-byte trailer. The loader reads its own image, so it needs no
 * external payload. See find_embedded_bundle(). */
#define EMBED_MAGIC        "VAPEEMB" /* 8 bytes: 'V','A','P','E','E','M','B','\0' */
#define EMBED_TRAILER_SIZE 24u       /* magic(8) + u64 offset + u64 size       */

/* The two entries the Windows injector consumes out of the bundle:
 *
 *   the host   — the native DLL the Loader writes into the target and starts
 *                with a remote LoadLibraryW. It finds the target's JVM and
 *                loads the client into it, then acks the bootstrap.
 *   the client — the Java payload, rebuilt from src/payload.
 *
 * The original shipped a native product DLL; this reconstruction ships the
 * same DLL shape (so the injection mechanism is a literal copy) with the Java
 * client as the thing it hosts. */
#define HOST_DLL_ENTRY "Vape421Native.dll"
#define PAYLOAD_ENTRY  "vape_client.jar"

/* --- pipeline stages --- */
typedef enum {
    STAGE_AUTH = 0,
    STAGE_ACQUIRE,
    STAGE_VALIDATE,
    STAGE_INJECT,
    STAGE_ACK_WAIT,
    STAGE_DONE
} stage_t;

/* Acquired payload: pointer + size (owned by acquirer). */
typedef struct {
    const uint8_t *data;
    size_t len;
    int from_bundle; /* 1 = bundled, 0 = downloaded */
} payload_t;

/* --- OS backend -----------------------------------------------------------
 * The portable implementation is os_stub.c (used by the console test and as
 * a fallback); os_win32.c is the real Windows backend. The order of the
 * calls mirrors the Loader: bootstrap objects, then the injection, then the
 * thread wait, then the ack. */
typedef struct {
    /* bootstrap IPC */
    int  (*bootstrap_create)(uint32_t pid, bootstrap_t **view, void **mapping,
                             void **ack_event, const char **err);
    void (*bootstrap_destroy)(bootstrap_t *view, void *mapping, void *ack_event);
    int  (*wait_ack)(void *ack_event, uint32_t timeout_ms);
    /* injection: open the target, place the payload and start it. `dll_path`
     * is where the payload was staged on disk (NULL when there is none, e.g.
     * a fileless image). On failure the backend sets *err. */
    int  (*inject)(const payload_t *payload, uint32_t pid, const char *dll_path,
                   const char **err);
    int  (*wait_inject)(uint32_t timeout_ms, uint32_t *exit_code);
    /* teardown of whatever inject() opened/allocated (free remote, close the
     * process and the injected thread). */
    void (*detach)(void);
    /* net / browser */
    int  (*http_post_form)(const char *url, const char *body,
                           uint8_t **out, size_t *out_len);
    int  (*browser_open)(const char *url);
} os_api_t;

/* Portable backend from os_stub.c: walks the real pipeline against a
 * simulated process. Used by the console test binary. */
extern const os_api_t OS_STUB;

/* Pipeline entry points (loader.c). */
int stage_validate_pe(const uint8_t *data, size_t len, const char **err);
int stage_validate_jar(const uint8_t *data, size_t len, const char **err);
/* Accepts either payload shape: the native host PE, or the Java client. */
int stage_validate_payload(const uint8_t *data, size_t len, const char **err);
/* Locate a bundle appended to `self` (the single-file injector layout). */
int find_embedded_bundle(const uint8_t *self, size_t n,
                         const uint8_t **blob, size_t *len);
int stage_build_bootstrap(bootstrap_t *b, uint32_t pid, const char *url,
                          const char *zeus_host, uint16_t zeus_port);
int bundle_extract(const uint8_t *blob, size_t blen, const char *name,
                   const uint8_t **out, size_t *out_len);
int run_loader(const os_api_t *os, const payload_t *payload, uint32_t pid,
               const char *dll_path, const char *url, const char *zeus_host,
               uint16_t zeus_port, const char **err);

#ifdef __cplusplus
} /* extern "C" */
#endif

#endif
