/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MTextureAtlasSpriteInfoBridge
extends Mapping {
    private final MappingMethod texturePathMethod = this.Y("texturePath", true, MappedClasses.zC, new Class[0]);

    public MTextureAtlasSpriteInfoBridge() {
        super(MappedClasses.zI);
    }

    public Object getTexturePath(Object spriteInfo) {
        return this.texturePathMethod.invokeObject(spriteInfo, new Object[0]);
    }
}

