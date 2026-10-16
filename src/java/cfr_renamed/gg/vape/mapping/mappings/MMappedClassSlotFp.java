/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.ui.click.component.GuiComponent;

public class MMappedClassSlotFp
extends Mapping {
    private static GuiComponent[] var_gg_vape_ui_click_component_GuiComponent_arr_g;

    public MMappedClassSlotFp() {
        super(MappedClasses.Fp);
    }

    public static void k(GuiComponent[] guiComponentArray) {
        var_gg_vape_ui_click_component_GuiComponent_arr_g = guiComponentArray;
    }

    public static GuiComponent[] l() {
        return var_gg_vape_ui_click_component_GuiComponent_arr_g;
    }

    static {
        if (MMappedClassSlotFp.l() == null) {
            MMappedClassSlotFp.k(new GuiComponent[1]);
        }
    }
}

