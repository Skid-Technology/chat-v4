package gg.vape.module.utility.inventory.cleaner;

import gg.vape.unmap.INamed;
import gg.vape.wrapper.impl.ItemStack;
import java.util.List;
import org.jetbrains.annotations.UnmodifiableView;

public interface InventoryFilterPresetData extends INamed {
   @UnmodifiableView List<InventoryFilterConditionGroup> getConditionGroups();

   boolean matches(ItemStack var1);

   @Override
   String getName();
}
