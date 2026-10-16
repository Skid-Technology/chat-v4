/*
 * Decompiled with CFR 0.152.
 */
package org.lwjgl.util.vector;

import java.io.Serializable;
import java.nio.FloatBuffer;
import org.lwjgl.util.vector.ReadableVector2f;
import org.lwjgl.util.vector.Vector;
import org.lwjgl.util.vector.WritableVector2f;

public class Vector2f
extends Vector
implements Serializable,
ReadableVector2f,
WritableVector2f {
    private final static long serialVersionUID = 1L;
    public float var_float_x;
    public float var_float_y;

    public Vector2f() {
    }

    public Vector2f(ReadableVector2f src) {
        this.set(src);
    }

    public Vector2f(float x, float y) {
        this.set(x, y);
    }

    public void set(float x, float y) {
        this.var_float_x = x;
        this.var_float_y = y;
    }

    public Vector2f set(ReadableVector2f src) {
        this.var_float_x = src.getX();
        this.var_float_y = src.getY();
        return this;
    }

    public float lengthSquared() {
        return this.var_float_x * this.var_float_x + this.var_float_y * this.var_float_y;
    }

    public Vector2f translate(float x, float y) {
        this.var_float_x += x;
        this.var_float_y += y;
        return this;
    }

    public Vector negate() {
        this.var_float_x = -this.var_float_x;
        this.var_float_y = -this.var_float_y;
        return this;
    }

    public Vector2f negate(Vector2f dest) {
        if (dest == null) {
            dest = new Vector2f();
        }
        dest.var_float_x = -this.var_float_x;
        dest.var_float_y = -this.var_float_y;
        return dest;
    }

    public Vector2f normalise(Vector2f dest) {
        float l = this.length();
        if (dest == null) {
            dest = new Vector2f(this.var_float_x / l, this.var_float_y / l);
        } else {
            dest.set(this.var_float_x / l, this.var_float_y / l);
        }
        return dest;
    }

    public static float dot(Vector2f left, Vector2f right) {
        return left.var_float_x * right.var_float_x + left.var_float_y * right.var_float_y;
    }

    public static float angle(Vector2f a, Vector2f b) {
        float dls = Vector2f.dot(a, b) / (a.length() * b.length());
        if (dls < -1.0f) {
            dls = -1.0f;
        } else if (dls > 1.0f) {
            dls = 1.0f;
        }
        return (float)Math.acos(dls);
    }

    public static Vector2f add(Vector2f left, Vector2f right, Vector2f dest) {
        if (dest == null) {
            return new Vector2f(left.var_float_x + right.var_float_x, left.var_float_y + right.var_float_y);
        }
        dest.set(left.var_float_x + right.var_float_x, left.var_float_y + right.var_float_y);
        return dest;
    }

    public static Vector2f sub(Vector2f left, Vector2f right, Vector2f dest) {
        if (dest == null) {
            return new Vector2f(left.var_float_x - right.var_float_x, left.var_float_y - right.var_float_y);
        }
        dest.set(left.var_float_x - right.var_float_x, left.var_float_y - right.var_float_y);
        return dest;
    }

    public Vector store(FloatBuffer buf) {
        buf.put(this.var_float_x);
        buf.put(this.var_float_y);
        return this;
    }

    public Vector load(FloatBuffer buf) {
        this.var_float_x = buf.get();
        this.var_float_y = buf.get();
        return this;
    }

    public Vector scale(float scale) {
        this.var_float_x *= scale;
        this.var_float_y *= scale;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(64);
        sb.append("Vector2f[");
        sb.append(this.var_float_x);
        sb.append(", ");
        sb.append(this.var_float_y);
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
        Vector2f other = (Vector2f)obj;
        return this.var_float_x == other.var_float_x && this.var_float_y == other.var_float_y;
    }
}

