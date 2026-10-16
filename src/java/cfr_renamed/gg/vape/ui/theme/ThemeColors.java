/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.theme;

import gg.vape.Vape;
import gg.vape.module.none.ClientSettings;
import gg.vape.unmap.ColorUtil;
import java.awt.Color;

public class ThemeColors {
    final public Color var_java_awt_Color_s;
    final public Color var_java_awt_Color_Y;
    final public Color var_java_awt_Color_r;
    final public Color var_java_awt_Color_i;
    final public Color var_java_awt_Color_f;
    final public Color var_java_awt_Color_M;
    final public Color var_java_awt_Color_U;
    final public Color var_java_awt_Color_A;
    final public static ThemeColors var_gg_vape_ui_theme_ThemeColors_J;
    final public Color var_java_awt_Color_I;
    final public Color var_java_awt_Color_Q;
    final public Color var_java_awt_Color_V;
    final public Color var_java_awt_Color_u;
    final public Color var_java_awt_Color_N;
    final public Color var_java_awt_Color_Z;
    final public Color var_java_awt_Color_q;
    final public Color var_java_awt_Color_c;
    final public Color var_java_awt_Color_h;
    final public Color var_java_awt_Color_W;
    final public Color var_java_awt_Color_C;
    private static int var_int_j;
    final public Color var_java_awt_Color_l;
    private Color var_java_awt_Color_G;
    final public Color var_java_awt_Color_K;
    final public Color var_java_awt_Color_m;
    final public Color var_java_awt_Color_H;
    final public Color var_java_awt_Color_S;
    final public Color var_java_awt_Color_g;
    final public Color var_java_awt_Color_o;
    final public Color var_java_awt_Color_d;
    final public Color var_java_awt_Color_X;
    final public Color var_java_awt_Color_a;
    final public Color var_java_awt_Color_R;
    final public Color var_java_awt_Color_O;
    final public Color var_java_awt_Color_k;
    final public Color var_java_awt_Color_T;
    final public Color var_java_awt_Color_E;
    final public Color var_java_awt_Color_y;
    final public Color var_java_awt_Color_B = new Color(5, 134, 105);
    final public Color var_java_awt_Color_t;
    final public Color var_java_awt_Color_F;
    private Color var_java_awt_Color_x;
    final public Color var_java_awt_Color_z;

    public ThemeColors() {
        this.var_java_awt_Color_O = new Color(6, 161, 126);
        this.var_java_awt_Color_d = new Color(250, 50, 56);
        this.var_java_awt_Color_c = new Color(255, 89, 94);
        this.var_java_awt_Color_T = new Color(47, 122, 229);
        this.var_java_awt_Color_X = new Color(80, 141, 229);
        this.var_java_awt_Color_I = new Color(236, 129, 44);
        this.var_java_awt_Color_N = new Color(236, 129, 44, 51);
        this.var_java_awt_Color_Y = new Color(255, 160, 84);
        this.var_java_awt_Color_V = new Color(173, 173, 173);
        this.var_java_awt_Color_W = new Color(122, 122, 122);
        this.var_java_awt_Color_f = new Color(209, 209, 209);
        this.var_java_awt_Color_K = new Color(54, 53, 54);
        this.var_java_awt_Color_U = new Color(5, 134, 105);
        this.var_java_awt_Color_r = new Color(20, 20, 20);
        this.var_java_awt_Color_i = new Color(26, 25, 26);
        this.var_java_awt_Color_H = new Color(37, 36, 38);
        this.var_java_awt_Color_S = new Color(37, 36, 37);
        this.var_java_awt_Color_m = new Color(31, 30, 31);
        this.var_java_awt_Color_l = new Color(54, 53, 54, 128);
        this.var_java_awt_Color_F = new Color(46, 45, 47);
        this.var_java_awt_Color_R = new Color(35, 34, 35);
        this.var_java_awt_Color_a = new Color(40, 39, 40);
        this.var_java_awt_Color_o = new Color(49, 48, 49);
        this.var_java_awt_Color_y = new Color(54, 53, 54);
        this.var_java_awt_Color_k = new Color(255, 255, 255, 10);
        this.var_java_awt_Color_A = new Color(209, 209, 209);
        this.var_java_awt_Color_Z = new Color(163, 163, 163);
        this.var_java_awt_Color_h = new Color(89, 88, 89);
        this.var_java_awt_Color_s = new Color(255, 255, 255, 22);
        this.var_java_awt_Color_Q = new Color(45, 46, 47);
        this.var_java_awt_Color_C = new Color(115, 113, 115);
        this.var_java_awt_Color_g = new Color(44, 43, 44, 255);
        this.var_java_awt_Color_t = new Color(0, 0, 0, 0);
        this.var_java_awt_Color_E = new Color(255, 255, 255, 5);
        this.var_java_awt_Color_z = new Color(255, 255, 255, 10);
        this.var_java_awt_Color_M = new Color(255, 255, 255, 15);
        this.var_java_awt_Color_q = new Color(236, 170, 118);
        this.var_java_awt_Color_u = new Color(0, 0, 0, 152);
        this.var_java_awt_Color_x = Color.WHITE;
        this.var_java_awt_Color_G = Color.BLACK;
    }

    public static int U() {
        int n = ThemeColors.W();
        return 15;
    }

    public Color z() {
        return ClientSettings.INSTANCE.getAccentColor();
    }

    public Color B() {
        if (Vape.INSTANCE.getClientSettings().guiColor.isRainbowEnabled()) {
            return new Color(45, 45, 45);
        }
        if (this.z().equals(this.var_java_awt_Color_x)) {
            return this.var_java_awt_Color_G;
        }
        this.var_java_awt_Color_x = this.z();
        this.var_java_awt_Color_G = ColorUtil.getContrastingGray(this.var_java_awt_Color_x, 45, 240);
        return this.var_java_awt_Color_G;
    }

    public static int W() {
        return var_int_j;
    }

    public static void l(int n) {
        var_int_j = n;
    }

    static {
        var_gg_vape_ui_theme_ThemeColors_J = new ThemeColors();
        ThemeColors.l(0);
    }
}

