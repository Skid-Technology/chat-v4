/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.utils.render;

public class RenderVector3f {
    public float var_float_y;
    public float var_float_z;
    public float var_float_x;

    public RenderVector3f cross(RenderVector3f other) {
        float crossX = this.var_float_y * other.var_float_z - this.var_float_z * other.var_float_y;
        float crossY = this.var_float_z * other.var_float_x - this.var_float_x * other.var_float_z;
        float crossZ = this.var_float_x * other.var_float_y - this.var_float_y * other.var_float_x;
        return new RenderVector3f(crossX, crossY, crossZ);
    }

    public RenderVector3f subtract(RenderVector3f other) {
        return new RenderVector3f(this.var_float_x - other.var_float_x, this.var_float_y - other.var_float_y, this.var_float_z - other.var_float_z);
    }

    public RenderVector3f negate() {
        this.var_float_x = -this.var_float_x;
        this.var_float_y = -this.var_float_y;
        this.var_float_z = -this.var_float_z;
        return this;
    }

    public float dot(RenderVector3f other) {
        return this.var_float_x * other.var_float_x + this.var_float_y * other.var_float_y + this.var_float_z * other.var_float_z;
    }

    public RenderVector3f(float x, float y, float z) {
        this.var_float_x = x;
        this.var_float_y = y;
        this.var_float_z = z;
    }

    public String toString() {
        return "Vec3{x=" + this.var_float_x + ", y=" + this.var_float_y + ", z=" + this.var_float_z + '}';
    }

    public RenderVector3f normalize() {
        float length = (float)Math.sqrt(this.var_float_x * this.var_float_x + this.var_float_y * this.var_float_y + this.var_float_z * this.var_float_z);
        return new RenderVector3f(this.var_float_x / length, this.var_float_y / length, this.var_float_z / length);
    }
}

