package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import org.lwjgl.LWJGLException;

final class LinuxPbufferPeerInfo extends LinuxPeerInfo {
   LinuxPbufferPeerInfo(int width, int height, PixelFormat pixel_format) throws LWJGLException {
      LinuxDisplay.lockAWT();

      try {
         GLContext.loadOpenGLLibrary();

         try {
            LinuxDisplay.incDisplay();

            try {
               nInitHandle(LinuxDisplay.getDisplay(), LinuxDisplay.getDefaultScreen(), this.getHandle(), width, height, pixel_format);
            } catch (LWJGLException var9) {
               LinuxDisplay.decDisplay();
               throw var9;
            }
         } catch (LWJGLException var10) {
            GLContext.unloadOpenGLLibrary();
            throw var10;
         }
      } finally {
         LinuxDisplay.unlockAWT();
      }
   }

   private static native void nInitHandle(long var0, int var2, ByteBuffer var3, int var4, int var5, PixelFormat var6) throws LWJGLException;

   @Override
   public void destroy() {
      LinuxDisplay.lockAWT();
      nDestroy(this.getHandle());
      LinuxDisplay.decDisplay();
      GLContext.unloadOpenGLLibrary();
      LinuxDisplay.unlockAWT();
   }

   private static native void nDestroy(ByteBuffer var0);

   @Override
   protected void doLockAndInitHandle() throws LWJGLException {
   }

   @Override
   protected void doUnlock() throws LWJGLException {
   }
}
