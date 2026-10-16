package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
final class EmptyImmutableBiMap extends ImmutableBiMap<Object, Object> {
   static final EmptyImmutableBiMap INSTANCE = new EmptyImmutableBiMap();

   private EmptyImmutableBiMap() {
   }

   @Override
   public ImmutableBiMap<Object, Object> inverse() {
      return this;
   }

   @Override
   public int size() {
      return 0;
   }

   @Override
   public boolean isEmpty() {
      return true;
   }

   @Override
   public Object get(@Nullable Object key) {
      return null;
   }

   @Override
   public ImmutableSet<Entry<Object, Object>> entrySet() {
      return ImmutableSet.of();
   }

   @Override
   ImmutableSet<Entry<Object, Object>> createEntrySet() {
      throw new AssertionError("should never be called");
   }

   @Override
   public ImmutableSetMultimap<Object, Object> asMultimap() {
      return ImmutableSetMultimap.of();
   }

   @Override
   public ImmutableSet<Object> keySet() {
      return ImmutableSet.of();
   }

   @Override
   boolean isPartialView() {
      return false;
   }

   Object readResolve() {
      return INSTANCE;
   }
}
