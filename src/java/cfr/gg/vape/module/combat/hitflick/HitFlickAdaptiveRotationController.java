/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.module.combat.hitflick;

import gg.vape.module.combat.HitFlick;
import gg.vape.rotation.AdaptiveRotationController;

public class HitFlickAdaptiveRotationController
extends AdaptiveRotationController {
    private final HitFlick targetingModule;

    public HitFlickAdaptiveRotationController(HitFlick targetingModule) {
        this.targetingModule = targetingModule;
    }

    @Override
    public float getSpeed() {
        switch (this.targetingModule.getRotationMode()) {
            case FLICKING_AWAY: {
                return this.targetingModule.getAttackRotationSpeed();
            }
            case ATTACKING: {
                return this.targetingModule.getFlickAwayRotationSpeed();
            }
        }
        return 48.0f;
    }
}

