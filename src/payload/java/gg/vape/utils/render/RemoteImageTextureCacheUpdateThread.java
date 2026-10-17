package gg.vape.utils.render;

import gg.vape.Vape;
import gg.vape.utils.SleepUtil;

class RemoteImageTextureCacheUpdateThread extends Thread {
   final RemoteImageTextureManager manager;

   @Override
   public void run() {
      while (!Vape.INSTANCE.isEnabled()) {
         SleepUtil.sleep(50L);

         for (Integer imageSize : RemoteImageTextureManager.getCaches(this.manager).keySet()) {
            RemoteImageTextureManager.getCaches(this.manager).get(imageSize).processPendingDownloads();
         }
      }
   }

   RemoteImageTextureCacheUpdateThread(RemoteImageTextureManager manager) {
      this.manager = manager;
   }
}
