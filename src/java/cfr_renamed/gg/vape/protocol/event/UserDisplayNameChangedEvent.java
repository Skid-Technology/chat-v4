/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.event;

import gg.vape.protocol.ZeusClient;
import gg.vape.protocol.event.OnlineEvent;

public class UserDisplayNameChangedEvent
extends OnlineEvent {
    private final long var_long_s;
    private final String var_java_lang_String_G;

    public UserDisplayNameChangedEvent(ZeusClient zeusClient, long l, String string) {
        super(zeusClient);
        this.var_long_s = l;
        this.var_java_lang_String_G = string;
    }

    public long R() {
        return this.var_long_s;
    }

    public String v() {
        return this.var_java_lang_String_G;
    }
}

