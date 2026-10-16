/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.packet;

import gg.vape.friend.UserModel;
import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.ZeusSerializablePacket;

public class ServerGroupInviteRemovedPacket
implements ZeusSerializablePacket {
    private UserModel var_gg_vape_friend_UserModel_d;

    public ServerGroupInviteRemovedPacket() {
    }

    @Override
    public void S(ZeusPacketBuffer zeusPacketBuffer) {
        this.var_gg_vape_friend_UserModel_d = new UserModel(zeusPacketBuffer);
    }

    public ServerGroupInviteRemovedPacket(UserModel userModel) {
        this.var_gg_vape_friend_UserModel_d = userModel;
    }

    @Override
    public void o(ZeusPacketBuffer zeusPacketBuffer) {
        this.var_gg_vape_friend_UserModel_d.writeTo(zeusPacketBuffer);
    }

    public UserModel G() {
        return this.var_gg_vape_friend_UserModel_d;
    }
}

