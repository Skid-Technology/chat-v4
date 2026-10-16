package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MGameSettingsGuiScale extends Mapping {
   private static int[] controlFlowState;
   private static final String BASE_FIELD_NAME = "base";
   private MappingField baseField;

   public static void setVecDeltaCodecControlFlowState(int[] state) {
      controlFlowState = state;
   }

   public MGameSettingsGuiScale() {
      super(MappedClasses.Fg);
      Class baseFieldType = MappedClasses.qP;
      boolean baseFieldPublic = true;
      String baseFieldName = BASE_FIELD_NAME;
      this.baseField = this.J(baseFieldName, baseFieldPublic, baseFieldType);
      int[] currentControlFlowState = getVecDeltaCodecControlFlowState();
   }

   private Object readBase(Object codecHandle) {
      return this.baseField.getObject(codecHandle);
   }

   public static int[] getVecDeltaCodecControlFlowState() {
      return controlFlowState;
   }

   public static Object getBase(MGameSettingsGuiScale mapping, Object codecHandle) {
      return mapping.readBase(codecHandle);
   }

   static {
      setVecDeltaCodecControlFlowState(new int[1]);
   }
}
