/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.utils.network;

import gg.vape.wrapper.impl.Packet;
import java.util.ArrayList;
import java.util.List;

public class PacketDispatchMarkerRegistry {
    private static List<Object> var_java_util_List_java_lang_Object__s;
    private static List<Object> var_java_util_List_java_lang_Object__y;

    public static void q(Packet packet) {
        var_java_util_List_java_lang_Object__s.add(packet.getObject());
    }

    public static void I(Packet packet) {
        var_java_util_List_java_lang_Object__s.remove(packet.getObject());
    }

    public static void p(Packet packet) {
        var_java_util_List_java_lang_Object__y.remove(packet.getObject());
    }

    public static boolean J(Packet packet) {
        return var_java_util_List_java_lang_Object__y.contains(packet.getObject());
    }

    public static boolean S(Packet packet) {
        return var_java_util_List_java_lang_Object__s.contains(packet.getObject());
    }

    public static void u(Packet packet) {
        var_java_util_List_java_lang_Object__y.add(packet.getObject());
    }

    static {
        var_java_util_List_java_lang_Object__y = new ArrayList<Object>();
        var_java_util_List_java_lang_Object__s = new ArrayList<Object>();
    }
}

