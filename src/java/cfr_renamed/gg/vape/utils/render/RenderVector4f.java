/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.utils.render;

public class RenderVector4f {
    public float var_float_y;
    public float var_float_x;
    public float var_float_w;
    public float var_float_z;

    public RenderVector4f(float x, float y, float z, float w) {
        this.var_float_x = x;
        this.var_float_y = y;
        this.var_float_z = z;
        this.var_float_w = w;
    }

    public RenderVector4f(double x, double y, double z, double w) {
        this.var_float_x = (float)x;
        this.var_float_y = (float)y;
        this.var_float_z = (float)z;
        this.var_float_w = (float)w;
    }

    public String toString() {
        return "Vec4{x=" + this.var_float_x + ", y=" + this.var_float_y + ", z=" + this.var_float_z + ", w=" + this.var_float_w + '}';
    }
}

