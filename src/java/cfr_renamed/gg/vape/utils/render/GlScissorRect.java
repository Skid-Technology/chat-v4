/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.utils.render;

public class GlScissorRect {
    public int width;
    public int var_int_y;
    public int height;
    public int var_int_x;

    public GlScissorRect(int x, int y, int width, int height) {
        this.var_int_x = x;
        this.var_int_y = y;
        this.width = width;
        this.height = height;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        GlScissorRect glScissorRect = (GlScissorRect)object;
        return this.var_int_x == glScissorRect.var_int_x && this.var_int_y == glScissorRect.var_int_y && this.width == glScissorRect.width && this.height == glScissorRect.height;
    }

    public String toString() {
        return "ScissorCoords{x=" + this.var_int_x + ", y=" + this.var_int_y + ", w=" + this.width + ", h=" + this.height + '}';
    }
}

