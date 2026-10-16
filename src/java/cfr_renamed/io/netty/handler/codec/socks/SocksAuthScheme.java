/*
 * Decompiled with CFR 0.152.
 */
package io.netty.handler.codec.socks;

public enum SocksAuthScheme {
    NO_AUTH(0),
    AUTH_GSSAPI(1),
    AUTH_PASSWORD(2),
    UNKNOWN(-1);

    private final byte var_byte_b;

    private SocksAuthScheme(byte b) {
        this.var_byte_b = b;
    }

    @Deprecated
    public static SocksAuthScheme fromByte(byte b) {
        return SocksAuthScheme.valueOf(b);
    }

    public static SocksAuthScheme valueOf(byte b) {
        for (SocksAuthScheme code : SocksAuthScheme.values()) {
            if (code.var_byte_b != b) continue;
            return code;
        }
        return UNKNOWN;
    }

    public byte byteValue() {
        return this.var_byte_b;
    }
}

