/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.event;

import gg.vape.protocol.ZeusClient;
import gg.vape.protocol.event.OnlineEvent;

public class FriendRequestRemovedEvent
extends OnlineEvent {
    private final long var_long_g;

    public long v() {
        return this.var_long_g;
    }

    public FriendRequestRemovedEvent(ZeusClient oZ, long l) {
        super(oZ);
        this.var_long_g = l;
    }
}

