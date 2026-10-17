package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;

public class MobSpawnerBaseLogic extends Wrapper {
   public MobSpawnerBaseLogic(Object wrappedObject) {
      super(wrappedObject);
   }

   public String getEntityName() {
      if (ForgeVersion.MC_1_12_2.d()) {
         Entity entity = new Entity(
            vapeInstance.getMappings().mobSpawnerBaseLogic.getCachedEntity(this.I, ForgeVersion.MC_1_17.d() ? Minecraft.theWorld().getObject() : null)
         );
         return entity.isNotNull() ? entity.getName() : "";
      } else {
         return vapeInstance.getMappings().mobSpawnerBaseLogic.getEntityNameToSpawn(this.I);
      }
   }
}
