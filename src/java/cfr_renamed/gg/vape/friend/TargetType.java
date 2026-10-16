/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.friend;

public enum TargetType {
    FRIEND,
    ENEMY;

    private final static TargetType[] VALUES_COPY;

    static {
        String[] declaredNames = new String[]{"ENEMY", "FRIEND"};
        VALUES_COPY = new TargetType[]{FRIEND, ENEMY};
    }
}

