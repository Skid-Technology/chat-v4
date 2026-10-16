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
import io.netty.channel.socket.DefaultSocketChannelConfig;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.SocketChannelConfig;
import io.netty.channel.socket.oio.OioSocketChannel;
import io.netty.channel.socket.oio.OioSocketChannelConfig;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public class DefaultOioSocketChannelConfig
extends DefaultSocketChannelConfig
implements OioSocketChannelConfig {
    @Deprecated
    public DefaultOioSocketChannelConfig(SocketChannel channel, Socket javaSocket) {
        super(channel, javaSocket);
    }

    DefaultOioSocketChannelConfig(OioSocketChannel channel, Socket javaSocket) {
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
    public OioSocketChannelConfig setSoTimeout(int timeout) {
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
    public OioSocketChannelConfig setTcpNoDelay(boolean tcpNoDelay) {
        super.setTcpNoDelay(tcpNoDelay);
        return this;
    }

    @Override
    public OioSocketChannelConfig setSoLinger(int soLinger) {
        super.setSoLinger(soLinger);
        return this;
    }

    @Override
    public OioSocketChannelConfig setSendBufferSize(int sendBufferSize) {
        super.setSendBufferSize(sendBufferSize);
        return this;
    }

    @Override
    public OioSocketChannelConfig setReceiveBufferSize(int receiveBufferSize) {
        super.setReceiveBufferSize(receiveBufferSize);
        return this;
    }

    @Override
    public OioSocketChannelConfig setKeepAlive(boolean keepAlive) {
        super.setKeepAlive(keepAlive);
        return this;
    }

    @Override
    public OioSocketChannelConfig setTrafficClass(int trafficClass) {
        super.setTrafficClass(trafficClass);
        return this;
    }

    @Override
    public OioSocketChannelConfig setReuseAddress(boolean reuseAddress) {
        super.setReuseAddress(reuseAddress);
        return this;
    }

    @Override
    public OioSocketChannelConfig setPerformancePreferences(int connectionTime, int latency, int bandwidth) {
        super.setPerformancePreferences(connectionTime, latency, bandwidth);
        return this;
    }

    @Override
    public OioSocketChannelConfig setAllowHalfClosure(boolean allowHalfClosure) {
        super.setAllowHalfClosure(allowHalfClosure);
        return this;
    }

    @Override
    public OioSocketChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
        super.setConnectTimeoutMillis(connectTimeoutMillis);
        return this;
    }

    @Override
    public OioSocketChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
        super.setMaxMessagesPerRead(maxMessagesPerRead);
        return this;
    }

    @Override
    public OioSocketChannelConfig setWriteSpinCount(int writeSpinCount) {
        super.setWriteSpinCount(writeSpinCount);
        return this;
    }

    @Override
    public OioSocketChannelConfig setAllocator(ByteBufAllocator allocator) {
        super.setAllocator(allocator);
        return this;
    }

    @Override
    public OioSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
        super.setRecvByteBufAllocator(allocator);
        return this;
    }

    @Override
    public OioSocketChannelConfig setAutoRead(boolean autoRead) {
        super.setAutoRead(autoRead);
        return this;
    }

    @Override
    protected void autoReadCleared() {
        if (this.channel instanceof OioSocketChannel) {
            ((OioSocketChannel)this.channel).setReadPending(false);
        }
    }

    @Override
    public OioSocketChannelConfig setAutoClose(boolean autoClose) {
        super.setAutoClose(autoClose);
        return this;
    }

    @Override
    public OioSocketChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
        super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
        return this;
    }

    @Override
    public OioSocketChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
        super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
        return this;
    }

    @Override
    public OioSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
        super.setMessageSizeEstimator(estimator);
        return this;
    }

    @Override
    public SocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator x0) {
        return this.setMessageSizeEstimator(x0);
    }

    @Override
    public SocketChannelConfig setWriteBufferLowWaterMark(int x0) {
        return this.setWriteBufferLowWaterMark(x0);
    }

    @Override
    public SocketChannelConfig setWriteBufferHighWaterMark(int x0) {
        return this.setWriteBufferHighWaterMark(x0);
    }

    @Override
    public SocketChannelConfig setAutoClose(boolean x0) {
        return this.setAutoClose(x0);
    }

    @Override
    public SocketChannelConfig setAutoRead(boolean x0) {
        return this.setAutoRead(x0);
    }

    @Override
    public SocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator x0) {
        return this.setRecvByteBufAllocator(x0);
    }

    @Override
    public SocketChannelConfig setAllocator(ByteBufAllocator x0) {
        return this.setAllocator(x0);
    }

    @Override
    public SocketChannelConfig setWriteSpinCount(int x0) {
        return this.setWriteSpinCount(x0);
    }

    @Override
    public SocketChannelConfig setMaxMessagesPerRead(int x0) {
        return this.setMaxMessagesPerRead(x0);
    }

    @Override
    public SocketChannelConfig setConnectTimeoutMillis(int x0) {
        return this.setConnectTimeoutMillis(x0);
    }

    @Override
    public SocketChannelConfig setAllowHalfClosure(boolean x0) {
        return this.setAllowHalfClosure(x0);
    }

    @Override
    public SocketChannelConfig setTrafficClass(int x0) {
        return this.setTrafficClass(x0);
    }

    @Override
    public SocketChannelConfig setTcpNoDelay(boolean x0) {
        return this.setTcpNoDelay(x0);
    }

    @Override
    public SocketChannelConfig setSoLinger(int x0) {
        return this.setSoLinger(x0);
    }

    @Override
    public SocketChannelConfig setSendBufferSize(int x0) {
        return this.setSendBufferSize(x0);
    }

    @Override
    public SocketChannelConfig setReuseAddress(boolean x0) {
        return this.setReuseAddress(x0);
    }

    @Override
    public SocketChannelConfig setReceiveBufferSize(int x0) {
        return this.setReceiveBufferSize(x0);
    }

    @Override
    public SocketChannelConfig setPerformancePreferences(int x0, int x1, int x2) {
        return this.setPerformancePreferences(x0, x1, x2);
    }

    @Override
    public SocketChannelConfig setKeepAlive(boolean x0) {
        return this.setKeepAlive(x0);
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

