package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;
import javax.annotation.Nullable;

@Beta
public final class ImmutableRangeSet<C extends Comparable> extends AbstractRangeSet<C> implements Serializable {
   private static final ImmutableRangeSet<Comparable<?>> EMPTY = new ImmutableRangeSet<>(ImmutableList.of());
   private static final ImmutableRangeSet<Comparable<?>> ALL = new ImmutableRangeSet<>(ImmutableList.of(Range.all()));
   private final transient ImmutableList<Range<C>> ranges;
   private transient ImmutableRangeSet<C> complement;

   public static <C extends Comparable> ImmutableRangeSet<C> of() {
      return EMPTY;
   }

   static <C extends Comparable> ImmutableRangeSet<C> all() {
      return ALL;
   }

   public static <C extends Comparable> ImmutableRangeSet<C> of(Range<C> range) {
      Preconditions.checkNotNull(range);
      if (range.isEmpty()) {
         return of();
      } else {
         return range.equals(Range.all()) ? all() : new ImmutableRangeSet<>(ImmutableList.of(range));
      }
   }

   public static <C extends Comparable> ImmutableRangeSet<C> copyOf(RangeSet<C> rangeSet) {
      Preconditions.checkNotNull(rangeSet);
      if (rangeSet.isEmpty()) {
         return of();
      } else if (rangeSet.encloses(Range.all())) {
         return all();
      } else {
         if (rangeSet instanceof ImmutableRangeSet) {
            ImmutableRangeSet<C> immutableRangeSet = (ImmutableRangeSet<C>)rangeSet;
            if (!immutableRangeSet.isPartialView()) {
               return immutableRangeSet;
            }
         }

         return new ImmutableRangeSet<>(ImmutableList.copyOf(rangeSet.asRanges()));
      }
   }

   ImmutableRangeSet(ImmutableList<Range<C>> ranges) {
      this.ranges = ranges;
   }

   private ImmutableRangeSet(ImmutableList<Range<C>> ranges, ImmutableRangeSet<C> complement) {
      this.ranges = ranges;
      this.complement = complement;
   }

   @Override
   public boolean encloses(Range<C> otherRange) {
      int index = SortedLists.binarySearch(
         this.ranges,
         Range.lowerBoundFn(),
         otherRange.lowerBound,
         Ordering.natural(),
         SortedLists.KeyPresentBehavior.ANY_PRESENT,
         SortedLists.KeyAbsentBehavior.NEXT_LOWER
      );
      return index != -1 && this.ranges.get(index).encloses(otherRange);
   }

   @Override
   public Range<C> rangeContaining(C value) {
      int index = SortedLists.binarySearch(
         this.ranges,
         Range.lowerBoundFn(),
         Cut.belowValue(value),
         Ordering.natural(),
         SortedLists.KeyPresentBehavior.ANY_PRESENT,
         SortedLists.KeyAbsentBehavior.NEXT_LOWER
      );
      if (index != -1) {
         Range<C> range = this.ranges.get(index);
         return range.contains(value) ? range : null;
      } else {
         return null;
      }
   }

   @Override
   public Range<C> span() {
      if (this.ranges.isEmpty()) {
         throw new NoSuchElementException();
      } else {
         return Range.create(this.ranges.get(0).lowerBound, this.ranges.get(this.ranges.size() - 1).upperBound);
      }
   }

   @Override
   public boolean isEmpty() {
      return this.ranges.isEmpty();
   }

   @Override
   public void add(Range<C> range) {
      throw new UnsupportedOperationException();
   }

   @Override
   public void addAll(RangeSet<C> other) {
      throw new UnsupportedOperationException();
   }

   @Override
   public void remove(Range<C> range) {
      throw new UnsupportedOperationException();
   }

   @Override
   public void removeAll(RangeSet<C> other) {
      throw new UnsupportedOperationException();
   }

   public ImmutableSet<Range<C>> asRanges() {
      return (ImmutableSet<Range<C>>)(this.ranges.isEmpty() ? ImmutableSet.of() : new RegularImmutableSortedSet<>(this.ranges, Range.RANGE_LEX_ORDERING));
   }

   public ImmutableRangeSet<C> complement() {
      ImmutableRangeSet<C> result = this.complement;
      if (result != null) {
         return result;
      } else if (this.ranges.isEmpty()) {
         return this.complement = all();
      } else if (this.ranges.size() == 1 && this.ranges.get(0).equals(Range.all())) {
         return this.complement = of();
      } else {
         ImmutableList<Range<C>> complementRanges = new ImmutableRangeSet.ComplementRanges();
         return this.complement = new ImmutableRangeSet<>(complementRanges, this);
      }
   }

