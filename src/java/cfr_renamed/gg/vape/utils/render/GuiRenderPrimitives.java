/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.utils.render;

import gg.vape.Vape;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.render.hud.FreeLookHudModule;
import gg.vape.runtime.NativeBridge;
import gg.vape.ui.theme.ThemeColors;
import gg.vape.unmap.GLUtils;
import gg.vape.utils.MutableColor;
import gg.vape.utils.render.BufferedGuiRenderPrimitives;
import gg.vape.utils.render.BufferedRenderPrimitives;
import gg.vape.utils.render.GlImageTexture;
import gg.vape.utils.render.ImageRenderer;
import gg.vape.utils.render.ItemStackRenderUtils;
import gg.vape.utils.render.LegacyFontShaderProgram;
import gg.vape.utils.render.OpenGlBackendHolder;
import gg.vape.utils.render.RenderBatchBuilder;
import gg.vape.utils.render.RenderBatchManager;
import gg.vape.utils.render.RenderUtil;
import gg.vape.utils.render.RenderUtils;
import gg.vape.utils.render.VertexCoordinateMode;
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
    private static LegacyRoundedRectShaderProgram var_gg_vape_utils_render_shader_LegacyRoundedRectShaderProgram_t;
    private static boolean var_boolean_f;
    private static LegacyGradientRoundedRectShaderProgram var_gg_vape_utils_render_shader_LegacyGradientRoundedRectShaderProgram_b;
    private static boolean var_boolean_s;
    private static TexturedCircleShaderProgram var_gg_vape_utils_render_shader_TexturedCircleShaderProgram_a;
    private static CircleShaderProgram var_gg_vape_utils_render_shader_CircleShaderProgram_V;
    private static RingShaderProgram var_gg_vape_utils_render_shader_RingShaderProgram_B;
    private static GLUtils borderVertexBuffer;
    private static TexturedRoundedRectShaderProgram var_gg_vape_utils_render_shader_TexturedRoundedRectShaderProgram_h;
    public static CompositeRoundedRectShaderProgram var_gg_vape_utils_render_shader_CompositeRoundedRectShaderProgram_G;
    private static ArcShaderProgram var_gg_vape_utils_render_shader_ArcShaderProgram_p;
    private static boolean var_boolean_D;
    public static TexturedQuadShaderProgram var_gg_vape_utils_render_shader_TexturedQuadShaderProgram_Y;
    public static boolean var_boolean_U;
    private static RoundedRectShadowShaderProgram var_gg_vape_utils_render_shader_RoundedRectShadowShaderProgram_l;
    private static VignetteShaderProgram var_gg_vape_utils_render_shader_VignetteShaderProgram_k;
    private static RoundedRectBorderShaderProgram var_gg_vape_utils_render_shader_RoundedRectBorderShaderProgram_c;
    public static LegacyFontShaderProgram var_gg_vape_utils_render_LegacyFontShaderProgram_i;
    private static RoundedRectShaderProgram var_gg_vape_utils_render_shader_RoundedRectShaderProgram_g;
    private static GLUtils quadVertexBuffer;
    private static boolean var_boolean_T;
    private static boolean var_boolean_S;
    private static RoundedCornerMaskShaderProgram var_gg_vape_utils_render_shader_RoundedCornerMaskShaderProgram_y;
    private static LegacyDualRadiusRoundedRectShaderProgram var_gg_vape_utils_render_shader_LegacyDualRadiusRoundedRectShaderProgram_r;
    private static boolean var_boolean_Q;

    public static void W(double d, double d2, double d3, double d4, int n, boolean bl) {
        GlStateManager.bindTexture(n);
        if (d3 == 0.0 || d4 == 0.0) {
            return;
        }
        double d5 = 0.0;
        double d6 = 0.0;
        double d7 = 1.0;
        double d8 = 1.0;
        if (GuiRenderPrimitives.d()) {
            RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder(VertexCoordinateMode.DEFAULT, bl).setTexture(new GlImageTexture(n)).addTexturedRect((float)d, (float)d2, (float)d3, (float)d4, (float)d3, (float)d4, (float)d5, (float)d6, (float)d7, (float)d8, Color.WHITE);
            if (bl) {
                RenderBatchManager.getInstance().queueWorldBatch(renderBatchBuilder);
            } else {
                RenderBatchManager.getInstance().queueGuiBatch(renderBatchBuilder);
            }
            return;
        }
        GuiRenderPrimitives.L();
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
        GuiRenderPrimitives.T();
    }

    public static void Y() {
        GuiRenderPrimitives.o(Minecraft.J(), Minecraft.h());
    }

    public static void a(double d, double d2, double d3, float f, Color color) {
        GuiRenderPrimitives.u(d, d2, d + d3, d2, f, color);
    }

    public static void V(double d, double d2, double d3, double d4, Color color) {
        GuiRenderPrimitives.Y((float)d, (float)d2, (float)d3, (float)d4, color, 0.0, null);
    }

    public static void j(double d, double d2, double d3, double d4, Color color) {
        if (d3 <= 0.0 || d4 <= 0.0) {
            return;
        }
        if (GuiRenderPrimitives.d()) {
            BufferedGuiRenderPrimitives.fillCapsule((float)d, (float)d2, (float)d3, (float)d4, color);
        } else {
            if (!var_gg_vape_utils_render_shader_LegacyRoundedRectShaderProgram_t.isLinked()) {
                return;
            }
            float f = Math.max((float)d4 / 2.0f - 0.5f, 0.25f);
            float f2 = (float)d2 + (float)d4 / 2.0f;
            float f3 = (float)d + (float)d4 / 2.0f;
            float f4 = (float)d + (float)d3 - (float)d4 / 2.0f;
            float f5 = 0.5f;
            float f6 = (float)d - f5;
            float f7 = (float)d2 - f5;
            float f8 = (float)d + (float)d3 + f5;
            float f9 = (float)d2 + (float)d4 + f5;
            var_gg_vape_utils_render_shader_LegacyRoundedRectShaderProgram_t.bind();
            GuiRenderPrimitives.L();
            var_gg_vape_utils_render_shader_LegacyRoundedRectShaderProgram_t.setStart(f3, f2);
            var_gg_vape_utils_render_shader_LegacyRoundedRectShaderProgram_t.setEnd(f4, f2);
            var_gg_vape_utils_render_shader_LegacyRoundedRectShaderProgram_t.setRadius(f);
            GuiRenderPrimitives.Z(color);
            quadVertexBuffer.reset();
            quadVertexBuffer.addVertex2D(f6, f7);
            quadVertexBuffer.addVertex2D(f8, f7);
            quadVertexBuffer.addVertex2D(f8, f9);
            quadVertexBuffer.addVertex2D(f6, f9);
            quadVertexBuffer.uploadAndDraw();
            GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
            GuiRenderPrimitives.T();
            var_gg_vape_utils_render_shader_LegacyRoundedRectShaderProgram_t.restorePreviousProgram();
        }
    }

    public static void S(double d, double d2, double d3, double d4, int n, boolean bl) {
        GlStateManager.bindTexture(n);
        if (d3 == 0.0 || d4 == 0.0) {
            return;
        }
        double d5 = 0.0;
        double d6 = 1.0;
        double d7 = 1.0;
        double d8 = 0.0;
        if (GuiRenderPrimitives.d()) {
            RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder(VertexCoordinateMode.DEFAULT, bl).setTexture(new GlImageTexture(n)).addItemTextureRect((float)d, (float)d2, (float)d3, (float)d4, (float)d3, (float)d4, (float)d5, (float)d6, (float)d7, (float)d8, Color.WHITE);
            if (bl) {
                RenderBatchManager.getInstance().queueWorldBatch(renderBatchBuilder);
            } else {
                RenderBatchManager.getInstance().queueGuiBatch(renderBatchBuilder);
            }
            return;
        }
        GuiRenderPrimitives.L();
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
        GuiRenderPrimitives.T();
    }

    public static void F(double d, double d2, double d3, double d4, float[] fArray, float[] fArray2) {
        if (d3 <= 0.0 || d4 <= 0.0) {
            return;
        }
        if (GuiRenderPrimitives.d()) {
            BufferedGuiRenderPrimitives.drawGradientPill((float)d, (float)d2, (float)d3, (float)d4, fArray, fArray2);
        } else {
            if (!var_gg_vape_utils_render_shader_LegacyGradientRoundedRectShaderProgram_b.isLinked()) {
                return;
            }
            float f = Math.max((float)d4 / 2.0f - 0.5f, 0.25f);
            float f2 = (float)d2 + (float)d4 / 2.0f;
            float f3 = (float)d + (float)d4 / 2.0f;
            float f4 = (float)d + (float)d3 - (float)d4 / 2.0f;
            float f5 = 0.5f;
            float f6 = (float)d - f5;
            float f7 = (float)d2 - f5;
            float f8 = (float)d + (float)d3 + f5;
            float f9 = (float)d2 + (float)d4 + f5;
            var_gg_vape_utils_render_shader_LegacyGradientRoundedRectShaderProgram_b.bind();
            GuiRenderPrimitives.L();
            var_gg_vape_utils_render_shader_LegacyGradientRoundedRectShaderProgram_b.setStart(f3, f2);
            var_gg_vape_utils_render_shader_LegacyGradientRoundedRectShaderProgram_b.setEnd(f4, f2);
            var_gg_vape_utils_render_shader_LegacyGradientRoundedRectShaderProgram_b.setRadius(f);
            var_gg_vape_utils_render_shader_LegacyGradientRoundedRectShaderProgram_b.setStartHsba(fArray[0], fArray[1], fArray[2], fArray[3]);
            var_gg_vape_utils_render_shader_LegacyGradientRoundedRectShaderProgram_b.setEndHsba(fArray2[0], fArray2[1], fArray2[2], fArray2[3]);
            quadVertexBuffer.reset();
            quadVertexBuffer.addVertex2D(f6, f7);
            quadVertexBuffer.addVertex2D(f8, f7);
            quadVertexBuffer.addVertex2D(f8, f9);
            quadVertexBuffer.addVertex2D(f6, f9);
            quadVertexBuffer.uploadAndDraw();
            GuiRenderPrimitives.T();
            var_gg_vape_utils_render_shader_LegacyGradientRoundedRectShaderProgram_b.restorePreviousProgram();
        }
    }

    public static void L(double d, double d2, double d3, Color color) {
        for (double d4 = 0.0; d4 < d3; d4 += 2.0) {
            GuiRenderPrimitives.a(d + d4, d2, 1.0, 1.0f, color);
        }
    }

    public static boolean V() {
        if (!var_boolean_S) {
            var_boolean_D = MappedClasses.zb != null && MappedClasses.Y2 == null;
            var_boolean_S = true;
        }
        return var_boolean_D;
    }

    public static void o(int n, int n2) {
        if (!GuiRenderPrimitives.d()) {
            NativeBridge.rs(0, n, n2);
            GuiRenderPrimitives.prepareLegacyHudRendering();
        }
        OpenGlBackendHolder.backend.scale(2.0f, 2.0f, 2.0f);
    }

    private static void prepareLegacyHudRendering() {
        if (!ForgeVersion.MC_1_8_9.L()) {
            return;
        }
        GL11.glEnable(3553);
        GL11.glEnable(3008);
        GL11.glAlphaFunc(516, 0.1f);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(2896);
    }

    public static void z(double d, double d2, double d3, double d4, double d5, double d6, Color color) {
        GuiRenderPrimitives.a((float)d, (float)d2, (float)d3, (float)d4, (float)d5, (float)d6, color);
    }

    public static void t(double d, double d2, double d3, double d4, Color color) {
        double d5 = 0.0;
        double d6 = 0.0;
        int n = (int)((d3 + d4) * 2.0 / 2.0);
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            double d7;
            GuiRenderPrimitives.a(d + d5, d2 + d6, 1.0, 1.0f, color);
            if (n2 == 0) {
                if (!((d5 += 2.0) >= d3)) continue;
                d7 = 0.0;
                if (d5 > d3) {
                    d7 = d5 - d3;
                }
                d5 = d3;
                d6 += d7;
                n2 = 1;
                continue;
            }
            if (n2 == 1) {
                if (!((d6 += 2.0) >= d4)) continue;
                d7 = 0.0;
                if (d6 > d4) {
                    d7 = d6 - d4;
                }
                d6 = d4;
                d5 -= d7;
                n2 = 2;
                continue;
            }
            if (n2 == 2) {
                if (!((d5 -= 2.0) <= 0.0)) continue;
                d7 = 0.0;
                if (d5 < d3) {
                    d7 = 0.0 - d5;
                }
                d5 = 0.0;
                d6 -= d7;
                n2 = 3;
                continue;
            }
            if (n2 != 3 || !((d6 -= 2.0) <= 0.0)) continue;
            d7 = 0.0;
            if (d6 < d4) {
                d7 = 0.0 - d6;
            }
            d6 = 0.0;
            d5 += d7;
            n2 = 0;
        }
    }

    public static void h(String string, float f, float f2, float f3, float f4, Color color) {
        ImageRenderer.drawImage(color, f - f3 / 2.0f, f2 - f4 / 2.0f, string, f3, f4, false);
    }

    public static void a(double d, double d2, double d3, double d4, float f, float f2) {
        if (d3 == 0.0 || d4 == 0.0) {
            return;
        }
        if (GuiRenderPrimitives.d()) {
            return;
        }
        float f3 = 0.5f;
        if (f == 0.0f) {
            f3 = 0.0f;
        }
        float f4 = f2;
        float f5 = Math.max(0.0f, (f += f3 * 2.0f) - f4);
        if (f != 0.0f) {
            d -= (double)f4 - 0.5;
            d2 -= (double)f4;
            d4 += (double)f4 * 1.5;
            d3 += (double)(f4 * 1.0f);
        }
        var_gg_vape_utils_render_shader_TexturedRoundedRectShaderProgram_h.bind();
        var_gg_vape_utils_render_shader_TexturedRoundedRectShaderProgram_h.setRadius(f5);
        var_gg_vape_utils_render_shader_TexturedRoundedRectShaderProgram_h.setInnerRect((float)d + f, (float)d2 + f, (float)(d + d3) - f, (float)(d2 + d4) - f);
        var_gg_vape_utils_render_shader_TexturedRoundedRectShaderProgram_h.setSpread(f4);
        GuiRenderPrimitives.L();
        GlStateManager.enableTexture2D();
        float f6 = 0.0f;
        float f7 = 1.0f;
        float f8 = 1.0f;
        float f9 = 0.0f;
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
        GuiRenderPrimitives.T();
        var_gg_vape_utils_render_shader_TexturedRoundedRectShaderProgram_h.restorePreviousProgram();
    }

    public static void A(double d, double d2, double d3, double d4, Color color, float f, float f2, int n) {
        if (d3 == 0.0 || d4 == 0.0) {
            return;
        }
        if (GuiRenderPrimitives.d()) {
            BufferedGuiRenderPrimitives.drawInvertedRoundedRectCorners(d, d2, d3, d4, (double)f, (double)f2, n, color);
            return;
        }
        if (!var_gg_vape_utils_render_shader_RoundedCornerMaskShaderProgram_y.isLinked()) {
            return;
        }
        float f3 = 0.5f;
        if (f <= 0.0f) {
            f3 = 0.0f;
        }
        float f4 = f2;
        float f5 = Math.max(0.0f, (f += f3 * 2.0f) - f4);
        if (f > 0.0f) {
            d -= (double)f4 - 0.5;
            d2 -= (double)f4;
            d4 += (double)f4 * 1.5;
            d3 += (double)(f4 * 1.0f);
        }
        var_gg_vape_utils_render_shader_RoundedCornerMaskShaderProgram_y.bind();
        var_gg_vape_utils_render_shader_RoundedCornerMaskShaderProgram_y.setRadius(f5);
        var_gg_vape_utils_render_shader_RoundedCornerMaskShaderProgram_y.setInnerRect((float)d + f, (float)d2 + f, (float)(d + d3) - f, (float)(d2 + d4) - f);
        var_gg_vape_utils_render_shader_RoundedCornerMaskShaderProgram_y.setSpread(f4);
        var_gg_vape_utils_render_shader_RoundedCornerMaskShaderProgram_y.setCornerMask(n);
        GuiRenderPrimitives.L();
        GuiRenderPrimitives.Z(color);
        quadVertexBuffer.reset();
        quadVertexBuffer.addVertex2D((float)d + f3, (float)d2 + f3);
        quadVertexBuffer.addVertex2D((float)(d + d3 - (double)f3), (float)d2 + f3);
        quadVertexBuffer.addVertex2D((float)(d + d3 - (double)f3), (float)(d2 + d4 - (double)f3));
        quadVertexBuffer.addVertex2D((float)d + f3, (float)(d2 + d4 - (double)f3));
        quadVertexBuffer.uploadAndDraw();
        GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
        GuiRenderPrimitives.T();
        var_gg_vape_utils_render_shader_RoundedCornerMaskShaderProgram_y.restorePreviousProgram();
    }

    public static void a(float f, float f2, float f3, float f4, float f5, float f6, Color color) {
        if (GuiRenderPrimitives.d()) {
            BufferedGuiRenderPrimitives.drawDottedLine(f, f2, f3, f4, f5, f6, color);
            return;
        }
        if (!var_gg_vape_utils_render_shader_LegacyDualRadiusRoundedRectShaderProgram_r.isLinked()) {
            return;
        }
        float f7 = f5 + f6;
        float f8 = Math.min(f, f3) - f7;
        float f9 = Math.max(f, f3) + f7;
        float f10 = Math.min(f2, f4) - f7;
        float f11 = Math.max(f2, f4) + f7;
        var_gg_vape_utils_render_shader_LegacyDualRadiusRoundedRectShaderProgram_r.bind();
        GuiRenderPrimitives.L();
        var_gg_vape_utils_render_shader_LegacyDualRadiusRoundedRectShaderProgram_r.setStart(f, f2);
        var_gg_vape_utils_render_shader_LegacyDualRadiusRoundedRectShaderProgram_r.setEnd(f3, f4);
        var_gg_vape_utils_render_shader_LegacyDualRadiusRoundedRectShaderProgram_r.setThickness(f5);
        var_gg_vape_utils_render_shader_LegacyDualRadiusRoundedRectShaderProgram_r.setSpacing(f6);
        GuiRenderPrimitives.Z(color);
        quadVertexBuffer.reset();
        quadVertexBuffer.addVertex2D(f8, f10);
        quadVertexBuffer.addVertex2D(f9, f10);
        quadVertexBuffer.addVertex2D(f9, f11);
        quadVertexBuffer.addVertex2D(f8, f11);
        quadVertexBuffer.uploadAndDraw();
        GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
        GuiRenderPrimitives.T();
        var_gg_vape_utils_render_shader_LegacyDualRadiusRoundedRectShaderProgram_r.restorePreviousProgram();
    }

    public static void B(double d, double d2, double d3, double d4, Color color, float f) {
        GuiRenderPrimitives.e(d, d2, d3, d4, color, false, f, 1.0f);
    }

    public static void q(double d, double d2, double d3, double d4, double d5, Color color, Color color2) {
        if (GuiRenderPrimitives.d()) {
            BufferedGuiRenderPrimitives.fillBorderAdjustedRect(d, d2, d3, d4, d5, color, color2);
            return;
        }
        if (color.equals(color2)) {
            d5 = 0.0;
            GuiRenderPrimitives.C(d, d2, d3 - d5, d4 - d5, color);
            return;
        }
        GuiRenderPrimitives.C(d -= d5, d2, (d3 += d5) - d5, (d4 += d5) - d5, color);
        GuiRenderPrimitives.L();
        borderVertexBuffer.reset();
        GuiRenderPrimitives.Z(color2);
        borderVertexBuffer.addVertex2D((float)(d - d5), (float)(d2 - d5));
        borderVertexBuffer.addVertex2D((float)(d + d3), (float)(d2 - d5));
        borderVertexBuffer.addVertex2D((float)(d + d3), (float)d2);
        borderVertexBuffer.addVertex2D((float)(d - d5), (float)d2);
        borderVertexBuffer.addVertex2D((float)(d - d5), (float)(d2 + d4 - d5));
        borderVertexBuffer.addVertex2D((float)(d + d3), (float)(d2 + d4 - d5));
        borderVertexBuffer.addVertex2D((float)(d + d3), (float)(d2 + d4));
        borderVertexBuffer.addVertex2D((float)(d - d5), (float)(d2 + d4));
        borderVertexBuffer.addVertex2D((float)(d - d5), (float)d2);
        borderVertexBuffer.addVertex2D((float)d, (float)d2);
        borderVertexBuffer.addVertex2D((float)d, (float)(d2 + d4 - d5));
        borderVertexBuffer.addVertex2D((float)(d - d5), (float)(d2 + d4 - d5));
        borderVertexBuffer.addVertex2D((float)(d + d3 - d5), (float)d2);
        borderVertexBuffer.addVertex2D((float)(d + d3), (float)d2);
        borderVertexBuffer.addVertex2D((float)(d + d3), (float)(d2 + d4 - d5));
        borderVertexBuffer.addVertex2D((float)(d + d3 - d5), (float)(d2 + d4 - d5));
        borderVertexBuffer.uploadAndDraw();
        GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
        GuiRenderPrimitives.T();
    }

    public static void F(double d, double d2, double d3) {
        if (GuiRenderPrimitives.d()) {
            return;
        }
        var_gg_vape_utils_render_shader_VignetteShaderProgram_k.bind();
        var_gg_vape_utils_render_shader_VignetteShaderProgram_k.setOuterRadius((float)d);
        var_gg_vape_utils_render_shader_VignetteShaderProgram_k.setInnerRadius((float)d2);
        var_gg_vape_utils_render_shader_VignetteShaderProgram_k.setIntensity((float)d3);
        var_gg_vape_utils_render_shader_VignetteShaderProgram_k.setResolution((float)Minecraft.J() / 2.0f, (float)Minecraft.h() / 2.0f);
        GuiRenderPrimitives.L();
        GuiRenderPrimitives.T();
        var_gg_vape_utils_render_shader_VignetteShaderProgram_k.restorePreviousProgram();
    }

    public static void p(float f, float f2, float f3, float f4, float f5, float f6, float f7, Color color) {
        if (GuiRenderPrimitives.d()) {
            BufferedGuiRenderPrimitives.drawArcStroke(f, f2, f3, f4, f5, f6, f7, color);
            return;
        }
        float f8 = (f3 += f5 * 4.0f) / 2.0f;
        var_gg_vape_utils_render_shader_ArcShaderProgram_p.bind();
        var_gg_vape_utils_render_shader_ArcShaderProgram_p.setRadiusAndThickness(f8 - f4 / 2.0f, f4 / 2.0f);
        var_gg_vape_utils_render_shader_ArcShaderProgram_p.setCenter((f -= f5 * 2.0f) + f8, (f2 -= f5 * 2.0f) + f8);
        var_gg_vape_utils_render_shader_ArcShaderProgram_p.setFeather(f5);
        var_gg_vape_utils_render_shader_ArcShaderProgram_p.setAngleRange(f6, f7);
        GuiRenderPrimitives.L();
        GuiRenderPrimitives.Z(color);
        quadVertexBuffer.reset();
        quadVertexBuffer.addVertex2D(f, f2);
        quadVertexBuffer.addVertex2D(f + f3, f2);
        quadVertexBuffer.addVertex2D(f + f3, f2 + f3);
        quadVertexBuffer.addVertex2D(f, f2 + f3);
        quadVertexBuffer.uploadAndDraw();
        GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
        GuiRenderPrimitives.T();
        var_gg_vape_utils_render_shader_ArcShaderProgram_p.restorePreviousProgram();
    }

    public static void g(double d, double d2, double d3, double d4, float f, float f2, Color color) {
        if (GuiRenderPrimitives.d()) {
            BufferedGuiRenderPrimitives.drawRoundedRectShadow((float)d, (float)d2, (float)d3, (float)d4, f, f2, color);
            return;
        }
        if (d3 == 0.0 || d4 == 0.0) {
            return;
        }
        if (!var_gg_vape_utils_render_shader_RoundedRectShadowShaderProgram_l.bind()) {
            return;
        }
        var_gg_vape_utils_render_shader_RoundedRectShadowShaderProgram_l.setInnerRect((float)d, (float)(d2 -= 1.0), (float)d3, (float)(d4 += 1.0));
        var_gg_vape_utils_render_shader_RoundedRectShadowShaderProgram_l.setSpread(f);
        var_gg_vape_utils_render_shader_RoundedRectShadowShaderProgram_l.setShadowColor(color);
        var_gg_vape_utils_render_shader_RoundedRectShadowShaderProgram_l.setCornerRadius(f2);
        GuiRenderPrimitives.L();
        quadVertexBuffer.reset();
        quadVertexBuffer.addVertex2D((float)d - f, (float)d2 - f);
        quadVertexBuffer.addVertex2D((float)(d + d3 + (double)f), (float)d2 - f);
        quadVertexBuffer.addVertex2D((float)(d + d3 + (double)f), (float)(d2 + d4 + (double)f));
        quadVertexBuffer.addVertex2D((float)d - f, (float)(d2 + d4 + (double)f));
        quadVertexBuffer.uploadAndDraw();
        GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
        GuiRenderPrimitives.T();
        var_gg_vape_utils_render_shader_RoundedRectShadowShaderProgram_l.restorePreviousProgram();
    }

    private static void T() {
        if (GuiRenderPrimitives.d()) {
            return;
        }
        if (var_boolean_U) {
            return;
        }
        GL11.glPopMatrix();
        if (var_boolean_T) {
            GlStateManager.enableTexture2D();
        }
        if (!var_boolean_Q) {
            GlStateManager.disableBlend();
        }
        if (var_boolean_f) {
            GlStateManager.L();
        }
    }

    public static void C(double d, double d2, double d3, double d4, Color color) {
        GuiRenderPrimitives.y((float)d, (float)d2, (float)d3, (float)d4, color);
    }

    private static Void lambda$drawItemSprite$0(double d, ItemStack itemStack, double d2, double d3) {
        RenderItemTextBridge renderItemTextBridge;
        Wrapper wrapper;
        double d4 = Vape.INSTANCE.getClientSettings().getGuiScaleFactor();
        double d5 = d * d4;
        float f = (float)Minecraft.p().k(Minecraft.gameSettings().T(), false) / 2.0f;
        float f2 = 1.0f / f;
        MatrixStack matrixStack = MatrixStack.A();
        matrixStack.H();
        matrixStack.S(f2, f2, f2);
        matrixStack.s(d5, d5, d5);
        matrixStack.V(0.5, -0.5, 0.0);
        if (ForgeVersion.MC_1_21_6.d()) {
            wrapper = Matrix4fHandle.b(16);
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
            wrapper = Minecraft.v();
            ((RenderItem)wrapper).a(itemStack, (int)(d2 * (double)f2), (int)(d3 * (double)f2), matrixStack);
        }
        return null;
    }

    public static void l(String string) {
        if (GuiRenderPrimitives.d()) {
            return;
        }
        int n = GL11.glGetError();
    }

    public static void B() {
        if (!GuiRenderPrimitives.d()) {
            quadVertexBuffer.configureVertexBuffer(8, 7, 2);
            borderVertexBuffer.configureVertexBuffer(40, 7, 2);
            var_gg_vape_utils_render_shader_RoundedRectShaderProgram_g = new RoundedRectShaderProgram();
            var_gg_vape_utils_render_shader_RoundedRectShadowShaderProgram_l = new RoundedRectShadowShaderProgram();
            var_gg_vape_utils_render_shader_CircleShaderProgram_V = new CircleShaderProgram();
            var_gg_vape_utils_render_shader_RingShaderProgram_B = new RingShaderProgram();
            var_gg_vape_utils_render_shader_ArcShaderProgram_p = new ArcShaderProgram();
            var_gg_vape_utils_render_shader_RoundedRectBorderShaderProgram_c = new RoundedRectBorderShaderProgram();
            var_gg_vape_utils_render_shader_TexturedQuadShaderProgram_Y = new TexturedQuadShaderProgram();
            var_gg_vape_utils_render_shader_TexturedRoundedRectShaderProgram_h = new TexturedRoundedRectShaderProgram();
            var_gg_vape_utils_render_shader_VignetteShaderProgram_k = new VignetteShaderProgram();
            var_gg_vape_utils_render_shader_TexturedCircleShaderProgram_a = new TexturedCircleShaderProgram();
            var_gg_vape_utils_render_shader_RoundedCornerMaskShaderProgram_y = new RoundedCornerMaskShaderProgram();
            var_gg_vape_utils_render_shader_CompositeRoundedRectShaderProgram_G = new CompositeRoundedRectShaderProgram();
            var_gg_vape_utils_render_shader_LegacyDualRadiusRoundedRectShaderProgram_r = new LegacyDualRadiusRoundedRectShaderProgram();
            var_gg_vape_utils_render_shader_LegacyRoundedRectShaderProgram_t = new LegacyRoundedRectShaderProgram();
            var_gg_vape_utils_render_shader_LegacyGradientRoundedRectShaderProgram_b = new LegacyGradientRoundedRectShaderProgram();
            var_gg_vape_utils_render_LegacyFontShaderProgram_i = new LegacyFontShaderProgram();
        }
    }

    public static boolean y() {
        boolean bl = GuiRenderPrimitives.j();
        return true;
    }

    public static void I(double d, double d2, double d3, double d4, Color color, boolean bl, float f, float f2, float f3, Color color2) {
        GuiRenderPrimitives.p(d, d2, d3, d4, color, bl, f, f2, f3, color2, 15);
    }

    public static void D() {
        GuiRenderPrimitives.L(Minecraft.J(), Minecraft.h());
    }

    public static void g(ItemStack itemStack, double d, double d2, double d3, boolean bl) {
        if (GuiRenderPrimitives.d()) {
            Supplier<Void> supplier = () -> GuiRenderPrimitives.lambda$drawItemSprite$0(d, itemStack, d2, d3);
            RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder(VertexCoordinateMode.MINECRAFT, false).setStandaloneRenderCallback(supplier);
            RenderBatchManager.getInstance().queueGuiBatch(renderBatchBuilder);
            return;
        }
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
        renderItem.z(110.0f);
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

    public static void d(double d, double d2, double d3, double d4, Color color) {
        GuiRenderPrimitives.e(d, d2, d3, d4, color, false, 1.5f, 1.0f);
    }

    public static void y(float f, float f2, float f3, float f4, Color color) {
        if (GuiRenderPrimitives.d()) {
            BufferedGuiRenderPrimitives.fillRect(f, f2, f3, f4, color);
            return;
        }
        GuiRenderPrimitives.L();
        GuiRenderPrimitives.Z(color);
        quadVertexBuffer.reset();
        quadVertexBuffer.addVertex2D(f, f2);
        quadVertexBuffer.addVertex2D(f + f3, f2);
        quadVertexBuffer.addVertex2D(f + f3, f2 + f4);
        quadVertexBuffer.addVertex2D(f, f2 + f4);
        quadVertexBuffer.uploadAndDraw();
        GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
        GuiRenderPrimitives.T();
    }

    public static void v(double d, double d2, double d3, double d4, float f, float f2, float f3, int n) {
        if (d3 == 0.0 || d4 == 0.0) {
            return;
        }
        if (!var_gg_vape_utils_render_shader_TexturedQuadShaderProgram_Y.isLinked()) {
            return;
        }
        var_gg_vape_utils_render_shader_TexturedQuadShaderProgram_Y.bind();
        var_gg_vape_utils_render_shader_TexturedQuadShaderProgram_Y.setBlurRadius(f);
        var_gg_vape_utils_render_shader_TexturedQuadShaderProgram_Y.configureTexture((float)d3, (float)d4);
        var_gg_vape_utils_render_shader_TexturedQuadShaderProgram_Y.setBlurDirection(n);
        GuiRenderPrimitives.L();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        float f4 = 0.0f;
        float f5 = 1.0f;
        float f6 = 1.0f;
        float f7 = 0.0f;
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
        GuiRenderPrimitives.T();
        var_gg_vape_utils_render_shader_TexturedQuadShaderProgram_Y.restorePreviousProgram();
    }

    public static void u(double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8, Color color) {
        if (GuiRenderPrimitives.d()) {
            BufferedGuiRenderPrimitives.fillQuad(d, d2, d3, d4, d5, d6, d7, d8, color);
            return;
        }
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
        GL11.glColor4d((double)color.getRed() / 255.0, (double)color.getGreen() / 255.0, (double)color.getBlue() / 255.0, (double)color.getAlpha() / 255.0);
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

    public static void n(double d, double d2, double d3, double d4) {
        if (d3 == 0.0 || d4 == 0.0) {
            return;
        }
        if (GuiRenderPrimitives.d()) {
            float f = (float)d;
            float f2 = (float)d2 - 1.0f;
            float f3 = (float)d3;
            float f4 = (float)d4 + 1.0f;
            BufferedGuiRenderPrimitives.drawCompositeRoundedRect(f, f2, f3, f4, 12.0f, 6.0f, new Color(0, 0, 0, 150), 0.0f, 0.0f, 1.0f, new Color(45, 45, 45, 255), new Color(0, 0, 0, 170), 0.0f, 0.0f, new Color(51, 51, 51, 255), false);
            return;
        }
        float f = 12.0f;
        var_gg_vape_utils_render_shader_CompositeRoundedRectShaderProgram_G.setInnerRect((float)d, (float)(d2 -= 1.0), (float)d3, (float)(d4 += 1.0));
        GuiRenderPrimitives.L();
        quadVertexBuffer.reset();
        quadVertexBuffer.addVertex2D((float)d - f, (float)d2 - f);
        quadVertexBuffer.addVertex2D((float)(d + d3 + (double)f), (float)d2 - f);
        quadVertexBuffer.addVertex2D((float)(d + d3 + (double)f), (float)(d2 + d4 + (double)f));
        quadVertexBuffer.addVertex2D((float)d - f, (float)(d2 + d4 + (double)f));
        quadVertexBuffer.uploadAndDraw();
        GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
        GuiRenderPrimitives.T();
    }

    public static void F(String string, double d, double d2, double d3, double d4, Color color) {
        GuiRenderPrimitives.h(string, (float)d, (float)d2, (float)d3, (float)d4, color);
    }

    public static void U(double d, double d2, double d3, double d4, double d5, double d6, Color color) {
        if (GuiRenderPrimitives.d()) {
            BufferedGuiRenderPrimitives.fillTriangle(d, d2, d3, d4, d5, d6, color);
            return;
        }
        GuiRenderPrimitives.L();
        GL11.glBlendFunc(770, 771);
        OpenGlBackendHolder.backend.enableCapability(2881);
        GL11.glPushMatrix();
        GL11.glColor4d((double)color.getRed() / 255.0, (double)color.getGreen() / 255.0, (double)color.getBlue() / 255.0, (double)color.getAlpha() / 255.0);
        GL11.glBegin(6);
        GL11.glVertex2d(d3, d4);
        GL11.glVertex2d(d, d2);
        GL11.glVertex2d(d5, d6);
        GL11.glEnd();
        GL11.glPopMatrix();
        OpenGlBackendHolder.backend.disableCapability(2881);
        GuiRenderPrimitives.T();
    }

    public static void e(double d, double d2, double d3, double d4, Color color, boolean bl, float f, float f2) {
        GuiRenderPrimitives.I(d, d2, d3, d4, color, bl, f, f2, 8.0f, ThemeColors.var_gg_vape_ui_theme_ThemeColors_J.var_java_awt_Color_u);
    }

    public static void u(double d, double d2, double d3, double d4, float f, Color color) {
        if (GuiRenderPrimitives.d()) {
            BufferedGuiRenderPrimitives.drawLine((float)d, (float)d2, (float)d3, (float)d4, f, color);
            return;
        }
        boolean bl = GL11.glIsEnabled(3042);
        boolean bl2 = GL11.glIsEnabled(3553);
        if (!bl) {
            GlStateManager.enableBlend();
        }
        if (bl2) {
            GlStateManager.disableTexture2D();
        }
        GL11.glColor4d((double)color.getRed() / 255.0, (double)color.getGreen() / 255.0, (double)color.getBlue() / 255.0, (double)color.getAlpha() / 255.0);
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

    public static void R(double d, double d2, double d3, float f, float f2, float f3, Color color) {
        if (GuiRenderPrimitives.d()) {
            BufferedRenderPrimitives.drawCylinderSides(d, d2, d3, f, f2, f3, color);
            return;
        }
        double d4 = RenderManager.getInterpolatedRenderPosX();
        double d5 = RenderManager.getInterpolatedRenderPosY();
        double d6 = RenderManager.getInterpolatedRenderPosZ();
        d -= d4;
        d2 -= d5;
        d3 -= d6;
        float f4 = FreeLookHudModule.isActive() ? FreeLookHudModule.getRenderPitch() - Minecraft.thePlayer().J() : 0.0f;
        float f5 = f4;
        if (!ForgeVersion.MC_1_16_5.d()) {
            OpenGlBackendHolder.backend.translate(d, d2, d3);
            OpenGlBackendHolder.backend.rotate(-f4, 0.0f, 1.0f, 0.0f);
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
        int n = 0;
        while ((float)n <= f) {
            float f6 = (float)(Math.PI * 2 * (double)n / (double)f);
            float f7 = (float)((double)f2 * Math.cos(f6));
            float f8 = (float)((double)f2 * Math.sin(f6));
            RenderUtils.w(new MutableColor(color).withAlpha(0));
            GL11.glVertex3f(f7, f3, f8);
            RenderUtils.w(color);
            GL11.glVertex3f(f7, 0.0f, f8);
            ++n;
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

    public static boolean j() {
        return var_boolean_s;
    }

    public static boolean d() {
        return ForgeVersion.MC_1_17.d();
    }

    public static void p(double d, double d2, double d3, double d4, Color color, boolean bl, float f, float f2, float f3, Color color2, int n) {
        if (d3 == 0.0 || d4 == 0.0) {
            return;
        }
        if (GuiRenderPrimitives.d()) {
            BufferedGuiRenderPrimitives.drawCornerMaskedRoundedRect((float)d, (float)d2, (float)d3, (float)d4, color, bl, f, f2, f3, color2, n);
            return;
        }
        if (!var_gg_vape_utils_render_shader_RoundedRectShaderProgram_g.isLinked() || f == 0.0f) {
            GuiRenderPrimitives.C(d, d2, d3, d4, color);
            return;
        }
        if (bl) {
            GuiRenderPrimitives.g(d, d2 + 0.5, d3, d4 - 1.5, f3, f, color2);
        }
        float f4 = 0.5f;
        if (f <= 0.0f) {
            f4 = 0.0f;
        }
        float f5 = f2;
        float f6 = Math.max(0.0f, (f += f4 * 2.0f) - f5);
        if (f > 0.0f) {
            d -= (double)f5 - 0.5;
            d2 -= (double)f5;
            d4 += (double)(f5 * 1.0f);
            d3 += (double)(f5 * 1.0f);
        }
        var_gg_vape_utils_render_shader_RoundedRectShaderProgram_g.bind();
        var_gg_vape_utils_render_shader_RoundedRectShaderProgram_g.setRadius(f6);
        var_gg_vape_utils_render_shader_RoundedRectShaderProgram_g.setInnerRect((float)d + f, (float)d2 + f, (float)(d + d3) - f, (float)(d2 + d4) - f);
        var_gg_vape_utils_render_shader_RoundedRectShaderProgram_g.setSpread(f5);
        var_gg_vape_utils_render_shader_RoundedRectShaderProgram_g.setCornerMask(n);
        GuiRenderPrimitives.L();
        GuiRenderPrimitives.Z(color);
        quadVertexBuffer.reset();
        quadVertexBuffer.addVertex2D((float)d + f4, (float)d2 + f4);
        quadVertexBuffer.addVertex2D((float)(d + d3 - (double)f4), (float)d2 + f4);
        quadVertexBuffer.addVertex2D((float)(d + d3 - (double)f4), (float)(d2 + d4 - (double)f4));
        quadVertexBuffer.addVertex2D((float)d + f4, (float)(d2 + d4 - (double)f4));
        quadVertexBuffer.uploadAndDraw();
        GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
        GuiRenderPrimitives.T();
        var_gg_vape_utils_render_shader_RoundedRectShaderProgram_g.restorePreviousProgram();
    }

    public static void d(double d, double d2, double d3, float f, Color color) {
        GuiRenderPrimitives.u(d, d2, d, d2 + d3, f, color);
    }

    public static void E(boolean bl) {
        var_boolean_s = bl;
    }

    public static void Y(float f, float f2, float f3, float f4, Color color, double d, Color color2) {
        if (GuiRenderPrimitives.d()) {
            BufferedGuiRenderPrimitives.drawCircleWithCenterRect(f, f2, f3, f4, color, (float)d, color2);
            return;
        }
        f -= f4 / 2.0f;
        f2 -= f4 / 2.0f;
        float f5 = f3 += f4;
        float f6 = f3;
        if (color2 != null) {
            GuiRenderPrimitives.Z(color2);
            double d2 = (double)(f + f5 / 2.0f) - d / 2.0;
            GuiRenderPrimitives.d(d2, (double)f2, d, (double)f3, color2);
        }
        var_gg_vape_utils_render_shader_CircleShaderProgram_V.bind();
        GuiRenderPrimitives.L();
        var_gg_vape_utils_render_shader_CircleShaderProgram_V.setRadius(f3 / 2.0f);
        var_gg_vape_utils_render_shader_CircleShaderProgram_V.setFeather(f4);
        var_gg_vape_utils_render_shader_CircleShaderProgram_V.setCenter(f + f5 / 2.0f, f2 + f6 / 2.0f);
        GuiRenderPrimitives.Z(color);
        quadVertexBuffer.reset();
        quadVertexBuffer.addVertex2D(f, f2);
        quadVertexBuffer.addVertex2D(f + f5, f2);
        quadVertexBuffer.addVertex2D(f + f5, f2 + f6);
        quadVertexBuffer.addVertex2D(f, f2 + f6);
        quadVertexBuffer.uploadAndDraw();
        GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
        GuiRenderPrimitives.T();
        var_gg_vape_utils_render_shader_CircleShaderProgram_V.restorePreviousProgram();
    }

    public static void n(ItemStack itemStack, double d, double d2, double d3) {
        GuiRenderPrimitives.g(itemStack, d, d2, d3, false);
    }

    public static void d(int n, double d, double d2, double d3) {
        if (n < 1) {
            return;
        }
        GuiRenderPrimitives.n(ItemStack.S(Item.T(n)), d, d2, d3);
    }

    public static void m(float f, float f2, float f3, float f4, float f5, Color color) {
        if (GuiRenderPrimitives.d()) {
            BufferedGuiRenderPrimitives.drawCircleStroke(f, f2, f3, f4, f5, color);
            return;
        }
        f -= f5 / 2.0f;
        f2 -= f5 / 2.0f;
        float f6 = f3 += f5;
        float f7 = f3;
        var_gg_vape_utils_render_shader_RingShaderProgram_B.bind();
        var_gg_vape_utils_render_shader_RingShaderProgram_B.setRadiusAndThickness(f3 / 2.0f, f4);
        var_gg_vape_utils_render_shader_RingShaderProgram_B.setFeather(f5);
        var_gg_vape_utils_render_shader_RingShaderProgram_B.setCenter(f + f6 / 2.0f, f2 + f7 / 2.0f);
        GuiRenderPrimitives.L();
        GuiRenderPrimitives.Z(color);
        quadVertexBuffer.reset();
        quadVertexBuffer.addVertex2D(f, f2);
        quadVertexBuffer.addVertex2D(f + f6, f2);
        quadVertexBuffer.addVertex2D(f + f6, f2 + f7);
        quadVertexBuffer.addVertex2D(f, f2 + f7);
        quadVertexBuffer.uploadAndDraw();
        GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
        GuiRenderPrimitives.T();
        var_gg_vape_utils_render_shader_RingShaderProgram_B.restorePreviousProgram();
    }

    public static void u(float f, float f2, float f3, float f4, Color color, GlImageTexture glImageTexture) {
        if (GuiRenderPrimitives.d()) {
            BufferedGuiRenderPrimitives.drawTexturedCircle(f, f2, f3, f4, color, glImageTexture);
            return;
        }
        glImageTexture.bind();
        f -= f4 / 2.0f;
        f2 -= f4 / 2.0f;
        float f5 = f3 += f4;
        float f6 = f3;
        if (color != null) {
            GuiRenderPrimitives.Z(color);
        }
        var_gg_vape_utils_render_shader_TexturedCircleShaderProgram_a.bind();
        GuiRenderPrimitives.L();
        var_gg_vape_utils_render_shader_TexturedCircleShaderProgram_a.setRadius(f3 / 2.0f);
        var_gg_vape_utils_render_shader_TexturedCircleShaderProgram_a.setFeather(f4);
        var_gg_vape_utils_render_shader_TexturedCircleShaderProgram_a.setCenter(f + f5 / 2.0f, f2 + f6 / 2.0f);
        GlStateManager.enableTexture2D();
        float f7 = 0.0f;
        float f8 = 0.0f;
        float f9 = 1.0f;
        float f10 = 1.0f;
        GL11.glBegin(7);
        GL11.glTexCoord2f(f9, f8);
        GL11.glVertex2f(f + f5, f2);
        GL11.glTexCoord2f(f7, f8);
        GL11.glVertex2f(f, f2);
        GL11.glTexCoord2f(f7, f10);
        GL11.glVertex2f(f, f2 + f6);
        GL11.glTexCoord2f(f9, f10);
        GL11.glVertex2f(f + f5, f2 + f6);
        GL11.glEnd();
        GuiRenderPrimitives.T();
        var_gg_vape_utils_render_shader_TexturedCircleShaderProgram_a.restorePreviousProgram();
    }

    public static void P(double d, double d2, double d3, double d4, Color color, float f, float f2, float f3) {
        if (GuiRenderPrimitives.d()) {
            BufferedGuiRenderPrimitives.drawRoundedRect((float)d, (float)d2, (float)d3, (float)d4, color, f, f2, f3);
            return;
        }
        float f4 = 0.5f;
        float f5 = f3;
        var_gg_vape_utils_render_shader_RoundedRectBorderShaderProgram_c.bind();
        var_gg_vape_utils_render_shader_RoundedRectBorderShaderProgram_c.setInnerRect((float)d + f + f2, (float)(d2 -= (double)f5) + f + f2, (float)((d -= (double)f5 - 0.5) + (d3 += (double)(f5 * 1.0f))) - (f + f2), (float)(d2 + (d4 += (double)f5 * 1.5)) - (f + f2));
        var_gg_vape_utils_render_shader_RoundedRectBorderShaderProgram_c.setRadiusParameters(f, f3, f2);
        GuiRenderPrimitives.L();
        GuiRenderPrimitives.Z(color);
        quadVertexBuffer.reset();
        quadVertexBuffer.addVertex2D((float)d + f4, (float)d2 + f4);
        quadVertexBuffer.addVertex2D((float)(d + d3 - (double)f4), (float)d2 + f4);
        quadVertexBuffer.addVertex2D((float)(d + d3 - (double)f4), (float)(d2 + d4 - (double)f4));
        quadVertexBuffer.addVertex2D((float)d + f4, (float)(d2 + d4 - (double)f4));
        quadVertexBuffer.uploadAndDraw();
        GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
        GuiRenderPrimitives.T();
        var_gg_vape_utils_render_shader_RoundedRectBorderShaderProgram_c.restorePreviousProgram();
    }

    public static void L(int n, int n2) {
        if (GuiRenderPrimitives.d()) {
            return;
        }
        NativeBridge.rs(1, n, n2);
    }

    private static void L() {
        if (GuiRenderPrimitives.d()) {
            return;
        }
        if (var_boolean_U) {
            return;
        }
        var_boolean_f = GL11.glIsEnabled(2884);
        var_boolean_Q = GL11.glIsEnabled(3042);
        var_boolean_T = GL11.glIsEnabled(3553);
        if (var_boolean_f) {
            GlStateManager.Y();
        }
        if (!var_boolean_Q) {
            GlStateManager.enableBlend();
        }
        if (var_boolean_T) {
            GlStateManager.disableTexture2D();
        }
        GL11.glBlendFunc(770, 771);
        GL11.glPushMatrix();
    }

    public static void Z(Color color) {
        OpenGlBackendHolder.backend.setColor((float)color.getRed() / 255.0f, (float)color.getGreen() / 255.0f, (float)color.getBlue() / 255.0f, (float)color.getAlpha() / 255.0f);
    }

    static {
        quadVertexBuffer = new GLUtils();
        GuiRenderPrimitives.E(false);
        borderVertexBuffer = new GLUtils();
        var_boolean_U = false;
    }
}

