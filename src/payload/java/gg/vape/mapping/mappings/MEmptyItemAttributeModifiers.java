package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MEmptyItemAttributeModifiers extends Mapping {
   private static final String CREATE_METHOD_NAME = "create";
   private final MappingMethod createMethod = this.registerStaticMethod("create", false, MappedClasses.V1);

   public static Object create(MEmptyItemAttributeModifiers mapping) {
      return mapping.invokeCreate();
   }

   private Object invokeCreate() {
      return this.createMethod.invokeObject(null);
   }

   public MEmptyItemAttributeModifiers() {
      super(MappedClasses.V1);
   }
}
