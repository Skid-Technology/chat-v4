package gg.vape.wrapper.impl;

public class Direction extends EnumFacing {
   public DirectionVector Q$src$Lgg_vape_wrapper_impl_DirectionVector_$l2h44r() {
      return new DirectionVector(vapeInstance.getMappings().Q.g(this.I));
   }

   public Direction(Object object) {
      super(object);
   }

   public DirectionAxis n() {
      return new DirectionAxis(vapeInstance.getMappings().Q.J(this.I));
   }

   public int F() {
      return vapeInstance.getMappings().Q.z(this.I);
   }

   public static Direction i(double d, double d2, double d3) {
      return new Direction(vapeInstance.getMappings().Q.w(d, d2, d3));
   }

   public int Q() {
      return vapeInstance.getMappings().Q.o(this.I);
   }

   public int S() {
      return vapeInstance.getMappings().Q.S(this.I);
   }
}
