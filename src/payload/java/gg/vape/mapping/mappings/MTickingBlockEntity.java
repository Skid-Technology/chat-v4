package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MTickingBlockEntity extends Mapping {
   private static int[] controlFlowState;
   private final MappingMethod getPosMethod;
   private static final String GET_POS_METHOD_NAME = "getPos";

   public MTickingBlockEntity() {
      super(MappedClasses.TICKING_BLOCK_ENTITY);
      this.getPosMethod = this.Y(GET_POS_METHOD_NAME, true, MappedClasses.lf);
   }

   private Object invokeGetPos(Object tickingBlockEntity) {
      return this.getPosMethod.invokeObject(tickingBlockEntity);
   }

   public static Object getPos(MTickingBlockEntity mappings, Object tickingBlockEntity) {
      return mappings.invokeGetPos(tickingBlockEntity);
   }

   public static int[] getTickingBlockEntityControlFlowState() {
      return controlFlowState;
   }

   public static void setTickingBlockEntityControlFlowState(int[] state) {
      controlFlowState = state;
   }

   static {
      setTickingBlockEntityControlFlowState(new int[5]);
   }
}
