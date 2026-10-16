package org.lwjgl.opengl;

final class WindowsFileVersion {
   private final int product_version_ms;
   private final int product_version_ls;

   WindowsFileVersion(int product_version_ms, int product_version_ls) {
      this.product_version_ms = product_version_ms;
      this.product_version_ls = product_version_ls;
   }

   @Override
   public String toString() {
      int f1 = this.product_version_ms >> 16 & 65535;
      int f2 = this.product_version_ms & 65535;
      int f3 = this.product_version_ls >> 16 & 65535;
      int f4 = this.product_version_ls & 65535;
      return f1 + "." + f2 + "." + f3 + "." + f4;
   }
}
