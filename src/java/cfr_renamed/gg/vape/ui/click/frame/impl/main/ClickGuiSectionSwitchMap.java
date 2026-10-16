/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame.impl.main;

import gg.vape.ui.click.frame.impl.main.ClickGuiSection;

class ClickGuiSectionSwitchMap {
    final static int[] var_int_arr_y;

    ClickGuiSectionSwitchMap() {
    }

    static {
        var_int_arr_y = new int[ClickGuiSection.values().length];
        try {
            ClickGuiSectionSwitchMap.var_int_arr_y[ClickGuiSection.MODULES.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            ClickGuiSectionSwitchMap.var_int_arr_y[ClickGuiSection.FRIENDS.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            ClickGuiSectionSwitchMap.var_int_arr_y[ClickGuiSection.PROFILES.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}

