/*
 * request_payload.c — portable reconstruction of FUN_140005af0.
 *
 * Observed behavior (Ghidra decompile of 60e47510.exe @ 0x140005af0):
 *   1. Build an x-www-form-urlencoded body: "token=<token>"
 *   2. POST it to <base_url> + "/api/v1/client/request-payload"
 *      (Content-Type: application/x-www-form-urlencoded)
 *   3. Copy the raw response bytes into the caller's output buffer.
 *      Empty response -> zeroed output.
 *
 * This file is portable C11: no Windows headers, no Ghidra types.
 * The HTTP transport is injected as a function pointer so the logic
 * compiles and runs everywhere; plug in WinHTTP/libcurl/etc. on target.
 *
 * Build:  cc -std=c11 -Wall -Wextra -o request_payload request_payload.c
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdint.h>

#define PAYLOAD_PATH "/api/v1/client/request-payload"
#define TOKEN_PREFIX "token="

/* Transport: POST body to url, return malloc'd response (or NULL on
 * empty/error) with length in *out_len. Returns 0 on success. */
typedef int (*http_post_fn)(const char *url,
                            const char *content_type,
                            const uint8_t *body, size_t body_len,
                            uint8_t **out, size_t *out_len,
                            void *userdata);

/* Build "token=<token>" body. Returns malloc'd NUL-terminated buffer. */
static char *build_token_body(const char *token)
{
    size_t need = strlen(TOKEN_PREFIX) + strlen(token) + 1;
    char *body = malloc(need);
    if (!body)
        return NULL;
    snprintf(body, need, "%s%s", TOKEN_PREFIX, token);
    return body;
}

/*
 * Mirrors FUN_140005af0: on empty response the original zeroes a 32-byte
 * (4x8) output struct; here we return NULL/0 and let the caller decide.
 */
static int request_payload(http_post_fn post, void *userdata,
                           const char *base_url, const char *token,
                           uint8_t **out, size_t *out_len)
{
    char url[512];
    char *body;
    int rc;

    snprintf(url, sizeof(url), "%s%s", base_url, PAYLOAD_PATH);
    body = build_token_body(token);
    if (!body)
        return -1;

    rc = post(url, "application/x-www-form-urlencoded",
              (const uint8_t *)body, strlen(body),
              out, out_len, userdata);
    free(body);
    if (rc != 0 || *out == NULL || *out_len == 0) {
        free(*out);
        *out = NULL;
        *out_len = 0;
        return 1; /* empty: original zeroes output, we signal it */
    }
    return 0;
}

/* Demo transport: prints what WOULD be sent, returns a canned reply,
 * so the logic runs anywhere without network. */
static int demo_post(const char *url, const char *content_type,
                     const uint8_t *body, size_t body_len,
                     uint8_t **out, size_t *out_len, void *userdata)
{
    static const char canned[] = "{\"demo\":true}";
    (void)userdata;
    printf("POST %s\n", url);
    printf("Content-Type: %s\n", content_type);
    printf("Body: %.*s\n", (int)body_len, body);
    *out_len = sizeof(canned) - 1;
    *out = malloc(*out_len);
    if (!*out)
        return -1;
    memcpy(*out, canned, *out_len);
    return 0;
}

int main(void)
{
    uint8_t *resp = NULL;
    size_t resp_len = 0;
    int rc = request_payload(demo_post, NULL,
                             "http://online.vape.chat",
                             "DEMO_TOKEN", &resp, &resp_len);
    printf("rc=%d resp_len=%zu resp=%.*s\n",
           rc, resp_len, (int)resp_len, resp ? (const char *)resp : "");
    free(resp);

    /* Empty-response path: original zeroes the output struct. */
    return 0;
}