   private ImmutableList<Range<C>> intersectRanges(final Range<C> range) {
      if (this.ranges.isEmpty() || range.isEmpty()) {
         return ImmutableList.of();
      } else if (range.encloses(this.span())) {
         return this.ranges;
      } else {
         final int fromIndex;
         if (range.hasLowerBound()) {
            fromIndex = SortedLists.binarySearch(
               this.ranges, Range.upperBoundFn(), range.lowerBound, SortedLists.KeyPresentBehavior.FIRST_AFTER, SortedLists.KeyAbsentBehavior.NEXT_HIGHER
            );
         } else {
            fromIndex = 0;
         }

         int toIndex;
         if (range.hasUpperBound()) {
            toIndex = SortedLists.binarySearch(
               this.ranges, Range.lowerBoundFn(), range.upperBound, SortedLists.KeyPresentBehavior.FIRST_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_HIGHER
            );
         } else {
            toIndex = this.ranges.size();
         }

         final int length = toIndex - fromIndex;
         return length == 0
            ? ImmutableList.of()
            : new ImmutableList<Range<C>>() {
               @Override
               public int size() {
                  return length;
               }

               public Range<C> get(int index) {
                  Preconditions.checkElementIndex(index, length);
                  return index != 0 && index != length - 1
                     ? ImmutableRangeSet.this.ranges.get(index + fromIndex)
                     : ImmutableRangeSet.this.ranges.get(index + fromIndex).intersection(range);
               }

               @Override
               boolean isPartialView() {
                  return true;
               }
            };
      }
   }

   public ImmutableRangeSet<C> subRangeSet(Range<C> range) {
      if (!this.isEmpty()) {
         Range<C> span = this.span();
         if (range.encloses(span)) {
            return this;
         }

         if (range.isConnected(span)) {
            return new ImmutableRangeSet<>(this.intersectRanges(range));
         }
      }

      return of();
   }

   public ImmutableSortedSet<C> asSet(DiscreteDomain<C> domain) {
      Preconditions.checkNotNull(domain);
      if (this.isEmpty()) {
         return ImmutableSortedSet.of();
      } else {
         Range<C> span = this.span().canonical(domain);
         if (!span.hasLowerBound()) {
            throw new IllegalArgumentException("Neither the DiscreteDomain nor this range set are bounded below");
         } else {
            if (!span.hasUpperBound()) {
               try {
                  domain.maxValue();
               } catch (NoSuchElementException var4) {
                  throw new IllegalArgumentException("Neither the DiscreteDomain nor this range set are bounded above");
               }
            }

            return new ImmutableRangeSet.AsSet(domain);
         }
      }
   }

   boolean isPartialView() {
      return this.ranges.isPartialView();
   }

   public static <C extends Comparable<?>> ImmutableRangeSet.Builder<C> builder() {
      return new ImmutableRangeSet.Builder<>();
   }

   Object writeReplace() {
      return new ImmutableRangeSet.SerializedForm<>(this.ranges);
   }

   private final class AsSet extends ImmutableSortedSet<C> {
      private final DiscreteDomain<C> domain;
      private transient Integer size;

      AsSet(DiscreteDomain<C> domain) {
         super(Ordering.natural());
         this.domain = domain;
      }

      @Override
      public int size() {
         Integer result = this.size;
         if (result == null) {
            long total = 0L;

            for (Range<C> range : ImmutableRangeSet.this.ranges) {
               total += ContiguousSet.create(range, this.domain).size();
               if (total >= 2147483647L) {
                  break;
               }
            }

            result = this.size = Ints.saturatedCast(total);
         }

         return result;
      }

      @Override
      public UnmodifiableIterator<C> iterator() {
         return new AbstractIterator<C>() {
            final Iterator<Range<C>> rangeItr = ImmutableRangeSet.this.ranges.iterator();
            Iterator<C> elemItr = Iterators.emptyIterator();

            protected C computeNext() {
               while (!this.elemItr.hasNext()) {
                  if (!this.rangeItr.hasNext()) {
                     return (C)this.endOfData();
                  }

                  this.elemItr = ContiguousSet.create(this.rangeItr.next(), AsSet.this.domain).iterator();
               }

               return this.elemItr.next();
            }
         };
      }

      @GwtIncompatible("NavigableSet")
      @Override
      public UnmodifiableIterator<C> descendingIterator() {
         return new AbstractIterator<C>() {
            final Iterator<Range<C>> rangeItr = ImmutableRangeSet.this.ranges.reverse().iterator();
            Iterator<C> elemItr = Iterators.emptyIterator();

            protected C computeNext() {
               while (!this.elemItr.hasNext()) {
                  if (!this.rangeItr.hasNext()) {
                     return (C)this.endOfData();
                  }

                  this.elemItr = ContiguousSet.create(this.rangeItr.next(), AsSet.this.domain).descendingIterator();
               }

               return this.elemItr.next();
            }
         };
      }

      ImmutableSortedSet<C> subSet(Range<C> range) {
         return ImmutableRangeSet.this.subRangeSet(range).asSet(this.domain);
      }

      ImmutableSortedSet<C> headSetImpl(C toElement, boolean inclusive) {
         return this.subSet(Range.upTo(toElement, BoundType.forBoolean(inclusive)));
      }

      ImmutableSortedSet<C> subSetImpl(C fromElement, boolean fromInclusive, C toElement, boolean toInclusive) {
         return !fromInclusive && !toInclusive && Range.compareOrThrow(fromElement, toElement) == 0
            ? ImmutableSortedSet.of()
            : this.subSet(Range.range(fromElement, BoundType.forBoolean(fromInclusive), toElement, BoundType.forBoolean(toInclusive)));
      }

