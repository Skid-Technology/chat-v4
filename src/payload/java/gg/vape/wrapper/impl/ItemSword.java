package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MItemSword;

public class ItemSword extends Item {
   private static final String b = "Removed";

   private static UnsupportedOperationException a(UnsupportedOperationException unsupportedOperationException) {
      return unsupportedOperationException;
   }

   public float Q$src$F$vp4c40() {
      if (ForgeVersion.MC_1_21_4.d()) {
         return 0.0F;
      } else {
         return ForgeVersion.MC_1_20_6.d()
            ? MItemSword.c(vapeInstance.getMappings().qF, this.s().getObject())
            : MItemSword.c(vapeInstance.getMappings().qF, this.I);
      }
   }

   public ItemSword(Object object) {
      super(object);
      if (ForgeVersion.MC_1_21_5.d()) {
         throw new UnsupportedOperationException("Removed");
      }
   }

   public float R() {
      if (ForgeVersion.MC_1_21_4.d()) {
         return 0.0F;
      } else {
         return ForgeVersion.MC_1_16_5.d() ? MItemSword.T(vapeInstance.getMappings().qF, this.I) : MItemSword.X(vapeInstance.getMappings().qF, this.I);
      }
   }

   public ToolMaterial s() {
      return ForgeVersion.MC_1_21_4.d() ? null : new ToolMaterial(MItemSword.x(vapeInstance.getMappings().qF, this.I));
   }
}
