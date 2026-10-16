package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;

@GwtCompatible
public abstract class Ordering<T> implements Comparator<T> {
   static final int LEFT_IS_GREATER = 1;
   static final int RIGHT_IS_GREATER = -1;

   @GwtCompatible(serializable = true)
   public static <C extends Comparable> Ordering<C> natural() {
      return NaturalOrdering.INSTANCE;
   }

   @GwtCompatible(serializable = true)
   public static <T> Ordering<T> from(Comparator<T> comparator) {
      return (Ordering<T>)(comparator instanceof Ordering ? (Ordering)comparator : new ComparatorOrdering<>(comparator));
   }

   @Deprecated
   @GwtCompatible(serializable = true)
   public static <T> Ordering<T> from(Ordering<T> ordering) {
      return Preconditions.checkNotNull(ordering);
   }

   @GwtCompatible(serializable = true)
   public static <T> Ordering<T> explicit(List<T> valuesInOrder) {
      return new ExplicitOrdering<>(valuesInOrder);
   }

   @GwtCompatible(serializable = true)
   public static <T> Ordering<T> explicit(T leastValue, T... remainingValuesInOrder) {
      return explicit(Lists.asList(leastValue, remainingValuesInOrder));
   }

   @GwtCompatible(serializable = true)
   public static Ordering<Object> allEqual() {
      return AllEqualOrdering.INSTANCE;
   }

   @GwtCompatible(serializable = true)
   public static Ordering<Object> usingToString() {
      return UsingToStringOrdering.INSTANCE;
   }

   public static Ordering<Object> arbitrary() {
      return Ordering.ArbitraryOrderingHolder.ARBITRARY_ORDERING;
   }

   protected Ordering() {
   }

   @GwtCompatible(serializable = true)
   public <S extends T> Ordering<S> reverse() {
      return new ReverseOrdering<>(this);
   }

   @GwtCompatible(serializable = true)
   public <S extends T> Ordering<S> nullsFirst() {
      return new NullsFirstOrdering<>(this);
   }

   @GwtCompatible(serializable = true)
   public <S extends T> Ordering<S> nullsLast() {
      return new NullsLastOrdering<>(this);
   }

   @GwtCompatible(serializable = true)
   public <F> Ordering<F> onResultOf(Function<F, ? extends T> function) {
      return new ByFunctionOrdering<>(function, this);
   }

   <T2 extends T> Ordering<Entry<T2, ?>> onKeys() {
      return this.onResultOf(Maps.keyFunction());
   }

   @GwtCompatible(serializable = true)
   public <U extends T> Ordering<U> compound(Comparator<? super U> secondaryComparator) {
      return new CompoundOrdering<>(this, Preconditions.checkNotNull(secondaryComparator));
   }

   @GwtCompatible(serializable = true)
   public static <T> Ordering<T> compound(Iterable<? extends Comparator<? super T>> comparators) {
      return new CompoundOrdering<>(comparators);
   }

   @GwtCompatible(serializable = true)
   public <S extends T> Ordering<Iterable<S>> lexicographical() {
      return new LexicographicalOrdering<>(this);
   }

   @Override
   public abstract int compare(@Nullable T var1, @Nullable T var2);

   public <E extends T> E min(Iterator<E> iterator) {
      E minSoFar = iterator.next();

      while (iterator.hasNext()) {
         minSoFar = this.min(minSoFar, iterator.next());
      }

      return minSoFar;
   }

   public <E extends T> E min(Iterable<E> iterable) {
      return this.min(iterable.iterator());
   }

   public <E extends T> E min(@Nullable E a, @Nullable E b) {
      return this.compare((T)a, (T)b) <= 0 ? a : b;
   }

   public <E extends T> E min(@Nullable E a, @Nullable E b, @Nullable E c, E... rest) {
      E minSoFar = this.min(this.min(a, b), c);

      for (E r : rest) {
         minSoFar = this.min(minSoFar, r);
      }

      return minSoFar;
   }

   public <E extends T> E max(Iterator<E> iterator) {
      E maxSoFar = iterator.next();

      while (iterator.hasNext()) {
         maxSoFar = this.max(maxSoFar, iterator.next());
      }

      return maxSoFar;
   }

   public <E extends T> E max(Iterable<E> iterable) {
      return this.max(iterable.iterator());
   }

   public <E extends T> E max(@Nullable E a, @Nullable E b) {
      return this.compare((T)a, (T)b) >= 0 ? a : b;
   }

   public <E extends T> E max(@Nullable E a, @Nullable E b, @Nullable E c, E... rest) {
      E maxSoFar = this.max(this.max(a, b), c);

      for (E r : rest) {
         maxSoFar = this.max(maxSoFar, r);
      }

      return maxSoFar;
   }

   public <E extends T> List<E> leastOf(Iterable<E> iterable, int k) {
      if (iterable instanceof Collection) {
         Collection<E> collection = (Collection<E>)iterable;
         if (collection.size() <= 2L * k) {
            E[] array = (E[])collection.toArray();
            Arrays.sort(array, this);
            if (array.length > k) {
               array = ObjectArrays.arraysCopyOf(array, k);
            }

            return Collections.unmodifiableList(Arrays.asList(array));
         }
      }

      return this.leastOf(iterable.iterator(), k);
   }

