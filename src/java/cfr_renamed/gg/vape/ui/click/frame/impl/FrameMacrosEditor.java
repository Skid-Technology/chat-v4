/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame.impl;

import gg.vape.Vape;
import gg.vape.module.Macro;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.AnimatedIconButtonComponent;
import gg.vape.ui.click.component.FadingTruncatedTextComponent;
import gg.vape.ui.click.component.IconButtonComponent;
import gg.vape.ui.click.component.gui.InteractiveComponent;
import gg.vape.ui.click.component.gui.TextButton;
import gg.vape.ui.click.component.input.BindableInputComponent;
import gg.vape.ui.click.component.value.BooleanToggleComponent;
import gg.vape.ui.click.component.value.RandomRangeSliderComponent;
import gg.vape.ui.click.frame.impl.FrameMacros;
import gg.vape.ui.click.frame.impl.FrameMacrosEditorRefreshFrameMouseListener;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;

public class FrameMacrosEditor
extends InteractiveComponent {
    private RandomRangeSliderComponent var_gg_vape_ui_click_component_value_RandomRangeSliderComponent_Q;
    private Macro q1;
    private final TextButton q5;
    private BooleanToggleComponent qz;
    private final AnimatedIconButtonComponent var_gg_vape_ui_click_component_AnimatedIconButtonComponent_K;
    private RandomRangeSliderComponent qR;
    private Macro q3;
    private BindableInputComponent qr;
    private final IconButtonComponent var_gg_vape_ui_click_component_IconButtonComponent_v;
    private FrameMacros qQ;
    private FadingTruncatedTextComponent var_gg_vape_ui_click_component_FadingTruncatedTextComponent_I;
    private final TextButton var_gg_vape_ui_click_component_gui_TextButton_b;
    private boolean qn;

    @Override
    public void H() {
        double d;
        SmoothFontRenderer smoothFontRenderer = this.getFontRenderer(0.9);
        double d2 = smoothFontRenderer.d(this.q1.getName());
        double d3 = this.n() + 9.0 - d2 / 2.0;
        double d4 = this.n() + 9.0;
        double d5 = this.G$src$D$1b2f02a() + 10.0;
        this.var_gg_vape_ui_click_component_FadingTruncatedTextComponent_I.K(d5);
        this.var_gg_vape_ui_click_component_FadingTruncatedTextComponent_I.S(d3);
        this.var_gg_vape_ui_click_component_FadingTruncatedTextComponent_I.setMaxWidth(this.qn ? this.A() - 20.0 - 8.0 : 65.0 - this.qr.A());
        if (this.qn) {
            GuiRenderPrimitives.d(this.G$src$D$1b2f02a() + 5.0 - 0.5, this.n() + 0.5, this.A() - 10.0 + 1.0, this.L() - 1.0, FrameMacrosEditor.J.var_java_awt_Color_l);
        }
        GuiRenderPrimitives.d(this.G$src$D$1b2f02a() + 5.0, this.n() + 1.0, this.A() - 10.0, this.L() - 2.0, FrameMacrosEditor.J.var_java_awt_Color_m);
        if (this.qn) {
            d = this.G$src$D$1b2f02a() + 5.0;
            double d6 = this.n() + 20.0;
            double d7 = this.A() - 10.0;
            this.qR.K(d);
            this.qR.S(d6);
            this.qR.o(d7);
            this.qz.K(d);
            this.qz.S(d6 += this.qR.L());
            this.qz.o(d7 - 6.0);
            d6 += this.qz.L();
            if (this.qz.getBooleanValue().getEffectiveValue().booleanValue()) {
                this.var_gg_vape_ui_click_component_value_RandomRangeSliderComponent_Q.setVisible(true);
                this.var_gg_vape_ui_click_component_value_RandomRangeSliderComponent_Q.K(d);
                this.var_gg_vape_ui_click_component_value_RandomRangeSliderComponent_Q.S(d6);
                this.var_gg_vape_ui_click_component_value_RandomRangeSliderComponent_Q.o(d7);
                d6 += this.var_gg_vape_ui_click_component_value_RandomRangeSliderComponent_Q.L();
            } else {
                this.var_gg_vape_ui_click_component_value_RandomRangeSliderComponent_Q.setVisible(false);
                d6 += 5.0;
            }
            double d8 = (this.A() - 25.0) / 5.0;
            double d9 = this.G$src$D$1b2f02a() + 10.0;
            if (this.var_gg_vape_ui_click_component_AnimatedIconButtonComponent_K != null) {
                this.var_gg_vape_ui_click_component_AnimatedIconButtonComponent_K.K(d9);
                this.var_gg_vape_ui_click_component_AnimatedIconButtonComponent_K.S(d6);
                this.var_gg_vape_ui_click_component_AnimatedIconButtonComponent_K.Y(18.0);
                this.var_gg_vape_ui_click_component_AnimatedIconButtonComponent_K.o(d8);
            }
            this.q5.K(d9 += d8 + 2.5);
            this.q5.S(d6);
            this.q5.Y(18.0);
            this.q5.o(d8 * 2.0);
            this.var_gg_vape_ui_click_component_gui_TextButton_b.K(d9 += d8 * 2.0 + 2.5);
            this.var_gg_vape_ui_click_component_gui_TextButton_b.S(d6);
            this.var_gg_vape_ui_click_component_gui_TextButton_b.Y(18.0);
            this.var_gg_vape_ui_click_component_gui_TextButton_b.o(d8 * 2.0);
        }
        d = this.G$src$D$1b2f02a() + this.A() - 15.0;
        if (this.var_gg_vape_ui_click_component_IconButtonComponent_v.V$src$Z$1xhop3l()) {
            this.var_gg_vape_ui_click_component_IconButtonComponent_v.setOverrideColor(this.qn ? FrameMacrosEditor.J.var_java_awt_Color_f : null);
            this.var_gg_vape_ui_click_component_IconButtonComponent_v.K(d);
            this.var_gg_vape_ui_click_component_IconButtonComponent_v.S(this.n());
            this.var_gg_vape_ui_click_component_IconButtonComponent_v.Y(18.0);
            d -= 5.0;
        }
        if (!this.qn) {
            this.qr.getBindLabel().setMaxWidth(20.0);
            this.qr.K(d -= this.qr.A());
            this.qr.S(this.n() + 4.0);
            GuiRenderPrimitives.F("newclock", d -= 9.0, d4, 8.0, 8.0, FrameMacrosEditor.J.var_java_awt_Color_K);
        }
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
        if (this.qQ.l$src$Lgg_vape_ui_click_frame_impl_FrameMacrosEditor_$1712c36() != null && this.qQ.l$src$Lgg_vape_ui_click_frame_impl_FrameMacrosEditor_$1712c36().equals(this) && !this.getBounds().J(guiMouseEvent.getX(), guiMouseEvent.getY())) {
            if (!this.var_gg_vape_ui_click_component_IconButtonComponent_v.V$src$Z$1xhop3l()) {
                this.qQ.X(null);
                return;
            }
            this.var_gg_vape_ui_click_component_IconButtonComponent_v.dispatchPrimaryClick();
            return;
        }
        super.g(guiMouseEvent);
    }

    @Override
    public void I() {
    }

    public Macro z() {
        return this.q1;
    }

    public FrameMacrosEditor(FrameMacros frameMacros, Macro macro) {
        this.var_gg_vape_ui_click_component_gui_TextButton_b = new TextButton("Update", 0.8, FrameMacrosEditor.J.var_java_awt_Color_B).setTransparentBackgroundBorder(FrameMacrosEditor.J.var_java_awt_Color_l);
        this.q5 = new TextButton("Cancel", 0.8, FrameMacrosEditor.J.var_java_awt_Color_B).setTransparentBackgroundBorder(FrameMacrosEditor.J.var_java_awt_Color_l);
        this.var_gg_vape_ui_click_component_AnimatedIconButtonComponent_K = new AnimatedIconButtonComponent("newtrash", 0.8, FrameMacrosEditor.J.var_java_awt_Color_d);
        this.var_gg_vape_ui_click_component_IconButtonComponent_v = new IconButtonComponent("settingdots");
        this.var_gg_vape_ui_click_component_gui_TextButton_b.setDeriveTextColorFromBackground(false);
        this.q5.setDeriveTextColorFromBackground(false);
        this.qQ = frameMacros;
        this.q1 = macro;
        this.qr = new BindableInputComponent(macro);
        FrameMacrosEditor frameMacrosEditor = this;
        this.var_gg_vape_ui_click_component_IconButtonComponent_v.addClickListener(() -> {
            boolean bl = this.qn = !this.qn;
            if (this.qn) {
                frameMacros.X(frameMacrosEditor);
                this.q3 = Macro.create(macro.getName()).loadJson(macro.toJson());
                this.qz.setVisible(true);
                this.qR.setVisible(true);
                this.var_gg_vape_ui_click_component_value_RandomRangeSliderComponent_Q.setVisible(true);
                this.q5.setVisible(true);
                this.var_gg_vape_ui_click_component_gui_TextButton_b.setVisible(true);
                this.var_gg_vape_ui_click_component_AnimatedIconButtonComponent_K.setVisible(true);
                this.qr.setVisible(false);
            } else {
                frameMacros.X(null);
                this.qz.setVisible(false);
                this.qR.setVisible(false);
                this.var_gg_vape_ui_click_component_value_RandomRangeSliderComponent_Q.setVisible(false);
                this.q5.setVisible(false);
                this.var_gg_vape_ui_click_component_gui_TextButton_b.setVisible(false);
                this.var_gg_vape_ui_click_component_AnimatedIconButtonComponent_K.setVisible(false);
                this.qr.setVisible(true);
            }
            frameMacros.l$src$V$1mibm4x();
        });
        this.qR = new RandomRangeSliderComponent(macro.getDelay());
        this.var_gg_vape_ui_click_component_value_RandomRangeSliderComponent_Q = new RandomRangeSliderComponent(macro.getDoubleClickDelay());
        this.qz = new BooleanToggleComponent(macro.getDoubleClick());
        this.qz.addMouseListener(new FrameMacrosEditorRefreshFrameMouseListener(this, frameMacros));
        this.var_gg_vape_ui_click_component_AnimatedIconButtonComponent_K.addClickListener(() -> {
            Vape.INSTANCE.getMacrosManager().removeMacro(macro);
            frameMacros.X(null);
        });
        this.q5.addClickListener(() -> {
            if (this.q3 != null) {
                macro.loadJson(this.q3.toJson());
            }
            this.var_gg_vape_ui_click_component_IconButtonComponent_v.dispatchPrimaryClick();
        });
        this.var_gg_vape_ui_click_component_gui_TextButton_b.addClickListener(() -> {
            if (!this.var_gg_vape_ui_click_component_IconButtonComponent_v.V$src$Z$1xhop3l()) {
                frameMacros.X(null);
            } else {
                this.var_gg_vape_ui_click_component_IconButtonComponent_v.dispatchPrimaryClick();
            }
        });
        this.qz.setVisible(false);
        this.qz.setUseExplicitWidth(true);
        this.qz.setDisabledOverlayColor(FrameMacrosEditor.J.var_java_awt_Color_m);
        this.qR.setVisible(false);
        this.qR.setUseExplicitWidth(true);
        this.qR.setDisabledOverlayColor(FrameMacrosEditor.J.var_java_awt_Color_m);
        this.var_gg_vape_ui_click_component_value_RandomRangeSliderComponent_Q.setVisible(false);
        this.var_gg_vape_ui_click_component_value_RandomRangeSliderComponent_Q.setUseExplicitWidth(true);
        this.var_gg_vape_ui_click_component_value_RandomRangeSliderComponent_Q.setDisabledOverlayColor(FrameMacrosEditor.J.var_java_awt_Color_m);
        this.q5.setVisible(false);
        this.var_gg_vape_ui_click_component_gui_TextButton_b.setVisible(false);
        this.var_gg_vape_ui_click_component_AnimatedIconButtonComponent_K.setVisible(false);
        this.var_gg_vape_ui_click_component_AnimatedIconButtonComponent_K.setDisabledOverlayColor(FrameMacrosEditor.J.var_java_awt_Color_m);
        this.var_gg_vape_ui_click_component_FadingTruncatedTextComponent_I = new FadingTruncatedTextComponent(macro.getName(), 40.0, 0.9, FrameMacrosEditor.J.var_java_awt_Color_Z, FrameMacrosEditor.J.var_java_awt_Color_m, false, false);
        this.addChildren(this.var_gg_vape_ui_click_component_FadingTruncatedTextComponent_I, this.var_gg_vape_ui_click_component_IconButtonComponent_v, this.qr, this.qz, this.qR, this.var_gg_vape_ui_click_component_value_RandomRangeSliderComponent_Q, this.q5, this.var_gg_vape_ui_click_component_gui_TextButton_b, this.var_gg_vape_ui_click_component_AnimatedIconButtonComponent_K);
    }

    @Override
    public double C() {
        return this.qn ? (this.qz.getBooleanValue().getEffectiveValue().booleanValue() ? 120.0 : 90.0) : 18.0;
    }

    private void lambda$setInitialMode$4() {
        this.setVisible(false);
        this.qQ.X(null);
    }

    private void lambda$setInitialMode$5() {
        this.q1 = Macro.create(this.q1.getName()).loadJson(this.q1.toJson());
        Vape.INSTANCE.getMacrosManager().addMacro(this.q1);
        this.setVisible(false);
        this.qQ.X(null);
    }

    @Override
    public double x() {
        return 110.0;
    }

    @Override
    public void F() {
    }

    @Override
    public void u() {
    }

    public void N$src$V$13y6z98() {
        this.var_gg_vape_ui_click_component_IconButtonComponent_v.dispatchPrimaryClick();
        this.var_gg_vape_ui_click_component_IconButtonComponent_v.setVisible(false);
        this.qr.setVisible(false);
        this.var_gg_vape_ui_click_component_AnimatedIconButtonComponent_K.setVisible(false);
        this.q5.setLabelText("Cancel");
        this.q5.setBackgroundAnimationColors(new Color(0, 0, 0, 0), FrameMacrosEditor.J.var_java_awt_Color_d);
        this.var_gg_vape_ui_click_component_gui_TextButton_b.setLabelText("Add");
        this.var_gg_vape_ui_click_component_gui_TextButton_b.setBackgroundAnimationColors(new Color(0, 0, 0, 0), FrameMacrosEditor.J.var_java_awt_Color_B);
        this.q5.setClickListener(this::lambda$setInitialMode$4);
        this.var_gg_vape_ui_click_component_gui_TextButton_b.setClickListener(this::lambda$setInitialMode$5);
    }
}

