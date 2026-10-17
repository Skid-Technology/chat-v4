package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.impl.ForgeVersion;

public class MVoxelShapeBridge extends Mapping {
   private MappingMethod bufferSourceMethod;

   public MVoxelShapeBridge() {
      super(MappedClasses.ZL);
      if (ForgeVersion.MC_26_2.v()) {
         this.bufferSourceMethod = this.Y("bufferSource", true, MappedClasses.lp);
      }
   }

   public Object getBufferSource(Object renderer) {
      return this.bufferSourceMethod == null ? null : this.bufferSourceMethod.invokeObject(renderer);
   }
}
