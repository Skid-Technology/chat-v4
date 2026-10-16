/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.event;

import gg.vape.friend.UserModel;
import gg.vape.protocol.ZeusClient;
import gg.vape.protocol.event.OnlineEventPayloadBase;

public class GroupChatMessageEvent
extends OnlineEventPayloadBase {
    private final String var_java_lang_String_d;
    private final UserModel var_gg_vape_friend_UserModel_h;

    public GroupChatMessageEvent(ZeusClient oZ, UserModel oj_12, UserModel oj_13, String string) {
        super(oZ, oj_12);
        this.var_gg_vape_friend_UserModel_h = oj_13;
        this.var_java_lang_String_d = string;
    }

    public String K() {
        return this.var_java_lang_String_d;
    }

    public UserModel V() {
        return this.var_gg_vape_friend_UserModel_h;
    }
}

