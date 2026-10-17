/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.impl.ForgeVersion;

public class MC03PacketPlayerBridge
extends Mapping {
    private static final String CONSTRUCTOR_METHOD_NAME = "<init>";
    private final MappingMethod constructorMethod;
    private final boolean hasHorizontalCollision;

    public Object newInstance(boolean onGround, boolean horizontalCollision) {
        if (this.hasHorizontalCollision) {
            return this.constructorMethod.newInstance(onGround, horizontalCollision);
        }
        return this.constructorMethod.newInstance(onGround);
    }

    public MC03PacketPlayerBridge() {
        super(MappedClasses.Dl);
        if (ForgeVersion.MC_1_21_4.d() && !ForgeVersion.MC_1_21_11.d()) {
            this.constructorMethod = this.Y(CONSTRUCTOR_METHOD_NAME, false, Void.TYPE, Boolean.TYPE);
            this.hasHorizontalCollision = false;
        } else {
            this.constructorMethod = this.Y(CONSTRUCTOR_METHOD_NAME, false, Void.TYPE, Boolean.TYPE, Boolean.TYPE);
            this.hasHorizontalCollision = true;
        }
    }
}

