package gg.vape.event.impl;

import gg.vape.wrapper.impl.Entity;

public class EventPostMotion extends EventMotion {
   @Override
   public boolean fire() {
      if (player.isNull()) {
         return false;
      } else {
         boolean fired = super.fire();
         player.T(EventMotion.getSavedRidingEntity());
         return fired;
      }
   }

   public EventPostMotion(Object playerHandle) {
      super(new Entity(playerHandle));
   }
}
