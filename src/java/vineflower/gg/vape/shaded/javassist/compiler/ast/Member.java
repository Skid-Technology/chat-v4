package gg.vape.shaded.javassist.compiler.ast;

import gg.vape.shaded.javassist.CtField;
import gg.vape.shaded.javassist.compiler.CompileError;

public class Member extends Symbol {
   private static final long serialVersionUID = 1L;
   private CtField field = null;

   public Member(String name) {
      super(name);
   }

   public void setField(CtField f) {
      this.field = f;
   }

   public CtField getField() {
      return this.field;
   }

   @Override
   public void accept(Visitor v) throws CompileError {
      v.atMember(this);
   }
}
