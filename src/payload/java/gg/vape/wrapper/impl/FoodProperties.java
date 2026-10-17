package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class FoodProperties extends Wrapper {
   public FoodProperties(Object wrappedObject) {
      super(wrappedObject);
   }

   public boolean canAlwaysEat() {
      return vapeInstance.getMappingsMapperCompat().foodProperties.canAlwaysEat(this.getObject());
   }

   public int getNutrition() {
      return vapeInstance.getMappingsMapperCompat().foodProperties.getNutrition(this.getObject());
   }

   public float getSaturation() {
      return vapeInstance.getMappingsMapperCompat().foodProperties.getSaturation(this.getObject());
   }
}
