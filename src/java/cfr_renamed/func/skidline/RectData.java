/*
 * Decompiled with CFR 0.152.
 */
package func.skidline;

import gg.vape.ui.click.MousePosition;
import gg.vape.utils.MathUtil;
import java.awt.Point;

public class RectData {
    private final static String var_java_lang_String_b;
    private double var_double_Z;
    private double var_double_d;
    private double var_double_c;
    private static int var_int_l;
    private double var_double_Y;

    public boolean Z(MousePosition mousePosition) {
        return this.J(mousePosition.var_int_O, mousePosition.var_int_H);
    }

    public boolean q(RectData rectData) {
        return this.c(rectData) > 0.0;
    }

    public static int B() {
        int n = RectData.X();
        return 78;
    }

    public void A(double d) {
        this.var_double_Y = d;
    }

    public double c(RectData rectData) {
        double d = Math.max(0.0, Math.min(this.o() + this.e(), rectData.o() + rectData.e()) - Math.max(this.o(), rectData.o()));
        double d2 = Math.max(0.0, Math.min(this.W() + this.R(), rectData.W() + rectData.R()) - Math.max(this.W(), rectData.W()));
        return d * d2;
    }

    public void M(double d) {
        this.var_double_d = d;
    }

    public RectData d() {
        return new RectData(this.o(), this.W(), this.e(), this.R());
    }

    public boolean z(double d, double d2, double d3) {
        if (MathUtil.O(d, d2, d3, this.var_double_d, this.var_double_Z) || MathUtil.O(d, d2, d3, this.var_double_d + this.var_double_Y, this.var_double_Z) || MathUtil.O(d, d2, d3, this.var_double_d + this.var_double_Y, this.var_double_Z + this.var_double_c) || MathUtil.O(d, d2, d3, this.var_double_d, this.var_double_Z + this.var_double_c)) {
            return MathUtil.H(d, d2, d3, this.var_double_d, this.var_double_Z) || MathUtil.H(d, d2, d3, this.var_double_d + this.var_double_Y, this.var_double_Z) || MathUtil.H(d, d2, d3, this.var_double_d + this.var_double_Y, this.var_double_Z + this.var_double_c) || MathUtil.H(d, d2, d3, this.var_double_d, this.var_double_Z + this.var_double_c);
        }
        return false;
    }

    public double e() {
        return this.var_double_Y;
    }

    public double o() {
        return this.var_double_d;
    }

    public double W() {
        return this.var_double_Z;
    }

    public String toString() {
        return String.format(var_java_lang_String_b, this.o(), this.W(), this.e(), this.R());
    }

    public RectData x(double d, double d2) {
        return new RectData(d + this.o(), d2 + this.W(), this.e(), this.R());
    }

    public static int X() {
        return var_int_l;
    }

    public RectData(double d, double d2, double d3, double d4) {
        this.var_double_d = d;
        this.var_double_Z = d2;
        this.var_double_Y = d3;
        this.var_double_c = d4;
    }

    public boolean J(double d, double d2) {
        return d >= this.o() && d <= this.o() + this.e() && d2 >= this.W() && d2 <= this.W() + this.R();
    }

    public void O(double d) {
        this.var_double_Z = d;
    }

    public static void b(int n) {
        var_int_l = n;
    }

    public RectData y(double d, double d2) {
        return new RectData(this.o() - d, this.W() - d2, this.e() + d * 2.0, this.R() + d2 * 2.0);
    }

    public boolean R(Point point) {
        return this.J(point.var_int_x, point.var_int_y);
    }

    public double R() {
        return this.var_double_c;
    }

    public void U(double d) {
        this.var_double_c = d;
    }

    static {
        RectData.b(0);
        var_java_lang_String_b = "[%s, %s, %s, %s]";
    }
}

