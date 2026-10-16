package gg.vape.utils.render;

import gg.vape.Vape;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.render.hud.FreeLookHudModule;
import gg.vape.runtime.NativeBridge;
import gg.vape.ui.theme.ThemeColors;
import gg.vape.unmap.GLUtils;
import gg.vape.utils.MutableColor;
import gg.vape.utils.render.shader.ArcShaderProgram;
import gg.vape.utils.render.shader.CircleShaderProgram;
import gg.vape.utils.render.shader.CompositeRoundedRectShaderProgram;
import gg.vape.utils.render.shader.LegacyDualRadiusRoundedRectShaderProgram;
import gg.vape.utils.render.shader.LegacyGradientRoundedRectShaderProgram;
import gg.vape.utils.render.shader.LegacyRoundedRectShaderProgram;
import gg.vape.utils.render.shader.RingShaderProgram;
import gg.vape.utils.render.shader.RoundedCornerMaskShaderProgram;
import gg.vape.utils.render.shader.RoundedRectBorderShaderProgram;
import gg.vape.utils.render.shader.RoundedRectShaderProgram;
import gg.vape.utils.render.shader.RoundedRectShadowShaderProgram;
import gg.vape.utils.render.shader.TexturedCircleShaderProgram;
import gg.vape.utils.render.shader.TexturedQuadShaderProgram;
import gg.vape.utils.render.shader.TexturedRoundedRectShaderProgram;
import gg.vape.utils.render.shader.VignetteShaderProgram;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GlStateManager;
import gg.vape.wrapper.impl.Item;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.Matrix4fHandle;
import gg.vape.wrapper.impl.MatrixStack;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.RenderHelper;
import gg.vape.wrapper.impl.RenderItem;
import gg.vape.wrapper.impl.RenderItemTextBridge;
import gg.vape.wrapper.impl.RenderManager;
import java.awt.Color;
import java.util.function.Supplier;
import org.lwjgl.opengl.GL11;

public class GuiRenderPrimitives {
   private static LegacyRoundedRectShaderProgram t;
   private static boolean f;
   private static LegacyGradientRoundedRectShaderProgram b;
   private static boolean s;
   private static TexturedCircleShaderProgram a;
   private static CircleShaderProgram V;
   private static RingShaderProgram B;
   private static GLUtils borderVertexBuffer = new GLUtils();
   private static TexturedRoundedRectShaderProgram h;
   public static CompositeRoundedRectShaderProgram G;
   private static ArcShaderProgram p;
   private static boolean D;
   public static TexturedQuadShaderProgram Y;
   public static boolean U = false;
   private static RoundedRectShadowShaderProgram l;
   private static VignetteShaderProgram k;
   private static RoundedRectBorderShaderProgram c;
   public static LegacyFontShaderProgram i;
   private static RoundedRectShaderProgram g;
   private static GLUtils quadVertexBuffer = new GLUtils();
   private static boolean T;
   private static boolean S;
   private static RoundedCornerMaskShaderProgram y;
   private static LegacyDualRadiusRoundedRectShaderProgram r;
   private static boolean Q;

   public static void W(double d, double d2, double d3, double d4, int n, boolean bl) {
      GlStateManager.bindTexture(n);
      if (d3 != 0.0 && d4 != 0.0) {
         double d5 = 0.0;
         double d6 = 0.0;
         double d7 = 1.0;
         double d8 = 1.0;
         if (d()) {
            RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder(VertexCoordinateMode.DEFAULT, bl)
               .setTexture(new GlImageTexture(n))
               .addTexturedRect((float)d, (float)d2, (float)d3, (float)d4, (float)d3, (float)d4, (float)d5, (float)d6, (float)d7, (float)d8, Color.WHITE);
            if (bl) {
               RenderBatchManager.getInstance().queueWorldBatch(renderBatchBuilder);
            } else {
               RenderBatchManager.getInstance().queueGuiBatch(renderBatchBuilder);
            }
         } else {
            L();
            GlStateManager.enableTexture2D();
            GL11.glBegin(7);
            GL11.glTexCoord2d(d7, d6);
            GL11.glVertex2d(d + d3, d2);
            GL11.glTexCoord2d(d5, d6);
            GL11.glVertex2d(d, d2);
            GL11.glTexCoord2d(d5, d8);
            GL11.glVertex2d(d, d2 + d4);
            GL11.glTexCoord2d(d7, d8);
            GL11.glVertex2d(d + d3, d2 + d4);
            GL11.glEnd();
            T();
         }
      }
   }

   public static void Y() {
      o(Minecraft.J(), Minecraft.h());
   }

   public static void a(double d, double d2, double d3, float f, Color color) {
      u(d, d2, d + d3, d2, f, color);
   }

   public static void V(double d, double d2, double d3, double d4, Color color) {
      Y((float)d, (float)d2, (float)d3, (float)d4, color, 0.0, null);
   }

