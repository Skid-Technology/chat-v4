/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.ResourceLocation;
import gg.vape.wrapper.impl.TextureAtlasSpriteInfo;

public class PlayerSkin
extends Wrapper {
    public TextureAtlasSpriteInfo getBodySpriteInfo() {
        return new TextureAtlasSpriteInfo(PlayerSkin.vapeInstance.getMappingsMapperCompat().playerSkin.getBody(this.I));
    }

    public PlayerSkin(Object playerSkinHandle) {
        super(playerSkinHandle);
    }

    public ResourceLocation getTexture() {
        if (ForgeVersion.MC_1_21_10.d()) {
            return this.getBodySpriteInfo().getTexturePath();
        }
        return new ResourceLocation(PlayerSkin.vapeInstance.getMappingsMapperCompat().playerSkin.getTexture(this.I));
    }
}

