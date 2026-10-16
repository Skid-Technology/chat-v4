/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.component.value;

import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.gui.TextLabel;
import gg.vape.ui.click.component.input.BindableInputComponent;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.unmap.ModeSelection;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.value.ModeValue;

public class ModeToggleComponent
extends GuiComponent {
    private final BindableInputComponent var_gg_vape_ui_click_component_input_BindableInputComponent_B;
    private final ModeToggleButton var_gg_vape_ui_click_component_value_ModeToggleComponent$ModeToggleButton_e;
    private final ModeValue var_gg_vape_value_ModeValue_v;

    private void lambda$new$0() {
        this.var_gg_vape_value_ModeValue_v.cycle();
    }

    public ModeToggleComponent(ModeValue vb_22) {
        this.var_gg_vape_value_ModeValue_v = vb_22;
        this.var_gg_vape_ui_click_component_value_ModeToggleComponent$ModeToggleButton_e = new ModeToggleButton();
        this.var_gg_vape_ui_click_component_input_BindableInputComponent_B = new BindableInputComponent(vb_22.getBind());
        this.bindValue(vb_22);
        this.var_gg_vape_ui_click_component_value_ModeToggleComponent$ModeToggleButton_e.addClickListener(this::lambda$new$0);
        this.addChildren(this.var_gg_vape_ui_click_component_value_ModeToggleComponent$ModeToggleButton_e, this.var_gg_vape_ui_click_component_input_BindableInputComponent_B);
    }

    @Override
    public void c() {
        super.c();
        SmoothFontRenderer o62 = this.getAlternateFontRenderer(0.9);
        String string = this.var_gg_vape_value_ModeValue_v.getName();
        double d = o62.d(string);
        double d3 = this.G$src$D$1b2f02a();
        this.getClass();
        o62.d(string, d3 + 5.0, this.n() + this.L() / 2.0 - d / 2.0, ModeToggleComponent.J.var_java_awt_Color_Z);
        this.var_gg_vape_ui_click_component_value_ModeToggleComponent$ModeToggleButton_e.setLabelText(((ModeSelection)this.var_gg_vape_value_ModeValue_v.getValue()).toString());
        this.var_gg_vape_ui_click_component_value_ModeToggleComponent$ModeToggleButton_e.Y(10.0);
        this.var_gg_vape_ui_click_component_value_ModeToggleComponent$ModeToggleButton_e.o(Math.max(38.0, this.var_gg_vape_ui_click_component_value_ModeToggleComponent$ModeToggleButton_e.getTextWidth() + 10.0));
        this.var_gg_vape_ui_click_component_input_BindableInputComponent_B.getBindLabel().setMaxWidth(this.A() - 12.5);
        this.var_gg_vape_ui_click_component_input_BindableInputComponent_B.S(this.n() + 5.0);
        this.var_gg_vape_ui_click_component_value_ModeToggleComponent$ModeToggleButton_e.S(this.n() + 5.0);
        double d5 = this.G$src$D$1b2f02a() + this.A();
        this.getClass();
        this.var_gg_vape_ui_click_component_value_ModeToggleComponent$ModeToggleButton_e.K(d5 - 5.0 - this.var_gg_vape_ui_click_component_value_ModeToggleComponent$ModeToggleButton_e.A());
        this.var_gg_vape_ui_click_component_input_BindableInputComponent_B.K(this.var_gg_vape_ui_click_component_value_ModeToggleComponent$ModeToggleButton_e.G$src$D$1b2f02a() - this.var_gg_vape_ui_click_component_input_BindableInputComponent_B.A() - 4.0);
    }

    @Override
    public double x() {
        return 110.0;
    }

    @Override
    public void u() {
    }

    @Override
    public void I() {
    }

    @Override
    public double C() {
        return 20.0;
    }

    private static class ModeToggleButton
    extends TextLabel {
        public ModeToggleButton() {
            super("", 0.8, false);
            this.setCentered(true);
            this.setTextColor(ModeToggleButton.J.var_java_awt_Color_Z);
        }

        @Override
        public void H() {
            GuiRenderPrimitives.e(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), this.w$src$Z$e457mb() ? ModeToggleButton.J.var_java_awt_Color_S.brighter() : ModeToggleButton.J.var_java_awt_Color_S, false, 2.0f, 1.0f);
            super.H();
        }

        @Override
        public double C() {
            return 10.0;
        }
    }
}

