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

public class TextWebSocketFrame
extends WebSocketFrame {
    public TextWebSocketFrame() {
        super(Unpooled.buffer(0));
    }

    public TextWebSocketFrame(String text) {
        super(TextWebSocketFrame.fromText(text));
    }

    public TextWebSocketFrame(ByteBuf binaryData) {
        super(binaryData);
    }

    public TextWebSocketFrame(boolean finalFragment, int rsv, String text) {
        super(finalFragment, rsv, TextWebSocketFrame.fromText(text));
    }

    private static ByteBuf fromText(String text) {
        if (text == null || text.isEmpty()) {
            return Unpooled.EMPTY_BUFFER;
        }
        return Unpooled.copiedBuffer(text, CharsetUtil.UTF_8);
    }

    public TextWebSocketFrame(boolean finalFragment, int rsv, ByteBuf binaryData) {
        super(finalFragment, rsv, binaryData);
    }

    public String text() {
        return this.content().toString(CharsetUtil.UTF_8);
    }

    @Override
    public TextWebSocketFrame copy() {
        return new TextWebSocketFrame(this.isFinalFragment(), this.rsv(), this.content().copy());
    }

    @Override
    public TextWebSocketFrame duplicate() {
        return new TextWebSocketFrame(this.isFinalFragment(), this.rsv(), this.content().duplicate());
    }

    @Override
    public TextWebSocketFrame retain() {
        super.retain();
        return this;
    }

    @Override
    public TextWebSocketFrame retain(int increment) {
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

