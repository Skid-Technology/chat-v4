/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.layout;

import gg.vape.ui.click.layout.LayoutAnchor;
import gg.vape.ui.click.layout.LayoutDock;

class ComponentLayoutSwitchMap {
    final static int[] var_int_arr_q;
    final static int[] var_int_arr_p;

    ComponentLayoutSwitchMap() {
    }

    static {
        var_int_arr_q = new int[LayoutDock.values().length];
        try {
            ComponentLayoutSwitchMap.var_int_arr_q[LayoutDock.TOP.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            ComponentLayoutSwitchMap.var_int_arr_q[LayoutDock.BOTTOM.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            ComponentLayoutSwitchMap.var_int_arr_q[LayoutDock.LEFT.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            ComponentLayoutSwitchMap.var_int_arr_q[LayoutDock.RIGHT.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        var_int_arr_p = new int[LayoutAnchor.values().length];
        try {
            ComponentLayoutSwitchMap.var_int_arr_p[LayoutAnchor.BOTTOM_RIGHT.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            ComponentLayoutSwitchMap.var_int_arr_p[LayoutAnchor.TOP_LEFT.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            ComponentLayoutSwitchMap.var_int_arr_p[LayoutAnchor.OFFSET.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}

