package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

public class MChannel extends Mapping {
   private MappingMethod usesBlockLightMethod;
   private static GuiComponent[] bakedModelControlFlowState;
   private static final String USES_BLOCK_LIGHT_METHOD_NAME = "usesBlockLight";

   public static GuiComponent[] getBakedModelControlFlowState() {
      return bakedModelControlFlowState;
   }

   public MChannel() {
      this(getBakedModelControlFlowState());
   }

   private MChannel(GuiComponent[] controlFlowState) {
      super(MappedClasses.lc);
      if (ForgeVersion.MC_1_17.d()) {
         Class[] parameterTypes = new Class[0];
         Class<Boolean> returnType = boolean.class;
         boolean methodPublic = true;
         String methodName = USES_BLOCK_LIGHT_METHOD_NAME;
         this.usesBlockLightMethod = this.Y(methodName, methodPublic, returnType, parameterTypes);
      }
   }

   private boolean invokeUsesBlockLight(Object bakedModelHandle) {
      return this.usesBlockLightMethod.invokeBoolean(bakedModelHandle);
   }

   public static void setBakedModelControlFlowState(GuiComponent[] controlFlowState) {
      bakedModelControlFlowState = controlFlowState;
   }

   public static boolean usesBlockLight(MChannel mapping, Object bakedModelHandle) {
      return mapping.invokeUsesBlockLight(bakedModelHandle);
   }

   static {
      setBakedModelControlFlowState(null);
   }
}
