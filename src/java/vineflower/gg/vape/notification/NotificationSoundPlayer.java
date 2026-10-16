package gg.vape.notification;

import gg.vape.Vape;
import java.util.concurrent.atomic.AtomicReference;

public class NotificationSoundPlayer {
   private static int[] controlFlowMarker;
   private final AtomicReference<SoundClip> pendingSound = new AtomicReference<>();

   public NotificationSoundPlayer() {
      this.startSoundThread();
   }

   public void playPendingSound() {
      if (this.pendingSound.get() != null) {
         SoundClip sound = this.pendingSound.get();
         this.pendingSound.set(null);
         if (!this.isMuted()) {
            sound.play(this.getVolumePercent());
         }
      }
   }

   public boolean isMuted() {
      return Vape.INSTANCE.getPublicProfileSettings().muted.getEffectiveValue();
   }

   public static int[] getControlFlowMarker() {
      return controlFlowMarker;
   }

   public static void setControlFlowMarker(int[] marker) {
      controlFlowMarker = marker;
   }

   public float getVolumePercent() {
      return Vape.INSTANCE.getPublicProfileSettings().volume.getValue().floatValue();
   }

   public void queue(SoundClip sound) {
      this.pendingSound.set(sound);
   }

   public void startSoundThread() {
      new Thread(this::runSoundLoop, "Vape notification sound player").start();
   }

   private void runSoundLoop() {
      while (!Vape.INSTANCE.enabled) {
         try {
            Thread.sleep(100L);
            this.playPendingSound();
         } catch (Exception var2) {
            Vape.logThrowable(var2);
         }
      }
   }

   static {
      if (getControlFlowMarker() == null) {
         setControlFlowMarker(new int[3]);
      }
   }
}
