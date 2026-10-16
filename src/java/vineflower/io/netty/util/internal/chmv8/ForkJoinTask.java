package io.netty.util.internal.chmv8;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReentrantLock;
import sun.misc.Unsafe;

public abstract class ForkJoinTask<V> implements Future<V>, Serializable {
   volatile int status;
   static final int DONE_MASK = -268435456;
   static final int NORMAL = -268435456;
   static final int CANCELLED = -1073741824;
   static final int EXCEPTIONAL = Integer.MIN_VALUE;
   static final int SIGNAL = 65536;
   static final int SMASK = 65535;
   private static final ForkJoinTask.ExceptionNode[] exceptionTable = new ForkJoinTask.ExceptionNode[32];
   private static final ReentrantLock exceptionTableLock = new ReentrantLock();
   private static final ReferenceQueue<Object> exceptionTableRefQueue = new ReferenceQueue<>();
   private static final int EXCEPTION_MAP_CAPACITY = 32;
   private static final long serialVersionUID = -7721805057305804111L;
   private static final Unsafe U;
   private static final long STATUS;

   private int setCompletion(int completion) {
      int s;
      do {
         s = this.status;
         if (this.status < 0) {
            return s;
         }
      } while (!U.compareAndSwapInt(this, STATUS, s, s | completion));

      if (s >>> 16 != 0) {
         synchronized (this) {
            this.notifyAll();
         }
      }

      return completion;
   }

   final int doExec() {
      int s = this.status;
      if (this.status >= 0) {
         boolean completed;
         try {
            completed = this.exec();
         } catch (Throwable var4) {
            return this.setExceptionalCompletion(var4);
         }

         if (completed) {
            s = this.setCompletion(-268435456);
         }
      }

      return s;
   }

   final boolean trySetSignal() {
      int s = this.status;
      return s >= 0 && U.compareAndSwapInt(this, STATUS, s, s | 65536);
   }

   private int externalAwaitDone() {
      ForkJoinPool cp = ForkJoinPool.common;
      int s = this.status;
      if (this.status >= 0) {
         if (cp != null) {
            if (this instanceof CountedCompleter) {
               s = cp.externalHelpComplete((CountedCompleter<?>)this);
            } else if (cp.tryExternalUnpush(this)) {
               s = this.doExec();
            }
         }

         if (s >= 0) {
            s = this.status;
            if (this.status >= 0) {
               boolean interrupted = false;

               do {
                  if (U.compareAndSwapInt(this, STATUS, s, s | 65536)) {
                     synchronized (this) {
                        if (this.status >= 0) {
                           try {
                              this.wait();
                           } catch (InterruptedException var7) {
                              interrupted = true;
                           }
                        } else {
                           this.notifyAll();
                        }
                     }
                  }

                  s = this.status;
               } while (this.status >= 0);

               if (interrupted) {
                  Thread.currentThread().interrupt();
               }
            }
         }
      }

      return s;
   }

   private int externalInterruptibleAwaitDone() throws InterruptedException {
      ForkJoinPool cp = ForkJoinPool.common;
      if (Thread.interrupted()) {
         throw new InterruptedException();
      } else {
         int s = this.status;
         if (this.status >= 0 && cp != null) {
            if (this instanceof CountedCompleter) {
               cp.externalHelpComplete((CountedCompleter<?>)this);
            } else if (cp.tryExternalUnpush(this)) {
               this.doExec();
            }
         }

         while (true) {
            s = this.status;
            if (this.status < 0) {
               return s;
            }

            if (U.compareAndSwapInt(this, STATUS, s, s | 65536)) {
               synchronized (this) {
                  if (this.status >= 0) {
                     this.wait();
                  } else {
                     this.notifyAll();
                  }
               }
            }
         }
      }
   }

   private int doJoin() {
      int s = this.status;
      Thread t;
      ForkJoinWorkerThread wt;
      ForkJoinPool.WorkQueue w;
      int var5;
      return this.status < 0
         ? s
         : (
            (t = Thread.currentThread()) instanceof ForkJoinWorkerThread
               ? ((w = (wt = (ForkJoinWorkerThread)t).workQueue).tryUnpush(this) && (var5 = this.doExec()) < 0 ? var5 : wt.pool.awaitJoin(w, this))
               : this.externalAwaitDone()
         );
   }

