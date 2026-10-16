/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.event;

import gg.vape.protocol.ZeusClient;
import gg.vape.protocol.event.OnlineEvent;
import gg.vape.protocol.packet.GroupOption;

public class GroupOptionUpdatedEvent
extends OnlineEvent {
    private static String[] var_java_lang_String_arr_y;
    private final GroupOption var_gg_vape_protocol_packet_GroupOption_c;
    private final Object var_java_lang_Object_h;

    public static String[] r() {
        return var_java_lang_String_arr_y;
    }

    public Object U() {
        return this.var_java_lang_Object_h;
    }

    public static void s(String[] stringArray) {
        var_java_lang_String_arr_y = stringArray;
    }

    public GroupOptionUpdatedEvent(ZeusClient oZ, GroupOption gk_02, Object object) {
        super(oZ);
        this.var_gg_vape_protocol_packet_GroupOption_c = gk_02;
        this.var_java_lang_Object_h = object;
    }

    public GroupOption j() {
        return this.var_gg_vape_protocol_packet_GroupOption_c;
    }

    static {
        if (GroupOptionUpdatedEvent.r() == null) {
            GroupOptionUpdatedEvent.s(new String[4]);
        }
    }
}

