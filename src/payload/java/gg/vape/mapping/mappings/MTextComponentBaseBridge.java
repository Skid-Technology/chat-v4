package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.List;

public class MTextComponentBaseBridge extends Mapping {
   private static final String SET_STYLE_METHOD_NAME = "setStyle";
   public MappingMethod constructor;
   private static int[] mutableComponentControlFlowState;
   public MappingMethod setStyleMethod;

   public static int[] getMutableComponentControlFlowState() {
      return mutableComponentControlFlowState;
   }

   public Object create(Object contentsHandle, List siblingHandles, Object styleHandle) {
      return this.constructor.newInstance(contentsHandle, siblingHandles, styleHandle);
   }

   public MTextComponentBaseBridge() {
      this(getMutableComponentControlFlowState());
   }

   private MTextComponentBaseBridge(int[] controlFlowState) {
      super(MappedClasses.uM);
      if (ForgeVersion.MC_1_20_6.d()) {
         Class[] setStyleParameterTypes = new Class[]{MappedClasses.Va};
         Class<?> setStyleReturnType = MappedClasses.uM;
         boolean setStylePublic = true;
         String setStyleMethodName = SET_STYLE_METHOD_NAME;
         this.setStyleMethod = this.Y(setStyleMethodName, setStylePublic, setStyleReturnType, setStyleParameterTypes);
         Class[] constructorParameterTypes = new Class[]{MappedClasses.YT, List.class, MappedClasses.Va};
         this.constructor = this.registerConstructor(constructorParameterTypes);
      }
   }

   public static void setMutableComponentControlFlowState(int[] controlFlowState) {
      mutableComponentControlFlowState = controlFlowState;
   }

   public Object setStyle(Object componentHandle, Object styleHandle) {
      return this.setStyleMethod.invokeObject(componentHandle, styleHandle);
   }

   static {
      setMutableComponentControlFlowState(new int[2]);
   }
}
