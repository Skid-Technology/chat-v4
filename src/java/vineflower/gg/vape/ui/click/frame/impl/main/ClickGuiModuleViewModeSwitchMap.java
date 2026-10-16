package gg.vape.ui.click.frame.impl.main;

public class ClickGuiModuleViewModeSwitchMap {
   public static final int[] C = new int[ClickGuiModuleViewMode.values().length];

   ClickGuiModuleViewModeSwitchMap() {
   }

   static {
      try {
         C[ClickGuiModuleViewMode.MACROS.ordinal()] = 1;
      } catch (NoSuchFieldError var3) {
      }

      try {
         C[ClickGuiModuleViewMode.LEGIT.ordinal()] = 2;
      } catch (NoSuchFieldError var2) {
      }

      try {
         C[ClickGuiModuleViewMode.MODULE_CATEGORY.ordinal()] = 3;
      } catch (NoSuchFieldError var1) {
      }
   }
}
