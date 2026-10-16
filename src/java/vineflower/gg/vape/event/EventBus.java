package gg.vape.event;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class EventBus {
   public static boolean timingEnabled = false;
   private static int obfuscationState;
   private static Method cachedHasListenersMethod;
   private final EventTimingHistory timingHistory;
   private static Method cachedFireMethod;
   private final Map<Class<? extends IEvent>, EventListeners> listenerCountersByEventType = new LinkedHashMap<>();
   private final Map<EventListener, EventListenerRegistration> registrationsByListener = new LinkedHashMap<>();
   private static final EventBus INSTANCE = new EventBus();
   private final Map<Class<? extends IEvent>, ArrayList<EventListenerRegistration>> registrationsByEventType = new LinkedHashMap<>();

   private static Throwable identityThrowable(Throwable throwable) {
      return throwable;
   }

   public static void setObfuscationState(int state) {
      obfuscationState = state;
   }

   public EventTimingHistory getTimingHistory() {
      return this.timingHistory;
   }

   public static Method getHasListenersMethod() {
      if (cachedHasListenersMethod == null) {
         for (Method method : EventListeners.class.getDeclaredMethods()) {
            if (method.getReturnType() == boolean.class && method.getParameterCount() == 0) {
               cachedHasListenersMethod = method;
               break;
            }
         }
      }

      return cachedHasListenersMethod;
   }

   private EventListeners resolveEventListeners(Class<? extends IEvent> eventType) {
      EventListeners eventListeners = this.listenerCountersByEventType.get(eventType);
      if (eventListeners != null) {
         return eventListeners;
      } else {
         try {
            Method method = findEventListenersAccessor(eventType);
            if (method == null) {
               return null;
            } else {
               EventListeners resolvedListeners = (EventListeners)method.invoke(null);
               this.listenerCountersByEventType.put(eventType, resolvedListeners);
               return resolvedListeners;
            }
         } catch (Throwable var5) {
            return null;
         }
      }
   }

   public static int getObfuscationState() {
      return obfuscationState;
   }

   public static int getObfuscationZero() {
      int state = getObfuscationState();
      return 0;
   }

   public Map<Class<? extends IEvent>, ArrayList<EventListenerRegistration>> getRegistrationsByEventType() {
      return this.registrationsByEventType;
   }

   public static Method findEventListenersAccessor(Class<? extends IEvent> eventType) {
      for (Method method : eventType.getDeclaredMethods()) {
         if (method.getReturnType() == EventListeners.class && method.getParameterCount() == 0 && Modifier.isStatic(method.getModifiers())) {
            return method;
         }
      }

      return IEvent.class.isAssignableFrom(eventType.getSuperclass()) ? findEventListenersAccessor(eventType.getSuperclass().asSubclass(IEvent.class)) : null;
   }

   public <T extends IEvent> T post(T event) {
      EventDispatchTrace dispatchTrace = null;
      if (timingEnabled) {
         dispatchTrace = new EventDispatchTrace((Class<? extends IEvent>)event.getClass());
      }

      try {
         ArrayList<EventListenerRegistration> registrations = this.registrationsByEventType.get(event.getClass());
         if (registrations != null && !registrations.isEmpty()) {
            ArrayList<EventListenerRegistration> matchingRegistrations = new ArrayList<>();

            for (int index = 0; index < registrations.size(); index++) {
               EventListenerRegistration registration = registrations.get(index);
               if (registration.passesFilters(event)) {
                  matchingRegistrations.add(registration);
               }
            }

            if (!matchingRegistrations.isEmpty()) {
               for (EventPriority priority : EventPriority.values()) {
                  for (int indexx = 0; indexx < matchingRegistrations.size(); indexx++) {
                     try {
                        EventListenerRegistration registration = matchingRegistrations.get(indexx);
                        EventListenerTiming listenerTiming = null;
                        if (timingEnabled) {
                           listenerTiming = new EventListenerTiming(registration);
                        }

                        registration.dispatch(event, priority);
                        if (timingEnabled) {
                           listenerTiming.finish();
                           dispatchTrace.addListenerTiming(listenerTiming);
                        }
                     } catch (Throwable var12) {
                     }
                  }
               }
            }
         }
      } catch (Throwable var13) {
      }

      if (timingEnabled) {
         dispatchTrace.finish();
         this.timingHistory.addTrace(dispatchTrace);
      }

      return event;
   }

   private static ArrayList<EventListenerRegistration> createRegistrationList(Class<? extends IEvent> eventType) {
      return new ArrayList<>();
   }

   public boolean unregisterListener(EventListener eventListener) {
      if (eventListener == null) {
         return false;
      } else {
         EventListenerRegistration registration = this.registrationsByListener.remove(eventListener);
         if (registration == null) {
            return false;
         } else {
            Collection<Class<? extends IEvent>> eventTypes = registration.getEventTypes();
            if (eventTypes != null && !eventTypes.isEmpty()) {
               for (Class<? extends IEvent> eventType : eventTypes) {
                  List<EventListenerRegistration> registrations = this.registrationsByEventType.get(eventType);
                  if (registrations != null) {
                     registrations.remove(registration);
                     this.resolveEventListeners(eventType).decrementListenerCount();
                  }
               }

               return true;
            } else {
               return false;
            }
         }
      }
   }

   public static EventBus getInstance() {
      return INSTANCE;
   }

   public EventBus() {
      this.timingHistory = new EventTimingHistory();
   }

   @SafeVarargs
   public final void registerListener(EventListener eventListener, Predicate<IEvent>... filters) {
      if (!this.registrationsByListener.containsKey(eventListener)) {
         EventListenerRegistration registration = new EventListenerRegistration(eventListener, filters);
         this.registrationsByListener.put(eventListener, registration);

         for (Class<? extends IEvent> eventType : registration.getEventTypes()) {
            this.registrationsByEventType.computeIfAbsent(eventType, EventBus::createRegistrationList).add(registration);
            this.resolveEventListeners(eventType).incrementListenerCount();
         }
      }
   }

   public static Method getFireMethod(Class<?> eventType) {
      if (cachedFireMethod == null) {
         for (Method method : IEvent.class.getDeclaredMethods()) {
            if (method.getReturnType() == boolean.class && method.getParameterCount() == 0) {
               cachedFireMethod = method;
               break;
            }
         }
      }

      return cachedFireMethod;
   }

   static {
      setObfuscationState(32);
   }
}
