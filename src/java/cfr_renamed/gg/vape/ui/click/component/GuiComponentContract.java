/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.component;

import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.theme.ThemeColors;

public interface GuiComponentContract {
    final public static double var_double_t = 110.0;
    final public static ThemeColors var_gg_vape_ui_theme_ThemeColors_J;
    final public static double var_double_p = 20.0;

    default public void F() {
    }

    default public double x() {
        return 0.0;
    }

    default public void onEnable() {
    }

    default public void H() {
    }

    public void K(double var1);

    public double G$src$D$1b2f02a();

    public double n();

    public boolean V$src$Z$1xhop3l();

    public double L();

    default public double C() {
        return 0.0;
    }

    default public void g(GuiMouseEvent guiMouseEvent) {
    }

    public double A();

    default public void I() {
    }

    public void S(double var1);

    default public void u() {
    }

    public void Y(double var1);

    public void o(double var1);

    default public void U(GuiMouseEvent guiMouseEvent) {
    }

    static {
        var_gg_vape_ui_theme_ThemeColors_J = ThemeColors.var_gg_vape_ui_theme_ThemeColors_J;
    }
}

