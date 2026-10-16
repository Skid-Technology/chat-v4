package gg.vape.shaded.javassist.util.proxy;

import java.io.InvalidClassException;
import java.io.InvalidObjectException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

class SerializedProxy implements Serializable {
   private static final long serialVersionUID = 1L;
   private String superClass;
   private String[] interfaces;
   private byte[] filterSignature;
   private MethodHandler handler;

   SerializedProxy(Class<?> proxy, byte[] sig, MethodHandler h) {
      this.filterSignature = sig;
      this.handler = h;
      this.superClass = proxy.getSuperclass().getName();
      Class<?>[] infs = proxy.getInterfaces();
      int n = infs.length;
      this.interfaces = new String[n - 1];
      String setterInf = ProxyObject.class.getName();
      String setterInf2 = Proxy.class.getName();

      for (int i = 0; i < n; i++) {
         String name = infs[i].getName();
         if (!name.equals(setterInf) && !name.equals(setterInf2)) {
            this.interfaces[i] = name;
         }
      }
   }

   protected Class<?> loadClass(final String className) throws ClassNotFoundException {
      try {
         return AccessController.doPrivileged(new PrivilegedExceptionAction<Class<?>>() {
            public Class<?> run() throws Exception {
               ClassLoader cl = Thread.currentThread().getContextClassLoader();
               return Class.forName(className, true, cl);
            }
         });
      } catch (PrivilegedActionException var3) {
         throw new RuntimeException("cannot load the class: " + className, var3.getException());
      }
   }

   Object readResolve() throws ObjectStreamException {
      try {
         int n = this.interfaces.length;
         Class<?>[] infs = new Class[n];

         for (int i = 0; i < n; i++) {
            infs[i] = this.loadClass(this.interfaces[i]);
         }

         ProxyFactory f = new ProxyFactory();
         f.setSuperclass(this.loadClass(this.superClass));
         f.setInterfaces(infs);
         Proxy proxy = (Proxy)f.createClass(this.filterSignature).getConstructor().newInstance();
         proxy.setHandler(this.handler);
         return proxy;
      } catch (NoSuchMethodException var5) {
         throw new InvalidClassException(var5.getMessage());
      } catch (InvocationTargetException var6) {
         throw new InvalidClassException(var6.getMessage());
      } catch (ClassNotFoundException var7) {
         throw new InvalidClassException(var7.getMessage());
      } catch (InstantiationException var8) {
         throw new InvalidObjectException(var8.getMessage());
      } catch (IllegalAccessException var9) {
         throw new InvalidClassException(var9.getMessage());
      }
   }
}