   public static void j(double d, double d2, double d3, double d4, Color color) {
      if (!(d3 <= 0.0) && !(d4 <= 0.0)) {
         if (d()) {
            BufferedGuiRenderPrimitives.fillCapsule((float)d, (float)d2, (float)d3, (float)d4, color);
         } else {
            if (!t.isLinked()) {
               return;
            }

            float f = Math.max((float)d4 / 2.0F - 0.5F, 0.25F);
            float f2 = (float)d2 + (float)d4 / 2.0F;
            float f3 = (float)d + (float)d4 / 2.0F;
            float f4 = (float)d + (float)d3 - (float)d4 / 2.0F;
            float f5 = 0.5F;
            float f6 = (float)d - f5;
            float f7 = (float)d2 - f5;
            float f8 = (float)d + (float)d3 + f5;
            float f9 = (float)d2 + (float)d4 + f5;
            t.bind();
            L();
            t.setStart(f3, f2);
            t.setEnd(f4, f2);
            t.setRadius(f);
            Z(color);
            quadVertexBuffer.reset();
            quadVertexBuffer.addVertex2D(f6, f7);
            quadVertexBuffer.addVertex2D(f8, f7);
            quadVertexBuffer.addVertex2D(f8, f9);
            quadVertexBuffer.addVertex2D(f6, f9);
            quadVertexBuffer.uploadAndDraw();
            GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
            T();
            t.restorePreviousProgram();
         }
      }
   }

   public static void S(double d, double d2, double d3, double d4, int n, boolean bl) {
      GlStateManager.bindTexture(n);
      if (d3 != 0.0 && d4 != 0.0) {
         double d5 = 0.0;
         double d6 = 1.0;
         double d7 = 1.0;
         double d8 = 0.0;
         if (d()) {
            RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder(VertexCoordinateMode.DEFAULT, bl)
               .setTexture(new GlImageTexture(n))
               .addItemTextureRect((float)d, (float)d2, (float)d3, (float)d4, (float)d3, (float)d4, (float)d5, (float)d6, (float)d7, (float)d8, Color.WHITE);
            if (bl) {
               RenderBatchManager.getInstance().queueWorldBatch(renderBatchBuilder);
            } else {
               RenderBatchManager.getInstance().queueGuiBatch(renderBatchBuilder);
            }
         } else {
            L();
            GlStateManager.enableTexture2D();
            GL11.glBegin(7);
            GL11.glTexCoord2d(d7, d6);
            GL11.glVertex2d(d + d3, d2);
            GL11.glTexCoord2d(d5, d6);
            GL11.glVertex2d(d, d2);
            GL11.glTexCoord2d(d5, d8);
            GL11.glVertex2d(d, d2 + d4);
            GL11.glTexCoord2d(d7, d8);
            GL11.glVertex2d(d + d3, d2 + d4);
            GL11.glEnd();
            T();
         }
      }
   }

   public static void F(double d, double d2, double d3, double d4, float[] fArray, float[] fArray2) {
      if (!(d3 <= 0.0) && !(d4 <= 0.0)) {
         if (d()) {
            BufferedGuiRenderPrimitives.drawGradientPill((float)d, (float)d2, (float)d3, (float)d4, fArray, fArray2);
         } else {
            if (!b.isLinked()) {
               return;
            }

            float f = Math.max((float)d4 / 2.0F - 0.5F, 0.25F);
            float f2 = (float)d2 + (float)d4 / 2.0F;
            float f3 = (float)d + (float)d4 / 2.0F;
            float f4 = (float)d + (float)d3 - (float)d4 / 2.0F;
            float f5 = 0.5F;
            float f6 = (float)d - f5;
            float f7 = (float)d2 - f5;
            float f8 = (float)d + (float)d3 + f5;
            float f9 = (float)d2 + (float)d4 + f5;
            b.bind();
            L();
            b.setStart(f3, f2);
            b.setEnd(f4, f2);
            b.setRadius(f);
            b.setStartHsba(fArray[0], fArray[1], fArray[2], fArray[3]);
            b.setEndHsba(fArray2[0], fArray2[1], fArray2[2], fArray2[3]);
            quadVertexBuffer.reset();
            quadVertexBuffer.addVertex2D(f6, f7);
            quadVertexBuffer.addVertex2D(f8, f7);
            quadVertexBuffer.addVertex2D(f8, f9);
            quadVertexBuffer.addVertex2D(f6, f9);
            quadVertexBuffer.uploadAndDraw();
            T();
            b.restorePreviousProgram();
         }
      }
   }

   public static void L(double d, double d2, double d3, Color color) {
      for (double d4 = 0.0; d4 < d3; d4 += 2.0) {
         a(d + d4, d2, 1.0, 1.0F, color);
      }
   }

   public static boolean V() {
      if (!S) {
         D = MappedClasses.zb != null && MappedClasses.Y2 == null;
         S = true;
      }

      return D;
   }

   public static void o(int n, int n2) {
      if (!d()) {
         NativeBridge.rs(0, n, n2);
         prepareLegacyHudRendering();
      }

      OpenGlBackendHolder.backend.scale(2.0F, 2.0F, 2.0F);
   }

   private static void prepareLegacyHudRendering() {
      if (ForgeVersion.MC_1_8_9.L()) {
         GL11.glEnable(3553);
         GL11.glEnable(3008);
         GL11.glAlphaFunc(516, 0.1F);
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         GL11.glDisable(2896);
      }
   }

   public static void z(double d, double d2, double d3, double d4, double d5, double d6, Color color) {
      a((float)d, (float)d2, (float)d3, (float)d4, (float)d5, (float)d6, color);
   }

