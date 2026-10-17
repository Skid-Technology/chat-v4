package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventPlayerTabOverlayDisplayName;

public class PlayerTabOverlayDisplayNameMappingTask extends JavassistMappingTask {
   @Override
   public void transform() {
      EventInjectionSpec eventInjectionSpec = new EventInjectionSpec(Vape.INSTANCE.getMappings().hP.O, EventPlayerTabOverlayDisplayName.class);
      eventInjectionSpec.setConstructorArguments("$0, $1");
      eventInjectionSpec.setReturnExpression("$event.getDisplayNameInstance()");
      this.registerEventInjection(eventInjectionSpec);
   }

   public PlayerTabOverlayDisplayNameMappingTask() {
      super(MappedClasses.lF);
   }
}
