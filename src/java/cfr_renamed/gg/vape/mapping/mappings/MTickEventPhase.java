/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.ui.click.component.GuiComponent;

public class MTickEventPhase
extends Mapping {
    private MappingField var_gg_vape_mapping_MappingField_h;
    private static GuiComponent[] var_gg_vape_ui_click_component_GuiComponent_arr_N;
    private MappingField var_gg_vape_mapping_MappingField_y;

    public static void m(GuiComponent[] guiComponentArray) {
        var_gg_vape_ui_click_component_GuiComponent_arr_N = guiComponentArray;
    }

    public static Object B(MTickEventPhase mTickEventPhase) {
        return mTickEventPhase.h();
    }

    private Object p() {
        return this.var_gg_vape_mapping_MappingField_y.getObject(null);
    }

    public static Object G(MTickEventPhase mTickEventPhase) {
        return mTickEventPhase.p();
    }

    public static GuiComponent[] P() {
        return var_gg_vape_ui_click_component_GuiComponent_arr_N;
    }

    public MTickEventPhase() {
        this(MTickEventPhase.P());
    }

    private MTickEventPhase(GuiComponent[] guiComponentArray) {
        super(MappedClasses.VT);
        GuiComponent[] guiComponentArray2 = guiComponentArray;
        Class clazz = MappedClasses.VT;
        boolean bl = true;
        String string = "DISTANCE_TO_ORIGIN";
        MTickEventPhase mTickEventPhase = this;
        this.var_gg_vape_mapping_MappingField_y = this.registerStaticField(string, bl, clazz);
        Class clazz2 = MappedClasses.VT;
        boolean bl2 = true;
        String string2 = "ORTHOGRAPHIC_Z";
        MTickEventPhase mTickEventPhase2 = this;
        this.var_gg_vape_mapping_MappingField_h = this.registerStaticField(string2, bl2, clazz2);
        if (GuiComponent.getLegacyComponentState() == null) {
            MTickEventPhase.m(new GuiComponent[2]);
        }
    }

    private Object h() {
        return this.var_gg_vape_mapping_MappingField_h.getObject(null);
    }

    static {
        MTickEventPhase.m(null);
    }
}

