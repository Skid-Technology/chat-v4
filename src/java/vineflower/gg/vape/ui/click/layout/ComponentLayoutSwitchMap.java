package gg.vape.ui.click.layout;

class ComponentLayoutSwitchMap {
   static final int[] q = new int[LayoutDock.values().length];
   static final int[] p;

   static {
      try {
         q[LayoutDock.TOP.ordinal()] = 1;
      } catch (NoSuchFieldError var7) {
      }

      try {
         q[LayoutDock.BOTTOM.ordinal()] = 2;
      } catch (NoSuchFieldError var6) {
      }

      try {
         q[LayoutDock.LEFT.ordinal()] = 3;
      } catch (NoSuchFieldError var5) {
      }

      try {
         q[LayoutDock.RIGHT.ordinal()] = 4;
      } catch (NoSuchFieldError var4) {
      }

      p = new int[LayoutAnchor.values().length];

      try {
         p[LayoutAnchor.BOTTOM_RIGHT.ordinal()] = 1;
      } catch (NoSuchFieldError var3) {
      }

      try {
         p[LayoutAnchor.TOP_LEFT.ordinal()] = 2;
      } catch (NoSuchFieldError var2) {
      }

      try {
         p[LayoutAnchor.OFFSET.ordinal()] = 3;
      } catch (NoSuchFieldError var1) {
      }
   }
}
