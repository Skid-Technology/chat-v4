package gg.vape.utils.render;

import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GlStateManager;
import gg.vape.wrapper.impl.Holder;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.PotionEffect;
import gg.vape.wrapper.impl.ResourceLocation;
import gg.vape.wrapper.impl.ResourceLocationConstantPair;
import gg.vape.wrapper.impl.Screen;
import gg.vape.wrapper.impl.StatusEffectSpriteUploader;
import gg.vape.wrapper.impl.TextureAtlasSprite;
import gg.vape.wrapper.impl.TextureManager;
import gg.vape.wrapper.impl.TextureObject;
import java.awt.Color;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class PotionEffectIconTexture implements PotionEffectIconRenderBackend {
   private GlFramebuffer framebuffer;

   @Override
   public void renderQueued(float x, float y, int width, int height, float opacity, boolean worldSpace) {
      RenderBatchBuilder batchBuilder = new RenderBatchBuilder(VertexCoordinateMode.DEFAULT, worldSpace)
         .setTexture(new GlImageTexture(this.framebuffer.colorTextureId))
         .addTexturedRect(x, y, width, height, 18.0F, 18.0F, 0.0F, 1.0F, 1.0F, 0.0F, new Color(1.0F, 1.0F, 1.0F, opacity));
      if (worldSpace) {
         RenderBatchManager.getInstance().queueWorldBatch(batchBuilder);
      } else {
         RenderBatchManager.getInstance().queueGuiBatch(batchBuilder);
      }
   }

   private TextureAtlasSprite resolveEffectSprite(PotionEffect effect) {
      Holder effectHolder = effect.t();
      TextureAtlasSprite sprite;
      if (ForgeVersion.MC_1_21_10.d()) {
         gg.vape.wrapper.impl.TextureAtlas textureAtlas = Minecraft.x().getAtlas(ResourceLocationConstantPair.getGui());
         sprite = textureAtlas.getSprite(Screen.getMobEffectSprite(effectHolder));
      } else if (ForgeVersion.MC_1_21_6.d()) {
         sprite = Minecraft.T().getSprite(Screen.getMobEffectSprite(effectHolder));
      } else {
         StatusEffectSpriteUploader spriteUploader = StatusEffectSpriteUploader.getPotionSprites();
         sprite = spriteUploader.getSprite(effectHolder);
      }

      return sprite;
   }

   private static TextureObject getSpriteTexture(TextureAtlasSprite sprite) {
      ResourceLocation textureLocation;
      if (ForgeVersion.MC_1_20_6.d()) {
         textureLocation = sprite.getAtlasLocation();
      } else {
         Wrapper textureAtlas = new gg.vape.wrapper.impl.TextureAtlas(sprite.getContentsOrAtlasTexture());
         textureLocation = ((gg.vape.wrapper.impl.TextureAtlas)textureAtlas).getTextureLocation();
      }

      Wrapper textureManager = Minecraft.getTextureManager();
      return ((TextureManager)textureManager).getTexture(textureLocation);
   }

   @Override
   public void dispose() {
      this.framebuffer.delete();
      this.framebuffer = null;
   }

   @Override
   public void render(float x, float y, int width, int height, float opacity) {
      this.renderQueued(x, y, width, height, opacity, false);
   }

   @Override
   public void capture(PotionEffect effect) {
      RenderBatchManager.getInstance().flushGuiBatches(0.0F);
      int iconWidth = 18;
      int iconHeight = 18;
      int previousFramebufferId = GL11.glGetInteger(36006);
      int previousTextureId = GL11.glGetInteger(32873);
      boolean scissorEnabled = GL11.glIsEnabled(3089);
      if (scissorEnabled) {
         GL11.glDisable(3089);
         ByteBuffer viewportBytes = ByteBuffer.allocateDirect(64);
         viewportBytes.order(ByteOrder.nativeOrder());
         IntBuffer viewport = viewportBytes.asIntBuffer();
         gg.vape.wrapper.impl.GL11.X(2978, viewport);
         this.framebuffer = new GlFramebuffer(iconWidth, iconHeight, true);
         this.framebuffer.bind(true);
         GlStateManager.enableDepth();
         GlStateManager.enableBlend();
         GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
         GL11.glClear(16384);
         GL11.glClear(256);
         OpenGlBackendHolder.backend.pushMatrix();
         OpenGlBackendHolder.backend.translate(0.0, -2.0, 0.0);
         OpenGlBackendHolder.backend.scale((double)Minecraft.J() / (iconWidth * 2.0F), (double)Minecraft.h() / (iconWidth * 2.0F), 0.0);
         TextureAtlasSprite sprite = this.resolveEffectSprite(effect);
         TextureObject texture = getSpriteTexture(sprite);
         float[] textureCoordinates = sprite.getTextureCoordinates();
         GlScissorRect previousScissorRect = BufferedGuiRenderPrimitives.scissorRect;
         BufferedGuiRenderPrimitives.scissorRect = null;
         RenderBatchBuilder batchBuilder = new RenderBatchBuilder()
            .setTexture(new GlImageTexture(texture.getId()))
            .addTexturedRect(
               0.0F,
               -1.0F,
               iconWidth,
               iconHeight,
               iconWidth,
               iconHeight,
               textureCoordinates[0],
               textureCoordinates[2],
               textureCoordinates[1],
               textureCoordinates[3],
               Color.WHITE
            );
         RenderBatchManager batchManager = RenderBatchManager.getInstance();
         batchManager.queueGuiBatch(batchBuilder);
         batchManager.setFramebufferOverride(this.framebuffer.framebufferId);
         batchManager.flushGuiBatches(0.0F);
         batchManager.restoreFramebufferOverride();
         BufferedGuiRenderPrimitives.scissorRect = previousScissorRect;
         this.framebuffer.bindColorTexture();
         this.framebuffer.unbind();
         GL11.glViewport(viewport.get(0), viewport.get(1), viewport.get(2), viewport.get(3));
         GL30.glBindFramebuffer(36160, previousFramebufferId);
         GlStateManager.bindTexture(previousTextureId);
         GL11.glEnable(3089);
         OpenGlBackendHolder.backend.popMatrix();
      } else {
         ByteBuffer viewportBytes = ByteBuffer.allocateDirect(64);
         viewportBytes.order(ByteOrder.nativeOrder());
         IntBuffer viewport = viewportBytes.asIntBuffer();
         gg.vape.wrapper.impl.GL11.X(2978, viewport);
         this.framebuffer = new GlFramebuffer(iconWidth, iconHeight, true);
         this.framebuffer.bind(true);
         GlStateManager.enableDepth();
         GlStateManager.enableBlend();
         GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
         GL11.glClear(16384);
         GL11.glClear(256);
         OpenGlBackendHolder.backend.pushMatrix();
         OpenGlBackendHolder.backend.translate(0.0, -2.0, 0.0);
         OpenGlBackendHolder.backend.scale((double)Minecraft.J() / (iconWidth * 2.0F), (double)Minecraft.h() / (iconWidth * 2.0F), 0.0);
         TextureAtlasSprite sprite = this.resolveEffectSprite(effect);
         TextureObject texture = getSpriteTexture(sprite);
         float[] textureCoordinates = sprite.getTextureCoordinates();
         GlScissorRect previousScissorRect = BufferedGuiRenderPrimitives.scissorRect;
         BufferedGuiRenderPrimitives.scissorRect = null;
         RenderBatchBuilder batchBuilder = new RenderBatchBuilder()
            .setTexture(new GlImageTexture(texture.getId()))
            .addTexturedRect(
               0.0F,
               -1.0F,
               iconWidth,
               iconHeight,
               iconWidth,
               iconHeight,
               textureCoordinates[0],
               textureCoordinates[2],
               textureCoordinates[1],
               textureCoordinates[3],
               Color.WHITE
            );
         RenderBatchManager batchManager = RenderBatchManager.getInstance();
         batchManager.queueGuiBatch(batchBuilder);
         batchManager.setFramebufferOverride(this.framebuffer.framebufferId);
         batchManager.flushGuiBatches(0.0F);
         batchManager.restoreFramebufferOverride();
         BufferedGuiRenderPrimitives.scissorRect = previousScissorRect;
         this.framebuffer.bindColorTexture();
         this.framebuffer.unbind();
         GL11.glViewport(viewport.get(0), viewport.get(1), viewport.get(2), viewport.get(3));
         GL30.glBindFramebuffer(36160, previousFramebufferId);
         GlStateManager.bindTexture(previousTextureId);
         OpenGlBackendHolder.backend.popMatrix();
      }
   }
}
