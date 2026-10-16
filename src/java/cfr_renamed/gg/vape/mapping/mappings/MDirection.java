/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.mapping.MappingMethodBuilder;
import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MDirection
extends Mapping {
    private MappingMethod var_gg_vape_mapping_MappingMethod_l;
    private final MappingMethod var_gg_vape_mapping_MappingMethod_m;
    private final MappingMethod var_gg_vape_mapping_MappingMethod_G;
    private final MappingMethod var_gg_vape_mapping_MappingMethod_Y;
    private MappingMethod var_gg_vape_mapping_MappingMethod_y;
    private MappingMethod var_gg_vape_mapping_MappingMethod_O;

    public Object J(Object object) {
        return this.var_gg_vape_mapping_MappingMethod_Y.invokeObject(object, new Object[0]);
    }

    public int o(Object object) {
        return this.var_gg_vape_mapping_MappingMethod_l.invokeInt(object, new Object[0]);
    }

    public Object g(Object object) {
        return this.var_gg_vape_mapping_MappingMethod_G.invokeObject(object, new Object[0]);
    }

    public int S(Object object) {
        return this.var_gg_vape_mapping_MappingMethod_y.invokeInt(object, new Object[0]);
    }

    public int z(Object object) {
        return this.var_gg_vape_mapping_MappingMethod_O.invokeInt(object, new Object[0]);
    }

    public Object w(double d, double d2, double d3) {
        return this.var_gg_vape_mapping_MappingMethod_m.invokeObject(null, d, d2, d3);
    }

    public MDirection() {
        super(MappedClasses.us);
        Class[] classArray = new Class[]{Double.TYPE, Double.TYPE, Double.TYPE};
        Class clazz = MappedClasses.us;
        String string = "func_210769_a";
        MDirection mDirection = this;
        this.var_gg_vape_mapping_MappingMethod_m = ((MappingMethodBuilder)((MappingMethodBuilder)((MappingMethodBuilder)this.methodBuilder(string, clazz, classArray).setStaticMember(true)).setNameForVersion(ForgeVersion.MC_1_21_4.n(), "getApproximateNearest")).setMappedMemberForVersion(ForgeVersion.MC_1_21_4.b(), Wrapper.isNativeAvailable)).buildMethod();
        Class[] classArray2 = new Class[]{};
        Class clazz2 = MappedClasses.u9;
        String string2 = "getAxis";
        MDirection mDirection2 = this;
        this.var_gg_vape_mapping_MappingMethod_Y = ((MappingMethodBuilder)((MappingMethodBuilder)this.methodBuilder(string2, clazz2, classArray2).setNameForVersion(ForgeVersion.MC_1_20_6.b(), "func_176740_k")).setMappedMemberForVersion(ForgeVersion.MC_1_20_6.b(), Wrapper.isNativeAvailable)).buildMethod();
        Class[] classArray3 = new Class[]{};
        Class clazz3 = MappedClasses.Vy;
        String string3 = "getAxisDirection";
        MDirection mDirection3 = this;
        this.var_gg_vape_mapping_MappingMethod_G = ((MappingMethodBuilder)((MappingMethodBuilder)this.methodBuilder(string3, clazz3, classArray3).setNameForVersion(ForgeVersion.MC_1_20_6.b(), "func_176743_c")).setMappedMemberForVersion(ForgeVersion.MC_1_20_6.b(), Wrapper.isNativeAvailable)).buildMethod();
        if (BlockData.W() != null) {
            if (ForgeVersion.MC_1_21_4.d()) {
                Class[] classArray4 = new Class[]{};
                Class<Integer> clazz4 = Integer.TYPE;
                String string4 = "getStepX";
                MDirection mDirection4 = this;
                this.var_gg_vape_mapping_MappingMethod_y = this.methodBuilder(string4, clazz4, classArray4).buildMethod();
                Class[] classArray5 = new Class[]{};
                Class<Integer> clazz5 = Integer.TYPE;
                String string5 = "getStepY";
                MDirection mDirection5 = this;
                this.var_gg_vape_mapping_MappingMethod_l = this.methodBuilder(string5, clazz5, classArray5).buildMethod();
                Class[] classArray6 = new Class[]{};
                Class<Integer> clazz6 = Integer.TYPE;
                String string6 = "getStepZ";
                MDirection mDirection6 = this;
                this.var_gg_vape_mapping_MappingMethod_O = this.methodBuilder(string6, clazz6, classArray6).buildMethod();
            }
            return;
        }
        Class[] classArray7 = new Class[]{};
        Class<Integer> clazz7 = Integer.TYPE;
        String string7 = "getStepZ";
        MDirection mDirection7 = this;
        this.var_gg_vape_mapping_MappingMethod_O = this.methodBuilder(string7, clazz7, classArray7).buildMethod();
    }
}

