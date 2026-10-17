package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.ui.click.component.GuiComponent;
import java.util.List;

public class MItemAttributeModifiersComponent extends Mapping {
   private static GuiComponent[] controlFlowState;
   private final MappingField modifiersField;
   private static final String MODIFIERS_FIELD_NAME = "modifiers";

   public MItemAttributeModifiersComponent() {
      super(MappedClasses.Dp);
      this.modifiersField = this.J(MODIFIERS_FIELD_NAME, true, List.class);
   }

   public static void setItemAttributeModifiersControlFlowState(GuiComponent[] state) {
      controlFlowState = state;
   }

   public static GuiComponent[] getItemAttributeModifiersControlFlowState() {
      return controlFlowState;
   }

   public List<Object> getModifiers(Object component) {
      return (List<Object>)this.modifiersField.getObject(component);
   }

   static {
      setItemAttributeModifiersControlFlowState(new GuiComponent[4]);
   }
}
