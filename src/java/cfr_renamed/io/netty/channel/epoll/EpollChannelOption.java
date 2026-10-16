/*
 * Decompiled with CFR 0.152.
 */
package io.netty.channel.epoll;

import io.netty.channel.ChannelOption;

public final class EpollChannelOption<T>
extends ChannelOption<T> {
    final public static ChannelOption<Boolean> TCP_CORK = EpollChannelOption.valueOf("TCP_CORK");
    final public static ChannelOption<Integer> TCP_KEEPIDLE = EpollChannelOption.valueOf("TCP_KEEPIDLE");
    final public static ChannelOption<Integer> TCP_KEEPINTVL = EpollChannelOption.valueOf("TCP_KEEPINTVL");
    final public static ChannelOption<Integer> TCP_KEEPCNT = EpollChannelOption.valueOf("TCP_KEEPCNT");
    final public static ChannelOption<Boolean> SO_REUSEPORT = EpollChannelOption.valueOf("SO_REUSEPORT");

    private EpollChannelOption(String name) {
        super(name);
    }
}

