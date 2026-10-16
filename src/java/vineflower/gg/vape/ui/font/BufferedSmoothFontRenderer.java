package gg.vape.ui.font;

import gg.vape.Vape;
import gg.vape.ui.font.stb.StbTrueTypeFontInfo;
import gg.vape.ui.font.stb.StbTrueTypePackContext;
import gg.vape.ui.font.stb.StbTrueTypePackRange;
import gg.vape.ui.font.stb.StbTrueTypePackedChar;
import gg.vape.utils.MutableColor;
import gg.vape.utils.RenderThreadTaskQueue;
import gg.vape.utils.render.BufferedGuiRenderPrimitives;
import gg.vape.utils.render.BufferedRenderPrimitives;
import gg.vape.utils.render.GlImageTexture;
import gg.vape.wrapper.impl.Minecraft;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.lwjgl.BufferUtils;

public class BufferedSmoothFontRenderer extends SmoothFontRenderer {
   private float x;
   private Map<Integer, SmoothFontGlyph> R = new HashMap<>();
   private final float Z = 1.0F;
   private GlImageTexture i;
   private float q;

   private void u(double d, double d2, String string, int n, boolean bl, float f, boolean bl2) {
      SmoothFontRenderer smoothFontRenderer = Vape.INSTANCE.getFontManager().x(this.D);
      if (smoothFontRenderer instanceof StbSmoothFontRenderer) {
         StbSmoothFontRenderer stbSmoothFontRenderer = (StbSmoothFontRenderer)smoothFontRenderer;
         if (bl) {
            stbSmoothFontRenderer.u(string, d, d2, n, false, bl2);
         } else {
            stbSmoothFontRenderer.c(string, d, d2, n, false, bl2);
         }
      } else if (bl) {
         smoothFontRenderer.g(string, d, d2, n);
      } else {
         smoothFontRenderer.F(string, d, d2, n);
      }
   }

   public void T(String string, double d, double d2, int n, boolean bl, boolean bl2) {
      if (!bl) {
         this.v(string, d + 0.5, d2 + 0.5, Integer.MIN_VALUE);
         this.F(string, d, d2, n);
      } else {
         this.u(d, d2, string, n, true, 1.0F, bl2);
      }
   }

   public BufferedSmoothFontRenderer(byte[] byArray, int n, String string) {
      this.w(byArray, n, string);
   }

   private static Throwable a(Throwable throwable) {
      return throwable;
   }

   @Override
   public void t(String string, double d, double d2, Color color, Color color2, boolean bl) {
      this.g(string, d, d2, color, color2, bl, false);
   }

   public void W(String string, double d, double d2, Color color, boolean bl, boolean bl2) {
      int n = color.getRGB();
      if (color instanceof MutableColor) {
         n = ((MutableColor)color).l();
      }

      this.T(string, d, d2, n, bl, bl2);
   }

   @Override
   public double Y(String string, boolean bl) {
      string = Vape.INSTANCE.getFontSelector().W().s(string);
      double d = Vape.INSTANCE.getClientSettings().getGuiScaleFactor();
      String string2 = "";
      char[] cArray = string.toCharArray();

      for (int i = 0; i < cArray.length; i++) {
         char c = cArray[i];
         if (c == 167) {
            i++;
         } else {
            string2 = string2 + c;
         }
      }

      string = string2;
      float f = 0.6F;
      if (d != 1.0) {
         f *= (float)(1.0 / d);
      }

      if (bl) {
         float f2 = this.D / 16.0F;
         if (d != 1.0) {
            f2 *= (float)(1.0 / d);
         }

         return (int)(Minecraft.getFontRenderer().getStringWidth(string2) * f2);
      } else if (this.T.containsKey(string2)) {
         return this.T.get(string2);
      } else {
         double d2 = 0.0;

         for (int ix = 0; ix < string.length(); ix++) {
            char c = string.charAt(ix);
            SmoothFontGlyph smoothFontGlyph = this.R.get(Integer.valueOf(c));
            if (smoothFontGlyph == null) {
               smoothFontGlyph = this.R.get(32);
            }

            d2 += smoothFontGlyph.S * f;
         }

         this.T.put(string, d2);
         return d2;
      }
   }

