package io.netty.util.internal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

final class MpscLinkedQueue<E> extends MpscLinkedQueueTailRef<E> implements Queue<E> {
   private static final long serialVersionUID = -1878402552271506449L;
   long p00;
   long p01;
   long p02;
   long p03;
   long p04;
   long p05;
   long p06;
   long p07;
   long p30;
   long p31;
   long p32;
   long p33;
   long p34;
   long p35;
   long p36;
   long p37;

   MpscLinkedQueue() {
      MpscLinkedQueueNode<E> tombstone = new MpscLinkedQueue.DefaultNode<>(null);
      this.setHeadRef(tombstone);
      this.setTailRef(tombstone);
   }

   private MpscLinkedQueueNode<E> peekNode() {
      MpscLinkedQueueNode<E> head;
      do {
         head = this.headRef();
         MpscLinkedQueueNode<E> next = head.next();
         if (next != null) {
            return next;
         }
      } while (head != this.tailRef());

      return null;
   }

   @Override
   public boolean offer(E value) {
      if (value == null) {
         throw new NullPointerException("value");
      } else {
         MpscLinkedQueueNode<E> newTail;
         if (value instanceof MpscLinkedQueueNode) {
            newTail = (MpscLinkedQueueNode<E>)value;
            newTail.setNext(null);
         } else {
            newTail = new MpscLinkedQueue.DefaultNode<>(value);
         }

         MpscLinkedQueueNode<E> oldTail = this.getAndSetTailRef(newTail);
         oldTail.setNext(newTail);
         return true;
      }
   }

   @Override
   public E poll() {
      MpscLinkedQueueNode<E> next = this.peekNode();
      if (next == null) {
         return null;
      } else {
         MpscLinkedQueueNode<E> oldHead = this.headRef();
         this.lazySetHeadRef(next);
         oldHead.unlink();
         return next.clearMaybe();
      }
   }

   @Override
   public E peek() {
      MpscLinkedQueueNode<E> next = this.peekNode();
      return next == null ? null : next.value();
   }

   @Override
   public int size() {
      int count = 0;

      for (MpscLinkedQueueNode<E> n = this.peekNode(); n != null; n = n.next()) {
         count++;
      }

      return count;
   }

   @Override
   public boolean isEmpty() {
      return this.peekNode() == null;
   }

   @Override
   public boolean contains(Object o) {
      for (MpscLinkedQueueNode<E> n = this.peekNode(); n != null; n = n.next()) {
         if (n.value() == o) {
            return true;
         }
      }

      return false;
   }

   @Override
   public Iterator<E> iterator() {
      return new Iterator<E>() {
         private MpscLinkedQueueNode<E> node = MpscLinkedQueue.this.peekNode();

         @Override
         public boolean hasNext() {
            return this.node != null;
         }

         @Override
         public E next() {
            MpscLinkedQueueNode<E> node = this.node;
            if (node == null) {
               throw new NoSuchElementException();
            } else {
               E value = node.value();
               this.node = node.next();
               return value;
            }
         }

         @Override
         public void remove() {
            throw new UnsupportedOperationException();
         }
      };
   }

   @Override
   public boolean add(E e) {
      if (this.offer(e)) {
         return true;
      } else {
         throw new IllegalStateException("queue full");
      }
   }

   @Override
   public E remove() {
      E e = this.poll();
      if (e != null) {
         return e;
      } else {
         throw new NoSuchElementException();
      }
   }

   @Override
   public E element() {
      E e = this.peek();
      if (e != null) {
         return e;
      } else {
         throw new NoSuchElementException();
      }
   }

   @Override
   public Object[] toArray() {
      Object[] array = new Object[this.size()];
      Iterator<E> it = this.iterator();

      for (int i = 0; i < array.length; i++) {
         if (!it.hasNext()) {
            return Arrays.copyOf(array, i);
         }

         array[i] = it.next();
      }

      return array;
   }

   @Override
   public <T> T[] toArray(T[] a) {
      int size = this.size();
      T[] array;
      if (a.length >= size) {
         array = a;
      } else {
         array = (T[])Array.newInstance(a.getClass().getComponentType(), size);
      }

      Iterator<E> it = this.iterator();

      for (int i = 0; i < array.length; i++) {
         if (!it.hasNext()) {
            if (a == array) {
               array[i] = null;
               return array;
            }

            if (a.length < i) {
               return (T[])Arrays.copyOf(array, i);
            }

            System.arraycopy(array, 0, a, 0, i);
            if (a.length > i) {
               a[i] = null;
            }

            return a;
         }

         array[i] = (T)it.next();
      }

      return array;
   }

   @Override
   public boolean remove(Object o) {
      throw new UnsupportedOperationException();
   }

   @Override
   public boolean containsAll(Collection<?> c) {
      for (Object e : c) {
         if (!this.contains(e)) {
            return false;
         }
      }

      return true;
   }

   @Override
   public boolean addAll(Collection<? extends E> c) {
      if (c == null) {
         throw new NullPointerException("c");
      } else if (c == this) {
         throw new IllegalArgumentException("c == this");
      } else {
         boolean modified = false;

         for (E e : c) {
            this.add(e);
            modified = true;
         }

         return modified;
      }
   }

   @Override
   public boolean removeAll(Collection<?> c) {
      throw new UnsupportedOperationException();
   }

   @Override
   public boolean retainAll(Collection<?> c) {
      throw new UnsupportedOperationException();
   }

   @Override
   public void clear() {
      while (this.poll() != null) {
      }
   }

   private void writeObject(ObjectOutputStream out) throws IOException {
      out.defaultWriteObject();

      for (E e : this) {
         out.writeObject(e);
      }

      out.writeObject(null);
   }

   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      in.defaultReadObject();
      MpscLinkedQueueNode<E> tombstone = new MpscLinkedQueue.DefaultNode<>(null);
      this.setHeadRef(tombstone);
      this.setTailRef(tombstone);

      while (true) {
         E e = (E)in.readObject();
         if (e == null) {
            return;
         }

         this.add(e);
      }
   }

   private static final class DefaultNode<T> extends MpscLinkedQueueNode<T> {
      private T value;

      DefaultNode(T value) {
         this.value = value;
      }

      @Override
      public T value() {
         return this.value;
      }

      @Override
      protected T clearMaybe() {
         T value = this.value;
         this.value = null;
         return value;
      }
   }
}
