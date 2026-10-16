package gg.vape.manager.client;

public enum OnlineDisconnectReason {
   UNKNOWN(true, true),
   LOGGED_IN_FROM_ANOTHER_LOCATION(false, true),
   BANNED(false, false),
   AUTH_FAILED(false, false);

   private final boolean allowsAutomaticReconnect;
   private static final OnlineDisconnectReason[] cachedValues = new OnlineDisconnectReason[]{UNKNOWN, LOGGED_IN_FROM_ANOTHER_LOCATION, BANNED, AUTH_FAILED};
   private final boolean reservedFlag;

   private OnlineDisconnectReason(boolean allowsAutomaticReconnect, boolean reservedFlag) {
      this.allowsAutomaticReconnect = allowsAutomaticReconnect;
      this.reservedFlag = reservedFlag;
   }

   public boolean allowsAutomaticReconnect() {
      return this.allowsAutomaticReconnect;
   }

   public boolean isReservedFlagEnabled() {
      return this.reservedFlag;
   }

   static {
      String[] var10000 = new String[]{"LOGGED_IN_FROM_ANOTHER_LOCATION", "AUTH_FAILED", "BANNED", "UNKNOWN"};
   }
}
