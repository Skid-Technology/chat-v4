/* Ghidra 12.1.4 decompiler output via REA procedure_pseudo_code.
   Target: 60e47510.exe (sha256 581a7ab6c901a9c51bb8fecb35ab91b62e12c03d302b4adc5e1a2be80044470c)
   Proc: FUN_140023524 @ 0x140023524, 2793 bytes, 146 basic blocks.
   Signature: undefined1 __fastcall FUN_140023524(uint, longlong *, ushort, undefined8 *, longlong *)
   Role: reflective-DLL injection of the downloaded payload PE into the
   Minecraft process. Validates MZ/PE/PE32+ headers, sets up the same
   0x1A8 "VB4T" bootstrap block as FUN_140023070, then two paths:
     a) FUN_140022e10(payload)!=0: write full image, fix relocs/imports via
        FUN_1400221e4, start reflective loader thread (FUN_1400224a0),
        30 s wait, GetExitCodeThread must be nonzero.
     b) manual-map path: allocate SizeOfImage in target, copy headers +
        sections, apply base relocs (types 10/3), write mapped image +
        0x200-byte LoadLibraryA/GetProcAddress injection stub
        (FUN_140022f60), run stub, 30 s wait, ReadProcessMemory result
        must be nonzero.
   Then waits on the BootstrapAck event for status==2. All error strings
   below are verbatim wide strings from the binary. */

/* WARNING: Function: __security_check_cookie replaced with injection: security_check_cookie */

undefined1
FUN_140023524(uint param_1,longlong *param_2,ushort param_3,undefined8 *param_4,longlong *param_5)

