package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MEntityLivingBase;
import java.util.Collection;

public class EntityLivingBase extends Entity {
   private static String[] x;

   public void P$src$V$14ztfy0() {
   }

   public void o(float f) {
      MEntityLivingBase.e(vapeInstance.getMappings().hx, this.I, f);
   }

   public boolean p$src$Z$15hev10() {
      return ForgeVersion.MC_1_21_4.d() ? MEntityLivingBase.p$src$Z$qthfwb(vapeInstance.getMappings().hx, this.I) : false;
   }

   public double z(EquipmentSlotGroup equipmentSlotGroup) {
      return MEntityLivingBase.c(vapeInstance.getMappings().hx, this.I, equipmentSlotGroup.getObject());
   }

   public boolean l(BlockStateWorldBridge blockStateWorldBridge) {
      if (ForgeVersion.MC_1_20_6.d()) {
         return MEntityLivingBase.c$src$Z$uftdxu(vapeInstance.getMappings().hx, this.I, blockStateWorldBridge.getObject());
      } else {
         Object object = blockStateWorldBridge.getType().getObject();
         return object == null ? false : MEntityLivingBase.c$src$Z$uftdxu(vapeInstance.getMappings().hx, this.I, object);
      }
   }

   public EnumHand G$src$Lgg_vape_wrapper_impl_EnumHand_$33es8x() {
      if (ForgeVersion.MC_1_12_2.v()) {
         return null;
      } else {
         Object object = vapeInstance.getMappings().hx.r(this.I);
         return object == null ? EnumHand.mainHand() : new EnumHand(object);
      }
   }

   public void t$src$V$15jm1b0() {
      MEntityLivingBase.h(vapeInstance.getMappings().hx, this.I);
   }

   public float N$src$F$14ypudi() {
      return MEntityLivingBase.b(vapeInstance.getMappings().hx, this.I);
   }

   public void q(int n) {
      if (ForgeVersion.MC_1_21_4.d()) {
         Registry registry = BuiltInRegistries.j();
         Object object = registry.t(n);
         Holder holder = registry.J(object);
         MEntityLivingBase.r(vapeInstance.getMappings().hx, this.I, holder.getObject());
      } else if (ForgeVersion.MC_1_16_5.d()) {
         MEntityLivingBase.r(vapeInstance.getMappings().hx, this.I, StatusEffect.E(n).getObject());
      } else if (ForgeVersion.MC_1_12_2.d()) {
         MEntityLivingBase.E(vapeInstance.getMappings().hx, this.I, Potion.getPotionById(n).getObject());
      } else {
         MEntityLivingBase.m(vapeInstance.getMappings().hx, this.I, n);
      }
   }

   public boolean i(StatusEffect statusEffect) {
      if (ForgeVersion.MC_1_20_6.d()) {
         Registry registry = BuiltInRegistries.j();
         return MEntityLivingBase.m(vapeInstance.getMappings().hx, this.I, registry.J(statusEffect.getObject()).getObject());
      } else {
         return MEntityLivingBase.m(vapeInstance.getMappings().hx, this.I, statusEffect.getObject());
      }
   }

   public EnumCreatureAttribute k$src$Lgg_vape_wrapper_impl_EnumCreatureAttribute_$uojvxj() {
      return ForgeVersion.MC_1_20_6.d() ? null : new EnumCreatureAttribute(vapeInstance.getMappings().hx.i(this.I));
   }

   public boolean k$src$Z$15enw27() {
      return ForgeVersion.MC_1_12_2.v() ? false : MEntityLivingBase.o$src$Z$10vin0a(vapeInstance.getMappings().hx, this.I);
   }

   public boolean isElytraFlying() {
      return this.k$src$Z$15enw27();
   }

   public float s() {
      return this.isNull() ? 0.0F : MEntityLivingBase.z(vapeInstance.getMappings().hx, this.I);
   }

   public void V(Object object) {
      MEntityLivingBase.P(vapeInstance.getMappings().hx, this.I, object);
   }

   public void B$src$V$14s4bmy() {
      if (ForgeVersion.MC_1_21_4.d()) {
         MEntityLivingBase.V(vapeInstance.getMappings().hx, this.I);
      }
   }

   public void Y(float f) {
      MEntityLivingBase.Y(vapeInstance.getMappings().hx, this.I, f);
   }

