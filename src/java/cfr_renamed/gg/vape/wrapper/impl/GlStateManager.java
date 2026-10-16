/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MGlStateManager;
import gg.vape.runtime.NativeBridge;
import gg.vape.utils.render.BufferedGuiRenderPrimitives;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.OpenGlBackendHolder;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GlStateManager$TextureState;
import gg.vape.wrapper.impl.GlStateManager_BlendState;
import java.nio.IntBuffer;
import java.util.ArrayList;
import org.lwjgl.opengl.GL11;

public class GlStateManager
extends Wrapper {
    private static GlStateManager$TextureState activeTextureState;
    private static Boolean var_java_lang_Boolean_K;
    private static GlStateManager_BlendState var_gg_vape_wrapper_impl_GlStateManager_BlendState_F;
    private final static long var_long_d;
    private final static String var_java_lang_String_b;

    public static void disableTexture2D() {
        if (GuiRenderPrimitives.d()) {
            return;
        }
        if (GlStateManager.i()) {
            OpenGlBackendHolder.backend.disableCapability(3553);
            return;
        }
        MGlStateManager.a(GlStateManager.vapeInstance.getMappings().Dt);
    }

    public static int F() {
        if (GlStateManager.i()) {
            return GL11.glGenTextures();
        }
        return MGlStateManager.A(GlStateManager.vapeInstance.getMappings().Dt);
    }

    public static void L() {
        if (GlStateManager.i()) {
            OpenGlBackendHolder.backend.enableCapability(2884);
            return;
        }
        MGlStateManager.c(GlStateManager.vapeInstance.getMappings().Dt);
    }

    public GlStateManager(Object object) {
        super(object);
    }

    public static void setActiveTexture(int n) {
        if (GlStateManager.i()) {
            return;
        }
        MGlStateManager.m(GlStateManager.vapeInstance.getMappings().Dt, n);
    }

    public static void r() {
        OpenGlBackendHolder.backend.enableCapability(2848);
    }

    public static GlStateManager_BlendState W() {
        if (var_gg_vape_wrapper_impl_GlStateManager_BlendState_F == null) {
            var_gg_vape_wrapper_impl_GlStateManager_BlendState_F = new GlStateManager_BlendState(MGlStateManager.U(GlStateManager.vapeInstance.getMappings().Dt));
        }
        return var_gg_vape_wrapper_impl_GlStateManager_BlendState_F;
    }

    public static void tryBlendFuncSeparate(int n, int n2, int n3, int n4) {
        if (GlStateManager.i()) {
            GL11.glBlendFunc(n, n2);
            return;
        }
        MGlStateManager.B(GlStateManager.vapeInstance.getMappings().Dt, n, n2, n3, n4);
    }

    public static void enableTexture2D() {
        if (GuiRenderPrimitives.d()) {
            return;
        }
        if (GlStateManager.i()) {
            OpenGlBackendHolder.backend.enableCapability(3553);
            return;
        }
        MGlStateManager.Z(GlStateManager.vapeInstance.getMappings().Dt);
    }

    public static void F$src$V$acq27m() {
        if (GlStateManager.i()) {
            OpenGlBackendHolder.backend.loadIdentity();
            return;
        }
        MGlStateManager.P(GlStateManager.vapeInstance.getMappings().Dt);
    }

    public static void Y() {
        if (GlStateManager.i()) {
            OpenGlBackendHolder.backend.disableCapability(2884);
            return;
        }
        MGlStateManager.N(GlStateManager.vapeInstance.getMappings().Dt);
    }

    public static void disableBlend() {
        if (ForgeVersion.MC_1_7_10.L()) {
            OpenGlBackendHolder.backend.disableCapability(3042);
            return;
        }
        GlStateManager.W().getBlendEnabledState().O(false);
        BufferedGuiRenderPrimitives.capabilityState.enableCapability(3042);
    }

    public static void depthMask(boolean bl) {
        if (GlStateManager.i()) {
            OpenGlBackendHolder.backend.setDepthMask(bl);
            return;
        }
        MGlStateManager.p(GlStateManager.vapeInstance.getMappings().Dt, bl);
    }

    public static void enableDepth() {
        if (GlStateManager.i()) {
            OpenGlBackendHolder.backend.enableCapability(2929);
            return;
        }
        MGlStateManager.M(GlStateManager.vapeInstance.getMappings().Dt);
    }

    public static int p() {
        return GL11.glGetInteger((int)var_long_d);
    }

    public static void bindTexture(int n) {
        Object object;
        Object[] objectArray;
        if (ForgeVersion.MC_1_7_10.L()) {
            GL11.glBindTexture(3553, n);
            return;
        }
        if (activeTextureState == null && (objectArray = MGlStateManager.e(GlStateManager.vapeInstance.getMappings().Dt)) != null && objectArray.length > 0 && (object = objectArray[0]) != null) {
            activeTextureState = new GlStateManager$TextureState(object);
        }
        if (activeTextureState != null && n != activeTextureState.getTextureName()) {
            activeTextureState.setTextureName(n);
            GL11.glBindTexture(3553, n);
        }
    }

    public static boolean i() {
        if (var_java_lang_Boolean_K == null) {
            var_java_lang_Boolean_K = ForgeVersion.MC_1_7_10.L() || NativeBridge.isForgeAbsent();
        }
        return var_java_lang_Boolean_K;
    }

    public static void disableDepth() {
        if (GlStateManager.i()) {
            OpenGlBackendHolder.backend.disableCapability(2929);
            return;
        }
        MGlStateManager.j(GlStateManager.vapeInstance.getMappings().Dt);
    }

    public static void color(float f, float f2, float f3, float f4) {
        if (GlStateManager.i()) {
            OpenGlBackendHolder.backend.setColor(f, f2, f3, f4);
            return;
        }
        if (ForgeVersion.MC_1_17.d()) {
            MGlStateManager.Z(GlStateManager.vapeInstance.getMappings().Dt, f == 1.0f, f2 == 1.0f, f3 == 1.0f, f4 == 1.0f);
            return;
        }
        MGlStateManager.O(GlStateManager.vapeInstance.getMappings().Dt, f, f2, f3, f4);
    }

    public static void g(float f) {
        if (GlStateManager.i()) {
            GL11.glFogf(2914, f);
            return;
        }
        MGlStateManager.c(GlStateManager.vapeInstance.getMappings().Dt, f);
    }

    private static IllegalArgumentException a(IllegalArgumentException illegalArgumentException) {
        return illegalArgumentException;
    }

    public static void P() {
        OpenGlBackendHolder.backend.disableCapability(2848);
    }

    public static void d(float f, float f2, float f3, float f4) {
        if (GlStateManager.i()) {
            OpenGlBackendHolder.backend.rotate(f, f2, f3, f4);
            return;
        }
        MGlStateManager.P(GlStateManager.vapeInstance.getMappings().Dt, f, f2, f3, f4);
    }

    public static void Y(int n, int n2) {
        if (ForgeVersion.MC_1_21_6.d()) {
            GlStateManager.tryBlendFuncSeparate(n, n2, n, n2);
            return;
        }
        MGlStateManager.j(GlStateManager.vapeInstance.getMappings().Dt, n, n2);
    }

    public static int i(int n) {
        if (ForgeVersion.MC_1_21_10.v()) {
            return -1;
        }
        return GlStateManager.vapeInstance.getMappings().Dt.w(n);
    }

    public static void t(int n, int n2) {
        if (ForgeVersion.MC_1_20_6.v()) {
            return;
        }
        MGlStateManager.a(GlStateManager.vapeInstance.getMappings().Dt, n, n2);
    }

    public static void disableLighting() {
        if (GuiRenderPrimitives.d()) {
            return;
        }
        if (GlStateManager.i()) {
            OpenGlBackendHolder.backend.disableCapability(2896);
            return;
        }
        MGlStateManager.X(GlStateManager.vapeInstance.getMappings().Dt);
    }

    public static void enableBlend() {
        if (ForgeVersion.MC_1_7_10.L()) {
            OpenGlBackendHolder.backend.enableCapability(3042);
            return;
        }
        GlStateManager.W().getBlendEnabledState().O(true);
        BufferedGuiRenderPrimitives.capabilityState.enableCapability(3042);
    }

    public static void enableAlpha() {
        if (GuiRenderPrimitives.d()) {
            return;
        }
        if (GlStateManager.i()) {
            OpenGlBackendHolder.backend.enableCapability(3008);
            return;
        }
        MGlStateManager.v(GlStateManager.vapeInstance.getMappings().Dt);
    }

    public static void disableAlpha() {
        if (GuiRenderPrimitives.d()) {
            return;
        }
        if (GlStateManager.i()) {
            OpenGlBackendHolder.backend.disableCapability(3008);
            return;
        }
        MGlStateManager.O(GlStateManager.vapeInstance.getMappings().Dt);
    }

    public static void enableLighting() {
        if (GuiRenderPrimitives.d()) {
            return;
        }
        if (GlStateManager.i()) {
            OpenGlBackendHolder.backend.enableCapability(2896);
            return;
        }
        MGlStateManager.x(GlStateManager.vapeInstance.getMappings().Dt);
    }

    public static void h(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, IntBuffer intBuffer) {
        MGlStateManager.Q(GlStateManager.vapeInstance.getMappings().Dt, n, n2, n3, n4, n5, n6, n7, n8, intBuffer);
    }

    public static void r(int n, boolean bl) {
        if (n == 2896) {
            if (bl) {
                GlStateManager.enableLighting();
            } else {
                GlStateManager.disableLighting();
            }
        } else if (n == 2929) {
            if (bl) {
                GlStateManager.enableDepth();
            } else {
                GlStateManager.disableDepth();
            }
        } else if (n == 3042) {
            if (bl) {
                GlStateManager.enableBlend();
            } else {
                GlStateManager.disableBlend();
            }
        } else if (n == 3553) {
            if (bl) {
                GlStateManager.enableTexture2D();
            } else {
                GlStateManager.disableTexture2D();
            }
        } else if (n == 3008) {
            if (bl) {
                GlStateManager.enableAlpha();
            } else {
                GlStateManager.disableAlpha();
            }
        } else if (n == 2884) {
            if (bl) {
                GlStateManager.L();
            } else {
                GlStateManager.Y();
            }
        } else if (n == 2848) {
            if (bl) {
                OpenGlBackendHolder.backend.enableCapability(2848);
            } else {
                OpenGlBackendHolder.backend.disableCapability(2848);
            }
        } else {
            throw new IllegalArgumentException(var_java_lang_String_b + n);
        }
    }

    public static ArrayList<GlStateManager$TextureState> R() {
        ArrayList<GlStateManager$TextureState> arrayList = new ArrayList<GlStateManager$TextureState>();
        for (Object object : MGlStateManager.e(GlStateManager.vapeInstance.getMappings().Dt)) {
            arrayList.add(new GlStateManager$TextureState(object));
        }
        return arrayList;
    }

    static {
        var_java_lang_String_b = "Invalid flag: ";
        var_long_d = -4864325950512267159L;
        var_java_lang_Boolean_K = null;
        activeTextureState = null;
        var_gg_vape_wrapper_impl_GlStateManager_BlendState_F = null;
    }
}

