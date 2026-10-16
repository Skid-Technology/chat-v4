/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.impl.ForgeVersion;

public class MRenderSystem
extends Mapping {
    private MappingMethod var_gg_vape_mapping_MappingMethod_X;
    private MappingMethod var_gg_vape_mapping_MappingMethod_P;
    private MappingField var_gg_vape_mapping_MappingField_U;
    private static int[] var_int_arr_x;
    public MappingMethod var_gg_vape_mapping_MappingMethod_v;
    private MappingMethod var_gg_vape_mapping_MappingMethod_S;
    private MappingMethod var_gg_vape_mapping_MappingMethod_B;
    private MappingMethod var_gg_vape_mapping_MappingMethod_M;
    private MappingField var_gg_vape_mapping_MappingField_p;
    private MappingMethod var_gg_vape_mapping_MappingMethod_N;
    private MappingMethod var_gg_vape_mapping_MappingMethod_C;
    private MappingMethod var_gg_vape_mapping_MappingMethod_Y;
    private MappingMethod var_gg_vape_mapping_MappingMethod_Q;
    private MappingMethod var_gg_vape_mapping_MappingMethod_a;
    private MappingMethod var_gg_vape_mapping_MappingMethod_f;
    private MappingMethod var_gg_vape_mapping_MappingMethod_h;
    private MappingMethod var_gg_vape_mapping_MappingMethod_V;
    private MappingMethod var_gg_vape_mapping_MappingMethod_w;

    public static void D(MRenderSystem mRenderSystem) {
        mRenderSystem.j();
    }

    public static void S(MRenderSystem mRenderSystem, float f, float f2, float f3, float f4) {
        mRenderSystem.D(f, f2, f3, f4);
    }

    public MRenderSystem() {
        this(MRenderSystem.K());
    }

    private MRenderSystem(int[] nArray) {
        super(MappedClasses.ls);
        MRenderSystem mRenderSystem;
        String string;
        Class[] classArray;
        int[] nArray2 = nArray;
        if (ForgeVersion.MC_1_17.d()) {
            boolean bl;
            Class<Void> clazz;
            if (ForgeVersion.MC_1_21_4.v()) {
                classArray = new Class[]{};
                clazz = Void.TYPE;
                bl = true;
                string = "applyModelViewMatrix";
                mRenderSystem = this;
                this.var_gg_vape_mapping_MappingMethod_N = mRenderSystem.registerStaticMethod(string, bl, clazz, classArray);
                Class<int[]> clazz2 = int[].class;
                boolean bl2 = false;
                String string2 = "f_157152_";
                MRenderSystem mRenderSystem2 = this;
                this.var_gg_vape_mapping_MappingField_U = this.registerStaticField(string2, bl2, clazz2);
            }
            if (ForgeVersion.MC_1_21_6.v()) {
                classArray = new Class[]{};
                clazz = Void.TYPE;
                bl = false;
                string = "m_69478_";
                mRenderSystem = this;
                this.var_gg_vape_mapping_MappingMethod_V = mRenderSystem.registerStaticMethod(string, bl, clazz, classArray);
                Class[] classArray2 = new Class[]{Integer.TYPE, MappedClasses.zC};
                Class<Void> clazz3 = Void.TYPE;
                boolean bl3 = false;
                String string3 = "m_157179_";
                MRenderSystem mRenderSystem3 = this;
                this.var_gg_vape_mapping_MappingMethod_X = this.registerStaticMethod(string3, bl3, clazz3, classArray2);
                Class[] classArray3 = new Class[]{Integer.TYPE, Integer.TYPE};
                Class<Void> clazz4 = Void.TYPE;
                boolean bl4 = false;
                String string4 = "m_69405_";
                MRenderSystem mRenderSystem4 = this;
                this.var_gg_vape_mapping_MappingMethod_Q = this.registerStaticMethod(string4, bl4, clazz4, classArray3);
                Class[] classArray4 = new Class[]{Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE};
                Class<Void> clazz5 = Void.TYPE;
                boolean bl5 = false;
                String string5 = "m_157429_";
                MRenderSystem mRenderSystem5 = this;
                this.var_gg_vape_mapping_MappingMethod_B = this.registerStaticMethod(string5, bl5, clazz5, classArray4);
                Class[] classArray5 = new Class[]{};
                Class<Void> clazz6 = Void.TYPE;
                boolean bl6 = false;
                String string6 = "m_69482_";
                MRenderSystem mRenderSystem6 = this;
                this.var_gg_vape_mapping_MappingMethod_C = this.registerStaticMethod(string6, bl6, clazz6, classArray5);
                Class[] classArray6 = new Class[]{};
                Class clazz7 = MappedClasses.qr;
                boolean bl7 = false;
                String string7 = "getProjectionMatrix";
                MRenderSystem mRenderSystem7 = this;
                this.var_gg_vape_mapping_MappingMethod_M = this.registerStaticMethod(string7, bl7, clazz7, classArray6);
                Class[] classArray7 = new Class[]{MappedClasses.qr, MappedClasses.VT};
                Class<Void> clazz8 = Void.TYPE;
                boolean bl8 = false;
                String string8 = "setProjectionMatrix";
                MRenderSystem mRenderSystem8 = this;
                this.var_gg_vape_mapping_MappingMethod_S = this.registerStaticMethod(string8, bl8, clazz8, classArray7);
                Class[] classArray8 = new Class[]{};
                Class<Void> clazz9 = Void.TYPE;
                boolean bl9 = false;
                String string9 = "disableBlend";
                MRenderSystem mRenderSystem9 = this;
                this.var_gg_vape_mapping_MappingMethod_Y = this.registerStaticMethod(string9, bl9, clazz9, classArray8);
                Class[] classArray9 = new Class[]{};
                Class<Void> clazz10 = Void.TYPE;
                boolean bl10 = false;
                String string10 = "disableDepthTest";
                MRenderSystem mRenderSystem10 = this;
                this.var_gg_vape_mapping_MappingMethod_h = this.registerStaticMethod(string10, bl10, clazz10, classArray9);
                Class[] classArray10 = new Class[]{};
                Class<Void> clazz11 = Void.TYPE;
                boolean bl11 = false;
                String string11 = "defaultBlendFunc";
                MRenderSystem mRenderSystem11 = this;
                this.var_gg_vape_mapping_MappingMethod_f = this.registerStaticMethod(string11, bl11, clazz11, classArray10);
            }
            Class clazz2 = MappedClasses.DQ;
            boolean bl2 = true;
            String string2 = "f_157141_";
            MRenderSystem mRenderSystem2 = this;
            this.var_gg_vape_mapping_MappingField_p = mRenderSystem2.registerStaticField(string2, bl2, clazz2);
            if (ForgeVersion.MC_1_21_11.v()) {
                Class[] classArray2 = new Class[]{Float.TYPE};
                Class<Void> clazz12 = Void.TYPE;
                boolean bl12 = false;
                String string12 = "lineWidth";
                MRenderSystem mRenderSystem12 = this;
                this.var_gg_vape_mapping_MappingMethod_P = this.registerStaticMethod(string12, bl12, clazz12, classArray2);
            }
            if (ForgeVersion.MC_26_2.d()) {
                this.var_gg_vape_mapping_MappingMethod_w = this.registerStaticMethod("getDevice", true, MappedClasses.GPU_DEVICE, new Class[0]);
            }
        } else {
            classArray = new Class[]{};
            Class<Void> clazz = Void.TYPE;
            boolean bl = false;
            string = "defaultAlphaFunc";
            mRenderSystem = this;
            this.var_gg_vape_mapping_MappingMethod_v = mRenderSystem.registerStaticMethod(string, bl, clazz, classArray);
        }
        if (ForgeVersion.MC_1_21_6.v()) {
            classArray = new Class[]{Float.TYPE};
            Class<Void> clazz = Void.TYPE;
            boolean bl = false;
            string = "fogDensity";
            mRenderSystem = this;
            this.var_gg_vape_mapping_MappingMethod_a = mRenderSystem.registerStaticMethod(string, bl, clazz, classArray);
        }
    }

    public static void h(MRenderSystem mRenderSystem) {
        mRenderSystem.x();
    }

    private void H(float f) {
        this.var_gg_vape_mapping_MappingMethod_a.invokeVoid(null, Float.valueOf(f));
    }

    private void y() {
        this.var_gg_vape_mapping_MappingMethod_Y.invokeVoidNoArgs(null);
    }

    public static void o(MRenderSystem mRenderSystem, int n, int n2) {
        mRenderSystem.D(n, n2);
    }

    public static void o(MRenderSystem mRenderSystem, int n, Object object) {
        mRenderSystem.G(n, object);
    }

    private int[] l() {
        return this.var_gg_vape_mapping_MappingField_U.getIntArray(null);
    }

    public static int[] K() {
        return var_int_arr_x;
    }

    public static void n(MRenderSystem mRenderSystem, int n, int n2) {
        mRenderSystem.J(n, n2);
    }

    private void F(float f) {
        this.var_gg_vape_mapping_MappingMethod_P.invokeVoid(null, Float.valueOf(f));
    }

    public static void f(MRenderSystem mRenderSystem) {
        mRenderSystem.I();
    }

    private void J(int n, int n2) {
        int[] nArray = this.l();
        nArray[n] = n2;
        this.var_gg_vape_mapping_MappingField_U.setIntArray(null, nArray);
    }

    private void I() {
        this.var_gg_vape_mapping_MappingMethod_f.invokeVoidNoArgs(null);
    }

    public static int[] x(MRenderSystem mRenderSystem) {
        return mRenderSystem.l();
    }

    private void j() {
        this.var_gg_vape_mapping_MappingMethod_C.invokeVoidNoArgs(null);
    }

    public static void F(MRenderSystem mRenderSystem, float f) {
        mRenderSystem.F(f);
    }

    private void G(int n, Object object) {
        this.var_gg_vape_mapping_MappingMethod_X.invokeVoid(n, object);
    }

    public static void U(MRenderSystem mRenderSystem) {
        mRenderSystem.K$src$V$1mimvos();
    }

    private void D(int n, int n2) {
        this.var_gg_vape_mapping_MappingMethod_Q.invokeVoid(null, n, n2);
    }

    private void K$src$V$1mimvos() {
        this.var_gg_vape_mapping_MappingMethod_N.invokeVoidNoArgs(null);
    }

    private void D(float f, float f2, float f3, float f4) {
        this.var_gg_vape_mapping_MappingMethod_B.invokeVoid(null, Float.valueOf(f), Float.valueOf(f2), Float.valueOf(f3), Float.valueOf(f4));
    }

    private Object F() {
        return this.var_gg_vape_mapping_MappingMethod_M.invokeObject(null, new Object[0]);
    }

    public static void c(MRenderSystem mRenderSystem) {
        mRenderSystem.y();
    }

    private void x() {
        this.var_gg_vape_mapping_MappingMethod_h.invokeVoidNoArgs(null);
    }

    public Object P() {
        return this.var_gg_vape_mapping_MappingField_p.getObject(null);
    }

    public static void D(int[] nArray) {
        var_int_arr_x = nArray;
    }

    public static void j(MRenderSystem mRenderSystem) {
        mRenderSystem.S();
    }

    private void S() {
        this.var_gg_vape_mapping_MappingMethod_V.invokeVoidNoArgs(null);
    }

    public static void Z(MRenderSystem mRenderSystem, float f) {
        mRenderSystem.H(f);
    }

    private void i(Object object, Object object2) {
        this.var_gg_vape_mapping_MappingMethod_S.invokeVoid(null, object, object2);
    }

    public Object getDevice() {
        if (this.var_gg_vape_mapping_MappingMethod_w == null) {
            return null;
        }
        return this.var_gg_vape_mapping_MappingMethod_w.invokeObject(null, new Object[0]);
    }

    public static Object b(MRenderSystem mRenderSystem) {
        return mRenderSystem.getDevice();
    }

    static {
        MRenderSystem.D((int[])null);
    }
}

