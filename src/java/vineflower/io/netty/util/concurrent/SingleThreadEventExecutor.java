package io.netty.util.concurrent;

import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public abstract class SingleThreadEventExecutor extends AbstractEventExecutor {
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(SingleThreadEventExecutor.class);
   private static final int ST_NOT_STARTED = 1;
   private static final int ST_STARTED = 2;
   private static final int ST_SHUTTING_DOWN = 3;
   private static final int ST_SHUTDOWN = 4;
   private static final int ST_TERMINATED = 5;
   private static final Runnable WAKEUP_TASK = new Runnable() {
      @Override
      public void run() {
      }
   };
   private static final AtomicIntegerFieldUpdater<SingleThreadEventExecutor> STATE_UPDATER;
   private final EventExecutorGroup parent;
   private final Queue<Runnable> taskQueue;
   final Queue<ScheduledFutureTask<?>> delayedTaskQueue = new PriorityQueue<>();
   private final Thread thread;
   private final Semaphore threadLock = new Semaphore(0);
   private final Set<Runnable> shutdownHooks = new LinkedHashSet<>();
   private final boolean addTaskWakesUp;
   private long lastExecutionTime;
   private volatile int state = 1;
   private volatile long gracefulShutdownQuietPeriod;
   private volatile long gracefulShutdownTimeout;
   private long gracefulShutdownStartTime;
   private final Promise<?> terminationFuture = new DefaultPromise(GlobalEventExecutor.INSTANCE);
   private static final long SCHEDULE_PURGE_INTERVAL;

   protected SingleThreadEventExecutor(EventExecutorGroup parent, ThreadFactory threadFactory, boolean addTaskWakesUp) {
      if (threadFactory == null) {
         throw new NullPointerException("threadFactory");
      } else {
         this.parent = parent;
         this.addTaskWakesUp = addTaskWakesUp;
         this.thread = threadFactory.newThread(
            new Runnable() {
               // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
               // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
               @Override
               public void run() {
                  boolean success = false;
                  SingleThreadEventExecutor.this.updateLastExecutionTime();
                  boolean var112 = false /* VF: Semaphore variable */;

                  label1059: {
                     try {
                        var112 = true;
                        SingleThreadEventExecutor.this.run();
                        success = true;
                        var112 = false;
                        break label1059;
                     } catch (Throwable var119) {
                        SingleThreadEventExecutor.logger.warn("Unexpected exception from an event executor: ", var119);
                        var112 = false;
                     } finally {
                        if (var112) {
                           int oldState;
                           do {
                              oldState = SingleThreadEventExecutor.STATE_UPDATER.get(SingleThreadEventExecutor.this);
                           } while (oldState < 3 && !SingleThreadEventExecutor.STATE_UPDATER.compareAndSet(SingleThreadEventExecutor.this, oldState, 3));

                           try {
                              while (!SingleThreadEventExecutor.this.confirmShutdown()) {
                              }
                           } finally {
                              try {
                                 SingleThreadEventExecutor.this.cleanup();
                              } finally {
                                 SingleThreadEventExecutor.STATE_UPDATER.set(SingleThreadEventExecutor.this, 5);
                                 SingleThreadEventExecutor.this.threadLock.release();
                                 if (!SingleThreadEventExecutor.this.taskQueue.isEmpty()) {
                                    SingleThreadEventExecutor.logger
                                       .warn("An event executor terminated with non-empty task queue (" + SingleThreadEventExecutor.this.taskQueue.size() + ')');
                                 }

                                 SingleThreadEventExecutor.this.terminationFuture.setSuccess(null);
                              }
                           }
                        }
                     }

                     int oldState;
                     do {
                        oldState = SingleThreadEventExecutor.STATE_UPDATER.get(SingleThreadEventExecutor.this);
                     } while (oldState < 3 && !SingleThreadEventExecutor.STATE_UPDATER.compareAndSet(SingleThreadEventExecutor.this, oldState, 3));

                     try {
                        while (!SingleThreadEventExecutor.this.confirmShutdown()) {
                        }

                        return;
                     } finally {
                        try {
                           SingleThreadEventExecutor.this.cleanup();
                        } finally {
                           SingleThreadEventExecutor.STATE_UPDATER.set(SingleThreadEventExecutor.this, 5);
                           SingleThreadEventExecutor.this.threadLock.release();
                           if (!SingleThreadEventExecutor.this.taskQueue.isEmpty()) {
                              SingleThreadEventExecutor.logger
                                 .warn("An event executor terminated with non-empty task queue (" + SingleThreadEventExecutor.this.taskQueue.size() + ')');
                           }

                           SingleThreadEventExecutor.this.terminationFuture.setSuccess(null);
                        }
                     }
                  }

                  int oldState;
                  do {
                     oldState = SingleThreadEventExecutor.STATE_UPDATER.get(SingleThreadEventExecutor.this);
                  } while (oldState < 3 && !SingleThreadEventExecutor.STATE_UPDATER.compareAndSet(SingleThreadEventExecutor.this, oldState, 3));

                  if (SingleThreadEventExecutor.this.gracefulShutdownStartTime == 0L) {
                     SingleThreadEventExecutor.logger
                        .error(
                           "Buggy "
                              + EventExecutor.class.getSimpleName()
                              + " implementation; "
                              + SingleThreadEventExecutor.class.getSimpleName()
                              + ".confirmShutdown() must be called "
                              + "before run() implementation terminates."
                        );
                  }

                  try {
                     while (!SingleThreadEventExecutor.this.confirmShutdown()) {
                     }
                  } finally {
                     try {
                        SingleThreadEventExecutor.this.cleanup();
                     } finally {
                        SingleThreadEventExecutor.STATE_UPDATER.set(SingleThreadEventExecutor.this, 5);
                        SingleThreadEventExecutor.this.threadLock.release();
                        if (!SingleThreadEventExecutor.this.taskQueue.isEmpty()) {
                           SingleThreadEventExecutor.logger
                              .warn("An event executor terminated with non-empty task queue (" + SingleThreadEventExecutor.this.taskQueue.size() + ')');
                        }

                        SingleThreadEventExecutor.this.terminationFuture.setSuccess(null);
                     }
                  }
               }
            }
         );
         this.taskQueue = this.newTaskQueue();
      }
   }

   protected Queue<Runnable> newTaskQueue() {
      return new LinkedBlockingQueue<>();
   }

   @Override
   public EventExecutorGroup parent() {
      return this.parent;
   }

   protected void interruptThread() {
      this.thread.interrupt();
   }

   protected Runnable pollTask() {
      assert this.inEventLoop();

      Runnable task;
      do {
         task = this.taskQueue.poll();
      } while (task == WAKEUP_TASK);

      return task;
   }

   protected Runnable takeTask() {
      assert this.inEventLoop();

      if (!(this.taskQueue instanceof BlockingQueue)) {
         throw new UnsupportedOperationException();
      } else {
         BlockingQueue<Runnable> taskQueue = (BlockingQueue<Runnable>)this.taskQueue;

         Runnable task;
         do {
            ScheduledFutureTask<?> delayedTask = this.delayedTaskQueue.peek();
            if (delayedTask == null) {
               Runnable taskx = null;

               try {
                  taskx = taskQueue.take();
                  if (taskx == WAKEUP_TASK) {
                     taskx = null;
                  }
               } catch (InterruptedException var7) {
               }

               return taskx;
            }

            long delayNanos = delayedTask.delayNanos();
            task = null;
            if (delayNanos > 0L) {
               try {
                  task = taskQueue.poll(delayNanos, TimeUnit.NANOSECONDS);
               } catch (InterruptedException var8) {
                  return null;
               }
            }

            if (task == null) {
               this.fetchFromDelayedQueue();
               task = taskQueue.poll();
            }
         } while (task == null);

         return task;
      }
   }

   private void fetchFromDelayedQueue() {
      long nanoTime = 0L;

      while (true) {
         ScheduledFutureTask<?> delayedTask = this.delayedTaskQueue.peek();
         if (delayedTask == null) {
            break;
         }

         if (nanoTime == 0L) {
            nanoTime = ScheduledFutureTask.nanoTime();
         }

         if (delayedTask.deadlineNanos() > nanoTime) {
            break;
         }

         this.delayedTaskQueue.remove();
         this.taskQueue.add(delayedTask);
      }
   }

   protected Runnable peekTask() {
      assert this.inEventLoop();

      return this.taskQueue.peek();
   }

   protected boolean hasTasks() {
      assert this.inEventLoop();

      return !this.taskQueue.isEmpty();
   }

   protected boolean hasScheduledTasks() {
      assert this.inEventLoop();

      ScheduledFutureTask<?> delayedTask = this.delayedTaskQueue.peek();
      return delayedTask != null && delayedTask.deadlineNanos() <= ScheduledFutureTask.nanoTime();
   }

   public final int pendingTasks() {
      return this.taskQueue.size();
   }

   protected void addTask(Runnable task) {
      if (task == null) {
         throw new NullPointerException("task");
      } else {
         if (this.isShutdown()) {
            reject();
         }

         this.taskQueue.add(task);
      }
   }

   protected boolean removeTask(Runnable task) {
      if (task == null) {
         throw new NullPointerException("task");
      } else {
         return this.taskQueue.remove(task);
      }
   }

   protected boolean runAllTasks() {
      this.fetchFromDelayedQueue();
      Runnable task = this.pollTask();
      if (task == null) {
         return false;
      } else {
         do {
            try {
               task.run();
            } catch (Throwable var3) {
               logger.warn("A task raised an exception.", var3);
            }

            task = this.pollTask();
         } while (task != null);

         this.lastExecutionTime = ScheduledFutureTask.nanoTime();
         return true;
      }
   }

   protected boolean runAllTasks(long timeoutNanos) {
      this.fetchFromDelayedQueue();
      Runnable task = this.pollTask();
      if (task == null) {
         return false;
      } else {
         long deadline = ScheduledFutureTask.nanoTime() + timeoutNanos;
         long runTasks = 0L;

         long lastExecutionTime;
         while (true) {
            try {
               task.run();
            } catch (Throwable var11) {
               logger.warn("A task raised an exception.", var11);
            }

            if ((++runTasks & 63L) == 0L) {
               lastExecutionTime = ScheduledFutureTask.nanoTime();
               if (lastExecutionTime >= deadline) {
                  break;
               }
            }

            task = this.pollTask();
            if (task == null) {
               lastExecutionTime = ScheduledFutureTask.nanoTime();
               break;
            }
         }

         this.lastExecutionTime = lastExecutionTime;
         return true;
      }
   }

   protected long delayNanos(long currentTimeNanos) {
      ScheduledFutureTask<?> delayedTask = this.delayedTaskQueue.peek();
      return delayedTask == null ? SCHEDULE_PURGE_INTERVAL : delayedTask.delayNanos(currentTimeNanos);
   }

   protected void updateLastExecutionTime() {
      this.lastExecutionTime = ScheduledFutureTask.nanoTime();
   }

   protected abstract void run();

   protected void cleanup() {
   }

   protected void wakeup(boolean inEventLoop) {
      if (!inEventLoop || STATE_UPDATER.get(this) == 3) {
         this.taskQueue.add(WAKEUP_TASK);
      }
   }

   @Override
   public boolean inEventLoop(Thread thread) {
      return thread == this.thread;
   }

   public void addShutdownHook(final Runnable task) {
      if (this.inEventLoop()) {
         this.shutdownHooks.add(task);
      } else {
         this.execute(new Runnable() {
            @Override
            public void run() {
               SingleThreadEventExecutor.this.shutdownHooks.add(task);
            }
         });
      }
   }

   public void removeShutdownHook(final Runnable task) {
      if (this.inEventLoop()) {
         this.shutdownHooks.remove(task);
      } else {
         this.execute(new Runnable() {
            @Override
            public void run() {
               SingleThreadEventExecutor.this.shutdownHooks.remove(task);
            }
         });
      }
   }

   private boolean runShutdownHooks() {
      boolean ran = false;

      while (!this.shutdownHooks.isEmpty()) {
         List<Runnable> copy = new ArrayList<>(this.shutdownHooks);
         this.shutdownHooks.clear();

         for (Runnable task : copy) {
            try {
               task.run();
            } catch (Throwable var9) {
               logger.warn("Shutdown hook raised an exception.", var9);
            } finally {
               ran = true;
            }
         }
      }

      if (ran) {
         this.lastExecutionTime = ScheduledFutureTask.nanoTime();
      }

      return ran;
   }

   @Override
   public Future<?> shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit) {
      if (quietPeriod < 0L) {
         throw new IllegalArgumentException("quietPeriod: " + quietPeriod + " (expected >= 0)");
      } else if (timeout < quietPeriod) {
         throw new IllegalArgumentException("timeout: " + timeout + " (expected >= quietPeriod (" + quietPeriod + "))");
      } else if (unit == null) {
         throw new NullPointerException("unit");
      } else if (this.isShuttingDown()) {
         return this.terminationFuture();
      } else {
         boolean inEventLoop = this.inEventLoop();

         while (!this.isShuttingDown()) {
            boolean wakeup = true;
            int oldState = STATE_UPDATER.get(this);
            int newState;
            if (inEventLoop) {
               newState = 3;
            } else {
               switch (oldState) {
                  case 1:
                  case 2:
                     newState = 3;
                     break;
                  default:
                     newState = oldState;
                     wakeup = false;
               }
            }

            if (STATE_UPDATER.compareAndSet(this, oldState, newState)) {
               this.gracefulShutdownQuietPeriod = unit.toNanos(quietPeriod);
               this.gracefulShutdownTimeout = unit.toNanos(timeout);
               if (oldState == 1) {
                  this.thread.start();
               }

               if (wakeup) {
                  this.wakeup(inEventLoop);
               }

               return this.terminationFuture();
            }
         }

         return this.terminationFuture();
      }
   }

   @Override
   public Future<?> terminationFuture() {
      return this.terminationFuture;
   }

   @Deprecated
   @Override
   public void shutdown() {
      if (!this.isShutdown()) {
         boolean inEventLoop = this.inEventLoop();

         while (!this.isShuttingDown()) {
            boolean wakeup = true;
            int oldState = STATE_UPDATER.get(this);
            int newState;
            if (inEventLoop) {
               newState = 4;
            } else {
               switch (oldState) {
                  case 1:
                  case 2:
                  case 3:
                     newState = 4;
                     break;
                  default:
                     newState = oldState;
                     wakeup = false;
               }
            }

            if (STATE_UPDATER.compareAndSet(this, oldState, newState)) {
               if (oldState == 1) {
                  this.thread.start();
               }

               if (wakeup) {
                  this.wakeup(inEventLoop);
               }

               return;
            }
         }
      }
   }

   @Override
   public boolean isShuttingDown() {
      return STATE_UPDATER.get(this) >= 3;
   }

   @Override
   public boolean isShutdown() {
      return STATE_UPDATER.get(this) >= 4;
   }

   @Override
   public boolean isTerminated() {
      return STATE_UPDATER.get(this) == 5;
   }

   protected boolean confirmShutdown() {
      if (!this.isShuttingDown()) {
         return false;
      } else if (!this.inEventLoop()) {
         throw new IllegalStateException("must be invoked from an event loop");
      } else {
         this.cancelDelayedTasks();
         if (this.gracefulShutdownStartTime == 0L) {
            this.gracefulShutdownStartTime = ScheduledFutureTask.nanoTime();
         }

         if (!this.runAllTasks() && !this.runShutdownHooks()) {
            long nanoTime = ScheduledFutureTask.nanoTime();
            if (this.isShutdown() || nanoTime - this.gracefulShutdownStartTime > this.gracefulShutdownTimeout) {
               return true;
            } else if (nanoTime - this.lastExecutionTime <= this.gracefulShutdownQuietPeriod) {
               this.wakeup(true);

               try {
                  Thread.sleep(100L);
               } catch (InterruptedException var4) {
               }

               return false;
            } else {
               return true;
            }
         } else if (this.isShutdown()) {
            return true;
         } else {
            this.wakeup(true);
            return false;
         }
      }
   }

   private void cancelDelayedTasks() {
      if (!this.delayedTaskQueue.isEmpty()) {
         ScheduledFutureTask<?>[] delayedTasks = this.delayedTaskQueue.toArray(new ScheduledFutureTask[this.delayedTaskQueue.size()]);

         for (ScheduledFutureTask<?> task : delayedTasks) {
            task.cancel(false);
         }

         this.delayedTaskQueue.clear();
      }
   }

   @Override
   public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
      if (unit == null) {
         throw new NullPointerException("unit");
      } else if (this.inEventLoop()) {
         throw new IllegalStateException("cannot await termination of the current thread");
      } else {
         if (this.threadLock.tryAcquire(timeout, unit)) {
            this.threadLock.release();
         }

         return this.isTerminated();
      }
   }

   @Override
   public void execute(Runnable task) {
      if (task == null) {
         throw new NullPointerException("task");
      } else {
         boolean inEventLoop = this.inEventLoop();
         if (inEventLoop) {
            this.addTask(task);
         } else {
            this.startThread();
            this.addTask(task);
            if (this.isShutdown() && this.removeTask(task)) {
               reject();
            }
         }

         if (!this.addTaskWakesUp && this.wakesUpForTask(task)) {
            this.wakeup(inEventLoop);
         }
      }
   }

   protected boolean wakesUpForTask(Runnable task) {
      return true;
   }

   protected static void reject() {
      throw new RejectedExecutionException("event executor terminated");
   }

   @Override
   public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
      if (command == null) {
         throw new NullPointerException("command");
      } else if (unit == null) {
         throw new NullPointerException("unit");
      } else if (delay < 0L) {
         throw new IllegalArgumentException(String.format("delay: %d (expected: >= 0)", delay));
      } else {
         return this.schedule(new ScheduledFutureTask(this, this.delayedTaskQueue, command, null, ScheduledFutureTask.deadlineNanos(unit.toNanos(delay))));
      }
   }

   @Override
   public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
      if (callable == null) {
         throw new NullPointerException("callable");
      } else if (unit == null) {
         throw new NullPointerException("unit");
      } else if (delay < 0L) {
         throw new IllegalArgumentException(String.format("delay: %d (expected: >= 0)", delay));
      } else {
         return this.schedule(new ScheduledFutureTask<>(this, this.delayedTaskQueue, callable, ScheduledFutureTask.deadlineNanos(unit.toNanos(delay))));
      }
   }

   @Override
   public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
      if (command == null) {
         throw new NullPointerException("command");
      } else if (unit == null) {
         throw new NullPointerException("unit");
      } else if (initialDelay < 0L) {
         throw new IllegalArgumentException(String.format("initialDelay: %d (expected: >= 0)", initialDelay));
      } else if (period <= 0L) {
         throw new IllegalArgumentException(String.format("period: %d (expected: > 0)", period));
      } else {
         return this.schedule(
            new ScheduledFutureTask(
               this,
               this.delayedTaskQueue,
               Executors.callable(command, null),
               ScheduledFutureTask.deadlineNanos(unit.toNanos(initialDelay)),
               unit.toNanos(period)
            )
         );
      }
   }

   @Override
   public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
      if (command == null) {
         throw new NullPointerException("command");
      } else if (unit == null) {
         throw new NullPointerException("unit");
      } else if (initialDelay < 0L) {
         throw new IllegalArgumentException(String.format("initialDelay: %d (expected: >= 0)", initialDelay));
      } else if (delay <= 0L) {
         throw new IllegalArgumentException(String.format("delay: %d (expected: > 0)", delay));
      } else {
         return this.schedule(
            new ScheduledFutureTask(
               this,
               this.delayedTaskQueue,
               Executors.callable(command, null),
               ScheduledFutureTask.deadlineNanos(unit.toNanos(initialDelay)),
               -unit.toNanos(delay)
            )
         );
      }
   }

   private <V> ScheduledFuture<V> schedule(final ScheduledFutureTask<V> task) {
      if (task == null) {
         throw new NullPointerException("task");
      } else {
         if (this.inEventLoop()) {
            this.delayedTaskQueue.add(task);
         } else {
            this.execute(new Runnable() {
               @Override
               public void run() {
                  SingleThreadEventExecutor.this.delayedTaskQueue.add(task);
               }
            });
         }

         return task;
      }
   }

   private void startThread() {
      if (STATE_UPDATER.get(this) == 1 && STATE_UPDATER.compareAndSet(this, 1, 2)) {
         this.delayedTaskQueue
            .add(
               new ScheduledFutureTask(
                  this,
                  this.delayedTaskQueue,
                  Executors.callable(new SingleThreadEventExecutor.PurgeTask(), null),
                  ScheduledFutureTask.deadlineNanos(SCHEDULE_PURGE_INTERVAL),
                  -SCHEDULE_PURGE_INTERVAL
               )
            );
         this.thread.start();
      }
   }

   static {
      AtomicIntegerFieldUpdater<SingleThreadEventExecutor> updater = PlatformDependent.newAtomicIntegerFieldUpdater(SingleThreadEventExecutor.class, "state");
      if (updater == null) {
         updater = AtomicIntegerFieldUpdater.newUpdater(SingleThreadEventExecutor.class, "state");
      }

      STATE_UPDATER = updater;
      SCHEDULE_PURGE_INTERVAL = TimeUnit.SECONDS.toNanos(1L);
   }

   private final class PurgeTask implements Runnable {
      private PurgeTask() {
      }

      @Override
      public void run() {
         Iterator<ScheduledFutureTask<?>> i = SingleThreadEventExecutor.this.delayedTaskQueue.iterator();

         while (i.hasNext()) {
            ScheduledFutureTask<?> task = i.next();
            if (task.isCancelled()) {
               i.remove();
            }
         }
      }
   }
}