   private int doInvoke() {
      int s;
      Thread t;
      ForkJoinWorkerThread wt;
      return (s = this.doExec()) < 0
         ? s
         : (
            (t = Thread.currentThread()) instanceof ForkJoinWorkerThread
               ? (wt = (ForkJoinWorkerThread)t).pool.awaitJoin(wt.workQueue, this)
               : this.externalAwaitDone()
         );
   }

   final int recordExceptionalCompletion(Throwable ex) {
      int s = this.status;
      if (this.status >= 0) {
         int h = System.identityHashCode(this);
         ReentrantLock lock = exceptionTableLock;
         lock.lock();

         try {
            expungeStaleExceptions();
            ForkJoinTask.ExceptionNode[] t = exceptionTable;
            int i = h & t.length - 1;
            ForkJoinTask.ExceptionNode e = t[i];

            while (true) {
               if (e == null) {
                  t[i] = new ForkJoinTask.ExceptionNode(this, ex, t[i]);
                  break;
               }

               if (e.get() == this) {
                  break;
               }

               e = e.next;
            }
         } finally {
            lock.unlock();
         }

         s = this.setCompletion(Integer.MIN_VALUE);
      }

      return s;
   }

   private int setExceptionalCompletion(Throwable ex) {
      int s = this.recordExceptionalCompletion(ex);
      if ((s & -268435456) == Integer.MIN_VALUE) {
         this.internalPropagateException(ex);
      }

      return s;
   }

   void internalPropagateException(Throwable ex) {
   }

   static final void cancelIgnoringExceptions(ForkJoinTask<?> t) {
      if (t != null && t.status >= 0) {
         try {
            t.cancel(false);
         } catch (Throwable var2) {
         }
      }
   }

   private void clearExceptionalCompletion() {
      int h = System.identityHashCode(this);
      ReentrantLock lock = exceptionTableLock;
      lock.lock();

      try {
         ForkJoinTask.ExceptionNode[] t = exceptionTable;
         int i = h & t.length - 1;
         ForkJoinTask.ExceptionNode e = t[i];
         ForkJoinTask.ExceptionNode pred = null;

         while (e != null) {
            ForkJoinTask.ExceptionNode next = e.next;
            if (e.get() == this) {
               if (pred == null) {
                  t[i] = next;
               } else {
                  pred.next = next;
               }
               break;
            }

            pred = e;
            e = next;
         }

         expungeStaleExceptions();
         this.status = 0;
      } finally {
         lock.unlock();
      }
   }

   private Throwable getThrowableException() {
      if ((this.status & -268435456) != Integer.MIN_VALUE) {
         return null;
      } else {
         int h = System.identityHashCode(this);
         ReentrantLock lock = exceptionTableLock;
         lock.lock();

         ForkJoinTask.ExceptionNode e;
         try {
            expungeStaleExceptions();
            ForkJoinTask.ExceptionNode[] t = exceptionTable;
            e = t[h & t.length - 1];

            while (e != null && e.get() != this) {
               e = e.next;
            }
         } finally {
            lock.unlock();
         }

         if (e != null) {
            Throwable ex = e.ex;
            if (e.ex != null) {
               return ex;
            }
         }

         return null;
      }
   }

   private static void expungeStaleExceptions() {
      Object x;
      while ((x = exceptionTableRefQueue.poll()) != null) {
         if (x instanceof ForkJoinTask.ExceptionNode) {
            ForkJoinTask<?> key = ((ForkJoinTask.ExceptionNode)x).get();
            ForkJoinTask.ExceptionNode[] t = exceptionTable;
            int i = System.identityHashCode(key) & t.length - 1;
            ForkJoinTask.ExceptionNode e = t[i];
            ForkJoinTask.ExceptionNode pred = null;

            while (e != null) {
               ForkJoinTask.ExceptionNode next = e.next;
               if (e == x) {
                  if (pred == null) {
                     t[i] = next;
                     break;
                  }

                  pred.next = next;
                  break;
               }

               pred = e;
               e = next;
            }
         }
      }
   }

