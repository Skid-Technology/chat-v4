package gg.vape.module.macro;

import gg.vape.module.Macro;
import gg.vape.wrapper.impl.Item;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.Minecraft;

public class ItemMacro extends Macro {
   public ItemMacro(String name) {
      super(name);
   }

   @Override
   public MacroAction createAction() {
      int hotbarSlot = this.findHotbarSlot();
      return hotbarSlot == -1 ? null : new ItemMacroAction(this);
   }

   int findHotbarSlot() {
      try {
         for (int hotbarSlot = 0; hotbarSlot < 9; hotbarSlot++) {
            ItemStack itemStack = Minecraft.thePlayer().V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().c(hotbarSlot);
            if (itemStack.getObject() != null && itemStack.getItem().getObject() != null) {
               if (String.valueOf(Item.f(itemStack.getItem())).equals(this.getName())) {
                  return hotbarSlot;
               }

               if (itemStack.x().equalsIgnoreCase(this.getName()) || itemStack.getItem().getItemStackDisplayName(itemStack).equalsIgnoreCase(this.getName())) {
                  return hotbarSlot;
               }
            }
         }
      } catch (Exception var3) {
      }

      return -1;
   }
}
