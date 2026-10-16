package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import java.util.Collection;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible(serializable = true, emulated = true)
class EmptyImmutableSortedSet<E> extends ImmutableSortedSet<E> {
   EmptyImmutableSortedSet(Comparator<? super E> comparator) {
      super(comparator);
   }

   @Override
   public int size() {
      return 0;
   }

   @Override
   public boolean isEmpty() {
      return true;
   }

   @Override
   public boolean contains(@Nullable Object target) {
      return false;
   }

   @Override
   public boolean containsAll(Collection<?> targets) {
      return targets.isEmpty();
   }

   @Override
   public UnmodifiableIterator<E> iterator() {
      return Iterators.emptyIterator();
   }

   @GwtIncompatible("NavigableSet")
   @Override
   public UnmodifiableIterator<E> descendingIterator() {
      return Iterators.emptyIterator();
   }

   @Override
   boolean isPartialView() {
      return false;
   }

   @Override
   public ImmutableList<E> asList() {
      return ImmutableList.of();
   }

   @Override
   int copyIntoArray(Object[] dst, int offset) {
      return offset;
   }

   @Override
   public boolean equals(@Nullable Object object) {
      if (object instanceof Set) {
         Set<?> that = (Set<?>)object;
         return that.isEmpty();
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return 0;
   }

   @Override
   public String toString() {
      return "[]";
   }

   @Override
   public E first() {
      throw new NoSuchElementException();
   }

   @Override
   public E last() {
      throw new NoSuchElementException();
   }

   @Override
   ImmutableSortedSet<E> headSetImpl(E toElement, boolean inclusive) {
      return this;
   }

   @Override
   ImmutableSortedSet<E> subSetImpl(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
      return this;
   }

   @Override
   ImmutableSortedSet<E> tailSetImpl(E fromElement, boolean inclusive) {
      return this;
   }

   @Override
   int indexOf(@Nullable Object target) {
      return -1;
   }

   @Override
   ImmutableSortedSet<E> createDescendingSet() {
      return new EmptyImmutableSortedSet<>(Ordering.from(this.comparator).reverse());
   }
}
