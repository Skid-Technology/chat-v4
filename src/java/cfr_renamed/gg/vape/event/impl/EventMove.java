/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.event.impl;

import gg.vape.event.Event;
import gg.vape.event.EventListeners;

public class EventMove
extends Event {
    private double var_double_z;
    private final static EventListeners EVENT_LISTENERS = new EventListeners();
    private double var_double_y;
    private double var_double_x;

    public EventMove setY(double y) {
        this.var_double_y = y;
        return this;
    }

    public double getZ() {
        return this.var_double_z;
    }

    public EventMove setZ(double z) {
        this.var_double_z = z;
        return this;
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    public double getX() {
        return this.var_double_x;
    }

    public EventMove(double x, double y, double z) {
        this.var_double_x = x;
        this.var_double_y = y;
        this.var_double_z = z;
    }

    public EventMove setX(double x) {
        this.var_double_x = x;
        return this;
    }

    public double getY() {
        return this.var_double_y;
    }

    @Override
    public boolean fire() {
        return super.fire();
    }
}

