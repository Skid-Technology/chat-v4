package gg.vape.utils.render;

import gg.vape.Vape;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.Map;

public class RemoteImageHttpDownloader {
   private static final Map<String, byte[]> imageCache = new LinkedHashMap<>();
   private static final String CACHE_DIRECTORY = System.getProperty("user.home") + File.separator + "vapeTextures" + File.separator;
   private static boolean enabled;

   public static byte[] loadCachedImage(String imageName) {
      File cacheDirectory = new File(CACHE_DIRECTORY);
      if (!cacheDirectory.exists()) {
         cacheDirectory.mkdirs();
      }

      if (imageCache.containsKey(imageName)) {
         return imageCache.get(imageName);
      } else {
         try {
            byte[] imageData = Files.readAllBytes(new File(CACHE_DIRECTORY + imageName + ".png").toPath());
            imageCache.put(imageName, imageData);
            return imageData;
         } catch (IOException var3) {
            Vape.logThrowable(var3);
            imageCache.put(imageName, null);
            return null;
         }
      }
   }

   public static void setEnabled(boolean enabled) {
      RemoteImageHttpDownloader.enabled = enabled;
   }

   private static Exception propagateException(Exception exception) {
      return exception;
   }

   public static boolean legacyAlwaysFalseCheck() {
      boolean enabled = isEnabled();
      return false;
   }

   public static boolean isEnabled() {
      return enabled;
   }

   static {
      setEnabled(true);
   }
}
