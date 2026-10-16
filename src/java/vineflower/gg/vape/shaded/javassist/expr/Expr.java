package gg.vape.shaded.javassist.expr;

import gg.vape.shaded.javassist.CannotCompileException;
import gg.vape.shaded.javassist.ClassPool;
import gg.vape.shaded.javassist.CtBehavior;
import gg.vape.shaded.javassist.CtClass;
import gg.vape.shaded.javassist.CtConstructor;
import gg.vape.shaded.javassist.CtPrimitiveType;
import gg.vape.shaded.javassist.NotFoundException;
import gg.vape.shaded.javassist.bytecode.BadBytecode;
import gg.vape.shaded.javassist.bytecode.Bytecode;
import gg.vape.shaded.javassist.bytecode.ClassFile;
import gg.vape.shaded.javassist.bytecode.CodeAttribute;
import gg.vape.shaded.javassist.bytecode.CodeIterator;
import gg.vape.shaded.javassist.bytecode.ConstPool;
import gg.vape.shaded.javassist.bytecode.ExceptionTable;
import gg.vape.shaded.javassist.bytecode.ExceptionsAttribute;
import gg.vape.shaded.javassist.bytecode.MethodInfo;
import gg.vape.shaded.javassist.bytecode.Opcode;
import java.util.LinkedList;
import java.util.List;

public abstract class Expr implements Opcode {
   int currentPos;
   CodeIterator iterator;
   CtClass thisClass;
   MethodInfo thisMethod;
   boolean edited;
   int maxLocals;
   int maxStack;
   static final String javaLangObject = "java.lang.Object";

   protected Expr(int pos, CodeIterator i, CtClass declaring, MethodInfo m) {
      this.currentPos = pos;
      this.iterator = i;
      this.thisClass = declaring;
      this.thisMethod = m;
   }

   public CtClass getEnclosingClass() {
      return this.thisClass;
   }

   protected final ConstPool getConstPool() {
      return this.thisMethod.getConstPool();
   }

   protected final boolean edited() {
      return this.edited;
   }

   protected final int locals() {
      return this.maxLocals;
   }

   protected final int stack() {
      return this.maxStack;
   }

   protected final boolean withinStatic() {
      return (this.thisMethod.getAccessFlags() & 8) != 0;
   }

   public CtBehavior where() {
      MethodInfo mi = this.thisMethod;
      CtBehavior[] cb = this.thisClass.getDeclaredBehaviors();

      for (int i = cb.length - 1; i >= 0; i--) {
         if (cb[i].getMethodInfo2() == mi) {
            return cb[i];
         }
      }

      CtConstructor init = this.thisClass.getClassInitializer();
      if (init != null && init.getMethodInfo2() == mi) {
         return init;
      } else {
         for (int ix = cb.length - 1; ix >= 0; ix--) {
            if (this.thisMethod.getName().equals(cb[ix].getMethodInfo2().getName())
               && this.thisMethod.getDescriptor().equals(cb[ix].getMethodInfo2().getDescriptor())) {
               return cb[ix];
            }
         }

         throw new RuntimeException("fatal: not found");
      }
   }

   public CtClass[] mayThrow() {
      ClassPool pool = this.thisClass.getClassPool();
      ConstPool cp = this.thisMethod.getConstPool();
      List<CtClass> list = new LinkedList<>();

      try {
         CodeAttribute ca = this.thisMethod.getCodeAttribute();
         ExceptionTable et = ca.getExceptionTable();
         int pos = this.currentPos;
         int n = et.size();

         for (int i = 0; i < n; i++) {
            if (et.startPc(i) <= pos && pos < et.endPc(i)) {
               int t = et.catchType(i);
               if (t > 0) {
                  try {
                     addClass(list, pool.get(cp.getClassInfo(t)));
                  } catch (NotFoundException var12) {
                  }
               }
            }
         }
      } catch (NullPointerException var13) {
      }

      ExceptionsAttribute ea = this.thisMethod.getExceptionsAttribute();
      if (ea != null) {
         String[] exceptions = ea.getExceptions();
         if (exceptions != null) {
            int n = exceptions.length;

            for (int ix = 0; ix < n; ix++) {
               try {
                  addClass(list, pool.get(exceptions[ix]));
               } catch (NotFoundException var11) {
               }
            }
         }
      }

      return list.toArray(new CtClass[list.size()]);
   }

