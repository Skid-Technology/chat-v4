/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MGlStateManagerTexGenState
extends Mapping {
    private final static long var_long_e;
    private static boolean var_boolean_p;
    private MappingMethod var_gg_vape_mapping_MappingMethod_T;
    private MappingMethod var_gg_vape_mapping_MappingMethod_S;

    public int F(int n) {
        if (this.var_gg_vape_mapping_MappingMethod_S != null) {
            return this.var_gg_vape_mapping_MappingMethod_S.invokeInt(null, (int)var_long_e, n);
        }
        return 0;
    }

    public static boolean z() {
        return var_boolean_p;
    }

    public static void d(boolean bl) {
        var_boolean_p = bl;
    }

    public static boolean F() {
        boolean bl = MGlStateManagerTexGenState.z();
        return !bl;
    }

    public MGlStateManagerTexGenState() {
        super(MappedClasses.ZC);
        Class[] classArray = new Class[]{Integer.TYPE, Integer.TYPE};
        Class<Void> clazz = Void.TYPE;
        boolean bl = false;
        String string = "glBindSampler";
        MGlStateManagerTexGenState mGlStateManagerTexGenState = this;
        this.var_gg_vape_mapping_MappingMethod_T = this.registerStaticMethod(string, bl, clazz, classArray);
        Class[] classArray2 = new Class[]{Integer.TYPE, Integer.TYPE};
        Class<Integer> clazz2 = Integer.TYPE;
        boolean bl2 = false;
        String string2 = "glGetIntegeri";
        MGlStateManagerTexGenState mGlStateManagerTexGenState2 = this;
        this.var_gg_vape_mapping_MappingMethod_S = this.registerStaticMethod(string2, bl2, clazz2, classArray2);
    }

    public void K(int n, int n2) {
        if (this.var_gg_vape_mapping_MappingMethod_T != null) {
            this.var_gg_vape_mapping_MappingMethod_T.invokeVoid(null, n, n2);
        }
    }

    static {
        MGlStateManagerTexGenState.d(true);
        var_long_e = 8525806735951169817L;
    }
}

