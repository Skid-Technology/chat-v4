/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.event;

import gg.vape.friend.GroupUserModel;
import gg.vape.protocol.ZeusClient;
import gg.vape.protocol.event.OnlineEvent;
import gg.vape.protocol.event.PartyMemberAction;
import gg.vape.protocol.packet.PartyMemberActionType;

public class PartyMemberUpdateEvent
extends OnlineEvent {
    private final GroupUserModel var_gg_vape_friend_GroupUserModel_V;
    private final PartyMemberAction var_gg_vape_protocol_event_PartyMemberAction_R;
    private static String[] var_java_lang_String_arr_o;

    public static String[] w() {
        return var_java_lang_String_arr_o;
    }

    public GroupUserModel S() {
        return this.var_gg_vape_friend_GroupUserModel_V;
    }

    public PartyMemberAction q() {
        return this.var_gg_vape_protocol_event_PartyMemberAction_R;
    }

    public static void h(String[] stringArray) {
        var_java_lang_String_arr_o = stringArray;
    }

    public PartyMemberUpdateEvent(ZeusClient oZ, GroupUserModel groupUserModel, PartyMemberActionType eN) {
        super(oZ);
        this.var_gg_vape_friend_GroupUserModel_V = groupUserModel;
        this.var_gg_vape_protocol_event_PartyMemberAction_R = PartyMemberAction.J(eN);
    }

    static {
        if (PartyMemberUpdateEvent.w() != null) {
            PartyMemberUpdateEvent.h(new String[4]);
        }
    }
}

