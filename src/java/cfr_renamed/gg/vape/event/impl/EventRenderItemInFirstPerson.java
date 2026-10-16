/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.event.impl;

import gg.vape.event.Event;
import gg.vape.event.EventListeners;
import gg.vape.wrapper.impl.ItemRenderer;

public class EventRenderItemInFirstPerson
extends Event {
    private final Object itemRendererHandle;
    final public float partialTicks;
    private final static EventListeners EVENT_LISTENERS = new EventListeners();

    public EventRenderItemInFirstPerson(Object itemRendererHandle, float partialTicks) {
        this.itemRendererHandle = itemRendererHandle;
        this.partialTicks = partialTicks;
    }

    public ItemRenderer getItemRenderer() {
        return new ItemRenderer(this.itemRendererHandle);
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }
}

