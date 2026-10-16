/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.mappings.MSlot;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

public class MEquipmentSlotSet
extends Mapping {
    private MappingField var_gg_vape_mapping_MappingField_o;
    private MappingField var_gg_vape_mapping_MappingField_a;
    private MappingField var_gg_vape_mapping_MappingField_d;
    private MappingField var_gg_vape_mapping_MappingField_n;
    private MappingField var_gg_vape_mapping_MappingField_L;
    private MappingField var_gg_vape_mapping_MappingField_b;
    private MappingField var_gg_vape_mapping_MappingField_x;
    private MappingField var_gg_vape_mapping_MappingField_D;

    private Object A(Object object) {
        return this.var_gg_vape_mapping_MappingField_a.getObject(object);
    }

    public static Object a(MEquipmentSlotSet mEquipmentSlotSet, Object object) {
        return mEquipmentSlotSet.A(object);
    }

    private Object r() {
        return this.var_gg_vape_mapping_MappingField_x.getObject(null);
    }

    public static MappingField t(MEquipmentSlotSet mEquipmentSlotSet) {
        return mEquipmentSlotSet.var_gg_vape_mapping_MappingField_d;
    }

    private Object B() {
        return this.var_gg_vape_mapping_MappingField_D.getObject(null);
    }

    public static MappingField P(MEquipmentSlotSet mEquipmentSlotSet) {
        return mEquipmentSlotSet.var_gg_vape_mapping_MappingField_D;
    }

    private Object b() {
        return this.var_gg_vape_mapping_MappingField_n.getObject(null);
    }

    public static MappingField e(MEquipmentSlotSet mEquipmentSlotSet) {
        return mEquipmentSlotSet.var_gg_vape_mapping_MappingField_b;
    }

    public static MappingField s(MEquipmentSlotSet mEquipmentSlotSet) {
        return mEquipmentSlotSet.var_gg_vape_mapping_MappingField_o;
    }

    private Object x() {
        return this.var_gg_vape_mapping_MappingField_o.getObject(null);
    }

    public static MappingField T(MEquipmentSlotSet mEquipmentSlotSet) {
        return mEquipmentSlotSet.var_gg_vape_mapping_MappingField_x;
    }

    private int E(Object object) {
        return this.var_gg_vape_mapping_MappingField_L.getInt(object);
    }

    private Object c() {
        return this.var_gg_vape_mapping_MappingField_d.getObject(null);
    }

    public MEquipmentSlotSet() {
        super(MappedClasses.um);
        Class clazz = MappedClasses.um;
        boolean bl = true;
        String string = "MAINHAND";
        MEquipmentSlotSet mEquipmentSlotSet = this;
        this.var_gg_vape_mapping_MappingField_n = this.registerStaticField(string, bl, clazz);
        Class clazz2 = MappedClasses.um;
        boolean bl2 = true;
        String string2 = "OFFHAND";
        MEquipmentSlotSet mEquipmentSlotSet2 = this;
        this.var_gg_vape_mapping_MappingField_x = this.registerStaticField(string2, bl2, clazz2);
        Class clazz3 = MappedClasses.um;
        boolean bl3 = true;
        String string3 = "FEET";
        MEquipmentSlotSet mEquipmentSlotSet3 = this;
        this.var_gg_vape_mapping_MappingField_D = this.registerStaticField(string3, bl3, clazz3);
        Class clazz4 = MappedClasses.um;
        boolean bl4 = true;
        String string4 = "LEGS";
        MEquipmentSlotSet mEquipmentSlotSet4 = this;
        this.var_gg_vape_mapping_MappingField_o = this.registerStaticField(string4, bl4, clazz4);
        Class clazz5 = MappedClasses.um;
        boolean bl5 = true;
        String string5 = "CHEST";
        MEquipmentSlotSet mEquipmentSlotSet5 = this;
        this.var_gg_vape_mapping_MappingField_b = this.registerStaticField(string5, bl5, clazz5);
        if (MSlot.getSlotControlFlowState() != null) {
            Class clazz6 = MappedClasses.um;
            boolean bl6 = true;
            String string6 = "HEAD";
            MEquipmentSlotSet mEquipmentSlotSet6 = this;
            this.var_gg_vape_mapping_MappingField_d = this.registerStaticField(string6, bl6, clazz6);
            Class clazz7 = MappedClasses.Vf;
            boolean bl7 = true;
            String string7 = "type";
            MEquipmentSlotSet mEquipmentSlotSet7 = this;
            this.var_gg_vape_mapping_MappingField_a = this.J(string7, bl7, clazz7);
            if (GuiComponent.getLegacyComponentState() == null) {
                MSlot.setSlotControlFlowState(new int[5]);
            }
            return;
        }
        Class clazz8 = MappedClasses.um;
        boolean bl8 = true;
        String string8 = "HEAD";
        MEquipmentSlotSet mEquipmentSlotSet8 = this;
        this.var_gg_vape_mapping_MappingField_d = this.registerStaticField(string8, bl8, clazz8);
        if (ForgeVersion.MC_1_21_6.d()) {
            Class<Integer> clazz9 = Integer.TYPE;
            boolean bl9 = true;
            String string9 = "id";
            MEquipmentSlotSet mEquipmentSlotSet9 = this;
            this.var_gg_vape_mapping_MappingField_L = this.J(string9, bl9, clazz9);
            Class clazz10 = MappedClasses.Vf;
            boolean bl10 = true;
            String string10 = "type";
            MEquipmentSlotSet mEquipmentSlotSet10 = this;
            this.var_gg_vape_mapping_MappingField_a = this.J(string10, bl10, clazz10);
        }
        if (GuiComponent.getLegacyComponentState() == null) {
            MSlot.setSlotControlFlowState(new int[5]);
        }
    }

    public static int e(MEquipmentSlotSet mEquipmentSlotSet, Object object) {
        return mEquipmentSlotSet.E(object);
    }

    public static MappingField r(MEquipmentSlotSet mEquipmentSlotSet) {
        return mEquipmentSlotSet.var_gg_vape_mapping_MappingField_n;
    }

    private Object j() {
        return this.var_gg_vape_mapping_MappingField_b.getObject(null);
    }
}

