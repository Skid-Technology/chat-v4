package gg.vape.event;

import java.lang.reflect.Method;

public interface EventHandlerInvoker {
   <T extends IEvent> void invoke(T var1);

   static EventHandlerInvoker create(EventListener listener, Class<? extends IEvent> eventType, Method method) {
      try {
         return new GeneratedEventHandlerInvoker(listener, eventType, method);
      } catch (Throwable var4) {
         return new ReflectiveEventHandlerInvoker(listener, eventType, method);
      }
   }
}
