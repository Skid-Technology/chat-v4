/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.event;

import gg.vape.protocol.packet.PartyMemberActionType;
import java.util.Arrays;
import java.util.List;

public enum PartyMemberAction {
    ADD(PartyMemberActionType.ADD),
    REMOVE(PartyMemberActionType.REMOVE);

    private final static List<PartyMemberAction> var_java_util_List_gg_vape_protocol_event_PartyMemberAction__M;
    private final PartyMemberActionType var_gg_vape_protocol_packet_PartyMemberActionType_I;
    private final static PartyMemberAction[] var_gg_vape_protocol_event_PartyMemberAction_arr_p;

    public PartyMemberActionType c() {
        return this.var_gg_vape_protocol_packet_PartyMemberActionType_I;
    }

    public static PartyMemberAction J(PartyMemberActionType partyMemberActionType) {
        for (PartyMemberAction partyMemberAction : var_java_util_List_gg_vape_protocol_event_PartyMemberAction__M) {
            if (partyMemberAction.var_gg_vape_protocol_packet_PartyMemberActionType_I != partyMemberActionType) continue;
            return partyMemberAction;
        }
        return null;
    }

    private PartyMemberAction(PartyMemberActionType partyMemberActionType) {
        this.var_gg_vape_protocol_packet_PartyMemberActionType_I = partyMemberActionType;
    }

    static {
        String[] stringArray = new String[]{"ADD", "REMOVE"};
        var_gg_vape_protocol_event_PartyMemberAction_arr_p = new PartyMemberAction[]{ADD, REMOVE};
        var_java_util_List_gg_vape_protocol_event_PartyMemberAction__M = Arrays.asList(PartyMemberAction.values());
    }
}

