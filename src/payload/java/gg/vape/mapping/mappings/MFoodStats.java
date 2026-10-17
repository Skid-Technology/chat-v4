package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MFoodStats extends Mapping {
   private static final String GET_FOOD_LEVEL_METHOD_NAME = "getFoodLevel";
   private final MappingMethod getFoodLevelMethod = this.Y("getFoodLevel", true, int.class);

   private int invokeGetFoodLevel(Object foodStats) {
      return this.getFoodLevelMethod.invokeInt(foodStats);
   }

   public MFoodStats() {
      super(MappedClasses.Zd);
   }

   public static int getFoodLevel(MFoodStats mapping, Object foodStats) {
      return mapping.invokeGetFoodLevel(foodStats);
   }
}
