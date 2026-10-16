package io.netty.util.internal.chmv8;

public class ForkJoinWorkerThread extends Thread {
   final ForkJoinPool pool;
   final ForkJoinPool.WorkQueue workQueue;

   protected ForkJoinWorkerThread(ForkJoinPool pool) {
      super("aForkJoinWorkerThread");
      this.pool = pool;
      this.workQueue = pool.registerWorker(this);
   }

   public ForkJoinPool getPool() {
      return this.pool;
   }

   public int getPoolIndex() {
      return this.workQueue.poolIndex >>> 1;
   }

   protected void onStart() {
   }

   protected void onTermination(Throwable exception) {
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void run() {
      Throwable exception = null;
      boolean var40 = false /* VF: Semaphore variable */;

      label193: {
         try {
            var40 = true;
            this.onStart();
            this.pool.runWorker(this.workQueue);
            var40 = false;
            break label193;
         } catch (Throwable var47) {
            exception = var47;
            var40 = false;
         } finally {
            if (var40) {
               try {
                  this.onTermination(exception);
               } catch (Throwable var41) {
                  exception = var41;
               } finally {
                  this.pool.deregisterWorker(this, exception);
               }
            }
         }

         try {
            try {
               this.onTermination(exception);
            } catch (Throwable var45) {
               if (exception == null) {
                  exception = var45;
                  return;
               }
            }

            return;
         } finally {
            this.pool.deregisterWorker(this, exception);
         }
      }

      try {
         this.onTermination(exception);
      } catch (Throwable var43) {
         exception = var43;
      } finally {
         this.pool.deregisterWorker(this, exception);
      }
   }
}
