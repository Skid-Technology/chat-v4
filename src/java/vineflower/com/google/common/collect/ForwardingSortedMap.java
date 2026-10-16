package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.SortedMap;
import javax.annotation.Nullable;

@GwtCompatible
public abstract class ForwardingSortedMap<K, V> extends ForwardingMap<K, V> implements SortedMap<K, V> {
   protected ForwardingSortedMap() {
   }

   protected abstract SortedMap<K, V> delegate();

   @Override
   public Comparator<? super K> comparator() {
      return this.delegate().comparator();
   }

   @Override
   public K firstKey() {
      return this.delegate().firstKey();
   }

   @Override
   public SortedMap<K, V> headMap(K toKey) {
      return this.delegate().headMap(toKey);
   }

   @Override
   public K lastKey() {
      return this.delegate().lastKey();
   }

   @Override
   public SortedMap<K, V> subMap(K fromKey, K toKey) {
      return this.delegate().subMap(fromKey, toKey);
   }

   @Override
   public SortedMap<K, V> tailMap(K fromKey) {
      return this.delegate().tailMap(fromKey);
   }

   private int unsafeCompare(Object k1, Object k2) {
      Comparator<? super K> comparator = this.comparator();
      return comparator == null ? ((Comparable)k1).compareTo(k2) : comparator.compare((K)k1, (K)k2);
   }

   @Beta
   @Override
   protected boolean standardContainsKey(@Nullable Object key) {
      try {
         Object ceilingKey = this.tailMap((K)key).firstKey();
         return this.unsafeCompare(ceilingKey, key) == 0;
      } catch (ClassCastException var4) {
         return false;
      } catch (NoSuchElementException var5) {
         return false;
      } catch (NullPointerException var6) {
         return false;
      }
   }

   @Beta
   protected SortedMap<K, V> standardSubMap(K fromKey, K toKey) {
      Preconditions.checkArgument(this.unsafeCompare(fromKey, toKey) <= 0, "fromKey must be <= toKey");
      return this.tailMap(fromKey).headMap(toKey);
   }

   @Beta
   protected class StandardKeySet extends Maps.SortedKeySet<K, V> {
      public StandardKeySet() {
         super(ForwardingSortedMap.this);
      }
   }
}
