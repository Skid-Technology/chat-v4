package gg.vape.event;

import gg.vape.event.impl.EventPreTick;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import org.jetbrains.annotations.UnmodifiableView;

public class EventListenerRegistration {
   private final Map<Class<? extends IEvent>, ArrayList<EventHandlerEntry>> handlersByEventType = new LinkedHashMap<>();
   private static String obfuscationState;
   private final Predicate<IEvent>[] filters;
   private final EventListener listener;

   public EventListenerRegistration(EventListener listener, Predicate<IEvent>... filters) {
      this.listener = listener;
      this.filters = filters;
      this.discoverHandlers();
   }

   public EventListener getListener() {
      return this.listener;
   }

   public @UnmodifiableView Collection<Class<? extends IEvent>> getEventTypes() {
      return this.handlersByEventType.keySet();
   }

   public static String getObfuscationState() {
      return obfuscationState;
   }

   private static Exception identityException(Exception exception) {
      return exception;
   }

   private void discoverHandlers() {
      try {
         for (Method method : this.listener.getClass().getMethods()) {
            EventHandler annotation = method.getDeclaredAnnotation(EventHandler.class);
            Class<?> parameterType;
            if (annotation != null && method.getParameterCount() == 1 && IEvent.class.isAssignableFrom(parameterType = method.getParameterTypes()[0])) {
               Class<? extends IEvent> eventType = parameterType.asSubclass(IEvent.class);
               EventHandlerInvoker invoker = EventHandlerInvoker.create(this.listener, eventType, method);
               EventHandlerEntry handler = new EventHandlerEntry(this, annotation.priority(), annotation.skipCanceled(), invoker);
               List<EventHandlerEntry> handlers = this.handlersByEventType.computeIfAbsent(eventType, EventListenerRegistration::createHandlerList);
               handlers.add(handler);
            }
         }

         for (List<EventHandlerEntry> handlers : this.handlersByEventType.values()) {
            handlers.sort(Comparator.comparing(EventHandlerEntry::getPriority));
         }
      } catch (Exception var11) {
      }
   }

   public static void setObfuscationState(String state) {
      obfuscationState = state;
   }

   public boolean passesFilters(IEvent event) {
      for (Predicate<IEvent> filter : this.filters) {
         if (!filter.test(event)) {
            return false;
         }
      }

      return true;
   }

   public <T extends IEvent> T dispatch(T event, EventPriority priority) {
      boolean isPreTickEvent = event instanceof EventPreTick;
      ArrayList<EventHandlerEntry> handlers = this.handlersByEventType.get(event.getClass());
      if (handlers != null && !handlers.isEmpty()) {
         ICancelableEvent cancelableEvent = event instanceof ICancelableEvent ? (ICancelableEvent)event : null;
         int handlerCount = handlers.size();

         for (int index = 0; index < handlerCount; index++) {
            EventHandlerEntry handler = handlers.get(index);
            if (handler.getPriority().equals(priority) && (!handler.shouldSkipCanceled() || cancelableEvent == null || !cancelableEvent.isCanceled())) {
               handler.invoke(event);
            }
         }

         return event;
      } else {
         return event;
      }
   }

   private static ArrayList<EventHandlerEntry> createHandlerList(Class<? extends IEvent> eventType) {
      return new ArrayList<>();
   }

   static {
      if (getObfuscationState() != null) {
         setObfuscationState("HxeBW");
      }
   }
}
