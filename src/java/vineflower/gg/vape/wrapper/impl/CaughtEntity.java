package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MCaughtEntityActionBridge;
import gg.vape.wrapper.Wrapper;

public class CaughtEntity extends Wrapper {
   public CaughtEntity(Object object) {
      super(object);
   }

   public boolean inEventLoop() {
      return MCaughtEntityActionBridge.inEventLoop(vapeInstance.getMappingsMapperCompat().eventLoop, this.I);
   }

   public void execute(Runnable runnable) {
      MCaughtEntityActionBridge.execute(vapeInstance.getMappingsMapperCompat().eventLoop, this.I, runnable);
   }
}
