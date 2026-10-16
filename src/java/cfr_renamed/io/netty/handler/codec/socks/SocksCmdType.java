/*
 * Decompiled with CFR 0.152.
 */
package io.netty.handler.codec.socks;

public enum SocksCmdType {
    CONNECT(1),
    BIND(2),
    UDP(3),
    UNKNOWN(-1);

    private final byte var_byte_b;

    private SocksCmdType(byte b) {
        this.var_byte_b = b;
    }

    @Deprecated
    public static SocksCmdType fromByte(byte b) {
        return SocksCmdType.valueOf(b);
    }

    public static SocksCmdType valueOf(byte b) {
        for (SocksCmdType code : SocksCmdType.values()) {
            if (code.var_byte_b != b) continue;
            return code;
        }
        return UNKNOWN;
    }

    public byte byteValue() {
        return this.var_byte_b;
    }
}

