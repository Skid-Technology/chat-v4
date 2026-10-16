package io.netty.channel;

import io.netty.util.concurrent.SingleThreadEventExecutor;
import java.util.concurrent.ThreadFactory;

public abstract class SingleThreadEventLoop extends SingleThreadEventExecutor implements EventLoop {
   protected SingleThreadEventLoop(EventLoopGroup parent, ThreadFactory threadFactory, boolean addTaskWakesUp) {
      super(parent, threadFactory, addTaskWakesUp);
   }

   @Override
   public EventLoopGroup parent() {
      return (EventLoopGroup)super.parent();
   }

   @Override
   public EventLoop next() {
      return (EventLoop)super.next();
   }

   @Override
   public ChannelFuture register(Channel channel) {
      return this.register(channel, new DefaultChannelPromise(channel, this));
   }

   @Override
   public ChannelFuture register(Channel channel, ChannelPromise promise) {
      if (channel == null) {
         throw new NullPointerException("channel");
      } else if (promise == null) {
         throw new NullPointerException("promise");
      } else {
         channel.unsafe().register(this, promise);
         return promise;
      }
   }

   @Override
   protected boolean wakesUpForTask(Runnable task) {
      return !(task instanceof SingleThreadEventLoop.NonWakeupRunnable);
   }

   interface NonWakeupRunnable extends Runnable {
   }
}
