/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.packet;

import gg.vape.friend.UserModel;
import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.ZeusSerializablePacket;

public class ServerFriendRemovedPacket
implements ZeusSerializablePacket {
    private UserModel var_gg_vape_friend_UserModel_M;

    public UserModel m() {
        return this.var_gg_vape_friend_UserModel_M;
    }

    public ServerFriendRemovedPacket() {
    }

    @Override
    public void S(ZeusPacketBuffer zeusPacketBuffer) {
        this.var_gg_vape_friend_UserModel_M = new UserModel(zeusPacketBuffer);
    }

    public ServerFriendRemovedPacket(UserModel userModel) {
        this.var_gg_vape_friend_UserModel_M = userModel;
    }

    @Override
    public void o(ZeusPacketBuffer zeusPacketBuffer) {
        this.var_gg_vape_friend_UserModel_M.writeTo(zeusPacketBuffer);
    }
}

