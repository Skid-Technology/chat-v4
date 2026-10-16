package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MTextComponentTranslationBridge extends Mapping {
   private MappingMethod getModelIdentityMethod;
   private static final String GET_MODEL_IDENTITY_METHOD_NAME = "getModelIdentity";
   private static boolean controlFlowStateEnabled;

   public MTextComponentTranslationBridge() {
      super(MappedClasses.zE);
      Class[] parameterTypes = new Class[0];
      Class<Object> returnType = Object.class;
      boolean methodPublic = true;
      String methodName = GET_MODEL_IDENTITY_METHOD_NAME;
      this.getModelIdentityMethod = this.Y(methodName, methodPublic, returnType, parameterTypes);
   }

   public Object getModelIdentity(Object renderStateHandle) {
      return this.getModelIdentityMethod.invokeObject(renderStateHandle);
   }

   public static boolean isControlFlowStateDisabled() {
      boolean enabled = isControlFlowStateEnabled();
      return !enabled;
   }

   public static boolean isControlFlowStateEnabled() {
      return controlFlowStateEnabled;
   }

   public static void setControlFlowStateEnabled(boolean enabled) {
      controlFlowStateEnabled = enabled;
   }

   static {
      setControlFlowStateEnabled(true);
   }
}
