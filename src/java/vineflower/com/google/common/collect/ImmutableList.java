package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;
import javax.annotation.Nullable;

@GwtCompatible(serializable = true, emulated = true)
public abstract class ImmutableList<E> extends ImmutableCollection<E> implements List<E>, RandomAccess {
   private static final ImmutableList<Object> EMPTY = new RegularImmutableList<>(ObjectArrays.EMPTY_ARRAY);

   public static <E> ImmutableList<E> of() {
      return (ImmutableList<E>)EMPTY;
   }

   public static <E> ImmutableList<E> of(E element) {
      return new SingletonImmutableList<>(element);
   }

   public static <E> ImmutableList<E> of(E e1, E e2) {
      return construct(e1, e2);
   }

   public static <E> ImmutableList<E> of(E e1, E e2, E e3) {
      return construct(e1, e2, e3);
   }

   public static <E> ImmutableList<E> of(E e1, E e2, E e3, E e4) {
      return construct(e1, e2, e3, e4);
   }

   public static <E> ImmutableList<E> of(E e1, E e2, E e3, E e4, E e5) {
      return construct(e1, e2, e3, e4, e5);
   }

   public static <E> ImmutableList<E> of(E e1, E e2, E e3, E e4, E e5, E e6) {
      return construct(e1, e2, e3, e4, e5, e6);
   }

