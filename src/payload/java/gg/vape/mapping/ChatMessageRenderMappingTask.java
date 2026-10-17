package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventChatMessageRender;

public class ChatMessageRenderMappingTask extends JavassistMappingTask {
   @Override
   public void transform() {
      this.registerChatMessageRenderEvent();
   }

   public ChatMessageRenderMappingTask() {
      super(MappedClasses.d);
   }

   private void registerChatMessageRenderEvent() {
      MappingMethod mappingMethod = Vape.INSTANCE.getMappings().chatMessageRenderTarget.addMessageMethod;
      if (mappingMethod != null && !mappingMethod.hasResolutionFailed()) {
         EventInjectionSpec eventInjectionSpec = new EventInjectionSpec(mappingMethod, EventChatMessageRender.class);
         eventInjectionSpec.setConstructorArguments("$0, $1, $2, $3");
         eventInjectionSpec.setAfterCode("$1 = (" + MappedClasses.Yr.getName() + ") $event.getOutputContentComponent();");
         this.registerEventInjection(eventInjectionSpec);
      }
   }
}
