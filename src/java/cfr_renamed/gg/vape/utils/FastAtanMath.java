/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.utils;

public class FastAtanMath {
    private final static float[] var_float_arr_w;
    private final static int var_int_U;
    private final static float[] var_float_arr_R;
    private final static float var_float_P = 1.5707964f;
    private final static float[] var_float_arr_G;
    private final static float[] var_float_arr_S;
    private final static int var_int_a;
    private final static float[] var_float_arr_c;
    private final static float var_float_A = (float)Math.PI;
    private final static float[] var_float_arr_d;
    private final static float[] var_float_arr_v;
    private final static float[] var_float_arr_O;
    private final static float[] var_float_arr_M;

    public static float atan2Approximation(float f, float f2) {
        if (f < 0.0f) {
            float f3;
            if (f2 < 0.0f) {
                if (f < f2) {
                    return var_float_arr_S[(int)(f2 / f * 100000.0f)];
                }
                return var_float_arr_G[(int)(f / f2 * 100000.0f)];
            }
            f = -f;
            if (f3 > f2) {
                return var_float_arr_O[(int)(f2 / f * 100000.0f)];
            }
            return var_float_arr_d[(int)(f / f2 * 100000.0f)];
        }
        if (f2 < 0.0f) {
            float f4;
            f2 = -f2;
            if (f > f4) {
                return var_float_arr_c[(int)(f2 / f * 100000.0f)];
            }
            return var_float_arr_M[(int)(f / f2 * 100000.0f)];
        }
        if (f > f2) {
            return var_float_arr_v[(int)(f2 / f * 100000.0f)];
        }
        return var_float_arr_w[(int)(f / f2 * 100000.0f)];
    }

    public static float atanApproximation(float f) {
        float f2;
        float f3 = Math.abs(f);
        if (f3 < 1.0f) {
            float f4 = f * f;
            f2 = f * (1.0f + f4 * (var_float_arr_R[0] + f4 * (var_float_arr_R[1] + f4 * (var_float_arr_R[2] + f4 * var_float_arr_R[3]))));
        } else {
            float f5 = 1.0f / f3;
            float f6 = f5 * f5;
            float f7 = f5 * (1.0f + f6 * (var_float_arr_R[0] + f6 * (var_float_arr_R[1] + f6 * (var_float_arr_R[2] + f6 * var_float_arr_R[3]))));
            f2 = Math.copySign(var_float_arr_R[4], f) - f7;
        }
        return f2;
    }

    static {
        long[] lArray = new long[]{-4778158233560709471L, -4211190230859807071L, 3095591377875076769L, -5764595647449561440L, 8562453793512457888L};
        var_int_a = (int)lArray[3];
        var_int_U = (int)lArray[2];
        var_float_arr_w = new float[(int)lArray[0]];
        var_float_arr_M = new float[(int)lArray[1]];
        var_float_arr_d = new float[(int)lArray[1]];
        var_float_arr_G = new float[(int)lArray[1]];
        var_float_arr_v = new float[(int)lArray[1]];
        var_float_arr_c = new float[(int)lArray[1]];
        var_float_arr_O = new float[(int)lArray[1]];
        var_float_arr_S = new float[(int)lArray[1]];
        for (int i = 0; i <= (int)lArray[4]; ++i) {
            float f;
            double d = (double)i / 100000.0;
            double d2 = 1.0;
            double d3 = d2 * d;
            FastAtanMath.var_float_arr_w[i] = f = (float)Math.atan2(d3, d2);
            FastAtanMath.var_float_arr_M[i] = (float)Math.PI - f;
            FastAtanMath.var_float_arr_d[i] = -f;
            FastAtanMath.var_float_arr_G[i] = (float)(-Math.PI) + f;
            FastAtanMath.var_float_arr_v[i] = 1.5707964f - f;
            FastAtanMath.var_float_arr_c[i] = 1.5707964f + f;
            FastAtanMath.var_float_arr_O[i] = -1.5707964f + f;
            FastAtanMath.var_float_arr_S[i] = -1.5707964f - f;
        }
        var_float_arr_R = new float[]{-0.33333147f, 0.19993551f, -0.142089f, 0.10656264f, 1.5707964f};
    }
}

