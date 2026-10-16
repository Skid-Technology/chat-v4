package org.lwjgl.input;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.InputImplementation;

final class OpenGLPackageAccess {
   static final Object global_lock;

   static InputImplementation createImplementation() {
      try {
         return AccessController.doPrivileged(new PrivilegedExceptionAction<InputImplementation>() {
            public InputImplementation run() throws Exception {
               Method getImplementation_method = Display.class.getDeclaredMethod("getImplementation");
               getImplementation_method.setAccessible(true);
               return (InputImplementation)getImplementation_method.invoke(null);
            }
         });
      } catch (PrivilegedActionException var1) {
         throw new Error(var1);
      }
   }

   static {
      try {
         global_lock = AccessController.doPrivileged(new PrivilegedExceptionAction<Object>() {
            @Override
            public Object run() throws Exception {
               Field lock_field = Class.forName("org.lwjgl.opengl.GlobalLock").getDeclaredField("lock");
               lock_field.setAccessible(true);
               return lock_field.get(null);
            }
         });
      } catch (PrivilegedActionException var1) {
         throw new Error(var1);
      }
   }
}