{
  int *piVar1;
  longlong *plVar2;
  int *piVar3;
  uint uVar4;
  short *psVar5;
  bool bVar6;
  char cVar7;
  undefined1 uVar8;
  DWORD DVar9;
  uint uVar10;
  uint uVar11;
  uint uVar12;
  BOOL BVar13;
  LPCWSTR ***ppppWVar14;
  HANDLE pvVar15;
  HANDLE pvVar16;
  LPCVOID lpBaseAddress;
  HMODULE hModule;
  FARPROC pFVar17;
  longlong lVar18;
  void *pvVar19;
  ushort uVar20;
  wchar_t *pwVar21;
  ulonglong uVar22;
  undefined8 ****_Src;
  longlong lVar23;
  undefined1 *puVar25;
  undefined1 *puVar26;
  undefined1 *puVar27;
  undefined4 *puVar28;
  size_t _Size;
  ulonglong uVar29;
  code *pcVar30;
  undefined4 *puVar31;
  longlong lVar32;
  longlong lVar33;
  int *piVar34;
  undefined1 auStackY_1c8 [8];
  undefined1 auStackY_1c0 [24];
  ushort local_188 [4];
  undefined4 *local_180;
  HANDLE local_178;
  DWORD local_170 [2];
  void *local_168;
  void *pvStack_160;
  longlong local_158;
  ulonglong local_150;
  longlong local_148;
  longlong local_140;
  HANDLE local_138;
  HANDLE local_130;
  SIZE_T local_128;
  LPCVOID local_120;
  longlong local_118;
  int local_110;
  int local_10c;
  int local_108;
  undefined4 local_104;
  undefined4 uStack_100;
  undefined4 uStack_fc;
  undefined4 uStack_f8;
  undefined4 uStack_f4;
  undefined4 uStack_f0;
  undefined4 local_ec;
  undefined8 local_e8;
  undefined8 uStack_e0;
  undefined8 local_d8;
  undefined8 uStack_d0;
  undefined8 local_c8;
  undefined8 uStack_c0;
  undefined8 ***local_b8;
  undefined8 uStack_b0;
  ulonglong local_a8;
  ulonglong local_a0;
  LPCWSTR **local_98 [3];
  ulonglong local_80;
  LPCWSTR **local_78 [3];
  ulonglong local_60;
  ulonglong local_58;
  ulonglong uVar24;

  puVar26 = auStackY_1c8;
  puVar27 = auStackY_1c8;
  local_58 = DAT_140101040 ^ (ulonglong)auStackY_1c8;
  psVar5 = (short *)*param_2;
  if (psVar5 == (short *)param_2[1]) {
    pwVar21 = L"Payload image is empty";
  }
  else if (param_3 == 0) {
    pwVar21 = L"Loader controller port is unavailable";
  }
  else if (param_4[2] - 1 < 0xff) {
    uVar22 = param_2[1] - (longlong)psVar5;
    if (uVar22 < 0x40) {
      pwVar21 = L"Payload is too small to be a valid PE";
    }
    else if (((*psVar5 == 0x5a4d) && (0 < *(int *)(psVar5 + 0x1e))) &&
            ((longlong)*(int *)(psVar5 + 0x1e) + 0x108U <= uVar22)) {
      piVar1 = (int *)((longlong)*(int *)(psVar5 + 0x1e) + (longlong)psVar5);
      if ((*piVar1 == 0x4550) && ((short)piVar1[6] == 0x20b)) {
        uStack_b0 = 0;
        local_a8 = 0;
        local_a0 = 0xf;
        local_b8 = (undefined8 ****)0x0;
        local_188[0] = 0;
        cVar7 = FUN_140022ba0((longlong *)&local_b8,local_188);
        if (cVar7 == '\0') {
          FUN_14000b140(param_5,L"VAPE_ZEUS_ADDRESS must use host:port format");
          uVar8 = 0;
          puVar27 = auStackY_1c8;
        }
        else {
          FUN_140022788(local_78,L"Bootstrap",param_1);
          FUN_140022788(local_98,L"BootstrapAck",param_1);
          ppppWVar14 = local_78;
          if (7 < local_60) {
            ppppWVar14 = (LPCWSTR ***)local_78[0];
          }
          pvVar15 = CreateFileMappingW((HANDLE)0xffffffffffffffff,(LPSECURITY_ATTRIBUTES)0x0,4,0,
                                       0x1a8,(LPCWSTR)ppppWVar14);
          local_130 = pvVar15;
          if ((pvVar15 == (HANDLE)0x0) || (DVar9 = GetLastError(), DVar9 == 0xb7)) {
            FUN_14000b140(param_5,L"A Loader bootstrap already exists for this process");
            if (pvVar15 != (HANDLE)0x0) goto LAB_140023f83;
LAB_140023f8c:
            uVar8 = 0;
          }
          else {
            local_180 = MapViewOfFile(pvVar15,0xf001f,0,0,0x1a8);
            ppppWVar14 = local_98;
            if (7 < local_80) {
              ppppWVar14 = (LPCWSTR ***)local_98[0];
            }
            pvVar16 = CreateEventW((LPSECURITY_ATTRIBUTES)0x0,1,0,(LPCWSTR)ppppWVar14);
            local_178 = pvVar16;
            if ((pvVar16 == (HANDLE)0x0) || (DVar9 = GetLastError(), DVar9 != 0xb7)) {
              bVar6 = false;
            }
            else {
              bVar6 = true;
            }
            if (((local_180 == (undefined4 *)0x0) || (pvVar16 == (HANDLE)0x0)) || (bVar6)) {
              FUN_14000b140(param_5,L"Failed to create the Loader bootstrap objects");
              if (local_180 != (undefined4 *)0x0) {
                UnmapViewOfFile(local_180);
              }
              pvVar15 = local_130;
              if (pvVar16 != (HANDLE)0x0) {
                CloseHandle(pvVar16);
                pvVar15 = local_130;
              }
LAB_140023f83:
              CloseHandle(pvVar15);
              goto LAB_140023f8c;
            }
            puVar28 = local_180;
            for (lVar18 = 0x1a8; lVar18 != 0; lVar18 = lVar18 + -1) {
              *(undefined1 *)puVar28 = 0;
              puVar28 = (undefined4 *)((longlong)puVar28 + 1);
            }
            *local_180 = 0x54423456;
            local_180[1] = 0x1a80002;
            local_180[2] = param_1;
            local_180[3] = 1;
            *(ushort *)(local_180 + 4) = param_3;
            FUN_140022b5c(local_180 + 5,0x100,param_4);
            _Size = 0x7f;
            if (local_a8 < 0x7f) {
              _Size = local_a8;
            }
            _Src = &local_b8;
            if (0xf < local_a0) {
              _Src = (undefined8 ****)local_b8;
            }
            memcpy(local_180 + 0x45,_Src,_Size);
            *(undefined1 *)(_Size + 0x114 + (longlong)local_180) = 0;
            *(ushort *)(local_180 + 0x65) = local_188[0];
            local_180[0x69] = 1;
            local_188[0] = local_188[0] & 0xff00;
            local_138 = (HANDLE)0x0;
            local_120 = (LPCVOID)0x0;
            pvVar15 = (HANDLE)FUN_140021d3c(local_180,_Src,(ulonglong)param_1);
            if (pvVar15 == (HANDLE)0x0) {
              pwVar21 = L"Failed to open Minecraft process";
LAB_1400237f0:
              FUN_14000b140(param_5,pwVar21);
              uVar8 = 0;
LAB_140023ee2:
              pvVar16 = *(HANDLE *)(puVar26 + 0x50);
LAB_140023ee7:
              puVar28 = *(undefined4 **)(puVar26 + 0x48);
              puVar27 = puVar26;
            }
            else {
              uVar10 = FUN_140022e10(param_2);
              if (uVar10 != 0) {
                /* Path A: reflective loader. uVar10 = loader entry RVA. */
                lVar18 = FUN_140021f28(pvVar15,_Src,param_2[1] - *param_2,ppppWVar14,4);
                local_140 = 0;
                if (lVar18 == 0) {
LAB_14002391c:
                  FUN_14000b140(param_5,L"Failed to write reflective image into target process");
                  puVar26 = auStackY_1c8;
                  if (lVar18 != 0) goto LAB_1400238c1;
                }
                else {
                  lVar33 = param_2[1] - *param_2;
                  uVar11 = FUN_140022338(pvVar15,lVar18,*param_2,lVar33,&local_140);
                  if ((uVar11 == 0) || (lVar32 = param_2[1] - *param_2, local_140 != lVar32))
                  goto LAB_14002391c;
                  local_150 = local_150 & 0xffffffff00000000;
                  lVar23 = lVar18;
                  FUN_1400221e4(pvVar15,lVar18,lVar32,lVar33,&local_150);
                  pvVar16 = (HANDLE)FUN_1400224a0(pvVar15,lVar23,lVar32,lVar18 + (ulonglong)uVar10,
                                                  (ulonglong)param_3);
                  local_138 = pvVar16;
                  if (pvVar16 != (HANDLE)0x0) {
                    WaitForSingleObject(pvVar16,30000);
                    local_170[0] = 0;
                    GetExitCodeThread(pvVar16,local_170);
                    FUN_140022094(pvVar15,lVar18);
                    if (local_170[0] != 0) goto LAB_140023e50;
                    FUN_14000b140(param_5,L"Reflective loader returned failure");
                    puVar26 = auStackY_1c8;
                    goto LAB_140023ede;
                  }
                  FUN_14000b140(param_5,L"Failed to start reflective loader thread");
LAB_1400238c1:
                  FUN_140022094(pvVar15,lVar18);
                  puVar26 = auStackY_1c8;
                }
LAB_140023ede:
                uVar8 = puVar26[0x40];
                goto LAB_140023ee2;
              }
              /* Path B: manual map + LoadLibraryA injection stub. */
              uVar10 = piVar1[0x14];              /* OptionalHeader.SizeOfImage */
              uVar22 = (ulonglong)uVar10;
              local_140 = FUN_140021f28(pvVar15,_Src,uVar22,ppppWVar14,0x40);
              if (local_140 == 0) {
                pwVar21 = L"Failed to allocate memory in target process for payload";
                goto LAB_1400237f0;
              }
              local_168 = (void *)0x0;
              pvStack_160 = (void *)0x0;
              local_158 = 0;
              if (uVar10 != 0) {
                if (uVar10 < 0x1000) {
                  local_168 = (void *)FUN_1400c7ac0(uVar22);
LAB_1400239af:
                  lVar18 = (longlong)local_168 + uVar22;
                  pvStack_160 = local_168;
                  local_158 = lVar18;
                  memset(local_168,0,uVar22);
                  local_150 = 0;
                  pvStack_160 = (void *)lVar18;
                  FUN_14000edf8(&local_150);
                  goto LAB_1400239e7;
                }
                if (uVar22 + 0x27 <= uVar22) {
                    /* WARNING: Subroutine does not return */
                  FUN_140005130();
                }
                lVar18 = FUN_1400c7ac0(uVar22 + 0x27);
                if (lVar18 != 0) {
                  local_168 = (void *)(lVar18 + 0x27U & 0xffffffffffffffe0);
                  *(longlong *)((longlong)local_168 - 8) = lVar18;
                  goto LAB_1400239af;
                }
LAB_140023ebd:
                pcVar30 = (code *)swi(0x29);
                (*pcVar30)(5);
                puVar26 = auStackY_1c0;
LAB_140023ec4:
                puVar25 = puVar26;
                pwVar21 = L"Failed to write mapped payload into target process";
LAB_140023ecb:
                *(undefined8 *)(puVar25 + -8) = 0x140023ed3;
                FUN_14000b140(param_5,pwVar21);
                *(undefined8 *)(puVar25 + -8) = 0x140023ede;
                FUN_14000ae7c((longlong *)(puVar25 + 0x60));
                puVar26 = puVar25;
                goto LAB_140023ede;
              }
LAB_1400239e7:
              puVar25 = auStackY_1c8;
              uVar24 = 0;
              uVar29 = (ulonglong)(uint)piVar1[0x15];   /* SizeOfHeaders */
              if ((ulonglong)(param_2[1] - *param_2) < (ulonglong)(uint)piVar1[0x15]) {
                uVar29 = param_2[1] - *param_2;
              }
              memcpy(local_168,(void *)*param_2,uVar29); /* headers */
              lVar18 = (ulonglong)*(ushort *)(piVar1 + 5) + 0x18 + (longlong)piVar1; /* 1st section */
              local_170[0] = 0;
              if (*(short *)((longlong)piVar1 + 6) != 0) {  /* NumberOfSections */
                do {                                        /* copy sections */
                  uVar11 = *(uint *)(lVar18 + 0x10);        /* SizeOfRawData */
                  if (uVar11 != 0) {
                    lVar33 = *param_2;
                    uVar4 = *(uint *)(lVar18 + 0x14);       /* PointerToRawData */
                    if ((ulonglong)uVar4 < (ulonglong)(param_2[1] - lVar33)) {
                      uVar12 = ((int)param_2[1] - uVar4) - (int)lVar33;
                      if (uVar12 < uVar11) {
                        uVar11 = uVar12;
                      }
                      if (*(uint *)(lVar18 + 0xc) + uVar11 <= uVar10) { /* VirtualAddress+size */
                        memcpy((void *)((ulonglong)*(uint *)(lVar18 + 0xc) + (longlong)local_168),
                               (void *)((ulonglong)uVar4 + lVar33),(ulonglong)uVar11);
                        uVar24 = (ulonglong)local_170[0];
                      }
                    }
                  }
                  uVar20 = (short)uVar24 + 1;
                  uVar24 = CONCAT62((int6)(uVar24 >> 0x10),uVar20);
                  local_170[0] = (DWORD)uVar24;
                  lVar18 = lVar18 + 0x28;
                } while (uVar20 < *(ushort *)((longlong)piVar1 + 6));
              }
              lVar18 = local_140;
              uVar29 = 0;
              lVar33 = local_140 - *(longlong *)(piVar1 + 0xc); /* delta = newBase - ImageBase */
              if (((lVar33 != 0) && (piVar1[0x2d] != 0)) &&      /* reloc dir present */
                 (uVar11 = piVar1[0x2c], piVar1[0x2d] + uVar11 <= uVar10)) {
                do {                                            /* apply base relocs */
                  piVar34 = (int *)((longlong)local_168 + uVar29 + uVar11);
                  uVar10 = piVar34[1];                          /* block size */
                  if ((uVar10 == 0) || ((uint)(piVar1[0x2d] - (int)uVar29) < uVar10)) break;
                  uVar24 = 0;
                  uVar10 = (uint)((ulonglong)uVar10 - 8 >> 1);
                  if (uVar10 != 0) {
                    do {
                      uVar20 = *(ushort *)((longlong)piVar34 + uVar24 * 2 + 8);
                      uVar11 = (uint)(uVar20 & 0xfff) + *piVar34;
                      if ((ulonglong)uVar11 + 8 <= uVar22) {
                        uVar20 = uVar20 >> 0xc;
                        if (uVar20 == 10) {                     /* DIR64 */
                          plVar2 = (longlong *)((ulonglong)uVar11 + (longlong)local_168);
                          *plVar2 = *plVar2 + lVar33;
                        }
                        else if (uVar20 == 3) {                 /* HIGHLOW */
                          piVar3 = (int *)((ulonglong)uVar11 + (longlong)local_168);
                          *piVar3 = *piVar3 + (int)lVar33;
                        }
                      }
                      uVar11 = (int)uVar24 + 1;
                      uVar24 = (ulonglong)uVar11;
                    } while (uVar11 < uVar10);
                    uVar11 = piVar1[0x2c];
                  }
                  uVar10 = (int)uVar29 + piVar34[1];
                  uVar29 = (ulonglong)uVar10;
                } while (uVar10 < (uint)piVar1[0x2d]);
              }
              local_148 = 0;
              lVar32 = (longlong)pvStack_160 - (longlong)local_168;
              lVar33 = local_140;
              uVar10 = FUN_140022338(pvVar15,local_140,local_168,lVar32,&local_148);
              puVar26 = auStackY_1c8;
              if ((uVar10 == 0) ||
                 (puVar26 = auStackY_1c8, local_148 != (longlong)pvStack_160 - (longlong)local_168))
              goto LAB_140023ec4;
              lpBaseAddress = (LPCVOID)FUN_140021f28(pvVar15,lVar33,0x1000,lVar32,0x40);
              local_120 = lpBaseAddress;
              if (lpBaseAddress == (LPCVOID)0x0) {
                pwVar21 = L"Failed to allocate memory for injection stub";
                goto LAB_140023ecb;
              }
              hModule = GetModuleHandleW(L"kernel32.dll");
              if (hModule == (HMODULE)0x0) {
                hModule = LoadLibraryW(L"kernel32.dll");
              }
              local_104 = 0;
              uStack_100 = 0;
              uStack_fc = 0;
              uStack_f8 = 0;
              uStack_f4 = 0;
              uStack_f0 = 0;
              local_ec = 0;
              local_118 = lVar18;
              local_110 = piVar1[0x24];
              local_10c = piVar1[10];
              local_108 = piVar1[0x34];
              pFVar17 = GetProcAddress(hModule,"LoadLibraryA");
              uStack_100 = SUB84(pFVar17,0);
              uStack_fc = (undefined4)((ulonglong)pFVar17 >> 0x20);
              pFVar17 = GetProcAddress(hModule,"GetProcAddress");
              uStack_f8 = SUB84(pFVar17,0);
              uStack_f4 = (undefined4)((ulonglong)pFVar17 >> 0x20);
              uStack_f0 = 0;
              pcVar30 = FUN_140022f60;      /* 0x200-byte stub body */
              if (FUN_140022f60 == (code)0xe9) {
                local_170[0] = 0;
                memcpy(local_170,(void *)0x140022f61,4);
                pcVar30 = (code *)((longlong)(int)local_170[0] + 0x140022f65);
              }
              else if ((FUN_140022f60 == (code)0xff) && (cRam0000000140022f61 == '%')) {
                local_150 = local_150 & 0xffffffff00000000;
                memcpy(&local_150,(void *)0x140022f62,4);
                pcVar30 = *(code **)((longlong)(int)local_150 + 0x140022f66);
              }
              /* stub params (0x30 bytes) then stub code (0x200 bytes) */
              uVar10 = FUN_140022338(pvVar15,lpBaseAddress,&local_118,0x30,&local_148);
              if ((uVar10 == 0) || (local_148 != 0x30)) {
                pwVar21 = L"Failed to write injection stub parameters";
LAB_140023d40:
                FUN_14000b140(param_5,pwVar21);
                puVar26 = auStackY_1c8;
                if (local_168 != (void *)0x0) {
                  pvVar19 = local_168;
                  if ((0xfff < (ulonglong)(local_158 - (longlong)local_168)) &&
                     (pvVar19 = *(void **)((longlong)local_168 + -8),
                     0x1f < (ulonglong)((longlong)local_168 + (-8 - (longlong)pvVar19))))
                  goto LAB_140023ebd;
                  free(pvVar19);
                  local_168 = (void *)0x0;
                  pvStack_160 = (void *)0x0;
                  local_158 = 0;
                  puVar26 = auStackY_1c8;
                }
                goto LAB_140023ede;
              }
              lVar18 = (longlong)lpBaseAddress + 0x40;
              uVar10 = FUN_140022338(pvVar15,lVar18,pcVar30,0x200,&local_148);
              if ((uVar10 == 0) || (local_148 != 0x200)) {
                pwVar21 = L"Failed to write injection stub code";
                goto LAB_140023d40;
              }
              local_138 = (HANDLE)FUN_1400224a0(pvVar15,lVar18,pcVar30,
                                                (longlong)lpBaseAddress + 0x40,lpBaseAddress);
              if (local_138 == (HANDLE)0x0) {
                pwVar21 = L"Failed to execute injection stub in target process";
                goto LAB_140023d40;
              }
              DVar9 = WaitForSingleObject(local_138,30000);
              if (DVar9 != 0) {
                pwVar21 = L"Injection stub execution timed out";
                goto LAB_140023d40;
              }
              local_e8 = 0;
              uStack_e0 = 0;
              local_d8 = 0;
              uStack_d0 = 0;
              local_c8 = 0;
              uStack_c0 = 0;
              local_128 = 0;
              BVar13 = ReadProcessMemory(pvVar15,lpBaseAddress,&local_e8,0x30,&local_128);
              if (((BVar13 != 0) && (local_128 == 0x30)) && ((int)uStack_c0 == 0)) {
                pwVar21 = L"Injection stub returned failure (DllMain or import resolution failed)";
                goto LAB_140023d40;
              }
              if (local_168 != (void *)0x0) {
                pvVar19 = local_168;
                if ((0xfff < (ulonglong)(local_158 - (longlong)local_168)) &&
                   (pvVar19 = *(void **)((longlong)local_168 + -8),
                   0x1f < (ulonglong)((longlong)local_168 + (-8 - (longlong)pvVar19))))
                goto LAB_140023ebd;
                free(pvVar19);
                local_168 = (void *)0x0;
                pvStack_160 = (void *)0x0;
                local_158 = 0;
              }
LAB_140023e50:
              pvVar16 = local_178;
              puVar26 = auStackY_1c8;
              puVar27 = auStackY_1c8;
              DVar9 = WaitForSingleObject(local_178,30000);
              puVar28 = local_180;
              if (DVar9 != 0) {
                FUN_14000b140(param_5,L"Product DLL did not acknowledge the socket bootstrap");
                uVar8 = (undefined1)local_188[0];
                goto LAB_140023ee7;
              }
              if (local_180[0x69] == 2) {
                uVar8 = 1;
              }
              else {
                FUN_14000b140(param_5,L"Product DLL rejected the socket bootstrap");
                puVar27 = auStackY_1c8;
                uVar8 = (undefined1)local_188[0];
              }
            }
            /* shared cleanup: zero block, free remote mem, close handles */
            puVar31 = puVar28;
            for (lVar18 = 0x1a8; lVar18 != 0; lVar18 = lVar18 + -1) {
              *(undefined1 *)puVar31 = 0;
              puVar31 = (undefined4 *)((longlong)puVar31 + 1);
            }
            if ((local_120 != (LPCVOID)0x0) && (pvVar15 != (HANDLE)0x0)) {
              *(undefined8 *)(puVar27 + -8) = 0x140023f11;
              FUN_140022094(pvVar15,local_120);
            }
            if (local_138 != (HANDLE)0x0) {
              *(undefined8 *)(puVar27 + -8) = 0x140023f23;
              CloseHandle(local_138);
            }
            if (pvVar15 != (HANDLE)0x0) {
              *(undefined8 *)(puVar27 + -8) = 0x140023f31;
              CloseHandle(pvVar15);
            }
            *(undefined8 *)(puVar27 + -8) = 0x140023f3a;
            UnmapViewOfFile(puVar28);
            *(undefined8 *)(puVar27 + -8) = 0x140023f43;
            CloseHandle(pvVar16);
            *(undefined8 *)(puVar27 + -8) = 0x140023f4d;
            CloseHandle(local_130);
          }
          *(undefined8 *)(puVar27 + -8) = 0x140023f97;
          FUN_14000b3b4((longlong *)local_98);
          *(undefined8 *)(puVar27 + -8) = 0x140023fa1;
          FUN_14000b3b4((longlong *)local_78);
        }
        *(undefined8 *)(puVar27 + -8) = 0x140023fab;
        FUN_14000bdf4((longlong *)&local_b8);
        goto LAB_140023fe8;
      }
      pwVar21 = L"Payload is not a valid 64-bit PE image";
    }
    else {
      pwVar21 = L"Payload has invalid PE headers";
    }
  }
  else {
    pwVar21 = L"VAPE_ONLINE_BASE_URL is too long for the bootstrap";
  }
  FUN_14000b140(param_5,pwVar21);
  uVar8 = 0;
  puVar27 = auStackY_1c8;
LAB_140023fe8:
  *(undefined8 *)(puVar27 + -8) = 0x140023ff4;
  return uVar8;
}
