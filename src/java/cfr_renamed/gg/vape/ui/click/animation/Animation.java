/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.animation;

public abstract class Animation<T> {
    private boolean var_boolean_i;
    private long var_long_b = 0L;
    private long var_long_p = Long.MAX_VALUE;
    private long var_long_m;
    private static boolean var_boolean_n;
    private long var_long_D = 0L;

    public double X() {
        return -(Math.cos(Math.PI * (double)this.s()) - 1.0) / 2.0;
    }

    public boolean N() {
        return this.q() == 100.0;
    }

    public void u(boolean bl) {
        double d = this.q();
        if (bl) {
            if (d == 0.0) {
                this.c();
                return;
            }
            if (d == 100.0) {
                return;
            }
        } else if (d == 100.0) {
            this.Z();
            return;
        }
    }

    public boolean n() {
        return this.var_boolean_i;
    }

    public Animation(double d) {
        this.var_long_m = (long)(d * 1000.0);
    }

    public static boolean Z$src$Z$cdkyx1() {
        boolean bl = Animation.R$src$Z$c96m65();
        return true;
    }

    public void g(double d) {
        this.var_long_b = (long)(1000.0 * d);
    }

    public static boolean R$src$Z$c96m65() {
        return var_boolean_n;
    }

    public float s() {
        return (float)(this.q() / 100.0);
    }

    public void O() {
        this.var_long_p = 0L;
        this.var_boolean_i = true;
    }

    public boolean I$src$Z$c48gtw() {
        return !this.var_boolean_i && this.q() != 0.0;
    }

    public Animation(double d, double d2) {
        this.var_long_b = this.var_long_D = (long)(d2 * 1000.0);
        this.var_long_m = (long)(d * 1000.0);
    }

    public void U(double d) {
        this.var_long_m = (long)(d * 1000.0);
    }

    public long f() {
        return this.var_long_m;
    }

    public boolean l() {
        double d = this.q();
        return d != 0.0 && d != 100.0;
    }

    public double q() {
        long l = this.var_boolean_i ? this.var_long_b : this.var_long_D;
        long l2 = System.currentTimeMillis() - this.var_long_p - l;
        boolean bl = l > 0L && l2 + l >= 0L;
        double d = Math.min((double)l2 / (double)this.var_long_m * 100.0, 100.0);
        if (d < 0.0) {
            double d2 = d = bl ? 0.1 : 0.0;
        }
        if (this.var_boolean_i) {
            return 100.0 - d;
        }
        return d;
    }

    public Animation(double d, double d2, double d3) {
        this.var_long_m = (long)(d * 1000.0);
        this.var_long_D = (long)(d2 * 1000.0);
        this.var_long_b = (long)(d3 * 1000.0);
    }

    public static void K(boolean bl) {
        var_boolean_n = bl;
    }

    public void P(double d) {
        this.var_long_D = (long)(1000.0 * d);
    }

    public abstract T R();

    public void Z() {
        this.var_long_p = System.currentTimeMillis();
        this.var_boolean_i = true;
    }

    public void C() {
        this.var_long_p = 0L;
        this.var_boolean_i = false;
    }

    public long T() {
        return this.var_long_b;
    }

    public void c() {
        this.var_long_p = System.currentTimeMillis();
        this.var_boolean_i = false;
    }

    public double I() {
        return this.var_long_D / 1000L;
    }

    public void J() {
        double d = this.q();
        if (d == 0.0) {
            this.c();
            return;
        }
        if (d == 100.0) {
            this.Z();
            return;
        }
        long l = System.currentTimeMillis() - this.var_long_p;
        long l2 = this.var_long_m - l;
        this.var_long_p = System.currentTimeMillis() - l2;
        if (this.var_boolean_i) {
            this.var_boolean_i = false;
            return;
        }
        this.var_boolean_i = true;
    }

    static {
        if (Animation.R$src$Z$c96m65()) {
            Animation.K(true);
        }
    }
}

