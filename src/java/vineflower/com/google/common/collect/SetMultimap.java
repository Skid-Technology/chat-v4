package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible
public interface SetMultimap<K, V> extends Multimap<K, V> {
   Set<V> get(@Nullable K var1);

   Set<V> removeAll(@Nullable Object var1);

   Set<V> replaceValues(K var1, Iterable<? extends V> var2);

   Set<Entry<K, V>> entries();

   @Override
   Map<K, Collection<V>> asMap();

   @Override
   boolean equals(@Nullable Object var1);
}
