package gg.vape.shaded.javassist.expr;

import gg.vape.shaded.javassist.CtClass;
import gg.vape.shaded.javassist.CtConstructor;
import gg.vape.shaded.javassist.CtMethod;
import gg.vape.shaded.javassist.NotFoundException;
import gg.vape.shaded.javassist.bytecode.CodeIterator;
import gg.vape.shaded.javassist.bytecode.MethodInfo;

public class ConstructorCall extends MethodCall {
   protected ConstructorCall(int pos, CodeIterator i, CtClass decl, MethodInfo m) {
      super(pos, i, decl, m);
   }

   @Override
   public String getMethodName() {
      return this.isSuper() ? "super" : "this";
   }

   @Override
   public CtMethod getMethod() throws NotFoundException {
      throw new NotFoundException("this is a constructor call.  Call getConstructor().");
   }

   public CtConstructor getConstructor() throws NotFoundException {
      return this.getCtClass().getConstructor(this.getSignature());
   }

   @Override
   public boolean isSuper() {
      return super.isSuper();
   }
}
