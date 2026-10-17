package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MItemRendererBridge extends Mapping {
   private static final String FIRST_PERSON_FIELD_NAME = "FIRST_PERSON";
   private static String controlFlowState;
   private MappingField firstPersonField;

   public static void setItemCameraTransformControlFlowState(String state) {
      controlFlowState = state;
   }

   public Object getFirstPerson() {
      return this.firstPersonField.getObject(null);
   }

   public MItemRendererBridge() {
      this(getItemCameraTransformControlFlowState());
   }

   private MItemRendererBridge(String state) {
      super(MappedClasses.z0);
      Class firstPersonFieldType = MappedClasses.z0;
      boolean firstPersonFieldPublic = true;
      String firstPersonFieldName = FIRST_PERSON_FIELD_NAME;
      this.firstPersonField = this.registerStaticField(firstPersonFieldName, firstPersonFieldPublic, firstPersonFieldType);
   }

   public static String getItemCameraTransformControlFlowState() {
      return controlFlowState;
   }

   static {
      setItemCameraTransformControlFlowState(null);
   }
}
