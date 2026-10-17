package gg.vape.module.utility.inventory.cleaner;

import gg.vape.unmap.INamed;
import gg.vape.wrapper.impl.ItemStack;
import java.util.Comparator;
import java.util.List;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

public interface InventoryItemCategory extends INamed {
   String getId();

   @UnmodifiableView List<InventoryItemMatcher> getMatchers();

   @Override
   String getName();

   static DefaultInventoryItemCategoryBuilder builder() {
      return new DefaultInventoryItemCategoryBuilder();
   }

   boolean isCompatible(ItemFilterSelection var1);

   String getDisplayName();

   @Nullable
   Comparator<ItemStack> getComparator();
}
