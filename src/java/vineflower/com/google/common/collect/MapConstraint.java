package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import javax.annotation.Nullable;

@GwtCompatible
@Beta
public interface MapConstraint<K, V> {
   void checkKeyValue(@Nullable K var1, @Nullable V var2);

   @Override
   String toString();
}
