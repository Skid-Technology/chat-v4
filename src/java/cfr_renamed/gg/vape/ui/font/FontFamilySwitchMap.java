/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.font;

import gg.vape.ui.font.FontFamily;

public class FontFamilySwitchMap {
    final public static int[] var_int_arr_d;

    FontFamilySwitchMap() {
    }

    static {
        var_int_arr_d = new int[FontFamily.values().length];
        try {
            FontFamilySwitchMap.var_int_arr_d[FontFamily.PROXIMA.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            FontFamilySwitchMap.var_int_arr_d[FontFamily.ARIAL.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            FontFamilySwitchMap.var_int_arr_d[FontFamily.NOTO.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            FontFamilySwitchMap.var_int_arr_d[FontFamily.MINECRAFT.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}

