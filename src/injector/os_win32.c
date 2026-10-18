/* os_win32.c — the real Windows injection backend.
 *
 * This is the Loader's own mechanism (2ef23a8d.exe), re-implemented in C:
 *
 *   1. find the target: visible top-level windows, then the owning process's
 *      command line (NtQueryInformationProcess class 0x3C) tested for
 *      "minecraft" / "net.minecraft" / "cpw.mods.modlauncher";
 *   2. create  Local\Vape421.Bootstrap<pid>  (0x1A8, PAGE_READWRITE) and
 *      Local\Vape421.BootstrapAck<pid>  (manual-reset event);
 *   3. write the bootstrap block, status = 1;
 *   4. NtOpenProcess(0x43A);
 *   5. NtAllocateVirtualMemory + NtWriteVirtualMemory of the product path
 *      (wide, pathChars*2 + 2 bytes, PAGE_READWRITE);
 *   6. NtCreateThreadEx(THREAD_ALL_ACCESS, LoadLibraryW, remote path);
 *   7. wait 30 s on the thread, then 30 s on the ack, require status == 2;
 *   8. teardown in the observed order.
 *
 * Substitution: the product is our native host DLL (bundle entry
 * Vape421Native.dll), which hosts the Java client. Everything up to and
 * including the remote LoadLibraryW is a literal copy.
 *
 * Error strings are the Loader's own, so the UI shows what it shows.
 */
#define WIN32_LEAN_AND_MEAN
#define _WIN32_WINNT 0x0600
#include <windows.h>
#include <tlhelp32.h>
#include <shellapi.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "loader.h"
#include "os_win32.h"

/* --- NT calls (resolved from ntdll, plain names) --------------------------
 * The Loader XOR-masks these names; that is obfuscation, not behaviour, so
 * this re-implementation resolves them directly. */
typedef LONG NTSTATUS;
#define NT_SUCCESS(s) ((NTSTATUS)(s) >= 0)

typedef struct {                     /* ObjectAttributes, x64, 0x30 bytes */
    ULONG  Length;
    ULONG  pad;
    HANDLE RootDirectory;
    PVOID  ObjectName;
    ULONG  Attributes;
    ULONG  pad2;
    PVOID  SecurityDescriptor;
    PVOID  SecurityQualityOfService;
} objattr_t;

typedef struct { PVOID UniqueProcess; PVOID UniqueThread; } clientid_t;

typedef struct { USHORT Length; USHORT MaximumLength; PWSTR Buffer; } uni_string_t;

typedef NTSTATUS (NTAPI *pNtOpenProcess)(PHANDLE, ACCESS_MASK, objattr_t *, clientid_t *);
typedef NTSTATUS (NTAPI *pNtAllocateVirtualMemory)(HANDLE, PVOID *, ULONG_PTR, PSIZE_T, ULONG, ULONG);
typedef NTSTATUS (NTAPI *pNtWriteVirtualMemory)(HANDLE, PVOID, PVOID, SIZE_T, PSIZE_T);
typedef NTSTATUS (NTAPI *pNtFreeVirtualMemory)(HANDLE, PVOID *, PSIZE_T, ULONG);
typedef NTSTATUS (NTAPI *pNtCreateThreadEx)(PHANDLE, ACCESS_MASK, objattr_t *, HANDLE,
                                            PVOID, PVOID, ULONG, ULONG_PTR, SIZE_T, SIZE_T, PVOID);
typedef NTSTATUS (NTAPI *pNtQueryInformationProcess)(HANDLE, ULONG, PVOID, ULONG, PULONG);

static pNtOpenProcess            nt_open_process;
static pNtAllocateVirtualMemory  nt_alloc;
static pNtWriteVirtualMemory     nt_write;
static pNtFreeVirtualMemory      nt_free;
static pNtCreateThreadEx         nt_create_thread;
static pNtQueryInformationProcess nt_query_info;

static void *nt_sym(const char *name)
{
    HMODULE h = GetModuleHandleW(L"ntdll.dll");
    if (!h)
        h = LoadLibraryW(L"ntdll.dll");
    return h ? (void *)GetProcAddress(h, name) : NULL;
}

static int nt_init(void)
{
    if (nt_open_process)
        return 0;
    nt_open_process   = (pNtOpenProcess)nt_sym("NtOpenProcess");
    nt_alloc          = (pNtAllocateVirtualMemory)nt_sym("NtAllocateVirtualMemory");
    nt_write          = (pNtWriteVirtualMemory)nt_sym("NtWriteVirtualMemory");
    nt_free           = (pNtFreeVirtualMemory)nt_sym("NtFreeVirtualMemory");
    nt_create_thread  = (pNtCreateThreadEx)nt_sym("NtCreateThreadEx");
    nt_query_info     = (pNtQueryInformationProcess)nt_sym("NtQueryInformationProcess");
    return (nt_open_process && nt_alloc && nt_write && nt_free &&
            nt_create_thread && nt_query_info) ? 0 : -1;
}

