package org.lwjgl;

abstract class DefaultSysImplementation implements SysImplementation {
   @Override
   public native int getJNIVersion();

   @Override
   public native int getPointerSize();

   @Override
   public native void setDebug(boolean var1);

   @Override
   public long getTimerResolution() {
      return 1000L;
   }

   @Override
   public boolean has64Bit() {
      return false;
   }

   @Override
   public abstract long getTime();

   @Override
   public abstract void alert(String var1, String var2);

   @Override
   public abstract String getClipboard();
}
