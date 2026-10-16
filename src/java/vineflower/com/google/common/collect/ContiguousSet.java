package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.util.NoSuchElementException;

@Beta
@GwtCompatible(emulated = true)
public abstract class ContiguousSet<C extends Comparable> extends ImmutableSortedSet<C> {
   final DiscreteDomain<C> domain;

   public static <C extends Comparable> ContiguousSet<C> create(Range<C> range, DiscreteDomain<C> domain) {
      Preconditions.checkNotNull(range);
      Preconditions.checkNotNull(domain);
      Range<C> effectiveRange = range;

      try {
         if (!range.hasLowerBound()) {
            effectiveRange = effectiveRange.intersection(Range.atLeast(domain.minValue()));
         }

         if (!range.hasUpperBound()) {
            effectiveRange = effectiveRange.intersection(Range.atMost(domain.maxValue()));
         }
      } catch (NoSuchElementException var4) {
         throw new IllegalArgumentException(var4);
      }

      boolean empty = effectiveRange.isEmpty()
         || Range.compareOrThrow(range.lowerBound.leastValueAbove(domain), range.upperBound.greatestValueBelow(domain)) > 0;
      return (ContiguousSet<C>)(empty ? new EmptyContiguousSet<>(domain) : new RegularContiguousSet<>(effectiveRange, domain));
   }

   ContiguousSet(DiscreteDomain<C> domain) {
      super(Ordering.natural());
      this.domain = domain;
   }

   public ContiguousSet<C> headSet(C toElement) {
      return this.headSetImpl(Preconditions.checkNotNull(toElement), false);
   }

   @GwtIncompatible("NavigableSet")
   public ContiguousSet<C> headSet(C toElement, boolean inclusive) {
      return this.headSetImpl(Preconditions.checkNotNull(toElement), inclusive);
   }

   public ContiguousSet<C> subSet(C fromElement, C toElement) {
      Preconditions.checkNotNull(fromElement);
      Preconditions.checkNotNull(toElement);
      Preconditions.checkArgument(this.comparator().compare(fromElement, toElement) <= 0);
      return this.subSetImpl(fromElement, true, toElement, false);
   }

   @GwtIncompatible("NavigableSet")
   public ContiguousSet<C> subSet(C fromElement, boolean fromInclusive, C toElement, boolean toInclusive) {
      Preconditions.checkNotNull(fromElement);
      Preconditions.checkNotNull(toElement);
      Preconditions.checkArgument(this.comparator().compare(fromElement, toElement) <= 0);
      return this.subSetImpl(fromElement, fromInclusive, toElement, toInclusive);
   }

   public ContiguousSet<C> tailSet(C fromElement) {
      return this.tailSetImpl(Preconditions.checkNotNull(fromElement), true);
   }

   @GwtIncompatible("NavigableSet")
   public ContiguousSet<C> tailSet(C fromElement, boolean inclusive) {
      return this.tailSetImpl(Preconditions.checkNotNull(fromElement), inclusive);
   }

   abstract ContiguousSet<C> headSetImpl(C var1, boolean var2);

   abstract ContiguousSet<C> subSetImpl(C var1, boolean var2, C var3, boolean var4);

   abstract ContiguousSet<C> tailSetImpl(C var1, boolean var2);

   public abstract ContiguousSet<C> intersection(ContiguousSet<C> var1);

   public abstract Range<C> range();

   public abstract Range<C> range(BoundType var1, BoundType var2);

   @Override
   public String toString() {
      return this.range().toString();
   }

   @Deprecated
   public static <E> ImmutableSortedSet.Builder<E> builder() {
      throw new UnsupportedOperationException();
   }
}
