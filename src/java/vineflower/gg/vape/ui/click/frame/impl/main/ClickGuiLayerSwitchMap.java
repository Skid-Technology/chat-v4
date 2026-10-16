package gg.vape.ui.click.frame.impl.main;

class ClickGuiLayerSwitchMap {
   static final int[] d = new int[ClickGuiLayer.values().length];

   static {
      try {
         d[ClickGuiLayer.MAIN.ordinal()] = 1;
      } catch (NoSuchFieldError var2) {
      }

      try {
         d[ClickGuiLayer.OVERLAYS.ordinal()] = 2;
      } catch (NoSuchFieldError var1) {
      }
   }
}
