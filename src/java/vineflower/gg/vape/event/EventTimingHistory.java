package gg.vape.event;

import gg.vape.utils.TimerUtil;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class EventTimingHistory {
   private final ConcurrentHashMap<EventDispatchTrace, Long> traces = new ConcurrentHashMap<>();
   private final TimerUtil cleanupTimer = new TimerUtil();

   private static boolean isExpired(long nowNanos, Entry<EventDispatchTrace, Long> entry) {
      return nowNanos - entry.getKey().getStartNanos() > 10000000000L;
   }

   public void addTrace(EventDispatchTrace eventDispatchTrace) {
      this.traces.put(eventDispatchTrace, eventDispatchTrace.getDurationNanos());
      if (this.cleanupTimer.hasTimeElapsed(1000L)) {
         this.cleanupTimer.reset();
         this.cleanupExpiredTraces();
      }
   }

   public ConcurrentHashMap<EventDispatchTrace, Long> getTraces() {
      return this.traces;
   }

   private void cleanupExpiredTraces() {
      long nowNanos = System.nanoTime();
      this.traces.entrySet().removeIf(entry -> isExpired(nowNanos, (Entry<EventDispatchTrace, Long>)entry));
   }
}
