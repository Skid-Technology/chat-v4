/* os_win32.c — Windows backend (build on Windows only).
 * Exact API sequence recovered from FUN_140023524 / FUN_140023070.
 * Kept as documented structure: fill in the small helpers (FUN_140021d3c
 * process-open, FUN_140021f28 NtAllocateVirtualMemory wrapper, FUN_140022338
 * NtWriteVirtualMemory wrapper, FUN_1400224a0 NtCreateThreadEx wrapper,
 * FUN_140022094 NtFreeVirtualMemory wrapper, FUN_140022e10 reflective-entry
 * finder, FUN_1400221e4 import/reloc fixup, FUN_140022f60 0x200-B stub).
 *
 * Sequence per decompile:
 *  CreateFileMappingW(INVALID_HANDLE_VALUE, NULL, PAGE_READWRITE, 0, 0x1A8,
 *                     L"Bootstrap<pid>")
 *    -> ERROR_ALREADY_EXISTS: "A Loader bootstrap already exists for this process"
 *  MapViewOfFile(hMap, FILE_MAP_ALL_ACCESS, 0, 0, 0x1A8)
 *  CreateEventW(NULL, TRUE, FALSE, L"BootstrapAck<pid>")
 *  open_process: Toolhelp32 snapshot -> FUN_140021d3c -> NtOpenProcess
 *  path A: alloc(size of image) -> write image -> FUN_1400221e4 fixup ->
 *          NtCreateThreadEx(entry = base + FUN_140022e10) ->
 *          WaitForSingleObject(30000) -> GetExitCodeThread nonzero required
 *  path B: alloc(SizeOfImage) -> copy headers+sections locally ->
 *          apply DIR64/HIGHLOW relocs -> write mapped image ->
 *          alloc 0x1000 stub area -> write 0x30 params (LoadLibraryA/
 *          GetProcAddress addresses from kernel32) + 0x200 stub code ->
 *          NtCreateThreadEx(stub) -> WaitForSingleObject(30000) ->
 *          ReadProcessMemory 0x30 result, dword[?] must be nonzero
 *  WaitForSingleObject(ack, 30000); require view->status == 2
 *  cleanup: zero 0x1A8, free remote, CloseHandle x3, UnmapViewOfFile.
 */
#ifdef _WIN32
/* Real implementation goes here; signatures must match loader.h os_api_t.
 * Not compiled on other platforms. */
#endif
