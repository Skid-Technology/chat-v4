/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.event;

import gg.vape.friend.FriendRequestModel;
import gg.vape.protocol.ZeusClient;
import gg.vape.protocol.event.OnlineEvent;

public abstract class UserOnlineEvent
extends OnlineEvent {
    private final FriendRequestModel var_gg_vape_friend_FriendRequestModel_G;
    private static boolean var_boolean_y;

    public UserOnlineEvent(ZeusClient oZ, FriendRequestModel friendRequestModel) {
        super(oZ);
        this.var_gg_vape_friend_FriendRequestModel_G = friendRequestModel;
    }

    public static boolean M() {
        return var_boolean_y;
    }

    public static void L(boolean bl) {
        var_boolean_y = bl;
    }

    public static boolean I() {
        boolean bl = UserOnlineEvent.M();
        return true;
    }

    public FriendRequestModel q() {
        return this.var_gg_vape_friend_FriendRequestModel_G;
    }

    static {
        if (UserOnlineEvent.M()) {
            UserOnlineEvent.L(true);
        }
    }
}

