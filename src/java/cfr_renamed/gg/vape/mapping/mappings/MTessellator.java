/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MEntityRenderer;
import gg.vape.wrapper.impl.ForgeVersion;

public class MTessellator
extends Mapping {
    private MappingMethod var_gg_vape_mapping_MappingMethod_e;
    private MappingMethod var_gg_vape_mapping_MappingMethod_Z;
    private MappingMethod var_gg_vape_mapping_MappingMethod_w;
    private MappingMethod var_gg_vape_mapping_MappingMethod_D;
    private MappingField var_gg_vape_mapping_MappingField_A;
    private MappingMethod var_gg_vape_mapping_MappingMethod_N;
    private final MappingField var_gg_vape_mapping_MappingField_i;
    private MappingMethod var_gg_vape_mapping_MappingMethod_O;
    private MappingField var_gg_vape_mapping_MappingField_u;

    private void u(Object object) {
        this.var_gg_vape_mapping_MappingMethod_Z.invokeVoidNoArgs(object);
    }

    public static void z(MTessellator mTessellator, Object object) {
        mTessellator.c(object);
    }

    public void w(Object object, int n, int n2, int n3, int n4) {
        this.var_gg_vape_mapping_MappingMethod_N.invokeVoid(object, n, n2, n3, n4);
    }

    public static Object a(MTessellator mTessellator) {
        return mTessellator.a();
    }

    private void O(Object object, double d, double d2, double d3, double d4, double d5) {
        this.var_gg_vape_mapping_MappingMethod_O.invokeVoid(object, d, d2, d3, d4, d5);
    }

    private void C(Object object, int n) {
        this.var_gg_vape_mapping_MappingMethod_D.invokeVoid(object, n);
    }

    private void Z(Object object, double d, double d2, double d3) {
        this.var_gg_vape_mapping_MappingMethod_w.invokeVoid(object, d, d2, d3);
    }

    private Object Z(Object object) {
        return this.var_gg_vape_mapping_MappingField_u.getObject(object);
    }

    public boolean K(Object object) {
        return this.var_gg_vape_mapping_MappingField_A.getBoolean(object);
    }

    private Object a() {
        return this.var_gg_vape_mapping_MappingField_i.getObject(null);
    }

    public static void v(MTessellator mTessellator, Object object, double d, double d2, double d3) {
        mTessellator.Z(object, d, d2, d3);
    }

    public static Object W(MTessellator mTessellator, Object object) {
        return mTessellator.Z(object);
    }

    public boolean E$src$Z$1p5wubj() {
        return this.var_gg_vape_mapping_MappingField_A != null && !this.var_gg_vape_mapping_MappingField_A.hasResolutionFailed();
    }

    public static void k(MTessellator mTessellator, Object object, int n) {
        mTessellator.C(object, n);
    }

    private void c(Object object) {
        this.var_gg_vape_mapping_MappingMethod_e.invokeVoidNoArgs(object);
    }

    public static void Y(MTessellator mTessellator, Object object) {
        mTessellator.u(object);
    }

    public MTessellator() {
        this(MEntityRenderer.n());
    }

    private MTessellator(int n) {
        super(MappedClasses.ZX);
        String string;
        MTessellator mTessellator;
        String string2;
        boolean bl;
        Class clazz;
        int n2 = n;
        if (ForgeVersion.MC_1_12_2.d()) {
            clazz = MappedClasses.ZX;
            bl = true;
            string2 = "INSTANCE";
            mTessellator = this;
            this.var_gg_vape_mapping_MappingField_i = mTessellator.registerStaticField(string2, bl, clazz);
        } else {
            clazz = MappedClasses.ZX;
            bl = true;
            string2 = "instance";
            mTessellator = this;
            this.var_gg_vape_mapping_MappingField_i = mTessellator.registerStaticField(string2, bl, clazz);
        }
        if (ForgeVersion.MC_1_7_10.Y()) {
            if (ForgeVersion.MC_1_21_0.H().y()) {
                if (ForgeVersion.MC_1_16_5.d()) {
                    clazz = MappedClasses.lX;
                    bl = true;
                    string2 = "buffer";
                    mTessellator = this;
                    this.var_gg_vape_mapping_MappingField_u = mTessellator.J(string2, bl, clazz);
                } else {
                    clazz = MappedClasses.lX;
                    bl = true;
                    string2 = ForgeVersion.c() >= 23 ? "buffer" : "worldRenderer";
                    mTessellator = this;
                    this.var_gg_vape_mapping_MappingField_u = mTessellator.J(string2, bl, clazz);
                }
                Class[] classArray = new Class[]{};
                Class<Void> clazz2 = Void.TYPE;
                boolean bl2 = true;
                string = "draw";
                MTessellator mTessellator2 = this;
                this.var_gg_vape_mapping_MappingMethod_e = mTessellator2.Y(string, bl2, clazz2, classArray);
            }
        } else {
            clazz = Boolean.TYPE;
            bl = true;
            boolean bl2 = true;
            string = "renderingChunk";
            MTessellator mTessellator3 = this;
            this.var_gg_vape_mapping_MappingField_A = mTessellator3.registerInstanceFieldWithSecondaryFlag(string, bl2, bl, clazz);
            Class[] classArray = new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE};
            Class<Void> clazz2 = Void.TYPE;
            boolean bl3 = true;
            String string22 = "setColorRGBA";
            MTessellator mTessellator2 = this;
            this.var_gg_vape_mapping_MappingMethod_N = this.Y(string22, bl3, clazz2, classArray);
            Class[] classArray2 = new Class[]{};
            Class<Void> clazz3 = Void.TYPE;
            boolean bl4 = true;
            String string3 = "startDrawingQuads";
            MTessellator mTessellator32 = this;
            this.var_gg_vape_mapping_MappingMethod_Z = this.Y(string3, bl4, clazz3, classArray2);
            Class[] classArray3 = new Class[]{Double.TYPE, Double.TYPE, Double.TYPE};
            Class<Void> clazz4 = Void.TYPE;
            boolean bl5 = true;
            String string4 = "addVertex";
            MTessellator mTessellator4 = this;
            this.var_gg_vape_mapping_MappingMethod_w = this.Y(string4, bl5, clazz4, classArray3);
            Class[] classArray4 = new Class[]{};
            Class<Integer> clazz5 = Integer.TYPE;
            boolean bl6 = true;
            String string5 = "draw";
            MTessellator mTessellator5 = this;
            this.var_gg_vape_mapping_MappingMethod_e = this.Y(string5, bl6, clazz5, classArray4);
            Class[] classArray5 = new Class[]{Integer.TYPE};
            Class<Void> clazz6 = Void.TYPE;
            boolean bl7 = true;
            String string6 = "startDrawing";
            MTessellator mTessellator6 = this;
            this.var_gg_vape_mapping_MappingMethod_D = this.Y(string6, bl7, clazz6, classArray5);
            Class[] classArray6 = new Class[]{Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE};
            Class<Void> clazz7 = Void.TYPE;
            boolean bl8 = true;
            String string7 = "addVertexWithUV";
            MTessellator mTessellator7 = this;
            this.var_gg_vape_mapping_MappingMethod_O = this.Y(string7, bl8, clazz7, classArray6);
        }
    }

    public static void E(MTessellator mTessellator, Object object, double d, double d2, double d3, double d4, double d5) {
        mTessellator.O(object, d, d2, d3, d4, d5);
    }
}

