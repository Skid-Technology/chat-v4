/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.packet;

import gg.vape.friend.GroupUserModel;
import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.PartyMemberActionType;
import gg.vape.protocol.packet.ZeusSerializablePacket;

public class ServerPartyMemberUpdatePacket
implements ZeusSerializablePacket {
    private static String var_java_lang_String_j;
    private GroupUserModel var_gg_vape_friend_GroupUserModel_E;
    private PartyMemberActionType var_gg_vape_protocol_packet_PartyMemberActionType_o;

    @Override
    public void o(ZeusPacketBuffer zeusPacketBuffer) {
        this.var_gg_vape_friend_GroupUserModel_E.writeTo(zeusPacketBuffer);
        zeusPacketBuffer.writeEnum(this.var_gg_vape_protocol_packet_PartyMemberActionType_o);
    }

    public static void x(String string) {
        var_java_lang_String_j = string;
    }

    public GroupUserModel R() {
        return this.var_gg_vape_friend_GroupUserModel_E;
    }

    public ServerPartyMemberUpdatePacket(GroupUserModel groupUserModel, PartyMemberActionType partyMemberActionType) {
        this.var_gg_vape_friend_GroupUserModel_E = groupUserModel;
        this.var_gg_vape_protocol_packet_PartyMemberActionType_o = partyMemberActionType;
    }

    public ServerPartyMemberUpdatePacket() {
    }

    public static String java_lang_String_o() {
        return var_java_lang_String_j;
    }

    @Override
    public void S(ZeusPacketBuffer zeusPacketBuffer) {
        this.var_gg_vape_friend_GroupUserModel_E = new GroupUserModel(zeusPacketBuffer);
        this.var_gg_vape_protocol_packet_PartyMemberActionType_o = zeusPacketBuffer.readEnum(PartyMemberActionType.class);
    }

    public PartyMemberActionType a_EN_o() {
        return this.var_gg_vape_protocol_packet_PartyMemberActionType_o;
    }

    public PartyMemberActionType o() {
        return this.a_EN_o();
    }

    static {
        if (ServerPartyMemberUpdatePacket.java_lang_String_o() == null) {
            ServerPartyMemberUpdatePacket.x("rgtxib");
        }
    }
}

