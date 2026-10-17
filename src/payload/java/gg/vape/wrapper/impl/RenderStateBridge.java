package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MRenderStateBridge;
import gg.vape.wrapper.Wrapper;

public class RenderStateBridge extends Wrapper {
   public double k() {
      return MRenderStateBridge.Z(vapeInstance.getMappingsMapperCompat().q3, this.I);
   }

   public ITextComponent d() {
      return new ITextComponent(vapeInstance.getMappingsMapperCompat().q3.u(this.I));
   }

   public double L() {
      return MRenderStateBridge.g(vapeInstance.getMappingsMapperCompat().q3, this.I);
   }

   public RenderStateBridge(Object object) {
      super(object);
   }

   public double F() {
      return MRenderStateBridge.Y(vapeInstance.getMappingsMapperCompat().q3, this.I);
   }

   public void Z(ITextComponent t3_02) {
      vapeInstance.getMappingsMapperCompat().q3.q(this.I, t3_02.getObject());
   }
}
