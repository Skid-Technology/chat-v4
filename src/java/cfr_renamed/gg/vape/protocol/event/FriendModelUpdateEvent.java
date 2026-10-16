/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.event;

import gg.vape.friend.FriendModel;
import gg.vape.protocol.ZeusClient;
import gg.vape.protocol.event.OnlineEventPayloadBase;

public class FriendModelUpdateEvent
extends OnlineEventPayloadBase {
    private final FriendModel var_gg_vape_friend_FriendModel_P;

    public FriendModelUpdateEvent(ZeusClient zeusClient, FriendModel friendModel) {
        super(zeusClient, friendModel.getUser());
        this.var_gg_vape_friend_FriendModel_P = friendModel;
    }

    public FriendModel q() {
        return this.var_gg_vape_friend_FriendModel_P;
    }
}

