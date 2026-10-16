/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.event;

import gg.vape.friend.UserModel;
import gg.vape.protocol.ZeusClient;
import gg.vape.protocol.event.OnlineEvent;

public class PartyInviteReceivedEvent
extends OnlineEvent {
    private final UserModel var_gg_vape_friend_UserModel_R;

    public UserModel R() {
        return this.var_gg_vape_friend_UserModel_R;
    }

    public PartyInviteReceivedEvent(ZeusClient zeusClient, UserModel userModel) {
        super(zeusClient);
        this.var_gg_vape_friend_UserModel_R = userModel;
    }
}

