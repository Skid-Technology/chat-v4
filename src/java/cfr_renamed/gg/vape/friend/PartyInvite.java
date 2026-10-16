/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.friend;

import gg.vape.friend.OnlineFriend;

public class PartyInvite {
    private final OnlineFriend inviter;

    public PartyInvite(OnlineFriend inviter) {
        this.inviter = inviter;
    }

    public OnlineFriend getInviter() {
        return this.inviter;
    }
}

