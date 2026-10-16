/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol;

public enum ZeusPacketDirection {
    CLIENT,
    SERVER;

    private final static ZeusPacketDirection[] var_gg_vape_protocol_ZeusPacketDirection_arr_P;
    private static String[] var_java_lang_String_arr_Q;

    public static void U(String[] stringArray) {
        var_java_lang_String_arr_Q = stringArray;
    }

    public static String[] F() {
        return var_java_lang_String_arr_Q;
    }

    static {
        if (ZeusPacketDirection.F() != null) {
            ZeusPacketDirection.U(new String[5]);
        }
        String[] stringArray = new String[]{"CLIENT", "SERVER"};
        var_gg_vape_protocol_ZeusPacketDirection_arr_P = new ZeusPacketDirection[]{CLIENT, SERVER};
    }
}

