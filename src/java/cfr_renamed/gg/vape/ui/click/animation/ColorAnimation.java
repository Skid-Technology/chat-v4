/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package gg.vape.ui.click.animation;

import gg.vape.ui.click.animation.Animation;
import java.awt.Color;
import org.jetbrains.annotations.Nullable;

public class ColorAnimation
extends Animation<Color> {
    private int var_int_C;
    private Color var_java_awt_Color_a;
    private static boolean var_boolean_c;
    private int var_int_j;
    private int var_int_g;
    private int var_int_M;
    private int var_int_h;
    private int var_int_P;
    private int var_int_v;
    private Color var_java_awt_Color_s;
    private int var_int_G;

    public static ColorAnimation Y(@Nullable Color color) {
        return new ColorAnimation(0.0, color, color);
    }

    public static boolean b() {
        return var_boolean_c;
    }

    public Color getStartColor() {
        return this.var_java_awt_Color_a;
    }

    public static void U(boolean bl) {
        var_boolean_c = bl;
    }

    public static boolean k() {
        boolean bl = ColorAnimation.b();
        return !bl;
    }

    public Color getEndColor() {
        return this.var_java_awt_Color_s;
    }

    public Color getInterpolatedColor() {
        double d = this.q();
        if (d == 0.0) {
            return this.var_java_awt_Color_a;
        }
        if (d == 100.0) {
            return this.var_java_awt_Color_s;
        }
        int n = this.var_int_g + (int)(this.q() * (double)this.var_int_G / 100.0);
        int n2 = this.var_int_v + (int)(this.q() * (double)this.var_int_j / 100.0);
        int n3 = this.var_int_M + (int)(this.q() * (double)this.var_int_h / 100.0);
        int n4 = this.var_int_P + (int)(this.q() * (double)this.var_int_C / 100.0);
        return new Color(n, n2, n3, n4);
    }

    @Override
    public Color R() {
        return this.getInterpolatedColor();
    }

    public void setEndColor(Color color) {
        this.var_java_awt_Color_s = color;
        this.var_int_G = color.getRed() - this.var_int_g;
        this.var_int_j = color.getGreen() - this.var_int_v;
        this.var_int_h = color.getBlue() - this.var_int_M;
        this.var_int_C = color.getAlpha() - this.var_int_P;
    }

    public ColorAnimation(double d, @Nullable Color color, @Nullable Color color2) {
        super(d);
        this.var_java_awt_Color_a = color != null ? color : new Color(0, 0, 0, 0);
        this.var_java_awt_Color_s = color2 != null ? color2 : new Color(0, 0, 0, 0);
        this.var_int_g = this.var_java_awt_Color_a.getRed();
        this.var_int_v = this.var_java_awt_Color_a.getGreen();
        this.var_int_M = this.var_java_awt_Color_a.getBlue();
        this.var_int_P = this.var_java_awt_Color_a.getAlpha();
        this.var_int_G = this.var_java_awt_Color_s.getRed() - this.var_int_g;
        this.var_int_j = this.var_java_awt_Color_s.getGreen() - this.var_int_v;
        this.var_int_h = this.var_java_awt_Color_s.getBlue() - this.var_int_M;
        this.var_int_C = this.var_java_awt_Color_s.getAlpha() - this.var_int_P;
    }

    public void setStartColor(Color color) {
        this.var_java_awt_Color_a = color;
        this.var_int_g = color.getRed();
        this.var_int_v = color.getGreen();
        this.var_int_M = color.getBlue();
        this.var_int_P = color.getAlpha();
        this.var_int_G = this.var_java_awt_Color_s.getRed() - this.var_int_g;
        this.var_int_j = this.var_java_awt_Color_s.getGreen() - this.var_int_v;
        this.var_int_h = this.var_java_awt_Color_s.getBlue() - this.var_int_M;
        this.var_int_C = this.var_java_awt_Color_s.getAlpha() - this.var_int_P;
    }

    @Override
    public Object R() {
        return this.R();
    }

    static {
        if (!ColorAnimation.k()) {
            ColorAnimation.U(true);
        }
    }
}

