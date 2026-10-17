package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MEntityFishHook;
import gg.vape.wrapper.Wrapper;

public class EntityFishHookState extends Wrapper {
   public boolean isOpen() {
      return MEntityFishHook.isOpen(vapeInstance.getMappingsMapperCompat().nettyChannel, this.I);
   }

   public CaughtEntity eventLoop() {
      return new CaughtEntity(MEntityFishHook.eventLoop(vapeInstance.getMappingsMapperCompat().nettyChannel, this.I));
   }

   public EntityFishHookState(Object object) {
      super(object);
   }
}
