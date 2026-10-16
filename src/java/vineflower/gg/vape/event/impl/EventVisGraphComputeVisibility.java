package gg.vape.event.impl;

import gg.vape.Vape;
import gg.vape.event.Event;
import gg.vape.event.EventListeners;
import gg.vape.module.world.XRay;
import gg.vape.wrapper.impl.SetVisibility;

public class EventVisGraphComputeVisibility extends Event {
   private static final EventListeners EVENT_LISTENERS = new EventListeners();

   public static EventListeners getEventListeners() {
      return EVENT_LISTENERS;
   }

   @Override
   public boolean fire() {
      XRay xRay = Vape.INSTANCE.getModManager().getXRayModule();
      if (xRay != null && xRay.boolean_r()) {
         this.setCancelled(true);
         return true;
      } else {
         return false;
      }
   }

   @Override
   public EventListeners getListeners() {
      return EVENT_LISTENERS;
   }

   public EventVisGraphComputeVisibility() {
   }

   public EventVisGraphComputeVisibility(Object ignoredVisGraphHandle) {
   }

   public static Object getVisibility() {
      SetVisibility setVisibility = new SetVisibility(Vape.INSTANCE.getMappingsMapperCompat().setVisibility.constructor.newInstance());
      setVisibility.setAllVisible(true);
      return setVisibility.getObject();
   }
}
