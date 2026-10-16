/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.event;

import gg.vape.friend.UserModel;
import gg.vape.protocol.ZeusClient;
import gg.vape.protocol.event.OnlineEvent;

public class FriendChatMessageEvent
extends OnlineEvent {
    private final String var_java_lang_String_t;
    private final UserModel var_gg_vape_friend_UserModel_q;

    public UserModel U() {
        return this.var_gg_vape_friend_UserModel_q;
    }

    public String g() {
        return this.var_java_lang_String_t;
    }

    public FriendChatMessageEvent(ZeusClient oZ, UserModel oj_12, String string) {
        super(oZ);
        this.var_gg_vape_friend_UserModel_q = oj_12;
        this.var_java_lang_String_t = string;
    }
}

