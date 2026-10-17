package gg.vape.module.utility.inventory.cleaner;

import gg.vape.mapping.MappedClasses;
import gg.vape.wrapper.impl.DataComponentMap;
import gg.vape.wrapper.impl.DataComponents;
import gg.vape.wrapper.impl.FoodProperties;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Item;
import gg.vape.wrapper.impl.ItemFood;
import gg.vape.wrapper.impl.ItemStack;

public class FoodInventoryItemMatchers {
   public static final CompositeInventoryItemMatcher ANY_FOOD;
   public static final StringInventoryItemMatcher COOKED_FOOD;

   private static boolean isFoodItem(ItemStack itemStack, Item item) {
      if (ForgeVersion.MC_1_20_6.d()) {
         DataComponentMap dataComponentMap = item.g();
         return dataComponentMap.V(DataComponents.d());
      } else {
         return item.isInstance(MappedClasses.ITEM_FOOD);
      }
   }

   public static void initialize() {
      InventoryItemMatcherRegistry.register(ANY_FOOD);
      InventoryItemMatcherRegistry.register(COOKED_FOOD);
   }

   private static int compareByNutrition(InventoryItemMatchContext firstContext, InventoryItemMatchContext secondContext) {
      if (ForgeVersion.MC_1_20_6.d()) {
         DataComponentMap firstComponents = firstContext.getItem().g();
         DataComponentMap secondComponents = secondContext.getItem().g();
         Object firstFoodComponent = firstComponents.E(DataComponents.d());
         Object secondFoodComponent = secondComponents.E(DataComponents.d());
         if (firstFoodComponent != null && secondFoodComponent != null) {
            FoodProperties firstFood = new FoodProperties(firstFoodComponent);
            FoodProperties secondFood = new FoodProperties(secondFoodComponent);
            float firstNutritionScore = firstFood.getNutrition() * firstFood.getSaturation();
            float secondNutritionScore = secondFood.getNutrition() * secondFood.getSaturation();
            return Float.compare(firstNutritionScore, secondNutritionScore);
         } else {
            return 0;
         }
      } else {
         ItemFood firstFood = new ItemFood(firstContext.getItem());
         ItemFood secondFood = new ItemFood(secondContext.getItem());
         float firstNutritionScore = firstFood.getNutrition() * firstFood.getSaturation();
         float secondNutritionScore = secondFood.getNutrition() * secondFood.getSaturation();
         return Float.compare(firstNutritionScore, secondNutritionScore);
      }
   }

   static {
      String[] labels = new String[]{
         "baked_",
         "cooked_",
         "cooked-food-hover@2x",
         "Cooked food",
         "Any type of cooked food",
         "food-hover@2x",
         "Any food",
         "Any type of food",
         "cooked-food",
         "any-food"
      };
      ANY_FOOD = InventoryItemMatcher.builder()
         .composite()
         .withId(labels[9])
         .withName(labels[6])
         .withDescription(labels[7])
         .withIconName(labels[5])
         .withGroup(InventoryItemMatcherGroup.FOOD)
         .withPredicate(FoodInventoryItemMatchers::isFoodItem)
         .withComparator(FoodInventoryItemMatchers::compareByNutrition)
         .build();
      COOKED_FOOD = InventoryItemMatcher.builder()
         .stringMatcher()
         .withId(labels[8])
         .withName(labels[3])
         .withDescription(labels[4])
         .withIconName(labels[2])
         .withGroup(InventoryItemMatcherGroup.FOOD)
         .addPattern(labels[1], StringMatchOperator.STARTS)
         .addPattern(labels[0], StringMatchOperator.STARTS)
         .withComparator(ANY_FOOD.getComparator())
         .build();
   }
}
