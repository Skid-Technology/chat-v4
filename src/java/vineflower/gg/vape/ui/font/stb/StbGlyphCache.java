package gg.vape.ui.font.stb;

import gg.vape.Vape;
import gg.vape.ui.font.SmoothFontGlyph;
import gg.vape.wrapper.impl.FontManager;
import gg.vape.wrapper.impl.FontSet;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GlyphInfo;
import gg.vape.wrapper.impl.Minecraft;
import java.util.HashMap;
import java.util.Map;
import org.lwjgl.opengl.GL11;

public class StbGlyphCache {
   private final Map<Integer, StbGlyphCacheEntry> I = new HashMap<>();
   private static final int u = 33321;
   private final Map<Integer, Boolean> h = new HashMap<>();
   private static final int E = 4099;
   private boolean U = false;
   private FontSet V;

   public float H(char c) {
      StbGlyphCacheEntry stbGlyphCacheEntry = this.R(c);
      if (stbGlyphCacheEntry != null) {
         return stbGlyphCacheEntry.i;
      } else {
         return ForgeVersion.MC_26_2.d() && c == ' ' ? Minecraft.getFontRenderer().getStringWidth(" ") : 0.0F;
      }
   }

   public void u() {
      this.I.clear();
      this.h.clear();
   }

   public void U(FontSet fontSet) {
      if (fontSet.isNull()) {
         Vape.debugLog("[MCFontSetup] Failed to initialize - FontSet is null");
      } else {
         this.V = fontSet;
         this.U = true;
         this.I.clear();
         this.h.clear();
         Vape.debugLog("[MCFontSetup] Initialized with FontSet");
      }
   }

   public float D(String string) {
      if (string != null && !string.isEmpty()) {
         float f = 0.0F;

         for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if (c == 167 && i + 1 < string.length()) {
               i++;
            } else {
               f += this.H(c);
            }
         }

         return f;
      } else {
         return 0.0F;
      }
   }

   public StbGlyphCacheEntry K(int n) {
      if (!this.U) {
         return null;
      } else {
         StbGlyphCacheEntry stbGlyphCacheEntry = this.I.get(n);
         if (stbGlyphCacheEntry != null) {
            return stbGlyphCacheEntry;
         } else {
            StbGlyphCacheEntry stbGlyphCacheEntry2 = this.B(n);
            if (stbGlyphCacheEntry2 != null) {
               this.I.put(n, stbGlyphCacheEntry2);
            }

            return stbGlyphCacheEntry2;
         }
      }
   }

   private boolean l(int n) {
      Boolean bl = this.h.get(n);
      if (bl != null) {
         return bl;
      } else {
         boolean bl2 = true;

         try {
            int n2 = GL11.glGetInteger(32873);
            GL11.glBindTexture(3553, n);
            int n3 = GL11.glGetTexLevelParameteri(3553, 0, 4099);
            bl2 = n3 != 33321;
            GL11.glBindTexture(3553, n2);
         } catch (Exception var6) {
            Vape.debugLog("[MCFontSetup] Error detecting texture format for ID " + n + ": " + var6.getMessage());
         }

         this.h.put(n, bl2);
         return bl2;
      }
   }

   private static Exception a(Exception exception) {
      return exception;
   }

   private StbGlyphCacheEntry B(int n) {
      try {
         GlyphInfo glyphInfo = this.V.getGlyphInfo((char)n);
         if (glyphInfo != null && glyphInfo.getObject() != null) {
            int n2 = glyphInfo.q();
            if (n2 <= 0) {
               Vape.debugLog("[MCFontSetup] Invalid texture ID for codepoint " + n);
               return null;
            } else {
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
         } else {
            return n != 32 ? this.K(32) : null;
         }
      } catch (Exception var15) {
         Vape.debugLog("[MCFontSetup] Error extracting glyph for codepoint " + n + ": " + var15.getMessage());
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
      } else {
         try {
            FontManager fontManager = Minecraft.q();
            if (fontManager == null) {
               Vape.debugLog("[MCFontSetup] FontManager is null");
               return false;
            } else {
               FontSet fontSet = fontManager.h();
               if (fontSet != null && fontSet.getObject() != null) {
                  this.U(fontSet);
                  return true;
               } else {
                  Vape.debugLog("[MCFontSetup] Default FontSet is null");
                  return false;
               }
            }
         } catch (Exception var3) {
            Vape.debugLog("[MCFontSetup] Error initializing from Minecraft: " + var3.getMessage());
            var3.printStackTrace();
            return false;
         }
      }
   }

   public boolean u$src$Z$1yysxfx() {
      return this.U && this.V != null;
   }
}
