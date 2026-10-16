/*
 * Decompiled with CFR 0.152.
 */
package io.netty.handler.codec.socks;

public enum SocksAuthStatus {
    SUCCESS(0),
    FAILURE(-1);

    private final byte var_byte_b;

    private SocksAuthStatus(byte b) {
        this.var_byte_b = b;
    }

    @Deprecated
    public static SocksAuthStatus fromByte(byte b) {
        return SocksAuthStatus.valueOf(b);
    }

    public static SocksAuthStatus valueOf(byte b) {
        for (SocksAuthStatus code : SocksAuthStatus.values()) {
            if (code.var_byte_b != b) continue;
            return code;
        }
        return FAILURE;
    }

    public byte byteValue() {
        return this.var_byte_b;
    }
}

