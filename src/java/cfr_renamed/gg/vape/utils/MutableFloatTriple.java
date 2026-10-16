/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.utils;

public class MutableFloatTriple {
    public float var_float_x;
    public float var_float_y;
    public float var_float_z;

    public void setZ(float z) {
        this.var_float_z = z;
    }

    public float getX() {
        return this.var_float_x;
    }

    public float getY() {
        return this.var_float_y;
    }

    public MutableFloatTriple(float x, float y, float z) {
        this.var_float_x = x;
        this.var_float_y = y;
        this.var_float_z = z;
    }

    public void setX(float x) {
        this.var_float_x = x;
    }

    public float getZ() {
        return this.var_float_z;
    }

    public void setY(float y) {
        this.var_float_y = y;
    }
}

