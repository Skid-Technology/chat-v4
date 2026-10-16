/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.Vape;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.mappings.MSPacketMapChunkBulk;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class MEntityPlayerMacroBridge
extends Mapping {
    private final MappingField var_gg_vape_mapping_MappingField_q;
    private final MappingField var_gg_vape_mapping_MappingField_z;
    private MappingField var_gg_vape_mapping_MappingField_g;

    private boolean s(Object object) {
        return this.var_gg_vape_mapping_MappingField_z.getBoolean(object);
    }

    public static Object F(MEntityPlayerMacroBridge mEntityPlayerMacroBridge, Object object) {
        return mEntityPlayerMacroBridge.D(object);
    }

    private Object D(Object object) {
        return this.var_gg_vape_mapping_MappingField_g.getObject(object);
    }

    public MEntityPlayerMacroBridge() {
        this(MSPacketMapChunkBulk.getMappingControlFlowToken());
    }

    private MEntityPlayerMacroBridge(String string) {
        super(MappedClasses.lM);
        MEntityPlayerMacroBridge mEntityPlayerMacroBridge;
        boolean bl;
        Class clazz;
        if (string != null) {
            MEntityPlayerMacroBridge mEntityPlayerMacroBridge2;
            boolean bl2;
            Class clazz2;
            if (ForgeVersion.MC_1_16_5.d()) {
                clazz2 = MappedClasses.zc;
                bl2 = true;
                String string2 = "caughtEntity";
                mEntityPlayerMacroBridge2 = this;
                this.var_gg_vape_mapping_MappingField_q = mEntityPlayerMacroBridge2.J(string2, bl2, clazz2);
                Class<Boolean> clazz22 = Boolean.TYPE;
                boolean bl22 = Wrapper.isNativeAvailable;
                String string3 = "field_234597_c_";
                MEntityPlayerMacroBridge mEntityPlayerMacroBridge22 = this;
                this.var_gg_vape_mapping_MappingField_z = this.J(string3, bl22, clazz22);
            } else if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
                clazz2 = MappedClasses.zc;
                bl2 = true;
                String string4 = "caughtEntity";
                mEntityPlayerMacroBridge2 = this;
                this.var_gg_vape_mapping_MappingField_q = mEntityPlayerMacroBridge2.J(string4, bl2, clazz2);
                Class<Boolean> clazz3 = Boolean.TYPE;
                boolean bl3 = true;
                String string5 = "inGround";
                MEntityPlayerMacroBridge mEntityPlayerMacroBridge3 = this;
                this.var_gg_vape_mapping_MappingField_z = this.J(string5, bl3, clazz3);
            } else {
                clazz2 = MappedClasses.zc;
                bl2 = Wrapper.isNativeAvailable;
                String string6 = "field_146043_c";
                mEntityPlayerMacroBridge2 = this;
                this.var_gg_vape_mapping_MappingField_q = mEntityPlayerMacroBridge2.J(string6, bl2, clazz2);
                Class<Boolean> clazz4 = Boolean.TYPE;
                boolean bl4 = Wrapper.isNativeAvailable;
                String string7 = "field_146051_au";
                MEntityPlayerMacroBridge mEntityPlayerMacroBridge4 = this;
                this.var_gg_vape_mapping_MappingField_z = this.J(string7, bl4, clazz4);
            }
            if (ForgeVersion.MC_1_16_5.v()) {
                clazz2 = MappedClasses.Yl;
                bl2 = true;
                String string8 = "angler";
                mEntityPlayerMacroBridge2 = this;
                this.var_gg_vape_mapping_MappingField_g = mEntityPlayerMacroBridge2.J(string8, bl2, clazz2);
            }
            return;
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            clazz = MappedClasses.zc;
            bl = true;
            String string9 = "caughtEntity";
            mEntityPlayerMacroBridge = this;
            mEntityPlayerMacroBridge.J(string9, bl, clazz);
            Class<Boolean> clazz5 = Boolean.TYPE;
            boolean bl5 = true;
            String string10 = "inGround";
            MEntityPlayerMacroBridge mEntityPlayerMacroBridge5 = this;
            mEntityPlayerMacroBridge5.J(string10, bl5, clazz5);
        }
        clazz = MappedClasses.zc;
        bl = Wrapper.isNativeAvailable;
        String string11 = "field_146043_c";
        mEntityPlayerMacroBridge = this;
        this.var_gg_vape_mapping_MappingField_q = mEntityPlayerMacroBridge.J(string11, bl, clazz);
        Class<Boolean> clazz6 = Boolean.TYPE;
        boolean bl6 = Wrapper.isNativeAvailable;
        String string12 = "field_146051_au";
        MEntityPlayerMacroBridge mEntityPlayerMacroBridge6 = this;
        this.var_gg_vape_mapping_MappingField_z = this.J(string12, bl6, clazz6);
        if (ForgeVersion.MC_1_16_5.v()) {
            Class clazz7 = MappedClasses.Yl;
            boolean bl7 = true;
            String string13 = "angler";
            MEntityPlayerMacroBridge mEntityPlayerMacroBridge7 = this;
            this.var_gg_vape_mapping_MappingField_g = this.J(string13, bl7, clazz7);
        }
    }

    private Object k(Object object) {
        return this.var_gg_vape_mapping_MappingField_q.getObject(object);
    }

    public static Object W(MEntityPlayerMacroBridge mEntityPlayerMacroBridge, Object object) {
        return mEntityPlayerMacroBridge.k(object);
    }

    public static boolean B(MEntityPlayerMacroBridge mEntityPlayerMacroBridge, Object object) {
        return mEntityPlayerMacroBridge.s(object);
    }
}

