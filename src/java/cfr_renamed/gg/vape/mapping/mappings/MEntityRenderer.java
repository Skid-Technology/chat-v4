/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.Vape;
import gg.vape.asm.helper.DescUtils;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.MappingMethodBuilder;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MEntityRenderer
extends Mapping {
    private MappingMethod var_gg_vape_mapping_MappingMethod_g;
    private MappingField var_gg_vape_mapping_MappingField_K;
    final public MappingMethod var_gg_vape_mapping_MappingMethod_k;
    private MappingField var_gg_vape_mapping_MappingField_q;
    public MappingMethod var_gg_vape_mapping_MappingMethod_V;
    private MappingMethod var_gg_vape_mapping_MappingMethod_m;
    private MappingField var_gg_vape_mapping_MappingField_c;
    private MappingField fogRendererField;
    private MappingField var_gg_vape_mapping_MappingField_f;
    public MappingMethod var_gg_vape_mapping_MappingMethod_C;
    public MappingMethod var_gg_vape_mapping_MappingMethod_H;
    public MappingMethod var_gg_vape_mapping_MappingMethod_x;
    public MappingMethod extractRenderStateMethod;
    final public MappingMethod var_gg_vape_mapping_MappingMethod_M;
    private MappingField var_gg_vape_mapping_MappingField_N;
    private MappingField var_gg_vape_mapping_MappingField_X;
    public MappingMethod var_gg_vape_mapping_MappingMethod_i;
    private MappingField var_gg_vape_mapping_MappingField_d;
    public MappingMethod var_gg_vape_mapping_MappingMethod_L;
    private MappingField var_gg_vape_mapping_MappingField_u;
    private static int var_int_s;
    final public MappingMethod var_gg_vape_mapping_MappingMethod_J;
    private final MappingField var_gg_vape_mapping_MappingField_j;
    private MappingField var_gg_vape_mapping_MappingField_G;
    private MappingField var_gg_vape_mapping_MappingField_Z;
    private MappingMethod var_gg_vape_mapping_MappingMethod_l;
    public MappingMethod var_gg_vape_mapping_MappingMethod_e;
    private MappingMethod var_gg_vape_mapping_MappingMethod_p;
    private MappingField var_gg_vape_mapping_MappingField_v;
    private final MappingField var_gg_vape_mapping_MappingField_D;
    private MappingField var_gg_vape_mapping_MappingField_P;
    private MappingField var_gg_vape_mapping_MappingField_U;
    private MappingField var_gg_vape_mapping_MappingField_z;
    private MappingField var_gg_vape_mapping_MappingField_n;
    private MappingMethod var_gg_vape_mapping_MappingMethod_Q;
    public MappingMethod var_gg_vape_mapping_MappingMethod_W;
    private MappingField var_gg_vape_mapping_MappingField_h;
    public MappingMethod var_gg_vape_mapping_MappingMethod_B;
    public MappingMethod var_gg_vape_mapping_MappingMethod_a;
    private MappingField var_gg_vape_mapping_MappingField_A;
    private MappingMethod var_gg_vape_mapping_MappingMethod_r;

    public static Object P(MEntityRenderer mEntityRenderer, Object object) {
        return mEntityRenderer.k(object);
    }

    private Object f(Object object) {
        return this.var_gg_vape_mapping_MappingField_n.getObject(object);
    }

    private void u(Object object, Object object2) {
        if (this.var_gg_vape_mapping_MappingMethod_C != null) {
            if (ForgeVersion.MC_26_2.d()) {
                this.var_gg_vape_mapping_MappingMethod_C.invokeVoid(object, object2);
            } else if (ForgeVersion.MC_26_1.d()) {
                this.var_gg_vape_mapping_MappingMethod_C.invokeVoid(object, object2, false);
            } else {
                this.var_gg_vape_mapping_MappingMethod_C.invokeVoid(object, object2);
            }
        }
    }

    public static void N(MEntityRenderer mEntityRenderer, Object object, float f) {
        mEntityRenderer.W(object, f);
    }

    private float j(Object object) {
        if (this.var_gg_vape_mapping_MappingField_D == null) {
            return 1.0f;
        }
        return this.var_gg_vape_mapping_MappingField_D.getFloat(object);
    }

    private Object L(Object object, Object object2, float f, boolean bl) {
        if (ForgeVersion.MC_26_1.d()) {
            return this.O(object);
        }
        if (ForgeVersion.MC_1_21_4.d()) {
            float f2 = this.var_gg_vape_mapping_MappingMethod_m.invokeFloat(object, object2, Float.valueOf(f), bl);
            return this.var_gg_vape_mapping_MappingMethod_r.invokeObject(object, Float.valueOf(f2));
        }
        if (ForgeVersion.MC_1_20_6.d()) {
            double d = this.var_gg_vape_mapping_MappingMethod_m.invokeDouble(object, object2, Float.valueOf(f), bl);
            return this.var_gg_vape_mapping_MappingMethod_r.invokeObject(object, d);
        }
        return this.var_gg_vape_mapping_MappingMethod_r.invokeObject(object, object2, Float.valueOf(f), bl);
    }

    public static boolean c$src$Z$4ble0w(MEntityRenderer mEntityRenderer, Object object) {
        return mEntityRenderer.v(object);
    }

    public static Object Z(MEntityRenderer mEntityRenderer, Object object) {
        return mEntityRenderer.J(object);
    }

    public static void q(MEntityRenderer mEntityRenderer, Object object, Object object2) {
        mEntityRenderer.G(object, object2);
    }

    public static void z(MEntityRenderer mEntityRenderer, Object object, Object object2, float f) {
        mEntityRenderer.S(object, object2, f);
    }

    private void a(Object object, Object object2) {
        this.var_gg_vape_mapping_MappingField_v.setObject(object, object2);
    }

    private void y(Object object, float f) {
        if (this.var_gg_vape_mapping_MappingField_j == null) {
            return;
        }
        this.var_gg_vape_mapping_MappingField_j.setFloat(object, f);
    }

    public static void i(MEntityRenderer mEntityRenderer, Object object, float f) {
        mEntityRenderer.M(object, f);
    }

    private float h(Object object) {
        if (this.var_gg_vape_mapping_MappingField_j == null) {
            return 1.0f;
        }
        return this.var_gg_vape_mapping_MappingField_j.getFloat(object);
    }

    public static float A(MEntityRenderer mEntityRenderer, Object object) {
        return mEntityRenderer.h(object);
    }

    public static Object t(MEntityRenderer mEntityRenderer, Object object) {
        return mEntityRenderer.N(object);
    }

    public static void M(MEntityRenderer mEntityRenderer, Object object, Object object2, float f) {
        mEntityRenderer.m(object, object2, f);
    }

    public static void f(MEntityRenderer mEntityRenderer, Object object, double d) {
        mEntityRenderer.S(object, d);
    }

    private void L(Object object, float f) {
        if (this.var_gg_vape_mapping_MappingField_D == null) {
            return;
        }
        this.var_gg_vape_mapping_MappingField_D.setFloat(object, f);
    }

    private void S(Object object, double d) {
        if (ForgeVersion.MC_1_7_10.Y()) {
            this.var_gg_vape_mapping_MappingMethod_e.invokeVoidNoArgs(object);
        } else {
            this.var_gg_vape_mapping_MappingMethod_e.invokeVoid(object, d);
        }
    }

    private void b(Object object, float f) {
        if (this.var_gg_vape_mapping_MappingField_c == null) {
            return;
        }
        this.var_gg_vape_mapping_MappingField_c.setFloat(object, f);
    }

    public static void F(MEntityRenderer mEntityRenderer, Object object, float f, long l) {
        mEntityRenderer.z(object, f, l);
    }

    public static void u(MEntityRenderer mEntityRenderer, Object object, float f) {
        mEntityRenderer.b(object, f);
    }

    private Object a(Object object) {
        return this.var_gg_vape_mapping_MappingField_U.getObject(object);
    }

    private Object O(Object object) {
        if (this.var_gg_vape_mapping_MappingField_K == null) {
            return null;
        }
        Object object2 = this.var_gg_vape_mapping_MappingField_K.getObject(object);
        if (object2 == null) {
            return null;
        }
        Object object3 = this.var_gg_vape_mapping_MappingField_u.getObject(object2);
        if (object3 == null) {
            return null;
        }
        Object object4 = this.var_gg_vape_mapping_MappingField_f.getObject(object3);
        if (object4 == null) {
            return null;
        }
        return this.var_gg_vape_mapping_MappingField_h.getObject(object4);
    }

    public static Object o(MEntityRenderer mEntityRenderer, Object object) {
        return mEntityRenderer.O(object);
    }

    public static void h(MEntityRenderer mEntityRenderer, Object object, float f, long l, Object object2) {
        mEntityRenderer.J(object, f, l, object2);
    }

    private void x(Object object, double d) {
        if (ForgeVersion.MC_1_7_10.Y()) {
            this.var_gg_vape_mapping_MappingMethod_W.invokeVoidNoArgs(object);
        } else {
            this.var_gg_vape_mapping_MappingMethod_W.invokeVoid(object, d);
        }
    }

    public static void x(MEntityRenderer mEntityRenderer, Object object, float f) {
        mEntityRenderer.y(object, f);
    }

    private void G(Object object, Object object2) {
        this.var_gg_vape_mapping_MappingMethod_a.invokeVoid(object, object2);
    }

    public static Object b(MEntityRenderer mEntityRenderer, Object object) {
        return mEntityRenderer.u(object);
    }

    private void m(Object object, Object object2, float f) {
        if (this.var_gg_vape_mapping_MappingMethod_l == null) {
            return;
        }
        this.var_gg_vape_mapping_MappingMethod_l.invokeVoid(object, object2, Float.valueOf(f));
    }

    private void Y(Object object, float f, long l) {
        this.var_gg_vape_mapping_MappingMethod_J.invokeVoid(object, Float.valueOf(f));
    }

    public static Object E(MEntityRenderer mEntityRenderer, Object object) {
        return mEntityRenderer.z(object);
    }

    public static int n() {
        int n = MEntityRenderer.X();
        if (n == 0) {
            return 57;
        }
        return 0;
    }

    private boolean v(Object object) {
        return this.var_gg_vape_mapping_MappingField_Z.getBoolean(object);
    }

    private void S(Object object, Object object2, float f) {
        if (this.var_gg_vape_mapping_MappingMethod_g == null) {
            return;
        }
        this.var_gg_vape_mapping_MappingMethod_g.invokeVoid(object, object2, Float.valueOf(f));
    }

    private Object u(Object object) {
        if (this.var_gg_vape_mapping_MappingField_P == null) {
            Object object2 = this.var_gg_vape_mapping_MappingField_K.getObject(object);
            return object2 == null ? null : this.var_gg_vape_mapping_MappingField_G.getObject(object2);
        }
        return this.var_gg_vape_mapping_MappingField_P.getObject(object);
    }

    public static int X() {
        return var_int_s;
    }

    private Object s() {
        return this.var_gg_vape_mapping_MappingMethod_Q.invokeObject(null, new Object[0]);
    }

    private void z(Object object, float f, long l) {
        this.var_gg_vape_mapping_MappingMethod_a.invokeVoid(object, Float.valueOf(f), l);
    }

    public static Object Q(MEntityRenderer mEntityRenderer, Object object) {
        return mEntityRenderer.f(object);
    }

    private int I(Object object) {
        if (this.var_gg_vape_mapping_MappingField_q == null) {
            return 0;
        }
        if (ForgeVersion.MC_1_21_6.d()) {
            return (int)this.var_gg_vape_mapping_MappingField_q.getFloat(object);
        }
        return this.var_gg_vape_mapping_MappingField_q.getInt(object);
    }

    private Object k(Object object) {
        return this.var_gg_vape_mapping_MappingField_X.getObject(object);
    }

    private Object A(Object object) {
        return this.var_gg_vape_mapping_MappingField_A.getObject(object);
    }

    public static void T(MEntityRenderer mEntityRenderer, Object object, Object object2) {
        mEntityRenderer.a(object, object2);
    }

    public static Object getFogRenderer(MEntityRenderer mappings, Object entityRenderer) {
        return mappings.getFogRendererHandle(entityRenderer);
    }

    private void d(Object object, Object object2) {
        this.var_gg_vape_mapping_MappingField_N.setObject(object, object2);
    }

    public MEntityRenderer() {
        this(MEntityRenderer.X());
    }

    private MEntityRenderer(int n) {
        super(MappedClasses.FW);
        MEntityRenderer mEntityRenderer;
        String string;
        Class[] classArray;
        MEntityRenderer mEntityRenderer2;
        String string2;
        boolean bl;
        Class<Object> clazz;
        int n2 = n;
        if (ForgeVersion.MC_1_21_6.v()) {
            clazz = MappedClasses.qY;
            bl = true;
            string2 = "resourceManager";
            mEntityRenderer2 = this;
            this.var_gg_vape_mapping_MappingField_X = mEntityRenderer2.J(string2, bl, clazz);
        } else {
            if (ForgeVersion.MC_26_1.d()) {
                clazz = MappedClasses.uI;
                bl = true;
                string2 = "gameRenderState";
                mEntityRenderer2 = this;
                this.var_gg_vape_mapping_MappingField_K = mEntityRenderer2.J(string2, bl, clazz);
                Class clazz2 = MappedClasses.var_java_lang_Class_i;
                boolean bl2 = true;
                String string22 = "guiRenderState";
                Class clazz3 = MappedClasses.uI;
                MEntityRenderer mEntityRenderer22 = this;
                this.var_gg_vape_mapping_MappingField_G = this.registerInstanceFieldForOwner(clazz3, string22, bl2, clazz2);
                Class clazz4 = MappedClasses.z6;
                boolean bl3 = true;
                String string3 = "levelRenderState";
                Class clazz5 = MappedClasses.uI;
                MEntityRenderer mEntityRenderer3 = this;
                this.var_gg_vape_mapping_MappingField_u = this.registerInstanceFieldForOwner(clazz5, string3, bl3, clazz4);
                Class clazz6 = MappedClasses.zf;
                boolean bl4 = true;
                String string4 = "cameraRenderState";
                Class clazz7 = MappedClasses.z6;
                MEntityRenderer mEntityRenderer4 = this;
                this.var_gg_vape_mapping_MappingField_f = this.registerInstanceFieldForOwner(clazz7, string4, bl4, clazz6);
                Class clazz8 = MappedClasses.qr;
                boolean bl5 = true;
                String string5 = "projectionMatrix";
                Class clazz9 = MappedClasses.zf;
                MEntityRenderer mEntityRenderer5 = this;
                this.var_gg_vape_mapping_MappingField_h = this.registerInstanceFieldForOwner(clazz9, string5, bl5, clazz8);
            } else {
                clazz = MappedClasses.var_java_lang_Class_i;
                bl = true;
                string2 = "guiRenderState";
                mEntityRenderer2 = this;
                this.var_gg_vape_mapping_MappingField_P = mEntityRenderer2.J(string2, bl, clazz);
            }
            clazz = MappedClasses.Vq;
            bl = true;
            string2 = "screenEffectRenderer";
            mEntityRenderer2 = this;
            this.var_gg_vape_mapping_MappingField_A = mEntityRenderer2.J(string2, bl, clazz);
            Class clazz10 = MappedClasses.FOG_RENDERER;
            boolean bl6 = true;
            String string6 = "fogRenderer";
            MEntityRenderer mEntityRenderer6 = this;
            this.fogRendererField = this.J(string6, bl6, clazz10);
            Class clazz11 = MappedClasses.var_java_lang_Class_w;
            boolean bl7 = true;
            String string7 = "guiRenderer";
            MEntityRenderer mEntityRenderer7 = this;
            this.var_gg_vape_mapping_MappingField_z = this.J(string7, bl7, clazz11);
        }
        if (ForgeVersion.MC_1_16_5.v()) {
            classArray = new Class[]{Integer.TYPE, Float.TYPE};
            Class<Void> clazz2 = Void.TYPE;
            boolean bl2 = true;
            string = "setupFog";
            mEntityRenderer = this;
            this.var_gg_vape_mapping_MappingMethod_L = mEntityRenderer.Y(string, bl2, clazz2, classArray);
        }
        if (ForgeVersion.MC_1_20_6.v()) {
            if (ForgeVersion.MC_1_12_2.d()) {
                clazz = DescUtils.getArrayType(MappedClasses.zC);
                boolean bl3 = true;
                String string3 = "SHADERS_TEXTURES";
                mEntityRenderer2 = this;
                this.var_gg_vape_mapping_MappingField_d = mEntityRenderer2.registerStaticField(string3, bl3, clazz);
            } else {
                clazz = DescUtils.getArrayType(MappedClasses.zC);
                boolean bl4 = true;
                String string4 = "shaderResourceLocations";
                mEntityRenderer2 = this;
                this.var_gg_vape_mapping_MappingField_d = mEntityRenderer2.registerStaticField(string4, bl4, clazz);
            }
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            Class[] classArray2;
            MappingField mappingField2;
            Class[] classArray3;
            if (ForgeVersion.MC_1_21_0.v()) {
                Class clazz3 = MappedClasses.Fo;
                boolean bl5 = true;
                string = "shaderGroup";
                mEntityRenderer = this;
                this.var_gg_vape_mapping_MappingField_N = mEntityRenderer.J(string, bl5, clazz3);
            }
            Class clazz4 = MappedClasses.zH;
            boolean bl6 = true;
            string = ForgeVersion.MC_26_1.d() ? "lightmap" : "lightmapTexture";
            mEntityRenderer = this;
            this.var_gg_vape_mapping_MappingField_U = mEntityRenderer.J(string, bl6, clazz4);
            if (ForgeVersion.MC_26_1.v()) {
                classArray3 = new Class[]{MappedClasses.zJ, MappedClasses.zc, Boolean.TYPE, Boolean.TYPE, Float.TYPE};
                Class<Void> clazz12 = Void.TYPE;
                String string8 = "update";
                MEntityRenderer mEntityRenderer8 = this;
                this.var_gg_vape_mapping_MappingMethod_x = ((MappingMethodBuilder)((MappingMethodBuilder)this.methodBuilder(string8, clazz12, classArray3).setOwnerClass(MappedClasses.lt)).setNameForVersion(ForgeVersion.MC_1_21_11.n(), "setup")).setParameterTypesForVersion(ForgeVersion.MC_1_21_11.n(), MappedClasses.YU, MappedClasses.zc, Boolean.TYPE, Boolean.TYPE, Float.TYPE).buildMethod();
            } else {
                classArray3 = new Class[]{MappedClasses.uy};
                Class<Void> clazz13 = Void.TYPE;
                String string9 = "update";
                MEntityRenderer mEntityRenderer9 = this;
                this.var_gg_vape_mapping_MappingMethod_x = ((MappingMethodBuilder)this.methodBuilder(string9, clazz13, classArray3).setOwnerClass(MappedClasses.lt)).buildMethod();
            }
            Class clazz14 = MappedClasses.lt;
            boolean bl8 = true;
            String string10 = ForgeVersion.MC_26_1.d() ? "mainCamera" : "activeRender";
            MEntityRenderer mEntityRenderer10 = this;
            this.var_gg_vape_mapping_MappingField_n = this.J(string10, bl8, clazz14);
            if (ForgeVersion.MC_26_1.v()) {
                Class[] classArray4 = new Class[]{MappedClasses.DQ, Float.TYPE};
                Class<Void> clazz15 = Void.TYPE;
                boolean bl9 = true;
                String string11 = "hurtCameraEffect";
                MEntityRenderer mEntityRenderer11 = this;
                this.var_gg_vape_mapping_MappingMethod_l = this.Y(string11, bl9, clazz15, classArray4);
                Class[] classArray22 = new Class[]{MappedClasses.DQ, Float.TYPE};
                Class<Void> clazz16 = Void.TYPE;
                boolean bl10 = true;
                String string12 = "applyBobbing";
                MEntityRenderer mEntityRenderer12 = this;
                this.var_gg_vape_mapping_MappingMethod_g = this.Y(string12, bl10, clazz16, classArray22);
            }
            if (ForgeVersion.MC_26_1.d()) {
                mappingField2 = null;
            } else {
                Class<Integer> clazz17 = Integer.TYPE;
                boolean bl11 = true;
                String string13 = "rendererUpdateCount";
                MEntityRenderer mEntityRenderer13 = this;
                mappingField2 = this.var_gg_vape_mapping_MappingField_q = this.J(string13, bl11, clazz17);
            }
            if (ForgeVersion.MC_1_21_0.v()) {
                Class[] classArray5 = new Class[]{MappedClasses.qr};
                Class<Void> clazz18 = Void.TYPE;
                boolean bl12 = true;
                String string14 = "resetProjectionMatrix";
                MEntityRenderer mEntityRenderer14 = this;
                this.var_gg_vape_mapping_MappingMethod_p = this.Y(string14, bl12, clazz18, classArray5);
            }
            Class clazz19 = MappedClasses.zc;
            boolean bl13 = true;
            String string15 = "pointedEntity";
            Class clazz20 = MappedClasses.uP;
            MEntityRenderer mEntityRenderer15 = this;
            this.var_gg_vape_mapping_MappingField_v = this.registerInstanceFieldForOwner(clazz20, string15, bl13, clazz19);
            if (ForgeVersion.MC_1_20_6.d()) {
                if (ForgeVersion.MC_26_1.d()) {
                    this.var_gg_vape_mapping_MappingMethod_m = null;
                    this.var_gg_vape_mapping_MappingMethod_r = null;
                } else {
                    classArray2 = new Class[]{MappedClasses.lt, Float.TYPE, Boolean.TYPE};
                    Class<Double> clazz21 = Double.TYPE;
                    boolean bl14 = true;
                    String string16 = "getFov";
                    MEntityRenderer mEntityRenderer16 = this;
                    this.var_gg_vape_mapping_MappingMethod_m = this.Y(string16, bl14, clazz21, classArray2);
                    Class[] classArray32 = new Class[]{Double.TYPE};
                    Class clazz22 = MappedClasses.qr;
                    boolean bl15 = true;
                    String string17 = "getProjectionMatrix";
                    MEntityRenderer mEntityRenderer17 = this;
                    this.var_gg_vape_mapping_MappingMethod_r = this.Y(string17, bl15, clazz22, classArray32);
                }
            } else {
                classArray2 = new Class[]{MappedClasses.lt, Float.TYPE, Boolean.TYPE};
                Class<Double> clazz23 = Double.TYPE;
                boolean bl16 = Wrapper.isNativeAvailable;
                String string18 = "func_215311_a";
                MEntityRenderer mEntityRenderer18 = this;
                this.var_gg_vape_mapping_MappingMethod_m = this.Y(string18, bl16, clazz23, classArray2);
                Class[] classArray4 = new Class[]{MappedClasses.lt, Float.TYPE, Boolean.TYPE};
                Class clazz24 = MappedClasses.qr;
                boolean bl17 = true;
                String string19 = "getProjectionMatrix";
                MEntityRenderer mEntityRenderer19 = this;
                this.var_gg_vape_mapping_MappingMethod_r = this.Y(string19, bl17, clazz24, classArray4);
            }
            classArray2 = new Class[]{Float.TYPE, Long.TYPE, MappedClasses.DQ};
            Class<Void> clazz25 = Void.TYPE;
            String string20 = "renderLevel";
            MEntityRenderer mEntityRenderer20 = this;
            this.var_gg_vape_mapping_MappingMethod_a = this.methodBuilder(string20, clazz25, classArray2).setParameterTypesForVersion(ForgeVersion.MC_1_21_0.n(), MappedClasses.uy).setParameterTypesForVersion(ForgeVersion.MC_1_20_6.S(), Float.TYPE, Long.TYPE).buildMethod();
            if (ForgeVersion.MC_1_21_11.d()) {
                Class[] classArray5;
                String string21;
                String string5 = string21 = ForgeVersion.MC_26_1.d() ? "update" : "updateCamera";
                if (ForgeVersion.MC_26_2.d()) {
                    classArray5 = new Class[]{MappedClasses.uy};
                } else if (ForgeVersion.MC_26_1.d()) {
                    Class[] classArray6 = new Class[2];
                    classArray6[0] = MappedClasses.uy;
                    classArray5 = classArray6;
                    classArray6[1] = Boolean.TYPE;
                } else {
                    Class[] classArray7 = new Class[1];
                    classArray5 = classArray7;
                    classArray7[0] = MappedClasses.uy;
                }
                Class[] classArray8 = classArray5;
                Class<Void> clazz26 = Void.TYPE;
                boolean bl18 = true;
                String string22 = string21;
                MEntityRenderer mEntityRenderer21 = this;
                this.var_gg_vape_mapping_MappingMethod_C = this.Y(string22, bl18, clazz26, classArray8);
                if (ForgeVersion.MC_26_1.d()) {
                    this.extractRenderStateMethod = this.Y(ForgeVersion.MC_26_2.d() ? "extract" : "extractRenderState", true, Void.TYPE, MappedClasses.uy, Boolean.TYPE);
                }
            }
        } else {
            clazz = MappedClasses.Fo;
            boolean bl7 = true;
            String string6 = ForgeVersion.c() >= 23 ? "shaderGroup" : "theShaderGroup";
            mEntityRenderer2 = this;
            this.var_gg_vape_mapping_MappingField_N = mEntityRenderer2.J(string6, bl7, clazz);
            Class clazz27 = MappedClasses.zc;
            boolean bl19 = true;
            String string23 = "pointedEntity";
            MEntityRenderer mEntityRenderer22 = this;
            this.var_gg_vape_mapping_MappingField_v = this.J(string23, bl19, clazz27);
            Class[] classArray6 = new Class[]{Float.TYPE, Integer.TYPE};
            Class<Void> clazz28 = Void.TYPE;
            boolean bl20 = true;
            String string24 = "setupCameraTransform";
            MEntityRenderer mEntityRenderer23 = this;
            this.var_gg_vape_mapping_MappingMethod_i = this.Y(string24, bl20, clazz28, classArray6);
            Class[] classArray9 = new Class[]{Float.TYPE, Long.TYPE};
            Class<Void> clazz29 = Void.TYPE;
            boolean bl21 = true;
            String string25 = "renderWorld";
            MEntityRenderer mEntityRenderer24 = this;
            this.var_gg_vape_mapping_MappingMethod_a = this.Y(string25, bl21, clazz29, classArray9);
            if (ForgeVersion.MC_1_8_9.d()) {
                Class[] classArray10 = new Class[]{Integer.TYPE, Float.TYPE, Long.TYPE};
                Class<Void> clazz30 = Void.TYPE;
                boolean bl22 = true;
                String string26 = "renderWorldPass";
                MEntityRenderer mEntityRenderer25 = this;
                this.var_gg_vape_mapping_MappingMethod_H = this.Y(string26, bl22, clazz30, classArray10);
            }
            Class[] classArray11 = new Class[]{Float.TYPE};
            Class<Void> clazz31 = Void.TYPE;
            boolean bl23 = true;
            String string27 = "orientCamera";
            MEntityRenderer mEntityRenderer26 = this;
            this.var_gg_vape_mapping_MappingMethod_B = this.Y(string27, bl23, clazz31, classArray11);
        }
        if (ForgeVersion.MC_26_1.d()) {
            this.var_gg_vape_mapping_MappingField_j = null;
            this.var_gg_vape_mapping_MappingField_D = null;
        } else {
            clazz = Float.TYPE;
            boolean bl8 = true;
            String string7 = "fovModifierHand";
            mEntityRenderer2 = this;
            this.var_gg_vape_mapping_MappingField_j = mEntityRenderer2.J(string7, bl8, clazz);
            Class<Float> clazz32 = Float.TYPE;
            boolean bl24 = true;
            String string28 = "fovModifierHandPrev";
            MEntityRenderer mEntityRenderer27 = this;
            this.var_gg_vape_mapping_MappingField_D = this.J(string28, bl24, clazz32);
        }
        classArray = new Class[]{Integer.TYPE, Integer.TYPE};
        Class<Void> clazz5 = Void.TYPE;
        boolean bl9 = true;
        string = "updateShaderGroupSize";
        mEntityRenderer = this;
        this.var_gg_vape_mapping_MappingMethod_M = mEntityRenderer.Y(string, bl9, clazz5, classArray);
        if (ForgeVersion.MC_26_1.d()) {
            this.var_gg_vape_mapping_MappingMethod_k = null;
            Object mappingMethod = null;
        } else {
            Class[] classArray12 = new Class[]{Float.TYPE};
            Class<Void> clazz33 = Void.TYPE;
            boolean bl25 = true;
            String string29 = "getMouseOver";
            MEntityRenderer mEntityRenderer28 = this;
            MappingMethod mappingMethod = this.var_gg_vape_mapping_MappingMethod_k = this.Y(string29, bl25, clazz33, classArray12);
        }
        if (ForgeVersion.MC_26_1.d()) {
            Object mappingField = null;
        } else {
            Class<Float> clazz34 = Float.TYPE;
            boolean bl26 = true;
            String string30 = "farPlaneDistance";
            MEntityRenderer mEntityRenderer29 = this;
            MappingField mappingField = this.var_gg_vape_mapping_MappingField_c = this.J(string30, bl26, clazz34);
        }
        if (ForgeVersion.MC_1_7_10.Y()) {
            Class<Boolean> clazz35 = Boolean.TYPE;
            boolean bl27 = true;
            String string31 = "useShader";
            MEntityRenderer mEntityRenderer30 = this;
            this.var_gg_vape_mapping_MappingField_Z = this.J(string31, bl27, clazz35);
            if (ForgeVersion.MC_1_16_5.d()) {
                Class[] classArray13 = new Class[]{Float.TYPE, Long.TYPE, Boolean.TYPE};
                Class<Void> clazz36 = Void.TYPE;
                boolean bl28 = true;
                String string32 = "updateCameraAndRender";
                MEntityRenderer mEntityRenderer31 = this;
                this.var_gg_vape_mapping_MappingMethod_J = this.Y(string32, bl28, clazz36, classArray13);
            } else {
                if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
                    Class[] classArray14 = new Class[]{Float.TYPE, Long.TYPE};
                    Class<Void> clazz37 = Void.TYPE;
                    boolean bl29 = true;
                    String string33 = "updateCameraAndRender";
                    MEntityRenderer mEntityRenderer32 = this;
                    this.var_gg_vape_mapping_MappingMethod_J = this.Y(string33, bl29, clazz37, classArray14);
                } else {
                    Class[] classArray15 = new Class[]{Float.TYPE, Long.TYPE};
                    Class<Void> clazz38 = Void.TYPE;
                    boolean bl30 = Wrapper.isNativeAvailable;
                    String string34 = "func_181560_a";
                    MEntityRenderer mEntityRenderer33 = this;
                    this.var_gg_vape_mapping_MappingMethod_J = this.Y(string34, bl30, clazz38, classArray15);
                }
                Class[] classArray16 = new Class[]{};
                Class<Void> clazz39 = Void.TYPE;
                boolean bl31 = true;
                String string35 = "disableLightmap";
                MEntityRenderer mEntityRenderer34 = this;
                this.var_gg_vape_mapping_MappingMethod_e = this.Y(string35, bl31, clazz39, classArray16);
                Class[] classArray17 = new Class[]{};
                Class<Void> clazz40 = Void.TYPE;
                boolean bl32 = true;
                String string36 = "enableLightmap";
                MEntityRenderer mEntityRenderer35 = this;
                this.var_gg_vape_mapping_MappingMethod_W = this.Y(string36, bl32, clazz40, classArray17);
            }
        } else {
            Class[] classArray18 = new Class[]{Double.TYPE};
            Class<Void> clazz41 = Void.TYPE;
            boolean bl33 = true;
            String string37 = "disableLightmap";
            MEntityRenderer mEntityRenderer36 = this;
            this.var_gg_vape_mapping_MappingMethod_e = this.Y(string37, bl33, clazz41, classArray18);
            Class[] classArray19 = new Class[]{Double.TYPE};
            Class<Void> clazz42 = Void.TYPE;
            boolean bl34 = true;
            String string38 = "enableLightmap";
            MEntityRenderer mEntityRenderer37 = this;
            this.var_gg_vape_mapping_MappingMethod_W = this.Y(string38, bl34, clazz42, classArray19);
            if (Wrapper.vapeInstance.isVanillaMinecraftPresent()) {
                Class[] classArray20 = new Class[]{Float.TYPE};
                Class<Void> clazz43 = Void.TYPE;
                boolean bl35 = true;
                String string39 = "updateCameraAndRender";
                MEntityRenderer mEntityRenderer38 = this;
                this.var_gg_vape_mapping_MappingMethod_J = this.Y(string39, bl35, clazz43, classArray20);
            } else {
                Class[] classArray21 = new Class[]{Float.TYPE};
                Class<Void> clazz44 = Void.TYPE;
                boolean bl36 = Wrapper.isNativeAvailable;
                String string40 = "func_78480_b";
                MEntityRenderer mEntityRenderer39 = this;
                this.var_gg_vape_mapping_MappingMethod_J = this.Y(string40, bl36, clazz44, classArray21);
            }
        }
        if (ForgeVersion.MC_1_17.d()) {
            if (ForgeVersion.MC_1_21_6.v()) {
                Class[] classArray22 = new Class[]{Integer.TYPE, Integer.TYPE, Float.TYPE};
                Class<Void> clazz45 = Void.TYPE;
                boolean bl37 = true;
                String string41 = "renderItemActivationAnimation";
                MEntityRenderer mEntityRenderer40 = this;
                this.var_gg_vape_mapping_MappingMethod_V = this.Y(string41, bl37, clazz45, classArray22);
            }
            if (ForgeVersion.MC_1_21_0.H().y()) {
                Class[] classArray23 = new Class[]{};
                Class clazz46 = MappedClasses.Fv;
                boolean bl38 = true;
                String string42 = "getPositionColorShader";
                MEntityRenderer mEntityRenderer41 = this;
                this.var_gg_vape_mapping_MappingMethod_Q = this.registerStaticMethod(string42, bl38, clazz46, classArray23);
            }
        }
    }

    public static void X(MEntityRenderer mEntityRenderer, Object object, float f, long l) {
        mEntityRenderer.Y(object, f, l);
    }

    private void w(Object object, int n, int n2) {
        this.var_gg_vape_mapping_MappingMethod_M.invokeVoid(object, n, n2);
    }

    private void S(Object object, boolean bl) {
        this.var_gg_vape_mapping_MappingField_Z.setBoolean(object, bl);
    }

    public static int m(MEntityRenderer mEntityRenderer, Object object) {
        return mEntityRenderer.I(object);
    }

    private void W(Object object, float f) {
        if (this.var_gg_vape_mapping_MappingMethod_k == null) {
            return;
        }
        this.var_gg_vape_mapping_MappingMethod_k.invokeVoid(object, Float.valueOf(f));
    }

    private void J(Object object, float f, long l, Object object2) {
        this.var_gg_vape_mapping_MappingMethod_a.invokeVoid(object, Float.valueOf(f), l, object2);
    }

    private void X(Object object, float f, int n) {
        this.var_gg_vape_mapping_MappingMethod_i.invokeVoid(object, Float.valueOf(f), n);
    }

    public static Object[] k(MEntityRenderer mEntityRenderer) {
        return mEntityRenderer.F();
    }

    public static void j(MEntityRenderer mEntityRenderer, Object object, float f) {
        mEntityRenderer.L(object, f);
    }

    private Object getFogRendererHandle(Object entityRenderer) {
        return this.fogRendererField.getObject(entityRenderer);
    }

    private Object J(Object object) {
        return this.var_gg_vape_mapping_MappingField_v.getObject(object);
    }

    public static void D(MEntityRenderer mEntityRenderer, Object object, float f, int n) {
        mEntityRenderer.X(object, f, n);
    }

    public static Object z(MEntityRenderer mEntityRenderer, Object object, Object object2, float f, boolean bl) {
        return mEntityRenderer.L(object, object2, f, bl);
    }

    public static void i(MEntityRenderer mEntityRenderer, Object object, int n, int n2) {
        mEntityRenderer.w(object, n, n2);
    }

    private Object N(Object object) {
        return this.var_gg_vape_mapping_MappingField_N.getObject(object);
    }

    public static void o(MEntityRenderer mEntityRenderer, Object object, Object object2) {
        mEntityRenderer.z(object, object2);
    }

    public static void a(MEntityRenderer mEntityRenderer, Object object, Object object2) {
        mEntityRenderer.u(object, object2);
    }

    private void z(Object object, Object object2) {
        this.var_gg_vape_mapping_MappingMethod_p.invokeVoid(object, object2);
    }

    public static void c(int n) {
        var_int_s = n;
    }

    private Object[] F() {
        return this.var_gg_vape_mapping_MappingField_d.getObjectArray(null);
    }

    public static void C(MEntityRenderer mEntityRenderer, Object object, boolean bl) {
        mEntityRenderer.S(object, bl);
    }

    public static Object O(MEntityRenderer mEntityRenderer, Object object) {
        return mEntityRenderer.a(object);
    }

    public static float c(MEntityRenderer mEntityRenderer, Object object) {
        return mEntityRenderer.j(object);
    }

    private Object z(Object object) {
        return this.var_gg_vape_mapping_MappingField_z.getObject(object);
    }

    public static void D(MEntityRenderer mEntityRenderer, Object object, Object object2) {
        mEntityRenderer.d(object, object2);
    }

    private void M(Object object, float f) {
        this.var_gg_vape_mapping_MappingMethod_B.invokeVoid(object, Float.valueOf(f));
    }

    public static Object G(MEntityRenderer mEntityRenderer) {
        return mEntityRenderer.s();
    }

    public static void V(MEntityRenderer mEntityRenderer, Object object, double d) {
        mEntityRenderer.x(object, d);
    }

    public void extractRenderState(Object entityRenderer, Object deltaTracker) {
        if (this.extractRenderStateMethod != null) {
            this.extractRenderStateMethod.invokeVoid(entityRenderer, deltaTracker, true);
        }
    }

    static {
        MEntityRenderer.c(0);
    }
}

