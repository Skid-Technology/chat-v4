/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.event;

import gg.vape.friend.OnlineFriend;
import gg.vape.protocol.ZeusClient;
import gg.vape.protocol.event.OnlineEvent;

public class PartyInviteRemovedEvent
extends OnlineEvent {
    private final OnlineFriend var_gg_vape_friend_OnlineFriend_H;
    private static boolean var_boolean_F;

    public static boolean R() {
        boolean bl = PartyInviteRemovedEvent.G();
        return false;
    }

    public PartyInviteRemovedEvent(ZeusClient zeusClient, OnlineFriend onlineFriend) {
        super(zeusClient);
        this.var_gg_vape_friend_OnlineFriend_H = onlineFriend;
    }

    public static boolean G() {
        return var_boolean_F;
    }

    public OnlineFriend D() {
        return this.var_gg_vape_friend_OnlineFriend_H;
    }

    public static void g(boolean bl) {
        var_boolean_F = bl;
    }

    static {
        if (!PartyInviteRemovedEvent.G()) {
            PartyInviteRemovedEvent.g(true);
        }
    }
}

