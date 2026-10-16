/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.animation;

import gg.vape.ui.click.MousePosition;
import gg.vape.ui.click.animation.Animation;

public class MousePositionAnimation
extends Animation<MousePosition> {
    private MousePosition var_gg_vape_ui_click_MousePosition_X;
    private MousePosition var_gg_vape_ui_click_MousePosition_e;
    private int var_int_G;
    private int var_int_w;

    public MousePositionAnimation(double d, MousePosition mousePosition, MousePosition mousePosition2) {
        super(d);
        this.var_gg_vape_ui_click_MousePosition_X = mousePosition;
        this.var_gg_vape_ui_click_MousePosition_e = mousePosition2;
        this.var_int_G = mousePosition2.var_int_O - mousePosition.var_int_O;
        this.var_int_w = mousePosition2.var_int_H - mousePosition.var_int_H;
    }

    public void I(MousePosition mousePosition) {
        this.var_gg_vape_ui_click_MousePosition_X = mousePosition;
        this.var_int_G = this.var_gg_vape_ui_click_MousePosition_e.var_int_O - mousePosition.var_int_O;
        this.var_int_w = this.var_gg_vape_ui_click_MousePosition_e.var_int_H - mousePosition.var_int_H;
    }

    public MousePosition k() {
        double d = this.q();
        if (d == 0.0) {
            return this.var_gg_vape_ui_click_MousePosition_X;
        }
        if (d == 100.0) {
            return this.var_gg_vape_ui_click_MousePosition_e;
        }
        int n = this.var_gg_vape_ui_click_MousePosition_X.var_int_O + (int)(this.q() * (double)this.var_int_G / 100.0);
        int n2 = this.var_gg_vape_ui_click_MousePosition_X.var_int_H + (int)(this.q() * (double)this.var_int_w / 100.0);
        return new MousePosition(n, n2);
    }

    @Override
    public MousePosition R() {
        return this.k();
    }

    public MousePosition Y() {
        return this.var_gg_vape_ui_click_MousePosition_e;
    }

    public void B(MousePosition mousePosition) {
        this.var_gg_vape_ui_click_MousePosition_e = mousePosition;
        this.var_int_G = mousePosition.var_int_O - this.var_gg_vape_ui_click_MousePosition_X.var_int_O;
        this.var_int_w = mousePosition.var_int_H - this.var_gg_vape_ui_click_MousePosition_X.var_int_H;
    }

    public MousePosition o() {
        return this.var_gg_vape_ui_click_MousePosition_X;
    }

    @Override
    public Object R() {
        return this.R();
    }
}

