package gg.vape.ui.font;

import gg.vape.Vape;
import gg.vape.ui.font.stb.StbGlyphCache;
import gg.vape.ui.font.stb.StbGlyphCacheEntry;
import gg.vape.utils.MutableColor;
import gg.vape.utils.render.BufferedGuiRenderPrimitives;
import gg.vape.utils.render.BufferedRenderPrimitives;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.FontSet;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.MatrixStack;
import gg.vape.wrapper.impl.Minecraft;
import java.awt.Color;
import java.util.Arrays;
import org.lwjgl.opengl.GL11;

public class StbSmoothFontRenderer extends SmoothFontRenderer {
   private boolean R;
   private int L;
   private static final String c = "[MCFontRenderer] Using legacy Minecraft font renderer fallback";
   private long A;
   private final StbGlyphCache w;
   private int V;
   private Object P = null;

   public StbSmoothFontRenderer(int n) {
      this.V = -1;
      this.A = 0L;
      this.L = -1;
      this.R = false;
      this.D = n;
      this.w = new StbGlyphCache();
   }

   private void H() {
      int fontIdentity = -1;

      try {
         EntityPlayerSP entityPlayerSP = Minecraft.thePlayer();
         if (entityPlayerSP != null && !entityPlayerSP.isNull()) {
            fontIdentity = entityPlayerSP.l();
         }
      } catch (Exception var5) {
      }

      if (fontIdentity != -1) {
         if (fontIdentity == this.V) {
            return;
         }

         this.V = fontIdentity;
      } else {
         long l2 = System.currentTimeMillis();
         if (l2 - this.A < 50L) {
            return;
         }

         this.A = l2;
      }

      try {
         gg.vape.wrapper.impl.FontManager fontManager = Minecraft.q();
         if (fontManager == null) {
            return;
         }

         FontSet fontSet = fontManager.h();
         if (fontSet == null || fontSet.isNull()) {
            return;
         }

         Object object2 = fontSet.getObject();
         if (this.P == null) {
            this.P = object2;
            if (!this.w.u$src$Z$1yysxfx()) {
               this.w.U(fontSet);
               this.f();
               this.L = -1;
            }

            return;
         }

         if (object2 != this.P) {
            this.w.U(fontSet);
            this.f();
            this.L = -1;
            this.P = object2;
         }
      } catch (Exception var6) {
      }
   }

   private Color G(Color color) {
      int n = (int)(color.getRed() * 0.25F);
      int n2 = (int)(color.getGreen() * 0.25F);
      int n3 = (int)(color.getBlue() * 0.25F);
      return new Color(n, n2, n3, color.getAlpha());
   }

   @Override
   public void g(String string, double d, double d2, int n) {
      this.u(string, d, d2, n, false, false);
   }

   @Override
   public double R(String string, boolean bl) {
      float f = this.y();
      return 8.0F * f;
   }

   private void E(float f, float f2, SmoothFontGlyph smoothFontGlyph, int n, boolean bl, Color color, float f3, boolean worldSpace) {
      this.L(n);
      if (worldSpace) {
         if (bl) {
            BufferedRenderPrimitives.drawMinecraftColorFontGlyph(f, f2, smoothFontGlyph, n, color, f3);
         } else {
            BufferedRenderPrimitives.drawMinecraftIntensityFontGlyph(f, f2, smoothFontGlyph, n, color, f3);
         }
      } else if (bl) {
         BufferedGuiRenderPrimitives.drawMinecraftColorFontGlyph(f, f2, smoothFontGlyph, n, color, f3);
      } else {
         BufferedGuiRenderPrimitives.drawMinecraftIntensityFontGlyph(f, f2, smoothFontGlyph, n, color, f3);
      }
   }

   private void A() {
      if (!this.R) {
         this.R = true;
         Vape.debugLog(c);
      }
   }

   public void c(String string, double d, double d2, int n, boolean bl, boolean bl2) {
      this.Z(string, d, d2, n, bl, bl2, false);
   }

   @Override
   public void O(String string, double d, double d2, int n, boolean bl) {
      this.c(string, d, d2, n, bl, false);
   }

