package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible(serializable = true, emulated = true)
public class ImmutableListMultimap<K, V> extends ImmutableMultimap<K, V> implements ListMultimap<K, V> {
   private transient ImmutableListMultimap<V, K> inverse;
   @GwtIncompatible("Not needed in emulated source")
   private static final long serialVersionUID = 0L;

   public static <K, V> ImmutableListMultimap<K, V> of() {
      return EmptyImmutableListMultimap.INSTANCE;
   }

   public static <K, V> ImmutableListMultimap<K, V> of(K k1, V v1) {
      ImmutableListMultimap.Builder<K, V> builder = builder();
      builder.put(k1, v1);
      return builder.build();
   }

   public static <K, V> ImmutableListMultimap<K, V> of(K k1, V v1, K k2, V v2) {
      ImmutableListMultimap.Builder<K, V> builder = builder();
      builder.put(k1, v1);
      builder.put(k2, v2);
      return builder.build();
   }

   public static <K, V> ImmutableListMultimap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3) {
      ImmutableListMultimap.Builder<K, V> builder = builder();
      builder.put(k1, v1);
      builder.put(k2, v2);
      builder.put(k3, v3);
      return builder.build();
   }

   public static <K, V> ImmutableListMultimap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
      ImmutableListMultimap.Builder<K, V> builder = builder();
      builder.put(k1, v1);
      builder.put(k2, v2);
      builder.put(k3, v3);
      builder.put(k4, v4);
      return builder.build();
   }

   public static <K, V> ImmutableListMultimap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
      ImmutableListMultimap.Builder<K, V> builder = builder();
      builder.put(k1, v1);
      builder.put(k2, v2);
      builder.put(k3, v3);
      builder.put(k4, v4);
      builder.put(k5, v5);
      return builder.build();
   }

   public static <K, V> ImmutableListMultimap.Builder<K, V> builder() {
      return new ImmutableListMultimap.Builder<>();
   }

   public static <K, V> ImmutableListMultimap<K, V> copyOf(Multimap<? extends K, ? extends V> multimap) {
      if (multimap.isEmpty()) {
         return of();
      } else {
         if (multimap instanceof ImmutableListMultimap) {
            ImmutableListMultimap<K, V> kvMultimap = (ImmutableListMultimap<K, V>)multimap;
            if (!kvMultimap.isPartialView()) {
               return kvMultimap;
            }
         }

         ImmutableMap.Builder<K, ImmutableList<V>> builder = ImmutableMap.builder();
         int size = 0;

         for (Entry<? extends K, ? extends Collection<? extends V>> entry : multimap.asMap().entrySet()) {
            ImmutableList<V> list = ImmutableList.copyOf((Collection<? extends V>)entry.getValue());
            if (!list.isEmpty()) {
               builder.put((K)entry.getKey(), list);
               size += list.size();
            }
         }

         return new ImmutableListMultimap<>(builder.build(), size);
      }
   }

   ImmutableListMultimap(ImmutableMap<K, ImmutableList<V>> map, int size) {
      super(map, size);
   }

   public ImmutableList<V> get(@Nullable K key) {
      ImmutableList<V> list = (ImmutableList<V>)this.map.get(key);
      return list == null ? ImmutableList.of() : list;
   }

   public ImmutableListMultimap<V, K> inverse() {
      ImmutableListMultimap<V, K> result = this.inverse;
      return result == null ? (this.inverse = this.invert()) : result;
   }

   private ImmutableListMultimap<V, K> invert() {
      ImmutableListMultimap.Builder<V, K> builder = builder();

      for (Entry<K, V> entry : this.entries()) {
         builder.put(entry.getValue(), entry.getKey());
      }

      ImmutableListMultimap<V, K> invertedMultimap = builder.build();
      invertedMultimap.inverse = this;
      return invertedMultimap;
   }

   @Deprecated
   public ImmutableList<V> removeAll(Object key) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public ImmutableList<V> replaceValues(K key, Iterable<? extends V> values) {
      throw new UnsupportedOperationException();
   }

   @GwtIncompatible("java.io.ObjectOutputStream")
   private void writeObject(ObjectOutputStream stream) throws IOException {
      stream.defaultWriteObject();
      Serialization.writeMultimap(this, stream);
   }

   @GwtIncompatible("java.io.ObjectInputStream")
   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
      stream.defaultReadObject();
      int keyCount = stream.readInt();
      if (keyCount < 0) {
         throw new InvalidObjectException("Invalid key count " + keyCount);
      } else {
         ImmutableMap.Builder<Object, ImmutableList<Object>> builder = ImmutableMap.builder();
         int tmpSize = 0;

         for (int i = 0; i < keyCount; i++) {
            Object key = stream.readObject();
            int valueCount = stream.readInt();
            if (valueCount <= 0) {
               throw new InvalidObjectException("Invalid value count " + valueCount);
            }

            Object[] array = new Object[valueCount];

            for (int j = 0; j < valueCount; j++) {
               array[j] = stream.readObject();
            }

            builder.put(key, ImmutableList.copyOf(array));
            tmpSize += valueCount;
         }

         ImmutableMap<Object, ImmutableList<Object>> tmpMap;
         try {
            tmpMap = builder.build();
         } catch (IllegalArgumentException var10) {
            throw (InvalidObjectException)new InvalidObjectException(var10.getMessage()).initCause(var10);
         }

         ImmutableMultimap.FieldSettersHolder.MAP_FIELD_SETTER.set(this, tmpMap);
         ImmutableMultimap.FieldSettersHolder.SIZE_FIELD_SETTER.set(this, tmpSize);
      }
   }

   public static final class Builder<K, V> extends ImmutableMultimap.Builder<K, V> {
      public ImmutableListMultimap.Builder<K, V> put(K key, V value) {
         super.put(key, value);
         return this;
      }

      public ImmutableListMultimap.Builder<K, V> put(Entry<? extends K, ? extends V> entry) {
         super.put(entry);
         return this;
      }

      public ImmutableListMultimap.Builder<K, V> putAll(K key, Iterable<? extends V> values) {
         super.putAll(key, values);
         return this;
      }

      public ImmutableListMultimap.Builder<K, V> putAll(K key, V... values) {
         super.putAll(key, values);
         return this;
      }

      public ImmutableListMultimap.Builder<K, V> putAll(Multimap<? extends K, ? extends V> multimap) {
         super.putAll(multimap);
         return this;
      }

      public ImmutableListMultimap.Builder<K, V> orderKeysBy(Comparator<? super K> keyComparator) {
         super.orderKeysBy(keyComparator);
         return this;
      }

      public ImmutableListMultimap.Builder<K, V> orderValuesBy(Comparator<? super V> valueComparator) {
         super.orderValuesBy(valueComparator);
         return this;
      }

      public ImmutableListMultimap<K, V> build() {
         return (ImmutableListMultimap<K, V>)super.build();
      }
   }
}
