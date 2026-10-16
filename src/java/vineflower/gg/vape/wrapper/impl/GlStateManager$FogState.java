package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class GlStateManager$FogState extends Wrapper {
   public GlStateManager$FogState(Object fogStateHandle) {
      super(fogStateHandle);
   }

   public void reset() {
      vapeInstance.getMappingsMapperCompat().glFogStateObject.reset(this.I);
   }

   public GlStateManagerFogStateBridge getFogMode() {
      Object fogModeHandle = vapeInstance.getMappingsMapperCompat().glFogStateObject.getCurrent(this.I);
      return new GlStateManagerFogStateBridge(fogModeHandle);
   }
}