   public AttributeInstance t(Holder holder) {
      return new AttributeInstance(MEntityLivingBase.G(vapeInstance.getMappings().hx, this.I, holder.getObject()));
   }

   public void j(float f) {
      if (ForgeVersion.MC_1_21_4.d() && ForgeVersion.MC_1_21_10.v()) {
         MEntityLivingBase.q(vapeInstance.getMappings().hx, this.I, f);
      }
   }

   public void z(float f) {
      MEntityLivingBase.J(vapeInstance.getMappings().hx, this.I, f);
   }

   public void T(float f) {
      if (ForgeVersion.MC_1_20_6.d()) {
         SPacketExplosion sPacketExplosion = new SPacketExplosion(vapeInstance.getMappings().hx.u(this.I));
         sPacketExplosion.setPreviousSpeed(f);
      } else {
         MEntityLivingBase.d(vapeInstance.getMappings().hx, this.I, f);
      }
   }

   public void G(float f) {
      if (ForgeVersion.MC_1_20_6.d()) {
         SPacketExplosion sPacketExplosion = new SPacketExplosion(vapeInstance.getMappings().hx.u(this.I));
         sPacketExplosion.setPosition(f);
      } else {
         MEntityLivingBase.P(vapeInstance.getMappings().hx, this.I, f);
      }
   }

   public EntityLivingBase(Object object) {
      super(object);
   }

   public float F() {
      return MEntityLivingBase.w(vapeInstance.getMappings().hx, this.I);
   }

   public void z(Entity entity) {
      vapeInstance.getMappings().hx.F(this.I, entity.getObject());
   }

   public boolean S$src$Z$151gttj() {
      return MEntityLivingBase.O$src$Z$3j0onu(vapeInstance.getMappings().hx, this.I);
   }

   public int B$src$I$14s4bbr() {
      return MEntityLivingBase.M(vapeInstance.getMappings().hx, this.I);
   }

   public void k$src$V$5315b7(float f) {
      MEntityLivingBase.l(vapeInstance.getMappings().hx, this.I, f);
   }

   public void X(float f) {
      MEntityLivingBase.c(vapeInstance.getMappings().hx, this.I, f);
   }

   public void m$src$V$15frh5h() {
      if (ForgeVersion.MC_1_12_2.d()) {
         MEntityLivingBase.a(vapeInstance.getMappings().hx, this.I, EnumHand.mainHand().getObject());
      } else {
         MEntityLivingBase.w$src$V$rgd406(vapeInstance.getMappings().hx, this.I);
      }
   }

   public void swingArm() {
      this.m$src$V$15frh5h();
   }

   public Vec3 O(float f) {
      return new Vec3(vapeInstance.getMappings().hx.w(this.I, f));
   }

   public boolean e$src$Z$15bd4i1() {
      return MEntityLivingBase.e(vapeInstance.getMappings().hx, this.I);
   }

   public void I(float f) {
      MEntityLivingBase.X(vapeInstance.getMappings().hx, this.I, f);
   }

   public ItemStack B$src$Lgg_vape_wrapper_impl_ItemStack_$impdvt() {
      return new ItemStack(MEntityLivingBase.s(vapeInstance.getMappings().hx, this.I));
   }

   public RayTraceResult W(double d, float f) {
      return ForgeVersion.MC_1_16_5.d()
         ? new RayTraceResult(vapeInstance.getMappings().hx.Y(this.I, d, f, false))
         : new RayTraceResult(vapeInstance.getMappings().hx.v(this.I, d, f));
   }

   public static AttributeModifier A$src$Lgg_vape_wrapper_impl_AttributeModifier_$lg3ax4() {
      return new AttributeModifier(MEntityLivingBase.I(vapeInstance.getMappings().hx));
   }

   public void R$src$V$150x14q() {
      if (ForgeVersion.MC_1_21_4.d()) {
         MEntityLivingBase.Q(vapeInstance.getMappings().hx, this.I);
      }
   }

   public boolean V$src$Z$15347lm() {
      return ForgeVersion.MC_1_21_4.d() ? MEntityLivingBase.N$src$Z$dl1vrt(vapeInstance.getMappings().hx, this.I) : false;
   }

   public float m$src$F$15frgrp() {
      return MEntityLivingBase.L(vapeInstance.getMappings().hx, this.I);
   }

   public void H(boolean bl) {
      vapeInstance.getMappings().hx.G(this.I, bl);
   }

