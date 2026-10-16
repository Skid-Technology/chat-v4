/* Ghidra 12.1.4 decompiler output via REA procedure_pseudo_code.
   Target: 60e47510.exe (sha256 581a7ab6...)
   Proc: FUN_1400278b8 @ 0x1400278b8, 527 bytes. COMPLETE.
   Role: "Login through browser" button. Measures the label, centers it,
   draws it (FUN_140039c6c), and on click runs FUN_14004ec10; when the
   browser flow starts it sets state 7 (*(DAT_140101b70+0x2360) = 7) and
   renders with the alternate color set (DAT_1401015cc vs DAT_1401015bc).
   Called from FUN_14001f268 (login screen, y=480). */

/* WARNING: Globals starting with '_' overlap smaller symbols at the same address */

char FUN_1400278b8(undefined8 param_1)

{
  longlong *plVar1;
  undefined8 uVar2;
  char extraout_AL;
  char cVar3;
  uint uVar4;
  float fVar5;
  longlong lVar6;
  float *pfVar7;
  undefined8 local_res8;
  char local_res10 [8];
  float local_res18;
  float local_res1c;
  undefined8 local_res20;
  float local_58;
  float local_54;
  float local_50;
  float local_4c;
  float local_48;
  undefined4 uStack_44;
  undefined4 uStack_40;
  float fStack_3c;

  lVar6 = DAT_140101b70;
  *(undefined1 *)(*(longlong *)(DAT_140101b70 + 0x13a8) + 0xce) = 1;
  plVar1 = *(longlong **)(lVar6 + 0x13a8);
  if (*(char *)((longlong)plVar1 + 0xd1) == '\0') {
    lVar6 = DAT_140101b08;
    if (DAT_140101b10 != 0) {
      lVar6 = DAT_140101b10;
    }
    local_res8 = param_1;
    FUN_140036228(lVar6);
    FUN_14002f8ac((undefined8 *)&local_res18,(byte *)"Login through browser",(byte *)0x0,'\x01',-1.0
                 );
    lVar6 = DAT_140101b70;
    fVar5 = *(float *)(plVar1 + 6);
    *(undefined1 *)(*(longlong *)(DAT_140101b70 + 0x13a8) + 0xce) = 1;
    lVar6 = *(longlong *)(lVar6 + 0x13a8);
    *(undefined1 *)(lVar6 + 0x159) = 1;
    *(float *)(lVar6 + 0x118) =
         (*(float *)(lVar6 + 0x28) - *(float *)(lVar6 + 0x98)) + (fVar5 - local_res18) * 0.5;
    uVar4 = FUN_140036c20(plVar1,(byte *)"Login through browser");
    lVar6 = plVar1[0x23];
    local_res20._4_4_ = (float)((ulonglong)lVar6 >> 0x20);
    local_54 = local_res20._4_4_;
    local_4c = local_res20._4_4_ + local_res1c;
    local_58 = (float)lVar6;
    local_50 = local_58 + local_res18;
    local_res20 = lVar6;
    FUN_14003a2dc(&local_res18,-1.0);
    FUN_140039c6c(&local_58,(ulonglong)uVar4,(float *)0x0,(longlong *)0x0);
    if (extraout_AL == '\0') {
      cVar3 = '\0';
    }
    else {
      cVar3 = FUN_14004ec10(&local_58,uVar4,(longlong)&local_res8,local_res10,0);
      if ((char)local_res8 != '\0') {
        *(undefined4 *)(DAT_140101b70 + 0x2360) = 7;
      }
      lVar6 = FUN_140025f28(uVar4);
      if (*(char *)(lVar6 + 0x58) == '\0') {
        *(undefined1 *)(lVar6 + 0x58) = 1;
        uVar2 = uRam00000001401015c4;
        *(undefined8 *)(lVar6 + 0x18) = _DAT_1401015bc;
        *(undefined8 *)(lVar6 + 0x20) = uVar2;
      }
      pfVar7 = (float *)&DAT_1401015bc;
      if ((char)local_res8 != '\0') {
        pfVar7 = (float *)&DAT_1401015cc;
      }
      FUN_14002640c((undefined8 *)&local_48,(float *)(lVar6 + 0x18),pfVar7,0.1);
      local_48 = *(float *)(lVar6 + 0x18);
      uStack_44 = *(undefined4 *)(lVar6 + 0x1c);
      uStack_40 = *(undefined4 *)(lVar6 + 0x20);
      fStack_3c = *(float *)(lVar6 + 0x24) * *(float *)(DAT_140101b70 + 0xc28);
      fVar5 = (float)FUN_140029a6c(&local_48);
      FUN_140046758((int *)plVar1[0x58],(float *)&local_res20,fVar5,(byte *)"Login through browser")
      ;
    }
    FUN_14003644c();
  }
  else {
    cVar3 = '\0';
  }
  return cVar3;
}
