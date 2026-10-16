package gg.vape.shaded.javassist.bytecode.annotation;

import gg.vape.shaded.javassist.ClassPool;
import gg.vape.shaded.javassist.bytecode.ConstPool;
import java.io.IOException;
import java.lang.reflect.Method;

public class DoubleMemberValue extends MemberValue {
   int valueIndex;

   public DoubleMemberValue(int index, ConstPool cp) {
      super('D', cp);
      this.valueIndex = index;
   }

   public DoubleMemberValue(double d, ConstPool cp) {
      super('D', cp);
      this.setValue(d);
   }

   public DoubleMemberValue(ConstPool cp) {
      super('D', cp);
      this.setValue(0.0);
   }

   @Override
   Object getValue(ClassLoader cl, ClassPool cp, Method m) {
      return this.getValue();
   }

   @Override
   Class<?> getType(ClassLoader cl) {
      return double.class;
   }

   public double getValue() {
      return this.cp.getDoubleInfo(this.valueIndex);
   }

   public void setValue(double newValue) {
      this.valueIndex = this.cp.addDoubleInfo(newValue);
   }

   @Override
   public String toString() {
      return Double.toString(this.getValue());
   }

   @Override
   public void write(AnnotationsWriter writer) throws IOException {
      writer.constValueIndex(this.getValue());
   }

   @Override
   public void accept(MemberValueVisitor visitor) {
      visitor.visitDoubleMemberValue(this);
   }
}
