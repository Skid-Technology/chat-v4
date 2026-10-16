package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible
public interface BiMap<K, V> extends Map<K, V> {
   @Override
   V put(@Nullable K var1, @Nullable V var2);

   V forcePut(@Nullable K var1, @Nullable V var2);

   @Override
   void putAll(Map<? extends K, ? extends V> var1);

   Set<V> values();

   BiMap<V, K> inverse();
}