   public static void t(double d, double d2, double d3, double d4, Color color) {
      double d5 = 0.0;
      double d6 = 0.0;
      int n = (int)((d3 + d4) * 2.0 / 2.0);
      int n2 = 0;

      for (int i = 0; i < n; i++) {
         a(d + d5, d2 + d6, 1.0, 1.0F, color);
         if (n2 == 0) {
            if ((d5 += 2.0) >= d3) {
               double d7 = 0.0;
               if (d5 > d3) {
                  d7 = d5 - d3;
               }

               d5 = d3;
               d6 += d7;
               n2 = 1;
            }
         } else if (n2 == 1) {
            if ((d6 += 2.0) >= d4) {
               double d7 = 0.0;
               if (d6 > d4) {
                  d7 = d6 - d4;
               }

               d6 = d4;
               d5 -= d7;
               n2 = 2;
            }
         } else if (n2 == 2) {
            if ((d5 -= 2.0) <= 0.0) {
               double d7 = 0.0;
               if (d5 < d3) {
                  d7 = 0.0 - d5;
               }

               d5 = 0.0;
               d6 -= d7;
               n2 = 3;
            }
         } else if (n2 == 3 && (d6 -= 2.0) <= 0.0) {
            double d7 = 0.0;
            if (d6 < d4) {
               d7 = 0.0 - d6;
            }

            d6 = 0.0;
            d5 += d7;
            n2 = 0;
         }
      }
   }

   public static void h(String string, float f, float f2, float f3, float f4, Color color) {
      ImageRenderer.drawImage(color, f - f3 / 2.0F, f2 - f4 / 2.0F, string, f3, f4, false);
   }

   public static void a(double d, double d2, double d3, double d4, float f, float f2) {
      if (d3 != 0.0 && d4 != 0.0) {
         if (!d()) {
            float f3 = 0.5F;
            if (f == 0.0F) {
               f3 = 0.0F;
            }

            float var17;
            float f5 = Math.max(0.0F, (var17 = f + f3 * 2.0F) - f2);
            if (var17 != 0.0F) {
               d -= f2 - 0.5;
               d2 -= f2;
               d4 += f2 * 1.5;
               d3 += f2 * 1.0F;
            }

            h.bind();
            h.setRadius(f5);
            h.setInnerRect((float)d + var17, (float)d2 + var17, (float)(d + d3) - var17, (float)(d2 + d4) - var17);
            h.setSpread(f2);
            L();
            GlStateManager.enableTexture2D();
            float f6 = 0.0F;
            float f7 = 1.0F;
            float f8 = 1.0F;
            float f9 = 0.0F;
            GL11.glBegin(7);
            GL11.glTexCoord2f(f8, f7);
            GL11.glVertex2d(d + d3, d2);
            GL11.glTexCoord2f(f6, f7);
            GL11.glVertex2d(d, d2);
            GL11.glTexCoord2f(f6, f9);
            GL11.glVertex2d(d, d2 + d4);
            GL11.glTexCoord2f(f8, f9);
            GL11.glVertex2d(d + d3, d2 + d4);
            GL11.glEnd();
            T();
            h.restorePreviousProgram();
         }
      }
   }

   public static void A(double d, double d2, double d3, double d4, Color color, float f, float f2, int n) {
      if (d3 != 0.0 && d4 != 0.0) {
         if (d()) {
            BufferedGuiRenderPrimitives.drawInvertedRoundedRectCorners(d, d2, d3, d4, (double)f, (double)f2, n, color);
         } else if (y.isLinked()) {
            float f3 = 0.5F;
            if (f <= 0.0F) {
               f3 = 0.0F;
            }

            float var15;
            float f5 = Math.max(0.0F, (var15 = f + f3 * 2.0F) - f2);
            if (var15 > 0.0F) {
               d -= f2 - 0.5;
               d2 -= f2;
               d4 += f2 * 1.5;
               d3 += f2 * 1.0F;
            }

            y.bind();
            y.setRadius(f5);
            y.setInnerRect((float)d + var15, (float)d2 + var15, (float)(d + d3) - var15, (float)(d2 + d4) - var15);
            y.setSpread(f2);
            y.setCornerMask(n);
            L();
            Z(color);
            quadVertexBuffer.reset();
            quadVertexBuffer.addVertex2D((float)d + f3, (float)d2 + f3);
            quadVertexBuffer.addVertex2D((float)(d + d3 - f3), (float)d2 + f3);
            quadVertexBuffer.addVertex2D((float)(d + d3 - f3), (float)(d2 + d4 - f3));
            quadVertexBuffer.addVertex2D((float)d + f3, (float)(d2 + d4 - f3));
            quadVertexBuffer.uploadAndDraw();
            GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
            T();
            y.restorePreviousProgram();
         }
      }
   }

   public static void a(float f, float f2, float f3, float f4, float f5, float f6, Color color) {
      if (d()) {
         BufferedGuiRenderPrimitives.drawDottedLine(f, f2, f3, f4, f5, f6, color);
      } else if (r.isLinked()) {
         float f7 = f5 + f6;
         float f8 = Math.min(f, f3) - f7;
         float f9 = Math.max(f, f3) + f7;
         float f10 = Math.min(f2, f4) - f7;
         float f11 = Math.max(f2, f4) + f7;
         r.bind();
         L();
         r.setStart(f, f2);
         r.setEnd(f3, f4);
         r.setThickness(f5);
         r.setSpacing(f6);
         Z(color);
         quadVertexBuffer.reset();
         quadVertexBuffer.addVertex2D(f8, f10);
         quadVertexBuffer.addVertex2D(f9, f10);
         quadVertexBuffer.addVertex2D(f9, f11);
         quadVertexBuffer.addVertex2D(f8, f11);
         quadVertexBuffer.uploadAndDraw();
         GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
         T();
         r.restorePreviousProgram();
      }
   }

   public static void B(double d, double d2, double d3, double d4, Color color, float f) {
      e(d, d2, d3, d4, color, false, f, 1.0F);
   }

