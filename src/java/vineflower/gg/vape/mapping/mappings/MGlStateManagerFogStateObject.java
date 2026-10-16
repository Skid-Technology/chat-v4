package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;

public class MGlStateManagerFogStateObject extends Mapping {
   private final MappingMethod resetMethod;
   private final MappingField currentField = this.J("current", true, MappedClasses.zM);

   public Object getCurrent(Object fogStateHandle) {
      return this.currentField.getObject(fogStateHandle);
   }

   public MGlStateManagerFogStateObject() {
      this(MTextComponentTranslationBridge.isControlFlowStateEnabled());
   }

   private MGlStateManagerFogStateObject(boolean controlFlowState) {
      super(MappedClasses.i);
      this.resetMethod = this.Y("reset", true, void.class);
      if (!controlFlowState) {
         GuiComponent.setLegacyComponentState(new GuiComponent[4]);
      }
   }

   public void reset(Object fogStateHandle) {
      this.resetMethod.invokeVoidNoArgs(fogStateHandle);
   }
}
