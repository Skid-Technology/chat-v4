package com.google.common.base;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

@GwtCompatible
public final class Suppliers {
   private Suppliers() {
   }

   public static <F, T> Supplier<T> compose(Function<? super F, T> function, Supplier<F> supplier) {
      Preconditions.checkNotNull(function);
      Preconditions.checkNotNull(supplier);
      return new Suppliers.SupplierComposition<>(function, supplier);
   }

   public static <T> Supplier<T> memoize(Supplier<T> delegate) {
      return (Supplier<T>)(delegate instanceof Suppliers.MemoizingSupplier ? delegate : new Suppliers.MemoizingSupplier<>(Preconditions.checkNotNull(delegate)));
   }

   public static <T> Supplier<T> memoizeWithExpiration(Supplier<T> delegate, long duration, TimeUnit unit) {
      return new Suppliers.ExpiringMemoizingSupplier<>(delegate, duration, unit);
   }

   public static <T> Supplier<T> ofInstance(@Nullable T instance) {
      return new Suppliers.SupplierOfInstance<>(instance);
   }

   public static <T> Supplier<T> synchronizedSupplier(Supplier<T> delegate) {
      return new Suppliers.ThreadSafeSupplier<>(Preconditions.checkNotNull(delegate));
   }

   @Beta
   public static <T> Function<Supplier<T>, T> supplierFunction() {
      Suppliers.SupplierFunction<T> sf = Suppliers.SupplierFunctionImpl.INSTANCE;
      return sf;
   }

   @VisibleForTesting
   static class ExpiringMemoizingSupplier<T> implements Supplier<T>, Serializable {
      final Supplier<T> delegate;
      final long durationNanos;
      transient volatile T value;
      transient volatile long expirationNanos;
      private static final long serialVersionUID = 0L;

      ExpiringMemoizingSupplier(Supplier<T> delegate, long duration, TimeUnit unit) {
         this.delegate = Preconditions.checkNotNull(delegate);
         this.durationNanos = unit.toNanos(duration);
         Preconditions.checkArgument(duration > 0L);
      }

      @Override
      public T get() {
         long nanos = this.expirationNanos;
         long now = Platform.systemNanoTime();
         if (nanos == 0L || now - nanos >= 0L) {
            synchronized (this) {
               if (nanos == this.expirationNanos) {
                  T t = this.delegate.get();
                  this.value = t;
                  nanos = now + this.durationNanos;
                  this.expirationNanos = nanos == 0L ? 1L : nanos;
                  return t;
               }
            }
         }

         return this.value;
      }

      @Override
      public String toString() {
         return "Suppliers.memoizeWithExpiration(" + this.delegate + ", " + this.durationNanos + ", NANOS)";
      }
   }

   @VisibleForTesting
   static class MemoizingSupplier<T> implements Supplier<T>, Serializable {
      final Supplier<T> delegate;
      transient volatile boolean initialized;
      transient T value;
      private static final long serialVersionUID = 0L;

      MemoizingSupplier(Supplier<T> delegate) {
         this.delegate = delegate;
      }

      @Override
      public T get() {
         if (!this.initialized) {
            synchronized (this) {
               if (!this.initialized) {
                  T t = this.delegate.get();
                  this.value = t;
                  this.initialized = true;
                  return t;
               }
            }
         }

         return this.value;
      }

      @Override
      public String toString() {
         return "Suppliers.memoize(" + this.delegate + ")";
      }
   }

   private static class SupplierComposition<F, T> implements Supplier<T>, Serializable {
      final Function<? super F, T> function;
      final Supplier<F> supplier;
      private static final long serialVersionUID = 0L;

      SupplierComposition(Function<? super F, T> function, Supplier<F> supplier) {
         this.function = function;
         this.supplier = supplier;
      }

      @Override
      public T get() {
         return this.function.apply(this.supplier.get());
      }

      @Override
      public boolean equals(@Nullable Object obj) {
         if (!(obj instanceof Suppliers.SupplierComposition)) {
            return false;
         } else {
            Suppliers.SupplierComposition<?, ?> that = (Suppliers.SupplierComposition<?, ?>)obj;
            return this.function.equals(that.function) && this.supplier.equals(that.supplier);
         }
      }

      @Override
      public int hashCode() {
         return Objects.hashCode(this.function, this.supplier);
      }

      @Override
      public String toString() {
         return "Suppliers.compose(" + this.function + ", " + this.supplier + ")";
      }
   }

   private interface SupplierFunction<T> extends Function<Supplier<T>, T> {
   }

   private static enum SupplierFunctionImpl implements Suppliers.SupplierFunction<Object> {
      INSTANCE;

      public Object apply(Supplier<Object> input) {
         return input.get();
      }

      @Override
      public String toString() {
         return "Suppliers.supplierFunction()";
      }
   }

   private static class SupplierOfInstance<T> implements Supplier<T>, Serializable {
      final T instance;
      private static final long serialVersionUID = 0L;

      SupplierOfInstance(@Nullable T instance) {
         this.instance = instance;
      }

      @Override
      public T get() {
         return this.instance;
      }

      @Override
      public boolean equals(@Nullable Object obj) {
         if (obj instanceof Suppliers.SupplierOfInstance) {
            Suppliers.SupplierOfInstance<?> that = (Suppliers.SupplierOfInstance<?>)obj;
            return Objects.equal(this.instance, that.instance);
         } else {
            return false;
         }
      }

      @Override
      public int hashCode() {
         return Objects.hashCode(this.instance);
      }

      @Override
      public String toString() {
         return "Suppliers.ofInstance(" + this.instance + ")";
      }
   }

   private static class ThreadSafeSupplier<T> implements Supplier<T>, Serializable {
      final Supplier<T> delegate;
      private static final long serialVersionUID = 0L;

      ThreadSafeSupplier(Supplier<T> delegate) {
         this.delegate = delegate;
      }

      @Override
      public T get() {
         synchronized (this.delegate) {
            return this.delegate.get();
         }
      }

      @Override
      public String toString() {
         return "Suppliers.synchronizedSupplier(" + this.delegate + ")";
      }
   }
}
