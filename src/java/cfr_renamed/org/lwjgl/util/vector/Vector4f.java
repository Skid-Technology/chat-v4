/*
 * Decompiled with CFR 0.152.
 */
package org.lwjgl.util.vector;

import java.io.Serializable;
import java.nio.FloatBuffer;
import org.lwjgl.util.vector.ReadableVector4f;
import org.lwjgl.util.vector.Vector;
import org.lwjgl.util.vector.WritableVector4f;

public class Vector4f
extends Vector
implements Serializable,
ReadableVector4f,
WritableVector4f {
    private final static long serialVersionUID = 1L;
    public float var_float_x;
    public float var_float_y;
    public float var_float_z;
    public float var_float_w;

    public Vector4f() {
    }

    public Vector4f(ReadableVector4f src) {
        this.set(src);
    }

    public Vector4f(float x, float y, float z, float w) {
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

    public Vector4f set(ReadableVector4f src) {
        this.var_float_x = src.getX();
        this.var_float_y = src.getY();
        this.var_float_z = src.getZ();
        this.var_float_w = src.getW();
        return this;
    }

    public float lengthSquared() {
        return this.var_float_x * this.var_float_x + this.var_float_y * this.var_float_y + this.var_float_z * this.var_float_z + this.var_float_w * this.var_float_w;
    }

    public Vector4f translate(float x, float y, float z, float w) {
        this.var_float_x += x;
        this.var_float_y += y;
        this.var_float_z += z;
        this.var_float_w += w;
        return this;
    }

    public static Vector4f add(Vector4f left, Vector4f right, Vector4f dest) {
        if (dest == null) {
            return new Vector4f(left.var_float_x + right.var_float_x, left.var_float_y + right.var_float_y, left.var_float_z + right.var_float_z, left.var_float_w + right.var_float_w);
        }
        dest.set(left.var_float_x + right.var_float_x, left.var_float_y + right.var_float_y, left.var_float_z + right.var_float_z, left.var_float_w + right.var_float_w);
        return dest;
    }

    public static Vector4f sub(Vector4f left, Vector4f right, Vector4f dest) {
        if (dest == null) {
            return new Vector4f(left.var_float_x - right.var_float_x, left.var_float_y - right.var_float_y, left.var_float_z - right.var_float_z, left.var_float_w - right.var_float_w);
        }
        dest.set(left.var_float_x - right.var_float_x, left.var_float_y - right.var_float_y, left.var_float_z - right.var_float_z, left.var_float_w - right.var_float_w);
        return dest;
    }

    public Vector negate() {
        this.var_float_x = -this.var_float_x;
        this.var_float_y = -this.var_float_y;
        this.var_float_z = -this.var_float_z;
        this.var_float_w = -this.var_float_w;
        return this;
    }

    public Vector4f negate(Vector4f dest) {
        if (dest == null) {
            dest = new Vector4f();
        }
        dest.var_float_x = -this.var_float_x;
        dest.var_float_y = -this.var_float_y;
        dest.var_float_z = -this.var_float_z;
        dest.var_float_w = -this.var_float_w;
        return dest;
    }

    public Vector4f normalise(Vector4f dest) {
        float l = this.length();
        if (dest == null) {
            dest = new Vector4f(this.var_float_x / l, this.var_float_y / l, this.var_float_z / l, this.var_float_w / l);
        } else {
            dest.set(this.var_float_x / l, this.var_float_y / l, this.var_float_z / l, this.var_float_w / l);
        }
        return dest;
    }

    public static float dot(Vector4f left, Vector4f right) {
        return left.var_float_x * right.var_float_x + left.var_float_y * right.var_float_y + left.var_float_z * right.var_float_z + left.var_float_w * right.var_float_w;
    }

    public static float angle(Vector4f a, Vector4f b) {
        float dls = Vector4f.dot(a, b) / (a.length() * b.length());
        if (dls < -1.0f) {
            dls = -1.0f;
        } else if (dls > 1.0f) {
            dls = 1.0f;
        }
        return (float)Math.acos(dls);
    }

    public Vector load(FloatBuffer buf) {
        this.var_float_x = buf.get();
        this.var_float_y = buf.get();
        this.var_float_z = buf.get();
        this.var_float_w = buf.get();
        return this;
    }

    public Vector scale(float scale) {
        this.var_float_x *= scale;
        this.var_float_y *= scale;
        this.var_float_z *= scale;
        this.var_float_w *= scale;
        return this;
    }

    public Vector store(FloatBuffer buf) {
        buf.put(this.var_float_x);
        buf.put(this.var_float_y);
        buf.put(this.var_float_z);
        buf.put(this.var_float_w);
        return this;
    }

    public String toString() {
        return "Vector4f: " + this.var_float_x + " " + this.var_float_y + " " + this.var_float_z + " " + this.var_float_w;
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

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Vector4f other = (Vector4f)obj;
        return this.var_float_x == other.var_float_x && this.var_float_y == other.var_float_y && this.var_float_z == other.var_float_z && this.var_float_w == other.var_float_w;
    }
}

