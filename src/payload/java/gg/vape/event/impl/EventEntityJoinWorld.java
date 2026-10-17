package gg.vape.event.impl;

import gg.vape.event.Event;
import gg.vape.event.EventListeners;
import gg.vape.wrapper.impl.Entity;

public class EventEntityJoinWorld extends Event {
   private static final EventListeners EVENT_LISTENERS = new EventListeners();
   private static final String SERVER_THREAD_NAME = "Server";
   private final Entity entity;

   public EventEntityJoinWorld(Object entityHandle) {
      this.entity = new Entity(entityHandle);
   }

   @Override
   public boolean fire() {
      return Thread.currentThread().getName().contains(SERVER_THREAD_NAME) ? false : super.fire();
   }

   public Entity getEntity() {
      return this.entity;
   }

   public static EventListeners getEventListeners() {
      return EVENT_LISTENERS;
   }

   @Override
   public EventListeners getListeners() {
      return EVENT_LISTENERS;
   }
}
