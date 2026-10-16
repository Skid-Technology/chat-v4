/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.event;

import gg.vape.friend.OnlineFriend;
import gg.vape.protocol.ZeusClient;
import gg.vape.protocol.event.OnlineEvent;

public class GroupInviteSentEvent
extends OnlineEvent {
    private final OnlineFriend var_gg_vape_friend_OnlineFriend_I;

    public GroupInviteSentEvent(ZeusClient oZ, OnlineFriend yS) {
        super(oZ);
        this.var_gg_vape_friend_OnlineFriend_I = yS;
    }

    public OnlineFriend n() {
        return this.var_gg_vape_friend_OnlineFriend_I;
    }
}

