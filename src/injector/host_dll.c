/* host_dll.c — the native host the Loader's remote LoadLibraryW starts.
 *
 * This plays the role the original product DLL played: it is written into the
 * target's address space and started by a remote LoadLibraryW thread. What it
 * hosts here is the Java client.
 *
 *   DllMain          — hands off to a worker thread (never blocks the loader
 *                      lock, and lets the remote LoadLibraryW return at once).
 *   worker           — finds the JVM already inside this process
 *                      (JNI_GetCreatedJavaVMs), attaches, loads the client jar
 *                      sitting next to this DLL into a URLClassLoader, and
 *                      then acks the bootstrap: status = 2 + the named event.
 *
 * Build (see the repo Makefile):
 *   x86_64-w64-mingw32-gcc -shared -o Vape421Native.dll host_dll.c \
 *       -I third_party/jni -luser32
 */
#define WIN32_LEAN_AND_MEAN
#define _WIN32_WINNT 0x0600
#include <windows.h>
#include <stdio.h>
#include <string.h>
#include "loader.h"
#include "jni.h"

typedef jint (JNICALL *pGetCreatedJavaVMs)(JavaVM **pvm, jsize bufLen, jsize *nVMs);

/* --- bootstrap ack --------------------------------------------------------
 * The Loader is waiting on the mapping it created; setting status to 2 and
 * signalling the ack event is what "the product accepted the bootstrap"
 * means. Done only after the client was actually loaded. */
static void ack_bootstrap(void)
{
    wchar_t name[128];
    DWORD pid = GetCurrentProcessId();
    HANDLE map, ev;
    bootstrap_t *view;

    _snwprintf(name, 128, L"Local\\Vape421.Bootstrap%lu", (unsigned long)pid);
    map = OpenFileMappingW(FILE_MAP_ALL_ACCESS, FALSE, name);
    if (map) {
        view = (bootstrap_t *)MapViewOfFile(map, FILE_MAP_ALL_ACCESS, 0, 0, BOOTSTRAP_SIZE);
        if (view) {
            if (view->magic == BOOTSTRAP_MAGIC)
                view->status = STATUS_ACKED;
            UnmapViewOfFile(view);
        }
        CloseHandle(map);
    }
    _snwprintf(name, 128, L"Local\\Vape421.BootstrapAck%lu", (unsigned long)pid);
    ev = OpenEventW(EVENT_MODIFY_STATE, FALSE, name);
    if (ev) {
        SetEvent(ev);
        CloseHandle(ev);
    }
}

/* --- client load, through a plain class loader ---------------------------
 * The client jar is loaded and initialised directly, so it does not depend on
 * the jar carrying Agent-Class/Premain-Class manifest entries. */
static int load_client(JNIEnv *env, const char *jar)
{
    jclass file_cls, uri_cls, url_cls, ucl_cls, class_cls;
    jmethodID file_init, to_uri, to_url, new_instance, for_name;
    jobject file, uri, url;
    jobjectArray urls;
    jobject loader, cls;

    file_cls = (*env)->FindClass(env, "java/io/File");
    uri_cls  = (*env)->FindClass(env, "java/net/URI");
    url_cls  = (*env)->FindClass(env, "java/net/URL");
    ucl_cls  = (*env)->FindClass(env, "java/net/URLClassLoader");
    class_cls = (*env)->FindClass(env, "java/lang/Class");
    if (!file_cls || !uri_cls || !url_cls || !ucl_cls || !class_cls)
        return -1;

    file_init = (*env)->GetMethodID(env, file_cls, "<init>", "(Ljava/lang/String;)V");
    to_uri    = (*env)->GetMethodID(env, file_cls, "toURI", "()Ljava/net/URI;");
    to_url    = (*env)->GetMethodID(env, uri_cls, "toURL", "()Ljava/net/URL;");
    new_instance = (*env)->GetStaticMethodID(env, ucl_cls, "newInstance",
                                             "([Ljava/net/URL;)Ljava/net/URLClassLoader;");
    for_name  = (*env)->GetStaticMethodID(env, class_cls, "forName",
                                          "(Ljava/lang/String;ZLjava/lang/ClassLoader;)Ljava/lang/Class;");
    if (!file_init || !to_uri || !to_url || !new_instance || !for_name)
        return -1;

    file = (*env)->NewObject(env, file_cls, file_init, (*env)->NewStringUTF(env, jar));
    if (!file)
        return -1;
    uri = (*env)->CallObjectMethod(env, file, to_uri);
    if (!uri)
        return -1;
    url = (*env)->CallObjectMethod(env, uri, to_url);
    if (!url)
        return -1;
    urls = (*env)->NewObjectArray(env, 1, url_cls, url);
    if (!urls)
        return -1;
    loader = (*env)->CallStaticObjectMethod(env, ucl_cls, new_instance, urls);
    if (!loader)
        return -1;
    /* initialises the client (static initialiser runs on forName(..., true, ...)) */
    cls = (*env)->CallStaticObjectMethod(env, class_cls, for_name,
                                         (*env)->NewStringUTF(env, "gg.vape.Vape"),
                                         JNI_TRUE, loader);
    return cls ? 0 : -1;
}

static DWORD WINAPI worker(LPVOID param)
{
    HMODULE self = NULL;
    wchar_t dll_path[MAX_PATH * 4];
    char jar[MAX_PATH * 4];
    wchar_t *slash;
    HMODULE jvm;
    pGetCreatedJavaVMs get_vms;
    JavaVM *vm = NULL;
    JNIEnv *env = NULL;
    jsize n = 0;
    (void)param;

    if (!GetModuleHandleExW(GET_MODULE_HANDLE_EX_FLAG_FROM_ADDRESS |
                            GET_MODULE_HANDLE_EX_FLAG_UNCHANGED_REFCOUNT,
                            (LPCWSTR)(void *)&worker, &self))
        return 1;
    if (!GetModuleFileNameW(self, dll_path, (DWORD)(sizeof dll_path / sizeof dll_path[0])))
        return 1;
    slash = wcsrchr(dll_path, L'\\');
    if (!slash)
        return 1;
    *slash = L'\0';
    WideCharToMultiByte(CP_UTF8, 0, dll_path, -1, jar, (int)sizeof jar, NULL, NULL);
    strncat(jar, "\\" PAYLOAD_ENTRY, sizeof jar - strlen(jar) - 1);

    /* the JVM is already in this process — that is the whole point */
    jvm = GetModuleHandleW(L"jvm.dll");
    if (!jvm)
        jvm = LoadLibraryW(L"jvm.dll");
    if (!jvm)
        return 1;
    get_vms = (pGetCreatedJavaVMs)(void *)GetProcAddress(jvm, "JNI_GetCreatedJavaVMs");
    if (!get_vms)
        return 1;
    if (get_vms(&vm, 1, &n) != JNI_OK || !vm || n < 1)
        return 1;
    if ((*vm)->AttachCurrentThread(vm, (void **)&env, NULL) != JNI_OK || !env)
        return 1;

    if (load_client(env, jar) == 0)
        ack_bootstrap();

    (*vm)->DetachCurrentThread(vm);
    return 0;
}

BOOL WINAPI DllMain(HINSTANCE inst, DWORD reason, LPVOID reserved)
{
    (void)reserved;
    if (reason == DLL_PROCESS_ATTACH) {
        HANDLE t;
        DisableThreadLibraryCalls(inst);
        t = CreateThread(NULL, 0, worker, NULL, 0, NULL);
        if (t)
            CloseHandle(t);
    }
    return TRUE;
}
