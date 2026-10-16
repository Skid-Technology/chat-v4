/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MEntityRenderer;
import gg.vape.wrapper.impl.ForgeVersion;

public class MRenderHelper
extends Mapping {
    private MappingMethod var_gg_vape_mapping_MappingMethod_Y;
    private MappingMethod var_gg_vape_mapping_MappingMethod_E;
    private MappingMethod var_gg_vape_mapping_MappingMethod_h;

    private void I() {
        this.var_gg_vape_mapping_MappingMethod_h.invokeVoidNoArgs(null);
    }

    private void Z() {
        this.var_gg_vape_mapping_MappingMethod_Y.invokeVoidNoArgs(null);
    }

    private void W() {
        this.var_gg_vape_mapping_MappingMethod_E.invokeVoidNoArgs(null);
    }

    public static void L(MRenderHelper mRenderHelper) {
        mRenderHelper.I();
    }

    public static void Q(MRenderHelper mRenderHelper) {
        mRenderHelper.W();
    }

    public static void M(MRenderHelper mRenderHelper) {
        mRenderHelper.Z();
    }

    public MRenderHelper() {
        this(MEntityRenderer.X());
    }

    private MRenderHelper(int n) {
        super(MappedClasses.ql);
        MRenderHelper mRenderHelper;
        String string;
        boolean bl;
        Class<Void> clazz;
        Class[] classArray;
        int n2 = n;
        if (ForgeVersion.MC_1_17.v()) {
            classArray = new Class[]{};
            clazz = Void.TYPE;
            bl = true;
            string = "disableStandardItemLighting";
            mRenderHelper = this;
            this.var_gg_vape_mapping_MappingMethod_h = mRenderHelper.registerStaticMethod(string, bl, clazz, classArray);
            Class[] classArray2 = new Class[]{};
            Class<Void> clazz2 = Void.TYPE;
            boolean bl2 = true;
            String string2 = "enableStandardItemLighting";
            MRenderHelper mRenderHelper2 = this;
            this.var_gg_vape_mapping_MappingMethod_E = this.registerStaticMethod(string2, bl2, clazz2, classArray2);
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            if (ForgeVersion.MC_1_17.d() && ForgeVersion.MC_1_21_6.v()) {
                classArray = new Class[]{};
                clazz = Void.TYPE;
                bl = true;
                string = "setupForFlatItems";
                mRenderHelper = this;
                this.var_gg_vape_mapping_MappingMethod_Y = mRenderHelper.registerStaticMethod(string, bl, clazz, classArray);
                Class[] classArray3 = new Class[]{};
                Class<Void> clazz3 = Void.TYPE;
                boolean bl3 = true;
                String string3 = "setupFor3DItems";
                MRenderHelper mRenderHelper3 = this;
                this.var_gg_vape_mapping_MappingMethod_E = this.registerStaticMethod(string3, bl3, clazz3, classArray3);
            }
        } else {
            classArray = new Class[]{};
            clazz = Void.TYPE;
            bl = true;
            string = "enableGUIStandardItemLighting";
            mRenderHelper = this;
            this.var_gg_vape_mapping_MappingMethod_Y = mRenderHelper.registerStaticMethod(string, bl, clazz, classArray);
        }
    }
}

