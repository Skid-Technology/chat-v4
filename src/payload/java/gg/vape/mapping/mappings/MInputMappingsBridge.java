package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MInputMappingsBridge extends Mapping {
   private static final String GET_INPUT_BY_CODE = "getInputByCode";
   private final MappingMethod getInputByCodeMethod = this.registerStaticMethod("getInputByCode", true, MappedClasses.zp, int.class, int.class);

   public MInputMappingsBridge() {
      super(MappedClasses.Q);
   }

   public Object getInputByCode(int keyCode, int scanCode) {
      return this.getInputByCodeMethod.invokeObject(null, keyCode, scanCode);
   }

   public Object getInputByCode(Object keyEvent) {
      return this.getInputByCodeMethod.invokeObject(null, keyEvent);
   }
}
