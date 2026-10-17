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
    private final BindableInputComponent B;
    private final ModeToggleButton e;
    private final ModeValue v;

    private void lambda$new$0() {
        this.v.cycle();
    }

    public ModeToggleComponent(ModeValue vb_22) {
        this.v = vb_22;
        this.e = new ModeToggleButton();
        this.B = new BindableInputComponent(vb_22.getBind());
        this.bindValue(vb_22);
        this.e.addClickListener(this::lambda$new$0);
        this.addChildren(this.e, this.B);
    }

    @Override
    public void c() {
        super.c();
        SmoothFontRenderer o62 = this.getAlternateFontRenderer(0.9);
        String string = this.v.getName();
        double d = o62.d(string);
        double d3 = this.G$src$D$1b2f02a();
        this.getClass();
        o62.d(string, d3 + 5.0, this.n() + this.L() / 2.0 - d / 2.0, ModeToggleComponent.J.Z);
        this.e.setLabelText(((ModeSelection)this.v.getValue()).toString());
        this.e.Y(10.0);
        this.e.o(Math.max(38.0, this.e.getTextWidth() + 10.0));
        this.B.getBindLabel().setMaxWidth(this.A() - 12.5);
        this.B.S(this.n() + 5.0);
        this.e.S(this.n() + 5.0);
        double d5 = this.G$src$D$1b2f02a() + this.A();
        this.getClass();
        this.e.K(d5 - 5.0 - this.e.A());
        this.B.K(this.e.G$src$D$1b2f02a() - this.B.A() - 4.0);
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
            this.setTextColor(ModeToggleButton.J.Z);
        }

        @Override
        public void H() {
            GuiRenderPrimitives.e(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), this.w$src$Z$e457mb() ? ModeToggleButton.J.S.brighter() : ModeToggleButton.J.S, false, 2.0f, 1.0f);
            super.H();
        }

        @Override
        public double C() {
            return 10.0;
        }
    }
}

