package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventRenderItemInFirstPerson;

public class ItemRendererFirstPersonEventMappingTask extends JavassistMappingTask {
   private static final String c = "$0, $1";

   @Override
   public void transform() {
      this.c(Vape.INSTANCE.getMappings().qE.S, EventRenderItemInFirstPerson.class, "$0, $1");
   }

   public ItemRendererFirstPersonEventMappingTask() {
      super(MappedClasses.zN);
   }
}
