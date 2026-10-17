package gg.vape.module.utility.inventory;

import gg.vape.config.ClientSettings;
import gg.vape.module.utility.AutoHotbar;
import gg.vape.wrapper.impl.Slot;
import java.util.Comparator;

public class InventoryManagerPrimaryItemScoreComparator implements Comparator<Slot> {
   final AutoHotbar autoHotbar;

   public int compare(Slot first, Slot second) {
      return this.comparePrimaryScore(first, second);
   }

   public int comparePrimaryScore(Slot first, Slot second) {
      return Double.compare(ClientSettings.getWeaponDamageScore(first.getStack()), ClientSettings.getWeaponDamageScore(second.getStack()));
   }

   public InventoryManagerPrimaryItemScoreComparator(AutoHotbar autoHotbar) {
      this.autoHotbar = autoHotbar;
   }
}
