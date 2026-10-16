package gg.vape.shaded.javassist.scopedpool;

import gg.vape.shaded.javassist.ClassPool;
import gg.vape.shaded.javassist.LoaderClassPath;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.Map.Entry;

public class ScopedClassPoolRepositoryImpl implements ScopedClassPoolRepository {
   private static final ScopedClassPoolRepositoryImpl instance = new ScopedClassPoolRepositoryImpl();
   private boolean prune = true;
   boolean pruneWhenCached;
   protected Map<ClassLoader, ScopedClassPool> registeredCLs = Collections.synchronizedMap(new WeakHashMap<>());
   protected ClassPool classpool;
   protected ScopedClassPoolFactory factory = new ScopedClassPoolFactoryImpl();

   public static ScopedClassPoolRepository getInstance() {
      return instance;
   }

   private ScopedClassPoolRepositoryImpl() {
      this.classpool = ClassPool.getDefault();
      ClassLoader cl = Thread.currentThread().getContextClassLoader();
      this.classpool.insertClassPath(new LoaderClassPath(cl));
   }

   @Override
   public boolean isPrune() {
      return this.prune;
   }

   @Override
   public void setPrune(boolean prune) {
      this.prune = prune;
   }

   @Override
   public ScopedClassPool createScopedClassPool(ClassLoader cl, ClassPool src) {
      return this.factory.create(cl, src, this);
   }

   @Override
   public ClassPool findClassPool(ClassLoader cl) {
      return cl == null ? this.registerClassLoader(ClassLoader.getSystemClassLoader()) : this.registerClassLoader(cl);
   }

   @Override
   public ClassPool registerClassLoader(ClassLoader ucl) {
      synchronized (this.registeredCLs) {
         if (this.registeredCLs.containsKey(ucl)) {
            return this.registeredCLs.get(ucl);
         } else {
            ScopedClassPool pool = this.createScopedClassPool(ucl, this.classpool);
            this.registeredCLs.put(ucl, pool);
            return pool;
         }
      }
   }

   @Override
   public Map<ClassLoader, ScopedClassPool> getRegisteredCLs() {
      this.clearUnregisteredClassLoaders();
      return this.registeredCLs;
   }

   @Override
   public void clearUnregisteredClassLoaders() {
      List<ClassLoader> toUnregister = null;
      synchronized (this.registeredCLs) {
         for (Entry<ClassLoader, ScopedClassPool> reg : this.registeredCLs.entrySet()) {
            if (reg.getValue().isUnloadedClassLoader()) {
               ClassLoader cl = reg.getValue().getClassLoader();
               if (cl != null) {
                  if (toUnregister == null) {
                     toUnregister = new ArrayList<>();
                  }

                  toUnregister.add(cl);
               }

               this.registeredCLs.remove(reg.getKey());
            }
         }

         if (toUnregister != null) {
            for (ClassLoader cl : toUnregister) {
               this.unregisterClassLoader(cl);
            }
         }
      }
   }

   @Override
   public void unregisterClassLoader(ClassLoader cl) {
      synchronized (this.registeredCLs) {
         ScopedClassPool pool = this.registeredCLs.remove(cl);
         if (pool != null) {
            pool.close();
         }
      }
   }

   public void insertDelegate(ScopedClassPoolRepository delegate) {
   }

   @Override
   public void setClassPoolFactory(ScopedClassPoolFactory factory) {
      this.factory = factory;
   }

   @Override
   public ScopedClassPoolFactory getClassPoolFactory() {
      return this.factory;
   }
}
