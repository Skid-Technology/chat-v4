package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.render.OffscreenRenderContext;
import gg.vape.wrapper.impl.Minecraft;

public class LegacyRenderStringEventRender3DCallback extends InsertedCallbackMarker {
   private static final String b = "hand";

   private static Exception a(Exception exception) {
      return exception;
   }

   public static void call(String string) {
      if (string.equals("hand") && !OffscreenRenderContext.isRenderingOffscreen()) {
         float f = Minecraft.getTimer().renderPartialTicks();
         EventRender3DCallback eventRender3DCallback = new EventRender3DCallback(f);

         try {
            eventRender3DCallback.fire();
         } catch (Exception var4) {
            Vape.logThrowable(var4);
         }
      }
   }
}
