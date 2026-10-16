/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.module.control;

import gg.vape.event.impl.EventKeyInputBase;
import gg.vape.module.control.AttackCancellationAdapter;

public class PhysicalAttackCancellationAdapter
implements AttackCancellationAdapter {
    private final EventKeyInputBase keyInputEvent;

    @Override
    public void setCancelled(boolean cancelled) {
        if (this.keyInputEvent != null) {
            this.keyInputEvent.setCancelled(cancelled);
        }
    }

    @Override
    public boolean isCancelled() {
        return this.keyInputEvent != null && this.keyInputEvent.isCanceled();
    }

    public PhysicalAttackCancellationAdapter(EventKeyInputBase eventKeyInputBase) {
        this.keyInputEvent = eventKeyInputBase;
    }
}

