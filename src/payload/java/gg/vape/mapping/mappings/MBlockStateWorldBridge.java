package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MBlockStateWorldBridge extends Mapping {
   private final MappingMethod getTypeMethod = this.Y("getType", true, MappedClasses.VR);
   private final MappingMethod isTagMethod;
   private final MappingMethod isEmptyMethod = this.Y("isEmpty", true, boolean.class);
   private static boolean fluidStateControlFlowState;
   private final MappingMethod getHeightMethod;

   public MBlockStateWorldBridge() {
      super(MappedClasses.Dw);
      this.isTagMethod = this.Y("is", true, boolean.class, MappedClasses.qC);
      this.getHeightMethod = this.Y("getHeight", true, float.class, MappedClasses.zJ, MappedClasses.lf);
   }

   public static void setFluidStateControlFlowState(boolean state) {
      fluidStateControlFlowState = state;
   }

   public Object getType(Object fluidState) {
      return this.getTypeMethod.invokeObject(fluidState);
   }

   public boolean isEmpty(Object fluidState) {
      return this.isEmptyMethod.invokeBoolean(fluidState);
   }

   public static boolean getControlFlowSentinel() {
      return false;
   }

   public static boolean getFluidStateControlFlowState() {
      return fluidStateControlFlowState;
   }

   public float getHeight(Object fluidState, Object world, Object blockPosition) {
      return this.getHeightMethod.invokeFloat(fluidState, world, blockPosition);
   }

   public boolean isTag(Object fluidState, Object tag) {
      return this.isTagMethod.invokeBoolean(fluidState, tag);
   }

   static {
      setFluidStateControlFlowState(true);
   }
}
