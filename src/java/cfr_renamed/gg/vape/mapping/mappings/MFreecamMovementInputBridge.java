/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;

public class MFreecamMovementInputBridge
extends Mapping {
    private MappingField var_gg_vape_mapping_MappingField_A;
    private static GuiComponent[] var_gg_vape_ui_click_component_GuiComponent_arr_n;
    private MappingField var_gg_vape_mapping_MappingField_i;
    private MappingField var_gg_vape_mapping_MappingField_o;
    private MappingField var_gg_vape_mapping_MappingField_h;
    private MappingField var_gg_vape_mapping_MappingField_V;
    private MappingField var_gg_vape_mapping_MappingField_z;
    private MappingMethod var_gg_vape_mapping_MappingMethod_l;
    private MappingField var_gg_vape_mapping_MappingField_r;

    public void K(Object object, boolean bl) {
        this.var_gg_vape_mapping_MappingField_A.setBoolean(object, bl);
    }

    public MFreecamMovementInputBridge() {
        super(MappedClasses.qn);
        Class<Boolean> clazz = Boolean.TYPE;
        boolean bl = true;
        String string = "forward";
        MFreecamMovementInputBridge mFreecamMovementInputBridge = this;
        this.var_gg_vape_mapping_MappingField_z = this.J(string, bl, clazz);
        Class<Boolean> clazz2 = Boolean.TYPE;
        boolean bl2 = true;
        String string2 = "backward";
        MFreecamMovementInputBridge mFreecamMovementInputBridge2 = this;
        this.var_gg_vape_mapping_MappingField_r = this.J(string2, bl2, clazz2);
        Class<Boolean> clazz3 = Boolean.TYPE;
        boolean bl3 = true;
        String string3 = "left";
        MFreecamMovementInputBridge mFreecamMovementInputBridge3 = this;
        this.var_gg_vape_mapping_MappingField_V = this.J(string3, bl3, clazz3);
        Class<Boolean> clazz4 = Boolean.TYPE;
        boolean bl4 = true;
        String string4 = "right";
        MFreecamMovementInputBridge mFreecamMovementInputBridge4 = this;
        this.var_gg_vape_mapping_MappingField_i = this.J(string4, bl4, clazz4);
        Class<Boolean> clazz5 = Boolean.TYPE;
        boolean bl5 = true;
        String string5 = "jump";
        MFreecamMovementInputBridge mFreecamMovementInputBridge5 = this;
        this.var_gg_vape_mapping_MappingField_h = this.J(string5, bl5, clazz5);
        GuiComponent[] guiComponentArray = MFreecamMovementInputBridge.x();
        Class<Boolean> clazz6 = Boolean.TYPE;
        boolean bl6 = true;
        String string6 = "shift";
        MFreecamMovementInputBridge mFreecamMovementInputBridge6 = this;
        this.var_gg_vape_mapping_MappingField_o = this.J(string6, bl6, clazz6);
        Class<Boolean> clazz7 = Boolean.TYPE;
        boolean bl7 = true;
        String string7 = "sprint";
        MFreecamMovementInputBridge mFreecamMovementInputBridge7 = this;
        this.var_gg_vape_mapping_MappingField_A = this.J(string7, bl7, clazz7);
        Class[] classArray = new Class[]{Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE};
        MFreecamMovementInputBridge mFreecamMovementInputBridge8 = this;
        this.var_gg_vape_mapping_MappingMethod_l = this.registerConstructor(classArray);
    }

    public boolean Y(Object object) {
        return this.var_gg_vape_mapping_MappingField_A.getBoolean(object);
    }

    public boolean r(Object object) {
        return this.var_gg_vape_mapping_MappingField_o.getBoolean(object);
    }

    public static GuiComponent[] x() {
        return var_gg_vape_ui_click_component_GuiComponent_arr_n;
    }

    public void V(Object object, boolean bl) {
        this.var_gg_vape_mapping_MappingField_o.setBoolean(object, bl);
    }

    public static void B(GuiComponent[] guiComponentArray) {
        var_gg_vape_ui_click_component_GuiComponent_arr_n = guiComponentArray;
    }

    public void A(Object object, boolean bl) {
        this.var_gg_vape_mapping_MappingField_h.setBoolean(object, bl);
    }

    public boolean A(Object object) {
        return this.var_gg_vape_mapping_MappingField_i.getBoolean(object);
    }

    public boolean H(Object object) {
        return this.var_gg_vape_mapping_MappingField_z.getBoolean(object);
    }

    public boolean K(Object object) {
        return this.var_gg_vape_mapping_MappingField_V.getBoolean(object);
    }

    public boolean W(Object object) {
        return this.var_gg_vape_mapping_MappingField_r.getBoolean(object);
    }

    public Object z(boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, boolean bl6, boolean bl7) {
        return this.var_gg_vape_mapping_MappingMethod_l.newInstance(bl, bl2, bl3, bl4, bl5, bl6, bl7);
    }

    public boolean p(Object object) {
        return this.var_gg_vape_mapping_MappingField_h.getBoolean(object);
    }

    static {
        MFreecamMovementInputBridge.B(new GuiComponent[3]);
    }
}

