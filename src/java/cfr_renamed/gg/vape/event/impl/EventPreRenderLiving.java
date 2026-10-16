/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.event.impl;

import gg.vape.event.Event;
import gg.vape.event.EventListeners;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.MatrixStack;

public class EventPreRenderLiving
extends Event {
    private final Object matrixStackHandle;
    private MatrixStack matrixStack;
    private Entity entity;
    private final static EventListeners EVENT_LISTENERS = new EventListeners();
    private final double var_double_x;
    private final double var_double_y;
    private final double var_double_z;
    private final Object entityHandle;

    public Entity getEntity() {
        if (this.entity == null) {
            this.entity = new Entity(this.entityHandle);
        }
        return this.entity;
    }

    public double getZ() {
        return this.var_double_z;
    }

    @Override
    public boolean fire() {
        return super.fire();
    }

    public double getY() {
        return this.var_double_y;
    }

    @Override
    public EventListeners getListeners() {
        return EVENT_LISTENERS;
    }

    public static EventListeners getEventListeners() {
        return EVENT_LISTENERS;
    }

    public MatrixStack getMatrixStack() {
        if (this.matrixStack == null) {
            this.matrixStack = new MatrixStack(this.matrixStackHandle);
        }
        return this.matrixStack;
    }

    public double getX() {
        return this.var_double_x;
    }

    public EventPreRenderLiving(Object entityHandle, double x, double y, double z, Object matrixStackHandle) {
        this.entityHandle = entityHandle;
        this.matrixStackHandle = matrixStackHandle;
        this.var_double_x = x;
        this.var_double_y = y;
        this.var_double_z = z;
    }
}

