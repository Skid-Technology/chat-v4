package gg.vape.event.impl;

public class EventPreAttack extends EventAttackBase {
   @Override
   public boolean fire() {
      return super.fire();
   }

   public EventPreAttack(Object targetHandle) {
      super(targetHandle);
   }
}
