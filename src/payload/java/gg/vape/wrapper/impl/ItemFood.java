package gg.vape.wrapper.impl;

public class ItemFood extends Item {
   public float getSaturation() {
      return vapeInstance.getMappings().itemFood.getSaturation(this.I);
   }

   public ItemFood(Object wrappedObject) {
      super(wrappedObject);
   }

   public int getNutrition() {
      return vapeInstance.getMappings().itemFood.getNutrition(this.I);
   }
}
