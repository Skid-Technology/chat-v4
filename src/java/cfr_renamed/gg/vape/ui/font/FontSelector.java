/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.font;

import gg.vape.module.none.ClientSettings;
import gg.vape.ui.font.BaseFontOption;
import gg.vape.ui.font.FontOption;
import gg.vape.ui.font.FontOptionVariantA;
import gg.vape.ui.font.FontOptionVariantB;
import gg.vape.ui.font.FontOptionVariantC;
import gg.vape.ui.font.IdentityFontOption;
import gg.vape.ui.font.NotoFontOption;

public class FontSelector {
    private FontOption var_gg_vape_ui_font_FontOption_r = var_gg_vape_ui_font_BaseFontOption_j;
    final public static FontOption var_gg_vape_ui_font_FontOption_S;
    final public static FontOption var_gg_vape_ui_font_FontOption_a;
    final public static FontOption var_gg_vape_ui_font_FontOption_P;
    final public static FontOption var_gg_vape_ui_font_FontOption_c;
    final public static BaseFontOption var_gg_vape_ui_font_BaseFontOption_j;

    public FontOption W() {
        return this.var_gg_vape_ui_font_FontOption_r;
    }

    public void N(FontOption fontOption) {
        this.var_gg_vape_ui_font_FontOption_r = fontOption;
        ClientSettings.INSTANCE.requestFrameLayoutRefresh();
    }

    public FontSelector() {
        var_gg_vape_ui_font_FontOption_S.g(var_gg_vape_ui_font_BaseFontOption_j);
        var_gg_vape_ui_font_FontOption_c.g(var_gg_vape_ui_font_BaseFontOption_j);
        var_gg_vape_ui_font_FontOption_a.g(var_gg_vape_ui_font_BaseFontOption_j);
        var_gg_vape_ui_font_FontOption_P.g(var_gg_vape_ui_font_BaseFontOption_j);
    }

    static {
        String[] stringArray = new String[]{"French", "English", "Portuguese", "Spanish", "Chinese"};
        var_gg_vape_ui_font_BaseFontOption_j = new IdentityFontOption(stringArray[1]);
        var_gg_vape_ui_font_FontOption_S = new FontOptionVariantC(stringArray[3]);
        var_gg_vape_ui_font_FontOption_c = new NotoFontOption(stringArray[4]);
        var_gg_vape_ui_font_FontOption_a = new FontOptionVariantA(stringArray[2]);
        var_gg_vape_ui_font_FontOption_P = new FontOptionVariantB(stringArray[0]);
    }
}

