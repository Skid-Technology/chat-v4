package gg.vape.event.impl;

public class EventStepSnapshot extends EventStep {
   public EventStepSnapshot(Object entityHandle) {
      super(entityHandle);
      EventStep.storeOriginalStepHeight(this.getEntity().u());
   }
}
