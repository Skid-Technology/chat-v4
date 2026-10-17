package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.ui.click.component.GuiComponent;

public class MBiomeRegistryName extends Mapping {
   private static final String NAME_FIELD_NAME = "name";
   private final MappingField nameField = this.J("name", true, String.class);

   public MBiomeRegistryName() {
      this(MBiomeRegistrySwitch.getBiomeRegistryControlFlowState());
   }

   private MBiomeRegistryName(int[] biomeRegistryControlFlowState) {
      super(MappedClasses.h);
      if (biomeRegistryControlFlowState == null) {
         GuiComponent.setLegacyComponentState(new GuiComponent[5]);
      }
   }

   private String readName(Object registryName) {
      return (String)this.nameField.getObject(registryName);
   }

   public static String getName(MBiomeRegistryName mapping, Object registryName) {
      return mapping.readName(registryName);
   }
}
