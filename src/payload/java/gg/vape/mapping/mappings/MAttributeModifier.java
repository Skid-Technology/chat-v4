package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.UUID;

public class MAttributeModifier extends Mapping {
   private MappingField idField;
   private MappingMethod getIdMethod;
   private final MappingMethod getAmountMethod;
   private final MappingField amountField;

   public static UUID getUuid(MAttributeModifier mapping, Object attributeModifier) {
      return mapping.readUuid(attributeModifier);
   }

   private Object readResourceLocation(Object attributeModifier) {
      return this.idField.getObject(attributeModifier);
   }

   private UUID readUuid(Object attributeModifier) {
      return (UUID)this.getIdMethod.invokeObject(attributeModifier);
   }

   public double getAmount(Object attributeModifier) {
      return this.amountField != null ? this.amountField.getDouble(attributeModifier) : this.getAmountMethod.invokeDouble(attributeModifier);
   }

   public static Object getResourceLocation(MAttributeModifier mapping, Object attributeModifier) {
      return mapping.readResourceLocation(attributeModifier);
   }

   public MAttributeModifier() {
      this(MIAttributeInstance.B());
   }

   private MAttributeModifier(String mappingState) {
      super(MappedClasses.z_);
      if (mappingState != null && ForgeVersion.MC_1_21_0.d()) {
         this.idField = this.J("id", true, MappedClasses.zC);
      } else {
         this.getIdMethod = this.Y("getID", true, UUID.class);
      }

      if (ForgeVersion.MC_1_21_0.d()) {
         this.amountField = this.J("amount", true, double.class);
         this.getAmountMethod = null;
      } else {
         this.amountField = null;
         this.getAmountMethod = this.Y("getAmount", true, double.class);
      }

      if (GuiComponent.getLegacyComponentState() == null) {
         MIAttributeInstance.k("QRcZV");
      }
   }
}
