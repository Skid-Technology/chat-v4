/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.utils.render;

public enum GpuVendor {
    NVIDIA,
    AMD,
    INTEL,
    UNKNOWN;

    private final static GpuVendor[] VALUES;

    static {
        String[] legacyEnumNames = new String[]{"UNKNOWN", "NVIDIA", "AMD", "INTEL"};
        VALUES = new GpuVendor[]{NVIDIA, AMD, INTEL, UNKNOWN};
    }
}

