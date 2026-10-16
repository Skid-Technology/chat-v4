package org.lwjgl;

import com.apple.eio.FileManager;
import java.awt.Toolkit;

final class MacOSXSysImplementation extends J2SESysImplementation {
   private static final int JNI_VERSION = 25;

   @Override
   public int getRequiredJNIVersion() {
      return 25;
   }

   @Override
   public boolean openURL(String url) {
      try {
         FileManager.openURL(url);
         return true;
      } catch (Exception var3) {
         LWJGLUtil.log("Exception occurred while trying to invoke browser: " + var3);
         return false;
      }
   }

   static {
      Toolkit.getDefaultToolkit();
   }
}
