package org.lwjgl.opengl;

import java.util.HashMap;
import java.util.Map;

final class CallbackUtil {
   private static final Map<ContextCapabilities, Long> contextUserParamsARB = new HashMap<>();
   private static final Map<ContextCapabilities, Long> contextUserParamsAMD = new HashMap<>();
   private static final Map<ContextCapabilities, Long> contextUserParamsKHR = new HashMap<>();

   private CallbackUtil() {
   }

   static long createGlobalRef(Object obj) {
      return obj == null ? 0L : ncreateGlobalRef(obj);
   }

   private static native long ncreateGlobalRef(Object var0);

   private static native void deleteGlobalRef(long var0);

   private static void registerContextCallback(long userParam, Map<ContextCapabilities, Long> contextUserData) {
      ContextCapabilities caps = GLContext.getCapabilities();
      if (caps == null) {
         deleteGlobalRef(userParam);
         throw new IllegalStateException("No context is current.");
      } else {
         Long userParam_old = contextUserData.remove(caps);
         if (userParam_old != null) {
            deleteGlobalRef(userParam_old);
         }

         if (userParam != 0L) {
            contextUserData.put(caps, userParam);
         }
      }
   }

   static void unregisterCallbacks(Object context) {
      ContextCapabilities caps = GLContext.getCapabilities(context);
      Long userParam = contextUserParamsARB.remove(caps);
      if (userParam != null) {
         deleteGlobalRef(userParam);
      }

      userParam = contextUserParamsAMD.remove(caps);
      if (userParam != null) {
         deleteGlobalRef(userParam);
      }

      userParam = contextUserParamsKHR.remove(caps);
      if (userParam != null) {
         deleteGlobalRef(userParam);
      }
   }

   static native long getDebugOutputCallbackARB();

   static void registerContextCallbackARB(long userParam) {
      registerContextCallback(userParam, contextUserParamsARB);
   }

   static native long getDebugOutputCallbackAMD();

   static void registerContextCallbackAMD(long userParam) {
      registerContextCallback(userParam, contextUserParamsAMD);
   }

   static native long getDebugCallbackKHR();

   static void registerContextCallbackKHR(long userParam) {
      registerContextCallback(userParam, contextUserParamsKHR);
   }
}
