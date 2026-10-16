package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import org.lwjgl.BufferChecks;
import org.lwjgl.MemoryUtil;

public final class GREMEDYStringMarker {
   private GREMEDYStringMarker() {
   }

   public static void glStringMarkerGREMEDY(ByteBuffer string) {
      ContextCapabilities caps = GLContext.getCapabilities();
      long function_pointer = caps.glStringMarkerGREMEDY;
      BufferChecks.checkFunctionAddress(function_pointer);
      BufferChecks.checkDirect(string);
      nglStringMarkerGREMEDY(string.remaining(), MemoryUtil.getAddress(string), function_pointer);
   }

   static native void nglStringMarkerGREMEDY(int var0, long var1, long var3);

   public static void glStringMarkerGREMEDY(CharSequence string) {
      ContextCapabilities caps = GLContext.getCapabilities();
      long function_pointer = caps.glStringMarkerGREMEDY;
      BufferChecks.checkFunctionAddress(function_pointer);
      nglStringMarkerGREMEDY(string.length(), APIUtil.getBuffer(caps, string), function_pointer);
   }
}
