package gg.vape.shaded.javassist.tools.reflect;

import gg.vape.shaded.javassist.CannotCompileException;
import gg.vape.shaded.javassist.ClassPool;
import gg.vape.shaded.javassist.NotFoundException;

public class Loader extends gg.vape.shaded.javassist.Loader {
   protected Reflection reflection;

   public static void main(String[] args) throws Throwable {
      Loader cl = new Loader();
      cl.run(args);
   }

   public Loader() throws CannotCompileException, NotFoundException {
      this.delegateLoadingOf("gg.vape.shaded.javassist.tools.reflect.Loader");
      this.reflection = new Reflection();
      ClassPool pool = ClassPool.getDefault();
      this.addTranslator(pool, this.reflection);
   }

   public boolean makeReflective(String clazz, String metaobject, String metaclass) throws CannotCompileException, NotFoundException {
      return this.reflection.makeReflective(clazz, metaobject, metaclass);
   }
}
