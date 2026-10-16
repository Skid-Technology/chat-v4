package gg.vape.ui.click.frame.impl.main;

class ClickGuiSectionSwitchMap {
   static final int[] y = new int[ClickGuiSection.values().length];

   static {
      try {
         y[ClickGuiSection.MODULES.ordinal()] = 1;
      } catch (NoSuchFieldError var3) {
      }

      try {
         y[ClickGuiSection.FRIENDS.ordinal()] = 2;
      } catch (NoSuchFieldError var2) {
      }

      try {
         y[ClickGuiSection.PROFILES.ordinal()] = 3;
      } catch (NoSuchFieldError var1) {
      }
   }
}
