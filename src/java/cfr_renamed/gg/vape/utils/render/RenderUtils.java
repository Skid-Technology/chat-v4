/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.utils.render;

import gg.vape.Vape;
import gg.vape.input.MouseInput;
import gg.vape.runtime.NativeBridge;
import gg.vape.ui.click.MousePosition;
import gg.vape.ui.click.component.GuiComponentContract;
import gg.vape.utils.render.BufferedGuiRenderPrimitives;
import gg.vape.utils.render.BufferedRenderPrimitives;
import gg.vape.utils.render.GlScissorRect;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.OpenGlBackendHolder;
import gg.vape.wrapper.impl.GlStateManager;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.Tessellator;
import java.awt.Color;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Stack;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public final class RenderUtils {
    private static IntBuffer var_java_nio_IntBuffer_T;
    private final static Color[] var_java_awt_Color_arr_j;
    static float var_float_I;
    static float var_float_O;
    static int var_int_r;
    static float var_float_l;
    static int var_int_z;
    private static FloatBuffer var_java_nio_FloatBuffer_d;
    static float var_float_Q;
    static Stack<GlScissorRect> var_java_util_Stack_gg_vape_utils_render_GlScissorRect__k;
    private static String[] var_java_lang_String_arr_V;
    private final static Color[] var_java_awt_Color_arr_v;
    private static FloatBuffer var_java_nio_FloatBuffer_X;
    static int var_int_A;
    private final static float[] var_float_arr_H;
    private static MousePosition var_gg_vape_ui_click_MousePosition_w;
    static Stack<GlScissorRect> var_java_util_Stack_gg_vape_utils_render_GlScissorRect__P;
    static int var_int_y;
    static IntBuffer var_java_nio_IntBuffer_g;

    public static void C() {
        int n = Minecraft.h() - MouseInput.getInvertedMouseY();
        RenderUtils.C(MouseInput.getMouseX(), n);
    }

    public static void C(int mouseX, int mouseY) {
        double d = 2.0 * Vape.INSTANCE.getClientSettings().getGuiScaleFactor();
        var_gg_vape_ui_click_MousePosition_w = new MousePosition((int)((double)mouseX / d), (int)((double)mouseY / d));
    }

    public static void X(Color color, Color color2, double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8) {
        if (GuiRenderPrimitives.d()) {
            BufferedGuiRenderPrimitives.drawLine(d, d2, d3, d4, 1.0f, color2);
            BufferedGuiRenderPrimitives.drawLine(d, d2, d7, d8, 1.0f, color2);
            BufferedGuiRenderPrimitives.drawLine(d5, d6, d7, d8, 1.0f, color);
            BufferedGuiRenderPrimitives.drawLine(d5, d6, d3, d4, 1.0f, color);
            return;
        }
        OpenGlBackendHolder.backend.disableCapability(3553);
        OpenGlBackendHolder.backend.enableCapability(3042);
        OpenGlBackendHolder.backend.disableCapability(3008);
        GL11.glBlendFunc(770, 771);
        GL11.glShadeModel(7425);
        RenderUtils.w(color2);
        GL11.glBegin(7);
        GL11.glVertex2d(d, d2);
        GL11.glVertex2d(d3, d4);
        RenderUtils.w(color);
        GL11.glVertex2d(d5, d6);
        GL11.glVertex2d(d7, d8);
        GL11.glEnd();
        GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
        GL11.glShadeModel(7424);
        OpenGlBackendHolder.backend.disableCapability(3042);
        OpenGlBackendHolder.backend.enableCapability(3008);
        OpenGlBackendHolder.backend.enableCapability(3553);
    }

    public static void J(double d, double d2, double d3, double d4) {
        GL11.glBegin(7);
        GL11.glTexCoord2f(1.0f, 0.0f);
        GL11.glVertex3d(d, d4, 0.0);
        GL11.glTexCoord2f(0.0f, 0.0f);
        GL11.glVertex3d(d3, d4, 0.0);
        GL11.glTexCoord2f(0.0f, 1.0f);
        GL11.glVertex3d(d3, d2, 0.0);
        GL11.glTexCoord2f(1.0f, 1.0f);
        GL11.glVertex3d(d, d2, 0.0);
        GL11.glEnd();
    }

    public static void j(String[] stringArray) {
        var_java_lang_String_arr_V = stringArray;
    }

    public static void i(double d, double d2, double d3, double d4) {
    }

    public static void X(double d, double d2, double d3, double d4) {
        IntBuffer intBuffer = BufferUtils.createIntBuffer(16);
        gg.vape.wrapper.impl.GL11.X(2978, intBuffer);
        var_java_util_Stack_gg_vape_utils_render_GlScissorRect__k.push(new GlScissorRect(intBuffer.get(0), intBuffer.get(1), intBuffer.get(2), intBuffer.get(3)));
        int n = Minecraft.h();
        double d5 = 2.0 * Vape.INSTANCE.getClientSettings().getGuiScaleFactor();
        int n2 = (int)(d3 * d5);
        int n3 = (int)(d4 * d5);
        double d6 = d * d5;
        int n4 = (int)d6;
        double d7 = d2 * d5;
        int n5 = (int)((double)n - d7 - (double)n3);
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
        if (GuiRenderPrimitives.d()) {
            return 0;
        }
        return NativeBridge.sts();
    }

    public static Color K(float[] fArray, Color[] colorArray, float f) {
        Color color = Color.RED;
        if (fArray != null && colorArray != null && fArray.length == colorArray.length) {
            int[] nArray = RenderUtils.Z(fArray, f);
            float[] fArray2 = new float[]{fArray[nArray[0]], fArray[nArray[1]]};
            Color[] colorArray2 = new Color[]{colorArray[nArray[0]], colorArray[nArray[1]]};
            float f2 = fArray2[1] - fArray2[0];
            float f3 = f - fArray2[0];
            float f4 = f3 / f2;
            color = RenderUtils.h(colorArray2[0], colorArray2[1], 1.0f - f4);
        }
        return color;
    }

    public static Color q(float f, boolean bl) {
        return RenderUtils.K(var_float_arr_H, bl ? var_java_awt_Color_arr_v : var_java_awt_Color_arr_j, Math.max(0.0f, Math.min(1.0f, f)));
    }

    public static String[] v() {
        return var_java_lang_String_arr_V;
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
        Color color3;
        float f = (float)d;
        float f2 = 1.0f - f;
        float[] fArray = new float[3];
        float[] fArray2 = new float[3];
        color.getColorComponents(fArray);
        color2.getColorComponents(fArray2);
        float f3 = fArray[0] * f + fArray2[0] * f2;
        float f4 = fArray[1] * f + fArray2[1] * f2;
        float f5 = fArray[2] * f + fArray2[2] * f2;
        if (f3 < 0.0f) {
            f3 = 0.0f;
        } else if (f3 > 255.0f) {
            f3 = 255.0f;
        }
        if (f4 < 0.0f) {
            f4 = 0.0f;
        } else if (f4 > 255.0f) {
            f4 = 255.0f;
        }
        if (f5 < 0.0f) {
            f5 = 0.0f;
        } else if (f5 > 255.0f) {
            f5 = 255.0f;
        }
        try {
            color3 = new Color(f3, f4, f5);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return Color.RED;
        }
        return color3;
    }

    public static void T() {
        if (!var_java_util_Stack_gg_vape_utils_render_GlScissorRect__P.empty()) {
            GlScissorRect glScissorRect = var_java_util_Stack_gg_vape_utils_render_GlScissorRect__P.pop();
            OpenGlBackendHolder.backend.setScissor(glScissorRect.var_int_x, glScissorRect.var_int_y, glScissorRect.width, glScissorRect.height);
        } else {
            OpenGlBackendHolder.backend.setScissor(var_int_A, var_int_r, var_int_z, var_int_y);
            BufferedGuiRenderPrimitives.scissorRect = null;
            OpenGlBackendHolder.backend.disableCapability(3089);
        }
    }

    public static int g() {
        if (GuiRenderPrimitives.d()) {
            return 0;
        }
        return NativeBridge.gts();
    }

    public static boolean w(Color color) {
        float f = (float)color.getRed() / 255.0f;
        float f2 = (float)color.getGreen() / 255.0f;
        float f3 = (float)color.getBlue() / 255.0f;
        float f4 = (float)color.getAlpha() / 255.0f;
        boolean bl = f == var_float_l && f2 == var_float_I && f3 == var_float_O && var_float_Q == f4;
        OpenGlBackendHolder.backend.setColor(f, f2, f3, f4);
        GlStateManager.color(f, f2, f3, f4);
        var_float_l = f;
        var_float_I = f2;
        var_float_O = f3;
        var_float_Q = f4;
        return bl;
    }

    public static MousePosition h() {
        if (var_gg_vape_ui_click_MousePosition_w == null) {
            RenderUtils.C();
        }
        return var_gg_vape_ui_click_MousePosition_w;
    }

    public static void R(int n, int n2, int n3, int n4, int n5, int n6) {
        float f = 0.00390625f;
        float f2 = 0.00390625f;
        GL11.glBegin(7);
        RenderUtils.R(n, n2 + n6, 0.0, (float)n3 * f, (float)(n4 + n6) * f2);
        RenderUtils.R(n + n5, n2 + n6, 0.0, (float)(n3 + n5) * f, (float)(n4 + n6) * f2);
        RenderUtils.R(n + n5, n2, 0.0, (float)(n3 + n5) * f, (float)(n4 + 0) * f2);
        RenderUtils.R(n, n2, 0.0, (float)n3 * f, (float)(n4 + 0) * f2);
        GL11.glEnd();
    }

    public static float[] d(int n) {
        float f = (float)(n >> 24 & 0xFF) / 255.0f;
        float f2 = (float)(n >> 16 & 0xFF) / 255.0f;
        float f3 = (float)(n >> 8 & 0xFF) / 255.0f;
        float f4 = (float)(n & 0xFF) / 255.0f;
        return new float[]{f2, f3, f4, f};
    }

    private static int[] Z(float[] fArray, float f) {
        int n;
        int[] nArray = new int[2];
        for (n = 0; n < fArray.length && fArray[n] <= f; ++n) {
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
            var_java_nio_IntBuffer_g.clear();
            gg.vape.wrapper.impl.GL11.X(3088, var_java_nio_IntBuffer_g);
            n = var_java_nio_IntBuffer_g.get(0);
            n2 = var_java_nio_IntBuffer_g.get(1);
            n3 = var_java_nio_IntBuffer_g.get(2);
            n4 = var_java_nio_IntBuffer_g.get(3);
            var_java_util_Stack_gg_vape_utils_render_GlScissorRect__P.push(new GlScissorRect(n, n2, n3, n4));
        } else {
            IntBuffer intBuffer = BufferUtils.createIntBuffer(16);
            gg.vape.wrapper.impl.GL11.X(3088, intBuffer);
            var_int_A = intBuffer.get(0);
            var_int_r = intBuffer.get(1);
            var_int_z = intBuffer.get(2);
            var_int_y = intBuffer.get(3);
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
        int n10 = (int)Math.ceil((double)n5 - d7 - (double)n8);
        if (bl) {
            int n11;
            int n12;
            int n13;
            int n14;
            if (n9 < n) {
                n9 = n;
            }
            if ((n14 = n9 + n7) > (n13 = n + n3)) {
                n12 = n14 - n13;
                n7 -= n12;
                n7 = Math.max(0, n7);
            }
            if (n10 < n2) {
                n10 = n2;
            }
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
        GL11.glTexCoord2f(0.0f, 0.0f);
        GL11.glVertex3d(d, d4, 0.0);
        GL11.glTexCoord2f(1.0f, 0.0f);
        GL11.glVertex3d(d3, d4, 0.0);
        GL11.glTexCoord2f(1.0f, 1.0f);
        GL11.glVertex3d(d3, d2, 0.0);
        GL11.glTexCoord2f(0.0f, 1.0f);
        GL11.glVertex3d(d, d2, 0.0);
        GL11.glEnd();
    }

    public static void R(double d, double d2, double d3, double d4, double d5) {
        GL11.glTexCoord2d(d4, d5);
        GL11.glVertex3d(d, d2, 5.0);
    }

    public static void u() {
        if (!var_java_util_Stack_gg_vape_utils_render_GlScissorRect__k.empty()) {
            GlScissorRect glScissorRect = var_java_util_Stack_gg_vape_utils_render_GlScissorRect__k.pop();
            GL11.glViewport(glScissorRect.var_int_x, glScissorRect.var_int_y, glScissorRect.width, glScissorRect.height);
        }
    }

    public static Color S(float f) {
        return RenderUtils.q(f, false);
    }

    private RenderUtils() {
    }

    static {
        var_java_util_Stack_gg_vape_utils_render_GlScissorRect__P = new Stack();
        var_java_util_Stack_gg_vape_utils_render_GlScissorRect__k = new Stack();
        var_java_nio_IntBuffer_g = BufferUtils.createIntBuffer(16);
        var_int_A = 0;
        var_int_r = 0;
        var_int_z = 0;
        var_int_y = 0;
        var_float_arr_H = new float[]{0.0f, 0.5f, 1.0f};
        var_java_awt_Color_arr_j = new Color[]{Color.RED, Color.YELLOW, Color.GREEN};
        var_java_awt_Color_arr_v = new Color[]{Color.RED, Color.YELLOW, GuiComponentContract.var_gg_vape_ui_theme_ThemeColors_J.var_java_awt_Color_B};
        RenderUtils.j(null);
    }
}

