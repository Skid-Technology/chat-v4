package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MAxisAlignedBB;
import gg.vape.wrapper.Wrapper;
import java.util.Optional;

public class AxisAlignedBB extends Wrapper {
   public AxisAlignedBB A(double d, double d2, double d3) {
      return ForgeVersion.MC_1_20_6.d()
         ? create(this.getMinX() + d, this.getMinY() + d2, this.getMinZ() + d3, this.getMaxX() + d, this.getMaxY() + d2, this.getMaxZ() + d3)
         : new AxisAlignedBB(vapeInstance.getMappingsCompat().RQ.B(this.I, d, d2, d3));
   }

   public double getMaxX() {
      return vapeInstance.getMappingsCompat().RQ.k(this.I);
   }

   public AxisAlignedBB z(double d, double d2, double d3) {
      return new AxisAlignedBB(MAxisAlignedBB.Z(vapeInstance.getMappingsCompat().RQ, this.I, d, d2, d3));
   }

   public AxisAlignedBB contract(double d, double d2, double d3) {
      return this.expand(-d, -d2, -d3);
   }

   public double getMaxZ() {
      return vapeInstance.getMappingsCompat().RQ.p(this.I);
   }

   public Optional b(Vec3 vec3, Vec3 vec32) {
      return (Optional)MAxisAlignedBB.a(vapeInstance.getMappingsCompat().RQ, this.I, vec3.getObject(), vec32.getObject());
   }

   public double getMinX() {
      return vapeInstance.getMappingsCompat().RQ.z(this.I);
   }

   public AxisAlignedBB(Object object) {
      super(object);
   }

   public double getMinZ() {
      return vapeInstance.getMappingsCompat().RQ.G(this.I);
   }

   public AxisAlignedBB k(double d, double d2, double d3) {
      return ForgeVersion.MC_1_7_10.Y() ? this.A(d, d2, d3) : new AxisAlignedBB(MAxisAlignedBB.c(vapeInstance.getMappingsCompat().RQ, this.I, d, d2, d3));
   }

   public AxisAlignedBB addCoord(double d, double d2, double d3) {
      double d4 = this.getMinX();
      double d5 = this.getMinY();
      double d6 = this.getMinZ();
      double d7 = this.getMaxX();
      double d8 = this.getMaxY();
      double d9 = this.getMaxZ();
      if (d < 0.0) {
         d4 += d;
      } else if (d > 0.0) {
         d7 += d;
      }

      if (d2 < 0.0) {
         d5 += d2;
      } else if (d2 > 0.0) {
         d8 += d2;
      }

      if (d3 < 0.0) {
         d6 += d3;
      } else if (d3 > 0.0) {
         d9 += d3;
      }

      return create(d4, d5, d6, d7, d8, d9);
   }

   public RayTraceResult calculateIntercept(Vec3 vec3, Vec3 vec32) {
      if (ForgeVersion.MC_1_16_5.d()) {
         double[] dArray = new double[]{1.0};
         double d3 = vec32.getX() - vec3.getX();
         double d;
         double d2;
         EnumFacing enumFacing = u(this, vec3, dArray, new EnumFacing(null), d3, d2 = vec32.getY() - vec3.getY(), d = vec32.getZ() - vec3.getZ());
         if (enumFacing.isNull()) {
            return new RayTraceResult(null);
         } else {
            double d4 = dArray[0];
            Vec3 vec33 = vec3.addVector(d4 * d3, d4 * d2, d4 * d);
            return RayTraceResult.create(new Entity(null), vec33);
         }
      } else {
         return new RayTraceResult(vapeInstance.getMappingsCompat().RQ.x(this.I, vec3.getObject(), vec32.getObject()));
      }
   }

   public AxisAlignedBB copy() {
      return new AxisAlignedBB(MAxisAlignedBB.Y(vapeInstance.getMappingsCompat().RQ, this.I));
   }

   public AxisAlignedBB v(double d) {
      return this.I(-d);
   }

   public double getMaxY() {
      return vapeInstance.getMappingsCompat().RQ.L(this.I);
   }

   private static EnumFacing u(AxisAlignedBB axisAlignedBB, Vec3 vec3, double[] dArray, EnumFacing enumFacing, double d, double d2, double d3) {
      return new EnumFacing(
         MAxisAlignedBB.K(vapeInstance.getMappingsCompat().RQ, axisAlignedBB.getObject(), vec3.getObject(), dArray, enumFacing.getObject(), d, d2, d3)
      );
   }

   public double getMinY() {
      return vapeInstance.getMappingsCompat().RQ.T(this.I);
   }

   public AxisAlignedBB expand(double d, double d2, double d3) {
      double d4 = this.getMinX() - d;
      double d5 = this.getMinY() - d2;
      double d6 = this.getMinZ() - d3;
      double d7 = this.getMaxX() + d;
      double d8 = this.getMaxY() + d2;
      double d9 = this.getMaxZ() + d3;
      return create(d4, d5, d6, d7, d8, d9);
   }

   @Override
   public String toString() {
      return this.I == null
         ? "AxisAlignedBB[null]"
         : "AxisAlignedBB[minX="
            + this.getMinX()
            + ", maxX="
            + this.getMaxX()
            + ", minY="
            + this.getMinY()
            + ", maxY="
            + this.getMaxY()
            + ", minZ="
            + this.getMinZ()
            + ", maxZ="
            + this.getMaxZ()
            + "]";
   }

   public AxisAlignedBB c(Vec3 vec3) {
      return new AxisAlignedBB(MAxisAlignedBB.C(vapeInstance.getMappingsCompat().RQ, this.I, vec3.getObject()));
   }

   public AxisAlignedBB y(double d) {
      return this.contract(d, d, d);
   }

   public static AxisAlignedBB create(double d, double d2, double d3, double d4, double d5, double d6) {
      return new AxisAlignedBB(vapeInstance.getMappingsCompat().RQ.init(d, d2, d3, d4, d5, d6));
   }

   public AxisAlignedBB I(double d) {
      return new AxisAlignedBB(MAxisAlignedBB.v(vapeInstance.getMappingsCompat().RQ, this.I, d));
   }

   public boolean isVecInside(Vec3 vec3) {
      if (!(vec3.getX() > this.getMinX())) {
         return false;
      } else if (!(vec3.getX() < this.getMaxX())) {
         return false;
      } else if (!(vec3.getY() > this.getMinY())) {
         return false;
      } else if (!(vec3.getY() < this.getMaxY())) {
         return false;
      } else {
         return !(vec3.getZ() > this.getMinZ()) ? false : vec3.getZ() < this.getMaxZ();
      }
   }

   public boolean intersects(AxisAlignedBB axisAlignedBB) {
      return MAxisAlignedBB.e(vapeInstance.getMappingsCompat().RQ, this.I, axisAlignedBB.getObject());
   }

   public boolean E(Vec3 vec3) {
      return MAxisAlignedBB.j(vapeInstance.getMappingsCompat().RQ, this.I, vec3.getObject());
   }
}
