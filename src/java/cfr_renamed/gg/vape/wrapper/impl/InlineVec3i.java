/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.impl.Vec3i;

public class InlineVec3i
extends Vec3i {
    private final int var_int_x;
    private final int var_int_y;
    private final int var_int_z;

    @Override
    public int getY() {
        return this.var_int_y;
    }

    @Override
    public int getX() {
        return this.var_int_x;
    }

    @Override
    public int getZ() {
        return this.var_int_z;
    }

    public InlineVec3i(int x, int y, int z) {
        super(null);
        this.var_int_x = x;
        this.var_int_y = y;
        this.var_int_z = z;
    }
}

