package gg.vape.mapping;

import gg.vape.event.impl.EventPreTick;

public class EventPreTickCallback extends InsertedTickCallbackBase {
   public static void call() {
      new EventPreTick().fire();
   }
}
