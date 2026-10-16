package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class PlaySoundAtEntityEvent extends Wrapper {
   public PlaySoundAtEntityEvent(Object object) {
      super(object);
   }

   public String getName() {
      return vapeInstance.getMappingsMapperCompat().C5.Z(this.I);
   }
}
