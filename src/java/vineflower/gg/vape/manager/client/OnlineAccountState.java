package gg.vape.manager.client;

public enum OnlineAccountState {
   REGISTERED,
   BANNED,
   UNREGISTERED,
   REGISTRATION_OFFLINE,
   CONNECTING;

   private static final OnlineAccountState[] cachedValues = new OnlineAccountState[]{REGISTERED, BANNED, UNREGISTERED, REGISTRATION_OFFLINE, CONNECTING};

   static {
      String[] var10000 = new String[]{"REGISTRATION_OFFLINE", "CONNECTING", "BANNED", "REGISTERED", "UNREGISTERED"};
   }
}
