/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.utils.render.glu;

import org.lwjgl.opengl.GL11;

public class GluQuadric {
    final public static int DRAW_POINT = 100010;
    final public static int DRAW_LINE = 100011;
    final public static int DRAW_FILL = 100012;
    final public static int DRAW_SILHOUETTE = 100013;
    final public static int ORIENTATION_OUTSIDE = 100020;
    final public static int ORIENTATION_INSIDE = 100021;
    final public static int NORMAL_SMOOTH = 100000;
    final public static int NORMAL_FLAT = 100001;
    final public static int NORMAL_NONE = 100002;
    protected boolean textureCoordinatesEnabled = false;
    protected int drawStyle = 100012;
    protected int normalMode = 100000;
    protected int orientation = 100020;

    public int getDrawStyle() {
        return this.drawStyle;
    }

    protected void emitNormalizedNormal(float x, float y, float z) {
        float length = (float)Math.sqrt(x * x + y * y + z * z);
        if (length > 1.0E-5f) {
            x /= length;
            y /= length;
            z /= length;
        }
        GL11.glNormal3f(x, y, z);
    }

    public void setDrawStyle(int drawStyle) {
        this.drawStyle = drawStyle;
    }

    public void setNormalMode(int normalMode) {
        this.normalMode = normalMode;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public void setTextureCoordinatesEnabled(boolean enabled) {
        this.textureCoordinatesEnabled = enabled;
    }

    protected float cos(float angle) {
        return (float)Math.cos(angle);
    }

    public boolean isTextureCoordinatesEnabled() {
        return this.textureCoordinatesEnabled;
    }

    public int getOrientation() {
        return this.orientation;
    }

    public int getNormalMode() {
        return this.normalMode;
    }

    protected float sin(float angle) {
        return (float)Math.sin(angle);
    }

    protected void emitTextureCoordinate(float u, float v) {
        if (this.textureCoordinatesEnabled) {
            GL11.glTexCoord2f(u, v);
        }
    }
}

