/*
 * Decompiled with CFR 0.152.
 */
package io.netty.handler.codec.socks;

public enum SocksAddressType {
    IPv4(1),
    DOMAIN(3),
    IPv6(4),
    UNKNOWN(-1);

    private final byte var_byte_b;

    private SocksAddressType(byte b) {
        this.var_byte_b = b;
    }

    @Deprecated
    public static SocksAddressType fromByte(byte b) {
        return SocksAddressType.valueOf(b);
    }

    public static SocksAddressType valueOf(byte b) {
        for (SocksAddressType code : SocksAddressType.values()) {
            if (code.var_byte_b != b) continue;
            return code;
        }
        return UNKNOWN;
    }

    public byte byteValue() {
        return this.var_byte_b;
    }
}

