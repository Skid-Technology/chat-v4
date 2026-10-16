/*
 * Decompiled with CFR 0.152.
 */
package io.netty.handler.codec.http.websocketx;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.ReferenceCounted;

public class PongWebSocketFrame
extends WebSocketFrame {
    public PongWebSocketFrame() {
        super(Unpooled.buffer(0));
    }

    public PongWebSocketFrame(ByteBuf binaryData) {
        super(binaryData);
    }

    public PongWebSocketFrame(boolean finalFragment, int rsv, ByteBuf binaryData) {
        super(finalFragment, rsv, binaryData);
    }

    @Override
    public PongWebSocketFrame copy() {
        return new PongWebSocketFrame(this.isFinalFragment(), this.rsv(), this.content().copy());
    }

    @Override
    public PongWebSocketFrame duplicate() {
        return new PongWebSocketFrame(this.isFinalFragment(), this.rsv(), this.content().duplicate());
    }

    @Override
    public PongWebSocketFrame retain() {
        super.retain();
        return this;
    }

    @Override
    public PongWebSocketFrame retain(int increment) {
        super.retain(increment);
        return this;
    }

    @Override
    public WebSocketFrame retain(int x0) {
        return this.retain(x0);
    }

    @Override
    public WebSocketFrame retain() {
        return this.retain();
    }

    @Override
    public WebSocketFrame duplicate() {
        return this.duplicate();
    }

    @Override
    public WebSocketFrame copy() {
        return this.copy();
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

