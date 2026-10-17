package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class RenderGlobal extends Wrapper {
   public RenderGlobal(Object renderGlobalHandle) {
      super(renderGlobalHandle);
   }

   public void loadRenderers() {
      vapeInstance.getMappingsMapperCompat().renderGlobal.loadRenderers(this.I);
   }
}
