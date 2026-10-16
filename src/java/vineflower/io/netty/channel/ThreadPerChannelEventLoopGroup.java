package io.netty.channel;

import io.netty.util.concurrent.AbstractEventExecutorGroup;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.ReadOnlyIterator;
import java.util.Collections;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class ThreadPerChannelEventLoopGroup extends AbstractEventExecutorGroup implements EventLoopGroup {
   private final Object[] childArgs;
   private final int maxChannels;
   final ThreadFactory threadFactory;
   final Set<ThreadPerChannelEventLoop> activeChildren = Collections.newSetFromMap(PlatformDependent.newConcurrentHashMap());
   final Queue<ThreadPerChannelEventLoop> idleChildren = new ConcurrentLinkedQueue<>();
   private final ChannelException tooManyChannels;
   private volatile boolean shuttingDown;
   private final Promise<?> terminationFuture = new DefaultPromise(GlobalEventExecutor.INSTANCE);
   private final FutureListener<Object> childTerminationListener = new FutureListener<Object>() {
      @Override
      public void operationComplete(Future<Object> future) throws Exception {
         if (ThreadPerChannelEventLoopGroup.this.isTerminated()) {
            ThreadPerChannelEventLoopGroup.this.terminationFuture.trySuccess(null);
         }
      }
   };

   protected ThreadPerChannelEventLoopGroup() {
      this(0);
   }

   protected ThreadPerChannelEventLoopGroup(int maxChannels) {
      this(maxChannels, Executors.defaultThreadFactory());
   }

   protected ThreadPerChannelEventLoopGroup(int maxChannels, ThreadFactory threadFactory, Object... args) {
      if (maxChannels < 0) {
         throw new IllegalArgumentException(String.format("maxChannels: %d (expected: >= 0)", maxChannels));
      } else if (threadFactory == null) {
         throw new NullPointerException("threadFactory");
      } else {
         if (args == null) {
            this.childArgs = EmptyArrays.EMPTY_OBJECTS;
         } else {
            this.childArgs = (Object[])args.clone();
         }

         this.maxChannels = maxChannels;
         this.threadFactory = threadFactory;
         this.tooManyChannels = new ChannelException("too many channels (max: " + maxChannels + ')');
         this.tooManyChannels.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
      }
   }

   protected ThreadPerChannelEventLoop newChild(Object... args) throws Exception {
      return new ThreadPerChannelEventLoop(this);
   }

   @Override
   public Iterator<EventExecutor> iterator() {
      return new ReadOnlyIterator<>(this.activeChildren.iterator());
   }

   @Override
   public EventLoop next() {
      throw new UnsupportedOperationException();
   }

   @Override
   public Future<?> shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit) {
      this.shuttingDown = true;

      for (EventLoop l : this.activeChildren) {
         l.shutdownGracefully(quietPeriod, timeout, unit);
      }

      for (EventLoop l : this.idleChildren) {
         l.shutdownGracefully(quietPeriod, timeout, unit);
      }

      if (this.isTerminated()) {
         this.terminationFuture.trySuccess(null);
      }

      return this.terminationFuture();
   }

   @Override
   public Future<?> terminationFuture() {
      return this.terminationFuture;
   }

   @Deprecated
   @Override
   public void shutdown() {
      this.shuttingDown = true;

      for (EventLoop l : this.activeChildren) {
         l.shutdown();
      }

      for (EventLoop l : this.idleChildren) {
         l.shutdown();
      }

      if (this.isTerminated()) {
         this.terminationFuture.trySuccess(null);
      }
   }

   @Override
   public boolean isShuttingDown() {
      for (EventLoop l : this.activeChildren) {
         if (!l.isShuttingDown()) {
            return false;
         }
      }

      for (EventLoop lx : this.idleChildren) {
         if (!lx.isShuttingDown()) {
            return false;
         }
      }

      return true;
   }

   @Override
   public boolean isShutdown() {
      for (EventLoop l : this.activeChildren) {
         if (!l.isShutdown()) {
            return false;
         }
      }

      for (EventLoop lx : this.idleChildren) {
         if (!lx.isShutdown()) {
            return false;
         }
      }

      return true;
   }

   @Override
   public boolean isTerminated() {
      for (EventLoop l : this.activeChildren) {
         if (!l.isTerminated()) {
            return false;
         }
      }

      for (EventLoop lx : this.idleChildren) {
         if (!lx.isTerminated()) {
            return false;
         }
      }

      return true;
   }

   @Override
   public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
      long deadline = System.nanoTime() + unit.toNanos(timeout);

      long timeLeft;
      for (EventLoop l : this.activeChildren) {
         do {
            timeLeft = deadline - System.nanoTime();
            if (timeLeft <= 0L) {
               return this.isTerminated();
            }
         } while (!l.awaitTermination(timeLeft, TimeUnit.NANOSECONDS));
      }

      for (EventLoop l : this.idleChildren) {
         do {
            timeLeft = deadline - System.nanoTime();
            if (timeLeft <= 0L) {
               return this.isTerminated();
            }
         } while (!l.awaitTermination(timeLeft, TimeUnit.NANOSECONDS));
      }

      return this.isTerminated();
   }

   @Override
   public ChannelFuture register(Channel channel) {
      if (channel == null) {
         throw new NullPointerException("channel");
      } else {
         try {
            EventLoop l = this.nextChild();
            return l.register(channel, new DefaultChannelPromise(channel, l));
         } catch (Throwable var3) {
            return new FailedChannelFuture(channel, GlobalEventExecutor.INSTANCE, var3);
         }
      }
   }

   @Override
   public ChannelFuture register(Channel channel, ChannelPromise promise) {
      if (channel == null) {
         throw new NullPointerException("channel");
      } else {
         try {
            return this.nextChild().register(channel, promise);
         } catch (Throwable var4) {
            promise.setFailure(var4);
            return promise;
         }
      }
   }

   private EventLoop nextChild() throws Exception {
      if (this.shuttingDown) {
         throw new RejectedExecutionException("shutting down");
      } else {
         ThreadPerChannelEventLoop loop = this.idleChildren.poll();
         if (loop == null) {
            if (this.maxChannels > 0 && this.activeChildren.size() >= this.maxChannels) {
               throw this.tooManyChannels;
            }

            loop = this.newChild(this.childArgs);
            loop.terminationFuture().addListener(this.childTerminationListener);
         }

         this.activeChildren.add(loop);
         return loop;
      }
   }
}
