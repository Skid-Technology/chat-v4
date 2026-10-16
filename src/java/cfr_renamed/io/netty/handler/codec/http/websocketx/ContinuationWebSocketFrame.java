/*
 * Decompiled with CFR 0.152.
 */
package io.netty.handler.codec.http.websocketx;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCounted;

public class ContinuationWebSocketFrame
extends WebSocketFrame {
    public ContinuationWebSocketFrame() {
        this(Unpooled.buffer(0));
    }

    public ContinuationWebSocketFrame(ByteBuf binaryData) {
        super(binaryData);
    }

    public ContinuationWebSocketFrame(boolean finalFragment, int rsv, ByteBuf binaryData) {
        super(finalFragment, rsv, binaryData);
    }

    public ContinuationWebSocketFrame(boolean finalFragment, int rsv, String text) {
        this(finalFragment, rsv, ContinuationWebSocketFrame.fromText(text));
    }

    public String text() {
        return this.content().toString(CharsetUtil.UTF_8);
    }

    private static ByteBuf fromText(String text) {
        if (text == null || text.isEmpty()) {
            return Unpooled.EMPTY_BUFFER;
        }
        return Unpooled.copiedBuffer(text, CharsetUtil.UTF_8);
    }

    @Override
    public ContinuationWebSocketFrame copy() {
        return new ContinuationWebSocketFrame(this.isFinalFragment(), this.rsv(), this.content().copy());
    }

    @Override
    public ContinuationWebSocketFrame duplicate() {
        return new ContinuationWebSocketFrame(this.isFinalFragment(), this.rsv(), this.content().duplicate());
    }

    @Override
    public ContinuationWebSocketFrame retain() {
        super.retain();
        return this;
    }

    @Override
    public ContinuationWebSocketFrame retain(int increment) {
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

