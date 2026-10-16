/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.ScaledResolution;

public class MGuiScreen
extends Mapping {
    private MappingField var_gg_vape_mapping_MappingField_e;
    private final MappingField var_gg_vape_mapping_MappingField_Z;
    private final MappingField var_gg_vape_mapping_MappingField_D;

    private int f(Object object) {
        return this.var_gg_vape_mapping_MappingField_Z.getInt(object);
    }

    private int A(Object object) {
        return this.var_gg_vape_mapping_MappingField_D.getInt(object);
    }

    public Object S(Object object) {
        return this.var_gg_vape_mapping_MappingField_e.getObject(object);
    }

    public MGuiScreen() {
        this(ScaledResolution.q());
    }

    private MGuiScreen(int n) {
        super(MappedClasses.VW);
        if (n != 0) {
            Class<Integer> clazz = Integer.TYPE;
            boolean bl = true;
            String string = "width";
            MGuiScreen mGuiScreen = this;
            this.var_gg_vape_mapping_MappingField_Z = mGuiScreen.J(string, bl, clazz);
            Class<Integer> clazz2 = Integer.TYPE;
            boolean bl2 = true;
            String string2 = "height";
            MGuiScreen mGuiScreen2 = this;
            this.var_gg_vape_mapping_MappingField_e = this.J(string2, bl2, clazz2);
            this.var_gg_vape_mapping_MappingField_D = null;
            return;
        }
        Class<Integer> clazz = Integer.TYPE;
        boolean bl = true;
        String string = "width";
        MGuiScreen mGuiScreen = this;
        this.var_gg_vape_mapping_MappingField_Z = mGuiScreen.J(string, bl, clazz);
        Class<Integer> clazz3 = Integer.TYPE;
        boolean bl3 = true;
        String string3 = "height";
        MGuiScreen mGuiScreen3 = this;
        this.var_gg_vape_mapping_MappingField_D = this.J(string3, bl3, clazz3);
        if (ForgeVersion.MC_1_16_5.d()) {
            Class clazz4 = MappedClasses.Yr;
            boolean bl4 = true;
            String string4 = "title";
            MGuiScreen mGuiScreen4 = this;
            this.var_gg_vape_mapping_MappingField_e = this.J(string4, bl4, clazz4);
        }
    }

    public static int U(MGuiScreen mGuiScreen, Object object) {
        return mGuiScreen.f(object);
    }

    public static int D(MGuiScreen mGuiScreen, Object object) {
        return mGuiScreen.A(object);
    }
}