   public static void q(double d, double d2, double d3, double d4, double d5, Color color, Color color2) {
      if (d()) {
         BufferedGuiRenderPrimitives.fillBorderAdjustedRect(d, d2, d3, d4, d5, color, color2);
      } else if (color.equals(color2)) {
         d5 = 0.0;
         C(d, d2, d3 - d5, d4 - d5, color);
      } else {
         double var12;
         double var13;
         double var14;
         C(var12 = d - d5, d2, (var13 = d3 + d5) - d5, (var14 = d4 + d5) - d5, color);
         L();
         borderVertexBuffer.reset();
         Z(color2);
         borderVertexBuffer.addVertex2D((float)(var12 - d5), (float)(d2 - d5));
         borderVertexBuffer.addVertex2D((float)(var12 + var13), (float)(d2 - d5));
         borderVertexBuffer.addVertex2D((float)(var12 + var13), (float)d2);
         borderVertexBuffer.addVertex2D((float)(var12 - d5), (float)d2);
         borderVertexBuffer.addVertex2D((float)(var12 - d5), (float)(d2 + var14 - d5));
         borderVertexBuffer.addVertex2D((float)(var12 + var13), (float)(d2 + var14 - d5));
         borderVertexBuffer.addVertex2D((float)(var12 + var13), (float)(d2 + var14));
         borderVertexBuffer.addVertex2D((float)(var12 - d5), (float)(d2 + var14));
         borderVertexBuffer.addVertex2D((float)(var12 - d5), (float)d2);
         borderVertexBuffer.addVertex2D((float)var12, (float)d2);
         borderVertexBuffer.addVertex2D((float)var12, (float)(d2 + var14 - d5));
         borderVertexBuffer.addVertex2D((float)(var12 - d5), (float)(d2 + var14 - d5));
         borderVertexBuffer.addVertex2D((float)(var12 + var13 - d5), (float)d2);
         borderVertexBuffer.addVertex2D((float)(var12 + var13), (float)d2);
         borderVertexBuffer.addVertex2D((float)(var12 + var13), (float)(d2 + var14 - d5));
         borderVertexBuffer.addVertex2D((float)(var12 + var13 - d5), (float)(d2 + var14 - d5));
         borderVertexBuffer.uploadAndDraw();
         GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
         T();
      }
   }

   public static void F(double d, double d2, double d3) {
      if (!d()) {
         k.bind();
         k.setOuterRadius((float)d);
         k.setInnerRadius((float)d2);
         k.setIntensity((float)d3);
         k.setResolution(Minecraft.J() / 2.0F, Minecraft.h() / 2.0F);
         L();
         T();
         k.restorePreviousProgram();
      }
   }

   public static void p(float f, float f2, float f3, float f4, float f5, float f6, float f7, Color color) {
      if (d()) {
         BufferedGuiRenderPrimitives.drawArcStroke(f, f2, f3, f4, f5, f6, f7, color);
      } else {
         float var11;
         float f8 = (var11 = f3 + f5 * 4.0F) / 2.0F;
         p.bind();
         p.setRadiusAndThickness(f8 - f4 / 2.0F, f4 / 2.0F);
         float var9;
         float var10;
         p.setCenter((var9 = f - f5 * 2.0F) + f8, (var10 = f2 - f5 * 2.0F) + f8);
         p.setFeather(f5);
         p.setAngleRange(f6, f7);
         L();
         Z(color);
         quadVertexBuffer.reset();
         quadVertexBuffer.addVertex2D(var9, var10);
         quadVertexBuffer.addVertex2D(var9 + var11, var10);
         quadVertexBuffer.addVertex2D(var9 + var11, var10 + var11);
         quadVertexBuffer.addVertex2D(var9, var10 + var11);
         quadVertexBuffer.uploadAndDraw();
         GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
         T();
         p.restorePreviousProgram();
      }
   }

   public static void g(double d, double d2, double d3, double d4, float f, float f2, Color color) {
      if (d()) {
         BufferedGuiRenderPrimitives.drawRoundedRectShadow((float)d, (float)d2, (float)d3, (float)d4, f, f2, color);
      } else if (d3 != 0.0 && d4 != 0.0) {
         if (l.bind()) {
            l.setInnerRect((float)d, (float)(--d2), (float)d3, (float)(++d4));
            l.setSpread(f);
            l.setShadowColor(color);
            l.setCornerRadius(f2);
            L();
            quadVertexBuffer.reset();
            quadVertexBuffer.addVertex2D((float)d - f, (float)d2 - f);
            quadVertexBuffer.addVertex2D((float)(d + d3 + f), (float)d2 - f);
            quadVertexBuffer.addVertex2D((float)(d + d3 + f), (float)(d2 + d4 + f));
            quadVertexBuffer.addVertex2D((float)d - f, (float)(d2 + d4 + f));
            quadVertexBuffer.uploadAndDraw();
            GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
            T();
            l.restorePreviousProgram();
         }
      }
   }

   private static void T() {
      if (!d()) {
         if (!U) {
            GL11.glPopMatrix();
            if (T) {
               GlStateManager.enableTexture2D();
            }

            if (!Q) {
               GlStateManager.disableBlend();
            }

            if (f) {
               GlStateManager.L();
            }
         }
      }
   }

   public static void C(double d, double d2, double d3, double d4, Color color) {
      y((float)d, (float)d2, (float)d3, (float)d4, color);
   }

