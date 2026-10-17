/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.module.control;

import gg.vape.module.control.ModuleControlClaim;

public class RenderPassControlClaim
extends ModuleControlClaim {
    public boolean isRenderBlocked() {
        return this.isClaimed();
    }

    public void blockRender() {
        this.setClaimed(true);
    }
}

