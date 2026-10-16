package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import javax.annotation.Nullable;

@GwtCompatible(serializable = true)
final class ByFunctionOrdering<F, T> extends Ordering<F> implements Serializable {
   final Function<F, ? extends T> function;
   final Ordering<T> ordering;
   private static final long serialVersionUID = 0L;

   ByFunctionOrdering(Function<F, ? extends T> function, Ordering<T> ordering) {
      this.function = Preconditions.checkNotNull(function);
      this.ordering = Preconditions.checkNotNull(ordering);
   }

   @Override
   public int compare(F left, F right) {
      return this.ordering.compare((T)this.function.apply(left), (T)this.function.apply(right));
   }

   @Override
   public boolean equals(@Nullable Object object) {
      if (object == this) {
         return true;
      } else if (!(object instanceof ByFunctionOrdering)) {
         return false;
      } else {
         ByFunctionOrdering<?, ?> that = (ByFunctionOrdering<?, ?>)object;
         return this.function.equals(that.function) && this.ordering.equals(that.ordering);
      }
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(this.function, this.ordering);
   }

   @Override
   public String toString() {
      return this.ordering + ".onResultOf(" + this.function + ")";
   }
}
