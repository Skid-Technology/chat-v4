/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.packet;

import gg.vape.friend.UserModel;
import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.ZeusSerializablePacket;

public class ServerGroupInvitePacket
implements ZeusSerializablePacket {
    private UserModel var_gg_vape_friend_UserModel_H;

    public UserModel Z() {
        return this.var_gg_vape_friend_UserModel_H;
    }

    @Override
    public void S(ZeusPacketBuffer zeusPacketBuffer) {
        this.var_gg_vape_friend_UserModel_H = new UserModel(zeusPacketBuffer);
    }

    public ServerGroupInvitePacket(UserModel userModel) {
        this.var_gg_vape_friend_UserModel_H = userModel;
    }

    public ServerGroupInvitePacket() {
    }

    @Override
    public void o(ZeusPacketBuffer zeusPacketBuffer) {
        this.var_gg_vape_friend_UserModel_H.writeTo(zeusPacketBuffer);
    }
}

