package gg.vape.mapping.expr;

import gg.vape.Vape;
import gg.vape.event.impl.EventRender2D;
import gg.vape.mapping.EntityRendererEventMappingTask;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import gg.vape.wrapper.impl.ForgeVersion;
import java.util.concurrent.atomic.AtomicBoolean;

public class EventRender2DInjectionExprEditor extends ExprEditor {
   final EntityRendererEventMappingTask N;
   final AtomicBoolean g;
   final String s;

   public EventRender2DInjectionExprEditor(EntityRendererEventMappingTask entityRendererEventMappingTask, String string, AtomicBoolean atomicBoolean) {
      this.N = entityRendererEventMappingTask;
      this.s = string;
      this.g = atomicBoolean;
   }

   private static CannotCompileException a(CannotCompileException cannotCompileException) {
      return cannotCompileException;
   }

   @Override
   public void edit(MethodCall methodCall) throws CannotCompileException {
      if (methodCall.getSignature().equals(this.s)) {
         if (!ForgeVersion.MC_1_7_10.L() || methodCall.getMethodName().equals("glAlphaFunc")) {
            if (!ForgeVersion.MC_1_7_10.Y()
               || !ForgeVersion.MC_1_16_5.v()
               || methodCall.getMethodName().equals(Vape.INSTANCE.getMappings().Dt.q.getResolvedName())) {
               if (!ForgeVersion.MC_1_16_5.d()
                  || !ForgeVersion.MC_1_17.v()
                  || methodCall.getMethodName().equals(Vape.INSTANCE.getMappings().f.v.getResolvedName())) {
                  methodCall.replace("{ $_ = $proceed($$); " + EventRender2D.class.getName() + "#create(); }");
                  this.g.set(true);
               }
            }
         }
      }
   }
}
