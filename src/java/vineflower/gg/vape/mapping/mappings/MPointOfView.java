package gg.vape.mapping.mappings;

import gg.vape.asm.helper.DescUtils;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MPointOfView extends Mapping {
   private static final String VALUES_METHOD_NAME = "values";
   private final MappingMethod valuesMethod = this.registerStaticMethod("values", true, DescUtils.getArrayType(MappedClasses.ZR));

   public Object[] values() {
      return this.valuesMethod.invokeObjectArray(null);
   }

   public MPointOfView() {
      super(MappedClasses.ZR);
   }
}
