/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.barchart.udt.nio.ChannelUDT
 */
package io.netty.channel.udt;

import com.barchart.udt.nio.ChannelUDT;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelOption;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.udt.DefaultUdtChannelConfig;
import io.netty.channel.udt.UdtChannel;
import io.netty.channel.udt.UdtChannelConfig;
import io.netty.channel.udt.UdtServerChannelConfig;
import java.io.IOException;
import java.util.Map;

public class DefaultUdtServerChannelConfig
extends DefaultUdtChannelConfig
implements UdtServerChannelConfig {
    private volatile int backlog = 64;

    public DefaultUdtServerChannelConfig(UdtChannel channel, ChannelUDT channelUDT, boolean apply) throws IOException {
        super(channel, channelUDT, apply);
        if (apply) {
            this.apply(channelUDT);
        }
    }

    @Override
    protected void apply(ChannelUDT channelUDT) throws IOException {
    }

    @Override
    public int getBacklog() {
        return this.backlog;
    }

    @Override
    public <T> T getOption(ChannelOption<T> option) {
        if (option == ChannelOption.SO_BACKLOG) {
            return (T)Integer.valueOf(this.getBacklog());
        }
        return super.getOption(option);
    }

    @Override
    public Map<ChannelOption<?>, Object> getOptions() {
        return this.getOptions(super.getOptions(), ChannelOption.SO_BACKLOG);
    }

    @Override
    public UdtServerChannelConfig setBacklog(int backlog) {
        this.backlog = backlog;
        return this;
    }

    @Override
    public <T> boolean setOption(ChannelOption<T> option, T value) {
        this.validate(option, value);
        if (option != ChannelOption.SO_BACKLOG) {
            return super.setOption(option, value);
        }
        this.setBacklog((Integer)value);
        return true;
    }

    @Override
    public UdtServerChannelConfig setProtocolReceiveBufferSize(int protocolReceiveBuferSize) {
        super.setProtocolReceiveBufferSize(protocolReceiveBuferSize);
        return this;
    }

    @Override
    public UdtServerChannelConfig setProtocolSendBufferSize(int protocolSendBuferSize) {
        super.setProtocolSendBufferSize(protocolSendBuferSize);
        return this;
    }

    @Override
    public UdtServerChannelConfig setReceiveBufferSize(int receiveBufferSize) {
        super.setReceiveBufferSize(receiveBufferSize);
        return this;
    }

    @Override
    public UdtServerChannelConfig setReuseAddress(boolean reuseAddress) {
        super.setReuseAddress(reuseAddress);
        return this;
    }

    @Override
    public UdtServerChannelConfig setSendBufferSize(int sendBufferSize) {
        super.setSendBufferSize(sendBufferSize);
        return this;
    }

    @Override
    public UdtServerChannelConfig setSoLinger(int soLinger) {
        super.setSoLinger(soLinger);
        return this;
    }

    @Override
    public UdtServerChannelConfig setSystemReceiveBufferSize(int systemSendBuferSize) {
        super.setSystemReceiveBufferSize(systemSendBuferSize);
        return this;
    }

    @Override
    public UdtServerChannelConfig setSystemSendBufferSize(int systemReceiveBufferSize) {
        super.setSystemSendBufferSize(systemReceiveBufferSize);
        return this;
    }

    @Override
    public UdtServerChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
        super.setConnectTimeoutMillis(connectTimeoutMillis);
        return this;
    }

    @Override
    public UdtServerChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
        super.setMaxMessagesPerRead(maxMessagesPerRead);
        return this;
    }

    @Override
    public UdtServerChannelConfig setWriteSpinCount(int writeSpinCount) {
        super.setWriteSpinCount(writeSpinCount);
        return this;
    }

    @Override
    public UdtServerChannelConfig setAllocator(ByteBufAllocator allocator) {
        super.setAllocator(allocator);
        return this;
    }

    @Override
    public UdtServerChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
        super.setRecvByteBufAllocator(allocator);
        return this;
    }

    @Override
    public UdtServerChannelConfig setAutoRead(boolean autoRead) {
        super.setAutoRead(autoRead);
        return this;
    }

    @Override
    public UdtServerChannelConfig setAutoClose(boolean autoClose) {
        super.setAutoClose(autoClose);
        return this;
    }

    @Override
    public UdtServerChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
        super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
        return this;
    }

    @Override
    public UdtServerChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
        super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
        return this;
    }

    @Override
    public UdtServerChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
        super.setMessageSizeEstimator(estimator);
        return this;
    }

    @Override
    public UdtChannelConfig setMessageSizeEstimator(MessageSizeEstimator x0) {
        return this.setMessageSizeEstimator(x0);
    }

    @Override
    public UdtChannelConfig setWriteBufferHighWaterMark(int x0) {
        return this.setWriteBufferHighWaterMark(x0);
    }

    @Override
    public UdtChannelConfig setWriteBufferLowWaterMark(int x0) {
        return this.setWriteBufferLowWaterMark(x0);
    }

    @Override
    public UdtChannelConfig setAutoClose(boolean x0) {
        return this.setAutoClose(x0);
    }

    @Override
    public UdtChannelConfig setAutoRead(boolean x0) {
        return this.setAutoRead(x0);
    }

    @Override
    public UdtChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator x0) {
        return this.setRecvByteBufAllocator(x0);
    }

    @Override
    public UdtChannelConfig setAllocator(ByteBufAllocator x0) {
        return this.setAllocator(x0);
    }

    @Override
    public UdtChannelConfig setWriteSpinCount(int x0) {
        return this.setWriteSpinCount(x0);
    }

    @Override
    public UdtChannelConfig setMaxMessagesPerRead(int x0) {
        return this.setMaxMessagesPerRead(x0);
    }

    @Override
    public UdtChannelConfig setConnectTimeoutMillis(int x0) {
        return this.setConnectTimeoutMillis(x0);
    }

    @Override
    public UdtChannelConfig setSystemReceiveBufferSize(int x0) {
        return this.setSystemReceiveBufferSize(x0);
    }

    @Override
    public UdtChannelConfig setProtocolSendBufferSize(int x0) {
        return this.setProtocolSendBufferSize(x0);
    }

    @Override
    public UdtChannelConfig setSystemSendBufferSize(int x0) {
        return this.setSystemSendBufferSize(x0);
    }

    @Override
    public UdtChannelConfig setSoLinger(int x0) {
        return this.setSoLinger(x0);
    }

    @Override
    public UdtChannelConfig setSendBufferSize(int x0) {
        return this.setSendBufferSize(x0);
    }

    @Override
    public UdtChannelConfig setReuseAddress(boolean x0) {
        return this.setReuseAddress(x0);
    }

    @Override
    public UdtChannelConfig setReceiveBufferSize(int x0) {
        return this.setReceiveBufferSize(x0);
    }

    @Override
    public UdtChannelConfig setProtocolReceiveBufferSize(int x0) {
        return this.setProtocolReceiveBufferSize(x0);
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

