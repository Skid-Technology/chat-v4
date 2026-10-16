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
import gg.vape.wrapper.impl.ForgeVersion;

public class MPlayerInfo
extends Mapping {
    private final MappingMethod var_gg_vape_mapping_MappingMethod_K;
    private final MappingField var_gg_vape_mapping_MappingField_q;
    private final MappingMethod var_gg_vape_mapping_MappingMethod_I;
    private final MappingMethod var_gg_vape_mapping_MappingMethod_D;
    private final MappingMethod var_gg_vape_mapping_MappingMethod_f;
    private static String[] var_java_lang_String_arr_p;

    public static Object Z(MPlayerInfo mPlayerInfo, Object object) {
        return mPlayerInfo.I(object);
    }

    private Object I(Object object) {
        return this.var_gg_vape_mapping_MappingMethod_K.invokeObject(object, new Object[0]);
    }

    private int L(Object object) {
        return this.var_gg_vape_mapping_MappingMethod_D.invokeInt(object, new Object[0]);
    }

    public static String[] K() {
        return var_java_lang_String_arr_p;
    }

    public static Object l(MPlayerInfo mPlayerInfo, Object object) {
        return mPlayerInfo.r(object);
    }

    public static Object W(MPlayerInfo mPlayerInfo, Object object) {
        return mPlayerInfo.e(object);
    }

    public static int H(MPlayerInfo mPlayerInfo, Object object) {
        return mPlayerInfo.L(object);
    }

    private Object r(Object object) {
        return this.var_gg_vape_mapping_MappingField_q.getObject(object);
    }

    public static Object J(MPlayerInfo mPlayerInfo, Object object) {
        return mPlayerInfo.A(object);
    }

    public static void r(String[] stringArray) {
        var_java_lang_String_arr_p = stringArray;
    }

    private Object e(Object object) {
        return this.var_gg_vape_mapping_MappingMethod_f.invokeObject(object, new Object[0]);
    }

    private Object A(Object object) {
        return this.var_gg_vape_mapping_MappingMethod_I.invokeObject(object, new Object[0]);
    }

    public MPlayerInfo() {
        super(MappedClasses.Zc);
        Class[] classArray = new Class[]{};
        Class clazz = MappedClasses.VD;
        boolean bl = true;
        String string = "getGameProfile";
        MPlayerInfo mPlayerInfo = this;
        this.var_gg_vape_mapping_MappingMethod_f = this.Y(string, bl, clazz, classArray);
        Class[] classArray2 = new Class[]{};
        Class<Integer> clazz2 = Integer.TYPE;
        boolean bl2 = true;
        String string2 = "getResponseTime";
        MPlayerInfo mPlayerInfo2 = this;
        this.var_gg_vape_mapping_MappingMethod_D = this.Y(string2, bl2, clazz2, classArray2);
        Class[] classArray3 = new Class[]{};
        Class clazz3 = MappedClasses.u6;
        String string3 = "getPlayerTeam";
        MPlayerInfo mPlayerInfo3 = this;
        this.var_gg_vape_mapping_MappingMethod_K = ((MappingMethodBuilder)this.methodBuilder(string3, clazz3, classArray3).setNameForVersion(ForgeVersion.MC_1_16_5.n(), "getTeam")).buildMethod();
        String[] stringArray = MPlayerInfo.K();
        Class clazz4 = MappedClasses.Yr;
        String string4 = "displayName";
        MPlayerInfo mPlayerInfo4 = this;
        this.var_gg_vape_mapping_MappingField_q = ((MappingFieldBuilder)this.fieldBuilder(string4, clazz4).setNameForVersion(ForgeVersion.MC_1_16_5.n(), "tabListDisplayName")).buildField();
        Class[] classArray4 = new Class[]{};
        Class clazz5 = MappedClasses.zC;
        String string5 = "getLocationSkin";
        MPlayerInfo mPlayerInfo5 = this;
        this.var_gg_vape_mapping_MappingMethod_I = ((MappingMethodBuilder)((MappingMethodBuilder)((MappingMethodBuilder)this.methodBuilder(string5, clazz5, classArray4).setNameForVersion(ForgeVersion.MC_1_20_6.n(), "getSkin")).setNameForVersion(ForgeVersion.MC_1_16_5.n(), "getSkinTextureLocation")).setTypeForVersion(ForgeVersion.MC_1_20_6.n(), MappedClasses.uZ)).buildMethod();
    }

    static {
        MPlayerInfo.r(new String[3]);
    }
}

