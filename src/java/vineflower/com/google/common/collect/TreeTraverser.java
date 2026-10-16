package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.Queue;

@Beta
@GwtCompatible(emulated = true)
public abstract class TreeTraverser<T> {
   public abstract Iterable<T> children(T var1);

   public final FluentIterable<T> preOrderTraversal(final T root) {
      Preconditions.checkNotNull(root);
      return new FluentIterable<T>() {
         public UnmodifiableIterator<T> iterator() {
            return TreeTraverser.this.preOrderIterator(root);
         }
      };
   }

   UnmodifiableIterator<T> preOrderIterator(T root) {
      return new TreeTraverser.PreOrderIterator(root);
   }

   public final FluentIterable<T> postOrderTraversal(final T root) {
      Preconditions.checkNotNull(root);
      return new FluentIterable<T>() {
         public UnmodifiableIterator<T> iterator() {
            return TreeTraverser.this.postOrderIterator(root);
         }
      };
   }

   UnmodifiableIterator<T> postOrderIterator(T root) {
      return new TreeTraverser.PostOrderIterator(root);
   }

   public final FluentIterable<T> breadthFirstTraversal(final T root) {
      Preconditions.checkNotNull(root);
      return new FluentIterable<T>() {
         public UnmodifiableIterator<T> iterator() {
            return TreeTraverser.this.new BreadthFirstIterator(root);
         }
      };
   }

   private final class BreadthFirstIterator extends UnmodifiableIterator<T> implements PeekingIterator<T> {
      private final Queue<T> queue = new ArrayDeque<>();

      BreadthFirstIterator(T root) {
         this.queue.add(root);
      }

      @Override
      public boolean hasNext() {
         return !this.queue.isEmpty();
      }

      @Override
      public T peek() {
         return this.queue.element();
      }

      @Override
      public T next() {
         T result = this.queue.remove();
         Iterables.addAll(this.queue, TreeTraverser.this.children(result));
         return result;
      }
   }

   private final class PostOrderIterator extends AbstractIterator<T> {
      private final ArrayDeque<TreeTraverser.PostOrderNode<T>> stack = new ArrayDeque<>();

      PostOrderIterator(T root) {
         this.stack.addLast(this.expand(root));
      }

      @Override
      protected T computeNext() {
         while (!this.stack.isEmpty()) {
            TreeTraverser.PostOrderNode<T> top = this.stack.getLast();
            if (!top.childIterator.hasNext()) {
               this.stack.removeLast();
               return top.root;
            }

            T child = top.childIterator.next();
            this.stack.addLast(this.expand(child));
         }

         return (T)this.endOfData();
      }

      private TreeTraverser.PostOrderNode<T> expand(T t) {
         return new TreeTraverser.PostOrderNode<>(t, TreeTraverser.this.children(t).iterator());
      }
   }

   private static final class PostOrderNode<T> {
      final T root;
      final Iterator<T> childIterator;

      PostOrderNode(T root, Iterator<T> childIterator) {
         this.root = Preconditions.checkNotNull(root);
         this.childIterator = Preconditions.checkNotNull(childIterator);
      }
   }

   private final class PreOrderIterator extends UnmodifiableIterator<T> {
      private final Deque<Iterator<T>> stack = new ArrayDeque<>();

      PreOrderIterator(T root) {
         this.stack.addLast(Iterators.singletonIterator(Preconditions.checkNotNull(root)));
      }

      @Override
      public boolean hasNext() {
         return !this.stack.isEmpty();
      }

      @Override
      public T next() {
         Iterator<T> itr = this.stack.getLast();
         T result = Preconditions.checkNotNull(itr.next());
         if (!itr.hasNext()) {
            this.stack.removeLast();
         }

         Iterator<T> childItr = TreeTraverser.this.children(result).iterator();
         if (childItr.hasNext()) {
            this.stack.addLast(childItr);
         }

         return result;
      }
   }
}
