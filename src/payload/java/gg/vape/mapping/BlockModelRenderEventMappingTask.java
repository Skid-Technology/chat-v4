package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventBlockModelRender;

public class BlockModelRenderEventMappingTask extends JavassistMappingTask {
   public BlockModelRenderEventMappingTask() {
      super(MappedClasses.VU);
   }

   @Override
   public void transform() {
      MappingMethod renderModelMethod = Vape.INSTANCE.getMappings().hE.renderModelMethod;
      EventInjectionSpec eventInjectionSpec = new EventInjectionSpec(renderModelMethod, EventBlockModelRender.class);
      eventInjectionSpec.setConstructorArguments("$0, $1, $2, $3, $4, $5, $6");
      eventInjectionSpec.setReturnExpression("$event.getResult()");
      this.registerEventInjection(eventInjectionSpec);
   }
}
