/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.event;

import gg.vape.friend.FriendModel;
import gg.vape.protocol.ZeusClient;
import gg.vape.protocol.event.OnlineEvent;

public class FriendVisibilityUpdateEvent
extends OnlineEvent {
    private static int[] var_int_arr_s;
    private final long var_long_y;
    private final boolean var_boolean_h;

    public static int[] m() {
        return var_int_arr_s;
    }

    public long N() {
        return this.var_long_y;
    }

    public boolean q() {
        return this.var_boolean_h;
    }

    public FriendVisibilityUpdateEvent(ZeusClient zeusClient, long l, boolean bl) {
        super(zeusClient);
        this.var_long_y = l;
        this.var_boolean_h = bl;
    }

    public static void J(int[] nArray) {
        var_int_arr_s = nArray;
    }

    public FriendVisibilityUpdateEvent(ZeusClient zeusClient, FriendModel friendModel) {
        this(zeusClient, friendModel.getUserId(), friendModel.isVisible());
    }

    static {
        if (FriendVisibilityUpdateEvent.m() == null) {
            FriendVisibilityUpdateEvent.J(new int[4]);
        }
    }
}

