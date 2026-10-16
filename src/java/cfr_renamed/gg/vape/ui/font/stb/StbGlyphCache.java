/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.font.stb;

import gg.vape.Vape;
import gg.vape.ui.font.SmoothFontGlyph;
import gg.vape.ui.font.stb.StbGlyphCacheEntry;
import gg.vape.wrapper.impl.FontManager;
import gg.vape.wrapper.impl.FontSet;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GlyphInfo;
import gg.vape.wrapper.impl.Minecraft;
import java.util.HashMap;
import java.util.Map;
import org.lwjgl.opengl.GL11;

public class StbGlyphCache {
    private final Map<Integer, StbGlyphCacheEntry> var_java_util_Map_java_lang_Integer,_gg_vape_ui_font_stb_StbGlyphCacheEntry__I = new HashMap<Integer, StbGlyphCacheEntry>();
    private final static int var_int_u = 33321;
    private final Map<Integer, Boolean> var_java_util_Map_java_lang_Integer,_java_lang_Boolean__h = new HashMap<Integer, Boolean>();
    private final static int var_int_E = 4099;
    private boolean var_boolean_U = false;
    private FontSet var_gg_vape_wrapper_impl_FontSet_V;

    public float H(char c) {
        StbGlyphCacheEntry stbGlyphCacheEntry = this.R(c);
        if (stbGlyphCacheEntry != null) {
            return stbGlyphCacheEntry.var_float_i;
        }
        if (ForgeVersion.MC_26_2.d() && c == ' ') {
            return Minecraft.getFontRenderer().getStringWidth(" ");
        }
        return 0.0f;
    }

    public void u() {
        this.var_java_util_Map_java_lang_Integer,_gg_vape_ui_font_stb_StbGlyphCacheEntry__I.clear();
        this.var_java_util_Map_java_lang_Integer,_java_lang_Boolean__h.clear();
    }

    public void U(FontSet fontSet) {
        if (fontSet.isNull()) {
            Vape.debugLog("[MCFontSetup] Failed to initialize - FontSet is null");
            return;
        }
        this.var_gg_vape_wrapper_impl_FontSet_V = fontSet;
        this.var_boolean_U = true;
        this.var_java_util_Map_java_lang_Integer,_gg_vape_ui_font_stb_StbGlyphCacheEntry__I.clear();
        this.var_java_util_Map_java_lang_Integer,_java_lang_Boolean__h.clear();
        Vape.debugLog("[MCFontSetup] Initialized with FontSet");
    }

    public float D(String string) {
        if (string == null || string.isEmpty()) {
            return 0.0f;
        }
        float f = 0.0f;
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (c == '\u00a7' && i + 1 < string.length()) {
                ++i;
                continue;
            }
            f += this.H(c);
        }
        return f;
    }

    public StbGlyphCacheEntry K(int n) {
        if (!this.var_boolean_U) {
            return null;
        }
        StbGlyphCacheEntry stbGlyphCacheEntry = this.var_java_util_Map_java_lang_Integer,_gg_vape_ui_font_stb_StbGlyphCacheEntry__I.get(n);
        if (stbGlyphCacheEntry != null) {
            return stbGlyphCacheEntry;
        }
        StbGlyphCacheEntry stbGlyphCacheEntry2 = this.B(n);
        if (stbGlyphCacheEntry2 != null) {
            this.var_java_util_Map_java_lang_Integer,_gg_vape_ui_font_stb_StbGlyphCacheEntry__I.put(n, stbGlyphCacheEntry2);
        }
        return stbGlyphCacheEntry2;
    }

    private boolean l(int n) {
        Boolean bl = this.var_java_util_Map_java_lang_Integer,_java_lang_Boolean__h.get(n);
        if (bl != null) {
            return bl;
        }
        boolean bl2 = true;
        try {
            int n2 = GL11.glGetInteger(32873);
            GL11.glBindTexture(3553, n);
            int n3 = GL11.glGetTexLevelParameteri(3553, 0, 4099);
            bl2 = n3 != 33321;
            GL11.glBindTexture(3553, n2);
        }
        catch (Exception exception) {
            Vape.debugLog("[MCFontSetup] Error detecting texture format for ID " + n + ": " + exception.getMessage());
        }
        this.var_java_util_Map_java_lang_Integer,_java_lang_Boolean__h.put(n, bl2);
        return bl2;
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    private StbGlyphCacheEntry B(int n) {
        try {
            GlyphInfo glyphInfo = this.var_gg_vape_wrapper_impl_FontSet_V.getGlyphInfo((char)n);
            if (glyphInfo == null || glyphInfo.getObject() == null) {
                if (n != 32) {
                    return this.K(32);
                }
                return null;
            }
            int n2 = glyphInfo.q();
            if (n2 <= 0) {
                Vape.debugLog("[MCFontSetup] Invalid texture ID for codepoint " + n);
                return null;
            }
            boolean bl = this.l(n2);
            float f = glyphInfo.H();
            float f2 = glyphInfo.x();
            float f3 = glyphInfo.S();
            float f4 = glyphInfo.M();
            float f5 = glyphInfo.h();
            float f6 = glyphInfo.s();
            float f7 = glyphInfo.t();
            float f8 = glyphInfo.j();
            float f9 = glyphInfo.F(false);
            SmoothFontGlyph smoothFontGlyph = new SmoothFontGlyph(f5, f7, f6, f8, f, f3, f2, f4, f9);
            return new StbGlyphCacheEntry(smoothFontGlyph, n2, f9, bl);
        }
        catch (Exception exception) {
            Vape.debugLog("[MCFontSetup] Error extracting glyph for codepoint " + n + ": " + exception.getMessage());
            return null;
        }
    }

    public StbGlyphCacheEntry R(char c) {
        return this.K(c);
    }

    public boolean l() {
        if (!ForgeVersion.MC_1_21_10.d()) {
            Vape.debugLog("[MCFontSetup] Minecraft font bridge requires 1.21.10+");
            return false;
        }
        try {
            FontManager fontManager = Minecraft.q();
            if (fontManager == null) {
                Vape.debugLog("[MCFontSetup] FontManager is null");
                return false;
            }
            FontSet fontSet = fontManager.h();
            if (fontSet == null || fontSet.getObject() == null) {
                Vape.debugLog("[MCFontSetup] Default FontSet is null");
                return false;
            }
            this.U(fontSet);
            return true;
        }
        catch (Exception exception) {
            Vape.debugLog("[MCFontSetup] Error initializing from Minecraft: " + exception.getMessage());
            exception.printStackTrace();
            return false;
        }
    }

    public boolean u$src$Z$1yysxfx() {
        return this.var_boolean_U && this.var_gg_vape_wrapper_impl_FontSet_V != null;
    }
}

