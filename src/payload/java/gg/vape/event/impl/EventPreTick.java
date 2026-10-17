package gg.vape.event.impl;

public class EventPreTick extends EventTickBase {
   @Override
   public boolean fire() {
      PRE_TICK_EXECUTOR.runPending();
      return super.fire();
   }
}
