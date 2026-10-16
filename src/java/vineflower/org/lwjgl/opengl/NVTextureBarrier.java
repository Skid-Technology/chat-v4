package org.lwjgl.opengl;

import org.lwjgl.BufferChecks;

public final class NVTextureBarrier {
   private NVTextureBarrier() {
   }

   public static void glTextureBarrierNV() {
      ContextCapabilities caps = GLContext.getCapabilities();
      long function_pointer = caps.glTextureBarrierNV;
      BufferChecks.checkFunctionAddress(function_pointer);
      nglTextureBarrierNV(function_pointer);
   }

   static native void nglTextureBarrierNV(long var0);
}
