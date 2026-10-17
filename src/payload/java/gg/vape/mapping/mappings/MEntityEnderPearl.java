package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

public class MEntityEnderPearl extends Mapping {
   private MappingMethod getFuseMethod;
   private MappingField fuseField;
   private static GuiComponent[] controlFlowState;

   public int getFuse(Object entityHandle) {
      return ForgeVersion.MC_1_17.d() ? this.getFuseMethod.invokeInt(entityHandle) : this.fuseField.getInt(entityHandle);
   }

   public MEntityEnderPearl() {
      this(getEntityControlFlowState());
   }

   private MEntityEnderPearl(GuiComponent[] entityControlFlowState) {
      super(MappedClasses.qM);
      if (entityControlFlowState != null) {
         if (ForgeVersion.MC_1_17.d()) {
            this.getFuseMethod = this.Y("getFuse", true, int.class);
         } else {
            this.fuseField = this.J("fuse", true, int.class);
         }

         if (GuiComponent.getLegacyComponentState() == null) {
            setEntityControlFlowState(new GuiComponent[1]);
         }
      } else {
         this.fuseField = this.J("fuse", true, int.class);
         if (GuiComponent.getLegacyComponentState() == null) {
            setEntityControlFlowState(new GuiComponent[1]);
         }
      }
   }

   public static GuiComponent[] getEntityControlFlowState() {
      return controlFlowState;
   }

   public static void setEntityControlFlowState(GuiComponent[] state) {
      controlFlowState = state;
   }

   static {
      setEntityControlFlowState(new GuiComponent[3]);
   }
}
