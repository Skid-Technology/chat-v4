package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.google.common.base.Stopwatch;
import com.google.common.base.Supplier;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Multiset;
import com.google.common.collect.Ordering;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.concurrent.GuardedBy;

@Beta
public final class ServiceManager {
   private static final Logger logger = Logger.getLogger(ServiceManager.class.getName());
   private static final ListenerCallQueue.Callback<ServiceManager.Listener> HEALTHY_CALLBACK = new ListenerCallQueue.Callback<ServiceManager.Listener>(
      "healthy()"
   ) {
      void call(ServiceManager.Listener listener) {
         listener.healthy();
      }
   };
   private static final ListenerCallQueue.Callback<ServiceManager.Listener> STOPPED_CALLBACK = new ListenerCallQueue.Callback<ServiceManager.Listener>(
      "stopped()"
   ) {
      void call(ServiceManager.Listener listener) {
         listener.stopped();
      }
   };
   private final ServiceManager.ServiceManagerState state;
   private final ImmutableList<Service> services;

   public ServiceManager(Iterable<? extends Service> services) {
      ImmutableList<Service> copy = ImmutableList.copyOf(services);
      if (copy.isEmpty()) {
         logger.log(
            Level.WARNING,
            "ServiceManager configured with no services.  Is your application configured properly?",
            (Throwable)(new ServiceManager.EmptyServiceManagerWarning())
         );
         copy = ImmutableList.of(new ServiceManager.NoOpService());
      }

      this.state = new ServiceManager.ServiceManagerState(copy);
      this.services = copy;
      WeakReference<ServiceManager.ServiceManagerState> stateReference = new WeakReference<>(this.state);
      Executor sameThreadExecutor = MoreExecutors.sameThreadExecutor();

      for (Service service : copy) {
         service.addListener(new ServiceManager.ServiceListener(service, stateReference), sameThreadExecutor);
         Preconditions.checkArgument(service.state() == Service.State.NEW, "Can only manage NEW services, %s", service);
      }

      this.state.markReady();
   }

   public void addListener(ServiceManager.Listener listener, Executor executor) {
      this.state.addListener(listener, executor);
   }

   public void addListener(ServiceManager.Listener listener) {
      this.state.addListener(listener, MoreExecutors.sameThreadExecutor());
   }

   public ServiceManager startAsync() {
      for (Service service : this.services) {
         Service.State state = service.state();
         Preconditions.checkState(state == Service.State.NEW, "Service %s is %s, cannot start it.", service, state);
      }

      for (Service service : this.services) {
         try {
            service.startAsync();
         } catch (IllegalStateException var4) {
            logger.log(Level.WARNING, "Unable to start Service " + service, (Throwable)var4);
         }
      }

      return this;
   }

   public void awaitHealthy() {
      this.state.awaitHealthy();
   }

   public void awaitHealthy(long timeout, TimeUnit unit) throws TimeoutException {
      this.state.awaitHealthy(timeout, unit);
   }

   public ServiceManager stopAsync() {
      for (Service service : this.services) {
         service.stopAsync();
      }

      return this;
   }

   public void awaitStopped() {
      this.state.awaitStopped();
   }

   public void awaitStopped(long timeout, TimeUnit unit) throws TimeoutException {
      this.state.awaitStopped(timeout, unit);
   }

   public boolean isHealthy() {
      for (Service service : this.services) {
         if (!service.isRunning()) {
            return false;
         }
      }

      return true;
   }

   public ImmutableMultimap<Service.State, Service> servicesByState() {
      return this.state.servicesByState();
   }

   public ImmutableMap<Service, Long> startupTimes() {
      return this.state.startupTimes();
   }

   @Override
   public String toString() {
      return Objects.toStringHelper(ServiceManager.class)
         .add("services", Collections2.filter(this.services, Predicates.not(Predicates.instanceOf(ServiceManager.NoOpService.class))))
         .toString();
   }

