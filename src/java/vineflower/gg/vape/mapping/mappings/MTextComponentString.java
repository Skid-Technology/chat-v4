package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMethod;

public class MTextComponentString extends Mapping {
   private final MappingField textField;
   private final MappingMethod constructor;

   public static String getText(MTextComponentString mapping, Object componentHandle) {
      return mapping.readText(componentHandle);
   }

   public MTextComponentString() {
      super(MappedClasses.z9);
      Class[] constructorParameterTypes = new Class[]{String.class};
      Class<Void> constructorReturnType = void.class;
      boolean constructorPublic = false;
      String constructorName = "<init>";
      this.constructor = this.Y(constructorName, constructorPublic, constructorReturnType, constructorParameterTypes);
      Class<String> textFieldType = String.class;
      boolean textFieldPublic = true;
      String textFieldName = "text";
      this.textField = this.J(textFieldName, textFieldPublic, textFieldType);
   }

   public static Object create(MTextComponentString mapping, String text) {
      return mapping.newInstance(text);
   }

   public static void setText(MTextComponentString mapping, Object componentHandle, String text) {
      mapping.writeText(componentHandle, text);
   }

   private void writeText(Object componentHandle, String text) {
      this.textField.setObject(componentHandle, text);
   }

   private String readText(Object componentHandle) {
      return this.textField.getObject(componentHandle).toString();
   }

   private Object newInstance(String text) {
      return this.constructor.newInstance(text);
   }
}
