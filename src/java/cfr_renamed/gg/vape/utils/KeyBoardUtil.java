/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.utils;

import gg.vape.input.InputEventDispatcher;
import gg.vape.runtime.NativeBridge;

public class KeyBoardUtil {
    final public static int var_int_Q;
    final public static int var_int_h;
    final public static int var_int_M;
    final public static int var_int_W;
    final public static int var_int_N;
    final public static int var_int_v;
    final public static int var_int_k;
    final public static int var_int_e;
    final public static int var_int_A;
    final public static int var_int_t;
    final public static int var_int_g;

    public static boolean m(int n) {
        return (NativeBridge.gks(n) & 0x100) != 0;
    }

    public static void K(int n) {
        int n2 = n == 18 || n == 164 || n == 165 ? 261 : 257;
        NativeBridge.smpm(false, InputEventDispatcher.getInstance().getWindowHandle(), n2, n, KeyBoardUtil.m(n, true));
    }

    private static long m(int n, boolean bl) {
        int n2 = NativeBridge.mvk(n, 0) & 0xFF;
        long l = 1L | (long)n2 << 16;
        if (KeyBoardUtil.z(n)) {
            l |= 0x1000000L;
        }
        if (bl) {
            l |= 0xC0000000L;
        }
        return l;
    }

    public static void l(int n) {
        int n2 = n == 18 || n == 164 || n == 165 ? 260 : 256;
        NativeBridge.smpm(false, InputEventDispatcher.getInstance().getWindowHandle(), n2, n, KeyBoardUtil.m(n, false));
    }

    public static boolean h(int n) {
        return (NativeBridge.gks(n) & 0x100) != 0;
    }

    private static boolean z(int n) {
        switch (n) {
            case 33: 
            case 34: 
            case 35: 
            case 36: 
            case 37: 
            case 38: 
            case 39: 
            case 40: 
            case 45: 
            case 46: 
            case 111: 
            case 144: 
            case 163: 
            case 165: {
                return true;
            }
        }
        return false;
    }

    static {
        long[] lArray = new long[]{8077820790617145376L, 297696900683923521L, 7634409973295874135L, -1542298918925107117L, -3486869839207726942L, -6499915239893499644L, -477195949194608480L, -4462240591253602043L, -3345871758006681532L, 350199465673490688L, 2390434692210884865L};
        var_int_h = (int)lArray[10];
        var_int_g = (int)lArray[7];
        var_int_N = (int)lArray[3];
        var_int_t = (int)lArray[2];
        var_int_e = (int)lArray[1];
        var_int_W = (int)lArray[0];
        var_int_v = (int)lArray[9];
        var_int_M = (int)lArray[8];
        var_int_A = (int)lArray[5];
        var_int_Q = (int)lArray[4];
        var_int_k = (int)lArray[6];
    }
}