   private static final class EmptyServiceManagerWarning extends Throwable {
      private EmptyServiceManagerWarning() {
      }
   }

   @Beta
   public abstract static class Listener {
      public void healthy() {
      }

      public void stopped() {
      }

      public void failure(Service service) {
      }
   }

   private static final class NoOpService extends AbstractService {
      private NoOpService() {
      }

      @Override
      protected void doStart() {
         this.notifyStarted();
      }

      @Override
      protected void doStop() {
         this.notifyStopped();
      }
   }

   private static final class ServiceListener extends Service.Listener {
      final Service service;
      final WeakReference<ServiceManager.ServiceManagerState> state;

      ServiceListener(Service service, WeakReference<ServiceManager.ServiceManagerState> state) {
         this.service = service;
         this.state = state;
      }

      @Override
      public void starting() {
         ServiceManager.ServiceManagerState state = this.state.get();
         if (state != null) {
            state.transitionService(this.service, Service.State.NEW, Service.State.STARTING);
            if (!(this.service instanceof ServiceManager.NoOpService)) {
               ServiceManager.logger.log(Level.FINE, "Starting {0}.", this.service);
            }
         }
      }

      @Override
      public void running() {
         ServiceManager.ServiceManagerState state = this.state.get();
         if (state != null) {
            state.transitionService(this.service, Service.State.STARTING, Service.State.RUNNING);
         }
      }

      @Override
      public void stopping(Service.State from) {
         ServiceManager.ServiceManagerState state = this.state.get();
         if (state != null) {
            state.transitionService(this.service, from, Service.State.STOPPING);
         }
      }

      @Override
      public void terminated(Service.State from) {
         ServiceManager.ServiceManagerState state = this.state.get();
         if (state != null) {
            if (!(this.service instanceof ServiceManager.NoOpService)) {
               ServiceManager.logger.log(Level.FINE, "Service {0} has terminated. Previous state was: {1}", new Object[]{this.service, from});
            }

            state.transitionService(this.service, from, Service.State.TERMINATED);
         }
      }

      @Override
      public void failed(Service.State from, Throwable failure) {
         ServiceManager.ServiceManagerState state = this.state.get();
         if (state != null) {
            if (!(this.service instanceof ServiceManager.NoOpService)) {
               ServiceManager.logger.log(Level.SEVERE, "Service " + this.service + " has failed in the " + from + " state.", failure);
            }

            state.transitionService(this.service, from, Service.State.FAILED);
         }
      }
   }

   private static final class ServiceManagerState {
      final Monitor monitor = new Monitor();
      @GuardedBy("monitor")
      final SetMultimap<Service.State, Service> servicesByState = Multimaps.newSetMultimap(new EnumMap<>(Service.State.class), new Supplier<Set<Service>>() {
         public Set<Service> get() {
            return Sets.newLinkedHashSet();
         }
      });
      @GuardedBy("monitor")
      final Multiset<Service.State> states = this.servicesByState.keys();
      @GuardedBy("monitor")
      final Map<Service, Stopwatch> startupTimers = Maps.newIdentityHashMap();
      @GuardedBy("monitor")
      boolean ready;
      @GuardedBy("monitor")
      boolean transitioned;
      final int numberOfServices;
      final Monitor.Guard awaitHealthGuard = new Monitor.Guard(this.monitor) {
         @Override
         public boolean isSatisfied() {
            return ServiceManagerState.this.states.count(Service.State.RUNNING) == ServiceManagerState.this.numberOfServices
               || ServiceManagerState.this.states.contains(Service.State.STOPPING)
               || ServiceManagerState.this.states.contains(Service.State.TERMINATED)
               || ServiceManagerState.this.states.contains(Service.State.FAILED);
         }
      };
      final Monitor.Guard stoppedGuard = new Monitor.Guard(this.monitor) {
         @Override
         public boolean isSatisfied() {
            return ServiceManagerState.this.states.count(Service.State.TERMINATED) + ServiceManagerState.this.states.count(Service.State.FAILED)
               == ServiceManagerState.this.numberOfServices;
         }
      };
      @GuardedBy("monitor")
      final List<ListenerCallQueue<ServiceManager.Listener>> listeners = Collections.synchronizedList(new ArrayList<>());

