package gg.vape.utils.render;

import gg.vape.Vape;
import java.awt.Color;
import java.io.FileInputStream;
import java.io.IOException;

public class LocalImageDebugRenderer {
   private static String legacyStatus;
   static final String DEBUG_IMAGE_PATH = "C:\\Users\\Moham\\Desktop\\pngs\\Group 5709.png";

   public static String getLegacyStatus() {
      return legacyStatus;
   }

   private static Exception propagateException(Exception exception) {
      return exception;
   }

   public static GlImageTexture loadDebugTexture() {
      GlImageTexture texture = null;

      try {
         texture = new GlImageTexture(new FileInputStream(DEBUG_IMAGE_PATH));
      } catch (IOException var2) {
         var2.printStackTrace();
      }

      return texture;
   }

   public static void renderDebugImage() {
      try {
         GlImageTexture texture = loadDebugTexture();
         if (texture != null) {
            Vape.debugLog("drawing");
            ImageRenderer.drawTexture(Color.BLACK, 10.0F, 10.0F, texture, 20.0F, 20.0F, true);
         }
      } catch (Exception var1) {
         Vape.logThrowable(var1);
      }
   }

   public static void setLegacyStatus(String status) {
      legacyStatus = status;
   }

   static {
      setLegacyStatus(null);
   }
}
