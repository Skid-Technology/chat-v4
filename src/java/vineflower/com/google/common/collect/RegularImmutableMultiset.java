package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible(serializable = true)
class RegularImmutableMultiset<E> extends ImmutableMultiset<E> {
   private final transient ImmutableMap<E, Integer> map;
   private final transient int size;

   RegularImmutableMultiset(ImmutableMap<E, Integer> map, int size) {
      this.map = map;
      this.size = size;
   }

   @Override
   boolean isPartialView() {
      return this.map.isPartialView();
   }

   @Override
   public int count(@Nullable Object element) {
      Integer value = this.map.get(element);
      return value == null ? 0 : value;
   }

   @Override
   public int size() {
      return this.size;
   }

   @Override
   public boolean contains(@Nullable Object element) {
      return this.map.containsKey(element);
   }

   public ImmutableSet<E> elementSet() {
      return this.map.keySet();
   }

   @Override
   Multiset.Entry<E> getEntry(int index) {
      Map.Entry<E, Integer> mapEntry = this.map.entrySet().asList().get(index);
      return Multisets.immutableEntry(mapEntry.getKey(), mapEntry.getValue());
   }

   @Override
   public int hashCode() {
      return this.map.hashCode();
   }
}
