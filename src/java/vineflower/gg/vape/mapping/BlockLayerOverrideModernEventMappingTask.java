package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventBlockLayerOverrideModern;

public class BlockLayerOverrideModernEventMappingTask extends JavassistMappingTask {
   public BlockLayerOverrideModernEventMappingTask() {
      super(MappedClasses.qa);
   }

   @Override
   public void transform() {
      MappingMethod mappingMethod = Vape.INSTANCE.getMappings().qg.S;
      EventInjectionSpec eventInjectionSpec = new EventInjectionSpec(mappingMethod, EventBlockLayerOverrideModern.class);
      eventInjectionSpec.setConstructorArguments("$0");
      eventInjectionSpec.setReturnExpression("($r) $event.getBlockLayer()");
      this.registerEventInjection(eventInjectionSpec);
   }
}
