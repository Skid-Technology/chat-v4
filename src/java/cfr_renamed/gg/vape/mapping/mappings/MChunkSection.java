/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingFieldBuilder;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.MappingMethodBuilder;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MChunkSection
extends Mapping {
    private MappingField var_gg_vape_mapping_MappingField_r;
    private MappingMethod var_gg_vape_mapping_MappingMethod_y;
    private static String var_java_lang_String_O;
    private final MappingMethod var_gg_vape_mapping_MappingMethod_Q;
    private MappingField var_gg_vape_mapping_MappingField_z;

    public int r(Object object) {
        return this.var_gg_vape_mapping_MappingField_r.getInt(object);
    }

    public void K(Object object, int n, int n2, int n3, Object object2) {
        this.var_gg_vape_mapping_MappingMethod_Q.invokeVoid(object, n, n2, n3, object2);
    }

    public static void G(String string) {
        var_java_lang_String_O = string;
    }

    public char[] L(Object object) {
        return this.var_gg_vape_mapping_MappingField_z.getCharArray(object);
    }

    public static String k() {
        return var_java_lang_String_O;
    }

    public MChunkSection() {
        this(MChunkSection.k());
    }

    private MChunkSection(String string) {
        super(MappedClasses.De);
        MChunkSection mChunkSection;
        Class[] classArray;
        String string2 = string;
        if (ForgeVersion.MC_1_20_6.v()) {
            classArray = new Class[]{Integer.TYPE, Boolean.TYPE};
            MChunkSection mChunkSection2 = this;
            this.var_gg_vape_mapping_MappingMethod_y = mChunkSection2.constructorBuilder(classArray).setParameterTypesForVersion(ForgeVersion.MC_1_16_5.n(), Integer.TYPE).buildMethod();
            Class<Integer> clazz = Integer.TYPE;
            String string3 = "yBase";
            MChunkSection mChunkSection22 = this;
            this.var_gg_vape_mapping_MappingField_r = ((MappingFieldBuilder)((MappingFieldBuilder)this.fieldBuilder(string3, clazz).setNameForVersion(ForgeVersion.MC_1_16_5.n(), "field_76684_a")).setMappedMemberForVersion(ForgeVersion.MC_1_16_5.n(), Wrapper.isNativeAvailable)).buildField();
        }
        if (ForgeVersion.MC_1_12_2.v()) {
            Class<char[]> clazz = char[].class;
            boolean bl = true;
            String string4 = "data";
            mChunkSection = this;
            this.var_gg_vape_mapping_MappingField_z = mChunkSection.J(string4, bl, clazz);
        }
        classArray = new Class[]{Integer.TYPE, Integer.TYPE, Integer.TYPE, MappedClasses.Vv};
        Class<Void> clazz = Void.TYPE;
        String string5 = "set";
        mChunkSection = this;
        this.var_gg_vape_mapping_MappingMethod_Q = ((MappingMethodBuilder)((MappingMethodBuilder)mChunkSection.methodBuilder(string5, clazz, classArray).setTypeForVersion(ForgeVersion.MC_1_16_5.n(), MappedClasses.Zl)).setNameForVersion(ForgeVersion.MC_1_16_5.n(), "setBlockState")).setParameterTypesForVersion(ForgeVersion.MC_1_16_5.n(), Integer.TYPE, Integer.TYPE, Integer.TYPE, MappedClasses.Zl, Boolean.TYPE).buildMethod();
    }

    public Object J(Object object, int n, int n2, int n3, Object object2, boolean bl) {
        return this.var_gg_vape_mapping_MappingMethod_Q.invokeObject(object, n, n2, n3, object2, bl);
    }

    public Object i(int n) {
        return this.var_gg_vape_mapping_MappingMethod_y.newInstance(n);
    }

    public Object U(int n, boolean bl) {
        return this.var_gg_vape_mapping_MappingMethod_y.newInstance(n, bl);
    }

    static {
        MChunkSection.G(null);
    }
}

