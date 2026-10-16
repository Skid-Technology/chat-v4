/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.module.render.trajectories;

import java.awt.Color;

public class TrajectoriesProjectileRenderState {
    final public Color color;
    final public float lineWidth;
    final public double startX;
    final public double endX;
    final public double endZ;
    final public double startY;
    final public double endY;
    final public double startZ;

    public TrajectoriesProjectileRenderState(double startX, double startY, double startZ) {
        this(startX, startY, startZ, 0.0, 0.0, 0.0, 0.0f, null);
    }

    public TrajectoriesProjectileRenderState(double startX, double startY, double startZ, double endX, double endY, double endZ, float lineWidth) {
        this(startX, startY, startZ, endX, endY, endZ, lineWidth, null);
    }

    public TrajectoriesProjectileRenderState(double startX, double startY, double startZ, double endX, double endY, double endZ, float lineWidth, Color color) {
        this.startX = startX;
        this.startY = startY;
        this.startZ = startZ;
        this.endX = endX;
        this.endY = endY;
        this.endZ = endZ;
        this.lineWidth = lineWidth;
        this.color = color;
    }
}

