package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;

@Beta
public final class JdkFutureAdapters {
   public static <V> ListenableFuture<V> listenInPoolThread(Future<V> future) {
      return (ListenableFuture<V>)(future instanceof ListenableFuture ? (ListenableFuture)future : new JdkFutureAdapters.ListenableFutureAdapter<>(future));
   }

   public static <V> ListenableFuture<V> listenInPoolThread(Future<V> future, Executor executor) {
      Preconditions.checkNotNull(executor);
      return (ListenableFuture<V>)(future instanceof ListenableFuture
         ? (ListenableFuture)future
         : new JdkFutureAdapters.ListenableFutureAdapter<>(future, executor));
   }

   private JdkFutureAdapters() {
   }

   private static class ListenableFutureAdapter<V> extends ForwardingFuture<V> implements ListenableFuture<V> {
      private static final ThreadFactory threadFactory = new ThreadFactoryBuilder().setDaemon(true).setNameFormat("ListenableFutureAdapter-thread-%d").build();
      private static final Executor defaultAdapterExecutor = Executors.newCachedThreadPool(threadFactory);
      private final Executor adapterExecutor;
      private final ExecutionList executionList = new ExecutionList();
      private final AtomicBoolean hasListeners = new AtomicBoolean(false);
      private final Future<V> delegate;

      ListenableFutureAdapter(Future<V> delegate) {
         this(delegate, defaultAdapterExecutor);
      }

      ListenableFutureAdapter(Future<V> delegate, Executor adapterExecutor) {
         this.delegate = Preconditions.checkNotNull(delegate);
         this.adapterExecutor = Preconditions.checkNotNull(adapterExecutor);
      }

      @Override
      protected Future<V> delegate() {
         return this.delegate;
      }

      @Override
      public void addListener(Runnable listener, Executor exec) {
         this.executionList.add(listener, exec);
         if (this.hasListeners.compareAndSet(false, true)) {
            if (this.delegate.isDone()) {
               this.executionList.execute();
               return;
            }

            this.adapterExecutor.execute(new Runnable() {
               @Override
               public void run() {
                  try {
                     Uninterruptibles.getUninterruptibly(ListenableFutureAdapter.this.delegate);
                  } catch (Error var2) {
                     throw var2;
                  } catch (Throwable var3) {
                  }

                  ListenableFutureAdapter.this.executionList.execute();
               }
            });
         }
      }
   }
}
