/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.config;

public enum PublicProfileReviewDisplayType {
    OTHER,
    SELF,
    REPLY;

    private final static PublicProfileReviewDisplayType[] ENUM_VALUES;

    static {
        String[] serializedNames = new String[]{"REPLY", "OTHER", "SELF"};
        ENUM_VALUES = new PublicProfileReviewDisplayType[]{OTHER, SELF, REPLY};
    }
}

