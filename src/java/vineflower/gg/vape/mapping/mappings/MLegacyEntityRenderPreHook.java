package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;

public class MLegacyEntityRenderPreHook extends Mapping {
   private static final String CONSTRUCTOR_NAME = "<init>";
   private static String[] controlFlowState;
   public final MappingMethod constructorMethod;

   public static String[] getControlFlowState() {
      return controlFlowState;
   }

   public static void setControlFlowState(String[] state) {
      controlFlowState = state;
   }

   public MLegacyEntityRenderPreHook() {
      super(MappedClasses.x);
      Class[] parameterTypes = new Class[]{MappedClasses.zm, MappedClasses.Fq, double.class, double.class, double.class};
      Class<Void> returnType = void.class;
      boolean methodPublic = false;
      String methodName = CONSTRUCTOR_NAME;
      this.constructorMethod = this.Y(methodName, methodPublic, returnType, parameterTypes);
      if (getControlFlowState() != null) {
         GuiComponent.setLegacyComponentState(new GuiComponent[4]);
      }
   }

   static {
      setControlFlowState(null);
   }
}