   private static void addClass(List<CtClass> list, CtClass c) {
      if (!list.contains(c)) {
         list.add(c);
      }
   }

   public int indexOfBytecode() {
      return this.currentPos;
   }

   public int getLineNumber() {
      return this.thisMethod.getLineNumber(this.currentPos);
   }

   public String getFileName() {
      ClassFile cf = this.thisClass.getClassFile2();
      return cf == null ? null : cf.getSourceFile();
   }

   static final boolean checkResultValue(CtClass retType, String prog) throws CannotCompileException {
      boolean hasIt = prog.indexOf("$_") >= 0;
      if (!hasIt && retType != CtClass.voidType) {
         throw new CannotCompileException("the resulting value is not stored in $_");
      } else {
         return hasIt;
      }
   }

   static final void storeStack(CtClass[] params, boolean isStaticCall, int regno, Bytecode bytecode) {
      storeStack0(0, params.length, params, regno + 1, bytecode);
      if (isStaticCall) {
         bytecode.addOpcode(1);
      }

      bytecode.addAstore(regno);
   }

   private static void storeStack0(int i, int n, CtClass[] params, int regno, Bytecode bytecode) {
      if (i < n) {
         CtClass c = params[i];
         int size;
         if (c instanceof CtPrimitiveType) {
            size = ((CtPrimitiveType)c).getDataSize();
         } else {
            size = 1;
         }

         storeStack0(i + 1, n, params, regno + size, bytecode);
         bytecode.addStore(regno, c);
      }
   }

   public abstract void replace(String var1) throws CannotCompileException;

   public void replace(String statement, ExprEditor recursive) throws CannotCompileException {
      this.replace(statement);
      if (recursive != null) {
         this.runEditor(recursive, this.iterator);
      }
   }

   protected void replace0(int pos, Bytecode bytecode, int size) throws BadBytecode {
      byte[] code = bytecode.get();
      this.edited = true;
      int gap = code.length - size;

      for (int i = 0; i < size; i++) {
         this.iterator.writeByte(0, pos + i);
      }

      if (gap > 0) {
         pos = this.iterator.insertGapAt(pos, gap, false).position;
      }

      this.iterator.write(code, pos);
      this.iterator.insert(bytecode.getExceptionTable(), pos);
      this.maxLocals = bytecode.getMaxLocals();
      this.maxStack = bytecode.getMaxStack();
   }

   protected void runEditor(ExprEditor ed, CodeIterator oldIterator) throws CannotCompileException {
      CodeAttribute codeAttr = oldIterator.get();
      int orgLocals = codeAttr.getMaxLocals();
      int orgStack = codeAttr.getMaxStack();
      int newLocals = this.locals();
      codeAttr.setMaxStack(this.stack());
      codeAttr.setMaxLocals(newLocals);
      ExprEditor.LoopContext context = new ExprEditor.LoopContext(newLocals);
      int size = oldIterator.getCodeLength();
      int endPos = oldIterator.lookAhead();
      oldIterator.move(this.currentPos);
      if (ed.doit(this.thisClass, this.thisMethod, context, oldIterator, endPos)) {
         this.edited = true;
      }

      oldIterator.move(endPos + oldIterator.getCodeLength() - size);
      codeAttr.setMaxLocals(orgLocals);
      codeAttr.setMaxStack(orgStack);
      this.maxLocals = context.maxLocals;
      this.maxStack = this.maxStack + context.maxStack;
   }
}
