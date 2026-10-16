package gg.vape.mapping;

import gg.vape.event.impl.EventPostTick;

public class EventPostTickCallback extends InsertedTickCallbackBase {
   public static void call() {
      new EventPostTick().fire();
   }
}
