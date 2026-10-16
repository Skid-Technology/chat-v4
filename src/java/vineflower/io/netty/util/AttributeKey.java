package io.netty.util;

import io.netty.util.internal.PlatformDependent;
import java.util.concurrent.ConcurrentMap;

public final class AttributeKey<T> extends UniqueName {
   private static final ConcurrentMap<String, Boolean> names = PlatformDependent.newConcurrentHashMap();

   public static <T> AttributeKey<T> valueOf(String name) {
      return new AttributeKey<>(name);
   }

   @Deprecated
   public AttributeKey(String name) {
      super(names, name);
   }
}
