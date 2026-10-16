package gg.vape.shaded.javassist.scopedpool;

import gg.vape.shaded.javassist.ClassPool;

public class ScopedClassPoolFactoryImpl implements ScopedClassPoolFactory {
   @Override
   public ScopedClassPool create(ClassLoader cl, ClassPool src, ScopedClassPoolRepository repository) {
      return new ScopedClassPool(cl, src, repository, false);
   }

   @Override
   public ScopedClassPool create(ClassPool src, ScopedClassPoolRepository repository) {
      return new ScopedClassPool(null, src, repository, true);
   }
}
