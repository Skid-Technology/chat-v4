package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MModelRenderer;
import gg.vape.wrapper.Wrapper;

public class ModelRenderer extends Wrapper {
   public float j() {
      return MModelRenderer.h(vapeInstance.getMappings().Rz, this.I);
   }

   public float s() {
      return MModelRenderer.k(vapeInstance.getMappings().Rz, this.I);
   }

   public void n(boolean bl) {
      MModelRenderer.r(vapeInstance.getMappings().Rz, this.I, bl);
   }

   public ModelRenderer(Object object) {
      super(object);
   }

   public float M() {
      return MModelRenderer.i(vapeInstance.getMappings().Rz, this.I);
   }

   public float B() {
      return MModelRenderer.v(vapeInstance.getMappings().Rz, this.I);
   }

   public float q() {
      return MModelRenderer.b(vapeInstance.getMappings().Rz, this.I);
   }

   public float getRotateAngleY() {
      return MModelRenderer.s(vapeInstance.getMappings().Rz, this.I);
   }

   public float getRotateAngleZ() {
      return MModelRenderer.U(vapeInstance.getMappings().Rz, this.I);
   }

   public void S(boolean bl) {
      MModelRenderer.j(vapeInstance.getMappings().Rz, this.I, bl);
   }

   public float getRotateAngleX() {
      return MModelRenderer.c(vapeInstance.getMappings().Rz, this.I);
   }

   public int s$src$I$x0ut69() {
      return MModelRenderer.z(vapeInstance.getMappings().Rz, this.I);
   }

   public float F() {
      return MModelRenderer.t(vapeInstance.getMappings().Rz, this.I);
   }

   public int C() {
      return MModelRenderer.j(vapeInstance.getMappings().Rz, this.I);
   }
}
