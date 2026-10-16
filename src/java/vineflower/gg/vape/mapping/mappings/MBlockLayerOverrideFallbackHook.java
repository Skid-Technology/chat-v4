package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MBlockLayerOverrideFallbackHook extends Mapping {
   private static int controlFlowState;
   private static final String GC_METHOD_NAME = "gc";
   public final MappingMethod gcMethod = this.registerStaticMethod(GC_METHOD_NAME, false, void.class);

   public static void setBlockLayerFallbackControlFlowState(int state) {
      controlFlowState = state;
   }

   public static int getControlFlowConstant() {
      getBlockLayerFallbackControlFlowState();
      return 41;
   }

   public MBlockLayerOverrideFallbackHook() {
      this(getBlockLayerFallbackControlFlowState());
   }

   private MBlockLayerOverrideFallbackHook(int controlFlowState) {
      super(MappedClasses.lA);
   }

   public static int getBlockLayerFallbackControlFlowState() {
      return controlFlowState;
   }

   static {
      setBlockLayerFallbackControlFlowState(0);
   }
}
