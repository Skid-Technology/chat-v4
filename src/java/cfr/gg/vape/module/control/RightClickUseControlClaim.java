/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.module.control;

import gg.vape.module.control.ModuleControlClaim;

public class RightClickUseControlClaim
extends ModuleControlClaim {
    public void blockUse() {
        this.setClaimed(true);
    }
}

