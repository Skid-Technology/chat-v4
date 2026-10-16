package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MPotion;
import gg.vape.wrapper.Wrapper;
import java.util.ArrayList;

public class Potion extends Wrapper {
   public Potion(Object object) {
      super(object);
   }

   public boolean n() {
      if (ForgeVersion.MC_1_16_5.d()) {
         for (Object e : vapeInstance.getMappings().qU.W(this.I)) {
            PotionEffect potionEffect = new PotionEffect(e);
            if (!potionEffect.i().p()) {
               return true;
            }
         }

         return false;
      } else {
         return vapeInstance.getMappings().qU.E(this.I);
      }
   }

   public int getId() {
      if (ForgeVersion.MC_1_16_5.d()) {
         return -1;
      } else {
         return ForgeVersion.MC_1_12_2.d() ? MPotion.R(vapeInstance.getMappings().qU, this.getObject()) : MPotion.g(vapeInstance.getMappings().qU, this.I);
      }
   }

   public boolean isBadEffect() {
      return MPotion.u(vapeInstance.getMappings().qU, this.I);
   }

   public static Potion[] getPotionTypes() {
      if (ForgeVersion.MC_1_12_2.d()) {
         Iterable iterable = (Iterable)MPotion.x(vapeInstance.getMappings().qU);
         ArrayList<Potion> arrayList = new ArrayList<>();
         arrayList.add(null);

         for (Object t : iterable) {
            Potion potion = new Potion(t);
            arrayList.add(potion);
         }

         return arrayList.toArray(new Potion[arrayList.size()]);
      } else {
         Object[] objectArray = MPotion.i(vapeInstance.getMappings().qU);
         Potion[] potionArray = new Potion[objectArray.length];

         for (int i = 0; i < objectArray.length; i++) {
            potionArray[i] = new Potion(objectArray[i]);
         }

         return potionArray;
      }
   }

   public int y() {
      return ForgeVersion.MC_1_16_5.d() ? -1 : MPotion.H(vapeInstance.getMappings().qU, this.I);
   }

   public String y$src$Ljava_lang_String_$yl6pfj() {
      return MPotion.q(vapeInstance.getMappings().qU, this.I);
   }

   public static Potion getPotionById(int n) {
      return ForgeVersion.MC_1_12_2.d() ? new Potion(MPotion.Q(vapeInstance.getMappings().qU, n)) : getPotionTypes()[n];
   }
}
