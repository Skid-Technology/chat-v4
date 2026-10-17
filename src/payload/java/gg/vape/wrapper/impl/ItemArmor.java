package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MItemArmor;

@Deprecated
public class ItemArmor extends Item {
   public float p$src$F$1fue3zl() {
      return MItemArmor.e(vapeInstance.getMappings().DZ, this.getObject());
   }

   public ItemArmor(Object object) {
      super(object);
      if (ForgeVersion.MC_1_21_6.d()) {
         throw new UnsupportedOperationException("Unsupported");
      }
   }

   private static UnsupportedOperationException a(UnsupportedOperationException unsupportedOperationException) {
      return unsupportedOperationException;
   }

   public int Y(ItemStack itemStack) {
      if (ForgeVersion.MC_1_20_6.d()) {
         throw new UnsupportedOperationException("Unimplemented");
      } else {
         return MItemArmor.w(vapeInstance.getMappings().DZ, this.I, itemStack.getObject());
      }
   }

   public ArmorMaterial Y$src$Lgg_vape_wrapper_impl_ArmorMaterial_$11f9rp6() {
      if (ForgeVersion.MC_1_21_0.d()) {
         throw new UnsupportedOperationException("Unimplemented");
      } else {
         return new ArmorMaterial(MItemArmor.o(vapeInstance.getMappings().DZ, this.I));
      }
   }

   public int S() {
      if (ForgeVersion.MC_1_21_4.d()) {
         throw new UnsupportedOperationException("Unsupported");
      } else {
         return MItemArmor.q(vapeInstance.getMappings().DZ, this.I);
      }
   }

   public int b$src$I$1fmozr4() {
      if (ForgeVersion.MC_1_20_6.d()) {
         EntityEquipmentSlot entityEquipmentSlot = new EntityEquipmentSlotHolder(MItemArmor.D(vapeInstance.getMappings().DZ, this.I)).getSlot();
         return entityEquipmentSlot.W();
      } else if (ForgeVersion.MC_1_12_2.d()) {
         EntityEquipmentSlot entityEquipmentSlot = new EntityEquipmentSlot(MItemArmor.D(vapeInstance.getMappings().DZ, this.I));
         return entityEquipmentSlot.W();
      } else {
         return MItemArmor.F(vapeInstance.getMappings().DZ, this.I);
      }
   }
}
