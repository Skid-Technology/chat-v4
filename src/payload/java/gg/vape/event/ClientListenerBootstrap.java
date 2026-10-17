package gg.vape.event;

import gg.vape.event.forge.ForgeClientChatReceivedEvent;
import gg.vape.event.listener.ClientListenerBootstrapEventListener;
import java.util.Arrays;
import java.util.List;

public class ClientListenerBootstrap {
   private final List<EventListener> listeners = Arrays.asList(new ForgeClientChatReceivedEvent(), new ClientListenerBootstrapEventListener());

   public void registerListeners() {
      for (EventListener listener : this.listeners) {
         EventBus.getInstance().registerListener(listener);
      }
   }

   public void unregisterListeners() {
      for (EventListener listener : this.listeners) {
         EventBus.getInstance().unregisterListener(listener);
      }
   }
}
