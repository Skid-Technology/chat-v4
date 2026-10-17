package gg.vape.event.impl;

import gg.vape.event.Event;
import gg.vape.event.EventListeners;

public class EventRenderGlobalUpdateChunksThrottle extends Event {
   private static final EventListeners EVENT_LISTENERS = new EventListeners();
   static float callsSinceReset = 0.0F;
   private static String obfuscationState;

   @Override
   public EventListeners getListeners() {
      return EVENT_LISTENERS;
   }

   @Override
   public boolean fire() {
      if (++callsSinceReset >= 100.0F) {
         callsSinceReset = 0.0F;
      }

      if (callsSinceReset > 0.0F) {
         this.setCancelled(true);
      }

      return super.fire();
   }

   public static void setObfuscationState(String state) {
      obfuscationState = state;
   }

   public static String getObfuscationState() {
      return obfuscationState;
   }

   public static EventListeners getEventListeners() {
      return EVENT_LISTENERS;
   }

   static {
      setObfuscationState("p3vFd");
   }
}
