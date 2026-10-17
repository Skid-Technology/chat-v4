package gg.vape.protocol.packet;

public enum PartyMemberActionType {
   ADD,
   REMOVE;

   private static final PartyMemberActionType[] g = new PartyMemberActionType[]{ADD, REMOVE};

   static {
      String[] var10000 = new String[]{"ADD", "REMOVE"};
   }
}
