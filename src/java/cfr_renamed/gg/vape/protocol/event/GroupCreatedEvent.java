/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.event;

import gg.vape.friend.PartyState;
import gg.vape.protocol.ZeusClient;
import gg.vape.protocol.event.OnlineEvent;

public class GroupCreatedEvent
extends OnlineEvent {
    private final PartyState var_gg_vape_friend_PartyState_Y;

    public GroupCreatedEvent(ZeusClient oZ, PartyState wF) {
        super(oZ);
        this.var_gg_vape_friend_PartyState_Y = wF;
    }

    public PartyState V() {
        return this.var_gg_vape_friend_PartyState_Y;
    }
}

