package org.lwjgl.opencl;

import org.lwjgl.BufferChecks;

public final class APPLESetMemObjectDestructor {
   private APPLESetMemObjectDestructor() {
   }

   public static int clSetMemObjectDestructorAPPLE(CLMem memobj, CLMemObjectDestructorCallback pfn_notify) {
      long function_pointer = CLCapabilities.clSetMemObjectDestructorAPPLE;
      BufferChecks.checkFunctionAddress(function_pointer);
      long user_data = CallbackUtil.createGlobalRef(pfn_notify);
      int __result = 0;

      int var7;
      try {
         __result = nclSetMemObjectDestructorAPPLE(memobj.getPointer(), pfn_notify.getPointer(), user_data, function_pointer);
         var7 = __result;
      } finally {
         CallbackUtil.checkCallback(__result, user_data);
      }

      return var7;
   }

   static native int nclSetMemObjectDestructorAPPLE(long var0, long var2, long var4, long var6);
}