   static final void helpExpungeStaleExceptions() {
      ReentrantLock lock = exceptionTableLock;
      if (lock.tryLock()) {
         try {
            expungeStaleExceptions();
         } finally {
            lock.unlock();
         }
      }
   }

   static void rethrow(Throwable ex) {
      if (ex != null) {
         uncheckedThrow(ex);
      }
   }

   static <T extends Throwable> void uncheckedThrow(Throwable t) throws T {
      throw t;
   }

   private void reportException(int s) {
      if (s == -1073741824) {
         throw new CancellationException();
      } else {
         if (s == Integer.MIN_VALUE) {
            rethrow(this.getThrowableException());
         }
      }
   }

   public final ForkJoinTask<V> fork() {
      Thread t;
      if ((t = Thread.currentThread()) instanceof ForkJoinWorkerThread) {
         ((ForkJoinWorkerThread)t).workQueue.push(this);
      } else {
         ForkJoinPool.common.externalPush(this);
      }

      return this;
   }

   public final V join() {
      int s;
      if ((s = this.doJoin() & -268435456) != -268435456) {
         this.reportException(s);
      }

      return this.getRawResult();
   }

   public final V invoke() {
      int s;
      if ((s = this.doInvoke() & -268435456) != -268435456) {
         this.reportException(s);
      }

      return this.getRawResult();
   }

   public static void invokeAll(ForkJoinTask<?> t1, ForkJoinTask<?> t2) {
      t2.fork();
      int s1;
      if ((s1 = t1.doInvoke() & -268435456) != -268435456) {
         t1.reportException(s1);
      }

      int s2;
      if ((s2 = t2.doJoin() & -268435456) != -268435456) {
         t2.reportException(s2);
      }
   }

   public static void invokeAll(ForkJoinTask<?>... tasks) {
      Throwable ex = null;
      int last = tasks.length - 1;

      for (int i = last; i >= 0; i--) {
         ForkJoinTask<?> t = tasks[i];
         if (t == null) {
            if (ex == null) {
               ex = new NullPointerException();
            }
         } else if (i != 0) {
            t.fork();
         } else if (t.doInvoke() < -268435456 && ex == null) {
            ex = t.getException();
         }
      }

      for (int ix = 1; ix <= last; ix++) {
         ForkJoinTask<?> t = tasks[ix];
         if (t != null) {
            if (ex != null) {
               t.cancel(false);
            } else if (t.doJoin() < -268435456) {
               ex = t.getException();
            }
         }
      }

      if (ex != null) {
         rethrow(ex);
      }
   }

   public static <T extends ForkJoinTask<?>> Collection<T> invokeAll(Collection<T> tasks) {
      if (tasks instanceof RandomAccess && tasks instanceof List) {
         List<? extends ForkJoinTask<?>> ts = (List<? extends ForkJoinTask<?>>)tasks;
         Throwable ex = null;
         int last = ts.size() - 1;

         for (int i = last; i >= 0; i--) {
            ForkJoinTask<?> t = (ForkJoinTask<?>)ts.get(i);
            if (t == null) {
               if (ex == null) {
                  ex = new NullPointerException();
               }
            } else if (i != 0) {
               t.fork();
            } else if (t.doInvoke() < -268435456 && ex == null) {
               ex = t.getException();
            }
         }

         for (int ix = 1; ix <= last; ix++) {
            ForkJoinTask<?> t = (ForkJoinTask<?>)ts.get(ix);
            if (t != null) {
               if (ex != null) {
                  t.cancel(false);
               } else if (t.doJoin() < -268435456) {
                  ex = t.getException();
               }
            }
         }

         if (ex != null) {
            rethrow(ex);
         }

         return tasks;
      } else {
         invokeAll(tasks.toArray(new ForkJoinTask[tasks.size()]));
         return tasks;
      }
   }

   @Override
   public boolean cancel(boolean mayInterruptIfRunning) {
      return (this.setCompletion(-1073741824) & -268435456) == -1073741824;
   }

   @Override
   public final boolean isDone() {
      return this.status < 0;
   }

   @Override
   public final boolean isCancelled() {
      return (this.status & -268435456) == -1073741824;
   }

   public final boolean isCompletedAbnormally() {
      return this.status < -268435456;
   }

