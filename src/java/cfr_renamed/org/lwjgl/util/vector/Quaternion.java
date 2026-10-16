/*
 * Decompiled with CFR 0.152.
 */
package org.lwjgl.util.vector;

import java.nio.FloatBuffer;
import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.ReadableVector4f;
import org.lwjgl.util.vector.Vector;
import org.lwjgl.util.vector.Vector4f;

public class Quaternion
extends Vector
implements ReadableVector4f {
    private final static long serialVersionUID = 1L;
    public float var_float_x;
    public float var_float_y;
    public float var_float_z;
    public float var_float_w;

    public Quaternion() {
        this.setIdentity();
    }

    public Quaternion(ReadableVector4f src) {
        this.set(src);
    }

    public Quaternion(float x, float y, float z, float w) {
        this.set(x, y, z, w);
    }

    public void set(float x, float y) {
        this.var_float_x = x;
        this.var_float_y = y;
    }

    public void set(float x, float y, float z) {
        this.var_float_x = x;
        this.var_float_y = y;
        this.var_float_z = z;
    }

    public void set(float x, float y, float z, float w) {
        this.var_float_x = x;
        this.var_float_y = y;
        this.var_float_z = z;
        this.var_float_w = w;
    }

    public Quaternion set(ReadableVector4f src) {
        this.var_float_x = src.getX();
        this.var_float_y = src.getY();
        this.var_float_z = src.getZ();
        this.var_float_w = src.getW();
        return this;
    }

    public Quaternion setIdentity() {
        return Quaternion.setIdentity(this);
    }

    public static Quaternion setIdentity(Quaternion q) {
        q.var_float_x = 0.0f;
        q.var_float_y = 0.0f;
        q.var_float_z = 0.0f;
        q.var_float_w = 1.0f;
        return q;
    }

    public float lengthSquared() {
        return this.var_float_x * this.var_float_x + this.var_float_y * this.var_float_y + this.var_float_z * this.var_float_z + this.var_float_w * this.var_float_w;
    }

    public static Quaternion normalise(Quaternion src, Quaternion dest) {
        float inv_l = 1.0f / src.length();
        if (dest == null) {
            dest = new Quaternion();
        }
        dest.set(src.var_float_x * inv_l, src.var_float_y * inv_l, src.var_float_z * inv_l, src.var_float_w * inv_l);
        return dest;
    }

    public Quaternion normalise(Quaternion dest) {
        return Quaternion.normalise(this, dest);
    }

    public static float dot(Quaternion left, Quaternion right) {
        return left.var_float_x * right.var_float_x + left.var_float_y * right.var_float_y + left.var_float_z * right.var_float_z + left.var_float_w * right.var_float_w;
    }

    public Quaternion negate(Quaternion dest) {
        return Quaternion.negate(this, dest);
    }

    public static Quaternion negate(Quaternion src, Quaternion dest) {
        if (dest == null) {
            dest = new Quaternion();
        }
        dest.var_float_x = -src.var_float_x;
        dest.var_float_y = -src.var_float_y;
        dest.var_float_z = -src.var_float_z;
        dest.var_float_w = src.var_float_w;
        return dest;
    }

    public Vector negate() {
        return Quaternion.negate(this, this);
    }

    public Vector load(FloatBuffer buf) {
        this.var_float_x = buf.get();
        this.var_float_y = buf.get();
        this.var_float_z = buf.get();
        this.var_float_w = buf.get();
        return this;
    }

    public Vector scale(float scale) {
        return Quaternion.scale(scale, this, this);
    }

    public static Quaternion scale(float scale, Quaternion src, Quaternion dest) {
        if (dest == null) {
            dest = new Quaternion();
        }
        dest.var_float_x = src.var_float_x * scale;
        dest.var_float_y = src.var_float_y * scale;
        dest.var_float_z = src.var_float_z * scale;
        dest.var_float_w = src.var_float_w * scale;
        return dest;
    }

    public Vector store(FloatBuffer buf) {
        buf.put(this.var_float_x);
        buf.put(this.var_float_y);
        buf.put(this.var_float_z);
        buf.put(this.var_float_w);
        return this;
    }

    public final float getX() {
        return this.var_float_x;
    }

    public final float getY() {
        return this.var_float_y;
    }

    public final void setX(float x) {
        this.var_float_x = x;
    }

    public final void setY(float y) {
        this.var_float_y = y;
    }

    public void setZ(float z) {
        this.var_float_z = z;
    }

    public float getZ() {
        return this.var_float_z;
    }

    public void setW(float w) {
        this.var_float_w = w;
    }

    public float getW() {
        return this.var_float_w;
    }

    public String toString() {
        return "Quaternion: " + this.var_float_x + " " + this.var_float_y + " " + this.var_float_z + " " + this.var_float_w;
    }

    public static Quaternion mul(Quaternion left, Quaternion right, Quaternion dest) {
        if (dest == null) {
            dest = new Quaternion();
        }
        dest.set(left.var_float_x * right.var_float_w + left.var_float_w * right.var_float_x + left.var_float_y * right.var_float_z - left.var_float_z * right.var_float_y, left.var_float_y * right.var_float_w + left.var_float_w * right.var_float_y + left.var_float_z * right.var_float_x - left.var_float_x * right.var_float_z, left.var_float_z * right.var_float_w + left.var_float_w * right.var_float_z + left.var_float_x * right.var_float_y - left.var_float_y * right.var_float_x, left.var_float_w * right.var_float_w - left.var_float_x * right.var_float_x - left.var_float_y * right.var_float_y - left.var_float_z * right.var_float_z);
        return dest;
    }

    public static Quaternion mulInverse(Quaternion left, Quaternion right, Quaternion dest) {
        float n = right.lengthSquared();
        float f = n = (double)n == 0.0 ? n : 1.0f / n;
        if (dest == null) {
            dest = new Quaternion();
        }
        dest.set((left.var_float_x * right.var_float_w - left.var_float_w * right.var_float_x - left.var_float_y * right.var_float_z + left.var_float_z * right.var_float_y) * n, (left.var_float_y * right.var_float_w - left.var_float_w * right.var_float_y - left.var_float_z * right.var_float_x + left.var_float_x * right.var_float_z) * n, (left.var_float_z * right.var_float_w - left.var_float_w * right.var_float_z - left.var_float_x * right.var_float_y + left.var_float_y * right.var_float_x) * n, (left.var_float_w * right.var_float_w + left.var_float_x * right.var_float_x + left.var_float_y * right.var_float_y + left.var_float_z * right.var_float_z) * n);
        return dest;
    }

    public final void setFromAxisAngle(Vector4f a1) {
        this.var_float_x = a1.var_float_x;
        this.var_float_y = a1.var_float_y;
        this.var_float_z = a1.var_float_z;
        float n = (float)Math.sqrt(this.var_float_x * this.var_float_x + this.var_float_y * this.var_float_y + this.var_float_z * this.var_float_z);
        float s = (float)(Math.sin(0.5 * (double)a1.var_float_w) / (double)n);
        this.var_float_x *= s;
        this.var_float_y *= s;
        this.var_float_z *= s;
        this.var_float_w = (float)Math.cos(0.5 * (double)a1.var_float_w);
    }

    public final Quaternion setFromMatrix(Matrix4f m) {
        return Quaternion.setFromMatrix(m, this);
    }

    public static Quaternion setFromMatrix(Matrix4f m, Quaternion q) {
        return q.setFromMat(m.m00, m.m01, m.m02, m.m10, m.m11, m.m12, m.m20, m.m21, m.m22);
    }

    public final Quaternion setFromMatrix(Matrix3f m) {
        return Quaternion.setFromMatrix(m, this);
    }

    public static Quaternion setFromMatrix(Matrix3f m, Quaternion q) {
        return q.setFromMat(m.m00, m.m01, m.m02, m.m10, m.m11, m.m12, m.m20, m.m21, m.m22);
    }

    private Quaternion setFromMat(float m00, float m01, float m02, float m10, float m11, float m12, float m20, float m21, float m22) {
        float tr = m00 + m11 + m22;
        if ((double)tr >= 0.0) {
            float s = (float)Math.sqrt((double)tr + 1.0);
            this.var_float_w = s * 0.5f;
            s = 0.5f / s;
            this.var_float_x = (m21 - m12) * s;
            this.var_float_y = (m02 - m20) * s;
            this.var_float_z = (m10 - m01) * s;
        } else {
            float max = Math.max(Math.max(m00, m11), m22);
            if (max == m00) {
                float s = (float)Math.sqrt((double)(m00 - (m11 + m22)) + 1.0);
                this.var_float_x = s * 0.5f;
                s = 0.5f / s;
                this.var_float_y = (m01 + m10) * s;
                this.var_float_z = (m20 + m02) * s;
                this.var_float_w = (m21 - m12) * s;
            } else if (max == m11) {
                float s = (float)Math.sqrt((double)(m11 - (m22 + m00)) + 1.0);
                this.var_float_y = s * 0.5f;
                s = 0.5f / s;
                this.var_float_z = (m12 + m21) * s;
                this.var_float_x = (m01 + m10) * s;
                this.var_float_w = (m02 - m20) * s;
            } else {
                float s = (float)Math.sqrt((double)(m22 - (m00 + m11)) + 1.0);
                this.var_float_z = s * 0.5f;
                s = 0.5f / s;
                this.var_float_x = (m20 + m02) * s;
                this.var_float_y = (m12 + m21) * s;
                this.var_float_w = (m10 - m01) * s;
            }
        }
        return this;
    }
}

