package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MItemRenderer;
import gg.vape.wrapper.Wrapper;

public class ItemRenderer extends Wrapper {
   public void g(EntityLivingBase entityLivingBase, ItemStack itemStack, ItemRendererBridge itemRendererBridge) {
      vapeInstance.getMappingsMapperCompat().qE.Z(this.I, entityLivingBase.getObject(), itemStack.getObject(), itemRendererBridge.getObject());
   }

   public ItemRenderer(Object object) {
      super(object);
   }

   public void X(AbstractClientPlayer abstractClientPlayer) {
      MItemRenderer.K(vapeInstance.getMappingsMapperCompat().qE, this.I, abstractClientPlayer.getObject());
   }

   public float e() {
      return vapeInstance.getMappingsMapperCompat().qE.S(this.I);
   }

   public ItemStack k() {
      return new ItemStack(vapeInstance.getMappingsMapperCompat().qE.v(this.I));
   }

   public float R() {
      return vapeInstance.getMappingsMapperCompat().qE.Y(this.I);
   }
}
