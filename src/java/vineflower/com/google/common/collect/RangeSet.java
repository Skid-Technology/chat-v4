package com.google.common.collect;

import com.google.common.annotations.Beta;
import java.util.Set;
import javax.annotation.Nullable;

@Beta
public interface RangeSet<C extends Comparable> {
   boolean contains(C var1);

   Range<C> rangeContaining(C var1);

   boolean encloses(Range<C> var1);

   boolean enclosesAll(RangeSet<C> var1);

   boolean isEmpty();

   Range<C> span();

   Set<Range<C>> asRanges();

   RangeSet<C> complement();

   RangeSet<C> subRangeSet(Range<C> var1);

   void add(Range<C> var1);

   void remove(Range<C> var1);

   void clear();

   void addAll(RangeSet<C> var1);

   void removeAll(RangeSet<C> var1);

   @Override
   boolean equals(@Nullable Object var1);

   @Override
   int hashCode();

   @Override
   String toString();
}
