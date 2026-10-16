package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.Immutable;

@Beta
public abstract class AbstractService implements Service {
   private static final ListenerCallQueue.Callback<Service.Listener> STARTING_CALLBACK = new ListenerCallQueue.Callback<Service.Listener>("starting()") {
      void call(Service.Listener listener) {
         listener.starting();
      }
   };
   private static final ListenerCallQueue.Callback<Service.Listener> RUNNING_CALLBACK = new ListenerCallQueue.Callback<Service.Listener>("running()") {
      void call(Service.Listener listener) {
         listener.running();
      }
   };
   private static final ListenerCallQueue.Callback<Service.Listener> STOPPING_FROM_STARTING_CALLBACK = stoppingCallback(Service.State.STARTING);
   private static final ListenerCallQueue.Callback<Service.Listener> STOPPING_FROM_RUNNING_CALLBACK = stoppingCallback(Service.State.RUNNING);
   private static final ListenerCallQueue.Callback<Service.Listener> TERMINATED_FROM_NEW_CALLBACK = terminatedCallback(Service.State.NEW);
   private static final ListenerCallQueue.Callback<Service.Listener> TERMINATED_FROM_RUNNING_CALLBACK = terminatedCallback(Service.State.RUNNING);
   private static final ListenerCallQueue.Callback<Service.Listener> TERMINATED_FROM_STOPPING_CALLBACK = terminatedCallback(Service.State.STOPPING);
   private final Monitor monitor = new Monitor();
   private final Monitor.Guard isStartable = new Monitor.Guard(this.monitor) {
      @Override
      public boolean isSatisfied() {
         return AbstractService.this.state() == Service.State.NEW;
      }
   };
   private final Monitor.Guard isStoppable = new Monitor.Guard(this.monitor) {
      @Override
      public boolean isSatisfied() {
         return AbstractService.this.state().compareTo(Service.State.RUNNING) <= 0;
      }
   };
   private final Monitor.Guard hasReachedRunning = new Monitor.Guard(this.monitor) {
      @Override
      public boolean isSatisfied() {
         return AbstractService.this.state().compareTo(Service.State.RUNNING) >= 0;
      }
   };
   private final Monitor.Guard isStopped = new Monitor.Guard(this.monitor) {
      @Override
      public boolean isSatisfied() {
         return AbstractService.this.state().isTerminal();
      }
   };
   @GuardedBy("monitor")
   private final List<ListenerCallQueue<Service.Listener>> listeners = Collections.synchronizedList(new ArrayList<>());
   @GuardedBy("monitor")
   private volatile AbstractService.StateSnapshot snapshot = new AbstractService.StateSnapshot(Service.State.NEW);

   private static ListenerCallQueue.Callback<Service.Listener> terminatedCallback(final Service.State from) {
      return new ListenerCallQueue.Callback<Service.Listener>("terminated({from = " + from + "})") {
         void call(Service.Listener listener) {
            listener.terminated(from);
         }
      };
   }

   private static ListenerCallQueue.Callback<Service.Listener> stoppingCallback(final Service.State from) {
      return new ListenerCallQueue.Callback<Service.Listener>("stopping({from = " + from + "})") {
         void call(Service.Listener listener) {
            listener.stopping(from);
         }
      };
   }

   protected AbstractService() {
   }

   protected abstract void doStart();

   protected abstract void doStop();

   @Override
   public final Service startAsync() {
      if (this.monitor.enterIf(this.isStartable)) {
         try {
            this.snapshot = new AbstractService.StateSnapshot(Service.State.STARTING);
            this.starting();
            this.doStart();
         } catch (Throwable var5) {
            this.notifyFailed(var5);
         } finally {
            this.monitor.leave();
            this.executeListeners();
         }

         return this;
      } else {
         throw new IllegalStateException("Service " + this + " has already been started");
      }
   }

