/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.utils;

import gg.vape.wrapper.impl.Vec3;

public class Vec3d {
    private double var_double_z;
    private static String legacyState;
    private double var_double_y;
    private double var_double_x;

    public void setZ(double z) {
        this.var_double_z = z;
    }

    public Vec3d(double d, double d2, double d3) {
        this.var_double_x = d;
        this.var_double_y = d2;
        this.var_double_z = d3;
    }

    public Vec3 toVec3() {
        return Vec3.create(this.var_double_x, this.var_double_y, this.var_double_z);
    }

    public double getZ() {
        return this.var_double_z;
    }

    public void rotateAroundYAxis(float f) {
        float f2 = (float)Math.cos(f);
        float f3 = (float)Math.sin(f);
        double d = this.var_double_x * (double)f2 + this.var_double_z * (double)f3;
        double d2 = this.var_double_y;
        double d3 = this.var_double_z * (double)f2 - this.var_double_x * (double)f3;
        this.var_double_x = d;
        this.var_double_y = d2;
        this.var_double_z = d3;
    }

    public void setX(double x) {
        this.var_double_x = x;
    }

    public double length() {
        return Math.sqrt(this.var_double_x * this.var_double_x + this.var_double_y * this.var_double_y + this.var_double_z * this.var_double_z);
    }

    public static void setLegacyState(String state) {
        legacyState = state;
    }

    public void scale(double factor) {
        this.var_double_x *= factor;
        this.var_double_y *= factor;
        this.var_double_z *= factor;
    }

    public void rotateAroundXAxis(float f) {
        float f2 = (float)Math.cos(f);
        float f3 = (float)Math.sin(f);
        double d = this.var_double_x;
        double d2 = this.var_double_y * (double)f2 + this.var_double_z * (double)f3;
        double d3 = this.var_double_z * (double)f2 - this.var_double_y * (double)f3;
        this.var_double_x = d;
        this.var_double_y = d2;
        this.var_double_z = d3;
    }

    public Vec3d(Vec3 vec3) {
        this.var_double_x = vec3.getX();
        this.var_double_y = vec3.getY();
        this.var_double_z = vec3.getZ();
    }

    public static String getLegacyState() {
        return legacyState;
    }

    public double getY() {
        return this.var_double_y;
    }

    public void translate(double x, double y, double z) {
        this.var_double_x += x;
        this.var_double_y += y;
        this.var_double_z += z;
    }

    public void crossProduct(Vec3d first, Vec3d second) {
        double x = first.var_double_y * second.var_double_z - first.var_double_z * second.var_double_y;
        double y = second.var_double_x * first.var_double_z - second.var_double_z * first.var_double_x;
        this.var_double_z = first.var_double_x * second.var_double_y - first.var_double_y * second.var_double_x;
        this.var_double_x = x;
        this.var_double_y = y;
    }

    public void subtract(double x, double y, double z) {
        this.var_double_x -= x;
        this.var_double_y -= y;
        this.var_double_z -= z;
    }

    public void add(Vec3d vector) {
        this.var_double_x += vector.var_double_x;
        this.var_double_y += vector.var_double_y;
        this.var_double_z += vector.var_double_z;
    }

    public double distanceTo(Vec3d vector) {
        double d = this.var_double_x - vector.var_double_x;
        double d2 = this.var_double_y - vector.var_double_y;
        double d3 = this.var_double_z - vector.var_double_z;
        return Math.sqrt(d * d + d2 * d2 + d3 * d3);
    }

    public void subtract(Vec3d vector) {
        this.var_double_x -= vector.var_double_x;
        this.var_double_y -= vector.var_double_y;
        this.var_double_z -= vector.var_double_z;
    }

    public void setY(double y) {
        this.var_double_y = y;
    }

    public void setCoordinates(double x, double y, double z) {
        this.var_double_x = x;
        this.var_double_y = y;
        this.var_double_z = z;
    }

    public void normalize() {
        double d = 1.0 / this.length();
        this.var_double_x *= d;
        this.var_double_y *= d;
        this.var_double_z *= d;
    }

    public Vec3d() {
        this.var_double_x = 0.0;
        this.var_double_y = 0.0;
        this.var_double_z = 0.0;
    }

    public double dotProduct(Vec3d vector) {
        return this.var_double_x * vector.var_double_x + this.var_double_y * vector.var_double_y + this.var_double_z * vector.var_double_z;
    }

    public double getX() {
        return this.var_double_x;
    }

    static {
        if (Vec3d.getLegacyState() == null) {
            Vec3d.setLegacyState("hgFcEc");
        }
    }
}