   public final boolean isCompletedNormally() {
      return (this.status & -268435456) == -268435456;
   }

   public final Throwable getException() {
      int s = this.status & -268435456;
      return (Throwable)(s >= -268435456 ? null : (s == -1073741824 ? new CancellationException() : this.getThrowableException()));
   }

   public void completeExceptionally(Throwable ex) {
      this.setExceptionalCompletion((Throwable)(!(ex instanceof RuntimeException) && !(ex instanceof Error) ? new RuntimeException(ex) : ex));
   }

   public void complete(V value) {
      try {
         this.setRawResult(value);
      } catch (Throwable var3) {
         this.setExceptionalCompletion(var3);
         return;
      }

      this.setCompletion(-268435456);
   }

   public final void quietlyComplete() {
      this.setCompletion(-268435456);
   }

   @Override
   public final V get() throws InterruptedException, ExecutionException {
      int s = Thread.currentThread() instanceof ForkJoinWorkerThread ? this.doJoin() : this.externalInterruptibleAwaitDone();
      if ((s = s & -268435456) == -1073741824) {
         throw new CancellationException();
      } else {
         Throwable ex;
         if (s == Integer.MIN_VALUE && (ex = this.getThrowableException()) != null) {
            throw new ExecutionException(ex);
         } else {
            return this.getRawResult();
         }
      }
   }