   private static Void lambda$drawItemSprite$0(double d, ItemStack itemStack, double d2, double d3) {
      double d4 = Vape.INSTANCE.getClientSettings().getGuiScaleFactor();
      double d5 = d * d4;
      float f = Minecraft.p().k(Minecraft.gameSettings().T(), false) / 2.0F;
      float f2 = 1.0F / f;
      MatrixStack matrixStack = MatrixStack.A();
      matrixStack.H();
      matrixStack.S(f2, f2, f2);
      matrixStack.s(d5, d5, d5);
      matrixStack.V(0.5, -0.5, 0.0);
      RenderItemTextBridge renderItemTextBridge;
      if (ForgeVersion.MC_1_21_6.d()) {
         Wrapper wrapper = Matrix4fHandle.b(16);
         ((Matrix4fHandle)wrapper).o();
         ((Matrix4fHandle)wrapper).a(f2, f2);
         ((Matrix4fHandle)wrapper).j(d5, d5);
         ((Matrix4fHandle)wrapper).z(0.5, -0.5);
         renderItemTextBridge = RenderItemTextBridge.l((Matrix4fHandle)wrapper);
      } else {
         renderItemTextBridge = RenderItemTextBridge.t(matrixStack);
      }

      if (ForgeVersion.MC_1_20_6.d()) {
         ItemStackRenderUtils.renderItemOverlay(renderItemTextBridge, itemStack, (int)(d2 / d5 * d4), (int)(d3 / d5 * d4));
      } else {
         Wrapper wrapper = Minecraft.v();
         ((RenderItem)wrapper).a(itemStack, (int)(d2 * f2), (int)(d3 * f2), matrixStack);
      }

      return null;
   }

   public static void l(String string) {
      if (!d()) {
         int n = GL11.glGetError();
      }
   }

   public static void B() {
      if (!d()) {
         quadVertexBuffer.configureVertexBuffer(8, 7, 2);
         borderVertexBuffer.configureVertexBuffer(40, 7, 2);
         g = new RoundedRectShaderProgram();
         l = new RoundedRectShadowShaderProgram();
         V = new CircleShaderProgram();
         B = new RingShaderProgram();
         p = new ArcShaderProgram();
         c = new RoundedRectBorderShaderProgram();
         Y = new TexturedQuadShaderProgram();
         h = new TexturedRoundedRectShaderProgram();
         k = new VignetteShaderProgram();
         a = new TexturedCircleShaderProgram();
         y = new RoundedCornerMaskShaderProgram();
         G = new CompositeRoundedRectShaderProgram();
         r = new LegacyDualRadiusRoundedRectShaderProgram();
         t = new LegacyRoundedRectShaderProgram();
         b = new LegacyGradientRoundedRectShaderProgram();
         i = new LegacyFontShaderProgram();
      }
   }

   public static boolean y() {
      boolean bl = j();
      return true;
   }

   public static void I(double d, double d2, double d3, double d4, Color color, boolean bl, float f, float f2, float f3, Color color2) {
      p(d, d2, d3, d4, color, bl, f, f2, f3, color2, 15);
   }

   public static void D() {
      L(Minecraft.J(), Minecraft.h());
   }

   public static void g(ItemStack itemStack, double d, double d2, double d3, boolean bl) {
      if (d()) {
         Supplier<Void> supplier = () -> lambda$drawItemSprite$0(d, itemStack, d2, d3);
         RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder(VertexCoordinateMode.MINECRAFT, false).setStandaloneRenderCallback(supplier);
         RenderBatchManager.getInstance().queueGuiBatch(renderBatchBuilder);
      } else {
         GL11.glPushMatrix();
         boolean bl2 = GL11.glIsEnabled(2929);
         boolean bl3 = GL11.glIsEnabled(3042);
         if (bl2 != bl) {
            if (bl) {
               GlStateManager.enableDepth();
            } else {
               GlStateManager.disableDepth();
            }
         }

         if (!bl3) {
            GlStateManager.enableBlend();
         }

         RenderItem renderItem = Minecraft.v();
         float f = renderItem.b();
         renderItem.z(110.0F);
         RenderHelper.s();
         RenderHelper.l();
         double d4 = 1.0 / d;
         GL11.glScaled(d, d, d);
         renderItem.c(Minecraft.getFontRenderer(), Minecraft.getTextureManager(), itemStack, (int)(d2 * d4), (int)(d3 * d4));
         GL11.glScaled(d4, d4, d4);
         RenderHelper.s();
         renderItem.z(f);
         if (bl2 != bl) {
            if (bl2) {
               GlStateManager.enableDepth();
            } else {
               GlStateManager.disableDepth();
            }
         }

         if (!bl3) {
            GlStateManager.disableBlend();
         }

         GL11.glPopMatrix();
      }
   }

   public static void d(double d, double d2, double d3, double d4, Color color) {
      e(d, d2, d3, d4, color, false, 1.5F, 1.0F);
   }

   public static void y(float f, float f2, float f3, float f4, Color color) {
      if (d()) {
         BufferedGuiRenderPrimitives.fillRect(f, f2, f3, f4, color);
      } else {
         L();
         Z(color);
         quadVertexBuffer.reset();
         quadVertexBuffer.addVertex2D(f, f2);
         quadVertexBuffer.addVertex2D(f + f3, f2);
         quadVertexBuffer.addVertex2D(f + f3, f2 + f4);
         quadVertexBuffer.addVertex2D(f, f2 + f4);
         quadVertexBuffer.uploadAndDraw();
         GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
         T();
      }
   }

