/*
 * Decompiled with CFR 0.152.
 */
package io.netty.handler.codec.socks;

public enum SocksSubnegotiationVersion {
    AUTH_PASSWORD(1),
    UNKNOWN(-1);

    private final byte var_byte_b;

    private SocksSubnegotiationVersion(byte b) {
        this.var_byte_b = b;
    }

    @Deprecated
    public static SocksSubnegotiationVersion fromByte(byte b) {
        return SocksSubnegotiationVersion.valueOf(b);
    }

    public static SocksSubnegotiationVersion valueOf(byte b) {
        for (SocksSubnegotiationVersion code : SocksSubnegotiationVersion.values()) {
            if (code.var_byte_b != b) continue;
            return code;
        }
        return UNKNOWN;
    }

    public byte byteValue() {
        return this.var_byte_b;
    }
}

