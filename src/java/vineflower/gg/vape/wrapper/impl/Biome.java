package gg.vape.wrapper.impl;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.mappings.MBiome;
import gg.vape.wrapper.Wrapper;

public class Biome extends Wrapper {
   private static final String b = "Unknown";

   public String n() {
      if (ForgeVersion.MC_1_20_6.d()) {
         ResourceKey resourceKey;
         return this.isInstance(MappedClasses.Vo) && !(resourceKey = new ResourceKey(new Holder(this.I).f().orElse(null))).isNull()
            ? resourceKey.getLocation().getResourcePath()
            : "Unknown";
      } else if (ForgeVersion.MC_1_16_5.d()) {
         BiomeRegistryName biomeRegistryName = new BiomeRegistryName(MBiome.getCategory(vapeInstance.getMappings().Rm, this.I));
         return biomeRegistryName.n();
      } else {
         return vapeInstance.getMappings().Rm.getBiomeName(this.I);
      }
   }

   public Biome(Object object) {
      super(object);
   }
}
