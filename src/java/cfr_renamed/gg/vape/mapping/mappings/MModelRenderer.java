/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

public class MModelRenderer
extends Mapping {
    private static int[] var_int_arr_y;
    private MappingField var_gg_vape_mapping_MappingField_J;
    private MappingField var_gg_vape_mapping_MappingField_o;
    private MappingField var_gg_vape_mapping_MappingField_v;
    private MappingField var_gg_vape_mapping_MappingField_q;
    private MappingField var_gg_vape_mapping_MappingField_x;
    private MappingField var_gg_vape_mapping_MappingField_r;
    private MappingField var_gg_vape_mapping_MappingField_D;
    private MappingField var_gg_vape_mapping_MappingField_l;
    private MappingField var_gg_vape_mapping_MappingField_w;
    private MappingField var_gg_vape_mapping_MappingField_F;
    private MappingField var_gg_vape_mapping_MappingField_I;
    private MappingField var_gg_vape_mapping_MappingField_e;
    private MappingField var_gg_vape_mapping_MappingField_a;

    private float K(Object object) {
        return this.var_gg_vape_mapping_MappingField_o.getFloat(object);
    }

    private void g(Object object, boolean bl) {
        this.var_gg_vape_mapping_MappingField_w.setBoolean(object, bl);
    }

    public static float U(MModelRenderer mModelRenderer, Object object) {
        return mModelRenderer.b(object);
    }

    private float e(Object object) {
        return this.var_gg_vape_mapping_MappingField_e.getFloat(object);
    }

    public static float v(MModelRenderer mModelRenderer, Object object) {
        return mModelRenderer.x(object);
    }

    private void A(Object object, boolean bl) {
        this.var_gg_vape_mapping_MappingField_J.setBoolean(object, bl);
    }

    private float b(Object object) {
        return this.var_gg_vape_mapping_MappingField_I.getFloat(object);
    }

    public static float i(MModelRenderer mModelRenderer, Object object) {
        return mModelRenderer.M(object);
    }

    public static float t(MModelRenderer mModelRenderer, Object object) {
        return mModelRenderer.r(object);
    }

    public static float h(MModelRenderer mModelRenderer, Object object) {
        return mModelRenderer.e(object);
    }

    public static void P(int[] nArray) {
        var_int_arr_y = nArray;
    }

    private int o(Object object) {
        return this.var_gg_vape_mapping_MappingField_D.getInt(object);
    }

    public MModelRenderer() {
        this(MModelRenderer.T());
    }

    private MModelRenderer(int[] nArray) {
        super(MappedClasses.Yd);
        if (nArray != null) {
            MModelRenderer mModelRenderer;
            String string;
            boolean bl;
            Class<Number> clazz;
            if (ForgeVersion.MC_1_17.d()) {
                clazz = Integer.TYPE;
                bl = true;
                string = "xTexOffs";
                Class clazz2 = MappedClasses.var_java_lang_Class_q;
                MModelRenderer mModelRenderer2 = this;
                this.var_gg_vape_mapping_MappingField_r = mModelRenderer2.registerInstanceFieldForOwner(clazz2, string, bl, clazz);
                Class<Integer> clazz3 = Integer.TYPE;
                boolean bl2 = true;
                String string2 = "yTexOffs";
                Class clazz4 = MappedClasses.var_java_lang_Class_q;
                MModelRenderer mModelRenderer22 = this;
                this.var_gg_vape_mapping_MappingField_D = this.registerInstanceFieldForOwner(clazz4, string2, bl2, clazz3);
            } else {
                clazz = Integer.TYPE;
                bl = true;
                string = "textureOffsetX";
                mModelRenderer = this;
                this.var_gg_vape_mapping_MappingField_r = mModelRenderer.J(string, bl, clazz);
                Class<Integer> clazz5 = Integer.TYPE;
                boolean bl3 = true;
                String string3 = "textureOffsetY";
                MModelRenderer mModelRenderer3 = this;
                this.var_gg_vape_mapping_MappingField_D = this.J(string3, bl3, clazz5);
            }
            clazz = Float.TYPE;
            bl = true;
            string = "rotateAngleX";
            mModelRenderer = this;
            this.var_gg_vape_mapping_MappingField_v = mModelRenderer.J(string, bl, clazz);
            Class<Float> clazz6 = Float.TYPE;
            boolean bl4 = true;
            String string4 = "rotateAngleY";
            MModelRenderer mModelRenderer4 = this;
            this.var_gg_vape_mapping_MappingField_o = this.J(string4, bl4, clazz6);
            Class<Float> clazz7 = Float.TYPE;
            boolean bl5 = true;
            String string5 = "rotateAngleZ";
            MModelRenderer mModelRenderer5 = this;
            this.var_gg_vape_mapping_MappingField_I = this.J(string5, bl5, clazz7);
            Class<Float> clazz8 = Float.TYPE;
            boolean bl6 = true;
            String string6 = "rotationPointX";
            MModelRenderer mModelRenderer6 = this;
            this.var_gg_vape_mapping_MappingField_e = this.J(string6, bl6, clazz8);
            Class<Float> clazz9 = Float.TYPE;
            boolean bl7 = true;
            String string7 = "rotationPointY";
            MModelRenderer mModelRenderer7 = this;
            this.var_gg_vape_mapping_MappingField_a = this.J(string7, bl7, clazz9);
            Class<Float> clazz10 = Float.TYPE;
            boolean bl8 = true;
            String string8 = "rotationPointZ";
            MModelRenderer mModelRenderer8 = this;
            this.var_gg_vape_mapping_MappingField_l = this.J(string8, bl8, clazz10);
            Class<Boolean> clazz11 = Boolean.TYPE;
            boolean bl9 = true;
            String string9 = "showModel";
            MModelRenderer mModelRenderer9 = this;
            this.var_gg_vape_mapping_MappingField_J = this.J(string9, bl9, clazz11);
            if (!ForgeVersion.MC_1_16_5.d()) {
                Class<Float> clazz12 = Float.TYPE;
                boolean bl10 = true;
                String string10 = "offsetX";
                MModelRenderer mModelRenderer10 = this;
                this.var_gg_vape_mapping_MappingField_F = this.J(string10, bl10, clazz12);
                Class<Float> clazz13 = Float.TYPE;
                boolean bl11 = true;
                String string11 = "offsetY";
                MModelRenderer mModelRenderer11 = this;
                this.var_gg_vape_mapping_MappingField_x = this.J(string11, bl11, clazz13);
                Class<Float> clazz14 = Float.TYPE;
                boolean bl12 = true;
                String string12 = "offsetZ";
                MModelRenderer mModelRenderer12 = this;
                this.var_gg_vape_mapping_MappingField_q = this.J(string12, bl12, clazz14);
                Class<Boolean> clazz15 = Boolean.TYPE;
                boolean bl13 = true;
                String string13 = "isHidden";
                MModelRenderer mModelRenderer13 = this;
                this.var_gg_vape_mapping_MappingField_w = this.J(string13, bl13, clazz15);
            }
            if (GuiComponent.getLegacyComponentState() == null) {
                MModelRenderer.P(new int[4]);
            }
            return;
        }
        Class<Integer> clazz = Integer.TYPE;
        boolean bl = true;
        String string = "textureOffsetY";
        MModelRenderer mModelRenderer = this;
        this.var_gg_vape_mapping_MappingField_D = mModelRenderer.J(string, bl, clazz);
        Class<Float> clazz16 = Float.TYPE;
        boolean bl14 = true;
        String string14 = "rotateAngleX";
        MModelRenderer mModelRenderer14 = this;
        this.var_gg_vape_mapping_MappingField_v = this.J(string14, bl14, clazz16);
        Class<Float> clazz17 = Float.TYPE;
        boolean bl15 = true;
        String string15 = "rotateAngleY";
        MModelRenderer mModelRenderer15 = this;
        this.var_gg_vape_mapping_MappingField_o = this.J(string15, bl15, clazz17);
        Class<Float> clazz18 = Float.TYPE;
        boolean bl16 = true;
        String string16 = "rotateAngleZ";
        MModelRenderer mModelRenderer16 = this;
        this.var_gg_vape_mapping_MappingField_I = this.J(string16, bl16, clazz18);
        Class<Float> clazz19 = Float.TYPE;
        boolean bl17 = true;
        String string17 = "rotationPointX";
        MModelRenderer mModelRenderer17 = this;
        this.var_gg_vape_mapping_MappingField_e = this.J(string17, bl17, clazz19);
        Class<Float> clazz20 = Float.TYPE;
        boolean bl18 = true;
        String string18 = "rotationPointY";
        MModelRenderer mModelRenderer18 = this;
        this.var_gg_vape_mapping_MappingField_a = this.J(string18, bl18, clazz20);
        Class<Float> clazz21 = Float.TYPE;
        boolean bl19 = true;
        String string19 = "rotationPointZ";
        MModelRenderer mModelRenderer19 = this;
        this.var_gg_vape_mapping_MappingField_l = this.J(string19, bl19, clazz21);
        Class<Boolean> clazz22 = Boolean.TYPE;
        boolean bl20 = true;
        String string20 = "showModel";
        MModelRenderer mModelRenderer20 = this;
        this.var_gg_vape_mapping_MappingField_J = this.J(string20, bl20, clazz22);
        Class<Boolean> clazz23 = Boolean.TYPE;
        boolean bl21 = true;
        String string21 = "isHidden";
        MModelRenderer mModelRenderer21 = this;
        this.var_gg_vape_mapping_MappingField_w = this.J(string21, bl21, clazz23);
        if (GuiComponent.getLegacyComponentState() == null) {
            MModelRenderer.P(new int[4]);
        }
        this.var_gg_vape_mapping_MappingField_r = null;
    }

    public static int j(MModelRenderer mModelRenderer, Object object) {
        return mModelRenderer.d(object);
    }

    public static float k(MModelRenderer mModelRenderer, Object object) {
        return mModelRenderer.I(object);
    }

    public static int[] T() {
        return var_int_arr_y;
    }

    private int d(Object object) {
        return this.var_gg_vape_mapping_MappingField_r.getInt(object);
    }

    private float r(Object object) {
        return this.var_gg_vape_mapping_MappingField_l.getFloat(object);
    }

    private float I(Object object) {
        return this.var_gg_vape_mapping_MappingField_q.getFloat(object);
    }

    public static float s(MModelRenderer mModelRenderer, Object object) {
        return mModelRenderer.K(object);
    }

    public static float b(MModelRenderer mModelRenderer, Object object) {
        return mModelRenderer.c(object);
    }

    public static int z(MModelRenderer mModelRenderer, Object object) {
        return mModelRenderer.o(object);
    }

    private float P(Object object) {
        return this.var_gg_vape_mapping_MappingField_v.getFloat(object);
    }

    private float x(Object object) {
        return this.var_gg_vape_mapping_MappingField_a.getFloat(object);
    }

    public static float c(MModelRenderer mModelRenderer, Object object) {
        return mModelRenderer.P(object);
    }

    private float c(Object object) {
        return this.var_gg_vape_mapping_MappingField_F.getFloat(object);
    }

    public static void j(MModelRenderer mModelRenderer, Object object, boolean bl) {
        mModelRenderer.A(object, bl);
    }

    public static void r(MModelRenderer mModelRenderer, Object object, boolean bl) {
        mModelRenderer.g(object, bl);
    }

    private float M(Object object) {
        return this.var_gg_vape_mapping_MappingField_x.getFloat(object);
    }

    static {
        MModelRenderer.P(new int[3]);
    }
}

