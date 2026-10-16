/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.font;

public class SmoothFontGlyph {
    public float var_float_w;
    public float var_float_t;
    public float var_float_B;
    public float var_float_X;
    public float var_float_G;
    private static String[] var_java_lang_String_arr_p;
    public float var_float_J;
    public float var_float_S;
    public float var_float_a;
    public float var_float_g;

    public SmoothFontGlyph(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9) {
        this.var_float_X = f;
        this.var_float_g = f3;
        this.var_float_a = f2;
        this.var_float_G = f4;
        this.var_float_t = f5;
        this.var_float_w = f7;
        this.var_float_J = f6;
        this.var_float_B = f8;
        this.var_float_S = f9;
    }

    public static String[] N() {
        return var_java_lang_String_arr_p;
    }

    public static void m(String[] stringArray) {
        var_java_lang_String_arr_p = stringArray;
    }

    static {
        if (SmoothFontGlyph.N() == null) {
            SmoothFontGlyph.m(new String[1]);
        }
    }
}

