/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.font;

import gg.vape.ui.font.SmoothFontRenderer;
import java.util.LinkedHashMap;
import java.util.Map;

public class SmoothFontRendererStringCache
extends LinkedHashMap<String, String> {
    final SmoothFontRenderer var_gg_vape_ui_font_SmoothFontRenderer_L;

    @Override
    protected boolean removeEldestEntry(Map.Entry<String, String> entry) {
        return this.size() > 5120;
    }

    public SmoothFontRendererStringCache(SmoothFontRenderer smoothFontRenderer, int n, float f, boolean bl) {
        super(n, f, bl);
        this.var_gg_vape_ui_font_SmoothFontRenderer_L = smoothFontRenderer;
    }
}

