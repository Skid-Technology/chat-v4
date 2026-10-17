package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import java.util.stream.Stream;

public class MResourceKeyRegistryLookupStreamBridge extends Mapping {
   private static final String LIST_ELEMENTS_METHOD_NAME = "listElements";
   private final MappingMethod listElementsMethod = this.Y("listElements", true, Stream.class);

   public MResourceKeyRegistryLookupStreamBridge() {
      super(MappedClasses.Da);
   }

   public Stream<Object> listElements(Object registryLookup) {
      return (Stream<Object>)this.listElementsMethod.invokeObject(registryLookup);
   }
}
