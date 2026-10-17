package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MInputMappings;
import gg.vape.wrapper.Wrapper;

public class MouseHandler extends Wrapper {
   public void O() {
      MInputMappings.F$src$V$13evi63(vapeInstance.getMappings().CW, this.I);
   }

   public double R() {
      return MInputMappings.g(vapeInstance.getMappings().CW, this.I);
   }

   public MouseHandler(Object object) {
      super(object);
   }

   public void L(long l, int n, boolean bl) {
      MInputMappings.d(vapeInstance.getMappings().CW, this.I, l, n, bl);
   }

   public void u() {
      MInputMappings.V(vapeInstance.getMappings().CW, this.I);
   }

   public int d() {
      return MInputMappings.T(vapeInstance.getMappings().CW, this.I);
   }

   public int z() {
      return MInputMappings.F(vapeInstance.getMappings().CW, this.I);
   }

   public boolean Z() {
      return MInputMappings.w(vapeInstance.getMappings().CW, this.I);
   }

   public double b() {
      return MInputMappings.B(vapeInstance.getMappings().CW, this.I);
   }
}
