package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import org.lwjgl.LWJGLException;

final class MacOSXPbufferPeerInfo extends MacOSXPeerInfo {
   MacOSXPbufferPeerInfo(int width, int height, PixelFormat pixel_format, ContextAttribs attribs) throws LWJGLException {
      super(pixel_format, attribs, false, false, true, false);
      nCreate(this.getHandle(), width, height);
   }

   private static native void nCreate(ByteBuffer var0, int var1, int var2) throws LWJGLException;

   @Override
   public void destroy() {
      nDestroy(this.getHandle());
   }

   private static native void nDestroy(ByteBuffer var0);

   @Override
   protected void doLockAndInitHandle() throws LWJGLException {
   }

   @Override
   protected void doUnlock() throws LWJGLException {
   }
}
