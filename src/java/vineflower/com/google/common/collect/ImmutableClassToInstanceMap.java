package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Primitives;
import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;

public final class ImmutableClassToInstanceMap<B> extends ForwardingMap<Class<? extends B>, B> implements ClassToInstanceMap<B>, Serializable {
   private final ImmutableMap<Class<? extends B>, B> delegate;

   public static <B> ImmutableClassToInstanceMap.Builder<B> builder() {
      return new ImmutableClassToInstanceMap.Builder<>();
   }

   public static <B, S extends B> ImmutableClassToInstanceMap<B> copyOf(Map<? extends Class<? extends S>, ? extends S> map) {
      return map instanceof ImmutableClassToInstanceMap ? (ImmutableClassToInstanceMap)map : new ImmutableClassToInstanceMap.Builder<B>().putAll(map).build();
   }

   private ImmutableClassToInstanceMap(ImmutableMap<Class<? extends B>, B> delegate) {
      this.delegate = delegate;
   }

   @Override
   protected Map<Class<? extends B>, B> delegate() {
      return this.delegate;
   }

   @Nullable
   @Override
   public <T extends B> T getInstance(Class<T> type) {
      return (T)this.delegate.get(Preconditions.checkNotNull(type));
   }

   @Deprecated
   @Override
   public <T extends B> T putInstance(Class<T> type, T value) {
      throw new UnsupportedOperationException();
   }

   public static final class Builder<B> {
      private final ImmutableMap.Builder<Class<? extends B>, B> mapBuilder = ImmutableMap.builder();

      public <T extends B> ImmutableClassToInstanceMap.Builder<B> put(Class<T> key, T value) {
         this.mapBuilder.put(key, (B)value);
         return this;
      }

      public <T extends B> ImmutableClassToInstanceMap.Builder<B> putAll(Map<? extends Class<? extends T>, ? extends T> map) {
         for (Entry<? extends Class<? extends T>, ? extends T> entry : map.entrySet()) {
            Class<? extends T> type = (Class<? extends T>)entry.getKey();
            T value = (T)entry.getValue();
            this.mapBuilder.put(type, cast((Class<B>)type, value));
         }

         return this;
      }

      private static <B, T extends B> T cast(Class<T> type, B value) {
         return Primitives.wrap(type).cast(value);
      }

      public ImmutableClassToInstanceMap<B> build() {
         return new ImmutableClassToInstanceMap<>(this.mapBuilder.build());
      }
   }
}
