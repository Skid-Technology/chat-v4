package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.Comparator;
import javax.annotation.Nullable;

@GwtCompatible(serializable = true)
final class ComparatorOrdering<T> extends Ordering<T> implements Serializable {
   final Comparator<T> comparator;
   private static final long serialVersionUID = 0L;

   ComparatorOrdering(Comparator<T> comparator) {
      this.comparator = Preconditions.checkNotNull(comparator);
   }

   @Override
   public int compare(T a, T b) {
      return this.comparator.compare(a, b);
   }

   @Override
   public boolean equals(@Nullable Object object) {
      if (object == this) {
         return true;
      } else if (object instanceof ComparatorOrdering) {
         ComparatorOrdering<?> that = (ComparatorOrdering<?>)object;
         return this.comparator.equals(that.comparator);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return this.comparator.hashCode();
   }

   @Override
   public String toString() {
      return this.comparator.toString();
   }
}
