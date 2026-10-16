package gg.vape.shaded.javassist.convert;

import gg.vape.shaded.javassist.CannotCompileException;
import gg.vape.shaded.javassist.CtClass;
import gg.vape.shaded.javassist.bytecode.BadBytecode;
import gg.vape.shaded.javassist.bytecode.CodeAttribute;
import gg.vape.shaded.javassist.bytecode.CodeIterator;
import gg.vape.shaded.javassist.bytecode.ConstPool;
import gg.vape.shaded.javassist.bytecode.MethodInfo;
import gg.vape.shaded.javassist.bytecode.Opcode;

public abstract class Transformer implements Opcode {
   private Transformer next;

   public Transformer(Transformer t) {
      this.next = t;
   }

   public Transformer getNext() {
      return this.next;
   }

   public void initialize(ConstPool cp, CodeAttribute attr) {
   }

   public void initialize(ConstPool cp, CtClass clazz, MethodInfo minfo) throws CannotCompileException {
      this.initialize(cp, minfo.getCodeAttribute());
   }

   public void clean() {
   }

   public abstract int transform(CtClass var1, int var2, CodeIterator var3, ConstPool var4) throws CannotCompileException, BadBytecode;

   public int extraLocals() {
      return 0;
   }

   public int extraStack() {
      return 0;
   }
}
