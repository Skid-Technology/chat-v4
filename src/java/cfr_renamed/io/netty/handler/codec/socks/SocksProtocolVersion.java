/*
 * Decompiled with CFR 0.152.
 */
package io.netty.handler.codec.socks;

public enum SocksProtocolVersion {
    SOCKS4a(4),
    SOCKS5(5),
    UNKNOWN(-1);

    private final byte var_byte_b;

    private SocksProtocolVersion(byte b) {
        this.var_byte_b = b;
    }

    @Deprecated
    public static SocksProtocolVersion fromByte(byte b) {
        return SocksProtocolVersion.valueOf(b);
    }

    public static SocksProtocolVersion valueOf(byte b) {
        for (SocksProtocolVersion code : SocksProtocolVersion.values()) {
            if (code.var_byte_b != b) continue;
            return code;
        }
        return UNKNOWN;
    }

    public byte byteValue() {
        return this.var_byte_b;
    }
}

