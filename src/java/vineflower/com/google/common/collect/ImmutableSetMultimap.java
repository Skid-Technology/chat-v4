package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible(serializable = true, emulated = true)
public class ImmutableSetMultimap<K, V> extends ImmutableMultimap<K, V> implements SetMultimap<K, V> {
   private final transient ImmutableSet<V> emptySet;
   private transient ImmutableSetMultimap<V, K> inverse;
   private transient ImmutableSet<Entry<K, V>> entries;
   @GwtIncompatible("not needed in emulated source.")
   private static final long serialVersionUID = 0L;

   public static <K, V> ImmutableSetMultimap<K, V> of() {
      return EmptyImmutableSetMultimap.INSTANCE;
   }

   public static <K, V> ImmutableSetMultimap<K, V> of(K k1, V v1) {
      ImmutableSetMultimap.Builder<K, V> builder = builder();
      builder.put(k1, v1);
      return builder.build();
   }

   public static <K, V> ImmutableSetMultimap<K, V> of(K k1, V v1, K k2, V v2) {
      ImmutableSetMultimap.Builder<K, V> builder = builder();
      builder.put(k1, v1);
      builder.put(k2, v2);
      return builder.build();
   }

   public static <K, V> ImmutableSetMultimap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3) {
      ImmutableSetMultimap.Builder<K, V> builder = builder();
      builder.put(k1, v1);
      builder.put(k2, v2);
      builder.put(k3, v3);
      return builder.build();
   }

   public static <K, V> ImmutableSetMultimap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
      ImmutableSetMultimap.Builder<K, V> builder = builder();
      builder.put(k1, v1);
      builder.put(k2, v2);
      builder.put(k3, v3);
      builder.put(k4, v4);
      return builder.build();
   }

   public static <K, V> ImmutableSetMultimap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
      ImmutableSetMultimap.Builder<K, V> builder = builder();
      builder.put(k1, v1);
      builder.put(k2, v2);
      builder.put(k3, v3);
      builder.put(k4, v4);
      builder.put(k5, v5);
      return builder.build();
   }

   public static <K, V> ImmutableSetMultimap.Builder<K, V> builder() {
      return new ImmutableSetMultimap.Builder<>();
   }

   public static <K, V> ImmutableSetMultimap<K, V> copyOf(Multimap<? extends K, ? extends V> multimap) {
      return copyOf(multimap, null);
   }

   private static <K, V> ImmutableSetMultimap<K, V> copyOf(Multimap<? extends K, ? extends V> multimap, Comparator<? super V> valueComparator) {
      Preconditions.checkNotNull(multimap);
      if (multimap.isEmpty() && valueComparator == null) {
         return of();
      } else {
         if (multimap instanceof ImmutableSetMultimap) {
            ImmutableSetMultimap<K, V> kvMultimap = (ImmutableSetMultimap<K, V>)multimap;
            if (!kvMultimap.isPartialView()) {
               return kvMultimap;
            }
         }

         ImmutableMap.Builder<K, ImmutableSet<V>> builder = ImmutableMap.builder();
         int size = 0;

         for (Entry<? extends K, ? extends Collection<? extends V>> entry : multimap.asMap().entrySet()) {
            K key = (K)entry.getKey();
            Collection<? extends V> values = (Collection<? extends V>)entry.getValue();
            ImmutableSet<V> set = valueSet(valueComparator, values);
            if (!set.isEmpty()) {
               builder.put(key, set);
               size += set.size();
            }
         }

         return new ImmutableSetMultimap<>(builder.build(), size, valueComparator);
      }
   }

   ImmutableSetMultimap(ImmutableMap<K, ImmutableSet<V>> map, int size, @Nullable Comparator<? super V> valueComparator) {
      super(map, size);
      this.emptySet = emptySet(valueComparator);
   }

   public ImmutableSet<V> get(@Nullable K key) {
      ImmutableSet<V> set = (ImmutableSet<V>)this.map.get(key);
      return Objects.firstNonNull(set, this.emptySet);
   }

   public ImmutableSetMultimap<V, K> inverse() {
      ImmutableSetMultimap<V, K> result = this.inverse;
      return result == null ? (this.inverse = this.invert()) : result;
   }

   private ImmutableSetMultimap<V, K> invert() {
      ImmutableSetMultimap.Builder<V, K> builder = builder();

      for (Entry<K, V> entry : this.entries()) {
         builder.put(entry.getValue(), entry.getKey());
      }

      ImmutableSetMultimap<V, K> invertedMultimap = builder.build();
      invertedMultimap.inverse = this;
      return invertedMultimap;
   }

   @Deprecated
   public ImmutableSet<V> removeAll(Object key) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public ImmutableSet<V> replaceValues(K key, Iterable<? extends V> values) {
      throw new UnsupportedOperationException();
   }

   public ImmutableSet<Entry<K, V>> entries() {
      ImmutableSet<Entry<K, V>> result = this.entries;
      return result == null ? (this.entries = new ImmutableSetMultimap.EntrySet<>(this)) : result;
   }

   private static <V> ImmutableSet<V> valueSet(@Nullable Comparator<? super V> valueComparator, Collection<? extends V> values) {
      return (ImmutableSet<V>)(valueComparator == null ? ImmutableSet.copyOf(values) : ImmutableSortedSet.copyOf(valueComparator, values));
   }

   private static <V> ImmutableSet<V> emptySet(@Nullable Comparator<? super V> valueComparator) {
      return (ImmutableSet<V>)(valueComparator == null ? ImmutableSet.of() : ImmutableSortedSet.emptySet(valueComparator));
   }

   @GwtIncompatible("java.io.ObjectOutputStream")
   private void writeObject(ObjectOutputStream stream) throws IOException {
      stream.defaultWriteObject();
      stream.writeObject(this.valueComparator());
      Serialization.writeMultimap(this, stream);
   }

   @Nullable
   Comparator<? super V> valueComparator() {
      return this.emptySet instanceof ImmutableSortedSet ? ((ImmutableSortedSet)this.emptySet).comparator() : null;
   }

   @GwtIncompatible("java.io.ObjectInputStream")
   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
      stream.defaultReadObject();
      Comparator<Object> valueComparator = (Comparator<Object>)stream.readObject();
      int keyCount = stream.readInt();
      if (keyCount < 0) {
         throw new InvalidObjectException("Invalid key count " + keyCount);
      } else {
         ImmutableMap.Builder<Object, ImmutableSet<Object>> builder = ImmutableMap.builder();
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

            ImmutableSet<Object> valueSet = valueSet(valueComparator, Arrays.asList(array));
            if (valueSet.size() != array.length) {
               throw new InvalidObjectException("Duplicate key-value pairs exist for key " + key);
            }

            builder.put(key, valueSet);
            tmpSize += valueCount;
         }

         ImmutableMap<Object, ImmutableSet<Object>> tmpMap;
         try {
            tmpMap = builder.build();
         } catch (IllegalArgumentException var11) {
            throw (InvalidObjectException)new InvalidObjectException(var11.getMessage()).initCause(var11);
         }

         ImmutableMultimap.FieldSettersHolder.MAP_FIELD_SETTER.set(this, tmpMap);
         ImmutableMultimap.FieldSettersHolder.SIZE_FIELD_SETTER.set(this, tmpSize);
         ImmutableMultimap.FieldSettersHolder.EMPTY_SET_FIELD_SETTER.set(this, emptySet(valueComparator));
      }
   }

   public static final class Builder<K, V> extends ImmutableMultimap.Builder<K, V> {
      public Builder() {
         this.builderMultimap = new ImmutableSetMultimap.BuilderMultimap<>();
      }

      public ImmutableSetMultimap.Builder<K, V> put(K key, V value) {
         this.builderMultimap.put(Preconditions.checkNotNull(key), Preconditions.checkNotNull(value));
         return this;
      }

      public ImmutableSetMultimap.Builder<K, V> put(Entry<? extends K, ? extends V> entry) {
         this.builderMultimap.put(Preconditions.checkNotNull((K)entry.getKey()), Preconditions.checkNotNull((V)entry.getValue()));
         return this;
      }

      public ImmutableSetMultimap.Builder<K, V> putAll(K key, Iterable<? extends V> values) {
         Collection<V> collection = this.builderMultimap.get(Preconditions.checkNotNull(key));

         for (V value : values) {
            collection.add(Preconditions.checkNotNull(value));
         }

         return this;
      }

      public ImmutableSetMultimap.Builder<K, V> putAll(K key, V... values) {
         return this.putAll(key, Arrays.asList(values));
      }

      public ImmutableSetMultimap.Builder<K, V> putAll(Multimap<? extends K, ? extends V> multimap) {
         for (Entry<? extends K, ? extends Collection<? extends V>> entry : multimap.asMap().entrySet()) {
            this.putAll((K)entry.getKey(), (Iterable<? extends V>)entry.getValue());
         }

         return this;
      }

      public ImmutableSetMultimap.Builder<K, V> orderKeysBy(Comparator<? super K> keyComparator) {
         this.keyComparator = Preconditions.checkNotNull(keyComparator);
         return this;
      }

      public ImmutableSetMultimap.Builder<K, V> orderValuesBy(Comparator<? super V> valueComparator) {
         super.orderValuesBy(valueComparator);
         return this;
      }

      public ImmutableSetMultimap<K, V> build() {
         if (this.keyComparator != null) {
            Multimap<K, V> sortedCopy = new ImmutableSetMultimap.BuilderMultimap<>();
            List<Entry<K, Collection<V>>> entries = Lists.newArrayList(this.builderMultimap.asMap().entrySet());
            Collections.sort(entries, Ordering.from(this.keyComparator).onKeys());

            for (Entry<K, Collection<V>> entry : entries) {
               sortedCopy.putAll(entry.getKey(), entry.getValue());
            }

            this.builderMultimap = sortedCopy;
         }

         return ImmutableSetMultimap.copyOf(this.builderMultimap, this.valueComparator);
      }
   }

   private static class BuilderMultimap<K, V> extends AbstractMapBasedMultimap<K, V> {
      private static final long serialVersionUID = 0L;

      BuilderMultimap() {
         super(new LinkedHashMap<>());
      }

      @Override
      Collection<V> createCollection() {
         return Sets.newLinkedHashSet();
      }
   }

   private static final class EntrySet<K, V> extends ImmutableSet<Entry<K, V>> {
      private final transient ImmutableSetMultimap<K, V> multimap;

      EntrySet(ImmutableSetMultimap<K, V> multimap) {
         this.multimap = multimap;
      }

      @Override
      public boolean contains(@Nullable Object object) {
         if (object instanceof Entry) {
            Entry<?, ?> entry = (Entry<?, ?>)object;
            return this.multimap.containsEntry(entry.getKey(), entry.getValue());
         } else {
            return false;
         }
      }

      @Override
      public int size() {
         return this.multimap.size();
      }

      @Override
      public UnmodifiableIterator<Entry<K, V>> iterator() {
         return this.multimap.entryIterator();
      }

      @Override
      boolean isPartialView() {
         return false;
      }
   }
}
