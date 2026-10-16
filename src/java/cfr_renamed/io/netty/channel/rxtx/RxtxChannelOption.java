/*
 * Decompiled with CFR 0.152.
 */
package io.netty.channel.rxtx;

import io.netty.channel.ChannelOption;
import io.netty.channel.rxtx.RxtxChannelConfig;

public final class RxtxChannelOption<T>
extends ChannelOption<T> {
    final public static RxtxChannelOption<Integer> BAUD_RATE = new RxtxChannelOption("BAUD_RATE");
    final public static RxtxChannelOption<Boolean> DTR = new RxtxChannelOption("DTR");
    final public static RxtxChannelOption<Boolean> RTS = new RxtxChannelOption("RTS");
    final public static RxtxChannelOption<RxtxChannelConfig.Stopbits> STOP_BITS = new RxtxChannelOption("STOP_BITS");
    final public static RxtxChannelOption<RxtxChannelConfig.Databits> DATA_BITS = new RxtxChannelOption("DATA_BITS");
    final public static RxtxChannelOption<RxtxChannelConfig.Paritybit> PARITY_BIT = new RxtxChannelOption("PARITY_BIT");
    final public static RxtxChannelOption<Integer> WAIT_TIME = new RxtxChannelOption("WAIT_TIME");
    final public static RxtxChannelOption<Integer> READ_TIMEOUT = new RxtxChannelOption("READ_TIMEOUT");

    private RxtxChannelOption(String name) {
        super(name);
    }
}

