package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MGuiSpriteManager extends Mapping {
   private final MappingMethod getSpriteMethod = this.Y("getSprite", true, MappedClasses.Db, MappedClasses.zC);

   public MGuiSpriteManager() {
      super(MappedClasses.D_);
   }

   public Object getSprite(Object spriteManager, Object resourceLocation) {
      return this.getSpriteMethod.invokeObject(spriteManager, resourceLocation);
   }
}