   @Override
   public double R(String string, boolean bl) {
      if (!bl) {
         float f = 0.6F;
         float f2 = (float)Vape.INSTANCE.getClientSettings().getGuiScaleFactor();
         if (f2 != 1.0) {
            f *= 1.0F / f2;
         }

         return this.D * f;
      } else {
         return 8.0F * (this.D / 16.0F);
      }
   }

   private void w(byte[] byArray, int n, String string) {
      if (string.contains("Proxima")) {
         n--;
      }

      HashSet<Integer> hashSet = new HashSet<>();

      for (int n2 = 32; n2 <= 126; n2++) {
         hashSet.add(n2);
      }

      for (int var36 = 160; var36 <= 255; var36++) {
         hashSet.add(var36);
      }

      hashSet.add(0);
      String string2 = "chinese.properties.txt";
      byte[] byArray2 = Vape.readResource(string2);
      if (byArray2 != null) {
         try {
            label108: {
               ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byArray2);
               BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(byteArrayInputStream, StandardCharsets.UTF_8));
               Throwable throwable = null;

               try {
                  int n3;
                  while ((n3 = bufferedReader.read()) != -1) {
                     if (n3 >= 32) {
                        hashSet.add(n3);
                     }
                  }

                  if (bufferedReader == null) {
                     break label108;
                  }
               } catch (Throwable var34) {
                  Throwable throwable2 = var34;

                  try {
                     throwable = throwable2;
                     throw throwable2;
                  } catch (Throwable var33) {
                     Throwable throwable3 = var33;
                     if (bufferedReader == null) {
                        throw var33;
                     }

                     if (throwable == null) {
                        bufferedReader.close();
                        throw var33;
                     }

                     try {
                        bufferedReader.close();
                        throw throwable3;
                     } catch (Throwable var31) {
                        throwable.addSuppressed(var31);
                        throw var33;
                     }
                  }
               }

               bufferedReader.close();
            }
         } catch (Exception var35) {
            Vape.logThrowable(var35);
         }
      }

      int[] nArray7 = hashSet.stream().mapToInt(Integer::intValue).toArray();
      Arrays.sort(nArray7);
      this.D = n;
      int n4 = 4096;
      StbTrueTypeFontInfo stbTrueTypeFontInfo = new StbTrueTypeFontInfo();
      if (SmoothFontRasterState.z(stbTrueTypeFontInfo, byArray, 0) == 0) {
         Vape.debugLog("Failed to init font information");
      } else {
         int[] nArray = new int[1];
         int[] nArray2 = new int[1];
         int[] nArray3 = new int[1];
         SmoothFontRasterState.x(stbTrueTypeFontInfo, nArray, nArray2, nArray3);
         this.q = nArray[0];
         int[] nArray4 = new int[1];
         int[] nArray8 = new int[1];
         int[] nArray5 = new int[1];
         int[] nArray6 = new int[1];
         SmoothFontRasterState.s(stbTrueTypeFontInfo, 72, nArray4, nArray8, nArray5, nArray6);
         float f = nArray6[0] - nArray8[0];
         float f2 = nArray[0] - nArray2[0];
         float f3 = f / f2;
         float f4 = 0.6F;
         float f5 = this.D * (f4 / f3);
         byte[] byArray3 = new byte[n4 * n4];
         StbTrueTypePackContext stbTrueTypePackContext = new StbTrueTypePackContext();
         if (SmoothFontRasterState.C(stbTrueTypePackContext, byArray3, n4, n4, 0, 1) == 0) {
            Vape.debugLog("Failed to begin packing");
         } else {
            int n5 = 2;
            int n6 = 2;
            SmoothFontRasterState.n(stbTrueTypePackContext, n6, n5);

            try {
               StbTrueTypePackedChar[] stbTrueTypePackedCharArray = new StbTrueTypePackedChar[nArray7.length];

               for (int i = 0; i < stbTrueTypePackedCharArray.length; i++) {
                  stbTrueTypePackedCharArray[i] = new StbTrueTypePackedChar();
               }

               StbTrueTypePackRange stbTrueTypePackRange = new StbTrueTypePackRange();
               stbTrueTypePackRange.E = f5;
               stbTrueTypePackRange.R = 0;
               stbTrueTypePackRange.N = nArray7;
               stbTrueTypePackRange.b = nArray7.length;
               stbTrueTypePackRange.M = stbTrueTypePackedCharArray;
               stbTrueTypePackRange.i = (byte)n6;
               stbTrueTypePackRange.y = (byte)n5;
               StbTrueTypePackRange[] stbTrueTypePackRangeArray = new StbTrueTypePackRange[]{stbTrueTypePackRange};
               SmoothFontRasterState.c(stbTrueTypePackContext, byArray, 0, stbTrueTypePackRangeArray, stbTrueTypePackRangeArray.length);
               SmoothFontRasterState.S(stbTrueTypePackContext);
               this.x = SmoothFontRasterState.V(stbTrueTypeFontInfo, f5);

               for (int i = 0; i < nArray7.length; i++) {
                  int n7 = nArray7[i];
                  StbTrueTypePackedChar stbTrueTypePackedChar = stbTrueTypePackedCharArray[i];
                  f4 = 1.0F / n4;
                  float f6 = 1.0F / n4;
                  this.R
                     .put(
                        n7,
                        new SmoothFontGlyph(
                           stbTrueTypePackedChar.s,
                           stbTrueTypePackedChar.G,
                           stbTrueTypePackedChar.t,
                           stbTrueTypePackedChar.R,
                           stbTrueTypePackedChar.b * f4,
                           stbTrueTypePackedChar.k * f6,
                           stbTrueTypePackedChar.q * f4,
                           stbTrueTypePackedChar.p * f6,
                           stbTrueTypePackedChar.A
                        )
                     );
               }
            } catch (Exception var32) {
               Vape.logThrowable(var32);
            }

            ByteBuffer byteBuffer = BufferUtils.createByteBuffer(byArray3.length);
            byteBuffer.put(byArray3);
            ((Buffer)byteBuffer).flip();
            RenderThreadTaskQueue.enqueue(() -> this.lambda$createFont$2(n4, byteBuffer));
         }
      }
   }

   public BufferedSmoothFontRenderer(String string, int n) {
      if (FontManager.y.size() == 0) {
         File[] fileArray = new File(System.getProperty("user.home") + "\\AppData\\Local\\Microsoft\\Windows\\Fonts")
            .listFiles(BufferedSmoothFontRenderer::lambda$new$0);
         if (fileArray != null) {
            for (File file : fileArray) {
               if (file.exists()) {
                  FontManager.y.add(file.getAbsolutePath());
               }
            }
         }

         if ((fileArray = new File(System.getenv("SystemRoot") + "\\Fonts").listFiles(BufferedSmoothFontRenderer::lambda$new$1)) != null) {
            for (File filex : fileArray) {
               if (filex.exists()) {
                  FontManager.y.add(filex.getAbsolutePath());
               }
            }
         }
      }

      byte[] byArray = new byte[0];

      for (String string2 : FontManager.y) {
         if (string2.contains(string)) {
            try {
               Vape.debugLog("Found file attempting to read: " + string);
               File filexx = new File(string2);
               FileInputStream fileInputStream = new FileInputStream(filexx);
               byArray = new byte[(int)filexx.length()];
               fileInputStream.read(byArray);
               fileInputStream.close();
            } catch (Exception var9) {
               Vape.logThrowable(var9);
            }
            break;
         }
      }

      this.w(byArray, n, string);
   }

   private static boolean lambda$new$1(File file) {
      return file.isFile() && file.getName().endsWith(".ttf") || file.isDirectory();
   }

   public void B(String string, double d, double d2, int n, boolean bl, boolean bl2) {
      this.b(string, d - this.N(string) / 2.0, d2, n, bl, bl2);
   }

   public void b(String string, double d, double d2, int n, boolean bl, boolean bl2) {
      if (this.j) {
         string = Vape.INSTANCE.getFontSelector().W().s(string);
      }

      if (!bl) {
         if (string == null || string.length() == 0) {
            return;
         }

         int n2 = (n & 0xFF0000) >> 16;
         int n3 = (n & 0xFF00) >> 8;
         int n4 = n & 0xFF;
         int n5 = (n & 0xFF000000) >> 24;
         if (n5 < 0) {
            n5 += 256;
         }

         if (n5 == 0) {
            n5 = 255;
         }

         Color color = new Color(n2, n3, n4, n5);
         char[] cArray = new char[string.length()];
         Arrays.fill(cArray, '\u0000');
         int n6 = 0;
         String string2 = "";
         char[] cArray2 = string.toCharArray();

         for (int i = 0; i < cArray2.length; i++) {
            char c = cArray2[i];
            if (c == 167) {
               cArray[n6] = cArray2[++i];
            } else {
               string2 = string2 + c;
               n6++;
            }
         }

         string = string2;
         float f = 0.6F;
         float f2 = (float)Vape.INSTANCE.getClientSettings().getGuiScaleFactor();
         if (f2 != 1.0) {
            f *= 1.0F / f2;
         }

         d2 += this.q * this.x * f;
         Color color2 = color;

         for (int ix = 0; ix < string.length(); ix++) {
            char c2 = string.charAt(ix);
            SmoothFontGlyph smoothFontGlyph = this.R.get(Integer.valueOf(c2));
            if (smoothFontGlyph == null) {
               smoothFontGlyph = this.R.get(32);
            }

            char c;
            if ((c = cArray[ix]) != 0) {
               float[] fArray = new float[3];
               this.o(c, fArray);
               color2 = new Color(fArray[0], fArray[1], fArray[2]);
               if (c == 'r') {
                  color2 = color;
               }
            }

            if (bl2) {
               BufferedRenderPrimitives.drawFontGlyph((float)d, (float)d2, smoothFontGlyph, this.i, color2, f);
            } else {
               BufferedGuiRenderPrimitives.drawFontGlyph((float)d, (float)d2, smoothFontGlyph, this.i, color2, f);
            }

            d += smoothFontGlyph.S * f;
         }
      } else {
         this.u(d, d2, string, n, false, 1.0F, bl2);
      }
   }

   private static boolean lambda$new$0(File file) {
      return file.isFile() && file.getName().endsWith(".ttf") || file.isDirectory();
   }

   @Override
   public void g(String string, double d, double d2, int n) {
      this.T(string, d, d2, n, false, false);
   }

   @Override
   public void i(String string, double d, double d2, Color color) {
      char[] cArray = string.toCharArray();
      int n = cArray.length;

      for (int i = 0; i < n; i++) {
         Character c = cArray[i];
         this.f(c.toString(), d, d2, color);
         d2 += this.d(c.toString()) * 0.9;
      }
   }

   @Override
   public void O(String string, double d, double d2, int n, boolean bl) {
      this.b(string, d, d2, n, bl, false);
   }

   private void lambda$createFont$2(int n, ByteBuffer byteBuffer) {
      this.i = GlImageTexture.create(n, n, byteBuffer, 6403, 9729, 10497);
   }

   public void g(String string, double d, double d2, Color color, Color color2, boolean bl, boolean bl2) {
      int n = color.getRGB();
      if (color instanceof MutableColor) {
         n = ((MutableColor)color).l();
      }

      if (!bl) {
         this.v(string, d + 0.5, d2 + 0.5, color2.getRGB());
         this.d(string, d, d2, color);
      } else {
         this.u(d, d2, string, n, true, 1.0F, bl2);
      }
   }

   @Override
   public void V(String string, double d, double d2, Color color, boolean bl) {
      this.W(string, d, d2, color, bl, false);
   }
}
