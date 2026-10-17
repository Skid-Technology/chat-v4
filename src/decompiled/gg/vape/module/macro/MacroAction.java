/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.module.macro;

public interface MacroAction {
    public boolean isFinished();

    default public void cancel() {
    }

    default public void tick() {
    }

    default public void inheritState(MacroAction previousAction) {
    }
}

