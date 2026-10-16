package io.netty.util.concurrent;

import io.netty.util.Signal;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.InternalThreadLocalMap;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.ArrayDeque;
import java.util.concurrent.CancellationException;
import java.util.concurrent.TimeUnit;

public class DefaultPromise<V> extends AbstractFuture<V> implements Promise<V> {
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(DefaultPromise.class);
   private static final InternalLogger rejectedExecutionLogger = InternalLoggerFactory.getInstance(DefaultPromise.class.getName() + ".rejectedExecution");
   private static final int MAX_LISTENER_STACK_DEPTH = 8;
   private static final Signal SUCCESS = Signal.valueOf(DefaultPromise.class.getName() + ".SUCCESS");
   private static final Signal UNCANCELLABLE = Signal.valueOf(DefaultPromise.class.getName() + ".UNCANCELLABLE");
   private static final DefaultPromise.CauseHolder CANCELLATION_CAUSE_HOLDER = new DefaultPromise.CauseHolder(new CancellationException());
   private final EventExecutor executor;
   private volatile Object result;
   private Object listeners;
   private DefaultPromise<V>.LateListeners lateListeners;
   private short waiters;

   public DefaultPromise(EventExecutor executor) {
      if (executor == null) {
         throw new NullPointerException("executor");
      } else {
         this.executor = executor;
      }
   }

   protected DefaultPromise() {
      this.executor = null;
   }

   protected EventExecutor executor() {
      return this.executor;
   }

   @Override
   public boolean isCancelled() {
      return isCancelled0(this.result);
   }

   private static boolean isCancelled0(Object result) {
      return result instanceof DefaultPromise.CauseHolder && ((DefaultPromise.CauseHolder)result).cause instanceof CancellationException;
   }

   @Override
   public boolean isCancellable() {
      return this.result == null;
   }

   @Override
   public boolean isDone() {
      return isDone0(this.result);
   }

   private static boolean isDone0(Object result) {
      return result != null && result != UNCANCELLABLE;
   }

   @Override
   public boolean isSuccess() {
      Object result = this.result;
      return result != null && result != UNCANCELLABLE ? !(result instanceof DefaultPromise.CauseHolder) : false;
   }

   @Override
   public Throwable cause() {
      Object result = this.result;
      return result instanceof DefaultPromise.CauseHolder ? ((DefaultPromise.CauseHolder)result).cause : null;
   }

   @Override
   public Promise<V> addListener(GenericFutureListener<? extends Future<? super V>> listener) {
      if (listener == null) {
         throw new NullPointerException("listener");
      } else if (this.isDone()) {
         this.notifyLateListener(listener);
         return this;
      } else {
         synchronized (this) {
            if (!this.isDone()) {
               if (this.listeners == null) {
                  this.listeners = listener;
               } else if (this.listeners instanceof DefaultFutureListeners) {
                  ((DefaultFutureListeners)this.listeners).add(listener);
               } else {
                  GenericFutureListener<? extends Future<V>> firstListener = (GenericFutureListener<? extends Future<V>>)this.listeners;
                  this.listeners = new DefaultFutureListeners(firstListener, listener);
               }

               return this;
            }
         }

         this.notifyLateListener(listener);
         return this;
      }
   }

   @Override
   public Promise<V> addListeners(GenericFutureListener<? extends Future<? super V>>... listeners) {
      if (listeners == null) {
         throw new NullPointerException("listeners");
      } else {
         for (GenericFutureListener<? extends Future<? super V>> l : listeners) {
            if (l == null) {
               break;
            }

            this.addListener(l);
         }

         return this;
      }
   }

   @Override
   public Promise<V> removeListener(GenericFutureListener<? extends Future<? super V>> listener) {
      if (listener == null) {
         throw new NullPointerException("listener");
      } else if (this.isDone()) {
         return this;
      } else {
         synchronized (this) {
            if (!this.isDone()) {
               if (this.listeners instanceof DefaultFutureListeners) {
                  ((DefaultFutureListeners)this.listeners).remove(listener);
               } else if (this.listeners == listener) {
                  this.listeners = null;
               }
            }

            return this;
         }
      }
   }

