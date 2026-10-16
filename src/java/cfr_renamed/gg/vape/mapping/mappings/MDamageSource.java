/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingFieldBuilder;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

public class MDamageSource
extends Mapping {
    private final MappingField var_gg_vape_mapping_MappingField_A;
    private MappingField var_gg_vape_mapping_MappingField_z;
    private MappingField var_gg_vape_mapping_MappingField_Y;
    private final MappingField var_gg_vape_mapping_MappingField_a;
    private static int[] var_int_arr_f;

    public static void f(int[] nArray) {
        var_int_arr_f = nArray;
    }

    public static Object s(MDamageSource mDamageSource) {
        return mDamageSource.L();
    }

    public static int[] r() {
        return var_int_arr_f;
    }

    private Object L() {
        return this.var_gg_vape_mapping_MappingField_Y.getObject(null);
    }

    public MDamageSource() {
        this(MDamageSource.r());
    }

    private MDamageSource(int[] nArray) {
        super(MappedClasses.DW);
        if (nArray != null) {
            Class clazz = MappedClasses.Di;
            boolean bl = true;
            String string = "potionitem";
            MDamageSource mDamageSource = this;
            this.var_gg_vape_mapping_MappingField_a = mDamageSource.registerStaticField(string, bl, clazz);
            if (GuiComponent.getLegacyComponentState() == null) {
                MDamageSource.f(new int[1]);
            }
            this.var_gg_vape_mapping_MappingField_A = null;
            return;
        }
        if (ForgeVersion.MC_1_12_2.d()) {
            Class clazz = MappedClasses.lb;
            boolean bl = true;
            String string = "WOODEN_SWORD";
            MDamageSource mDamageSource = this;
            this.var_gg_vape_mapping_MappingField_A = mDamageSource.registerStaticField(string, bl, clazz);
            if (ForgeVersion.MC_1_16_5.d()) {
                Class clazz2 = MappedClasses.lb;
                boolean bl2 = true;
                String string2 = "POTION";
                MDamageSource mDamageSource2 = this;
                this.var_gg_vape_mapping_MappingField_a = this.registerStaticField(string2, bl2, clazz2);
            } else {
                Class clazz3 = MappedClasses.Di;
                boolean bl3 = true;
                String string3 = "POTIONITEM";
                MDamageSource mDamageSource3 = this;
                this.var_gg_vape_mapping_MappingField_a = this.registerStaticField(string3, bl3, clazz3);
            }
            Class clazz4 = MappedClasses.lb;
            String string4 = "END_CRYSTAL";
            MDamageSource mDamageSource4 = this;
            this.var_gg_vape_mapping_MappingField_z = ((MappingFieldBuilder)this.fieldBuilder(string4, clazz4).setStaticMember(true)).buildField();
            Class clazz5 = MappedClasses.lb;
            String string5 = "TOTEM_OF_UNDYING";
            MDamageSource mDamageSource5 = this;
            this.var_gg_vape_mapping_MappingField_Y = ((MappingFieldBuilder)this.fieldBuilder(string5, clazz5).setStaticMember(true)).buildField();
        } else {
            Class clazz = MappedClasses.lb;
            boolean bl = true;
            String string = "wooden_sword";
            MDamageSource mDamageSource = this;
            this.var_gg_vape_mapping_MappingField_A = mDamageSource.registerStaticField(string, bl, clazz);
            Class clazz6 = MappedClasses.Di;
            boolean bl4 = true;
            String string6 = "potionitem";
            MDamageSource mDamageSource6 = this;
            this.var_gg_vape_mapping_MappingField_a = this.registerStaticField(string6, bl4, clazz6);
        }
        if (GuiComponent.getLegacyComponentState() == null) {
            MDamageSource.f(new int[1]);
        }
    }

    private Object M() {
        return this.var_gg_vape_mapping_MappingField_A.getObject(null);
    }

    public static Object D(MDamageSource mDamageSource) {
        return mDamageSource.W();
    }

    private Object Q() {
        return this.var_gg_vape_mapping_MappingField_z.getObject(null);
    }

    public static Object I(MDamageSource mDamageSource) {
        return mDamageSource.M();
    }

    public static Object T(MDamageSource mDamageSource) {
        return mDamageSource.Q();
    }

    private Object W() {
        return this.var_gg_vape_mapping_MappingField_a.getObject(null);
    }

    static {
        MDamageSource.f(null);
    }
}

