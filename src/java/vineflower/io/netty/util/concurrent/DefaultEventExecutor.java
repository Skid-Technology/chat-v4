package io.netty.util.concurrent;

import java.util.concurrent.ThreadFactory;

final class DefaultEventExecutor extends SingleThreadEventExecutor {
   DefaultEventExecutor(DefaultEventExecutorGroup parent, ThreadFactory threadFactory) {
      super(parent, threadFactory, true);
   }

   @Override
   protected void run() {
      do {
         Runnable task = this.takeTask();
         if (task != null) {
            task.run();
            this.updateLastExecutionTime();
         }
      } while (!this.confirmShutdown());
   }
}
