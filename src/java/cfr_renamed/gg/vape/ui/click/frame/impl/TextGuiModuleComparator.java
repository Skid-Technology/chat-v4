/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.frame.impl;

import gg.vape.module.Mod;
import gg.vape.ui.click.frame.impl.TextGuiOverlayComponent;
import java.util.Comparator;

public class TextGuiModuleComparator
implements Comparator<Mod> {
    final TextGuiOverlayComponent var_gg_vape_ui_click_frame_impl_TextGuiOverlayComponent_j;

    @Override
    public int compare(Mod mod, Mod mod2) {
        return this.W(mod, mod2);
    }

    public TextGuiModuleComparator(TextGuiOverlayComponent textGuiOverlayComponent) {
        this.var_gg_vape_ui_click_frame_impl_TextGuiOverlayComponent_j = textGuiOverlayComponent;
    }

    public int W(Mod mod, Mod mod2) {
        String string = mod.getName();
        String string2 = mod.getSuffixForMode(TextGuiOverlayComponent.F(this.var_gg_vape_ui_click_frame_impl_TextGuiOverlayComponent_j).getSuffixModeIndex());
        double d = this.var_gg_vape_ui_click_frame_impl_TextGuiOverlayComponent_j.var_gg_vape_ui_font_SmoothFontRenderer_b.Y(string, TextGuiOverlayComponent.G(this.var_gg_vape_ui_click_frame_impl_TextGuiOverlayComponent_j)) + this.var_gg_vape_ui_click_frame_impl_TextGuiOverlayComponent_j.var_gg_vape_ui_font_SmoothFontRenderer_K.Y(string2.isEmpty() ? "" : string2 + " ", TextGuiOverlayComponent.G(this.var_gg_vape_ui_click_frame_impl_TextGuiOverlayComponent_j));
        String string3 = mod2.getName();
        String string4 = mod2.getSuffixForMode(TextGuiOverlayComponent.F(this.var_gg_vape_ui_click_frame_impl_TextGuiOverlayComponent_j).getSuffixModeIndex());
        double d2 = this.var_gg_vape_ui_click_frame_impl_TextGuiOverlayComponent_j.var_gg_vape_ui_font_SmoothFontRenderer_b.Y(string3, TextGuiOverlayComponent.G(this.var_gg_vape_ui_click_frame_impl_TextGuiOverlayComponent_j)) + this.var_gg_vape_ui_click_frame_impl_TextGuiOverlayComponent_j.var_gg_vape_ui_font_SmoothFontRenderer_K.Y(string4.isEmpty() ? "" : string4 + " ", TextGuiOverlayComponent.G(this.var_gg_vape_ui_click_frame_impl_TextGuiOverlayComponent_j));
        return Double.compare(d2, d);
    }

    @Override
    public int compare(Object object, Object object2) {
        return this.compare((Mod)object, (Mod)object2);
    }
}

