/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame.impl.main;

import gg.vape.ui.click.frame.impl.main.ClickGuiLayer;

class ClickGuiLayerSwitchMap {
    final static int[] var_int_arr_d;

    ClickGuiLayerSwitchMap() {
    }

    static {
        var_int_arr_d = new int[ClickGuiLayer.values().length];
        try {
            ClickGuiLayerSwitchMap.var_int_arr_d[ClickGuiLayer.MAIN.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            ClickGuiLayerSwitchMap.var_int_arr_d[ClickGuiLayer.OVERLAYS.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}

