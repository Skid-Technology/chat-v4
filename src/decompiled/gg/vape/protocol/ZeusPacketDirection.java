/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol;

public enum ZeusPacketDirection {
    CLIENT,
    SERVER;

    private static final ZeusPacketDirection[] P;
    private static String[] Q;

    public static void U(String[] stringArray) {
        Q = stringArray;
    }

    public static String[] F() {
        return Q;
    }

    static {
        if (ZeusPacketDirection.F() != null) {
            ZeusPacketDirection.U(new String[5]);
        }
        String[] stringArray = new String[]{"CLIENT", "SERVER"};
        P = new ZeusPacketDirection[]{CLIENT, SERVER};
    }
}

