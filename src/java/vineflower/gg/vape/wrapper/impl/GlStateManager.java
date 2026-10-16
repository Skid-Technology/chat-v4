package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MGlStateManager;
import gg.vape.runtime.NativeBridge;
import gg.vape.utils.render.BufferedGuiRenderPrimitives;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.OpenGlBackendHolder;
import gg.vape.wrapper.Wrapper;
import java.nio.IntBuffer;
import java.util.ArrayList;

public class GlStateManager extends Wrapper {
   private static GlStateManager$TextureState activeTextureState = null;
   private static Boolean K = null;
   private static GlStateManager_BlendState F = null;
   private static final long d = -4864325950512267159L;
   private static final String b = "Invalid flag: ";

   public static void disableTexture2D() {
      if (!GuiRenderPrimitives.d()) {
         if (i()) {
            OpenGlBackendHolder.backend.disableCapability(3553);
         } else {
            MGlStateManager.a(vapeInstance.getMappings().Dt);
         }
      }
   }

   public static int F() {
      return i() ? org.lwjgl.opengl.GL11.glGenTextures() : MGlStateManager.A(vapeInstance.getMappings().Dt);
   }

   public static void L() {
      if (i()) {
         OpenGlBackendHolder.backend.enableCapability(2884);
      } else {
         MGlStateManager.c(vapeInstance.getMappings().Dt);
      }
   }

   public GlStateManager(Object object) {
      super(object);
   }

   public static void setActiveTexture(int n) {
      if (!i()) {
         MGlStateManager.m(vapeInstance.getMappings().Dt, n);
      }
   }

   public static void r() {
      OpenGlBackendHolder.backend.enableCapability(2848);
   }

   public static GlStateManager_BlendState W() {
      if (F == null) {
         F = new GlStateManager_BlendState(MGlStateManager.U(vapeInstance.getMappings().Dt));
      }

      return F;
   }

   public static void tryBlendFuncSeparate(int n, int n2, int n3, int n4) {
      if (i()) {
         org.lwjgl.opengl.GL11.glBlendFunc(n, n2);
      } else {
         MGlStateManager.B(vapeInstance.getMappings().Dt, n, n2, n3, n4);
      }
   }

   public static void enableTexture2D() {
      if (!GuiRenderPrimitives.d()) {
         if (i()) {
            OpenGlBackendHolder.backend.enableCapability(3553);
         } else {
            MGlStateManager.Z(vapeInstance.getMappings().Dt);
         }
      }
   }

   public static void F$src$V$acq27m() {
      if (i()) {
         OpenGlBackendHolder.backend.loadIdentity();
      } else {
         MGlStateManager.P(vapeInstance.getMappings().Dt);
      }
   }

   public static void Y() {
      if (i()) {
         OpenGlBackendHolder.backend.disableCapability(2884);
      } else {
         MGlStateManager.N(vapeInstance.getMappings().Dt);
      }
   }

   public static void disableBlend() {
      if (ForgeVersion.MC_1_7_10.L()) {
         OpenGlBackendHolder.backend.disableCapability(3042);
      } else {
         W().getBlendEnabledState().O(false);
         BufferedGuiRenderPrimitives.capabilityState.enableCapability(3042);
      }
   }

   public static void depthMask(boolean bl) {
      if (i()) {
         OpenGlBackendHolder.backend.setDepthMask(bl);
      } else {
         MGlStateManager.p(vapeInstance.getMappings().Dt, bl);
      }
   }

   public static void enableDepth() {
      if (i()) {
         OpenGlBackendHolder.backend.enableCapability(2929);
      } else {
         MGlStateManager.M(vapeInstance.getMappings().Dt);
      }
   }

   public static int p() {
      return org.lwjgl.opengl.GL11.glGetInteger((int)d);
   }

   public static void bindTexture(int n) {
      if (ForgeVersion.MC_1_7_10.L()) {
         org.lwjgl.opengl.GL11.glBindTexture(3553, n);
      } else {
         Object object;
         Object[] objectArray;
         if (activeTextureState == null
            && (objectArray = MGlStateManager.e(vapeInstance.getMappings().Dt)) != null
            && objectArray.length > 0
            && (object = objectArray[0]) != null) {
            activeTextureState = new GlStateManager$TextureState(object);
         }

         if (activeTextureState != null && n != activeTextureState.getTextureName()) {
            activeTextureState.setTextureName(n);
            org.lwjgl.opengl.GL11.glBindTexture(3553, n);
         }
      }
   }

   public static boolean i() {
      if (K == null) {
         K = ForgeVersion.MC_1_7_10.L() || NativeBridge.isForgeAbsent();
      }

      return K;
   }

   public static void disableDepth() {
      if (i()) {
         OpenGlBackendHolder.backend.disableCapability(2929);
      } else {
         MGlStateManager.j(vapeInstance.getMappings().Dt);
      }
   }

