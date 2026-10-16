package gg.vape.wrapper.impl;

public class TileEntityMobSpawner extends TileEntity {
   public MobSpawnerBaseLogic getSpawnerBaseLogic() {
      return new MobSpawnerBaseLogic(vapeInstance.getMappingsMapperCompat().mobSpawnerTileEntity.getSpawnerBaseLogic(this.I));
   }

   public TileEntityMobSpawner(Object wrappedObject) {
      super(wrappedObject);
   }
}
