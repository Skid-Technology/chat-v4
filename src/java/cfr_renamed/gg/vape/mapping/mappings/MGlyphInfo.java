/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.mappings.MGlyphProvider;
import gg.vape.ui.click.component.GuiComponent;

public class MGlyphInfo
extends Mapping {
    private MappingField var_gg_vape_mapping_MappingField_m;
    private MappingField var_gg_vape_mapping_MappingField_F;
    private MappingField var_gg_vape_mapping_MappingField_y;
    private MappingField var_gg_vape_mapping_MappingField_A;
    private MappingField var_gg_vape_mapping_MappingField_k;
    private MappingField var_gg_vape_mapping_MappingField_b;
    private MappingField var_gg_vape_mapping_MappingField_n;
    private MappingField var_gg_vape_mapping_MappingField_h;
    private MappingField var_gg_vape_mapping_MappingField_G;
    private MappingField var_gg_vape_mapping_MappingField_q;
    private MappingMethod var_gg_vape_mapping_MappingMethod_Z;

    public float K(Object object) {
        if (this.var_gg_vape_mapping_MappingField_G == null) {
            return 0.0f;
        }
        return this.var_gg_vape_mapping_MappingField_G.getFloat(object);
    }

    public MGlyphInfo() {
        super(MappedClasses.var_java_lang_Class_v);
        Class<Float> clazz = Float.TYPE;
        boolean bl = true;
        String string = "u0";
        MGlyphInfo mGlyphInfo = this;
        this.var_gg_vape_mapping_MappingField_m = this.J(string, bl, clazz);
        Class<Float> clazz2 = Float.TYPE;
        boolean bl2 = true;
        String string2 = "u1";
        MGlyphInfo mGlyphInfo2 = this;
        this.var_gg_vape_mapping_MappingField_h = this.J(string2, bl2, clazz2);
        Class<Float> clazz3 = Float.TYPE;
        boolean bl3 = true;
        String string3 = "v0";
        MGlyphInfo mGlyphInfo3 = this;
        this.var_gg_vape_mapping_MappingField_b = this.J(string3, bl3, clazz3);
        Class<Float> clazz4 = Float.TYPE;
        boolean bl4 = true;
        String string4 = "v1";
        MGlyphInfo mGlyphInfo4 = this;
        this.var_gg_vape_mapping_MappingField_n = this.J(string4, bl4, clazz4);
        Class<Float> clazz5 = Float.TYPE;
        boolean bl5 = true;
        String string5 = "left";
        MGlyphInfo mGlyphInfo5 = this;
        this.var_gg_vape_mapping_MappingField_F = this.J(string5, bl5, clazz5);
        Class<Float> clazz6 = Float.TYPE;
        boolean bl6 = true;
        String string6 = "right";
        MGlyphInfo mGlyphInfo6 = this;
        this.var_gg_vape_mapping_MappingField_A = this.J(string6, bl6, clazz6);
        if (MGlyphProvider.getGlyphSourceControlFlowState() != null) {
            Class<Float> clazz7 = Float.TYPE;
            boolean bl7 = true;
            String string7 = "up";
            MGlyphInfo mGlyphInfo7 = this;
            this.var_gg_vape_mapping_MappingField_y = this.J(string7, bl7, clazz7);
            Class<Float> clazz8 = Float.TYPE;
            boolean bl8 = true;
            String string8 = "down";
            MGlyphInfo mGlyphInfo8 = this;
            this.var_gg_vape_mapping_MappingField_G = this.J(string8, bl8, clazz8);
            Class clazz9 = MappedClasses.var_java_lang_Class_g;
            boolean bl9 = true;
            String string9 = "info";
            MGlyphInfo mGlyphInfo9 = this;
            this.var_gg_vape_mapping_MappingField_k = this.J(string9, bl9, clazz9);
            Class clazz10 = MappedClasses.GPU_TEXTURE_VIEW;
            boolean bl10 = true;
            String string10 = "textureView";
            MGlyphInfo mGlyphInfo10 = this;
            this.var_gg_vape_mapping_MappingField_q = this.J(string10, bl10, clazz10);
            Class[] classArray = new Class[]{};
            Class clazz11 = MappedClasses.var_java_lang_Class_g;
            boolean bl11 = true;
            String string11 = "info";
            MGlyphInfo mGlyphInfo11 = this;
            this.var_gg_vape_mapping_MappingMethod_Z = this.Y(string11, bl11, clazz11, classArray);
            GuiComponent.setLegacyComponentState(new GuiComponent[3]);
            return;
        }
        Class<Float> clazz12 = Float.TYPE;
        boolean bl12 = true;
        String string12 = "up";
        MGlyphInfo mGlyphInfo12 = this;
        this.var_gg_vape_mapping_MappingField_y = this.J(string12, bl12, clazz12);
        Class<Float> clazz13 = Float.TYPE;
        boolean bl13 = true;
        String string13 = "down";
        MGlyphInfo mGlyphInfo13 = this;
        this.var_gg_vape_mapping_MappingField_G = this.J(string13, bl13, clazz13);
        Class clazz14 = MappedClasses.var_java_lang_Class_g;
        boolean bl14 = true;
        String string14 = "info";
        MGlyphInfo mGlyphInfo14 = this;
        this.var_gg_vape_mapping_MappingField_k = this.J(string14, bl14, clazz14);
        Class clazz15 = MappedClasses.GPU_TEXTURE_VIEW;
        boolean bl15 = true;
        String string15 = "textureView";
        MGlyphInfo mGlyphInfo15 = this;
        this.var_gg_vape_mapping_MappingField_q = this.J(string15, bl15, clazz15);
        Class[] classArray = new Class[]{};
        Class clazz16 = MappedClasses.var_java_lang_Class_g;
        boolean bl16 = true;
        String string16 = "info";
        MGlyphInfo mGlyphInfo16 = this;
        this.var_gg_vape_mapping_MappingMethod_Z = this.Y(string16, bl16, clazz16, classArray);
    }

    public float h(Object object) {
        if (this.var_gg_vape_mapping_MappingField_n == null) {
            return 0.0f;
        }
        return this.var_gg_vape_mapping_MappingField_n.getFloat(object);
    }

    public Object r$src$Ljava_lang_Object_$1r6sqxs(Object object) {
        if (this.var_gg_vape_mapping_MappingField_q == null) {
            return null;
        }
        return this.var_gg_vape_mapping_MappingField_q.getObject(object);
    }

    public float v(Object object) {
        if (this.var_gg_vape_mapping_MappingField_b == null) {
            return 0.0f;
        }
        return this.var_gg_vape_mapping_MappingField_b.getFloat(object);
    }

    public float r(Object object) {
        if (this.var_gg_vape_mapping_MappingField_F == null) {
            return 0.0f;
        }
        return this.var_gg_vape_mapping_MappingField_F.getFloat(object);
    }

    public Object H(Object object) {
        if (this.var_gg_vape_mapping_MappingMethod_Z != null && !this.var_gg_vape_mapping_MappingMethod_Z.hasResolutionFailed()) {
            return this.var_gg_vape_mapping_MappingMethod_Z.invokeObject(object, new Object[0]);
        }
        if (this.var_gg_vape_mapping_MappingField_k == null) {
            return null;
        }
        return this.var_gg_vape_mapping_MappingField_k.getObject(object);
    }

    public float j(Object object) {
        if (this.var_gg_vape_mapping_MappingField_A == null) {
            return 0.0f;
        }
        return this.var_gg_vape_mapping_MappingField_A.getFloat(object);
    }

    public float M(Object object) {
        if (this.var_gg_vape_mapping_MappingField_m == null) {
            return 0.0f;
        }
        return this.var_gg_vape_mapping_MappingField_m.getFloat(object);
    }

    public float g(Object object) {
        if (this.var_gg_vape_mapping_MappingField_h == null) {
            return 0.0f;
        }
        return this.var_gg_vape_mapping_MappingField_h.getFloat(object);
    }

    public float N(Object object) {
        if (this.var_gg_vape_mapping_MappingField_y == null) {
            return 0.0f;
        }
        return this.var_gg_vape_mapping_MappingField_y.getFloat(object);
    }
}

