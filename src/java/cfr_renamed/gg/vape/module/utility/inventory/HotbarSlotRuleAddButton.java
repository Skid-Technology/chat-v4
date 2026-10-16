/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.module.utility.inventory;

import gg.vape.ui.click.animation.ColorAnimation;
import gg.vape.ui.click.component.gui.InteractiveComponent;
import gg.vape.utils.render.GuiRenderPrimitives;

public class HotbarSlotRuleAddButton
extends InteractiveComponent {
    private ColorAnimation colorAnimation;
    private final static String var_java_lang_String_I = "newadd";

    @Override
    public void onEnable() {
        this.colorAnimation.J();
        super.onEnable();
    }

    @Override
    public void F() {
        if (!this.w$src$Z$e457mb()) {
            this.colorAnimation.J();
        }
        super.F();
    }

    @Override
    public void I() {
    }

    @Override
    public void H() {
        GuiRenderPrimitives.d(this.G$src$D$1b2f02a() + 5.0, this.n() + 2.5, this.A() - 10.0, this.L() - 5.0, this.colorAnimation.getInterpolatedColor());
        GuiRenderPrimitives.d(this.G$src$D$1b2f02a() + 5.0 + 0.5, this.n() + 2.5 + 0.5, this.A() - 10.0 - 1.0, this.L() - 1.0 - 5.0, HotbarSlotRuleAddButton.J.var_java_awt_Color_i);
        GuiRenderPrimitives.F(var_java_lang_String_I, this.G$src$D$1b2f02a() + this.A() / 2.0, this.n() + this.L() / 2.0, 6.0, 6.0, HotbarSlotRuleAddButton.J.var_java_awt_Color_B);
    }

    @Override
    public double C() {
        return 20.0;
    }

    @Override
    public double x() {
        return 110.0;
    }

    public HotbarSlotRuleAddButton() {
        this.colorAnimation = new ColorAnimation(0.15, HotbarSlotRuleAddButton.J.var_java_awt_Color_l, HotbarSlotRuleAddButton.J.var_java_awt_Color_h);
    }
}

