package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.List;
import javax.annotation.Nullable;

@GwtCompatible
public abstract class ForwardingListMultimap<K, V> extends ForwardingMultimap<K, V> implements ListMultimap<K, V> {
   protected ForwardingListMultimap() {
   }

   protected abstract ListMultimap<K, V> delegate();

   @Override
   public List<V> get(@Nullable K key) {
      return this.delegate().get(key);
   }

   @Override
   public List<V> removeAll(@Nullable Object key) {
      return this.delegate().removeAll(key);
   }

   @Override
   public List<V> replaceValues(K key, Iterable<? extends V> values) {
      return this.delegate().replaceValues(key, values);
   }
}
