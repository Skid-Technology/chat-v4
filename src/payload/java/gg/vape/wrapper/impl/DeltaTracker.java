package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MDeltaTracker;
import gg.vape.wrapper.Wrapper;

public class DeltaTracker extends Wrapper {
   public float getGameTimeDeltaPartialTick(boolean runsNormally) {
      return this.isNull() ? 1.0F : MDeltaTracker.getGameTimeDeltaPartialTick(vapeInstance.getMappingsMapperCompat().deltaTracker, this.I, runsNormally);
   }

   public DeltaTracker(Object handle) {
      super(handle);
   }

   public float getGameTimeDeltaTicks() {
      return this.isNull() ? 1.0F : MDeltaTracker.getGameTimeDeltaTicks(vapeInstance.getMappingsMapperCompat().deltaTracker, this.I);
   }
}