   public static void v(double d, double d2, double d3, double d4, float f, float f2, float f3, int n) {
      if (d3 != 0.0 && d4 != 0.0) {
         if (Y.isLinked()) {
            Y.bind();
            Y.setBlurRadius(f);
            Y.configureTexture((float)d3, (float)d4);
            Y.setBlurDirection(n);
            L();
            GlStateManager.enableAlpha();
            GlStateManager.enableTexture2D();
            float f4 = 0.0F;
            float f5 = 1.0F;
            float f6 = 1.0F;
            float f7 = 0.0F;
            GL11.glBegin(7);
            GL11.glTexCoord2f(f6, f5);
            GL11.glVertex2d(d + d3, d2);
            GL11.glTexCoord2f(f4, f5);
            GL11.glVertex2d(d, d2);
            GL11.glTexCoord2f(f4, f7);
            GL11.glVertex2d(d, d2 + d4);
            GL11.glTexCoord2f(f6, f7);
            GL11.glVertex2d(d + d3, d2 + d4);
            GL11.glEnd();
            T();
            Y.restorePreviousProgram();
         }
      }
   }

   public static void u(double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8, Color color) {
      if (d()) {
         BufferedGuiRenderPrimitives.fillQuad(d, d2, d3, d4, d5, d6, d7, d8, color);
      } else {
         boolean bl = GL11.glIsEnabled(3042);
         boolean bl2 = GL11.glIsEnabled(3553);
         if (!bl) {
            GlStateManager.enableBlend();
         }

         if (bl2) {
            GlStateManager.disableTexture2D();
         }

         GL11.glBlendFunc(770, 771);
         OpenGlBackendHolder.backend.enableCapability(2881);
         GL11.glPushMatrix();
         GL11.glColor4d(color.getRed() / 255.0, color.getGreen() / 255.0, color.getBlue() / 255.0, color.getAlpha() / 255.0);
         GL11.glBegin(7);
         GL11.glVertex2d(d3, d4);
         GL11.glVertex2d(d, d2);
         GL11.glVertex2d(d7, d8);
         GL11.glVertex2d(d5, d6);
         GL11.glEnd();
         GL11.glPopMatrix();
         OpenGlBackendHolder.backend.disableCapability(2881);
         if (bl2) {
            GlStateManager.enableTexture2D();
         }

         if (!bl) {
            GlStateManager.disableBlend();
         }
      }
   }

   public static void n(double d, double d2, double d3, double d4) {
      if (d3 != 0.0 && d4 != 0.0) {
         if (d()) {
            float f = (float)d;
            float f2 = (float)d2 - 1.0F;
            float f3 = (float)d3;
            float f4 = (float)d4 + 1.0F;
            BufferedGuiRenderPrimitives.drawCompositeRoundedRect(
               f,
               f2,
               f3,
               f4,
               12.0F,
               6.0F,
               new Color(0, 0, 0, 150),
               0.0F,
               0.0F,
               1.0F,
               new Color(45, 45, 45, 255),
               new Color(0, 0, 0, 170),
               0.0F,
               0.0F,
               new Color(51, 51, 51, 255),
               false
            );
         } else {
            float f = 12.0F;
            G.setInnerRect((float)d, (float)(--d2), (float)d3, (float)(++d4));
            L();
            quadVertexBuffer.reset();
            quadVertexBuffer.addVertex2D((float)d - f, (float)d2 - f);
            quadVertexBuffer.addVertex2D((float)(d + d3 + f), (float)d2 - f);
            quadVertexBuffer.addVertex2D((float)(d + d3 + f), (float)(d2 + d4 + f));
            quadVertexBuffer.addVertex2D((float)d - f, (float)(d2 + d4 + f));
            quadVertexBuffer.uploadAndDraw();
            GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
            T();
         }
      }
   }

   public static void F(String string, double d, double d2, double d3, double d4, Color color) {
      h(string, (float)d, (float)d2, (float)d3, (float)d4, color);
   }

   public static void U(double d, double d2, double d3, double d4, double d5, double d6, Color color) {
      if (d()) {
         BufferedGuiRenderPrimitives.fillTriangle(d, d2, d3, d4, d5, d6, color);
      } else {
         L();
         GL11.glBlendFunc(770, 771);
         OpenGlBackendHolder.backend.enableCapability(2881);
         GL11.glPushMatrix();
         GL11.glColor4d(color.getRed() / 255.0, color.getGreen() / 255.0, color.getBlue() / 255.0, color.getAlpha() / 255.0);
         GL11.glBegin(6);
         GL11.glVertex2d(d3, d4);
         GL11.glVertex2d(d, d2);
         GL11.glVertex2d(d5, d6);
         GL11.glEnd();
         GL11.glPopMatrix();
         OpenGlBackendHolder.backend.disableCapability(2881);
         T();
      }
   }

   public static void e(double d, double d2, double d3, double d4, Color color, boolean bl, float f, float f2) {
      I(d, d2, d3, d4, color, bl, f, f2, 8.0F, ThemeColors.J.u);
   }

