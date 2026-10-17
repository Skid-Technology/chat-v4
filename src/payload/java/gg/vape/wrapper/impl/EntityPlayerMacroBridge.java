package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MEntityPlayerMacroBridge;
import gg.vape.module.render.proj.EnderPearlProjectileBridge;

public class EntityPlayerMacroBridge extends Entity {
   public Entity A$src$Lgg_vape_wrapper_impl_Entity_$12ijiu4() {
      return (Entity)(ForgeVersion.MC_1_16_5.v()
         ? new EntityPlayer(MEntityPlayerMacroBridge.F(vapeInstance.getMappings().Da, this.I))
         : new EnderPearlProjectileBridge(this.getObject()).getOwnerEntity());
   }

   public Entity r$src$Lgg_vape_wrapper_impl_Entity_$18p7x3h() {
      return new Entity(MEntityPlayerMacroBridge.W(vapeInstance.getMappings().Da, this.I));
   }

   public EntityPlayerMacroBridge(Object object) {
      super(object);
   }

   public boolean o() {
      return MEntityPlayerMacroBridge.B(vapeInstance.getMappings().Da, this.I);
   }
}
