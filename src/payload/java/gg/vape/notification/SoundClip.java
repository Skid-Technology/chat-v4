package gg.vape.notification;

import gg.vape.Vape;
import java.io.ByteArrayInputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.FloatControl.Type;

public class SoundClip {
   private static final String[] SUPPORTED_EXTENSIONS = new String[]{".wav", ".au", ".aif", ".aiff"};
   private final byte[] audioData;
   private Clip clip;

   public ByteArrayInputStream openStream() {
      return new ByteArrayInputStream(this.audioData);
   }

   private static byte[] loadAudioData(String resourceName) {
      if (resourceName.contains(".")) {
         String resourcePath = "sounds/" + resourceName;
         byte[] data = Vape.readResource(resourcePath);
         if (data != null) {
            return data;
         } else {
            throw new IllegalArgumentException("Missing sound resource: " + resourceName);
         }
      } else {
         for (String extension : SUPPORTED_EXTENSIONS) {
            String resourcePath = "sounds/" + resourceName + extension;
            byte[] data = Vape.readResource(resourcePath);
            if (data != null) {
               return data;
            }
         }

         throw new IllegalArgumentException("Missing sound resource with supported extensions: " + resourceName);
      }
   }

   public SoundClip(String resourceName) {
      this.audioData = loadAudioData(resourceName);
   }

   public void play(float volumePercent) {
      if (this.clip == null) {
         try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(this.openStream());
            this.clip = AudioSystem.getClip();
            this.clip.open(audioStream);
         } catch (Exception var5) {
            Vape.logThrowable(var5);
         }
      }

      if (this.clip != null) {
         FloatControl gainControl = (FloatControl)this.clip.getControl(Type.MASTER_GAIN);
         gainControl.setValue(20.0F * (float)Math.log10(volumePercent / 100.0));
         this.clip.setFramePosition(0);
         this.clip.start();

         try {
            Thread.sleep(this.clip.getMicrosecondLength() / 1000L);
         } catch (InterruptedException var4) {
            throw new RuntimeException(var4);
         }
      }
   }

   private static Exception propagateException(Exception error) {
      return error;
   }
}
