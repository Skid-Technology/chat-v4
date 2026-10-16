/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MRenderStateBridge
extends Mapping {
    private MappingField var_gg_vape_mapping_MappingField_z;
    private final static String var_java_lang_String_c;
    private MappingField var_gg_vape_mapping_MappingField_q;
    private MappingField var_gg_vape_mapping_MappingField_h;
    private MappingField var_gg_vape_mapping_MappingField_D;
    private static int var_int_a;

    private double e(Object object) {
        return this.var_gg_vape_mapping_MappingField_D.getDouble(object);
    }

    private double Z(Object object) {
        return this.var_gg_vape_mapping_MappingField_h.getDouble(object);
    }

    public Object u(Object object) {
        return this.var_gg_vape_mapping_MappingField_z.getObject(object);
    }

    public static double Z(MRenderStateBridge mRenderStateBridge, Object object) {
        return mRenderStateBridge.e(object);
    }

    public static int h() {
        int n = MRenderStateBridge.e();
        return 57;
    }

    public static void f(int n) {
        var_int_a = n;
    }

    private double M(Object object) {
        return this.var_gg_vape_mapping_MappingField_q.getDouble(object);
    }

    public static double Y(MRenderStateBridge mRenderStateBridge, Object object) {
        return mRenderStateBridge.M(object);
    }

    public void q(Object object, Object object2) {
        this.var_gg_vape_mapping_MappingField_z.setObject(object, object2);
    }

    public static int e() {
        return var_int_a;
    }

    public MRenderStateBridge() {
        this(MRenderStateBridge.e());
    }

    private MRenderStateBridge(int n) {
        super(MappedClasses.qX);
        Class<Double> clazz = Double.TYPE;
        boolean bl = true;
        String string = "x";
        MRenderStateBridge mRenderStateBridge = this;
        this.var_gg_vape_mapping_MappingField_D = this.J(string, bl, clazz);
        Class<Double> clazz2 = Double.TYPE;
        boolean bl2 = true;
        String string2 = "y";
        MRenderStateBridge mRenderStateBridge2 = this;
        this.var_gg_vape_mapping_MappingField_q = this.J(string2, bl2, clazz2);
        Class<Double> clazz3 = Double.TYPE;
        boolean bl3 = true;
        String string3 = "z";
        MRenderStateBridge mRenderStateBridge3 = this;
        this.var_gg_vape_mapping_MappingField_h = this.J(string3, bl3, clazz3);
        int n2 = n;
        Class clazz4 = MappedClasses.Yr;
        boolean bl4 = true;
        String string4 = var_java_lang_String_c;
        MRenderStateBridge mRenderStateBridge4 = this;
        this.var_gg_vape_mapping_MappingField_z = this.J(string4, bl4, clazz4);
    }

    public static double g(MRenderStateBridge mRenderStateBridge, Object object) {
        return mRenderStateBridge.Z(object);
    }

    static {
        MRenderStateBridge.f(0);
        var_java_lang_String_c = "nameTag";
    }
}

