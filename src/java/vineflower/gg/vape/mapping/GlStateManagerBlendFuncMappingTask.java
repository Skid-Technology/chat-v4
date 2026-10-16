package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.shaded.javassist.CannotCompileException;
import gg.vape.shaded.javassist.CtBehavior;

public class GlStateManagerBlendFuncMappingTask extends JavassistMappingTask {
   @Override
   public void transform() {
      MappingMethod mappingMethod = Vape.INSTANCE.getMappings().Dt.O;
      CtBehavior ctBehavior = this.F(mappingMethod);

      try {
         String string = "{if(" + InsertedCallbackLockMarker.class.getName() + "#check($1, $2)) {   return;}}";
         ctBehavior.insertBefore(string);
      } catch (CannotCompileException var4) {
         Vape.logThrowable(var4);
      }
   }

   public GlStateManagerBlendFuncMappingTask() {
      super(MappedClasses.K);
   }
}
