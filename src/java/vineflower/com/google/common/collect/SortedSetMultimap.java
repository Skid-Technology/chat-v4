package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedSet;
import javax.annotation.Nullable;

@GwtCompatible
public interface SortedSetMultimap<K, V> extends SetMultimap<K, V> {
   SortedSet<V> get(@Nullable K var1);

   SortedSet<V> removeAll(@Nullable Object var1);

   SortedSet<V> replaceValues(K var1, Iterable<? extends V> var2);

   @Override
   Map<K, Collection<V>> asMap();

   Comparator<? super V> valueComparator();
}
