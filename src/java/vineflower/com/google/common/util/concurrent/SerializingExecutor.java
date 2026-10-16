package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.concurrent.GuardedBy;

final class SerializingExecutor implements Executor {
   private static final Logger log = Logger.getLogger(SerializingExecutor.class.getName());
   private final Executor executor;
   @GuardedBy("internalLock")
   private final Queue<Runnable> waitQueue = new ArrayDeque<>();
   @GuardedBy("internalLock")
   private boolean isThreadScheduled = false;
   private final SerializingExecutor.TaskRunner taskRunner = new SerializingExecutor.TaskRunner();
   private final Object internalLock = new Object() {
      @Override
      public String toString() {
         return "SerializingExecutor lock: " + super.toString();
      }
   };

   public SerializingExecutor(Executor executor) {
      Preconditions.checkNotNull(executor, "'executor' must not be null.");
      this.executor = executor;
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void execute(Runnable r) {
      Preconditions.checkNotNull(r, "'r' must not be null.");
      boolean scheduleTaskRunner = false;
      synchronized (this.internalLock) {
         this.waitQueue.add(r);
         if (!this.isThreadScheduled) {
            this.isThreadScheduled = true;
            scheduleTaskRunner = true;
         }
      }

      if (scheduleTaskRunner) {
         boolean threw = true;
         boolean var12 = false /* VF: Semaphore variable */;

         try {
            var12 = true;
            this.executor.execute(this.taskRunner);
            threw = false;
            var12 = false;
         } finally {
            if (var12) {
               synchronized (this.internalLock) {
                  this.isThreadScheduled = false;
               }
            }
         }
      }
   }

   private class TaskRunner implements Runnable {
      private TaskRunner() {
      }

      @Override
      public void run() {
         boolean stillRunning = true;

         try {
            while (true) {
               Preconditions.checkState(SerializingExecutor.this.isThreadScheduled);
               Runnable nextToRun;
               synchronized (SerializingExecutor.this.internalLock) {
                  nextToRun = SerializingExecutor.this.waitQueue.poll();
                  if (nextToRun == null) {
                     SerializingExecutor.this.isThreadScheduled = false;
                     stillRunning = false;
                     return;
                  }
               }

               try {
                  nextToRun.run();
               } catch (RuntimeException var14) {
                  SerializingExecutor.log.log(Level.SEVERE, "Exception while executing runnable " + nextToRun, (Throwable)var14);
               }
            }
         } finally {
            if (stillRunning) {
               synchronized (SerializingExecutor.this.internalLock) {
                  SerializingExecutor.this.isThreadScheduled = false;
               }
            }
         }
      }
   }
}
