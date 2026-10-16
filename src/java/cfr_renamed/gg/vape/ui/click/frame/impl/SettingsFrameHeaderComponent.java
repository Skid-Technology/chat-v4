/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame.impl;

import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.component.IconButtonComponent;
import gg.vape.ui.click.component.SquareIconButtonComponent;
import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.click.frame.FrameHeaderComponent;
import gg.vape.ui.click.frame.impl.SettingsFrameHeaderPrimaryButtonClickHandler;
import gg.vape.ui.click.frame.impl.SettingsFrameHeaderSecondaryButtonClickHandler;
import gg.vape.ui.font.SmoothFontRenderer;
import java.awt.Color;

public class SettingsFrameHeaderComponent
extends FrameHeaderComponent {
    private String var_java_lang_String_i;
    private GuiClickListener var_gg_vape_ui_click_component_GuiClickListener_G;
    private GuiClickListener var_gg_vape_ui_click_component_GuiClickListener_I;
    private IconButtonComponent var_gg_vape_ui_click_component_IconButtonComponent_K = new IconButtonComponent("moduleback");
    private SquareIconButtonComponent var_gg_vape_ui_click_component_SquareIconButtonComponent_R = new SquareIconButtonComponent("newclose", 1.5);
    private float var_float_o;

    static GuiClickListener D(SettingsFrameHeaderComponent settingsFrameHeaderComponent) {
        return settingsFrameHeaderComponent.var_gg_vape_ui_click_component_GuiClickListener_I;
    }

    @Override
    public void u() {
    }

    public SettingsFrameHeaderComponent(Frame frame, String string) {
        this(frame, string, 1.0f);
    }

    static GuiClickListener O(SettingsFrameHeaderComponent settingsFrameHeaderComponent) {
        return settingsFrameHeaderComponent.var_gg_vape_ui_click_component_GuiClickListener_G;
    }

    public SettingsFrameHeaderComponent(Frame frame, String string, float f) {
        super(frame);
        this.var_java_lang_String_i = string;
        this.var_float_o = f;
        this.var_gg_vape_ui_click_component_IconButtonComponent_K.addClickListener(new SettingsFrameHeaderSecondaryButtonClickHandler(this));
        this.addChildren(this.var_gg_vape_ui_click_component_IconButtonComponent_K);
        this.var_gg_vape_ui_click_component_SquareIconButtonComponent_R.addClickListener(new SettingsFrameHeaderPrimaryButtonClickHandler(this, frame));
        this.addChildren(this.var_gg_vape_ui_click_component_SquareIconButtonComponent_R);
    }

    public SettingsFrameHeaderComponent n(GuiClickListener guiClickListener) {
        this.var_gg_vape_ui_click_component_GuiClickListener_G = guiClickListener;
        return this;
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    @Override
    public void I() {
    }

    @Override
    public void H() {
        SmoothFontRenderer smoothFontRenderer = this.getFontRenderer(0.9);
        Color color = SettingsFrameHeaderComponent.J.var_java_awt_Color_A;
        double d = smoothFontRenderer.d(this.var_java_lang_String_i);
        double d2 = this.n() + this.L() / 2.0 - d / 2.0 + 1.0;
        smoothFontRenderer.d(this.var_java_lang_String_i, this.G$src$D$1b2f02a() + 10.0 + 8.0, d2, color);
        this.var_gg_vape_ui_click_component_IconButtonComponent_K.K(this.G$src$D$1b2f02a() + 5.0 - 2.0);
        this.var_gg_vape_ui_click_component_IconButtonComponent_K.S(this.n() + 1.0);
        this.var_gg_vape_ui_click_component_IconButtonComponent_K.Y(this.L());
        this.var_gg_vape_ui_click_component_IconButtonComponent_K.setImageDivisor(3.5);
        this.var_gg_vape_ui_click_component_SquareIconButtonComponent_R.K(this.G$src$D$1b2f02a() + this.A() - 13.0);
        this.var_gg_vape_ui_click_component_SquareIconButtonComponent_R.S(this.n() + 1.0);
        this.var_gg_vape_ui_click_component_SquareIconButtonComponent_R.Y(this.L());
        this.var_gg_vape_ui_click_component_SquareIconButtonComponent_R.setIconScale(1.2f);
        this.var_gg_vape_ui_click_component_SquareIconButtonComponent_R.setImageDivisor(3.5);
    }

    public SettingsFrameHeaderComponent f(GuiClickListener guiClickListener) {
        this.var_gg_vape_ui_click_component_GuiClickListener_I = guiClickListener;
        return this;
    }

    @Override
    public void F() {
    }
}

