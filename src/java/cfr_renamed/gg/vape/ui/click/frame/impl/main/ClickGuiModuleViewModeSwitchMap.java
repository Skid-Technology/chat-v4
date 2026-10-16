/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame.impl.main;

import gg.vape.ui.click.frame.impl.main.ClickGuiModuleViewMode;

public class ClickGuiModuleViewModeSwitchMap {
    final public static int[] var_int_arr_C;

    ClickGuiModuleViewModeSwitchMap() {
    }

    static {
        var_int_arr_C = new int[ClickGuiModuleViewMode.values().length];
        try {
            ClickGuiModuleViewModeSwitchMap.var_int_arr_C[ClickGuiModuleViewMode.MACROS.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            ClickGuiModuleViewModeSwitchMap.var_int_arr_C[ClickGuiModuleViewMode.LEGIT.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            ClickGuiModuleViewModeSwitchMap.var_int_arr_C[ClickGuiModuleViewMode.MODULE_CATEGORY.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}

