/* Ghidra 12.1.4 decompiler output via REA analyze_function.
   Target: 60e47510.exe (sha256 581a7ab6...)
   Proc: FUN_140023070 @ 0x140023070, 1204 bytes.
   Role: creates "Bootstrap"/"BootstrapAck" file-mapping + event, opens the
   Minecraft process, writes the Product DLL path, resolves the loader entry,
   starts loading, waits 30s, expects ack magic (lpBaseAddress[0x69]==2).
   Error strings verifiable in binary at 0x1400eb0xx-0x1400eb2xx.
   Caller: FUN_140007c30. */

/* WARNING: Function: __security_check_cookie replaced with injection: security_check_cookie */

ulonglong FUN_140023070(uint param_1,LPCWSTR param_2,short param_3,undefined8 *param_4,
                       longlong *param_5)

{
  code *pcVar1;
  bool bVar2;
  char cVar3;
  DWORD DVar4;
  uint uVar5;
  LPCWSTR ***ppppWVar6;
  HANDLE pvVar7;
  undefined4 *lpBaseAddress;
  HANDLE hObject;
  longlong lVar8;
  FARPROC pFVar9;
  longlong *extraout_RAX;
  ulonglong uVar10;
  longlong *plVar11;
  LPCWSTR lpFileName;
  longlong lVar12;
  undefined8 ****ppppuVar13;
  wchar_t *pwVar14;
  longlong lVar15;
  uint7 uVar16;
  undefined1 *puVar17;
  undefined1 *puVar18;
  size_t _Size;
  undefined4 *puVar19;
  undefined1 auStackY_108 [8];
  undefined1 auStackY_100 [24];
  undefined2 local_c4 [2];
  longlong local_c0;
  HANDLE local_b8;
  HANDLE local_b0;
  undefined8 ***local_a8;
  undefined8 uStack_a0;
  ulonglong local_98;
  ulonglong local_90;
  LPCWSTR **local_88 [3];
  ulonglong local_70;
  LPCWSTR **local_68 [3];
  ulonglong local_50;
  ulonglong local_48;

  puVar17 = auStackY_108;
  puVar18 = auStackY_108;
  local_48 = DAT_140101040 ^ (ulonglong)auStackY_108;
  if (param_3 == 0) {
    pwVar14 = L"Loader controller port is unavailable";
    puVar18 = auStackY_108;
  }
  else {
    if (param_4[2] - 1 < 0xff) {
      uStack_a0 = 0;
      local_98 = 0;
      local_90 = 0xf;
      local_a8 = (undefined8 ****)0x0;
      local_c4[0] = 0;
      cVar3 = FUN_140022ba0((longlong *)&local_a8,local_c4);
      uVar16 = (uint7)((ulonglong)param_5 >> 8);
      if (cVar3 == '\0') {
        pwVar14 = L"VAPE_ZEUS_ADDRESS must use host:port format";
LAB_140023129:
        plVar11 = FUN_14000b140(param_5,pwVar14);
        param_5 = (longlong *)((ulonglong)uVar16 << 8);
      }
      else {
        lpFileName = param_2;
        if (7 < *(ulonglong *)(param_2 + 0xc)) {
          lpFileName = *(LPCWSTR *)param_2;
        }
        DVar4 = GetFileAttributesW(lpFileName);
        if (DVar4 == 0xffffffff) {
          pwVar14 = L"NativeLoader.dll was not found";
          goto LAB_140023129;
        }
        FUN_140022788(local_68,L"Bootstrap",param_1);
        FUN_140022788(local_88,L"BootstrapAck",param_1);
        ppppWVar6 = local_68;
        if (7 < local_50) {
          ppppWVar6 = (LPCWSTR ***)local_68[0];
        }
        pvVar7 = CreateFileMappingW((HANDLE)0xffffffffffffffff,(LPSECURITY_ATTRIBUTES)0x0,4,0,0x1a8,
                                    (LPCWSTR)ppppWVar6);
        local_b0 = pvVar7;
        if ((pvVar7 == (HANDLE)0x0) || (DVar4 = GetLastError(), DVar4 == 0xb7)) {
          FUN_14000b140(param_5,L"A Loader bootstrap already exists for this process");
          if (pvVar7 != (HANDLE)0x0) goto LAB_14002348a;
LAB_140023493:
          param_5 = (longlong *)((ulonglong)uVar16 << 8);
        }
        else {
          lpBaseAddress = MapViewOfFile(pvVar7,0xf001f,0,0,0x1a8);
          ppppWVar6 = local_88;
          if (7 < local_70) {
            ppppWVar6 = (LPCWSTR ***)local_88[0];
          }
          hObject = CreateEventW((LPSECURITY_ATTRIBUTES)0x0,1,0,(LPCWSTR)ppppWVar6);
          if ((hObject == (HANDLE)0x0) || (DVar4 = GetLastError(), DVar4 != 0xb7)) {
            bVar2 = false;
          }
          else {
            bVar2 = true;
          }
          if (((lpBaseAddress == (undefined4 *)0x0) || (hObject == (HANDLE)0x0)) || (bVar2)) {
            FUN_14000b140(param_5,L"Failed to create the Loader bootstrap objects");
            if (lpBaseAddress != (undefined4 *)0x0) {
              UnmapViewOfFile(lpBaseAddress);
            }
            if (hObject != (HANDLE)0x0) {
              CloseHandle(hObject);
            }
LAB_14002348a:
            CloseHandle(pvVar7);
            goto LAB_140023493;
          }
          puVar19 = lpBaseAddress;
          for (lVar12 = 0x1a8; lVar12 != 0; lVar12 = lVar12 + -1) {
            *(undefined1 *)puVar19 = 0;
            puVar19 = (undefined4 *)((longlong)puVar19 + 1);
          }
          *lpBaseAddress = 0x54423456;
          lpBaseAddress[1] = 0x1a80002;
          lpBaseAddress[2] = param_1;
          lpBaseAddress[3] = 1;
          *(short *)(lpBaseAddress + 4) = param_3;
          FUN_140022b5c(lpBaseAddress + 5,0x100,param_4);
          _Size = 0x7f;
          if (local_98 < 0x7f) {
            _Size = local_98;
          }
          ppppuVar13 = &local_a8;
          if (0xf < local_90) {
            ppppuVar13 = (undefined8 ****)local_a8;
          }
          memcpy(lpBaseAddress + 0x45,ppppuVar13,_Size);
          *(undefined1 *)(_Size + 0x114 + (longlong)lpBaseAddress) = 0;
          *(undefined2 *)(lpBaseAddress + 0x65) = local_c4[0];
          lpBaseAddress[0x69] = 1;
          local_b8 = (HANDLE)0x0;
          lVar12 = *(longlong *)(param_2 + 8);
          local_c0 = 0;
          pvVar7 = (HANDLE)FUN_140021d3c(0,ppppuVar13,(ulonglong)param_1);
          if (pvVar7 == (HANDLE)0x0) {
            lVar8 = 0;
            FUN_14000b140(param_5,L"Failed to open Minecraft process");
            bVar2 = false;
            param_5 = (longlong *)((ulonglong)uVar16 << 8);
          }
          else {
            lVar12 = lVar12 * 2 + 2;
            lVar8 = FUN_140021f28(pvVar7,ppppuVar13,lVar12,ppppWVar6,4);
            bVar2 = false;
            if (lVar8 == 0) {
LAB_1400233d3:
              pwVar14 = L"Failed to write the Product DLL path";
            }
            else {
              if (7 < *(ulonglong *)(param_2 + 0xc)) {
                param_2 = *(LPCWSTR *)param_2;
              }
              lVar15 = lVar8;
              uVar5 = FUN_140022338(pvVar7,lVar8,param_2,lVar12,&local_c0);
              if ((uVar5 == 0) || (local_c0 != lVar12)) goto LAB_1400233d3;
              pFVar9 = FUN_14002262c();
              if (pFVar9 == (FARPROC)0x0) {
                pwVar14 = L"Failed to resolve loader entry";
              }
              else {
                local_b8 = (HANDLE)FUN_1400224a0(pvVar7,lVar15,param_2,pFVar9,lVar8);
                if (local_b8 == (HANDLE)0x0) {
                  pwVar14 = L"Failed to start Product DLL loading";
                }
                else {
                  DVar4 = WaitForSingleObject(local_b8,30000);
                  if (DVar4 == 0) {
                    bVar2 = true;
                    DVar4 = WaitForSingleObject(hObject,30000);
                    if (DVar4 == 0) {
                      if (lpBaseAddress[0x69] == 2) {
                        param_5 = (longlong *)CONCAT71(uVar16,1);
                        goto LAB_1400233e8;
                      }
                      pwVar14 = L"Product DLL rejected the socket bootstrap";
                    }
                    else {
                      pwVar14 = L"Product DLL did not acknowledge the socket bootstrap";
                    }
                    FUN_14000b140(param_5,pwVar14);
                    param_5 = (longlong *)((ulonglong)uVar16 << 8);
                    goto LAB_1400233e8;
                  }
                  pwVar14 = L"Product DLL loading timed out";
                }
              }
            }
            FUN_14000b140(param_5,pwVar14);
            param_5 = (longlong *)((ulonglong)uVar16 << 8);
          }
LAB_1400233e8:
          puVar19 = lpBaseAddress;
          for (lVar12 = 0x1a8; lVar12 != 0; lVar12 = lVar12 + -1) {
            *(undefined1 *)puVar19 = 0;
            puVar19 = (undefined4 *)((longlong)puVar19 + 1);
          }
          if (local_b8 != (HANDLE)0x0) {
            CloseHandle(local_b8);
          }
          if (pvVar7 != (HANDLE)0x0) {
            if ((lVar8 != 0) && (bVar2)) {
              FUN_140022094(pvVar7,lVar8);
            }
            CloseHandle(pvVar7);
          }
          UnmapViewOfFile(lpBaseAddress);
          CloseHandle(hObject);
          CloseHandle(local_b0);
        }
        FUN_14000b3b4((longlong *)local_88);
        plVar11 = (longlong *)FUN_14000b3b4((longlong *)local_68);
      }
      if (0xf < local_90) {
        ppppuVar13 = (undefined8 ****)local_a8;
        if ((0xfff < local_90 + 1) &&
           (ppppuVar13 = (undefined8 ****)local_a8[-1],
           0x1f < (ulonglong)((longlong)local_a8 + (-8 - (longlong)ppppuVar13)))) {
          pcVar1 = (code *)swi(0x29);
          (*pcVar1)(5);
          puVar17 = auStackY_100;
          goto LAB_1400234ec;
        }
        free(ppppuVar13);
        plVar11 = extraout_RAX;
      }
      uVar10 = CONCAT71((int7)((ulonglong)plVar11 >> 8),(char)param_5);
      goto LAB_1400234fd;
    }
LAB_1400234ec:
    pwVar14 = L"VAPE_ONLINE_BASE_URL is too long for the bootstrap";
    puVar18 = puVar17;
  }
  *(undefined8 *)(puVar18 + -8) = 0x1400234fb;
  plVar11 = FUN_14000b140(param_5,pwVar14);
  uVar10 = (ulonglong)plVar11 & 0xffffffffffffff00;
LAB_1400234fd:
  *(undefined8 *)(puVar18 + -8) = 0x140023509;
  return uVar10;
}
