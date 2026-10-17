package gg.vape.protocol.packet;

public enum UserDisplayNameStatus {
   SUCCESSFUL,
   COOLDOWN,
   USERNAME_TAKEN,
   USERNAME_VALIDATION_FAILED,
   BANNED,
   FAILED;

   private static final UserDisplayNameStatus[] G = new UserDisplayNameStatus[]{
      SUCCESSFUL, COOLDOWN, USERNAME_TAKEN, USERNAME_VALIDATION_FAILED, BANNED, FAILED
   };

   static {
      String[] var10000 = new String[]{"USERNAME_VALIDATION_FAILED", "BANNED", "SUCCESSFUL", "USERNAME_TAKEN", "COOLDOWN", "FAILED"};
   }
}
