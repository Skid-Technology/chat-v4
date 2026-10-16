package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import java.util.ArrayList;
import java.util.List;

public class MLists extends Mapping {
   public final MappingMethod reverseMethod = this.registerStaticMethod("reverse", false, List.class, List.class);
   public final MappingMethod newArrayListMethod = this.registerStaticMethod("newArrayList", false, ArrayList.class, Iterable.class);

   public ArrayList newArrayList(Iterable iterable) {
      return (ArrayList)this.newArrayListMethod.invokeObject(null, iterable);
   }

   public List reverse(List list) {
      return (List)this.reverseMethod.invokeObject(null, list);
   }

   public MLists() {
      this(MOrdering.getControlFlowMarker());
   }

   private MLists(String controlFlowMarker) {
      super(MappedClasses.VZ);
      if (GuiComponent.getLegacyComponentState() == null) {
         MOrdering.setControlFlowMarker("Ze85D");
      }
   }
}
