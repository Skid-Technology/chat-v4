package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.Comparator;
import javax.annotation.Nullable;

@GwtCompatible(serializable = true)
final class GeneralRange<T> implements Serializable {
   private final Comparator<? super T> comparator;
   private final boolean hasLowerBound;
   @Nullable
   private final T lowerEndpoint;
   private final BoundType lowerBoundType;
   private final boolean hasUpperBound;
   @Nullable
   private final T upperEndpoint;
   private final BoundType upperBoundType;
   private transient GeneralRange<T> reverse;

   static <T extends Comparable> GeneralRange<T> from(Range<T> range) {
      T lowerEndpoint = range.hasLowerBound() ? range.lowerEndpoint() : null;
      BoundType lowerBoundType = range.hasLowerBound() ? range.lowerBoundType() : BoundType.OPEN;
      T upperEndpoint = range.hasUpperBound() ? range.upperEndpoint() : null;
      BoundType upperBoundType = range.hasUpperBound() ? range.upperBoundType() : BoundType.OPEN;
      return new GeneralRange<>(Ordering.natural(), range.hasLowerBound(), lowerEndpoint, lowerBoundType, range.hasUpperBound(), upperEndpoint, upperBoundType);
   }

   static <T> GeneralRange<T> all(Comparator<? super T> comparator) {
      return new GeneralRange<>(comparator, false, null, BoundType.OPEN, false, null, BoundType.OPEN);
   }

   static <T> GeneralRange<T> downTo(Comparator<? super T> comparator, @Nullable T endpoint, BoundType boundType) {
      return new GeneralRange<>(comparator, true, endpoint, boundType, false, null, BoundType.OPEN);
   }

   static <T> GeneralRange<T> upTo(Comparator<? super T> comparator, @Nullable T endpoint, BoundType boundType) {
      return new GeneralRange<>(comparator, false, null, BoundType.OPEN, true, endpoint, boundType);
   }

   static <T> GeneralRange<T> range(Comparator<? super T> comparator, @Nullable T lower, BoundType lowerType, @Nullable T upper, BoundType upperType) {
      return new GeneralRange<>(comparator, true, lower, lowerType, true, upper, upperType);
   }

   private GeneralRange(
      Comparator<? super T> comparator,
      boolean hasLowerBound,
      @Nullable T lowerEndpoint,
      BoundType lowerBoundType,
      boolean hasUpperBound,
      @Nullable T upperEndpoint,
      BoundType upperBoundType
   ) {
      this.comparator = Preconditions.checkNotNull(comparator);
      this.hasLowerBound = hasLowerBound;
      this.hasUpperBound = hasUpperBound;
      this.lowerEndpoint = lowerEndpoint;
      this.lowerBoundType = Preconditions.checkNotNull(lowerBoundType);
      this.upperEndpoint = upperEndpoint;
      this.upperBoundType = Preconditions.checkNotNull(upperBoundType);
      if (hasLowerBound) {
         comparator.compare(lowerEndpoint, lowerEndpoint);
      }

      if (hasUpperBound) {
         comparator.compare(upperEndpoint, upperEndpoint);
      }

      if (hasLowerBound && hasUpperBound) {
         int cmp = comparator.compare(lowerEndpoint, upperEndpoint);
         Preconditions.checkArgument(cmp <= 0, "lowerEndpoint (%s) > upperEndpoint (%s)", lowerEndpoint, upperEndpoint);
         if (cmp == 0) {
            Preconditions.checkArgument(lowerBoundType != BoundType.OPEN | upperBoundType != BoundType.OPEN);
         }
      }
   }

   Comparator<? super T> comparator() {
      return this.comparator;
   }

   boolean hasLowerBound() {
      return this.hasLowerBound;
   }

   boolean hasUpperBound() {
      return this.hasUpperBound;
   }

   boolean isEmpty() {
      return this.hasUpperBound() && this.tooLow(this.getUpperEndpoint()) || this.hasLowerBound() && this.tooHigh(this.getLowerEndpoint());
   }

   boolean tooLow(@Nullable T t) {
      if (!this.hasLowerBound()) {
         return false;
      } else {
         T lbound = this.getLowerEndpoint();
         int cmp = this.comparator.compare(t, lbound);
         return cmp < 0 | cmp == 0 & this.getLowerBoundType() == BoundType.OPEN;
      }
   }

