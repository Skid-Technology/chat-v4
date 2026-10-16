package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;

public class MRegistryAccessBridge extends Mapping {
   private static final String LOOKUP_OR_THROW_METHOD_NAME = "lookupOrThrow";
   private final MappingMethod lookupOrThrowMethod = this.Y("lookupOrThrow", true, MappedClasses.l1, MappedClasses.qB);

   public Object lookupOrThrow(Object registryAccess, Object resourceKey) {
      return this.lookupOrThrowMethod.invokeObject(registryAccess, resourceKey);
   }

   public MRegistryAccessBridge() {
      super(MappedClasses.zi);
   }
}
