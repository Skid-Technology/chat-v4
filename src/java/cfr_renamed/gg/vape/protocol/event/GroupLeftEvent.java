/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.event;

import gg.vape.protocol.ZeusClient;
import gg.vape.protocol.event.OnlineEvent;

public class GroupLeftEvent
extends OnlineEvent {
    private static String var_java_lang_String_l;

    public static String java_lang_String_u() {
        return var_java_lang_String_l;
    }

    public GroupLeftEvent(ZeusClient zeusClient) {
        super(zeusClient);
    }

    public static void t(String string) {
        var_java_lang_String_l = string;
    }

    static {
        if (GroupLeftEvent.java_lang_String_u() != null) {
            GroupLeftEvent.t("mqBJS");
        }
    }
}

