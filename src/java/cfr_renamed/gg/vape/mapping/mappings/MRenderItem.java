/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MRenderManager;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MRenderItem
extends Mapping {
    private MappingMethod var_gg_vape_mapping_MappingMethod_r;
    private MappingMethod var_gg_vape_mapping_MappingMethod_o;
    private MappingMethod var_gg_vape_mapping_MappingMethod_s;
    private MappingField var_gg_vape_mapping_MappingField_Q;
    private MappingMethod var_gg_vape_mapping_MappingMethod_F;
    private MappingMethod var_gg_vape_mapping_MappingMethod_H;
    private MappingMethod var_gg_vape_mapping_MappingMethod_d;
    private MappingMethod var_gg_vape_mapping_MappingMethod_k;
    private MappingMethod var_gg_vape_mapping_MappingMethod_n;
    private MappingField var_gg_vape_mapping_MappingField_v;
    private MappingMethod var_gg_vape_mapping_MappingMethod_N;

    public static void x(MRenderItem mRenderItem, Object object, Object object2, Object object3, boolean bl, Object object4, Object object5, int n, int n2, Object object6) {
        mRenderItem.E(object, object2, object3, bl, object4, object5, n, n2, object6);
    }

    public static MappingMethod V(MRenderItem mRenderItem) {
        return mRenderItem.var_gg_vape_mapping_MappingMethod_o;
    }

    public static void G(MRenderItem mRenderItem, Object object, float f) {
        mRenderItem.Z(object, f);
    }

    private float f(Object object) {
        return this.var_gg_vape_mapping_MappingField_Q.getFloat(object);
    }

    private void i(Object object, Object object2, Object object3, Object object4, int n, int n2) {
        this.var_gg_vape_mapping_MappingMethod_H.invokeVoid(object, object2, object3, object4, n, n2);
    }

    public static Object a(MRenderItem mRenderItem) {
        return mRenderItem.Q();
    }

    private void E(Object object, Object object2, Object object3, boolean bl, Object object4, Object object5, int n, int n2, Object object6) {
        this.var_gg_vape_mapping_MappingMethod_N.invokeVoid(object, object2, object3, bl, object4, object5, n, n2, object6);
    }

    private Object F(Object object, Object object2, Object object3, Object object4, int n) {
        return this.var_gg_vape_mapping_MappingMethod_k.invokeObject(object, object2, object3, object4, n);
    }

    private void C(Object object, Object object2, Object object3, int n, int n2, int[] nArray, Object object4, Object object5, Object object6) {
        this.var_gg_vape_mapping_MappingMethod_F.invokeVoid(null, object, object2, object3, n, n2, nArray, object4, object5, object6);
    }

    public static void P(MRenderItem mRenderItem, Object object, Object object2, Object object3, Object object4, int n, int n2) {
        mRenderItem.Q(object, object2, object3, object4, n, n2);
    }

    private Object Q() {
        return this.var_gg_vape_mapping_MappingMethod_o.invokeObject(null, new Object[0]);
    }

    public static Object A(MRenderItem mRenderItem, Object object, Object object2, Object object3, Object object4, int n) {
        return mRenderItem.F(object, object2, object3, object4, n);
    }

    public MRenderItem() {
        this(MRenderManager.O());
    }

    private MRenderItem(String[] stringArray) {
        super(MappedClasses.z8);
        String string;
        boolean bl;
        Class<Void> clazz;
        Class[] classArray;
        String[] stringArray2 = stringArray;
        if (ForgeVersion.MC_1_7_10.L()) {
            classArray = new Class[]{};
            clazz = Void.TYPE;
            bl = false;
            string = "<init>";
            MRenderItem mRenderItem = this;
            this.var_gg_vape_mapping_MappingMethod_n = mRenderItem.Y(string, bl, clazz, classArray);
            if (!Wrapper.vapeInstance.isForgeAbsent()) {
                Class[] classArray2 = new Class[]{};
                Class clazz2 = MappedClasses.z8;
                boolean bl2 = false;
                String string2 = "getInstance";
                MRenderItem mRenderItem2 = this;
                this.var_gg_vape_mapping_MappingMethod_o = this.registerStaticMethod(string2, bl2, clazz2, classArray2);
            }
            Class[] classArray3 = new Class[]{MappedClasses.uQ, MappedClasses.Dt, MappedClasses.VK, Integer.TYPE, Integer.TYPE};
            Class<Void> clazz3 = Void.TYPE;
            boolean bl3 = true;
            String string3 = "renderItemIntoGUI";
            MRenderItem mRenderItem3 = this;
            this.var_gg_vape_mapping_MappingMethod_H = this.Y(string3, bl3, clazz3, classArray3);
            Class[] classArray4 = new Class[]{MappedClasses.uQ, MappedClasses.Dt, MappedClasses.VK, Integer.TYPE, Integer.TYPE};
            Class<Void> clazz4 = Void.TYPE;
            boolean bl4 = true;
            String string4 = "renderItemOverlayIntoGUI";
            MRenderItem mRenderItem4 = this;
            this.var_gg_vape_mapping_MappingMethod_r = this.Y(string4, bl4, clazz4, classArray4);
        } else if (ForgeVersion.MC_1_20_6.d()) {
            if (ForgeVersion.MC_26_1.d()) {
                classArray = new Class[]{MappedClasses.VK, Integer.TYPE, Integer.TYPE};
                clazz = Void.TYPE;
                bl = true;
                string = "item";
                Class clazz5 = MappedClasses.var_java_lang_Class_m;
                MRenderItem mRenderItem = this;
                this.var_gg_vape_mapping_MappingMethod_d = mRenderItem.registerInstanceMethodForOwner(clazz5, string, bl, clazz, classArray);
                Class[] classArray5 = new Class[]{MappedClasses.uQ, MappedClasses.VK, Integer.TYPE, Integer.TYPE};
                Class<Void> clazz6 = Void.TYPE;
                boolean bl5 = true;
                String string5 = "itemDecorations";
                Class clazz7 = MappedClasses.var_java_lang_Class_m;
                MRenderItem mRenderItem5 = this;
                this.var_gg_vape_mapping_MappingMethod_s = this.registerInstanceMethodForOwner(clazz7, string5, bl5, clazz6, classArray5);
            } else {
                classArray = new Class[]{MappedClasses.VK, Integer.TYPE, Integer.TYPE};
                clazz = Void.TYPE;
                bl = true;
                string = "renderFakeItem";
                Class clazz8 = MappedClasses.var_java_lang_Class_m;
                MRenderItem mRenderItem = this;
                this.var_gg_vape_mapping_MappingMethod_d = mRenderItem.registerInstanceMethodForOwner(clazz8, string, bl, clazz, classArray);
                Class[] classArray6 = new Class[]{MappedClasses.uQ, MappedClasses.VK, Integer.TYPE, Integer.TYPE};
                Class<Void> clazz9 = Void.TYPE;
                boolean bl6 = true;
                String string6 = "renderItemDecorations";
                Class clazz10 = MappedClasses.var_java_lang_Class_m;
                MRenderItem mRenderItem6 = this;
                this.var_gg_vape_mapping_MappingMethod_s = this.registerInstanceMethodForOwner(clazz10, string6, bl6, clazz9, classArray6);
            }
        } else {
            classArray = new Class[]{MappedClasses.VK, Integer.TYPE, Integer.TYPE};
            clazz = Void.TYPE;
            bl = true;
            string = "renderItemIntoGUI";
            MRenderItem mRenderItem = this;
            this.var_gg_vape_mapping_MappingMethod_d = mRenderItem.Y(string, bl, clazz, classArray);
            Class[] classArray7 = new Class[]{MappedClasses.uQ, MappedClasses.VK, Integer.TYPE, Integer.TYPE};
            Class<Void> clazz11 = Void.TYPE;
            boolean bl7 = true;
            String string7 = "renderItemOverlays";
            MRenderItem mRenderItem7 = this;
            this.var_gg_vape_mapping_MappingMethod_s = this.Y(string7, bl7, clazz11, classArray7);
        }
        if (ForgeVersion.MC_1_20_6.v()) {
            Class<Float> clazz2 = Float.TYPE;
            boolean bl2 = true;
            String string2 = "zLevel";
            MRenderItem mRenderItem = this;
            this.var_gg_vape_mapping_MappingField_Q = mRenderItem.J(string2, bl2, clazz2);
        }
    }

    public static void Y(MRenderItem mRenderItem, Object object, Object object2, Object object3, int n, int n2) {
        mRenderItem.r(object, object2, object3, n, n2);
    }

    private void Z(Object object, float f) {
        this.var_gg_vape_mapping_MappingField_v.setFloat(object, f);
    }

    private float j(Object object) {
        return this.var_gg_vape_mapping_MappingField_v.getFloat(object);
    }

    public static float x(MRenderItem mRenderItem, Object object) {
        return mRenderItem.j(object);
    }

    public void M(Object object, Object object2, int n, int n2) {
        this.var_gg_vape_mapping_MappingMethod_d.invokeVoid(object, object2, n, n2);
    }

    public static MappingMethod F(MRenderItem mRenderItem) {
        return mRenderItem.var_gg_vape_mapping_MappingMethod_n;
    }

    public static void z(MRenderItem mRenderItem, Object object, Object object2, Object object3, Object object4, int n, int n2) {
        mRenderItem.i(object, object2, object3, object4, n, n2);
    }

    private void r(Object object, Object object2, Object object3, int n, int n2) {
        this.var_gg_vape_mapping_MappingMethod_s.invokeVoid(object, object2, object3, n, n2);
    }

    public static void l(MRenderItem mRenderItem, Object object, float f) {
        mRenderItem.r(object, f);
    }

    public static float I(MRenderItem mRenderItem, Object object) {
        return mRenderItem.f(object);
    }

    private void Q(Object object, Object object2, Object object3, Object object4, int n, int n2) {
        this.var_gg_vape_mapping_MappingMethod_r.invokeVoid(object, object2, object3, object4, n, n2);
    }

    private void r(Object object, float f) {
        this.var_gg_vape_mapping_MappingField_Q.setFloat(object, f);
    }
}

