/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.event;

import gg.vape.friend.UserModel;
import gg.vape.protocol.ZeusClient;
import gg.vape.protocol.event.OnlineEvent;

public class PartyLeaderChangedEvent
extends OnlineEvent {
    private final UserModel var_gg_vape_friend_UserModel_j;

    public PartyLeaderChangedEvent(ZeusClient oZ, UserModel oj_12) {
        super(oZ);
        this.var_gg_vape_friend_UserModel_j = oj_12;
    }

    public UserModel z() {
        return this.var_gg_vape_friend_UserModel_j;
    }
}

