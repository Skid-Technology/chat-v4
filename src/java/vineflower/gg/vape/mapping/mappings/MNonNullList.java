package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.impl.ForgeVersion;

public class MNonNullList extends Mapping {
   private final MappingMethod createMethod;

   public Object create() {
      return ForgeVersion.MC_1_17.d() ? this.createMethod.invokeObject(null) : this.createMethod.newInstance();
   }

   public MNonNullList() {
      this(BlockData.W());
   }

   private MNonNullList(String[] nonNullListMappingState) {
      super(MappedClasses.Vd);
      if (nonNullListMappingState != null) {
         if (ForgeVersion.MC_1_17.d()) {
            this.createMethod = this.registerStaticMethod("create", true, MappedClasses.Vd);
         } else {
            this.createMethod = this.Y("<init>", false, void.class);
         }
      } else {
         this.createMethod = this.Y("<init>", false, void.class);
      }
   }
}
