package gg.vape.wrapper.impl;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.mappings.MItemPotion;
import java.util.ArrayList;
import java.util.List;

public class ItemSplashPotion extends Item {
   @Override
   public String getItemStackDisplayName(ItemStack itemStack) {
      if (ForgeVersion.MC_1_16_5.d()) {
         ITextComponent iTextComponent = new ITextComponent(MItemPotion.M(vapeInstance.getMappings().CI, this.I, itemStack.getObject()));
         return iTextComponent.getFormattedText();
      } else {
         return MItemPotion.B(vapeInstance.getMappings().CI, this.I, itemStack.getObject());
      }
   }

   public List getRawPotionEffects(ItemStack itemStack) {
      if (!ForgeVersion.MC_1_20_6.d()) {
         return MItemPotion.R(vapeInstance.getMappings().CI, this.I, itemStack.getObject());
      } else {
         BiomeProvider biomeProvider = new BiomeProvider(itemStack.w(DataComponents.T()));
         ArrayList arrayList = new ArrayList();

         for (Object t : biomeProvider.getAllEffects()) {
            arrayList.add(t);
         }

         return arrayList;
      }
   }

   public List<PotionEffect> getPotionEffects(ItemStack itemStack) {
      ArrayList<PotionEffect> arrayList = new ArrayList<>();

      for (Object e : ForgeVersion.MC_1_20_6.d()
         ? this.getRawPotionEffects(itemStack)
         : MItemPotion.R(vapeInstance.getMappings().CI, this.I, itemStack.getObject())) {
         arrayList.add(new PotionEffect(e));
      }

      return arrayList;
   }

   public static boolean isSplashPotion(ItemStack itemStack) {
      return !ForgeVersion.MC_1_12_2.d() ? (itemStack.L() & 16384) != 0 : itemStack.getItem().isNotNull() && itemStack.getItem().isInstance(MappedClasses.o);
   }

   public List b(ItemStack itemStack) {
      return MItemPotion.R(vapeInstance.getMappings().CI, this.I, itemStack.getObject());
   }

   public List<PotionEffect> getPotionEffects(int n) {
      ArrayList<PotionEffect> arrayList = new ArrayList<>();

      for (Object e : MItemPotion.R(vapeInstance.getMappings().CI, this.I, n)) {
         arrayList.add(new PotionEffect(e));
      }

      return arrayList;
   }

   public ItemSplashPotion(Object object) {
      super(object);
   }
}
