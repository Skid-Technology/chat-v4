package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import java.io.Serializable;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
final class ImmutableMapKeySet<K, V> extends ImmutableSet<K> {
   private final ImmutableMap<K, V> map;

   ImmutableMapKeySet(ImmutableMap<K, V> map) {
      this.map = map;
   }

   @Override
   public int size() {
      return this.map.size();
   }

   @Override
   public UnmodifiableIterator<K> iterator() {
      return this.asList().iterator();
   }

   @Override
   public boolean contains(@Nullable Object object) {
      return this.map.containsKey(object);
   }

   @Override
   ImmutableList<K> createAsList() {
      final ImmutableList<Entry<K, V>> entryList = this.map.entrySet().asList();
      return new ImmutableAsList<K>() {
         @Override
         public K get(int index) {
            return entryList.get(index).getKey();
         }

         @Override
         ImmutableCollection<K> delegateCollection() {
            return ImmutableMapKeySet.this;
         }
      };
   }

   @Override
   boolean isPartialView() {
      return true;
   }

   @GwtIncompatible("serialization")
   @Override
   Object writeReplace() {
      return new ImmutableMapKeySet.KeySetSerializedForm<>(this.map);
   }

   @GwtIncompatible("serialization")
   private static class KeySetSerializedForm<K> implements Serializable {
      final ImmutableMap<K, ?> map;
      private static final long serialVersionUID = 0L;

      KeySetSerializedForm(ImmutableMap<K, ?> map) {
         this.map = map;
      }

      Object readResolve() {
         return this.map.keySet();
      }
   }
}
