/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame.impl.main;

import gg.vape.ui.click.frame.impl.main.ClickGuiFriendSourceMode;

public class ClickGuiFriendSourceModeSwitchMap {
    final public static int[] var_int_arr_Z;

    ClickGuiFriendSourceModeSwitchMap() {
    }

    static {
        var_int_arr_Z = new int[ClickGuiFriendSourceMode.values().length];
        try {
            ClickGuiFriendSourceModeSwitchMap.var_int_arr_Z[ClickGuiFriendSourceMode.ONLINE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            ClickGuiFriendSourceModeSwitchMap.var_int_arr_Z[ClickGuiFriendSourceMode.MINECRAFT.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}

