package com.google.common.math;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Booleans;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Iterator;

@GwtCompatible(emulated = true)
public final class DoubleMath {
   private static final double MIN_INT_AS_DOUBLE = -2.1474836E9F;
   private static final double MAX_INT_AS_DOUBLE = 2.147483647E9;
   private static final double MIN_LONG_AS_DOUBLE = -9.223372E18F;
   private static final double MAX_LONG_AS_DOUBLE_PLUS_ONE = 9.223372E18F;
   private static final double LN_2 = Math.log(2.0);
   @VisibleForTesting
   static final int MAX_FACTORIAL = 170;
   @VisibleForTesting
   static final double[] everySixteenthFactorial = new double[]{
      1.0,
      2.0922789888E13,
      2.631308369336935E35,
      1.2413915592536073E61,
      1.2688693218588417E89,
      7.156945704626381E118,
      9.916779348709496E149,
      1.974506857221074E182,
      3.856204823625804E215,
      5.5502938327393044E249,
      4.7147236359920616E284
   };

   @GwtIncompatible("#isMathematicalInteger, com.google.common.math.DoubleUtils")
   static double roundIntermediate(double x, RoundingMode mode) {
      if (!DoubleUtils.isFinite(x)) {
         throw new ArithmeticException("input is infinite or NaN");
      } else {
         switch (mode) {
            case UNNECESSARY:
               MathPreconditions.checkRoundingUnnecessary(isMathematicalInteger(x));
               return x;
            case FLOOR:
               if (!(x >= 0.0) && !isMathematicalInteger(x)) {
                  return x - 1.0;
               }

               return x;
            case CEILING:
               if (!(x <= 0.0) && !isMathematicalInteger(x)) {
                  return x + 1.0;
               }

               return x;
            case DOWN:
               return x;
            case UP:
               if (isMathematicalInteger(x)) {
                  return x;
               }

               return x + Math.copySign(1.0, x);
            case HALF_EVEN:
               return Math.rint(x);
            case HALF_UP:
               double z = Math.rint(x);
               if (Math.abs(x - z) == 0.5) {
                  return x + Math.copySign(0.5, x);
               }

               return z;
            case HALF_DOWN:
               double z = Math.rint(x);
               if (Math.abs(x - z) == 0.5) {
                  return x;
               }

               return z;
            default:
               throw new AssertionError();
         }
      }
   }

   @GwtIncompatible("#roundIntermediate")
   public static int roundToInt(double x, RoundingMode mode) {
      double z = roundIntermediate(x, mode);
      MathPreconditions.checkInRange(z > -2.147483649E9 & z < 2.1474836E9F);
      return (int)z;
   }

   @GwtIncompatible("#roundIntermediate")
   public static long roundToLong(double x, RoundingMode mode) {
      double z = roundIntermediate(x, mode);
      MathPreconditions.checkInRange(-9.223372E18F - z < 1.0 & z < 9.223372E18F);
      return (long)z;
   }

   @GwtIncompatible("#roundIntermediate, java.lang.Math.getExponent, com.google.common.math.DoubleUtils")
   public static BigInteger roundToBigInteger(double x, RoundingMode mode) {
      x = roundIntermediate(x, mode);
      if (-9.223372E18F - x < 1.0 & x < 9.223372E18F) {
         return BigInteger.valueOf((long)x);
      } else {
         int exponent = Math.getExponent(x);
         long significand = DoubleUtils.getSignificand(x);
         BigInteger result = BigInteger.valueOf(significand).shiftLeft(exponent - 52);
         return x < 0.0 ? result.negate() : result;
      }
   }

   @GwtIncompatible("com.google.common.math.DoubleUtils")
   public static boolean isPowerOfTwo(double x) {
      return x > 0.0 && DoubleUtils.isFinite(x) && LongMath.isPowerOfTwo(DoubleUtils.getSignificand(x));
   }

   public static double log2(double x) {
      return Math.log(x) / LN_2;
   }

