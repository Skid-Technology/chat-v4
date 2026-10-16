package gg.vape.protocol.event;

import gg.vape.protocol.packet.PartyMemberActionType;
import java.util.Arrays;
import java.util.List;

public enum PartyMemberAction {
   ADD(PartyMemberActionType.ADD),
   REMOVE(PartyMemberActionType.REMOVE);

   private static final List<PartyMemberAction> M = Arrays.asList(values());
   private final PartyMemberActionType I;
   private static final PartyMemberAction[] p = new PartyMemberAction[]{ADD, REMOVE};

   public PartyMemberActionType c() {
      return this.I;
   }

   public static PartyMemberAction J(PartyMemberActionType partyMemberActionType) {
      for (PartyMemberAction partyMemberAction : M) {
         if (partyMemberAction.I == partyMemberActionType) {
            return partyMemberAction;
         }
      }

      return null;
   }

   private PartyMemberAction(PartyMemberActionType partyMemberActionType) {
      this.I = partyMemberActionType;
   }

   static {
      String[] var10000 = new String[]{"ADD", "REMOVE"};
   }
}
