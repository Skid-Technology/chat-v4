/*
 * Decompiled with CFR 0.152.
 */
package org.lwjgl.util.vector;

import java.io.Serializable;
import java.nio.FloatBuffer;
import org.lwjgl.util.vector.ReadableVector3f;
import org.lwjgl.util.vector.Vector;
import org.lwjgl.util.vector.WritableVector3f;

public class Vector3f
extends Vector
implements Serializable,
ReadableVector3f,
WritableVector3f {
    private final static long serialVersionUID = 1L;
    public float var_float_x;
    public float var_float_y;
    public float var_float_z;

    public Vector3f() {
    }

    public Vector3f(ReadableVector3f src) {
        this.set(src);
    }

    public Vector3f(float x, float y, float z) {
        this.set(x, y, z);
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

    public Vector3f set(ReadableVector3f src) {
        this.var_float_x = src.getX();
        this.var_float_y = src.getY();
        this.var_float_z = src.getZ();
        return this;
    }

    public float lengthSquared() {
        return this.var_float_x * this.var_float_x + this.var_float_y * this.var_float_y + this.var_float_z * this.var_float_z;
    }

    public Vector3f translate(float x, float y, float z) {
        this.var_float_x += x;
        this.var_float_y += y;
        this.var_float_z += z;
        return this;
    }

    public static Vector3f add(Vector3f left, Vector3f right, Vector3f dest) {
        if (dest == null) {
            return new Vector3f(left.var_float_x + right.var_float_x, left.var_float_y + right.var_float_y, left.var_float_z + right.var_float_z);
        }
        dest.set(left.var_float_x + right.var_float_x, left.var_float_y + right.var_float_y, left.var_float_z + right.var_float_z);
        return dest;
    }

    public static Vector3f sub(Vector3f left, Vector3f right, Vector3f dest) {
        if (dest == null) {
            return new Vector3f(left.var_float_x - right.var_float_x, left.var_float_y - right.var_float_y, left.var_float_z - right.var_float_z);
        }
        dest.set(left.var_float_x - right.var_float_x, left.var_float_y - right.var_float_y, left.var_float_z - right.var_float_z);
        return dest;
    }

    public static Vector3f cross(Vector3f left, Vector3f right, Vector3f dest) {
        if (dest == null) {
            dest = new Vector3f();
        }
        dest.set(left.var_float_y * right.var_float_z - left.var_float_z * right.var_float_y, right.var_float_x * left.var_float_z - right.var_float_z * left.var_float_x, left.var_float_x * right.var_float_y - left.var_float_y * right.var_float_x);
        return dest;
    }

    public Vector negate() {
        this.var_float_x = -this.var_float_x;
        this.var_float_y = -this.var_float_y;
        this.var_float_z = -this.var_float_z;
        return this;
    }

    public Vector3f negate(Vector3f dest) {
        if (dest == null) {
            dest = new Vector3f();
        }
        dest.var_float_x = -this.var_float_x;
        dest.var_float_y = -this.var_float_y;
        dest.var_float_z = -this.var_float_z;
        return dest;
    }

    public Vector3f normalise(Vector3f dest) {
        float l = this.length();
        if (dest == null) {
            dest = new Vector3f(this.var_float_x / l, this.var_float_y / l, this.var_float_z / l);
        } else {
            dest.set(this.var_float_x / l, this.var_float_y / l, this.var_float_z / l);
        }
        return dest;
    }

    public static float dot(Vector3f left, Vector3f right) {
        return left.var_float_x * right.var_float_x + left.var_float_y * right.var_float_y + left.var_float_z * right.var_float_z;
    }

    public static float angle(Vector3f a, Vector3f b) {
        float dls = Vector3f.dot(a, b) / (a.length() * b.length());
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
        return this;
    }

    public Vector scale(float scale) {
        this.var_float_x *= scale;
        this.var_float_y *= scale;
        this.var_float_z *= scale;
        return this;
    }

    public Vector store(FloatBuffer buf) {
        buf.put(this.var_float_x);
        buf.put(this.var_float_y);
        buf.put(this.var_float_z);
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(64);
        sb.append("Vector3f[");
        sb.append(this.var_float_x);
        sb.append(", ");
        sb.append(this.var_float_y);
        sb.append(", ");
        sb.append(this.var_float_z);
        sb.append(']');
        return sb.toString();
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
        Vector3f other = (Vector3f)obj;
        return this.var_float_x == other.var_float_x && this.var_float_y == other.var_float_y && this.var_float_z == other.var_float_z;
    }
}

