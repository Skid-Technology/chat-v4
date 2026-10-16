package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventBlockRenderColorOpacity;

public class BlockRenderColorOpacityMappingTask extends JavassistMappingTask {
   private static final String c = "$0, $1, $2, $3, $4";

   public BlockRenderColorOpacityMappingTask() {
      super(MappedClasses.lX);
   }

   @Override
   public void transform() {
      MappingMethod mappingMethod = Vape.INSTANCE.getMappings().qZ.b;
      EventInjectionSpec eventInjectionSpec = new EventInjectionSpec(mappingMethod, EventBlockRenderColorOpacity.class);
      eventInjectionSpec.setInsertBefore(false);
      eventInjectionSpec.setConstructorArguments("$0, $1, $2, $3, $4");
      this.registerEventInjection(eventInjectionSpec);
   }
}
