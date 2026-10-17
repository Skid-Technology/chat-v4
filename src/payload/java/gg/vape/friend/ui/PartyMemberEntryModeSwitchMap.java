package gg.vape.friend.ui;

class PartyMemberEntryModeSwitchMap {
   static final int[] MODE_MAPPINGS = new int[PartyMemberEntryMode.values().length];

   static {
      try {
         MODE_MAPPINGS[PartyMemberEntryMode.CURRENT_PARTY.ordinal()] = 1;
      } catch (NoSuchFieldError var2) {
      }

      try {
         MODE_MAPPINGS[PartyMemberEntryMode.INVITE.ordinal()] = 2;
      } catch (NoSuchFieldError var1) {
      }
   }
}
