package gg.vape.event;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;

public class MethodHandleEventHandlerInvoker implements EventHandlerInvoker {
   private final EventListener listener;
   private final MethodHandle methodHandle;
   private final Class<? extends IEvent> eventType;

   @Override
   public <T extends IEvent> void invoke(T event) {
      try {
         this.methodHandle.invoke((EventListener)this.listener, (IEvent)event);
      } catch (Throwable var3) {
         throw new RuntimeException(var3);
      }
   }

   public MethodHandleEventHandlerInvoker(EventListener listener, Class<? extends IEvent> eventType, Method method) {
      this.listener = listener;
      this.eventType = eventType;

      try {
         this.methodHandle = MethodHandles.lookup().unreflect(method);
      } catch (IllegalAccessException var5) {
         throw new RuntimeException(var5);
      }
   }
}
