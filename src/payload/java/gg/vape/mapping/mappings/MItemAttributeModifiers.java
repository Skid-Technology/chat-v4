package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import java.util.Collection;

public class MItemAttributeModifiers extends Mapping {
   public MappingMethod sizeMethod;
   public MappingMethod putMethod;
   public MappingMethod valuesMethod;

   public boolean put(Object multimapHandle, Object key, Object value) {
      return this.putMethod.invokeBoolean(multimapHandle, key, value);
   }

   public int size(Object multimapHandle) {
      return this.sizeMethod.invokeInt(multimapHandle);
   }

   public MItemAttributeModifiers() {
      this(MOrdering.getControlFlowMarker());
   }

   private MItemAttributeModifiers(String controlFlowMarker) {
      super(MappedClasses.Yb);
      if (controlFlowMarker != null) {
         Class[] sizeParameterTypes = new Class[0];
         Class<Integer> sizeReturnType = int.class;
         boolean sizePublic = false;
         String sizeMethodName = "size";
         this.sizeMethod = this.Y(sizeMethodName, sizePublic, sizeReturnType, sizeParameterTypes);
         Class[] valuesParameterTypes = new Class[0];
         Class<Collection> valuesReturnType = Collection.class;
         boolean valuesPublic = false;
         String valuesMethodName = "values";
         this.valuesMethod = this.Y(valuesMethodName, valuesPublic, valuesReturnType, valuesParameterTypes);
         Class[] putParameterTypes = new Class[]{Object.class, Object.class};
         Class<Boolean> putReturnType = boolean.class;
         boolean putPublic = false;
         String putMethodName = "put";
         this.putMethod = this.Y(putMethodName, putPublic, putReturnType, putParameterTypes);
      } else {
         Class[] sizeParameterTypes = new Class[0];
         Class<Integer> sizeReturnType = int.class;
         boolean sizePublic = false;
         String sizeMethodName = "size";
         this.sizeMethod = this.Y(sizeMethodName, sizePublic, sizeReturnType, sizeParameterTypes);
         Class[] valuesParameterTypes = new Class[0];
         Class<Collection> valuesReturnType = Collection.class;
         boolean valuesPublic = false;
         String valuesMethodName = "values";
         this.valuesMethod = this.Y(valuesMethodName, valuesPublic, valuesReturnType, valuesParameterTypes);
         Class[] putParameterTypes = new Class[]{Object.class, Object.class};
         Class<Boolean> putReturnType = boolean.class;
         boolean putPublic = false;
         String putMethodName = "put";
         this.putMethod = this.Y(putMethodName, putPublic, putReturnType, putParameterTypes);
         GuiComponent.setLegacyComponentState(new GuiComponent[4]);
      }
   }

   public Collection values(Object multimapHandle) {
      return (Collection)this.valuesMethod.invokeObject(multimapHandle);
   }
}
