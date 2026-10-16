/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.wrapper.impl.ForgeVersion;

public class MSoundAwareEntityFX
extends Mapping {
    private final MappingField var_gg_vape_mapping_MappingField_I;
    private static String[] var_java_lang_String_arr_O;
    private final MappingField var_gg_vape_mapping_MappingField_o;

    public static void u(MSoundAwareEntityFX mSoundAwareEntityFX, Object object, Object object2) {
        mSoundAwareEntityFX.g(object, object2);
    }

    public static void Z(String[] stringArray) {
        var_java_lang_String_arr_O = stringArray;
    }

    private Object o(Object object) {
        return this.var_gg_vape_mapping_MappingField_I.getObject(object);
    }

    private void g(Object object, Object object2) {
        this.var_gg_vape_mapping_MappingField_o.setObject(object, object2);
    }

    public MSoundAwareEntityFX() {
        this(MSoundAwareEntityFX.t());
    }

    private MSoundAwareEntityFX(String[] stringArray) {
        super(MappedClasses.uu);
        if (stringArray != null) {
            Class clazz = MappedClasses.zc;
            boolean bl = true;
            String string = "attachedEntity";
            MSoundAwareEntityFX mSoundAwareEntityFX = this;
            this.var_gg_vape_mapping_MappingField_o = mSoundAwareEntityFX.J(string, bl, clazz);
            if (ForgeVersion.MC_1_16_5.d()) {
                Class clazz2 = MappedClasses.lZ;
                boolean bl2 = true;
                String string2 = "particleTypes";
                MSoundAwareEntityFX mSoundAwareEntityFX2 = this;
                this.var_gg_vape_mapping_MappingField_I = this.J(string2, bl2, clazz2);
            } else {
                Class clazz3 = MappedClasses.qi;
                boolean bl3 = true;
                String string3 = "particleTypes";
                MSoundAwareEntityFX mSoundAwareEntityFX3 = this;
                this.var_gg_vape_mapping_MappingField_I = this.J(string3, bl3, clazz3);
            }
            return;
        }
        Class clazz = MappedClasses.zc;
        boolean bl = true;
        String string = "attachedEntity";
        MSoundAwareEntityFX mSoundAwareEntityFX = this;
        this.var_gg_vape_mapping_MappingField_I = mSoundAwareEntityFX.J(string, bl, clazz);
        this.var_gg_vape_mapping_MappingField_o = null;
    }

    private Object H(Object object) {
        return this.var_gg_vape_mapping_MappingField_o.getObject(object);
    }

    public static String[] t() {
        return var_java_lang_String_arr_O;
    }

    public static Object b(MSoundAwareEntityFX mSoundAwareEntityFX, Object object) {
        return mSoundAwareEntityFX.o(object);
    }

    public static Object q(MSoundAwareEntityFX mSoundAwareEntityFX, Object object) {
        return mSoundAwareEntityFX.H(object);
    }

    static {
        MSoundAwareEntityFX.Z(new String[5]);
    }
}

