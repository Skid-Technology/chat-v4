package gg.vape.shaded.javassist.bytecode.annotation;

import gg.vape.shaded.javassist.ClassPool;
import gg.vape.shaded.javassist.bytecode.ConstPool;
import java.io.IOException;
import java.lang.reflect.Method;

public class ByteMemberValue extends MemberValue {
   int valueIndex;

   public ByteMemberValue(int index, ConstPool cp) {
      super('B', cp);
      this.valueIndex = index;
   }

   public ByteMemberValue(byte b, ConstPool cp) {
      super('B', cp);
      this.setValue(b);
   }

   public ByteMemberValue(ConstPool cp) {
      super('B', cp);
      this.setValue((byte)0);
   }

   @Override
   Object getValue(ClassLoader cl, ClassPool cp, Method m) {
      return this.getValue();
   }

   @Override
   Class<?> getType(ClassLoader cl) {
      return byte.class;
   }

   public byte getValue() {
      return (byte)this.cp.getIntegerInfo(this.valueIndex);
   }

   public void setValue(byte newValue) {
      this.valueIndex = this.cp.addIntegerInfo(newValue);
   }

   @Override
   public String toString() {
      return Byte.toString(this.getValue());
   }

   @Override
   public void write(AnnotationsWriter writer) throws IOException {
      writer.constValueIndex(this.getValue());
   }

   @Override
   public void accept(MemberValueVisitor visitor) {
      visitor.visitByteMemberValue(this);
   }
}
