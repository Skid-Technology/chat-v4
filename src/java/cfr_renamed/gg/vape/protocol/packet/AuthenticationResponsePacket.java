/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.packet;

import gg.vape.friend.UserModel;
import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.AuthenticationPacket;
import gg.vape.protocol.packet.ZeusTrackedPacket;

public class AuthenticationResponsePacket
extends ZeusTrackedPacket<AuthenticationPacket> {
    private UserModel var_gg_vape_friend_UserModel_E;

    @Override
    public void x(ZeusPacketBuffer gx_12) {
        int n = gx_12.readVarInt();
        this.var_gg_vape_friend_UserModel_E = new UserModel(gx_12);
    }

    public AuthenticationResponsePacket() {
    }

    public UserModel s() {
        return this.var_gg_vape_friend_UserModel_E;
    }

    @Override
    public void T(ZeusPacketBuffer gx_12) {
        gx_12.writeVarInt(1);
        this.var_gg_vape_friend_UserModel_E.writeTo(gx_12);
    }

    public AuthenticationResponsePacket(AuthenticationPacket authenticationPacket, UserModel oj_12) {
        this(authenticationPacket);
        this.var_gg_vape_friend_UserModel_E = oj_12;
    }

    public AuthenticationResponsePacket(AuthenticationPacket authenticationPacket) {
        super(authenticationPacket);
    }
}