   public static void u(double d, double d2, double d3, double d4, float f, Color color) {
      if (d()) {
         BufferedGuiRenderPrimitives.drawLine((float)d, (float)d2, (float)d3, (float)d4, f, color);
      } else {
         boolean bl = GL11.glIsEnabled(3042);
         boolean bl2 = GL11.glIsEnabled(3553);
         if (!bl) {
            GlStateManager.enableBlend();
         }

         if (bl2) {
            GlStateManager.disableTexture2D();
         }

         GL11.glColor4d(color.getRed() / 255.0, color.getGreen() / 255.0, color.getBlue() / 255.0, color.getAlpha() / 255.0);
         GL11.glLineWidth(f);
         OpenGlBackendHolder.backend.enableCapability(2848);
         GL11.glBegin(3);
         GL11.glVertex2d(d, d2);
         GL11.glVertex2d(d3, d4);
         GL11.glEnd();
         if (bl2) {
            GlStateManager.enableTexture2D();
         }

         if (!bl) {
            GlStateManager.disableBlend();
         }
      }
   }

   public static void R(double d, double d2, double d3, float f, float f2, float f3, Color color) {
      if (d()) {
         BufferedRenderPrimitives.drawCylinderSides(d, d2, d3, f, f2, f3, color);
      } else {
         double d4 = RenderManager.getInterpolatedRenderPosX();
         double d5 = RenderManager.getInterpolatedRenderPosY();
         double d6 = RenderManager.getInterpolatedRenderPosZ();
         d -= d4;
         d2 -= d5;
         d3 -= d6;
         float f4 = FreeLookHudModule.isActive() ? FreeLookHudModule.getRenderPitch() - Minecraft.thePlayer().J() : 0.0F;
         if (!ForgeVersion.MC_1_16_5.d()) {
            OpenGlBackendHolder.backend.translate(d, d2, d3);
            OpenGlBackendHolder.backend.rotate(-f4, 0.0F, 1.0F, 0.0F);
         }

         RenderUtil.d();
         Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf().B(1.0);
         RenderUtils.g();
         OpenGlBackendHolder.backend.enableCapability(3042);
         GL11.glBlendFunc(770, 771);
         GlStateManager.disableTexture2D();
         GL11.glDepthMask(false);
         GlStateManager.Y();
         GL11.glShadeModel(7425);
         GL11.glBegin(8);

         for (int n = 0; n <= f; n++) {
            float f6 = (float)((Math.PI * 2) * n / f);
            float f7 = (float)(f2 * Math.cos(f6));
            float f8 = (float)(f2 * Math.sin(f6));
            RenderUtils.w(new MutableColor(color).withAlpha(0));
            GL11.glVertex3f(f7, f3, f8);
            RenderUtils.w(color);
            GL11.glVertex3f(f7, 0.0F, f8);
         }

         GL11.glEnd();
         GL11.glShadeModel(7424);
         GL11.glDepthMask(true);
         GlStateManager.enableTexture2D();
         OpenGlBackendHolder.backend.disableCapability(3042);
         RenderUtils.f();
         Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf().O(1.0);
         RenderUtil.Y();
         RenderUtils.w(Color.WHITE);
      }
   }

   public static boolean j() {
      return s;
   }

   public static boolean d() {
      return ForgeVersion.MC_1_17.d();
   }

   public static void p(double d, double d2, double d3, double d4, Color color, boolean bl, float f, float f2, float f3, Color color2, int n) {
      if (d3 != 0.0 && d4 != 0.0) {
         if (d()) {
            BufferedGuiRenderPrimitives.drawCornerMaskedRoundedRect((float)d, (float)d2, (float)d3, (float)d4, color, bl, f, f2, f3, color2, n);
         } else if (g.isLinked() && f != 0.0F) {
            if (bl) {
               g(d, d2 + 0.5, d3, d4 - 1.5, f3, f, color2);
            }

            float f4 = 0.5F;
            if (f <= 0.0F) {
               f4 = 0.0F;
            }

            float var18;
            float f6 = Math.max(0.0F, (var18 = f + f4 * 2.0F) - f2);
            if (var18 > 0.0F) {
               d -= f2 - 0.5;
               d2 -= f2;
               d4 += f2 * 1.0F;
               d3 += f2 * 1.0F;
            }

            g.bind();
            g.setRadius(f6);
            g.setInnerRect((float)d + var18, (float)d2 + var18, (float)(d + d3) - var18, (float)(d2 + d4) - var18);
            g.setSpread(f2);
            g.setCornerMask(n);
            L();
            Z(color);
            quadVertexBuffer.reset();
            quadVertexBuffer.addVertex2D((float)d + f4, (float)d2 + f4);
            quadVertexBuffer.addVertex2D((float)(d + d3 - f4), (float)d2 + f4);
            quadVertexBuffer.addVertex2D((float)(d + d3 - f4), (float)(d2 + d4 - f4));
            quadVertexBuffer.addVertex2D((float)d + f4, (float)(d2 + d4 - f4));
            quadVertexBuffer.uploadAndDraw();
            GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
            T();
            g.restorePreviousProgram();
         } else {
            C(d, d2, d3, d4, color);
         }
      }
   }

   public static void d(double d, double d2, double d3, float f, Color color) {
      u(d, d2, d, d2 + d3, f, color);
   }

   public static void E(boolean bl) {
      s = bl;
   }

