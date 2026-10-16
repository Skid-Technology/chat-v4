package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import java.util.Set;
import java.util.SortedSet;

@GwtCompatible
final class Constraints {
   private Constraints() {
   }

   public static <E> Collection<E> constrainedCollection(Collection<E> collection, Constraint<? super E> constraint) {
      return new Constraints.ConstrainedCollection<>(collection, constraint);
   }

   public static <E> Set<E> constrainedSet(Set<E> set, Constraint<? super E> constraint) {
      return new Constraints.ConstrainedSet<>(set, constraint);
   }

   public static <E> SortedSet<E> constrainedSortedSet(SortedSet<E> sortedSet, Constraint<? super E> constraint) {
      return new Constraints.ConstrainedSortedSet<>(sortedSet, constraint);
   }

   public static <E> List<E> constrainedList(List<E> list, Constraint<? super E> constraint) {
      return (List<E>)(list instanceof RandomAccess
         ? new Constraints.ConstrainedRandomAccessList<>(list, constraint)
         : new Constraints.ConstrainedList<>(list, constraint));
   }

   private static <E> ListIterator<E> constrainedListIterator(ListIterator<E> listIterator, Constraint<? super E> constraint) {
      return new Constraints.ConstrainedListIterator<>(listIterator, constraint);
   }

   static <E> Collection<E> constrainedTypePreservingCollection(Collection<E> collection, Constraint<E> constraint) {
      if (collection instanceof SortedSet) {
         return constrainedSortedSet((SortedSet<E>)collection, constraint);
      } else if (collection instanceof Set) {
         return constrainedSet((Set<E>)collection, constraint);
      } else {
         return (Collection<E>)(collection instanceof List ? constrainedList((List<E>)collection, constraint) : constrainedCollection(collection, constraint));
      }
   }

   private static <E> Collection<E> checkElements(Collection<E> elements, Constraint<? super E> constraint) {
      Collection<E> copy = Lists.newArrayList(elements);

      for (E element : copy) {
         constraint.checkElement(element);
      }

      return copy;
   }

   static class ConstrainedCollection<E> extends ForwardingCollection<E> {
      private final Collection<E> delegate;
      private final Constraint<? super E> constraint;

      public ConstrainedCollection(Collection<E> delegate, Constraint<? super E> constraint) {
         this.delegate = Preconditions.checkNotNull(delegate);
         this.constraint = Preconditions.checkNotNull(constraint);
      }

      @Override
      protected Collection<E> delegate() {
         return this.delegate;
      }

      @Override
      public boolean add(E element) {
         this.constraint.checkElement(element);
         return this.delegate.add(element);
      }

      @Override
      public boolean addAll(Collection<? extends E> elements) {
         return this.delegate.addAll(Constraints.checkElements(elements, this.constraint));
      }
   }

   @GwtCompatible
   private static class ConstrainedList<E> extends ForwardingList<E> {
      final List<E> delegate;
      final Constraint<? super E> constraint;

      ConstrainedList(List<E> delegate, Constraint<? super E> constraint) {
         this.delegate = Preconditions.checkNotNull(delegate);
         this.constraint = Preconditions.checkNotNull(constraint);
      }

      @Override
      protected List<E> delegate() {
         return this.delegate;
      }

      @Override
      public boolean add(E element) {
         this.constraint.checkElement(element);
         return this.delegate.add(element);
      }

      @Override
      public void add(int index, E element) {
         this.constraint.checkElement(element);
         this.delegate.add(index, element);
      }

      @Override
      public boolean addAll(Collection<? extends E> elements) {
         return this.delegate.addAll(Constraints.checkElements(elements, this.constraint));
      }

      @Override
      public boolean addAll(int index, Collection<? extends E> elements) {
         return this.delegate.addAll(index, Constraints.checkElements(elements, this.constraint));
      }

      @Override
      public ListIterator<E> listIterator() {
         return Constraints.constrainedListIterator(this.delegate.listIterator(), this.constraint);
      }

      @Override
      public ListIterator<E> listIterator(int index) {
         return Constraints.constrainedListIterator(this.delegate.listIterator(index), this.constraint);
      }

      @Override
      public E set(int index, E element) {
         this.constraint.checkElement(element);
         return this.delegate.set(index, element);
      }

      @Override
      public List<E> subList(int fromIndex, int toIndex) {
         return Constraints.constrainedList(this.delegate.subList(fromIndex, toIndex), this.constraint);
      }
   }

   static class ConstrainedListIterator<E> extends ForwardingListIterator<E> {
      private final ListIterator<E> delegate;
      private final Constraint<? super E> constraint;

      public ConstrainedListIterator(ListIterator<E> delegate, Constraint<? super E> constraint) {
         this.delegate = delegate;
         this.constraint = constraint;
      }

      @Override
      protected ListIterator<E> delegate() {
         return this.delegate;
      }

      @Override
      public void add(E element) {
         this.constraint.checkElement(element);
         this.delegate.add(element);
      }

      @Override
      public void set(E element) {
         this.constraint.checkElement(element);
         this.delegate.set(element);
      }
   }

   static class ConstrainedRandomAccessList<E> extends Constraints.ConstrainedList<E> implements RandomAccess {
      ConstrainedRandomAccessList(List<E> delegate, Constraint<? super E> constraint) {
         super(delegate, constraint);
      }
   }

   static class ConstrainedSet<E> extends ForwardingSet<E> {
      private final Set<E> delegate;
      private final Constraint<? super E> constraint;

      public ConstrainedSet(Set<E> delegate, Constraint<? super E> constraint) {
         this.delegate = Preconditions.checkNotNull(delegate);
         this.constraint = Preconditions.checkNotNull(constraint);
      }

      @Override
      protected Set<E> delegate() {
         return this.delegate;
      }

      @Override
      public boolean add(E element) {
         this.constraint.checkElement(element);
         return this.delegate.add(element);
      }

      @Override
      public boolean addAll(Collection<? extends E> elements) {
         return this.delegate.addAll(Constraints.checkElements(elements, this.constraint));
      }
   }

   private static class ConstrainedSortedSet<E> extends ForwardingSortedSet<E> {
      final SortedSet<E> delegate;
      final Constraint<? super E> constraint;

      ConstrainedSortedSet(SortedSet<E> delegate, Constraint<? super E> constraint) {
         this.delegate = Preconditions.checkNotNull(delegate);
         this.constraint = Preconditions.checkNotNull(constraint);
      }

      @Override
      protected SortedSet<E> delegate() {
         return this.delegate;
      }

      @Override
      public SortedSet<E> headSet(E toElement) {
         return Constraints.constrainedSortedSet(this.delegate.headSet(toElement), this.constraint);
      }

      @Override
      public SortedSet<E> subSet(E fromElement, E toElement) {
         return Constraints.constrainedSortedSet(this.delegate.subSet(fromElement, toElement), this.constraint);
      }

      @Override
      public SortedSet<E> tailSet(E fromElement) {
         return Constraints.constrainedSortedSet(this.delegate.tailSet(fromElement), this.constraint);
      }

      @Override
      public boolean add(E element) {
         this.constraint.checkElement(element);
         return this.delegate.add(element);
      }

      @Override
      public boolean addAll(Collection<? extends E> elements) {
         return this.delegate.addAll(Constraints.checkElements(elements, this.constraint));
      }
   }
}
