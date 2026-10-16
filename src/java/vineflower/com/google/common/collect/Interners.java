package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Equivalence;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import java.util.concurrent.ConcurrentMap;

@Beta
public final class Interners {
   private Interners() {
   }

   public static <E> Interner<E> newStrongInterner() {
      final ConcurrentMap<E, E> map = new MapMaker().makeMap();
      return new Interner<E>() {
         @Override
         public E intern(E sample) {
            E canonical = map.putIfAbsent(Preconditions.checkNotNull(sample), sample);
            return canonical == null ? sample : canonical;
         }
      };
   }

   @GwtIncompatible("java.lang.ref.WeakReference")
   public static <E> Interner<E> newWeakInterner() {
      return new Interners.WeakInterner<>();
   }

   public static <E> Function<E, E> asFunction(Interner<E> interner) {
      return new Interners.InternerFunction<>(Preconditions.checkNotNull(interner));
   }

   private static class InternerFunction<E> implements Function<E, E> {
      private final Interner<E> interner;

      public InternerFunction(Interner<E> interner) {
         this.interner = interner;
      }

      @Override
      public E apply(E input) {
         return this.interner.intern(input);
      }

      @Override
      public int hashCode() {
         return this.interner.hashCode();
      }

      @Override
      public boolean equals(Object other) {
         if (other instanceof Interners.InternerFunction) {
            Interners.InternerFunction<?> that = (Interners.InternerFunction<?>)other;
            return this.interner.equals(that.interner);
         } else {
            return false;
         }
      }
   }

   private static class WeakInterner<E> implements Interner<E> {
      private final MapMakerInternalMap<E, Interners.WeakInterner.Dummy> map = new MapMaker().weakKeys().keyEquivalence(Equivalence.equals()).makeCustomMap();

      private WeakInterner() {
      }

      @Override
      public E intern(E sample) {
         Interners.WeakInterner.Dummy sneaky;
         do {
            MapMakerInternalMap.ReferenceEntry<E, Interners.WeakInterner.Dummy> entry = this.map.getEntry(sample);
            if (entry != null) {
               sneaky = entry.getKey();
               if (sneaky != null) {
                  return (E)sneaky;
               }
            }

            sneaky = this.map.putIfAbsent(sample, Interners.WeakInterner.Dummy.VALUE);
         } while (sneaky != null);

         return sample;
      }

      private static enum Dummy {
         VALUE;
      }
   }
}
