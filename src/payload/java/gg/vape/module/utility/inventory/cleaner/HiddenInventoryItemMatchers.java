package gg.vape.module.utility.inventory.cleaner;

import gg.vape.config.ClientSettings;
import gg.vape.mapping.MappedClasses;
import gg.vape.wrapper.impl.ItemSplashPotion;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.PotionEffect;
import java.util.Comparator;
import java.util.List;

public class HiddenInventoryItemMatchers {
   public static final InventoryItemMatcher ANY_POTION;
   public static final InventoryItemMatcher ANY_BOW;
   public static final InventoryItemMatcher ANY_ITEM;

   private static double getHiddenItemSortScore(InventoryItemMatchContext context) {
      return ClientSettings.getHiddenItemScore(context.getItemStack());
   }

   public static void initialize() {
      InventoryItemMatcherRegistry.register(EmptySlotInventoryItemMatcher.EMPTY_SLOT);
      InventoryItemMatcherRegistry.register(ANY_ITEM);
      InventoryItemMatcherRegistry.register(ANY_BOW);
      InventoryItemMatcherRegistry.register(ANY_POTION);
   }

   private static int comparePotionAmplifiers(InventoryItemMatchContext firstContext, InventoryItemMatchContext secondContext) {
      ItemStack firstStack = firstContext.getItemStack();
      ItemStack secondStack = secondContext.getItemStack();
      List<PotionEffect> firstEffects = new ItemSplashPotion(firstStack.getItem()).getPotionEffects(firstStack);
      List<PotionEffect> secondEffects = new ItemSplashPotion(secondStack.getItem()).getPotionEffects(secondStack);
      int comparison = 0;

      for (PotionEffect firstEffect : firstEffects) {
         int firstEffectId = firstEffect.C();

         for (PotionEffect secondEffect : secondEffects) {
            int secondEffectId = secondEffect.C();
            if (firstEffectId == secondEffectId) {
               comparison += Integer.compare(firstEffect.L(), secondEffect.L());
            }
         }
      }

      return comparison;
   }

   static {
      String[] labels = new String[]{
         "Any bow", "Any type of potion", "any-bow", "other@2x", "any-potion", "Any Item", "Any type of bow", "any-item", "Any type of item", "Any potion"
      };
      ANY_ITEM = InventoryItemMatcher.builder()
         .stringMatcher()
         .withId(labels[7])
         .withName(labels[5])
         .withDescription(labels[8])
         .withIconName(labels[3])
         .withGroup(InventoryItemMatcherGroup.HIDDEN)
         .addPattern("", StringMatchOperator.ANY)
         .build();
      ANY_BOW = InventoryItemMatcher.builder()
         .classMatcher()
         .withId(labels[2])
         .withName(labels[0])
         .withDescription(labels[6])
         .withGroup(InventoryItemMatcherGroup.HIDDEN)
         .addClass(MappedClasses.Vl)
         .withListMode(InventoryMatcherListMode.WHITELIST)
         .withComparator(Comparator.comparingDouble(HiddenInventoryItemMatchers::getHiddenItemSortScore))
         .build();
      ANY_POTION = InventoryItemMatcher.builder()
         .classMatcher()
         .withId(labels[4])
         .withName(labels[9])
         .withDescription(labels[1])
         .withGroup(InventoryItemMatcherGroup.HIDDEN)
         .addClass(MappedClasses.Di)
         .withListMode(InventoryMatcherListMode.WHITELIST)
         .withComparator(HiddenInventoryItemMatchers::comparePotionAmplifiers)
         .build();
   }
}
