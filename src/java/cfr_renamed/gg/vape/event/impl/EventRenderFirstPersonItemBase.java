/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.event.impl;

import gg.vape.event.Event;
import gg.vape.event.EventListeners;

public class EventRenderFirstPersonItemBase
extends Event {
    final public float swingProgress;
    final public float equipProgress;
    private final static EventListeners EVENT_LISTENERS = new EventListeners();

    public EventRenderFirstPersonItemBase(float equipProgress, float swingProgress) {
        this.equipProgress = equipProgress;
        this.swingProgress = swingProgress;
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }
}

