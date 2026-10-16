/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame.impl.profile;

import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.utils.render.GuiRenderPrimitives;

public class ProfileCreateDividerComponent
extends GuiComponent {
    private final static String var_java_lang_String_b = "9+";

    @Override
    public void H() {
        int n = 6;
        String string = n <= 9 ? Integer.toString(n) : var_java_lang_String_b;
        GuiRenderPrimitives.V(this.G$src$D$1b2f02a(), this.n(), 8.0, 1.0, ProfileCreateDividerComponent.J.var_java_awt_Color_d);
        this.getAlternateFontRenderer(0.6).W(string, this.G$src$D$1b2f02a() + 4.0, this.n() + 2.5, ProfileCreateDividerComponent.J.var_java_awt_Color_A);
    }

    @Override
    public double x() {
        return 0.0;
    }

    @Override
    public void I() {
    }

    @Override
    public void F() {
    }

    @Override
    public double C() {
        return 0.0;
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    @Override
    public void u() {
    }
}

