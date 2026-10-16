package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class ChunkSection extends Wrapper {
   public char[] C() {
      return vapeInstance.getMappingsMapperCompat().Rx.L(this.I);
   }

   public int l() {
      return vapeInstance.getMappingsMapperCompat().Rx.r(this.I);
   }

   public static Object u(int n, boolean bl) {
      return ForgeVersion.MC_1_16_5.d() ? vapeInstance.getMappingsMapperCompat().Rx.i(n) : vapeInstance.getMappingsMapperCompat().Rx.U(n, bl);
   }

   public void g(int n, int n2, int n3, BlockState pa_22) {
      if (ForgeVersion.MC_1_16_5.d()) {
         vapeInstance.getMappingsMapperCompat().Rx.J(this.I, n, n2, n3, pa_22.getObject(), false);
      } else {
         vapeInstance.getMappingsMapperCompat().Rx.K(this.I, n, n2, n3, pa_22.getObject());
      }
   }

   public ChunkSection(Object object) {
      super(object);
   }
}
