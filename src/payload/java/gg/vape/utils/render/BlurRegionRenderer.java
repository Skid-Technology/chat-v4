package gg.vape.utils.render;

import gg.vape.Vape;
import gg.vape.wrapper.impl.GlStateManager;
import gg.vape.wrapper.impl.Minecraft;
import java.nio.ByteBuffer;
import java.util.function.Supplier;
import org.lwjgl.opengl.GL11;

public class BlurRegionRenderer {
   private int textureId = -1;
   private int regionWidth;
   private boolean textureInitialized = false;
   private static boolean legacyGlobalFlag;
   private int regionHeight;

   public BlurRegionRenderer(int width, int height) {
      this.regionWidth = width;
      this.regionHeight = height;
   }

   private Void copyHorizontalPassSource(int x, float captureScale, int sourceY) {
      GL11.glBindTexture(3553, this.textureId);
      GL11.glCopyTexSubImage2D(3553, 0, 0, 0, (int)(x * captureScale), sourceY, this.regionWidth, this.regionHeight);
      return null;
   }

   public void setDimensions(int width, int height) {
      double uiScale = Vape.INSTANCE.getClientSettings().getGuiScaleFactor();
      width = (int)(width * uiScale);
      height = (int)(height * uiScale);
      if (this.regionWidth != width || this.regionHeight != height) {
         this.regionWidth = width;
         this.regionHeight = height;
         this.textureInitialized = false;
      }
   }

   public static boolean getLegacyGlobalFlag() {
      return legacyGlobalFlag;
   }

   public static void setLegacyGlobalFlag(boolean enabled) {
      legacyGlobalFlag = enabled;
   }

   public void renderBlur(int x, int y, float blurRadius, float inset) {
      if (Minecraft.gameSettings().Y$src$Z$1rxemad()) {
         float captureScale = 2.0F;
         double uiScale = Vape.INSTANCE.getClientSettings().getGuiScaleFactor();
         x = (int)(x * uiScale);
         y = (int)(y * uiScale);
         int capturedX = x;
         int framebufferHeight = Minecraft.h();
         int sourceY = (int)(framebufferHeight - y * captureScale - this.regionHeight);
         if (!this.textureInitialized) {
            this.initializeTexture();
         }

         float renderScale = 0.5F;
         renderScale = (float)(renderScale / uiScale);
         OpenGlBackendHolder.backend.pushMatrix();
         if (!GuiRenderPrimitives.d()) {
            OpenGlBackendHolder.backend.scale(renderScale, renderScale, renderScale);
         }

         if (!GuiRenderPrimitives.d()) {
            GL11.glBindTexture(3553, this.textureId);
         }

         if (GuiRenderPrimitives.d()) {
            Supplier<Void> drawSetupCallback = () -> this.copyHorizontalPassSource(capturedX, captureScale, sourceY);
            if (this.regionWidth == 0 || this.regionHeight == 0) {
               return;
            }

            RenderBatchBuilder blurBatch = new RenderBatchBuilder()
               .setDrawSetupCallback(drawSetupCallback)
               .setTexture(new GlImageTexture(this.textureId))
               .addBlurPass(x, y, this.regionWidth / 2.0F, this.regionHeight / 2.0F, blurRadius, inset, 1.0F, 0.0F);
            RenderBatchManager.getInstance().queueGuiBatch(blurBatch);
         } else {
            GL11.glCopyTexSubImage2D(3553, 0, 0, 0, (int)(x * captureScale), sourceY, this.regionWidth, this.regionHeight);
            GuiRenderPrimitives.v(x * captureScale, y * captureScale, this.regionWidth, this.regionHeight, blurRadius, inset, 1.0F, 0);
         }

         if (GuiRenderPrimitives.d()) {
            Supplier<Void> drawSetupCallback = () -> this.copyVerticalPassSource(capturedX, captureScale, sourceY);
            if (this.regionWidth == 0 || this.regionHeight == 0) {
               return;
            }

            RenderBatchBuilder blurBatch = new RenderBatchBuilder()
               .setDrawSetupCallback(drawSetupCallback)
               .setTexture(new GlImageTexture(this.textureId))
               .addBlurPass(x, y, this.regionWidth / 2.0F, this.regionHeight / 2.0F, blurRadius, inset, 1.0F, 1.0F);
            RenderBatchManager.getInstance().queueGuiBatch(blurBatch);
         } else {
            GL11.glCopyTexSubImage2D(3553, 0, 0, 0, (int)(x * captureScale), sourceY, this.regionWidth, this.regionHeight);
            GuiRenderPrimitives.v(x * captureScale, y * captureScale, this.regionWidth, this.regionHeight, blurRadius, inset, 1.0F, 1);
         }

         OpenGlBackendHolder.backend.popMatrix();
         GlStateManager.bindTexture(0);
      }
   }

   public static boolean isBlurAvailable() {
      boolean ignoredLegacyFlag = getLegacyGlobalFlag();
      return true;
   }

   private Void copyVerticalPassSource(int x, float captureScale, int sourceY) {
      GL11.glBindTexture(3553, this.textureId);
      GL11.glCopyTexSubImage2D(3553, 0, 0, 0, (int)(x * captureScale), sourceY, this.regionWidth, this.regionHeight);
      return null;
   }

   private void initializeTexture() {
      this.textureId = GL11.glGenTextures();
      GL11.glBindTexture(3553, this.textureId);
      GL11.glTexParameteri(3553, 10241, 9729);
      GL11.glTexParameteri(3553, 10240, 9729);
      GL11.glTexParameteri(3553, 10242, 33071);
      GL11.glTexParameteri(3553, 10243, 33071);
      GL11.glTexImage2D(3553, 0, 6407, this.regionWidth, this.regionHeight, 0, 6407, 5121, (ByteBuffer)null);
      this.textureInitialized = true;
   }

   static {
      setLegacyGlobalFlag(false);
   }
}
