package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;

public class MEvent extends Mapping {
   private static final String SET_CANCELED_METHOD_NAME = "setCanceled";
   private final MappingMethod setCanceledMethod;

   public MEvent() {
      this(MLegacyEntityRenderPreHook.getControlFlowState());
   }

   private MEvent(String[] controlFlowState) {
      super(MappedClasses.S);
      Class[] parameterTypes = new Class[]{boolean.class};
      Class<Void> returnType = void.class;
      boolean methodPublic = false;
      String methodName = "setCanceled";
      this.setCanceledMethod = this.Y(methodName, methodPublic, returnType, parameterTypes);
      if (GuiComponent.getLegacyComponentState() == null) {
         MLegacyEntityRenderPreHook.setControlFlowState(new String[5]);
      }
   }

   public void setCanceled(Object eventHandle, boolean canceled) {
      this.setCanceledMethod.invokeVoid(eventHandle, canceled);
   }
}
