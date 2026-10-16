package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MEnchantmentHelperBridge extends Mapping {
   private final MappingMethod createLookupMethod;
   private static final String CREATE_LOOKUP_METHOD_NAME = "createLookup";
   private static int vanillaRegistriesControlFlowState;

   public MEnchantmentHelperBridge() {
      this(getVanillaRegistriesControlFlowState());
   }

   private MEnchantmentHelperBridge(int controlFlowState) {
      super(MappedClasses.qV);
      Class[] parameterTypes = new Class[0];
      Class returnType = MappedClasses.zi;
      boolean methodPublic = true;
      String methodName = CREATE_LOOKUP_METHOD_NAME;
      this.createLookupMethod = this.registerStaticMethod(methodName, methodPublic, returnType, parameterTypes);
   }

   public static int getControlFlowConstant() {
      int controlFlowState = getVanillaRegistriesControlFlowState();
      return 41;
   }

   public Object createLookup() {
      return this.createLookupMethod.invokeObject(null);
   }

   public static void setVanillaRegistriesControlFlowState(int state) {
      vanillaRegistriesControlFlowState = state;
   }

   public static int getVanillaRegistriesControlFlowState() {
      return vanillaRegistriesControlFlowState;
   }

   static {
      setVanillaRegistriesControlFlowState(0);
   }
}
