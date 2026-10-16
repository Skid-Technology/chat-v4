package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import java.lang.ref.WeakReference;

@GwtCompatible(emulated = true)
final class Platform {
   private Platform() {
   }

   static long systemNanoTime() {
      return System.nanoTime();
   }

   static CharMatcher precomputeCharMatcher(CharMatcher matcher) {
      return matcher.precomputedInternal();
   }

   static <T extends Enum<T>> Optional<T> getEnumIfPresent(Class<T> enumClass, String value) {
      WeakReference<? extends Enum<?>> ref = Enums.getEnumConstants(enumClass).get(value);
      return ref == null ? Optional.absent() : Optional.of(enumClass.cast(ref.get()));
   }
}
