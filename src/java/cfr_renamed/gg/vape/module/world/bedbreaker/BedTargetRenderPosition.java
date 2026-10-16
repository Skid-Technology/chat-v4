/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.module.world.bedbreaker;

import gg.vape.utils.MathUtil;
import gg.vape.wrapper.impl.RenderManager;
import java.util.Objects;

public class BedTargetRenderPosition {
    private final double var_double_z;
    private final double var_double_x;
    private final double var_double_y;

    public int hashCode() {
        return Objects.hash(this.var_double_x, this.var_double_y, this.var_double_z);
    }

    public double getRelativeZ() {
        double renderZ = RenderManager.getInterpolatedRenderPosZ();
        return this.var_double_z - renderZ;
    }

    public int getBlockY() {
        return MathUtil.floor(this.var_double_y);
    }

    public double getRelativeY() {
        double renderY = RenderManager.getInterpolatedRenderPosY();
        return this.var_double_y - renderY;
    }

    public double getY() {
        return this.var_double_y;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        BedTargetRenderPosition bedTargetRenderPosition = (BedTargetRenderPosition)object;
        return this.var_double_x == bedTargetRenderPosition.var_double_x && this.var_double_y == bedTargetRenderPosition.var_double_y && this.var_double_z == bedTargetRenderPosition.var_double_z;
    }

    public double getX() {
        return this.var_double_x;
    }

    public double getRelativeX() {
        double renderX = RenderManager.getInterpolatedRenderPosX();
        return this.var_double_x - renderX;
    }

    public int getBlockZ() {
        return MathUtil.floor(this.var_double_z);
    }

    public int getBlockX() {
        return MathUtil.floor(this.var_double_x);
    }

    public BedTargetRenderPosition(double x, double y, double z) {
        this.var_double_x = x;
        this.var_double_y = y;
        this.var_double_z = z;
    }

    public double getZ() {
        return this.var_double_z;
    }

    public BedTargetRenderPosition(int x, int y, int z) {
        this.var_double_x = x;
        this.var_double_y = y;
        this.var_double_z = z;
    }
}

