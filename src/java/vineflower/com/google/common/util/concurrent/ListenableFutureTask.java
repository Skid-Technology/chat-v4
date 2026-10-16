package com.google.common.util.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import javax.annotation.Nullable;

public class ListenableFutureTask<V> extends FutureTask<V> implements ListenableFuture<V> {
   private final ExecutionList executionList = new ExecutionList();

   public static <V> ListenableFutureTask<V> create(Callable<V> callable) {
      return new ListenableFutureTask<>(callable);
   }

   public static <V> ListenableFutureTask<V> create(Runnable runnable, @Nullable V result) {
      return new ListenableFutureTask<>(runnable, result);
   }

   ListenableFutureTask(Callable<V> callable) {
      super(callable);
   }

   ListenableFutureTask(Runnable runnable, @Nullable V result) {
      super(runnable, result);
   }

   @Override
   public void addListener(Runnable listener, Executor exec) {
      this.executionList.add(listener, exec);
   }

   @Override
   protected void done() {
      this.executionList.execute();
   }
}
