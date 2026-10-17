package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventWorldTime;

public class WorldTimeEventMappingTask extends JavassistMappingTask {
   @Override
   public void transform() {
      EventInjectionSpec eventInjectionSpec = new EventInjectionSpec(Vape.INSTANCE.getMappings().worldInfo.getWorldTimeMethod, EventWorldTime.class);
      eventInjectionSpec.setConstructorArguments("$0");
      eventInjectionSpec.setReturnExpression("$event.getWorldTime()");
      this.registerEventInjection(eventInjectionSpec);
   }

   public WorldTimeEventMappingTask() {
      super(MappedClasses.WORLD_INFO);
   }
}
