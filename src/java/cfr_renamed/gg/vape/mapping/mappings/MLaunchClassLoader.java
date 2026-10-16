/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class MLaunchClassLoader
extends Mapping {
    private MappingField var_gg_vape_mapping_MappingField_g;
    private MappingField var_gg_vape_mapping_MappingField_C;
    private MappingField var_gg_vape_mapping_MappingField_Y;
    public MappingMethod var_gg_vape_mapping_MappingMethod_I;
    private MappingField var_gg_vape_mapping_MappingField_L;
    private static int[] var_int_arr_Q;

    public Object getInstance() {
        if (this.var_gg_vape_mapping_MappingField_g == null) {
            return null;
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            return this.var_gg_vape_mapping_MappingField_g.getObject(this.var_gg_vape_mapping_MappingField_C.getObject(null));
        }
        return this.var_gg_vape_mapping_MappingField_g.getObject(null);
    }

    public Map cachedClasses(Object object) {
        return this.var_gg_vape_mapping_MappingField_Y == null ? null : (Map)this.var_gg_vape_mapping_MappingField_Y.getObject(object);
    }

    public boolean supportsLegacyClassCache() {
        return this.var_gg_vape_mapping_MappingField_g != null && this.var_gg_vape_mapping_MappingField_Y != null && this.var_gg_vape_mapping_MappingField_L != null;
    }

    public static Set l(MLaunchClassLoader mLaunchClassLoader, Object object) {
        return mLaunchClassLoader.V(object);
    }

    public MLaunchClassLoader() {
        this(MLaunchClassLoader.N());
    }

    private MLaunchClassLoader(int[] nArray) {
        super(MappedClasses.uG);
        int[] nArray2 = nArray;
        if (ForgeVersion.MC_1_16_5.d()) {
            Class clazz = MappedClasses.D1;
            boolean bl = false;
            String string = "INSTANCE";
            Class clazz2 = MappedClasses.D1;
            MLaunchClassLoader mLaunchClassLoader = this;
            this.var_gg_vape_mapping_MappingField_C = mLaunchClassLoader.registerStaticFieldForOwner(clazz2, string, bl, clazz);
            Class clazz3 = MappedClasses.uG;
            boolean bl2 = false;
            String string2 = "classLoader";
            Class clazz4 = MappedClasses.D1;
            MLaunchClassLoader mLaunchClassLoader2 = this;
            this.var_gg_vape_mapping_MappingField_g = this.registerInstanceFieldForOwner(clazz4, string2, bl2, clazz3);
            Class[] classArray = new Class[]{String.class, Function.class};
            Class<Class> clazz5 = Class.class;
            boolean bl3 = false;
            String string3 = "loadClass";
            MLaunchClassLoader mLaunchClassLoader3 = this;
            this.var_gg_vape_mapping_MappingMethod_I = this.Y(string3, bl3, clazz5, classArray);
        } else {
            if (!(MLaunchClassLoader.hasDeclaredField(MappedClasses.D1, "classLoader") && MLaunchClassLoader.hasDeclaredField(MappedClasses.uG, "cachedClasses") && MLaunchClassLoader.hasDeclaredField(MappedClasses.uG, "invalidClasses"))) {
                return;
            }
            Class clazz = MappedClasses.uG;
            boolean bl = false;
            String string = "classLoader";
            Class clazz6 = MappedClasses.D1;
            MLaunchClassLoader mLaunchClassLoader = this;
            this.var_gg_vape_mapping_MappingField_g = mLaunchClassLoader.registerStaticFieldForOwner(clazz6, string, bl, clazz);
            Class<Map> clazz7 = Map.class;
            boolean bl4 = false;
            String string4 = "cachedClasses";
            MLaunchClassLoader mLaunchClassLoader4 = this;
            this.var_gg_vape_mapping_MappingField_Y = this.J(string4, bl4, clazz7);
            Class<Set> clazz8 = Set.class;
            boolean bl5 = false;
            String string5 = "invalidClasses";
            MLaunchClassLoader mLaunchClassLoader5 = this;
            this.var_gg_vape_mapping_MappingField_L = this.J(string5, bl5, clazz8);
        }
    }

    public static void Q(int[] nArray) {
        var_int_arr_Q = nArray;
    }

    public static int[] N() {
        return var_int_arr_Q;
    }

    private Set V(Object object) {
        return this.var_gg_vape_mapping_MappingField_L == null ? null : (Set)this.var_gg_vape_mapping_MappingField_L.getObject(object);
    }

    private static boolean hasDeclaredField(Class<?> ownerClass, String fieldName) {
        if (ownerClass == null) {
            return false;
        }
        try {
            ownerClass.getDeclaredField(fieldName);
            return true;
        }
        catch (NoSuchFieldException ignored) {
            return false;
        }
    }

    static {
        MLaunchClassLoader.Q(new int[2]);
    }
}

