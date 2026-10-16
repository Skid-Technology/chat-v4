package gg.vape.shaded.javassist;

import gg.vape.shaded.javassist.bytecode.Descriptor;
import java.util.HashMap;

public class ClassMap extends HashMap<String, String> {
   private static final long serialVersionUID = 1L;
   private ClassMap parent;

   public ClassMap() {
      this.parent = null;
   }

   ClassMap(ClassMap map) {
      this.parent = map;
   }

   public void put(CtClass oldname, CtClass newname) {
      this.put(oldname.getName(), newname.getName());
   }

   public String put(String oldname, String newname) {
      if (oldname == newname) {
         return oldname;
      } else {
         String oldname2 = toJvmName(oldname);
         String s = this.get(oldname2);
         return s != null && s.equals(oldname2) ? s : super.put(oldname2, toJvmName(newname));
      }
   }

   public void putIfNone(String oldname, String newname) {
      if (oldname != newname) {
         String oldname2 = toJvmName(oldname);
         String s = this.get(oldname2);
         if (s == null) {
            super.put(oldname2, toJvmName(newname));
         }
      }
   }

   protected final String put0(String oldname, String newname) {
      return super.put(oldname, newname);
   }

   public String get(Object jvmClassName) {
      String found = (String)super.get(jvmClassName);
      return found == null && this.parent != null ? this.parent.get(jvmClassName) : found;
   }

   public void fix(CtClass clazz) {
      this.fix(clazz.getName());
   }

   public void fix(String name) {
      String name2 = toJvmName(name);
      super.put(name2, name2);
   }

   public static String toJvmName(String classname) {
      return Descriptor.toJvmName(classname);
   }

   public static String toJavaName(String classname) {
      return Descriptor.toJavaName(classname);
   }
}
