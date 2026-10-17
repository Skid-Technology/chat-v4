package gg.vape.mapping;

import gg.vape.Vape;

public class ThreadBoundTickEventMappingTask extends JavassistMappingTask {
   public ThreadBoundTickEventMappingTask() {
      super(MappedClasses.zY);
   }

   @Override
   public void transform() {
      p(ThreadBoundEventPreTickCallback.class);
      p(ThreadBoundEventPostTickCallback.class);
      MappingMethod mappingMethod = Vape.INSTANCE.getMappings().itemBucketUse.useMethod;
      MappingMethod mappingMethod2 = Vape.INSTANCE.getMappings().CF.p;
      this.k(mappingMethod, mappingMethod2, ThreadBoundEventPreTickCallback.class.getName() + "#call", true, false);
      this.k(mappingMethod, mappingMethod2, ThreadBoundEventPostTickCallback.class.getName() + "#call", false, false);
   }
}
