/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.event.impl;

import gg.vape.event.impl.EventPacketSend;

public class DelayedPacketSendEntry {
    private boolean offsetApplied;
    private final EventPacketSend event;

    public DelayedPacketSendEntry(EventPacketSend event) {
        this.event = event;
    }

    public void setOffsetApplied(boolean offsetApplied) {
        this.offsetApplied = offsetApplied;
    }

    public EventPacketSend getEvent() {
        return this.event;
    }

    public boolean isOffsetApplied() {
        return this.offsetApplied;
    }
}

