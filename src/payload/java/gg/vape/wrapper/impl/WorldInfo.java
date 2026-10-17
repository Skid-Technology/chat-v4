package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class WorldInfo extends Wrapper {
   public long getWorldTime() {
      return vapeInstance.getMappingsMapperCompat().worldInfo.getWorldTime(this.I);
   }

   public WorldInfo(Object wrappedObject) {
      super(wrappedObject);
   }
}
