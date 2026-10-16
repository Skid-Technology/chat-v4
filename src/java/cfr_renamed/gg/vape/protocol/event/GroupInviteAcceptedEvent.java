/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.event;

import gg.vape.friend.PartyState;
import gg.vape.protocol.ZeusClient;
import gg.vape.protocol.event.OnlineEvent;

public class GroupInviteAcceptedEvent
extends OnlineEvent {
    private final PartyState var_gg_vape_friend_PartyState_c;

    public PartyState P() {
        return this.var_gg_vape_friend_PartyState_c;
    }

    public GroupInviteAcceptedEvent(ZeusClient oZ, PartyState wF) {
        super(oZ);
        this.var_gg_vape_friend_PartyState_c = wF;
    }
}