   public static void Y(float f, float f2, float f3, float f4, Color color, double d, Color color2) {
      if (d()) {
         BufferedGuiRenderPrimitives.drawCircleWithCenterRect(f, f2, f3, f4, color, (float)d, color2);
      } else {
         f -= f4 / 2.0F;
         f2 -= f4 / 2.0F;
         float var14;
         float f5 = var14 = f3 + f4;
         if (color2 != null) {
            Z(color2);
            double d2 = f + f5 / 2.0F - d / 2.0;
            d(d2, (double)f2, d, (double)var14, color2);
         }

         V.bind();
         L();
         V.setRadius(var14 / 2.0F);
         V.setFeather(f4);
         V.setCenter(f + f5 / 2.0F, f2 + var14 / 2.0F);
         Z(color);
         quadVertexBuffer.reset();
         quadVertexBuffer.addVertex2D(f, f2);
         quadVertexBuffer.addVertex2D(f + f5, f2);
         quadVertexBuffer.addVertex2D(f + f5, f2 + var14);
         quadVertexBuffer.addVertex2D(f, f2 + var14);
         quadVertexBuffer.uploadAndDraw();
         GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
         T();
         V.restorePreviousProgram();
      }
   }

   public static void n(ItemStack itemStack, double d, double d2, double d3) {
      g(itemStack, d, d2, d3, false);
   }

   public static void d(int n, double d, double d2, double d3) {
      if (n >= 1) {
         n(ItemStack.S(Item.T(n)), d, d2, d3);
      }
   }

   public static void m(float f, float f2, float f3, float f4, float f5, Color color) {
      if (d()) {
         BufferedGuiRenderPrimitives.drawCircleStroke(f, f2, f3, f4, f5, color);
      } else {
         f -= f5 / 2.0F;
         f2 -= f5 / 2.0F;
         float var10;
         float f6 = var10 = f3 + f5;
         B.bind();
         B.setRadiusAndThickness(var10 / 2.0F, f4);
         B.setFeather(f5);
         B.setCenter(f + f6 / 2.0F, f2 + var10 / 2.0F);
         L();
         Z(color);
         quadVertexBuffer.reset();
         quadVertexBuffer.addVertex2D(f, f2);
         quadVertexBuffer.addVertex2D(f + f6, f2);
         quadVertexBuffer.addVertex2D(f + f6, f2 + var10);
         quadVertexBuffer.addVertex2D(f, f2 + var10);
         quadVertexBuffer.uploadAndDraw();
         GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
         T();
         B.restorePreviousProgram();
      }
   }

   public static void u(float f, float f2, float f3, float f4, Color color, GlImageTexture glImageTexture) {
      if (d()) {
         BufferedGuiRenderPrimitives.drawTexturedCircle(f, f2, f3, f4, color, glImageTexture);
      } else {
         glImageTexture.bind();
         f -= f4 / 2.0F;
         f2 -= f4 / 2.0F;
         float var14;
         float f5 = var14 = f3 + f4;
         if (color != null) {
            Z(color);
         }

         a.bind();
         L();
         a.setRadius(var14 / 2.0F);
         a.setFeather(f4);
         a.setCenter(f + f5 / 2.0F, f2 + var14 / 2.0F);
         GlStateManager.enableTexture2D();
         float f7 = 0.0F;
         float f8 = 0.0F;
         float f9 = 1.0F;
         float f10 = 1.0F;
         GL11.glBegin(7);
         GL11.glTexCoord2f(f9, f8);
         GL11.glVertex2f(f + f5, f2);
         GL11.glTexCoord2f(f7, f8);
         GL11.glVertex2f(f, f2);
         GL11.glTexCoord2f(f7, f10);
         GL11.glVertex2f(f, f2 + var14);
         GL11.glTexCoord2f(f9, f10);
         GL11.glVertex2f(f + f5, f2 + var14);
         GL11.glEnd();
         T();
         a.restorePreviousProgram();
      }
   }

   public static void P(double d, double d2, double d3, double d4, Color color, float f, float f2, float f3) {
      if (d()) {
         BufferedGuiRenderPrimitives.drawRoundedRect((float)d, (float)d2, (float)d3, (float)d4, color, f, f2, f3);
      } else {
         float f4 = 0.5F;
         c.bind();
         double var14;
         double var15;
         double var16;
         double var17;
         c.setInnerRect(
            (float)d + f + f2,
            (float)(var15 = d2 - f3) + f + f2,
            (float)((var14 = d - (f3 - 0.5)) + (var16 = d3 + f3 * 1.0F)) - (f + f2),
            (float)(var15 + (var17 = d4 + f3 * 1.5)) - (f + f2)
         );
         c.setRadiusParameters(f, f3, f2);
         L();
         Z(color);
         quadVertexBuffer.reset();
         quadVertexBuffer.addVertex2D((float)var14 + f4, (float)var15 + f4);
         quadVertexBuffer.addVertex2D((float)(var14 + var16 - f4), (float)var15 + f4);
         quadVertexBuffer.addVertex2D((float)(var14 + var16 - f4), (float)(var15 + var17 - f4));
         quadVertexBuffer.addVertex2D((float)var14 + f4, (float)(var15 + var17 - f4));
         quadVertexBuffer.uploadAndDraw();
         GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
         T();
         c.restorePreviousProgram();
      }
   }

   public static void L(int n, int n2) {
      if (!d()) {
         NativeBridge.rs(1, n, n2);
      }
   }

   private static void L() {
      if (!d()) {
         if (!U) {
            f = GL11.glIsEnabled(2884);
            Q = GL11.glIsEnabled(3042);
            T = GL11.glIsEnabled(3553);
            if (f) {
               GlStateManager.Y();
            }

            if (!Q) {
               GlStateManager.enableBlend();
            }

            if (T) {
               GlStateManager.disableTexture2D();
            }

            GL11.glBlendFunc(770, 771);
            GL11.glPushMatrix();
         }
      }
   }

   public static void Z(Color color) {
      OpenGlBackendHolder.backend.setColor(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
   }

   static {
      E(false);
   }
}
