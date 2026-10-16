package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import java.util.concurrent.Callable;
import javax.annotation.Nullable;

public final class Callables {
   private Callables() {
   }

   public static <T> Callable<T> returning(@Nullable final T value) {
      return new Callable<T>() {
         @Override
         public T call() {
            return value;
         }
      };
   }

   static <T> Callable<T> threadRenaming(final Callable<T> callable, final Supplier<String> nameSupplier) {
      Preconditions.checkNotNull(nameSupplier);
      Preconditions.checkNotNull(callable);
      return new Callable<T>() {
         @Override
         public T call() throws Exception {
            Thread currentThread = Thread.currentThread();
            String oldName = currentThread.getName();
            boolean restoreName = Callables.trySetName(nameSupplier.get(), currentThread);

            Object var4;
            try {
               var4 = callable.call();
            } finally {
               if (restoreName) {
                  Callables.trySetName(oldName, currentThread);
               }
            }

            return (T)var4;
         }
      };
   }

   static Runnable threadRenaming(final Runnable task, final Supplier<String> nameSupplier) {
      Preconditions.checkNotNull(nameSupplier);
      Preconditions.checkNotNull(task);
      return new Runnable() {
         @Override
         public void run() {
            Thread currentThread = Thread.currentThread();
            String oldName = currentThread.getName();
            boolean restoreName = Callables.trySetName(nameSupplier.get(), currentThread);

            try {
               task.run();
            } finally {
               if (restoreName) {
                  Callables.trySetName(oldName, currentThread);
               }
            }
         }
      };
   }

   private static boolean trySetName(String threadName, Thread currentThread) {
      try {
         currentThread.setName(threadName);
         return true;
      } catch (SecurityException var3) {
         return false;
      }
   }
}
