package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class Foods extends Wrapper {
   public Foods(Object handle) {
      super(handle);
   }

   public static FoodProperties apple() {
      return wrap(vapeInstance.getMappings().foods.apple());
   }

   public static FoodProperties bakedPotato() {
      return wrap(vapeInstance.getMappings().foods.bakedPotato());
   }

   public static FoodProperties beef() {
      return wrap(vapeInstance.getMappings().foods.beef());
   }

   public static FoodProperties beetroot() {
      return wrap(vapeInstance.getMappings().foods.beetroot());
   }

   public static FoodProperties beetrootSoup() {
      return wrap(vapeInstance.getMappings().foods.beetrootSoup());
   }

   public static FoodProperties bread() {
      return wrap(vapeInstance.getMappings().foods.bread());
   }

   public static FoodProperties carrot() {
      return wrap(vapeInstance.getMappings().foods.carrot());
   }

   public static FoodProperties chicken() {
      return wrap(vapeInstance.getMappings().foods.chicken());
   }

   public static FoodProperties chorusFruit() {
      return wrap(vapeInstance.getMappings().foods.chorusFruit());
   }

   public static FoodProperties cod() {
      return wrap(vapeInstance.getMappings().foods.cod());
   }

   public static FoodProperties cookedBeef() {
      return wrap(vapeInstance.getMappings().foods.cookedBeef());
   }

   public static FoodProperties cookedChicken() {
      return wrap(vapeInstance.getMappings().foods.cookedChicken());
   }

   public static FoodProperties cookedCod() {
      return wrap(vapeInstance.getMappings().foods.cookedCod());
   }

   public static FoodProperties cookedMutton() {
      return wrap(vapeInstance.getMappings().foods.cookedMutton());
   }

   public static FoodProperties cookedPorkchop() {
      return wrap(vapeInstance.getMappings().foods.cookedPorkchop());
   }

   public static FoodProperties cookedRabbit() {
      return wrap(vapeInstance.getMappings().foods.cookedRabbit());
   }

   public static FoodProperties cookedSalmon() {
      return wrap(vapeInstance.getMappings().foods.cookedSalmon());
   }

   public static FoodProperties cookie() {
      return wrap(vapeInstance.getMappings().foods.cookie());
   }

   public static FoodProperties driedKelp() {
      return wrap(vapeInstance.getMappings().foods.driedKelp());
   }

   public static FoodProperties enchantedGoldenApple() {
      return wrap(vapeInstance.getMappings().foods.enchantedGoldenApple());
   }

   public static FoodProperties goldenApple() {
      return wrap(vapeInstance.getMappings().foods.goldenApple());
   }

   public static FoodProperties goldenCarrot() {
      return wrap(vapeInstance.getMappings().foods.goldenCarrot());
   }

   public static FoodProperties honeyBottle() {
      return wrap(vapeInstance.getMappings().foods.honeyBottle());
   }

   public static FoodProperties melonSlice() {
      return wrap(vapeInstance.getMappings().foods.melonSlice());
   }

   public static FoodProperties mushroomStew() {
      return wrap(vapeInstance.getMappings().foods.mushroomStew());
   }

   public static FoodProperties mutton() {
      return wrap(vapeInstance.getMappings().foods.mutton());
   }

   public static FoodProperties poisonousPotato() {
      return wrap(vapeInstance.getMappings().foods.poisonousPotato());
   }

   public static FoodProperties porkchop() {
      return wrap(vapeInstance.getMappings().foods.porkchop());
   }

   public static FoodProperties potato() {
      return wrap(vapeInstance.getMappings().foods.potato());
   }

   public static FoodProperties pufferfish() {
      return wrap(vapeInstance.getMappings().foods.pufferfish());
   }

   public static FoodProperties pumpkinPie() {
      return wrap(vapeInstance.getMappings().foods.pumpkinPie());
   }

   public static FoodProperties rabbit() {
      return wrap(vapeInstance.getMappings().foods.rabbit());
   }

   public static FoodProperties rabbitStew() {
      return wrap(vapeInstance.getMappings().foods.rabbitStew());
   }

   public static FoodProperties rottenFlesh() {
      return wrap(vapeInstance.getMappings().foods.rottenFlesh());
   }

   public static FoodProperties salmon() {
      return wrap(vapeInstance.getMappings().foods.salmon());
   }

   public static FoodProperties spiderEye() {
      return wrap(vapeInstance.getMappings().foods.spiderEye());
   }

   public static FoodProperties suspiciousStew() {
      return wrap(vapeInstance.getMappings().foods.suspiciousStew());
   }

   public static FoodProperties sweetBerries() {
      return wrap(vapeInstance.getMappings().foods.sweetBerries());
   }

   public static FoodProperties glowBerries() {
      return wrap(vapeInstance.getMappings().foods.glowBerries());
   }

   public static FoodProperties tropicalFish() {
      return wrap(vapeInstance.getMappings().foods.tropicalFish());
   }

   private static FoodProperties wrap(Object handle) {
      return new FoodProperties(handle);
   }
}
