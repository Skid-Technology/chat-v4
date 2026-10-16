/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MGameProfile;
import gg.vape.wrapper.Wrapper;
import java.util.UUID;

public class GameProfile
extends Wrapper {
    public GameProfile(Object object) {
        super(object);
    }

    public static GameProfile create(UUID uUID, String string) {
        return new GameProfile(MGameProfile.N(GameProfile.vapeInstance.getMappingsMapperCompat().var_gg_vape_mapping_mappings_MGameProfile_d, uUID, string));
    }

    public String getName() {
        return MGameProfile.P(GameProfile.vapeInstance.getMappingsMapperCompat().var_gg_vape_mapping_mappings_MGameProfile_d, this.I);
    }

    public void setName(String string) {
        MGameProfile.z(GameProfile.vapeInstance.getMappingsMapperCompat().var_gg_vape_mapping_mappings_MGameProfile_d).setObject(this.I, string);
    }

    public UUID getUUID() {
        return MGameProfile.h(GameProfile.vapeInstance.getMappingsMapperCompat().var_gg_vape_mapping_mappings_MGameProfile_d, this.I);
    }
}

