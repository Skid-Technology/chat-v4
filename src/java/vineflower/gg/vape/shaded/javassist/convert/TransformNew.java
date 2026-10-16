package gg.vape.shaded.javassist.convert;

import gg.vape.shaded.javassist.CannotCompileException;
import gg.vape.shaded.javassist.CtClass;
import gg.vape.shaded.javassist.bytecode.CodeAttribute;
import gg.vape.shaded.javassist.bytecode.CodeIterator;
import gg.vape.shaded.javassist.bytecode.ConstPool;
import gg.vape.shaded.javassist.bytecode.Descriptor;
import gg.vape.shaded.javassist.bytecode.StackMap;
import gg.vape.shaded.javassist.bytecode.StackMapTable;

public final class TransformNew extends Transformer {
   private int nested;
   private String classname;
   private String trapClass;
   private String trapMethod;

   public TransformNew(Transformer next, String classname, String trapClass, String trapMethod) {
      super(next);
      this.classname = classname;
      this.trapClass = trapClass;
      this.trapMethod = trapMethod;
   }

   @Override
   public void initialize(ConstPool cp, CodeAttribute attr) {
      this.nested = 0;
   }

   @Override
   public int transform(CtClass clazz, int pos, CodeIterator iterator, ConstPool cp) throws CannotCompileException {
      int c = iterator.byteAt(pos);
      if (c == 187) {
         int index = iterator.u16bitAt(pos + 1);
         if (cp.getClassInfo(index).equals(this.classname)) {
            if (iterator.byteAt(pos + 3) != 89) {
               throw new CannotCompileException("NEW followed by no DUP was found");
            }

            iterator.writeByte(0, pos);
            iterator.writeByte(0, pos + 1);
            iterator.writeByte(0, pos + 2);
            iterator.writeByte(0, pos + 3);
            this.nested++;
            StackMapTable smt = (StackMapTable)iterator.get().getAttribute("StackMapTable");
            if (smt != null) {
               smt.removeNew(pos);
            }

            StackMap sm = (StackMap)iterator.get().getAttribute("StackMap");
            if (sm != null) {
               sm.removeNew(pos);
            }
         }
      } else if (c == 183) {
         int index = iterator.u16bitAt(pos + 1);
         int typedesc = cp.isConstructor(this.classname, index);
         if (typedesc != 0 && this.nested > 0) {
            int methodref = this.computeMethodref(typedesc, cp);
            iterator.writeByte(184, pos);
            iterator.write16bit(methodref, pos + 1);
            this.nested--;
         }
      }

      return pos;
   }

   private int computeMethodref(int typedesc, ConstPool cp) {
      int classIndex = cp.addClassInfo(this.trapClass);
      int mnameIndex = cp.addUtf8Info(this.trapMethod);
      typedesc = cp.addUtf8Info(Descriptor.changeReturnType(this.classname, cp.getUtf8Info(typedesc)));
      return cp.addMethodrefInfo(classIndex, cp.addNameAndTypeInfo(mnameIndex, typedesc));
   }
}
