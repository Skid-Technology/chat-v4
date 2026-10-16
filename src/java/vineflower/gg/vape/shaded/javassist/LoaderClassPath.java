package gg.vape.shaded.javassist;

import java.io.InputStream;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.net.URL;

public class LoaderClassPath implements ClassPath {
   private Reference<ClassLoader> clref;

   public LoaderClassPath(ClassLoader cl) {
      this.clref = new WeakReference<>(cl);
   }

   @Override
   public String toString() {
      return this.clref.get() == null ? "<null>" : this.clref.get().toString();
   }

   @Override
   public InputStream openClassfile(String classname) throws NotFoundException {
      String cname = classname.replace('.', '/') + ".class";
      ClassLoader cl = this.clref.get();
      return cl == null ? null : cl.getResourceAsStream(cname);
   }

   @Override
   public URL find(String classname) {
      String cname = classname.replace('.', '/') + ".class";
      ClassLoader cl = this.clref.get();
      return cl == null ? null : cl.getResource(cname);
   }
}
