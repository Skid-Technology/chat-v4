/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package gg.vape.protocol.packet;

import gg.vape.friend.UserModel;
import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.GroupInviteUpdateStatus;
import gg.vape.protocol.packet.ZeusSerializablePacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ServerGroupInviteUpdatePacket
implements ZeusSerializablePacket {
    @NotNull
    private UserModel var_gg_vape_friend_UserModel_x;
    @Nullable
    private UserModel var_gg_vape_friend_UserModel_u;
    @NotNull
    private GroupInviteUpdateStatus var_gg_vape_protocol_packet_GroupInviteUpdateStatus_p;
    final static boolean var_boolean_K;

    public static ServerGroupInviteUpdatePacket L(UserModel oj_12) {
        return new ServerGroupInviteUpdatePacket(null, oj_12, GroupInviteUpdateStatus.DECLINED);
    }

    public static ServerGroupInviteUpdatePacket T(UserModel oj_12, UserModel oj_13) {
        return new ServerGroupInviteUpdatePacket(oj_12, oj_13, GroupInviteUpdateStatus.SENT);
    }

    @Nullable
    public UserModel k() {
        return this.var_gg_vape_friend_UserModel_u;
    }

    @NotNull
    public UserModel f() {
        return this.var_gg_vape_friend_UserModel_x;
    }

    private ServerGroupInviteUpdatePacket(@Nullable UserModel oj_12, @NotNull UserModel oj_13, @NotNull GroupInviteUpdateStatus p4) {
        this.var_gg_vape_friend_UserModel_u = oj_12;
        this.var_gg_vape_friend_UserModel_x = oj_13;
        this.var_gg_vape_protocol_packet_GroupInviteUpdateStatus_p = p4;
    }

    @Override
    public void o(ZeusPacketBuffer gx_12) {
        gx_12.writeEnum(this.var_gg_vape_protocol_packet_GroupInviteUpdateStatus_p);
        if (this.var_gg_vape_protocol_packet_GroupInviteUpdateStatus_p == GroupInviteUpdateStatus.SENT) {
            if (!var_boolean_K && this.var_gg_vape_friend_UserModel_u == null) {
                throw new AssertionError();
            }
            this.var_gg_vape_friend_UserModel_u.writeTo(gx_12);
        }
        this.var_gg_vape_friend_UserModel_x.writeTo(gx_12);
    }

    public ServerGroupInviteUpdatePacket() {
    }

    @NotNull
    public GroupInviteUpdateStatus e() {
        return this.var_gg_vape_protocol_packet_GroupInviteUpdateStatus_p;
    }

    @Override
    public void S(ZeusPacketBuffer gx_12) {
        this.var_gg_vape_protocol_packet_GroupInviteUpdateStatus_p = gx_12.readEnum(GroupInviteUpdateStatus.class);
        if (this.var_gg_vape_protocol_packet_GroupInviteUpdateStatus_p == GroupInviteUpdateStatus.SENT) {
            this.var_gg_vape_friend_UserModel_u = new UserModel(gx_12);
        }
        this.var_gg_vape_friend_UserModel_x = new UserModel(gx_12);
    }

    public static ServerGroupInviteUpdatePacket K(UserModel oj_12) {
        return new ServerGroupInviteUpdatePacket(null, oj_12, GroupInviteUpdateStatus.ACCEPTED);
    }

    static {
        boolean bl = !ServerGroupInviteUpdatePacket.class.desiredAssertionStatus();
        var_boolean_K = bl;
    }
}