   @Override
   public final Service stopAsync() {
      if (this.monitor.enterIf(this.isStoppable)) {
         try {
            Service.State previous = this.state();
            switch (previous) {
               case NEW:
                  this.snapshot = new AbstractService.StateSnapshot(Service.State.TERMINATED);
                  this.terminated(Service.State.NEW);
                  break;
               case STARTING:
                  this.snapshot = new AbstractService.StateSnapshot(Service.State.STARTING, true, null);
                  this.stopping(Service.State.STARTING);
                  break;
               case RUNNING:
                  this.snapshot = new AbstractService.StateSnapshot(Service.State.STOPPING);
                  this.stopping(Service.State.RUNNING);
                  this.doStop();
                  break;
               case STOPPING:
               case TERMINATED:
               case FAILED:
                  throw new AssertionError("isStoppable is incorrectly implemented, saw: " + previous);
               default:
                  throw new AssertionError("Unexpected state: " + previous);
            }
         } catch (Throwable var5) {
            this.notifyFailed(var5);
         } finally {
            this.monitor.leave();
            this.executeListeners();
         }
      }

      return this;
   }

   @Override
   public final void awaitRunning() {
      this.monitor.enterWhenUninterruptibly(this.hasReachedRunning);

      try {
         this.checkCurrentState(Service.State.RUNNING);
      } finally {
         this.monitor.leave();
      }
   }

   @Override
   public final void awaitRunning(long timeout, TimeUnit unit) throws TimeoutException {
      if (this.monitor.enterWhenUninterruptibly(this.hasReachedRunning, timeout, unit)) {
         try {
            this.checkCurrentState(Service.State.RUNNING);
         } finally {
            this.monitor.leave();
         }
      } else {
         throw new TimeoutException("Timed out waiting for " + this + " to reach the RUNNING state. " + "Current state: " + this.state());
      }
   }

   @Override
   public final void awaitTerminated() {
      this.monitor.enterWhenUninterruptibly(this.isStopped);

      try {
         this.checkCurrentState(Service.State.TERMINATED);
      } finally {
         this.monitor.leave();
      }
   }

   @Override
   public final void awaitTerminated(long timeout, TimeUnit unit) throws TimeoutException {
      if (this.monitor.enterWhenUninterruptibly(this.isStopped, timeout, unit)) {
         try {
            this.checkCurrentState(Service.State.TERMINATED);
         } finally {
            this.monitor.leave();
         }
      } else {
         throw new TimeoutException("Timed out waiting for " + this + " to reach a terminal state. " + "Current state: " + this.state());
      }
   }

   @GuardedBy("monitor")
   private void checkCurrentState(Service.State expected) {
      Service.State actual = this.state();
      if (actual != expected) {
         if (actual == Service.State.FAILED) {
            throw new IllegalStateException("Expected the service to be " + expected + ", but the service has FAILED", this.failureCause());
         } else {
            throw new IllegalStateException("Expected the service to be " + expected + ", but was " + actual);
         }
      }
   }

   protected final void notifyStarted() {
      this.monitor.enter();

      try {
         if (this.snapshot.state != Service.State.STARTING) {
            IllegalStateException failure = new IllegalStateException("Cannot notifyStarted() when the service is " + this.snapshot.state);
            this.notifyFailed(failure);
            throw failure;
         }

         if (this.snapshot.shutdownWhenStartupFinishes) {
            this.snapshot = new AbstractService.StateSnapshot(Service.State.STOPPING);
            this.doStop();
         } else {
            this.snapshot = new AbstractService.StateSnapshot(Service.State.RUNNING);
            this.running();
         }
      } finally {
         this.monitor.leave();
         this.executeListeners();
      }
   }

   protected final void notifyStopped() {
      this.monitor.enter();

      try {
         Service.State previous = this.snapshot.state;
         if (previous != Service.State.STOPPING && previous != Service.State.RUNNING) {
            IllegalStateException failure = new IllegalStateException("Cannot notifyStopped() when the service is " + previous);
            this.notifyFailed(failure);
            throw failure;
         }

         this.snapshot = new AbstractService.StateSnapshot(Service.State.TERMINATED);
         this.terminated(previous);
      } finally {
         this.monitor.leave();
         this.executeListeners();
      }
   }

   protected final void notifyFailed(Throwable cause) {
      Preconditions.checkNotNull(cause);
      this.monitor.enter();

      try {
         Service.State previous = this.state();
         switch (previous) {
            case NEW:
            case TERMINATED:
               throw new IllegalStateException("Failed while in state:" + previous, cause);
            case STARTING:
            case RUNNING:
            case STOPPING:
               this.snapshot = new AbstractService.StateSnapshot(Service.State.FAILED, false, cause);
               this.failed(previous, cause);
            case FAILED:
               break;
            default:
               throw new AssertionError("Unexpected state: " + previous);
         }
      } finally {
         this.monitor.leave();
         this.executeListeners();
      }
   }