   public float B$src$F$14s4b96() {
      if (ForgeVersion.MC_1_20_6.d()) {
         SPacketExplosion sPacketExplosion = new SPacketExplosion(vapeInstance.getMappings().hx.u(this.I));
         return sPacketExplosion.getPreviousSpeed();
      } else {
         return MEntityLivingBase.A(vapeInstance.getMappings().hx, this.I);
      }
   }

   public Vec3 n(Vec3 vec3) {
      return new Vec3(MEntityLivingBase.R(vapeInstance.getMappings().hx, this.I, vec3.getObject()));
   }

   public Collection B$src$Ljava_util_Collection_$1uxz2f9() {
      return (Collection)MEntityLivingBase.C(vapeInstance.getMappings().hx, this.I);
   }

   public int i() {
      return vapeInstance.getMappings().hx.U(this.I);
   }

   public float w$src$F$15l9epb() {
      return MEntityLivingBase.I(vapeInstance.getMappings().hx, this.I);
   }

   public float getHealth() {
      return this.w$src$F$15l9epb();
   }

   public float getAbsorption() {
      return this.p();
   }

   public final float e() {
      return ForgeVersion.MC_1_21_4.d() ? MEntityLivingBase.o(vapeInstance.getMappings().hx, this.I) : 1.0F;
   }

   public ItemStack Y$src$Lgg_vape_wrapper_impl_ItemStack_$1e6807m() {
      return new ItemStack(MEntityLivingBase.P(vapeInstance.getMappings().hx, this.I));
   }

   public static String[] L() {
      return x;
   }

   public float b(float f) {
      return MEntityLivingBase.b(vapeInstance.getMappings().hx, this.I, f);
   }

   public int c$src$I$15a9iwo() {
      return MEntityLivingBase.Z(vapeInstance.getMappings().hx, this.I);
   }

   public void b(boolean bl) {
      MEntityLivingBase.L(vapeInstance.getMappings().hx, this.I, bl);
   }

   public float y$src$F$15mczw1() {
      return ForgeVersion.MC_1_20_6.d() ? vapeInstance.getMappings().hx.X(this.I) : MEntityLivingBase.t(vapeInstance.getMappings().hx, this.I);
   }

   public int d(EnumHand enumHand) {
      if (ForgeVersion.MC_1_12_2.v()) {
         return 0;
      } else {
         Object object = vapeInstance.getMappings().hx.r(this.I);
         if (object == null) {
            return 0;
         } else {
            return object.equals(enumHand.getObject()) ? vapeInstance.getMappings().hx.Y(this.I) : 0;
         }
      }
   }

   public float K() {
      return ForgeVersion.MC_1_21_4.d() && ForgeVersion.MC_1_21_10.v() ? MEntityLivingBase.Y(vapeInstance.getMappings().hx, this.I) : 1.0F;
   }

   public float U$src$F$152kej1() {
      if (ForgeVersion.MC_1_20_6.d()) {
         SPacketExplosion sPacketExplosion = new SPacketExplosion(vapeInstance.getMappings().hx.u(this.I));
         return sPacketExplosion.getPosition();
      } else {
         return MEntityLivingBase.N(vapeInstance.getMappings().hx, this.I);
      }
   }

   public double J$src$D$14winyc() {
      return ForgeVersion.MC_1_21_4.d() ? MEntityLivingBase.n(vapeInstance.getMappings().hx, this.I) : 0.08;
   }

   public static void g(String[] stringArray) {
      x = stringArray;
   }

   public void s(PotionEffect potionEffect) {
      MEntityLivingBase.B(vapeInstance.getMappings().hx, this.I, potionEffect.getObject());
   }

   public float P$src$F$14ztfk8() {
      return vapeInstance.getMappings().hx.J(this.I);
   }

   public float W$src$F$153nzpr() {
      return MEntityLivingBase.O(vapeInstance.getMappings().hx, this.I);
   }

   public void h$src$V$15d0i6o() {
      MEntityLivingBase.A$src$V$299c5s(vapeInstance.getMappings().hx, this.I);
   }

   public float H$src$F$14vf2tc() {
      if (ForgeVersion.MC_1_20_6.d()) {
         SPacketExplosion sPacketExplosion = new SPacketExplosion(vapeInstance.getMappings().hx.u(this.I));
         return sPacketExplosion.getSpeed();
      } else {
         return MEntityLivingBase.p(vapeInstance.getMappings().hx, this.I);
      }
   }

