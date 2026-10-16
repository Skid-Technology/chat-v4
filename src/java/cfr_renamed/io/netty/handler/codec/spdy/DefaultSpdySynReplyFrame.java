/*
 * Decompiled with CFR 0.152.
 */
package io.netty.handler.codec.spdy;

import io.netty.handler.codec.spdy.DefaultSpdyHeadersFrame;
import io.netty.handler.codec.spdy.SpdyHeadersFrame;
import io.netty.handler.codec.spdy.SpdyStreamFrame;
import io.netty.handler.codec.spdy.SpdySynReplyFrame;
import io.netty.util.internal.StringUtil;

public class DefaultSpdySynReplyFrame
extends DefaultSpdyHeadersFrame
implements SpdySynReplyFrame {
    public DefaultSpdySynReplyFrame(int streamId) {
        super(streamId);
    }

    @Override
    public SpdySynReplyFrame setStreamId(int streamId) {
        super.setStreamId(streamId);
        return this;
    }

    @Override
    public SpdySynReplyFrame setLast(boolean last) {
        super.setLast(last);
        return this;
    }

    @Override
    public SpdySynReplyFrame setInvalid() {
        super.setInvalid();
        return this;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(StringUtil.simpleClassName(this));
        buf.append("(last: ");
        buf.append(this.isLast());
        buf.append(')');
        buf.append(StringUtil.NEWLINE);
        buf.append("--> Stream-ID = ");
        buf.append(this.streamId());
        buf.append(StringUtil.NEWLINE);
        buf.append("--> Headers:");
        buf.append(StringUtil.NEWLINE);
        this.appendHeaders(buf);
        buf.setLength(buf.length() - StringUtil.NEWLINE.length());
        return buf.toString();
    }

    @Override
    public SpdyHeadersFrame setInvalid() {
        return this.setInvalid();
    }

    @Override
    public SpdyHeadersFrame setLast(boolean x0) {
        return this.setLast(x0);
    }

    @Override
    public SpdyHeadersFrame setStreamId(int x0) {
        return this.setStreamId(x0);
    }

    @Override
    public SpdyStreamFrame setLast(boolean x0) {
        return this.setLast(x0);
    }

    @Override
    public SpdyStreamFrame setStreamId(int x0) {
        return this.setStreamId(x0);
    }
}

