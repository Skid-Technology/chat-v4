package gg.vape.shaded.javassist.compiler.ast;

import gg.vape.shaded.javassist.compiler.CompileError;

public class Variable extends Symbol {
   private static final long serialVersionUID = 1L;
   protected Declarator declarator;

   public Variable(String sym, Declarator d) {
      super(sym);
      this.declarator = d;
   }

   public Declarator getDeclarator() {
      return this.declarator;
   }

   @Override
   public String toString() {
      return this.identifier + ":" + this.declarator.getType();
   }

   @Override
   public void accept(Visitor v) throws CompileError {
      v.atVariable(this);
   }
}
