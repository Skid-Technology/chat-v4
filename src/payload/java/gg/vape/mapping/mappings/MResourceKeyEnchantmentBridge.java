package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.ui.click.component.GuiComponent;

public class MResourceKeyEnchantmentBridge extends Mapping {
   private MappingField enchantmentField;
   private static boolean enchantmentRegistryControlFlowState;
   private static final String ENCHANTMENT_FIELD_NAME = "ENCHANTMENT";

   public static Object getEnchantment(MResourceKeyEnchantmentBridge mapping) {
      return mapping.readEnchantment();
   }

   public static void setEnchantmentRegistryControlFlowState(boolean state) {
      enchantmentRegistryControlFlowState = state;
   }

   public static boolean getEnchantmentRegistryControlFlowState() {
      return enchantmentRegistryControlFlowState;
   }

   public static boolean shouldUpdateLegacyState() {
      boolean controlFlowState = getEnchantmentRegistryControlFlowState();
      return false;
   }

   public MResourceKeyEnchantmentBridge() {
      super(MappedClasses.a);
      Class enchantmentFieldType = MappedClasses.qB;
      boolean enchantmentFieldPublic = true;
      String enchantmentFieldName = ENCHANTMENT_FIELD_NAME;
      this.enchantmentField = this.registerStaticField(enchantmentFieldName, enchantmentFieldPublic, enchantmentFieldType);
      if (shouldUpdateLegacyState()) {
         GuiComponent.setLegacyComponentState(new GuiComponent[1]);
      }
   }

   private Object readEnchantment() {
      return this.enchantmentField.getObject(null);
   }

   static {
      setEnchantmentRegistryControlFlowState(true);
   }
}
