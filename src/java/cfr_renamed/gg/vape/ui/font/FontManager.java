/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.font;

import gg.vape.Vape;
import gg.vape.ui.font.BufferedSmoothFontRenderer;
import gg.vape.ui.font.FontFamily;
import gg.vape.ui.font.FontFamilySwitchMap;
import gg.vape.ui.font.LegacySmoothFontRenderer;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.ui.font.StbSmoothFontRenderer;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class FontManager {
    final public static double var_double_r = 0.9375;
    byte[] var_byte_arr_I;
    private final ConcurrentMap<Integer, SmoothFontRenderer> var_java_util_concurrent_ConcurrentMap_java_lang_Integer,_gg_vape_ui_font_SmoothFontRenderer__p;
    final public static double var_double_K = 0.5;
    byte[] var_byte_arr_J;
    public static ArrayList<String> var_java_util_ArrayList_java_lang_String__y;
    byte[] var_byte_arr_w;
    private final ConcurrentMap<Integer, SmoothFontRenderer> var_java_util_concurrent_ConcurrentMap_java_lang_Integer,_gg_vape_ui_font_SmoothFontRenderer__q;
    private final String var_java_lang_String_l;
    private final ConcurrentMap<Integer, SmoothFontRenderer> var_java_util_concurrent_ConcurrentMap_java_lang_Integer,_gg_vape_ui_font_SmoothFontRenderer__N;
    final public static double var_double_z = 0.875;
    private final ConcurrentMap<Integer, SmoothFontRenderer> var_java_util_concurrent_ConcurrentMap_java_lang_Integer,_gg_vape_ui_font_SmoothFontRenderer__b = new ConcurrentHashMap<Integer, SmoothFontRenderer>();
    byte[] var_byte_arr_V;
    private boolean var_boolean_X = false;
    final public static double var_double_C = 0.6875;
    private final ConcurrentMap<Integer, SmoothFontRenderer> var_java_util_concurrent_ConcurrentMap_java_lang_Integer,_gg_vape_ui_font_SmoothFontRenderer__u;
    private final ConcurrentMap<Integer, SmoothFontRenderer> var_java_util_concurrent_ConcurrentMap_java_lang_Integer,_gg_vape_ui_font_SmoothFontRenderer__U;
    final public static double var_double_t = 0.5625;
    private final ConcurrentMap<Integer, SmoothFontRenderer> var_java_util_concurrent_ConcurrentMap_java_lang_Integer,_gg_vape_ui_font_SmoothFontRenderer__M = new ConcurrentHashMap<Integer, SmoothFontRenderer>();
    final public static double var_double_i = 0.75;
    private final ConcurrentMap<Integer, SmoothFontRenderer> var_java_util_concurrent_ConcurrentMap_java_lang_Integer,_gg_vape_ui_font_SmoothFontRenderer__P = new ConcurrentHashMap<Integer, SmoothFontRenderer>();
    final public static double var_double_s = 0.8125;
    final public static double var_double_x = 0.625;
    final public static double var_double_Z = 16.0;

    public boolean p() {
        return this.var_boolean_X;
    }

    public SmoothFontRenderer n(FontFamily fontFamily, int n, boolean bl) {
        if (fontFamily != null) {
            switch (FontFamilySwitchMap.var_int_arr_d[fontFamily.ordinal()]) {
                case 1: {
                    return this.z(n, bl);
                }
                case 2: {
                    return this.a(n, bl);
                }
                case 3: {
                    return this.o(n);
                }
                case 4: {
                    return this.x(n);
                }
            }
        }
        return this.D(n, bl);
    }

    public SmoothFontRenderer D(int n, boolean bl) {
        FontFamily fontFamily = Vape.INSTANCE.getFontSelector().W().b();
        if (fontFamily != null) {
            return this.n(fontFamily, n, bl);
        }
        return this.z(n, bl);
    }

    public SmoothFontRenderer Y() {
        return this.H(false);
    }

    public SmoothFontRenderer W(double d, boolean bl) {
        int n = (int)(16.0 * d);
        double d2 = Vape.INSTANCE.getClientSettings().getGuiScaleFactor();
        n = (int)((double)n * d2);
        return this.D(n, true);
    }

    public SmoothFontRenderer Y(double d) {
        return this.E(d, true);
    }

    public void U() {
        this.var_java_util_concurrent_ConcurrentMap_java_lang_Integer,_gg_vape_ui_font_SmoothFontRenderer__b.clear();
        this.var_java_util_concurrent_ConcurrentMap_java_lang_Integer,_gg_vape_ui_font_SmoothFontRenderer__M.clear();
        this.var_java_util_concurrent_ConcurrentMap_java_lang_Integer,_gg_vape_ui_font_SmoothFontRenderer__P.clear();
        this.var_java_util_concurrent_ConcurrentMap_java_lang_Integer,_gg_vape_ui_font_SmoothFontRenderer__N.clear();
        this.var_java_util_concurrent_ConcurrentMap_java_lang_Integer,_gg_vape_ui_font_SmoothFontRenderer__q.clear();
        this.var_java_util_concurrent_ConcurrentMap_java_lang_Integer,_gg_vape_ui_font_SmoothFontRenderer__U.clear();
        this.var_java_util_concurrent_ConcurrentMap_java_lang_Integer,_gg_vape_ui_font_SmoothFontRenderer__u.clear();
        this.var_java_util_concurrent_ConcurrentMap_java_lang_Integer,_gg_vape_ui_font_SmoothFontRenderer__p.clear();
    }

    private SmoothFontRenderer a(int n, boolean bl) {
        ConcurrentMap<Integer, SmoothFontRenderer> concurrentMap = bl ? this.var_java_util_concurrent_ConcurrentMap_java_lang_Integer,_gg_vape_ui_font_SmoothFontRenderer__q : this.var_java_util_concurrent_ConcurrentMap_java_lang_Integer,_gg_vape_ui_font_SmoothFontRenderer__b;
        SmoothFontRenderer smoothFontRenderer = (SmoothFontRenderer)concurrentMap.get(n);
        if (smoothFontRenderer != null) {
            return smoothFontRenderer;
        }
        String string = bl ? "arialbd.ttf" : "arial.ttf";
        SmoothFontRenderer smoothFontRenderer2 = GuiRenderPrimitives.d() ? new BufferedSmoothFontRenderer(string, n) : new LegacySmoothFontRenderer(string, n);
        concurrentMap.put(n, smoothFontRenderer2);
        return smoothFontRenderer2;
    }

    private SmoothFontRenderer z(int n, boolean bl) {
        ConcurrentMap<Integer, SmoothFontRenderer> concurrentMap = bl ? this.var_java_util_concurrent_ConcurrentMap_java_lang_Integer,_gg_vape_ui_font_SmoothFontRenderer__U : this.var_java_util_concurrent_ConcurrentMap_java_lang_Integer,_gg_vape_ui_font_SmoothFontRenderer__M;
        ConcurrentMap<Integer, SmoothFontRenderer> concurrentMap2 = concurrentMap;
        if (!concurrentMap.containsKey(n)) {
            byte[] fontData = bl ? this.var_byte_arr_J : this.var_byte_arr_w;
            String fontName = bl ? "Proxima Bold" : "Proxima";
            SmoothFontRenderer smoothFontRenderer = GuiRenderPrimitives.d() ? new BufferedSmoothFontRenderer(fontData, n, fontName) : new LegacySmoothFontRenderer(fontData, n, fontName);
            concurrentMap.put(n, smoothFontRenderer);
            return smoothFontRenderer;
        }
        return (SmoothFontRenderer)concurrentMap.get(n);
    }

    public SmoothFontRenderer p(double d) {
        int n = (int)(16.0 * d);
        double d2 = Vape.INSTANCE.getClientSettings().getGuiScaleFactor();
        n = (int)((double)n * d2);
        return this.x(n);
    }

    public void e() {
        String string = "proxima.ttf";
        this.var_byte_arr_w = Vape.readResource(string);
        String string2 = "proximabd.ttf";
        this.var_byte_arr_J = Vape.readResource(string2);
        String string3 = "noto.ttf";
        this.var_byte_arr_V = Vape.readResource(string3);
        String string4 = "poppins_rg.ttf";
        this.var_byte_arr_I = Vape.readResource(string4);
        this.var_boolean_X = true;
    }

    public SmoothFontRenderer b(FontFamily fontFamily, float f, boolean bl) {
        int n = (int)(16.0f * f);
        double d = Vape.INSTANCE.getClientSettings().getGuiScaleFactor();
        n = (int)((double)n * d);
        return this.n(fontFamily, n, bl);
    }

    public FontManager() {
        this.var_java_lang_String_l = "arial";
        this.var_java_util_concurrent_ConcurrentMap_java_lang_Integer,_gg_vape_ui_font_SmoothFontRenderer__N = new ConcurrentHashMap<Integer, SmoothFontRenderer>();
        this.var_java_util_concurrent_ConcurrentMap_java_lang_Integer,_gg_vape_ui_font_SmoothFontRenderer__q = new ConcurrentHashMap<Integer, SmoothFontRenderer>();
        this.var_java_util_concurrent_ConcurrentMap_java_lang_Integer,_gg_vape_ui_font_SmoothFontRenderer__U = new ConcurrentHashMap<Integer, SmoothFontRenderer>();
        this.var_java_util_concurrent_ConcurrentMap_java_lang_Integer,_gg_vape_ui_font_SmoothFontRenderer__u = new ConcurrentHashMap<Integer, SmoothFontRenderer>();
        this.var_java_util_concurrent_ConcurrentMap_java_lang_Integer,_gg_vape_ui_font_SmoothFontRenderer__p = new ConcurrentHashMap<Integer, SmoothFontRenderer>();
    }

    public SmoothFontRenderer x(int n) {
        if (!GuiRenderPrimitives.d()) {
            return this.z(n, false);
        }
        SmoothFontRenderer smoothFontRenderer = (SmoothFontRenderer)this.var_java_util_concurrent_ConcurrentMap_java_lang_Integer,_gg_vape_ui_font_SmoothFontRenderer__p.get(n);
        if (smoothFontRenderer != null) {
            return smoothFontRenderer;
        }
        StbSmoothFontRenderer stbSmoothFontRenderer = new StbSmoothFontRenderer(n);
        this.var_java_util_concurrent_ConcurrentMap_java_lang_Integer,_gg_vape_ui_font_SmoothFontRenderer__p.put(n, stbSmoothFontRenderer);
        return stbSmoothFontRenderer;
    }

    public SmoothFontRenderer w() {
        return this.D(12, false);
    }

    public SmoothFontRenderer K(double d, boolean bl) {
        int n = (int)(16.0 * d);
        double d2 = Vape.INSTANCE.getClientSettings().getGuiScaleFactor();
        SmoothFontRenderer smoothFontRenderer = (SmoothFontRenderer)this.var_java_util_concurrent_ConcurrentMap_java_lang_Integer,_gg_vape_ui_font_SmoothFontRenderer__P.get(n = (int)((double)n * d2));
        if (smoothFontRenderer != null) {
            return smoothFontRenderer;
        }
        SmoothFontRenderer smoothFontRenderer2 = GuiRenderPrimitives.d() ? new BufferedSmoothFontRenderer("bahnschrift", n) : new LegacySmoothFontRenderer("bahnschrift", n);
        this.var_java_util_concurrent_ConcurrentMap_java_lang_Integer,_gg_vape_ui_font_SmoothFontRenderer__P.put(n, smoothFontRenderer2);
        return smoothFontRenderer2;
    }

    public SmoothFontRenderer E(double d, boolean bl) {
        int n = (int)(16.0 * d);
        double d2 = Vape.INSTANCE.getClientSettings().getGuiScaleFactor();
        n = (int)((double)n * d2);
        return this.D(n, false);
    }

    public SmoothFontRenderer H(boolean bl) {
        return this.D(16, bl);
    }

    private SmoothFontRenderer o(int n) {
        SmoothFontRenderer smoothFontRenderer = (SmoothFontRenderer)this.var_java_util_concurrent_ConcurrentMap_java_lang_Integer,_gg_vape_ui_font_SmoothFontRenderer__N.get(n);
        if (smoothFontRenderer != null) {
            return smoothFontRenderer;
        }
        SmoothFontRenderer smoothFontRenderer2 = GuiRenderPrimitives.d() ? new BufferedSmoothFontRenderer(this.var_byte_arr_V, n, "Noto") : new LegacySmoothFontRenderer(this.var_byte_arr_V, n, "Noto");
        this.var_java_util_concurrent_ConcurrentMap_java_lang_Integer,_gg_vape_ui_font_SmoothFontRenderer__N.put(n, smoothFontRenderer2);
        return smoothFontRenderer2;
    }

    public SmoothFontRenderer a(int n) {
        SmoothFontRenderer smoothFontRenderer = (SmoothFontRenderer)this.var_java_util_concurrent_ConcurrentMap_java_lang_Integer,_gg_vape_ui_font_SmoothFontRenderer__u.get(n);
        if (smoothFontRenderer != null) {
            return smoothFontRenderer;
        }
        SmoothFontRenderer smoothFontRenderer2 = GuiRenderPrimitives.d() ? new BufferedSmoothFontRenderer(this.var_byte_arr_I, n, "Poppins") : new LegacySmoothFontRenderer(this.var_byte_arr_I, n, "Poppins");
        this.var_java_util_concurrent_ConcurrentMap_java_lang_Integer,_gg_vape_ui_font_SmoothFontRenderer__u.put(n, smoothFontRenderer2);
        return smoothFontRenderer2;
    }

    public Map<String, Map<Integer, SmoothFontRenderer>> n() {
        LinkedHashMap<String, Map<Integer, SmoothFontRenderer>> linkedHashMap = new LinkedHashMap<String, Map<Integer, SmoothFontRenderer>>();
        linkedHashMap.put("fonts", this.var_java_util_concurrent_ConcurrentMap_java_lang_Integer,_gg_vape_ui_font_SmoothFontRenderer__b);
        linkedHashMap.put("modernFonts", this.var_java_util_concurrent_ConcurrentMap_java_lang_Integer,_gg_vape_ui_font_SmoothFontRenderer__M);
        linkedHashMap.put("bahnschriftFont", this.var_java_util_concurrent_ConcurrentMap_java_lang_Integer,_gg_vape_ui_font_SmoothFontRenderer__P);
        linkedHashMap.put("notoFont", this.var_java_util_concurrent_ConcurrentMap_java_lang_Integer,_gg_vape_ui_font_SmoothFontRenderer__N);
        linkedHashMap.put("boldFonts", this.var_java_util_concurrent_ConcurrentMap_java_lang_Integer,_gg_vape_ui_font_SmoothFontRenderer__q);
        linkedHashMap.put("boldModernFonts", this.var_java_util_concurrent_ConcurrentMap_java_lang_Integer,_gg_vape_ui_font_SmoothFontRenderer__U);
        linkedHashMap.put("poppinsFont", this.var_java_util_concurrent_ConcurrentMap_java_lang_Integer,_gg_vape_ui_font_SmoothFontRenderer__u);
        return linkedHashMap;
    }

    static {
        var_java_util_ArrayList_java_lang_String__y = new ArrayList();
    }
}

