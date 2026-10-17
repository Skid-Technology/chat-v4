package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MDamageSource;
import gg.vape.wrapper.Wrapper;

public class ItemsRegistryHolder extends Wrapper {
   private static UnsupportedOperationException a(UnsupportedOperationException unsupportedOperationException) {
      return unsupportedOperationException;
   }

   public static Item e() {
      return new Item(MDamageSource.I(vapeInstance.getMappings().RK));
   }

   public static Item w() {
      if (ForgeVersion.MC_1_12_2.d()) {
         return new Item(MDamageSource.s(vapeInstance.getMappings().RK));
      } else {
         throw new UnsupportedOperationException("Totem of Undying is not available in this version.");
      }
   }

   public static ItemSplashPotion O() {
      return new ItemSplashPotion(MDamageSource.D(vapeInstance.getMappings().RK));
   }

   public ItemsRegistryHolder(Object object) {
      super(object);
   }

   public static Item h() {
      if (ForgeVersion.MC_1_12_2.d()) {
         return new Item(MDamageSource.T(vapeInstance.getMappings().RK));
      } else {
         throw new UnsupportedOperationException("End Crystal is not available in this version.");
      }
   }
}
