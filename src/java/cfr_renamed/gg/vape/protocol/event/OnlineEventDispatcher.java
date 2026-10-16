/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.event;

import gg.vape.protocol.event.OnlineEvent;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class OnlineEventDispatcher {
    private final Map<Class<? extends OnlineEvent>, List<Consumer<OnlineEvent>>> var_java_util_Map_java_lang_Class___extends_gg_vape_protocol_event_OnlineEvent_,_java_util_List_java_util_function_Consumer_gg_vape_protocol_event_OnlineEvent____E = new LinkedHashMap<Class<? extends OnlineEvent>, List<Consumer<OnlineEvent>>>();
    final public static OnlineEventDispatcher var_gg_vape_protocol_event_OnlineEventDispatcher_O;

    public <T extends OnlineEvent> void G(T t) {
        List<Consumer<OnlineEvent>> list = this.var_java_util_Map_java_lang_Class___extends_gg_vape_protocol_event_OnlineEvent_,_java_util_List_java_util_function_Consumer_gg_vape_protocol_event_OnlineEvent____E.get(t.getClass());
        if (list == null) {
            return;
        }
        try {
            for (Consumer<OnlineEvent> consumer : list) {
                consumer.accept(t);
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    private static Throwable a(Throwable throwable) {
        return throwable;
    }

    public <T extends OnlineEvent> void M(Class<T> clazz, Consumer<T> consumer) {
        this.var_java_util_Map_java_lang_Class___extends_gg_vape_protocol_event_OnlineEvent_,_java_util_List_java_util_function_Consumer_gg_vape_protocol_event_OnlineEvent____E.compute(clazz, (arg_0, arg_1) -> OnlineEventDispatcher.lambda$listen$0(consumer, arg_0, arg_1));
    }

    private static List lambda$listen$0(Consumer consumer, Class clazz, List list) {
        List list2 = list != null ? list : new ArrayList();
        list2.add(consumer);
        return list2;
    }

    static {
        var_gg_vape_protocol_event_OnlineEventDispatcher_O = new OnlineEventDispatcher();
    }
}

