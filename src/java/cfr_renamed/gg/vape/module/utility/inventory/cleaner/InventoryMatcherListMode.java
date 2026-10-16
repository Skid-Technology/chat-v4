/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.module.utility.inventory.cleaner;

public enum InventoryMatcherListMode {
    WHITELIST,
    BLACKLIST;

    private final static InventoryMatcherListMode[] VALUES;

    static {
        String[] enumNames = new String[]{"WHITELIST", "BLACKLIST"};
        VALUES = new InventoryMatcherListMode[]{WHITELIST, BLACKLIST};
    }
}

