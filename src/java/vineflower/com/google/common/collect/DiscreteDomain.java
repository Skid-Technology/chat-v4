package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.NoSuchElementException;

@GwtCompatible
@Beta
public abstract class DiscreteDomain<C extends Comparable> {
   public static DiscreteDomain<Integer> integers() {
      return DiscreteDomain.IntegerDomain.INSTANCE;
   }

   public static DiscreteDomain<Long> longs() {
      return DiscreteDomain.LongDomain.INSTANCE;
   }

   public static DiscreteDomain<BigInteger> bigIntegers() {
      return DiscreteDomain.BigIntegerDomain.INSTANCE;
   }

   protected DiscreteDomain() {
   }

   public abstract C next(C var1);

   public abstract C previous(C var1);

   public abstract long distance(C var1, C var2);

   public C minValue() {
      throw new NoSuchElementException();
   }

   public C maxValue() {
      throw new NoSuchElementException();
   }

   private static final class BigIntegerDomain extends DiscreteDomain<BigInteger> implements Serializable {
      private static final DiscreteDomain.BigIntegerDomain INSTANCE = new DiscreteDomain.BigIntegerDomain();
      private static final BigInteger MIN_LONG = BigInteger.valueOf(Long.MIN_VALUE);
      private static final BigInteger MAX_LONG = BigInteger.valueOf(Long.MAX_VALUE);
      private static final long serialVersionUID = 0L;

      public BigInteger next(BigInteger value) {
         return value.add(BigInteger.ONE);
      }

      public BigInteger previous(BigInteger value) {
         return value.subtract(BigInteger.ONE);
      }

      public long distance(BigInteger start, BigInteger end) {
         return end.subtract(start).max(MIN_LONG).min(MAX_LONG).longValue();
      }

      private Object readResolve() {
         return INSTANCE;
      }

      @Override
      public String toString() {
         return "DiscreteDomain.bigIntegers()";
      }
   }

   private static final class IntegerDomain extends DiscreteDomain<Integer> implements Serializable {
      private static final DiscreteDomain.IntegerDomain INSTANCE = new DiscreteDomain.IntegerDomain();
      private static final long serialVersionUID = 0L;

      public Integer next(Integer value) {
         int i = value;
         return i == Integer.MAX_VALUE ? null : i + 1;
      }

      public Integer previous(Integer value) {
         int i = value;
         return i == Integer.MIN_VALUE ? null : i - 1;
      }

      public long distance(Integer start, Integer end) {
         return (long)end.intValue() - start.intValue();
      }

      public Integer minValue() {
         return Integer.MIN_VALUE;
      }

      public Integer maxValue() {
         return Integer.MAX_VALUE;
      }

      private Object readResolve() {
         return INSTANCE;
      }

      @Override
      public String toString() {
         return "DiscreteDomain.integers()";
      }
   }

   private static final class LongDomain extends DiscreteDomain<Long> implements Serializable {
      private static final DiscreteDomain.LongDomain INSTANCE = new DiscreteDomain.LongDomain();
      private static final long serialVersionUID = 0L;

      public Long next(Long value) {
         long l = value;
         return l == Long.MAX_VALUE ? null : l + 1L;
      }

      public Long previous(Long value) {
         long l = value;
         return l == Long.MIN_VALUE ? null : l - 1L;
      }

      public long distance(Long start, Long end) {
         long result = end - start;
         if (end > start && result < 0L) {
            return Long.MAX_VALUE;
         } else {
            return end < start && result > 0L ? Long.MIN_VALUE : result;
         }
      }

      public Long minValue() {
         return Long.MIN_VALUE;
      }

      public Long maxValue() {
         return Long.MAX_VALUE;
      }

      private Object readResolve() {
         return INSTANCE;
      }

      @Override
      public String toString() {
         return "DiscreteDomain.longs()";
      }
   }
}
