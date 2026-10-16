/*
 * Decompiled with CFR 0.152.
 */
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
        return IntegerDomain.INSTANCE;
    }

    public static DiscreteDomain<Long> longs() {
        return LongDomain.INSTANCE;
    }

    public static DiscreteDomain<BigInteger> bigIntegers() {
        return BigIntegerDomain.INSTANCE;
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

    private static final class BigIntegerDomain
    extends DiscreteDomain<BigInteger>
    implements Serializable {
        private final static BigIntegerDomain INSTANCE = new BigIntegerDomain();
        private final static BigInteger MIN_LONG = BigInteger.valueOf(Long.MIN_VALUE);
        private final static BigInteger MAX_LONG = BigInteger.valueOf(Long.MAX_VALUE);
        private final static long serialVersionUID = 0L;

        private BigIntegerDomain() {
        }

        @Override
        public BigInteger next(BigInteger value) {
            return value.add(BigInteger.ONE);
        }

        @Override
        public BigInteger previous(BigInteger value) {
            return value.subtract(BigInteger.ONE);
        }

        @Override
        public long distance(BigInteger start, BigInteger end) {
            return end.subtract(start).max(MIN_LONG).min(MAX_LONG).longValue();
        }

        private Object readResolve() {
            return INSTANCE;
        }

        public String toString() {
            return "DiscreteDomain.bigIntegers()";
        }

        @Override
        public long distance(Comparable x0, Comparable x1) {
            return this.distance((BigInteger)x0, (BigInteger)x1);
        }

        @Override
        public Comparable previous(Comparable x0) {
            return this.previous((BigInteger)x0);
        }

        @Override
        public Comparable next(Comparable x0) {
            return this.next((BigInteger)x0);
        }
    }

    private static final class LongDomain
    extends DiscreteDomain<Long>
    implements Serializable {
        private final static LongDomain INSTANCE = new LongDomain();
        private final static long serialVersionUID = 0L;

        private LongDomain() {
        }

        @Override
        public Long next(Long value) {
            long l = value;
            return l == Long.MAX_VALUE ? null : Long.valueOf(l + 1L);
        }

        @Override
        public Long previous(Long value) {
            long l = value;
            return l == Long.MIN_VALUE ? null : Long.valueOf(l - 1L);
        }

        @Override
        public long distance(Long start, Long end) {
            long result = end - start;
            if (end > start && result < 0L) {
                return Long.MAX_VALUE;
            }
            if (end < start && result > 0L) {
                return Long.MIN_VALUE;
            }
            return result;
        }

        @Override
        public Long minValue() {
            return Long.MIN_VALUE;
        }

        @Override
        public Long maxValue() {
            return Long.MAX_VALUE;
        }

        private Object readResolve() {
            return INSTANCE;
        }

        public String toString() {
            return "DiscreteDomain.longs()";
        }

        @Override
        public Comparable maxValue() {
            return this.maxValue();
        }

        @Override
        public Comparable minValue() {
            return this.minValue();
        }

        @Override
        public long distance(Comparable x0, Comparable x1) {
            return this.distance((Long)x0, (Long)x1);
        }

        @Override
        public Comparable previous(Comparable x0) {
            return this.previous((Long)x0);
        }

        @Override
        public Comparable next(Comparable x0) {
            return this.next((Long)x0);
        }
    }

    private static final class IntegerDomain
    extends DiscreteDomain<Integer>
    implements Serializable {
        private final static IntegerDomain INSTANCE = new IntegerDomain();
        private final static long serialVersionUID = 0L;

        private IntegerDomain() {
        }

        @Override
        public Integer next(Integer value) {
            int i = value;
            return i == Integer.MAX_VALUE ? null : Integer.valueOf(i + 1);
        }

        @Override
        public Integer previous(Integer value) {
            int i = value;
            return i == Integer.MIN_VALUE ? null : Integer.valueOf(i - 1);
        }

        @Override
        public long distance(Integer start, Integer end) {
            return (long)end.intValue() - (long)start.intValue();
        }

        @Override
        public Integer minValue() {
            return Integer.MIN_VALUE;
        }

        @Override
        public Integer maxValue() {
            return Integer.MAX_VALUE;
        }

        private Object readResolve() {
            return INSTANCE;
        }

        public String toString() {
            return "DiscreteDomain.integers()";
        }

        @Override
        public Comparable maxValue() {
            return this.maxValue();
        }

        @Override
        public Comparable minValue() {
            return this.minValue();
        }

        @Override
        public long distance(Comparable x0, Comparable x1) {
            return this.distance((Integer)x0, (Integer)x1);
        }

        @Override
        public Comparable previous(Comparable x0) {
            return this.previous((Integer)x0);
        }

        @Override
        public Comparable next(Comparable x0) {
            return this.next((Integer)x0);
        }
    }
}

