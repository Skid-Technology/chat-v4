package gg.vape.mapping;

import gg.vape.Vape;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import gg.vape.wrapper.impl.ForgeVersion;

public class Render2DStaticCallbackMappingTask extends JavassistMappingTask {
   private static final String c = "#call();}";

   private static CannotCompileException a(CannotCompileException cannotCompileException) {
      return cannotCompileException;
   }

   @Override
   public void transform() {
      if (!ForgeVersion.MC_26_1.d()) {
         p(EventRender2DStaticCallback.class);
         MappingMethod mappingMethod = Vape.INSTANCE.getMappings().titledScreen.renderHudMethod;
         if (mappingMethod != null) {
            CtBehavior ctBehavior = this.F(mappingMethod);

            try {
               ctBehavior.insertAfter("{" + EventRender2DStaticCallback.class.getName() + "#call();}");
            } catch (CannotCompileException var4) {
               Vape.logThrowable(var4);
            }
         }
      }
   }

   public Render2DStaticCallbackMappingTask() {
      super(MappedClasses.Zj);
   }
}
