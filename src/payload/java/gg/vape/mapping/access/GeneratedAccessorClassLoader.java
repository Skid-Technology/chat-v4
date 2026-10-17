package gg.vape.mapping.access;

public class GeneratedAccessorClassLoader extends ClassLoader {
   public GeneratedAccessorClassLoader(ClassLoader classLoader) {
      super(classLoader);
   }

   @Override
   public Object getClassLoadingLock(String string) {
      return super.getClassLoadingLock(string);
   }

   private Class<?> Z(String string, byte[] byArray) {
      Object object3 = this.getClassLoadingLock(string);
      synchronized (object3) {
         Class<?> clazz = this.defineClass(string, byArray, 0, byArray.length);
         this.resolveClass(clazz);
         return clazz;
      }
   }

   public boolean X(String string) {
      Object object3 = this.getClassLoadingLock(string);
      synchronized (object3) {
         boolean var10000;
         try {
            Class.forName(string);
            var10000 = true;
         } catch (ClassNotFoundException var8) {
            return false;
         }

         return var10000;
      }
   }

   public static Class d(GeneratedAccessorClassLoader generatedAccessorClassLoader, String string, byte[] byArray) {
      return generatedAccessorClassLoader.Z(string, byArray);
   }

   static {
      ClassLoader.registerAsParallelCapable();
   }
}
