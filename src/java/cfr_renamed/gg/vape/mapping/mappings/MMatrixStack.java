/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

public class MMatrixStack
extends Mapping {
    private MappingMethod var_gg_vape_mapping_MappingMethod_i;
    private MappingMethod var_gg_vape_mapping_MappingMethod_B;
    private static int[] var_int_arr_P;
    private MappingMethod var_gg_vape_mapping_MappingMethod_m;
    private MappingMethod var_gg_vape_mapping_MappingMethod_D;
    private MappingMethod var_gg_vape_mapping_MappingMethod_W;
    private MappingMethod var_gg_vape_mapping_MappingMethod_j;
    private MappingMethod var_gg_vape_mapping_MappingMethod_Q;
    private MappingMethod var_gg_vape_mapping_MappingMethod_M;
    private MappingMethod var_gg_vape_mapping_MappingMethod_Z;

    public MMatrixStack() {
        super(MappedClasses.DQ);
        Class[] classArray = new Class[]{};
        Class<Void> clazz = Void.TYPE;
        boolean bl = false;
        String string = "<init>";
        MMatrixStack mMatrixStack = this;
        this.var_gg_vape_mapping_MappingMethod_D = this.Y(string, bl, clazz, classArray);
        Class[] classArray2 = new Class[]{};
        Class clazz2 = MappedClasses.var_java_lang_Class_G;
        boolean bl2 = true;
        String string2 = "getLast";
        MMatrixStack mMatrixStack2 = this;
        this.var_gg_vape_mapping_MappingMethod_i = this.Y(string2, bl2, clazz2, classArray2);
        Class[] classArray3 = new Class[]{Float.TYPE, Float.TYPE, Float.TYPE};
        Class<Void> clazz3 = Void.TYPE;
        boolean bl3 = true;
        String string3 = "scale";
        MMatrixStack mMatrixStack3 = this;
        this.var_gg_vape_mapping_MappingMethod_Z = this.Y(string3, bl3, clazz3, classArray3);
        Class[] classArray4 = new Class[]{MappedClasses.qI};
        Class<Void> clazz4 = Void.TYPE;
        boolean bl4 = true;
        String string4 = "rotate";
        MMatrixStack mMatrixStack4 = this;
        this.var_gg_vape_mapping_MappingMethod_Q = this.Y(string4, bl4, clazz4, classArray4);
        Class[] classArray5 = new Class[]{Double.TYPE, Double.TYPE, Double.TYPE};
        Class<Void> clazz5 = Void.TYPE;
        boolean bl5 = true;
        String string5 = "translate";
        MMatrixStack mMatrixStack5 = this;
        this.var_gg_vape_mapping_MappingMethod_M = this.Y(string5, bl5, clazz5, classArray5);
        Class[] classArray6 = new Class[]{};
        Class<Void> clazz6 = Void.TYPE;
        boolean bl6 = true;
        String string6 = "push";
        MMatrixStack mMatrixStack6 = this;
        this.var_gg_vape_mapping_MappingMethod_m = this.Y(string6, bl6, clazz6, classArray6);
        Class[] classArray7 = new Class[]{};
        Class<Void> clazz7 = Void.TYPE;
        boolean bl7 = true;
        String string7 = "pop";
        MMatrixStack mMatrixStack7 = this;
        this.var_gg_vape_mapping_MappingMethod_B = this.Y(string7, bl7, clazz7, classArray7);
        if (MMatrixStack.l() != null) {
            if (ForgeVersion.MC_1_17.d()) {
                this.var_gg_vape_mapping_MappingMethod_j = ForgeVersion.MC_26_2.d() ? this.Y("mulPose", true, Void.TYPE, MappedClasses.ZA) : this.Y("mulPoseMatrix", true, Void.TYPE, MappedClasses.qr);
                Class[] classArray9 = new Class[]{};
                Class<Void> clazz9 = Void.TYPE;
                boolean bl9 = true;
                String string9 = "setIdentity";
                MMatrixStack mMatrixStack9 = this;
                this.var_gg_vape_mapping_MappingMethod_W = this.Y(string9, bl9, clazz9, classArray9);
            }
            if (GuiComponent.getLegacyComponentState() == null) {
                MMatrixStack.m(new int[3]);
            }
            return;
        }
        Class[] classArray10 = new Class[]{};
        Class<Void> clazz10 = Void.TYPE;
        boolean bl10 = true;
        String string10 = "setIdentity";
        MMatrixStack mMatrixStack10 = this;
        this.var_gg_vape_mapping_MappingMethod_W = this.Y(string10, bl10, clazz10, classArray10);
        if (GuiComponent.getLegacyComponentState() == null) {
            MMatrixStack.m(new int[3]);
        }
    }

    public static int[] l() {
        return var_int_arr_P;
    }

    public static void d(MMatrixStack mMatrixStack, Object object, double d, double d2, double d3) {
        mMatrixStack.p(object, d, d2, d3);
    }

    private void j(Object object) {
        this.var_gg_vape_mapping_MappingMethod_m.invokeVoidNoArgs(object);
    }

    private void p(Object object, double d, double d2, double d3) {
        this.var_gg_vape_mapping_MappingMethod_M.invokeVoid(object, d, d2, d3);
    }

    public static Object S(MMatrixStack mMatrixStack, Object object) {
        return mMatrixStack.q(object);
    }

    private void x(Object object, Object object2) {
        this.var_gg_vape_mapping_MappingMethod_j.invokeVoid(object, object2);
    }

    public static void u(MMatrixStack mMatrixStack, Object object) {
        mMatrixStack.g(object);
    }

    public static void b(MMatrixStack mMatrixStack, Object object) {
        mMatrixStack.h(object);
    }

    public static void r(MMatrixStack mMatrixStack, Object object, Object object2) {
        mMatrixStack.k(object, object2);
    }

    private void g(Object object) {
        this.var_gg_vape_mapping_MappingMethod_B.invokeVoidNoArgs(object);
    }

    public static void m(int[] nArray) {
        var_int_arr_P = nArray;
    }

    private Object t() {
        return this.var_gg_vape_mapping_MappingMethod_D.newInstance(new Object[0]);
    }

    private Object q(Object object) {
        return this.var_gg_vape_mapping_MappingMethod_i.invokeObject(object, new Object[0]);
    }

    private void k(Object object, Object object2) {
        this.var_gg_vape_mapping_MappingMethod_Q.invokeVoid(object, object2);
    }

    public static void o(MMatrixStack mMatrixStack, Object object, Object object2) {
        mMatrixStack.x(object, object2);
    }

    public static void o(MMatrixStack mMatrixStack, Object object) {
        mMatrixStack.j(object);
    }

    public static void o(MMatrixStack mMatrixStack, Object object, float f, float f2, float f3) {
        mMatrixStack.v(object, f, f2, f3);
    }

    private void v(Object object, float f, float f2, float f3) {
        this.var_gg_vape_mapping_MappingMethod_Z.invokeVoid(object, Float.valueOf(f), Float.valueOf(f2), Float.valueOf(f3));
    }

    public static Object U(MMatrixStack mMatrixStack) {
        return mMatrixStack.t();
    }

    private void h(Object object) {
        this.var_gg_vape_mapping_MappingMethod_W.invokeVoidNoArgs(object);
    }

    static {
        MMatrixStack.m(new int[3]);
    }
}

