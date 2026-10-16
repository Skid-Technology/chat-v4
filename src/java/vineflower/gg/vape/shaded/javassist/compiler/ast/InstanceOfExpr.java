package gg.vape.shaded.javassist.compiler.ast;

import gg.vape.shaded.javassist.compiler.CompileError;

public class InstanceOfExpr extends CastExpr {
   private static final long serialVersionUID = 1L;

   public InstanceOfExpr(ASTList className, int dim, ASTree expr) {
      super(className, dim, expr);
   }

   public InstanceOfExpr(int type, int dim, ASTree expr) {
      super(type, dim, expr);
   }

   @Override
   public String getTag() {
      return "instanceof:" + this.castType + ":" + this.arrayDim;
   }

   @Override
   public void accept(Visitor v) throws CompileError {
      v.atInstanceOfExpr(this);
   }
}
