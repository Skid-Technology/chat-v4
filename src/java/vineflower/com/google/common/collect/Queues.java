package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public final class Queues {
   private Queues() {
   }

   public static <E> ArrayBlockingQueue<E> newArrayBlockingQueue(int capacity) {
      return new ArrayBlockingQueue<>(capacity);
   }

   public static <E> ArrayDeque<E> newArrayDeque() {
      return new ArrayDeque<>();
   }

   public static <E> ArrayDeque<E> newArrayDeque(Iterable<? extends E> elements) {
      if (elements instanceof Collection) {
         return new ArrayDeque<>(Collections2.cast(elements));
      } else {
         ArrayDeque<E> deque = new ArrayDeque<>();
         Iterables.addAll(deque, elements);
         return deque;
      }
   }

   public static <E> ConcurrentLinkedQueue<E> newConcurrentLinkedQueue() {
      return new ConcurrentLinkedQueue<>();
   }

   public static <E> ConcurrentLinkedQueue<E> newConcurrentLinkedQueue(Iterable<? extends E> elements) {
      if (elements instanceof Collection) {
         return new ConcurrentLinkedQueue<>(Collections2.cast(elements));
      } else {
         ConcurrentLinkedQueue<E> queue = new ConcurrentLinkedQueue<>();
         Iterables.addAll(queue, elements);
         return queue;
      }
   }

   public static <E> LinkedBlockingDeque<E> newLinkedBlockingDeque() {
      return new LinkedBlockingDeque<>();
   }

   public static <E> LinkedBlockingDeque<E> newLinkedBlockingDeque(int capacity) {
      return new LinkedBlockingDeque<>(capacity);
   }

   public static <E> LinkedBlockingDeque<E> newLinkedBlockingDeque(Iterable<? extends E> elements) {
      if (elements instanceof Collection) {
         return new LinkedBlockingDeque<>(Collections2.cast(elements));
      } else {
         LinkedBlockingDeque<E> deque = new LinkedBlockingDeque<>();
         Iterables.addAll(deque, elements);
         return deque;
      }
   }

   public static <E> LinkedBlockingQueue<E> newLinkedBlockingQueue() {
      return new LinkedBlockingQueue<>();
   }

   public static <E> LinkedBlockingQueue<E> newLinkedBlockingQueue(int capacity) {
      return new LinkedBlockingQueue<>(capacity);
   }

   public static <E> LinkedBlockingQueue<E> newLinkedBlockingQueue(Iterable<? extends E> elements) {
      if (elements instanceof Collection) {
         return new LinkedBlockingQueue<>(Collections2.cast(elements));
      } else {
         LinkedBlockingQueue<E> queue = new LinkedBlockingQueue<>();
         Iterables.addAll(queue, elements);
         return queue;
      }
   }

   public static <E extends Comparable> PriorityBlockingQueue<E> newPriorityBlockingQueue() {
      return new PriorityBlockingQueue<>();
   }

   public static <E extends Comparable> PriorityBlockingQueue<E> newPriorityBlockingQueue(Iterable<? extends E> elements) {
      if (elements instanceof Collection) {
         return new PriorityBlockingQueue<>(Collections2.cast(elements));
      } else {
         PriorityBlockingQueue<E> queue = new PriorityBlockingQueue<>();
         Iterables.addAll(queue, elements);
         return queue;
      }
   }

   public static <E extends Comparable> PriorityQueue<E> newPriorityQueue() {
      return new PriorityQueue<>();
   }

   public static <E extends Comparable> PriorityQueue<E> newPriorityQueue(Iterable<? extends E> elements) {
      if (elements instanceof Collection) {
         return new PriorityQueue<>(Collections2.cast(elements));
      } else {
         PriorityQueue<E> queue = new PriorityQueue<>();
         Iterables.addAll(queue, elements);
         return queue;
      }
   }

   public static <E> SynchronousQueue<E> newSynchronousQueue() {
      return new SynchronousQueue<>();
   }

   @Beta
   public static <E> int drain(BlockingQueue<E> q, Collection<? super E> buffer, int numElements, long timeout, TimeUnit unit) throws InterruptedException {
      Preconditions.checkNotNull(buffer);
      long deadline = System.nanoTime() + unit.toNanos(timeout);
      int added = 0;

      while (added < numElements) {
         added += q.drainTo(buffer, numElements - added);
         if (added < numElements) {
            E e = q.poll(deadline - System.nanoTime(), TimeUnit.NANOSECONDS);
            if (e == null) {
               break;
            }

            buffer.add(e);
            added++;
         }
      }

      return added;
   }

   @Beta
   public static <E> int drainUninterruptibly(BlockingQueue<E> q, Collection<? super E> buffer, int numElements, long timeout, TimeUnit unit) {
      Preconditions.checkNotNull(buffer);
      long deadline = System.nanoTime() + unit.toNanos(timeout);
      int added = 0;
      boolean interrupted = false;

      try {
         while (added < numElements) {
            added += q.drainTo(buffer, numElements - added);
            if (added < numElements) {
               E e;
               while (true) {
                  try {
                     e = q.poll(deadline - System.nanoTime(), TimeUnit.NANOSECONDS);
                     break;
                  } catch (InterruptedException var15) {
                     interrupted = true;
                  }
               }

               if (e == null) {
                  break;
               }

               buffer.add(e);
               added++;
            }
         }
      } finally {
         if (interrupted) {
            Thread.currentThread().interrupt();
         }
      }

      return added;
   }

   @Beta
   public static <E> Queue<E> synchronizedQueue(Queue<E> queue) {
      return Synchronized.queue(queue, null);
   }

   @Beta
   public static <E> Deque<E> synchronizedDeque(Deque<E> deque) {
      return Synchronized.deque(deque, null);
   }
}
