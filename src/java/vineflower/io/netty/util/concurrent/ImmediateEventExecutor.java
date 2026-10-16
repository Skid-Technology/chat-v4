package io.netty.util.concurrent;

import java.util.concurrent.TimeUnit;

public final class ImmediateEventExecutor extends AbstractEventExecutor {
   public static final ImmediateEventExecutor INSTANCE = new ImmediateEventExecutor();
   private final Future<?> terminationFuture = new FailedFuture(GlobalEventExecutor.INSTANCE, new UnsupportedOperationException());

   private ImmediateEventExecutor() {
   }

   @Override
   public EventExecutorGroup parent() {
      return null;
   }

   @Override
   public boolean inEventLoop() {
      return true;
   }

   @Override
   public boolean inEventLoop(Thread thread) {
      return true;
   }

   @Override
   public Future<?> shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit) {
      return this.terminationFuture();
   }

   @Override
   public Future<?> terminationFuture() {
      return this.terminationFuture;
   }

   @Deprecated
   @Override
   public void shutdown() {
   }

   @Override
   public boolean isShuttingDown() {
      return false;
   }

   @Override
   public boolean isShutdown() {
      return false;
   }

   @Override
   public boolean isTerminated() {
      return false;
   }

   @Override
   public boolean awaitTermination(long timeout, TimeUnit unit) {
      return false;
   }

   @Override
   public void execute(Runnable command) {
      if (command == null) {
         throw new NullPointerException("command");
      } else {
         command.run();
      }
   }

   @Override
   public <V> Promise<V> newPromise() {
      return new ImmediateEventExecutor.ImmediatePromise<>(this);
   }

   @Override
   public <V> ProgressivePromise<V> newProgressivePromise() {
      return new ImmediateEventExecutor.ImmediateProgressivePromise<>(this);
   }

   static class ImmediateProgressivePromise<V> extends DefaultProgressivePromise<V> {
      ImmediateProgressivePromise(EventExecutor executor) {
         super(executor);
      }

      @Override
      protected void checkDeadLock() {
      }
   }

   static class ImmediatePromise<V> extends DefaultPromise<V> {
      ImmediatePromise(EventExecutor executor) {
         super(executor);
      }

      @Override
      protected void checkDeadLock() {
      }
   }
}
