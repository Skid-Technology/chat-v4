/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.animation.DoubleAnimation;
import gg.vape.ui.click.component.gui.InteractiveComponent;
import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.click.frame.FrameNavigationButtonClickListener;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.unmap.ColorUtil;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ImageRenderer;
import java.awt.Color;

public class FrameNavigationButtonComponent
extends InteractiveComponent {
    private boolean var_boolean_K;
    private String _X;
    private boolean var_boolean_b;
    private double var_double_I;
    private Color _J;
    private String _o;
    private Frame var_gg_vape_ui_click_frame_Frame_v;
    private final static String db = "expandarrow";
    private int _T;
    private DoubleAnimation var_gg_vape_ui_click_animation_DoubleAnimation_Q = new DoubleAnimation(0.15, 0.0, 3.0);
    private float _V = 4.0f;

    @Override
    public double C() {
        return 20.0;
    }

    public FrameNavigationButtonComponent(String string, String string2) {
        this(string, string2, 0.9);
    }

    static boolean K(FrameNavigationButtonComponent frameNavigationButtonComponent) {
        return frameNavigationButtonComponent.var_boolean_K;
    }

    @Override
    public double x() {
        return 110.0;
    }

    @Override
    public void I() {
    }

    public String N$src$Ljava_lang_String_$wy122q() {
        return this._o;
    }

    public FrameNavigationButtonComponent(String string, String string2, Class clazz) {
        this(string, string2);
        this.var_gg_vape_ui_click_frame_Frame_v = ClientSettings.getFrame(clazz);
        this.D$src$V$16hz67e();
    }

    public FrameNavigationButtonComponent(String string, String string2, double d) {
        this._J = FrameNavigationButtonComponent.J.var_java_awt_Color_W;
        this._T = 0;
        this._o = string;
        this._X = string2;
        this.var_double_I = d;
        this.var_gg_vape_ui_click_frame_Frame_v = ClientSettings.findFrameByName(string);
        this.addClickListener(new FrameNavigationButtonClickListener(this, string));
        this.D$src$V$16hz67e();
    }

    public DoubleAnimation A$src$Lgg_vape_ui_click_animation_DoubleAnimation_$olnbbk() {
        return this.var_gg_vape_ui_click_animation_DoubleAnimation_Q;
    }

    static boolean u(FrameNavigationButtonComponent frameNavigationButtonComponent, boolean bl) {
        frameNavigationButtonComponent.var_boolean_K = bl;
        return frameNavigationButtonComponent.var_boolean_K;
    }

    static DoubleAnimation d(FrameNavigationButtonComponent frameNavigationButtonComponent) {
        return frameNavigationButtonComponent.var_gg_vape_ui_click_animation_DoubleAnimation_Q;
    }

    @Override
    public void i$src$V$c9opdk() {
        this.D$src$V$16hz67e();
    }

    private void v(Frame frame) {
        if (frame != null) {
            if (frame.V$src$Z$1xhop3l()) {
                if (!this.var_gg_vape_ui_click_animation_DoubleAnimation_Q.I$src$Z$c48gtw()) {
                    this.var_gg_vape_ui_click_animation_DoubleAnimation_Q.c();
                    this.var_boolean_K = true;
                }
            } else if (this.var_gg_vape_ui_click_animation_DoubleAnimation_Q.I$src$Z$c48gtw()) {
                this.var_gg_vape_ui_click_animation_DoubleAnimation_Q.Z();
                this.var_boolean_K = false;
            }
        }
    }

    @Override
    public void u() {
        if (this.var_boolean_b && !this.w$src$Z$e457mb()) {
            this.var_boolean_b = false;
        }
        if (this.var_gg_vape_ui_click_frame_Frame_v == null) {
            this.var_gg_vape_ui_click_frame_Frame_v = ClientSettings.findFrameByName(this._o);
        }
        if (this.var_gg_vape_ui_click_frame_Frame_v != null) {
            this.v(this.var_gg_vape_ui_click_frame_Frame_v);
        }
    }

    @Override
    public void F() {
        this.var_boolean_b = true;
    }

    public boolean d$src$Z$16zklae() {
        return this.var_boolean_K;
    }

    @Override
    public void H() {
        float f = 6.72f;
        SmoothFontRenderer smoothFontRenderer = this.getFontRenderer(this.var_double_I);
        double d = smoothFontRenderer.d(this._o);
        double d2 = this.n() + this.L() / 2.0 - d / 2.0;
        double d3 = this.n() + this.L() / 2.0 - (double)(this._V / 2.0f);
        Color color = FrameNavigationButtonComponent.J.var_java_awt_Color_i;
        Color color2 = FrameNavigationButtonComponent.J.var_java_awt_Color_Z;
        if (this.var_boolean_b) {
            color = FrameNavigationButtonComponent.J.var_java_awt_Color_m;
            color2 = FrameNavigationButtonComponent.J.var_java_awt_Color_A;
        }
        if (this.var_boolean_K) {
            color = FrameNavigationButtonComponent.J.var_java_awt_Color_m;
            color2 = J.z();
            if (this.var_boolean_b) {
                color2 = ColorUtil.offsetRgb(color2, 30.0);
            }
        }
        GuiRenderPrimitives.C(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), color);
        ImageRenderer.drawImage(this._J, (float)this.G$src$D$1b2f02a() + (float)this.A() - 5.0f - 5.0f + this.var_gg_vape_ui_click_animation_DoubleAnimation_Q.getInterpolatedValue().floatValue(), (float)d3, db, this._V, this._V, false);
        if (this._X != null) {
            smoothFontRenderer.d(this._o, this.G$src$D$1b2f02a() + 8.0 + (double)f + 5.0, (int)d2, color2);
            int n = (int)(ImageRenderer.getImageWidth(this._X) / (double)3.1f);
            int n2 = (int)(ImageRenderer.getImageHeight(this._X) / (double)3.1f);
            int n3 = (int)(d2 + smoothFontRenderer.d(this._o) / 2.0 - (double)((float)n2 / 2.0f));
            ImageRenderer.drawImage(color2, (int)this.G$src$D$1b2f02a() + 8, n3 += this._T, this._X, n, n2, false);
        } else {
            smoothFontRenderer.d(this._o, this.G$src$D$1b2f02a() + 8.0, d2, color2);
        }
    }

    public FrameNavigationButtonComponent Q(int n) {
        this._T = n;
        return this;
    }

    public void D$src$V$16hz67e() {
        if (this.var_gg_vape_ui_click_frame_Frame_v != null) {
            if (this.var_gg_vape_ui_click_frame_Frame_v.V$src$Z$1xhop3l()) {
                this.var_gg_vape_ui_click_animation_DoubleAnimation_Q.C();
                this.var_boolean_K = true;
            } else if (this.var_gg_vape_ui_click_animation_DoubleAnimation_Q.I$src$Z$c48gtw()) {
                this.var_gg_vape_ui_click_animation_DoubleAnimation_Q.O();
                this.var_boolean_K = false;
            }
        } else {
            this.var_gg_vape_ui_click_frame_Frame_v = ClientSettings.findFrameByName(this._o);
        }
    }
}

