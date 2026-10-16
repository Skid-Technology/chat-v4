package gg.vape.shaded.javassist;

import java.io.InputStream;
import java.net.URL;

public class ClassClassPath implements ClassPath {
   private Class<?> thisClass;

   public ClassClassPath(Class<?> c) {
      this.thisClass = c;
   }

   ClassClassPath() {
      this(Object.class);
   }

   @Override
   public InputStream openClassfile(String classname) throws NotFoundException {
      String filename = '/' + classname.replace('.', '/') + ".class";
      return this.thisClass.getResourceAsStream(filename);
   }

   @Override
   public URL find(String classname) {
      String filename = '/' + classname.replace('.', '/') + ".class";
      return this.thisClass.getResource(filename);
   }

   @Override
   public String toString() {
      return this.thisClass.getName() + ".class";
   }
}