   @GwtIncompatible("java.lang.Math.getExponent, com.google.common.math.DoubleUtils")
   public static int log2(double x, RoundingMode mode) {
      Preconditions.checkArgument(x > 0.0 && DoubleUtils.isFinite(x), "x must be positive and finite");
      int exponent = Math.getExponent(x);
      if (!DoubleUtils.isNormal(x)) {
         return log2(x * 4.5035996E15F, mode) - 52;
      } else {
         boolean increment;
         switch (mode) {
            case UNNECESSARY:
               MathPreconditions.checkRoundingUnnecessary(isPowerOfTwo(x));
            case FLOOR:
               increment = false;
               break;
            case CEILING:
               increment = !isPowerOfTwo(x);
               break;
            case DOWN:
               increment = exponent < 0 & !isPowerOfTwo(x);
               break;
            case UP:
               increment = exponent >= 0 & !isPowerOfTwo(x);
               break;
            case HALF_EVEN:
            case HALF_UP:
            case HALF_DOWN:
               double xScaled = DoubleUtils.scaleNormalize(x);
               increment = xScaled * xScaled > 2.0;
               break;
            default:
               throw new AssertionError();
         }

         return increment ? exponent + 1 : exponent;
      }
   }

   @GwtIncompatible("java.lang.Math.getExponent, com.google.common.math.DoubleUtils")
   public static boolean isMathematicalInteger(double x) {
      return DoubleUtils.isFinite(x) && (x == 0.0 || 52 - Long.numberOfTrailingZeros(DoubleUtils.getSignificand(x)) <= Math.getExponent(x));
   }

   public static double factorial(int n) {
      MathPreconditions.checkNonNegative("n", n);
      if (n > 170) {
         return Double.POSITIVE_INFINITY;
      } else {
         double accum = 1.0;

         for (int i = 1 + (n & -16); i <= n; i++) {
            accum *= i;
         }

         return accum * everySixteenthFactorial[n >> 4];
      }
   }

   public static boolean fuzzyEquals(double a, double b, double tolerance) {
      MathPreconditions.checkNonNegative("tolerance", tolerance);
      return Math.copySign(a - b, 1.0) <= tolerance || a == b || Double.isNaN(a) && Double.isNaN(b);
   }

   public static int fuzzyCompare(double a, double b, double tolerance) {
      if (fuzzyEquals(a, b, tolerance)) {
         return 0;
      } else if (a < b) {
         return -1;
      } else {
         return a > b ? 1 : Booleans.compare(Double.isNaN(a), Double.isNaN(b));
      }
   }

   @GwtIncompatible("MeanAccumulator")
   public static double mean(double... values) {
      DoubleMath.MeanAccumulator accumulator = new DoubleMath.MeanAccumulator();

      for (double value : values) {
         accumulator.add(value);
      }

      return accumulator.mean();
   }

   @GwtIncompatible("MeanAccumulator")
   public static double mean(int... values) {
      DoubleMath.MeanAccumulator accumulator = new DoubleMath.MeanAccumulator();

      for (int value : values) {
         accumulator.add(value);
      }

      return accumulator.mean();
   }

   @GwtIncompatible("MeanAccumulator")
   public static double mean(long... values) {
      DoubleMath.MeanAccumulator accumulator = new DoubleMath.MeanAccumulator();

      for (long value : values) {
         accumulator.add(value);
      }

      return accumulator.mean();
   }

   @GwtIncompatible("MeanAccumulator")
   public static double mean(Iterable<? extends Number> values) {
      DoubleMath.MeanAccumulator accumulator = new DoubleMath.MeanAccumulator();

      for (Number value : values) {
         accumulator.add(value.doubleValue());
      }

      return accumulator.mean();
   }

   @GwtIncompatible("MeanAccumulator")
   public static double mean(Iterator<? extends Number> values) {
      DoubleMath.MeanAccumulator accumulator = new DoubleMath.MeanAccumulator();

      while (values.hasNext()) {
         accumulator.add(values.next().doubleValue());
      }

      return accumulator.mean();
   }

   private DoubleMath() {
   }

   @GwtIncompatible("com.google.common.math.DoubleUtils")
   private static final class MeanAccumulator {
      private long count = 0L;
      private double mean = 0.0;

      private MeanAccumulator() {
      }

      void add(double value) {
         Preconditions.checkArgument(DoubleUtils.isFinite(value));
         this.count++;
         this.mean = this.mean + (value - this.mean) / this.count;
      }

      double mean() {
         Preconditions.checkArgument(this.count > 0L, "Cannot take mean of 0 values");
         return this.mean;
      }
   }
}
