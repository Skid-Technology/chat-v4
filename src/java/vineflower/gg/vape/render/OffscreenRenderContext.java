package gg.vape.render;

import gg.vape.Vape;
import gg.vape.utils.TimerUtil;
import gg.vape.utils.render.GlFramebuffer;
import gg.vape.utils.render.GlImageTexture;
import gg.vape.utils.render.OpenGlBackendHolder;
import gg.vape.utils.render.RenderBatchBuilder;
import gg.vape.utils.render.RenderBatchManager;
import gg.vape.utils.render.RenderUtils;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityLivingBase;
import gg.vape.wrapper.impl.EntityRenderer;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Framebuffer;
import gg.vape.wrapper.impl.GameSettings;
import gg.vape.wrapper.impl.GlStateManager;
import gg.vape.wrapper.impl.Minecraft;
import java.awt.Color;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class OffscreenRenderContext {
   private GlFramebuffer modernFramebuffer;
   private Framebuffer legacyFramebuffer;
   public float cameraPitch;
   public double cameraZ;
   private int frameIntervalMillis = 30;
   private static int[] controlFlowMarker;
   private int width;
   private static boolean renderingOffscreen;
   public double cameraX;
   private boolean frameReady;
   public double cameraY;
   public float cameraYaw;
   private int height;
   private final TimerUtil frameTimer = new TimerUtil();
   private boolean flipVertically;
   private int fieldOfView;

   public Framebuffer getLegacyFramebuffer() {
      return this.legacyFramebuffer;
   }

   public static void setRenderingOffscreen(boolean rendering) {
      renderingOffscreen = rendering;
   }

   public int getWidth() {
      return this.width;
   }

   public OffscreenRenderContext setFlipVertically(boolean flipVertically) {
      this.flipVertically = flipVertically;
      return this;
   }

   public void drawFramebuffer(boolean visible, double left, double top, double right, double bottom) {
      this.drawFramebuffer(visible, left, top, right, bottom, Color.WHITE);
   }

   public OffscreenRenderContext() {
      this(false);
   }

   public void initializeFramebuffer() {
      if (ForgeVersion.MC_1_21_4.d()) {
         this.modernFramebuffer = new GlFramebuffer(this.width, this.height, true);
         this.legacyFramebuffer = null;
      } else {
         this.legacyFramebuffer = Framebuffer.create(this.width, this.height, true);
         this.modernFramebuffer = null;
         this.resizeFramebuffer();
      }
   }

   public void setHeight(int height) {
      this.height = height;
      this.frameReady = false;
   }

   public int getHeight() {
      return this.height;
   }

   protected void captureCameraState(Entity entity) {
      this.captureInterpolatedCameraPosition(entity);
      this.cameraYaw = entity.J();
      this.cameraPitch = entity.V();
   }

   public void setWidth(int width) {
      this.width = width;
      this.frameReady = false;
   }

   protected void captureInterpolatedCameraPosition(Entity entity) {
      this.cameraX = entity.M() - (entity.M() - entity.z()) * Minecraft.getTimer().getElapsedPartialTicks();
      this.cameraY = entity.W() - (entity.W() - entity.N()) * Minecraft.getTimer().getElapsedPartialTicks();
      this.cameraZ = entity.m$src$D$fwnne5() - (entity.m$src$D$fwnne5() - entity.h()) * Minecraft.getTimer().getElapsedPartialTicks();
   }

   public void setFrameIntervalMillis(int frameIntervalMillis) {
      this.frameIntervalMillis = frameIntervalMillis;
   }

   public void resizeFramebuffer() {
      if (ForgeVersion.MC_1_21_4.d()) {
         if (this.modernFramebuffer != null) {
            this.modernFramebuffer.delete();
         }

         this.modernFramebuffer = new GlFramebuffer(this.width, this.height, true);
      } else {
         this.legacyFramebuffer.createFramebuffer(this.width, this.height);
      }
   }

   public void setFieldOfView(int fieldOfView) {
      this.fieldOfView = fieldOfView;
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public void renderOffscreenFrame() {
      Entity entity = null;
      EntityRenderer entityRenderer = null;
      GameSettings gameSettings = null;
      double currentX = 0.0;
      double currentY = 0.0;
      double currentZ = 0.0;
      double previousX = 0.0;
      double previousY = 0.0;
      double previousZ = 0.0;
      double previousLastTickX = 0.0;
      double previousLastTickY = 0.0;
      double previousLastTickZ = 0.0;
      int displayWidth = 0;
      int displayHeight = 0;
      int previousThirdPersonView = 0;
      float currentYaw = 0.0F;
      float currentPitch = 0.0F;
      float previousYaw = 0.0F;
      float previousPitch = 0.0F;
      float previousFov = 0.0F;
      float previousCameraZoom = 0.0F;
      float previousCameraYaw = 0.0F;
      float currentBodyYaw = 0.0F;
      float previousBodyYaw = 0.0F;
      float previousCurrentHeadYaw = 0.0F;
      float previousHeadYaw = 0.0F;
      boolean previousHideGui = false;
      boolean previousViewBobbing = false;
      boolean stateModified = false;
      boolean framebufferBound = false;
      boolean var47 = false /* VF: Semaphore variable */;

      label266: {
         label267: {
            try {
               var47 = true;
               if (renderingOffscreen) {
                  return;
               }

               if (!this.canRender()) {
                  return;
               }

               gameSettings = Minecraft.gameSettings();
               if (gameSettings.d() > 0) {
                  return;
               }

               if (!gameSettings.Y$src$Z$1rxemad()) {
                  return;
               }

               if (gameSettings.M()) {
                  return;
               }

               if (gameSettings.M()) {
                  return;
               }

               if (gameSettings.isNull()) {
                  return;
               }

               if (Minecraft.F().isNull()) {
                  return;
               }

               entity = Minecraft.F();
               entityRenderer = Minecraft.m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf();
               displayWidth = Minecraft.J();
               displayHeight = Minecraft.h();
               currentYaw = entity.J();
               previousYaw = entity.j();
               currentPitch = entity.V();
               previousPitch = entity.D();
               currentBodyYaw = ((EntityLivingBase)entity).s();
               previousBodyYaw = ((EntityLivingBase)entity).P$src$F$14ztfk8();
               previousCurrentHeadYaw = ((EntityLivingBase)entity).W$src$F$153nzpr();
               previousHeadYaw = ((EntityLivingBase)entity).S$src$F$151gtcb();
               previousHideGui = gameSettings.U();
               previousThirdPersonView = gameSettings.x();
               previousViewBobbing = gameSettings.k();
               currentX = entity.z();
               previousX = entity.f();
               previousLastTickX = entity.M();
               currentY = entity.N();
               previousY = entity.H();
               previousLastTickY = entity.W();
               currentZ = entity.h();
               previousZ = entity.R();
               previousLastTickZ = entity.m$src$D$fwnne5();
               previousFov = gameSettings.g();
               previousCameraZoom = entityRenderer.b();
               previousCameraYaw = entityRenderer.s();
               entity.H(this.cameraX);
               entity.n(this.cameraX);
               entity.C(this.cameraX);
               entity.u(this.cameraY);
               entity.w(this.cameraY);
               entity.L(this.cameraY);
               entity.l(this.cameraZ);
               entity.A(this.cameraZ);
               entity.s(this.cameraZ);
               if (ForgeVersion.MC_1_21_4.v()) {
                  Minecraft.U(this.width);
                  Minecraft.X(this.height);
               }

               entity.H(this.cameraYaw);
               entity.D(this.cameraYaw);
               entity.C(this.cameraPitch);
               entity.l(this.cameraPitch);
               ((EntityLivingBase)entity).z(this.cameraYaw);
               ((EntityLivingBase)entity).o(this.cameraYaw);
               ((EntityLivingBase)entity).X(this.cameraYaw);
               ((EntityLivingBase)entity).Y(this.cameraYaw);
               gameSettings.I(0);
               gameSettings.O(false);
               gameSettings.F(true);
               gameSettings.k(this.fieldOfView);
               entityRenderer.V(1.0F);
               entityRenderer.r(1.0F);
               stateModified = true;
               if (!this.frameTimer.hasTimeElapsed(this.frameIntervalMillis) && this.frameReady) {
                  var47 = false;
                  break label267;
               }

               if (ForgeVersion.MC_1_21_4.d()) {
                  if (this.modernFramebuffer == null) {
                     this.initializeFramebuffer();
                  }

                  int previousFramebuffer;
                  if ((previousFramebuffer = RenderBatchManager.getInstance().getTargetFramebufferId()) <= 0) {
                     previousFramebuffer = GL11.glGetInteger(36006);
                  }

                  int previousDrawFramebuffer = GL11.glGetInteger(36010);
                  GL30.glBindFramebuffer(36160, previousFramebuffer);
                  GL11.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
                  GL11.glClear(16640);
                  this.bindFramebuffer(true);
                  framebufferBound = true;
                  RenderBatchManager.getInstance().setFramebufferOverride(this.modernFramebuffer.framebufferId);
                  entityRenderer.D(Minecraft.getTimer().renderPartialTicks(), 0L);
                  RenderBatchManager.getInstance().restoreFramebufferOverride();
                  int sourceWidth = Minecraft.p().getDeltaX();
                  int sourceHeight = Minecraft.p().e();
                  this.modernFramebuffer.clear();
                  GL30.glBindFramebuffer(36008, previousFramebuffer);
                  GL30.glBindFramebuffer(36009, this.modernFramebuffer.framebufferId);
                  GL30.glBlitFramebuffer(0, 0, sourceWidth, sourceHeight, 0, 0, this.width, this.height, 16384, 9729);
                  GL30.glBindFramebuffer(36009, this.modernFramebuffer.framebufferId);
                  GL11.glColorMask(false, false, false, true);
                  GL11.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
                  GL11.glClear(16384);
                  GL11.glColorMask(true, true, true, true);
                  GL30.glBindFramebuffer(36008, previousDrawFramebuffer);
                  GL30.glBindFramebuffer(36009, previousFramebuffer);
               } else {
                  this.bindFramebuffer(true);
                  framebufferBound = true;
                  this.legacyFramebuffer.bindFramebufferTexture();
                  entityRenderer.D(Minecraft.getTimer().renderPartialTicks(), 0L);
                  FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(16);
                  GL11.glGetFloat(3106, floatBuffer);
                  GL11.glColorMask(false, false, false, true);
                  GL11.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
                  GL11.glClear(16384);
                  GL11.glColorMask(true, true, true, true);
                  GL11.glClearColor(floatBuffer.get(0), floatBuffer.get(1), floatBuffer.get(2), floatBuffer.get(3));
               }

               this.frameTimer.reset();
               this.frameReady = true;
               var47 = false;
            } catch (Exception var48) {
               Vape ignored = Minecraft.vapeInstance;
               var47 = false;
               break label266;
            } finally {
               if (var47) {
                  if (stateModified && entity != null && entityRenderer != null && gameSettings != null) {
                     if (framebufferBound) {
                        this.bindFramebuffer(false);
                     }

                     if (ForgeVersion.MC_1_21_4.v()) {
                        Minecraft.U(displayWidth);
                        Minecraft.X(displayHeight);
                     }

                     entity.H(currentYaw);
                     entity.D(previousYaw);
                     entity.C(currentPitch);
                     entity.l(previousPitch);
                     ((EntityLivingBase)entity).z(currentBodyYaw);
                     ((EntityLivingBase)entity).o(previousBodyYaw);
                     ((EntityLivingBase)entity).X(previousCurrentHeadYaw);
                     ((EntityLivingBase)entity).Y(previousHeadYaw);
                     gameSettings.I(previousThirdPersonView);
                     gameSettings.F(previousHideGui);
                     gameSettings.O(previousViewBobbing);
                     entity.H(currentX);
                     entity.n(previousX);
                     entity.C(previousLastTickX);
                     entity.u(currentY);
                     entity.w(previousY);
                     entity.L(previousLastTickY);
                     entity.l(currentZ);
                     entity.A(previousZ);
                     entity.s(previousLastTickZ);
                     gameSettings.k(previousFov);
                     entityRenderer.V(previousCameraZoom);
                     entityRenderer.r(previousCameraYaw);
                     if (ForgeVersion.MC_1_21_4.d()) {
                        GL11.glViewport(0, 0, displayWidth, displayHeight);
                     } else {
                        this.legacyFramebuffer.unbindFramebuffer();
                        Minecraft.getFrameBuffer().bindFramebuffer(true);
                     }
                  }
               }
            }

            if (entity != null && entityRenderer != null && gameSettings != null) {
               this.bindFramebuffer(false);
               if (ForgeVersion.MC_1_21_4.v()) {
                  Minecraft.U(displayWidth);
                  Minecraft.X(displayHeight);
               }

               entity.H(currentYaw);
               entity.D(previousYaw);
               entity.C(currentPitch);
               entity.l(previousPitch);
               ((EntityLivingBase)entity).z(currentBodyYaw);
               ((EntityLivingBase)entity).o(previousBodyYaw);
               ((EntityLivingBase)entity).X(previousCurrentHeadYaw);
               ((EntityLivingBase)entity).Y(previousHeadYaw);
               gameSettings.I(previousThirdPersonView);
               gameSettings.F(previousHideGui);
               gameSettings.O(previousViewBobbing);
               entity.H(currentX);
               entity.n(previousX);
               entity.C(previousLastTickX);
               entity.u(currentY);
               entity.w(previousY);
               entity.L(previousLastTickY);
               entity.l(currentZ);
               entity.A(previousZ);
               entity.s(previousLastTickZ);
               gameSettings.k(previousFov);
               entityRenderer.V(previousCameraZoom);
               entityRenderer.r(previousCameraYaw);
               if (ForgeVersion.MC_1_21_4.d()) {
                  GL11.glViewport(0, 0, displayWidth, displayHeight);
               } else {
                  this.legacyFramebuffer.unbindFramebuffer();
                  Minecraft.getFrameBuffer().bindFramebuffer(true);
               }
            }

            return;
         }

         if (entity != null && entityRenderer != null && gameSettings != null) {
            if (ForgeVersion.MC_1_21_4.v()) {
               Minecraft.U(displayWidth);
               Minecraft.X(displayHeight);
            }

            entity.H(currentYaw);
            entity.D(previousYaw);
            entity.C(currentPitch);
            entity.l(previousPitch);
            ((EntityLivingBase)entity).z(currentBodyYaw);
            ((EntityLivingBase)entity).o(previousBodyYaw);
            ((EntityLivingBase)entity).X(previousCurrentHeadYaw);
            ((EntityLivingBase)entity).Y(previousHeadYaw);
            gameSettings.I(previousThirdPersonView);
            gameSettings.F(previousHideGui);
            gameSettings.O(previousViewBobbing);
            entity.H(currentX);
            entity.n(previousX);
            entity.C(previousLastTickX);
            entity.u(currentY);
            entity.w(previousY);
            entity.L(previousLastTickY);
            entity.l(currentZ);
            entity.A(previousZ);
            entity.s(previousLastTickZ);
            gameSettings.k(previousFov);
            entityRenderer.V(previousCameraZoom);
            entityRenderer.r(previousCameraYaw);
            if (ForgeVersion.MC_1_21_4.d()) {
               GL11.glViewport(0, 0, displayWidth, displayHeight);
            } else {
               this.legacyFramebuffer.unbindFramebuffer();
               Minecraft.getFrameBuffer().bindFramebuffer(true);
            }
         }

         return;
      }

      if (stateModified && entity != null && entityRenderer != null && gameSettings != null) {
         if (framebufferBound) {
            this.bindFramebuffer(false);
         }

         if (ForgeVersion.MC_1_21_4.v()) {
            Minecraft.U(displayWidth);
            Minecraft.X(displayHeight);
         }

         entity.H(currentYaw);
         entity.D(previousYaw);
         entity.C(currentPitch);
         entity.l(previousPitch);
         ((EntityLivingBase)entity).z(currentBodyYaw);
         ((EntityLivingBase)entity).o(previousBodyYaw);
         ((EntityLivingBase)entity).X(previousCurrentHeadYaw);
         ((EntityLivingBase)entity).Y(previousHeadYaw);
         gameSettings.I(previousThirdPersonView);
         gameSettings.F(previousHideGui);
         gameSettings.O(previousViewBobbing);
         entity.H(currentX);
         entity.n(previousX);
         entity.C(previousLastTickX);
         entity.u(currentY);
         entity.w(previousY);
         entity.L(previousLastTickY);
         entity.l(currentZ);
         entity.A(previousZ);
         entity.s(previousLastTickZ);
         gameSettings.k(previousFov);
         entityRenderer.V(previousCameraZoom);
         entityRenderer.r(previousCameraYaw);
         if (ForgeVersion.MC_1_21_4.d()) {
            GL11.glViewport(0, 0, displayWidth, displayHeight);
         } else {
            this.legacyFramebuffer.unbindFramebuffer();
            Minecraft.getFrameBuffer().bindFramebuffer(true);
         }
      }
   }

   private boolean canRender() {
      return true;
   }

   public static boolean isRenderingOffscreen() {
      return renderingOffscreen;
   }

   public OffscreenRenderContext(boolean flipVertically, int width, int height) {
      this.flipVertically = flipVertically;
      this.width = width;
      this.height = height;
      this.initializeFramebuffer();
   }

   public static int[] getControlFlowMarker() {
      return controlFlowMarker;
   }

   public boolean hasFrame() {
      return this.frameReady;
   }

   protected void bindFramebuffer(boolean bind) {
      if (ForgeVersion.MC_1_21_4.d()) {
         if (this.modernFramebuffer == null) {
            this.initializeFramebuffer();
         }

         if (bind) {
            this.modernFramebuffer.bind(true);
         } else {
            this.modernFramebuffer.unbind();
         }
      } else if (bind) {
         this.legacyFramebuffer.bindFramebuffer(true);
      } else {
         this.legacyFramebuffer.unbindFramebuffer();
      }

      renderingOffscreen = bind;
   }

   public void drawFramebuffer(boolean visible, double left, double top, double right, double bottom, Color color) {
      if (ForgeVersion.MC_1_21_4.d()) {
         if (visible && this.modernFramebuffer != null && this.modernFramebuffer.colorTextureId > 0) {
            float renderX = (float)Math.min(left, right);
            float renderY = (float)Math.min(top, bottom);
            float renderWidth = (float)Math.abs(right - left);
            float renderHeight = (float)Math.abs(bottom - top);
            float minV = this.flipVertically ? 1.0F : 0.0F;
            float maxV = this.flipVertically ? 0.0F : 1.0F;
            float maxU = 1.0F;
            float minU = 0.0F;
            RenderBatchBuilder renderBatchBuilder = new RenderBatchBuilder()
               .setTexture(new GlImageTexture(this.modernFramebuffer.colorTextureId))
               .addTexturedRect(renderX, renderY, renderWidth, renderHeight, renderWidth, renderHeight, minV, maxU, maxV, minU, color);
            RenderBatchManager.getInstance().queueGuiBatch(renderBatchBuilder);
         }
      } else if (Minecraft.gameSettings().Y$src$Z$1rxemad()) {
         OpenGlBackendHolder.backend.enableCapability(2903);
         boolean textureEnabled = GL11.glIsEnabled(3553);
         boolean lightingEnabled = GL11.glIsEnabled(2896);
         boolean alphaTestEnabled = GL11.glIsEnabled(3008);
         boolean blendingEnabled = GL11.glIsEnabled(3042);
         if (!textureEnabled) {
            GlStateManager.enableTexture2D();
         }

         if (lightingEnabled) {
            GlStateManager.disableLighting();
         }

         GlStateManager.disableAlpha();
         GlStateManager.enableBlend();
         GL11.glColor4f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
         if (visible) {
            if (ForgeVersion.MC_1_21_4.d()) {
               if (this.modernFramebuffer == null || this.modernFramebuffer.colorTextureId <= 0) {
                  return;
               }

               this.modernFramebuffer.bindColorTexture();
            } else {
               this.legacyFramebuffer.bindFramebufferTexture();
            }

            if (this.flipVertically) {
               RenderUtils.J(left, top, right, bottom);
            } else {
               RenderUtils.A(left, top, right, bottom);
            }

            if (ForgeVersion.MC_1_21_4.d()) {
               this.modernFramebuffer.restorePreviousTexture();
            } else {
               this.legacyFramebuffer.unbindFramebufferTexture();
            }
         }

         if (!textureEnabled) {
            GlStateManager.disableTexture2D();
         }

         if (lightingEnabled) {
            GlStateManager.enableLighting();
         }

         if (alphaTestEnabled) {
            GlStateManager.enableAlpha();
         }

         if (!blendingEnabled) {
            GlStateManager.disableBlend();
         }
      }
   }

   public OffscreenRenderContext(boolean flipVertically) {
      this(flipVertically, 720, 400);
   }

   public static void setControlFlowMarker(int[] marker) {
      controlFlowMarker = marker;
   }

   private static Exception propagateException(Exception exception) {
      return exception;
   }

   static {
      setControlFlowMarker(null);
   }
}
