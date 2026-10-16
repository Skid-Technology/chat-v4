/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.notification;

import gg.vape.value.BooleanValue;

public class FriendNotificationSettings {
    final public BooleanValue friendOnline;
    final public BooleanValue partyInviteAccepted;
    final public BooleanValue partyInvites;
    final public BooleanValue chats;
    final public BooleanValue friendRequests;
    final public BooleanValue general = BooleanValue.create(null, "Too many pings", true);

    public FriendNotificationSettings() {
        this.friendRequests = BooleanValue.create(null, "Friend requests", true);
        this.chats = BooleanValue.create(null, "Chats", true);
        this.friendOnline = BooleanValue.create(null, "Friend online", true);
        this.partyInvites = BooleanValue.create(null, "Party invites", true);
        this.partyInviteAccepted = BooleanValue.create(null, "Party invite accepted", true);
    }
}

