/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.notification;

public enum NotificationGroup {
    NONE,
    FRIENDS;

    private final static NotificationGroup[] cachedValues;

    static {
        String[] groupNames = new String[]{"FRIENDS", "NONE"};
        cachedValues = new NotificationGroup[]{NONE, FRIENDS};
    }
}

