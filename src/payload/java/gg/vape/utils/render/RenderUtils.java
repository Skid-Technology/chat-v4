package gg.vape.utils.render;

import gg.vape.Vape;
import gg.vape.input.MouseInput;
import gg.vape.runtime.NativeBridge;
import gg.vape.ui.click.MousePosition;
import gg.vape.ui.click.component.GuiComponentContract;
import gg.vape.wrapper.impl.GlStateManager;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.Tessellator;
import java.awt.Color;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Stack;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public final class RenderUtils {
   private static IntBuffer T;
   private static final Color[] j = new Color[]{Color.RED, Color.YELLOW, Color.GREEN};
   static float I;
   static float O;
   static int r = 0;
   static float l;
   static int z = 0;
   private static FloatBuffer d;
   static float Q;
   static Stack<GlScissorRect> k = new Stack<>();
   private static String[] V;
   private static final Color[] v = new Color[]{Color.RED, Color.YELLOW, GuiComponentContract.J.B};
   private static FloatBuffer X;
   static int A = 0;
   private static final float[] H = new float[]{0.0F, 0.5F, 1.0F};
   private static MousePosition w;
   static Stack<GlScissorRect> P = new Stack<>();
   static int y = 0;
   static IntBuffer g = BufferUtils.createIntBuffer(16);

   public static void C() {
      int n = Minecraft.h() - MouseInput.getInvertedMouseY();
      C(MouseInput.getMouseX(), n);
   }

   public static void C(int mouseX, int mouseY) {
      double d = 2.0 * Vape.INSTANCE.getClientSettings().getGuiScaleFactor();
      w = new MousePosition((int)(mouseX / d), (int)(mouseY / d));
   }

   public static void X(Color color, Color color2, double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8) {
      if (GuiRenderPrimitives.d()) {
         BufferedGuiRenderPrimitives.drawLine(d, d2, d3, d4, 1.0F, color2);
         BufferedGuiRenderPrimitives.drawLine(d, d2, d7, d8, 1.0F, color2);
         BufferedGuiRenderPrimitives.drawLine(d5, d6, d7, d8, 1.0F, color);
         BufferedGuiRenderPrimitives.drawLine(d5, d6, d3, d4, 1.0F, color);
      } else {
         OpenGlBackendHolder.backend.disableCapability(3553);
         OpenGlBackendHolder.backend.enableCapability(3042);
         OpenGlBackendHolder.backend.disableCapability(3008);
         GL11.glBlendFunc(770, 771);
         GL11.glShadeModel(7425);
         w(color2);
         GL11.glBegin(7);
         GL11.glVertex2d(d, d2);
         GL11.glVertex2d(d3, d4);
         w(color);
         GL11.glVertex2d(d5, d6);
         GL11.glVertex2d(d7, d8);
         GL11.glEnd();
         GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
         GL11.glShadeModel(7424);
         OpenGlBackendHolder.backend.disableCapability(3042);
         OpenGlBackendHolder.backend.enableCapability(3008);
         OpenGlBackendHolder.backend.enableCapability(3553);
      }
   }

   public static void J(double d, double d2, double d3, double d4) {
      GL11.glBegin(7);
      GL11.glTexCoord2f(1.0F, 0.0F);
      GL11.glVertex3d(d, d4, 0.0);
      GL11.glTexCoord2f(0.0F, 0.0F);
      GL11.glVertex3d(d3, d4, 0.0);
      GL11.glTexCoord2f(0.0F, 1.0F);
      GL11.glVertex3d(d3, d2, 0.0);
      GL11.glTexCoord2f(1.0F, 1.0F);
      GL11.glVertex3d(d, d2, 0.0);
      GL11.glEnd();
   }

   public static void j(String[] stringArray) {
      V = stringArray;
   }

   public static void i(double d, double d2, double d3, double d4) {
   }

   public static void X(double d, double d2, double d3, double d4) {
      IntBuffer intBuffer = BufferUtils.createIntBuffer(16);
      gg.vape.wrapper.impl.GL11.X(2978, intBuffer);
      k.push(new GlScissorRect(intBuffer.get(0), intBuffer.get(1), intBuffer.get(2), intBuffer.get(3)));
      int n = Minecraft.h();
      double d5 = 2.0 * Vape.INSTANCE.getClientSettings().getGuiScaleFactor();
      int n2 = (int)(d3 * d5);
      int n3 = (int)(d4 * d5);
      double d6 = d * d5;
      int n4 = (int)d6;
      double d7 = d2 * d5;
      int n5 = (int)(n - d7 - n3);
      GL11.glViewport(n4, n5, n2, n3);
   }

   private static IllegalArgumentException a(IllegalArgumentException illegalArgumentException) {
      return illegalArgumentException;
   }

   public static void M(double d, double d2, double d3, double d4, double d5, Color color, Color color2) {
      if (GuiRenderPrimitives.d()) {
         BufferedRenderPrimitives.fillBorderAdjustedRect(d, d2, d3 - d, d4 - d2, (float)d5, color, color2);
      } else {
         GuiRenderPrimitives.q(d, d2, d3 - d, d4 - d2, d5, color, color2);
      }
   }

   public static int f() {
      return GuiRenderPrimitives.d() ? 0 : NativeBridge.sts();
   }

   public static Color K(float[] fArray, Color[] colorArray, float f) {
      Color color = Color.RED;
      if (fArray != null && colorArray != null && fArray.length == colorArray.length) {
         int[] nArray = Z(fArray, f);
         float[] fArray2 = new float[]{fArray[nArray[0]], fArray[nArray[1]]};
         Color[] colorArray2 = new Color[]{colorArray[nArray[0]], colorArray[nArray[1]]};
         float f2 = fArray2[1] - fArray2[0];
         float f3 = f - fArray2[0];
         float f4 = f3 / f2;
         color = h(colorArray2[0], colorArray2[1], 1.0F - f4);
      }

      return color;
   }

   public static Color q(float f, boolean bl) {
      return K(H, bl ? v : j, Math.max(0.0F, Math.min(1.0F, f)));
   }

   public static String[] v() {
      return V;
   }

   public static void j(double d, double d2, double d3, double d4) {
      Tessellator tessellator = Tessellator.getInstance();
      tessellator.M(7);
      tessellator.W(d, d4, 0.0, 1.0, 0.0);
      tessellator.W(d3, d4, 0.0, 0.0, 0.0);
      tessellator.W(d3, d2, 0.0, 0.0, 1.0);
      tessellator.W(d, d2, 0.0, 1.0, 1.0);
      tessellator.h();
   }

   private static Color h(Color color, Color color2, double d) {
      float f = (float)d;
      float f2 = 1.0F - f;
      float[] fArray = new float[3];
      float[] fArray2 = new float[3];
      color.getColorComponents(fArray);
      color2.getColorComponents(fArray2);
      float f3 = fArray[0] * f + fArray2[0] * f2;
      float f4 = fArray[1] * f + fArray2[1] * f2;
      float f5 = fArray[2] * f + fArray2[2] * f2;
      if (f3 < 0.0F) {
         f3 = 0.0F;
      } else if (f3 > 255.0F) {
         f3 = 255.0F;
      }

      if (f4 < 0.0F) {
         f4 = 0.0F;
      } else if (f4 > 255.0F) {
         f4 = 255.0F;
      }

      if (f5 < 0.0F) {
         f5 = 0.0F;
      } else if (f5 > 255.0F) {
         f5 = 255.0F;
      }

      try {
         return new Color(f3, f4, f5);
      } catch (IllegalArgumentException var13) {
         return Color.RED;
      }
   }

   public static void T() {
      if (!P.empty()) {
         GlScissorRect glScissorRect = P.pop();
         OpenGlBackendHolder.backend.setScissor(glScissorRect.x, glScissorRect.y, glScissorRect.width, glScissorRect.height);
      } else {
         OpenGlBackendHolder.backend.setScissor(A, r, z, y);
         BufferedGuiRenderPrimitives.scissorRect = null;
         OpenGlBackendHolder.backend.disableCapability(3089);
      }
   }

   public static int g() {
      return GuiRenderPrimitives.d() ? 0 : NativeBridge.gts();
   }

   public static boolean w(Color color) {
      float f = color.getRed() / 255.0F;
      float f2 = color.getGreen() / 255.0F;
      float f3 = color.getBlue() / 255.0F;
      float f4 = color.getAlpha() / 255.0F;
      boolean bl = f == l && f2 == I && f3 == O && Q == f4;
      OpenGlBackendHolder.backend.setColor(f, f2, f3, f4);
      GlStateManager.color(f, f2, f3, f4);
      l = f;
      I = f2;
      O = f3;
      Q = f4;
      return bl;
   }

   public static MousePosition h() {
      if (w == null) {
         C();
      }

      return w;
   }

   public static void R(int n, int n2, int n3, int n4, int n5, int n6) {
      float f = 0.00390625F;
      float f2 = 0.00390625F;
      GL11.glBegin(7);
      R(n, n2 + n6, 0.0, n3 * f, (n4 + n6) * f2);
      R(n + n5, n2 + n6, 0.0, (n3 + n5) * f, (n4 + n6) * f2);
      R(n + n5, n2, 0.0, (n3 + n5) * f, (n4 + 0) * f2);
      R(n, n2, 0.0, n3 * f, (n4 + 0) * f2);
      GL11.glEnd();
   }

   public static float[] d(int n) {
      float f = (n >> 24 & 0xFF) / 255.0F;
      float f2 = (n >> 16 & 0xFF) / 255.0F;
      float f3 = (n >> 8 & 0xFF) / 255.0F;
      float f4 = (n & 0xFF) / 255.0F;
      return new float[]{f2, f3, f4, f};
   }

   private static int[] Z(float[] fArray, float f) {
      int[] nArray = new int[2];
      int n = 0;

      while (n < fArray.length && fArray[n] <= f) {
         n++;
      }

      if (n >= fArray.length) {
         n = fArray.length - 1;
      }

      nArray[0] = n - 1;
      nArray[1] = n;
      return nArray;
   }

   public static void m(double d, double d2, double d3, double d4) {
      boolean bl = false;
      int n = 0;
      int n2 = 0;
      int n3 = 0;
      int n4 = 0;
      if (GL11.glIsEnabled(3089)) {
         bl = true;
         ((Buffer)g).clear();
         gg.vape.wrapper.impl.GL11.X(3088, g);
         n = g.get(0);
         n2 = g.get(1);
         n3 = g.get(2);
         n4 = g.get(3);
         P.push(new GlScissorRect(n, n2, n3, n4));
      } else {
         IntBuffer intBuffer = BufferUtils.createIntBuffer(16);
         gg.vape.wrapper.impl.GL11.X(3088, intBuffer);
         A = intBuffer.get(0);
         r = intBuffer.get(1);
         z = intBuffer.get(2);
         y = intBuffer.get(3);
         OpenGlBackendHolder.backend.enableCapability(3089);
      }

      int n5 = Minecraft.h();
      int n6 = Minecraft.J();
      double d5 = 2.0 * Vape.INSTANCE.getClientSettings().getGuiScaleFactor();
      int n7 = (int)(d3 * d5);
      int n8 = (int)(d4 * d5);
      double d6 = d * d5;
      int n9 = (int)Math.ceil(d6);
      double d7 = d2 * d5;
      int n10 = (int)Math.ceil(n5 - d7 - n8);
      if (bl) {
         if (n9 < n) {
            n9 = n;
         }

         int n13;
         int n14;
         if ((n14 = n9 + n7) > (n13 = n + n3)) {
            int n12 = n14 - n13;
            n7 -= n12;
            n7 = Math.max(0, n7);
         }

         if (n10 < n2) {
            n10 = n2;
         }

         int n11;
         int n12;
         if ((n12 = n10 + n8) > (n11 = n2 + n4)) {
            int n15 = n12 - n11;
            n8 -= n15;
            n8 = Math.max(0, n8);
         }
      }

      OpenGlBackendHolder.backend.setScissor(n9, n10, n7, n8);
   }

   public static void A(double d, double d2, double d3, double d4) {
      GL11.glBegin(7);
      GL11.glTexCoord2f(0.0F, 0.0F);
      GL11.glVertex3d(d, d4, 0.0);
      GL11.glTexCoord2f(1.0F, 0.0F);
      GL11.glVertex3d(d3, d4, 0.0);
      GL11.glTexCoord2f(1.0F, 1.0F);
      GL11.glVertex3d(d3, d2, 0.0);
      GL11.glTexCoord2f(0.0F, 1.0F);
      GL11.glVertex3d(d, d2, 0.0);
      GL11.glEnd();
   }

   public static void R(double d, double d2, double d3, double d4, double d5) {
      GL11.glTexCoord2d(d4, d5);
      GL11.glVertex3d(d, d2, 5.0);
   }

   public static void u() {
      if (!k.empty()) {
         GlScissorRect glScissorRect = k.pop();
         GL11.glViewport(glScissorRect.x, glScissorRect.y, glScissorRect.width, glScissorRect.height);
      }
   }

   public static Color S(float f) {
      return q(f, false);
   }

   private RenderUtils() {
   }

   static {
      j(null);
   }
}