      ServiceManagerState(ImmutableCollection<Service> services) {
         this.numberOfServices = services.size();
         this.servicesByState.putAll(Service.State.NEW, services);

         for (Service service : services) {
            this.startupTimers.put(service, Stopwatch.createUnstarted());
         }
      }

      void markReady() {
         this.monitor.enter();

         try {
            if (this.transitioned) {
               List<Service> servicesInBadStates = Lists.newArrayList();

               for (Service service : this.servicesByState().values()) {
                  if (service.state() != Service.State.NEW) {
                     servicesInBadStates.add(service);
                  }
               }

               throw new IllegalArgumentException(
                  "Services started transitioning asynchronously before the ServiceManager was constructed: " + servicesInBadStates
               );
            }

            this.ready = true;
         } finally {
            this.monitor.leave();
         }
      }

      void addListener(ServiceManager.Listener listener, Executor executor) {
         Preconditions.checkNotNull(listener, "listener");
         Preconditions.checkNotNull(executor, "executor");
         this.monitor.enter();

         try {
            if (!this.stoppedGuard.isSatisfied()) {
               this.listeners.add(new ListenerCallQueue<>(listener, executor));
            }
         } finally {
            this.monitor.leave();
         }
      }

      void awaitHealthy() {
         this.monitor.enterWhenUninterruptibly(this.awaitHealthGuard);

         try {
            this.checkHealthy();
         } finally {
            this.monitor.leave();
         }
      }

      void awaitHealthy(long timeout, TimeUnit unit) throws TimeoutException {
         this.monitor.enter();

         try {
            if (!this.monitor.waitForUninterruptibly(this.awaitHealthGuard, timeout, unit)) {
               throw new TimeoutException(
                  "Timeout waiting for the services to become healthy. The following services have not started: "
                     + Multimaps.<Service.State, Service>filterKeys(
                        this.servicesByState, Predicates.in(ImmutableSet.of(Service.State.NEW, Service.State.STARTING))
                     )
               );
            }

            this.checkHealthy();
         } finally {
            this.monitor.leave();
         }
      }

      void awaitStopped() {
         this.monitor.enterWhenUninterruptibly(this.stoppedGuard);
         this.monitor.leave();
      }

      void awaitStopped(long timeout, TimeUnit unit) throws TimeoutException {
         this.monitor.enter();

         try {
            if (!this.monitor.waitForUninterruptibly(this.stoppedGuard, timeout, unit)) {
               throw new TimeoutException(
                  "Timeout waiting for the services to stop. The following services have not stopped: "
                     + Multimaps.<Service.State, Service>filterKeys(
                        this.servicesByState, Predicates.not(Predicates.in(ImmutableSet.of(Service.State.TERMINATED, Service.State.FAILED)))
                     )
               );
            }
         } finally {
            this.monitor.leave();
         }
      }

      ImmutableMultimap<Service.State, Service> servicesByState() {
         ImmutableSetMultimap.Builder<Service.State, Service> builder = ImmutableSetMultimap.builder();
         this.monitor.enter();

         try {
            for (Entry<Service.State, Service> entry : this.servicesByState.entries()) {
               if (!(entry.getValue() instanceof ServiceManager.NoOpService)) {
                  builder.put(entry.getKey(), entry.getValue());
               }
            }
         } finally {
            this.monitor.leave();
         }

         return builder.build();
      }

