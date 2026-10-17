package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MWorldProvider extends Mapping {
   private static final String GET_HAS_NO_SKY_METHOD_NAME = "getHasNoSky";
   private final MappingMethod getHasNoSkyMethod = this.Y("getHasNoSky", true, boolean.class);

   public MWorldProvider() {
      super(MappedClasses.WORLD_PROVIDER);
   }

   public boolean hasNoSky(Object worldProvider) {
      return this.getHasNoSkyMethod.invokeBoolean(worldProvider);
   }
}
