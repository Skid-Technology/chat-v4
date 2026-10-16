/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.utils.render;

public class TextureAtlasRegion {
    final public int width;
    final public int height;
    public float minU;
    public float maxV;
    final public int var_int_y;
    final public int var_int_x;
    public float maxU;
    public float minV;

    public TextureAtlasRegion(int x, int y, int width, int height, float minU, float minV, float maxU, float maxV) {
        this.var_int_x = x;
        this.var_int_y = y;
        this.width = width;
        this.height = height;
        this.minU = minU;
        this.minV = minV;
        this.maxU = maxU;
        this.maxV = maxV;
    }
}

