package gg.vape.utils.render;

import gg.vape.Vape;
import gg.vape.mapping.MappedClasses;
import gg.vape.wrapper.impl.AbstractClientPlayer;
import gg.vape.wrapper.impl.EntityLivingBase;
import gg.vape.wrapper.impl.GlStateManager;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.Render;
import gg.vape.wrapper.impl.ResourceLocation;
import gg.vape.wrapper.impl.TextureManager;
import gg.vape.wrapper.impl.TextureObject;
import java.awt.Color;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Post117EntityModelFramebufferRenderer implements EntityModelRenderBackend {
   private static final String CAPTURE_ERROR_PREFIX = "[CachedFace] Exception during capture: ";
   GlFramebuffer framebuffer;
   private boolean fallbackAttempted = false;

   private void captureDefaultTextureFallback() {
      if ((this.framebuffer == null || this.framebuffer.colorTextureId <= 0) && !this.fallbackAttempted) {
         this.fallbackAttempted = true;
         this.captureTextureInternal(EntityModelRenderCache.getDefaultSkinTexture());
      }
   }

   private void captureTextureInternal(ResourceLocation texture) {
      int textureWidth = 32;
      int textureHeight = 32;

      try {
         RenderBatchManager batchManager = RenderBatchManager.getInstance();
         batchManager.flushGuiBatches(0.0F);
         int previousVertexArrayId = GL11.glGetInteger(34229);
         int previousShaderProgramId = GL11.glGetInteger(35725);
         int previousTextureId = GL11.glGetInteger(32873);
         int previousFramebufferId = GL11.glGetInteger(36006);
         int previousArrayBufferId = GL11.glGetInteger(34964);
         int previousElementArrayBufferId = GL11.glGetInteger(34965);
         boolean scissorEnabled = GL11.glIsEnabled(3089);
         if (scissorEnabled) {
            GL11.glDisable(3089);
         }

         ByteBuffer viewportBytes = ByteBuffer.allocateDirect(64);
         viewportBytes.order(ByteOrder.nativeOrder());
         IntBuffer viewport = viewportBytes.asIntBuffer();
         gg.vape.wrapper.impl.GL11.X(2978, viewport);
         this.framebuffer = new GlFramebuffer(textureWidth, textureHeight, true);
         this.framebuffer.bind(true);
         GlStateManager.enableBlend();
         GlStateManager.Y(770, 771);
         GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
         GL11.glClear(16640);
         TextureManager textureManager = Minecraft.getTextureManager();
         TextureObject textureObject = textureManager.getTexture(texture);
         if (textureObject == null || textureObject.isNull() || textureObject.getId() <= 0) {
            this.framebuffer.unbind();
            GL11.glViewport(viewport.get(0), viewport.get(1), viewport.get(2), viewport.get(3));
            GL30.glBindFramebuffer(36160, previousFramebufferId);
            GlStateManager.bindTexture(previousTextureId);
            if (scissorEnabled) {
               GL11.glEnable(3089);
            }

            this.captureDefaultTextureFallback();
            return;
         }

         float textureScaleU = 0.00390625F;
         float textureScaleV = 0.00390625F;
         RenderBatchBuilder batchBuilder = new RenderBatchBuilder()
            .setTexture(new GlImageTexture(textureObject.getId()))
            .addTexturedRect(
               0.0F,
               0.0F,
               32.0F,
               32.0F,
               textureWidth,
               textureHeight,
               32.0F * textureScaleU,
               (32 + textureHeight) * textureScaleV,
               (32 + textureWidth) * textureScaleU,
               32.0F * textureScaleV,
               Color.WHITE
            );
         RenderMatrix4f previousProjectionMatrix = BufferedGuiRenderPrimitives.projectionMatrix;
         RenderMatrix4f previousViewMatrix = BufferedGuiRenderPrimitives.viewMatrix;
         RenderMatrixStack previousMatrixStack = BufferedGuiRenderPrimitives.matrixStack;
         BufferedGuiRenderPrimitives.projectionMatrix = new RenderMatrix4f()
            .setIdentity()
            .setOrthographic(0.0F, textureWidth, textureHeight, 0.0F, -21000.0F, 21000.0F);
         BufferedGuiRenderPrimitives.viewMatrix = new RenderMatrix4f().setIdentity();
         BufferedGuiRenderPrimitives.matrixStack = new RenderMatrixStack();
         int textureId = textureObject.getId();
         GL11.glBindTexture(3553, textureId);
         int previousMinFilter = GL11.glGetTexParameteri(3553, 10241);
         int previousMagFilter = GL11.glGetTexParameteri(3553, 10240);
         GL11.glTexParameteri(3553, 10241, 9728);
         GL11.glTexParameteri(3553, 10240, 9728);
         RenderBatchBuffer batchBuffer = batchManager.getBatchBuffer();
         RenderBatch batch = new RenderBatch(batchBuilder);
         batchBuffer.bindResources();
         batchBuffer.stageBatch(batch);
         batchBuffer.draw();
         GL30.glBindVertexArray(0);
         GL11.glBindTexture(3553, textureId);
         GL11.glTexParameteri(3553, 10241, previousMinFilter);
         GL11.glTexParameteri(3553, 10240, previousMagFilter);
         BufferedGuiRenderPrimitives.projectionMatrix = previousProjectionMatrix;
         BufferedGuiRenderPrimitives.viewMatrix = previousViewMatrix;
         BufferedGuiRenderPrimitives.matrixStack = previousMatrixStack;
         this.framebuffer.unbind();
         GL11.glViewport(viewport.get(0), viewport.get(1), viewport.get(2), viewport.get(3));
         GL30.glBindVertexArray(previousVertexArrayId);
         GL20.glUseProgram(previousShaderProgramId);
         GL11.glBindTexture(3553, previousTextureId);
         GL30.glBindFramebuffer(36160, previousFramebufferId);
         GL15.glBindBuffer(34962, previousArrayBufferId);
         GL15.glBindBuffer(34963, previousElementArrayBufferId);
         if (scissorEnabled) {
            GL11.glEnable(3089);
         }
      } catch (Exception var27) {
         Vape.debugLog("[CachedFace] Exception during capture: " + var27.getMessage());
         Vape.logThrowable(var27);
      }
   }

   @Override
   public void render(float x, float y, int width, int height, Color color, float cornerRadius) {
      if (this.framebuffer != null && this.framebuffer.colorTextureId > 0) {
         BufferedGuiRenderPrimitives.drawRoundedTexturedRect(
            x, y, width, height, cornerRadius, 1.0F, color, new GlImageTexture(this.framebuffer.colorTextureId)
         );
      }
   }

   private static Exception identityException(Exception exception) {
      return exception;
   }

   @Override
   public void captureTexture(ResourceLocation texture) {
      this.captureTextureInternal(texture);
   }

   @Override
   public void dispose() {
      this.framebuffer.delete();
      this.framebuffer = null;
   }

   @Override
   public void captureEntity(EntityLivingBase entity) {
      ResourceLocation texture = EntityModelRenderCache.getDefaultSkinTexture();
      if (entity != null) {
         if (entity.isInstance(MappedClasses.zt)) {
            AbstractClientPlayer clientPlayer = new AbstractClientPlayer(entity);
            texture = clientPlayer.O();
         } else {
            Render entityRenderer = Minecraft.D().getEntityRenderObject(entity);
            texture = entityRenderer.getEntityTexture(entity);
         }
      }

      this.captureTextureInternal(texture);
   }
}
