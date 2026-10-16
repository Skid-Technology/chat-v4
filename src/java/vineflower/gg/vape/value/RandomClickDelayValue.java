package gg.vape.value;

import gg.vape.utils.TimerUtil;
import java.util.Random;

public class RandomClickDelayValue extends RandomValue {
   private int burstLength;
   private final Random delaySpikeChanceRandom;
   private final Random burstRandom;
   private long currentDelayMillis;
   private final TimerUtil clickTimer = new TimerUtil();
   private final Random delaySpikeRandom;
   private boolean burstActive;
   private final Random clicksPerSecondRandom = new Random();
   private int burstProgress;

   public boolean hasClickDelayElapsed() {
      return this.clickTimer.hasTimeElapsed(this.calculateNextDelayMillis());
   }

   public static RandomClickDelayValue create(
      Object owner,
      String name,
      String formatPattern,
      String suffix,
      double allowedMinimum,
      double defaultMinimum,
      double defaultMaximum,
      double allowedMaximum
   ) {
      return new RandomClickDelayValue(owner, name, new double[]{defaultMinimum, defaultMaximum}, allowedMinimum, allowedMaximum, formatPattern, suffix);
   }

   public RandomClickDelayValue(
      Object owner, String name, double[] defaultRange, double allowedMinimum, double allowedMaximum, String formatPattern, String suffix
   ) {
      super(owner, name, defaultRange, allowedMinimum, allowedMaximum, formatPattern, suffix);
      this.delaySpikeChanceRandom = new Random();
      this.delaySpikeRandom = new Random();
      this.burstRandom = new Random();
   }

   public long calculateNextDelayMillis() {
      int minimumCps = this.getMinimumInt();
      int maximumCps;
      int cpsSpan = minimumCps - (maximumCps = this.getMaximumInt());
      int selectedCps = cpsSpan <= 0 ? maximumCps : this.clicksPerSecondRandom.nextInt(cpsSpan) + maximumCps + 1;
      if (selectedCps == 0) {
         selectedCps = 1;
      }

      if (!this.burstActive) {
         this.currentDelayMillis = 1000 / selectedCps;
         if (this.burstRandom.nextInt(4) == 1) {
            this.burstActive = true;
            this.burstLength = 1 + this.burstRandom.nextInt(5);
         } else if (this.burstRandom.nextInt(10) != 1 && this.burstRandom.nextInt(10) == 1) {
            this.burstActive = true;
            this.burstLength = 5 + this.burstRandom.nextInt(10);
         }
      }

      if (this.burstActive) {
         this.burstProgress++;
         if (this.burstProgress >= this.burstLength) {
            this.burstProgress = 0;
            this.burstActive = false;
         }
      }

      boolean unused = true;
      if (this.delaySpikeChanceRandom.nextInt(48) % 10 == 0 && !this.burstActive) {
         int var7 = 25;
         int var8 = 70;
         cpsSpan = var8 - var7;
         this.currentDelayMillis = this.currentDelayMillis + (this.delaySpikeRandom.nextInt(cpsSpan) + var7);
      }

      return this.currentDelayMillis;
   }

   public static RandomClickDelayValue createLegacy(
      Object owner,
      String name,
      String legacyLabel,
      String formatPattern,
      String suffix,
      double allowedMinimum,
      double defaultMinimum,
      double defaultMaximum,
      double allowedMaximum,
      double legacyIncrement
   ) {
      return new RandomClickDelayValue(owner, name, new double[]{defaultMinimum, defaultMaximum}, allowedMinimum, allowedMaximum, formatPattern, suffix);
   }

   public static RandomClickDelayValue createWithDescription(
      Object owner,
      String name,
      String formatPattern,
      String suffix,
      double allowedMinimum,
      double defaultMinimum,
      double defaultMaximum,
      double allowedMaximum,
      double increment,
      String description
   ) {
      RandomClickDelayValue randomClickDelayValue = new RandomClickDelayValue(
         owner, name, new double[]{defaultMinimum, defaultMaximum}, allowedMinimum, allowedMaximum, formatPattern, suffix
      );
      randomClickDelayValue.setIncrement(increment);
      randomClickDelayValue.setDescription(description);
      return randomClickDelayValue;
   }

   public void resetClickTimer() {
      this.clickTimer.reset();
   }

   public static RandomClickDelayValue createWithIncrement(
      Object owner,
      String name,
      String formatPattern,
      String suffix,
      double allowedMinimum,
      double defaultMinimum,
      double defaultMaximum,
      double allowedMaximum,
      double increment
   ) {
      RandomClickDelayValue randomClickDelayValue = new RandomClickDelayValue(
         owner, name, new double[]{defaultMinimum, defaultMaximum}, allowedMinimum, allowedMaximum, formatPattern, suffix
      );
      randomClickDelayValue.setIncrement(increment);
      return randomClickDelayValue;
   }

   public boolean hasClickDelayElapsed(long delayMillis) {
      return this.clickTimer.hasTimeElapsed(delayMillis);
   }
}