   boolean tooHigh(@Nullable T t) {
      if (!this.hasUpperBound()) {
         return false;
      } else {
         T ubound = this.getUpperEndpoint();
         int cmp = this.comparator.compare(t, ubound);
         return cmp > 0 | cmp == 0 & this.getUpperBoundType() == BoundType.OPEN;
      }
   }

   boolean contains(@Nullable T t) {
      return !this.tooLow(t) && !this.tooHigh(t);
   }

   GeneralRange<T> intersect(GeneralRange<T> other) {
      Preconditions.checkNotNull(other);
      Preconditions.checkArgument(this.comparator.equals(other.comparator));
      boolean hasLowBound = this.hasLowerBound;
      T lowEnd = this.getLowerEndpoint();
      BoundType lowType = this.getLowerBoundType();
      if (!this.hasLowerBound()) {
         hasLowBound = other.hasLowerBound;
         lowEnd = other.getLowerEndpoint();
         lowType = other.getLowerBoundType();
      } else if (other.hasLowerBound()) {
         int cmp = this.comparator.compare(this.getLowerEndpoint(), other.getLowerEndpoint());
         if (cmp < 0 || cmp == 0 && other.getLowerBoundType() == BoundType.OPEN) {
            lowEnd = other.getLowerEndpoint();
            lowType = other.getLowerBoundType();
         }
      }

      boolean hasUpBound = this.hasUpperBound;
      T upEnd = this.getUpperEndpoint();
      BoundType upType = this.getUpperBoundType();
      if (!this.hasUpperBound()) {
         hasUpBound = other.hasUpperBound;
         upEnd = other.getUpperEndpoint();
         upType = other.getUpperBoundType();
      } else if (other.hasUpperBound()) {
         int cmp = this.comparator.compare(this.getUpperEndpoint(), other.getUpperEndpoint());
         if (cmp > 0 || cmp == 0 && other.getUpperBoundType() == BoundType.OPEN) {
            upEnd = other.getUpperEndpoint();
            upType = other.getUpperBoundType();
         }
      }

      if (hasLowBound && hasUpBound) {
         int cmp = this.comparator.compare(lowEnd, upEnd);
         if (cmp > 0 || cmp == 0 && lowType == BoundType.OPEN && upType == BoundType.OPEN) {
            lowEnd = upEnd;
            lowType = BoundType.OPEN;
            upType = BoundType.CLOSED;
         }
      }

      return new GeneralRange<>(this.comparator, hasLowBound, lowEnd, lowType, hasUpBound, upEnd, upType);
   }

   @Override
   public boolean equals(@Nullable Object obj) {
      if (!(obj instanceof GeneralRange)) {
         return false;
      } else {
         GeneralRange<?> r = (GeneralRange<?>)obj;
         return this.comparator.equals(r.comparator)
            && this.hasLowerBound == r.hasLowerBound
            && this.hasUpperBound == r.hasUpperBound
            && this.getLowerBoundType().equals(r.getLowerBoundType())
            && this.getUpperBoundType().equals(r.getUpperBoundType())
            && Objects.equal(this.getLowerEndpoint(), r.getLowerEndpoint())
            && Objects.equal(this.getUpperEndpoint(), r.getUpperEndpoint());
      }
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(this.comparator, this.getLowerEndpoint(), this.getLowerBoundType(), this.getUpperEndpoint(), this.getUpperBoundType());
   }

   GeneralRange<T> reverse() {
      GeneralRange<T> result = this.reverse;
      if (result == null) {
         result = new GeneralRange<>(
            Ordering.from(this.comparator).reverse(),
            this.hasUpperBound,
            this.getUpperEndpoint(),
            this.getUpperBoundType(),
            this.hasLowerBound,
            this.getLowerEndpoint(),
            this.getLowerBoundType()
         );
         result.reverse = this;
         return this.reverse = result;
      } else {
         return result;
      }
   }

   @Override
   public String toString() {
      return this.comparator
         + ":"
         + (this.lowerBoundType == BoundType.CLOSED ? 91 : 40)
         + (this.hasLowerBound ? this.lowerEndpoint : "-∞")
         + ','
         + (this.hasUpperBound ? this.upperEndpoint : "∞")
         + (this.upperBoundType == BoundType.CLOSED ? 93 : 41);
   }

   T getLowerEndpoint() {
      return this.lowerEndpoint;
   }

   BoundType getLowerBoundType() {
      return this.lowerBoundType;
   }

   T getUpperEndpoint() {
      return this.upperEndpoint;
   }

   BoundType getUpperBoundType() {
      return this.upperBoundType;
   }
}
