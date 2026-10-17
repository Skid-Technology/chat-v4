package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class WorldNameable extends Wrapper {
   public ITextComponent getDisplayName() {
      return new ITextComponent(vapeInstance.getMappingsMapperCompat().Dm.A(this.I));
   }

   public boolean hasCustomName() {
      return vapeInstance.getMappingsMapperCompat().Dm.V(this.I);
   }

   public String getName() {
      if (ForgeVersion.MC_1_16_5.d()) {
         ITextComponent iTextComponent = new ITextComponent(vapeInstance.getMappingsMapperCompat().Dm.s(this.I));
         return iTextComponent.getFormattedText();
      } else {
         return vapeInstance.getMappingsMapperCompat().Dm.L(this.I);
      }
   }

   public WorldNameable(Object object) {
      super(object);
   }
}
