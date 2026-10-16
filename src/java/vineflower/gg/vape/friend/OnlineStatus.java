package gg.vape.friend;

import gg.vape.protocol.PresenceState;
import java.awt.Color;
import java.util.Arrays;
import java.util.List;

public enum OnlineStatus {
   ONLINE("Online", PresenceState.ONLINE, new Color(5, 134, 105)),
   AWAY("Away", PresenceState.AWAY, new Color(180, 120, 50)),
   OFFLINE("Offline", PresenceState.OFFLINE, new Color(89, 88, 89));

   private final Color color;
   private static final OnlineStatus[] VALUES_COPY = new OnlineStatus[]{ONLINE, AWAY, OFFLINE};
   private static final List<OnlineStatus> ALL_STATUSES = Arrays.asList(values());
   private final String displayName;
   private final PresenceState presenceState;

   public Color getColor() {
      return this.color;
   }

   public String getDisplayName() {
      return this.displayName;
   }

   private OnlineStatus(String displayName, PresenceState presenceState, Color color) {
      this.displayName = displayName;
      this.presenceState = presenceState;
      this.color = color;
   }

   public PresenceState getPresenceState() {
      return this.presenceState;
   }

   public static OnlineStatus fromPresenceState(PresenceState presenceState) {
      for (OnlineStatus status : ALL_STATUSES) {
         if (status.presenceState == presenceState) {
            return status;
         }
      }

      return null;
   }

   static {
      String[] var10000 = new String[]{"AWAY", "Online", "Offline", "Away", "ONLINE", "OFFLINE"};
   }
}
