package gg.vape.shaded.javassist.scopedpool;

import gg.vape.shaded.javassist.ClassPool;
import java.util.Map;

public interface ScopedClassPoolRepository {
   void setClassPoolFactory(ScopedClassPoolFactory var1);

   ScopedClassPoolFactory getClassPoolFactory();

   boolean isPrune();

   void setPrune(boolean var1);

   ScopedClassPool createScopedClassPool(ClassLoader var1, ClassPool var2);

   ClassPool findClassPool(ClassLoader var1);

   ClassPool registerClassLoader(ClassLoader var1);

   Map<ClassLoader, ScopedClassPool> getRegisteredCLs();

   void clearUnregisteredClassLoaders();

   void unregisterClassLoader(ClassLoader var1);
}