   public void e(float f) {
      if (ForgeVersion.MC_1_20_6.d()) {
         SPacketExplosion sPacketExplosion = new SPacketExplosion(vapeInstance.getMappings().hx.u(this.I));
         sPacketExplosion.setPreviousSpeed(f);
      } else {
         MEntityLivingBase.F(vapeInstance.getMappings().hx, this.I, f);
      }
   }

   public PotionEffect e(StatusEffect statusEffect) {
      if (ForgeVersion.MC_1_20_6.d()) {
         Registry registry = BuiltInRegistries.j();
         return new PotionEffect(MEntityLivingBase.T(vapeInstance.getMappings().hx, this.I, registry.J(statusEffect.getObject()).getObject()));
      } else {
         return new PotionEffect(MEntityLivingBase.T(vapeInstance.getMappings().hx, this.I, statusEffect.getObject()));
      }
   }

   public void t(float f) {
      MEntityLivingBase.S(vapeInstance.getMappings().hx, this.I, f);
   }

   public void I(int n) {
      MEntityLivingBase.H(vapeInstance.getMappings().hx, this.I, n);
   }

   public float p() {
      return vapeInstance.getMappings().hx.f(this.I);
   }

   public void M(float f) {
      MEntityLivingBase.L(vapeInstance.getMappings().hx, this.I, f);
   }

   public PotionEffect getActivePotionEffect(Potion potion) {
      return new PotionEffect(MEntityLivingBase.T(vapeInstance.getMappings().hx, this.I, potion.getObject()));
   }

   public float S$src$F$151gtcb() {
      return vapeInstance.getMappings().hx.C(this.I);
   }

   public Vec3 J(float f) {
      return new Vec3(vapeInstance.getMappings().hx.m(this.I, f));
   }

   public float L(float f) {
      return vapeInstance.getMappings().hx.X(this.I, f);
   }

   public boolean Y$src$Z$154rldp() {
      return MEntityLivingBase.c(vapeInstance.getMappings().hx, this.I);
   }

   public PotionEffect b(PotionEntry potionEntry) {
      return ForgeVersion.MC_1_16_5.d() ? this.e(potionEntry.getStatusEffect()) : this.getActivePotionEffect(potionEntry.getLegacyPotion());
   }

   public boolean i(PotionEntry potionEntry) {
      return ForgeVersion.MC_1_16_5.d() ? this.i(potionEntry.getStatusEffect()) : this.isPotionActive(potionEntry.getLegacyPotion());
   }

   public AttributeInstance h(EquipmentSlotGroup equipmentSlotGroup) {
      return new AttributeInstance(MEntityLivingBase.G(vapeInstance.getMappings().hx, this.I, equipmentSlotGroup.getObject()));
   }

   public boolean canEntityBeSeen(Entity entity) {
      return vapeInstance.getMappings().hx.C(this.I, entity.getObject());
   }

   public ItemStack i(EnumHand enumHand) {
      return new ItemStack(MEntityLivingBase.f(vapeInstance.getMappings().hx, this.I, enumHand.getObject()));
   }

   public Vec3 G(double d, boolean bl, Vec3 vec3) {
      return new Vec3(MEntityLivingBase.g(vapeInstance.getMappings().hx, this.I, d, bl, vec3.getObject()));
   }

   public void p(int n) {
      vapeInstance.getMappings().hx.d(this.I, n);
   }

   public Object z$src$Ljava_lang_Object_$1k68ls2() {
      return MEntityLivingBase.z$src$Ljava_lang_Object_$rov6uz(vapeInstance.getMappings().hx, this.I);
   }

   public void L(int n) {
      MEntityLivingBase.W(vapeInstance.getMappings().hx, this.I, n);
   }

   public int a$src$I$1595xpy() {
      return MEntityLivingBase.H(vapeInstance.getMappings().hx, this.I);
   }

   public boolean isPotionActive(Potion potion) {
      return MEntityLivingBase.m(vapeInstance.getMappings().hx, this.I, potion.getObject());
   }

   public float I$src$F$14vyvep() {
      return MEntityLivingBase.v(vapeInstance.getMappings().hx, this.I);
   }

   public double o(Holder holder) {
      return MEntityLivingBase.c(vapeInstance.getMappings().hx, this.I, holder.getObject());
   }

   public boolean boolean_S() {
      return this.S$src$Z$151gttj();
   }

   static {
      if (L() != null) {
         g(new String[3]);
      }
   }
}
