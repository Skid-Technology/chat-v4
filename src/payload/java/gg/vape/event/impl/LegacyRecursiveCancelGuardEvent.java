package gg.vape.event.impl;

import gg.vape.event.Event;
import gg.vape.event.EventListeners;

public class LegacyRecursiveCancelGuardEvent extends Event {
   private static int[] obfuscationState;
   static int invocationCounter = 0;
   private static final EventListeners EVENT_LISTENERS = new EventListeners();

   @Override
   public EventListeners getListeners() {
      return EVENT_LISTENERS;
   }

   public static int[] getGuardObfuscationState() {
      return obfuscationState;
   }

   @Override
   public boolean fire() {
      if (invocationCounter > 0) {
         this.setCancelled(true);
      }

      if (++invocationCounter > 100) {
         invocationCounter = 0;
      }

      return super.fire();
   }

   public static void setGuardObfuscationState(int[] state) {
      obfuscationState = state;
   }

   public static EventListeners getEventListeners() {
      return EVENT_LISTENERS;
   }

   static {
      setGuardObfuscationState(new int[5]);
   }
}
