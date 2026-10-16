/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.Vape;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MRenderManager
extends Mapping {
    public MappingMethod var_gg_vape_mapping_MappingMethod_r;
    private MappingField var_gg_vape_mapping_MappingField_Y;
    private MappingField var_gg_vape_mapping_MappingField_X;
    private MappingField var_gg_vape_mapping_MappingField_D;
    private MappingField var_gg_vape_mapping_MappingField_C;
    private MappingField var_gg_vape_mapping_MappingField_p;
    private static String[] var_java_lang_String_arr_m;
    public MappingMethod var_gg_vape_mapping_MappingMethod_W;
    private MappingField var_gg_vape_mapping_MappingField_k;
    private MappingField var_gg_vape_mapping_MappingField_L;
    public MappingMethod var_gg_vape_mapping_MappingMethod_j;
    public MappingMethod var_gg_vape_mapping_MappingMethod_x;
    private final MappingMethod var_gg_vape_mapping_MappingMethod_l;
    private MappingField var_gg_vape_mapping_MappingField_d;

    public static Object q(MRenderManager mRenderManager, Object object) {
        return mRenderManager.x(object);
    }

    private Object K(Object object, Object object2) {
        return this.var_gg_vape_mapping_MappingMethod_l.invokeObject(object, object2);
    }

    public static double t(MRenderManager mRenderManager, Object object) {
        return mRenderManager.r(object);
    }

    private Object O(Object object) {
        return this.var_gg_vape_mapping_MappingField_X.getObject(object);
    }

    private Object p() {
        return this.var_gg_vape_mapping_MappingField_d.getObject(null);
    }

    private Object x(Object object) {
        return this.var_gg_vape_mapping_MappingField_D.getObject(object);
    }

    public MRenderManager() {
        this(MRenderManager.O());
    }

    private MRenderManager(String[] stringArray) {
        super(MappedClasses.Dc);
        MRenderManager mRenderManager;
        Class<Float> clazz;
        MRenderManager mRenderManager2;
        String string;
        Class[] classArray;
        String[] stringArray2 = stringArray;
        if (ForgeVersion.MC_1_21_0.d()) {
            boolean bl;
            Class<Object> clazz2;
            if (ForgeVersion.MC_1_21_4.d()) {
                if (ForgeVersion.MC_1_21_10.d()) {
                    classArray = new Class[]{MappedClasses.zc, MappedClasses.qh, Double.TYPE, Double.TYPE, Double.TYPE};
                    clazz2 = Boolean.TYPE;
                    bl = true;
                    string = "shouldRender";
                    mRenderManager2 = this;
                    this.var_gg_vape_mapping_MappingMethod_j = mRenderManager2.Y(string, bl, clazz2, classArray);
                } else {
                    classArray = new Class[]{MappedClasses.zc, Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, MappedClasses.DQ, MappedClasses.ZK, Integer.TYPE, MappedClasses.VQ};
                    clazz2 = Void.TYPE;
                    bl = true;
                    string = "render";
                    mRenderManager2 = this;
                    this.var_gg_vape_mapping_MappingMethod_x = mRenderManager2.Y(string, bl, clazz2, classArray);
                    Class[] classArray2 = new Class[]{MappedClasses.qX, Double.TYPE, Double.TYPE, Double.TYPE, MappedClasses.DQ, MappedClasses.ZK, Integer.TYPE, MappedClasses.VQ};
                    Class<Void> clazz22 = Void.TYPE;
                    boolean bl2 = true;
                    String string2 = "render";
                    MRenderManager mRenderManager22 = this;
                    this.var_gg_vape_mapping_MappingMethod_r = this.Y(string2, bl2, clazz22, classArray2);
                }
            } else {
                classArray = new Class[]{MappedClasses.zc, Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Float.TYPE, MappedClasses.DQ, MappedClasses.ZK, Integer.TYPE};
                clazz2 = Void.TYPE;
                bl = true;
                string = "render";
                mRenderManager2 = this;
                this.var_gg_vape_mapping_MappingMethod_x = mRenderManager2.Y(string, bl, clazz2, classArray);
            }
        }
        if (ForgeVersion.MC_1_7_10.L()) {
            clazz = MappedClasses.Dc;
            boolean bl = true;
            String string2 = "instance";
            mRenderManager = this;
            this.var_gg_vape_mapping_MappingField_d = mRenderManager.registerStaticField(string2, bl, clazz);
        }
        if (ForgeVersion.MC_1_8_9.L()) {
            if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
                classArray = new Class[]{MappedClasses.zc, Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Float.TYPE, Boolean.TYPE};
                Class<Boolean> clazz3 = Boolean.TYPE;
                boolean bl = true;
                string = "doRenderEntity";
                mRenderManager2 = this;
                this.var_gg_vape_mapping_MappingMethod_W = mRenderManager2.Y(string, bl, clazz3, classArray);
            } else {
                classArray = new Class[]{MappedClasses.zc, Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Float.TYPE, Boolean.TYPE};
                Class<Boolean> clazz4 = Boolean.TYPE;
                boolean bl = Wrapper.isNativeAvailable;
                string = "func_147939_a";
                mRenderManager2 = this;
                this.var_gg_vape_mapping_MappingMethod_W = mRenderManager2.Y(string, bl, clazz4, classArray);
            }
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            clazz = MappedClasses.lt;
            boolean bl = true;
            String string3 = "info";
            mRenderManager = this;
            this.var_gg_vape_mapping_MappingField_X = mRenderManager.J(string3, bl, clazz);
            Class[] classArray2 = new Class[]{MappedClasses.zc};
            Class clazz3 = MappedClasses.VQ;
            boolean bl3 = true;
            String string32 = "getRenderer";
            MRenderManager mRenderManager3 = this;
            this.var_gg_vape_mapping_MappingMethod_l = this.Y(string32, bl3, clazz3, classArray2);
            if (ForgeVersion.MC_1_21_10.v()) {
                Class clazz4 = MappedClasses.qI;
                boolean bl4 = true;
                String string4 = "cameraOrientation";
                MRenderManager mRenderManager4 = this;
                this.var_gg_vape_mapping_MappingField_D = this.J(string4, bl4, clazz4);
                Class[] classArray3 = new Class[]{MappedClasses.zc, Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Float.TYPE, MappedClasses.DQ, MappedClasses.ZK, Integer.TYPE};
                Class<Void> clazz5 = Void.TYPE;
                boolean bl5 = true;
                String string5 = "renderEntityStatic";
                MRenderManager mRenderManager5 = this;
                this.var_gg_vape_mapping_MappingMethod_W = this.Y(string5, bl5, clazz5, classArray3);
            }
        } else {
            clazz = Float.TYPE;
            boolean bl = true;
            String string4 = "playerViewX";
            mRenderManager = this;
            this.var_gg_vape_mapping_MappingField_p = mRenderManager.J(string4, bl, clazz);
            Class<Float> clazz6 = Float.TYPE;
            boolean bl6 = true;
            String string6 = "playerViewY";
            MRenderManager mRenderManager6 = this;
            this.var_gg_vape_mapping_MappingField_k = this.J(string6, bl6, clazz6);
            Class<Double> clazz7 = Double.TYPE;
            boolean bl7 = true;
            String string7 = "viewerPosX";
            MRenderManager mRenderManager7 = this;
            this.var_gg_vape_mapping_MappingField_Y = this.J(string7, bl7, clazz7);
            Class<Double> clazz8 = Double.TYPE;
            boolean bl8 = true;
            String string8 = "viewerPosY";
            MRenderManager mRenderManager8 = this;
            this.var_gg_vape_mapping_MappingField_L = this.J(string8, bl8, clazz8);
            Class<Double> clazz9 = Double.TYPE;
            boolean bl9 = true;
            String string9 = "viewerPosZ";
            MRenderManager mRenderManager9 = this;
            this.var_gg_vape_mapping_MappingField_C = this.J(string9, bl9, clazz9);
            Class[] classArray3 = new Class[]{MappedClasses.zc};
            Class clazz10 = MappedClasses.VQ;
            boolean bl10 = true;
            String string10 = "getEntityRenderObject";
            MRenderManager mRenderManager10 = this;
            this.var_gg_vape_mapping_MappingMethod_l = this.Y(string10, bl10, clazz10, classArray3);
        }
        if (GuiComponent.getLegacyComponentState() == null) {
            MRenderManager.o(new String[5]);
        }
    }

    private float d(Object object) {
        return this.var_gg_vape_mapping_MappingField_k.getFloat(object);
    }

    public static float o(MRenderManager mRenderManager, Object object) {
        return mRenderManager.d(object);
    }

    public static float r(MRenderManager mRenderManager, Object object) {
        return mRenderManager.f(object);
    }

    public static double M(MRenderManager mRenderManager, Object object) {
        return mRenderManager.t(object);
    }

    private double r(Object object) {
        return this.var_gg_vape_mapping_MappingField_C.getDouble(object);
    }

    public static double N(MRenderManager mRenderManager, Object object) {
        return mRenderManager.F(object);
    }

    private float f(Object object) {
        return this.var_gg_vape_mapping_MappingField_p.getFloat(object);
    }

    private double t(Object object) {
        return this.var_gg_vape_mapping_MappingField_Y.getDouble(object);
    }

    public static Object e(MRenderManager mRenderManager, Object object) {
        return mRenderManager.O(object);
    }

    public static String[] O() {
        return var_java_lang_String_arr_m;
    }

    private double F(Object object) {
        return this.var_gg_vape_mapping_MappingField_L.getDouble(object);
    }

    public static Object O(MRenderManager mRenderManager, Object object, Object object2) {
        return mRenderManager.K(object, object2);
    }

    public static void o(String[] stringArray) {
        var_java_lang_String_arr_m = stringArray;
    }

    public static Object T(MRenderManager mRenderManager) {
        return mRenderManager.p();
    }

    static {
        MRenderManager.o(null);
    }
}

