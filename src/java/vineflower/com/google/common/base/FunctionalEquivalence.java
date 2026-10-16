package com.google.common.base;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import java.io.Serializable;
import javax.annotation.Nullable;

@Beta
@GwtCompatible
final class FunctionalEquivalence<F, T> extends Equivalence<F> implements Serializable {
   private static final long serialVersionUID = 0L;
   private final Function<F, ? extends T> function;
   private final Equivalence<T> resultEquivalence;

   FunctionalEquivalence(Function<F, ? extends T> function, Equivalence<T> resultEquivalence) {
      this.function = Preconditions.checkNotNull(function);
      this.resultEquivalence = Preconditions.checkNotNull(resultEquivalence);
   }

   @Override
   protected boolean doEquivalent(F a, F b) {
      return this.resultEquivalence.equivalent((T)this.function.apply(a), (T)this.function.apply(b));
   }

   @Override
   protected int doHash(F a) {
      return this.resultEquivalence.hash((T)this.function.apply(a));
   }

   @Override
   public boolean equals(@Nullable Object obj) {
      if (obj == this) {
         return true;
      } else if (!(obj instanceof FunctionalEquivalence)) {
         return false;
      } else {
         FunctionalEquivalence<?, ?> that = (FunctionalEquivalence<?, ?>)obj;
         return this.function.equals(that.function) && this.resultEquivalence.equals(that.resultEquivalence);
      }
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(this.function, this.resultEquivalence);
   }

   @Override
   public String toString() {
      return this.resultEquivalence + ".onResultOf(" + this.function + ")";
   }
}
