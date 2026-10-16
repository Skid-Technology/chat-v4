package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.shaded.javassist.CannotCompileException;
import gg.vape.shaded.javassist.CtBehavior;

public class ThreadBoundCallbackMappingTask extends JavassistMappingTask {
   private static final String c = "#call();}";

   @Override
   public void transform() {
      MappingMethod mappingMethod = Vape.INSTANCE.getMappings().glCommandEncoder.drawFromBuffersMethod;
      CtBehavior ctBehavior = this.F(mappingMethod);

      try {
         ctBehavior.insertBefore("{" + RenderBatchFlushCallbackMarker.class.getName() + "#call();}");
      } catch (CannotCompileException var4) {
         Vape.logThrowable(var4);
      }
   }

   public ThreadBoundCallbackMappingTask() {
      super(MappedClasses.zg);
   }
}