   @Override
   public final V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
      if (Thread.interrupted()) {
         throw new InterruptedException();
      } else {
         long ns = unit.toNanos(timeout);
         int s = this.status;
         if (this.status >= 0 && ns > 0L) {
            long deadline = System.nanoTime() + ns;
            ForkJoinPool p = null;
            ForkJoinPool.WorkQueue w = null;
            Thread t = Thread.currentThread();
            if (t instanceof ForkJoinWorkerThread) {
               ForkJoinWorkerThread wt = (ForkJoinWorkerThread)t;
               p = wt.pool;
               w = wt.workQueue;
               p.helpJoinOnce(w, this);
            } else {
               ForkJoinPool cp = ForkJoinPool.common;
               if (ForkJoinPool.common != null) {
                  if (this instanceof CountedCompleter) {
                     cp.externalHelpComplete((CountedCompleter<?>)this);
                  } else if (cp.tryExternalUnpush(this)) {
                     this.doExec();
                  }
               }
            }

            boolean canBlock = false;
            boolean interrupted = false;

            try {
               while (true) {
                  s = this.status;
                  if (this.status < 0) {
                     break;
                  }

                  if (w != null && w.qlock < 0) {
                     cancelIgnoringExceptions(this);
                  } else if (!canBlock) {
                     if (p == null || p.tryCompensate(p.ctl)) {
                        canBlock = true;
                     }
                  } else {
                     long ms;
                     if ((ms = TimeUnit.NANOSECONDS.toMillis(ns)) > 0L && U.compareAndSwapInt(this, STATUS, s, s | 65536)) {
                        synchronized (this) {
                           if (this.status >= 0) {
                              try {
                                 this.wait(ms);
                              } catch (InterruptedException var24) {
                                 if (p == null) {
                                    interrupted = true;
                                 }
                              }
                           } else {
                              this.notifyAll();
                           }
                        }
                     }

                     s = this.status;
                     if (this.status < 0 || interrupted || (ns = deadline - System.nanoTime()) <= 0L) {
                        break;
                     }
                  }
               }
            } finally {
               if (p != null && canBlock) {
                  p.incrementActiveCount();
               }
            }

            if (interrupted) {
               throw new InterruptedException();
            }
         }

         if ((s = s & -268435456) != -268435456) {
            if (s == -1073741824) {
               throw new CancellationException();
            }

            if (s != Integer.MIN_VALUE) {
               throw new TimeoutException();
            }

            Throwable ex;
            if ((ex = this.getThrowableException()) != null) {
               throw new ExecutionException(ex);
            }
         }

         return this.getRawResult();
      }
   }

   public final void quietlyJoin() {
      this.doJoin();
   }

   public final void quietlyInvoke() {
      this.doInvoke();
   }

   public static void helpQuiesce() {
      Thread t;
      if ((t = Thread.currentThread()) instanceof ForkJoinWorkerThread) {
         ForkJoinWorkerThread wt = (ForkJoinWorkerThread)t;
         wt.pool.helpQuiescePool(wt.workQueue);
      } else {
         ForkJoinPool.quiesceCommonPool();
      }
   }

   public void reinitialize() {
      if ((this.status & -268435456) == Integer.MIN_VALUE) {
         this.clearExceptionalCompletion();
      } else {
         this.status = 0;
      }
   }

   public static ForkJoinPool getPool() {
      Thread t = Thread.currentThread();
      return t instanceof ForkJoinWorkerThread ? ((ForkJoinWorkerThread)t).pool : null;
   }

   public static boolean inForkJoinPool() {
      return Thread.currentThread() instanceof ForkJoinWorkerThread;
   }

   public boolean tryUnfork() {
      Thread t;
      return (t = Thread.currentThread()) instanceof ForkJoinWorkerThread
         ? ((ForkJoinWorkerThread)t).workQueue.tryUnpush(this)
         : ForkJoinPool.common.tryExternalUnpush(this);
   }

   public static int getQueuedTaskCount() {
      Thread t;
      ForkJoinPool.WorkQueue q;
      if ((t = Thread.currentThread()) instanceof ForkJoinWorkerThread) {
         q = ((ForkJoinWorkerThread)t).workQueue;
      } else {
         q = ForkJoinPool.commonSubmitterQueue();
      }

      return q == null ? 0 : q.queueSize();
   }

   public static int getSurplusQueuedTaskCount() {
      return ForkJoinPool.getSurplusQueuedTaskCount();
   }

   public abstract V getRawResult();

   protected abstract void setRawResult(V var1);

   protected abstract boolean exec();

   protected static ForkJoinTask<?> peekNextLocalTask() {
      Thread t;
      ForkJoinPool.WorkQueue q;
      if ((t = Thread.currentThread()) instanceof ForkJoinWorkerThread) {
         q = ((ForkJoinWorkerThread)t).workQueue;
      } else {
         q = ForkJoinPool.commonSubmitterQueue();
      }

      return q == null ? null : q.peek();
   }

   protected static ForkJoinTask<?> pollNextLocalTask() {
      Thread t;
      return (t = Thread.currentThread()) instanceof ForkJoinWorkerThread ? ((ForkJoinWorkerThread)t).workQueue.nextLocalTask() : null;
   }

   protected static ForkJoinTask<?> pollTask() {
      Thread t;
      ForkJoinWorkerThread wt;
      return (t = Thread.currentThread()) instanceof ForkJoinWorkerThread ? (wt = (ForkJoinWorkerThread)t).pool.nextTaskFor(wt.workQueue) : null;
   }

   public final short getForkJoinTaskTag() {
      return (short)this.status;
   }

   public final short setForkJoinTaskTag(short tag) {
      int s;
      do {
         s = this.status;
      } while (!U.compareAndSwapInt(this, STATUS, this.status, s & -65536 | tag & '\uffff'));

      return (short)s;
   }

   public final boolean compareAndSetForkJoinTaskTag(short e, short tag) {
      int s;
      do {
         s = this.status;
         if ((short)this.status != e) {
            return false;
         }
      } while (!U.compareAndSwapInt(this, STATUS, s, s & -65536 | tag & '\uffff'));

      return true;
   }

   public static ForkJoinTask<?> adapt(Runnable runnable) {
      return new ForkJoinTask.AdaptedRunnableAction(runnable);
   }

   public static <T> ForkJoinTask<T> adapt(Runnable runnable, T result) {
      return new ForkJoinTask.AdaptedRunnable<>(runnable, result);
   }

   public static <T> ForkJoinTask<T> adapt(Callable<? extends T> callable) {
      return new ForkJoinTask.AdaptedCallable<>(callable);
   }

   private void writeObject(ObjectOutputStream s) throws IOException {
      s.defaultWriteObject();
      s.writeObject(this.getException());
   }

   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
      s.defaultReadObject();
      Object ex = s.readObject();
      if (ex != null) {
         this.setExceptionalCompletion((Throwable)ex);
      }
   }

   private static Unsafe getUnsafe() {
      try {
         return Unsafe.getUnsafe();
      } catch (SecurityException var2) {
         try {
            return AccessController.doPrivileged(new PrivilegedExceptionAction<Unsafe>() {
               public Unsafe run() throws Exception {
                  Class<Unsafe> k = Unsafe.class;

                  for (Field f : k.getDeclaredFields()) {
                     f.setAccessible(true);
                     Object x = f.get(null);
                     if (k.isInstance(x)) {
                        return k.cast(x);
                     }
                  }

                  throw new NoSuchFieldError("the Unsafe");
               }
            });
         } catch (PrivilegedActionException var1) {
            throw new RuntimeException("Could not initialize intrinsics", var1.getCause());
         }
      }
   }

   static {
      try {
         U = getUnsafe();
         Class<?> k = ForkJoinTask.class;
         STATUS = U.objectFieldOffset(k.getDeclaredField("status"));
      } catch (Exception var1) {
         throw new Error(var1);
      }
   }

   static final class AdaptedCallable<T> extends ForkJoinTask<T> implements RunnableFuture<T> {
      final Callable<? extends T> callable;
      T result;
      private static final long serialVersionUID = 2838392045355241008L;

      AdaptedCallable(Callable<? extends T> callable) {
         if (callable == null) {
            throw new NullPointerException();
         } else {
            this.callable = callable;
         }
      }

      @Override
      public final T getRawResult() {
         return this.result;
      }

      @Override
      public final void setRawResult(T v) {
         this.result = v;
      }

      @Override
      public final boolean exec() {
         try {
            this.result = (T)this.callable.call();
            return true;
         } catch (Error var2) {
            throw var2;
         } catch (RuntimeException var3) {
            throw var3;
         } catch (Exception var4) {
            throw new RuntimeException(var4);
         }
      }

      @Override
      public final void run() {
         this.invoke();
      }
   }

   static final class AdaptedRunnable<T> extends ForkJoinTask<T> implements RunnableFuture<T> {
      final Runnable runnable;
      T result;
      private static final long serialVersionUID = 5232453952276885070L;

      AdaptedRunnable(Runnable runnable, T result) {
         if (runnable == null) {
            throw new NullPointerException();
         } else {
            this.runnable = runnable;
            this.result = result;
         }
      }

      @Override
      public final T getRawResult() {
         return this.result;
      }

      @Override
      public final void setRawResult(T v) {
         this.result = v;
      }

      @Override
      public final boolean exec() {
         this.runnable.run();
         return true;
      }

      @Override
      public final void run() {
         this.invoke();
      }
   }

   static final class AdaptedRunnableAction extends ForkJoinTask<Void> implements RunnableFuture<Void> {
      final Runnable runnable;
      private static final long serialVersionUID = 5232453952276885070L;

      AdaptedRunnableAction(Runnable runnable) {
         if (runnable == null) {
            throw new NullPointerException();
         } else {
            this.runnable = runnable;
         }
      }

      public final Void getRawResult() {
         return null;
      }

      public final void setRawResult(Void v) {
      }

      @Override
      public final boolean exec() {
         this.runnable.run();
         return true;
      }

      @Override
      public final void run() {
         this.invoke();
      }
   }

   static final class ExceptionNode extends WeakReference<ForkJoinTask<?>> {
      final Throwable ex;
      ForkJoinTask.ExceptionNode next;
      final long thrower;

      ExceptionNode(ForkJoinTask<?> task, Throwable ex, ForkJoinTask.ExceptionNode next) {
         super(task, ForkJoinTask.exceptionTableRefQueue);
         this.ex = ex;
         this.next = next;
         this.thrower = Thread.currentThread().getId();
      }
   }

   static final class RunnableExecuteAction extends ForkJoinTask<Void> {
      final Runnable runnable;
      private static final long serialVersionUID = 5232453952276885070L;

      RunnableExecuteAction(Runnable runnable) {
         if (runnable == null) {
            throw new NullPointerException();
         } else {
            this.runnable = runnable;
         }
      }

      public final Void getRawResult() {
         return null;
      }

      public final void setRawResult(Void v) {
      }

      @Override
      public final boolean exec() {
         this.runnable.run();
         return true;
      }

      @Override
      void internalPropagateException(Throwable ex) {
         rethrow(ex);
      }
   }
}
