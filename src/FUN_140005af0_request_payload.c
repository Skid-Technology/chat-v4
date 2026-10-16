/* Ghidra 12.1.4 decompiler output via REA analyze_function.
   Target: 60e47510.exe (sha256 581a7ab6...)
   Proc: FUN_140005af0 @ 0x140005af0, 295 bytes.
   Role: builds "token=<token>" body and POSTs to /api/v1/client/request-payload.
   Caller: FUN_140007c30. */

/* WARNING: Function: __security_check_cookie replaced with injection: security_check_cookie */

undefined8 * FUN_140005af0(undefined8 *param_1,undefined8 *param_2,undefined8 *param_3)

{
  code *pcVar1;
  undefined8 *puVar2;
  void **ppvVar3;
  void *_Memory;
  undefined1 auStack_b8 [56];
  void *local_80;
  void *local_78;
  undefined8 *local_68;
  void *local_60;
  undefined8 uStack_58;
  undefined8 local_50;
  ulonglong local_48;
  longlong local_40 [4];
  ulonglong local_20;

  local_20 = DAT_140101040 ^ (ulonglong)auStack_b8;
  local_68 = param_1;
  FUN_14000a024(local_40,"token=",param_3);
  FUN_1400055c0(&local_80,param_2,L"/api/v1/client/request-payload",local_40);
  if (local_80 == local_78) {
    *param_1 = 0;
    param_1[1] = 0;
    param_1[2] = 0;
    param_1[3] = 0xf;
    *(undefined1 *)param_1 = 0;
  }
  else {
    local_60 = (void *)0x0;
    uStack_58 = 0;
    local_50 = 0;
    local_48 = 0;
    ppvVar3 = &local_60;
    FUN_14000dea0(ppvVar3,local_80,(longlong)local_78 - (longlong)local_80);
    FUN_140005c18(ppvVar3,param_1,(undefined1 (*) [32])&local_60);
    if (0xf < local_48) {
      _Memory = local_60;
      if ((0xfff < local_48 + 1) &&
         (_Memory = *(void **)((longlong)local_60 + -8),
         0x1f < (ulonglong)((longlong)local_60 + (-8 - (longlong)_Memory)))) {
        pcVar1 = (code *)swi(0x29);
        (*pcVar1)(5);
        pcVar1 = (code *)swi(3);
        puVar2 = (undefined8 *)(*pcVar1)();
        return puVar2;
      }
      free(_Memory);
    }
    local_50 = 0;
    local_48 = 0xf;
    local_60 = (void *)((ulonglong)local_60 & 0xffffffffffffff00);
  }
  FUN_14000ae7c((longlong *)&local_80);
  FUN_14000bdf4(local_40);
  return param_1;
}
