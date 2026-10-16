package org.lwjgl;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import org.lwjgl.opengl.Display;

final class WindowsSysImplementation extends DefaultSysImplementation {
   private static final int JNI_VERSION = 24;

   @Override
   public int getRequiredJNIVersion() {
      return 24;
   }

   @Override
   public long getTimerResolution() {
      return 1000L;
   }

   @Override
   public long getTime() {
      return nGetTime();
   }

   private static native long nGetTime();

   @Override
   public boolean has64Bit() {
      return true;
   }

   private static long getHwnd() {
      if (!Display.isCreated()) {
         return 0L;
      } else {
         try {
            return AccessController.doPrivileged(new PrivilegedExceptionAction<Long>() {
               public Long run() throws Exception {
                  Method getImplementation_method = Display.class.getDeclaredMethod("getImplementation");
                  getImplementation_method.setAccessible(true);
                  Object display_impl = getImplementation_method.invoke(null);
                  Class<?> WindowsDisplay_class = Class.forName("org.lwjgl.opengl.WindowsDisplay");
                  Method getHwnd_method = WindowsDisplay_class.getDeclaredMethod("getHwnd");
                  getHwnd_method.setAccessible(true);
                  return (Long)getHwnd_method.invoke(display_impl);
               }
            });
         } catch (PrivilegedActionException var1) {
            throw new Error(var1);
         }
      }
   }

   @Override
   public void alert(String title, String message) {
      if (!Display.isCreated()) {
         initCommonControls();
      }

      LWJGLUtil.log(String.format("*** Alert *** %s\n%s\n", title, message));
      ByteBuffer titleText = MemoryUtil.encodeUTF16(title);
      ByteBuffer messageText = MemoryUtil.encodeUTF16(message);
      nAlert(getHwnd(), MemoryUtil.getAddress(titleText), MemoryUtil.getAddress(messageText));
   }

   private static native void nAlert(long var0, long var2, long var4);

   private static native void initCommonControls();

   @Override
   public boolean openURL(String url) {
      try {
         LWJGLUtil.execPrivileged(new String[]{"rundll32", "url.dll,FileProtocolHandler", url});
         return true;
      } catch (Exception var3) {
         LWJGLUtil.log("Failed to open url (" + url + "): " + var3.getMessage());
         return false;
      }
   }

   @Override
   public String getClipboard() {
      return nGetClipboard();
   }

   private static native String nGetClipboard();

   static {
      Sys.initialize();
   }
}
