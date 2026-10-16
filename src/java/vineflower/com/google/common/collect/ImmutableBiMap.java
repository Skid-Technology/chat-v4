package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.Map;
import java.util.Map.Entry;

@GwtCompatible(serializable = true, emulated = true)
public abstract class ImmutableBiMap<K, V> extends ImmutableMap<K, V> implements BiMap<K, V> {
   private static final Entry<?, ?>[] EMPTY_ENTRY_ARRAY = new Entry[0];

   public static <K, V> ImmutableBiMap<K, V> of() {
      return EmptyImmutableBiMap.INSTANCE;
   }

   public static <K, V> ImmutableBiMap<K, V> of(K k1, V v1) {
      return new SingletonImmutableBiMap<>(k1, v1);
   }

   public static <K, V> ImmutableBiMap<K, V> of(K k1, V v1, K k2, V v2) {
      return new RegularImmutableBiMap<>(entryOf(k1, v1), entryOf(k2, v2));
   }

   public static <K, V> ImmutableBiMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3) {
      return new RegularImmutableBiMap<>(entryOf(k1, v1), entryOf(k2, v2), entryOf(k3, v3));
   }

   public static <K, V> ImmutableBiMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
      return new RegularImmutableBiMap<>(entryOf(k1, v1), entryOf(k2, v2), entryOf(k3, v3), entryOf(k4, v4));
   }

   public static <K, V> ImmutableBiMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
      return new RegularImmutableBiMap<>(entryOf(k1, v1), entryOf(k2, v2), entryOf(k3, v3), entryOf(k4, v4), entryOf(k5, v5));
   }

   public static <K, V> ImmutableBiMap.Builder<K, V> builder() {
      return new ImmutableBiMap.Builder<>();
   }

   public static <K, V> ImmutableBiMap<K, V> copyOf(Map<? extends K, ? extends V> map) {
      if (map instanceof ImmutableBiMap) {
         ImmutableBiMap<K, V> bimap = (ImmutableBiMap<K, V>)map;
         if (!bimap.isPartialView()) {
            return bimap;
         }
      }

      Entry<?, ?>[] entries = map.entrySet().toArray(EMPTY_ENTRY_ARRAY);
      switch (entries.length) {
         case 0:
            return of();
         case 1:
            Entry<K, V> entry = (Entry<K, V>)entries[0];
            return of(entry.getKey(), entry.getValue());
         default:
            return new RegularImmutableBiMap<>(entries);
      }
   }

   ImmutableBiMap() {
   }

   public abstract ImmutableBiMap<V, K> inverse();

   public ImmutableSet<V> values() {
      return this.inverse().keySet();
   }

   @Deprecated
   @Override
   public V forcePut(K key, V value) {
      throw new UnsupportedOperationException();
   }

   @Override
   Object writeReplace() {
      return new ImmutableBiMap.SerializedForm(this);
   }

   public static final class Builder<K, V> extends ImmutableMap.Builder<K, V> {
      public ImmutableBiMap.Builder<K, V> put(K key, V value) {
         super.put(key, value);
         return this;
      }

      public ImmutableBiMap.Builder<K, V> putAll(Map<? extends K, ? extends V> map) {
         super.putAll(map);
         return this;
      }

      public ImmutableBiMap<K, V> build() {
         switch (this.size) {
            case 0:
               return ImmutableBiMap.of();
            case 1:
               return ImmutableBiMap.of(this.entries[0].getKey(), this.entries[0].getValue());
            default:
               return new RegularImmutableBiMap<>(this.size, this.entries);
         }
      }
   }

   private static class SerializedForm extends ImmutableMap.SerializedForm {
      private static final long serialVersionUID = 0L;

      SerializedForm(ImmutableBiMap<?, ?> bimap) {
         super(bimap);
      }

      @Override
      Object readResolve() {
         ImmutableBiMap.Builder<Object, Object> builder = new ImmutableBiMap.Builder<>();
         return this.createMap(builder);
      }
   }
}
