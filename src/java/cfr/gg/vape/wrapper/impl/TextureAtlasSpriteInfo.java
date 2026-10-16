/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ResourceLocation;

public class TextureAtlasSpriteInfo
extends Wrapper {
    public TextureAtlasSpriteInfo(Object handle) {
        super(handle);
    }

    public ResourceLocation getTexturePath() {
        return new ResourceLocation(TextureAtlasSpriteInfo.vapeInstance.getMappingsMapperCompat().textureAtlasSpriteInfo.getTexturePath(this.I));
    }
}

