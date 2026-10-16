package org.lwjgl.opencl;

interface InfoUtil<T extends CLObject> {
   int getInfoInt(T var1, int var2);

   long getInfoSize(T var1, int var2);

   long[] getInfoSizeArray(T var1, int var2);

   long getInfoLong(T var1, int var2);

   String getInfoString(T var1, int var2);
}