   @Override
   public double Y(String string, boolean bl) {
      this.H();
      if (string != null && !string.isEmpty()) {
         if (this.j) {
            string = Vape.INSTANCE.getFontSelector().W().s(string);
         }

         if (this.u()) {
            this.A();
            return Minecraft.getFontRenderer().getStringWidth(this.P(string)) * this.y();
         } else if (!this.w.u$src$Z$1yysxfx() && !this.x()) {
            return Minecraft.getFontRenderer().getStringWidth(this.P(string)) * this.y();
         } else if (this.T.containsKey(string = this.P(string))) {
            return this.T.get(string);
         } else {
            float f = this.y();
            float f2 = this.w.D(string) * f;
            this.T.put(string, (double)f2);
            return f2;
         }
      } else {
         return 0.0;
      }
   }

   public void w(FontSet fontSet) {
      this.w.U(fontSet);
      this.P = fontSet != null ? fontSet.getObject() : null;
   }

   @Override
   public void t(String string, double d, double d2, Color color, Color color2, boolean bl) {
      this.H(string, d, d2, color, color2, bl, false);
   }

   public boolean x() {
      return this.w.u$src$Z$1yysxfx() ? true : this.w.l();
   }

   public void e() {
      this.w.u();
      this.L = -1;
      this.f();
   }

   public void u(String string, double d, double d2, int n, boolean bl, boolean bl2) {
      if (string != null && !string.isEmpty()) {
         String string2 = string;
         if (this.j) {
            string2 = Vape.INSTANCE.getFontSelector().W().s(string);
         }

         if (this.u()) {
            this.A();
            this.y(string2, d, d2, n, true);
         } else if (!this.w.u$src$Z$1yysxfx() && !this.x()) {
            this.y(string2, d, d2, n, true);
         } else {
            int n2 = n >> 24 & 0xFF;
            if (n2 == 0) {
               n2 = 255;
            }

            int n3 = n >> 16 & 0xFF;
            int n4 = n >> 8 & 0xFF;
            int n5 = n & 0xFF;
            Color color = this.G(new Color(n3, n4, n5, n2));
            int n6 = color.getAlpha() << 24 | color.getRed() << 16 | color.getGreen() << 8 | color.getBlue();
            this.Z(string, d + 1.0, d2 + 1.0, n6, bl, bl2, true);
            this.Z(string, d, d2, n, bl, bl2, false);
         }
      }
   }

   private float y() {
      float f = this.D / 16.0F;
      float f2 = (float)Vape.INSTANCE.getClientSettings().getGuiScaleFactor();
      if (f2 != 1.0F) {
         f *= 1.0F / f2;
      }

      return f;
   }

   @Override
   public void i(String string, double d, double d2, Color color) {
      for (char c : string.toCharArray()) {
         this.f(String.valueOf(c), d, d2, color);
         d2 += this.d(String.valueOf(c)) * 0.9;
      }
   }

   private void L(int n) {
      if (n != this.L) {
         int n2 = GL11.glGetInteger(32873);
         GL11.glBindTexture(3553, n);
         GL11.glTexParameteri(3553, 10241, 9728);
         GL11.glTexParameteri(3553, 10240, 9728);
         GL11.glBindTexture(3553, n2);
         this.L = n;
      }
   }

   private String P(String string) {
      StringBuilder stringBuilder = new StringBuilder();
      char[] cArray = string.toCharArray();

      for (int i = 0; i < cArray.length; i++) {
         char c = cArray[i];
         if (c == 167 && i + 1 < cArray.length) {
            i++;
         } else {
            stringBuilder.append(c);
         }
      }

      return stringBuilder.toString();
   }

   public void R(String string, double d, double d2, Color color, boolean bl, boolean bl2) {
      int n = color.getRGB();
      if (color instanceof MutableColor) {
         n = ((MutableColor)color).l();
      }

      this.u(string, d, d2, n, bl, bl2);
   }

