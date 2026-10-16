package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@Beta
@GwtIncompatible("NavigableMap")
public class ImmutableRangeMap<K extends Comparable<?>, V> implements RangeMap<K, V> {
   private static final ImmutableRangeMap<Comparable<?>, Object> EMPTY = new ImmutableRangeMap<>(ImmutableList.of(), ImmutableList.of());
   private final ImmutableList<Range<K>> ranges;
   private final ImmutableList<V> values;

   public static <K extends Comparable<?>, V> ImmutableRangeMap<K, V> of() {
      return (ImmutableRangeMap<K, V>)EMPTY;
   }

   public static <K extends Comparable<?>, V> ImmutableRangeMap<K, V> of(Range<K> range, V value) {
      return new ImmutableRangeMap<>(ImmutableList.of(range), ImmutableList.of(value));
   }

   public static <K extends Comparable<?>, V> ImmutableRangeMap<K, V> copyOf(RangeMap<K, ? extends V> rangeMap) {
      if (rangeMap instanceof ImmutableRangeMap) {
         return (ImmutableRangeMap<K, V>)rangeMap;
      } else {
         Map<Range<K>, ? extends V> map = rangeMap.asMapOfRanges();
         ImmutableList.Builder<Range<K>> rangesBuilder = new ImmutableList.Builder<>(map.size());
         ImmutableList.Builder<V> valuesBuilder = new ImmutableList.Builder<>(map.size());

         for (Entry<Range<K>, ? extends V> entry : map.entrySet()) {
            rangesBuilder.add(entry.getKey());
            valuesBuilder.add((V)entry.getValue());
         }

         return new ImmutableRangeMap<>(rangesBuilder.build(), valuesBuilder.build());
      }
   }

   public static <K extends Comparable<?>, V> ImmutableRangeMap.Builder<K, V> builder() {
      return new ImmutableRangeMap.Builder<>();
   }

   ImmutableRangeMap(ImmutableList<Range<K>> ranges, ImmutableList<V> values) {
      this.ranges = ranges;
      this.values = values;
   }

   @Nullable
   @Override
   public V get(K key) {
      int index = SortedLists.binarySearch(
         this.ranges, Range.lowerBoundFn(), Cut.belowValue(key), SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_LOWER
      );
      if (index == -1) {
         return null;
      } else {
         Range<K> range = this.ranges.get(index);
         return range.contains(key) ? this.values.get(index) : null;
      }
   }

   @Nullable
   @Override
   public Entry<Range<K>, V> getEntry(K key) {
      int index = SortedLists.binarySearch(
         this.ranges, Range.lowerBoundFn(), Cut.belowValue(key), SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_LOWER
      );
      if (index == -1) {
         return null;
      } else {
         Range<K> range = this.ranges.get(index);
         return range.contains(key) ? Maps.immutableEntry(range, this.values.get(index)) : null;
      }
   }

   @Override
   public Range<K> span() {
      if (this.ranges.isEmpty()) {
         throw new NoSuchElementException();
      } else {
         Range<K> firstRange = this.ranges.get(0);
         Range<K> lastRange = this.ranges.get(this.ranges.size() - 1);
         return Range.create(firstRange.lowerBound, lastRange.upperBound);
      }
   }

   @Override
   public void put(Range<K> range, V value) {
      throw new UnsupportedOperationException();
   }

   @Override
   public void putAll(RangeMap<K, V> rangeMap) {
      throw new UnsupportedOperationException();
   }

   @Override
   public void clear() {
      throw new UnsupportedOperationException();
   }

   @Override
   public void remove(Range<K> range) {
      throw new UnsupportedOperationException();
   }

   public ImmutableMap<Range<K>, V> asMapOfRanges() {
      if (this.ranges.isEmpty()) {
         return ImmutableMap.of();
      } else {
         RegularImmutableSortedSet<Range<K>> rangeSet = new RegularImmutableSortedSet<>(this.ranges, Range.RANGE_LEX_ORDERING);
         return new RegularImmutableSortedMap<>(rangeSet, this.values);
      }
   }

