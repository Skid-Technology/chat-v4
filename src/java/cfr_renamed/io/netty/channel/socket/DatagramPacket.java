/*
 * Decompiled with CFR 0.152.
 */
package io.netty.channel.socket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.channel.AddressedEnvelope;
import io.netty.channel.DefaultAddressedEnvelope;
import io.netty.util.ReferenceCounted;
import java.net.InetSocketAddress;

public final class DatagramPacket
extends DefaultAddressedEnvelope<ByteBuf, InetSocketAddress>
implements ByteBufHolder {
    public DatagramPacket(ByteBuf data, InetSocketAddress recipient) {
        super(data, recipient);
    }

    public DatagramPacket(ByteBuf data, InetSocketAddress recipient, InetSocketAddress sender) {
        super(data, recipient, sender);
    }

    @Override
    public DatagramPacket copy() {
        return new DatagramPacket(((ByteBuf)this.content()).copy(), (InetSocketAddress)this.recipient(), (InetSocketAddress)this.sender());
    }

    @Override
    public DatagramPacket duplicate() {
        return new DatagramPacket(((ByteBuf)this.content()).duplicate(), (InetSocketAddress)this.recipient(), (InetSocketAddress)this.sender());
    }

    @Override
    public DatagramPacket retain() {
        super.retain();
        return this;
    }

    @Override
    public DatagramPacket retain(int increment) {
        super.retain(increment);
        return this;
    }

    @Override
    public AddressedEnvelope retain(int x0) {
        return this.retain(x0);
    }

    @Override
    public AddressedEnvelope retain() {
        return this.retain();
    }

    @Override
    public ReferenceCounted retain(int x0) {
        return this.retain(x0);
    }

    @Override
    public ReferenceCounted retain() {
        return this.retain();
    }

    @Override
    public ByteBufHolder retain(int x0) {
        return this.retain(x0);
    }

    @Override
    public ByteBufHolder retain() {
        return this.retain();
    }

    @Override
    public ByteBufHolder duplicate() {
        return this.duplicate();
    }

    @Override
    public ByteBufHolder copy() {
        return this.copy();
    }

    @Override
    public ByteBuf content() {
        return (ByteBuf)super.content();
    }
}