   @Override
   public Promise<V> removeListeners(GenericFutureListener<? extends Future<? super V>>... listeners) {
      if (listeners == null) {
         throw new NullPointerException("listeners");
      } else {
         for (GenericFutureListener<? extends Future<? super V>> l : listeners) {
            if (l == null) {
               break;
            }

            this.removeListener(l);
         }

         return this;
      }
   }

   @Override
   public Promise<V> sync() throws InterruptedException {
      this.await();
      this.rethrowIfFailed();
      return this;
   }

   @Override
   public Promise<V> syncUninterruptibly() {
      this.awaitUninterruptibly();
      this.rethrowIfFailed();
      return this;
   }

   private void rethrowIfFailed() {
      Throwable cause = this.cause();
      if (cause != null) {
         PlatformDependent.throwException(cause);
      }
   }

   @Override
   public Promise<V> await() throws InterruptedException {
      if (this.isDone()) {
         return this;
      } else if (Thread.interrupted()) {
         throw new InterruptedException(this.toString());
      } else {
         synchronized (this) {
            while (!this.isDone()) {
               this.checkDeadLock();
               this.incWaiters();

               try {
                  this.wait();
               } finally {
                  this.decWaiters();
               }
            }

            return this;
         }
      }
   }

   @Override
   public boolean await(long timeout, TimeUnit unit) throws InterruptedException {
      return this.await0(unit.toNanos(timeout), true);
   }

   @Override
   public boolean await(long timeoutMillis) throws InterruptedException {
      return this.await0(TimeUnit.MILLISECONDS.toNanos(timeoutMillis), true);
   }

   @Override
   public Promise<V> awaitUninterruptibly() {
      if (this.isDone()) {
         return this;
      } else {
         boolean interrupted = false;
         synchronized (this) {
            while (!this.isDone()) {
               this.checkDeadLock();
               this.incWaiters();

               try {
                  this.wait();
               } catch (InterruptedException var9) {
                  interrupted = true;
               } finally {
                  this.decWaiters();
               }
            }
         }

         if (interrupted) {
            Thread.currentThread().interrupt();
         }

         return this;
      }
   }

   @Override
   public boolean awaitUninterruptibly(long timeout, TimeUnit unit) {
      try {
         return this.await0(unit.toNanos(timeout), false);
      } catch (InterruptedException var5) {
         throw new InternalError();
      }
   }

   @Override
   public boolean awaitUninterruptibly(long timeoutMillis) {
      try {
         return this.await0(TimeUnit.MILLISECONDS.toNanos(timeoutMillis), false);
      } catch (InterruptedException var4) {
         throw new InternalError();
      }
   }

   private boolean await0(long timeoutNanos, boolean interruptable) throws InterruptedException {
      if (this.isDone()) {
         return true;
      } else if (timeoutNanos <= 0L) {
         return this.isDone();
      } else if (interruptable && Thread.interrupted()) {
         throw new InterruptedException(this.toString());
      } else {
         long startTime = System.nanoTime();
         long waitTime = timeoutNanos;
         boolean interrupted = false;

         try {
            synchronized (this) {
               if (this.isDone()) {
                  return true;
               } else if (waitTime <= 0L) {
                  return this.isDone();
               } else {
                  this.checkDeadLock();
                  this.incWaiters();

                  while (true) {
                     try {
                        try {
                           this.wait(waitTime / 1000000L, (int)(waitTime % 1000000L));
                        } catch (InterruptedException var22) {
                           if (interruptable) {
                              throw var22;
                           }

                           interrupted = true;
                        }

                        if (!this.isDone()) {
                           waitTime = timeoutNanos - (System.nanoTime() - startTime);
                           if (waitTime <= 0L) {
                              return this.isDone();
                           }
                        } else {
                           return true;
                        }
                     } finally {
                        this.decWaiters();
                     }
                  }
               }
            }
         } finally {
            if (interrupted) {
               Thread.currentThread().interrupt();
            }
         }
      }
   }

   protected void checkDeadLock() {
      EventExecutor e = this.executor();
      if (e != null && e.inEventLoop()) {
         throw new BlockingOperationException(this.toString());
      }
   }

