package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MLightTexture;
import gg.vape.wrapper.Wrapper;

public class LightTexture extends Wrapper {
   public void Z(float f) {
      MLightTexture.B(vapeInstance.getMappingsMapperCompat().hF, this.I, f);
   }

   public void V() {
      if (!ForgeVersion.MC_1_21_11.d()) {
         MLightTexture.f(vapeInstance.getMappingsMapperCompat().hF, this.I);
      }
   }

   public LightTexture(Object object) {
      super(object);
   }

   public void X() {
      if (!ForgeVersion.MC_1_21_11.d()) {
         MLightTexture.z(vapeInstance.getMappingsMapperCompat().hF, this.I);
      }
   }
}
