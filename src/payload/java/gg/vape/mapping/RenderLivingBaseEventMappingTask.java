package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventBlockRenderColorOverride;
import gg.vape.mapping.expr.EventBlockRenderColorOverrideExprEditor;
import javassist.CtBehavior;
import gg.vape.wrapper.impl.ForgeVersion;

public class RenderLivingBaseEventMappingTask extends JavassistMappingTask {
   private static Exception a(Exception exception) {
      return exception;
   }

   public RenderLivingBaseEventMappingTask() {
      super(MappedClasses.Fq);
   }

   @Override
   public void transform() {
      if (ForgeVersion.MC_1_8_9.d()) {
         MappingMethod mappingMethod = Vape.INSTANCE.getMappings().CP.O;
         String string = EventBlockRenderColorOverride.class.getName();
         CtBehavior ctBehavior = this.F(mappingMethod);
         this.O(mappingMethod, EventBlockRenderColorOverride.class, "", "false");

         try {
            ctBehavior.instrument(new EventBlockRenderColorOverrideExprEditor(this, string));
         } catch (Exception var5) {
            Vape.logThrowable(var5);
         }
      }

      if (ForgeVersion.MC_1_7_10.L() && Vape.INSTANCE.isForgeAbsent()) {
         MappingMethod mappingMethod = Vape.INSTANCE.getMappings().CP.r;
         this.c(mappingMethod, EventPreRenderEntityForgeCallback.class, "$1, $2, $3, $4");
      }
   }
}
