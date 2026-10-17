package gg.vape.wrapper.impl;

import gg.vape.mapping.MappedClasses;

public class TileEntityOpenedChest extends TileEntity {
   public float getLidAngle() {
      return vapeInstance.getMappingsMapperCompat().h5.Z(this.getObject());
   }

   public int getNumPlayersUsing() {
      if (ForgeVersion.MC_1_16_5.d()) {
         return this.isInstance(MappedClasses.qH) ? 1 : 0;
      } else if (ForgeVersion.MC_1_12_2.d()) {
         OpenedChestTypeSentinel openedChestTypeSentinel = new OpenedChestTypeSentinel(vapeInstance.getMappingsMapperCompat().h5.b(this.getObject()));
         if (openedChestTypeSentinel.isNull()) {
            return 0;
         } else {
            return vapeInstance.getMappingsMapperCompat().h5.b(this.getObject()).equals(OpenedChestTypeSentinel.basic().getObject()) ? 0 : 1;
         }
      } else {
         return vapeInstance.getMappingsMapperCompat().h5.s(this.getObject());
      }
   }

   public TileEntityOpenedChest(Object object) {
      super(object);
   }

   public float Y() {
      return ForgeVersion.MC_1_17.d()
         ? vapeInstance.getMappingsMapperCompat().h5.o(this.getObject(), 0.0F)
         : vapeInstance.getMappingsMapperCompat().h5.G(this.getObject());
   }
}
