package gg.vape.utils;

public class NanoTimerUtil extends TimerUtil {
   public double getElapsedMilliseconds() {
      return this.getLastMS() / 1000000.0;
   }

   @Override
   public void reset() {
      this.Q = System.nanoTime();
   }

   @Override
   public long getLastMS() {
      return System.nanoTime() - this.Q;
   }

   @Override
   public boolean hasTimeElapsed(long l) {
      return this.getLastMS() >= l;
   }
}
