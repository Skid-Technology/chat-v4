package gg.vape.mapping;

import gg.vape.event.impl.EventRender2D;

public class EventRender2DStaticCallback extends InsertedCallbackMarker {
   public static void call() {
      EventRender2D.create();
   }
}
