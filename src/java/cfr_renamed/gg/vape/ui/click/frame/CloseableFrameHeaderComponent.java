/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame;

import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.IconButtonComponent;
import gg.vape.ui.click.frame.CollapsibleFrame;
import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.click.frame.FrameHeaderCloseToggleClickHandler;
import gg.vape.ui.click.frame.FrameHeaderComponent;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.ImageRenderer;
import java.awt.Color;

public class CloseableFrameHeaderComponent
extends FrameHeaderComponent {
    private String var_java_lang_String_O;
    private static int[] var_int_arr_Q;
    private double var_double_v;
    private String var_java_lang_String_I;
    private boolean var_boolean_G = true;
    private boolean var_boolean_K;
    private IconButtonComponent var_gg_vape_ui_click_component_IconButtonComponent_R;

    public CloseableFrameHeaderComponent(Frame frame, String string, String string2) {
        this(frame, string, string2, 1.0);
    }

    @Override
    public void F() {
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    static boolean c(CloseableFrameHeaderComponent closeableFrameHeaderComponent, boolean bl) {
        closeableFrameHeaderComponent.var_boolean_K = bl;
        return closeableFrameHeaderComponent.var_boolean_K;
    }

    public void Y(boolean bl) {
        this.var_boolean_G = bl;
    }

    public static void o(int[] nArray) {
        var_int_arr_Q = nArray;
    }

    @Override
    public void H() {
        SmoothFontRenderer smoothFontRenderer = this.getFontRenderer(0.9);
        this.getClass();
        float f = 8.0f * (float)this.var_double_v * 0.9f;
        Color color = CloseableFrameHeaderComponent.J.var_java_awt_Color_A;
        double d = smoothFontRenderer.d(this.var_java_lang_String_O);
        double d2 = this.n() + this.L() / 2.0 - d / 2.0;
        double d3 = this.n() + this.L() / 2.0 - (double)(f / 2.0f);
        double d4 = this.G$src$D$1b2f02a() + 6.0;
        double d5 = 32.0 * ((double)f / (ImageRenderer.getImageWidth(this.var_java_lang_String_I) * this.var_double_v));
        smoothFontRenderer.d(this.var_java_lang_String_O, d4 + d5 + 2.0, d2, color);
        if (this.var_boolean_G) {
            ImageRenderer.drawImage(color, (float)d4, (float)d3, this.var_java_lang_String_I, f, f, false);
        }
        this.var_gg_vape_ui_click_component_IconButtonComponent_R.setOverrideColor(this.w$src$Lgg_vape_ui_click_frame_Frame_$y4htd0().y$src$Z$1f55jvh() ? CloseableFrameHeaderComponent.J.var_java_awt_Color_f : null);
        double d6 = this.G$src$D$1b2f02a() + this.A() - 7.5;
        this.getClass();
        this.var_gg_vape_ui_click_component_IconButtonComponent_R.K(d6 - 8.0);
        this.var_gg_vape_ui_click_component_IconButtonComponent_R.S(this.n());
        this.var_gg_vape_ui_click_component_IconButtonComponent_R.Y(this.L());
        this.var_gg_vape_ui_click_component_IconButtonComponent_R.setIconResource(this.var_boolean_K ? "downexpand" : "upcollapse");
    }

    public static int[] A$src$AI$cq4488() {
        return var_int_arr_Q;
    }

    @Override
    public void u() {
    }

    @Override
    public void I() {
    }

    public CloseableFrameHeaderComponent(Frame frame, String string, String string2, double d) {
        super(frame);
        this.var_java_lang_String_I = string;
        this.var_java_lang_String_O = string2;
        this.var_double_v = d;
        this.var_boolean_K = ((CollapsibleFrame)((Object)frame)).q();
        this.var_gg_vape_ui_click_component_IconButtonComponent_R = new IconButtonComponent(this.var_boolean_K ? "downexpand" : "upcollapse", 0.3);
        this.var_gg_vape_ui_click_component_IconButtonComponent_R.addClickListener(new FrameHeaderCloseToggleClickHandler(this, frame));
        this.addChildren(this.var_gg_vape_ui_click_component_IconButtonComponent_R);
    }

    static {
        CloseableFrameHeaderComponent.o(null);
    }
}