      ImmutableSortedSet<C> tailSetImpl(C fromElement, boolean inclusive) {
         return this.subSet(Range.downTo(fromElement, BoundType.forBoolean(inclusive)));
      }

      @Override
      public boolean contains(@Nullable Object o) {
         if (o == null) {
            return false;
         } else {
            try {
               C c = (C)o;
               return ImmutableRangeSet.this.contains(c);
            } catch (ClassCastException var3) {
               return false;
            }
         }
      }

      @Override
      int indexOf(Object target) {
         if (this.contains(target)) {
            C c = (C)target;
            long total = 0L;

            for (Range<C> range : ImmutableRangeSet.this.ranges) {
               if (range.contains(c)) {
                  return Ints.saturatedCast(total + ContiguousSet.create(range, this.domain).indexOf(c));
               }

               total += ContiguousSet.create(range, this.domain).size();
            }

            throw new AssertionError("impossible");
         } else {
            return -1;
         }
      }

      @Override
      boolean isPartialView() {
         return ImmutableRangeSet.this.ranges.isPartialView();
      }

      @Override
      public String toString() {
         return ImmutableRangeSet.this.ranges.toString();
      }

      @Override
      Object writeReplace() {
         return new ImmutableRangeSet.AsSetSerializedForm<>(ImmutableRangeSet.this.ranges, this.domain);
      }
   }

   private static class AsSetSerializedForm<C extends Comparable> implements Serializable {
      private final ImmutableList<Range<C>> ranges;
      private final DiscreteDomain<C> domain;

      AsSetSerializedForm(ImmutableList<Range<C>> ranges, DiscreteDomain<C> domain) {
         this.ranges = ranges;
         this.domain = domain;
      }

      Object readResolve() {
         return new ImmutableRangeSet<>(this.ranges).asSet(this.domain);
      }
   }

   public static class Builder<C extends Comparable<?>> {
      private final RangeSet<C> rangeSet = TreeRangeSet.create();

      public ImmutableRangeSet.Builder<C> add(Range<C> range) {
         if (range.isEmpty()) {
            throw new IllegalArgumentException("range must not be empty, but was " + range);
         } else if (this.rangeSet.complement().encloses(range)) {
            this.rangeSet.add(range);
            return this;
         } else {
            for (Range<C> currentRange : this.rangeSet.asRanges()) {
               Preconditions.checkArgument(
                  !currentRange.isConnected(range) || currentRange.intersection(range).isEmpty(),
                  "Ranges may not overlap, but received %s and %s",
                  currentRange,
                  range
               );
            }

            throw new AssertionError("should have thrown an IAE above");
         }
      }

      public ImmutableRangeSet.Builder<C> addAll(RangeSet<C> ranges) {
         for (Range<C> range : ranges.asRanges()) {
            this.add(range);
         }

         return this;
      }

      public ImmutableRangeSet<C> build() {
         return ImmutableRangeSet.copyOf(this.rangeSet);
      }
   }

   private final class ComplementRanges extends ImmutableList<Range<C>> {
      private final boolean positiveBoundedBelow = ImmutableRangeSet.this.ranges.get(0).hasLowerBound();
      private final boolean positiveBoundedAbove = Iterables.getLast(ImmutableRangeSet.this.ranges).hasUpperBound();
      private final int size;

      ComplementRanges() {
         int size = ImmutableRangeSet.this.ranges.size() - 1;
         if (this.positiveBoundedBelow) {
            size++;
         }

         if (this.positiveBoundedAbove) {
            size++;
         }

         this.size = size;
      }

      @Override
      public int size() {
         return this.size;
      }

      public Range<C> get(int index) {
         Preconditions.checkElementIndex(index, this.size);
         Cut<C> lowerBound;
         if (this.positiveBoundedBelow) {
            lowerBound = index == 0 ? Cut.belowAll() : ImmutableRangeSet.this.ranges.get(index - 1).upperBound;
         } else {
            lowerBound = ImmutableRangeSet.this.ranges.get(index).upperBound;
         }

         Cut<C> upperBound;
         if (this.positiveBoundedAbove && index == this.size - 1) {
            upperBound = Cut.aboveAll();
         } else {
            upperBound = ImmutableRangeSet.this.ranges.get(index + (this.positiveBoundedBelow ? 0 : 1)).lowerBound;
         }

         return Range.create(lowerBound, upperBound);
      }

      @Override
      boolean isPartialView() {
         return true;
      }
   }

   private static final class SerializedForm<C extends Comparable> implements Serializable {
      private final ImmutableList<Range<C>> ranges;

      SerializedForm(ImmutableList<Range<C>> ranges) {
         this.ranges = ranges;
      }

      Object readResolve() {
         if (this.ranges.isEmpty()) {
            return ImmutableRangeSet.of();
         } else {
            return this.ranges.equals(ImmutableList.of(Range.all())) ? ImmutableRangeSet.all() : new ImmutableRangeSet<>(this.ranges);
         }
      }
   }
}
