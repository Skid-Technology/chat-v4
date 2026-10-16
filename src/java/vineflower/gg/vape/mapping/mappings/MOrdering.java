package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import java.util.List;

public class MOrdering extends Mapping {
   public final MappingMethod sortedCopyMethod;
   private static final String SORTED_COPY_METHOD_NAME = "sortedCopy";
   private static String controlFlowMarker;

   public static String getControlFlowMarker() {
      return controlFlowMarker;
   }

   public static void setControlFlowMarker(String marker) {
      controlFlowMarker = marker;
   }

   public List sortedCopy(Object ordering, Iterable iterable) {
      return (List)this.sortedCopyMethod.invokeObject(ordering, iterable);
   }

   public MOrdering() {
      super(MappedClasses.F4);
      this.sortedCopyMethod = this.Y(SORTED_COPY_METHOD_NAME, false, List.class, Iterable.class);
   }

   static {
      setControlFlowMarker("XPzfA");
   }
}
