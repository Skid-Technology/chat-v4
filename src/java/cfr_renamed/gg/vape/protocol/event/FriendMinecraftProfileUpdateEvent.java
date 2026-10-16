/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.event;

import gg.vape.friend.FriendModel;
import gg.vape.protocol.ZeusClient;
import gg.vape.protocol.event.OnlineEvent;
import java.util.UUID;

public class FriendMinecraftProfileUpdateEvent
extends OnlineEvent {
    private final UUID var_java_util_UUID_F;
    private final long var_long_g;
    private final String var_java_lang_String_h;

    public long long_b() {
        return this.var_long_g;
    }

    public String java_lang_String_b() {
        return this.var_java_lang_String_h;
    }

    public UUID h() {
        return this.var_java_util_UUID_F;
    }

    public FriendMinecraftProfileUpdateEvent(ZeusClient zeusClient, FriendModel friendModel) {
        this(zeusClient, friendModel.getUserId(), friendModel.getMinecraftUuid(), friendModel.getMinecraftUsername());
    }

    public FriendMinecraftProfileUpdateEvent(ZeusClient zeusClient, long l, UUID uUID, String string) {
        super(zeusClient);
        this.var_long_g = l;
        this.var_java_util_UUID_F = uUID;
        this.var_java_lang_String_h = string;
    }

    public long b() {
        return this.long_b();
    }

    public String b$src$Ljava_lang_String_$171yzxt() {
        return this.java_lang_String_b();
    }
}

