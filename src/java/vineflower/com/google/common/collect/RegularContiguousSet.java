package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.Collection;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
final class RegularContiguousSet<C extends Comparable> extends ContiguousSet<C> {
   private final Range<C> range;
   private static final long serialVersionUID = 0L;

   RegularContiguousSet(Range<C> range, DiscreteDomain<C> domain) {
      super(domain);
      this.range = range;
   }

   private ContiguousSet<C> intersectionInCurrentDomain(Range<C> other) {
      return (ContiguousSet<C>)(this.range.isConnected(other)
         ? ContiguousSet.create(this.range.intersection(other), this.domain)
         : new EmptyContiguousSet<>(this.domain));
   }

   @Override
   ContiguousSet<C> headSetImpl(C toElement, boolean inclusive) {
      return this.intersectionInCurrentDomain(Range.upTo(toElement, BoundType.forBoolean(inclusive)));
   }

   @Override
   ContiguousSet<C> subSetImpl(C fromElement, boolean fromInclusive, C toElement, boolean toInclusive) {
      return (ContiguousSet<C>)(fromElement.compareTo(toElement) == 0 && !fromInclusive && !toInclusive
         ? new EmptyContiguousSet<>(this.domain)
         : this.intersectionInCurrentDomain(Range.range(fromElement, BoundType.forBoolean(fromInclusive), toElement, BoundType.forBoolean(toInclusive))));
   }

   @Override
   ContiguousSet<C> tailSetImpl(C fromElement, boolean inclusive) {
      return this.intersectionInCurrentDomain(Range.downTo(fromElement, BoundType.forBoolean(inclusive)));
   }

   @GwtIncompatible("not used by GWT emulation")
   @Override
   int indexOf(Object target) {
      return this.contains(target) ? (int)this.domain.distance(this.first(), (C)target) : -1;
   }

   @Override
   public UnmodifiableIterator<C> iterator() {
      return new AbstractSequentialIterator<C>(this.first()) {
         final C last = (C)RegularContiguousSet.this.last();

         protected C computeNext(C previous) {
            return RegularContiguousSet.equalsOrThrow(previous, this.last) ? null : RegularContiguousSet.this.domain.next(previous);
         }
      };
   }

   @GwtIncompatible("NavigableSet")
   @Override
   public UnmodifiableIterator<C> descendingIterator() {
      return new AbstractSequentialIterator<C>(this.last()) {
         final C first = (C)RegularContiguousSet.this.first();

         protected C computeNext(C previous) {
            return RegularContiguousSet.equalsOrThrow(previous, this.first) ? null : RegularContiguousSet.this.domain.previous(previous);
         }
      };
   }

   private static boolean equalsOrThrow(Comparable<?> left, @Nullable Comparable<?> right) {
      return right != null && Range.compareOrThrow(left, right) == 0;
   }

   @Override
   boolean isPartialView() {
      return false;
   }

   public C first() {
      return this.range.lowerBound.leastValueAbove(this.domain);
   }

   public C last() {
      return this.range.upperBound.greatestValueBelow(this.domain);
   }

   @Override
   public int size() {
      long distance = this.domain.distance(this.first(), this.last());
      return distance >= 2147483647L ? Integer.MAX_VALUE : (int)distance + 1;
   }

   @Override
   public boolean contains(@Nullable Object object) {
      if (object == null) {
         return false;
      } else {
         try {
            return this.range.contains((C)object);
         } catch (ClassCastException var3) {
            return false;
         }
      }
   }

   @Override
   public boolean containsAll(Collection<?> targets) {
      return Collections2.containsAllImpl(this, targets);
   }

   @Override
   public boolean isEmpty() {
      return false;
   }

   @Override
   public ContiguousSet<C> intersection(ContiguousSet<C> other) {
      Preconditions.checkNotNull(other);
      Preconditions.checkArgument(this.domain.equals(other.domain));
      if (other.isEmpty()) {
         return other;
      } else {
         C lowerEndpoint = Ordering.natural().max(this.first(), other.first());
         C upperEndpoint = Ordering.natural().min(this.last(), other.last());
         return (ContiguousSet<C>)(lowerEndpoint.compareTo(upperEndpoint) < 0
            ? ContiguousSet.create(Range.closed(lowerEndpoint, upperEndpoint), this.domain)
            : new EmptyContiguousSet<>(this.domain));
      }
   }

   @Override
   public Range<C> range() {
      return this.range(BoundType.CLOSED, BoundType.CLOSED);
   }

   @Override
   public Range<C> range(BoundType lowerBoundType, BoundType upperBoundType) {
      return Range.create(
         this.range.lowerBound.withLowerBoundType(lowerBoundType, this.domain), this.range.upperBound.withUpperBoundType(upperBoundType, this.domain)
      );
   }

   @Override
   public boolean equals(@Nullable Object object) {
      if (object == this) {
         return true;
      } else {
         if (object instanceof RegularContiguousSet) {
            RegularContiguousSet<?> that = (RegularContiguousSet<?>)object;
            if (this.domain.equals(that.domain)) {
               return this.first().equals(that.first()) && this.last().equals(that.last());
            }
         }

         return super.equals(object);
      }
   }

   @Override
   public int hashCode() {
      return Sets.hashCodeImpl(this);
   }

   @GwtIncompatible("serialization")
   @Override
   Object writeReplace() {
      return new RegularContiguousSet.SerializedForm(this.range, this.domain);
   }

   @GwtIncompatible("serialization")
   private static final class SerializedForm<C extends Comparable> implements Serializable {
      final Range<C> range;
      final DiscreteDomain<C> domain;

      private SerializedForm(Range<C> range, DiscreteDomain<C> domain) {
         this.range = range;
         this.domain = domain;
      }

      private Object readResolve() {
         return new RegularContiguousSet<>(this.range, this.domain);
      }
   }
}