/* Constants from the spec. */
#define NT_OPEN_ACCESS      0x43Au   /* QUERY_INFORMATION|VM_READ|VM_WRITE|VM_OPERATION|CREATE_THREAD */
#define NT_ALLOC_TYPE       0x3000u  /* MEM_COMMIT|MEM_RESERVE */
#define NT_FREE_TYPE        0x8000u  /* MEM_RELEASE */
#define MY_THREAD_ALL_ACCESS 0x1FFFFFu
#define PAGE_RW             4u
#define PROC_CMD_LINE_CLASS 0x3Cu    /* ProcessCommandLineInformation */

/* --- target discovery ---------------------------------------------------- */

typedef struct { uint32_t pid; wchar_t title[192]; } winrec_t;
static winrec_t s_win[64];
static int      s_win_n;

static BOOL CALLBACK enum_cb(HWND hwnd, LPARAM lp)
{
    wchar_t buf[192];
    DWORD pid = 0;
    int i, len;
    (void)lp;

    if (!IsWindowVisible(hwnd))
        return TRUE;
    len = GetWindowTextLengthW(hwnd);
    if (len <= 0)
        return TRUE;
    if (s_win_n >= (int)(sizeof s_win / sizeof s_win[0]))
        return FALSE;
    if (!GetWindowTextW(hwnd, buf, (int)(sizeof buf / sizeof buf[0])))
        return TRUE;
    GetWindowThreadProcessId(hwnd, &pid);
    if (!pid)
        return TRUE;
    for (i = 0; i < s_win_n; i++)          /* one record per pid */
        if (s_win[i].pid == pid)
            return TRUE;
    s_win[s_win_n].pid = pid;
    lstrcpynW(s_win[s_win_n].title, buf, 192);
    s_win_n++;
    return TRUE;
}

/* The target's command line, via ProcessCommandLineInformation. */
static int process_command_line(uint32_t pid, wchar_t *out, int cap)
{
    static BYTE buf[0x8000];
    uni_string_t *us = (uni_string_t *)buf;
    HANDLE h;
    ULONG got = 0;
    int n;

    h = OpenProcess(PROCESS_QUERY_LIMITED_INFORMATION, FALSE, pid);
    if (!h)
        h = OpenProcess(PROCESS_QUERY_INFORMATION | PROCESS_VM_READ, FALSE, pid);
    if (!h)
        return -1;
    if (!NT_SUCCESS(nt_query_info(h, PROC_CMD_LINE_CLASS, buf, sizeof buf, &got)) ||
        us->Length == 0 || us->Buffer == NULL) {
        CloseHandle(h);
        return -1;
    }
    n = us->Length / (int)sizeof(wchar_t);
    if (n > cap - 1)
        n = cap - 1;
    memcpy(out, us->Buffer, (size_t)n * sizeof(wchar_t));
    out[n] = L'\0';
    CloseHandle(h);
    return 0;
}

static int wcontains(const wchar_t *hay, const wchar_t *needle)
{
    size_t nl = wcslen(needle);
    for (; *hay; hay++)
        if (_wcsnicmp(hay, needle, nl) == 0)
            return 1;
    return 0;
}

/* "minecraft" / "net.minecraft" / "cpw.mods.modlauncher", case-insensitive. */
static int looks_like_minecraft(const wchar_t *cmdline)
{
    return wcontains(cmdline, L"minecraft") ||
           wcontains(cmdline, L"net.minecraft") ||
           wcontains(cmdline, L"cpw.mods.modlauncher");
}

/* Already carries the loader/host: module name, or a module path fragment. */
static int pid_has_host(uint32_t pid)
{
    HANDLE snap = CreateToolhelp32Snapshot(TH32CS_SNAPMODULE | TH32CS_SNAPMODULE32, pid);
    MODULEENTRY32W me;
    int found = 0;
    if (snap == INVALID_HANDLE_VALUE)
        return 0;
    me.dwSize = sizeof me;
    if (Module32FirstW(snap, &me)) {
        do {
            if (_wcsicmp(me.szModule, L"Vape421Native.dll") == 0 ||
                _wcsicmp(me.szModule, L"NativeLoader.dll") == 0 ||
                wcontains(me.szExePath, L"vapecache") ||
                wcontains(me.szExePath, L"CLR_v4.0_32") ||
                wcontains(me.szExePath, L".vapeclient")) {
                found = 1;
                break;
            }
        } while (Module32NextW(snap, &me));
    }
    CloseHandle(snap);
    return found;
}

