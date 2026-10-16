/* Ghidra 12.1.4 decompiler output via REA procedure_pseudo_code.
   Target: 60e47510.exe (sha256 581a7ab6...)
   Proc: FUN_14001f268 @ 0x14001f268, 1558 bytes. COMPLETE (head + tail).
   Role: login screen. Exact layout (all X centered, explicit Y):
     y=210: input  ##username  label "Username / email" (plain)
     y=270: input  ##password  label "Password" (password mode)
     y=359: button "Login" (FUN_140027124), enabled only if both fields
            non-empty -> FUN_140009a3c (password login)
     y=436: separator (FUN_14002753c)
     y=480: "Login through browser" (FUN_1400278b8) -> FUN_1400091bc
     y=530: status/error text line
   Buffers DAT_140101a60 / DAT_140101aa0 (0x40) hold username/password. */

/* WARNING: Function: __security_check_cookie replaced with injection: security_check_cookie */

void FUN_14001f268(longlong param_1,undefined8 param_2,undefined8 param_3,ulonglong param_4)

{
  longlong lVar1;
  longlong lVar2;
  bool bVar3;
  float fVar4;
  bool bVar5;
  char cVar6;
  char cVar7;
  int cbMultiByte;
  size_t sVar8;
  short *psVar9;
  byte ***pppbVar10;
  char ***_Src;
  LPCWSTR **pppWVar11;
  undefined8 uVar12;
  undefined1 auStackY_e8 [32];
  byte **local_a8 [3];
  ulonglong local_90;
  char **local_88;
  undefined8 uStack_80;
  ulonglong local_70;
  LPCWSTR *local_68 [2];
  longlong local_58;
  ulonglong local_50;
  ulonglong local_48;

  local_48 = DAT_140101040 ^ (ulonglong)auStackY_e8;
  bVar3 = false;
  local_88 = (char **)((ulonglong)local_88 & 0xffffffff00000000);
  if (DAT_140101aec == '\0') {
    FUN_14001d9cc(&local_88,(LPCWSTR)(*(longlong *)(param_1 + 0x60) + 0x78));
    FUN_14001d9cc(local_a8,(LPCWSTR)(*(longlong *)(param_1 + 0x60) + 0x98));
    _Src = &local_88;
    if (0xf < local_70) {
      _Src = (char ***)local_88;
    }
    strcpy_s(&DAT_140101a60,0x40,(char *)_Src);
    pppbVar10 = local_a8;
    if (0xf < local_90) {
      pppbVar10 = (byte ***)local_a8[0];
    }
    strcpy_s(&DAT_140101aa0,0x40,(char *)pppbVar10);
    DAT_140101aec = '\x01';
    FUN_14000bdf4((longlong *)local_a8);
    FUN_14000bdf4((longlong *)&local_88);
  }
  lVar1 = DAT_140101b70;
  *(undefined1 *)(*(longlong *)(DAT_140101b70 + 0x13a8) + 0xce) = 1;
  lVar1 = *(longlong *)(lVar1 + 0x13a8);
  *(float *)(lVar1 + 0x118) = (*(float *)(lVar1 + 0x28) - *(float *)(lVar1 + 0x98)) + 0.0;
  *(float *)(lVar1 + 0x11c) = (*(float *)(lVar1 + 0x2c) - *(float *)(lVar1 + 0x9c)) + 210.0;
  *(undefined1 *)(lVar1 + 0x159) = 1;
  FUN_140026d50((byte *)"##username",(float *)"Username / email",(float *)&DAT_140101a60,param_4,0);
  lVar1 = DAT_140101b70;
  *(undefined1 *)(*(longlong *)(DAT_140101b70 + 0x13a8) + 0xce) = 1;
  lVar1 = *(longlong *)(lVar1 + 0x13a8);
  *(float *)(lVar1 + 0x118) = (*(float *)(lVar1 + 0x28) - *(float *)(lVar1 + 0x98)) + 0.0;
  *(float *)(lVar1 + 0x11c) = (*(float *)(lVar1 + 0x2c) - *(float *)(lVar1 + 0x9c)) + 270.0;
  *(undefined1 *)(lVar1 + 0x159) = 1;
  FUN_140026d50((byte *)"##password",(float *)"Password",(float *)&DAT_140101aa0,param_4,1);
  sVar8 = strlen(&DAT_140101a60);
  if (sVar8 == 0) {
LAB_14001f45e:
    bVar5 = false;
  }
  else {
    sVar8 = strlen(&DAT_140101a60);
    psVar9 = FUN_14000aa2c((short *)local_a8,&DAT_140101a60,&DAT_140101a60 + sVar8,param_4);
    bVar3 = true;
    bVar5 = FUN_140021be0((undefined8 *)(*(longlong *)(param_1 + 0x60) + 0x78),(undefined8 *)psVar9)
    ;
    if (!bVar5) goto LAB_14001f45e;
    bVar5 = true;
  }
  if (bVar3) {
    FUN_14000b3b4((longlong *)local_a8);
  }
  bVar3 = false;
  if (bVar5) {
    sVar8 = strlen(&DAT_140101a60);
    FUN_14000aa2c((short *)local_a8,&DAT_140101a60,&DAT_140101a60 + sVar8,param_4);
    FUN_14000b274((longlong *)(*(longlong *)(param_1 + 0x60) + 0x78),(longlong *)local_a8);
    FUN_14000b3b4((longlong *)local_a8);
  }
  sVar8 = strlen(&DAT_140101aa0);
  if (sVar8 != 0) {
    sVar8 = strlen(&DAT_140101aa0);
    psVar9 = FUN_14000aa2c((short *)&local_88,&DAT_140101aa0,&DAT_140101aa0 + sVar8,param_4);
    bVar3 = true;
    bVar5 = FUN_140021be0((undefined8 *)(*(longlong *)(param_1 + 0x60) + 0x98),(undefined8 *)psVar9)
    ;
    if (bVar5) {
      bVar5 = true;
      goto LAB_14001f4f1;
    }
  }
  bVar5 = false;
LAB_14001f4f1:
  if (bVar3) {
    FUN_14000b3b4((longlong *)&local_88);
  }
  if (bVar5) {
    sVar8 = strlen(&DAT_140101aa0);
    FUN_14000aa2c((short *)local_a8,&DAT_140101aa0,&DAT_140101aa0 + sVar8,param_4);
    FUN_14000b274((longlong *)(*(longlong *)(param_1 + 0x60) + 0x98),(longlong *)local_a8);
    FUN_14000b3b4((longlong *)local_a8);
  }
  lVar1 = DAT_140101b70;
  if ((DAT_140101a60 == '\0') || (DAT_140101aa0 == '\0')) {
    cVar7 = '\0';   /* Login button disabled unless both fields non-empty */
  }
  else {
    cVar7 = '\x01';
  }
  *(undefined1 *)(*(longlong *)(DAT_140101b70 + 0x13a8) + 0xce) = 1;
  lVar1 = *(longlong *)(lVar1 + 0x13a8);
  *(float *)(lVar1 + 0x118) = (*(float *)(lVar1 + 0x28) - *(float *)(lVar1 + 0x98)) + 0.0;
  *(float *)(lVar1 + 0x11c) = (*(float *)(lVar1 + 0x2c) - *(float *)(lVar1 + 0x9c)) + 359.0;
  *(undefined1 *)(lVar1 + 0x159) = 1;
  local_88 = (char **)0x0;
  cVar6 = FUN_140027124((byte *)"Login",(float *)&local_88,cVar7,'\x01');
  if ((cVar6 != '\0') && (cVar7 != '\0')) {
    FUN_140009a3c(*(longlong *)(param_1 + 0x60));
  }
  lVar1 = DAT_140101b70;
  *(undefined1 *)(*(longlong *)(DAT_140101b70 + 0x13a8) + 0xce) = 1;
  lVar1 = *(longlong *)(lVar1 + 0x13a8);
  *(float *)(lVar1 + 0x118) = (*(float *)(lVar1 + 0x28) - *(float *)(lVar1 + 0x98)) + 0.0;
  *(float *)(lVar1 + 0x11c) = (*(float *)(lVar1 + 0x2c) - *(float *)(lVar1 + 0x9c)) + 436.0;
  *(undefined1 *)(lVar1 + 0x159) = 1;
  FUN_14002753c();   /* separator */
  lVar2 = DAT_140101b70;
  *(undefined1 *)(*(longlong *)(DAT_140101b70 + 0x13a8) + 0xce) = 1;
  lVar1 = *(longlong *)(lVar2 + 0x13a8);
  *(float *)(lVar1 + 0x118) = (*(float *)(lVar1 + 0x28) - *(float *)(lVar1 + 0x98)) + 0.0;
  *(float *)(lVar1 + 0x11c) = (*(float *)(lVar1 + 0x2c) - *(float *)(lVar1 + 0x9c)) + 480.0;
  *(undefined1 *)(lVar1 + 0x159) = 1;
  cVar7 = FUN_1400278b8(lVar2);   /* "Login through browser" */
  if (cVar7 != '\0') {
    FUN_1400091bc(*(longlong *)(param_1 + 0x60),*(longlong *)(param_1 + 8));
  }
  /* ... status/error text line at y=530 (tail, WideCharToMultiByte +
     measure + FUN_14002f8ac + FUN_14004e9b0 "%s") ... */
  FUN_140006f0c(*(longlong *)(param_1 + 0x60),local_68);
  /* tail: draws status string centered at y = base + 530.0 */
  FUN_14000b3b4((longlong *)local_68);
  return;
}
