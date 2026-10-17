package gg.vape.utils;

public class FastAtanMath {
   private static final float[] w;
   private static final int U;
   private static final float[] R;
   private static final float P = (float) (Math.PI / 2);
   private static final float[] G;
   private static final float[] S;
   private static final int a;
   private static final float[] c;
   private static final float A = (float) Math.PI;
   private static final float[] d;
   private static final float[] v;
   private static final float[] O;
   private static final float[] M;

   public static float atan2Approximation(float f, float f2) {
      if (f < 0.0F) {
         if (f2 < 0.0F) {
            return f < f2 ? S[(int)(f2 / f * 100000.0F)] : G[(int)(f / f2 * 100000.0F)];
         } else {
            float var2;
            return (var2 = -f) > f2 ? O[(int)(f2 / var2 * 100000.0F)] : d[(int)(var2 / f2 * 100000.0F)];
         }
      } else if (f2 < 0.0F) {
         float var3;
         return f > (var3 = -f2) ? c[(int)(var3 / f * 100000.0F)] : M[(int)(f / var3 * 100000.0F)];
      } else {
         return f > f2 ? v[(int)(f2 / f * 100000.0F)] : w[(int)(f / f2 * 100000.0F)];
      }
   }

   public static float atanApproximation(float f) {
      float f3 = Math.abs(f);
      float f2;
      if (f3 < 1.0F) {
         float f4 = f * f;
         f2 = f * (1.0F + f4 * (R[0] + f4 * (R[1] + f4 * (R[2] + f4 * R[3]))));
      } else {
         float f5 = 1.0F / f3;
         float f6 = f5 * f5;
         float f7 = f5 * (1.0F + f6 * (R[0] + f6 * (R[1] + f6 * (R[2] + f6 * R[3]))));
         f2 = Math.copySign(R[4], f) - f7;
      }

      return f2;
   }

   static {
      long[] lArray = new long[]{-4778158233560709471L, -4211190230859807071L, 3095591377875076769L, -5764595647449561440L, 8562453793512457888L};
      a = (int)lArray[3];
      U = (int)lArray[2];
      w = new float[(int)lArray[0]];
      M = new float[(int)lArray[1]];
      d = new float[(int)lArray[1]];
      G = new float[(int)lArray[1]];
      v = new float[(int)lArray[1]];
      c = new float[(int)lArray[1]];
      O = new float[(int)lArray[1]];
      S = new float[(int)lArray[1]];

      for (int i = 0; i <= (int)lArray[4]; i++) {
         double d = i / 100000.0;
         double d2 = 1.0;
         double d3 = d2 * d;
         float f;
         w[i] = f = (float)Math.atan2(d3, d2);
         M[i] = (float) Math.PI - f;
         FastAtanMath.d[i] = -f;
         G[i] = (float) -Math.PI + f;
         v[i] = (float) (Math.PI / 2) - f;
         c[i] = (float) (Math.PI / 2) + f;
         O[i] = (float) (-Math.PI / 2) + f;
         S[i] = (float) (-Math.PI / 2) - f;
      }

      R = new float[]{-0.33333147F, 0.19993551F, -0.142089F, 0.10656264F, (float) (Math.PI / 2)};
   }
}
