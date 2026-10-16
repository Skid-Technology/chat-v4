package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.shaded.javassist.CannotCompileException;
import gg.vape.shaded.javassist.CtBehavior;

public class LegacyRenderStringHookMappingTask extends JavassistMappingTask {
   private static final String c = "#call($1);}";

   public LegacyRenderStringHookMappingTask() {
      super(MappedClasses.Vk);
   }

   @Override
   public void transform() {
      p(LegacyRenderStringEventRender3DCallback.class);
      MappingMethod mappingMethod = Vape.INSTANCE.getMappings().C5.A;
      CtBehavior ctBehavior = this.F(mappingMethod);

      try {
         ctBehavior.insertBefore("{" + LegacyRenderStringEventRender3DCallback.class.getName() + "#call($1);}");
      } catch (CannotCompileException var4) {
         Vape.logThrowable(var4);
      }
   }
}
