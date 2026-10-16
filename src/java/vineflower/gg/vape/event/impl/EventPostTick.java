package gg.vape.event.impl;

public class EventPostTick extends EventTickBase {
   @Override
   public boolean fire() {
      POST_TICK_EXECUTOR.runPending();
      return super.fire();
   }
}
