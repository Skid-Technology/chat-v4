package gg.vape.ui.click.component.value;

class RangeEndpointSwitchMap {
   static final int[] ENDPOINT_CASES = new int[RangeEndpoint.values().length];

   static {
      try {
         ENDPOINT_CASES[RangeEndpoint.MINIMUM.ordinal()] = 1;
      } catch (NoSuchFieldError var2) {
      }

      try {
         ENDPOINT_CASES[RangeEndpoint.MAXIMUM.ordinal()] = 2;
      } catch (NoSuchFieldError var1) {
      }
   }
}