   private void Z(String string, double d, double d2, int n, boolean bl, boolean bl2, boolean bl3) {
      this.H();
      if (string != null && !string.isEmpty()) {
         if (this.j) {
            string = Vape.INSTANCE.getFontSelector().W().s(string);
         }

         if (this.u()) {
            this.A();
            this.y(string, d, d2, n, false);
         } else if (!this.w.u$src$Z$1yysxfx() && !this.x()) {
            this.y(string, d, d2, n, false);
         } else {
            int n2 = n >> 16 & 0xFF;
            int n3 = n >> 8 & 0xFF;
            int n4 = n & 0xFF;
            int n5 = n >> 24 & 0xFF;
            if (n5 == 0) {
               n5 = 255;
            }

            Color color;
            Color color2 = color = new Color(n2, n3, n4, n5);
            char[] cArray = new char[string.length()];
            string = this.g(string, cArray);
            float f = this.y();
            float f2 = (float)d;

            for (int i = 0; i < string.length(); i++) {
               char c = string.charAt(i);
               char c2 = cArray[i];
               if (c2 != 0) {
                  if (c2 == 'r') {
                     color = color2;
                  } else {
                     float[] fArray = new float[3];
                     this.o(c2, fArray);
                     color = new Color(fArray[0], fArray[1], fArray[2], n5 / 255.0F);
                     if (bl3) {
                        color = this.G(color);
                     }
                  }
               }

               StbGlyphCacheEntry stbGlyphCacheEntry2 = this.w.R(c);
               if (stbGlyphCacheEntry2 == null) {
                  if (ForgeVersion.MC_26_2.d() && c == ' ') {
                     f2 += this.w.H(c) * f;
                  } else {
                     StbGlyphCacheEntry stbGlyphCacheEntry = this.w.R(' ');
                     if (stbGlyphCacheEntry != null) {
                        f2 += stbGlyphCacheEntry.i * f;
                     }
                  }
               } else {
                  this.E(f2, (float)d2, stbGlyphCacheEntry2.d, stbGlyphCacheEntry2.y, stbGlyphCacheEntry2.u, color, f, bl2);
                  f2 += stbGlyphCacheEntry2.i * f;
               }
            }
         }
      }
   }

   public boolean z() {
      return this.w.u$src$Z$1yysxfx();
   }

   private boolean u() {
      return ForgeVersion.MC_1_21_10.v();
   }

   public void U(String string, double d, double d2, int n, boolean bl, boolean bl2) {
      this.c(string, d - this.N(string) / 2.0, d2, n, bl, bl2);
   }

   private String g(String string, char[] cArray) {
      Arrays.fill(cArray, '\u0000');
      StringBuilder stringBuilder = new StringBuilder();
      char[] cArray2 = string.toCharArray();
      int n = 0;

      for (int i = 0; i < cArray2.length; i++) {
         char c = cArray2[i];
         if (c == 167 && i + 1 < cArray2.length) {
            char c2;
            cArray[n] = c2 = cArray2[++i];
         } else {
            stringBuilder.append(c);
            n++;
         }
      }

      return stringBuilder.toString();
   }

   public void H(String string, double d, double d2, Color color, Color color2, boolean bl, boolean bl2) {
      int n = color.getRGB();
      if (color instanceof MutableColor) {
         n = ((MutableColor)color).l();
      }

      this.Z(string, d + 1.0, d2 + 1.0, color2.getRGB(), bl, bl2, true);
      this.Z(string, d, d2, n, bl, bl2, false);
   }

   private void y(String string, double d, double d2, int n, boolean bl) {
      if (string != null && !string.isEmpty()) {
         float f = this.y();
         float f2 = 1.0F / f;
         double d3 = Math.ceil(d) * f2;
         double d4 = Math.ceil(d2) * f2;
         MatrixStack matrixStack = MatrixStack.A();
         matrixStack.S(f, f, f);
         if (bl) {
            Minecraft.getFontRenderer().V(string, d3, d4, n, matrixStack);
         } else {
            Minecraft.getFontRenderer().E(string, (float)d3, (float)d4, n, false, matrixStack);
         }
      }
   }

   @Override
   public void V(String string, double d, double d2, Color color, boolean bl) {
      this.R(string, d, d2, color, bl, false);
   }
}
