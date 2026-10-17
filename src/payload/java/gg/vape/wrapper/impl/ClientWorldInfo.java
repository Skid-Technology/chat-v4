package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class ClientWorldInfo extends Wrapper {
   public void setDayTime(long dayTime) {
      vapeInstance.getMappingsMapperCompat().clientWorldInfo.setDayTime(this.I, dayTime);
   }

   public ClientWorldInfo(Object wrappedObject) {
      super(wrappedObject);
   }

   public void setGameTime(long gameTime) {
      vapeInstance.getMappingsMapperCompat().clientWorldInfo.setGameTime(this.I, gameTime);
   }
}
