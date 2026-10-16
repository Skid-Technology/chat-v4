/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.event;

import gg.vape.friend.UserModel;
import gg.vape.protocol.ZeusClient;
import gg.vape.protocol.event.OnlineEvent;

public class FriendRequestEvent
extends OnlineEvent {
    private static int var_int_y;
    private final UserModel var_gg_vape_friend_UserModel_k;

    public static void i(int n) {
        var_int_y = n;
    }

    public static int R() {
        int n = FriendRequestEvent.c();
        if (n == 0) {
            return 36;
        }
        return 0;
    }

    public FriendRequestEvent(ZeusClient oZ, UserModel oj_12) {
        super(oZ);
        this.var_gg_vape_friend_UserModel_k = oj_12;
    }

    public static int c() {
        return var_int_y;
    }

    public UserModel f() {
        return this.var_gg_vape_friend_UserModel_k;
    }

    static {
        if (FriendRequestEvent.R() == 0) {
            FriendRequestEvent.i(18);
        }
    }
}

