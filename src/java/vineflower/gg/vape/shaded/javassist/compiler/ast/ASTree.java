package gg.vape.shaded.javassist.compiler.ast;

import gg.vape.shaded.javassist.compiler.CompileError;
import java.io.Serializable;

public abstract class ASTree implements Serializable {
   private static final long serialVersionUID = 1L;

   public ASTree getLeft() {
      return null;
   }

   public ASTree getRight() {
      return null;
   }

   public void setLeft(ASTree _left) {
   }

   public void setRight(ASTree _right) {
   }

   public abstract void accept(Visitor var1) throws CompileError;

   @Override
   public String toString() {
      StringBuilder sbuf = new StringBuilder();
      sbuf.append('<');
      sbuf.append(this.getTag());
      sbuf.append('>');
      return sbuf.toString();
   }

   protected String getTag() {
      String name = this.getClass().getName();
      return name.substring(name.lastIndexOf(46) + 1);
   }
}
