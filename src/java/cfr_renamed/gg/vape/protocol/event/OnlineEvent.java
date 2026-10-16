/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.event;

import gg.vape.protocol.ZeusClient;
import gg.vape.protocol.event.OnlineEventDispatcher;

public abstract class OnlineEvent {
    private static String[] var_java_lang_String_arr_A;
    private final ZeusClient var_gg_vape_protocol_ZeusClient_W;

    public void u() {
        OnlineEventDispatcher.var_gg_vape_protocol_event_OnlineEventDispatcher_O.G(this);
    }

    public static void n(String[] stringArray) {
        var_java_lang_String_arr_A = stringArray;
    }

    public static String[] L() {
        return var_java_lang_String_arr_A;
    }

    public OnlineEvent(ZeusClient oZ) {
        this.var_gg_vape_protocol_ZeusClient_W = oZ;
    }

    public ZeusClient e() {
        return this.var_gg_vape_protocol_ZeusClient_W;
    }

    static {
        if (OnlineEvent.L() == null) {
            OnlineEvent.n(new String[2]);
        }
    }
}

