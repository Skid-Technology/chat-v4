package gg.vape.utils.render;

import gg.vape.Vape;
import gg.vape.module.utility.inventory.cleaner.InventoryItemMatcher;
import gg.vape.module.utility.inventory.cleaner.InventoryItemMatcherGroup;
import gg.vape.module.utility.inventory.cleaner.InventoryItemMatcherRegistry;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.unmap.ImageParser$Format;
import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import org.lwjgl.opengl.GL11;

public class ImageRenderer {
   public static HashMap<String, GlImageTexture> textureCache = new HashMap<>();
   private static boolean texture2dWasEnabled = false;
   private static boolean batchActive;
   private static boolean blendingWasEnabled = false;
   private static GuiComponent[] legacyComponents;

   public static double getImageHeight(String resourceName) {
      if (GuiRenderPrimitives.d()) {
         TextureAtlas textureAtlas = TextureAtlasRegistry.getInstance().get("vape_texture");
         if (textureAtlas.getRegion(resourceName) == null) {
            loadImage(resourceName);
         }

         TextureAtlasRegion atlasRegion = textureAtlas.getRegion(resourceName);
         return atlasRegion != null ? atlasRegion.height : 0.0;
      } else {
         GlImageTexture texture = loadImage(resourceName);
         return texture != null ? texture.height : 0.0;
      }
   }

   private static GlImageTexture loadImage(String resourceName) {
      return loadResource(resourceName, false, false);
   }

   public static void drawRes(Color color, float x, float y, String resourceName, float scale) {
      drawResWithShadow(color, x, y, resourceName, scale, true);
   }

   public static void beginBatch() {
      batchActive = true;
      OpenGlBackendHolder.backend.pushMatrix();
      blendingWasEnabled = OpenGlBackendHolder.backend.isCapabilityEnabled(3042);
      texture2dWasEnabled = OpenGlBackendHolder.backend.isCapabilityEnabled(3553);
      if (!blendingWasEnabled) {
         OpenGlBackendHolder.backend.enableCapability(3042);
      }

      if (!texture2dWasEnabled) {
         OpenGlBackendHolder.backend.enableCapability(3553);
      }
   }

   public static void endBatch() {
      RenderUtils.w(Color.white);
      if (!blendingWasEnabled) {
         OpenGlBackendHolder.backend.disableCapability(3042);
      }

      if (!texture2dWasEnabled) {
         OpenGlBackendHolder.backend.disableCapability(3553);
      }

      OpenGlBackendHolder.backend.popMatrix();
      batchActive = false;
   }

   public static GlImageTexture loadResource(String resourceName, boolean useMipmaps, boolean whiteMask) {
      TextureAtlas textureAtlas = GuiRenderPrimitives.d() ? TextureAtlasRegistry.getInstance().getActiveAtlas() : null;
      if (textureAtlas != null) {
         TextureAtlasRegion atlasRegion = textureAtlas.getRegion(resourceName);
         if (atlasRegion != null) {
            return createRegionTexture(textureAtlas, atlasRegion);
         }
      } else {
         GlImageTexture cachedTexture = textureCache.get(resourceName);
         if (cachedTexture != null) {
            return cachedTexture;
         }
      }

      try {
         String resourcePath = "textures/" + resourceName + ".png";
         byte[] resourceData = Vape.readResource(resourcePath);
         if (resourceData != null && resourceData.length != 0) {
            if (textureAtlas != null) {
               textureAtlas.addImage(resourceName, resourceData, whiteMask);
               return createRegionTexture(textureAtlas, textureAtlas.getRegion(resourceName));
            } else {
               GlImageTexture texture = new GlImageTexture(
                  new ByteArrayInputStream(resourceData), useMipmaps ? 9987 : 9729, whiteMask ? ImageParser$Format.WHITE : ImageParser$Format.RGBA
               );
               textureCache.put(resourceName, texture);
               return texture;
            }
         } else if ("world".equals(resourceName)) {
            return null;
         } else {
            GlImageTexture fallbackTexture = loadImage("world");
            if (fallbackTexture != null) {
               textureCache.put(resourceName, fallbackTexture);
            }

            return fallbackTexture;
         }
      } catch (IOException var7) {
         Vape.logThrowable(var7);
         return null;
      }
   }

