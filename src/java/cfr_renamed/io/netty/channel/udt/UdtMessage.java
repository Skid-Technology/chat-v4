/*
 * Decompiled with CFR 0.152.
 */
package io.netty.channel.udt;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.DefaultByteBufHolder;
import io.netty.util.ReferenceCounted;

public final class UdtMessage
extends DefaultByteBufHolder {
    public UdtMessage(ByteBuf data) {
        super(data);
    }

    @Override
    public UdtMessage copy() {
        return new UdtMessage(this.content().copy());
    }

    @Override
    public UdtMessage duplicate() {
        return new UdtMessage(this.content().duplicate());
    }

    @Override
    public UdtMessage retain() {
        super.retain();
        return this;
    }

    @Override
    public UdtMessage retain(int increment) {
        super.retain(increment);
        return this;
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
    public ReferenceCounted retain(int x0) {
        return this.retain(x0);
    }

    @Override
    public ReferenceCounted retain() {
        return this.retain();
    }
}

