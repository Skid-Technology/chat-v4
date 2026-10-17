package gg.vape.ui.click.frame.impl.main;

public class ClickGuiFriendSourceModeSwitchMap {
   public static final int[] Z = new int[ClickGuiFriendSourceMode.values().length];

   ClickGuiFriendSourceModeSwitchMap() {
   }

   static {
      try {
         Z[ClickGuiFriendSourceMode.ONLINE.ordinal()] = 1;
      } catch (NoSuchFieldError var2) {
      }

      try {
         Z[ClickGuiFriendSourceMode.MINECRAFT.ordinal()] = 2;
      } catch (NoSuchFieldError var1) {
      }
   }
}
