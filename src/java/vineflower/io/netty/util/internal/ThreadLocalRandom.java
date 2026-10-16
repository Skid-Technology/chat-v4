package io.netty.util.internal;

import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.lang.Thread.UncaughtExceptionHandler;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public final class ThreadLocalRandom extends Random {
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ThreadLocalRandom.class);
   private static final AtomicLong seedUniquifier = new AtomicLong();
   private static volatile long initialSeedUniquifier;
   private static final long multiplier = 25214903917L;
   private static final long addend = 11L;
   private static final long mask = 281474976710655L;
   private long rnd;
   boolean initialized = true;
   private long pad0;
   private long pad1;
   private long pad2;
   private long pad3;
   private long pad4;
   private long pad5;
   private long pad6;
   private long pad7;
   private static final long serialVersionUID = -5851777807851030925L;

   public static void setInitialSeedUniquifier(long initialSeedUniquifier) {
      ThreadLocalRandom.initialSeedUniquifier = initialSeedUniquifier;
   }

   public static synchronized long getInitialSeedUniquifier() {
      long initialSeedUniquifier = ThreadLocalRandom.initialSeedUniquifier;
      if (initialSeedUniquifier == 0L) {
         ThreadLocalRandom.initialSeedUniquifier = initialSeedUniquifier = SystemPropertyUtil.getLong("io.netty.initialSeedUniquifier", 0L);
      }

      if (initialSeedUniquifier == 0L) {
         final BlockingQueue<byte[]> queue = new LinkedBlockingQueue<>();
         Thread generatorThread = new Thread("initialSeedUniquifierGenerator") {
            @Override
            public void run() {
               SecureRandom random = new SecureRandom();
               queue.add(random.generateSeed(8));
            }
         };
         generatorThread.setDaemon(true);
         generatorThread.start();
         generatorThread.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
               ThreadLocalRandom.logger.debug("An exception has been raised by {}", t.getName(), e);
            }
         });
         long timeoutSeconds = 3L;
         long deadLine = System.nanoTime() + TimeUnit.SECONDS.toNanos(3L);
         boolean interrupted = false;

         while (true) {
            long waitTime = deadLine - System.nanoTime();
            if (waitTime <= 0L) {
               generatorThread.interrupt();
               logger.warn("Failed to generate a seed from SecureRandom within {} seconds. Not enough entrophy?", 3L);
               break;
            }

            try {
               byte[] seed = queue.poll(waitTime, TimeUnit.NANOSECONDS);
               if (seed != null) {
                  initialSeedUniquifier = (seed[0] & 255L) << 56
                     | (seed[1] & 255L) << 48
                     | (seed[2] & 255L) << 40
                     | (seed[3] & 255L) << 32
                     | (seed[4] & 255L) << 24
                     | (seed[5] & 255L) << 16
                     | (seed[6] & 255L) << 8
                     | seed[7] & 255L;
                  break;
               }
            } catch (InterruptedException var12) {
               interrupted = true;
               logger.warn("Failed to generate a seed from SecureRandom due to an InterruptedException.");
               break;
            }
         }

         long var13 = initialSeedUniquifier ^ 3627065505421648153L;
         initialSeedUniquifier = var13 ^ Long.reverse(System.nanoTime());
         ThreadLocalRandom.initialSeedUniquifier = initialSeedUniquifier;
         if (interrupted) {
            Thread.currentThread().interrupt();
            generatorThread.interrupt();
         }
      }

      return initialSeedUniquifier;
   }

   private static long newSeed() {
      long startTime = System.nanoTime();

      long current;
      long actualCurrent;
      long next;
      do {
         current = seedUniquifier.get();
         actualCurrent = current != 0L ? current : getInitialSeedUniquifier();
         next = actualCurrent * 181783497276652981L;
      } while (!seedUniquifier.compareAndSet(current, next));

      if (current == 0L && logger.isDebugEnabled()) {
         logger.debug(
            String.format("-Dio.netty.initialSeedUniquifier: 0x%016x (took %d ms)", actualCurrent, TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime))
         );
      }

      return next ^ System.nanoTime();
   }

   ThreadLocalRandom() {
      super(newSeed());
   }

   public static ThreadLocalRandom current() {
      return InternalThreadLocalMap.get().random();
   }

   @Override
   public void setSeed(long seed) {
      if (this.initialized) {
         throw new UnsupportedOperationException();
      } else {
         this.rnd = (seed ^ 25214903917L) & 281474976710655L;
      }
   }

   @Override
   protected int next(int bits) {
      this.rnd = this.rnd * 25214903917L + 11L & 281474976710655L;
      return (int)(this.rnd >>> 48 - bits);
   }

   @Override
   public int nextInt(int least, int bound) {
      if (least >= bound) {
         throw new IllegalArgumentException();
      } else {
         return this.nextInt(bound - least) + least;
      }
   }

   @Override
   public long nextLong(long n) {
      if (n <= 0L) {
         throw new IllegalArgumentException("n must be positive");
      } else {
         long offset = 0L;

         while (n >= 2147483647L) {
            int bits = this.next(2);
            long half = n >>> 1;
            long nextn = (bits & 2) == 0 ? half : n - half;
            if ((bits & 1) == 0) {
               offset += n - nextn;
            }

            n = nextn;
         }

         return offset + this.nextInt((int)n);
      }
   }

   @Override
   public long nextLong(long least, long bound) {
      if (least >= bound) {
         throw new IllegalArgumentException();
      } else {
         return this.nextLong(bound - least) + least;
      }
   }

   @Override
   public double nextDouble(double n) {
      if (n <= 0.0) {
         throw new IllegalArgumentException("n must be positive");
      } else {
         return this.nextDouble() * n;
      }
   }

   @Override
   public double nextDouble(double least, double bound) {
      if (least >= bound) {
         throw new IllegalArgumentException();
      } else {
         return this.nextDouble() * (bound - least) + least;
      }
   }
}
