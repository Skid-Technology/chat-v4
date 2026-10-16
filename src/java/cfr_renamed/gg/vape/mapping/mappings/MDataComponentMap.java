/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.Set;

public class MDataComponentMap
extends Mapping {
    private MappingMethod var_gg_vape_mapping_MappingMethod_D;
    private MappingField var_gg_vape_mapping_MappingField_T;
    private MappingMethod var_gg_vape_mapping_MappingMethod_F;
    private MappingMethod var_gg_vape_mapping_MappingMethod_j;
    private static boolean var_boolean_n;

    public boolean V(Object object, Object object2) {
        return this.var_gg_vape_mapping_MappingMethod_D.invokeBoolean(object, object2);
    }

    public static boolean N() {
        boolean bl = MDataComponentMap.v();
        return false;
    }

    public Object j(Object object, Object object2) {
        return this.var_gg_vape_mapping_MappingMethod_F.invokeObject(object, object2);
    }

    private Object N$src$Ljava_lang_Object_$1l5xr4h() {
        return this.var_gg_vape_mapping_MappingField_T.getObject(null);
    }

    public static Object m(MDataComponentMap mDataComponentMap) {
        return mDataComponentMap.N$src$Ljava_lang_Object_$1l5xr4h();
    }

    public static Object w(MDataComponentMap mDataComponentMap, Object object) {
        return mDataComponentMap.l(object);
    }

    public MDataComponentMap() {
        this(MDataComponentMap.N());
    }

    private MDataComponentMap(boolean bl) {
        super(MappedClasses.zD);
        MDataComponentMap mDataComponentMap;
        String string;
        Class<Object> clazz;
        Class[] classArray;
        if (bl) {
            GuiComponent.setLegacyComponentState(new GuiComponent[5]);
            Class[] classArray2 = new Class[]{MappedClasses.Fz};
            Class<Object> clazz2 = Object.class;
            boolean bl2 = true;
            String string2 = "get";
            MDataComponentMap mDataComponentMap2 = this;
            this.var_gg_vape_mapping_MappingMethod_F = mDataComponentMap2.Y(string2, bl2, clazz2, classArray2);
            Class[] classArray22 = new Class[]{};
            Class<Set> clazz22 = Set.class;
            boolean bl3 = true;
            String string22 = "keySet";
            MDataComponentMap mDataComponentMap22 = this;
            this.var_gg_vape_mapping_MappingMethod_j = this.Y(string22, bl3, clazz22, classArray22);
            Class[] classArray3 = new Class[]{MappedClasses.Fz};
            Class<Boolean> clazz3 = Boolean.TYPE;
            boolean bl4 = true;
            String string3 = "has";
            MDataComponentMap mDataComponentMap3 = this;
            this.var_gg_vape_mapping_MappingMethod_D = this.Y(string3, bl4, clazz3, classArray3);
            Class clazz4 = MappedClasses.zD;
            boolean bl5 = true;
            String string4 = "EMPTY";
            MDataComponentMap mDataComponentMap4 = this;
            this.var_gg_vape_mapping_MappingField_T = this.registerStaticField(string4, bl5, clazz4);
            return;
        }
        if (ForgeVersion.MC_1_21_6.d()) {
            classArray = new Class[]{MappedClasses.Fz};
            clazz = Object.class;
            boolean bl6 = true;
            string = "get";
            Class clazz5 = MappedClasses.uS;
            MDataComponentMap mDataComponentMap3 = this;
            this.var_gg_vape_mapping_MappingMethod_F = mDataComponentMap3.registerInstanceMethodForOwner(clazz5, string, bl6, clazz, classArray);
        } else {
            classArray = new Class[]{MappedClasses.Fz};
            clazz = Object.class;
            boolean bl7 = true;
            string = "get";
            mDataComponentMap = this;
            this.var_gg_vape_mapping_MappingMethod_F = mDataComponentMap.Y(string, bl7, clazz, classArray);
        }
        classArray = new Class[]{};
        clazz = Set.class;
        boolean bl8 = true;
        string = "keySet";
        mDataComponentMap = this;
        this.var_gg_vape_mapping_MappingMethod_j = mDataComponentMap.Y(string, bl8, clazz, classArray);
        Class[] classArray4 = new Class[]{MappedClasses.Fz};
        Class<Boolean> clazz6 = Boolean.TYPE;
        boolean bl9 = true;
        String string5 = "has";
        MDataComponentMap mDataComponentMap5 = this;
        this.var_gg_vape_mapping_MappingMethod_D = this.Y(string5, bl9, clazz6, classArray4);
        Class clazz7 = MappedClasses.zD;
        boolean bl10 = true;
        String string6 = "EMPTY";
        MDataComponentMap mDataComponentMap6 = this;
        this.var_gg_vape_mapping_MappingField_T = this.registerStaticField(string6, bl10, clazz7);
    }

    public static boolean v() {
        return var_boolean_n;
    }

    private Object l(Object object) {
        return this.var_gg_vape_mapping_MappingMethod_j.invokeObject(object, new Object[0]);
    }

    public static void d(boolean bl) {
        var_boolean_n = bl;
    }

    static {
        MDataComponentMap.d(true);
    }
}

