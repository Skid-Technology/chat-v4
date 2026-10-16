/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MGameSettingsGuiScale;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.Vec3;

public class GameSettingsGuiScale
extends Wrapper {
    public GameSettingsGuiScale(Object handle) {
        super(handle);
    }

    public Vec3 getBase() {
        return new Vec3(MGameSettingsGuiScale.getBase(GameSettingsGuiScale.vapeInstance.getMappingsMapperCompat().vecDeltaCodec, this.I));
    }
}

