package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventChunkRenderRebuild;

public class ChunkRenderRebuildEventMappingTask extends JavassistMappingTask {
   private static final String c = "($r) 1";

   @Override
   public void transform() {
      MappingMethod mappingMethod = Vape.INSTANCE.getMappings().qg.w;
      EventInjectionSpec eventInjectionSpec = new EventInjectionSpec(mappingMethod, EventChunkRenderRebuild.class);
      eventInjectionSpec.setReturnExpression("($r) 1");
      this.registerEventInjection(eventInjectionSpec);
   }

   public ChunkRenderRebuildEventMappingTask() {
      super(MappedClasses.Zk);
   }
}