   public ImmutableRangeMap<K, V> subRangeMap(final Range<K> range) {
      if (Preconditions.checkNotNull(range).isEmpty()) {
         return of();
      } else if (!this.ranges.isEmpty() && !range.encloses(this.span())) {
         final int lowerIndex = SortedLists.binarySearch(
            this.ranges, Range.upperBoundFn(), range.lowerBound, SortedLists.KeyPresentBehavior.FIRST_AFTER, SortedLists.KeyAbsentBehavior.NEXT_HIGHER
         );
         int upperIndex = SortedLists.binarySearch(
            this.ranges, Range.lowerBoundFn(), range.upperBound, SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_HIGHER
         );
         if (lowerIndex >= upperIndex) {
            return of();
         } else {
            final int len = upperIndex - lowerIndex;
            ImmutableList<Range<K>> subRanges = new ImmutableList<Range<K>>() {
               @Override
               public int size() {
                  return len;
               }

               public Range<K> get(int index) {
                  Preconditions.checkElementIndex(index, len);
                  return index != 0 && index != len - 1
                     ? ImmutableRangeMap.this.ranges.get(index + lowerIndex)
                     : ImmutableRangeMap.this.ranges.get(index + lowerIndex).intersection(range);
               }

               @Override
               boolean isPartialView() {
                  return true;
               }
            };
            final ImmutableRangeMap<K, V> outer = this;
            return new ImmutableRangeMap<K, V>(subRanges, this.values.subList(lowerIndex, upperIndex)) {
               @Override
               public ImmutableRangeMap<K, V> subRangeMap(Range<K> subRange) {
                  return range.isConnected(subRange) ? outer.subRangeMap(subRange.intersection(range)) : ImmutableRangeMap.of();
               }
            };
         }
      } else {
         return this;
      }
   }

   @Override
   public int hashCode() {
      return this.asMapOfRanges().hashCode();
   }

   @Override
   public boolean equals(@Nullable Object o) {
      if (o instanceof RangeMap) {
         RangeMap<?, ?> rangeMap = (RangeMap<?, ?>)o;
         return this.asMapOfRanges().equals(rangeMap.asMapOfRanges());
      } else {
         return false;
      }
   }

   @Override
   public String toString() {
      return this.asMapOfRanges().toString();
   }

   public static final class Builder<K extends Comparable<?>, V> {
      private final RangeSet<K> keyRanges = TreeRangeSet.create();
      private final RangeMap<K, V> rangeMap = TreeRangeMap.create();

      public ImmutableRangeMap.Builder<K, V> put(Range<K> range, V value) {
         Preconditions.checkNotNull(range);
         Preconditions.checkNotNull(value);
         Preconditions.checkArgument(!range.isEmpty(), "Range must not be empty, but was %s", range);
         if (!this.keyRanges.complement().encloses(range)) {
            for (Entry<Range<K>, V> entry : this.rangeMap.asMapOfRanges().entrySet()) {
               Range<K> key = entry.getKey();
               if (key.isConnected(range) && !key.intersection(range).isEmpty()) {
                  throw new IllegalArgumentException("Overlapping ranges: range " + range + " overlaps with entry " + entry);
               }
            }
         }

         this.keyRanges.add(range);
         this.rangeMap.put(range, value);
         return this;
      }

      public ImmutableRangeMap.Builder<K, V> putAll(RangeMap<K, ? extends V> rangeMap) {
         for (Entry<Range<K>, ? extends V> entry : rangeMap.asMapOfRanges().entrySet()) {
            this.put(entry.getKey(), (V)entry.getValue());
         }

         return this;
      }

      public ImmutableRangeMap<K, V> build() {
         Map<Range<K>, V> map = this.rangeMap.asMapOfRanges();
         ImmutableList.Builder<Range<K>> rangesBuilder = new ImmutableList.Builder<>(map.size());
         ImmutableList.Builder<V> valuesBuilder = new ImmutableList.Builder<>(map.size());

         for (Entry<Range<K>, V> entry : map.entrySet()) {
            rangesBuilder.add(entry.getKey());
            valuesBuilder.add(entry.getValue());
         }

         return new ImmutableRangeMap<>(rangesBuilder.build(), valuesBuilder.build());
      }
   }
}
