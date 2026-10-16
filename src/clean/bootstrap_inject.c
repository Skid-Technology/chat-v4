/*
 * bootstrap_inject.c — portable reconstruction of FUN_140023070.
 *
 * Observed behavior (Ghidra decompile of 60e47510.exe @ 0x140023070):
 *   The loader prepares a 0x1A8-byte shared bootstrap block (file mapping
 *   named "Bootstrap<pid>", event "BootstrapAck<pid>") with this layout,
 *   then opens the Minecraft process, writes the Product DLL path into it,
 *   resolves the loader entry, starts loading, and waits 30 s for the
 *   Product DLL to set status == 2 (ack). All strings below are verbatim
 *   from the binary (.rsrc wide strings).
 *
 * This file is portable C11: struct layout + builder + demo main only.
 * OS parts (CreateFileMappingW/MapViewOfFile/OpenProcess/etc.) are left
 * as a documented call sequence, not code, so it builds everywhere.
 *
 * Build:  cc -std=c11 -Wall -Wextra -o bootstrap bootstrap_inject.c
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdint.h>

#define BOOTSTRAP_SIZE   0x1A8u
#define BOOTSTRAP_MAGIC  0x54423456u  /* bytes 'V','B','4','T' ("VB4T") */
#define BOOTSTRAP_VER    0x1A80002u   /* hi16 = struct size, lo16 = version 2 */
#define STATUS_POSTED    1u
#define STATUS_ACKED     2u

/* Field offsets, recovered from the decompile (u32 indices * 4):
 *   [0]=magic  [1]=ver  [2]=pid  [3]=1  +4:port(u16)
 *   +5(0x14): product DLL path, 0x100 bytes
 *   +0x45(0x114): zeus host, max 0x7f + NUL
 *   +0x65*4=0x194: controller port (u16)
 *   [0x69]=0x1a4: status u32 (1 posted -> 2 acked) */
#pragma pack(push, 1)
typedef struct {
    uint32_t magic;            /* 0x00: 0x54423456 */
    uint32_t ver_size;         /* 0x04: 0x01A80002 */
    uint32_t pid;              /* 0x08 */
    uint32_t one;              /* 0x0C: always 1 */
    uint16_t loader_port;      /* 0x10 */
    uint16_t pad10;            /* 0x12 */
    uint8_t  dll_path[0x100];  /* 0x14: Product DLL path */
    char     zeus_host[0x80];  /* 0x114: host (<=0x7f chars) */
    uint16_t ctrl_port;        /* 0x194 */
    uint8_t  pad196[14];       /* 0x196..0x1A3 */
    uint32_t status;           /* 0x1A4: 1 posted, 2 acked */
} bootstrap_t;
#pragma pack(pop)

_Static_assert(sizeof(bootstrap_t) == BOOTSTRAP_SIZE,
               "bootstrap_t must be exactly 0x1A8 bytes");
_Static_assert(sizeof(bootstrap_t) % 4 == 0, "u32-aligned");

/* Error strings, verbatim from the binary: */
static const char *const ERRORS[] = {
    "Loader controller port is unavailable",
    "VAPE_ZEUS_ADDRESS must use host:port format",
    "VAPE_ONLINE_BASE_URL is too long for the bootstrap",
    "NativeLoader.dll was not found",
    "A Loader bootstrap already exists for this process",
    "Failed to create the Loader bootstrap objects",
    "Failed to open Minecraft process",
    "Failed to write the Product DLL path",
    "Failed to resolve loader entry",
    "Failed to start Product DLL loading",
    "Product DLL loading timed out",
    "Product DLL did not acknowledge the socket bootstrap",
    "Product DLL rejected the socket bootstrap",
};

/* Portable part: fill the block. Returns 0 ok, -1 host too long. */
static int bootstrap_build(bootstrap_t *b, uint32_t pid,
                           uint16_t loader_port, const char *dll_path,
                           size_t dll_len, const char *zeus_host,
                           uint16_t ctrl_port)
{
    size_t hlen = strlen(zeus_host);
    if (hlen > 0x7F)
        return -1;
    memset(b, 0, sizeof(*b));
    b->magic = BOOTSTRAP_MAGIC;
    b->ver_size = BOOTSTRAP_VER;
    b->pid = pid;
    b->one = 1;
    b->loader_port = loader_port;
    memcpy(b->dll_path, dll_path,
           dll_len > sizeof(b->dll_path) ? sizeof(b->dll_path) : dll_len);
    memcpy(b->zeus_host, zeus_host, hlen + 1);
    b->ctrl_port = ctrl_port;
    b->status = STATUS_POSTED;
    return 0;
}

/* OS-specific sequence (documented, not compiled):
 *   1. GetFileAttributesW(NativeLoader.dll) else "NativeLoader.dll was not found"
 *   2. CreateFileMappingW("Bootstrap<pid>", 0x1A8) / handle ERROR_ALREADY_EXISTS
 *   3. MapViewOfFile + CreateEventW("BootstrapAck<pid>")
 *   4. Open Minecraft process (Toolhelp snapshot enumeration)
 *   5. Write Product DLL path into target, resolve loader entry
 *   6. Start remote load, WaitForSingleObject(handle, 30000)
 *   7. WaitForSingleObject(ack_event, 30000), require status == 2
 *   8. Zero the block, unmap, close handles.
 */

static void hexdump(const void *p, size_t n)
{
    const uint8_t *b = p;
    for (size_t i = 0; i < n; i++) {
        printf("%02x%c", b[i], (i + 1) % 16 == 0 ? '\n' : ' ');
    }
    if (n % 16)
        printf("\n");
}

int main(void)
{
    bootstrap_t b;
    const char *dll = "C:\\load\\Vape421Native.dll";
    int rc = bootstrap_build(&b, 1234, 0, dll, strlen(dll) + 1,
                             "zeus-prod.vape.chat:8091", 5678);
    if (rc != 0) {
        printf("host too long\n");
        return 1;
    }
    printf("magic=%08x ver=%08x pid=%u status=%u\n",
           b.magic, b.ver_size, b.pid, b.status);
    printf("host=%s\n", b.zeus_host);
    hexdump(&b, 64);
    printf("error strings known: %zu, e.g. \"%s\"\n",
           sizeof(ERRORS) / sizeof(ERRORS[0]), ERRORS[6]);
    return 0;
}
