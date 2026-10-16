/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.impl.ForgeVersion;

public class MMutableTextComponent
extends Mapping {
    private static int[] var_int_arr_W;
    public MappingMethod var_gg_vape_mapping_MappingMethod_r;
    public MappingMethod var_gg_vape_mapping_MappingMethod_e;

    public Object p(Object object, Object object2) {
        return this.var_gg_vape_mapping_MappingMethod_r.invokeObject(object, object2);
    }

    public static int[] e() {
        return var_int_arr_W;
    }

    public static void U(int[] nArray) {
        var_int_arr_W = nArray;
    }

    public MMutableTextComponent() {
        this(MMutableTextComponent.e());
    }

    private MMutableTextComponent(int[] nArray) {
        super(MappedClasses.qQ);
        if (nArray != null) {
            if (ForgeVersion.MC_1_16_5.d()) {
                Class[] classArray = new Class[]{MappedClasses.Yr};
                Class clazz = MappedClasses.YO;
                boolean bl = true;
                String string = "append";
                MMutableTextComponent mMutableTextComponent = this;
                this.var_gg_vape_mapping_MappingMethod_r = mMutableTextComponent.Y(string, bl, clazz, classArray);
                Class[] classArray2 = new Class[]{MappedClasses.Va};
                Class clazz2 = MappedClasses.YO;
                boolean bl2 = true;
                String string2 = "setStyle";
                MMutableTextComponent mMutableTextComponent2 = this;
                this.var_gg_vape_mapping_MappingMethod_e = this.Y(string2, bl2, clazz2, classArray2);
            }
            return;
        }
        if (ForgeVersion.MC_1_16_5.d() && ForgeVersion.MC_1_20_6.v()) {
            Class[] classArray = new Class[]{MappedClasses.Yr};
            Class clazz = MappedClasses.YO;
            boolean bl = true;
            String string = "append";
            MMutableTextComponent mMutableTextComponent = this;
            this.var_gg_vape_mapping_MappingMethod_r = mMutableTextComponent.Y(string, bl, clazz, classArray);
            Class[] classArray3 = new Class[]{MappedClasses.Va};
            Class clazz3 = MappedClasses.YO;
            boolean bl3 = true;
            String string3 = "setStyle";
            MMutableTextComponent mMutableTextComponent3 = this;
            this.var_gg_vape_mapping_MappingMethod_e = this.Y(string3, bl3, clazz3, classArray3);
        }
    }

    public Object v(Object object, Object object2) {
        return this.var_gg_vape_mapping_MappingMethod_e.invokeObject(object, object2);
    }

    static {
        MMutableTextComponent.U(null);
    }
}

