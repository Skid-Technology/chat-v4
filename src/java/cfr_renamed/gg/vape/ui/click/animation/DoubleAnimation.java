/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.animation;

import gg.vape.ui.click.animation.Animation;

public class DoubleAnimation
extends Animation<Double> {
    private double var_double_f;
    private double var_double_A;
    private double var_double_S;

    public Double getInterpolatedValue() {
        if (this.f() == 0L) {
            return this.var_double_S;
        }
        double d = this.q();
        if (d == 0.0) {
            return this.var_double_f;
        }
        if (d == 100.0) {
            return this.var_double_S;
        }
        return this.var_double_f + this.q() * this.var_double_A / 100.0;
    }

    @Override
    public Double R() {
        return this.getInterpolatedValue();
    }

    public double getEndValue() {
        return this.var_double_S;
    }

    public DoubleAnimation(double d, double d2, double d3, double d4) {
        super(d, d2);
        this.var_double_f = d3;
        this.var_double_S = d4;
        this.var_double_A = d4 - d3;
    }

    public DoubleAnimation(double d, double d2, double d3) {
        super(d);
        this.var_double_f = d2;
        this.var_double_S = d3;
        this.var_double_A = d3 - d2;
    }

    public DoubleAnimation(double d, double d2, double d3, double d4, double d5) {
        super(d, d2, d3);
        this.var_double_f = d4;
        this.var_double_S = d5;
        this.var_double_A = d5 - d4;
    }

    public void setEndValue(double d) {
        this.var_double_S = d;
        this.var_double_A = d - this.var_double_f;
    }

    public double getStartValue() {
        return this.var_double_f;
    }

    @Override
    public Object R() {
        return this.R();
    }
}

