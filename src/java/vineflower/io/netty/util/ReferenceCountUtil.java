package io.netty.util;

import io.netty.util.internal.StringUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

public final class ReferenceCountUtil {
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ReferenceCountUtil.class);

   public static <T> T retain(T msg) {
      return (T)(msg instanceof ReferenceCounted ? ((ReferenceCounted)msg).retain() : msg);
   }

   public static <T> T retain(T msg, int increment) {
      return (T)(msg instanceof ReferenceCounted ? ((ReferenceCounted)msg).retain(increment) : msg);
   }

   public static boolean release(Object msg) {
      return msg instanceof ReferenceCounted ? ((ReferenceCounted)msg).release() : false;
   }

   public static boolean release(Object msg, int decrement) {
      return msg instanceof ReferenceCounted ? ((ReferenceCounted)msg).release(decrement) : false;
   }

   public static void safeRelease(Object msg) {
      try {
         release(msg);
      } catch (Throwable var2) {
         logger.warn("Failed to release a message: {}", msg, var2);
      }
   }

   public static void safeRelease(Object msg, int decrement) {
      try {
         release(msg, decrement);
      } catch (Throwable var3) {
         if (logger.isWarnEnabled()) {
            logger.warn("Failed to release a message: {} (decrement: {})", msg, decrement, var3);
         }
      }
   }

   public static <T> T releaseLater(T msg) {
      return releaseLater(msg, 1);
   }

   public static <T> T releaseLater(T msg, int decrement) {
      if (msg instanceof ReferenceCounted) {
         ThreadDeathWatcher.watch(Thread.currentThread(), new ReferenceCountUtil.ReleasingTask((ReferenceCounted)msg, decrement));
      }

      return msg;
   }

   private ReferenceCountUtil() {
   }

   private static final class ReleasingTask implements Runnable {
      private final ReferenceCounted obj;
      private final int decrement;

      ReleasingTask(ReferenceCounted obj, int decrement) {
         this.obj = obj;
         this.decrement = decrement;
      }

      @Override
      public void run() {
         try {
            if (!this.obj.release(this.decrement)) {
               ReferenceCountUtil.logger.warn("Non-zero refCnt: {}", this);
            } else {
               ReferenceCountUtil.logger.debug("Released: {}", this);
            }
         } catch (Exception var2) {
            ReferenceCountUtil.logger.warn("Failed to release an object: {}", this.obj, var2);
         }
      }

      @Override
      public String toString() {
         return StringUtil.simpleClassName(this.obj) + ".release(" + this.decrement + ") refCnt: " + this.obj.refCnt();
      }
   }
}
