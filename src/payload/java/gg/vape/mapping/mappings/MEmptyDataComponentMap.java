package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;

public class MEmptyDataComponentMap extends Mapping {
   private static final String CONSTRUCTOR = "<init>";
   private final MappingMethod constructor = this.Y(CONSTRUCTOR, false, void.class, MappedClasses.zD);
   private static String[] controlFlowState;

   public MEmptyDataComponentMap() {
      this(getControlFlowState());
   }

   private MEmptyDataComponentMap(String[] componentMapControlFlowState) {
      super(MappedClasses.DP);
      if (GuiComponent.getLegacyComponentState() == null) {
         setControlFlowState(new String[5]);
      }
   }

   public Object create(Object defaultComponentsHandle) {
      return this.constructor.newInstance(defaultComponentsHandle);
   }

   public static void setControlFlowState(String[] state) {
      controlFlowState = state;
   }

   public static String[] getControlFlowState() {
      return controlFlowState;
   }

   static {
      setControlFlowState(null);
   }
}
