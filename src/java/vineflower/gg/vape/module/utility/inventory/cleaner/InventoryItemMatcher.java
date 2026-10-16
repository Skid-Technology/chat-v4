package gg.vape.module.utility.inventory.cleaner;

import gg.vape.unmap.INamed;
import gg.vape.wrapper.impl.Item;
import gg.vape.wrapper.impl.ItemStack;
import java.util.Comparator;
import org.jetbrains.annotations.Nullable;

public interface InventoryItemMatcher extends INamed, DescribedOption {
   default boolean matches(ItemStack itemStack) {
      return this.matches(itemStack, itemStack.getItem());
   }

   default InventoryItemCategory getCategory() {
      return null;
   }

   @Nullable
   String getIconName();

   @Nullable
   Comparator<InventoryItemMatchContext> getComparator();

   static InventoryItemMatcherBuilder builder() {
      return new InventoryItemMatcherBuilder();
   }

   String getId();

   InventoryItemMatcherGroup getGroup();

   boolean matches(ItemStack var1, Item var2);

   default void setCategory(InventoryItemCategory category) {
   }
}