   public <E extends T> List<E> leastOf(Iterator<E> elements, int k) {
      Preconditions.checkNotNull(elements);
      CollectPreconditions.checkNonnegative(k, "k");
      if (k != 0 && elements.hasNext()) {
         if (k >= 1073741823) {
            ArrayList<E> list = Lists.newArrayList(elements);
            Collections.sort(list, this);
            if (list.size() > k) {
               list.subList(k, list.size()).clear();
            }

            list.trimToSize();
            return Collections.unmodifiableList(list);
         } else {
            int bufferCap = k * 2;
            E[] buffer = (E[])(new Object[bufferCap]);
            E threshold = elements.next();
            buffer[0] = threshold;
            int bufferSize = 1;

            while (bufferSize < k && elements.hasNext()) {
               E e = elements.next();
               buffer[bufferSize++] = e;
               threshold = this.max(threshold, e);
            }

            while (elements.hasNext()) {
               E e = elements.next();
               if (this.compare((T)e, (T)threshold) < 0) {
                  buffer[bufferSize++] = e;
                  if (bufferSize == bufferCap) {
                     int left = 0;
                     int right = bufferCap - 1;
                     int minThresholdPosition = 0;

                     while (left < right) {
                        int pivotIndex = left + right + 1 >>> 1;
                        int pivotNewIndex = this.partition(buffer, left, right, pivotIndex);
                        if (pivotNewIndex > k) {
                           right = pivotNewIndex - 1;
                        } else {
                           if (pivotNewIndex >= k) {
                              break;
                           }

                           left = Math.max(pivotNewIndex, left + 1);
                           minThresholdPosition = pivotNewIndex;
                        }
                     }

                     bufferSize = k;
                     threshold = buffer[minThresholdPosition];

                     for (int i = minThresholdPosition + 1; i < bufferSize; i++) {
                        threshold = this.max(threshold, buffer[i]);
                     }
                  }
               }
            }

            Arrays.sort(buffer, 0, bufferSize, this);
            bufferSize = Math.min(bufferSize, k);
            return Collections.unmodifiableList(Arrays.asList(ObjectArrays.arraysCopyOf(buffer, bufferSize)));
         }
      } else {
         return ImmutableList.of();
      }
   }

   private <E extends T> int partition(E[] values, int left, int right, int pivotIndex) {
      E pivotValue = values[pivotIndex];
      values[pivotIndex] = values[right];
      values[right] = pivotValue;
      int storeIndex = left;

      for (int i = left; i < right; i++) {
         if (this.compare((T)values[i], (T)pivotValue) < 0) {
            ObjectArrays.swap(values, storeIndex, i);
            storeIndex++;
         }
      }

      ObjectArrays.swap(values, right, storeIndex);
      return storeIndex;
   }

   public <E extends T> List<E> greatestOf(Iterable<E> iterable, int k) {
      return this.reverse().leastOf(iterable, k);
   }

   public <E extends T> List<E> greatestOf(Iterator<E> iterator, int k) {
      return this.reverse().leastOf(iterator, k);
   }

   public <E extends T> List<E> sortedCopy(Iterable<E> elements) {
      E[] array = (E[])Iterables.toArray(elements);
      Arrays.sort(array, this);
      return Lists.newArrayList(Arrays.asList(array));
   }

   public <E extends T> ImmutableList<E> immutableSortedCopy(Iterable<E> elements) {
      E[] array = (E[])Iterables.toArray(elements);

      for (E e : array) {
         Preconditions.checkNotNull(e);
      }

      Arrays.sort(array, this);
      return ImmutableList.asImmutableList(array);
   }

   public boolean isOrdered(Iterable<? extends T> iterable) {
      Iterator<? extends T> it = iterable.iterator();
      if (it.hasNext()) {
         T prev = (T)it.next();

         while (it.hasNext()) {
            T next = (T)it.next();
            if (this.compare(prev, next) > 0) {
               return false;
            }

            prev = next;
         }
      }

      return true;
   }

   public boolean isStrictlyOrdered(Iterable<? extends T> iterable) {
      Iterator<? extends T> it = iterable.iterator();
      if (it.hasNext()) {
         T prev = (T)it.next();

         while (it.hasNext()) {
            T next = (T)it.next();
            if (this.compare(prev, next) >= 0) {
               return false;
            }

            prev = next;
         }
      }

      return true;
   }

   public int binarySearch(List<? extends T> sortedList, @Nullable T key) {
      return Collections.binarySearch(sortedList, key, this);
   }

   @VisibleForTesting
   static class ArbitraryOrdering extends Ordering<Object> {
      private Map<Object, Integer> uids = Platform.tryWeakKeys(new MapMaker()).makeComputingMap(new Function<Object, Integer>() {
         final AtomicInteger counter = new AtomicInteger(0);

         public Integer apply(Object from) {
            return this.counter.getAndIncrement();
         }
      });

      @Override
      public int compare(Object left, Object right) {
         if (left == right) {
            return 0;
         } else if (left == null) {
            return -1;
         } else if (right == null) {
            return 1;
         } else {
            int leftCode = this.identityHashCode(left);
            int rightCode = this.identityHashCode(right);
            if (leftCode != rightCode) {
               return leftCode < rightCode ? -1 : 1;
            } else {
               int result = this.uids.get(left).compareTo(this.uids.get(right));
               if (result == 0) {
                  throw new AssertionError();
               } else {
                  return result;
               }
            }
         }
      }

      @Override
      public String toString() {
         return "Ordering.arbitrary()";
      }

      int identityHashCode(Object object) {
         return System.identityHashCode(object);
      }
   }

   private static class ArbitraryOrderingHolder {
      static final Ordering<Object> ARBITRARY_ORDERING = new Ordering.ArbitraryOrdering();
   }

   @VisibleForTesting
   static class IncomparableValueException extends ClassCastException {
      final Object value;
      private static final long serialVersionUID = 0L;

      IncomparableValueException(Object value) {
         super("Cannot compare value: " + value);
         this.value = value;
      }
   }
}
