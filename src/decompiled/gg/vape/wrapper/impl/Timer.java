/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MTimer;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;

public class Timer
extends Wrapper {
    public float getTimerSpeed() {
        if (this.isNull()) {
            return 1.0f;
        }
        if (ForgeVersion.MC_1_12_2.d()) {
            float f = 50.0f;
            return f / Timer.vapeInstance.getMappings().hr.Z(this.I);
        }
        return MTimer.S(Timer.vapeInstance.getMappings().hr, this.I);
    }

    public void setTimerSpeed(float f) {
        if (this.isNull()) {
            return;
        }
        if (ForgeVersion.MC_1_12_2.d()) {
            float f2 = 50.0f;
            MTimer.k(Timer.vapeInstance.getMappings().hr, this.I, f2 / f);
            return;
        }
        MTimer.y(Timer.vapeInstance.getMappings().hr, this.I, f);
    }

    public Timer(Object object) {
        super(object);
    }

    public float renderPartialTicks() {
        if (this.isNull()) {
            return 1.0f;
        }
        return MTimer.l(Timer.vapeInstance.getMappings().hr, this.I);
    }

    public float getElapsedPartialTicks() {
        if (this.isNull()) {
            return 1.0f;
        }
        return MTimer.H(Timer.vapeInstance.getMappings().hr, this.I);
    }
}

