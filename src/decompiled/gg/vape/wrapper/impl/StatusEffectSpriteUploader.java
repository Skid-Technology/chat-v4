/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MStatusEffectSpriteUploader;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.Holder;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.StatusEffect;
import gg.vape.wrapper.impl.TextureAtlasSprite;

public class StatusEffectSpriteUploader
extends Wrapper {
    public StatusEffectSpriteUploader(Object handle) {
        super(handle);
    }

    public TextureAtlasSprite getSprite(StatusEffect statusEffect) {
        return new TextureAtlasSprite(MStatusEffectSpriteUploader.getSprite(StatusEffectSpriteUploader.vapeInstance.getMappingsMapperCompat().statusEffectSpriteUploader, this.I, statusEffect.getObject()));
    }

    public TextureAtlasSprite getSprite(Holder holder) {
        return new TextureAtlasSprite(MStatusEffectSpriteUploader.getSprite(StatusEffectSpriteUploader.vapeInstance.getMappingsMapperCompat().statusEffectSpriteUploader, this.I, holder.getObject()));
    }

    public static StatusEffectSpriteUploader getPotionSprites() {
        Object uploaderHandle = MStatusEffectSpriteUploader.getPotionSprites(StatusEffectSpriteUploader.vapeInstance.getMappingsMapperCompat().statusEffectSpriteUploader, Minecraft.i());
        return new StatusEffectSpriteUploader(uploaderHandle);
    }
}

