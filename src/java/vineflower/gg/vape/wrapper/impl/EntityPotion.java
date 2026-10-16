package gg.vape.wrapper.impl;

public class EntityPotion extends EntityEnderPearl {
   public EntityPotion(Object potionEntityHandle) {
      super(potionEntityHandle);
   }

   public ItemStack getPotion() {
      Object itemHandle = vapeInstance.getMappingsMapperCompat().entityPotion.getPotionItem(this.getObject());
      return new ItemStack(itemHandle);
   }
}
