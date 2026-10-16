package gg.vape.shaded.javassist.expr;

import gg.vape.shaded.javassist.CannotCompileException;
import gg.vape.shaded.javassist.CtBehavior;
import gg.vape.shaded.javassist.CtClass;
import gg.vape.shaded.javassist.NotFoundException;
import gg.vape.shaded.javassist.bytecode.Bytecode;
import gg.vape.shaded.javassist.bytecode.CodeAttribute;
import gg.vape.shaded.javassist.bytecode.CodeIterator;
import gg.vape.shaded.javassist.bytecode.ConstPool;
import gg.vape.shaded.javassist.bytecode.ExceptionTable;
import gg.vape.shaded.javassist.bytecode.MethodInfo;
import gg.vape.shaded.javassist.compiler.CompileError;
import gg.vape.shaded.javassist.compiler.Javac;

public class Handler extends Expr {
   private static String EXCEPTION_NAME = "$1";
   private ExceptionTable etable;
   private int index;

   protected Handler(ExceptionTable et, int nth, CodeIterator it, CtClass declaring, MethodInfo m) {
      super(et.handlerPc(nth), it, declaring, m);
      this.etable = et;
      this.index = nth;
   }

   @Override
   public CtBehavior where() {
      return super.where();
   }

   @Override
   public int getLineNumber() {
      return super.getLineNumber();
   }

   @Override
   public String getFileName() {
      return super.getFileName();
   }

   @Override
   public CtClass[] mayThrow() {
      return super.mayThrow();
   }

   public CtClass getType() throws NotFoundException {
      int type = this.etable.catchType(this.index);
      if (type == 0) {
         return null;
      } else {
         ConstPool cp = this.getConstPool();
         String name = cp.getClassInfo(type);
         return this.thisClass.getClassPool().getCtClass(name);
      }
   }

   public boolean isFinally() {
      return this.etable.catchType(this.index) == 0;
   }

   @Override
   public void replace(String statement) throws CannotCompileException {
      throw new RuntimeException("not implemented yet");
   }

   public void insertBefore(String src) throws CannotCompileException {
      this.edited = true;
      ConstPool cp = this.getConstPool();
      CodeAttribute ca = this.iterator.get();
      Javac jv = new Javac(this.thisClass);
      Bytecode b = jv.getBytecode();
      b.setStackDepth(1);
      b.setMaxLocals(ca.getMaxLocals());

      try {
         CtClass type = this.getType();
         int var = jv.recordVariable(type, EXCEPTION_NAME);
         jv.recordReturnType(type, false);
         b.addAstore(var);
         jv.compileStmnt(src);
         b.addAload(var);
         int oldHandler = this.etable.handlerPc(this.index);
         b.addOpcode(167);
         b.addIndex(oldHandler - this.iterator.getCodeLength() - b.currentPc() + 1);
         this.maxStack = b.getMaxStack();
         this.maxLocals = b.getMaxLocals();
         int pos = this.iterator.append(b.get());
         this.iterator.append(b.getExceptionTable(), pos);
         this.etable.setHandlerPc(this.index, pos);
      } catch (NotFoundException var10) {
         throw new CannotCompileException(var10);
      } catch (CompileError var11) {
         throw new CannotCompileException(var11);
      }
   }
}
