package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MEnchantment;
import gg.vape.utils.EnchantmentUtil;
import gg.vape.wrapper.Wrapper;
import java.util.ArrayList;

public class Enchantment extends Wrapper {
   public static Enchantment projectileProtection() {
      return getEnchantmentById(4);
   }

   public static Enchantment flame() {
      return getEnchantmentById(N() ? 25 : 50);
   }

   public static Enchantment baneOfArthropods() {
      return getEnchantmentById(N() ? 14 : 18);
   }

   private static boolean N() {
      return ForgeVersion.MC_1_16_5.d();
   }

   public static Enchantment smite() {
      return getEnchantmentById(N() ? 13 : 17);
   }

   public static Enchantment fireProtection() {
      return getEnchantmentById(1);
   }

   public static Enchantment fortune() {
      return getEnchantmentById(N() ? 22 : 35);
   }

   public static Enchantment getEnchantmentById(int n) {
      if (ForgeVersion.MC_1_12_2.d() && ForgeVersion.MC_1_16_5.v()) {
         return new Enchantment(MEnchantment.k(vapeInstance.getMappings().RC, n));
      } else {
         for (Enchantment enchantment : EnchantmentUtil.Y) {
            if (enchantment != null && !enchantment.isNull() && enchantment.getId() == n) {
               return enchantment;
            }
         }

         return new Enchantment(null);
      }
   }

   public int getId() {
      if (this.isNull()) {
         return -1;
      } else if (ForgeVersion.MC_1_16_5.d()) {
         Enchantment[] enchantmentArray = EnchantmentUtil.Y;

         for (int i = 0; i < enchantmentArray.length; i++) {
            Enchantment enchantment = enchantmentArray[i];
            if (enchantment.getObject() == this.I) {
               return i;
            }
         }

         return -1;
      } else {
         return ForgeVersion.MC_1_12_2.d() ? MEnchantment.m(vapeInstance.getMappings().RC, this.I) : MEnchantment.o(vapeInstance.getMappings().RC, this.I);
      }
   }

   public static Enchantment[] getEnchantments() {
      if (ForgeVersion.MC_1_21_4.d()) {
         return EnchantmentUtil.B();
      } else if (ForgeVersion.MC_1_21_0.d()) {
         return new Enchantment[0];
      } else if (ForgeVersion.MC_1_12_2.d()) {
         Iterable iterable = (Iterable)MEnchantment.N(vapeInstance.getMappings().RC);
         ArrayList<Enchantment> arrayList = new ArrayList<>();

         for (Object t : iterable) {
            Enchantment enchantment = new Enchantment(t);
            arrayList.add(enchantment);
         }

         return arrayList.toArray(new Enchantment[arrayList.size()]);
      } else {
         Object[] objectArray = MEnchantment.x(vapeInstance.getMappings().RC);
         if (objectArray == null) {
            return new Enchantment[0];
         } else {
            Enchantment[] enchantmentArray = new Enchantment[objectArray.length];

            for (int i = 0; i < objectArray.length; i++) {
               enchantmentArray[i] = new Enchantment(objectArray[i]);
            }

            return enchantmentArray;
         }
      }
   }

   public static Enchantment looting() {
      return getEnchantmentById(N() ? 17 : 21);
   }

   public static Enchantment knockback() {
      return getEnchantmentById(N() ? 15 : 19);
   }

   public static Enchantment sweepingEdge() {
      return getEnchantmentById(N() ? 18 : 22);
   }

   public static Enchantment h() {
      if (!ForgeVersion.MC_1_21_0.d()) {
         return new Enchantment(null);
      } else if (ForgeVersion.MC_1_21_4.d()) {
         Enchantment enchantment = EnchantmentUtil.h("wind_burst");
         return enchantment != null ? enchantment : new Enchantment(null);
      } else {
         return null;
      }
   }

   public static Enchantment unbreaking() {
      return getEnchantmentById(N() ? 21 : 34);
   }

   public static Enchantment thorns() {
      return getEnchantmentById(7);
   }

   public static Enchantment silkTouch() {
      return getEnchantmentById(N() ? 20 : 33);
   }

   public Enchantment(Object object) {
      super(object);
   }

   public static Enchantment protection() {
      return getEnchantmentById(0);
   }

   public static Enchantment a() {
      if (!ForgeVersion.MC_1_21_0.d()) {
         return new Enchantment(null);
      } else if (ForgeVersion.MC_1_21_4.d()) {
         Enchantment enchantment = EnchantmentUtil.h("breach");
         return enchantment != null ? enchantment : new Enchantment(null);
      } else {
         return null;
      }
   }

   public static Enchantment i() {
      if (!ForgeVersion.MC_1_21_0.d()) {
         return new Enchantment(null);
      } else if (ForgeVersion.MC_1_21_4.d()) {
         Enchantment enchantment = EnchantmentUtil.h("density");
         return enchantment != null ? enchantment : new Enchantment(null);
      } else {
         return null;
      }
   }

   public static Enchantment sharpness() {
      return getEnchantmentById(N() ? 12 : 16);
   }

   public static Enchantment power() {
      return getEnchantmentById(N() ? 23 : 48);
   }

   public static Enchantment efficiency() {
      return getEnchantmentById(N() ? 19 : 32);
   }

   public static Enchantment featherFalling() {
      return getEnchantmentById(2);
   }

   public String getTranslatedName(int n) {
      if (this.isNull()) {
         return "";
      } else if (ForgeVersion.MC_1_21_0.d()) {
         Holder holder = Holder.A(this.getObject());
         ITextComponent iTextComponent = new ITextComponent(MEnchantment.O(vapeInstance.getMappings().RC, holder.getObject(), n));
         return iTextComponent.getFormattedText();
      } else if (ForgeVersion.MC_1_16_5.d()) {
         ITextComponent iTextComponent = new ITextComponent(MEnchantment.J(vapeInstance.getMappings().RC, this.I, n));
         return iTextComponent.getFormattedText();
      } else {
         return MEnchantment.E(vapeInstance.getMappings().RC, this.I, n);
      }
   }

   public static Enchantment punch() {
      return getEnchantmentById(N() ? 24 : 49);
   }

   public static Enchantment fireAspect() {
      return getEnchantmentById(N() ? 16 : 20);
   }

   public static Enchantment mending() {
      return getEnchantmentById(N() ? 36 : 70);
   }

   public static Enchantment blastProtection() {
      return getEnchantmentById(3);
   }

   public static Enchantment infinity() {
      return getEnchantmentById(N() ? 26 : 51);
   }
}
