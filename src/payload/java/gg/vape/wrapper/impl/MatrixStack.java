package gg.vape.wrapper.impl;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.mappings.MMatrixStack;
import gg.vape.wrapper.Wrapper;

public class MatrixStack extends Wrapper {
   public void H() {
      if (this.isInstance(MappedClasses.VH)) {
         new Matrix4fTransformBridge(this.I).J();
      } else {
         MMatrixStack.o(vapeInstance.getMappingsMapperCompat().qn, this.I);
      }
   }

   public MatrixStackEntry F() {
      return new MatrixStackEntry(MMatrixStack.S(vapeInstance.getMappingsMapperCompat().qn, this.I));
   }

   public void y() {
      if (this.isInstance(MappedClasses.VH)) {
         new Matrix4fTransformBridge(this.I).X();
      } else {
         MMatrixStack.b(vapeInstance.getMappingsMapperCompat().qn, this.I);
      }
   }

   public void i(Quaternion jd_12) {
      MMatrixStack.r(vapeInstance.getMappingsMapperCompat().qn, this.I, jd_12.getObject());
   }

   public void i(Matrix4f tl_12) {
      if (this.isInstance(MappedClasses.VH)) {
         new Matrix4fTransformBridge(this.I).a(tl_12);
      } else {
         MMatrixStack.o(vapeInstance.getMappingsMapperCompat().qn, this.I, tl_12.getObject());
      }
   }

   public void V(double d, double d2, double d3) {
      if (this.isInstance(MappedClasses.VH)) {
         new Matrix4fTransformBridge(this.I).N((float)d, (float)d2, (float)d3);
      } else {
         MMatrixStack.d(vapeInstance.getMappingsMapperCompat().qn, this.I, d, d2, d3);
      }
   }

   public void U() {
      if (this.isInstance(MappedClasses.VH)) {
         new Matrix4fTransformBridge(this.I).h();
      } else {
         MMatrixStack.u(vapeInstance.getMappingsMapperCompat().qn, this.I);
      }
   }

   public void S(float f, float f2, float f3) {
      if (this.isInstance(MappedClasses.VH)) {
         new Matrix4fTransformBridge(this.I).d(f, f2, f3);
      } else {
         MMatrixStack.o(vapeInstance.getMappingsMapperCompat().qn, this.I, f, f2, f3);
      }
   }

   public MatrixStack(Object object) {
      super(object);
   }

   public void s(double d, double d2, double d3) {
      this.S((float)d, (float)d2, (float)d3);
   }

   public static MatrixStack A() {
      return new MatrixStack(MMatrixStack.U(vapeInstance.getMappingsMapperCompat().qn));
   }
}
