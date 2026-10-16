package gg.vape.event.impl;

import gg.vape.event.Event;
import gg.vape.event.EventListeners;
import gg.vape.wrapper.impl.Minecraft;

public class EventLegacyRenderGlobalUpdateRenderersThrottle extends Event {
   static float lastPartialTicks = 0.0F;
   private static final EventListeners EVENT_LISTENERS = new EventListeners();
   static float callsSincePartialTickChange = 0.0F;
   private static boolean obfuscationState;

   @Override
   public boolean fire() {
      float partialTicks = Minecraft.getTimer().renderPartialTicks();
      callsSincePartialTickChange++;
      if (partialTicks != lastPartialTicks && callsSincePartialTickChange >= 10.0F) {
         callsSincePartialTickChange = 0.0F;
      }

      if (callsSincePartialTickChange > 0.0F) {
         this.setCancelled(true);
      }

      lastPartialTicks = partialTicks;
      return super.fire();
   }

   @Override
   public EventListeners getListeners() {
      return EVENT_LISTENERS;
   }

   public static void setThrottleObfuscationState(boolean state) {
      obfuscationState = state;
   }

   public static boolean getThrottleObfuscationState() {
      return obfuscationState;
   }

   public static EventListeners getEventListeners() {
      return EVENT_LISTENERS;
   }

   public static boolean getObfuscationConstant() {
      boolean state = getThrottleObfuscationState();
      return true;
   }

   static {
      setThrottleObfuscationState(false);
   }
}
