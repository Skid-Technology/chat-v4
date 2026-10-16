/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.utils.datas;

import java.awt.Color;
import java.util.concurrent.atomic.AtomicBoolean;

public class HSBData {
    private static int var_int_K;
    public AtomicBoolean var_java_util_concurrent_atomic_AtomicBoolean_Q;
    public int var_int_N;
    public int var_int_D;
    public Color var_java_awt_Color_U;
    public int var_int_F;
    public int var_int_O;

    public HSBData(int n, int n2, int n3, int n4, Color color) {
        this(n, n2, n3, n4, color, null);
    }

    public HSBData(int n, int n2, int n3, int n4, Color color, AtomicBoolean atomicBoolean) {
        this.var_int_F = n;
        this.var_int_O = n2;
        this.var_int_D = n3;
        this.var_int_N = n4;
        this.var_java_awt_Color_U = color;
        this.var_java_util_concurrent_atomic_AtomicBoolean_Q = atomicBoolean;
    }

    public boolean q() {
        if (this.var_java_util_concurrent_atomic_AtomicBoolean_Q == null) {
            return true;
        }
        return this.var_java_util_concurrent_atomic_AtomicBoolean_Q.get();
    }

    public int D() {
        return this.var_int_O;
    }

    public int U() {
        return this.var_int_F;
    }

    public int Y() {
        return this.var_int_D;
    }

    public static int H() {
        return var_int_K;
    }

    public static int m() {
        int n = HSBData.H();
        if (n == 0) {
            return 72;
        }
        return 0;
    }

    public static void k(int n) {
        var_int_K = n;
    }

    static {
        if (HSBData.m() != 0) {
            HSBData.k(32);
        }
    }
}