/* "Minecraft <version> (<window title>)" — the picker's primary line. */
static void build_label(const wchar_t *title, uint32_t pid, char *out, size_t cap)
{
    wchar_t buf[320];
    const wchar_t *tail = title;
    if (_wcsnicmp(title, L"Minecraft ", 10) == 0)
        tail = title + 10;
    else if (_wcsnicmp(title, L"Minecraft (", 11) == 0)
        tail = title + 10;
    _snwprintf(buf, sizeof buf / sizeof buf[0], L"Minecraft %ls", tail);
    WideCharToMultiByte(CP_UTF8, 0, buf, -1, out, (int)cap, NULL, NULL);
    (void)pid;
}

int win_list_targets(target_t *out, int cap)
{
    wchar_t cmd[4096];
    int i, n = 0;

    if (nt_init())
        return -1;
    s_win_n = 0;
    EnumWindows(enum_cb, 0);

    for (i = 0; i < s_win_n && n < cap; i++) {
        if (process_command_line(s_win[i].pid, cmd, (int)(sizeof cmd / sizeof cmd[0])))
            continue;
        if (!looks_like_minecraft(cmd))
            continue;
        out[n].pid = s_win[i].pid;
        build_label(s_win[i].title, s_win[i].pid, out[n].name, sizeof out[n].name);
        out[n].already = pid_has_host(s_win[i].pid);
        n++;
    }
    return n;
}

/* --- executable paths ---------------------------------------------------- */

int win_self_dir(char *out, size_t cap)
{
    char path[MAX_PATH * 4];
    DWORD n = GetModuleFileNameA(NULL, path, (DWORD)sizeof path);
    char *slash;
    size_t len;
    if (!n || n >= sizeof path)
        return -1;
    slash = strrchr(path, '\\');
    if (!slash)
        return -1;
    *slash = '\0';
    len = strlen(path);
    if (len >= cap)
        return -1;
    memcpy(out, path, len + 1);
    return 0;
}

int win_stage_next_to_self(const char *name, const uint8_t *data, size_t len,
                           char *out_path, size_t cap)
{
    char dir[MAX_PATH * 4];
    FILE *f;
    if (win_self_dir(dir, sizeof dir))
        return -1;
    if ((size_t)_snprintf(out_path, cap, "%s\\%s", dir, name) >= cap)
        return -1;
    f = fopen(out_path, "wb");
    if (!f)
        return -1;
    if (fwrite(data, 1, len, f) != len || fclose(f)) {
        remove(out_path);
        return -1;
    }
    return 0;
}

/* --- bootstrap handshake -------------------------------------------------- */

static void bootstrap_name(wchar_t *out, size_t cap, const wchar_t *base, uint32_t pid)
{
    _snwprintf(out, cap, L"Local\\Vape421.%ls%lu", base, (unsigned long)pid);
}

static int s_bcreate(uint32_t pid, bootstrap_t **view, void **mapping,
                     void **ack_event, const char **err)
{
    wchar_t map_name[128], ack_name[128];
    HANDLE hmap, hack;
    DWORD last;

    bootstrap_name(map_name, 128, L"Bootstrap", pid);
    bootstrap_name(ack_name, 128, L"BootstrapAck", pid);

    hmap = CreateFileMappingW(INVALID_HANDLE_VALUE, NULL, PAGE_READWRITE, 0,
                              BOOTSTRAP_SIZE, map_name);
    last = GetLastError();
    if (!hmap || last == ERROR_ALREADY_EXISTS) {
        if (hmap)
            CloseHandle(hmap);
        *err = "A Loader bootstrap already exists for this process";
        return -1;
    }
    *view = (bootstrap_t *)MapViewOfFile(hmap, FILE_MAP_ALL_ACCESS, 0, 0, BOOTSTRAP_SIZE);
    if (!*view) {
        CloseHandle(hmap);
        *err = "Failed to create the Loader bootstrap objects";
        return -1;
    }
    /* The Loader requires the ack event to pre-exist (the socket side owns
     * it). Ours is created here and the host DLL opens it by name, so a
     * fresh event is accepted too. */
    hack = CreateEventW(NULL, TRUE, FALSE, ack_name);
    if (!hack) {
        UnmapViewOfFile(*view);
        CloseHandle(hmap);
        *err = "Failed to create the Loader bootstrap objects";
        return -1;
    }
    *mapping = hmap;
    *ack_event = hack;
    *err = NULL;
    return 0;
}

static void s_bdestroy(bootstrap_t *view, void *mapping, void *ack_event)
{
    if (view)
        UnmapViewOfFile(view);
    if (ack_event)
        CloseHandle((HANDLE)ack_event);
    if (mapping)
        CloseHandle((HANDLE)mapping);
}

static int s_wait_ack(void *ack_event, uint32_t ms)
{
    return WaitForSingleObject((HANDLE)ack_event, ms) == WAIT_OBJECT_0 ? 0 : -1;
}

