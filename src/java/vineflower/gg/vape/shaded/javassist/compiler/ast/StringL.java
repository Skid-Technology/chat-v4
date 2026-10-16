package gg.vape.shaded.javassist.compiler.ast;

import gg.vape.shaded.javassist.compiler.CompileError;

public class StringL extends ASTree {
   private static final long serialVersionUID = 1L;
   protected String text;

   public StringL(String t) {
      this.text = t;
   }

   public String get() {
      return this.text;
   }

   @Override
   public String toString() {
      return "\"" + this.text + "\"";
   }

   @Override
   public void accept(Visitor v) throws CompileError {
      v.atStringL(this);
   }
}
