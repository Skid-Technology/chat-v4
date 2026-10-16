package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;

public class MSetVisibility extends Mapping {
   public final MappingMethod setAllVisibleMethod;
   public final MappingMethod constructor;

   public void setAllVisible(Object visibilitySet, boolean visible) {
      this.setAllVisibleMethod.invokeVoid(visibilitySet, visible);
   }

   public MSetVisibility() {
      this(MVisGraph.getVisGraphControlFlowMarker());
   }

   private MSetVisibility(String controlFlowMarker) {
      super(MappedClasses.SET_VISIBILITY);
      Class[] constructorParameterTypes = new Class[0];
      Class<Void> constructorReturnType = void.class;
      boolean remapConstructor = false;
      String constructorName = "<init>";
      this.constructor = this.Y(constructorName, remapConstructor, constructorReturnType, constructorParameterTypes);
      if (controlFlowMarker != null) {
         Class[] parameterTypes = new Class[]{boolean.class};
         Class<Void> returnType = void.class;
         boolean remap = true;
         String methodName = "setAllVisible";
         this.setAllVisibleMethod = this.Y(methodName, remap, returnType, parameterTypes);
      } else {
         Class[] parameterTypes = new Class[]{boolean.class};
         Class<Void> returnType = void.class;
         boolean remap = true;
         String methodName = "setAllVisible";
         this.setAllVisibleMethod = this.Y(methodName, remap, returnType, parameterTypes);
         GuiComponent.setLegacyComponentState(new GuiComponent[2]);
      }
   }
}
