package org.lwjgl.opengl;

import org.lwjgl.Sys;

class Sync {
   private static final long NANOS_IN_SECOND = 1000000000L;
   private static long nextFrame = 0L;
   private static boolean initialised = false;
   private static Sync.RunningAvg sleepDurations = new Sync.RunningAvg(10);
   private static Sync.RunningAvg yieldDurations = new Sync.RunningAvg(10);

   public static void sync(int fps) {
      if (fps > 0) {
         if (!initialised) {
            initialise();
         }

         try {
            long t0 = getTime();

            while (nextFrame - t0 > sleepDurations.avg()) {
               Thread.sleep(1L);
               long t1;
               sleepDurations.add((t1 = getTime()) - t0);
               t0 = t1;
            }

            sleepDurations.dampenForLowResTicker();
            t0 = getTime();

            while (nextFrame - t0 > yieldDurations.avg()) {
               Thread.yield();
               long t1;
               yieldDurations.add((t1 = getTime()) - t0);
               t0 = t1;
            }
         } catch (InterruptedException var5) {
         }

         nextFrame = Math.max(nextFrame + 1000000000L / fps, getTime());
      }
   }

   private static void initialise() {
      initialised = true;
      sleepDurations.init(1000000L);
      yieldDurations.init((int)(-(getTime() - getTime()) * 1.333));
      nextFrame = getTime();
      String osName = System.getProperty("os.name");
      if (osName.startsWith("Win")) {
         Thread timerAccuracyThread = new Thread(new Runnable() {
            @Override
            public void run() {
               try {
                  Thread.sleep(Long.MAX_VALUE);
               } catch (Exception var2) {
               }
            }
         });
         timerAccuracyThread.setName("LWJGL Timer");
         timerAccuracyThread.setDaemon(true);
         timerAccuracyThread.start();
      }
   }

   private static long getTime() {
      return Sys.getTime() * 1000000000L / Sys.getTimerResolution();
   }

   private static class RunningAvg {
      private final long[] slots;
      private int offset;
      private static final long DAMPEN_THRESHOLD = 10000000L;
      private static final float DAMPEN_FACTOR = 0.9F;

      public RunningAvg(int slotCount) {
         this.slots = new long[slotCount];
         this.offset = 0;
      }

      public void init(long value) {
         while (this.offset < this.slots.length) {
            this.slots[this.offset++] = value;
         }
      }

      public void add(long value) {
         this.slots[this.offset++ % this.slots.length] = value;
         this.offset = this.offset % this.slots.length;
      }

      public long avg() {
         long sum = 0L;

         for (int i = 0; i < this.slots.length; i++) {
            sum += this.slots[i];
         }

         return sum / this.slots.length;
      }

      public void dampenForLowResTicker() {
         if (this.avg() > 10000000L) {
            for (int i = 0; i < this.slots.length; i++) {
               this.slots[i] = (long)((float)this.slots[i] * 0.9F);
            }
         }
      }
   }
}
