package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.LWJGLException;
import org.lwjgl.LWJGLUtil;

final class WindowsContextImplementation implements ContextImplementation {
   @Override
   public ByteBuffer create(PeerInfo peer_info, IntBuffer attribs, ByteBuffer shared_context_handle) throws LWJGLException {
      ByteBuffer peer_handle = peer_info.lockAndGetHandle();

      ByteBuffer var5;
      try {
         var5 = nCreate(peer_handle, attribs, shared_context_handle);
      } finally {
         peer_info.unlock();
      }

      return var5;
   }

   private static native ByteBuffer nCreate(ByteBuffer var0, IntBuffer var1, ByteBuffer var2) throws LWJGLException;

   native long getHGLRC(ByteBuffer var1);

   native long getHDC(ByteBuffer var1);

   @Override
   public void swapBuffers() throws LWJGLException {
      ContextGL current_context = ContextGL.getCurrentContext();
      if (current_context == null) {
         throw new IllegalStateException("No context is current");
      } else {
         synchronized (current_context) {
            PeerInfo current_peer_info = current_context.getPeerInfo();
            ByteBuffer peer_handle = current_peer_info.lockAndGetHandle();

            try {
               nSwapBuffers(peer_handle);
            } finally {
               current_peer_info.unlock();
            }
         }
      }
   }

   private static native void nSwapBuffers(ByteBuffer var0) throws LWJGLException;

   @Override
   public void releaseDrawable(ByteBuffer context_handle) throws LWJGLException {
   }

   @Override
   public void update(ByteBuffer context_handle) {
   }

   @Override
   public void releaseCurrentContext() throws LWJGLException {
      nReleaseCurrentContext();
   }

   private static native void nReleaseCurrentContext() throws LWJGLException;

   @Override
   public void makeCurrent(PeerInfo peer_info, ByteBuffer handle) throws LWJGLException {
      ByteBuffer peer_handle = peer_info.lockAndGetHandle();

      try {
         nMakeCurrent(peer_handle, handle);
      } finally {
         peer_info.unlock();
      }
   }

   private static native void nMakeCurrent(ByteBuffer var0, ByteBuffer var1) throws LWJGLException;

   @Override
   public boolean isCurrent(ByteBuffer handle) throws LWJGLException {
      return nIsCurrent(handle);
   }

   private static native boolean nIsCurrent(ByteBuffer var0) throws LWJGLException;

   @Override
   public void setSwapInterval(int value) {
      boolean success = nSetSwapInterval(value);
      if (!success) {
         LWJGLUtil.log("Failed to set swap interval");
      }

      Util.checkGLError();
   }

   private static native boolean nSetSwapInterval(int var0);

   @Override
   public void destroy(PeerInfo peer_info, ByteBuffer handle) throws LWJGLException {
      nDestroy(handle);
   }

   private static native void nDestroy(ByteBuffer var0) throws LWJGLException;
}