   public static String xorString(String input, int key) {
      String decoded = "";

      for (int index = 0; index < input.length(); index++) {
         char character = input.charAt(index);
         decoded = decoded + String.valueOf((char)(character ^ key));
      }

      return decoded;
   }

   public static void drawVerticalGradientTexture(GlImageTexture texture, float x, float y, float width, float height, Color topColor, Color bottomColor) {
      float aspectRatio = (float)texture.width / texture.height;
      width *= aspectRatio;
      texture.bind();
      float minU = 0.0F;
      float minV = 0.0F;
      float maxU = 1.0F;
      float maxV = 1.0F;
      GL11.glShadeModel(7425);
      GL11.glBegin(7);
      GL11.glColor4d(topColor.getRed() / 255.0, topColor.getGreen() / 255.0, topColor.getBlue() / 255.0, topColor.getAlpha() / 255.0);
      GL11.glTexCoord2f(maxU, minV);
      GL11.glVertex2f(x + width, y);
      GL11.glTexCoord2f(minU, minV);
      GL11.glVertex2f(x, y);
      GL11.glColor4d(bottomColor.getRed() / 255.0, bottomColor.getGreen() / 255.0, bottomColor.getBlue() / 255.0, bottomColor.getAlpha() / 255.0);
      GL11.glTexCoord2f(minU, maxV);
      GL11.glVertex2f(x, y + height);
      GL11.glTexCoord2f(maxU, maxV);
      GL11.glVertex2f(x + width, y + height);
      GL11.glEnd();
      GL11.glShadeModel(7424);
   }

   public static void preloadResources() {
      loadResource("vapelogo", true, false);
      loadResource("v4", true, false);
      loadResource("lmb", true, false);
      loadResource("rmb", true, false);
      loadResource("mmb", true, false);
      loadResource("up", false, true);
      loadResource("up2", false, true);
      loadResource("down", false, true);
      loadResource("left", false, true);
      loadResource("right", false, true);
      loadResource("party1@2x", false, true);
      loadResource("synced@2x", false, true);
      loadResource("party@2x", false, true);
      loadResource("default_user", false, false);
      loadResource("chat@2x", false, true);
      loadResource("triangle", true, false);
      loadResource("ping_location", true, false);
      loadResource("icons8_downloading_updates", false, true);
      loadResource("online", false, false);
      loadResource("submit@2x", false, true);
      loadResource("legit_primary", true, true);

      for (InventoryItemMatcherGroup matcherGroup : InventoryItemMatcherGroup.VALUES) {
         if (matcherGroup.getIconName() != null) {
            loadResource(matcherGroup.getIconName(), false, true);
         }
      }

      for (InventoryItemMatcher inventoryItemMatcher : InventoryItemMatcherRegistry.getAll()) {
         if (inventoryItemMatcher.getIconName() != null) {
            loadResource(inventoryItemMatcher.getIconName(), false, true);
         }
      }

      loadResource("other@2x", false, true);
   }

   public static double getImageWidth(String resourceName) {
      if (GuiRenderPrimitives.d()) {
         TextureAtlas textureAtlas = TextureAtlasRegistry.getInstance().get("vape_texture");
         if (textureAtlas.getRegion(resourceName) == null) {
            loadImage(resourceName);
         }

         TextureAtlasRegion atlasRegion = textureAtlas.getRegion(resourceName);
         return atlasRegion != null ? atlasRegion.width : 0.0;
      } else {
         GlImageTexture texture = loadImage(resourceName);
         return texture != null ? texture.width : 0.0;
      }
   }

   public static void drawImage(Color topColor, Color bottomColor, float x, float y, String resourceName, float width, float height, boolean shadow) {
      GlImageTexture texture = loadImage(resourceName);
      drawImageInternal(topColor, bottomColor, x, y, texture, resourceName, width, height, shadow, -1.0F);
   }

   public static void drawResWithShadow(Color color, float x, float y, String resourceName, float scale, boolean shadow) {
      GlImageTexture texture = loadImage(resourceName);
      double inverseScale = 1.0 / scale;
      x = (float)(x * inverseScale);
      y = (float)(y * inverseScale);
      drawImageInternal(color, null, x, y, texture, resourceName, 32.0F, 32.0F, shadow, scale);
   }

