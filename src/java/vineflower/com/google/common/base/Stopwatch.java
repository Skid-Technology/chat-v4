package com.google.common.base;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import java.util.concurrent.TimeUnit;

@Beta
@GwtCompatible(emulated = true)
public final class Stopwatch {
   private final Ticker ticker;
   private boolean isRunning;
   private long elapsedNanos;
   private long startTick;

   public static Stopwatch createUnstarted() {
      return new Stopwatch();
   }

   public static Stopwatch createUnstarted(Ticker ticker) {
      return new Stopwatch(ticker);
   }

   public static Stopwatch createStarted() {
      return new Stopwatch().start();
   }

   public static Stopwatch createStarted(Ticker ticker) {
      return new Stopwatch(ticker).start();
   }

   @Deprecated
   Stopwatch() {
      this(Ticker.systemTicker());
   }

   @Deprecated
   Stopwatch(Ticker ticker) {
      this.ticker = Preconditions.checkNotNull(ticker, "ticker");
   }

   public boolean isRunning() {
      return this.isRunning;
   }

   public Stopwatch start() {
      Preconditions.checkState(!this.isRunning, "This stopwatch is already running.");
      this.isRunning = true;
      this.startTick = this.ticker.read();
      return this;
   }

   public Stopwatch stop() {
      long tick = this.ticker.read();
      Preconditions.checkState(this.isRunning, "This stopwatch is already stopped.");
      this.isRunning = false;
      this.elapsedNanos = this.elapsedNanos + (tick - this.startTick);
      return this;
   }

   public Stopwatch reset() {
      this.elapsedNanos = 0L;
      this.isRunning = false;
      return this;
   }

   private long elapsedNanos() {
      return this.isRunning ? this.ticker.read() - this.startTick + this.elapsedNanos : this.elapsedNanos;
   }

   public long elapsed(TimeUnit desiredUnit) {
      return desiredUnit.convert(this.elapsedNanos(), TimeUnit.NANOSECONDS);
   }

   @GwtIncompatible("String.format()")
   @Override
   public String toString() {
      long nanos = this.elapsedNanos();
      TimeUnit unit = chooseUnit(nanos);
      double value = (double)nanos / TimeUnit.NANOSECONDS.convert(1L, unit);
      return String.format("%.4g %s", value, abbreviate(unit));
   }

   private static TimeUnit chooseUnit(long nanos) {
      if (TimeUnit.DAYS.convert(nanos, TimeUnit.NANOSECONDS) > 0L) {
         return TimeUnit.DAYS;
      } else if (TimeUnit.HOURS.convert(nanos, TimeUnit.NANOSECONDS) > 0L) {
         return TimeUnit.HOURS;
      } else if (TimeUnit.MINUTES.convert(nanos, TimeUnit.NANOSECONDS) > 0L) {
         return TimeUnit.MINUTES;
      } else if (TimeUnit.SECONDS.convert(nanos, TimeUnit.NANOSECONDS) > 0L) {
         return TimeUnit.SECONDS;
      } else if (TimeUnit.MILLISECONDS.convert(nanos, TimeUnit.NANOSECONDS) > 0L) {
         return TimeUnit.MILLISECONDS;
      } else {
         return TimeUnit.MICROSECONDS.convert(nanos, TimeUnit.NANOSECONDS) > 0L ? TimeUnit.MICROSECONDS : TimeUnit.NANOSECONDS;
      }
   }

   private static String abbreviate(TimeUnit unit) {
      switch (unit) {
         case NANOSECONDS:
            return "ns";
         case MICROSECONDS:
            return "μs";
         case MILLISECONDS:
            return "ms";
         case SECONDS:
            return "s";
         case MINUTES:
            return "min";
         case HOURS:
            return "h";
         case DAYS:
            return "d";
         default:
            throw new AssertionError();
      }
   }
}
