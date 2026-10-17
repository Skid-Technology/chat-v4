/* loader.h — shared protocol constants for the recreated injector.
 * Portable C11. Sources: strings + decompiles of 60e47510.exe. */
#ifndef LOADER_H
#define LOADER_H

#include <stdint.h>
#include <stddef.h>

/* --- endpoints (verbatim from binary) --- */
#define LOADER_BASE_URL      "http://online.vape.chat"
#define PATH_AUTH_GENERATE   "/api/v1/app-auth/generate"
#define PATH_AUTH_STATUS     "/api/v1/app-auth/status"
#define AUTH_PROCEED_URL     "https://www.vape.chat/app-auth/proceed/"
#define PATH_REQ_PAYLOAD     "/api/v1/client/request-payload"
#define PATH_PAYLOAD         "/api/v1/client/payload"
#define PATH_LOADER_LOGIN    "/loader/login"
#define ZEUS_DEFAULT         "zeus-prod.vape.chat:8091"

/* --- bootstrap block (see src/clean/bootstrap_inject.c) --- */
#define BOOTSTRAP_SIZE   0x1A8u
#define BOOTSTRAP_MAGIC  0x54423456u /* "VB4T" */
#define BOOTSTRAP_VER    0x1A80002u
#define STATUS_POSTED    1u
#define STATUS_ACKED     2u
#define ACK_TIMEOUT_MS   30000u

#pragma pack(push, 1)
typedef struct {
    uint32_t magic;
    uint32_t ver_size;
    uint32_t pid;
    uint32_t one;
    uint16_t loader_port;
    uint16_t pad10;
    uint8_t  dll_path[0x100];
    char     zeus_host[0x80];
    uint16_t ctrl_port;
    uint8_t  pad196[14];
    uint32_t status;
} bootstrap_t;
#pragma pack(pop)

_Static_assert(sizeof(bootstrap_t) == BOOTSTRAP_SIZE, "0x1A8");

/* --- bundle format --- */
#define BUNDLE_MAGIC 0x56424C44u /* "VBDL" */
#define BUNDLE_VER   1u

/* --- single-file injector -------------------------------------------------
 * The released injector is one file: the loader image, the bundle appended,
 * and a 24-byte trailer. The loader reads its own image, so it needs no
 * external payload. See find_embedded_bundle(). */
#define EMBED_MAGIC        "VAPEEMB" /* 8 bytes: 'V','A','P','E','E','M','B','\0' */
#define EMBED_TRAILER_SIZE 24u       /* magic(8) + u64 offset + u64 size       */

/* The bundle entry the loader consumes. The original payload was a native
 * 64-bit PE; this reconstruction ships the Java client, rebuilt from
 * src/payload, so the entry is a JAR (and validation accepts either). */
#define PAYLOAD_ENTRY "vape_client.jar"

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

/* OS backend: implemented by os_win32.c / os_stub.c. */
typedef struct {
    /* process + memory */
    int (*open_process)(uint32_t pid, void **handle);
    int (*alloc_remote)(void *proc, size_t size, void **addr);
    int (*write_remote)(void *proc, void *addr, const void *buf, size_t n);
    int (*read_remote)(void *proc, const void *addr, void *buf, size_t n);
    int (*start_thread)(void *proc, void *entry, void *param, void **thread);
    int (*wait_thread)(void *thread, uint32_t timeout_ms, uint32_t *exit_code);
    int (*free_remote)(void *proc, void *addr);
    int (*close_handle)(void *handle);
    /* bootstrap IPC */
    int (*bootstrap_create)(uint32_t pid, bootstrap_t **view, void **mapping,
                            void **ack_event);
    int (*bootstrap_destroy)(bootstrap_t *view, void *mapping, void *ack_event);
    int (*wait_ack)(void *ack_event, uint32_t timeout_ms);
    /* net */
    int (*http_post_form)(const char *url, const char *body,
                          uint8_t **out, size_t *out_len);
    /* browser */
    int (*browser_open)(const char *url);
} os_api_t;

/* Pipeline entry points (loader.c). */
int stage_validate_pe(const uint8_t *data, size_t len, const char **err);
int stage_validate_jar(const uint8_t *data, size_t len, const char **err);
/* Accepts either payload shape: the original native PE, or the reconstructed
 * Java client (a JAR). Used by run_loader. */
int stage_validate_payload(const uint8_t *data, size_t len, const char **err);
/* Locate a bundle appended to `self` (the single-file injector layout). */
int find_embedded_bundle(const uint8_t *self, size_t n,
                         const uint8_t **blob, size_t *len);
int stage_build_bootstrap(bootstrap_t *b, uint32_t pid, const char *dll_path,
                          const char *zeus_host, uint16_t ctrl_port);
int bundle_extract(const uint8_t *blob, size_t blen, const char *name,
                   const uint8_t **out, size_t *out_len);
int run_loader(const os_api_t *os, const payload_t *payload, uint32_t pid,
               const char *dll_path, const char *zeus_host, uint16_t ctrl_port,
               const char **err);

#endif
