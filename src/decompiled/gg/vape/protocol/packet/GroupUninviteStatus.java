/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol.packet;

public enum GroupUninviteStatus {
    SUCCESS,
    FAILED;

    private static final GroupUninviteStatus[] L;

    static {
        String[] stringArray = new String[]{"SUCCESS", "FAILED"};
        L = new GroupUninviteStatus[]{SUCCESS, FAILED};
    }
}

