/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.geometry;

public class MutableFloatRect {
    public float var_float_d;
    public float var_float_m;
    public float var_float_y;
    public float var_float_D;

    public void n(float f) {
        this.var_float_y = f;
    }

    public float f() {
        return this.var_float_m;
    }

    public void O(float f) {
        this.var_float_d = f;
    }

    public MutableFloatRect(float f, float f2, float f3, float f4) {
        this.var_float_D = f;
        this.var_float_d = f2;
        this.var_float_y = f3;
        this.var_float_m = f4;
    }

    public float F() {
        return this.var_float_D;
    }

    public void u(float f) {
        this.var_float_D = f;
    }

    public void q(float f) {
        this.var_float_m = f;
    }

    public float V() {
        return this.var_float_y;
    }

    public float E() {
        return this.var_float_d;
    }
}

