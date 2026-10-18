/* os_win32.h — the real Windows backend for the injector.
 *
 * os_win32.c implements the Loader's injection exactly as observed
 * (2ef23a8d.exe): window/command-line target discovery, the
 * Local\Vape421.Bootstrap<pid> handshake, NtOpenProcess(0x43A), the remote
 * write of the product path and a remote LoadLibraryW thread.
 *
 * Substitution: the product is the native host DLL from the bundle, which
 * takes over the target's JVM and loads the Java client into it. */
#ifndef OS_WIN32_H
#define OS_WIN32_H

#include <stdint.h>
#include <stddef.h>
#include "loader.h"

#ifdef __cplusplus
extern "C" {
#endif

/* The real backend. Drop-in for OS_STUB in run_loader(). */
extern const os_api_t OS_WIN;

/* A selectable Minecraft target, as the picker shows it. */
typedef struct {
    uint32_t pid;
    char     name[192];    /* "Minecraft <version> (<window title>)" */
    int      already;      /* 1 = the loader/host module is already inside */
} target_t;

/* Walk visible top-level windows, keep the ones whose process command line
 * looks like Minecraft, and flag the ones already carrying the host module.
 * Returns the number found (0 on none, <0 on error). */
int win_list_targets(target_t *out, int cap);

/* Directory of this executable (UTF-8, no trailing separator). */
int win_self_dir(char *out, size_t cap);

/* Write `data` next to this executable as `name` (extracted payload staging,
 * so the target can LoadLibraryW it). Returns 0 on success. */
int win_stage_next_to_self(const char *name, const uint8_t *data, size_t len,
                           char *out_path, size_t cap);

#ifdef __cplusplus
} /* extern "C" */
#endif

#endif