   public static void color(float f, float f2, float f3, float f4) {
      if (i()) {
         OpenGlBackendHolder.backend.setColor(f, f2, f3, f4);
      } else if (ForgeVersion.MC_1_17.d()) {
         MGlStateManager.Z(vapeInstance.getMappings().Dt, f == 1.0F, f2 == 1.0F, f3 == 1.0F, f4 == 1.0F);
      } else {
         MGlStateManager.O(vapeInstance.getMappings().Dt, f, f2, f3, f4);
      }
   }

   public static void g(float f) {
      if (i()) {
         org.lwjgl.opengl.GL11.glFogf(2914, f);
      } else {
         MGlStateManager.c(vapeInstance.getMappings().Dt, f);
      }
   }

   private static IllegalArgumentException a(IllegalArgumentException illegalArgumentException) {
      return illegalArgumentException;
   }

   public static void P() {
      OpenGlBackendHolder.backend.disableCapability(2848);
   }

   public static void d(float f, float f2, float f3, float f4) {
      if (i()) {
         OpenGlBackendHolder.backend.rotate(f, f2, f3, f4);
      } else {
         MGlStateManager.P(vapeInstance.getMappings().Dt, f, f2, f3, f4);
      }
   }

   public static void Y(int n, int n2) {
      if (ForgeVersion.MC_1_21_6.d()) {
         tryBlendFuncSeparate(n, n2, n, n2);
      } else {
         MGlStateManager.j(vapeInstance.getMappings().Dt, n, n2);
      }
   }

   public static int i(int n) {
      return ForgeVersion.MC_1_21_10.v() ? -1 : vapeInstance.getMappings().Dt.w(n);
   }

   public static void t(int n, int n2) {
      if (!ForgeVersion.MC_1_20_6.v()) {
         MGlStateManager.a(vapeInstance.getMappings().Dt, n, n2);
      }
   }

   public static void disableLighting() {
      if (!GuiRenderPrimitives.d()) {
         if (i()) {
            OpenGlBackendHolder.backend.disableCapability(2896);
         } else {
            MGlStateManager.X(vapeInstance.getMappings().Dt);
         }
      }
   }

   public static void enableBlend() {
      if (ForgeVersion.MC_1_7_10.L()) {
         OpenGlBackendHolder.backend.enableCapability(3042);
      } else {
         W().getBlendEnabledState().O(true);
         BufferedGuiRenderPrimitives.capabilityState.enableCapability(3042);
      }
   }

   public static void enableAlpha() {
      if (!GuiRenderPrimitives.d()) {
         if (i()) {
            OpenGlBackendHolder.backend.enableCapability(3008);
         } else {
            MGlStateManager.v(vapeInstance.getMappings().Dt);
         }
      }
   }

   public static void disableAlpha() {
      if (!GuiRenderPrimitives.d()) {
         if (i()) {
            OpenGlBackendHolder.backend.disableCapability(3008);
         } else {
            MGlStateManager.O(vapeInstance.getMappings().Dt);
         }
      }
   }

   public static void enableLighting() {
      if (!GuiRenderPrimitives.d()) {
         if (i()) {
            OpenGlBackendHolder.backend.enableCapability(2896);
         } else {
            MGlStateManager.x(vapeInstance.getMappings().Dt);
         }
      }
   }

   public static void h(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, IntBuffer intBuffer) {
      MGlStateManager.Q(vapeInstance.getMappings().Dt, n, n2, n3, n4, n5, n6, n7, n8, intBuffer);
   }

   public static void r(int n, boolean bl) {
      if (n == 2896) {
         if (bl) {
            enableLighting();
         } else {
            disableLighting();
         }
      } else if (n == 2929) {
         if (bl) {
            enableDepth();
         } else {
            disableDepth();
         }
      } else if (n == 3042) {
         if (bl) {
            enableBlend();
         } else {
            disableBlend();
         }
      } else if (n == 3553) {
         if (bl) {
            enableTexture2D();
         } else {
            disableTexture2D();
         }
      } else if (n == 3008) {
         if (bl) {
            enableAlpha();
         } else {
            disableAlpha();
         }
      } else if (n == 2884) {
         if (bl) {
            L();
         } else {
            Y();
         }
      } else {
         if (n != 2848) {
            throw new IllegalArgumentException(b + n);
         }

         if (bl) {
            OpenGlBackendHolder.backend.enableCapability(2848);
         } else {
            OpenGlBackendHolder.backend.disableCapability(2848);
         }
      }
   }

   public static ArrayList<GlStateManager$TextureState> R() {
      ArrayList<GlStateManager$TextureState> arrayList = new ArrayList<>();

      for (Object object : MGlStateManager.e(vapeInstance.getMappings().Dt)) {
         arrayList.add(new GlStateManager$TextureState(object));
      }

      return arrayList;
   }
}
