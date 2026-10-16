package gg.vape.account;

public class LicenseManager {
   private String licenseKey;
   private LicenseInfo licenseInfo;
   private static int[] opaqueState;

   public static int[] getOpaqueState() {
      return opaqueState;
   }

   public LicenseInfo getLicenseInfo() {
      return this.licenseInfo;
   }

   public static void setOpaqueState(int[] state) {
      opaqueState = state;
   }

   public LicenseStatus generateAccount() {
      return this.licenseKey == null ? null : new LicenseStatusClient().generateAccount(this.licenseKey);
   }

   public LicenseInfo fetchLicenseInfo(String licenseKey) {
      this.licenseInfo = new LicenseInfoClient().fetchLicenseInfo(licenseKey);
      if (this.licenseInfo != null) {
         this.licenseKey = licenseKey;
      }

      return this.licenseInfo;
   }

   static {
      if (getOpaqueState() == null) {
         setOpaqueState(new int[1]);
      }
   }
}
