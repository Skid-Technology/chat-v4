package com.google.common.cache;

import com.google.common.annotations.Beta;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.UncheckedExecutionException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Beta
public abstract class AbstractLoadingCache<K, V> extends AbstractCache<K, V> implements LoadingCache<K, V> {
   protected AbstractLoadingCache() {
   }

   @Override
   public V getUnchecked(K key) {
      try {
         return this.get(key);
      } catch (ExecutionException var3) {
         throw new UncheckedExecutionException(var3.getCause());
      }
   }

   @Override
   public ImmutableMap<K, V> getAll(Iterable<? extends K> keys) throws ExecutionException {
      Map<K, V> result = Maps.newLinkedHashMap();

      for (K key : keys) {
         if (!result.containsKey(key)) {
            result.put(key, this.get(key));
         }
      }

      return ImmutableMap.copyOf(result);
   }

   @Override
   public final V apply(K key) {
      return this.getUnchecked(key);
   }

   @Override
   public void refresh(K key) {
      throw new UnsupportedOperationException();
   }
}
