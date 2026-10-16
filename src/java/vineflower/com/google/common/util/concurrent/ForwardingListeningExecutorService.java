package com.google.common.util.concurrent;

import java.util.concurrent.Callable;

public abstract class ForwardingListeningExecutorService extends ForwardingExecutorService implements ListeningExecutorService {
   protected ForwardingListeningExecutorService() {
   }

   protected abstract ListeningExecutorService delegate();

   @Override
   public <T> ListenableFuture<T> submit(Callable<T> task) {
      return this.delegate().submit(task);
   }

   @Override
   public ListenableFuture<?> submit(Runnable task) {
      return this.delegate().submit(task);
   }

   @Override
   public <T> ListenableFuture<T> submit(Runnable task, T result) {
      return this.delegate().submit(task, result);
   }
}
