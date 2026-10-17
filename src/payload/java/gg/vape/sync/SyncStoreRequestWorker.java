package gg.vape.sync;

import gg.vape.Vape;
import java.util.concurrent.atomic.AtomicBoolean;

public class SyncStoreRequestWorker implements Runnable {
   private final AtomicBoolean saveRequested = new AtomicBoolean();

   public void requestSave() {
      this.saveRequested.set(true);
   }

   @Override
   public void run() {
      while (!Vape.INSTANCE.isEnabled()) {
         try {
            Thread.sleep(100L);
         } catch (InterruptedException var3) {
            var3.printStackTrace();
         }

         if (this.saveRequested.get()) {
            try {
               Thread.sleep(100L);
            } catch (InterruptedException var2) {
               var2.printStackTrace();
            }

            Vape.INSTANCE.getSyncThread().saveSettings();
            this.saveRequested.set(false);
         }
      }
   }
}
