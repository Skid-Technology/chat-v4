package gg.vape.shaded.javassist.bytecode.annotation;

import gg.vape.shaded.javassist.ClassPool;
import gg.vape.shaded.javassist.bytecode.ConstPool;
import java.io.IOException;
import java.lang.reflect.Method;

public class CharMemberValue extends MemberValue {
   int valueIndex;

   public CharMemberValue(int index, ConstPool cp) {
      super('C', cp);
      this.valueIndex = index;
   }

   public CharMemberValue(char c, ConstPool cp) {
      super('C', cp);
      this.setValue(c);
   }

   public CharMemberValue(ConstPool cp) {
      super('C', cp);
      this.setValue('\u0000');
   }

   @Override
   Object getValue(ClassLoader cl, ClassPool cp, Method m) {
      return this.getValue();
   }

   @Override
   Class<?> getType(ClassLoader cl) {
      return char.class;
   }

   public char getValue() {
      return (char)this.cp.getIntegerInfo(this.valueIndex);
   }

   public void setValue(char newValue) {
      this.valueIndex = this.cp.addIntegerInfo(newValue);
   }

   @Override
   public String toString() {
      return Character.toString(this.getValue());
   }

   @Override
   public void write(AnnotationsWriter writer) throws IOException {
      writer.constValueIndex(this.getValue());
   }

   @Override
   public void accept(MemberValueVisitor visitor) {
      visitor.visitCharMemberValue(this);
   }
}
