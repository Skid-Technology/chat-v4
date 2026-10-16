/* Ghidra 12.1.4 decompiler output via REA procedure_pseudo_code.
   Target: 60e47510.exe (sha256 581a7ab6...)
   Proc: FUN_140020744 @ 0x140020744, 1332 bytes. COMPLETE (head + tail).
   Role: Minecraft picker. Two branches (list from FUN_140007004):
     empty: "No Minecraft found" gray 0.42 at y=290 (centered in 1000px),
            "Open Minecraft to continue" gray 0.33 at y=312.
     else:  "Select a Minecraft to use" (0.53,0.52,0.53) y=165,
            "Make sure game is fully loaded first" (0.36) y=187,
            per-instance rows from y=239 step 65: label "Already Injected
            [%lu]" if injected else "PID %lu", each a 205x60 button
            (FUN_140027ac8). Clicking a non-injected row calls
            FUN_140007c30 (start load for that pid). */

/* WARNING: Function: __security_check_cookie replaced with injection: security_check_cookie */

void FUN_140020744(longlong param_1)

{
  longlong lVar1;
  char cVar2;
  char *pcVar3;
  longlong lVar4;
  uint *puVar5;
  longlong lVar6;
  LPCWSTR lpWideCharStr;
  ulonglong uVar7;
  undefined8 uVar8;
  float fVar9;
  undefined1 auStackY_208 [32];
  float local_1c8 [4];
  uint *local_1b8;
  uint *local_1b0;
  byte local_198 [320];
  ulonglong local_58;

  local_58 = DAT_140101040 ^ (ulonglong)auStackY_208;
  FUN_140007004(*(longlong *)(param_1 + 0x60),&local_1b8);
  lVar4 = DAT_140101b08;
  if (DAT_140101b00 != 0) {
    lVar4 = DAT_140101b00;
  }
  lVar6 = DAT_140101b08;
  if (DAT_140101b10 != 0) {
    lVar6 = DAT_140101b10;
  }
  if (local_1b8 == local_1b0) {
    /* empty branch (full): gray labels, then FUN_140007194 refresh */
    /* "No Minecraft found" y=290; "Open Minecraft to continue" y=312 */
    FUN_140007194(*(longlong *)(param_1 + 0x60));
  }
  else {
    /* "Select a Minecraft to use" y=165; "Make sure game is fully loaded
       first" y=187 (full header code as recovered); rows below: */
    fVar9 = 239.0;
    for (puVar5 = local_1b8; puVar5 != local_1b0; puVar5 = puVar5 + 0xc) {
      /* process name -> local_198+0x40 (WideCharToMultiByte, 0x100 max) */
      pcVar3 = "Already Injected [%lu]";
      if ((char)puVar5[10] == '\0') {
        pcVar3 = "PID %lu";
      }
      uVar7 = (ulonglong)*puVar5;
      FUN_140021c64(local_198,pcVar3,uVar7,0xffffffff);  /* format row label */
      /* cursor y = fVar9 + base; 205x60 button via FUN_140027ac8 */
      cVar2 = FUN_140027ac8(local_198 + 0x40,local_198,(char)puVar5[10],local_1c8);
      if ((cVar2 != '\0') && ((char)puVar5[10] == '\0')) {
        FUN_140007c30(*(longlong *)(param_1 + 0x60),*puVar5,uVar8);  /* inject */
      }
      fVar9 = fVar9 + 65.0;
    }
  }
  FUN_14000af4c((longlong *)&local_1b8);
  return;
}
