package gg.vape.shaded.javassist.bytecode.annotation;

import gg.vape.shaded.javassist.ClassPool;
import gg.vape.shaded.javassist.bytecode.BadBytecode;
import gg.vape.shaded.javassist.bytecode.ConstPool;
import gg.vape.shaded.javassist.bytecode.Descriptor;
import gg.vape.shaded.javassist.bytecode.SignatureAttribute;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

public class ClassMemberValue extends MemberValue {
   int valueIndex;

   public ClassMemberValue(int index, ConstPool cp) {
      super('c', cp);
      this.valueIndex = index;
   }

   public ClassMemberValue(String className, ConstPool cp) {
      super('c', cp);
      this.setValue(className);
   }

   public ClassMemberValue(ConstPool cp) {
      super('c', cp);
      this.setValue("java.lang.Class");
   }

   @Override
   Object getValue(ClassLoader cl, ClassPool cp, Method m) throws ClassNotFoundException {
      String classname = this.getValue();
      if (classname.equals("void")) {
         return void.class;
      } else if (classname.equals("int")) {
         return int.class;
      } else if (classname.equals("byte")) {
         return byte.class;
      } else if (classname.equals("long")) {
         return long.class;
      } else if (classname.equals("double")) {
         return double.class;
      } else if (classname.equals("float")) {
         return float.class;
      } else if (classname.equals("char")) {
         return char.class;
      } else if (classname.equals("short")) {
         return short.class;
      } else {
         return classname.equals("boolean") ? boolean.class : loadClass(cl, classname);
      }
   }

   @Override
   Class<?> getType(ClassLoader cl) throws ClassNotFoundException {
      return loadClass(cl, "java.lang.Class");
   }

   @Override
   public void renameClass(String oldname, String newname) {
      String value = this.cp.getUtf8Info(this.valueIndex);
      String newValue = Descriptor.rename(value, oldname, newname);
      this.setValue(Descriptor.toClassName(newValue));
   }

   @Override
   public void renameClass(Map<String, String> classnames) {
      String value = this.cp.getUtf8Info(this.valueIndex);
      String newValue = Descriptor.rename(value, classnames);
      this.setValue(Descriptor.toClassName(newValue));
   }

   public String getValue() {
      String v = this.cp.getUtf8Info(this.valueIndex);

      try {
         return SignatureAttribute.toTypeSignature(v).jvmTypeName();
      } catch (BadBytecode var3) {
         throw new RuntimeException(var3);
      }
   }

   public void setValue(String newClassName) {
      String setTo = Descriptor.of(newClassName);
      this.valueIndex = this.cp.addUtf8Info(setTo);
   }

   @Override
   public String toString() {
      return this.getValue().replace('$', '.') + ".class";
   }

   @Override
   public void write(AnnotationsWriter writer) throws IOException {
      writer.classInfoIndex(this.cp.getUtf8Info(this.valueIndex));
   }

   @Override
   public void accept(MemberValueVisitor visitor) {
      visitor.visitClassMemberValue(this);
   }
}
