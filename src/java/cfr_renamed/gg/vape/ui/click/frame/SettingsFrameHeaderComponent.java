/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame;

import gg.vape.friend.ui.OnlineCombatStatsSettingsFrame;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.IconButtonComponent;
import gg.vape.ui.click.frame.CollapsibleFrame;
import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.click.frame.FrameHeaderComponent;
import gg.vape.ui.click.frame.SettingsFrameHeaderCollapseClickListener;
import gg.vape.ui.click.frame.SettingsFrameHeaderToggleClickListener;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.ImageRenderer;
import java.awt.Color;

public class SettingsFrameHeaderComponent
extends FrameHeaderComponent {
    private String var_java_lang_String_i;
    private IconButtonComponent var_gg_vape_ui_click_component_IconButtonComponent_I;
    private String var_java_lang_String_R;
    private IconButtonComponent var_gg_vape_ui_click_component_IconButtonComponent_Q = new IconButtonComponent("settingdots");

    @Override
    public void u() {
    }

    @Override
    public void F() {
    }

    public IconButtonComponent x$src$Lgg_vape_ui_click_component_IconButtonComponent_$x1h5th() {
        return this.var_gg_vape_ui_click_component_IconButtonComponent_Q;
    }

    private void A$src$V$18zygv7() {
        SmoothFontRenderer smoothFontRenderer = this.getFontRenderer(0.9);
        Color color = SettingsFrameHeaderComponent.J.var_java_awt_Color_A;
        double d = smoothFontRenderer.d(this.var_java_lang_String_i);
        float f = (float)ImageRenderer.getImageWidth(this.var_java_lang_String_R) / 3.5f;
        float f2 = (float)ImageRenderer.getImageHeight(this.var_java_lang_String_R) / 3.5f;
        double d2 = this.n() + this.L() / 2.0 - d / 2.0 + 1.0;
        double d3 = this.n() + this.L() / 2.0 - (double)(f2 / 2.0f) + 1.0;
        double d4 = this.G$src$D$1b2f02a() + 6.0;
        this.var_gg_vape_ui_click_component_IconButtonComponent_I.setImageDivisor(3.5);
        this.var_gg_vape_ui_click_component_IconButtonComponent_Q.setImageDivisor(3.2);
        smoothFontRenderer.d(this.var_java_lang_String_i, d4 + (double)f + 4.0, d2, color);
        ImageRenderer.drawImage(color, (float)d4, (float)d3, this.var_java_lang_String_R, f, f2, false);
        this.var_gg_vape_ui_click_component_IconButtonComponent_I.setOverrideColor(this.w$src$Lgg_vape_ui_click_frame_Frame_$y4htd0().y$src$Z$1f55jvh() ? SettingsFrameHeaderComponent.J.var_java_awt_Color_f : SettingsFrameHeaderComponent.J.var_java_awt_Color_W.darker());
        this.var_gg_vape_ui_click_component_IconButtonComponent_I.K(this.G$src$D$1b2f02a() + this.A() - 20.0);
        this.var_gg_vape_ui_click_component_IconButtonComponent_I.S(this.n() + 1.0);
        this.var_gg_vape_ui_click_component_IconButtonComponent_I.Y(this.L());
        this.var_gg_vape_ui_click_component_IconButtonComponent_I.o(10.0);
        this.var_gg_vape_ui_click_component_IconButtonComponent_Q.setOverrideColor(this.w$src$Lgg_vape_ui_click_frame_Frame_$y4htd0() instanceof CollapsibleFrame && !((CollapsibleFrame)((Object)this.w$src$Lgg_vape_ui_click_frame_Frame_$y4htd0())).q() ? SettingsFrameHeaderComponent.J.var_java_awt_Color_f : null);
        this.var_gg_vape_ui_click_component_IconButtonComponent_Q.K(this.G$src$D$1b2f02a() + this.A() - 12.0);
        this.var_gg_vape_ui_click_component_IconButtonComponent_Q.S(this.n() + 1.0);
        this.var_gg_vape_ui_click_component_IconButtonComponent_Q.Y(this.L());
    }

    @Override
    public void H() {
        this.A$src$V$18zygv7();
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    public SettingsFrameHeaderComponent(Frame frame, String string, String string2) {
        super(frame);
        this.var_gg_vape_ui_click_component_IconButtonComponent_I = new IconButtonComponent("newpin");
        this.var_java_lang_String_R = string;
        this.var_java_lang_String_i = string2;
        this.var_gg_vape_ui_click_component_IconButtonComponent_I.addClickListener(new SettingsFrameHeaderToggleClickListener(this));
        this.var_gg_vape_ui_click_component_IconButtonComponent_Q.addClickListener(new SettingsFrameHeaderCollapseClickListener(this, frame));
        this.addChildren(this.var_gg_vape_ui_click_component_IconButtonComponent_Q, this.var_gg_vape_ui_click_component_IconButtonComponent_I);
        this.var_gg_vape_ui_click_component_IconButtonComponent_I.setImageDivisor(3.5);
    }

    @Override
    public void I() {
        if (this.w$src$Lgg_vape_ui_click_frame_Frame_$y4htd0() instanceof CollapsibleFrame && !((CollapsibleFrame)((Object)this.w$src$Lgg_vape_ui_click_frame_Frame_$y4htd0())).q() && !(this.w$src$Lgg_vape_ui_click_frame_Frame_$y4htd0() instanceof OnlineCombatStatsSettingsFrame)) {
            ((CollapsibleFrame)((Object)this.w$src$Lgg_vape_ui_click_frame_Frame_$y4htd0())).w();
        }
    }
}

