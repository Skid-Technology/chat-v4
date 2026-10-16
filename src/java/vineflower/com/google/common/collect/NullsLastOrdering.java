package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.io.Serializable;
import javax.annotation.Nullable;

@GwtCompatible(serializable = true)
final class NullsLastOrdering<T> extends Ordering<T> implements Serializable {
   final Ordering<? super T> ordering;
   private static final long serialVersionUID = 0L;

   NullsLastOrdering(Ordering<? super T> ordering) {
      this.ordering = ordering;
   }

   @Override
   public int compare(@Nullable T left, @Nullable T right) {
      if (left == right) {
         return 0;
      } else if (left == null) {
         return 1;
      } else {
         return right == null ? -1 : this.ordering.compare(left, right);
      }
   }

   @Override
   public <S extends T> Ordering<S> reverse() {
      return this.ordering.reverse().nullsFirst();
   }

   @Override
   public <S extends T> Ordering<S> nullsFirst() {
      return this.ordering.nullsFirst();
   }

   @Override
   public <S extends T> Ordering<S> nullsLast() {
      return this;
   }

   @Override
   public boolean equals(@Nullable Object object) {
      if (object == this) {
         return true;
      } else if (object instanceof NullsLastOrdering) {
         NullsLastOrdering<?> that = (NullsLastOrdering<?>)object;
         return this.ordering.equals(that.ordering);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return this.ordering.hashCode() ^ -921210296;
   }

   @Override
   public String toString() {
      return this.ordering + ".nullsLast()";
   }
}
