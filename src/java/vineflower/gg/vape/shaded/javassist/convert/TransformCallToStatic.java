package gg.vape.shaded.javassist.convert;

import gg.vape.shaded.javassist.CtMethod;
import gg.vape.shaded.javassist.bytecode.CodeIterator;
import gg.vape.shaded.javassist.bytecode.ConstPool;
import gg.vape.shaded.javassist.bytecode.Descriptor;

public class TransformCallToStatic extends TransformCall {
   public TransformCallToStatic(Transformer next, CtMethod origMethod, CtMethod substMethod) {
      super(next, origMethod, substMethod);
      this.methodDescriptor = origMethod.getMethodInfo2().getDescriptor();
   }

   @Override
   protected int match(int c, int pos, CodeIterator iterator, int typedesc, ConstPool cp) {
      if (this.newIndex == 0) {
         String desc = Descriptor.insertParameter(this.classname, this.methodDescriptor);
         int nt = cp.addNameAndTypeInfo(this.newMethodname, desc);
         int ci = cp.addClassInfo(this.newClassname);
         this.newIndex = cp.addMethodrefInfo(ci, nt);
         this.constPool = cp;
      }

      iterator.writeByte(184, pos);
      iterator.write16bit(this.newIndex, pos + 1);
      return pos;
   }
}
