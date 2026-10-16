package gg.vape.friend;

import gg.vape.Vape;
import gg.vape.manager.client.OnlineConnectionManager;
import gg.vape.manager.client.OnlineSettings;
import java.awt.Color;
import java.util.LinkedHashMap;
import java.util.Map;

public class OnlineFriendColorUtil {
   private static final Map<Integer, Color> GROUP_ROLE_COLORS = new LinkedHashMap<>();
   private static final Color DEFAULT_GROUP_ROLE_COLOR = new Color(255, 255, 255);

   public static Color getDisplayColor(OnlineFriend onlineFriend) {
      OnlineSettings onlineSettings = OnlineConnectionManager.INSTANCE.getSettings();
      if (onlineSettings.getIndicatorColorMode().getValue().equals(onlineSettings.getPartyColorOption())) {
         return getGroupRoleColor(onlineFriend);
      } else {
         return onlineSettings.getIndicatorColorMode().getValue().equals(onlineSettings.getFriendColorOption()) ? getFriendColor() : Color.WHITE;
      }
   }

   public static Color getFriendColor() {
      return Vape.INSTANCE.getFriendManager().friendColor.getMutableColor();
   }

   public static Color getGroupRoleColor(int groupRole) {
      return groupRole != -1 && groupRole <= 8 ? GROUP_ROLE_COLORS.getOrDefault(groupRole, DEFAULT_GROUP_ROLE_COLOR) : DEFAULT_GROUP_ROLE_COLOR;
   }

   private static void initializeGroupRoleColors() {
      GROUP_ROLE_COLORS.put(0, new Color(5, 134, 105));
      GROUP_ROLE_COLORS.put(1, new Color(47, 122, 229));
      GROUP_ROLE_COLORS.put(2, new Color(250, 50, 56));
      GROUP_ROLE_COLORS.put(3, new Color(126, 84, 217));
      GROUP_ROLE_COLORS.put(4, new Color(242, 99, 33));
      GROUP_ROLE_COLORS.put(5, new Color(252, 179, 22));
      GROUP_ROLE_COLORS.put(6, new Color(232, 96, 152));
      GROUP_ROLE_COLORS.put(7, new Color(145, 145, 145));
      GROUP_ROLE_COLORS.put(8, new Color(126, 65, 19));
   }

   public static Color getGroupRoleColor(OnlineFriend onlineFriend) {
      return getGroupRoleColor(onlineFriend.getGroupRole());
   }

   static {
      initializeGroupRoleColors();
   }
}
