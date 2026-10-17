/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.event;

import gg.vape.event.EventListener;
import gg.vape.event.GeneratedEventHandlerInvoker;
import gg.vape.event.IEvent;
import gg.vape.event.ReflectiveEventHandlerInvoker;
import java.lang.reflect.Method;

public interface EventHandlerInvoker {
    public <T extends IEvent> void invoke(T var1);

    public static EventHandlerInvoker create(EventListener listener, Class<? extends IEvent> eventType, Method method) {
        try {
            return new GeneratedEventHandlerInvoker(listener, eventType, method);
        }
        catch (Throwable throwable) {
            return new ReflectiveEventHandlerInvoker(listener, eventType, method);
        }
    }
}

