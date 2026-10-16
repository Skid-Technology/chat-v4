/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame.impl;

import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GuiClickListener;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.gui.AnimatedCenteredTextLabelComponent;

public class ClientSettingsActionButtonRowComponent
extends GuiComponent {
    private AnimatedCenteredTextLabelComponent var_gg_vape_ui_click_component_gui_AnimatedCenteredTextLabelComponent_b;

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    @Override
    public double C() {
        return 15.0;
    }

    @Override
    public void I() {
    }

    @Override
    public void u() {
    }

    @Override
    public double x() {
        return 110.0;
    }

    @Override
    public void F() {
    }

    public ClientSettingsActionButtonRowComponent(String string, GuiClickListener guiClickListener) {
        this.var_gg_vape_ui_click_component_gui_AnimatedCenteredTextLabelComponent_b = new AnimatedCenteredTextLabelComponent(string, ClientSettingsActionButtonRowComponent.J.var_java_awt_Color_l);
        this.var_gg_vape_ui_click_component_gui_AnimatedCenteredTextLabelComponent_b.addClickListener(guiClickListener);
        this.addChildren(this.var_gg_vape_ui_click_component_gui_AnimatedCenteredTextLabelComponent_b);
    }

    @Override
    public void H() {
        this.var_gg_vape_ui_click_component_gui_AnimatedCenteredTextLabelComponent_b.K(this.G$src$D$1b2f02a() + 5.0);
        this.var_gg_vape_ui_click_component_gui_AnimatedCenteredTextLabelComponent_b.S(this.n() + 1.0);
        this.var_gg_vape_ui_click_component_gui_AnimatedCenteredTextLabelComponent_b.o(this.A() - 10.0);
        this.var_gg_vape_ui_click_component_gui_AnimatedCenteredTextLabelComponent_b.Y(this.L() - 2.0);
    }
}