   @Override
   public Promise<V> setSuccess(V result) {
      if (this.setSuccess0(result)) {
         this.notifyListeners();
         return this;
      } else {
         throw new IllegalStateException("complete already: " + this);
      }
   }

   @Override
   public boolean trySuccess(V result) {
      if (this.setSuccess0(result)) {
         this.notifyListeners();
         return true;
      } else {
         return false;
      }
   }

   @Override
   public Promise<V> setFailure(Throwable cause) {
      if (this.setFailure0(cause)) {
         this.notifyListeners();
         return this;
      } else {
         throw new IllegalStateException("complete already: " + this, cause);
      }
   }

   @Override
   public boolean tryFailure(Throwable cause) {
      if (this.setFailure0(cause)) {
         this.notifyListeners();
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean cancel(boolean mayInterruptIfRunning) {
      Object result = this.result;
      if (!isDone0(result) && result != UNCANCELLABLE) {
         synchronized (this) {
            result = this.result;
            if (isDone0(result) || result == UNCANCELLABLE) {
               return false;
            }

            this.result = CANCELLATION_CAUSE_HOLDER;
            if (this.hasWaiters()) {
               this.notifyAll();
            }
         }

         this.notifyListeners();
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean setUncancellable() {
      Object result = this.result;
      if (isDone0(result)) {
         return !isCancelled0(result);
      } else {
         synchronized (this) {
            result = this.result;
            if (isDone0(result)) {
               return !isCancelled0(result);
            } else {
               this.result = UNCANCELLABLE;
               return true;
            }
         }
      }
   }

   private boolean setFailure0(Throwable cause) {
      if (cause == null) {
         throw new NullPointerException("cause");
      } else if (this.isDone()) {
         return false;
      } else {
         synchronized (this) {
            if (this.isDone()) {
               return false;
            } else {
               this.result = new DefaultPromise.CauseHolder(cause);
               if (this.hasWaiters()) {
                  this.notifyAll();
               }

               return true;
            }
         }
      }
   }

   private boolean setSuccess0(V result) {
      if (this.isDone()) {
         return false;
      } else {
         synchronized (this) {
            if (this.isDone()) {
               return false;
            } else {
               if (result == null) {
                  this.result = SUCCESS;
               } else {
                  this.result = result;
               }

               if (this.hasWaiters()) {
                  this.notifyAll();
               }

               return true;
            }
         }
      }
   }

   @Override
   public V getNow() {
      Object result = this.result;
      return (V)(!(result instanceof DefaultPromise.CauseHolder) && result != SUCCESS ? result : null);
   }

   private boolean hasWaiters() {
      return this.waiters > 0;
   }

   private void incWaiters() {
      if (this.waiters == 32767) {
         throw new IllegalStateException("too many waiters: " + this);
      } else {
         this.waiters++;
      }
   }

   private void decWaiters() {
      this.waiters--;
   }

   private void notifyListeners() {
      Object listeners = this.listeners;
      if (listeners != null) {
         EventExecutor executor = this.executor();
         if (executor.inEventLoop()) {
            InternalThreadLocalMap threadLocals = InternalThreadLocalMap.get();
            int stackDepth = threadLocals.futureListenerStackDepth();
            if (stackDepth < 8) {
               threadLocals.setFutureListenerStackDepth(stackDepth + 1);

               try {
                  if (listeners instanceof DefaultFutureListeners) {
                     notifyListeners0(this, (DefaultFutureListeners)listeners);
                  } else {
                     GenericFutureListener<? extends Future<V>> l = (GenericFutureListener<? extends Future<V>>)listeners;
                     notifyListener0(this, l);
                  }
               } finally {
                  this.listeners = null;
                  threadLocals.setFutureListenerStackDepth(stackDepth);
               }

               return;
            }
         }

         if (listeners instanceof DefaultFutureListeners) {
            final DefaultFutureListeners dfl = (DefaultFutureListeners)listeners;
            execute(executor, new Runnable() {
               @Override
               public void run() {
                  DefaultPromise.notifyListeners0(DefaultPromise.this, dfl);
                  DefaultPromise.this.listeners = null;
               }
            });
         } else {
            final GenericFutureListener<? extends Future<V>> l = (GenericFutureListener<? extends Future<V>>)listeners;
            execute(executor, new Runnable() {
               @Override
               public void run() {
                  DefaultPromise.notifyListener0(DefaultPromise.this, l);
                  DefaultPromise.this.listeners = null;
               }
            });
         }
      }
   }

   private static void notifyListeners0(Future<?> future, DefaultFutureListeners listeners) {
      GenericFutureListener<?>[] a = listeners.listeners();
      int size = listeners.size();

      for (int i = 0; i < size; i++) {
         notifyListener0(future, a[i]);
      }
   }

   private void notifyLateListener(GenericFutureListener<?> l) {
      EventExecutor executor = this.executor();
      if (executor.inEventLoop()) {
         if (this.listeners != null || this.lateListeners != null) {
            DefaultPromise<V>.LateListeners lateListeners = this.lateListeners;
            if (lateListeners == null) {
               this.lateListeners = lateListeners = new DefaultPromise.LateListeners();
            }

            lateListeners.add(l);
            execute(executor, lateListeners);
            return;
         }

         InternalThreadLocalMap threadLocals = InternalThreadLocalMap.get();
         int stackDepth = threadLocals.futureListenerStackDepth();
         if (stackDepth < 8) {
            threadLocals.setFutureListenerStackDepth(stackDepth + 1);

            try {
               notifyListener0(this, l);
            } finally {
               threadLocals.setFutureListenerStackDepth(stackDepth);
            }

            return;
         }
      }

      execute(executor, new DefaultPromise.LateListenerNotifier(l));
   }

   protected static void notifyListener(EventExecutor eventExecutor, final Future<?> future, final GenericFutureListener<?> l) {
      if (eventExecutor.inEventLoop()) {
         InternalThreadLocalMap threadLocals = InternalThreadLocalMap.get();
         int stackDepth = threadLocals.futureListenerStackDepth();
         if (stackDepth < 8) {
            threadLocals.setFutureListenerStackDepth(stackDepth + 1);

            try {
               notifyListener0(future, l);
            } finally {
               threadLocals.setFutureListenerStackDepth(stackDepth);
            }

            return;
         }
      }

      execute(eventExecutor, new Runnable() {
         @Override
         public void run() {
            DefaultPromise.notifyListener0(future, l);
         }
      });
   }

   private static void execute(EventExecutor executor, Runnable task) {
      try {
         executor.execute(task);
      } catch (Throwable var3) {
         rejectedExecutionLogger.error("Failed to submit a listener notification task. Event loop shut down?", var3);
      }
   }

   static void notifyListener0(Future future, GenericFutureListener l) {
      try {
         l.operationComplete(future);
      } catch (Throwable var3) {
         if (logger.isWarnEnabled()) {
            logger.warn("An exception was thrown by " + l.getClass().getName() + ".operationComplete()", var3);
         }
      }
   }

   private synchronized Object progressiveListeners() {
      Object listeners = this.listeners;
      if (listeners == null) {
         return null;
      } else if (listeners instanceof DefaultFutureListeners) {
         DefaultFutureListeners dfl = (DefaultFutureListeners)listeners;
         int progressiveSize = dfl.progressiveSize();
         switch (progressiveSize) {
            case 0:
               return null;
            case 1:
               for (GenericFutureListener<?> l : dfl.listeners()) {
                  if (l instanceof GenericProgressiveFutureListener) {
                     return l;
                  }
               }

               return null;
            default:
               GenericFutureListener<?>[] array = dfl.listeners();
               GenericProgressiveFutureListener<?>[] copy = new GenericProgressiveFutureListener[progressiveSize];
               int i = 0;

               for (int j = 0; j < progressiveSize; i++) {
                  GenericFutureListener<?> lx = array[i];
                  if (lx instanceof GenericProgressiveFutureListener) {
                     copy[j++] = (GenericProgressiveFutureListener<?>)lx;
                  }
               }

               return copy;
         }
      } else {
         return listeners instanceof GenericProgressiveFutureListener ? listeners : null;
      }
   }

   void notifyProgressiveListeners(final long progress, final long total) {
      Object listeners = this.progressiveListeners();
      if (listeners != null) {
         final ProgressiveFuture<V> self = (ProgressiveFuture<V>)this;
         EventExecutor executor = this.executor();
         if (executor.inEventLoop()) {
            if (listeners instanceof GenericProgressiveFutureListener[]) {
               notifyProgressiveListeners0(self, (GenericProgressiveFutureListener<?>[])listeners, progress, total);
            } else {
               notifyProgressiveListener0(self, (GenericProgressiveFutureListener)listeners, progress, total);
            }
         } else if (listeners instanceof GenericProgressiveFutureListener[]) {
            final GenericProgressiveFutureListener<?>[] array = (GenericProgressiveFutureListener<?>[])listeners;
            execute(executor, new Runnable() {
               @Override
               public void run() {
                  DefaultPromise.notifyProgressiveListeners0(self, array, progress, total);
               }
            });
         } else {
            final GenericProgressiveFutureListener<ProgressiveFuture<V>> l = (GenericProgressiveFutureListener<ProgressiveFuture<V>>)listeners;
            execute(executor, new Runnable() {
               @Override
               public void run() {
                  DefaultPromise.notifyProgressiveListener0(self, l, progress, total);
               }
            });
         }
      }
   }

   private static void notifyProgressiveListeners0(ProgressiveFuture<?> future, GenericProgressiveFutureListener<?>[] listeners, long progress, long total) {
      for (GenericProgressiveFutureListener<?> l : listeners) {
         if (l == null) {
            break;
         }

         notifyProgressiveListener0(future, l, progress, total);
      }
   }

   private static void notifyProgressiveListener0(ProgressiveFuture future, GenericProgressiveFutureListener l, long progress, long total) {
      try {
         l.operationProgressed(future, progress, total);
      } catch (Throwable var7) {
         if (logger.isWarnEnabled()) {
            logger.warn("An exception was thrown by " + l.getClass().getName() + ".operationProgressed()", var7);
         }
      }
   }

   @Override
   public String toString() {
      return this.toStringBuilder().toString();
   }

   protected StringBuilder toStringBuilder() {
      StringBuilder buf = new StringBuilder(64);
      buf.append(StringUtil.simpleClassName(this));
      buf.append('@');
      buf.append(Integer.toHexString(this.hashCode()));
      Object result = this.result;
      if (result == SUCCESS) {
         buf.append("(success)");
      } else if (result == UNCANCELLABLE) {
         buf.append("(uncancellable)");
      } else if (result instanceof DefaultPromise.CauseHolder) {
         buf.append("(failure(");
         buf.append(((DefaultPromise.CauseHolder)result).cause);
         buf.append(')');
      } else {
         buf.append("(incomplete)");
      }

      return buf;
   }

   static {
      CANCELLATION_CAUSE_HOLDER.cause.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
   }

   private static final class CauseHolder {
      final Throwable cause;

      CauseHolder(Throwable cause) {
         this.cause = cause;
      }
   }

   private final class LateListenerNotifier implements Runnable {
      private GenericFutureListener<?> l;

      LateListenerNotifier(GenericFutureListener<?> l) {
         this.l = l;
      }

      @Override
      public void run() {
         DefaultPromise<V>.LateListeners lateListeners = DefaultPromise.this.lateListeners;
         if (this.l != null) {
            if (lateListeners == null) {
               DefaultPromise.this.lateListeners = lateListeners = DefaultPromise.this.new LateListeners();
            }

            lateListeners.add(this.l);
            this.l = null;
         }

         lateListeners.run();
      }
   }

   private final class LateListeners extends ArrayDeque<GenericFutureListener<?>> implements Runnable {
      private static final long serialVersionUID = -687137418080392244L;

      LateListeners() {
         super(2);
      }

      @Override
      public void run() {
         if (DefaultPromise.this.listeners == null) {
            while (true) {
               GenericFutureListener<?> l = this.poll();
               if (l == null) {
                  break;
               }

               DefaultPromise.notifyListener0(DefaultPromise.this, l);
            }
         } else {
            DefaultPromise.execute(DefaultPromise.this.executor(), this);
         }
      }
   }
}
