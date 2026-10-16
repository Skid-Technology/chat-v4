package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import java.util.Comparator;
import java.util.List;
import java.util.RandomAccess;
import javax.annotation.Nullable;

@GwtCompatible
@Beta
final class SortedLists {
   private SortedLists() {
   }

   public static <E extends Comparable> int binarySearch(
      List<? extends E> list, E e, SortedLists.KeyPresentBehavior presentBehavior, SortedLists.KeyAbsentBehavior absentBehavior
   ) {
      Preconditions.checkNotNull(e);
      return binarySearch(list, Preconditions.checkNotNull(e), Ordering.natural(), presentBehavior, absentBehavior);
   }

   public static <E, K extends Comparable> int binarySearch(
      List<E> list,
      Function<? super E, K> keyFunction,
      @Nullable K key,
      SortedLists.KeyPresentBehavior presentBehavior,
      SortedLists.KeyAbsentBehavior absentBehavior
   ) {
      return binarySearch(list, keyFunction, key, Ordering.natural(), presentBehavior, absentBehavior);
   }

   public static <E, K> int binarySearch(
      List<E> list,
      Function<? super E, K> keyFunction,
      @Nullable K key,
      Comparator<? super K> keyComparator,
      SortedLists.KeyPresentBehavior presentBehavior,
      SortedLists.KeyAbsentBehavior absentBehavior
   ) {
      return binarySearch(Lists.transform(list, keyFunction), key, keyComparator, presentBehavior, absentBehavior);
   }

   public static <E> int binarySearch(
      List<? extends E> list,
      @Nullable E key,
      Comparator<? super E> comparator,
      SortedLists.KeyPresentBehavior presentBehavior,
      SortedLists.KeyAbsentBehavior absentBehavior
   ) {
      Preconditions.checkNotNull(comparator);
      Preconditions.checkNotNull(list);
      Preconditions.checkNotNull(presentBehavior);
      Preconditions.checkNotNull(absentBehavior);
      if (!(list instanceof RandomAccess)) {
         list = Lists.newArrayList(list);
      }

      int lower = 0;
      int upper = list.size() - 1;

      while (lower <= upper) {
         int middle = lower + upper >>> 1;
         int c = comparator.compare(key, (E)list.get(middle));
         if (c < 0) {
            upper = middle - 1;
         } else {
            if (c <= 0) {
               return lower + presentBehavior.resultIndex(comparator, key, list.subList(lower, upper + 1), middle - lower);
            }

            lower = middle + 1;
         }
      }

      return absentBehavior.resultIndex(lower);
   }

   public static enum KeyAbsentBehavior {
      NEXT_LOWER {
         @Override
         int resultIndex(int higherIndex) {
            return higherIndex - 1;
         }
      },
      NEXT_HIGHER {
         @Override
         public int resultIndex(int higherIndex) {
            return higherIndex;
         }
      },
      INVERTED_INSERTION_INDEX {
         @Override
         public int resultIndex(int higherIndex) {
            return ~higherIndex;
         }
      };

      private KeyAbsentBehavior() {
      }

      abstract int resultIndex(int var1);
   }

   public static enum KeyPresentBehavior {
      ANY_PRESENT {
         @Override
         <E> int resultIndex(Comparator<? super E> comparator, E key, List<? extends E> list, int foundIndex) {
            return foundIndex;
         }
      },
      LAST_PRESENT {
         @Override
         <E> int resultIndex(Comparator<? super E> comparator, E key, List<? extends E> list, int foundIndex) {
            int lower = foundIndex;
            int upper = list.size() - 1;

            while (lower < upper) {
               int middle = lower + upper + 1 >>> 1;
               int c = comparator.compare((E)list.get(middle), key);
               if (c > 0) {
                  upper = middle - 1;
               } else {
                  lower = middle;
               }
            }

            return lower;
         }
      },
      FIRST_PRESENT {
         @Override
         <E> int resultIndex(Comparator<? super E> comparator, E key, List<? extends E> list, int foundIndex) {
            int lower = 0;
            int upper = foundIndex;

            while (lower < upper) {
               int middle = lower + upper >>> 1;
               int c = comparator.compare((E)list.get(middle), key);
               if (c < 0) {
                  lower = middle + 1;
               } else {
                  upper = middle;
               }
            }

            return lower;
         }
      },
      FIRST_AFTER {
         @Override
         public <E> int resultIndex(Comparator<? super E> comparator, E key, List<? extends E> list, int foundIndex) {
            return LAST_PRESENT.resultIndex(comparator, key, list, foundIndex) + 1;
         }
      },
      LAST_BEFORE {
         @Override
         public <E> int resultIndex(Comparator<? super E> comparator, E key, List<? extends E> list, int foundIndex) {
            return FIRST_PRESENT.resultIndex(comparator, key, list, foundIndex) - 1;
         }
      };

      private KeyPresentBehavior() {
      }

      abstract <E> int resultIndex(Comparator<? super E> var1, E var2, List<? extends E> var3, int var4);
   }
}
