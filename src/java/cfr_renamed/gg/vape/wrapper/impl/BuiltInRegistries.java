/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.Vape;
import gg.vape.mapping.mappings.MBuiltInRegistries;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.Registry;

public class BuiltInRegistries {
    private static GuiComponent[] var_gg_vape_ui_click_component_GuiComponent_arr_N;

    public static Registry W() {
        return new Registry(MBuiltInRegistries.M(Vape.INSTANCE.getMappingsMapperCompat().q7));
    }

    public static Registry j() {
        return new Registry(MBuiltInRegistries.w(Vape.INSTANCE.getMappingsMapperCompat().q7));
    }

    public static Registry Y() {
        return new Registry(MBuiltInRegistries.v(Vape.INSTANCE.getMappingsMapperCompat().q7));
    }

    public static Registry I() {
        return new Registry(MBuiltInRegistries.F(Vape.INSTANCE.getMappingsMapperCompat().q7));
    }

    public static GuiComponent[] f() {
        return var_gg_vape_ui_click_component_GuiComponent_arr_N;
    }

    public static void O(GuiComponent[] guiComponentArray) {
        var_gg_vape_ui_click_component_GuiComponent_arr_N = guiComponentArray;
    }

    public static Registry a() {
        return new Registry(MBuiltInRegistries.g(Vape.INSTANCE.getMappingsMapperCompat().q7));
    }

    static {
        if (BuiltInRegistries.f() != null) {
            BuiltInRegistries.O(new GuiComponent[1]);
        }
    }
}

