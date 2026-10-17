package gg.vape.utils.render;

import java.util.concurrent.ConcurrentHashMap;

public class RemoteImageTextureManager {
   private ConcurrentHashMap<Integer, RemoteImageTextureCache> cachesBySize = new ConcurrentHashMap<>();
   private static final String DEFAULT_AVATAR_RESOURCE;
   private static RemoteImageTextureManager instance;

   private static String decodeUtf8(byte[] bytes) {
      int characterCount = 0;
      int byteCount = bytes.length;
      char[] characters = new char[byteCount];

      for (int byteIndex = 0; byteIndex < byteCount; byteIndex++) {
         int currentByte = 255 & bytes[byteIndex];
         if (currentByte < 192) {
            characters[characterCount++] = (char)currentByte;
         } else if (currentByte < 224) {
            char character = (char)((char)(currentByte & 31) << 6);
            int var12 = bytes[++byteIndex];
            character = (char)(character | (char)(var12 & 63));
            characters[characterCount++] = character;
         } else if (byteIndex < byteCount - 2) {
            char character = (char)((char)(currentByte & 15) << '\f');
            int var13 = bytes[++byteIndex];
            character = (char)(character | (char)(var13 & 63) << 6);
            var13 = bytes[++byteIndex];
            character = (char)(character | (char)(var13 & 63));
            characters[characterCount++] = character;
         }
      }

      return new String(characters, 0, characterCount);
   }

   static ConcurrentHashMap<Integer, RemoteImageTextureCache> getCaches(RemoteImageTextureManager manager) {
      return manager.cachesBySize;
   }

   public GlImageTexture getTexture(String username, int size) {
      if (this.cachesBySize.containsKey(size)) {
         GlImageTexture texture = this.cachesBySize.get(size).getTexture(username);
         return texture == null ? ImageRenderer.loadResource(DEFAULT_AVATAR_RESOURCE, false, false) : texture;
      } else {
         return null;
      }
   }

   public static RemoteImageTextureManager getInstance() {
      return instance;
   }

   public RemoteImageTextureManager() {
      new RemoteImageTextureCacheUpdateThread(this).start();
      this.cachesBySize.put(32, new RemoteImageTextureCache(32));
   }

   static {
      try {
         DEFAULT_AVATAR_RESOURCE = "default_user";
         instance = new RemoteImageTextureManager();
      } catch (Exception var1) {
         throw new ExceptionInInitializerError(var1);
      }
   }
}
