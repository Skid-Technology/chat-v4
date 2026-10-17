package gg.vape.wrapper.impl;

import gg.vape.Vape;
import gg.vape.mapping.mappings.MRenderWorldLastEvent;
import gg.vape.wrapper.Wrapper;

public class RenderWorldLastEvent extends Wrapper {
   public static float getRenderResolutionMultiplier() {
      return !Vape.renderReady ? 1.0F : MRenderWorldLastEvent.getRenderResolutionMultiplier(vapeInstance.getMappingsMapperCompat().shadersConfig);
   }

   public RenderWorldLastEvent(Object handle) {
      super(handle);
   }
}
