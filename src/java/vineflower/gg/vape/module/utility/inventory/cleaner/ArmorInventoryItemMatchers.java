package gg.vape.module.utility.inventory.cleaner;

import gg.vape.utils.ItemStackScoreUtil;
import gg.vape.wrapper.impl.Item;
import gg.vape.wrapper.impl.ItemStack;

public class ArmorInventoryItemMatchers {
   public static final InventoryItemMatcher ANY_ARMOR;

   static void initialize() {
      InventoryItemMatcherRegistry.register(ANY_ARMOR);
   }

   private static boolean isArmorItem(ItemStack itemStack, Item item) {
      return ItemStackScoreUtil.R(item);
   }

   static {
      String[] labels = new String[]{"any-armor", "Any armor", "armor_item", "Any type of armor"};
      ANY_ARMOR = InventoryItemMatcher.builder()
         .composite()
         .withId(labels[0])
         .withName(labels[1])
         .withIconName(labels[2])
         .withDescription(labels[3])
         .withGroup(InventoryItemMatcherGroup.ARMOR)
         .withPredicate(ArmorInventoryItemMatchers::isArmorItem)
         .build();
   }
}
