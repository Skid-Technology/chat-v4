/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.rotation;

import gg.vape.wrapper.impl.Vec3;

public interface WorldPointRotationTarget {
    default public void setTarget(float x, float y, float z) {
        this.setTarget(Vec3.create(x, y, z));
    }

    public void setTarget(Vec3 var1);

    public Vec3 getTarget();

    default public void setTarget(double x, double y, double z) {
        this.setTarget(Vec3.create(x, y, z));
    }

    default public void setTarget(int x, int y, int z) {
        this.setTarget(Vec3.create(x, y, z));
    }
}

