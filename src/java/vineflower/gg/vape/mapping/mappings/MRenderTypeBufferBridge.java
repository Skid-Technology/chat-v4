package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MRenderTypeBufferBridge extends Mapping {
   private static String controlFlowMarker;
   private static final String ON_INPUT_RECEIVED_METHOD_NAME = "onInputReceived";
   private final MappingMethod onInputReceivedMethod;

   public MRenderTypeBufferBridge() {
      this(getControlFlowMarker());
   }

   private MRenderTypeBufferBridge(String marker) {
      super(MappedClasses.lC);
      Class[] parameterTypes = new Class[0];
      Class<Void> returnType = void.class;
      boolean methodPublic = true;
      String methodName = ON_INPUT_RECEIVED_METHOD_NAME;
      this.onInputReceivedMethod = this.Y(methodName, methodPublic, returnType, parameterTypes);
   }

   public static void onInputReceived(MRenderTypeBufferBridge mapping, Object trackerHandle) {
      mapping.onInputReceivedMethod.invokeVoidNoArgs(trackerHandle);
   }

   public static String getControlFlowMarker() {
      return controlFlowMarker;
   }

   public static void setControlFlowMarker(String marker) {
      controlFlowMarker = marker;
   }

   static {
      setControlFlowMarker("bZI7xb");
   }
}
