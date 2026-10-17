package gg.vape.utils;

import gg.vape.wrapper.impl.Vec3i;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MathUtil {
   private static final double[] S = new double[257];
   private static final int[] J;
   private static final Map d = new HashMap(13);
   private static int s;
   private static final double[] Y = new double[257];
   private static final long[] b;
   private static final float[] A = new float[65536];
   private static final double Z = Double.longBitsToDouble(4805340802404319232L);
   private static final Integer[] c = new Integer[5];

   private static int log2Ceil(int n) {
      n = isPowerOfTwo(n) ? n : nextPowerOfTwo(n);
      return J[(int)(n * 125613361L >> 27) & 31];
   }

   public static int t() {
      int n = n();
      return 0;
   }

   public static double pushAwayFromZeroIfWithinThreshold(double d, double d2) {
      double d3 = d;
      if (d > 0.0) {
         if (d < d2) {
            d3 = d + d2;
         }
      } else if (d > -d2) {
         d3 = d - d2;
      }

      return d3;
   }

   public static double H(double d) {
      return d >= 0.0 ? d : -d;
   }

   public static float abs(float f) {
      return f >= 0.0F ? f : -f;
   }

   public static double p(double d, double d2) {
      int n = (int)Math.floor(d / d2);
      return d - n * d2;
   }

   public static double Z(double d, double d2, double d3, double d4, double d5, double d6) {
      return Math.sqrt(Math.pow(d - d4, 2.0) + Math.pow(d2 - d5, 2.0) + Math.pow(d3 - d6, 2.0));
   }

   public static long S(int n, int n2, int n3) {
      long l2 = n * 3129871 ^ n3 * 116129781L ^ n2;
      return l2 * l2 * 42317861L + l2 * 11L;
   }

   public static int parseInt(String string, int n) {
      int n2 = n;

      try {
         n2 = Integer.parseInt(string);
      } catch (Throwable var4) {
      }

      return n2;
   }

   public static float round(float f, float f2, float f3) {
      return f2 + f * (f3 - f2);
   }

   public static long S(Vec3i vec3i) {
      return S(vec3i.getX(), vec3i.getY(), vec3i.getZ());
   }

   public static double g(double... dArray) {
      double d = 0.0;

      for (double d2 : dArray) {
         d += Math.pow(d2, 2.0);
      }

      return Math.sqrt(d);
   }

   public static int absFloor(double d) {
      return (int)(d >= 0.0 ? d : -d + 1.0);
   }

   public static long floorLong(double d) {
      long l = (long)d;
      return d < l ? l - 1L : l;
   }

   public static double roundToIncrement(double d, double d2) {
      double d3 = 1.0 / d2;
      return Math.round(d * d3) / d3;
   }

   public static float c(float f, float f2) {
      int n = (int)Math.floor(f / f2);
      return f - n * f2;
   }

   public static int floor(double d) {
      int n = (int)d;
      return d < n ? n - 1 : n;
   }

   public static boolean isNullOrEmpty(String string) {
      return string == null || string.length() == 0;
   }

   public static double clampedLerp(double d, double d2, double d3) {
      return d3 < 0.0 ? d : (d3 > 1.0 ? d2 : d + (d2 - d) * d3);
   }

   public static int clamp(int n, int n2, int n3) {
      return n < n2 ? n2 : (n > n3 ? n3 : n);
   }

   public static int floorDiv(int n, int n2) {
      return n < 0 ? -((-n - 1) / n2) - 1 : n / n2;
   }

   public static float random(Random random, float f, float f2) {
      return f >= f2 ? f : random.nextFloat() * (f2 - f) + f;
   }

   public static int randomExclusiveUpper(Random random, int n, int n2) {
      return random.nextInt(n2 - n) + n;
   }

   public static double D(double d) {
      double d2 = 0.5 * d;
      long l = Double.doubleToRawLongBits(d);
      l = 6910469410427058090L - (l >> 1);
      d = Double.longBitsToDouble(l);
      return d * (1.5 - d2 * d * d);
   }

   public static int ceil(float f) {
      int n = (int)f;
      return f > n ? n + 1 : n;
   }

   public static int log2(int n) {
      return log2Ceil(n) - (isPowerOfTwo(n) ? 0 : 1);
   }

   public static double stepAwayFromZero(double d, double d2) {
      return d > 0.0 ? d + Math.abs(d2) : d - Math.abs(d2);
   }

   public static void g(int n) {
      s = n;
   }

   public static final float sin(float f) {
      return A[(int)(f * 10430.378F) & 65535];
   }

   public static float clamp(float f, float f2, float f3) {
      return f < f2 ? f2 : (f > f3 ? f3 : f);
   }

   public static int parseIntWithMin(String string, int n, int n2) {
      int n3 = n;

      try {
         n3 = Integer.parseInt(string);
      } catch (Throwable var5) {
      }

      if (n3 < n2) {
         n3 = n2;
      }

      return n3;
   }

   public static double clamp(double d, double d2, double d3) {
      return d < d2 ? d2 : (d > d3 ? d3 : d);
   }

   public static boolean e(double d, double d2, double d3) {
      return d >= d2 && d <= d3;
   }

   public static double roundToScale(double d, int n) {
      if (n < 0) {
         return d;
      } else {
         BigDecimal bigDecimal = new BigDecimal(d);
         bigDecimal = bigDecimal.setScale(n, RoundingMode.HALF_UP);
         return bigDecimal.doubleValue();
      }
   }

   public static boolean H(double d, double d2, double d3, double d4, double d5) {
      double d7 = d - d4;
      double d8 = d2 - d5;
      double d9 = d3 * d3;
      double var18;
      double var19;
      return (var18 = d7 * d7) + (var19 = d8 * d8) <= d9;
   }

   public static int stepAwayFromZero(int n, int n2) {
      return n > 0 ? n + Math.abs(n2) : n - Math.abs(n2);
   }

   public static double average(long[] lArray) {
      long l = 0L;

      for (long l2 : lArray) {
         l += l2;
      }

      return (double)l / lArray.length;
   }

   public static double maxAbs(double d, double d2) {
      if (d < 0.0) {
         d = -d;
      }

      if (d2 < 0.0) {
         d2 = -d2;
      }

      return d > d2 ? d : d2;
   }

   public static float wrapAngleTo180(float f) {
      if ((f = f % 360.0F) >= 180.0F) {
         f -= 360.0F;
      }

      if (f < -180.0F) {
         f += 360.0F;
      }

      return f;
   }

   public static int n() {
      return s;
   }

   public static final float sqrt(float f) {
      return (float)Math.sqrt(f);
   }

   public static double parseDoubleWithMin(String string, double d, double d2) {
      double d3 = d;

      try {
         d3 = Double.parseDouble(string);
      } catch (Throwable var8) {
      }

      if (d3 < d2) {
         d3 = d2;
      }

      return d3;
   }

   public static int floor(float f) {
      int n = (int)f;
      return f < n ? n - 1 : n;
   }

   public static final float cos(float f) {
      return A[(int)(f * 10430.378F + 16384.0F) & 65535];
   }

   public static int roundAwayFromZeroToMultiple(int n, int n2) {
      if (n2 == 0) {
         return 0;
      } else {
         if (n < 0) {
            n2 *= -1;
         }

         int n3;
         return (n3 = n % n2) == 0 ? n : n + n2 - n3;
      }
   }

   public static double parseDouble(String string, double d) {
      double d2 = d;

      try {
         d2 = Double.parseDouble(string);
      } catch (Throwable var6) {
      }

      return d2;
   }

   public static final float sqrt(double d) {
      return (float)Math.sqrt(d);
   }

   public static boolean r(int n, int n2, int n3) {
      return n >= n2 && n <= n3;
   }

   public static double wrapAngleTo180(double d) {
      if ((d = d % 360.0) >= 180.0) {
         d -= 360.0;
      }

      if (d < -180.0) {
         d += 360.0;
      }

      return d;
   }

   public static boolean O(double d, double d2, double d3, double d4, double d5) {
      return d4 >= d - d3 && d4 <= d + d3 && d5 >= d2 - d3 && d5 <= d2 + d3;
   }

   public static double randomRange(Random random, double d, double d2) {
      return d + (d2 - d) * random.nextDouble();
   }

   public static int ceil(double d) {
      int n = (int)d;
      return d > n ? n + 1 : n;
   }

   public static boolean e(float f, float f2, float f3) {
      return f >= f2 && f <= f3;
   }

   public static int fastFloor(double d) {
      return (int)(d + 1024.0) - 1024;
   }

   public static double V(double d, double d2) {
      double d4 = d2 * d2 + d * d;
      if (Double.isNaN(d4)) {
         return Double.NaN;
      } else {
         boolean bl3 = d < 0.0;
         if (bl3) {
            d = -d;
         }

         boolean bl2 = d2 < 0.0;
         if (bl2) {
            d2 = -d2;
         }

         boolean bl = d > d2;
         if (bl) {
            double d3 = d2;
            d2 = d;
            d = d3;
         }

         double d3 = D(d4);
         d2 *= d3;
         double var29;
         double d5 = Z + (var29 = d * d3);
         int n = (int)Double.doubleToRawLongBits(d5);
         double d6 = Y[n];
         double d7 = S[n];
         double d8 = d5 - Z;
         double d9 = var29 * d7 - d2 * d8;
         double d10 = (6.0 + d9 * d9) * d9 * 0.16666666666666666;
         double d11 = d6 + d10;
         if (bl) {
            d11 = (Math.PI / 2) - d11;
         }

         if (bl2) {
            d11 = Math.PI - d11;
         }

         if (bl3) {
            d11 = -d11;
         }

         return d11;
      }
   }

   private static boolean isPowerOfTwo(int n) {
      return n != 0 && (n & n - 1) == 0;
   }

   public static int randomInclusive(Random random, int n, int n2) {
      return n >= n2 ? n : random.nextInt(n2 - n + 1) + n;
   }

   public static int abs(int n) {
      return n >= 0 ? n : -n;
   }

   public static int nextPowerOfTwo(int n) {
      int n2 = n - 1;
      n2 |= n2 >> 1;
      n2 |= n2 >> 2;
      n2 |= n2 >> 4;
      n2 |= n2 >> 8;
      n2 |= n2 >> 16;
      return n2 + 1;
   }

   public static double random(Random random, double d, double d2) {
      return d >= d2 ? d : random.nextDouble() * (d2 - d) + d;
   }

   static {
      g(91);
      long[] lArray = new long[]{-5058601735682614043L, -6095211415617468827L, -3400524149349193330L, -334337254439352656L, -3133572606271077933L};
      b = lArray;

      for (int n = 0; n < 65536; n++) {
         A[n] = (float)Math.sin(n * Math.PI * 2.0 / 65536.0);
      }

      J = new int[]{0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9};
   }
}
