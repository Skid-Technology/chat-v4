package io.netty.util.concurrent;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;

public abstract class AbstractEventExecutor extends AbstractExecutorService implements EventExecutor {
   @Override
   public EventExecutor next() {
      return this;
   }

   @Override
   public boolean inEventLoop() {
      return this.inEventLoop(Thread.currentThread());
   }

   @Override
   public Iterator<EventExecutor> iterator() {
      return new AbstractEventExecutor.EventExecutorIterator();
   }

   @Override
   public Future<?> shutdownGracefully() {
      return this.shutdownGracefully(2L, 15L, TimeUnit.SECONDS);
   }

   @Deprecated
   @Override
   public abstract void shutdown();

   @Deprecated
   @Override
   public List<Runnable> shutdownNow() {
      this.shutdown();
      return Collections.emptyList();
   }

   @Override
   public <V> Promise<V> newPromise() {
      return new DefaultPromise<>(this);
   }

   @Override
   public <V> ProgressivePromise<V> newProgressivePromise() {
      return new DefaultProgressivePromise<>(this);
   }

   @Override
   public <V> Future<V> newSucceededFuture(V result) {
      return new SucceededFuture<>(this, result);
   }

   @Override
   public <V> Future<V> newFailedFuture(Throwable cause) {
      return new FailedFuture<>(this, cause);
   }

   @Override
   public Future<?> submit(Runnable task) {
      return (Future<?>)super.submit(task);
   }

   @Override
   public <T> Future<T> submit(Runnable task, T result) {
      return (Future<T>)super.<T>submit(task, result);
   }

   @Override
   public <T> Future<T> submit(Callable<T> task) {
      return (Future<T>)super.<T>submit(task);
   }

   @Override
   protected final <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value) {
      return new PromiseTask<>(this, runnable, value);
   }

   @Override
   protected final <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
      return new PromiseTask<>(this, callable);
   }

   @Override
   public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
      throw new UnsupportedOperationException();
   }

   @Override
   public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
      throw new UnsupportedOperationException();
   }

   @Override
   public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
      throw new UnsupportedOperationException();
   }

   @Override
   public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
      throw new UnsupportedOperationException();
   }

   private final class EventExecutorIterator implements Iterator<EventExecutor> {
      private boolean nextCalled;

      private EventExecutorIterator() {
      }

      @Override
      public boolean hasNext() {
         return !this.nextCalled;
      }

      public EventExecutor next() {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            this.nextCalled = true;
            return AbstractEventExecutor.this;
         }
      }

      @Override
      public void remove() {
         throw new UnsupportedOperationException("read-only");
      }
   }
}
