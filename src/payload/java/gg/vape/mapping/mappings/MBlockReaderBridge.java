package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MBlockReaderBridge extends Mapping {
   private static final String GET_BLOCK_STATE_METHOD_NAME = "getBlockState";
   private final MappingMethod getBlockStateMethod = this.Y("getBlockState", true, MappedClasses.Zl, MappedClasses.lf);

   public Object getBlockState(Object blockReader, Object blockPos) {
      return this.getBlockStateMethod.invokeObject(blockReader, blockPos);
   }

   public MBlockReaderBridge() {
      super(MappedClasses.zJ);
   }
}
