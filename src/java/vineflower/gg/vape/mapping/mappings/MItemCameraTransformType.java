package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.ui.click.component.GuiComponent;

public class MItemCameraTransformType extends Mapping {
   private static final String ITEMS_FIELD_NAME = "items";
   private MappingField itemsField;
   private static boolean repairableControlFlowState;

   public static boolean getRepairableControlFlowState() {
      return repairableControlFlowState;
   }

   public static boolean shouldSkipLegacyStateUpdate() {
      boolean controlFlowState = getRepairableControlFlowState();
      return true;
   }

   public MItemCameraTransformType() {
      super(MappedClasses.Z4);
      Class itemsFieldType = MappedClasses.u_;
      boolean itemsFieldPublic = true;
      String itemsFieldName = ITEMS_FIELD_NAME;
      this.itemsField = this.J(itemsFieldName, itemsFieldPublic, itemsFieldType);
      if (!shouldSkipLegacyStateUpdate()) {
         GuiComponent.setLegacyComponentState(new GuiComponent[2]);
      }
   }

   public Object getItems(Object repairableHandle) {
      return this.itemsField.getObject(repairableHandle);
   }

   public static void setRepairableControlFlowState(boolean state) {
      repairableControlFlowState = state;
   }

   static {
      setRepairableControlFlowState(false);
   }
}
