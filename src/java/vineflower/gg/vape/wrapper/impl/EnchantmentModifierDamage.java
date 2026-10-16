package gg.vape.wrapper.impl;

public class EnchantmentModifierDamage extends EnchantmentModifier {
   public DamageSource getSource() {
      return new DamageSource(vapeInstance.getMappingsMapperCompat().enchantmentDamageModifier.getSource(this.I));
   }

   public void setSource(DamageSource damageSource) {
      vapeInstance.getMappingsMapperCompat().enchantmentDamageModifier.setSource(this.I, damageSource.getObject());
   }

   public int getDamageModifierValue() {
      return vapeInstance.getMappingsMapperCompat().enchantmentDamageModifier.getDamageModifier(this.I);
   }

   public void setDamageModifier(int damageModifier) {
      vapeInstance.getMappingsMapperCompat().enchantmentDamageModifier.setDamageModifier(this.I, damageModifier);
   }

   public EnchantmentModifierDamage(Object handle) {
      super(handle);
   }
}
