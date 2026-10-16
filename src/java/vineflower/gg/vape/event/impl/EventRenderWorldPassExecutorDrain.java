package gg.vape.event.impl;

import gg.vape.event.Event;
import gg.vape.event.EventListeners;
import gg.vape.utils.ThreadBoundExecutor;
import gg.vape.wrapper.impl.DeltaTracker;

public class EventRenderWorldPassExecutorDrain extends Event {
   private static final EventListeners EVENT_LISTENERS = new EventListeners();
   private final float partialTicks;
   public static final ThreadBoundExecutor EXECUTOR = new ThreadBoundExecutor();
   private static String[] obfuscationState;

   public EventRenderWorldPassExecutorDrain(float partialTicks) {
      this.partialTicks = partialTicks;
   }

   public static String[] getWorldPassObfuscationState() {
      return obfuscationState;
   }

   @Override
   public boolean fire() {
      try {
         EXECUTOR.runPending();
      } catch (Throwable var2) {
      }

      return false;
   }

   public static void setWorldPassObfuscationState(String[] state) {
      obfuscationState = state;
   }

   @Override
   public EventListeners getListeners() {
      return EVENT_LISTENERS;
   }

   public EventRenderWorldPassExecutorDrain(Object deltaTrackerHandle) {
      DeltaTracker deltaTracker = new DeltaTracker(deltaTrackerHandle);
      this.partialTicks = deltaTracker.getGameTimeDeltaTicks();
   }

   public static EventListeners getEventListeners() {
      return EVENT_LISTENERS;
   }

   static {
      setWorldPassObfuscationState(null);
   }
}
