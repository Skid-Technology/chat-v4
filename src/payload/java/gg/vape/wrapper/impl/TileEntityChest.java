package gg.vape.wrapper.impl;

public class TileEntityChest extends TileEntity {
   public float getLidOpenness() {
      return ForgeVersion.MC_1_17.d()
         ? vapeInstance.getMappingsMapperCompat().chestTileEntity.getOpenness(this.getObject(), 0.0F)
         : vapeInstance.getMappingsMapperCompat().chestTileEntity.getStoredOpenness(this.getObject());
   }

   public TileEntityChest(Object wrappedObject) {
      super(wrappedObject);
   }

   public int getOpenCount() {
      return vapeInstance.getMappingsMapperCompat().chestTileEntity.getOpenCount(this.getObject());
   }
}