   public static void drawImage(Color color, float x, float y, String resourceName, float width, float height, boolean shadow) {
      GlImageTexture texture = loadImage(resourceName);
      drawImageInternal(color, null, x, y, texture, resourceName, width, height, shadow, -1.0F);
   }

   private static Exception propagateException(Exception exception) {
      return exception;
   }

   public static GuiComponent[] getLegacyComponents() {
      return legacyComponents;
   }

   public static void drawImageInternal(
      Color color, Color bottomColor, float x, float y, GlImageTexture texture, String resourceName, float width, float height, boolean shadow, float scale
   ) {
      if (texture != null) {
         if (GuiRenderPrimitives.d()) {
            BufferedGuiRenderPrimitives.drawAtlasTexture(x, y, width, height, texture, resourceName, scale, color, bottomColor, shadow);
         } else {
            boolean blendingWasEnabled = false;
            boolean texture2dWasEnabled = false;
            if (!batchActive) {
               GL11.glPushMatrix();
               blendingWasEnabled = GL11.glIsEnabled(3042);
               texture2dWasEnabled = GL11.glIsEnabled(3553);
               if (!blendingWasEnabled) {
                  OpenGlBackendHolder.backend.enableCapability(3042);
               }

               if (!texture2dWasEnabled) {
                  OpenGlBackendHolder.backend.enableCapability(3553);
               }
            }

            if (Math.signum(scale) >= 0.0F) {
               GL11.glScaled(scale, scale, scale);
            }

            if (shadow) {
               RenderUtils.w(new Color(0, 0, 0, 150));
               drawTextureQuad(texture, x + 0.5F, y + 0.5F, width, height);
            }

            RenderUtils.w(color);
            if (bottomColor == null) {
               drawTextureQuad(texture, x, y, width, height);
            } else {
               drawVerticalGradientTexture(texture, x, y, width, height, color, bottomColor);
            }

            if (Math.signum(scale) >= 0.0F) {
               GL11.glScaled(1.0, 1.0, 1.0);
            }

            if (!batchActive) {
               RenderUtils.w(Color.white);
               if (!blendingWasEnabled) {
                  OpenGlBackendHolder.backend.disableCapability(3042);
               }

               if (!texture2dWasEnabled) {
                  OpenGlBackendHolder.backend.disableCapability(3553);
               }

               GL11.glPopMatrix();
            }
         }
      }
   }

   public static void drawTexture(Color color, float x, float y, GlImageTexture texture, float width, float height, boolean shadow) {
      drawImageInternal(color, null, x, y, texture, null, width, height, shadow, -1.0F);
   }

   private static GlImageTexture createRegionTexture(TextureAtlas textureAtlas, TextureAtlasRegion atlasRegion) {
      GlImageTexture atlasTexture = textureAtlas.getTexture();
      return atlasTexture != null && atlasRegion != null
         ? new GlImageTexture(
            atlasTexture.textureId, atlasRegion.width, atlasRegion.height, atlasRegion.minU, atlasRegion.minV, atlasRegion.maxU, atlasRegion.maxV
         )
         : atlasTexture;
   }

   public static void drawTextureQuad(GlImageTexture texture, float x, float y, float width, float height) {
      if (width == height) {
         float aspectRatio = (float)texture.width / texture.height;
         width *= aspectRatio;
      }

      texture.bind();
      float minU = 0.0F;
      float minV = 0.0F;
      float maxU = 1.0F;
      float maxV = 1.0F;
      GL11.glBegin(7);
      GL11.glTexCoord2f(maxU, minV);
      GL11.glVertex2f(x + width, y);
      GL11.glTexCoord2f(minU, minV);
      GL11.glVertex2f(x, y);
      GL11.glTexCoord2f(minU, maxV);
      GL11.glVertex2f(x, y + height);
      GL11.glTexCoord2f(maxU, maxV);
      GL11.glVertex2f(x + width, y + height);
      GL11.glEnd();
   }

   public static void setLegacyComponents(GuiComponent[] components) {
      legacyComponents = components;
   }

   static {
      setLegacyComponents(new GuiComponent[5]);
   }
}