   public static <E> ImmutableList<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7) {
      return construct(e1, e2, e3, e4, e5, e6, e7);
   }

   public static <E> ImmutableList<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) {
      return construct(e1, e2, e3, e4, e5, e6, e7, e8);
   }

   public static <E> ImmutableList<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) {
      return construct(e1, e2, e3, e4, e5, e6, e7, e8, e9);
   }

   public static <E> ImmutableList<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) {
      return construct(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10);
   }

   public static <E> ImmutableList<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E e11) {
      return construct(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11);
   }

   public static <E> ImmutableList<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E e11, E e12, E... others) {
      Object[] array = new Object[12 + others.length];
      array[0] = e1;
      array[1] = e2;
      array[2] = e3;
      array[3] = e4;
      array[4] = e5;
      array[5] = e6;
      array[6] = e7;
      array[7] = e8;
      array[8] = e9;
      array[9] = e10;
      array[10] = e11;
      array[11] = e12;
      System.arraycopy(others, 0, array, 12, others.length);
      return construct(array);
   }

   public static <E> ImmutableList<E> copyOf(Iterable<? extends E> elements) {
      Preconditions.checkNotNull(elements);
      return elements instanceof Collection ? copyOf(Collections2.cast(elements)) : copyOf(elements.iterator());
   }

   public static <E> ImmutableList<E> copyOf(Collection<? extends E> elements) {
      if (elements instanceof ImmutableCollection) {
         ImmutableList<E> list = (ImmutableList<E>)((ImmutableCollection)elements).asList();
         return list.isPartialView() ? asImmutableList(list.toArray()) : list;
      } else {
         return construct(elements.toArray());
      }
   }

   public static <E> ImmutableList<E> copyOf(Iterator<? extends E> elements) {
      if (!elements.hasNext()) {
         return of();
      } else {
         E first = (E)elements.next();
         return !elements.hasNext() ? of(first) : new ImmutableList.Builder<E>().add(first).addAll(elements).build();
      }
   }

   public static <E> ImmutableList<E> copyOf(E[] elements) {
      switch (elements.length) {
         case 0:
            return of();
         case 1:
            return new SingletonImmutableList<>(elements[0]);
         default:
            return new RegularImmutableList<>(ObjectArrays.checkElementsNotNull((Object[])elements.clone()));
      }
   }

   private static <E> ImmutableList<E> construct(Object... elements) {
      return asImmutableList(ObjectArrays.checkElementsNotNull(elements));
   }

   static <E> ImmutableList<E> asImmutableList(Object[] elements) {
      return asImmutableList(elements, elements.length);
   }

   static <E> ImmutableList<E> asImmutableList(Object[] elements, int length) {
      switch (length) {
         case 0:
            return of();
         case 1:
            ImmutableList<E> list = new SingletonImmutableList<>((E)elements[0]);
            return list;
         default:
            if (length < elements.length) {
               elements = ObjectArrays.arraysCopyOf(elements, length);
            }

            return new RegularImmutableList<>(elements);
      }
   }

   ImmutableList() {
   }

   @Override
   public UnmodifiableIterator<E> iterator() {
      return this.listIterator();
   }

   public UnmodifiableListIterator<E> listIterator() {
      return this.listIterator(0);
   }

   public UnmodifiableListIterator<E> listIterator(int index) {
      return new AbstractIndexedListIterator<E>(this.size(), index) {
         @Override
         protected E get(int index) {
            return (E)ImmutableList.this.get(index);
         }
      };
   }

   @Override
   public int indexOf(@Nullable Object object) {
      return object == null ? -1 : Lists.indexOfImpl(this, object);
   }

   @Override
   public int lastIndexOf(@Nullable Object object) {
      return object == null ? -1 : Lists.lastIndexOfImpl(this, object);
   }

   @Override
   public boolean contains(@Nullable Object object) {
      return this.indexOf(object) >= 0;
   }

   public ImmutableList<E> subList(int fromIndex, int toIndex) {
      Preconditions.checkPositionIndexes(fromIndex, toIndex, this.size());
      int length = toIndex - fromIndex;
      switch (length) {
         case 0:
            return of();
         case 1:
            return of(this.get(fromIndex));
         default:
            return this.subListUnchecked(fromIndex, toIndex);
      }
   }

   ImmutableList<E> subListUnchecked(int fromIndex, int toIndex) {
      return new ImmutableList.SubList(fromIndex, toIndex - fromIndex);
   }

   @Deprecated
   @Override
   public final boolean addAll(int index, Collection<? extends E> newElements) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   @Override
   public final E set(int index, E element) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   @Override
   public final void add(int index, E element) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   @Override
   public final E remove(int index) {
      throw new UnsupportedOperationException();
   }

   @Override
   public final ImmutableList<E> asList() {
      return this;
   }

   @Override
   int copyIntoArray(Object[] dst, int offset) {
      int size = this.size();

      for (int i = 0; i < size; i++) {
         dst[offset + i] = this.get(i);
      }

      return offset + size;
   }

   public ImmutableList<E> reverse() {
      return new ImmutableList.ReverseImmutableList<>(this);
   }

   @Override
   public boolean equals(@Nullable Object obj) {
      return Lists.equalsImpl(this, obj);
   }

   @Override
   public int hashCode() {
      int hashCode = 1;
      int n = this.size();

      for (int i = 0; i < n; i++) {
         hashCode = 31 * hashCode + this.get(i).hashCode();
         hashCode = ~(~hashCode);
      }

      return hashCode;
   }

   private void readObject(ObjectInputStream stream) throws InvalidObjectException {
      throw new InvalidObjectException("Use SerializedForm");
   }

   @Override
   Object writeReplace() {
      return new ImmutableList.SerializedForm(this.toArray());
   }

   public static <E> ImmutableList.Builder<E> builder() {
      return new ImmutableList.Builder<>();
   }

   public static final class Builder<E> extends ImmutableCollection.ArrayBasedBuilder<E> {
      public Builder() {
         this(4);
      }

      Builder(int capacity) {
         super(capacity);
      }

      public ImmutableList.Builder<E> add(E element) {
         super.add(element);
         return this;
      }

      public ImmutableList.Builder<E> addAll(Iterable<? extends E> elements) {
         super.addAll(elements);
         return this;
      }

      public ImmutableList.Builder<E> add(E... elements) {
         super.add(elements);
         return this;
      }

      public ImmutableList.Builder<E> addAll(Iterator<? extends E> elements) {
         super.addAll(elements);
         return this;
      }

      public ImmutableList<E> build() {
         return ImmutableList.asImmutableList(this.contents, this.size);
      }
   }

   private static class ReverseImmutableList<E> extends ImmutableList<E> {
      private final transient ImmutableList<E> forwardList;

      ReverseImmutableList(ImmutableList<E> backingList) {
         this.forwardList = backingList;
      }

      private int reverseIndex(int index) {
         return this.size() - 1 - index;
      }

      private int reversePosition(int index) {
         return this.size() - index;
      }

      @Override
      public ImmutableList<E> reverse() {
         return this.forwardList;
      }

      @Override
      public boolean contains(@Nullable Object object) {
         return this.forwardList.contains(object);
      }

      @Override
      public int indexOf(@Nullable Object object) {
         int index = this.forwardList.lastIndexOf(object);
         return index >= 0 ? this.reverseIndex(index) : -1;
      }

      @Override
      public int lastIndexOf(@Nullable Object object) {
         int index = this.forwardList.indexOf(object);
         return index >= 0 ? this.reverseIndex(index) : -1;
      }

      @Override
      public ImmutableList<E> subList(int fromIndex, int toIndex) {
         Preconditions.checkPositionIndexes(fromIndex, toIndex, this.size());
         return this.forwardList.subList(this.reversePosition(toIndex), this.reversePosition(fromIndex)).reverse();
      }

      @Override
      public E get(int index) {
         Preconditions.checkElementIndex(index, this.size());
         return this.forwardList.get(this.reverseIndex(index));
      }

      @Override
      public int size() {
         return this.forwardList.size();
      }

      @Override
      boolean isPartialView() {
         return this.forwardList.isPartialView();
      }
   }

   static class SerializedForm implements Serializable {
      final Object[] elements;
      private static final long serialVersionUID = 0L;

      SerializedForm(Object[] elements) {
         this.elements = elements;
      }

      Object readResolve() {
         return ImmutableList.copyOf(this.elements);
      }
   }

   class SubList extends ImmutableList<E> {
      final transient int offset;
      final transient int length;

      SubList(int offset, int length) {
         this.offset = offset;
         this.length = length;
      }

      @Override
      public int size() {
         return this.length;
      }

      @Override
      public E get(int index) {
         Preconditions.checkElementIndex(index, this.length);
         return ImmutableList.this.get(index + this.offset);
      }

      @Override
      public ImmutableList<E> subList(int fromIndex, int toIndex) {
         Preconditions.checkPositionIndexes(fromIndex, toIndex, this.length);
         return ImmutableList.this.subList(fromIndex + this.offset, toIndex + this.offset);
      }

      @Override
      boolean isPartialView() {
         return true;
      }
   }
}
