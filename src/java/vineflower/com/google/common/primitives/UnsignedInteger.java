package com.google.common.primitives;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.math.BigInteger;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
public final class UnsignedInteger extends Number implements Comparable<UnsignedInteger> {
   public static final UnsignedInteger ZERO = fromIntBits(0);
   public static final UnsignedInteger ONE = fromIntBits(1);
   public static final UnsignedInteger MAX_VALUE = fromIntBits(-1);
   private final int value;

   private UnsignedInteger(int value) {
      this.value = value & -1;
   }

   public static UnsignedInteger fromIntBits(int bits) {
      return new UnsignedInteger(bits);
   }

   public static UnsignedInteger valueOf(long value) {
      Preconditions.checkArgument((value & 4294967295L) == value, "value (%s) is outside the range for an unsigned integer value", value);
      return fromIntBits((int)value);
   }

   public static UnsignedInteger valueOf(BigInteger value) {
      Preconditions.checkNotNull(value);
      Preconditions.checkArgument(value.signum() >= 0 && value.bitLength() <= 32, "value (%s) is outside the range for an unsigned integer value", value);
      return fromIntBits(value.intValue());
   }

   public static UnsignedInteger valueOf(String string) {
      return valueOf(string, 10);
   }

   public static UnsignedInteger valueOf(String string, int radix) {
      return fromIntBits(UnsignedInts.parseUnsignedInt(string, radix));
   }

   @CheckReturnValue
   public UnsignedInteger plus(UnsignedInteger val) {
      return fromIntBits(this.value + Preconditions.checkNotNull(val).value);
   }

   @CheckReturnValue
   public UnsignedInteger minus(UnsignedInteger val) {
      return fromIntBits(this.value - Preconditions.checkNotNull(val).value);
   }

   @CheckReturnValue
   @GwtIncompatible("Does not truncate correctly")
   public UnsignedInteger times(UnsignedInteger val) {
      return fromIntBits(this.value * Preconditions.checkNotNull(val).value);
   }

   @CheckReturnValue
   public UnsignedInteger dividedBy(UnsignedInteger val) {
      return fromIntBits(UnsignedInts.divide(this.value, Preconditions.checkNotNull(val).value));
   }

   @CheckReturnValue
   public UnsignedInteger mod(UnsignedInteger val) {
      return fromIntBits(UnsignedInts.remainder(this.value, Preconditions.checkNotNull(val).value));
   }

   @Override
   public int intValue() {
      return this.value;
   }

   @Override
   public long longValue() {
      return UnsignedInts.toLong(this.value);
   }

   @Override
   public float floatValue() {
      return (float)this.longValue();
   }

   @Override
   public double doubleValue() {
      return this.longValue();
   }

   public BigInteger bigIntegerValue() {
      return BigInteger.valueOf(this.longValue());
   }

   public int compareTo(UnsignedInteger other) {
      Preconditions.checkNotNull(other);
      return UnsignedInts.compare(this.value, other.value);
   }

   @Override
   public int hashCode() {
      return this.value;
   }

   @Override
   public boolean equals(@Nullable Object obj) {
      if (obj instanceof UnsignedInteger) {
         UnsignedInteger other = (UnsignedInteger)obj;
         return this.value == other.value;
      } else {
         return false;
      }
   }

   @Override
   public String toString() {
      return this.toString(10);
   }

   public String toString(int radix) {
      return UnsignedInts.toString(this.value, radix);
   }
}
