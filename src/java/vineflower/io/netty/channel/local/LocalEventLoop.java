package io.netty.channel.local;

import io.netty.channel.SingleThreadEventLoop;
import java.util.concurrent.ThreadFactory;

final class LocalEventLoop extends SingleThreadEventLoop {
   LocalEventLoop(LocalEventLoopGroup parent, ThreadFactory threadFactory) {
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
