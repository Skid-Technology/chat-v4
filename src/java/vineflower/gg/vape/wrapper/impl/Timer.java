package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MTimer;
import gg.vape.wrapper.Wrapper;

public class Timer extends Wrapper {
   public float getTimerSpeed() {
      if (this.isNull()) {
         return 1.0F;
      } else if (ForgeVersion.MC_1_12_2.d()) {
         float f = 50.0F;
         return f / vapeInstance.getMappings().hr.Z(this.I);
      } else {
         return MTimer.S(vapeInstance.getMappings().hr, this.I);
      }
   }

   public void setTimerSpeed(float f) {
      if (!this.isNull()) {
         if (ForgeVersion.MC_1_12_2.d()) {
            float f2 = 50.0F;
            MTimer.k(vapeInstance.getMappings().hr, this.I, f2 / f);
         } else {
            MTimer.y(vapeInstance.getMappings().hr, this.I, f);
         }
      }
   }

   public Timer(Object object) {
      super(object);
   }

   public float renderPartialTicks() {
      return this.isNull() ? 1.0F : MTimer.l(vapeInstance.getMappings().hr, this.I);
   }

   public float getElapsedPartialTicks() {
      return this.isNull() ? 1.0F : MTimer.H(vapeInstance.getMappings().hr, this.I);
   }
}
