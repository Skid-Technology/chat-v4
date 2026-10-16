package gg.vape.protocol;

public enum PresenceState {
   ONLINE,
   AWAY,
   OFFLINE;

   private static final PresenceState[] Q = new PresenceState[]{ONLINE, AWAY, OFFLINE};

   static {
      String[] var10000 = new String[]{"OFFLINE", "AWAY", "ONLINE"};
   }
}
