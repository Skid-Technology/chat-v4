/* Ghidra 12.1.4 decompiler output via REA procedure_pseudo_code.
   Target: 60e47510.exe (sha256 581a7ab6...)
   Proc: FUN_140021550 @ 0x140021550, 1105 bytes. COMPLETE.
   Role: loading/done screen. Two phases:
     - progress phase (param_1+0x90 == 0): orange bar (fill 0xff6f8b05 on
       track 0xff20201f, 290px wide, min 8px) at y = base+312; else quit.
     - done phase: "Vape has finished loading" (y=277),
       "Press RIGHT SHIFT(Default) while in game to open the GUI" (y=300),
       "Close Window" button 140x44 at y=336 -> PostMessageW(WM_CLOSE).
   All labels horizontally centered via (1000.0 - textwidth) * 0.5. */

void FUN_140021550(longlong param_1)

{
  int *piVar1;
  longlong lVar2;
  char cVar3;
  float *pfVar4;
  float *pfVar5;
  longlong lVar6;
  longlong lVar7;
  undefined8 uVar8;
  float fVar9;
  float fVar10;
  float fVar11;
  float local_res8;
  float local_resc;
  float local_res10;
  float local_res14;
  float local_88 [3];
  undefined8 local_7c;
  undefined8 uStack_74;

  if (*(char *)(*(longlong *)(param_1 + 0x60) + 0x29a) == '\0') {
    if (*(char *)(param_1 + 0x90) != '\0') goto LAB_1400215b3;
    *(undefined1 *)(param_1 + 0x90) = 1;
    *(undefined4 *)(param_1 + 0x8c) = 0;
  }
  if (*(char *)(param_1 + 0x90) == '\0') {
    lVar6 = DAT_140101b08;
    if (DAT_140101b00 != 0) {
      lVar6 = DAT_140101b00;
    }
    lVar7 = DAT_140101b08;
    if (DAT_140101b10 != 0) {
      lVar7 = DAT_140101b10;
    }
    if (lVar6 != 0) {
      FUN_140036228(lVar6);
    }
    uVar8 = 0;
    FUN_14002f8ac((undefined8 *)&local_res8,(byte *)"Vape has finished loading",(byte *)0x0,'\0',
                  -1.0);
    lVar2 = DAT_140101b70;
    *(undefined1 *)(*(longlong *)(DAT_140101b70 + 0x13a8) + 0xce) = 1;
    lVar2 = *(longlong *)(lVar2 + 0x13a8);
    *(undefined1 *)(lVar2 + 0x159) = 1;
    *(float *)(lVar2 + 0x118) =
         (*(float *)(lVar2 + 0x28) - *(float *)(lVar2 + 0x98)) + (1000.0 - local_res8) * 0.5;
    local_7c = 0x3f0f5c293f11eb85;
    uStack_74 = 0x3f8000003f11eb85;
    *(float *)(lVar2 + 0x11c) = (*(float *)(lVar2 + 0x2c) - *(float *)(lVar2 + 0x9c)) + 277.0;
    FUN_14004e9b0(&local_7c,"%s",0x1400ea970,uVar8);
    if (lVar6 != 0) {
      FUN_14003644c();
    }
    if (lVar7 != 0) {
      FUN_140036228(lVar7);
    }
    uVar8 = 0;
    FUN_14002f8ac((undefined8 *)&local_res8,
                  (byte *)"Press RIGHT SHIFT(Default) while in game to open the GUI",(byte *)0x0,
                  '\0',-1.0);
    lVar6 = DAT_140101b70;
    *(undefined1 *)(*(longlong *)(DAT_140101b70 + 0x13a8) + 0xce) = 1;
    lVar6 = *(longlong *)(lVar6 + 0x13a8);
    *(undefined1 *)(lVar6 + 0x159) = 1;
    *(float *)(lVar6 + 0x118) =
         (*(float *)(lVar6 + 0x28) - *(float *)(lVar6 + 0x98)) + (1000.0 - local_res8) * 0.5;
    local_7c = 0x3ecccccd3ed1eb85;
    uStack_74 = 0x3f8000003ed1eb85;
    *(float *)(lVar6 + 0x11c) = (*(float *)(lVar6 + 0x2c) - *(float *)(lVar6 + 0x9c)) + 300.0;
    FUN_14004e9b0(&local_7c,"%s",0x1400ea990,uVar8);
    if (lVar7 != 0) {
      FUN_14003644c();
    }
    lVar6 = DAT_140101b70;
    local_res8 = 140.0;
    local_resc = 44.0;
    *(undefined1 *)(*(longlong *)(DAT_140101b70 + 0x13a8) + 0xce) = 1;
    lVar6 = *(longlong *)(lVar6 + 0x13a8);
    *(undefined1 *)(lVar6 + 0x159) = 1;
    *(float *)(lVar6 + 0x11c) = (*(float *)(lVar6 + 0x2c) - *(float *)(lVar6 + 0x9c)) + 336.0;
    *(float *)(lVar6 + 0x118) = (*(float *)(lVar6 + 0x28) - *(float *)(lVar6 + 0x98)) + 0.0;
    cVar3 = FUN_140027124((byte *)"Close Window",&local_res8,'\x01','\x01');
    if (cVar3 == '\0') {
      return;
    }
    PostMessageW(*(HWND *)(param_1 + 8),0x10,0,0);
    return;
  }
LAB_1400215b3:
  lVar6 = DAT_140101b70;
  local_88[2] = 1.0;
  local_88[0] = 0.0;
  *(float *)(param_1 + 0x8c) = *(float *)(DAT_140101b70 + 0x18) + *(float *)(param_1 + 0x8c);
  *(undefined1 *)(*(longlong *)(lVar6 + 0x13a8) + 0xce) = 1;
  local_88[1] = 1.0 - (*(float *)(param_1 + 0x8c) + *(float *)(param_1 + 0x8c));
  pfVar5 = local_88;
  if (0.0 <= local_88[1]) {
    pfVar5 = local_88 + 1;
  }
  pfVar4 = local_88 + 2;
  if (local_88[1] <= 1.0) {
    pfVar4 = pfVar5;
  }
  if (0.0 < *pfVar4) {
    lVar6 = *(longlong *)(lVar6 + 0x13a8);
    fVar9 = *(float *)(lVar6 + 0x2c) + 312.0;
    piVar1 = *(int **)(lVar6 + 0x2c0);
    fVar11 = *pfVar4 * 290.0;
    if (fVar11 <= 8.0) {
      fVar11 = 8.0;
    }
    fVar10 = (290.0 - fVar11) * 0.5 + *(float *)(lVar6 + 0x28) + 355.0;
    local_res8 = fVar11 + fVar10;
    local_resc = fVar9 + 8.0;
    local_res10 = fVar10;
    local_res14 = fVar9;
    FUN_140045e4c(piVar1,&local_res10,&local_res8,0xff20201f,4.0,0);
    local_res8 = fVar11 + fVar10;
    local_resc = fVar9 + 8.0;
    local_res10 = fVar10;
    local_res14 = fVar9;
    FUN_140045e4c(piVar1,&local_res10,&local_res8,0xff6f8b05,4.0,0);
  }
  else {
    PostQuitMessage(0);
  }
  return;
}
