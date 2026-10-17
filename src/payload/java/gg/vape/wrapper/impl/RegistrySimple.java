package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MRegistrySimple;
import gg.vape.wrapper.Wrapper;
import java.util.Set;

public class RegistrySimple extends Wrapper {
   public Object S(Object object) {
      return MRegistrySimple.s(vapeInstance.getMappings().hD, this.I, object);
   }

   public Set D() {
      return vapeInstance.getMappings().hD.g(this.I);
   }

   public RegistrySimple(Object object) {
      super(object);
   }
}