/* --- the injection -------------------------------------------------------- */

static void       *g_proc;      /* target process handle */
static void       *g_remote;    /* remote product-path allocation */
static void       *g_thread;    /* the LoadLibraryW thread */

static int s_inject(const payload_t *payload, uint32_t pid, const char *dll_path,
                    const char **err)
{
    objattr_t oa;
    clientid_t cid;
    NTSTATUS st;
    WCHAR wide[MAX_PATH * 4];
    int wchars;
    SIZE_T size, written;
    FARPROC load_library_w;

    if (nt_init()) {
        *err = "Failed to resolve loader entry";
        return -1;
    }
    if (!payload || !payload->len) {
        *err = "Payload image is empty";
        return -1;
    }
    if (!dll_path) {
        *err = "Failed to write the Product DLL path";
        return -1;
    }
    wchars = MultiByteToWideChar(CP_UTF8, 0, dll_path, -1, wide,
                                 (int)(sizeof wide / sizeof wide[0]));
    if (wchars <= 0) {
        *err = "Failed to write the Product DLL path";
        return -1;
    }

    memset(&oa, 0, sizeof oa);
    oa.Length = sizeof oa;                       /* 0x30 */
    cid.UniqueProcess = (PVOID)(ULONG_PTR)pid;
    cid.UniqueThread = NULL;
    g_proc = NULL;
    st = nt_open_process(&g_proc, NT_OPEN_ACCESS, &oa, &cid);
    if (!NT_SUCCESS(st) || !g_proc) {
        g_proc = NULL;
        *err = "Failed to open Minecraft process";
        return -1;
    }

    /* the product path, wide, pathChars*2 + 2 bytes */
    size = (SIZE_T)(wchars * (int)sizeof(WCHAR));
    g_remote = NULL;
    st = nt_alloc(g_proc, &g_remote, 0, &size, NT_ALLOC_TYPE, PAGE_RW);
    if (!NT_SUCCESS(st) || !g_remote) {
        *err = "Failed to write the Product DLL path";
        return -1;
    }
    written = 0;
    st = nt_write(g_proc, g_remote, wide, size, &written);
    if (!NT_SUCCESS(st) || written != size) {
        *err = "Failed to write the Product DLL path";
        return -1;
    }

    load_library_w = GetProcAddress(GetModuleHandleW(L"kernel32.dll"), "LoadLibraryW");
    if (!load_library_w) {
        *err = "Failed to resolve loader entry";
        return -1;
    }

    g_thread = NULL;
    st = nt_create_thread(&g_thread, MY_THREAD_ALL_ACCESS, NULL, g_proc,
                          (PVOID)load_library_w, g_remote, 0, 0, 0, 0, NULL);
    if (!NT_SUCCESS(st) || !g_thread) {
        g_thread = NULL;
        *err = "Failed to start Product DLL loading";
        return -1;
    }
    (void)payload;
    *err = NULL;
    return 0;
}

static int s_wait_inject(uint32_t ms, uint32_t *exit_code)
{
    DWORD code = 0;
    if (!g_thread)
        return -1;
    if (WaitForSingleObject((HANDLE)g_thread, ms) != WAIT_OBJECT_0)
        return -1;
    if (!GetExitCodeThread((HANDLE)g_thread, &code))
        return -1;
    *exit_code = code;
    return 0;
}

/* Teardown, in the observed order: release the remote allocation, close the
 * injected thread, close the process. The mapping/view/event are released by
 * the caller (loader.c). */
static void s_detach(void)
{
    if (g_remote && g_proc) {
        SIZE_T size = 0;
        PVOID base = g_remote;
        nt_free(g_proc, &base, &size, NT_FREE_TYPE);
        g_remote = NULL;
    }
    if (g_thread) {
        CloseHandle((HANDLE)g_thread);
        g_thread = NULL;
    }
    if (g_proc) {
        CloseHandle((HANDLE)g_proc);
        g_proc = NULL;
    }
}

/* Network/browser hooks. The bundled path performs no network calls; the
 * browser hook is used by the UI when a target needs the game started. */
static int s_post(const char *url, const char *body, uint8_t **out, size_t *n)
{
    (void)url;
    (void)body;
    *out = NULL;
    *n = 0;
    return 1;
}

static int s_browser(const char *url)
{
    wchar_t w[1024];
    if (MultiByteToWideChar(CP_UTF8, 0, url, -1, w, 1024) <= 0)
        return -1;
    return (INT_PTR)ShellExecuteW(NULL, L"open", w, NULL, NULL, SW_SHOWNORMAL) > 32 ? 0 : -1;
}

const os_api_t OS_WIN = {
    s_bcreate, s_bdestroy, s_wait_ack, s_inject, s_wait_inject, s_detach,
    s_post, s_browser
};
