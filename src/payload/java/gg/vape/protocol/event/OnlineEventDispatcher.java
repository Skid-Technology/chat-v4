package gg.vape.protocol.event;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class OnlineEventDispatcher {
   private final Map<Class<? extends OnlineEvent>, List<Consumer<OnlineEvent>>> E = new LinkedHashMap<>();
   public static final OnlineEventDispatcher O = new OnlineEventDispatcher();

   public <T extends OnlineEvent> void G(T t) {
      List<Consumer<OnlineEvent>> list = this.E.get(t.getClass());
      if (list != null) {
         try {
            for (Consumer<OnlineEvent> consumer : list) {
               consumer.accept(t);
            }
         } catch (Throwable var5) {
         }
      }
   }

   private static Throwable a(Throwable throwable) {
      return throwable;
   }

   public <T extends OnlineEvent> void M(Class<T> clazz, Consumer<T> consumer) {
      this.E.compute(clazz, (arg_0, arg_1) -> lambda$listen$0(consumer, arg_0, arg_1));
   }

   private static List lambda$listen$0(Consumer consumer, Class clazz, List list) {
      List list2 = (List)(list != null ? list : new ArrayList());
      list2.add(consumer);
      return list2;
   }
}
