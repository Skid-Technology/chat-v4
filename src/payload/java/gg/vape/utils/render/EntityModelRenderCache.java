package gg.vape.utils.render;

import gg.vape.Vape;
import gg.vape.wrapper.impl.EntityLivingBase;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.ResourceLocation;
import java.awt.Color;
import java.util.HashMap;

public class EntityModelRenderCache {
   private static boolean enabled;
   static HashMap<EntityModelRenderCacheKey, EntityModelRenderBackend> renderers = new HashMap<>();

   public static void setEnabled(boolean enabled) {
      EntityModelRenderCache.enabled = enabled;
   }

   public static void renderEntity(EntityLivingBase entity, float x, float y, int width, int height, Color color, float cornerRadius) {
      try {
         EntityModelRenderCacheKey cacheKey = new EntityModelRenderCacheKey(entity);
         ensureEntityCached(entity);
         renderers.get(cacheKey).render(x, y, width, height, color, cornerRadius);
      } catch (Exception var8) {
         Vape.logThrowable(var8);
      }
   }

   public static void ensureTextureCached(ResourceLocation texture, String identifier) {
      EntityModelRenderCacheKey cacheKey = new EntityModelRenderCacheKey(identifier);
      if (!renderers.containsKey(cacheKey)) {
         cacheTexture(texture, cacheKey);
      }
   }

   private static void cacheTexture(ResourceLocation texture, EntityModelRenderCacheKey cacheKey) {
      EntityModelRenderBackend renderer = createRenderer();
      renderer.captureTexture(texture);
      renderers.put(cacheKey, renderer);
   }

   public static void ensureEntityCached(EntityLivingBase entity) {
      EntityModelRenderCacheKey cacheKey = new EntityModelRenderCacheKey(entity);
      if (!renderers.containsKey(cacheKey)) {
         cacheEntity(entity, cacheKey);
      }
   }

   public static void clear() {
      for (EntityModelRenderBackend renderer : renderers.values()) {
         renderer.dispose();
      }

      renderers.clear();
   }

   public static boolean isEnabled() {
      return enabled;
   }

   public static boolean isSupported() {
      boolean cacheEnabled = isEnabled();
      return true;
   }

   public static ResourceLocation getDefaultSkinTexture() {
      String texturePath = ForgeVersion.MC_1_21_4.d() ? "textures/entity/player/wide/steve.png" : "textures/entity/steve.png";
      return ResourceLocation.create(texturePath);
   }

   private static void cacheEntity(EntityLivingBase entity, EntityModelRenderCacheKey cacheKey) {
      EntityModelRenderBackend renderer = createRenderer();
      renderer.captureEntity(entity);
      renderers.put(cacheKey, renderer);
   }

   private static EntityModelRenderBackend createRenderer() {
      return (EntityModelRenderBackend)(GuiRenderPrimitives.d() ? new Post117EntityModelFramebufferRenderer() : new EntityModelFramebufferRenderer());
   }

   public static void renderTexture(ResourceLocation texture, String identifier, float x, float y, int width, int height, Color color, float cornerRadius) {
      try {
         EntityModelRenderCacheKey cacheKey = new EntityModelRenderCacheKey(identifier);
         ensureTextureCached(texture, identifier);
         renderers.get(cacheKey).render(x, y, width, height, color, cornerRadius);
      } catch (Exception var9) {
         Vape.logThrowable(var9);
      }
   }

   static {
      setEnabled(false);
   }
}
