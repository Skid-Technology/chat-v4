package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MEntityArrow;

public class EntityArrow extends Entity {
   public double o() {
      return ForgeVersion.MC_1_21_0.d() ? this.P$src$D$xovcst() : MEntityArrow.g(vapeInstance.getMappings().qo, this.I);
   }

   public double L() {
      return ForgeVersion.MC_1_21_0.d() ? this.P$src$D$xovcst() : MEntityArrow.d(vapeInstance.getMappings().qo, this.I);
   }

   public double P$src$D$xovcst() {
      return MEntityArrow.t(vapeInstance.getMappings().qo, this.I);
   }

   public EntityArrow(Object object) {
      super(object);
   }

   public double X$src$D$xt9pjp() {
      return ForgeVersion.MC_1_21_0.d() ? this.P$src$D$xovcst() : MEntityArrow.D(vapeInstance.getMappings().qo, this.I);
   }
}
