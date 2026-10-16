/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame.impl;

import gg.vape.ui.click.component.gui.InteractiveComponent;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ImageRenderer;
import java.awt.Color;

public class ClientSettingsFrameSectionLabelComponent
extends InteractiveComponent {
    private String var_java_lang_String_K;
    private boolean var_boolean_Q;
    private Color var_java_awt_Color_I;
    private double var_double_v;
    private float var_float_b = 4.0f;
    private final static String cb = "expandarrow";

    @Override
    public double C() {
        return 20.0;
    }

    @Override
    public void I() {
    }

    public ClientSettingsFrameSectionLabelComponent(String string) {
        this(string, 0.9);
    }

    @Override
    public double x() {
        return 110.0;
    }

    @Override
    public void F() {
        this.var_boolean_Q = true;
    }

    @Override
    public void H() {
        float f = 6.88f;
        SmoothFontRenderer smoothFontRenderer = this.getFontRenderer(this.var_double_v);
        double d = smoothFontRenderer.d(this.var_java_lang_String_K);
        double d2 = this.n() + this.L() / 2.0 - d / 2.0;
        double d3 = this.n() + this.L() / 2.0 - (double)(f / 2.0f);
        double d4 = this.n() + this.L() / 2.0 - (double)(this.var_float_b / 2.0f);
        Color color = ClientSettingsFrameSectionLabelComponent.J.var_java_awt_Color_i;
        Color color2 = ClientSettingsFrameSectionLabelComponent.J.var_java_awt_Color_Z;
        if (this.var_boolean_Q) {
            color = ClientSettingsFrameSectionLabelComponent.J.var_java_awt_Color_m;
            color2 = ClientSettingsFrameSectionLabelComponent.J.var_java_awt_Color_A;
        }
        GuiRenderPrimitives.C(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), color);
        ImageRenderer.drawImage(this.var_java_awt_Color_I, (float)this.G$src$D$1b2f02a() + (float)this.A() - 5.0f - 5.0f, (float)d4, cb, this.var_float_b, this.var_float_b, false);
        smoothFontRenderer.d(this.var_java_lang_String_K, this.G$src$D$1b2f02a() + 5.0, d2, color2);
    }

    public ClientSettingsFrameSectionLabelComponent(String string, double d) {
        this.var_java_awt_Color_I = ClientSettingsFrameSectionLabelComponent.J.var_java_awt_Color_W;
        this.var_java_lang_String_K = string;
        this.var_double_v = d;
    }

    @Override
    public void u() {
        if (this.var_boolean_Q && !this.w$src$Z$e457mb()) {
            this.var_boolean_Q = false;
        }
    }
}

