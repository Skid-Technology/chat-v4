/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.event.impl;

import gg.vape.event.Event;
import gg.vape.event.EventListeners;

public class LegacyStringRenderPayloadEvent
extends Event {
    private final Object fontRendererHandle;
    private final String text;
    private final static EventListeners EVENT_LISTENERS = new EventListeners();
    private final int color;
    private final boolean dropShadow;
    private final float var_float_x;
    private final float var_float_y;

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    public int getX() {
        return (int)this.var_float_x;
    }

    public LegacyStringRenderPayloadEvent(Object fontRendererHandle, String text, float x, float y, int color, boolean dropShadow) {
        this.fontRendererHandle = fontRendererHandle;
        this.text = text;
        this.var_float_x = x;
        this.var_float_y = y;
        this.color = color;
        this.dropShadow = dropShadow;
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }
}

