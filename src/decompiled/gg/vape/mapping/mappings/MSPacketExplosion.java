/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MSPacketExplosion
extends Mapping {
    private final MappingField previousSpeedField = this.J("speedOld", true, Float.TYPE);
    private final MappingField positionField;
    private final MappingField speedField = this.J("speed", true, Float.TYPE);

    public float getPosition(Object walkAnimationState) {
        return this.positionField.getFloat(walkAnimationState);
    }

    public float getSpeed(Object walkAnimationState) {
        return this.speedField.getFloat(walkAnimationState);
    }

    public void setPosition(Object walkAnimationState, float position) {
        this.positionField.setFloat(walkAnimationState, position);
    }

    public void setPreviousSpeed(Object walkAnimationState, float previousSpeed) {
        this.previousSpeedField.setFloat(walkAnimationState, previousSpeed);
    }

    public float getPreviousSpeed(Object walkAnimationState) {
        return this.previousSpeedField.getFloat(walkAnimationState);
    }

    private void setSpeed(Object walkAnimationState, float speed) {
        this.speedField.setFloat(walkAnimationState, speed);
    }

    public MSPacketExplosion() {
        super(MappedClasses.VB);
        this.positionField = this.J("position", true, Float.TYPE);
    }
}

