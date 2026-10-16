package gg.vape.event.impl;

import gg.vape.event.Event;
import gg.vape.event.EventListeners;

public class EventRenderHandBase extends Event {
   private static String primaryObfuscationState = null;
   private static final EventListeners EVENT_LISTENERS = new EventListeners();
   private static String secondaryObfuscationState = null;

   public static EventListeners getEventListeners() {
      return EVENT_LISTENERS;
   }

   @Override
   public EventListeners getListeners() {
      return EVENT_LISTENERS;
   }

   @Override
   public boolean fire() {
      return super.fire();
   }
}
