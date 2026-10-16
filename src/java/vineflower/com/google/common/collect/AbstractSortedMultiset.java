package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
abstract class AbstractSortedMultiset<E> extends AbstractMultiset<E> implements SortedMultiset<E> {
   @GwtTransient
   final Comparator<? super E> comparator;
   private transient SortedMultiset<E> descendingMultiset;

   AbstractSortedMultiset() {
      this(Ordering.natural());
   }

   AbstractSortedMultiset(Comparator<? super E> comparator) {
      this.comparator = Preconditions.checkNotNull(comparator);
   }

   @Override
   public NavigableSet<E> elementSet() {
      return (NavigableSet<E>)super.elementSet();
   }

   NavigableSet<E> createElementSet() {
      return new SortedMultisets.NavigableElementSet<>(this);
   }

   @Override
   public Comparator<? super E> comparator() {
      return this.comparator;
   }

   @Override
   public Multiset.Entry<E> firstEntry() {
      Iterator<Multiset.Entry<E>> entryIterator = this.entryIterator();
      return entryIterator.hasNext() ? entryIterator.next() : null;
   }

   @Override
   public Multiset.Entry<E> lastEntry() {
      Iterator<Multiset.Entry<E>> entryIterator = this.descendingEntryIterator();
      return entryIterator.hasNext() ? entryIterator.next() : null;
   }

   @Override
   public Multiset.Entry<E> pollFirstEntry() {
      Iterator<Multiset.Entry<E>> entryIterator = this.entryIterator();
      if (entryIterator.hasNext()) {
         Multiset.Entry<E> result = entryIterator.next();
         result = Multisets.immutableEntry(result.getElement(), result.getCount());
         entryIterator.remove();
         return result;
      } else {
         return null;
      }
   }

   @Override
   public Multiset.Entry<E> pollLastEntry() {
      Iterator<Multiset.Entry<E>> entryIterator = this.descendingEntryIterator();
      if (entryIterator.hasNext()) {
         Multiset.Entry<E> result = entryIterator.next();
         result = Multisets.immutableEntry(result.getElement(), result.getCount());
         entryIterator.remove();
         return result;
      } else {
         return null;
      }
   }

   @Override
   public SortedMultiset<E> subMultiset(@Nullable E fromElement, BoundType fromBoundType, @Nullable E toElement, BoundType toBoundType) {
      Preconditions.checkNotNull(fromBoundType);
      Preconditions.checkNotNull(toBoundType);
      return this.tailMultiset(fromElement, fromBoundType).headMultiset(toElement, toBoundType);
   }

   abstract Iterator<Multiset.Entry<E>> descendingEntryIterator();

   Iterator<E> descendingIterator() {
      return Multisets.iteratorImpl(this.descendingMultiset());
   }

   @Override
   public SortedMultiset<E> descendingMultiset() {
      SortedMultiset<E> result = this.descendingMultiset;
      return result == null ? (this.descendingMultiset = this.createDescendingMultiset()) : result;
   }

   SortedMultiset<E> createDescendingMultiset() {
      return new DescendingMultiset<E>() {
         @Override
         SortedMultiset<E> forwardMultiset() {
            return AbstractSortedMultiset.this;
         }

         @Override
         Iterator<Multiset.Entry<E>> entryIterator() {
            return AbstractSortedMultiset.this.descendingEntryIterator();
         }

         @Override
         public Iterator<E> iterator() {
            return AbstractSortedMultiset.this.descendingIterator();
         }
      };
   }
}
