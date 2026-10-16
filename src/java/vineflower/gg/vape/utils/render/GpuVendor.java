package gg.vape.utils.render;

public enum GpuVendor {
   NVIDIA,
   AMD,
   INTEL,
   UNKNOWN;

   private static final GpuVendor[] VALUES = new GpuVendor[]{NVIDIA, AMD, INTEL, UNKNOWN};

   static {
      String[] var10000 = new String[]{"UNKNOWN", "NVIDIA", "AMD", "INTEL"};
   }
}
