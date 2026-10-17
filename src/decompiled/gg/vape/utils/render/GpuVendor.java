/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.utils.render;

public enum GpuVendor {
    NVIDIA,
    AMD,
    INTEL,
    UNKNOWN;

    private static final GpuVendor[] VALUES;

    static {
        String[] legacyEnumNames = new String[]{"UNKNOWN", "NVIDIA", "AMD", "INTEL"};
        VALUES = new GpuVendor[]{NVIDIA, AMD, INTEL, UNKNOWN};
    }
}

