/*
 * Decompiled with CFR 0.152.
 */
package io.netty.channel.udt;

import io.netty.channel.ChannelOption;

public final class UdtChannelOption<T>
extends ChannelOption<T> {
    final public static UdtChannelOption<Integer> PROTOCOL_RECEIVE_BUFFER_SIZE = new UdtChannelOption("PROTOCOL_RECEIVE_BUFFER_SIZE");
    final public static UdtChannelOption<Integer> PROTOCOL_SEND_BUFFER_SIZE = new UdtChannelOption("PROTOCOL_SEND_BUFFER_SIZE");
    final public static UdtChannelOption<Integer> SYSTEM_RECEIVE_BUFFER_SIZE = new UdtChannelOption("SYSTEM_RECEIVE_BUFFER_SIZE");
    final public static UdtChannelOption<Integer> SYSTEM_SEND_BUFFER_SIZE = new UdtChannelOption("SYSTEM_SEND_BUFFER_SIZE");

    private UdtChannelOption(String name) {
        super(name);
    }
}

