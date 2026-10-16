/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.layout;

import gg.vape.ui.click.layout.LayoutAnchor;
import gg.vape.ui.click.layout.LayoutDock;

public class LayoutConstraints {
    private int var_int_m;
    private LayoutDock var_gg_vape_ui_click_layout_LayoutDock_r = LayoutDock.NONE;
    private boolean var_boolean_D;
    private LayoutAnchor var_gg_vape_ui_click_layout_LayoutAnchor_H = LayoutAnchor.NONE;
    private int var_int_i;
    private double var_double_j = 0.0;
    private boolean var_boolean_U;
    private boolean var_boolean_A = true;
    private boolean var_boolean_S;
    private static String var_java_lang_String_g;
    private int var_int_q;
    private int var_int_x;

    public void U(boolean bl) {
        this.var_boolean_U = bl;
    }

    public void X(int n) {
        this.var_int_m = n;
        this.var_gg_vape_ui_click_layout_LayoutAnchor_H = LayoutAnchor.OFFSET;
    }

    public int c() {
        return this.var_int_i;
    }

    public void v(LayoutAnchor g5) {
        this.var_gg_vape_ui_click_layout_LayoutAnchor_H = g5;
    }

    public int int_m() {
        return this.var_int_q;
    }

    public static String java_lang_String_m() {
        return var_java_lang_String_g;
    }

    public double double_G() {
        return this.var_double_j;
    }

    public LayoutAnchor P() {
        return this.var_gg_vape_ui_click_layout_LayoutAnchor_H;
    }

    public void M(int n) {
        this.var_int_x = n;
    }

    public int L() {
        return this.var_int_x;
    }

    public LayoutDock t() {
        return this.var_gg_vape_ui_click_layout_LayoutDock_r;
    }

    public boolean boolean_G() {
        return this.var_boolean_U;
    }

    public boolean K() {
        boolean bl = this.var_double_j > 0.0;
        return bl;
    }

    public static void f(String string) {
        var_java_lang_String_g = string;
    }

    public void Q(double d) {
        this.var_double_j = d;
    }

    public void b(boolean bl) {
        this.var_boolean_D = bl;
    }

    public void O(boolean bl) {
        this.var_boolean_S = bl;
    }

    public boolean boolean_a() {
        boolean bl = this.var_gg_vape_ui_click_layout_LayoutAnchor_H != LayoutAnchor.NONE;
        return bl;
    }

    public boolean V() {
        return this.var_boolean_S;
    }

    public void Q(int n) {
        this.var_int_q = n;
        this.var_gg_vape_ui_click_layout_LayoutAnchor_H = LayoutAnchor.OFFSET;
    }

    public boolean Y() {
        return this.var_boolean_D;
    }

    public void s(LayoutDock va_22) {
        this.var_gg_vape_ui_click_layout_LayoutDock_r = va_22;
    }

    public int int_a() {
        return this.var_int_m;
    }

    public boolean h() {
        return this.var_boolean_A;
    }

    public void B(int n) {
        this.var_int_i = n;
    }

    public void N(boolean bl) {
        this.var_boolean_A = bl;
    }

    public double G() {
        return this.double_G();
    }

    public boolean G$src$Z$1xi35hg() {
        return this.boolean_G();
    }

    public int a() {
        return this.int_a();
    }

    public boolean a$src$Z$1xwdswu() {
        return this.boolean_a();
    }

    public int m() {
        return this.int_m();
    }

    static {
        if (LayoutConstraints.java_lang_String_m() == null) {
            LayoutConstraints.f("S8QZvb");
        }
    }
}

