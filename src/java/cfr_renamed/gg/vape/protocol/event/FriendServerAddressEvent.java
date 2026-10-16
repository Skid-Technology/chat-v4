/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package gg.vape.protocol.event;

import gg.vape.protocol.ZeusClient;
import gg.vape.protocol.event.OnlineEvent;
import org.jetbrains.annotations.Nullable;

public class FriendServerAddressEvent
extends OnlineEvent {
    private final long var_long_f;
    @Nullable
    private final String var_java_lang_String_g;

    public long a() {
        return this.var_long_f;
    }

    public FriendServerAddressEvent(ZeusClient zeusClient, long l, @Nullable String string) {
        super(zeusClient);
        this.var_long_f = l;
        this.var_java_lang_String_g = string;
    }

    @Nullable
    public String Z() {
        return this.var_java_lang_String_g;
    }
}

