/*
 * Decompiled with CFR 0.152.
 */
package io.netty.channel.socket.oio;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelOption;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.socket.DefaultServerSocketChannelConfig;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.ServerSocketChannelConfig;
import io.netty.channel.socket.oio.OioServerSocketChannel;
import io.netty.channel.socket.oio.OioServerSocketChannelConfig;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Map;

public class DefaultOioServerSocketChannelConfig
extends DefaultServerSocketChannelConfig
implements OioServerSocketChannelConfig {
    @Deprecated
    public DefaultOioServerSocketChannelConfig(ServerSocketChannel channel, ServerSocket javaSocket) {
        super(channel, javaSocket);
    }

    DefaultOioServerSocketChannelConfig(OioServerSocketChannel channel, ServerSocket javaSocket) {
        super(channel, javaSocket);
    }

    @Override
    public Map<ChannelOption<?>, Object> getOptions() {
        return this.getOptions(super.getOptions(), ChannelOption.SO_TIMEOUT);
    }

    @Override
    public <T> T getOption(ChannelOption<T> option) {
        if (option == ChannelOption.SO_TIMEOUT) {
            return (T)Integer.valueOf(this.getSoTimeout());
        }
        return super.getOption(option);
    }

    @Override
    public <T> boolean setOption(ChannelOption<T> option, T value) {
        this.validate(option, value);
        if (option != ChannelOption.SO_TIMEOUT) {
            return super.setOption(option, value);
        }
        this.setSoTimeout((Integer)value);
        return true;
    }

    @Override
    public OioServerSocketChannelConfig setSoTimeout(int timeout) {
        try {
            this.javaSocket.setSoTimeout(timeout);
        }
        catch (IOException e) {
            throw new ChannelException(e);
        }
        return this;
    }

    @Override
    public int getSoTimeout() {
        try {
            return this.javaSocket.getSoTimeout();
        }
        catch (IOException e) {
            throw new ChannelException(e);
        }
    }

    @Override
    public OioServerSocketChannelConfig setBacklog(int backlog) {
        super.setBacklog(backlog);
        return this;
    }

    @Override
    public OioServerSocketChannelConfig setReuseAddress(boolean reuseAddress) {
        super.setReuseAddress(reuseAddress);
        return this;
    }

    @Override
    public OioServerSocketChannelConfig setReceiveBufferSize(int receiveBufferSize) {
        super.setReceiveBufferSize(receiveBufferSize);
        return this;
    }

    @Override
    public OioServerSocketChannelConfig setPerformancePreferences(int connectionTime, int latency, int bandwidth) {
        super.setPerformancePreferences(connectionTime, latency, bandwidth);
        return this;
    }

    @Override
    public OioServerSocketChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
        super.setConnectTimeoutMillis(connectTimeoutMillis);
        return this;
    }

    @Override
    public OioServerSocketChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
        super.setMaxMessagesPerRead(maxMessagesPerRead);
        return this;
    }

    @Override
    public OioServerSocketChannelConfig setWriteSpinCount(int writeSpinCount) {
        super.setWriteSpinCount(writeSpinCount);
        return this;
    }

    @Override
    public OioServerSocketChannelConfig setAllocator(ByteBufAllocator allocator) {
        super.setAllocator(allocator);
        return this;
    }

    @Override
    public OioServerSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
        super.setRecvByteBufAllocator(allocator);
        return this;
    }

    @Override
    public OioServerSocketChannelConfig setAutoRead(boolean autoRead) {
        super.setAutoRead(autoRead);
        return this;
    }

    @Override
    protected void autoReadCleared() {
        if (this.channel instanceof OioServerSocketChannel) {
            ((OioServerSocketChannel)this.channel).setReadPending(false);
        }
    }

    @Override
    public OioServerSocketChannelConfig setAutoClose(boolean autoClose) {
        super.setAutoClose(autoClose);
        return this;
    }

    @Override
    public OioServerSocketChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
        super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
        return this;
    }

    @Override
    public OioServerSocketChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
        super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
        return this;
    }

    @Override
    public OioServerSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
        super.setMessageSizeEstimator(estimator);
        return this;
    }

    @Override
    public ServerSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator x0) {
        return this.setMessageSizeEstimator(x0);
    }

    @Override
    public ServerSocketChannelConfig setWriteBufferLowWaterMark(int x0) {
        return this.setWriteBufferLowWaterMark(x0);
    }

    @Override
    public ServerSocketChannelConfig setWriteBufferHighWaterMark(int x0) {
        return this.setWriteBufferHighWaterMark(x0);
    }

    @Override
    public ServerSocketChannelConfig setAutoRead(boolean x0) {
        return this.setAutoRead(x0);
    }

    @Override
    public ServerSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator x0) {
        return this.setRecvByteBufAllocator(x0);
    }

    @Override
    public ServerSocketChannelConfig setAllocator(ByteBufAllocator x0) {
        return this.setAllocator(x0);
    }

    @Override
    public ServerSocketChannelConfig setWriteSpinCount(int x0) {
        return this.setWriteSpinCount(x0);
    }

    @Override
    public ServerSocketChannelConfig setMaxMessagesPerRead(int x0) {
        return this.setMaxMessagesPerRead(x0);
    }

    @Override
    public ServerSocketChannelConfig setConnectTimeoutMillis(int x0) {
        return this.setConnectTimeoutMillis(x0);
    }

    @Override
    public ServerSocketChannelConfig setBacklog(int x0) {
        return this.setBacklog(x0);
    }

    @Override
    public ServerSocketChannelConfig setPerformancePreferences(int x0, int x1, int x2) {
        return this.setPerformancePreferences(x0, x1, x2);
    }

    @Override
    public ServerSocketChannelConfig setReceiveBufferSize(int x0) {
        return this.setReceiveBufferSize(x0);
    }

    @Override
    public ServerSocketChannelConfig setReuseAddress(boolean x0) {
        return this.setReuseAddress(x0);
    }

    @Override
    public ChannelConfig setMessageSizeEstimator(MessageSizeEstimator x0) {
        return this.setMessageSizeEstimator(x0);
    }

    @Override
    public ChannelConfig setWriteBufferLowWaterMark(int x0) {
        return this.setWriteBufferLowWaterMark(x0);
    }

    @Override
    public ChannelConfig setWriteBufferHighWaterMark(int x0) {
        return this.setWriteBufferHighWaterMark(x0);
    }

    @Override
    public ChannelConfig setAutoClose(boolean x0) {
        return this.setAutoClose(x0);
    }

    @Override
    public ChannelConfig setAutoRead(boolean x0) {
        return this.setAutoRead(x0);
    }

    @Override
    public ChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator x0) {
        return this.setRecvByteBufAllocator(x0);
    }

    @Override
    public ChannelConfig setAllocator(ByteBufAllocator x0) {
        return this.setAllocator(x0);
    }

    @Override
    public ChannelConfig setWriteSpinCount(int x0) {
        return this.setWriteSpinCount(x0);
    }

    @Override
    public ChannelConfig setMaxMessagesPerRead(int x0) {
        return this.setMaxMessagesPerRead(x0);
    }

    @Override
    public ChannelConfig setConnectTimeoutMillis(int x0) {
        return this.setConnectTimeoutMillis(x0);
    }
}

