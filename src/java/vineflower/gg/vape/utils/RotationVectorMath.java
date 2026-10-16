package gg.vape.utils;

import gg.vape.rotation.RotationAngles;
import gg.vape.rotation.RotationManager;
import gg.vape.wrapper.impl.Vec3;

public class RotationVectorMath {
   public static final double T = Math.PI / 180.0;
   public static final double e = 180.0 / Math.PI;

   public static Vec3 l(RotationAngles rotationAngles) {
      float f = MathUtil.cos(-rotationAngles.getYaw() * (float) (Math.PI / 180.0) - (float) Math.PI);
      float f2 = MathUtil.sin(-rotationAngles.getYaw() * (float) (Math.PI / 180.0) - (float) Math.PI);
      float f3 = -MathUtil.cos(-rotationAngles.getPitch() * (float) (Math.PI / 180.0));
      float f4 = MathUtil.sin(-rotationAngles.getPitch() * (float) (Math.PI / 180.0));
      return Vec3.create(f2 * f3, f4, f * f3);
   }

   public static RotationAngles H(Vec3 vec3, Vec3 vec32, float f, boolean bl) {
      double[] dArray = new double[]{vec3.getX() - vec32.getX(), vec3.getY() - vec32.getY(), vec3.getZ() - vec32.getZ()};
      double d = Math.atan2(dArray[0], -dArray[2]);
      double d2 = Math.sqrt(dArray[0] * dArray[0] + dArray[2] * dArray[2]);
      double d3 = Math.atan2(dArray[1], d2);
      double d4 = d * (180.0 / Math.PI);
      double d5 = (int)((d4 - f) % 360.0);
      d5 = Math.abs(MathUtil.wrapAngleTo180(d5));
      if (bl && d5 > 90.0) {
         d3 = 90.0;
      }

      return new RotationAngles((float)(d * (180.0 / Math.PI)), (float)(d3 * (180.0 / Math.PI)));
   }

   public static RotationAngles l(RotationAngles rotationAngles, RotationAngles rotationAngles2) {
      float f = MathUtil.wrapAngleTo180(rotationAngles2.getYaw()) - MathUtil.wrapAngleTo180(rotationAngles.getYaw());
      if (f <= -180.0F) {
         f += 360.0F;
      } else if (f > 180.0F) {
         f -= 360.0F;
      }

      return new RotationAngles(f, rotationAngles2.getPitch() - rotationAngles.getPitch());
   }

   public static RotationAngles d(Vec3 vec3, Vec3 vec32, float f, float f2) {
      double d = vec3.getX() - vec32.getX();
      double d2 = vec3.getY() - vec32.getY();
      double d3 = vec3.getZ() - vec32.getZ();
      double d4 = FastAtanMath.atan2Approximation((float)d, (float)(-d3)) * (180.0 / Math.PI);
      double d5 = Math.sqrt(d * d + d3 * d3);
      double d6 = FastAtanMath.atan2Approximation((float)d2, (float)d5) * (180.0 / Math.PI);
      float f3 = RotationManager.INSTANCE.getMouseSensitivity();
      float f4 = f3 * 0.6F + 0.2F;
      float f5 = f4 * f4 * f4 * 8.0F;
      float f6 = (float)(0.0 + f5 * 0.15);
      float f7 = MathUtil.wrapAngleTo180((float)(d4 - f));
      int n = Math.round(f7 / f6);
      float f8 = MathUtil.wrapAngleTo180((float)(d6 - f2));
      int n2 = Math.round(f8 / f6);
      return new RotationAngles(f + f6 * n, f2 + f6 * n2);
   }
}
