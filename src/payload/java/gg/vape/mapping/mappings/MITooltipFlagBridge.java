package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.ui.click.component.GuiComponent;

public class MITooltipFlagBridge extends Mapping {
   private static final String WATER_FIELD_NAME = "WATER";
   private final MappingField waterField;
   private static GuiComponent[] fluidModeControlFlowState;

   public static void setFluidModeControlFlowState(GuiComponent[] controlFlowState) {
      fluidModeControlFlowState = controlFlowState;
   }

   public Object getWater() {
      return this.waterField.getObject(null);
   }

   public MITooltipFlagBridge() {
      this(getFluidModeControlFlowState());
   }

   private MITooltipFlagBridge(GuiComponent[] controlFlowState) {
      super(MappedClasses.Y9);
      Class waterFieldType = MappedClasses.Y9;
      boolean waterFieldPublic = true;
      String waterFieldName = WATER_FIELD_NAME;
      this.waterField = this.registerStaticField(waterFieldName, waterFieldPublic, waterFieldType);
      if (GuiComponent.getLegacyComponentState() == null) {
         setFluidModeControlFlowState(new GuiComponent[1]);
      }
   }

   public static GuiComponent[] getFluidModeControlFlowState() {
      return fluidModeControlFlowState;
   }

   static {
      setFluidModeControlFlowState(new GuiComponent[2]);
   }
}
