package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.base.Supplier;
import com.google.common.base.Throwables;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Beta
public abstract class AbstractIdleService implements Service {
   private final Supplier<String> threadNameSupplier = new Supplier<String>() {
      public String get() {
         return AbstractIdleService.this.serviceName() + " " + AbstractIdleService.this.state();
      }
   };
   private final Service delegate = new AbstractService() {
      @Override
      protected final void doStart() {
         MoreExecutors.renamingDecorator(AbstractIdleService.this.executor(), AbstractIdleService.this.threadNameSupplier).execute(new Runnable() {
            @Override
            public void run() {
               try {
                  AbstractIdleService.this.startUp();
                  notifyStarted();
               } catch (Throwable var2) {
                  notifyFailed(var2);
                  throw Throwables.propagate(var2);
               }
            }
         });
      }

      @Override
      protected final void doStop() {
         MoreExecutors.renamingDecorator(AbstractIdleService.this.executor(), AbstractIdleService.this.threadNameSupplier).execute(new Runnable() {
            @Override
            public void run() {
               try {
                  AbstractIdleService.this.shutDown();
                  notifyStopped();
               } catch (Throwable var2) {
                  notifyFailed(var2);
                  throw Throwables.propagate(var2);
               }
            }
         });
      }
   };

   protected AbstractIdleService() {
   }

   protected abstract void startUp() throws Exception;

   protected abstract void shutDown() throws Exception;

   protected Executor executor() {
      return new Executor() {
         @Override
         public void execute(Runnable command) {
            MoreExecutors.newThread(AbstractIdleService.this.threadNameSupplier.get(), command).start();
         }
      };
   }

   @Override
   public String toString() {
      return this.serviceName() + " [" + this.state() + "]";
   }

   @Override
   public final boolean isRunning() {
      return this.delegate.isRunning();
   }

   @Override
   public final Service.State state() {
      return this.delegate.state();
   }

   @Override
   public final void addListener(Service.Listener listener, Executor executor) {
      this.delegate.addListener(listener, executor);
   }

   @Override
   public final Throwable failureCause() {
      return this.delegate.failureCause();
   }

   @Override
   public final Service startAsync() {
      this.delegate.startAsync();
      return this;
   }

   @Override
   public final Service stopAsync() {
      this.delegate.stopAsync();
      return this;
   }

   @Override
   public final void awaitRunning() {
      this.delegate.awaitRunning();
   }

   @Override
   public final void awaitRunning(long timeout, TimeUnit unit) throws TimeoutException {
      this.delegate.awaitRunning(timeout, unit);
   }

   @Override
   public final void awaitTerminated() {
      this.delegate.awaitTerminated();
   }

   @Override
   public final void awaitTerminated(long timeout, TimeUnit unit) throws TimeoutException {
      this.delegate.awaitTerminated(timeout, unit);
   }

   protected String serviceName() {
      return this.getClass().getSimpleName();
   }
}
