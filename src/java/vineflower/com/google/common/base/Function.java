package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import javax.annotation.Nullable;

@GwtCompatible
public interface Function<F, T> {
   @Nullable
   T apply(@Nullable F var1);

   @Override
   boolean equals(@Nullable Object var1);
}
