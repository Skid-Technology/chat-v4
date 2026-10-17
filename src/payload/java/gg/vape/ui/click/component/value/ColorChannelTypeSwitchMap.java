package gg.vape.ui.click.component.value;

class ColorChannelTypeSwitchMap {
   static final int[] CHANNEL_CASES = new int[ColorChannelType.values().length];

   static {
      try {
         CHANNEL_CASES[ColorChannelType.BLOCK_CHILD.ordinal()] = 1;
      } catch (NoSuchFieldError var5) {
      }

      try {
         CHANNEL_CASES[ColorChannelType.RAINBOW.ordinal()] = 2;
      } catch (NoSuchFieldError var4) {
      }

      try {
         CHANNEL_CASES[ColorChannelType.SATURATION.ordinal()] = 3;
      } catch (NoSuchFieldError var3) {
      }

      try {
         CHANNEL_CASES[ColorChannelType.VIBRANCE.ordinal()] = 4;
      } catch (NoSuchFieldError var2) {
      }

      try {
         CHANNEL_CASES[ColorChannelType.OPACITY.ordinal()] = 5;
      } catch (NoSuchFieldError var1) {
      }
   }
}