   @Override
   public final boolean isRunning() {
      return this.state() == Service.State.RUNNING;
   }

   @Override
   public final Service.State state() {
      return this.snapshot.externalState();
   }

   @Override
   public final Throwable failureCause() {
      return this.snapshot.failureCause();
   }

   @Override
   public final void addListener(Service.Listener listener, Executor executor) {
      Preconditions.checkNotNull(listener, "listener");
      Preconditions.checkNotNull(executor, "executor");
      this.monitor.enter();

      try {
         if (!this.state().isTerminal()) {
            this.listeners.add(new ListenerCallQueue<>(listener, executor));
         }
      } finally {
         this.monitor.leave();
      }
   }

   @Override
   public String toString() {
      return this.getClass().getSimpleName() + " [" + this.state() + "]";
   }

   private void executeListeners() {
      if (!this.monitor.isOccupiedByCurrentThread()) {
         for (int i = 0; i < this.listeners.size(); i++) {
            this.listeners.get(i).execute();
         }
      }
   }

   @GuardedBy("monitor")
   private void starting() {
      STARTING_CALLBACK.enqueueOn(this.listeners);
   }

   @GuardedBy("monitor")
   private void running() {
      RUNNING_CALLBACK.enqueueOn(this.listeners);
   }

   @GuardedBy("monitor")
   private void stopping(Service.State from) {
      if (from == Service.State.STARTING) {
         STOPPING_FROM_STARTING_CALLBACK.enqueueOn(this.listeners);
      } else {
         if (from != Service.State.RUNNING) {
            throw new AssertionError();
         }

         STOPPING_FROM_RUNNING_CALLBACK.enqueueOn(this.listeners);
      }
   }

   @GuardedBy("monitor")
   private void terminated(Service.State from) {
      switch (from) {
         case NEW:
            TERMINATED_FROM_NEW_CALLBACK.enqueueOn(this.listeners);
            break;
         case STARTING:
         case TERMINATED:
         case FAILED:
         default:
            throw new AssertionError();
         case RUNNING:
            TERMINATED_FROM_RUNNING_CALLBACK.enqueueOn(this.listeners);
            break;
         case STOPPING:
            TERMINATED_FROM_STOPPING_CALLBACK.enqueueOn(this.listeners);
      }
   }

   @GuardedBy("monitor")
   private void failed(final Service.State from, final Throwable cause) {
      (new ListenerCallQueue.Callback<Service.Listener>("failed({from = " + from + ", cause = " + cause + "})") {
         void call(Service.Listener listener) {
            listener.failed(from, cause);
         }
      }).enqueueOn(this.listeners);
   }

   @Immutable
   private static final class StateSnapshot {
      final Service.State state;
      final boolean shutdownWhenStartupFinishes;
      @Nullable
      final Throwable failure;

      StateSnapshot(Service.State internalState) {
         this(internalState, false, null);
      }

      StateSnapshot(Service.State internalState, boolean shutdownWhenStartupFinishes, @Nullable Throwable failure) {
         Preconditions.checkArgument(
            !shutdownWhenStartupFinishes || internalState == Service.State.STARTING,
            "shudownWhenStartupFinishes can only be set if state is STARTING. Got %s instead.",
            internalState
         );
         Preconditions.checkArgument(
            !(failure != null ^ internalState == Service.State.FAILED),
            "A failure cause should be set if and only if the state is failed.  Got %s and %s instead.",
            internalState,
            failure
         );
         this.state = internalState;
         this.shutdownWhenStartupFinishes = shutdownWhenStartupFinishes;
         this.failure = failure;
      }

      Service.State externalState() {
         return this.shutdownWhenStartupFinishes && this.state == Service.State.STARTING ? Service.State.STOPPING : this.state;
      }

      Throwable failureCause() {
         Preconditions.checkState(this.state == Service.State.FAILED, "failureCause() is only valid if the service has failed, service is %s", this.state);
         return this.failure;
      }
   }
}
