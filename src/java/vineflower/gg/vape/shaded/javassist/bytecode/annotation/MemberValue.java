package gg.vape.shaded.javassist.bytecode.annotation;

import gg.vape.shaded.javassist.ClassPool;
import gg.vape.shaded.javassist.bytecode.ConstPool;
import gg.vape.shaded.javassist.bytecode.Descriptor;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

public abstract class MemberValue {
   ConstPool cp;
   char tag;

   MemberValue(char tag, ConstPool cp) {
      this.cp = cp;
      this.tag = tag;
   }

   abstract Object getValue(ClassLoader var1, ClassPool var2, Method var3) throws ClassNotFoundException;

   abstract Class<?> getType(ClassLoader var1) throws ClassNotFoundException;

   static Class<?> loadClass(ClassLoader cl, String classname) throws ClassNotFoundException, NoSuchClassError {
      try {
         return Class.forName(convertFromArray(classname), true, cl);
      } catch (LinkageError var3) {
         throw new NoSuchClassError(classname, var3);
      }
   }

   private static String convertFromArray(String classname) {
      int index = classname.indexOf("[]");
      if (index == -1) {
         return classname;
      } else {
         String rawType = classname.substring(0, index);

         StringBuilder sb;
         for (sb = new StringBuilder(Descriptor.of(rawType)); index != -1; index = classname.indexOf("[]", index + 1)) {
            sb.insert(0, '[');
         }

         return sb.toString().replace('/', '.');
      }
   }

   public void renameClass(String oldname, String newname) {
   }

   public void renameClass(Map<String, String> classnames) {
   }

   public abstract void accept(MemberValueVisitor var1);

   public abstract void write(AnnotationsWriter var1) throws IOException;
}
