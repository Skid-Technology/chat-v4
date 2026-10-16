package gg.vape.shaded.javassist.compiler.ast;

import gg.vape.shaded.javassist.compiler.CompileError;
import gg.vape.shaded.javassist.compiler.TokenId;

public class Stmnt extends ASTList implements TokenId {
   private static final long serialVersionUID = 1L;
   protected int operatorId;

   public Stmnt(int op, ASTree _head, ASTList _tail) {
      super(_head, _tail);
      this.operatorId = op;
   }

   public Stmnt(int op, ASTree _head) {
      super(_head);
      this.operatorId = op;
   }

   public Stmnt(int op) {
      this(op, null);
   }

   public static Stmnt make(int op, ASTree oprand1, ASTree oprand2) {
      return new Stmnt(op, oprand1, new ASTList(oprand2));
   }

   public static Stmnt make(int op, ASTree op1, ASTree op2, ASTree op3) {
      return new Stmnt(op, op1, new ASTList(op2, new ASTList(op3)));
   }

   @Override
   public void accept(Visitor v) throws CompileError {
      v.atStmnt(this);
   }

   public int getOperator() {
      return this.operatorId;
   }

   @Override
   protected String getTag() {
      return this.operatorId < 128 ? "stmnt:" + (char)this.operatorId : "stmnt:" + this.operatorId;
   }
}
