/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.font.stb;

import gg.vape.ui.font.SmoothFontGlyph;

public class StbGlyphCacheEntry {
    private static String[] var_java_lang_String_arr_A;
    final public int var_int_y;
    final public float var_float_i;
    final public SmoothFontGlyph var_gg_vape_ui_font_SmoothFontGlyph_d;
    final public boolean var_boolean_u;

    public static String[] f() {
        return var_java_lang_String_arr_A;
    }

    public StbGlyphCacheEntry(SmoothFontGlyph hv_22, int n, float f, boolean bl) {
        this.var_gg_vape_ui_font_SmoothFontGlyph_d = hv_22;
        this.var_int_y = n;
        this.var_float_i = f;
        this.var_boolean_u = bl;
    }

    public static void R(String[] stringArray) {
        var_java_lang_String_arr_A = stringArray;
    }

    static {
        if (StbGlyphCacheEntry.f() != null) {
            StbGlyphCacheEntry.R(new String[5]);
        }
    }
}

