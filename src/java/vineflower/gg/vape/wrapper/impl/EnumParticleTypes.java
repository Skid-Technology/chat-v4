package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MEnumParticleTypes;
import gg.vape.wrapper.Wrapper;

public class EnumParticleTypes extends Wrapper {
   public String K() {
      if (ForgeVersion.MC_1_20_6.d()) {
         Registry jn_12 = BuiltInRegistries.a();
         return jn_12.W(this.I).getResourcePath();
      } else {
         return ForgeVersion.MC_1_16_5.d()
            ? MEnumParticleTypes.i(vapeInstance.getMappingsMapperCompat().Rp, this.I)
            : MEnumParticleTypes.I(vapeInstance.getMappingsMapperCompat().Rp, this.I);
      }
   }

   public EnumParticleTypes(Object object) {
      super(object);
   }
}
