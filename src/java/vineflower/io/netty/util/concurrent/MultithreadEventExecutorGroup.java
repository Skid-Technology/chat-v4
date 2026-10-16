package io.netty.util.concurrent;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class MultithreadEventExecutorGroup extends AbstractEventExecutorGroup {
   private final EventExecutor[] children;
   private final AtomicInteger childIndex = new AtomicInteger();
   private final AtomicInteger terminatedChildren = new AtomicInteger();
   private final Promise<?> terminationFuture = new DefaultPromise(GlobalEventExecutor.INSTANCE);
   private final MultithreadEventExecutorGroup.EventExecutorChooser chooser;

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   protected MultithreadEventExecutorGroup(int nThreads, ThreadFactory threadFactory, Object... args) {
      if (nThreads <= 0) {
         throw new IllegalArgumentException(String.format("nThreads: %d (expected: > 0)", nThreads));
      } else {
         if (threadFactory == null) {
            threadFactory = this.newDefaultThreadFactory();
         }

         this.children = new SingleThreadEventExecutor[nThreads];
         if (isPowerOfTwo(this.children.length)) {
            this.chooser = new MultithreadEventExecutorGroup.PowerOfTwoEventExecutorChooser();
         } else {
            this.chooser = new MultithreadEventExecutorGroup.GenericEventExecutorChooser();
         }

         for (int i = 0; i < nThreads; i++) {
            boolean success = false;
            boolean var16 = false /* VF: Semaphore variable */;

            try {
               var16 = true;
               this.children[i] = this.newChild(threadFactory, args);
               success = true;
               var16 = false;
            } catch (Exception var17) {
               throw new IllegalStateException("failed to create a child event loop", var17);
            } finally {
               if (var16) {
                  for (int j = 0; j < i; j++) {
                     this.children[j].shutdownGracefully();
                  }

                  for (int j = 0; j < i; j++) {
                     EventExecutor e = this.children[j];

                     try {
                        while (!e.isTerminated()) {
                           e.awaitTermination(2147483647L, TimeUnit.SECONDS);
                        }
                     } catch (InterruptedException var18) {
                        Thread.currentThread().interrupt();
                        break;
                     }
                  }
               }
            }
         }

         FutureListener<Object> terminationListener = new FutureListener<Object>() {
            @Override
            public void operationComplete(Future<Object> future) throws Exception {
               if (MultithreadEventExecutorGroup.this.terminatedChildren.incrementAndGet() == MultithreadEventExecutorGroup.this.children.length) {
                  MultithreadEventExecutorGroup.this.terminationFuture.setSuccess(null);
               }
            }
         };

         for (EventExecutor e : this.children) {
            e.terminationFuture().addListener(terminationListener);
         }
      }
   }

   protected ThreadFactory newDefaultThreadFactory() {
      return new DefaultThreadFactory(this.getClass());
   }

   @Override
   public EventExecutor next() {
      return this.chooser.next();
   }

   @Override
   public Iterator<EventExecutor> iterator() {
      return this.children().iterator();
   }

   public final int executorCount() {
      return this.children.length;
   }

   protected Set<EventExecutor> children() {
      Set<EventExecutor> children = Collections.newSetFromMap(new LinkedHashMap<>());
      Collections.addAll(children, this.children);
      return children;
   }

   protected abstract EventExecutor newChild(ThreadFactory var1, Object... var2) throws Exception;

   @Override
   public Future<?> shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit) {
      for (EventExecutor l : this.children) {
         l.shutdownGracefully(quietPeriod, timeout, unit);
      }

      return this.terminationFuture();
   }

   @Override
   public Future<?> terminationFuture() {
      return this.terminationFuture;
   }

   @Deprecated
   @Override
   public void shutdown() {
      for (EventExecutor l : this.children) {
         l.shutdown();
      }
   }

   @Override
   public boolean isShuttingDown() {
      for (EventExecutor l : this.children) {
         if (!l.isShuttingDown()) {
            return false;
         }
      }

      return true;
   }

   @Override
   public boolean isShutdown() {
      for (EventExecutor l : this.children) {
         if (!l.isShutdown()) {
            return false;
         }
      }

      return true;
   }

   @Override
   public boolean isTerminated() {
      for (EventExecutor l : this.children) {
         if (!l.isTerminated()) {
            return false;
         }
      }

      return true;
   }

   @Override
   public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
      long deadline = System.nanoTime() + unit.toNanos(timeout);

      long timeLeft;
      for (EventExecutor l : this.children) {
         do {
            timeLeft = deadline - System.nanoTime();
            if (timeLeft <= 0L) {
               return this.isTerminated();
            }
         } while (!l.awaitTermination(timeLeft, TimeUnit.NANOSECONDS));
      }

      return this.isTerminated();
   }

   private static boolean isPowerOfTwo(int val) {
      return (val & -val) == val;
   }

   private interface EventExecutorChooser {
      EventExecutor next();
   }

   private final class GenericEventExecutorChooser implements MultithreadEventExecutorGroup.EventExecutorChooser {
      private GenericEventExecutorChooser() {
      }

      @Override
      public EventExecutor next() {
         return MultithreadEventExecutorGroup.this.children[Math.abs(
            MultithreadEventExecutorGroup.this.childIndex.getAndIncrement() % MultithreadEventExecutorGroup.this.children.length
         )];
      }
   }

   private final class PowerOfTwoEventExecutorChooser implements MultithreadEventExecutorGroup.EventExecutorChooser {
      private PowerOfTwoEventExecutorChooser() {
      }

      @Override
      public EventExecutor next() {
         return MultithreadEventExecutorGroup.this.children[MultithreadEventExecutorGroup.this.childIndex.getAndIncrement()
            & MultithreadEventExecutorGroup.this.children.length - 1];
      }
   }
}
