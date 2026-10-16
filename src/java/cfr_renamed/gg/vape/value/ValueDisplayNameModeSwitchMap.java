/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.value;

import gg.vape.value.ValueDisplayNameMode;

public class ValueDisplayNameModeSwitchMap {
    final public static int[] var_int_arr_T;

    ValueDisplayNameModeSwitchMap() {
    }

    static {
        var_int_arr_T = new int[ValueDisplayNameMode.values().length];
        try {
            ValueDisplayNameModeSwitchMap.var_int_arr_T[ValueDisplayNameMode.CUSTOM.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            ValueDisplayNameModeSwitchMap.var_int_arr_T[ValueDisplayNameMode.SIMPLE.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            ValueDisplayNameModeSwitchMap.var_int_arr_T[ValueDisplayNameMode.FULL.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}

