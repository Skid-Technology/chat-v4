/*
 * Decompiled with CFR 0.152.
 */
package org.lwjgl.util;

import java.io.Serializable;
import org.lwjgl.util.ReadablePoint;
import org.lwjgl.util.WritablePoint;

public final class Point
implements ReadablePoint,
WritablePoint,
Serializable {
    final static long serialVersionUID = 1L;
    private int var_int_x;
    private int var_int_y;

    public Point() {
    }

    public Point(int x, int y) {
        this.setLocation(x, y);
    }

    public Point(ReadablePoint p) {
        this.setLocation(p);
    }

    public void setLocation(int x, int y) {
        this.var_int_x = x;
        this.var_int_y = y;
    }

    public void setLocation(ReadablePoint p) {
        this.var_int_x = p.getX();
        this.var_int_y = p.getY();
    }

    public void setX(int x) {
        this.var_int_x = x;
    }

    public void setY(int y) {
        this.var_int_y = y;
    }

    public void translate(int dx, int dy) {
        this.var_int_x += dx;
        this.var_int_y += dy;
    }

    public void translate(ReadablePoint p) {
        this.var_int_x += p.getX();
        this.var_int_y += p.getY();
    }

    public void untranslate(ReadablePoint p) {
        this.var_int_x -= p.getX();
        this.var_int_y -= p.getY();
    }

    public boolean equals(Object obj) {
        if (obj instanceof Point) {
            Point pt = (Point)obj;
            return this.var_int_x == pt.var_int_x && this.var_int_y == pt.var_int_y;
        }
        return super.equals(obj);
    }

    public String toString() {
        return this.getClass().getName() + "[x=" + this.var_int_x + ",y=" + this.var_int_y + "]";
    }

    public int hashCode() {
        int sum = this.var_int_x + this.var_int_y;
        return sum * (sum + 1) / 2 + this.var_int_x;
    }

    public int getX() {
        return this.var_int_x;
    }

    public int getY() {
        return this.var_int_y;
    }

    public void getLocation(WritablePoint dest) {
        dest.setLocation(this.var_int_x, this.var_int_y);
    }
}