      ImmutableMap<Service, Long> startupTimes() {
         this.monitor.enter();

         List<Entry<Service, Long>> loadTimes;
         try {
            loadTimes = Lists.newArrayListWithCapacity(this.states.size() - this.states.count(Service.State.NEW) + this.states.count(Service.State.STARTING));

            for (Entry<Service, Stopwatch> entry : this.startupTimers.entrySet()) {
               Service service = entry.getKey();
               Stopwatch stopWatch = entry.getValue();
               if (!stopWatch.isRunning()
                  && !this.servicesByState.containsEntry(Service.State.NEW, service)
                  && !(service instanceof ServiceManager.NoOpService)) {
                  loadTimes.add(Maps.immutableEntry(service, stopWatch.elapsed(TimeUnit.MILLISECONDS)));
               }
            }
         } finally {
            this.monitor.leave();
         }

         Collections.sort(loadTimes, Ordering.<Long>natural().onResultOf(new Function<Entry<Service, Long>, Long>() {
            public Long apply(Entry<Service, Long> input) {
               return input.getValue();
            }
         }));
         ImmutableMap.Builder var9 = ImmutableMap.builder();

         for (Entry<Service, Long> entryx : loadTimes) {
            var9.put(entryx);
         }

         return var9.build();
      }

      void transitionService(Service service, Service.State from, Service.State to) {
         Preconditions.checkNotNull(service);
         Preconditions.checkArgument(from != to);
         this.monitor.enter();

         try {
            this.transitioned = true;
            if (!this.ready) {
               return;
            }

            Preconditions.checkState(this.servicesByState.remove(from, service), "Service %s not at the expected location in the state map %s", service, from);
            Preconditions.checkState(this.servicesByState.put(to, service), "Service %s in the state map unexpectedly at %s", service, to);
            Stopwatch stopwatch = this.startupTimers.get(service);
            if (from == Service.State.NEW) {
               stopwatch.start();
            }

            if (to.compareTo(Service.State.RUNNING) >= 0 && stopwatch.isRunning()) {
               stopwatch.stop();
               if (!(service instanceof ServiceManager.NoOpService)) {
                  ServiceManager.logger.log(Level.FINE, "Started {0} in {1}.", new Object[]{service, stopwatch});
               }
            }

            if (to == Service.State.FAILED) {
               this.fireFailedListeners(service);
            }

            if (this.states.count(Service.State.RUNNING) == this.numberOfServices) {
               this.fireHealthyListeners();
            } else if (this.states.count(Service.State.TERMINATED) + this.states.count(Service.State.FAILED) == this.numberOfServices) {
               this.fireStoppedListeners();
            }
         } finally {
            this.monitor.leave();
            this.executeListeners();
         }
      }

      @GuardedBy("monitor")
      void fireStoppedListeners() {
         ServiceManager.STOPPED_CALLBACK.enqueueOn(this.listeners);
      }

      @GuardedBy("monitor")
      void fireHealthyListeners() {
         ServiceManager.HEALTHY_CALLBACK.enqueueOn(this.listeners);
      }

      @GuardedBy("monitor")
      void fireFailedListeners(final Service service) {
         (new ListenerCallQueue.Callback<ServiceManager.Listener>("failed({service=" + service + "})") {
            void call(ServiceManager.Listener listener) {
               listener.failure(service);
            }
         }).enqueueOn(this.listeners);
      }

      void executeListeners() {
         Preconditions.checkState(!this.monitor.isOccupiedByCurrentThread(), "It is incorrect to execute listeners with the monitor held.");

         for (int i = 0; i < this.listeners.size(); i++) {
            this.listeners.get(i).execute();
         }
      }

      @GuardedBy("monitor")
      void checkHealthy() {
         if (this.states.count(Service.State.RUNNING) != this.numberOfServices) {
            throw new IllegalStateException(
               "Expected to be healthy after starting. The following services are not running: "
                  + Multimaps.<Service.State, Service>filterKeys(this.servicesByState, Predicates.not(Predicates.equalTo(Service.State.RUNNING)))
            );
         }
      }
   }
}
