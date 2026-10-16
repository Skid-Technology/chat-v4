package io.netty.util.concurrent;

import java.util.concurrent.ThreadFactory;

public class DefaultEventExecutorGroup extends MultithreadEventExecutorGroup {
   public DefaultEventExecutorGroup(int nThreads) {
      this(nThreads, null);
   }

   public DefaultEventExecutorGroup(int nThreads, ThreadFactory threadFactory) {
      super(nThreads, threadFactory);
   }

   @Override
   protected EventExecutor newChild(ThreadFactory threadFactory, Object... args) throws Exception {
      return new DefaultEventExecutor(this, threadFactory);
   }
}
