package gg.vape.shaded.javassist.bytecode.annotation;

import gg.vape.shaded.javassist.ClassPool;
import gg.vape.shaded.javassist.bytecode.ConstPool;
import java.io.IOException;
import java.lang.reflect.Method;

public class LongMemberValue extends MemberValue {
   int valueIndex;

   public LongMemberValue(int index, ConstPool cp) {
      super('J', cp);
      this.valueIndex = index;
   }

   public LongMemberValue(long j, ConstPool cp) {
      super('J', cp);
      this.setValue(j);
   }

   public LongMemberValue(ConstPool cp) {
      super('J', cp);
      this.setValue(0L);
   }

   @Override
   Object getValue(ClassLoader cl, ClassPool cp, Method m) {
      return this.getValue();
   }

   @Override
   Class<?> getType(ClassLoader cl) {
      return long.class;
   }

   public long getValue() {
      return this.cp.getLongInfo(this.valueIndex);
   }

   public void setValue(long newValue) {
      this.valueIndex = this.cp.addLongInfo(newValue);
   }

   @Override
   public String toString() {
      return Long.toString(this.getValue());
   }

   @Override
   public void write(AnnotationsWriter writer) throws IOException {
      writer.constValueIndex(this.getValue());
   }

   @Override
   public void accept(MemberValueVisitor visitor) {
      visitor.visitLongMemberValue(this);
   }
}
