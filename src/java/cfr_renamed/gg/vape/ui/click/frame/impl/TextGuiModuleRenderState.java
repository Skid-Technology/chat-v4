/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame.impl;

import func.skidline.RectData;
import gg.vape.module.Mod;

public class TextGuiModuleRenderState {
    private float var_float_T;
    private boolean var_boolean_W;
    private Mod var_gg_vape_module_Mod_o;
    private RectData var_func_skidline_RectData_k = null;
    private boolean var_boolean_B;

    public static RectData H(TextGuiModuleRenderState textGuiModuleRenderState, RectData rectData) {
        textGuiModuleRenderState.var_func_skidline_RectData_k = rectData;
        return textGuiModuleRenderState.var_func_skidline_RectData_k;
    }

    public void a(double d, double d2, double d3, double d4) {
        if (this.var_func_skidline_RectData_k == null) {
            this.var_func_skidline_RectData_k = new RectData(d, d2, d3, d4);
        }
        this.var_func_skidline_RectData_k.M(d);
        this.var_func_skidline_RectData_k.O(d2);
        this.var_func_skidline_RectData_k.A(d3);
        this.var_func_skidline_RectData_k.U(d4);
    }

    public void T(boolean bl) {
        this.var_boolean_W = bl;
    }

    public TextGuiModuleRenderState(Mod mod) {
        this.var_gg_vape_module_Mod_o = mod;
    }

    public void w(boolean bl) {
        this.var_boolean_B = bl;
    }

    public boolean W() {
        return this.var_boolean_W;
    }

    public static boolean a(TextGuiModuleRenderState textGuiModuleRenderState) {
        return textGuiModuleRenderState.var_boolean_B;
    }

    public static RectData J(TextGuiModuleRenderState textGuiModuleRenderState) {
        return textGuiModuleRenderState.var_func_skidline_RectData_k;
    }

    public float L() {
        return this.var_float_T;
    }

    public static float f(TextGuiModuleRenderState textGuiModuleRenderState, float f) {
        textGuiModuleRenderState.var_float_T = f;
        return textGuiModuleRenderState.var_float_T;
    }

    public static float G(TextGuiModuleRenderState textGuiModuleRenderState) {
        return textGuiModuleRenderState.var_float_T;
    }

    public void f(float f) {
        this.var_float_T = f;
    }

    public static boolean h(TextGuiModuleRenderState textGuiModuleRenderState) {
        return textGuiModuleRenderState.var_boolean_W;
    }
}

