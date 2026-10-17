package gg.vape.module;

class MinecraftVersionComparisonOperatorSwitchMap {
   static final int[] operatorOrdinals = new int[MinecraftVersionComparisonOperator.values().length];

   static {
      try {
         operatorOrdinals[MinecraftVersionComparisonOperator.EQUALS.ordinal()] = 1;
      } catch (NoSuchFieldError var6) {
      }

      try {
         operatorOrdinals[MinecraftVersionComparisonOperator.NOT_EQUAL.ordinal()] = 2;
      } catch (NoSuchFieldError var5) {
      }

      try {
         operatorOrdinals[MinecraftVersionComparisonOperator.GREATER_THAN.ordinal()] = 3;
      } catch (NoSuchFieldError var4) {
      }

      try {
         operatorOrdinals[MinecraftVersionComparisonOperator.GREATHER_THAN_OR_EQUAL.ordinal()] = 4;
      } catch (NoSuchFieldError var3) {
      }

      try {
         operatorOrdinals[MinecraftVersionComparisonOperator.LESS_THAN.ordinal()] = 5;
      } catch (NoSuchFieldError var2) {
      }

      try {
         operatorOrdinals[MinecraftVersionComparisonOperator.LESS_THAN_OR_EQUAL.ordinal()] = 6;
      } catch (NoSuchFieldError var1) {
      }
   }
}
