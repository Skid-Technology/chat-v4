package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import java.io.Serializable;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
final class ImmutableMapValues<K, V> extends ImmutableCollection<V> {
   private final ImmutableMap<K, V> map;

   ImmutableMapValues(ImmutableMap<K, V> map) {
      this.map = map;
   }

   @Override
   public int size() {
      return this.map.size();
   }

   @Override
   public UnmodifiableIterator<V> iterator() {
      return Maps.valueIterator(this.map.entrySet().iterator());
   }

   @Override
   public boolean contains(@Nullable Object object) {
      return object != null && Iterators.contains(this.iterator(), object);
   }

   @Override
   boolean isPartialView() {
      return true;
   }

   @Override
   ImmutableList<V> createAsList() {
      final ImmutableList<Entry<K, V>> entryList = this.map.entrySet().asList();
      return new ImmutableAsList<V>() {
         @Override
         public V get(int index) {
            return entryList.get(index).getValue();
         }

         @Override
         ImmutableCollection<V> delegateCollection() {
            return ImmutableMapValues.this;
         }
      };
   }

   @GwtIncompatible("serialization")
   @Override
   Object writeReplace() {
      return new ImmutableMapValues.SerializedForm<>(this.map);
   }

   @GwtIncompatible("serialization")
   private static class SerializedForm<V> implements Serializable {
      final ImmutableMap<?, V> map;
      private static final long serialVersionUID = 0L;

      SerializedForm(ImmutableMap<?, V> map) {
         this.map = map;
      }

      Object readResolve() {
         return this.map.values();
      }
   }
}
