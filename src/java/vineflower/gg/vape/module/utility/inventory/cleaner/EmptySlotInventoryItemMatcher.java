package gg.vape.module.utility.inventory.cleaner;

import gg.vape.wrapper.impl.Item;
import gg.vape.wrapper.impl.ItemStack;

public class EmptySlotInventoryItemMatcher extends AbstractInventoryItemMatcher {
   public static final EmptySlotInventoryItemMatcher EMPTY_SLOT = new EmptySlotInventoryItemMatcher();

   EmptySlotInventoryItemMatcher() {
      super(InventoryItemMatcher.builder().withName("Hand").withDescription("No item").withGroup(InventoryItemMatcherGroup.HIDDEN));
   }

   @Override
   public boolean matches(ItemStack itemStack, Item item) {
      return itemStack == null || itemStack.isNull() || item == null || item.isNull();
   }
}
