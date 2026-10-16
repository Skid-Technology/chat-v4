package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MPotionEffect;
import gg.vape.wrapper.Wrapper;

public class PotionEffect extends Wrapper {
   public Holder t() {
      return new Holder(MPotionEffect.I$src$Ljava_lang_Object_$1dcpybi(vapeInstance.getMappings().C8, this.I));
   }

   public int L() {
      return MPotionEffect.K(vapeInstance.getMappings().C8, this.I);
   }

   public boolean A() {
      return MPotionEffect.e$src$Z$v3xxtq(vapeInstance.getMappings().C8, this.I);
   }

   private static PotionEffect e(StatusEffect statusEffect, int n, int n2) {
      return new PotionEffect(vapeInstance.getMappings().C8.A(statusEffect.getObject(), n, n2));
   }

   public static PotionEffect o(int n, int n2, int n3) {
      if (ForgeVersion.MC_1_20_6.d()) {
         Registry registry = BuiltInRegistries.j();
         Holder holder = registry.J(registry.t(n));
         return m(holder, n2, n3);
      } else if (ForgeVersion.MC_1_16_5.d()) {
         return e(StatusEffect.E(n), n2, n3);
      } else {
         return ForgeVersion.MC_1_12_2.d() ? v(Potion.getPotionById(n), n2, n3) : new PotionEffect(MPotionEffect.q(vapeInstance.getMappings().C8, n, n2, n3));
      }
   }

   private static PotionEffect m(Holder holder, int n, int n2) {
      return new PotionEffect(vapeInstance.getMappings().C8.A(holder.getObject(), n, n2));
   }

   public PotionEffect(Object object) {
      super(object);
   }

   public StatusEffect i() {
      return ForgeVersion.MC_1_21_0.d()
         ? new StatusEffect(new Holder(MPotionEffect.I$src$Ljava_lang_Object_$1dcpybi(vapeInstance.getMappings().C8, this.I)).N())
         : new StatusEffect(MPotionEffect.I$src$Ljava_lang_Object_$1dcpybi(vapeInstance.getMappings().C8, this.I));
   }

   public int k() {
      return MPotionEffect.e(vapeInstance.getMappings().C8, this.I);
   }

   private static PotionEffect v(Potion potion, int n, int n2) {
      return new PotionEffect(vapeInstance.getMappings().C8.A(potion.getObject(), n, n2));
   }

   public int C() {
      if (ForgeVersion.MC_1_16_5.d()) {
         return StatusEffect.v(this.i());
      } else {
         return ForgeVersion.MC_1_12_2.d()
            ? new Potion(MPotionEffect.I$src$Ljava_lang_Object_$1dcpybi(vapeInstance.getMappings().C8, this.I)).getId()
            : MPotionEffect.I(vapeInstance.getMappings().C8, this.I);
      }
   }
}
